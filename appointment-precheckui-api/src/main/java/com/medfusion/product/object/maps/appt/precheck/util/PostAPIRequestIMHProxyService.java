package com.medfusion.product.object.maps.appt.precheck.util;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.Map;

public class PostAPIRequestIMHProxyService extends BaseTestNGWebDriver {
	private static PostAPIRequestIMHProxyService imhPostRequest = new PostAPIRequestIMHProxyService();

	private PostAPIRequestIMHProxyService() {

	}

	public static PostAPIRequestIMHProxyService getPostAPIRequestIMHProxyService() {
		return imhPostRequest;
	}

	public static RequestSpecification requestSpec;

	public void setupRequestSpecBuilder(String baseurl) {
		RestAssured.baseURI = baseurl;
		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
	}

	public Response getImhFormByConceptNameAndPracticeId(String bseurl, String conceptName, Map<String, String> Header,
			String practiceId) {
		log("Execute GET request for Get IMH Form by Concept Name and Practice Id");
		RestAssured.baseURI = bseurl;
		Response response = given().queryParam("conceptName", conceptName).headers(Header).log().all().when()
				.get(practiceId + "/form").then().log().all().extract().response();
		return response;
	}

	public Response getMasterListOfImhForms(String bseurl, Map<String, String> Header, String practiceId) {
		log("Execute GET request for Get Master List of IMH Forms");
		RestAssured.baseURI = bseurl;
		Response response = given().headers(Header).log().all().when().get(practiceId + "/forms").then().log().all()
				.extract().response();
		return response;
	}

	public Response saveAnImhFormPost(String bseurl, Map<String, String> Header, String payload, String practiceId) {
		log("Execute GET request for save single IMH Form");
		RestAssured.baseURI = bseurl;
		Response response = given().headers(Header).log().all().body(payload).when().post(practiceId + "/forms").then()
				.log().all().extract().response();
		return response;
	}

	public Response uploadMasterListOfImhForms(String bseurl, String masterFormList, Map<String, String> Header) {
		log("Execute GET request for uplaod IMH Forms");
		RestAssured.baseURI = bseurl;
		Response response = given().contentType("multipart/form-data").multiPart("file", new File(masterFormList))
				.headers(Header).log().all().when().post("/masterForms").then().log().all().extract().response();
		return response;
	}
	
	public Response createEncounterForPatient(Map<String, String> Header,String payload, String practiceId, String patientId) {
		log("Execute POST request for Create Encounter for patient and get first question.");
		Response response = given().spec(requestSpec).headers(Header).body(payload).log().all().when()
				.post(practiceId + "/intakeQuestions/" + patientId).then().log().all().extract().response();
		return response;
	}

	public Response answerTheQuestion(Map<String, String> Header, String Payload,String practiceId, String encounterId) {
		log("Execute PUT request for Answer the question.");
		Response response = given().spec(requestSpec).headers(Header).body(Payload).log().all().when()
				.put(practiceId + "/answerQuestion/" + encounterId).then().log().all().extract().response();
		return response;
	}

	public Response getInterviewCursor(Map<String, String> Header, String practiceId, String encounterId) {
		log("Execute GET request for Get interview cursor");
		Response response = given().spec(requestSpec).headers(Header).log().all().when()
				.get(practiceId + "/interviewCursor/" + encounterId).then().log().all().extract().response();
		return response;
	}

	public Response moveToPreviousOrNextQuestion(Map<String, String> Header,String payload, String practiceId, String encounterId) {
		log("Execute POST request for Move to Previous or Next Question.");
		Response response = given().spec(requestSpec).headers(Header).body(payload).log().all().when()
				.post(practiceId + "/move/interviewCursor/" + encounterId).then().log().all().extract().response();
		return response;
	}

}
