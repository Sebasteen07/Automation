package com.medfusion.product.pss2patientmodulatorapi.test;

import java.util.HashMap;
import java.util.Map;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

public class PayloadGW extends BaseTestNGWebDriver {

	public Map<String, Object> searchPatientPayload(String dob, String firstName, String gender, String lastName,
			String practiceTZ) {

		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("dateOfBirth", dob);
		hm.put("firstName", firstName);
		hm.put("gender", gender);
		hm.put("lastName", lastName);
		hm.put("practiceTimezone", practiceTZ);

		return hm;

	}
}
