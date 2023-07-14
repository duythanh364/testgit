package tech.utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import tech.model.HostConfig;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author thanglv
 * @date 7/23/2021 3:18 PM
 */
public class PropertiesUtility {
    private static Logger logger= LogManager.getLogger(PropertiesUtility.class);

    public static PropertiesIgnoreCase loadPropertiesIgnoreCase(String filePath) throws IOException {
        PropertiesIgnoreCase p = new PropertiesIgnoreCase();
        FileInputStream input = new FileInputStream(filePath);
        p.load(new InputStreamReader(input, StandardCharsets.UTF_8));
        return p;
    }

    public static Properties loadProperties(String filePath) throws IOException {
        Properties p = new Properties();
        FileInputStream input = new FileInputStream(filePath);
        p.load(new InputStreamReader(input, StandardCharsets.UTF_8));
        return p;
    }

    public static void storeNewProperties(PropertiesIgnoreCase p, String filePath, String comment) throws IOException {
        p.store(new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8), comment);
    }

    public static void updateProperties(PropertiesIgnoreCase p, String filePath, String comment) throws Exception {
        p.store(new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8), comment);
    }

    public static Map<String, String> getMapData(String filePath) throws IOException {
        PropertiesIgnoreCase p = loadPropertiesIgnoreCase(filePath);
        Map<String, String> map = new HashMap<String, String>();
        Enumeration<?> e = p.keys();
        if (p.size() > 0) {
            while (e.hasMoreElements()) {
                String key = e.nextElement().toString();
                String value = p.getProperty(key);
                map.put(key, value.trim());
            }
        }
        return map;
    }
    public static HostConfig loadConfig(String node){
        try {
            File f= new File("config/agent.properties");
            Properties param = PropertiesUtility.loadProperties(f.getAbsolutePath());
            HostConfig h = new HostConfig();
            h.setHost(param.getProperty(node+"_HOST"));
            h.setPort(Integer.parseInt(param.getProperty(node+"_PORT")));
            h.setUsername(param.getProperty(node+"_USERNAME"));
            h.setPassword(param.getProperty(node+"_PASSWORD"));
            return h;
        } catch (IOException ex) {
            logger.error("Cant load file config: "+ex.getMessage(),ex);
            return null;
        }
    }
}
