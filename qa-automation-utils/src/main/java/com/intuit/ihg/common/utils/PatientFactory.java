//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.common.utils;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.pojos.Patient;

public class PatientFactory {

		public static Patient createJalapenoPatient(String username, PropertyFileLoader testData) throws NullPointerException, Exception {
				Patient patient = new Patient();
				patient.setUsername(username);
				patient.setEmail(testData.getProperty("last.name") + IHGUtil.createRandomNumber()+"@yopmail.com");
				patient.setFirstName(testData.getProperty("first.name") + IHGUtil.createRandomNumber());
				patient.setLastName(testData.getProperty("last.name"));
				patient.setPassword(testData.getProperty("password"));
				patient.setGender(Patient.GenderExtended.MALE);
				patient.setDOBDay(testData.getProperty("dob.day"));
				patient.setDOBMonth(testData.getProperty("dob.month"));
				patient.setDOBYear(testData.getProperty("dob.year"));
				patient.setZipCode(testData.getProperty("zip.code"));
				patient.setAddress1(testData.getProperty("address1"));
				patient.setAddress2(testData.getProperty("address2"));
				patient.setCity(testData.getProperty("city"));
				patient.setState(testData.getProperty("state"));
				patient.setSecurityQuestion(testData.getProperty("secret.question"));
				patient.setSecurityQuestionAnswer(testData.getProperty("secret.answer"));
				patient.setPhoneMobile(testData.getProperty("phone.number"));
				return patient;
		}

		public static Patient generateNewPatient(PropertyFileLoader testData) throws NullPointerException, Exception {
				//TODO generate patient with random data
				return null;
		}

		public static Patient loadStaticPatient(PropertyFileLoader testData) throws NullPointerException, Exception {
				//TODO load data for already existing patient
				return null;
		}
}
