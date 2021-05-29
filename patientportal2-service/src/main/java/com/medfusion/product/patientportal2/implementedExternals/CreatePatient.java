//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.patientportal2.implementedExternals;

import com.medfusion.pojos.Patient;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.PatientDemographicPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.SecurityDetailsPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.patientportal2.flows.ICreatePatient;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

public class CreatePatient implements ICreatePatient {

	@Override
	public Patient selfRegisterPatient(WebDriver driver, Patient patient, String url) throws Exception {
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, url);
		PatientDemographicPage patientDemographicPage = loginPage.clickCreateANewAccountButton();
		patientDemographicPage.fillInPatientData(patient);
		SecurityDetailsPage accountDetailsPage = patientDemographicPage.continueToSecurityPage();
		JalapenoHomePage homePage = accountDetailsPage.fillAccountDetailsAndContinue(patient);
		homePage.clickOnLogout();
		patient.setWasSelfRegistered(true);
		return patient;
	}
	
	/*
	 * This method is used when we need to pass the statement delivery option 
	 * from testScript.In this we can pass the Statement delivery 
	 * value to get selected and proceed to patient portal
	 */
	
	public Patient selfRegisterPatientWithPreference(WebDriver driver, Patient patient, String url,int statementValue) throws Exception {
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, url);
		PatientDemographicPage patientDemographicPage = loginPage.clickCreateANewAccountButton();
		patientDemographicPage.fillInPatientData(patient);
		SecurityDetailsPage accountDetailsPage = patientDemographicPage.continueToSecurityPage();
		JalapenoHomePage homePage = accountDetailsPage.fillAccountDetailsAndContinueWithStatement(patient, statementValue);
		homePage.clickOnLogout();
		patient.setWasSelfRegistered(true);
		return patient;
	}
	
	public Patient registerPatient(WebDriver driver, Patient patient, String url) throws Exception {
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, url);
		PatientDemographicPage patientDemographicPage = loginPage.clickCreateANewAccountButton();
		patientDemographicPage.fillInPatientData(patient);
		SecurityDetailsPage accountDetailsPage = patientDemographicPage.continueToSecurityPage();
		accountDetailsPage.fillAccountDetailsAndContinue(patient);
		patient.setWasSelfRegistered(true);
		return patient;
	}

	public Patient selfRegisterUnderAgePatient(WebDriver driver, Patient patient, String url) throws Exception {
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, url);
		PatientDemographicPage patientDemographicPage = loginPage.clickCreateANewAccountButton();
		patientDemographicPage.fillInUnderAgePatientData(patient);
		return patient;

	}

	public Patient selfRegisterPatientStateSpecific(WebDriver driver, Patient patient, String url)
			throws InterruptedException, IOException {
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, url);
		PatientDemographicPage patientDemographicPage = loginPage.clickCreateANewAccountButton();
		patientDemographicPage.fillInPatientDataStateSpecific(patient);
		return patient;
	}

}
