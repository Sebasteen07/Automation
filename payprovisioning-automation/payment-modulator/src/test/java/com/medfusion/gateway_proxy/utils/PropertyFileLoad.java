// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.gateway_proxy.utils;

import com.intuit.ifs.csscat.core.TestConfig;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class PropertyFileLoad {
    private Properties property = new Properties();

    public PropertyFileLoad(String env) throws IOException {
        String propertyFileNameString = env + ".properties";
        URL url = ClassLoader.getSystemResource("data-driven/" + propertyFileNameString);
        FileReader inputStream = new FileReader(url.getFile());
        this.property.load(inputStream);
    }

    public String getProperty(String prop) throws NullPointerException {
        if (this.property.getProperty(prop) == null) {
            throw new NullPointerException("Property " + prop + " not found in the property file.");
        } else {
            return this.property.getProperty(prop);
        }
    }

    public static Object getEnvironmentType() {
        return TestConfig.getUserDefinedProperty("test.environment");
    }
}
