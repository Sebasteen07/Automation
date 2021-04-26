package com.medfusion.gateway_proxy.utils;

import com.medfusion.common.utils.IHGUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class DigitalWalletUtils {


    public static void saveWalletDetails(String value1, String value2) throws FileNotFoundException, IOException {
        Properties property = new Properties();
        String env = IHGUtil.getEnvironmentType().toString();
        String propertyFileNameString = env + ".properties";
        String filename = "src/test/resources/data-driven/"+propertyFileNameString;
        FileInputStream configStream = new FileInputStream(filename);
        property.load(configStream);
        configStream.close();

        property.setProperty("externalWalletId",value1 );
        property.setProperty("externalCardId",value2 );
        FileOutputStream output = new FileOutputStream(filename);
        property.store(output, null);
        output.close();
    }
}
