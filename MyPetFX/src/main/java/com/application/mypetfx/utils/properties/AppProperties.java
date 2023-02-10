package com.application.mypetfx.utils.properties;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppProperties {

    private static final Logger logger = Logger.getLogger(AppProperties.class);

    // FILENAME = Path to properties-file
    // Store and protect it where ever you want
    private static final String FILENAME = "app-credentials.properties";

    private static final AppProperties config_file = new AppProperties();
    private final Properties prop = new Properties();
    private String msg = "";

    private AppProperties(){

        try (InputStream input = new FileInputStream(FILENAME))
        {
            // Load a properties
            prop.load(input);
        }
        catch(IOException ex){
            msg = "Can't find/open property file";
            logger.error("IO Error: ", ex);
        }

    }

    public String getProperty (String key){
        return prop.getProperty(key);
    }

    public String getMsg () {
        return msg;
    }

    // == Singleton design pattern == //
    // Where ever you call this methode in application
    // you always get the same and only instance (config_file)
    public static AppProperties getInstance(){
        return config_file;
    }

}
