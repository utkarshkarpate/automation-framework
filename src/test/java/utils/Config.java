package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Logger logger = LoggerFactory.getLogger(Config.class.getName());
    private static final String DEFAULT_PROPERTIES = "config/default.properties";
    private static Properties properties;
    public static void init(){
        //load default properties
         properties = loadProperties();

        //check for override
        for (String propertyName : properties.stringPropertyNames()) { //this will iterate over all the property names in the properties file
            if (System.getProperties().contains(propertyName)) {
                properties.setProperty(propertyName, System.getProperty(propertyName));
            }
        }
        logger.info("Loaded properties: " + properties);
        for(String propertyName : properties.stringPropertyNames()){
            logger.info("Property: " + propertyName + " = " + properties.getProperty(propertyName));
        }
    }

    public static String getProperty(String key) {
        String systemProperty = System.getProperty(key);
        if (systemProperty != null) {
            return systemProperty;
        }
        if (properties == null) {
            init();
        }
        return properties.getProperty(key);
    }

    private static Properties loadProperties(){
        Properties properties = new Properties();
        try(InputStream stream  = ResourceLoader.getResources(DEFAULT_PROPERTIES)){
            properties.load(stream);
        } catch (Exception e) {
            logger.error("Error loading properties file: " + DEFAULT_PROPERTIES, e);
        }
        return properties;
    }




    /*
    Modify Config.getProperty to Read System Properties: Update the Config class to prioritize system properties over default.properties. For example:
public static String getProperty(String key) {
    String systemProperty = System.getProperty(key);
    if (systemProperty != null) {
        return systemProperty;
    }
    return properties.getProperty(key); // Fallback to default.properties
}
     */
}
