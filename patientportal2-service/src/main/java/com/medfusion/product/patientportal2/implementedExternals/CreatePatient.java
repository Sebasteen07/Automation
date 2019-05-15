package com.medfusion.product.patientportal2.implementedExternals;

import com.medfusion.pojos.Patient;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.PatientDemographicPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.SecurityDetailsPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.patientportal2.flows.ICreatePatient;
import org.openqa.selenium.WebDriver;

public class CreatePatient implements ICreatePatient {
		@Override
		public Patient selfRegisterPatient(WebDriver driver, Patient patient, String url) {
				JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, url);
				PatientDemographicPage patientDemographicPage = loginPage.clickCreateANewAccountButton();
				patientDemographicPage.fillInPatientData(patient);
				SecurityDetailsPage accountDetailsPage = patientDemographicPage.continueToSecurityPage();
				JalapenoHomePage homePage = accountDetailsPage.fillAccountDetailsAndContinue(patient);
				homePage.clickOnLogout();
				patient.setWasSelfRegistered(true);
				return patient;
		}

}

