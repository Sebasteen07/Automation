package com.intuit.ihg.product.forms.test;

import org.testng.annotations.Test;

import com.intuit.ihg.product.object.maps.portal.page.questionnaires.FormWelcomePage;
import com.intuit.ihg.product.portal.utils.Portal;
import com.intuit.ihg.product.portal.utils.TestcasesData;
import com.medfusion.product.object.maps.jalapeno.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.jalapeno.page.HomePage.JalapenoHomePage;

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
		FormWelcomePage pFormWelcomePage = homePage.clickStartRegistrationButton(driver);
		assertTrue(pFormWelcomePage.isWelcomePageLoaded());
		assertEquals(pFormWelcomePage.getMessageText(), welcomeMessage);

	}

}
