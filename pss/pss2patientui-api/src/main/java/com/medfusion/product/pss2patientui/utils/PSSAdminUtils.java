// Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientui.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.product.object.maps.pss2.page.PSS2MenuPage;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.ManageAppointmentType;
import com.medfusion.product.object.maps.pss2.page.CancelReason.ManageCancelReason;
import com.medfusion.product.object.maps.pss2.page.Location.ManageLocation;
import com.medfusion.product.object.maps.pss2.page.Login.PSS2AdminLogin;
import com.medfusion.product.object.maps.pss2.page.Resource.ManageResource;
import com.medfusion.product.object.maps.pss2.page.settings.AccessRules;
import com.medfusion.product.object.maps.pss2.page.settings.AdminAppointment;
import com.medfusion.product.object.maps.pss2.page.settings.AdminPatientMatching;
import com.medfusion.product.object.maps.pss2.page.settings.InsuranceCarrier;
import com.medfusion.product.object.maps.pss2.page.settings.LinkTab;
import com.medfusion.product.object.maps.pss2.page.settings.PSS2PracticeConfiguration;
import com.medfusion.product.object.maps.pss2.page.settings.PatientFlow;
import com.medfusion.product.pss2patientui.pojo.AdminUser;
import com.medfusion.product.pss2patientui.pojo.Appointment;

public class PSSAdminUtils {

