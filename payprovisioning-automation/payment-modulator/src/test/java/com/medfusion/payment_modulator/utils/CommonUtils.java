// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.payment_modulator.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import com.medfusion.common.utils.IHGUtil;

public class CommonUtils {
	

	public static void saveTransactionDetails(String value1, String value2) throws FileNotFoundException, IOException {
		Properties property = new Properties();
		String env = IHGUtil.getEnvironmentType().toString();
		String propertyFileNameString = env + ".properties";
		String filename = "src/test/resources/data-driven/"+propertyFileNameString;
		FileInputStream configStream = new FileInputStream(filename);
		property.load(configStream);
		configStream.close();
		
		property.setProperty("external.transaction.id",value1 );
		property.setProperty("order.id",value2 );
		FileOutputStream output = new FileOutputStream(filename);
		property.store(output, null);
	    output.close();
	}
}
	
