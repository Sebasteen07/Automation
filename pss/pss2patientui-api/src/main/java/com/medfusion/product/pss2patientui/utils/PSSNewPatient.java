package com.medfusion.product.pss2patientui.utils;

import org.apache.commons.lang.RandomStringUtils;

import com.medfusion.product.pss2patientui.pojo.Appointment;

public class PSSNewPatient {
	public PSSNewPatient() {

	}

	public Appointment createPatientDetails(Appointment appointment) {
		Long timeStamp = System.currentTimeMillis();
		String firstName = "firstName";
		appointment.setFirstName(firstName + timeStamp);
		appointment.setGender("M");
		appointment.setLastName("lastName" + timeStamp);
		appointment.setDob(PSSConstants.DOB);
		appointment.setPrimaryNumber(randomNumbers(10));
		appointment.setZipCode(PSSConstants.ZIP);
		appointment.setFirstName("firstName" + timeStamp);
		String email = firstName + timeStamp + "@yopmail.com";
		appointment.setEmail(email);
		return appointment;
	}

	public static String randomNumbers(int number) {
		return RandomStringUtils.random(number, false, true);
	}
}
