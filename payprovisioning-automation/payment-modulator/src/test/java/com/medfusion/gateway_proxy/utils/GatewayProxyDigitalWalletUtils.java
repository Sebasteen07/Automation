// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.gateway_proxy.utils;

import com.medfusion.common.utils.IHGUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class GatewayProxyDigitalWalletUtils {

    public static void saveWalletDetails(String externalWalletId, String externalCardId) throws FileNotFoundException, IOException {
        Properties property = new Properties();
        String env = IHGUtil.getEnvironmentType().toString();
        String propertyFileNameString = env + ".properties";
        String fileName = "src/test/resources/data-driven/"+propertyFileNameString;
        FileInputStream configStream = new FileInputStream(fileName);
        property.load(configStream);
        configStream.close();

        property.setProperty("externalWalletId", externalWalletId);
        property.setProperty("externalCardId", externalCardId);
        FileOutputStream output = new FileOutputStream(fileName);
        property.store(output, null);
        output.close();
    }
}
