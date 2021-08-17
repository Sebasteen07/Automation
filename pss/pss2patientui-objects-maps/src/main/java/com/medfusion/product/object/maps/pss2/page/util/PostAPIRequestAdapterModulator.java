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
	
	public Response saveAppointmenttype( String practiceid, String b)
			throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/appointmenttype").then().log().all().extract().response();
		return response;
	}
	public Response updateAppointmenttype( String practiceid, String b)
			throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.put(practiceid + "/appointmenttype").then().log().all().extract().response();
		return response;
	}
	public Response saveAppointmenttypeInvalid( String practiceid, String b)
			throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/appointmenttype").then().log().all().extract().response();
		return response;
	}
	public Response updateAppointmenttypeInvalid( String practiceid, String b)
			throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.put(practiceid + "/appointmenttype").then().log().all().extract().response();
		return response;
	}
	public Response getAppointmentTypeById( String practiceid, String b)
			throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.put(practiceid + "/appointmenttype/205755").then().log().all().extract().response();
		return response;
	}
	public Response getAppointmentTypeByBookId( String practiceid, String bookid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/bookappointmenttype/"+bookid).then().log().all().extract().response();
		return response;
	}
	
	public Response getAppointmentTypeByBookIdInvalid( String practiceid, String bookid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/bookappointmenttype/42470").then().log().all().extract().response();
		return response;
	}
	
	public Response reorderAppointmentType( String practiceid, String b)
			throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/appointmenttype/reorder").then().log().all().extract().response();
		return response;
	}
	
	public Response reorderAppointmentTypeWithoutBody( String practiceid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.post(practiceid + "/appointmenttype/reorder").then().log().all().extract().response();
		return response;
	}
	
	public Response deleteBookAppointmentType( String practiceid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/bookappointmenttype/book/4247/apptype/4195").then().log().all().extract().response();
		return response;
	}
	
	public Response saveBookAppointmentType( String practiceid, String b)
			throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/booklocation").then().log().all().extract().response();
		return response;
	}
	
	public Response deleteBookLocation( String practiceid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/booklocation/book/4248/location/4161").then().log().all().extract().response();
		return response;
	}
	
	public Response saveBookLocation( String practiceid, String b)
			throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/booklocation").then().log().all().extract().response();
		return response;
	}
	
	public Response associatedappointmenttype( String practiceid, String b)
			throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/associatedappointmenttype").then().log().all().extract().response();
		return response;
	}
	
	public Response filterappointmenttype( String practiceid, String b)
			throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/filterappointmenttype").then().log().all().extract().response();
		return response;
	}
	
	public Response partnerappointmenttype( String practiceid, String b)
			throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/partnerappointmenttype").then().log().all().extract().response();
		return response;
	}
	
	public Response practiceappointmenttype( String practiceid, String b)
			throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/booklocation").then().log().all().extract().response();
		return response;
	}
	
	public Response practiceSettings( String practiceid, String b)
			throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/practice/settings").then().log().all().extract().response();
		return response;
	}
	
	public Response bookAppointmentTypeGET( String practiceid, String bookid, String apptid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/bookappointmenttype/"+bookid+"/"+apptid).then().log().all().extract().response();
		return response;
	}
	
	public Response bookAppointmentTypeSave( String practiceid, String b)
			throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/bookappointmenttype").then().log().all().extract().response();
		return response;
	}
	
	public Response bookAppointmentTypeUpdate( String practiceid, String b)
			throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.put(practiceid + "/bookappointmenttype").then().log().all().extract().response();
		return response;
	}
	
	public Response bookAppointmentTypeDelete( String practiceid,String bookid, String apptid )
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/bookappointmenttype/book/"+bookid+"/apptype/"+apptid).then().log().all().extract().response();
		return response;
	}
	
	public Response bookLocationGET( String practiceid, String bookid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/booklocation/"+bookid).then().log().all().extract().response();
		return response;
	}
	
	public Response bookLocationSave( String practiceid, String b)
			throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/booklocation").then().log().all().extract().response();
		return response;
	}
	public Response bookLocationDelete( String practiceid,String bookid, String locationid )
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/booklocation/book/"+bookid+"/location/"+locationid).then().log().all().extract().response();
		return response;
	}
	
	public Response associatedBookGET( String practiceid,String bookid, String locationid )
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/booklocation/book/"+bookid+"/location/"+locationid).then().log().all().extract().response();
		return response;
	}
	
	public Response associatedBookUpdate( String practiceid,String bookid, String locationid )
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/booklocation/book/"+bookid+"/location/"+locationid).then().log().all().extract().response();
		return response;
	}
	
	public Response associatedBookSave( String practiceid,String bookid, String locationid )
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/booklocation/book/"+bookid+"/location/"+locationid).then().log().all().extract().response();
		return response;
	}
	
	public Response associatedBookDelete( String practiceid,String bookid, String locationid )
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/booklocation/book/"+bookid+"/location/"+locationid).then().log().all().extract().response();
		return response;
	}
	
	public Response getBookImage( String practiceid,String bookid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/book/"+bookid).then().log().all().extract().response();
		return response;
	}
	
	public Response reorderBook( String practiceid,String b)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().body(b).when()
				.post(practiceid + "/book/reorder").then().log().all().extract().response();
		return response;
	}
	
	public Response getBooksFromDB( String practiceid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/associatedbook").then().log().all().extract().response();
		return response;
	}
	
	public Response saveBook( String practiceid, String b)
			throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/book").then().log().all().extract().response();
		return response;
	}
	
	public Response getBooksFromPartner( String practiceid,String bookid, String locationid )
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/booklocation/book/"+bookid+"/location/"+locationid).then().log().all().extract().response();
		return response;
	}
	
	public Response getBookById( String practiceid,String bookid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/book/"+bookid).then().log().all().extract().response();
		return response;
	}
	
	public Response getBookLevel( String practiceid )
			throws Exception {
		Response response = given().spec(requestSpec).log().all().queryParam("groupType","RESOURCE_LEVEL").when()
				.get(practiceid + "/getbooklevel").then().log().all().extract().response();
		return response;
	}
	
	public Response practiceBook( String practiceid )
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/practicebook").then().log().all().extract().response();
		return response;
	}
	
	public Response partnerBook( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/partnerbook").then().log().all().extract().response();
		return response;
	}
	
	public Response bookSpecialty( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.post(practiceid + "/bookspecialty").then().log().all().extract().response();
		return response;
	}
	
	public Response partnerBook( String practiceid, String bookid, String specialtyid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/bookspecialty/book/"+bookid+"/specialty/"+specialtyid).then().log().all().extract().response();
		return response;
	}
	
	public Response getCancellationReasonFromDB( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/partnerbook").then().log().all().extract().response();
		return response;
	}
	
	public Response saveCancellationReason( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/partnerbook").then().log().all().extract().response();
		return response;
	}
	
	public Response getCancellationReasonById( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/partnerbook").then().log().all().extract().response();
		return response;
	}
	
	public Response deleteCancellationReason( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/partnerbook").then().log().all().extract().response();
		return response;
	}
	
	public Response reorderCancellationReason( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/partnerbook").then().log().all().extract().response();
		return response;
	}
	
	public Response getCancellationReason( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/partnerbook").then().log().all().extract().response();
		return response;
	}
	
	public Response saveSpecialty( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/partnerbook").then().log().all().extract().response();
		return response;
	}
	
	public Response deleteSpecialty( String practiceid )throws Exception {
		Response response = given().spec	(requestSpec).log().all().when()
				.get(practiceid + "/partnerbook").then().log().all().extract().response();
		return response;
	}
	
	public Response getBookBySpecialtyIdAndLevel( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/partnerbook").then().log().all().extract().response();
		return response;
	}
	
	public Response getBookAssociatedToCareTeam( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/partnerbook").then().log().all().extract().response();
		return response;
	}
	
	public Response saveCareTeamBook( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/partnerbook").then().log().all().extract().response();
		return response;
	}
	
	public Response deleteCareTeamBook( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/partnerbook").then().log().all().extract().response();
		return response;
	}
	
	public Response getCareTeamBookFromDB( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/partnerbook").then().log().all().extract().response();
		return response;
	}
	
	
	public Response getCareTeamFromDB( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/partnerbook").then().log().all().extract().response();
		return response;
	}
	
	public Response updateCareTeam( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/partnerbook").then().log().all().extract().response();
		return response;
	}
	
	public Response saveCareTeam( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/partnerbook").then().log().all().extract().response();
		return response;
	}
	
	public Response getCareTeamById( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/partnerbook").then().log().all().extract().response();
		return response;
	}
	public Response deleteCareTeam( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/partnerbook").then().log().all().extract().response();
		return response;
	}
	public Response getCategorysFromDB( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/partnerbook").then().log().all().extract().response();
		return response;
	}
	public Response saveCategory( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/partnerbook").then().log().all().extract().response();
		return response;
	}
	public Response getcategoryById( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/partnerbook").then().log().all().extract().response();
		return response;
	}
	public Response deleteCategory( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/partnerbook").then().log().all().extract().response();
		return response;
	}
	public Response saveCategoryDraft( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/partnerbook").then().log().all().extract().response();
		return response;
	}
	public Response getCategoryJsonFile( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/partnerbook").then().log().all().extract().response();
		return response;
	}
	public Response importCategoryInPractice( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/partnerbook").then().log().all().extract().response();
		return response;
	}
	public Response reorderCategorys( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/partnerbook").then().log().all().extract().response();
		return response;
	}
	public Response validateCategory( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/partnerbook").then().log().all().extract().response();
		return response;
	}

}
