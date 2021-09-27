package com.medfusion.product.object.maps.pss2.page.util;

import static io.restassured.RestAssured.given;

import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PostAPIRequestDBAdapter {
	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec;

	public void setupRequestSpecBuilder(String baseurl, Map<String, String> Header) {

		RestAssured.baseURI = baseurl;

		requestSpec = new RequestSpecBuilder().addHeaders(Header).setContentType(ContentType.JSON).build();
		responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	}

	public Response State(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + path).then().log().all()
				.extract().response();
		return response;
	}

	public Response ssoConfig(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + path).then().log().all()
				.extract().response();
		return response;
	}

	public Response ssoCode(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + path).then().log().all()
				.extract().response();
		return response;
	}

	public Response SSOGetPracticeFromGuid(String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(path).then().log().all().extract()
				.response();
		return response;
	}

	public Response speciality(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + path).then().log().all()
				.extract().response();
		return response;
	}

	public Response specilityById(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + path).then().log().all()
				.extract().response();
		return response;
	}

	public Response specilityByIdLanguage(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().header("language", "EN").when().get(practiceid + path)
				.then().log().all().extract().response();
		return response;
	}

	public Response ruleAll(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + path).then().log().all()
				.extract().response();
		return response;
	}

	public Response ruleMaster(String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(path).then().log().all().extract()
				.response();
		return response;
	}

	public Response resellerPractice(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + path).then().log().all()
				.extract().response();
		return response;
	}

	public Response resellerByLanguage(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().header("language", "EN").when().get(practiceid + path)
				.then().log().all().extract().response();
		return response;
	}

	public Response resellerByLanguageByUI(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().header("language", "EN").when().get(practiceid + path)
				.then().log().all().extract().response();
		return response;
	}

	public Response practiceTimeZone(String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(path).then().log().all().extract()
				.response();
		return response;
	}

	public Response practiceLanguage(String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(path).then().log().all().extract()
				.response();
		return response;
	}

	public Response practiceInfo(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + path).then().log().all()
				.extract().response();
		return response;
	}

	public Response practiceDetails(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + path).then().log().all()
				.extract().response();
		return response;
	}

	public Response patientMatch(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + path).then().log().all()
				.extract().response();
		return response;
	}

	public Response patientMatchMaster(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + path).then().log().all()
				.extract().response();
		return response;
	}

	public Response patientInfo(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + path).then().log().all()
				.extract().response();
		return response;
	}

	public Response partnerCustomandInfo(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + path).then().log().all()
				.extract().response();
		return response;
	}

	public Response partnerCustMetaData(String practiceid, String path, String value) throws Exception {
		Response response = given().queryParam("fieldType", value).spec(requestSpec).log().all().when()
				.get(practiceid + path).then().log().all().extract().response();
		return response;
	}

	public Response partnerMetaData(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + path).then().log().all()
				.extract().response();
		return response;
	}

	public Response partnerMetaDataByCode(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + path).then().log().all()
				.extract().response();
		return response;
	}

	public Response partnerConfig(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + path).then().log().all()
				.extract().response();
		return response;
	}

	public Response partnerDetails(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + path).then().log().all()
				.extract().response();
		return response;
	}

	public Response partnerWithoutPractice(String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(path).then().log().all().extract()
				.response();
		return response;
	}

	public Response partnerBaseUrl(String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(path).then().log().all().extract()
				.response();
		return response;
	}
	

	public Response loginless(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + path).then().log().all()
				.extract().response();
		return response;
	}
	
	public Response loginlessGuid( String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(path).then().log().all()
				.extract().response();
		return response;
	}
	
	public Response lockout(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid+path).then().log().all()
				.extract().response();
		return response;
	}
	
	
	public Response location(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid+path).then().log().all()
				.extract().response();
		return response;
	}
	public Response locationById(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid+path).then().log().all()
				.extract().response();
		return response;
	}
	
	public Response locationByAppId(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid+path).then().log().all()
				.extract().response();
		return response;
	}
	
	public Response locationByAppointmentId(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid+path).then().log().all()
				.extract().response();
		return response;
	}
	
	public Response locationBook(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid+path).then().log().all()
				.extract().response();
		return response;
	}
	
	public Response locationBookAppType(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid+path).then().log().all()
				.extract().response();
		return response;
	}
	
	public Response locationSpeciality(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid+path).then().log().all()
				.extract().response();
		return response;
	}
	
	public Response locationSpecialityAppType(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid+path).then().log().all()
				.extract().response();
		return response;
	}
	
	public Response locationSpecialityBook(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid+path).then().log().all()
				.extract().response();
		return response;
	}
	
	public Response locationLinkbyid(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid+path).then().log().all()
				.extract().response();
		return response;
	}
	
	public Response locations(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid+path).then().log().all()
				.extract().response();
		return response;
	}
	
	public Response insuranceCarrrier(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid+path).then().log().all()
				.extract().response();
		return response;
	}
	
	public Response insuranceCarrrierById(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid+path).then().log().all()
				.extract().response();
		return response;
	}
	
	public Response flowIdentity(String practiceid, String path,String flowType) throws Exception {
		Response response = given().queryParam("flowType", flowType).spec(requestSpec).log().all().when().get(practiceid+path).then().log().all()
				.extract().response();
		return response;
	}
	
	public Response configPartnerCode(String path,String partnerCode) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(path+partnerCode).then().log().all()
				.extract().response();
		return response;
	}
	
	public Response rescheduleApp(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid+path).then().log().all()
				.extract().response();
		return response;
	}
	
	public Response rescheduleAppGuid(String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(path).then().log().all()
				.extract().response();
		return response;
	}
	
	public Response practiceMetaData(String practiceid, String path,String flowType) throws Exception {
		Response response = given().queryParam("flowType", flowType).spec(requestSpec).log().all().when().get(practiceid+path).then().log().all()
				.extract().response();
		return response;
	}
}
