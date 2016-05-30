package com.intuit.ihg.product.forms.test;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.medfusion.product.object.maps.patientportal1.page.questionnaires.FormWelcomePage;
import com.medfusion.product.patientportal1.pojo.Portal;
import com.medfusion.product.patientportal1.utils.TestcasesData;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;

public class FormsPIAcceptanceTests extends FormsAcceptanceTests {

	@Override
	@Test(enabled = true, groups = { "PIForms" })
	public void testDiscreteFormDeleteCreatePublish() throws Exception {
		String welcomeMessage = createFormSG();

		log("step 6: Go to Patient Portal using the original window");
		Portal portal = new Portal();
		TestcasesData portalData = new TestcasesData(portal);
		log("URL: " + portalData.getPIFormsUrl());

		log("step 7: Log in to Patient Portal");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver,
				portalData.getPIFormsUrl());
		JalapenoHomePage homePage = loginPage.login(portalData.getUsername(),
				portalData.getPassword());
		assertTrue(homePage.isMessagesButtonDisplayed(driver));

		log("step 8: Click On Start Registration Button and verify welcome page of the previously created form");
		homePage.clickStartRegistrationButton(driver);
		FormWelcomePage pFormWelcomePage = PageFactory.initElements(driver, FormWelcomePage.class);
		assertTrue(pFormWelcomePage.isWelcomePageLoaded());
		assertEquals(pFormWelcomePage.getMessageText(), welcomeMessage);

	}

}
