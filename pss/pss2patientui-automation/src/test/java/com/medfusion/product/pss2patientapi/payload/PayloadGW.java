// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientapi.payload;

import java.util.HashMap;
import java.util.Map;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

public class PayloadGW extends BaseTestNGWebDriver {
	
	

	public Map<String, Object> pastApptPayload(String patientId) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("endDate", null);
		hm.put("patientId", patientId);

		HashMap<String, Object> practiceProvision = new HashMap<String, Object>();
		practiceProvision.put("active", true);
		practiceProvision.put("practiceId", "24253");
		practiceProvision.put("practiceName", "PSS-GW-12242@1#(%#(%(#%");
		practiceProvision.put("practiceDisplayName", "PSS-GW-12242@1#(%#(%(#%");
		practiceProvision.put("practiceTimezone", "America/New_York");

		hm.put("practiceProvision", practiceProvision);
		hm.put("startDate", "05/05/2021 02:00:00");

		return hm;
	}

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
	
	public Map<String, Object> availableslotsPayload(String appointmentCatId,String appointmentTypeId,String extId,String locationId,
			String patientId,String resourceCatId,String resourceId,String startTime) {

		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("appointmentCategoryId", appointmentCatId);
		hm.put("appointmentTypeId", appointmentTypeId);
		hm.put("apptTypeAllocated", true);
		hm.put("duration", null);
     	hm.put("endDate", null);
     	hm.put("extApptId", extId);
		hm.put("leadTime", "0");
		hm.put("locationId", locationId);
		hm.put("nextAvailability", true);
		hm.put("patientId", patientId);
		hm.put("practiceTimezone", "America/New_York");
		hm.put("preventScheduling",0);
		hm.put("reservedForSameDay",true);
		hm.put("resourceCategoryId", resourceCatId);
		hm.put("resourceId", resourceId);
    	hm.put("sameDayAppointment", true);
		hm.put("slotCount", 0);
		hm.put("slotLimit", null);
		hm.put("slotSize", "5");
		hm.put("stackingFlag",true);
		hm.put("startDate", startTime);
		return hm;

	
	}

	public Map<String, Object> nextAvailableslotsPayload(String appointmentCatId,String appointmentTypeId,String extId,String locationId,
			String patientId,String resourceCatId,String resourceId,String startTime) {

		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("appointmentCategoryId", appointmentCatId);
		hm.put("appointmentTypeId", appointmentTypeId);
		hm.put("apptTypeAllocated", true);
		hm.put("duration", null);
     	hm.put("endDate", null);
     	hm.put("extApptId", extId);
		hm.put("leadTime", "0");
		hm.put("locationId", locationId);
		hm.put("nextAvailability", true);
		hm.put("patientId", patientId);
		hm.put("practiceTimezone", "America/New_York");
		hm.put("preventScheduling",0);
		hm.put("reservedForSameDay",true);
		hm.put("resourceCategoryId", resourceCatId);
		hm.put("resourceId", resourceId);
    	hm.put("sameDayAppointment", true);
		hm.put("slotCount", 0);
		hm.put("slotLimit", null);
		hm.put("slotSize", "15");
		hm.put("stackingFlag",true);
		hm.put("startDate", startTime);
		return hm;
		
	}

	
	
}
