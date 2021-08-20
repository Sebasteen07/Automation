// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.util;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.apache.tools.ant.util.FileUtils;
import org.hamcrest.Matchers;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.rest.RestUtils;
import com.medfusion.common.utils.IHGUtil;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

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
	
	public Response getAnnouncementInvalid( String practiceid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/announcementt").then().log().all().extract().response();
		return response;
	}
	
	public Response getAnnouncementByCode( String practiceid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/announcementbycode/AG").then().log().all().extract().response();
		return response;
	}
	
	public Response getAnnouncementByInvalidCode( String practiceid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/announcementbycode/ZZZ").then().log().all().extract().response();
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
	
	public Response updateAnnouncementWithoutBody( String practiceid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.put(practiceid + "/announcement").then().log().all().extract().response();
		return response;
	}
	
	public Response saveAnnouncement( String practiceid, String b)
			throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/announcement").then().log().all().extract().response();
		return response;
	}
	
	public Response saveAnnouncementWithoutBody( String practiceid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.post(practiceid + "/announcement").then().log().all().extract().response();
		return response;
	}
	
	public Response deleteAnnouncement( String practiceid, int announcementid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/announcement/"+announcementid).then().log().all().extract().response();
		return response;
	}
	
	public Response deleteAnnouncementInvalidAnnId( String practiceid, int announcementid)
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
	
	public Response validatePracticeInvalid( String practiceid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/validatepracticee").then().extract().response();
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
	
	public Response saveBookAppointmentTypeInvalid( String practiceid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.post(practiceid + "/booklocation").then().log().all().extract().response();
		return response;
	}
	
	public Response deleteBookLocation( String practiceid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/booklocation/book/4248/location/4161").then().log().all().extract().response();
		return response;
	}
	
	public Response deleteBookLocationInvalid( String practiceid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/booklocation/book/4248/location/40610").then().log().all().extract().response();
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
	
	public Response bookAppointmentTypeGETInvalidBookId( String practiceid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/bookappointmenttype/200001/200033").then().log().all().extract().response();
		return response;
	}
	
	public Response bookAppointmentTypeGETInvalidApptId( String practiceid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/bookappointmenttype/206501/200033").then().log().all().extract().response();
		return response;
	}
	
	public Response bookAppointmentTypeSave( String practiceid, String b)
			throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/bookappointmenttype").then().log().all().extract().response();
		return response;
	}
	
	public Response bookAppointmentTypeSaveInvalid( String practiceid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
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
	
	public Response bookAppointmentTypeDeleteInvalid( String practiceid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/bookappointmenttype/book/"+206501+"/apptype/"+200033).then().log().all().extract().response();
		return response;
	}
	
	public Response bookLocationGET( String practiceid, String bookid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/booklocation/"+bookid).then().log().all().extract().response();
		return response;
	}
	
	public Response bookLocationGETInvalid( String practiceid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/booklocation/12345").then().log().all().extract().response();
		return response;
	}
	
	public Response bookLocationSave( String practiceid, String b)
			throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/booklocation").then().log().all().extract().response();
		return response;
	}
	
	public Response bookLocationSaveWithoutBody( String practiceid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.post(practiceid + "/booklocation").then().log().all().extract().response();
		return response;
	}
	
	public Response bookLocationDelete( String practiceid,String bookid, String locationid )
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/booklocation/book/"+bookid+"/location/"+locationid).then().log().all().extract().response();
		return response;
	}
	
	public Response bookLocationDeleteInvalid( String practiceid,String bookid )
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/booklocation/book/"+bookid+"/location/"+12345).then().log().all().extract().response();
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
	
	public Response bookSave( String practiceid,String b )
			throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/book").then().log().all().extract().response();
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
				.get(practiceid + "/book/" +bookid+ "/image").then().log().all().extract().response();
		return response;
	}
	
	public Response getBookImageInvalid( String practiceid,String bookid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/book/" +bookid+ "/image").then().log().all().extract().response();
		return response;
	}
	
	public Response reorderBook( String practiceid,String b)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().body(b).when()
				.post(practiceid + "/book/reorder").then().log().all().extract().response();
		return response;
	}
	
	public Response reorderBookWithoutBody( String practiceid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.post(practiceid + "/book/reorder").then().log().all().extract().response();
		return response;
	}
	
	public Response getBooksFromDB( String practiceid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/associatedbook").then().log().all().extract().response();
		return response;
	}
	
	public Response getBooksFromDBInvalid( String practiceid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/associatedbookk").then().log().all().assertThat().statusLine("HTTP/1.1 404 ").extract().response();
		return response;
	}
	
	public Response saveBook( String practiceid, String b)
			throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/book").then().log().all().extract().response();
		return response;
	}
	
	public Response saveBookWithoutBody( String practiceid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.post(practiceid + "/book").then().log().all().extract().response();
		return response;
	}
	
	public Response getBooksFromPartner( String practiceid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/partnerbook").then().log().all().extract().response();
		return response;
	}
	
	public Response getBooksFromPartnerInvalid( String practiceid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/partnerbookkkk").then().log().all().assertThat().statusLine("HTTP/1.1 404 ").extract().response();
		return response;
	}
	
	public Response getBookById( String practiceid,String bookid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/book/"+bookid).then().log().all().extract().response();
		return response;
	}
	
	public Response getBookByIdInvalid( String practiceid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/book/"+12345).then().log().all().extract().response();
		return response;
	}
	
	public Response getBookLevel( String practiceid )
			throws Exception {
		Response response = given().spec(requestSpec).log().all().queryParam("groupType","RESOURCE_LEVEL").when()
				.get(practiceid + "/getbooklevel").then().log().all().extract().response();
		return response;
	}
	
	public Response getBookLevelInvalid( String practiceid )
			throws Exception {
		Response response = given().spec(requestSpec).log().all().queryParam("groupType","ABCD").when()
				.get(practiceid + "/getbooklevel").then().log().all().assertThat().statusLine("HTTP/1.1 204 ").extract().response();
		return response;
	}
	
	public Response getCancelLevel( String practiceid )
			throws Exception {
		Response response = given().spec(requestSpec).log().all().queryParam("groupType","CANCEL_REASON").when()
				.get(practiceid + "/getbooklevel").then().log().all().extract().response();
		return response;
	}
	
	public Response practiceBook( String practiceid )
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/practicebook").then().log().all().extract().response();
		return response;
	}
	
	public Response practiceBookInvalid( String practiceid )
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/practicebookkkk").then().log().all().assertThat().statusLine("HTTP/1.1 404 ").extract().response();
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
				.get(practiceid + "/associatedcancellationreason").then().log().all().extract().response();
		return response;
	}
	
	public Response saveCancellationReason( String practiceid, String b)throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/cancellationreason").then().log().all().extract().response();
		return response;
	}
	
	public Response saveCancellationReasonInvalid( String practiceid)throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.post(practiceid + "/cancellationreason").then().log().all().extract().response();
		return response;
	}
	
	public Response getCancellationReasonById( String practiceid, String cancelreasonid)throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/cancellationreason/"+cancelreasonid).then().log().all().extract().response();
		return response;
	}
	
	public Response getCancellationReasonByIdInvalid( String practiceid)throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/cancellationreason/"+1222).then().log().all().extract().response();
		return response;
	}
	
	public Response deleteCancellationReason( String practiceid, String cancelreasonid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/cancellationreason/"+cancelreasonid).then().log().all().extract().response();
		return response;
	}
	
	public Response deleteCancellationReasonInvalid( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/cancellationreason/"+12345).then().log().all().extract().response();
		return response;
	}
	
	public Response reorderCancellationReason( String practiceid , String b)throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/cancellationreason/reorder").then().log().all().extract().response();
		return response;
	}
	
	public Response reorderCancellationReasonInvalid( String practiceid)throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.post(practiceid + "/cancellationreason/reorder").then().log().all().extract().response();
		return response;
	}
	
	public Response getPracticeCancellationReason( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/practicecancellationreason").then().log().all().extract().response();
		return response;
	}
	
	public Response getPracticeCancellationReasonInvalid( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/practicecancellationreasonn").then().log().all().assertThat().statusLine("HTTP/1.1 404 ").extract().response();
		return response;
	}
	
	public Response saveSpecialty( String practiceid , String b)throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/bookspecialty").then().log().all().extract().response();
		return response;
	}
	
	public Response deleteSpecialty( String practiceid,String bookid, String specialtyid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/bookspecialty/book/"+bookid+"/specialty/"+specialtyid
						).then().log().all().extract().response();
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

	public Response deleteCareTeamBook( String practiceid, String careteamid, String bookid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/careteambook/"+careteamid+"/book/"+bookid).then().log().all().extract().response();
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
	
	public Response saveCareTeam( String practiceid, String b)throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/careteam").then().log().all().extract().response();
		return response;
	}
	
	public Response saveCareTeamWithoutBody( String practiceid)throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.post(practiceid + "/careteam").then().log().all().extract().response();
		return response;
	}
	
	public Response getAssociatedCareteam( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/associatedcareteam").then().log().all().extract().response();
		return response;
	}
	public Response deleteCareTeam( String practiceid, String careteamid)throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/careteam/"+careteamid).then().log().all().extract().response();
		return response;
	}
	
	public Response getCareTeamById( String practiceid, String careteamid)throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/careteam/"+careteamid).then().log().all().extract().response();
		return response;
	}
	
	public Response getCategorysFromDB( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/partnerbook").then().log().all().extract().response();
		return response;
	}
	public Response saveCategory( String practiceid, String b )throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/category").then().log().all().extract().response();
		return response;
	}
	
	public Response getcategoryById( String practiceid, String categoryid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/categoryspecialty/"+categoryid).then().log().all().extract().response();
		return response;
	}
	
	public Response deleteCategory( String practiceid, String categoryid)throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/category/"+categoryid).then().log().all().extract().response();
		return response;
	}
	public Response saveCategoryDraft( String practiceid, String b)throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/category/draft").then().log().all().extract().response();
		return response;
	}
	public Response associatedCategory( String practiceid )throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/associatedcategory").then().log().all().extract().response();
		return response;
	}
	public Response exportCategory( String practiceid, String b)throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/category/export").then().log().all().extract().response();
		return response;
	}
	
	public Response importCategory(String practiceid, Map<String, String> Header) throws Exception {

		String file = "./src/test/resources/data-driven/Force.json";

		String jsonFile = "{\"EN\":\"SuperCategory\",\"ES\":\"SuperCategory_Es\"}";

		RestAssured.baseURI = "https://dev3-pss-adminportal-ui.dev.medfusion.net/pss-adapter-modulator/24702/category/import";
		Response response = given().urlEncodingEnabled(true).log().all().contentType("multipart/form-data")

				.headers(Header).formParam("name", "SuperCategory").formParam("displayNames", jsonFile)
				.formParam("type", "CG_APPOINTMENT_TYPE").multiPart("categoryFile", new File(file)).when().post().then()
				.log().all().extract().response();
		return response;
	}
	
	public static void writeFile(String xmlFilePath, String xml) throws IOException {
		FileWriter out = new FileWriter(xmlFilePath);
		out.write(xml);
		if (out != null) {
			out.close();
		}
		IHGUtil.PrintMethodName();
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
	
	public Response getBookBySpecialtyIdAndLevel( String practiceid , String specialtyid)throws Exception {
		Response response = given().spec(requestSpec).queryParam("specialtyid", specialtyid).queryParam("level", "RS_L1").log().all().when()
				.get(practiceid + "/bookcareteam").then().log().all().extract().response();
		return response;
	}
	
	public Response getBookBySpecialtyIdAndLevelInvalid( String practiceid , String specialtyid)throws Exception {
		Response response = given().spec(requestSpec).queryParam("specialtyid", specialtyid).queryParam("level", "RS_L10").log().all().when()
				.get(practiceid + "/bookcareteam").then().log().all().extract().response();
		return response;
	}
	
	public Response getBookAssociatedToCareTeam( String practiceid , String careteamid)throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/bookcareteam/"+careteamid).then().log().all().extract().response();
		return response;	
	}

	public Response saveCareTeamBook( String practiceid , String b)throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/careteambook").then().log().all().extract().response();
		return response;	
	}
	
	public Response getCareTeamByBookId( String practiceid , String bookid)throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/careteambook/book/"+bookid).then().log().all().extract().response();
		return response;	
	}
}
