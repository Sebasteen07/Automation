package com.medfusion.product.pss2adminportal.test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.product.object.maps.pss2.page.Login.PSS2AdminLogin;
import com.medfusion.product.object.maps.pss2.page.settings.PSS2PracticeConfiguration;
import com.medfusion.product.pss2patientui.pojo.AdminUser;
import com.medfusion.product.pss2patientui.utils.PSSAdminUtils;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

public class PSS2AdminPortalAcceptanceTests extends BaseTestNGWebDriver {

	@DataProvider(name = "staffPractice")
	public Object[][] portalVersionForRegistration() {
		Object[][] obj = new Object[][] { {"GW"}, {"GE"}, {"NG"}};
		return obj;
	}

	@Test(enabled = true, dataProvider = "staffPractice", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAdminLoginForExistingStaffAndPractice(String staffPracitceName) throws Exception {
		log("Test To verify if existing staff and practice can Login" + staffPracitceName);

		log("Step 1: set test data for existing patient ");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		AdminUser adminuser = new AdminUser();
		if (staffPracitceName.equalsIgnoreCase("GW")) {
			propertyData.setAdminGW(adminuser);
		}
		if (staffPracitceName.equalsIgnoreCase("GE")) {
			propertyData.setAdminGE(adminuser);
		}
		if (staffPracitceName.equalsIgnoreCase("NG")) {
			propertyData.setAdminNG(adminuser);
		}
		log("Step 2: Login to Admin portal ");
		PSS2AdminLogin pssadminlogin = new PSS2AdminLogin(driver, adminuser.getAdminUrl());
		PSS2PracticeConfiguration psspracticeConfig = pssadminlogin.login(adminuser.getUser(), adminuser.getPassword());

		Log4jUtil.log("refreshing admin page after login");
		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		pssadminutils.pageRefresh(driver);

		log("Theme Selected for the practice is = " + psspracticeConfig.getSelectedColor());
		log("Step 3 : Verify client logo link " + psspracticeConfig.checkLogoLink());
		assertTrue(psspracticeConfig.checkLogoLink().contains(adminuser.getPracticeId()));

		log("Step 4 : Verify client Practice ID= " + psspracticeConfig.practiceIDLinkText());
		assertTrue(psspracticeConfig.practiceIDLinkText().contains(adminuser.getPracticeId()), "Staff Practice ID not found");

		log("Step 5: Logout from PSS Admin Portal");
		psspracticeConfig.logout();

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testString() throws Exception {
		log("Test String");
		String a1 = " L, B, T";
		a1 = a1.replaceAll(" ", "");
		String s1 = "L,B,T";
		assertTrue(a1.equalsIgnoreCase(s1), "different string ");
	}
}
