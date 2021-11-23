package com.medfusion.util;

import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;

/**
 * Created by lhrub on 02.12.2015.
 */
public class Data {


    private static TreeProperties environmentProperties = new TreeProperties();

    static {
        try {
            //TODO put it to some better place
            Properties testConfig = new Properties();
            testConfig.load(new FileInputStream("testConfig.properties"));
            String env = testConfig.getProperty("test.environment");

            FileInputStream inputStream = new FileInputStream("src/test/resources/test-data/" + env + ".properties");
            environmentProperties.load(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return environmentProperties.getProperty(key);
    }

    public static Map<String,String> getMapFor(String key){ return environmentProperties.getPropertySubtree(key);}

    }
