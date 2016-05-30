package com.medfusion.product.patientportal2.tests;

import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.JalapenoCreateAccountPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.JalapenoPatientActivationPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.IHGUtil.Gender;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.patientportal2.utils.PortalUtil;
import org.openqa.selenium.WebDriver;

public class JalapenoPatient extends BaseTestNGWebDriver {

	private String email = "";
	private String password = "";
	private String url = "";
	private String firstName = "";
	private String lastName = "";

	private Gender gender = null;

	// Getters for getting the email and password value and reusing in other
	// tests
	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String str) {
		url = str;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setLastName(String str) {
		lastName = str;
	}

	public String getLastName() {
		return lastName;
	}

	public Gender getGender() {
		return gender;
	}

	public JalapenoPatient initPatientData(PropertyFileLoader testData) {
		IHGUtil.PrintMethodName();
		
		int randomize = PortalUtil.createRandomNumber();
		
		//Setting data according to test purpose
		if (email.isEmpty()) { email = IHGUtil.createRandomEmailAddress(testData.getEmail(),'.'); }
		if (firstName.isEmpty()) { firstName = testData.getFirstName() + randomize; }
		if (lastName.isEmpty()) { lastName = "TestPatient1"; }
		if (password.isEmpty()) { password = testData.getPassword(); }
		if (gender == null) { gender = Gender.MALE; }
		if (url == null || url.isEmpty()) { url = testData.getUrl(); }

		return this;
	}

	public JalapenoHomePage createAndLogInPatient(WebDriver driver, PropertyFileLoader testData) {
		initPatientData(testData);

		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, url);

		JalapenoCreateAccountPage createAccountPage = loginPage.clickSignInButton();
		assertTrue(createAccountPage.assessCreateAccountPageElements());

		createAccountPage.setName(firstName, lastName)
				.setEmail(email)
				.setGender(gender)
				.setZipCode(testData.getZipCode())
				.setDateOfBirth(testData);

		JalapenoPatientActivationPage patientActivationPage = createAccountPage.goToNextPage();

		assertTrue(patientActivationPage.assessPatientActivationPageElements(true));
		return patientActivationPage.fillInPatientActivation(getEmail(), getPassword(), testData);
	}

}
