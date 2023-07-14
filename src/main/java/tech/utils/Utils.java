/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.utils;


import com.jcraft.jsch.*;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import tech.model.HostConfig;

import java.io.*;
import java.util.Properties;
import java.util.zip.GZIPInputStream;

public class Utils {
    private Logger logger= LogManager.getLogger(Utils.class);
    private FTPClient ftp = new FTPClient();
    private Properties param;
    public Utils(){
        try {
            File f= new File("config/agent.properties");
            param = PropertiesUtility.loadProperties(f.getAbsolutePath());
        } catch (IOException ex) {
            logger.error("Cant load file config properties: "+ex.getMessage(), ex);
        }
    }
    public String processName(String source) {
        if (source.contains(".gz")) {

            String name= source.replace(".gz","");
            String targetName= source.replace(source, name);
            return targetName;

        }
        else return source;
    }

    public void unGzip(File source, File target) {

        GZIPInputStream gis = null;
        try {
            gis = new GZIPInputStream(
                    new FileInputStream(source));
            FileOutputStream fos = new FileOutputStream(target);
            // copy GZIPInputStream to FileOutputStream
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        } catch (IOException ex) {
            logger.error("Unzip file error:  "+ ex.getMessage(), ex);
        }
    }
    public boolean connectToSurepay(String nodename) throws IOException, Exception{
        HostConfig config= PropertiesUtility.loadConfig(nodename);
        

        if(config != null){
            String user = config.getUsername();
            String password = config.getPassword();
            String host = config.getHost();
            int port = config.getPort();
            String remoteFile = param.getProperty("file_surepay");
            // update code to cdr
            ftp.setControlKeepAliveTimeout(10);
            ftp.setListHiddenFiles(false);

            final int reply;
            ftp.connect(host,port);

            if (!ftp.login(user,password)) {
                ftp.logout();
                logger.error("Login failed");
            }

            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.changeWorkingDirectory(remoteFile);

    //            ftp.sendCommand("hihi");
    //            ftp.getReplyString();
    //            luu file tren server
    //            FileInputStream fis = new FileInputStream("D:\\vnpt\\filetestcdr\\"+fileNameTest);
    //            ftp.storeFile(fileNameTest, fis );
            return true;
        }
        else return false;
    }
    public boolean saveFile(String folderPath, String fileName) throws Exception{
        String fileNameTest= processName(fileName);

        boolean isDone = downloadFile(fileName, folderPath+"\\"+fileName);
        
        if(fileName.contains(".gz")){
            File fileUnzip = new File(folderPath+"\\"+fileName);
            File fileAfterExtract = new File(folderPath+"\\"+fileNameTest);
            unGzip(fileUnzip, fileAfterExtract );
        }
        return isDone;
    }
    
    public boolean downloadFile(String remoteFile, String localFilePath) throws FileNotFoundException, IOException
    {
        try (FileOutputStream fos = new FileOutputStream(localFilePath)) {

            this.ftp.retrieveFile(remoteFile, fos);
            fos.flush();
            fos.close();
            logger.info("Finish download file " + remoteFile);
            return true;
        } catch (IOException e) {
            logger.error("Error download file " + e.getMessage(),e);
            return false;
        }
    }

    public Session connectToTestbed(){
        try { 
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            HostConfig configHost= PropertiesUtility.loadConfig("TESTBED");
            Session session=jsch.getSession(configHost.getUsername(),configHost.getHost() , configHost.getPort());
            session.setPassword(configHost.getPassword());
            
            session.setConfig(config);
            session.connect();
            logger.info("Connect to testbed success");
            return session;
        } catch (JSchException ex) {
            logger.error("Error connect to testbed: "+ex.getMessage(), ex);
            return null;
        }
    }
    public boolean UploadFileAndDecodeInTestbed( Session session, String localFile, String fileName){
        String command1="cd /app/ocssp/cdr/cdrhnocs2; ./service.sh stop; ./service.sh start; tail -100f logs/traceCdr.txt";
        
        try{
            
            if( session!= null ){
                ChannelExec channelExec = (ChannelExec)session.openChannel("exec");
                channelExec.setCommand(command1);
                channelExec.setErrStream(System.err);                
                InputStream ins=channelExec.getInputStream();
                channelExec.connect();
                
                ChannelSftp channelUpload= (ChannelSftp)session.openChannel("sftp");
                channelUpload.connect();
                //put file to amainput để chạy service
                if(readByteFromTestBed(channelExec, "", "Started CdrMediationApplication")){

                    channelUpload.put(localFile, param.getProperty("AMAINPUT_DIR") + fileName);
                }
                if(readByteFromTestBed(channelExec, fileName, "Process Done")) {
                    channelUpload.disconnect();
                    channelExec.disconnect();
                    logger.info("upload and decode file Done");
                    return true;
                }
            }
            else{
                return false;
            }
        }catch(Exception e){
            logger.error("Error in Upload and Decode: "+e.getMessage(),e);
            return false;
        }
        return false;
    }
    public void downloadFileFromTestbed(Session session, String remoteFile, String localDir, String localFile ){
        try {
            ChannelSftp channelSftp= (ChannelSftp)session.openChannel("sftp");
            channelSftp.connect();
            channelSftp.get(remoteFile, localDir +"/"+ localFile);
            logger.info("Download file icc done");
            channelSftp.disconnect();
        } catch (JSchException ex) {
            logger.error("Error connect when download file from Testbed "+ex.getMessage(),ex);
        } catch (SftpException ex) {
            logger.error("Error download file from Testbed "+ex.getMessage(),ex);
        }
    }
    public boolean readByteFromTestBed(ChannelExec channelExec, String fileName, String checkProcess) throws IOException{
        boolean isEnd= false;
        boolean isContain= false;
        InputStream  ins=channelExec.getInputStream();
        
        byte[] tmp=new byte[1024];
        while(true){
            while(ins.available()>0){
                int i=ins.read(tmp, 0, 1024);
                if(i<0)break;
                String check = new String(tmp, 0, i);
                System.out.println(check);
                if(check.contains(fileName) && check.contains(checkProcess)){
                    logger.info("Decode done, detail: "+check);
                    isEnd=true;
                    isContain=true;
                    return isContain;
                }
            }
            if(isEnd) {
                break;
            }
            if(channelExec.isClosed()){
              break;
            }
            try{Thread.sleep(1000);}catch(Exception ee){}
        }
        return isContain;
    }
}
