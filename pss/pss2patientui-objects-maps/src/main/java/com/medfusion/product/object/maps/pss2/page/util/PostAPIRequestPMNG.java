// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.util;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.Map;

import org.hamcrest.Matchers;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PostAPIRequestPMNG extends BaseTestNGWebDriver {

	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec;

	public void setupRequestSpecBuilder(String baseUrl, Map<String, String> Header) {

		RestAssured.baseURI = baseUrl;

		requestSpec = new RequestSpecBuilder().addHeaders(Header).setContentType(ContentType.JSON).build();
		responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	}

	APIVerification apiVerification = new APIVerification();

	public Response apptDetailFromGuid(String baseUrl, Map<String, String> Header, String guidId, String practiceid) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().headers(Header).when()
				.get(practiceid + "/" + guidId + "/getapptdetails").then().log().all().extract().response();
		return response;
	}

	public Response getDetails(String baseUrl, Map<String, String> Header, String practiceid, String b) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().headers(Header).body(b).when().post(practiceid + "/getdetails").then()
				.log().all().extract().response();
		return response;
	}

	public Response practiceFromGuid(String baseUrl, Map<String, String> Header, String guidId) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().when().get("/anonymous/" + guidId).then().log().all().extract()
				.response();
		return response;
	}

	public Response linksValueGuid(String baseUrl, Map<String, String> Header, String guidId) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().queryParam("isreschedule", "true").when().get("/link/" + guidId).then()
				.log().all().extract().response();
		return response;
	}

	public Response linksValueGuidAndPractice(String baseUrl, Map<String, String> Header, String guidId,
			String practiceid) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().queryParam("isreschedule", "true").when()
				.get("/link/" + guidId + "/" + practiceid).then().log().all().extract().response();
		return response;
	}

	public Response linksDetailGuid(String baseUrl, Map<String, String> Header, String guidId) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().queryParam("isreschedule", "true").when().get("/linkdetail/" + guidId)
				.then().log().all().extract().response();
		return response;
	}

	public Response linksDetailGuidAndPractice(String baseUrl, Map<String, String> Header, String guidId) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().queryParam("isreschedule", "true").when().get("/linkdetail/" + guidId)
				.then().log().all().extract().response();
		return response;
	}

	public Response guidForLogoutPatient(String baseUrl, Map<String, String> Header, String PtacticeId) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().header("flowType", "LOGINLESS").when()
				.get(PtacticeId + "/patientlogout").then().log().all().extract().response();
		return response;
	}

	public Response practiceFromGuidLoginless(String baseUrl, Map<String, String> Header, String guidId) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().queryParam("isByPassGuid", false).when().get("/loginless/" + guidId)
				.then().log().all().extract().response();

		return response;
	}

	public Response tokenForLoginless(String baseUrl, Map<String, String> Header, String guidId) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().when().get("/view-appointment/" + guidId).then().log().all().extract()
				.response();
		return response;
	}

	public Response health(String baseUrl, Map<String, String> Header) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().when().get("/actuator/health").then().log().all().extract().response();
		return response;
	}

	public Response practiceInfo(String baseUrl, Map<String, String> Header, String PracticeId, String practiceName) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().when().get(PracticeId + "/practice").then().log().all().extract()
				.response();
		return response;
	}

	public Response practiceFromGuidSso(String baseUrl, Map<String, String> Header, String guidId) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().when().get("/sso/" + guidId).then().log().all().extract().response();

		return response;
	}

	public String createToken(String baseUrl, String practiceid) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().header("flowType", "LOGINLESS").get(practiceid + "/createtoken").then()
				.assertThat().statusCode(200).body("token", Matchers.notNullValue()).extract().response();
		JsonPath jsonPath = response.jsonPath();
		String access_Token = jsonPath.get("token");
		log("The Access Token is    " + jsonPath.get("token"));
		return access_Token;
	}

	public String createToken(String baseurl, String practiceid, String flowtype) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().header("flowType", flowtype).get(practiceid + "/createtoken").then()
				.assertThat().statusCode(200).body("token", Matchers.notNullValue()).extract().response();
		JsonPath jsonPath = response.jsonPath();
		String access_Token = jsonPath.get("token");
		log("The Access Token is    " + jsonPath.get("token"));
		return access_Token;
	}

	public Response upcomingConfiguration(String baseUrl, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().when().get(practiceId + "/upcomingconfiguration").then().log().all()
				.extract().response();
		return response;
	}

	public Response getapptDetail(String baseUrl, String b, Map<String, String> Header, String practiceId,
			String locationDisplayName, String apptTypeName) {
		RestAssured.baseURI = baseUrl;
		Response response = RestAssured.given().log().all().when().headers(Header).body(b).when()
				.post(practiceId + "/getdetails").then().log().all()
				.body("location.displayName", equalTo(locationDisplayName))
				.body("appointmentType.name", equalTo(apptTypeName)).extract().response();

		JsonPath js = new JsonPath(response.asString());

		log("Appointment location -" + js.getString("location.displayName"));
		log("Appointment location -" + js.getString("location.id"));
		log("Appointment Type -" + js.getString("appointmentType.name"));
		log("Appointment Book Name -" + js.getString("book.displayName"));

		return response;
	}

	public Response announcementByName(String baseUrl, Map<String, String> Header, String practiceId,
			String announcementcode) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().when().get(practiceId + "/announcement/" + announcementcode).then()
				.log().all().extract().response();
		return response;
	}

	public Response announcementByLanguage(String baseUrl, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().headers(Header).when().get(practiceId + "/announcementbylanguage")
				.then().log().all().extract().response();
		return response;
	}

	public Response announcementType(String baseUrl, Map<String, String> Header) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().when().get("/announcementtype").then().log().all().extract().response();
		return response;
	}

	public Response getImages(String baseUrl, Map<String, String> Header, String practiceId, String bookId) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().when().get(practiceId + "/book/" + bookId + "/image").then().log().all()
				.extract().response();
		return response;
	}

	public Response getLanguages(String baseUrl, Map<String, String> Header, String patientId) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().when().get("/language/" + patientId).then().log().all().extract()
				.response();
		return response;
	}

	public Response getStates(String baseUrl, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().headers(Header).log().all().when().get(practiceId + "/states").then()
				.log().all().extract().response();
		return response;
	}

	public Response validateProviderLink(String baseUrl, String b, Map<String, String> Header, String practiceId,
			String patientid) {
		RestAssured.baseURI = baseUrl;
		Response response;
		if (patientid == null) {
			response = RestAssured.given().when().headers(Header).body(b).log().all().when()
					.post(practiceId + "/validateproviderlink").then().log().all().extract().response();

		} else {
			response = RestAssured.given().when().headers(Header).body(b).log().all().when()
					.post(practiceId + "/validateproviderlink/" + patientid).then().log().all().extract().response();

		}
		return response;
	}

	public Response locationsByNextAvailable(String baseUrl, String b, Map<String, String> Header, String practiceId,
			String patientId) {
		RestAssured.baseURI = baseUrl;

		Response response;
		if (patientId == null) {
			response = RestAssured.given().when().headers(Header).body(b).log().all().when()
					.post(practiceId + "/location/nextavailable").then().log().all().extract().response();
		} else {

			response = RestAssured.given().when().headers(Header).body(b).log().all().when()
					.post(practiceId + "/location/nextavailable/" + patientId).then().log().all().extract().response();
		}
		return response;
	}

	public Response getAppTypesByNextAvailable(String baseUrl, String b, Map<String, String> Header, String practiceid,
			String patientid, String aaptype) {
		RestAssured.baseURI = baseUrl;

		Response response;
		if (patientid == null) {
			response = RestAssured.given().when().headers(Header).body(b).log().all().when()
					.post(practiceid + "/apptype/nextavailable").then().log().all().extract().response();
		} else {

			response = RestAssured.given().when().headers(Header).body(b).log().all().when()
					.post(practiceid + "/apptype/nextavailable" + patientid).then().log().all().extract().response();
		}
		return response;
	}

	public Response locationsByRule(String baseUrl, String b, Map<String, String> Header, String practiceid,
			String patientid) {
		RestAssured.baseURI = baseUrl;
		Response response;
		if (patientid == null) {
			response = RestAssured.given().when().headers(Header).body(b).log().all().when()
					.post(practiceid + "/location/rule").then().log().all().extract().response();
		} else {

			response = RestAssured.given().when().headers(Header).body(b).log().all().when()
					.post(practiceid + "/location/rule/" + patientid).then().log().all().extract().response();
		}
		return response;
	}

	public Response specialtyByRule(String baseUrl, String b, Map<String, String> Header, String practiceId,
			String patientId) {
		RestAssured.baseURI = baseUrl;
		Response response = RestAssured.given().when().headers(Header).body(b).log().all().when()
				.post(practiceId + "/specialty/rule/" + patientId).then().log().all().extract().response();
		return response;
	}

	public Response appointment(String baseUrl, Map<String, String> Header, String practiceId, String appointmentId) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().when().get(practiceId + "/appointment/" + appointmentId + "/book/test")
				.then().log().all().extract().response();
		return response;
	}

	public Response appointmentForIcs(String baseUrl, Map<String, String> Header, String practiceId,
			String appointmentId) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().when().get(practiceId + "/appointment/" + appointmentId).then().log()
				.all().extract().response();
		return response;
	}

	public Response upcomingAppointmentsByPage(String baseUrl, Map<String, String> Header, String practiceid,
			String patientid) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().headers(Header).queryParam("pageIndex", 1).log().all().when()
				.get(practiceid + "/upcomingappointmentsbypage/" + patientid + "/loginless").then().log().all()
				.extract().response();
		return response;
	}

	public Response insuranceCarrier(String baseUrl, Map<String, String> Header, String practiceid, String patientid) {
		RestAssured.baseURI = baseUrl;
		Response response;
		if (patientid == null) {

			response = given().log().all().headers(Header).log().all().when().get(practiceid + "/insurancecarrier")
					.then().log().all().extract().response();
			return response;

		} else {
			response = given().log().all().headers(Header).log().all().when()
					.get(practiceid + "/insurancecarrier/" + patientid).then().log().all().extract().response();
			return response;
		}
	}

	public Response cancellationReason(String baseUrl, Map<String, String> Header, String practiceId,
			String patientId) {
		RestAssured.baseURI = baseUrl;
		Response response;

		response = given().log().all().headers(Header).log().all().when()
				.get(practiceId + "/cancellationreason/" + patientId).then().log().all().extract().response();
		return response;
	}

	public Response rescheduleReason(String baseUrl, Map<String, String> Header, String practiceId, String patientId) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().headers(Header).log().all().when()
				.get(practiceId + "/reschedulereason/" + patientId).then().log().all().extract().response();
		return response;
	}

	public Response apptTypeNextAvailable(String baseUrl, String b, Map<String, String> Header, String practiceId,
			String patientId) {

		RestAssured.baseURI = baseUrl;
		Response response;
		if (patientId == null) {

			response = given().when().headers(Header).body(b).log().all().when()
					.post(practiceId + "/apptype/nextavailable").then().log().all().extract().response();
			return response;

		} else {
			response = given().when().headers(Header).body(b).log().all().when()
					.post(practiceId + "/apptype/nextavailable/" + patientId).then().log().all().extract().response();
			return response;
		}
	}

	public Response booksBynextAvailable(String baseUrl, String b, Map<String, String> Header, String practiceid,
			String patientid) {

		RestAssured.baseURI = baseUrl;
		if (patientid == null) {

			Response response = RestAssured.given().when().headers(Header).body(b).log().all().when()
					.post(practiceid + "/book/nextavailable").then().log().all().extract().response();
			return response;

		} else {
			Response response = RestAssured.given().when().headers(Header).body(b).log().all().when()
					.post(practiceid + "/book/nextavailable/" + patientid).then().log().all().extract().response();
			return response;
		}

	}

	public Response booksByRule(String baseUrl, String b, Map<String, String> Header, String practiceId,
			String patientid) {
		RestAssured.baseURI = baseUrl;
		Response response;
		if (patientid == null) {

			response = given().when().headers(Header).body(b).log().all().when().post(practiceId + "/book/rule").then()
					.log().all().extract().response();
			return response;

		} else {
			response = given().when().headers(Header).body(b).log().all().when()
					.post(practiceId + "/book/rule/" + patientid).then().log().all().extract().response();
			return response;
		}
	}

	public Response allowonlinecancellation(String baseUrl, String b, Map<String, String> Header, String practiceId,
			String patientId) {
		RestAssured.baseURI = baseUrl;

		Response response = given().when().headers(Header).body(b).log().all().when()
				.post(practiceId + "/allowonlinecancellation/" + patientId).then().log().all().extract().response();
		return response;
	}

	public Response cancelStatus(String baseUrl, String b, Map<String, String> Header, String practiceid,
			String patientid) {
		RestAssured.baseURI = baseUrl;

		Response response;

		if (patientid == null) {
			response = given().when().headers(Header).body(b).log().all().when()
					.post(practiceid + "/cancelstatus/" + patientid).then().log().all().extract().response();

		} else {
			response = given().when().headers(Header).body(b).log().all().when()
					.post(practiceid + "/cancelstatus/" + patientid).then().log().all().extract().response();
		}
		return response;
	}

	public Response commentDetails(String baseUrl, String b, Map<String, String> Header, String practiceId,
			String patientId) {
		RestAssured.baseURI = baseUrl;
		Response response;
		if (patientId == null) {
			response = given().when().headers(Header).body(b).log().all().when().post(practiceId + "/getCommentDetails")
					.then().log().all().extract().response();

		} else {
			response = given().when().headers(Header).body(b).log().all().when()
					.post(practiceId + "/getCommentDetails/" + patientId).then().log().all().extract().response();
		}
		return response;
	}

	public Response timeZoneCode(String baseUrl, String b, Map<String, String> Header, String practiceId,
			String patientId) {
		RestAssured.baseURI = baseUrl;

		Response response = given().when().headers(Header).body(b).log().all().when()
				.post(practiceId + "/timezonecode/" + patientId).then().log().all().extract().response();
		return response;
	}

	public Response availableSlots(String baseUrl, String b, Map<String, String> Header, String practiceId,
			String patientId) {
		RestAssured.baseURI = baseUrl;
		Response response;

		if (patientId == null) {
			response = given().when().headers(Header).body(b).log().all().when().post(practiceId + "/availableslots")
					.then().log().all().extract().response();

		} else {
			response = given().when().headers(Header).body(b).log().all().when()
					.post(practiceId + "/availableslots/" + patientId).then().log().all().extract().response();
		}

		return response;
	}

	public Response cancelAppointment(String baseUrl, String b, Map<String, String> Header, String practiceId,
			String patientId) {
		RestAssured.baseURI = baseUrl;

		Response response = given().when().headers(Header).body(b).log().all().when()
				.post(practiceId + "/cancelappointment/" + patientId).then().log().all().extract().response();

		return response;
	}

	public Response rescheduleAppointment(String baseUrl, String b, Map<String, String> Header, String practiceId,
			String patientId, String patientType) {
		RestAssured.baseURI = baseUrl;

		Response response = given().when().headers(Header).body(b).log().all().when()
				.post(practiceId + "/rescheduleappointment/" + patientId).then().log().all().extract().response();

		return response;
	}

	public Response scheduleAppointment(String baseUrl, String b, Map<String, String> Header, String practiceId,
			String patientId, String patientType) {

		RestAssured.baseURI = baseUrl;
		Response response;
		
		if (patientId == null) {
			response = given().when().headers(Header).body(b).log().all().when()
					.post(practiceId + "/scheduleappointment").then().log().all().extract().response();
		} else {
			response = given().when().headers(Header).body(b).log().all().when()
					.post(practiceId + "/scheduleappointment/" + patientId).then().log().all().extract().response();
		}
		return response;
	}

	public Response appointmentTypesByRule(String baseUrl, String b, Map<String, String> Header, String practiceId,
			String patientId) {

		RestAssured.baseURI = baseUrl;
		Response response;

		if (patientId == null) {
			response = given().when().headers(Header).body(b).log().all().when()
					.post(practiceId + "/appointmenttypes/rule").then().log().all().extract().response();
		} else {
			response = given().when().headers(Header).body(b).log().all().when()
					.post(practiceId + "/appointmenttypes/rule/" + patientId).then().log().all().extract().response();
		}
		return response;
	}

	public Response pastAppointmentsByPage(String baseUrl, Map<String, String> Header, String practiceid,
			String patientid) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().headers(Header).queryParam("pageIndex", 1).log().all().when()
				.get(practiceid + "/pastappointmentsbypage/" + patientid).then().log().all().extract().response();
		return response;
	}

	public Response logo(String baseUrl, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().headers(Header).when().get(practiceId + "/logo").then().log().all()
				.assertThat().statusCode(200).extract().response();
		return response;
	}

	public Response timezonePracticeResource(String baseUrl, Map<String, String> Header, String timezonePracticeId,
			String practiceName) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().when().get(timezonePracticeId + "/medfusionpractice").then().log().all()
				.extract().response();
		return response;
	}

	public Response practiceInfo(String baseUrl, Map<String, String> Header, String PracticeId) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().when().get(PracticeId + "/practice").then().log().all().extract()
				.response();
		return response;
	}

	public Response practiceResourceState(String baseUrl, Map<String, String> Header, String PracticeId) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().headers(Header).when().get(PracticeId + "/states").then().log().all()
				.extract().response();
		return response;
	}

	public Response resellerLogo(String baseUrl, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().when().get(practiceId + "/reseller/logo").then().log().all().extract()
				.response();
		return response;
	}

	public Response reseller(String baseUrl, Map<String, String> Header, String practiceId, String endPoint,
			String patientID) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().headers(Header).when().get(practiceId + endPoint + patientID).then()
				.log().all().extract().response();
		return response;
	}

	public Response sessionConfiguration(String baseUrl, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().when().get(practiceId + "/getsessionconfiguration").then().log().all()
				.extract().response();
		return response;
	}

	public Response practiceDetail(String baseUrl, Map<String, String> Header, String practiceId, String practiceName) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().when().get(practiceId + "/sso").then().log().all().extract().response();
		return response;
	}

	public Response practiceFromGuidSso(String baseUrl, Map<String, String> Header, String guidId, String endPoint) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().when().get(endPoint + guidId).then().log().all().extract().response();

		return response;
	}

	public Response timeZoneResource(String baseUrl, Map<String, String> Header, String patientId) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().when().get("/timezone/" + patientId).then().log().all().extract()
				.response();
		return response;
	}

	public Response upcomingConfiguration(String baseUrl, Map<String, String> Header, String practiceId,
			String endPoint) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().when().get(practiceId + endPoint).then().log().all().extract()
				.response();
		return response;
	}

	public Response demographicsProfiles(String baseUrl, Map<String, String> Header, String practiceId,
			String patientId) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().headers(Header).when().get(practiceId + "/demographics/" + patientId)
				.then().log().all().extract().response();
		return response;
	}

	public Response matchPatient(String baseUrl, Map<String, String> Header, String practiceId, String patientId) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().headers(Header).log().all().when()
				.get(practiceId + "/patientmatch/LOGINLESS").then().log().all().assertThat().statusCode(200).extract()
				.response();
		return response;
	}

	public Response flowIdentity(String baseUrl, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().headers(Header).log().all().when()
				.get(practiceId + "/flowidentity/LOGINLESS").then().log().all().extract().response();
		return response;
	}

	public Response patientInfo(String baseUrl, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().headers(Header).log().all().when()
				.get(practiceId + "/patientinfo/LOGINLESS").then().log().all().extract().response();
		return response;
	}

	public Response patientInfoAnonymous(String baseUrl, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().headers(Header).log().all().when()
				.get(practiceId + "/patientinfo/ANONYMOUS").then().log().all().extract().response();
		return response;
	}

	public Response genderMapping(String baseUrl, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().headers(Header).log().all().when().get(practiceId + "/gendermapping")
				.then().log().all().extract().response();
		return response;
	}

	public Response patientDemographics(String baseUrl, Map<String, String> Header, String practiceId,
			String patientId) {
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().headers(Header).log().all().when()
				.get(practiceId + "/demographics/" + patientId).then().log().all().extract().response();
		return response;
	}

	public Response anonymousMatchAndCreatePatient(String baseUrl, String b, Map<String, String> Header,
			String practiceId, String MatchPatientId) {
		RestAssured.baseURI = baseUrl;
		Response response = RestAssured.given().when().headers(Header).body(b).log().all().when()
				.post(practiceId + "/anonymouspatient/" + MatchPatientId).then().log().all().extract().response();
		return response;
	}

	public Response anonymousPatientNewPatient(String baseUrl, String b, Map<String, String> Header,
			String practiceId) {
		RestAssured.baseURI = baseUrl;
		Response response = RestAssured.given().log().all().when().headers(Header).body(b).when()
				.post(practiceId + "/anonymouspatient").then().log().all().extract().response();
		return response;
	}

	public Response identifyPatientForReschedule(String baseUrl, String b, Map<String, String> Header,
			String practiceId) {
		RestAssured.baseURI = baseUrl;
		Response response = RestAssured.given().when().headers(Header).body(b).log().all().when()
				.post(practiceId + "/identifypatient").then().log().all().extract().response();
		return response;
	}

	public Response specialtyByRule(String baseUrl, String b, Map<String, String> Header, String practiceId,
			String endPoint, String patientId) {
		RestAssured.baseURI = baseUrl;
		Response response = RestAssured.given().when().headers(Header).body(b).log().all().when()
				.post(practiceId + endPoint + patientId).then().log().all().extract().response();
		return response;
	}

	public Response createToken(String baseUrl, String b, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseUrl;
		Response response = RestAssured.given().when().headers(Header).body(b).log().all().when()
				.post("/createtoken/" + practiceId + "/tokens").then().log().all()
				.body("accessToken", Matchers.notNullValue()).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Valid Token -" + js.getString("validToken"));
		log("Access Token -" + js.getString("accessToken"));

		return response;
	}

	public Response locationsBasedOnZipcodeAndRadius(String baseUrl, String b, Map<String, String> Header,
			String practiceId, String patientId) {
		RestAssured.baseURI = baseUrl;
		Response response = RestAssured.given().when().headers(Header).body(b).log().all().when()
				.post(practiceId + "/zipcode/" + patientId).then().log().all().extract().response();
		return response;
	}

	public Response locationsBasedOnZipcode(String baseUrl, String b, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseUrl;
		Response response = RestAssured.given().when().headers(Header).body(b).log().all().when()
				.post(practiceId + "/zipcode").then().log().all().extract().response();
		return response;
	}

	public Response otpDetails(String baseUrl, String b, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseUrl;
		Response response = RestAssured.given().when().headers(Header).body(b).log().all().when()
				.post(practiceId + "/getotpdetails").then().log().all().extract().response();
		return response;
	}
	
	public Response ssoRescheduleAppointment(String baseUrl, String b, Map<String, String> Header, String practiceId,
			String patientId) {
		RestAssured.baseURI = baseUrl;
		Response response = given().when().headers(Header).body(b).log().all().when()
				.post(practiceId + "/rescheduleappointment/" + patientId).then().log().all().extract().response();
		return response;
	}

	public Response scheduleAppointment_PTNEW(String baseUrl, String b, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseUrl;
		Response response = given().when().headers(Header).body(b).log().all().when()
				.post(practiceId + "/scheduleappointment").then().log().all().extract().response();
		return response;
	}

	public Response scheduleAppointmentExisting(String baseUrl, String b, Map<String, String> Header, String practiceId,
			String patientId) {
		RestAssured.baseURI = baseUrl;
		Response response = given().when().headers(Header).body(b).log().all().when()
				.post(practiceId + "/scheduleappointment/" + patientId).then().log().all().extract().response();
		return response;
	}

	
	public Response patientDemographicsWithQueryParameter(String baseUrl, Map<String, String> Header, String practiceId,
			String patientId) {
		RestAssured.baseURI = baseUrl;
		Response response = given().queryParam("language", "EN").log().all().headers(Header).log().all().when()
				.get(practiceId + "/demographics/" + patientId).then().log().all().extract().response();
		return response;
	}
}
