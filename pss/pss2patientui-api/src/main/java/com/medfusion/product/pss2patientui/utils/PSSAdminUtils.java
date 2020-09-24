// Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientui.utils;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.ManageAppointmentType;
import com.medfusion.product.object.maps.pss2.page.Location.ManageLocation;
import com.medfusion.product.object.maps.pss2.page.Login.PSS2AdminLogin;
import com.medfusion.product.object.maps.pss2.page.Resource.ManageResource;
import com.medfusion.product.object.maps.pss2.page.settings.AccessRules;
import com.medfusion.product.object.maps.pss2.page.settings.AdminPatientMatching;
import com.medfusion.product.object.maps.pss2.page.settings.InsuranceCarrier;
import com.medfusion.product.object.maps.pss2.page.settings.PSS2PracticeConfiguration;
import com.medfusion.product.object.maps.pss2.page.settings.PatientFlow;
import com.medfusion.product.pss2patientui.pojo.AdminUser;
import com.medfusion.product.pss2patientui.pojo.Appointment;

public class PSSAdminUtils {

	public void adminSettings(WebDriver driver, AdminUser adminuser, Appointment testData, String urlToUse) throws Exception {
		PSS2PracticeConfiguration psspracticeConfig = loginToAdminPortal(driver, adminuser);
		AccessRules accessrule = psspracticeConfig.gotoAccessTab();
		Thread.sleep(2000);
		Log4jUtil.log("New = " + accessrule.isLLNewPatientSelected());
		Log4jUtil.log("Existing = " + accessrule.isLLExistingPatientSelected());
		Log4jUtil.log("isLLExistingPatientSelected " + accessrule.isLLExistingPatientSelected());
		if (accessrule.isLLNewPatientSelected().equalsIgnoreCase("true")) {
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
		if (urlToUse.equalsIgnoreCase(PSSConstants.ANONYMOUS)) {
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
			if (patientflow.getRule().contains(PSSConstants.SPECIALITY)) {
				setRulesNoSpecialitySet1(patientflow);
			}
			Thread.sleep(4000);
			Log4jUtil.log("Rule length : " + patientflow.getRule());
			adminuser.setRule(patientflow.getRule());
		}
		Log4jUtil.log("Insurance Displayed ? " + patientflow.isIsuranceDisplayed());
		if (patientflow.isIsuranceDisplayed().equalsIgnoreCase("true")) {
			adminuser.setIsInsuranceDisplayed(false);
		}
		AdminPatientMatching adminpatientmatching = patientflow.gotoPatientMatchingTab();

		adminpatientmatching.patientMatchingSelection();
		Log4jUtil.log("adminSettings Step 5: Logout from PSS Admin Portal");
		adminpatientmatching.logout();
		Thread.sleep(4000);
	}

	// ****************ADMIN SETTINGS FOR ANONYMOUS FLOW**************************
	public void adminSettingsAnonymous(WebDriver driver, AdminUser adminuser, Appointment testData, String urlToUse) throws Exception {
		Log4jUtil.log("adminSettings Step 1: LOGIN TO ADMIN");
		PSS2PracticeConfiguration psspracticeConfig = loginToAdminPortal(driver, adminuser);
		Log4jUtil.log("adminSettings Step 2: Navigate to ACCESS RULE tab in settings");
		AccessRules accessrule = psspracticeConfig.gotoAccessTab();
		Thread.sleep(2000);
		Log4jUtil.log("Status of Enable Anonymous Flow = " + accessrule.isEnableAnonymousSelected());
		Log4jUtil.log("Status of Display Privacy policy = " + accessrule.isDisplayPrivacypolicyAnonymous());
		Log4jUtil.log("Status of Allow Duplicate Patient " + accessrule.isAllowDuplicatePatientAnonymous());
		Log4jUtil.log("Status of Enable OTP " + accessrule.isEnableOtpAnonymous());
		if (urlToUse.equalsIgnoreCase(PSSConstants.LOGINLESS)) {
			Log4jUtil.log("PSS Patient URL : " + accessrule.getLoginlessURL());
			testData.setUrlLoginLess(accessrule.getLoginlessURL());
		}
		if (urlToUse.equalsIgnoreCase(PSSConstants.IDP)) {
			Log4jUtil.log("PSS Patient URL : " + accessrule.getIDPUrl());
			testData.setUrlIPD(accessrule.getIDPUrl());
		}
		if (urlToUse.equalsIgnoreCase(PSSConstants.ANONYMOUS)) {
			Log4jUtil.log("PSS Patient URL : " + accessrule.getAnonymousUrl());
			testData.setUrlAnonymous(accessrule.getAnonymousUrl());
		}
		accessrule.saveAnonymouSetting();
		Log4jUtil.log("Click on Save Button");
		Log4jUtil.log("adminSettings Step 3: Navigate to Patient Flow tab in settings");
		PatientFlow patientflow = accessrule.gotoPatientFlowTab();
		Log4jUtil.log("are basic elements present " + patientflow.areBasicPageElementsPresent());
		Log4jUtil.log("adminSettings Step 4: Fetch the list of Rules");
		Log4jUtil.log("length " + patientflow.ruleLength());
		Log4jUtil.log("Rule length : " + patientflow.getRule());
		if (patientflow.ruleLength() > 0) {
			if (patientflow.getRule().contains(PSSConstants.SPECIALITY)) {
				setRulesNoSpecialitySet1(patientflow);
			}
			Thread.sleep(4000);
			Log4jUtil.log("Rule length : " + patientflow.getRule());
			adminuser.setRule(patientflow.getRule());
		}
		Log4jUtil.log("Insurance Displayed ? " + patientflow.isIsuranceDisplayed());
		if (patientflow.isIsuranceDisplayed().equalsIgnoreCase("true")) {
			adminuser.setIsInsuranceDisplayed(false);
		}
		AdminPatientMatching adminpatientmatching = patientflow.gotoPatientMatchingTab();
		adminpatientmatching.patientMatchingSelection();
		Log4jUtil.log("adminSettings Step 5: Logout from PSS Admin Portal");
		adminpatientmatching.logout();
		Thread.sleep(4000);
	}

	public PSS2PracticeConfiguration loginToAdminPortal(WebDriver driver, AdminUser adminuser) throws Exception {
		Log4jUtil.log("adminSettings Step 1: Login to Admin portal. url=" + adminuser.getAdminUrl() + " and username=" + adminuser.getUser() + " and password="
				+ adminuser.getPassword());
		PSS2AdminLogin pssadminlogin = new PSS2AdminLogin(driver, adminuser.getAdminUrl());
		PSS2PracticeConfiguration psspracticeConfig = pssadminlogin.login(adminuser.getUser(), adminuser.getPassword());
		Log4jUtil.log("refreshing admin page after login");
		pageRefresh(driver);
		return psspracticeConfig;
	}

	public void pageRefresh(WebDriver driver) throws InterruptedException {
		Thread.sleep(6000);
		driver.navigate().refresh();
		Thread.sleep(14000);
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

	public void getAdminRule(WebDriver driver, AdminUser adminuser) throws Exception {
		PSS2PracticeConfiguration psspracticeConfig = loginToAdminPortal(driver, adminuser);
		PatientFlow patientflow = psspracticeConfig.gotoPatientFlowTab();
		patientflow.getRule();
		Log4jUtil.log("Rule = " + patientflow.getRule());
		patientflow.logout();
	}

	public void navigateTo(PSS2PracticeConfiguration psspracticeConfig) {
		ManageLocation mlocation = psspracticeConfig.gotoLocation();
		mlocation.showMoreLocation();
		List<WebElement> lList = mlocation.getLocationNameList();

		Log4jUtil.log("location Size = " + lList.size());
		for (int i = 0; i < lList.size(); i++) {
			Log4jUtil.log("location names = " + lList.get(i).getText());
		}
		ManageAppointmentType mappointmenttype = mlocation.gotoAppointment();
		mappointmenttype.aptNameList();
		Log4jUtil.log("apt Size = " + mappointmenttype.aptNameList().size());
		for (int i = 0; i < mappointmenttype.aptNameList().size(); i++) {
			Log4jUtil.log("appointment names = " + mappointmenttype.aptNameList().get(i).getText());
		}
		ManageResource mresource = mappointmenttype.gotoResource();
		mresource.areBasicPageElementsPresent();
	}

	public void setRuleWithoutSpeciality(WebDriver driver, AdminUser adminuser) throws Exception {
		PSS2PracticeConfiguration practiceconfiguration = loginToAdminPortal(driver, adminuser);
		PatientFlow patientflow = practiceconfiguration.gotoPatientFlowTab();
		setRulesNoSpecialitySet1(patientflow);
		Log4jUtil.log("Logging out of PSS 2.0 admin UI");
		patientflow.logout();
	}

	public void setRulesNoSpecialitySet1(PatientFlow patientflow) throws InterruptedException {
		patientflow.removeAllRules();
		Log4jUtil.log("-----------------------------------------------------------------------------------------");
		patientflow.addNewRulesButton();
		patientflow.selectRuleName("AppointmentType");
		patientflow.addNewRules(PSSConstants.RULE_APPOINTMENT_VALUE);
		patientflow.addNewRules(PSSConstants.RULE_LOCATION_VALUE);
		patientflow.addNewRules(PSSConstants.RULE_PROVIDER_VALUE);
		patientflow.saveRule();
		Thread.sleep(8000);
		Log4jUtil.log("--------------------------------WAIT FOR RULE TLB TO BE ADDED--------------------------------");
		patientflow.addNewRulesButton();
		patientflow.selectRuleName("Location");
		patientflow.addNewRules(PSSConstants.RULE_LOCATION_VALUE);
		patientflow.addNewRules(PSSConstants.RULE_APPOINTMENT_VALUE);
		patientflow.addNewRules(PSSConstants.RULE_PROVIDER_VALUE);
		patientflow.saveRule();
		Thread.sleep(8000);
		Log4jUtil.log("--------------------------------WAIT FOR RULE LTB TO BE ADDED--------------------------------");
		patientflow.addNewRulesButton();
		patientflow.selectRuleName("Provider");
		patientflow.addNewRules(PSSConstants.RULE_PROVIDER_VALUE);
		patientflow.addNewRules(PSSConstants.RULE_APPOINTMENT_VALUE);
		patientflow.addNewRules(PSSConstants.RULE_LOCATION_VALUE);
		patientflow.saveRule();
		Thread.sleep(8000);
		Log4jUtil.log("--------------------------------WAIT FOR RULE BTL TO BE ADDED--------------------------------");
	}

	public void setRulesNoSpecialitySet2(PatientFlow patientflow) throws InterruptedException {
		patientflow.removeAllRules();
		Log4jUtil.log("-----------------------------------------------------------------------------------------");
		patientflow.addNewRulesButton();
		patientflow.selectRuleName("AppointmentType");
		patientflow.addNewRules(PSSConstants.RULE_APPOINTMENT_VALUE);
		patientflow.addNewRules(PSSConstants.RULE_PROVIDER_VALUE);
		patientflow.addNewRules(PSSConstants.RULE_LOCATION_VALUE);
		patientflow.saveRule();
		Thread.sleep(8000);
		Log4jUtil.log("--------------------------------WAIT FOR RULE TBL TO BE ADDED--------------------------------");
		patientflow.addNewRulesButton();
		patientflow.selectRuleName("Location");
		patientflow.addNewRules(PSSConstants.RULE_LOCATION_VALUE);
		patientflow.addNewRules(PSSConstants.RULE_PROVIDER_VALUE);
		patientflow.addNewRules(PSSConstants.RULE_APPOINTMENT_VALUE);
		patientflow.saveRule();
		Thread.sleep(8000);
		Log4jUtil.log("--------------------------------WAIT FOR RULE LBT TO BE ADDED--------------------------------");
		patientflow.addNewRulesButton();
		patientflow.selectRuleName("Provider");
		patientflow.addNewRules(PSSConstants.RULE_PROVIDER_VALUE);
		patientflow.addNewRules(PSSConstants.RULE_LOCATION_VALUE);
		patientflow.addNewRules(PSSConstants.RULE_APPOINTMENT_VALUE);
		patientflow.saveRule();
		Thread.sleep(8000);
		Log4jUtil.log("--------------------------------WAIT FOR RULE BLT TO BE ADDED--------------------------------");
	}

	public void setInsuranceState(WebDriver driver, AdminUser adminuser) throws Exception {
		PSS2PracticeConfiguration psspracticeConfig = loginToAdminPortal(driver, adminuser);
		PatientFlow patientflow = psspracticeConfig.gotoPatientFlowTab();
		Thread.sleep(6000);
		Log4jUtil.log("isInsuranceToBeDisplayed=" + patientflow.isInsuranceToBeDisplayed());
		if (!patientflow.isInsuranceToBeDisplayed()) {
			patientflow.selectInsurance();
		}
		adminuser.setRule(patientflow.getRule());
		Log4jUtil.log("rule= " + patientflow.getRule());
		Log4jUtil.log("Patient Flow page Show Insurance= " + patientflow.isInsuranceToBeDisplayed());
		Thread.sleep(6000);
		InsuranceCarrier insurancecarrier = patientflow.gotoInsuranceCarrierTab();
		Log4jUtil.log("Basic elements of Insurance carrier page located? " + insurancecarrier.areBasicPageElementsPresent());
		insurancecarrier.enableshowInsuranceAtStart();
		insurancecarrier.logout();
	}
}
