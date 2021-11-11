package com.medfusion.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by lhrub on 26.11.2015.
 */
public class TreeProperties extends Properties {
    public TreeProperties() {
        super();
    }

    //TODO Do it in smart way
    public Map<String,String> getPropertySubtree(String key){
        Map<String,String> subtree = new HashMap<String, String>();

        for (String propertyName : super.stringPropertyNames() ) {
            if (propertyName.startsWith(key)) {
                subtree.put(propertyName.replace(key + ".",""), getProperty(propertyName));
            }
        }

        return subtree;
    }
}
