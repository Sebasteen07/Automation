package com.medfusion.product.pss2patientmodulatorapi.test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ihg.eh.core.dto.Timestamp;
import com.medfusion.product.object.maps.pss2.page.util.APIVerification;
import com.medfusion.product.object.maps.pss2.page.util.HeaderConfig;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestDBAdapter;
import com.medfusion.product.pss2patientapi.payload.PayloadDBAdapter;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PSS2DBAdapterModulatorTests extends BaseTestNG {

	public static PayloadDBAdapter payloadDB;
	public static PSSPropertyFileLoader propertyData;
	public static Appointment testData;
	public static PostAPIRequestDBAdapter postAPIRequestDB;
	HeaderConfig headerConfig;
	public static String practiceId;

	Timestamp timestamp = new Timestamp();

	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec;

	APIVerification apv;

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {
		payloadDB = new PayloadDBAdapter();
		propertyData = new PSSPropertyFileLoader();
		postAPIRequestDB = new PostAPIRequestDBAdapter();
		headerConfig = new HeaderConfig();
		apv = new APIVerification();
		practiceId = propertyData.getProperty("practice.id.db");
		postAPIRequestDB.setupRequestSpecBuilder(propertyData.getProperty("base.url.db"), headerConfig.defaultHeader());
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testStateGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.State(practiceId, "/states");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidation(response, "statecode");
		apv.responseKeyValidation(response, "statename");
		apv.responseKeyValidationJson(response, "active");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testStateInvalidPAthGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.State(practiceId, "/statesa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOConfigGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.ssoConfig(practiceId, "/sso");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "guid");
		apv.responseKeyValidationJson(response, "newPatients");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOConfigInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.ssoConfig(practiceId, "/ssoaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOConfigCodeGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.ssoCode(practiceId, "/sso/MF");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "guid");
		apv.responseKeyValidationJson(response, "newPatients");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOConfigCodeInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.State(practiceId, "/sso/MFa");
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOGuidGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String guid = propertyData.getProperty("sso.guid");
		Response response = postAPIRequestDB.SSOGetPracticeFromGuid("/sso/" + guid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "guid");
		apv.responseKeyValidationJson(response, "code");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOGuidInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String guid = "86aa36b2-637c-450d-bcb6-a5c1645d1da6aa";
		Response response = postAPIRequestDB.SSOGetPracticeFromGuid("/sso/" + guid);
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		apv.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "No Practice found for guid=" + guid);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialityPracticeGET() throws NullPointerException, Exception {

		logStep("Verifying the response");

		Response response = postAPIRequestDB.speciality(practiceId, ("/speciality"));
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "extSpecialtyId");
		apv.responseKeyValidationJson(response, "specialty");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialityPracticeInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");

		Response response = postAPIRequestDB.speciality(practiceId, ("/specialityaa"));
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialityByIdGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String id = propertyData.getProperty("speciality.id");
		Response response = postAPIRequestDB.State(practiceId, ("/speciality/" + id));
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialityByInvalidIdGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String id = "20360411";
		Response response = postAPIRequestDB.specilityById(practiceId, ("/speciality/" + id));
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "No Specialty found for id = " + id);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialityByIdandLanguageGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.specilityByIdLanguage(practiceId, ("/specialties"));
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialityByIdandLanguageInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.specilityByIdLanguage(practiceId, ("/specialtiesa"));
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRuleGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.ruleAll(practiceId, ("/configurations/rules"));
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "name");
		apv.responseKeyValidationJson(response, "rule");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRuleInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.ruleAll(practiceId, ("/configurations/rulesaa"));
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRuleMasterGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.ruleMaster("/configurations/rulesmaster");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidation(response, "key");
		apv.responseKeyValidation(response, "value");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRuleMasterInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.ruleMaster("/configurations/rulesmasteraa");
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testresellerPracticeGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.resellerPractice(practiceId, "/reseller");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testresellerPracticeInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.resellerPractice(practiceId, "/reselleraa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testresellerPracticeByLanguageGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.resellerByLanguage(practiceId, "/resellerbylanguage");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testresellerPracticeByLanguageInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.resellerByLanguage(practiceId, "/resellerbylanguageaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testresellerPracticeByLanguageForUIGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.resellerByLanguageByUI(practiceId, "/resellerbylanguageforui");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testresellerPracticeByLanguageForUIInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.resellerByLanguageByUI(practiceId, "/resellerbylanguageforuiaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testpracticeTimezoneGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.practiceTimeZone("/timezone");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "code");
		apv.responseKeyValidation(response, "abbr");
		apv.responseKeyValidation(response, "description");
		apv.responseKeyValidation(response, "utcOffset");
		apv.responseKeyValidation(response, "utcOffsetMM");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testpracticeTimezoneInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.practiceTimeZone("/timezoneaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testpracticeLanguageGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.practiceTimeZone("/language");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "code");
		apv.responseKeyValidation(response, "name");
		apv.responseKeyValidation(response, "flag");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testpracticeLanguageInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.practiceTimeZone("/languageaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeInfoGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.practiceInfo(practiceId, "/practiceinfo");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "extPracticeId");
		apv.responseKeyValidationJson(response, "name");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeInfoInvalidGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.practiceInfo(practiceId, "/practiceinfoaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeDetailsGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.practiceDetails(practiceId, "/practice");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "extPracticeId");
		apv.responseKeyValidationJson(response, "name");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeDetailsInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.practiceDetails(practiceId, "/practiceaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientMatchGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.patientMatch(practiceId, "/patientmatch/LG_LOG");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "entity");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientMatchInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.patientMatch(practiceId, "/patientmatchaa/LG_LOG");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientMatchMasterGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.patientMatchMaster(practiceId, "/patientmatchmaster/LG_LOG");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "entity");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientMatchMasterInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.patientMatchMaster(practiceId, "/patientmatchmasteraa/LG_LOG");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientInfoGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.patientInfo(practiceId, "/patientinfo/LG_LOG");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "entity");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientInfoInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.patientInfo(practiceId, "/patientmatchaa/LG_LOG");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientInfoMasterGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.patientInfo(practiceId, "/patientinfomaster/LG_LOG");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "entity");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientInfoMasterInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.patientInfo(practiceId, "/patientinfomasteraa/LG_LOG");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPartnerCustomandInfoGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerCustomandInfo(practiceId, "/partnercustomandinfo/LG_LOG");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "partnerCustomId");
		apv.responseKeyValidationJson(response, "paernerInfoId");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPartnerCustomandInfoInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerCustomandInfo(practiceId, "/partnercustomandinfoaa/LG_LOG");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPartnerCustMetaDataGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerCustMetaData(practiceId, "/partnercustommetadata", "ZIP");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPartnerCustMetaDataInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerCustMetaData(practiceId, "/partnercustommetadataaa", "ZIP");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPartnerMetaData() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerMetaData(practiceId, "/partnermetadata");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidation(response, "message");
		apv.responseKeyValidation(response, "code");
		apv.responseKeyValidation(response, "entity");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPartnerMetaDataInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerMetaData(practiceId, "/partnermetadataaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPartnerMetaDataByCode() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String code = propertyData.getProperty("partnermetadata.code");
		Response response = postAPIRequestDB.partnerMetaDataByCode(practiceId, "/partnermetadatabycode/" + code);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "message");
		apv.responseKeyValidationJson(response, "code");
		apv.responseKeyValidationJson(response, "entity");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPartnerMetaDataByCodeInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String code = propertyData.getProperty("partnermetadata.code");
		Response response = postAPIRequestDB.partnerMetaDataByCode(practiceId, "/partnermetadatabycodeaa/" + code);
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPartnerConfig() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerConfig(practiceId, "/partner");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "code");
		apv.responseKeyValidationJson(response, "name");
		apv.responseKeyValidationJson(response, "integrationId");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPartnerConfigInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerConfig(practiceId, "/partneraa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPartnerDetailsConfig() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerDetails(practiceId, "/partnerdetails");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "forceCareTeamDuration");
		apv.responseKeyValidationJson(response, "showCancelMessage");
		apv.responseKeyValidationJson(response, "otherCancelReason");
		apv.responseKeyValidationJson(response, "establishPatientLastVisit");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPartnerConfigDetailsInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerDetails(practiceId, "/partnerdetailsaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testpartner() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerWithoutPractice("/partner");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "code");
		apv.responseKeyValidationJson(response, "name");
		apv.responseKeyValidationJson(response, "integrationId");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testpartnerInvalidPath() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerWithoutPractice("/partneraa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testpartnerBaseUrl() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerWithoutPractice("/partnerbaseurls");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testpartnerBaseUrlInvalidPath() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerWithoutPractice("/partnerbaseurlsaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testloginlessConfig() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.loginless(practiceId, "/loginless");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "guid");
	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testloginlessInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.loginless(practiceId, "/loginlessaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testloginlessGuidConfig() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String guid=propertyData.getProperty("loginless.guid");
		Response response = postAPIRequestDB.loginlessGuid("/loginless/"+guid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "guid");
	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testloginlessGuidInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String guid=propertyData.getProperty("loginless.guid");
		Response response = postAPIRequestDB.loginlessGuid("/loginlessaa/"+guid);
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlockoutConfig() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.lockout(practiceId,"/lockout");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "type");
	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlockoutInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.lockout(practiceId,"/lockoutaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocation() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.location(practiceId,"/location");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "displayName");
		apv.responseKeyValidationJson(response, "extLocationId");
		apv.responseKeyValidationJson(response, "name");	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.location(practiceId,"/location");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationById() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String locationId=propertyData.getProperty("location.id.db");
		Response response = postAPIRequestDB.locationById(practiceId,"/location/"+locationId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "displayName");
		apv.responseKeyValidationJson(response, "extLocationId");
		apv.responseKeyValidationJson(response, "name");	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationByIdInvalidLocationIdGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String InvalidlocationId="2063511";
		Response response = postAPIRequestDB.locationById(practiceId,"/location/"+InvalidlocationId);
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "No Location found for id="+InvalidlocationId+" or the location could have been deleted.");
	}
	

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationAppType() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String AppId=propertyData.getProperty("location.appid.db");
		Response response = postAPIRequestDB.locationByAppId(practiceId,"/location/appointmenttype/"+AppId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "displayName");
		apv.responseKeyValidationJson(response, "extLocationId");
		apv.responseKeyValidationJson(response, "name");	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationAppInvalidIdGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String AppId="2063511";
		Response response = postAPIRequestDB.locationByAppId(practiceId,"/location/appointmenttype/"+AppId);
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "Invalid appointmenttype id="+AppId);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationAppointmentTypeGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String AppId=propertyData.getProperty("location.appid.db");
		Response response = postAPIRequestDB.locationByAppointmentId(practiceId,"/location/apptype/"+AppId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "displayName");
		apv.responseKeyValidationJson(response, "extLocationId");
		apv.responseKeyValidationJson(response, "name");	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationAppointmentInvalidIdGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String AppId="2063511";
		Response response = postAPIRequestDB.locationByAppointmentId(practiceId,"/location/apptype/"+AppId);
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "Invalid appointmenttype id="+AppId);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationBookGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String bookId=propertyData.getProperty("location.bookid.db");
		Response response = postAPIRequestDB.locationBook(practiceId,"/location/book/"+bookId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "displayName");
		apv.responseKeyValidationJson(response, "extLocationId");
		apv.responseKeyValidationJson(response, "name");	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationBookInvalidIdGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String invalidbooId="2063511";
		Response response = postAPIRequestDB.locationBook(practiceId,"/location/book/"+invalidbooId);
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "Invalid bookid="+invalidbooId);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationBookAppTypeGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String bookId=propertyData.getProperty("location.bookid.db");
		String AppId=propertyData.getProperty("location.appid.db");
		Response response = postAPIRequestDB.locationBookAppType(practiceId,"/location/book/"+bookId+"/apptype/"+AppId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "displayName");
		apv.responseKeyValidationJson(response, "extLocationId");
		apv.responseKeyValidationJson(response, "name");	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationBookAppTypeInvalidIdGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String invalidbooId="2063511";
		String AppId=propertyData.getProperty("location.appid.db");
		Response response = postAPIRequestDB.locationBookAppType(practiceId,"/location/book/"+invalidbooId+"/apptype/"+AppId);
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "Invalid bookid="+invalidbooId);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationSpecialityGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String specialtyId=propertyData.getProperty("location.specialityid.db");
		Response response = postAPIRequestDB.locationSpeciality(practiceId,"/location/specialty/"+specialtyId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "displayName");
		apv.responseKeyValidationJson(response, "extLocationId");
		apv.responseKeyValidationJson(response, "name");	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationSpecialityInvalidIdGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String invalidspecialtyId="2063511";
		Response response = postAPIRequestDB.locationSpeciality(practiceId,"/location/specialty/"+invalidspecialtyId);
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "Invalid speciality id="+invalidspecialtyId);
	}
	

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationSpecialityAppTypeGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String specialtyId=propertyData.getProperty("location.specialityid.db");
		String AppId=propertyData.getProperty("location.appid.db");
		Response response = postAPIRequestDB.locationSpecialityAppType(practiceId,"/location/specialty/"+specialtyId+"/apptype/"+AppId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "displayName");
		apv.responseKeyValidationJson(response, "extLocationId");
		apv.responseKeyValidationJson(response, "name");	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationSpecialityAppTypeInvalidIdGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String invalidSpecialityId="2063511";
		String AppId=propertyData.getProperty("location.appid.db");
		Response response = postAPIRequestDB.locationSpecialityAppType(practiceId,"/location/specialty/"+invalidSpecialityId+"/apptype/"+AppId);
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "Invalid speciality id="+invalidSpecialityId);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationSpecialityBookGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String bookId=propertyData.getProperty("location.bookid.db");
		String specialtyId=propertyData.getProperty("location.specialityid.db");
		Response response = postAPIRequestDB.locationSpecialityBook(practiceId,"/location/specialty/"+specialtyId+"/book/"+bookId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "displayName");
		apv.responseKeyValidationJson(response, "extLocationId");
		apv.responseKeyValidationJson(response, "name");	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationSpecialityBookInvalidIdGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String invalidSpecialityId="2063511";
		String AppId=propertyData.getProperty("location.appid.db");
		Response response = postAPIRequestDB.locationSpecialityBook(practiceId,"/location/specialty/"+invalidSpecialityId+"/book/"+AppId);
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "Invalid speciality id="+invalidSpecialityId);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationLinkByIdGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String locationId=propertyData.getProperty("location.id.db");
		Response response = postAPIRequestDB.locationLinkbyid(practiceId,"/locationlinkbyid/"+locationId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "displayName");
		apv.responseKeyValidationJson(response, "extLocationId");
		apv.responseKeyValidationJson(response, "name");	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationLinkInvalidIdGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String invalidLocationId="2063511";
		Response response = postAPIRequestDB.locationLinkbyid(practiceId,"/locationlinkbyid/"+invalidLocationId);
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "No Location found for id="+invalidLocationId);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationsGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.locations(practiceId,"/locations");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "displayName");
		apv.responseKeyValidationJson(response, "extLocationId");
		apv.responseKeyValidationJson(response, "name");	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationsInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.locations(practiceId,"/locationsaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	
	}
	

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInsuranceCarrierGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.insuranceCarrrier(practiceId,"/insurancecarrier");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "extInsuranceCarrierId");
		apv.responseKeyValidationJson(response, "name");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testinsuranceCarrierInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.insuranceCarrrier(practiceId,"/insurancecarrieraa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInsuranceCarrierByIdGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String insuranceId=propertyData.getProperty("insurance.id.db");
        Response response = postAPIRequestDB.insuranceCarrrierById(practiceId,"/insurancecarrier/"+insuranceId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "extInsuranceCarrierId");
		apv.responseKeyValidationJson(response, "name");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testinsuranceCarrierByIdInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String insuranceId=propertyData.getProperty("insurance.id.db");
		Response response = postAPIRequestDB.insuranceCarrrierById(practiceId,"/insurancecarrieraa"+insuranceId);
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	
	}
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testflowIdentityGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String flowType=propertyData.getProperty("flowtype.db");
        Response response = postAPIRequestDB.flowIdentity(practiceId,"/flowidentity", flowType);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "code");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testflowIdentityInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String flowType=propertyData.getProperty("flowtype.db");
		Response response = postAPIRequestDB.flowIdentity(practiceId,"/flowidentityaa", flowType);
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testConfigPartnerCodeGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String partnerCode=propertyData.getProperty("partnercode.db");
        Response response = postAPIRequestDB.configPartnerCode("/configurations/", partnerCode);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "group");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testConfigPartnerCodeInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String partnerCode=propertyData.getProperty("partnercode.db");
		Response response = postAPIRequestDB.configPartnerCode("/configurationsaa/", partnerCode);
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testrescheduleAppIdGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String AppId=propertyData.getProperty("reschedule.aaptypeid.db");
        Response response = postAPIRequestDB.rescheduleApp(practiceId,"/reschedule/"+AppId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRescheduleAppInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String AppId=propertyData.getProperty("reschedule.aaptypeid.db");
		Response response = postAPIRequestDB.rescheduleApp(practiceId,"/rescheduleaa/"+AppId);
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testrescheduleAppGuidGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String guid=propertyData.getProperty("reschedule.aaptypeguid.db");
        Response response = postAPIRequestDB.rescheduleAppGuid("/reschedule/"+guid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "appTypeId");
		apv.responseKeyValidationJson(response, "bookId");
		apv.responseKeyValidationJson(response, "locationId");
		apv.responseKeyValidationJson(response, "practiceId");

		
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRescheduleAppGuidInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String guid=propertyData.getProperty("reschedule.aaptypeguid.db");
		Response response = postAPIRequestDB.rescheduleAppGuid("/rescheduleaa/"+guid);
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPrerequisiteAppGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String AppId=propertyData.getProperty("location.appid.db");
        Response response = postAPIRequestDB.rescheduleApp(practiceId,"/prerequisiteappointmenttypes/"+AppId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "name");
		apv.responseKeyValidationJson(response, "extPreAppTypeId");
		apv.responseKeyValidationJson(response, "categoryId");
		apv.responseKeyValidationJson(response, "categoryName");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPrerequisiteAppInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String AppId=propertyData.getProperty("location.appid.db");
		Response response = postAPIRequestDB.rescheduleApp(practiceId,"/prerequisiteappointmenttypesaa/"+AppId);
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPrerequisiteAppInvaliIdGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String invalidAppID="12345";
		Response response = postAPIRequestDB.rescheduleApp(practiceId,"/prerequisiteappointmenttypes/"+invalidAppID);
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "No appointment found for id = " + invalidAppID);
	
	}
	

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeMetaDataGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String flowType=propertyData.getProperty("flowtype.db");
        Response response = postAPIRequestDB.practiceMetaData(practiceId,"/customdata",flowType);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "practicePatientInfo.id");
		apv.responseKeyValidationJson(response, "practicePatientInfo.entity");
		apv.responseKeyValidationJson(response, "practicePatientInfo.isSearchRequired");
		apv.responseKeyValidationJson(response, "practicePatientInfo.isCreateRequired");
		apv.responseKeyValidationJson(response, "practicePatientInfo.field");

	}

//@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
//public void testPracticeMetadaInvalidPathGET() throws NullPointerException, Exception {
//
//	logStep("Verifying the response");
//	Response response = postAPIRequestDB.rescheduleApp(practiceId,"/prerequisiteappointmenttypes/");
//	apv.responseCodeValidation(response, 404);
//	apv.responseTimeValidation(response);
//
//}
}
