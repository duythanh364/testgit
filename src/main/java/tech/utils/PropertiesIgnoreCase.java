package tech.utils;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

/**
 * @author thanglv
 * @date 8/3/2021 10:52 AM
 */
public class PropertiesIgnoreCase extends Properties {
    
    @Override
    public synchronized Object setProperty(String key, String value) {
        return super.setProperty(key.toLowerCase(), value);
    }

    @Override
    public String getProperty(String key) {
        return super.getProperty(key.toLowerCase());
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        return super.getProperty(key.toLowerCase(), defaultValue);
    }

    @Override
    public synchronized void load(Reader reader) throws IOException {
        super.load(reader);
    }
}
