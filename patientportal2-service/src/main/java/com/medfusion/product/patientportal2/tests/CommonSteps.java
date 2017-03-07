package com.medfusion.product.patientportal2.tests;

import org.openqa.selenium.WebDriver;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.PatientDemographicPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.SecurityDetailsPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.patientportal2.pojo.JalapenoPatient;

public class CommonSteps {

	public static JalapenoHomePage createAndLogInPatient(JalapenoPatient patient, PropertyFileLoader testData,
			WebDriver driver, String practiceUrl) {
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, practiceUrl);
		PatientDemographicPage patientDemographicPage = loginPage.clickCreateANewAccountButton();
		patientDemographicPage.fillInPatientData(patient);
		SecurityDetailsPage accountDetailsPage = patientDemographicPage.continueToSecurityPage();
		JalapenoHomePage homePage = accountDetailsPage.fillAccountDetailsAndContinue(patient.getEmail(), patient.getPassword(), testData);
		return homePage;
	}
}
