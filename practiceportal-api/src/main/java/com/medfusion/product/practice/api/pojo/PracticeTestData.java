// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.practice.api.pojo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.medfusion.common.utils.IHGUtil;

public class PracticeTestData {
	private static Properties property = new Properties();

	public PracticeTestData() throws IOException {
		String env = IHGUtil.getEnvironmentType().toString();
		String propertyFileNameString = env + ".properties";
		InputStream url = ClassLoader.getSystemResourceAsStream("data-driven/" + propertyFileNameString);
		property.load(url);
	}

	public String getUrl() {
		return property.getProperty("practice.portal.url");
	}

	public String getPracticeUsername() {
		return property.getProperty("practice.portal.username");
	}

	public String getPracticePassword() {
		return property.getProperty("practice.portal.password");
	}
}
