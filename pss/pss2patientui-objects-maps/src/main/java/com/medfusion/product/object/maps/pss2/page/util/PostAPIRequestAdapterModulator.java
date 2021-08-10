// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.util;

import static io.restassured.RestAssured.given;

import java.util.Map;

import org.hamcrest.Matchers;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PostAPIRequestAdapterModulator {
	
	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec;

	public void setupRequestSpecBuilder(String baseurl,Map<String, String> Header) {

		RestAssured.baseURI = baseurl;

		requestSpec = new RequestSpecBuilder().addHeaders(Header).setContentType(ContentType.JSON).build();
		responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	}
	
	public String openToken(String baseurl, String prcticeid, String b) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().header("Content-Type", "application/json").body(b).post(prcticeid+"/token").then().log().all()
				.assertThat().statusCode(200).body("access_token", Matchers.notNullValue()).extract().response();
		JsonPath jsonPath = response.jsonPath();
		String access_Token = jsonPath.get("access_token");
		return access_Token;
	}
	
	public Response getAnnouncement( String practiceid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/announcement").then().log().all().extract().response();
		return response;
	}
	
	public Response getAnnouncementByCode( String practiceid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/announcementbycode/AG").then().log().all().extract().response();
		return response;
	}
	
	public Response announcementType( String practiceid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/announcementtype").then().log().all().extract().response();
		return response;
	}
	
	public Response announcement( String practiceid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/announcement").then().log().all().extract().response();
		return response;
	}
	
	public Response updateAnnouncement( String practiceid, String b)
			throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.put(practiceid + "/announcement").then().log().all().extract().response();
		return response;
	}
	
	public Response saveAnnouncement( String practiceid, String b)
			throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/announcement").then().log().all().extract().response();
		return response;
	}
	public Response deleteAnnouncement( String practiceid, int announcementid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/announcement/"+announcementid).then().log().all().extract().response();
		return response;
	}

	public Response validatePractice( String practiceid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/validatepractice").then().log().all().extract().response();
		return response;
	}
}
