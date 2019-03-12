package com.intuit.ihg.common.utils;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.pojos.Patient;
import com.medfusion.portal.utils.PortalUtil;

public class PatientFactory {

		public static Patient createJalapenoPatient(String username, PropertyFileLoader testData) throws NullPointerException, Exception {
				Patient patient = new Patient();
				patient.setUsername(username);
				patient.setEmail(username + "@mailinator.com");
				patient.setFirstName(testData.getProperty("FirstName") + PortalUtil.createRandomNumber());
				patient.setLastName(testData.getProperty("LastName"));
				patient.setPassword(testData.getProperty("password"));
				patient.setGender(Patient.GenderExtended.MALE); //TODO
				patient.setDOBDay(testData.getProperty("DOBDay"));
				patient.setDOBMonth(testData.getProperty("DOBMonth"));
				patient.setDOBYear(testData.getProperty("DOBYear"));
				patient.setZipCode(testData.getProperty("ZipCode"));
				patient.setAddress1(testData.getProperty("Address1"));
				patient.setAddress2(testData.getProperty("Address2"));
				patient.setCity(testData.getProperty("City"));
				patient.setState(testData.getProperty("State"));
				patient.setSecurityQuestion(testData.getProperty("SecretQuestion"));
				patient.setSecurityQuestionAnswer(testData.getProperty("SecretAnswer"));
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
