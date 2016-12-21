package com.intuit.ihg.product.integrationplatform.utils;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.PatientVerificationPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.SecurityDetailsPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;

public class PatientRegistrationUtils {
	public void registerPatient(String activationUrl,String EmailID,String PatientPassword,String SecretQuestion,String SecretAnswer,String RegisterTelephone,WebDriver driver,String zip,String birthdate) throws InterruptedException {
		
		String[] Date = birthdate.split("/");
		Log4jUtil.log("Retrieved activation link is " + activationUrl);
		Log4jUtil.log("Finishing of patient activation: step 1 - verifying identity");
		PatientVerificationPage patientActivationPage = new PatientVerificationPage(driver, activationUrl);
		SecurityDetailsPage accountDetailsPage = patientActivationPage.fillPatientInfoAndContinue(zip, Date[0], Date[1], Date[2]);
		Log4jUtil.log("Finishing of patient activation: step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage =
				accountDetailsPage.fillAccountDetailsAndContinue(EmailID, PatientPassword, SecretQuestion, SecretAnswer,RegisterTelephone);
	
		Log4jUtil.log("Detecting if Home Page is opened");
		Thread.sleep(2000);
		Assert.assertTrue(jalapenoHomePage.isHomeButtonPresent(driver));
		
		Log4jUtil.log("Logging out");
		jalapenoHomePage.clickOnLogout();
	}
}