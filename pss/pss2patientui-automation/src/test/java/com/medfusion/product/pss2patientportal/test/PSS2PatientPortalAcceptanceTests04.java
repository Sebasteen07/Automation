package com.medfusion.product.pss2patientportal.test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.json.JSONArray;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ihg.eh.core.dto.Timestamp;
import com.medfusion.product.object.maps.pss2.page.AppEntryPoint.StartAppointmentInOrder;
import com.medfusion.product.object.maps.pss2.page.Appointment.DateTime.AppointmentDateTime;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Location.Location;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.DismissPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.LoginlessPatientInformation;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.NewPatientInsuranceInfo;
import com.medfusion.product.object.maps.pss2.page.Appointment.Provider.Provider;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.AppointmentPage;
import com.medfusion.product.object.maps.pss2.page.util.APIVerification;
import com.medfusion.product.object.maps.pss2.page.util.HeaderConfig;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestAdapterModulator;
import com.medfusion.product.pss2patientapi.payload.PayloadAM01;
import com.medfusion.product.pss2patientapi.payload.PayloadAM02;
import com.medfusion.product.pss2patientapi.payload.PayloadAdapterModulator;
import com.medfusion.product.pss2patientapi.validation.ValidationAdapterModulator;
import com.medfusion.product.pss2patientui.pojo.AdminUser;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSAdminUtils;
import com.medfusion.product.pss2patientui.utils.PSSConstants;
import com.medfusion.product.pss2patientui.utils.PSSNewPatient;
import com.medfusion.product.pss2patientui.utils.PSSPatientUtils;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PSS2PatientPortalAcceptanceTests04 extends BaseTestNGWebDriver{
	
	public static PayloadAdapterModulator payloadAM;
	public static PSSPropertyFileLoader propertyData;
	public static Appointment testData;
	public static PostAPIRequestAdapterModulator postAPIRequestAM;
	HeaderConfig headerConfig;
	public static String openToken;
	public static String practiceId;
	public static PayloadAM02 payloadAM02;
	public static PayloadAM01 payloadAM01;
	public static APIVerification apv;

	ValidationAdapterModulator validateAdapter = new ValidationAdapterModulator();
	Timestamp timestamp = new Timestamp();


	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec;	
	public void setUp(String practiceId1,String userID) throws IOException {
		payloadAM = new PayloadAdapterModulator();
		payloadAM01=new PayloadAM01();
		apv=new APIVerification();
		propertyData = new PSSPropertyFileLoader();
		postAPIRequestAM = new PostAPIRequestAdapterModulator();
		headerConfig = new HeaderConfig();

		practiceId = practiceId1;

		log("BASE URL AM -" + propertyData.getProperty("base.url.am"));
		openToken = postAPIRequestAM.openToken(propertyData.getProperty("base.url.am"), practiceId,
				payloadAM.openTokenPayload(practiceId,userID));

		postAPIRequestAM.setupRequestSpecBuilder(propertyData.getProperty("base.url.am"),
				headerConfig.HeaderwithToken(openToken));
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLeadTimeShowProviderOnNG() throws Exception {
 
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		PSSPatientUtils psspatientUtils = new PSSPatientUtils();		
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		String leadTime=propertyData.getProperty("leadtime.ng");
		int leadTimeint=Integer.parseInt(leadTime);
		logStep("Setting Rule By Using Adapter Modulator Api Call");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		apv.responseCodeValidation(response,200);
        JSONArray arr = new JSONArray(response.body().asString());
        int l=arr.length();
        log("Length is- "+l);
        for (int i=0; i<l; i++) {
        int ruleId=arr.getJSONObject(i).getInt("id");
        log("Object No."+i+"- "+ruleId);
        Response responseForDeleteRule = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
        apv.responseCodeValidation(responseForDeleteRule, 200);
        }
        Response responseRulePost = postAPIRequestAM.resourceConfigRulePost(practiceId,
        		 payloadAM.rulePayload("LTB", "L,T,B"));
        apv.responseCodeValidation(responseRulePost, 200);

		Response responseRulePostTL = postAPIRequestAM.resourceConfigRulePost(practiceId,
				 payloadAM.rulePayload("TBL", "T,B,L"));
		apv.responseCodeValidation(responseRulePostTL, 200);
		logStep("Show Provider On Using AM ");
		Response responseShowOff = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(responseShowOff, 200);
		
		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalLLNG());
		apv.responseCodeValidation(response, 200);
		
		response = postAPIRequestAM.locationById(practiceId, propertyData.getProperty("locationid.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone=apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- "+locationTimeZone);
		testData.setCurrentTimeZone(locationTimeZone);	
		testData.setLeadtimeDay(leadTimeint);


		String currentdate=psspatientUtils.currentDateWithTimeZone(locationTimeZone);		
		log("currentdate - "+currentdate);
		
		String futuredate=psspatientUtils.addDaysToDate(currentdate,leadTime, "MM/dd/yyyy");
		
		log("Future Date -"+futuredate);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.leadTimenotReserve1(driver, adminuser, testData, propertyData.getProperty("leadtime.ng"));
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
    	StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());

		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());

		Thread.sleep(20000);
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		logStep("Date selected is for App" + date);
		assertEquals(date, psspatientUtils.currentDateandLeadDay(testData));
		log("ReSetting Admin UI Setting");
		adminUtils.leadTimenotReserve1(driver, adminuser, testData, "0");
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLeadTimeShowProviderOnGW() throws Exception {
 
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGW(adminuser);
		propertyData.setAppointmentResponseGW(testData);
		PSSPatientUtils psspatientUtils = new PSSPatientUtils();		
		setUp(propertyData.getProperty("mf.practice.id.gw"), propertyData.getProperty("mf.authuserid.am.gw"));
		Response response;
		String leadTime=propertyData.getProperty("leadtime.ng");
		int leadTimeint=Integer.parseInt(leadTime);
		logStep("Setting Rule By Using Adapter Modulator Api Call");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		apv.responseCodeValidation(response,200);
        JSONArray arr = new JSONArray(response.body().asString());
        int l=arr.length();
        log("Length is- "+l);
        for (int i=0; i<l; i++) {
        int ruleId=arr.getJSONObject(i).getInt("id");
        log("Object No."+i+"- "+ruleId);
        Response responseForDeleteRule = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
        apv.responseCodeValidation(responseForDeleteRule, 200);
        }
        Response responseRulePost = postAPIRequestAM.resourceConfigRulePost(practiceId,
        		 payloadAM.rulePayload("LTB", "L,T,B"));
        apv.responseCodeValidation(responseRulePost, 200);

		Response responseRulePostTL = postAPIRequestAM.resourceConfigRulePost(practiceId,
				 payloadAM.rulePayload("TBL", "T,B,L"));
		apv.responseCodeValidation(responseRulePostTL, 200);
		logStep("Show Provider On Using AM ");
		Response responseShowOff = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(responseShowOff, 200);
		
		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalGW());
		apv.responseCodeValidation(response, 200);
		
		response = postAPIRequestAM.locationById(practiceId, propertyData.getProperty("locationid.gw"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone=apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- "+locationTimeZone);
		testData.setCurrentTimeZone(locationTimeZone);	
		testData.setLeadtimeDay(leadTimeint);


		String currentdate=psspatientUtils.currentDateWithTimeZone(locationTimeZone);		
		log("currentdate - "+currentdate);
		
		String futuredate=psspatientUtils.addDaysToDate(currentdate,leadTime, "MM/dd/yyyy");
		
		log("Future Date -"+futuredate);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.leadTimenotReserve1(driver, adminuser, testData, propertyData.getProperty("leadtime.ng"));
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
    	StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());

		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());

		Thread.sleep(20000);
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		logStep("Date selected is for App" + date);
		assertEquals(date, psspatientUtils.currentDateandLeadDay(testData));
		log("ReSetting Admin UI Setting");
		adminUtils.leadTimenotReserve1(driver, adminuser, testData, "0");
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLeadTimeShowProviderOnGE() throws Exception {
 
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGE(adminuser);
		propertyData.setAppointmentResponseGE(testData);
		PSSPatientUtils psspatientUtils = new PSSPatientUtils();		
		setUp(propertyData.getProperty("mf.practice.id.ge"), propertyData.getProperty("mf.authuserid.am.ge"));
		Response response;
		String leadTime=propertyData.getProperty("leadtime.ng");
		int leadTimeint=Integer.parseInt(leadTime);
		logStep("Setting Rule By Using Adapter Modulator Api Call");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		apv.responseCodeValidation(response,200);
        JSONArray arr = new JSONArray(response.body().asString());
        int l=arr.length();
        log("Length is- "+l);
        for (int i=0; i<l; i++) {
        int ruleId=arr.getJSONObject(i).getInt("id");
        log("Object No."+i+"- "+ruleId);
        Response responseForDeleteRule = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
        apv.responseCodeValidation(responseForDeleteRule, 200);
        }
        Response responseRulePost = postAPIRequestAM.resourceConfigRulePost(practiceId,
        		 payloadAM.rulePayload("LTB", "L,T,B"));
        apv.responseCodeValidation(responseRulePost, 200);

		Response responseRulePostTL = postAPIRequestAM.resourceConfigRulePost(practiceId,
				 payloadAM.rulePayload("TBL", "T,B,L"));
		apv.responseCodeValidation(responseRulePostTL, 200);
		logStep("Show Provider On Using AM ");
		Response responseShowOff = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(responseShowOff, 200);
		
		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalGE());
		apv.responseCodeValidation(response, 200);
		
		response = postAPIRequestAM.locationById(practiceId, propertyData.getProperty("locationid.ge"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone=apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- "+locationTimeZone);
		testData.setCurrentTimeZone(locationTimeZone);	
		testData.setLeadtimeDay(leadTimeint);


		String currentdate=psspatientUtils.currentDateWithTimeZone(locationTimeZone);		
		log("currentdate - "+currentdate);
		
		String futuredate=psspatientUtils.addDaysToDate(currentdate,leadTime, "MM/dd/yyyy");
		
		log("Future Date -"+futuredate);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.leadTimenotReserve1(driver, adminuser, testData, propertyData.getProperty("leadtime.ng"));
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
    	StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());

		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());

		Thread.sleep(20000);
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		logStep("Date selected is for App" + date);
		assertEquals(date, psspatientUtils.currentDateandLeadDay(testData));
		log("ReSetting Admin UI Setting");
		adminUtils.leadTimenotReserve1(driver, adminuser, testData, "0");
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
	}
	

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLeadTimeShowProviderOnAT() throws Exception {
 
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminAT(adminuser);
		propertyData.setAppointmentResponseAT(testData);
		PSSPatientUtils psspatientUtils = new PSSPatientUtils();		
		setUp(propertyData.getProperty("mf.practice.id.at"), propertyData.getProperty("mf.authuserid.am.at"));
		Response response;
		String leadTime=propertyData.getProperty("leadtime.ng");
		int leadTimeint=Integer.parseInt(leadTime);
		logStep("Setting Rule By Using Adapter Modulator Api Call");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		apv.responseCodeValidation(response,200);
        JSONArray arr = new JSONArray(response.body().asString());
        int l=arr.length();
        log("Length is- "+l);
        for (int i=0; i<l; i++) {
        int ruleId=arr.getJSONObject(i).getInt("id");
        log("Object No."+i+"- "+ruleId);
        Response responseForDeleteRule = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
        apv.responseCodeValidation(responseForDeleteRule, 200);
        }
        Response responseRulePost = postAPIRequestAM.resourceConfigRulePost(practiceId,
        		 payloadAM.rulePayload("LTB", "L,T,B"));
        apv.responseCodeValidation(responseRulePost, 200);

		Response responseRulePostTL = postAPIRequestAM.resourceConfigRulePost(practiceId,
				 payloadAM.rulePayload("TBL", "T,B,L"));
		apv.responseCodeValidation(responseRulePostTL, 200);
		logStep("Show Provider On Using AM ");
		Response responseShowOff = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(responseShowOff, 200);
		
		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientMatchAt());
		apv.responseCodeValidation(response, 200);
		
		response = postAPIRequestAM.locationById(practiceId, propertyData.getProperty("locationid.at"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone=apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- "+locationTimeZone);
		testData.setCurrentTimeZone(locationTimeZone);	
		testData.setLeadtimeDay(leadTimeint);


		String currentdate=psspatientUtils.currentDateWithTimeZone(locationTimeZone);		
		log("currentdate - "+currentdate);
		
		String futuredate=psspatientUtils.addDaysToDate(currentdate,leadTime, "MM/dd/yyyy");
		
		log("Future Date -"+futuredate);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.leadTimenotReserve1(driver, adminuser, testData, propertyData.getProperty("leadtime.ng"));
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
    	StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());

		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());

		Thread.sleep(20000);
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		logStep("Date selected is for App" + date);
		assertEquals(date, psspatientUtils.currentDateandLeadDay(testData));
		log("ReSetting Admin UI Setting");
		adminUtils.leadTimenotReserve1(driver, adminuser, testData, "0");
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELoginlessForNewPatientGW() throws Exception {
		log("Test To View if configuration change from Admin is reflected in PSS patient portal");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSNewPatient pssNewPatient = new PSSNewPatient();
		propertyData.setAdminGW(adminuser);
		propertyData.setAppointmentResponseGW(testData);
		log("-----Loaded the test data for New Patient----------");
		pssNewPatient.createPatientDetails(testData);
		log(testData.getUrlLoginLess());
		log(testData.getAppointmenttype());
		testData.setFutureApt(true);
		setUp(propertyData.getProperty("mf.practice.id.gw"), propertyData.getProperty("mf.authuserid.am.gw"));
		Response response;
		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalGW());
		apv.responseCodeValidation(response, 200);
		log("Step 2: Fetch rule and settings from PSS 2.0 Admin portal");
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		adminUtils.adminSettingsLoginless(driver, adminuser, testData, PSSConstants.LOGINLESS);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Step 3: Move to PSS patient Portal 2.0 to book an Appointment");
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		Boolean insuranceSelected = adminuser.getIsInsuranceDisplayed();
		log("insuranceSelected--> " + insuranceSelected);
		HomePage homepage;
		insuranceSelected = false;
		log("insuranceSelected--> " + insuranceSelected);
		if (insuranceSelected) {
			log("insuranceSelected--> ON");
			NewPatientInsuranceInfo newpatientinsuranceinfo = loginlessPatientInformation.fillPatientForm(
					testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
					testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
			homepage = newpatientinsuranceinfo.fillNewPatientInsuranceInfo(PSSConstants.INSURANCE_CARRIER,
					PSSConstants.INSURANCE_MEMBERID, PSSConstants.INSURANCE_GROUPID,
					PSSConstants.INSURANCE_PRIMARYPHONE);
		} else {
			log("insuranceSelected--> OFF");
			homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(),
					testData.getDob(), testData.getEmail(), testData.getGender(), testData.getZipCode(),
					testData.getPrimaryNumber());
		}
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELoginlessForNewPatientGE() throws Exception {
		log("Test To View if configuration change from Admin is reflected in PSS patient portal");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSNewPatient pssNewPatient = new PSSNewPatient();
		propertyData.setAdminGE(adminuser);
		propertyData.setAppointmentResponseGE(testData);
		log("-----Loaded the test data for New Patient----------");
		pssNewPatient.createPatientDetails(testData);
		log(testData.getUrlLoginLess());
		log(testData.getAppointmenttype());
		testData.setFutureApt(true);
		setUp(propertyData.getProperty("mf.practice.id.ge"), propertyData.getProperty("mf.authuserid.am.ge"));
		Response response;
		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalGE());
		apv.responseCodeValidation(response, 200);
		log("Step 2: Fetch rule and settings from PSS 2.0 Admin portal");
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		adminUtils.adminSettingsLoginless(driver, adminuser, testData, PSSConstants.LOGINLESS);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Step 3: Move to PSS patient Portal 2.0 to book an Appointment");
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		Boolean insuranceSelected = adminuser.getIsInsuranceDisplayed();
		log("insuranceSelected--> " + insuranceSelected);
		HomePage homepage;
		insuranceSelected = false;
		log("insuranceSelected--> " + insuranceSelected);
		if (insuranceSelected) {
			log("insuranceSelected--> ON");
			NewPatientInsuranceInfo newpatientinsuranceinfo = loginlessPatientInformation.fillPatientForm(
					testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
					testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
			homepage = newpatientinsuranceinfo.fillNewPatientInsuranceInfo(PSSConstants.INSURANCE_CARRIER,
					PSSConstants.INSURANCE_MEMBERID, PSSConstants.INSURANCE_GROUPID,
					PSSConstants.INSURANCE_PRIMARYPHONE);
		} else {
			log("insuranceSelected--> OFF");
			homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(),
					testData.getDob(), testData.getEmail(), testData.getGender(), testData.getZipCode(),
					testData.getPrimaryNumber());
		}
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELoginlessForNewPatientNG() throws Exception {
		log("Test To View if configuration change from Admin is reflected in PSS patient portal");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSNewPatient pssNewPatient = new PSSNewPatient();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		log("-----Loaded the test data for New Patient----------");
		pssNewPatient.createPatientDetails(testData);
		log(testData.getUrlLoginLess());
		log(testData.getAppointmenttype());
		testData.setFutureApt(true);
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalLLNG());
		apv.responseCodeValidation(response, 200);
		log("Step 2: Fetch rule and settings from PSS 2.0 Admin portal");
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		adminUtils.adminSettingsLoginless(driver, adminuser, testData, PSSConstants.LOGINLESS);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Step 3: Move to PSS patient Portal 2.0 to book an Appointment");
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		Boolean insuranceSelected = adminuser.getIsInsuranceDisplayed();
		log("insuranceSelected--> " + insuranceSelected);
		HomePage homepage;
		insuranceSelected = false;
		log("insuranceSelected--> " + insuranceSelected);
		if (insuranceSelected) {
			log("insuranceSelected--> ON");
			NewPatientInsuranceInfo newpatientinsuranceinfo = loginlessPatientInformation.fillPatientForm(
					testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
					testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
			homepage = newpatientinsuranceinfo.fillNewPatientInsuranceInfo(PSSConstants.INSURANCE_CARRIER,
					PSSConstants.INSURANCE_MEMBERID, PSSConstants.INSURANCE_GROUPID,
					PSSConstants.INSURANCE_PRIMARYPHONE);
		} else {
			log("insuranceSelected--> OFF");
			homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(),
					testData.getDob(), testData.getEmail(), testData.getGender(), testData.getZipCode(),
					testData.getPrimaryNumber());
		}
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELoginlessForNewPatientAT() throws Exception {
		log("Test To View if configuration change from Admin is reflected in PSS patient portal");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSNewPatient pssNewPatient = new PSSNewPatient();
		propertyData.setAdminAT(adminuser);
		propertyData.setAppointmentResponseAT(testData);
		log("-----Loaded the test data for New Patient----------");
		pssNewPatient.createPatientDetails(testData);
		log(testData.getUrlLoginLess());
		log(testData.getAppointmenttype());
		testData.setFutureApt(true);
		setUp(propertyData.getProperty("mf.practice.id.at"), propertyData.getProperty("mf.authuserid.am.at"));
		Response response;
		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientMatchAt());
		apv.responseCodeValidation(response, 200);
		log("Step 2: Fetch rule and settings from PSS 2.0 Admin portal");
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		adminUtils.adminSettingsLoginless(driver, adminuser, testData, PSSConstants.LOGINLESS);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Step 3: Move to PSS patient Portal 2.0 to book an Appointment");
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		Boolean insuranceSelected = adminuser.getIsInsuranceDisplayed();
		log("insuranceSelected--> " + insuranceSelected);
		HomePage homepage;
		insuranceSelected = false;
		log("insuranceSelected--> " + insuranceSelected);
		if (insuranceSelected) {
			log("insuranceSelected--> ON");
			NewPatientInsuranceInfo newpatientinsuranceinfo = loginlessPatientInformation.fillPatientForm(
					testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
					testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
			homepage = newpatientinsuranceinfo.fillNewPatientInsuranceInfo(PSSConstants.INSURANCE_CARRIER,
					PSSConstants.INSURANCE_MEMBERID, PSSConstants.INSURANCE_GROUPID,
					PSSConstants.INSURANCE_PRIMARYPHONE);
		} else {
			log("insuranceSelected--> OFF");
			homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(),
					testData.getDob(), testData.getEmail(), testData.getGender(), testData.getZipCode(),
					testData.getPrimaryNumber());
		}
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
	}

}
