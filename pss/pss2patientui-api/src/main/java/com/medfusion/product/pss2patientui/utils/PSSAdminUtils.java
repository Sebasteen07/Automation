package com.medfusion.product.pss2patientui.utils;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.product.object.maps.pss2.page.Login.PSS2AdminLogin;
import com.medfusion.product.object.maps.pss2.page.settings.AccessRules;
import com.medfusion.product.object.maps.pss2.page.settings.AdminPatientMatching;
import com.medfusion.product.object.maps.pss2.page.settings.PSS2PracticeConfiguration;
import com.medfusion.product.object.maps.pss2.page.settings.PatientFlow;
import com.medfusion.product.pss2patientui.pojo.AdminUser;
import com.medfusion.product.pss2patientui.pojo.Appointment;

public class PSSAdminUtils {

	public void adminSettings(WebDriver driver, AdminUser adminuser, Appointment testData, String urlToUse) throws Exception {
		PSS2PracticeConfiguration psspracticeConfig = loginToAdminPortal(driver, adminuser);

		AccessRules accessrule = psspracticeConfig.gotoAccessTab();
		Thread.sleep(2200);
		Log4jUtil.log("isLLExistingPatientSelected " + accessrule.isLLExistingPatientSelected());
		if (accessrule.isLLNewPatientSelected().equalsIgnoreCase("true")) {
			Log4jUtil.log("isLLInsurancePageSelected " + accessrule.isLLInsurancePageSelected());
			Log4jUtil.log("isLLPrivacyPolicySelected " + accessrule.isLLPrivacyPolicySelected());
			if (accessrule.isLLPrivacyPolicySelected().equalsIgnoreCase("true")) {
				accessrule.loginlessPrivacyPolicyClick();
			}
		} else {
			if (adminuser.getIsExisting() && adminuser.getIsLoginlessFlow() && accessrule.isLLExistingPatientSelected().equalsIgnoreCase("true")) {
				accessrule.selectLLExistingPatient();
			}
		}

		if (urlToUse.equalsIgnoreCase(PSSConstants.LOGINLESS)) {
			Log4jUtil.log("PSS Patient URL : " + accessrule.getLoginlessURL());
			testData.setUrlLoginLess(accessrule.getLoginlessURL());
		}
		if (urlToUse.equalsIgnoreCase(PSSConstants.IDP)) {
			Log4jUtil.log("PSS Patient URL : " + accessrule.getIDPUrl());
			testData.setUrlIPD(accessrule.getIDPUrl());
		}

		Log4jUtil.log("adminSettings Step 3: Navigate to Patient Flow tab in settings");
		PatientFlow patientflow = accessrule.gotoPatientFlowTab();
		Log4jUtil.log("are basic elements present " + patientflow.areBasicPageElementsPresent());

		Log4jUtil.log("adminSettings Step 4: Fetch the list of Rules");
		Log4jUtil.log("length " + patientflow.ruleLength());
		Log4jUtil.log("Rule length : " + patientflow.getRule());
		if (patientflow.ruleLength() > 0) {
			adminuser.setRule(patientflow.getRule());
		}
		Log4jUtil.log("Insurance Displayed ? " + patientflow.isIsuranceDisplayed());
		if (patientflow.isIsuranceDisplayed() != null && !patientflow.isIsuranceDisplayed().isEmpty()) {
			adminuser.setIsInsuranceDisplayed(true);
		}
		
		AdminPatientMatching adminpatientmatching = patientflow.gotoPatientMatchingTab();
		
		adminpatientmatching.patientMatchingSelection();
		Log4jUtil.log("adminSettings Step 5: Logout from PSS Admin Portal");
		adminpatientmatching.logout();
		Thread.sleep(18000);
	}

	public PSS2PracticeConfiguration loginToAdminPortal(WebDriver driver, AdminUser adminuser) throws Exception {
		Log4jUtil.log("adminSettings Step 1: Login to Admin portal ");
		PSS2AdminLogin pssadminlogin = new PSS2AdminLogin(driver, adminuser.getAdminUrl());
		PSS2PracticeConfiguration psspracticeConfig = pssadminlogin.login(adminuser.getUser(), adminuser.getPassword());

		Log4jUtil.log("refreshing admin page after login");
		pageRefresh(driver);
		return psspracticeConfig;
	}

	public void pageRefresh(WebDriver driver) throws InterruptedException {
		Thread.sleep(34000);
		 driver.navigate().refresh();
		 Thread.sleep(26000);
	}

	public AdminUser setPracticeAdminAccount(String staffPracitceName) throws IOException {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		AdminUser adminuser = new AdminUser();
		if (staffPracitceName.equalsIgnoreCase(PSSConstants.GW)) {
			propertyData.setAdminGW(adminuser);
		}
		if (staffPracitceName.equalsIgnoreCase(PSSConstants.GE)) {
			propertyData.setAdminGE(adminuser);
		}
		if (staffPracitceName.equalsIgnoreCase(PSSConstants.NG)) {
			propertyData.setAdminNG(adminuser);
		}
		return adminuser;
	}

}