	public void adminSettings(WebDriver driver, AdminUser adminuser, Appointment testData, String urlToUse)
			throws Exception {
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
			if (adminuser.getIsExisting() && adminuser.getIsLoginlessFlow()
					&& accessrule.isLLExistingPatientSelected().equalsIgnoreCase("true")) {
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

	public void adminSettingsLoginless(WebDriver driver, AdminUser adminuser, Appointment testData, String urlToUse)
			throws Exception {
		Log4jUtil.log("****************ADMIN SETTINGS FOR Loginless FLOW**************************");
		PSS2PracticeConfiguration psspracticeConfig = loginToAdminPortal(driver, adminuser);
		AccessRules accessrule = psspracticeConfig.gotoAccessTab();
		Thread.sleep(2000);
		Log4jUtil.log("New_Patient Select Checkedbox = " + accessrule.isLLNewPatientSelected());
		Log4jUtil.log("Existing_Patient Select Checkbox = " + accessrule.isLLExistingPatientSelected());
		if (accessrule.isLLNewPatientSelected().equalsIgnoreCase("true")) {
			Log4jUtil.log("isLLPrivacyPolicySelected " + accessrule.isLLPrivacyPolicySelected());
			if (accessrule.isLLPrivacyPolicySelected().equalsIgnoreCase("false")) {
				accessrule.loginlessPrivacyPolicyClick();
				Log4jUtil.log("PrivacyPolicySelected is set TRUE");
			}
		} else {
			if (adminuser.getIsExisting() && adminuser.getIsLoginlessFlow()
					&& accessrule.isLLExistingPatientSelected().equalsIgnoreCase("true")) {
				accessrule.selectLLExistingPatient();
			}
		}
		// Log4jUtil.log("------------Set OTP Settings off for loginless
		// flow----------");
		// if (accessrule.isEnableOTPSelected().equalsIgnoreCase("true")) {
		// Log4jUtil.log("Status of EnableOTP is " + accessrule.isEnableOTPSelected());
		// accessrule.clickEnableOTP();
		// Log4jUtil.log("Enable OTP is set False");
		// } else {
		// Log4jUtil.log("Enable OTP is already False no need to change");
		// }
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
		Log4jUtil.log("adminSettings Step 3: Navigate to Patient Flow tab in settings");
		PatientFlow patientflow = accessrule.gotoPatientFlowTab();
		Log4jUtil.log("are basic elements present " + patientflow.areBasicPageElementsPresent());

		Log4jUtil.log("adminSettings Step 4: Fetch the list of Rules");
		adminuser.setRule(patientflow.getRule());
		Log4jUtil.log("rule= " + patientflow.getRule());

		Log4jUtil.log("AdminSettings Step 5: Fetch the Insurance Status");
		testData.setIsinsuranceVisible(patientflow.insuracetogglestatus());
		Log4jUtil.log("Insurance Status= " + patientflow.insuracetogglestatus());

		Log4jUtil.log("AdminSettings Step 6: Fetch the Starting Point Status");
		testData.setIsstartpointPresent(patientflow.isstartpagepresent());
		Log4jUtil.log("Startpoint  Status= " + patientflow.isstartpagepresent());

		AdminPatientMatching adminpatientmatching = patientflow.gotoPatientMatchingTab();
		adminpatientmatching.patientMatchingSelection();
		Log4jUtil.log("adminSettings Step 5: Logout from PSS Admin Portal");
		adminpatientmatching.logout();
		Thread.sleep(4000);
	}

	public void adminSettingsAnonymous(WebDriver driver, AdminUser adminuser, Appointment testData, String urlToUse)
			throws Exception {
		Log4jUtil.log("****************ADMIN SETTINGS FOR ANONYMOUS FLOW**************************");
		Log4jUtil.log("adminSettings Step 1: LOGIN TO ADMIN");
		PSS2PracticeConfiguration psspracticeConfig = loginToAdminPortal(driver, adminuser);
		Log4jUtil.log("adminSettings Step 2: Navigate to ACCESS RULE tab in settings");
		AccessRules accessrule = psspracticeConfig.gotoAccessTab();
		Thread.sleep(2000);
		Log4jUtil.log("Status of Enable Anonymous Flow = " + accessrule.isEnableAnonymousSelected());
		Log4jUtil.log("Status of Display Privacy policy = " + accessrule.isDisplayPrivacypolicyAnonymous());
		Log4jUtil.log("Status of Allow Duplicate Patient " + accessrule.isAllowDuplicatePatientAnonymous());

		// OTP code will require in future release so commenting the code
		// Log4jUtil.log("Status of Enable OTP " + accessrule.isEnableOtpAnonymous());

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
		Log4jUtil.log("adminSettings Step 1: Login to Admin portal. url=" + adminuser.getAdminUrl() + " and username="
				+ adminuser.getUser() + " and password=" + adminuser.getPassword());
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
		Log4jUtil.log(
				"Basic elements of Insurance carrier page located? " + insurancecarrier.areBasicPageElementsPresent());
		insurancecarrier.enableshowInsuranceAtStart();
		insurancecarrier.logout();

	}

	public void getInsuranceStateandRule(WebDriver driver, AdminUser adminuser, Appointment appointment)
			throws Exception {
		PSS2PracticeConfiguration psspracticeConfig = loginToAdminPortal(driver, adminuser);
		PatientFlow patientflow = psspracticeConfig.gotoPatientFlowTab();
		adminuser.setRule(patientflow.getRule());
		Log4jUtil.log("rule= " + patientflow.getRule());
		appointment.setIsinsuranceVisible(patientflow.insuracetogglestatus());
		Log4jUtil.log("Insurance is Enabled= " + patientflow.insuracetogglestatus());
		appointment.setIsstartpointPresent(patientflow.isstartpagepresent());
		Log4jUtil.log("StartPage is Visible= " + patientflow.isstartpagepresent());
		patientflow.logout();
	}

	public ArrayList<String> getCancelRescheduleSettings(WebDriver driver, AdminUser adminuser, Appointment testData,
			AdminAppointment adminAppointment) throws Exception {
		PSS2PracticeConfiguration psspracticeConfig = loginToAdminPortal(driver, adminuser);
		PatientFlow patientflow = psspracticeConfig.gotoPatientFlowTab();
		adminuser.setRule(patientflow.getRule());
		Log4jUtil.log("rule= " + patientflow.getRule());
		testData.setIsinsuranceVisible(patientflow.insuracetogglestatus());
		Log4jUtil.log("Insurance is Enabled= " + patientflow.insuracetogglestatus());
		testData.setIsstartpointPresent(patientflow.isstartpagepresent());
		Log4jUtil.log("StartPage is Visible= " + patientflow.isstartpagepresent());
		adminAppointment = patientflow.gotoAdminAppointmentTab();
		adminAppointment.areBasicPageElementsPresent();
		Log4jUtil.log(
				"Step 3: Set the Cancellation & rescheduling lead time (hrs)- " + PSSConstants.CANCEL_APT_UPTO_HRS);
		adminAppointment.updateCancelAppointmentSettings(PSSConstants.CANCEL_APT_UPTO_HRS);
		Log4jUtil.log("Step 4: Fetch the status of cancel settings from Admin");
		adminAppointment.toggleCancelReason();
		Log4jUtil.log("Verify the Cancel Settings in ADMIN TAB");
		boolean cancel1 = adminAppointment.isShowCancellationRescheduleReason();
		Log4jUtil.log("verifying the settings of Cancel/Reschedule Reason : " + cancel1);
		Thread.sleep(2000);
		if (cancel1) {
			Log4jUtil.log("Cancel/Reschedule Reason ALREADY turned ON..");
			testData.setShowCancellationRescheduleReason(cancel1);
			boolean cancel2 = adminAppointment.isShowCancellationReasonPM();

			Log4jUtil.log("verifying the settings of Cancel Reason from PM : " + cancel2);
			if (cancel2) {
				testData.setShowCancellationReasonPM(cancel2);
				Log4jUtil.log("cancel 1 - ON and Cance2 - ON");
			} else {
				Log4jUtil.log("cancel 1 - ON and Cance2 - OFF");
			}
		} else {
			Log4jUtil.log("Cancel/Reschedule reason setting is OFF-Defaults pop up message will display");
			Log4jUtil.log("cancel 1 - OFF and Cancel2 - OFF");
		}
		PSS2MenuPage pss2MenuPage = adminAppointment.saveSlotCancelReasonSetting();
		ManageCancelReason manageCancelReason = pss2MenuPage.gotoCancelReason();
		ArrayList<String> list = manageCancelReason.fetchCancelReasonList(driver);
		patientflow.logout();
		return list;
	}

	public void getRescheduleSettings(WebDriver driver, AdminUser adminuser, Appointment testData,
			AdminAppointment adminAppointment) throws Exception {
		PSS2PracticeConfiguration psspracticeConfig = loginToAdminPortal(driver, adminuser);
		PatientFlow patientflow = psspracticeConfig.gotoPatientFlowTab();
		adminuser.setRule(patientflow.getRule());
		Log4jUtil.log("rule= " + patientflow.getRule());
		testData.setIsinsuranceVisible(patientflow.insuracetogglestatus());
		Log4jUtil.log("Insurance is Enabled= " + patientflow.insuracetogglestatus());
		testData.setIsstartpointPresent(patientflow.isstartpagepresent());
		Log4jUtil.log("StartPage is Visible= " + patientflow.isstartpagepresent());
		adminAppointment = patientflow.gotoAdminAppointmentTab();
		adminAppointment.areBasicPageElementsPresent();
		Log4jUtil.log(
				"Step 3: Set the Cancellation & rescheduling lead time (hrs)- " + PSSConstants.CANCEL_APT_UPTO_HRS);
		adminAppointment.updateCancelAppointmentSettings(PSSConstants.CANCEL_APT_UPTO_HRS);
		Log4jUtil.log("Step 4: Fetch the status of cancel settings from Admin");
		adminAppointment.toggleCancelReason();
		Log4jUtil.log("Verify the Cancel Settings in ADMIN TAB");
		boolean cancel1 = adminAppointment.isShowCancellationRescheduleReason();
		Log4jUtil.log("verifying the settings of Cancel/Reschedule Reason : " + cancel1);
		Thread.sleep(1000);
		if (cancel1) {
			Log4jUtil.log("Cancel/Reschedule Reason ALREADY turned ON..");
			testData.setShowCancellationRescheduleReason(cancel1);
			boolean cancel2 = adminAppointment.isShowCancellationReasonPM();

			Log4jUtil.log("verifying the settings of Cancel Reason from PM : " + cancel2);
			if (cancel2) {
				testData.setShowCancellationReasonPM(cancel2);
				Log4jUtil.log("cancel 1 - ON and Cance2 - ON");
			} else {
				Log4jUtil.log("cancel 1 - ON and Cance2 - OFF");
			}
		} else {
			Log4jUtil.log("Cancel/Reschedule reason setting is OFF-Defaults pop up message will display");
			Log4jUtil.log("cancel 1 - OFF and Cancel2 - OFF");
		}
		adminAppointment.saveSlotCancelReasonSetting();
	}

	public void leadTimenotReserve(WebDriver driver, AdminUser adminuser, Appointment appointment) throws Exception {

		PSS2PracticeConfiguration psspracticeConfig = loginToAdminPortal(driver, adminuser);
		psspracticeConfig = psspracticeConfig.gotoPracticeConfigTab();
		appointment.setBusinesshourStartTime(psspracticeConfig.gettextbusineesHourStarttime());
		appointment.setBusinesshourEndTime(psspracticeConfig.gettextbusineesHourEndtime());
		Log4jUtil.log("Starttime is " + appointment.getBusinesshourStartTime());
		Log4jUtil.log("End time is" + appointment.getBusinesshourEndTime());
		PatientFlow patientflow = psspracticeConfig.gotoPatientFlowTab();
		adminuser.setRule(patientflow.getRule());
		Log4jUtil.log("rule= " + patientflow.getRule());
		setRulesNoSpecialitySet1(patientflow);
		appointment.setIsinsuranceVisible(patientflow.insuracetogglestatus());
		Log4jUtil.log("Insurance is Enabled= " + patientflow.insuracetogglestatus());
		appointment.setIsstartpointPresent(patientflow.isstartpagepresent());
		Log4jUtil.log("StartPage is Visible= " + patientflow.isstartpagepresent());
		ManageResource manageResource = psspracticeConfig.gotoResource();
		pageRefresh(driver);
		manageResource.selectResource(appointment.getProvider());
		manageResource.selectAppointmenttype(appointment.getAppointmenttype());
		appointment.setLeadtimeDay(manageResource.getDay());
		Log4jUtil.log("Lead time Day is = " + appointment.getLeadtimeDay());
		appointment.setLeadtimeHour(manageResource.getHour());
		Log4jUtil.log("Lead time Hour is = " + appointment.getLeadtimeHour());
		appointment.setLeadtimeMinute(manageResource.getMinut());
		Log4jUtil.log("Lead time Minute is = " + appointment.getLeadtimeMinute());
		manageResource.notreserve();
		Log4jUtil.log("Status for AcceptFor Same day is" + manageResource.acceptforStatus());
		appointment.setAccepttoggleStatus(manageResource.acceptforStatus());
		Log4jUtil.log("Status for AcceptFor Same day is" + appointment.isAccepttoggleStatus());
		ManageLocation manageLocation = psspracticeConfig.gotoLocation();
		manageLocation.selectlocation(appointment.getLocation());
		appointment.setCurrentTimeZone(manageLocation.getTimezone());
		Log4jUtil.log("Current Timezone On AdminUi " + appointment.getCurrentTimeZone());
		patientflow.logout();
	}

	public void reserveforDay(WebDriver driver, AdminUser adminuser, Appointment appointment) throws Exception {
		PSS2PracticeConfiguration psspracticeConfig = loginToAdminPortal(driver, adminuser);
		psspracticeConfig = psspracticeConfig.gotoPracticeConfigTab();
		PatientFlow patientflow = psspracticeConfig.gotoPatientFlowTab();
		adminuser.setRule(patientflow.getRule());
		Log4jUtil.log("rule= " + patientflow.getRule());
		setRulesNoSpecialitySet1(patientflow);
		appointment.setBusinesshourStartTime(psspracticeConfig.gettextbusineesHourStarttime());
		appointment.setBusinesshourEndTime(psspracticeConfig.gettextbusineesHourEndtime());
		Log4jUtil.log("Starttime is " + appointment.getBusinesshourStartTime());
		Log4jUtil.log("End time is" + appointment.getBusinesshourEndTime());
		ManageResource mr = psspracticeConfig.gotoResource();
		pageRefresh(driver);
		mr.selectResource(appointment.getProvider());
		mr.selectAppointmenttype(appointment.getAppointmenttype());
		appointment.setLeadtimeDay(mr.getDay());
		Log4jUtil.log("Lead time Day is = " + appointment.getLeadtimeDay());
		appointment.setLeadtimeHour(mr.getHour());
		Log4jUtil.log("Lead time Hour is = " + appointment.getLeadtimeHour());
		appointment.setLeadtimeMinute(mr.getMinut());
		Log4jUtil.log("Lead time Minute is = " + appointment.getLeadtimeMinute());
		mr.reserveFor();
		ManageLocation manageLocation = psspracticeConfig.gotoLocation();
		manageLocation.selectlocation(appointment.getLocation());
		appointment.setCurrentTimeZone(manageLocation.getTimezone());
		Log4jUtil.log("Current Timezone On AdminUi " + appointment.getCurrentTimeZone());
		Log4jUtil.log("Successfully upto reserve for same day");
	}

	public void maxPerDayAT(WebDriver driver, AdminUser adminuser, Appointment appointment) throws Exception {

		PSS2PracticeConfiguration psspracticeConfig = loginToAdminPortal(driver, adminuser);
		PatientFlow patientflow = psspracticeConfig.gotoPatientFlowTab();
		adminuser.setRule(patientflow.getRule());
		Log4jUtil.log("rule= " + patientflow.getRule());
		appointment.setResourcetoggleStatus(patientflow.resourcetoggleStatus());
		Log4jUtil.log("Resource is Enabled= " + patientflow.resourcetoggleStatus());
		if (patientflow.resourcetoggleStatus() == false) {
			patientflow.clickonProviderToggle();
		}
		setRulesNoSpecialitySet1(patientflow);
		appointment.setIsinsuranceVisible(patientflow.insuracetogglestatus());
		Log4jUtil.log("Insurance is Enabled= " + patientflow.insuracetogglestatus());
		appointment.setIsstartpointPresent(patientflow.isstartpagepresent());
		Log4jUtil.log("StartPage is Visible= " + patientflow.isstartpagepresent());
		ManageResource manageResource = psspracticeConfig.gotoResource();
		pageRefresh(driver);
		manageResource.selectResource(appointment.getProvider());
		manageResource.selectAppointmenttype(appointment.getAppointmenttype());
		manageResource.maxperDay(appointment.getMaxperDay());
		Log4jUtil.log("Max per Day is " + appointment.getMaxperDay());
		patientflow.logout();
	}

	public void maxPerDayGE(WebDriver driver, AdminUser adminuser, Appointment appointment) throws Exception {

		PSS2PracticeConfiguration psspracticeConfig = loginToAdminPortal(driver, adminuser);
		PatientFlow patientflow = psspracticeConfig.gotoPatientFlowTab();
		adminuser.setRule(patientflow.getRule());
		Log4jUtil.log("rule= " + patientflow.getRule());
		setRulesNoSpecialitySet1(patientflow);
		appointment.setIsinsuranceVisible(patientflow.insuracetogglestatus());
		Log4jUtil.log("Insurance is Enabled= " + patientflow.insuracetogglestatus());
		appointment.setIsstartpointPresent(patientflow.isstartpagepresent());
		Log4jUtil.log("StartPage is Visible= " + patientflow.isstartpagepresent());
		ManageResource manageResource = psspracticeConfig.gotoResource();
		pageRefresh(driver);
		manageResource.selectResource(appointment.getProvider());
		manageResource.selectAppointmenttype(appointment.getAppointmenttype());
		manageResource.maxperDay(appointment.getMaxperDay());
		Log4jUtil.log("Max per Day is " + appointment.getMaxperDay());
		patientflow.logout();
	}

	public void acceptforsameDay(WebDriver driver, AdminUser adminuser, Appointment appointment) throws Exception {
		PSS2PracticeConfiguration psspracticeConfig = loginToAdminPortal(driver, adminuser);
		psspracticeConfig = psspracticeConfig.gotoPracticeConfigTab();
		appointment.setBusinesshourStartTime(psspracticeConfig.gettextbusineesHourStarttime());
		appointment.setBusinesshourEndTime(psspracticeConfig.gettextbusineesHourEndtime());
		Log4jUtil.log("Starttime is " + appointment.getBusinesshourStartTime());
		Log4jUtil.log("End time is" + appointment.getBusinesshourEndTime());
		PatientFlow patientflow = psspracticeConfig.gotoPatientFlowTab();
		setRulesNoSpecialitySet1(patientflow);
		adminuser.setRule(patientflow.getRule());
		ManageResource manageResource = psspracticeConfig.gotoResource();
		pageRefresh(driver);
		manageResource.selectResource(appointment.getProvider());
		manageResource.selectAppointmenttype(appointment.getAppointmenttype());
		appointment.setLeadtimeDay(manageResource.getDay());
		Log4jUtil.log("Lead time Day is = " + appointment.getLeadtimeDay());
		appointment.setLeadtimeHour(manageResource.getHour());
		Log4jUtil.log("Lead time Hour is = " + appointment.getLeadtimeHour());
		appointment.setLeadtimeMinute(manageResource.getMinut());
		Log4jUtil.log("Lead time Minute is = " + appointment.getLeadtimeMinute());
		manageResource.notreserve();
		appointment.setAccepttoggleStatus(manageResource.acceptforStatus());
		Log4jUtil.log("Status for AcceptFor Same day is   " + appointment.isAccepttoggleStatus());
		if (appointment.isAccepttoggleStatus() == true) {
			manageResource.clickacceptsameday();
			appointment.setAccepttoggleStatus(manageResource.acceptforStatus());
			Log4jUtil.log("Status for AcceptFor Same day is   " + appointment.isAccepttoggleStatus());
		}
		ManageLocation manageLocation = psspracticeConfig.gotoLocation();
		manageLocation.selectlocation(appointment.getLocation());
		appointment.setCurrentTimeZone(manageLocation.getTimezone());
		Log4jUtil.log("Current Timezone On AdminUi " + appointment.getCurrentTimeZone());
		patientflow.logout();
	}

	public void adminSettingLinkGeneration(WebDriver driver, AdminUser adminuser, Appointment testData, String urlToUse)
			throws Exception {
		Log4jUtil.log("****************ADMIN SETTINGS FOR Loginless FLOW**************************");
		PSS2PracticeConfiguration psspracticeConfig = loginToAdminPortal(driver, adminuser);
		Thread.sleep(2000);
		LinkTab linkTab = psspracticeConfig.linksTab();
		linkTab.searchLinkandRemove(testData.getLinkProvider());
		linkTab.addLink(testData.getLocation(), testData.getLinkProvider());
		linkTab.getURL(testData.getLinkProvider());
		testData.setUrlLinkGen(linkTab.getURL(testData.getLinkProvider()));
		PatientFlow patientflow = psspracticeConfig.gotoPatientFlowTab();
		AdminPatientMatching adminpatientmatching = patientflow.gotoPatientMatchingTab();
		adminpatientmatching.patientMatchingSelection();
		Log4jUtil.log("adminSettings Step 5: Logout from PSS Admin Portal");
		Thread.sleep(4000);
	}

	public void adminSettingLinkGenandDeleteLink(WebDriver driver, AdminUser adminuser, Appointment testData,
			String urlToUse) throws Exception {
		Log4jUtil.log("****************ADMIN SETTINGS FOR Loginless FLOW**************************");
		PSS2PracticeConfiguration psspracticeConfig = loginToAdminPortal(driver, adminuser);
		Thread.sleep(2000);
		LinkTab linkTab = psspracticeConfig.linksTab();
		linkTab.searchLinkandRemove(testData.getLinkProvider());
		linkTab.addLink(testData.getLocation(), testData.getLinkProvider());
		linkTab.getURL(testData.getLinkProvider());
		testData.setUrlLinkGen(linkTab.getURL(testData.getLinkProvider()));
		linkTab.searchLinkandRemove(testData.getLinkProvider());
		PatientFlow patientflow = psspracticeConfig.gotoPatientFlowTab();
		AdminPatientMatching adminpatientmatching = patientflow.gotoPatientMatchingTab();
		adminpatientmatching.patientMatchingSelection();
		Log4jUtil.log("adminSettings Step 5: Logout from PSS Admin Portal");
		Thread.sleep(4000);
	}

	public void ageRule(WebDriver driver, AdminUser adminuser, Appointment appointment) throws Exception {
		PSS2PracticeConfiguration psspracticeConfig = loginToAdminPortal(driver, adminuser);
		psspracticeConfig = psspracticeConfig.gotoPracticeConfigTab();
		PatientFlow patientflow = psspracticeConfig.gotoPatientFlowTab();
		adminuser.setRule(patientflow.getRule());
		Log4jUtil.log("rule= " + patientflow.getRule());
		setRulesNoSpecialitySet1(patientflow);
		AdminPatientMatching adminpatientmatching = patientflow.gotoPatientMatchingTab();
		adminpatientmatching.patientMatchingSelection();
		ManageResource manageResource = psspracticeConfig.gotoResource();
		pageRefresh(driver);
		manageResource.selectResource(appointment.getProvider());
		manageResource.selectAppointmenttype(appointment.getAppointmenttype());
		Log4jUtil.log("Status of Checkbox" + manageResource.checkBoxStatus());
		manageResource.ageRule();
		manageResource.ageRuleparameter(appointment.getAgeRuleMonthFirst(), appointment.getAgeRuleMonthSecond());
	}

	public void allowpcp(WebDriver driver, AdminUser adminuser, Appointment appointment) throws Exception {
		Log4jUtil.log("Step 2: Login to Admin portal.");
		PSS2PracticeConfiguration pss2practiceconfig = loginToAdminPortal(driver, adminuser);
		AdminAppointment adminappointment = pss2practiceconfig.gotoAdminAppointmentTab();

		Log4jUtil.log("Step 3: Clicking to Appointment tab.");
		adminappointment.areBasicPageElementsPresent();

		Log4jUtil.log(
				"Step 4: Checking the Enable care Team and Force Care Team is ON/OFF and set configuration accordingly.");
		adminappointment.toggleAllowPCPONOF();
		appointment.setPcptoggleState(adminappointment.toggleAllowPCPONOF());
		Log4jUtil.log("Status of PCP is " + appointment.isPcptoggleState());
		if (appointment.isPcptoggleState() == false) {
			Log4jUtil.log("Status of PCP  OFF");
			adminappointment.pcptoggleclick();
			Log4jUtil.log("Status of PCP  OFF and Clicked on ON");
		} else {
			Log4jUtil.log("Status of PCP is Already OFF");
		}
		Log4jUtil.log("is Next Availiable ON  " + adminappointment.toggleNextAvailableStatus());
		//appointment.setNextAvailiabletoggleStatus(adminappointment.toggleNextAvailableStatus());
		if (adminappointment.toggleNextAvailableStatus() == true) {
			Log4jUtil.log("Next Availiable Status is ON ");
			adminappointment.toggleNextavailable();
			Log4jUtil.log("Clicked For the OFF");
		} else {
			Log4jUtil.log("Next Availiable Status is Already OFF");
		}
//		PatientFlow patientflow = pss2practiceconfig.gotoPatientFlowTab();
//		adminuser.setRule(patientflow.getRule());
//		Log4jUtil.log("rule= " + patientflow.getRule());
//		setRulesNoSpecialitySet1(patientflow);
//		AdminPatientMatching adminpatientmatching = patientflow.gotoPatientMatchingTab();
//		adminpatientmatching.patientMatchingSelection();
		ManageResource manageResource = pss2practiceconfig.gotoResource();
		pageRefresh(driver);
		manageResource.selectResource(appointment.getProvider());
		Log4jUtil.log("is share patient ON for resource " + manageResource.isSharedPatientTrueForResource());
		if (manageResource.isSharedPatientTrueForResource() == false) {
			manageResource.clickShareToggle();
		} else {
			Log4jUtil.log("Share Patient is already ON");
		}

	}
}
