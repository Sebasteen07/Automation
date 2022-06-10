package com.medfusion.product.apptprecheckapi.test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.appt.precheck.payload.IMHProxyServicePayload;
import com.medfusion.product.appt.precheck.pojo.Appointment;
import com.medfusion.product.object.maps.appt.precheck.util.APIVerification;
import com.medfusion.product.object.maps.appt.precheck.util.AccessToken;
import com.medfusion.product.object.maps.appt.precheck.util.CommonMethods;
import com.medfusion.product.object.maps.appt.precheck.util.HeaderConfig;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestIMHProxyService;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ApptPrecheckIMHProxyServiceTest extends BaseTestNG {
	String getaccessToken;
	public static PropertyFileLoader propertyData;
	public static IMHProxyServicePayload payload;
	public static PostAPIRequestIMHProxyService postAPIRequest;
	public static AccessToken accessToken;
	public static HeaderConfig headerConfig;
	public static Appointment testData;
	APIVerification apiVerification = new APIVerification();
	CommonMethods commonMtd;

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {
		propertyData = new PropertyFileLoader();
		postAPIRequest = PostAPIRequestIMHProxyService.getPostAPIRequestIMHProxyService();
		accessToken = AccessToken.getAccessToken();
		getaccessToken = accessToken.getaccessTokenPost();
		payload = IMHProxyServicePayload.getIMHProxyServicePayload();
		headerConfig = HeaderConfig.getHeaderConfig();
		testData = new Appointment();
		commonMtd = new CommonMethods();
		postAPIRequest.setupRequestSpecBuilder(propertyData.getProperty("baseurl.imh.service"));
		log("BASE URL-" + propertyData.getProperty("baseurl.imh.service"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveAnImhFormPost() {
		Response response = postAPIRequest.saveAnImhFormPost(propertyData.getProperty("baseurl.imh.service"),
				headerConfig.HeaderwithToken(getaccessToken),
				payload.getSaveImhFormPayload(propertyData.getProperty("save.concept.id"),
						propertyData.getProperty("save.concept.name"), propertyData.getProperty("save.form.id"),
						propertyData.getProperty("imh.form.practice.id")),
				propertyData.getProperty("imh.form.practice.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyImhFormPracticeId(response, propertyData.getProperty("save.concept.name"),
				propertyData.getProperty("save.concept.id"), propertyData.getProperty("imh.form.practice.id"),
				propertyData.getProperty("save.form.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetImhFormByConceptNameAndPracticeId() {
		Response response = postAPIRequest.getImhFormByConceptNameAndPracticeId(
				propertyData.getProperty("baseurl.imh.service"), propertyData.getProperty("save.concept.name"),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("imh.form.practice.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyImhFormPracticeId(response, propertyData.getProperty("save.concept.name"),
				propertyData.getProperty("save.concept.id"), propertyData.getProperty("imh.form.practice.id"),
				propertyData.getProperty("save.form.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetMasterListOfImhForms() {
		Response response = postAPIRequest.getMasterListOfImhForms(propertyData.getProperty("baseurl.imh.service"),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("imh.form.practice.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyImhMasterFormResponse(response, "conceptName");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUploadImhFormsPost() {
		Response response = postAPIRequest.uploadMasterListOfImhForms(propertyData.getProperty("baseurl.imh.service"),
				propertyData.getProperty("upload.imh.master.file"), headerConfig.HeaderWithToken(getaccessToken));
		log("Verifying the response");
		if (response.getStatusCode() == 207) {
			assertEquals(response.getStatusCode(), 207);
			apiVerification.verifyImhMasterFormResponse(response, "conceptName");
		} else {
			assertEquals(response.getStatusCode(), 400);
			apiVerification.verifyImhMasterListIfAlreadyExist(response,
					propertyData.getProperty("message.forms.already.exist"));
		}
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveAnImhFormPostOnlyWithConceptNameAndPracticeId() {
		Response response = postAPIRequest.saveAnImhFormPost(propertyData.getProperty("baseurl.imh.service"),
				headerConfig.HeaderwithToken(getaccessToken),
				payload.saveImhFormPayload(propertyData.getProperty("save.concept.name"),
						propertyData.getProperty("imh.form.practice.id")),
				propertyData.getProperty("imh.form.practice.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyImhFormPracticeId(response, propertyData.getProperty("save.concept.name"),
				propertyData.getProperty("save.concept.id"), propertyData.getProperty("imh.form.practice.id"),
				propertyData.getProperty("save.form.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveAnImhFormPostWithoutConceptNameAndPracticeId() {
		Response response = postAPIRequest.saveAnImhFormPost(propertyData.getProperty("baseurl.imh.service"),
				headerConfig.HeaderwithToken(getaccessToken),
				payload.getSaveImhFormPayload(propertyData.getProperty("save.concept.id"),
						propertyData.getProperty("save.form.id")),
				propertyData.getProperty("imh.form.practice.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveAnImhFormPostForProperModelSchema() {
		Response response = postAPIRequest.saveAnImhFormPost(propertyData.getProperty("baseurl.imh.service"),
				headerConfig.HeaderwithToken(getaccessToken),
				payload.getSaveImhFormPayload(propertyData.getProperty("save.concept.id"),
						propertyData.getProperty("save.concept.name"), propertyData.getProperty("save.form.id"),
						propertyData.getProperty("imh.form.practice.id")),
				propertyData.getProperty("imh.form.practice.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyImhFormPracticeId(response, propertyData.getProperty("save.concept.name"),
				propertyData.getProperty("save.concept.id"), propertyData.getProperty("imh.form.practice.id"),
				propertyData.getProperty("save.form.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveAnImhFormAndReflectOnMasterListPost() {
		Response response = postAPIRequest.saveAnImhFormPost(propertyData.getProperty("baseurl.imh.service"),
				headerConfig.HeaderwithToken(getaccessToken),
				payload.saveImhFormPayload(propertyData.getProperty("new.concept.name"),
						propertyData.getProperty("imh.form.practice.id")),
				propertyData.getProperty("imh.form.practice.id"));
		assertEquals(response.getStatusCode(), 200);

		Response masterListResponse = postAPIRequest.getMasterListOfImhForms(
				propertyData.getProperty("baseurl.imh.service"), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("imh.form.practice.id"));

		log("Verifying the response");
		assertEquals(masterListResponse.getStatusCode(), 200);
		apiVerification.responseTimeValidation(masterListResponse);
		apiVerification.verifyImhMasterFormResponse(masterListResponse, "conceptName");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUploadMasterListMultipleTimesPost() {
		Response response = postAPIRequest.uploadMasterListOfImhForms(propertyData.getProperty("baseurl.imh.service"),
				propertyData.getProperty("upload.10.imh.forms"), headerConfig.HeaderWithToken(getaccessToken));
		assertEquals(response.getStatusCode(), 400);

		Response updateResponse = postAPIRequest.uploadMasterListOfImhForms(
				propertyData.getProperty("baseurl.imh.service"), propertyData.getProperty("upload.10.imh.forms"),
				headerConfig.HeaderWithToken(getaccessToken));

		log("Verifying the response");
		apiVerification.verifyImhMasterListIfAlreadyExist(updateResponse,
				propertyData.getProperty("message.forms.already.exist"));
		assertEquals(updateResponse.getStatusCode(), 400);
		apiVerification.responseTimeValidation(updateResponse);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUploadBlankMasterList() {
		Response response = postAPIRequest.uploadMasterListOfImhForms(propertyData.getProperty("baseurl.imh.service"),
				propertyData.getProperty("upload.blank.master.list"), headerConfig.HeaderWithToken(getaccessToken));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.verifyImhMasterListIfAlreadyExist(response,
				propertyData.getProperty("message.forms.already.exist"));
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUploadMasterListWithSpecialChar() {
		Response response = postAPIRequest.uploadMasterListOfImhForms(propertyData.getProperty("baseurl.imh.service"),
				propertyData.getProperty("upload.spec.char.master.list"), headerConfig.HeaderWithToken(getaccessToken));
		log("Verifying the response");
		if (response.getStatusCode() == 207) {
			assertEquals(response.getStatusCode(), 207);
			apiVerification.verifyImhMasterFormResponse(response, "conceptName");
		} else {
			assertEquals(response.getStatusCode(), 400);
			apiVerification.verifyImhMasterListIfAlreadyExist(response,
					propertyData.getProperty("message.forms.already.exist"));
		}
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUploadRandomDetailsPost() {
		String randomDetail = commonMtd.generateRandomNum();
		Response response = postAPIRequest.saveAnImhFormPost(propertyData.getProperty("baseurl.imh.service"),
				headerConfig.HeaderwithToken(getaccessToken),
				payload.uploadImhFormPayload(randomDetail, randomDetail, randomDetail,
						propertyData.getProperty("form.source.imh"), randomDetail,
						propertyData.getProperty("imh.form.practice.id")),
				propertyData.getProperty("imh.form.practice.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyImhFormPracticeId(response, "Shoulder" + randomDetail, randomDetail,
				propertyData.getProperty("imh.form.practice.id"), randomDetail);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetPracticeIds() {
		Response response = postAPIRequest.uploadMasterListOfImhForms(propertyData.getProperty("baseurl.imh.service"),
				propertyData.getProperty("upload.imh.master.file"), headerConfig.HeaderWithToken(getaccessToken));
		if (response.getStatusCode() == 207) {
			assertEquals(response.getStatusCode(), 207);
			apiVerification.verifyImhMasterFormResponse(response, "conceptName");
		} else {
			assertEquals(response.getStatusCode(), 400);
			apiVerification.verifyImhMasterListIfAlreadyExist(response,
					propertyData.getProperty("message.forms.already.exist"));
		}

		Response getFormsResponse = postAPIRequest.getMasterListOfImhForms(
				propertyData.getProperty("baseurl.imh.service"), headerConfig.HeaderWithToken(getaccessToken),
				propertyData.getProperty("imh.form.practice.id"));

		log("Verifying the response");
		assertEquals(getFormsResponse.getStatusCode(), 200);
		apiVerification.responseTimeValidation(getFormsResponse);
		apiVerification.verifyPracticeIds(getFormsResponse, propertyData.getProperty("imh.form.practice.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAllPracticeIds() {
		Response getFormsResponse = postAPIRequest.getMasterListOfImhForms(
				propertyData.getProperty("baseurl.imh.service"), headerConfig.HeaderWithToken(getaccessToken),
				propertyData.getProperty("imh.form.practice.id"));

		log("Verifying the response");
		assertEquals(getFormsResponse.getStatusCode(), 200);
		apiVerification.responseTimeValidation(getFormsResponse);
		apiVerification.verifyPracticeIds(getFormsResponse, propertyData.getProperty("imh.form.practice.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetPracticeIdsForNewPractice() {
		Response getFormsResponse = postAPIRequest.getMasterListOfImhForms(
				propertyData.getProperty("baseurl.imh.service"), headerConfig.HeaderWithToken(getaccessToken),
				propertyData.getProperty("new.practice.zero.id"));

		log("Verifying the response");
		assertEquals(getFormsResponse.getStatusCode(), 200);
		apiVerification.responseTimeValidation(getFormsResponse);
		apiVerification.verifyPracticeIdsForNewPractice(getFormsResponse);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCustomFormReflectOnGetCallPost() {
		String randomDetail = commonMtd.generateRandomNum();
		Response response = postAPIRequest.saveAnImhFormPost(propertyData.getProperty("baseurl.imh.service"),
				headerConfig.HeaderwithToken(getaccessToken),
				payload.uploadImhFormPayload(randomDetail, randomDetail, randomDetail,
						propertyData.getProperty("form.source.imh"), randomDetail,
						propertyData.getProperty("imh.form.practice.id")),
				propertyData.getProperty("imh.form.practice.id"));
		assertEquals(response.getStatusCode(), 200);

		Response getFormsResponse = postAPIRequest.getMasterListOfImhForms(
				propertyData.getProperty("baseurl.imh.service"), headerConfig.HeaderWithToken(getaccessToken),
				propertyData.getProperty("imh.form.practice.id"));

		log("Verifying the response");
		assertEquals(getFormsResponse.getStatusCode(), 200);
		apiVerification.responseTimeValidation(getFormsResponse);
		apiVerification.verifyCustomForm(getFormsResponse, "Shoulder" + randomDetail);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testProceedBackAndAnsSameQues() throws NullPointerException, IOException {
		log("schedule an Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("imh.form.practice.id"), propertyData.getProperty("mf.apt.scheduler.phone"),
				propertyData.getProperty("mf.apt.scheduler.email"), getaccessToken);

		log("Generating encounter ID");
		Response encounterResponse = postAPIRequest.createEncounterForPatient(
				headerConfig.HeaderwithToken(getaccessToken),
				payload.createEncounterPayload(Appointment.apptId, Appointment.plus20Minutes,
						propertyData.getProperty("encounter.concept.name"),
						propertyData.getProperty("encounter.patient.env"),
						propertyData.getProperty("encounter.practice.system.id"), 1, 1, 1991,
						propertyData.getProperty("encounter.patient.fName"),
						propertyData.getProperty("encounter.patient.gender"),
						propertyData.getProperty("encounter.patient.lang"),
						propertyData.getProperty("encounter.patient.lName"),
						propertyData.getProperty("encounter.patient.practice.name"),
						propertyData.getProperty("encounter.patient.source")),
				propertyData.getProperty("imh.form.practice.id"), Appointment.patientId);
		assertEquals(encounterResponse.getStatusCode(), 200);
		JSONObject jsonObj = new JSONObject(encounterResponse.asString());
		String getFirstQuestion = jsonObj.getJSONObject("normalizedForm").getString("questionText");
		JsonPath js = new JsonPath(encounterResponse.asString());
		String encounterId = js.getString("encounterId");
		String patientAnswerUrl = js.getString("patientAnswerUrl");

		log("Answer the question");
		Response answerResponse = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.answerPayload(true, false, "0", patientAnswerUrl),
				propertyData.getProperty("imh.form.practice.id"), encounterId);
		assertEquals(answerResponse.getStatusCode(), 200);
		JSONObject jsonObject = new JSONObject(answerResponse.asString());
		String getSecondQuestion = jsonObject.getJSONObject("normalizedForm").getString("questionText");
		log("first question and second question will not be same");
		apiVerification.verifyQuestionNotSame(getFirstQuestion, getSecondQuestion);

		log("Move to previous question");
		Response movePrevious = postAPIRequest.moveToPreviousOrNextQuestion(
				headerConfig.HeaderwithToken(getaccessToken), payload.moveNextOrPrevQuestionPayload("MovePrev"),
				propertyData.getProperty("imh.form.practice.id"), encounterId);
		assertEquals(movePrevious.getStatusCode(), 200);
		JSONObject jsnObject = new JSONObject(movePrevious.asString());
		String getPrevQuestion = jsnObject.getJSONObject("normalizedForm").getString("questionText");
		log("previous question and first question will be same");
		apiVerification.verifySameQuestion(getPrevQuestion, getFirstQuestion);

		log("Again Answer the same question");
		Response answerSameResponse = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.answerPayload(true, false, "0", patientAnswerUrl),
				propertyData.getProperty("imh.form.practice.id"), encounterId);

		log("Verifying the response");
		assertEquals(answerSameResponse.getStatusCode(), 200);
		JSONObject jsnObj = new JSONObject(answerSameResponse.asString());
		String getNextQuesAnswer = jsnObj.getJSONObject("normalizedForm").getString("questionText");
		apiVerification.responseTimeValidation(answerSameResponse);
		log("previous question and next question will be same");
		apiVerification.verifyQuestionNotSame(getFirstQuestion, getNextQuesAnswer);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testProceedBackAndAnsDifferentQues() throws NullPointerException, IOException {
		log("schedule an Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("imh.form.practice.id"), propertyData.getProperty("mf.apt.scheduler.phone"),
				propertyData.getProperty("mf.apt.scheduler.email"), getaccessToken);

		log("Generating encounter ID");
		Response encounterResponse = postAPIRequest.createEncounterForPatient(
				headerConfig.HeaderwithToken(getaccessToken),
				payload.createEncounterPayload(Appointment.apptId, Appointment.plus20Minutes,
						propertyData.getProperty("encounter.concept.name"),
						propertyData.getProperty("encounter.patient.env"),
						propertyData.getProperty("encounter.practice.system.id"), 1, 1, 1991,
						propertyData.getProperty("encounter.patient.fName"),
						propertyData.getProperty("encounter.patient.gender"),
						propertyData.getProperty("encounter.patient.lang"),
						propertyData.getProperty("encounter.patient.lName"),
						propertyData.getProperty("encounter.patient.practice.name"),
						propertyData.getProperty("encounter.patient.source")),
				propertyData.getProperty("imh.form.practice.id"), Appointment.patientId);
		assertEquals(encounterResponse.getStatusCode(), 200);
		JSONObject jsonObj = new JSONObject(encounterResponse.asString());
		String getFirstQuestion = jsonObj.getJSONObject("normalizedForm").getString("questionText");
		JsonPath js = new JsonPath(encounterResponse.asString());
		String encounterId = js.getString("encounterId");
		String patientAnswerUrl = js.getString("patientAnswerUrl");

		log("Answer the question");
		Response answerResponse = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.answerPayload(true, false, "0", patientAnswerUrl),
				propertyData.getProperty("imh.form.practice.id"), encounterId);
		assertEquals(answerResponse.getStatusCode(), 200);
		JSONObject jsonObject = new JSONObject(answerResponse.asString());
		String getSecondQuestion = jsonObject.getJSONObject("normalizedForm").getString("questionText");
		log("first question and second question will not be same");
		apiVerification.verifyQuestionNotSame(getFirstQuestion, getSecondQuestion);

		log("Move to previous question");
		Response movePrevious = postAPIRequest.moveToPreviousOrNextQuestion(
				headerConfig.HeaderwithToken(getaccessToken), payload.moveNextOrPrevQuestionPayload("MovePrev"),
				propertyData.getProperty("imh.form.practice.id"), encounterId);
		assertEquals(movePrevious.getStatusCode(), 200);
		JSONObject jsnObject = new JSONObject(movePrevious.asString());
		String getPrevQuestion = jsnObject.getJSONObject("normalizedForm").getString("questionText");
		log("previous question and first question will be same");
		apiVerification.verifySameQuestion(getPrevQuestion, getFirstQuestion);

		log("Again Answer the same question");
		Response answerSameResponse = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.answerPayload(true, false, "1", patientAnswerUrl),
				propertyData.getProperty("imh.form.practice.id"), encounterId);

		log("Verifying the response");
		assertEquals(answerSameResponse.getStatusCode(), 200);
		JSONObject jsnObj = new JSONObject(answerSameResponse.asString());
		String getNextQuesAnswer = jsnObj.getJSONObject("normalizedForm").getString("questionText");
		apiVerification.responseTimeValidation(answerSameResponse);
		log("first question and next question will not be same");
		apiVerification.verifyQuestionNotSame(getFirstQuestion, getNextQuesAnswer);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testProceedBackAndShowAnswerValue() throws NullPointerException, IOException {
		log("schedule an Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("imh.form.practice.id"), propertyData.getProperty("mf.apt.scheduler.phone"),
				propertyData.getProperty("mf.apt.scheduler.email"), getaccessToken);

		log("Generating encounter ID");
		Response encounterResponse = postAPIRequest.createEncounterForPatient(
				headerConfig.HeaderwithToken(getaccessToken),
				payload.createEncounterPayload(Appointment.apptId, Appointment.plus20Minutes,
						propertyData.getProperty("encounter.concept.name"),
						propertyData.getProperty("encounter.patient.env"),
						propertyData.getProperty("encounter.practice.system.id"), 1, 1, 1991,
						propertyData.getProperty("encounter.patient.fName"),
						propertyData.getProperty("encounter.patient.gender"),
						propertyData.getProperty("encounter.patient.lang"),
						propertyData.getProperty("encounter.patient.lName"),
						propertyData.getProperty("encounter.patient.practice.name"),
						propertyData.getProperty("encounter.patient.source")),
				propertyData.getProperty("imh.form.practice.id"), Appointment.patientId);
		assertEquals(encounterResponse.getStatusCode(), 200);
		JSONObject jsonObj = new JSONObject(encounterResponse.asString());
		String getFirstQuestion = jsonObj.getJSONObject("normalizedForm").getString("questionText");
		JsonPath js = new JsonPath(encounterResponse.asString());
		String encounterId = js.getString("encounterId");
		String patientAnswerUrl = js.getString("patientAnswerUrl");

		log("Answer the question");
		Response answerResponse = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.answerPayload(true, false, "0", patientAnswerUrl),
				propertyData.getProperty("imh.form.practice.id"), encounterId);
		assertEquals(answerResponse.getStatusCode(), 200);
		JSONObject jsonObject = new JSONObject(answerResponse.asString());
		String getSecondQuestion = jsonObject.getJSONObject("normalizedForm").getString("questionText");
		log("first question and second question will not be same");
		apiVerification.verifyQuestionNotSame(getFirstQuestion, getSecondQuestion);

		log("Move to previous question");
		Response movePrevious = postAPIRequest.moveToPreviousOrNextQuestion(
				headerConfig.HeaderwithToken(getaccessToken), payload.moveNextOrPrevQuestionPayload("MovePrev"),
				propertyData.getProperty("imh.form.practice.id"), encounterId);

		log("Verifying the response");
		assertEquals(movePrevious.getStatusCode(), 200);
		JSONObject jsnObject = new JSONObject(movePrevious.asString());
		String getPrevQuestion = jsnObject.getJSONObject("normalizedForm").getString("questionText");
		log("previous question and first question will be same and answer value will be same");
		apiVerification.verifySameQuestion(getPrevQuestion, getFirstQuestion);
		apiVerification.verifyPrevAnswerValue(movePrevious);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSkipAndMoveNextQuestion() throws NullPointerException, IOException {
		log("schedule an Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("imh.form.practice.id"), propertyData.getProperty("mf.apt.scheduler.phone"),
				propertyData.getProperty("mf.apt.scheduler.email"), getaccessToken);
		log("Generating encounter ID");

		Response encounterResponse = postAPIRequest.createEncounterForPatient(
				headerConfig.HeaderwithToken(getaccessToken),
				payload.createEncounterPayload(Appointment.apptId, Appointment.plus20Minutes,
						propertyData.getProperty("encounter.concept.name"),
						propertyData.getProperty("encounter.patient.env"),
						propertyData.getProperty("encounter.practice.system.id"), 1, 1, 1991,
						propertyData.getProperty("encounter.patient.fName"),
						propertyData.getProperty("encounter.patient.gender"),
						propertyData.getProperty("encounter.patient.lang"),
						propertyData.getProperty("encounter.patient.lName"),
						propertyData.getProperty("encounter.patient.practice.name"),
						propertyData.getProperty("encounter.patient.source")),
				propertyData.getProperty("imh.form.practice.id"), Appointment.patientId);
		assertEquals(encounterResponse.getStatusCode(), 200);
		JSONObject jsonObj = new JSONObject(encounterResponse.asString());
		String getFirstQuestion = jsonObj.getJSONObject("normalizedForm").getString("questionText");
		JsonPath js = new JsonPath(encounterResponse.asString());
		String encounterId = js.getString("encounterId");
		String patientAnswerUrl = js.getString("patientAnswerUrl");

		log("Answer the question");
		Response answerResponse = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.answerPayload(true, false, "0", patientAnswerUrl),
				propertyData.getProperty("imh.form.practice.id"), encounterId);
		assertEquals(answerResponse.getStatusCode(), 200);
		JSONObject jsonObject = new JSONObject(answerResponse.asString());
		String getSecondQuestion = jsonObject.getJSONObject("normalizedForm").getString("questionText");
		log("first question and second question will not be same");
		apiVerification.verifyQuestionNotSame(getFirstQuestion, getSecondQuestion);

		log("Move to previous question");
		Response movePrevious = postAPIRequest.moveToPreviousOrNextQuestion(
				headerConfig.HeaderwithToken(getaccessToken), payload.moveNextOrPrevQuestionPayload("MovePrev"),
				propertyData.getProperty("imh.form.practice.id"), encounterId);
		assertEquals(movePrevious.getStatusCode(), 200);
		JSONObject jsnObj = new JSONObject(movePrevious.asString());
		String getPrevQuestion = jsnObj.getJSONObject("normalizedForm").getString("questionText");
		apiVerification.verifyPrevAnswerValue(movePrevious);

		log("Skip the question");
		Response skipAnswerResponse = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.answerPayload(true, true, "0", patientAnswerUrl),
				propertyData.getProperty("imh.form.practice.id"), encounterId);

		log("Verifying the response");
		assertEquals(skipAnswerResponse.getStatusCode(), 200);
		JSONObject jsnObject = new JSONObject(skipAnswerResponse.asString());
		String getNextQuestion = jsnObject.getJSONObject("normalizedForm").getString("questionText");
		log("Next question and previous question will not be same");
		apiVerification.verifyQuestionNotSame(getNextQuestion, getPrevQuestion);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testIfQuestionsCompleted() throws NullPointerException, IOException {
		log("schedule an Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("imh.form.practice.id"), propertyData.getProperty("mf.apt.scheduler.phone"),
				propertyData.getProperty("mf.apt.scheduler.email"), getaccessToken);

		log("Generating encounter ID");
		Response encounterResponse = postAPIRequest.createEncounterForPatient(
				headerConfig.HeaderwithToken(getaccessToken),
				payload.createEncounterPayload(Appointment.apptId, Appointment.plus20Minutes,
						propertyData.getProperty("encounter.new.concept.name"),
						propertyData.getProperty("encounter.patient.env"),
						propertyData.getProperty("encounter.practice.system.id"), 1, 1, 1991,
						propertyData.getProperty("encounter.patient.fName"),
						propertyData.getProperty("encounter.patient.gender"),
						propertyData.getProperty("encounter.patient.lang"),
						propertyData.getProperty("encounter.patient.lName"),
						propertyData.getProperty("encounter.patient.practice.name"),
						propertyData.getProperty("encounter.patient.source")),
				propertyData.getProperty("imh.form.practice.id"), Appointment.patientId);
		assertEquals(encounterResponse.getStatusCode(), 200);
		JsonPath js = new JsonPath(encounterResponse.asString());
		String encounterId = js.getString("encounterId");
		String patientAnswerUrl = js.getString("patientAnswerUrl");

		log("Answer First question");
		Response answerResponse1 = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.firstAnswerPayload(false, patientAnswerUrl), propertyData.getProperty("imh.form.practice.id"),
				encounterId);
		assertEquals(answerResponse1.getStatusCode(), 200);
		JsonPath js1 = new JsonPath(answerResponse1.asString());
		String patientAnswerUrl1 = js1.getString("patientAnswerUrl");

		log("Answer Second question");
		Response answerResponse2 = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.secondAnswerPayload(false, patientAnswerUrl1), propertyData.getProperty("imh.form.practice.id"),
				encounterId);
		assertEquals(answerResponse2.getStatusCode(), 200);
		JsonPath js2 = new JsonPath(answerResponse2.asString());
		String patientAnswerUrl2 = js2.getString("patientAnswerUrl");

		log("Answer Third question");
		Response answerResponse3 = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.thirdAnswerPayload(false, patientAnswerUrl2), propertyData.getProperty("imh.form.practice.id"),
				encounterId);
		assertEquals(answerResponse3.getStatusCode(), 200);
		JsonPath js3 = new JsonPath(answerResponse3.asString());
		String patientAnswerUrl3 = js3.getString("patientAnswerUrl");

		log("Answer Forth question");
		Response answerResponse4 = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.forthAnswerPayload(false, patientAnswerUrl3), propertyData.getProperty("imh.form.practice.id"),
				encounterId);
		assertEquals(answerResponse4.getStatusCode(), 200);
		JsonPath js4 = new JsonPath(answerResponse4.asString());
		String patientAnswerUrl4 = js4.getString("patientAnswerUrl");

		log("Answer Fifth question");
		Response answerResponse5 = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.fifthAnswerPayload(false, patientAnswerUrl4), propertyData.getProperty("imh.form.practice.id"),
				encounterId);
		assertEquals(answerResponse5.getStatusCode(), 200);
		JsonPath js5 = new JsonPath(answerResponse5.asString());
		String patientAnswerUrl5 = js5.getString("patientAnswerUrl");

		log("Answer Sixth question");
		Response answerResponse6 = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.sixthAnswerPayload(false, patientAnswerUrl5), propertyData.getProperty("imh.form.practice.id"),
				encounterId);
		assertEquals(answerResponse6.getStatusCode(), 200);
		JsonPath js6 = new JsonPath(answerResponse6.asString());
		String patientAnswerUrl6 = js6.getString("patientAnswerUrl");

		log("Answer Seventh question");
		Response answerResponse7 = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.SeventhAnswerPayload(false, patientAnswerUrl6),
				propertyData.getProperty("imh.form.practice.id"), encounterId);
		assertEquals(answerResponse7.getStatusCode(), 200);
		JsonPath js7 = new JsonPath(answerResponse7.asString());
		String patientAnswerUrl7 = js7.getString("patientAnswerUrl");

		log("Answer Eighth question");
		Response answerResponse8 = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.eighthAnswerPayload(false, patientAnswerUrl7), propertyData.getProperty("imh.form.practice.id"),
				encounterId);
		assertEquals(answerResponse8.getStatusCode(), 200);
		JsonPath js8 = new JsonPath(answerResponse8.asString());
		String patientAnswerUrl8 = js8.getString("patientAnswerUrl");

		log("Answer Ningth question");
		Response answerResponse9 = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.ninthAnswerPayload(false, patientAnswerUrl8), propertyData.getProperty("imh.form.practice.id"),
				encounterId);
		assertEquals(answerResponse9.getStatusCode(), 200);
		JsonPath js9 = new JsonPath(answerResponse9.asString());
		String encounterId9 = js9.getString("encounterId");
		String patientAnswerUrl9 = js9.getString("patientAnswerUrl");

		log("Verifying the response");
		assertEquals("https://www.ptimhservice.com/api/v2/imh/encounters/" + encounterId + "/patientanswer/0",
				patientAnswerUrl9);
		Response movePrevious = postAPIRequest.moveToPreviousOrNextQuestion(
				headerConfig.HeaderwithToken(getaccessToken), payload.moveNextOrPrevQuestionPayload("MovePrev"),
				propertyData.getProperty("imh.form.practice.id"), encounterId9);
		assertEquals(movePrevious.getStatusCode(), 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAssociatedNewApptType() throws NullPointerException, IOException {
		log("schedule an Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("imh.form.practice.id"), propertyData.getProperty("mf.apt.scheduler.phone"),
				propertyData.getProperty("mf.apt.scheduler.email"), getaccessToken);

		log("Generating encounter ID");
		Response response = postAPIRequest.createEncounterForPatient(headerConfig.HeaderwithToken(getaccessToken),
				payload.createEncounterPayload(Appointment.apptId, Appointment.plus20Minutes,
						propertyData.getProperty("encounter.new.concept.name"),
						propertyData.getProperty("encounter.patient.env"),
						propertyData.getProperty("encounter.practice.system.id"), 1, 1, 1991,
						propertyData.getProperty("encounter.patient.fName"),
						propertyData.getProperty("encounter.patient.gender"),
						propertyData.getProperty("encounter.patient.lang"),
						propertyData.getProperty("encounter.patient.lName"),
						propertyData.getProperty("encounter.patient.practice.name"),
						propertyData.getProperty("encounter.patient.source")),
				propertyData.getProperty("imh.form.practice.id"), Appointment.patientId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyIntakeIdAndUrl(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInvalidEncounterIdPut() throws NullPointerException, IOException {
		log("schedule an Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("imh.form.practice.id"), propertyData.getProperty("mf.apt.scheduler.phone"),
				propertyData.getProperty("mf.apt.scheduler.email"), getaccessToken);

		log("Generating encounter ID");
		Response encounterResponse = postAPIRequest.createEncounterForPatient(
				headerConfig.HeaderwithToken(getaccessToken),
				payload.createEncounterPayload(Appointment.apptId, Appointment.plus20Minutes,
						propertyData.getProperty("encounter.new.concept.name"),
						propertyData.getProperty("encounter.patient.env"),
						propertyData.getProperty("encounter.practice.system.id"), 1, 1, 1991,
						propertyData.getProperty("encounter.patient.fName"),
						propertyData.getProperty("encounter.patient.gender"),
						propertyData.getProperty("encounter.patient.lang"),
						propertyData.getProperty("encounter.patient.lName"),
						propertyData.getProperty("encounter.patient.practice.name"),
						propertyData.getProperty("encounter.patient.source")),
				propertyData.getProperty("imh.form.practice.id"), Appointment.patientId);
		assertEquals(encounterResponse.getStatusCode(), 200);

		JsonPath js = new JsonPath(encounterResponse.asString());
		String encounterId = js.getString("encounterId");
		String patientAnswerUrl = js.getString("patientAnswerUrl");
		String[] words = patientAnswerUrl.split(encounterId);
		String urlFirstPart = words[0];
		String urlSecondPart = words[1];

		log("Answer the question");
		Response answerResponse = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.firstAnswerPayload(false, urlFirstPart + encounterId + "werJBxXMK" + urlSecondPart),
				propertyData.getProperty("imh.form.practice.id"), encounterId + "werJBxXMK");

		log("Verifying the response");
		assertEquals(answerResponse.getStatusCode(), 400);
		apiVerification.responseTimeValidation(answerResponse);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testEndOfQuestionnaireValueTrue() throws NullPointerException, IOException {
		log("schedule an Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("imh.form.practice.id"), propertyData.getProperty("mf.apt.scheduler.phone"),
				propertyData.getProperty("mf.apt.scheduler.email"), getaccessToken);

		log("Generating encounter ID");
		Response encounterResponse = postAPIRequest.createEncounterForPatient(
				headerConfig.HeaderwithToken(getaccessToken),
				payload.createEncounterPayload(Appointment.apptId, Appointment.plus20Minutes,
						propertyData.getProperty("encounter.new.concept.name"),
						propertyData.getProperty("encounter.patient.env"),
						propertyData.getProperty("encounter.practice.system.id"), 1, 1, 1991,
						propertyData.getProperty("encounter.patient.fName"),
						propertyData.getProperty("encounter.patient.gender"),
						propertyData.getProperty("encounter.patient.lang"),
						propertyData.getProperty("encounter.patient.lName"),
						propertyData.getProperty("encounter.patient.practice.name"),
						propertyData.getProperty("encounter.patient.source")),
				propertyData.getProperty("imh.form.practice.id"), Appointment.patientId);
		assertEquals(encounterResponse.getStatusCode(), 200);
		JsonPath js = new JsonPath(encounterResponse.asString());
		String encounterId = js.getString("encounterId");
		String patientAnswerUrl = js.getString("patientAnswerUrl");

		log("Answer First question");
		Response answerResponse1 = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.firstAnswerPayload(false, patientAnswerUrl), propertyData.getProperty("imh.form.practice.id"),
				encounterId);
		assertEquals(answerResponse1.getStatusCode(), 200);
		JsonPath js1 = new JsonPath(answerResponse1.asString());
		String patientAnswerUrl1 = js1.getString("patientAnswerUrl");

		log("Answer Second question");
		Response answerResponse2 = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.secondAnswerPayload(false, patientAnswerUrl1), propertyData.getProperty("imh.form.practice.id"),
				encounterId);
		assertEquals(answerResponse2.getStatusCode(), 200);
		JsonPath js2 = new JsonPath(answerResponse2.asString());
		String patientAnswerUrl2 = js2.getString("patientAnswerUrl");

		log("Answer Third question");
		Response answerResponse3 = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.thirdAnswerPayload(false, patientAnswerUrl2), propertyData.getProperty("imh.form.practice.id"),
				encounterId);
		assertEquals(answerResponse3.getStatusCode(), 200);
		JsonPath js3 = new JsonPath(answerResponse3.asString());
		String patientAnswerUrl3 = js3.getString("patientAnswerUrl");

		log("Answer Forth question");
		Response answerResponse4 = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.forthAnswerPayload(false, patientAnswerUrl3), propertyData.getProperty("imh.form.practice.id"),
				encounterId);
		assertEquals(answerResponse4.getStatusCode(), 200);
		JsonPath js4 = new JsonPath(answerResponse4.asString());
		String patientAnswerUrl4 = js4.getString("patientAnswerUrl");

		log("Answer Fifth question");
		Response answerResponse5 = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.fifthAnswerPayload(false, patientAnswerUrl4), propertyData.getProperty("imh.form.practice.id"),
				encounterId);
		assertEquals(answerResponse5.getStatusCode(), 200);
		JsonPath js5 = new JsonPath(answerResponse5.asString());
		String patientAnswerUrl5 = js5.getString("patientAnswerUrl");

		log("Answer Sixth question");
		Response answerResponse6 = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.sixthAnswerPayload(false, patientAnswerUrl5), propertyData.getProperty("imh.form.practice.id"),
				encounterId);
		assertEquals(answerResponse6.getStatusCode(), 200);
		JsonPath js6 = new JsonPath(answerResponse6.asString());
		String patientAnswerUrl6 = js6.getString("patientAnswerUrl");

		log("Answer Seventh question");
		Response answerResponse7 = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.SeventhAnswerPayload(false, patientAnswerUrl6),
				propertyData.getProperty("imh.form.practice.id"), encounterId);
		assertEquals(answerResponse7.getStatusCode(), 200);
		JsonPath js7 = new JsonPath(answerResponse7.asString());
		String patientAnswerUrl7 = js7.getString("patientAnswerUrl");

		log("Answer Eighth question");
		Response answerResponse8 = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.eighthAnswerPayload(false, patientAnswerUrl7), propertyData.getProperty("imh.form.practice.id"),
				encounterId);
		assertEquals(answerResponse8.getStatusCode(), 200);
		JsonPath js8 = new JsonPath(answerResponse8.asString());
		String patientAnswerUrl8 = js8.getString("patientAnswerUrl");

		log("Answer Ningth question");
		Response answerResponse9 = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.ninthAnswerPayload(false, patientAnswerUrl8), propertyData.getProperty("imh.form.practice.id"),
				encounterId);

		log("Verifying the response");
		assertEquals(answerResponse9.getStatusCode(), 200);
		apiVerification.verifyEndOfQuestionerValueTrue(answerResponse9);
		apiVerification.responseTimeValidation(answerResponse9);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testEndOfQuestionnaireValueFalse() throws NullPointerException, IOException {
		log("schedule an Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("imh.form.practice.id"), propertyData.getProperty("mf.apt.scheduler.phone"),
				propertyData.getProperty("mf.apt.scheduler.email"), getaccessToken);

		log("Generating encounter ID");
		Response encounterResponse = postAPIRequest.createEncounterForPatient(
				headerConfig.HeaderwithToken(getaccessToken),
				payload.createEncounterPayload(Appointment.apptId, Appointment.plus20Minutes,
						propertyData.getProperty("encounter.new.concept.name"),
						propertyData.getProperty("encounter.patient.env"),
						propertyData.getProperty("encounter.practice.system.id"), 1, 1, 1991,
						propertyData.getProperty("encounter.patient.fName"),
						propertyData.getProperty("encounter.patient.gender"),
						propertyData.getProperty("encounter.patient.lang"),
						propertyData.getProperty("encounter.patient.lName"),
						propertyData.getProperty("encounter.patient.practice.name"),
						propertyData.getProperty("encounter.patient.source")),
				propertyData.getProperty("imh.form.practice.id"), Appointment.patientId);
		assertEquals(encounterResponse.getStatusCode(), 200);

		JsonPath js = new JsonPath(encounterResponse.asString());
		String encounterId = js.getString("encounterId");
		String patientAnswerUrl = js.getString("patientAnswerUrl");

		log("Answer First question");
		Response answerResponse = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.firstAnswerPayload(false, patientAnswerUrl), propertyData.getProperty("imh.form.practice.id"),
				encounterId);

		log("Verifying the response");
		assertEquals(answerResponse.getStatusCode(), 200);
		apiVerification.verifyEndOfQuestionerValueFalse(answerResponse);
		apiVerification.responseTimeValidation(answerResponse);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAcceptRadioButtonAnswer() throws NullPointerException, IOException {
		log("schedule an Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("imh.form.practice.id"), propertyData.getProperty("mf.apt.scheduler.phone"),
				propertyData.getProperty("mf.apt.scheduler.email"), getaccessToken);

		log("Generating encounter ID");
		Response encounterResponse = postAPIRequest.createEncounterForPatient(
				headerConfig.HeaderwithToken(getaccessToken),
				payload.createEncounterPayload(Appointment.apptId, Appointment.plus20Minutes,
						propertyData.getProperty("encounter.concept.name"),
						propertyData.getProperty("encounter.patient.env"),
						propertyData.getProperty("encounter.practice.system.id"), 1, 1, 1991,
						propertyData.getProperty("encounter.patient.fName"),
						propertyData.getProperty("encounter.patient.gender"),
						propertyData.getProperty("encounter.patient.lang"),
						propertyData.getProperty("encounter.patient.lName"),
						propertyData.getProperty("encounter.patient.practice.name"),
						propertyData.getProperty("encounter.patient.source")),
				propertyData.getProperty("imh.form.practice.id"), Appointment.patientId);
		assertEquals(encounterResponse.getStatusCode(), 200);
		JSONObject jsonObj = new JSONObject(encounterResponse.asString());
		String getFirstQuestion = jsonObj.getJSONObject("normalizedForm").getString("questionText");
		JsonPath js = new JsonPath(encounterResponse.asString());
		String encounterId = js.getString("encounterId");
		String patientAnswerUrl = js.getString("patientAnswerUrl");

		log("Answer the question");
		Response answerResponse = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.answerPayload(true, false, "0", patientAnswerUrl),
				propertyData.getProperty("imh.form.practice.id"), encounterId);
		assertEquals(answerResponse.getStatusCode(), 200);
		JSONObject jsonObject = new JSONObject(answerResponse.asString());
		String getSecondQuestion = jsonObject.getJSONObject("normalizedForm").getString("questionText");

		log("first question and second question will not be same");
		apiVerification.verifyQuestionNotSame(getFirstQuestion, getSecondQuestion);
		apiVerification.responseTimeValidation(answerResponse);

		log("Verifying the response");
		Response movePrevious = postAPIRequest.moveToPreviousOrNextQuestion(
				headerConfig.HeaderwithToken(getaccessToken), payload.moveNextOrPrevQuestionPayload("MovePrev"),
				propertyData.getProperty("imh.form.practice.id"), encounterId);
		assertEquals(movePrevious.getStatusCode(), 200);
		apiVerification.verifyPrevAnswerValue(movePrevious, 0, 0);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAcceptTextboxAnswer() throws NullPointerException, IOException {
		log("schedule an Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("imh.form.practice.id"), propertyData.getProperty("mf.apt.scheduler.phone"),
				propertyData.getProperty("mf.apt.scheduler.email"), getaccessToken);

		log("Generating encounter ID");
		Response encounterResponse = postAPIRequest.createEncounterForPatient(
				headerConfig.HeaderwithToken(getaccessToken),
				payload.createEncounterPayload(Appointment.apptId, Appointment.plus20Minutes,
						propertyData.getProperty("encounter.new.concept.name"),
						propertyData.getProperty("encounter.patient.env"),
						propertyData.getProperty("encounter.practice.system.id"), 1, 1, 1991,
						propertyData.getProperty("encounter.patient.fName"),
						propertyData.getProperty("encounter.patient.gender"),
						propertyData.getProperty("encounter.patient.lang"),
						propertyData.getProperty("encounter.patient.lName"),
						propertyData.getProperty("encounter.patient.practice.name"),
						propertyData.getProperty("encounter.patient.source")),
				propertyData.getProperty("imh.form.practice.id"), Appointment.patientId);
		assertEquals(encounterResponse.getStatusCode(), 200);
		JsonPath js = new JsonPath(encounterResponse.asString());
		String encounterId = js.getString("encounterId");
		String patientAnswerUrl = js.getString("patientAnswerUrl");
		JSONObject jsonObj = new JSONObject(encounterResponse.asString());
		String getFirstQuestion = jsonObj.getJSONObject("normalizedForm").getString("questionText");

		log("Answer First question");
		Response answerResponse = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.firstAnswerPayload(false, patientAnswerUrl), propertyData.getProperty("imh.form.practice.id"),
				encounterId);

		log("Verify Response");
		assertEquals(answerResponse.getStatusCode(), 200);
		JSONObject jsonObject = new JSONObject(answerResponse.asString());
		String getSecondQuestion = jsonObject.getJSONObject("normalizedForm").getString("questionText");
		log("first question and second question will not be same");
		apiVerification.verifyQuestionNotSame(getFirstQuestion, getSecondQuestion);
		apiVerification.responseTimeValidation(answerResponse);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAcceptMultipleCheckboxAnswer() throws NullPointerException, IOException {
		log("schedule an Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("imh.form.practice.id"), propertyData.getProperty("mf.apt.scheduler.phone"),
				propertyData.getProperty("mf.apt.scheduler.email"), getaccessToken);

		log("Generating encounter ID");
		Response encounterResponse = postAPIRequest.createEncounterForPatient(
				headerConfig.HeaderwithToken(getaccessToken),
				payload.createEncounterPayload(Appointment.apptId, Appointment.plus20Minutes,
						propertyData.getProperty("encounter.new.concept.name"),
						propertyData.getProperty("encounter.patient.env"),
						propertyData.getProperty("encounter.practice.system.id"), 1, 1, 1991,
						propertyData.getProperty("encounter.patient.fName"),
						propertyData.getProperty("encounter.patient.gender"),
						propertyData.getProperty("encounter.patient.lang"),
						propertyData.getProperty("encounter.patient.lName"),
						propertyData.getProperty("encounter.patient.practice.name"),
						propertyData.getProperty("encounter.patient.source")),
				propertyData.getProperty("imh.form.practice.id"), Appointment.patientId);

		assertEquals(encounterResponse.getStatusCode(), 200);
		JsonPath js = new JsonPath(encounterResponse.asString());
		String encounterId = js.getString("encounterId");
		String answerUrl = js.getString("patientAnswerUrl");
		JSONObject jsonObj = new JSONObject(encounterResponse.asString());
		String getFirstQuestion = jsonObj.getJSONObject("normalizedForm").getString("questionText");

		log("Answer First question");
		Response answerResponse = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.firstAnswerPayload(false, answerUrl), propertyData.getProperty("imh.form.practice.id"),
				encounterId);
		assertEquals(answerResponse.getStatusCode(), 200);
		JsonPath json = new JsonPath(answerResponse.asString());
		String patientAnswerUrl = json.getString("patientAnswerUrl");
		JSONObject jsonObject = new JSONObject(answerResponse.asString());
		String getSecondQuestion = jsonObject.getJSONObject("normalizedForm").getString("questionText");

		log("first question and second question will not be same");
		apiVerification.verifyQuestionNotSame(getFirstQuestion, getSecondQuestion);

		log("Answer Second question");
		Response response = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.multipleAnswerPayload(false, patientAnswerUrl),
				propertyData.getProperty("imh.form.practice.id"), encounterId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		JSONObject jsnObject = new JSONObject(response.asString());
		String getThirdQuestion = jsnObject.getJSONObject("normalizedForm").getString("questionText");
		apiVerification.verifyQuestionNotSame(getThirdQuestion, getFirstQuestion);
		apiVerification.verifyQuestionNotSame(getThirdQuestion, getSecondQuestion);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testProceedBackToPreviousQuestion() throws NullPointerException, IOException {
		log("schedule an Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("imh.form.practice.id"), propertyData.getProperty("mf.apt.scheduler.phone"),
				propertyData.getProperty("mf.apt.scheduler.email"), getaccessToken);

		log("Generating encounter ID");
		Response encounterResponse = postAPIRequest.createEncounterForPatient(
				headerConfig.HeaderwithToken(getaccessToken),
				payload.createEncounterPayload(Appointment.apptId, Appointment.plus20Minutes,
						propertyData.getProperty("encounter.concept.name"),
						propertyData.getProperty("encounter.patient.env"),
						propertyData.getProperty("encounter.practice.system.id"), 1, 1, 1991,
						propertyData.getProperty("encounter.patient.fName"),
						propertyData.getProperty("encounter.patient.gender"),
						propertyData.getProperty("encounter.patient.lang"),
						propertyData.getProperty("encounter.patient.lName"),
						propertyData.getProperty("encounter.patient.practice.name"),
						propertyData.getProperty("encounter.patient.source")),
				propertyData.getProperty("imh.form.practice.id"), Appointment.patientId);
		assertEquals(encounterResponse.getStatusCode(), 200);
		JSONObject jsonObj = new JSONObject(encounterResponse.asString());
		String getFirstQuestion = jsonObj.getJSONObject("normalizedForm").getString("questionText");
		JsonPath js = new JsonPath(encounterResponse.asString());
		String encounterId = js.getString("encounterId");
		String patientAnswerUrl = js.getString("patientAnswerUrl");

		log("Answer the question");
		Response answerResponse = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.answerPayload(true, false, "0", patientAnswerUrl),
				propertyData.getProperty("imh.form.practice.id"), encounterId);
		assertEquals(answerResponse.getStatusCode(), 200);
		JSONObject jsonObject = new JSONObject(answerResponse.asString());
		String getSecondQuestion = jsonObject.getJSONObject("normalizedForm").getString("questionText");
		log("first question and second question will not be same");
		apiVerification.verifyQuestionNotSame(getFirstQuestion, getSecondQuestion);

		log("Move to previous question");
		Response movePrevious = postAPIRequest.moveToPreviousOrNextQuestion(
				headerConfig.HeaderwithToken(getaccessToken), payload.moveNextOrPrevQuestionPayload("MovePrev"),
				propertyData.getProperty("imh.form.practice.id"), encounterId);

		log("Verifying the response");
		assertEquals(movePrevious.getStatusCode(), 200);
		JSONObject jsnObject = new JSONObject(movePrevious.asString());
		String getPrevQuestion = jsnObject.getJSONObject("normalizedForm").getString("questionText");
		log("previous question and first question will be same");
		apiVerification.responseTimeValidation(movePrevious);
		apiVerification.verifySameQuestion(getPrevQuestion, getFirstQuestion);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnswerWithIncorrectField() throws NullPointerException, IOException {
		log("schedule an Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("imh.form.practice.id"), propertyData.getProperty("mf.apt.scheduler.phone"),
				propertyData.getProperty("mf.apt.scheduler.email"), getaccessToken);

		log("Generating encounter ID");
		Response response = postAPIRequest.createEncounterForPatient(headerConfig.HeaderwithToken(getaccessToken),
				payload.createEncounterPayload(Appointment.apptId, Appointment.plus20Minutes,
						propertyData.getProperty("encounter.concept.name"),
						propertyData.getProperty("encounter.patient.env"),
						propertyData.getProperty("encounter.practice.system.id"), 1, 1, 1991,
						propertyData.getProperty("encounter.patient.fName"),
						propertyData.getProperty("encounter.patient.gender"),
						propertyData.getProperty("encounter.patient.lang"),
						propertyData.getProperty("encounter.patient.lName"),
						propertyData.getProperty("encounter.patient.practice.name"),
						propertyData.getProperty("encounter.patient.source")),
				propertyData.getProperty("imh.form.practice.id"), Appointment.patientId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyIntakeIdAndUrl(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnswerWithBlankValue() throws NullPointerException, IOException {
		log("schedule an Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("imh.form.practice.id"), propertyData.getProperty("mf.apt.scheduler.phone"),
				propertyData.getProperty("mf.apt.scheduler.email"), getaccessToken);

		log("Generating encounter ID");
		Response response = postAPIRequest.createEncounterForPatient(headerConfig.HeaderwithToken(getaccessToken),
				payload.createEncounterPayload(Appointment.apptId, Appointment.plus20Minutes,
						propertyData.getProperty("encounter.concept.name"),
						propertyData.getProperty("encounter.patient.env"),
						propertyData.getProperty("encounter.practice.system.id"), 1, 1, 1991,
						propertyData.getProperty("encounter.patient.fName"),
						propertyData.getProperty("encounter.patient.gender"),
						propertyData.getProperty("encounter.patient.lang"),
						propertyData.getProperty("encounter.patient.lName"),
						propertyData.getProperty("encounter.patient.practice.name"),
						propertyData.getProperty("encounter.patient.source")),
				propertyData.getProperty("imh.form.practice.id"), Appointment.patientId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyIntakeIdAndUrl(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnswerBothFields() throws NullPointerException, IOException {
		log("schedule an Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("imh.form.practice.id"), propertyData.getProperty("mf.apt.scheduler.phone"),
				propertyData.getProperty("mf.apt.scheduler.email"), getaccessToken);

		log("Generating encounter ID");
		Response response = postAPIRequest.createEncounterForPatient(headerConfig.HeaderwithToken(getaccessToken),
				payload.createEncounterPayload(Appointment.apptId, Appointment.plus20Minutes,
						propertyData.getProperty("encounter.concept.name"),
						propertyData.getProperty("encounter.patient.env"),
						propertyData.getProperty("encounter.practice.system.id"), 1, 1, 1991,
						propertyData.getProperty("encounter.patient.fName"),
						propertyData.getProperty("encounter.patient.gender"),
						propertyData.getProperty("encounter.patient.lang"),
						propertyData.getProperty("encounter.patient.lName"),
						propertyData.getProperty("encounter.patient.practice.name"),
						propertyData.getProperty("encounter.patient.source")),
				propertyData.getProperty("imh.form.practice.id"), Appointment.patientId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyIntakeIdAndUrl(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSkipingSomeAnswer() throws NullPointerException, IOException {
		log("schedule an Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("imh.form.practice.id"), propertyData.getProperty("mf.apt.scheduler.phone"),
				propertyData.getProperty("mf.apt.scheduler.email"), getaccessToken);

		log("Generating encounter ID");
		Response encounterResponse = postAPIRequest.createEncounterForPatient(
				headerConfig.HeaderwithToken(getaccessToken),
				payload.createEncounterPayload(Appointment.apptId, Appointment.plus20Minutes,
						propertyData.getProperty("encounter.concept.name"),
						propertyData.getProperty("encounter.patient.env"),
						propertyData.getProperty("encounter.practice.system.id"), 1, 1, 1991,
						propertyData.getProperty("encounter.patient.fName"),
						propertyData.getProperty("encounter.patient.gender"),
						propertyData.getProperty("encounter.patient.lang"),
						propertyData.getProperty("encounter.patient.lName"),
						propertyData.getProperty("encounter.patient.practice.name"),
						propertyData.getProperty("encounter.patient.source")),
				propertyData.getProperty("imh.form.practice.id"), Appointment.patientId);
		assertEquals(encounterResponse.getStatusCode(), 200);

		log("Answer the question");
		assertEquals(encounterResponse.getStatusCode(), 200);
		JsonPath js = new JsonPath(encounterResponse.asString());
		String encounterId = js.getString("encounterId");
		String patientAnswerUrl = js.getString("patientAnswerUrl");

		log("Answer the question");
		Response firstAnswer = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.answerPayload(true, true, "0", patientAnswerUrl),
				propertyData.getProperty("imh.form.practice.id"), encounterId);
		assertEquals(firstAnswer.getStatusCode(), 200);
		JsonPath js1 = new JsonPath(encounterResponse.asString());
		String patientAnswerUrl1 = js1.getString("patientAnswerUrl");

		log("Answer the second  question");
		Response secondAnswer = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.answerPayload(true, true, "0", patientAnswerUrl1),
				propertyData.getProperty("imh.form.practice.id"), encounterId);

		log("Verifying the response");
		assertEquals(secondAnswer.getStatusCode(), 200);
		apiVerification.responseTimeValidation(secondAnswer);
		apiVerification.verifyIntakeIdAndUrl(secondAnswer);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testProceedToNextQuestion() throws NullPointerException, IOException {
		log("schedule an Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("imh.form.practice.id"), propertyData.getProperty("mf.apt.scheduler.phone"),
				propertyData.getProperty("mf.apt.scheduler.email"), getaccessToken);

		log("Generating encounter ID");
		Response encounterResponse = postAPIRequest.createEncounterForPatient(
				headerConfig.HeaderwithToken(getaccessToken),
				payload.createEncounterPayload(Appointment.apptId, Appointment.plus20Minutes,
						propertyData.getProperty("encounter.concept.name"),
						propertyData.getProperty("encounter.patient.env"),
						propertyData.getProperty("encounter.practice.system.id"), 1, 1, 1991,
						propertyData.getProperty("encounter.patient.fName"),
						propertyData.getProperty("encounter.patient.gender"),
						propertyData.getProperty("encounter.patient.lang"),
						propertyData.getProperty("encounter.patient.lName"),
						propertyData.getProperty("encounter.patient.practice.name"),
						propertyData.getProperty("encounter.patient.source")),
				propertyData.getProperty("imh.form.practice.id"), Appointment.patientId);
		assertEquals(encounterResponse.getStatusCode(), 200);
		JSONObject jsonObj = new JSONObject(encounterResponse.asString());
		String getFirstQuestion = jsonObj.getJSONObject("normalizedForm").getString("questionText");
		JsonPath js = new JsonPath(encounterResponse.asString());
		String encounterId = js.getString("encounterId");
		String patientAnswerUrl = js.getString("patientAnswerUrl");

		log("Answer the question");
		Response answerResponse = postAPIRequest.answerTheQuestion(headerConfig.HeaderwithToken(getaccessToken),
				payload.answerPayload(true, false, "0", patientAnswerUrl),
				propertyData.getProperty("imh.form.practice.id"), encounterId);

		log("Verifying the response");
		assertEquals(answerResponse.getStatusCode(), 200);
		JSONObject jsonObject = new JSONObject(answerResponse.asString());
		String getSecondQuestion = jsonObject.getJSONObject("normalizedForm").getString("questionText");
		log("first question and second question will not be same");
		apiVerification.verifyQuestionNotSame(getFirstQuestion, getSecondQuestion);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetFirstQuesAndEncounterId() throws NullPointerException, IOException {
		log("schedule an Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("imh.form.practice.id"), propertyData.getProperty("mf.apt.scheduler.phone"),
				propertyData.getProperty("mf.apt.scheduler.email"), getaccessToken);

		log("Generating encounter ID");
		Response encounterResponse = postAPIRequest.createEncounterForPatient(
				headerConfig.HeaderwithToken(getaccessToken),
				payload.createEncounterPayload(Appointment.apptId, Appointment.plus20Minutes,
						propertyData.getProperty("encounter.concept.name"),
						propertyData.getProperty("encounter.patient.env"),
						propertyData.getProperty("encounter.practice.system.id"), 1, 1, 1991,
						propertyData.getProperty("encounter.patient.fName"),
						propertyData.getProperty("encounter.patient.gender"),
						propertyData.getProperty("encounter.patient.lang"),
						propertyData.getProperty("encounter.patient.lName"),
						propertyData.getProperty("encounter.patient.practice.name"),
						propertyData.getProperty("encounter.patient.source")),
				propertyData.getProperty("imh.form.practice.id"), Appointment.patientId);

		log("Verifying the response");
		assertEquals(encounterResponse.getStatusCode(), 200);
		apiVerification.verifyQuestion(encounterResponse, propertyData.getProperty("encounter.question"));
		apiVerification.responseTimeValidation(encounterResponse);
	}

}
