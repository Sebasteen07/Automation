// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.patientportal2.api.pojos;

import java.util.HashMap;
import java.util.Map;

import com.medfusion.common.utils.PropertyFileLoader;

public class NotificationProfilePayload {
	public static Map<String, Object> createNotificationMap(PropertyFileLoader testData) {
		Map<String, Object> securityMap = new HashMap<String, Object>();
		securityMap.put("mobilePhone", testData.getProperty("identity.mobile.phone"));
		securityMap.put("email", testData.getProperty("identity.email"));
		securityMap.put("notifyByEmail", testData.getProperty("identity.notify.by.email"));
		securityMap.put("notifyByMobile", testData.getProperty("identity.notify.by.mobile"));
		securityMap.put("notifyByText", testData.getProperty("identity.notify.by.text"));
		return securityMap;
	}
	
	public static Map<String, Object> updateNotificationMap(PropertyFileLoader testData, String mobile) {
		Map<String, Object> securityMap = new HashMap<String, Object>();
		securityMap.put("id", 53100);
		securityMap.put("mobilePhone", mobile);
		securityMap.put("email", testData.getProperty("identity.email"));
		securityMap.put("notifyByEmail", testData.getProperty("identity.notify.by.email"));
		securityMap.put("notifyByMobile", testData.getProperty("identity.notify.by.mobile"));
		securityMap.put("notifyByText", "true");
		return securityMap;
	}
}
