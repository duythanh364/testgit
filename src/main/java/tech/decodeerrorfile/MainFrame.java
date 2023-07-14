
package tech.decodeerrorfile;

import com.google.common.io.Files;
import com.jcraft.jsch.Session;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import tech.utils.PropertiesUtility;
import tech.utils.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class MainFrame extends javax.swing.JFrame {
        private Logger logger= LogManager.getLogger(MainFrame.class);

    private DefaultTableModel tableFileName;
    private int stt;
    private Utils utils;
    private JFileChooser fileChooseAma;
    private JFileChooser fileChooseIcc;

    private String placeSaveAma;
    private String placeSaveIcc;
    Properties param;
    private Session session;
    public MainFrame() {
        initComponents();
        setLocationRelativeTo(null);
        stt=0;
        setTitle("Chọn file");
        try {
            File f= new File("config/agent.properties");

            param=PropertiesUtility.loadProperties(f.getAbsolutePath());
        } catch (IOException ex) {
            logger.error("Cant read file properties config: "+ex.getMessage(),ex);
            JOptionPane.showMessageDialog(null,"Can't load file properties config !");
            return;
        }
        tableFileName = (DefaultTableModel) table1.getModel();
        utils= new Utils();

        session = utils.connectToTestbed(); 
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                session.disconnect();
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnSaveAma = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table1 = new javax.swing.JTable();
        decodeAndSave = new javax.swing.JButton();
        fileNameField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        iccFolder = new javax.swing.JLabel();
        btnSaveIcc = new javax.swing.JButton();
        amaFolder = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnSaveAma.setText("Chọn thư mục lưu file AMA");
        btnSaveAma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveAmaActionPerformed(evt);
            }
        });

        table1.setModel(new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên file"
            }
        ));
        jScrollPane1.setViewportView(table1);
        if (table1.getColumnModel().getColumnCount() > 0) {
            table1.getColumnModel().getColumn(0).setMinWidth(80);
            table1.getColumnModel().getColumn(0).setPreferredWidth(80);
            table1.getColumnModel().getColumn(0).setMaxWidth(80);
            table1.getColumnModel().getColumn(1).setMinWidth(560);
            table1.getColumnModel().getColumn(1).setPreferredWidth(560);
            table1.getColumnModel().getColumn(1).setMaxWidth(560);
        }

        decodeAndSave.setText("Decode and Save");
        decodeAndSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                decodeAndSaveActionPerformed(evt);
            }
        });

        jLabel1.setText("Nhập tên file: ");

        btnSaveIcc.setText("Chọn thư mục lưu file ICC");
        btnSaveIcc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveIccActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 24, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 639, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(85, 85, 85)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fileNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(decodeAndSave, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnSaveIcc, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnSaveAma, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(iccFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(amaFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(27, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(fileNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSaveAma)
                    .addComponent(amaFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSaveIcc, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(iccFolder, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(decodeAndSave)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveAmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveAmaActionPerformed
        
        try {
            if(!utils.connectToSurepay(fileNameField.getText().split("[.]")[0])){
                JOptionPane.showMessageDialog(null, "Can't connect to Surepay Server !");
                return;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Cant connect to Surepay Server !");
            logger.error("Can't connect to Surepay server "+ ex.getMessage(), ex);
            return;
        }
        
        fileChooseAma = new JFileChooser("/");
        fileChooseAma.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooseAma.showSaveDialog(fileChooseAma);

        amaFolder.setText(fileChooseAma.getSelectedFile().getAbsolutePath());

    }//GEN-LAST:event_btnSaveAmaActionPerformed

    private void decodeAndSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_decodeAndSaveActionPerformed
        
        stt++;
        tableFileName.addRow(new Object[]{stt,fileNameField.getText()});
        // Download file from surepay
        File f = fileChooseAma.getSelectedFile();        
        try {
            
            if( !utils.saveFile(f.getAbsolutePath(), fileNameField.getText())){
                amaFolder.setText("Can't save file");
                
            }
        } catch (Exception ex) {
            logger.error("Can't save file: "+ex.getMessage(),ex);
            amaFolder.setText("Can't save file");
        }
        // rename with name != HNOCS1, copy to new file
        String name= fileNameField.getText();
        String nameProcess= utils.processName(name);
        String nameSplit[]= nameProcess.split("[.]");
        String nameReplace= nameProcess;
        
        File amaFolder = fileChooseAma.getSelectedFile();
        File iccFolder = fileChooseIcc.getSelectedFile();
        
        File fileAma = new File(amaFolder.getAbsolutePath()+"//"+nameProcess);
         File fileReplace=null;
        if(!nameSplit[0].equals("HNOCS1")){
            try {
                nameReplace=nameProcess.replace(nameSplit[0], "HNOCS1");
                fileReplace = new File(amaFolder.getAbsolutePath()+"//"+nameReplace);
                Files.copy(fileAma, fileReplace);
            } catch (IOException ex) {
                logger.error("Cant copy file in decode and save: "+ex.getMessage(),ex);
            }
        }
        // upload and decode
        if(session != null){
            if(utils.UploadFileAndDecodeInTestbed(session,amaFolder.getAbsolutePath()+"//"+nameReplace,nameReplace)){
                utils.downloadFileFromTestbed(session, 
                        param.getProperty("ICCOUPUT_DIR")+nameReplace+".txt", 
                        iccFolder.getAbsolutePath(), 
                            nameProcess+".txt");
                fileReplace.delete();
            }
            else{
                int kt = JOptionPane.showConfirmDialog(null, "Decode và upload bị lỗi, hãy kiểm tra lại !","Lỗi", JOptionPane.YES_NO_OPTION);
                if(kt != 1){
                    return;
                }
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Cant connect to TestBed");
            logger.error("Cant connect to TestBed ");
            return;
        }
    }//GEN-LAST:event_decodeAndSaveActionPerformed

    private void btnSaveIccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveIccActionPerformed
        fileChooseIcc = new JFileChooser("/");
        fileChooseIcc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooseIcc.showSaveDialog(fileChooseIcc);
        iccFolder.setText(fileChooseIcc.getSelectedFile().getAbsolutePath());

    }//GEN-LAST:event_btnSaveIccActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel amaFolder;
    private javax.swing.JButton btnSaveAma;
    private javax.swing.JButton btnSaveIcc;
    private javax.swing.JButton decodeAndSave;
    private javax.swing.JTextField fileNameField;
    private javax.swing.JLabel iccFolder;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table1;
    // End of variables declaration//GEN-END:variables
}
