package com.medfusion.product.apptprecheckapi.test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.appt.precheck.payload.AptPrecheckNegativeCasesPayload;
import com.medfusion.product.appt.precheck.pojo.Appointment;
import com.medfusion.product.object.maps.appt.precheck.util.APIVerification;
import com.medfusion.product.object.maps.appt.precheck.util.AccessToken;
import com.medfusion.product.object.maps.appt.precheck.util.HeaderConfig;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestAptPrecheckNegativeCases;

import io.restassured.response.Response;

public class AptPrecheckNegativeCasesTests extends BaseTestNG {
	String getaccessToken;
	public static PropertyFileLoader propertyData;
	public static AptPrecheckNegativeCasesPayload payload;
	public static PostAPIRequestAptPrecheckNegativeCases postAPIRequest;
	public static AccessToken accessToken;
	public static HeaderConfig headerConfig;
	public static Appointment testData;
	APIVerification apiVerification = new APIVerification();

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {
		propertyData = new PropertyFileLoader();
		postAPIRequest = PostAPIRequestAptPrecheckNegativeCases.getPostAPIRequestAptPrecheck();
		accessToken = AccessToken.getAccessToken();
		getaccessToken = accessToken.getaccessTokenPost();
		payload = AptPrecheckNegativeCasesPayload.getAptPrecheckPayload();
		headerConfig = HeaderConfig.getHeaderConfig();
		testData = new Appointment();
		postAPIRequest.setupRequestSpecBuilder(propertyData.getProperty("baseurl.apt.precheck"));
		log("BASE URL-" + propertyData.getProperty("base.url.apr.event.coll"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTCopaySkipIncorrectPatientId() throws IOException {

		Response response = postAPIRequest.aptpostCopaySkipIncorrectPatientId(
				propertyData.getProperty("apt.precheck.practice.id"),

				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.copay.skip.Incorrectpatient.id"),
				propertyData.getProperty("apt.precheck.copay.skip.appointment.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTBalanceSkip() throws IOException {

		Response response = postAPIRequest.aptpostBalanceSkip(propertyData.getProperty("apt.precheck.practice.id"),

				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.copay.skip.patient.id"),
				propertyData.getProperty("apt.precheck.copay.skip.appointment.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTBalanceSkipIncorrectPatientId() throws IOException {

		Response response = postAPIRequest.aptpostBalanceSkipIncorrectPatientId(
				propertyData.getProperty("apt.precheck.practice.id"),

				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.copay.skip.Incorrectpatient.id"),
				propertyData.getProperty("apt.precheck.copay.skip.appointment.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPUTFormsWithoutPatientId() throws IOException {

		Response response = postAPIRequest.aptPutForms(propertyData.getProperty("apt.precheck.practice.id"),
				payload.getFormsPayload(), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.copay.skip.appointment.id"));

		log("Payload- " + payload.getFormsPayload());
		log("Verify response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyWithoutApptId(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPUTFormsWithIncorrectPatientId() throws IOException {

		Response response = postAPIRequest.aptPutFormsIncorrectPatientId(
				propertyData.getProperty("apt.precheck.practice.id"), payload.getFormsPayload(),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.copay.skip.Incorrectpatient.id"),
				propertyData.getProperty("apt.precheck.copay.skip.appointment.id"));

		log("Payload- " + payload.getFormsPayload());
		log("Verify response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETRequiredFormsIncorrectPatientId() throws IOException {
		Response response = postAPIRequest.getRequiredFormsIncorrectPatientId(
				propertyData.getProperty("apt.precheck.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.copay.skip.Incorrectpatient.id"),
				propertyData.getProperty("apt.precheck.pm.appmnt.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInsuranceWithBlankStatus() throws IOException {

		Response response = postAPIRequest.aptPutInsurance(propertyData.getProperty("apt.precheck.practice.id"),
				payload.getInsuranceStatusBlankPayload(), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.copay.skip.patient.id"),
				propertyData.getProperty("apt.precheck.copay.skip.appointment.id"));

		log("Payload- " + payload.getInsuranceStatusBlankPayload());
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyinsuranceWithStatusBlank(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInsuranceWithIncorectEditstatus() throws IOException {

		Response response = postAPIRequest.aptPutInsurance(propertyData.getProperty("apt.precheck.practice.id"),
				payload.getInsuranceEditstatusIncorrectPayload(), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.copay.skip.patient.id"),
				propertyData.getProperty("apt.precheck.copay.skip.appointment.id"));

		log("Payload- " + payload.getInsuranceEditstatusIncorrectPayload());
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyinsuranceWithEditstatusIncorrrect(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInsuranceWithIncorectTier() throws IOException {

		Response response = postAPIRequest.aptPutInsurance(propertyData.getProperty("apt.precheck.practice.id"),
				payload.getInsuranceIncorrectTierPayload(), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.copay.skip.patient.id"),
				propertyData.getProperty("apt.precheck.copay.skip.appointment.id"));

		log("Payload- " + payload.getInsuranceIncorrectTierPayload());
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyinsuranceWithIncorrectTier(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPUTDemographicsIncorrectPatientId() throws IOException {

		Response response = postAPIRequest.aptPutDemographicsIncorrectPatientId(
				propertyData.getProperty("apt.precheck.practice.id"), payload.getDemographicsPayload(),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.copay.skip.Incorrectpatient.id"),
				propertyData.getProperty("apt.precheck.copay.skip.appointment.id"));

		log("Payload- " + payload.getDemographicsPayload());
		log("Verify response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTCopayPayNoAmount() throws IOException {

		Response response = postAPIRequest.aptCopayPayNoAmount(
				propertyData.getProperty("apt.precheck.balance.practice.id"),
				payload.getCopayPayPayload(propertyData.getProperty("apt.precheck.copay.noamount.patient.id")),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.copay.noamount.patient.id"),
				propertyData.getProperty("apt.precheck.copay.noamount.appt.id"));

		log("Payload- "
				+ payload.getCopayPayPayload(propertyData.getProperty("apt.precheck.copay.noamount.patient.id")));
		log("Verifying the response");

		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTBalancePayNoAmount() throws IOException {

		Response response = postAPIRequest.aptBalancePayNoAmount(
				propertyData.getProperty("apt.precheck.balance.practice.id"),
				payload.getBalancePayPayload(propertyData.getProperty("apt.precheck.copay.noamount.patient.id")),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.copay.noamount.patient.id"),
				propertyData.getProperty("apt.precheck.copay.noamount.appt.id"));

		log("Payload- "
				+ payload.getBalancePayPayload(propertyData.getProperty("apt.precheck.copay.noamount.patient.id")));
		log("Verifying the response");

		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETPatientsIdentifnIncorrectPatientId() throws IOException {
		Response response = postAPIRequest.getPatientsIdentfnIncorrectData(
				propertyData.getProperty("apt.precheck.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.post.appt.incorrect.patient.id"),
				propertyData.getProperty("apt.precheck.pm.appmnt.id"));
		log("Verifying the response for Incorrect PatientId");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyPatientIdentificationIncorrectdata(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETPatientsIdentifnIncorrectPracticeId() throws IOException {
		Response response = postAPIRequest.getPatientsIdentfnIncorrectData(
				propertyData.getProperty("apt.precheck.post.appt.incorrect.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.patient.id"),
				propertyData.getProperty("apt.precheck.pm.appmnt.id"));
		log("Verifying the response for IncorrectPracticeId");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyPatientIdentificationIncorrectdata(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETPatientsIdentifnIncorrectApptId() throws IOException {
		Response response = postAPIRequest.getPatientsIdentfnIncorrectData(
				propertyData.getProperty("apt.precheck.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.patient.id"),
				propertyData.getProperty("apt.precheck.patient.identfn.incorrect.appt.id"));
		log("Verifying the response for IncorrectApptId");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyPatientIdentificationIncorrectdata(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETFormInfoIncorrectPatientId() throws IOException {
		Response response = postAPIRequest.getFormInformationIncorrectData(
				propertyData.getProperty("apt.precheck.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty(("apt.precheck.post.appt.incorrect.patient.id")),
				propertyData.getProperty("apt.precheck.pm.appmnt.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyPatientIdentificationIncorrectdata(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETFormInfoIncorrectIncorrectPracticeId() throws IOException {
		Response response = postAPIRequest.getFormInformationIncorrectData(
				propertyData.getProperty("apt.precheck.post.appt.incorrect.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty(("apt.precheck.patient.id")),
				propertyData.getProperty("apt.precheck.pm.appmnt.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyPatientIdentificationIncorrectdata(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETFormInfoIncorrectIncorrectApptId() throws IOException {
		Response response = postAPIRequest.getFormInformationIncorrectData(
				propertyData.getProperty("apt.precheck.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty(("apt.precheck.patient.id")),
				propertyData.getProperty("apt.precheck.patient.identfn.incorrect.appt.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyPatientIdentificationIncorrectdata(response);
	}

	@BeforeMethod(enabled = true, groups = { "APItest" })
	public void getMethodName(ITestResult result) throws IOException {
		log("Method Name-- " + result.getMethod().getMethodName());
	}
}
