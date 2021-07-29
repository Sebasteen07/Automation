// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientapi.payload;

import java.util.HashMap;
import java.util.Map;

public class PayloadAT {

	public Map<String, Object> pastApptPayload(String enddate, String patientId) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("endDate", enddate);
		hm.put("patientId", patientId);

		HashMap<String, Object> practiceProvision = new HashMap<String, Object>();
		practiceProvision.put("active", true);
		practiceProvision.put("practiceId", "24269");
		practiceProvision.put("practiceName", "PSS - Athena - Dev1");
		practiceProvision.put("practiceDisplayName", "PSS - Athena - Dev1");
		practiceProvision.put("practiceTimezone", "America/New_York");

		hm.put("practiceProvision", practiceProvision);
		hm.put("providerId", "27");
		hm.put("startDate", "05/05/2021 02:00:00");

		return hm;
	}

	public Map<String, Object> cancelappointmentPayload(String appointmentId) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("appointmentId", appointmentId);
		return hm;
	}


}
