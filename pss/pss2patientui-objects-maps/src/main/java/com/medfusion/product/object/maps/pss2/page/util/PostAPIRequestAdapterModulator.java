// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.util;

import static io.restassured.RestAssured.given;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.hamcrest.Matchers;

import com.medfusion.common.utils.IHGUtil;

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

	public void setupRequestSpecBuilder(String baseurl, Map<String, String> Header) {

		RestAssured.baseURI = baseurl;

		requestSpec = new RequestSpecBuilder().addHeaders(Header).setContentType(ContentType.JSON).build();
		responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	}

	public String openToken(String baseurl, String prcticeid, String b) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().header("Content-Type", "application/json").body(b)
				.post(prcticeid + "/token").then().log().all().assertThat().statusCode(200)
				.body("access_token", Matchers.notNullValue()).extract().response();
		JsonPath jsonPath = response.jsonPath();
		String access_Token = jsonPath.get("access_token");
		return access_Token;
	}

	public Response getAnnouncement(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + path).then().log().all()
				.extract().response();
		return response;
	}

	public Response getAnnouncementByCode(String practiceid, String code) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/announcementbycode/" + code)
				.then().log().all().extract().response();
		return response;
	}

	public Response announcementType(String practiceid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/announcementtype").then()
				.log().all().extract().response();
		return response;
	}

	public Response announcement(String practiceid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/announcement").then().log()
				.all().extract().response();
		return response;
	}

	public Response updateAnnouncement(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().put(practiceid + "/announcement")
				.then().log().all().extract().response();
		return response;
	}

	public Response saveAnnouncement(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/announcement")
				.then().log().all().extract().response();
		return response;
	}

	public Response deleteAnnouncement(String practiceid, int announcementid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/announcement/" + announcementid).then().log().all().extract().response();
		return response;
	}

	public Response validatePractice(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + path).then().log().all()
				.extract().response();
		return response;
	}

	public Response saveAppointmenttype(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/appointmenttype")
				.then().log().all().extract().response();
		return response;
	}

	public Response updateAppointmenttype(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().put(practiceid + "/appointmenttype")
				.then().log().all().extract().response();
		return response;
	}

	public Response getAppointmentTypeById(String practiceid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/appointmenttype/205755").then().log().all().extract().response();
		return response;
	}

	public Response getAppointmentTypeByBookId(String practiceid, String bookid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/bookappointmenttype/" + bookid).then().log().all().extract().response();
		return response;
	}

	public Response reorderAppointmentType(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/appointmenttype/reorder").then().log().all().extract().response();
		return response;
	}

	public Response deleteBookAppointmentType(String practiceid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/bookappointmenttype/book/4247/apptype/4195").then().log().all().extract()
				.response();
		return response;
	}

	public Response saveBookAppointmentType(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/booklocation")
				.then().log().all().extract().response();
		return response;
	}

	public Response deleteBookLocation(String practiceid, String locationid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/booklocation/book/4248/location/" + locationid).then().log().all().extract()
				.response();
		return response;
	}

	public Response saveBookLocation(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/booklocation")
				.then().log().all().extract().response();
		return response;
	}

	public Response associatedappointmenttype(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/associatedappointmenttype").then().log().all().extract().response();
		return response;
	}

	public Response filterappointmenttype(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/filterappointmenttype").then().log().all().extract().response();
		return response;
	}

	public Response partnerappointmenttype(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/partnerappointmenttype").then().log().all().extract().response();
		return response;
	}

	public Response practiceappointmenttype(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/booklocation")
				.then().log().all().extract().response();
		return response;
	}

	public Response practiceSettings(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/practice/settings")
				.then().log().all().extract().response();
		return response;
	}

	public Response bookAppointmentTypeGET(String practiceid, String bookid, String apptid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/bookappointmenttype/" + bookid + "/" + apptid).then().log().all().extract()
				.response();
		return response;
	}

	public Response bookAppointmentTypeSave(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/bookappointmenttype").then().log().all().extract().response();
		return response;
	}

	public Response bookAppointmentTypeUpdate(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.put(practiceid + "/bookappointmenttype").then().log().all().extract().response();
		return response;
	}

	public Response bookAppointmentTypeDelete(String practiceid, String bookid, String apptid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/bookappointmenttype/book/" + bookid + "/apptype/" + apptid).then().log().all()
				.extract().response();
		return response;
	}

	public Response bookLocationGET(String practiceid, String bookid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/booklocation/" + bookid)
				.then().log().all().extract().response();
		return response;
	}

	public Response bookLocationSave(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/booklocation")
				.then().log().all().extract().response();
		return response;
	}

	public Response bookLocationDelete(String practiceid, String bookid, String locationid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/booklocation/book/" + bookid + "/location/" + locationid).then().log().all()
				.extract().response();
		return response;
	}

	public Response associatedBookGET(String practiceid, String bookid, String locationid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/booklocation/book/" + bookid + "/location/" + locationid).then().log().all()
				.extract().response();
		return response;
	}

	public Response associatedBookUpdate(String practiceid, String bookid, String locationid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/booklocation/book/" + bookid + "/location/" + locationid).then().log().all()
				.extract().response();
		return response;
	}

	public Response bookSave(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/book").then().log()
				.all().extract().response();
		return response;
	}

	public Response associatedBookDelete(String practiceid, String bookid, String locationid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/booklocation/book/" + bookid + "/location/" + locationid).then().log().all()
				.extract().response();
		return response;
	}

	public Response getBookImage(String practiceid, String bookid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/book/" + bookid + "/image")
				.then().log().all().extract().response();
		return response;
	}

	public Response reorderBook(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).log().all().body(b).when().post(practiceid + "/book/reorder")
				.then().log().all().extract().response();
		return response;
	}

	public Response getBooksFromDB(String practiceid, String endpointpath) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + endpointpath).then().log()
				.all().extract().response();
		return response;
	}

	public Response saveBook(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/book").then().log()
				.all().extract().response();
		return response;
	}

	public Response getBooksFromPartner(String practiceid, String endpointpath) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + endpointpath).then().log()
				.all().extract().response();
		return response;
	}

	public Response getBookById(String practiceid, String bookid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/book/" + bookid).then()
				.log().all().extract().response();
		return response;
	}

	public Response getBookLevel(String practiceid, String grouptype) throws Exception {
		Response response = given().spec(requestSpec).log().all().queryParam("groupType", grouptype).when()
				.get(practiceid + "/getbooklevel").then().log().all().extract().response();
		return response;
	}

	public Response practiceBook(String practiceid, String endpointpath) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + endpointpath).then().log()
				.all().extract().response();
		return response;
	}

	public Response partnerBook(String practiceid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/partnerbook").then().log()
				.all().extract().response();
		return response;
	}

	public Response bookSpecialty(String practiceid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().post(practiceid + "/bookspecialty").then()
				.log().all().extract().response();
		return response;
	}

	public Response partnerBook(String practiceid, String bookid, String specialtyid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/bookspecialty/book/" + bookid + "/specialty/" + specialtyid).then().log().all()
				.extract().response();
		return response;
	}

	public Response getCancellationReasonFromDB(String practiceid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/associatedcancellationreason").then().log().all().extract().response();
		return response;
	}

	public Response saveCancellationReason(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/cancellationreason").then().log().all().extract().response();
		return response;
	}

	public Response getCancellationReasonById(String practiceid, int cancelreasonid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/cancellationreason/" + cancelreasonid).then().log().all().extract().response();
		return response;
	}

	public Response deleteCancellationReason(String practiceid, int cancelreasonid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/cancellationreason/" + cancelreasonid).then().log().all().extract().response();
		return response;
	}

	public Response reorderCancellationReason(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/cancellationreason/reorder").then().log().all().extract().response();
		return response;
	}

	public Response getPracticeCancellationReason(String practiceid, String endpointpath) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + endpointpath).then().log()
				.all().extract().response();
		return response;
	}

	public Response saveSpecialty(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/bookspecialty")
				.then().log().all().extract().response();
		return response;
	}

	public Response deleteSpecialty(String practiceid, String bookid, String specialtyid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/bookspecialty/book/" + bookid + "/specialty/" + specialtyid).then().log().all()
				.extract().response();
		return response;
	}

	public Response getBookBySpecialtyIdAndLevel(String practiceid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/partnerbook").then().log()
				.all().extract().response();
		return response;
	}

	public Response getBookAssociatedToCareTeam(String practiceid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/partnerbook").then().log()
				.all().extract().response();
		return response;
	}

	public Response deleteCareTeamBook(String practiceid, String careteamid, String bookid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/careteambook/" + careteamid + "/book/" + bookid).then().log().all().extract()
				.response();
		return response;
	}

	public Response getCareTeamBookFromDB(String practiceid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/partnerbook").then().log()
				.all().extract().response();
		return response;
	}

	public Response getCareTeamFromDB(String practiceid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/partnerbook").then().log()
				.all().extract().response();
		return response;
	}

	public Response updateCareTeam(String practiceid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/partnerbook").then().log()
				.all().extract().response();
		return response;
	}

	public Response saveCareTeam(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/careteam").then()
				.log().all().extract().response();
		return response;
	}

	public Response saveCareTeamWithoutBody(String practiceid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().post(practiceid + "/careteam").then().log()
				.all().extract().response();
		return response;
	}

	public Response getAssociatedCareteam(String practiceid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/associatedcareteam").then()
				.log().all().extract().response();
		return response;
	}

	public Response deleteCareTeam(String practiceid, String careteamid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().delete(practiceid + "/careteam/" + careteamid)
				.then().log().all().extract().response();
		return response;
	}

	public Response getCareTeamById(String practiceid, String careteamid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/careteam/" + careteamid)
				.then().log().all().extract().response();
		return response;
	}

	public Response getCategorysFromDB(String practiceid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/partnerbook").then().log()
				.all().extract().response();
		return response;
	}

	public Response saveCategory(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/category").then()
				.log().all().extract().response();
		return response;
	}

	public Response getcategoryById(String practiceid, String categoryid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/categoryspecialty/" + categoryid).then().log().all().extract().response();
		return response;
	}

	public Response deleteCategory(String practiceid, String categoryid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().delete(practiceid + "/category/" + categoryid)
				.then().log().all().extract().response();
		return response;
	}

	public Response saveCategoryDraft(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/category/draft")
				.then().log().all().extract().response();
		return response;
	}

	public Response associatedCategory(String practiceid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/associatedcategory").then()
				.log().all().extract().response();
		return response;
	}

	public Response exportCategory(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/category/export")
				.then().log().all().extract().response();
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

	public Response reorderCategorys(String practiceid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/partnerbook").then().log()
				.all().extract().response();
		return response;
	}

	public Response validateCategory(String practiceid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/partnerbook").then().log()
				.all().extract().response();
		return response;
	}

	public Response getBookBySpecialtyIdAndLevel(String practiceid, String specialtyid, String level) throws Exception {
		Response response = given().spec(requestSpec).queryParam("specialtyid", specialtyid).queryParam("level", level)
				.log().all().when().get(practiceid + "/bookcareteam").then().log().all().extract().response();
		return response;
	}

	public Response getBookAssociatedToCareTeam(String practiceid, String careteamid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/bookcareteam/" + careteamid)
				.then().log().all().extract().response();
		return response;
	}

	public Response saveCareTeamBook(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/careteambook")
				.then().log().all().extract().response();
		return response;
	}

	public Response getCareTeamByBookId(String practiceid, String bookid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/careteambook/book/" + bookid).then().log().all().extract().response();
		return response;
	}

	public Response categorySpecialtyPost(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/categoryspecialty")
				.then().log().all().extract().response();
		return response;
	}

	public Response categorySpecialtyBook(String practiceid, String bookId, String pathEndPoint) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + pathEndPoint + bookId).then()
				.log().all().extract().response();
		return response;
	}

	public Response deleteCatSpeciality(String practiceid, String categoryid, String locationId) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/categoryspecialty/category/" + categoryid + "/specialty/" + locationId).then()
				.log().all().extract().response();
		return response;
	}

	public Response resourceConfigGet(String practiceid, String endpointpath) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + endpointpath).then().log()
				.all().extract().response();
		return response;
	}

	public Response resourceConfigPost(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/configuration")
				.then().log().all().extract().response();
		return response;
	}

	public Response resourceConfigSavePost(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/configuration/save").then().log().all().extract().response();
		return response;
	}

	public Response resourceConfigRuleGet(String practiceid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/configurations/rules")
				.then().log().all().extract().response();
		return response;
	}

	public Response resourceConfigRulePost(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/configurations/rules").then().log().all().extract().response();
		return response;
	}

	public Response resourceConfigRulePUT(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.put(practiceid + "/configurations/rules").then().log().all().extract().response();
		return response;
	}

	public Response deleteRuleById(String practiceid, String ruleId) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/configurations/rules/" + ruleId).then().log().all().extract().response();
		return response;
	}

	public Response versionGet(String practiceid, String endpointpath) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + endpointpath).then().log()
				.all().extract().response();
		return response;
	}

	public Response gendermapGet(String practiceid, String endpointpath) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + endpointpath).then().log()
				.all().extract().response();
		return response;
	}

	public Response gendermapPost(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/gendermap").then()
				.log().all().extract().response();
		return response;
	}

	public Response linkGet(String practiceid, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + endPointPath).then().log()
				.all().extract().response();
		return response;
	}

	public Response savelinkPost(String practiceid, String b, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + endPointPath).then()
				.log().all().extract().response();
		return response;
	}

	public Response deleteLink(String practiceid, String linkId) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().delete(practiceid + "/link/" + linkId).then()
				.log().all().extract().response();
		return response;
	}

	public Response locationAssociated(String practiceid, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + endPointPath).then().log()
				.all().extract().response();
		return response;
	}

	public Response locationPut(String practiceid, String b, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().put(practiceid + endPointPath).then()
				.log().all().extract().response();
		return response;
	}

	public Response locationSavePost(String practiceid, String b, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + endPointPath).then()
				.log().all().extract().response();
		return response;
	}

	public Response locationById(String practiceid, String locationId) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/location/" + locationId)
				.then().log().all().extract().response();
		return response;
	}

	public Response locationDeleteById(String practiceid, String locationId) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().delete(practiceid + "/location/" + locationId)
				.then().log().all().extract().response();
		return response;
	}

	public Response locationreorderPost(String practiceid, String b, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + endPointPath).then()
				.log().all().extract().response();
		return response;
	}

	public Response locationBySearch(String practiceid, String location) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/location/search/" + location).then().log().all().extract().response();
		return response;
	}

	public Response locationByExtId(String practiceid, String locationExtId) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/locationbyexternalid/" + locationExtId).then().log().all().extract().response();
		return response;
	}

	public Response locationByPartner(String practiceid, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + endPointPath).then().log()
				.all().extract().response();
		return response;
	}

	public Response locationByPractice(String practiceid, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + endPointPath).then().log()
				.all().extract().response();
		return response;
	}

	public Response associatedlockout(String practiceid, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + endPointPath).then().log()
				.all().extract().response();
		return response;
	}

	public Response lockoutPost(String practiceid, String b, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + endPointPath).then()
				.log().all().extract().response();
		return response;
	}

	public Response lockoutPut(String practiceid, String b, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().put(practiceid + endPointPath).then()
				.log().all().extract().response();
		return response;
	}

	public Response deleteLockoutById(String practiceid, String id) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().delete(practiceid + "/lockout/" + id).then()
				.log().all().extract().response();
		return response;
	}
	
	public Response deleteLockoutById(String practiceid, int id) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().delete(practiceid + "/lockout/" + id).then()
				.log().all().extract().response();
		return response;
	}

	public Response practicelockout(String practiceid, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + endPointPath).then().log()
				.all().extract().response();
		return response;
	}

	public Response loginlessSavePost(String practiceid, String b, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + endPointPath).then()
				.log().all().extract().response();
		return response;
	}

	public Response loginlessGet(String practiceid, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + endPointPath).then().log()
				.all().extract().response();
		return response;
	}

	public Response loginlessGuidGet(String practiceid, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + endPointPath).then().log()
				.all().extract().response();
		return response;
	}

	public Response partnerConfigGet(String practiceid, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + endPointPath).then().log()
				.all().extract().response();
		return response;
	}

	public Response patientInfoGet(String practiceid, String flowType) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/patientinfo/" + flowType)
				.then().log().all().extract().response();
		return response;
	}

	public Response patientInfoWithoutFlowTypeGet(String practiceid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/patientinfo/").then().log()
				.all().extract().response();
		return response;
	}

	public Response patientInfoPost(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/patientinfo")
				.then().log().all().extract().response();
		return response;
	}

	public Response medfusionpracticeTimeZone(String practiceid, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + endPointPath).then().log()
				.all().extract().response();
		return response;
	}

	public Response practiceInfo(String practiceid, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + endPointPath).then().log()
				.all().extract().response();
		return response;
	}

	public Response practicePut(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().put(practiceid + "/practice").then()
				.log().all().extract().response();
		return response;
	}

	public Response practicePost(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/practice").then()
				.log().all().extract().response();
		return response;
	}

	public Response preRequisiteAppointmenttypes(String practiceid, String id, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/prerequisiteappointmenttypes/" + id).then().log().all().extract().response();
		return response;
	}

	public Response preRequisiteAppById(String practiceid, String id) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/prerequisiteappointmenttypes/" + id).then().log().all().extract().response();
		return response;
	}

	public Response preRequisiteAppDeleteById(String practiceid, String id) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/prerequisiteappointmenttype/" + id).then().log().all().extract().response();
		return response;
	}

	public Response preRequisiteAppGet(String practiceid, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + endPointPath).then().log()
				.all().extract().response();
		return response;
	}

	public Response locationSearch(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/locationsearch")
				.then().log().all().extract().response();
		return response;
	}

	public Response bookSearch(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/booksearch").then()
				.log().all().extract().response();
		return response;
	}

	public Response anonymousGet(String practiceid, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + endPointPath).then().log()
				.all().extract().response();
		return response;
	}

	public Response anonymousPost(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/anonymous").then()
				.log().all().extract().response();
		return response;
	}

	public Response ssoGet(String practiceid, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + endPointPath).then().log()
				.all().extract().response();
		return response;
	}

	public Response ssoPost(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/sso").then().log()
				.all().extract().response();
		return response;
	}

	public Response ssoGenGuid(String practiceid, String Code) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/sso/guid/" + Code).then()
				.log().all().extract().response();
		return response;
	}

	public Response ssoGenWithoutGuid(String practiceid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/sso/guid/").then().log()
				.all().extract().response();
		return response;
	}

	public Response ssoDeleteGuid(String practiceid, String Code) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().delete(practiceid + "/sso/guid/" + Code).then()
				.log().all().extract().response();
		return response;
	}

	public Response ssoDeleteGuidWithoutCode(String practiceid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().delete(practiceid + "/sso/guid/").then().log()
				.all().extract().response();
		return response;
	}

	public Response resellerLogo(String practiceid, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + endPointPath).then().log()
				.all().extract().response();
		return response;
	}

	public Response reseller(String practiceid, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + endPointPath).then().log()
				.all().extract().response();
		return response;
	}

	public Response resellerPost(String practiceid, String b, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + endPointPath).then()
				.log().all().extract().response();
		return response;
	}

	public Response resellerLogoDelete(String practiceid, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().delete(practiceid + endPointPath).then().log()
				.all().extract().response();
		return response;
	}

	public Response getSessionConfiguration(String practiceid, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + endPointPath).then().log()
				.all().extract().response();
		return response;
	}

	public Response getassociatedspecialty(String practiceid, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + endPointPath).then().log()
				.all().extract().response();
		return response;
	}

	public Response getassociatedPracticespecialty(String practiceid, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + endPointPath).then().log()
				.all().extract().response();
		return response;
	}

	public Response specialityPut(String practiceid, String b, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().put(practiceid + endPointPath).then()
				.log().all().extract().response();
		return response;
	}

	public Response specialityPost(String practiceid, String b, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + endPointPath).then()
				.log().all().extract().response();
		return response;
	}

	public Response getassociatedspecialtyById(String practiceid, String id) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/specialty/" + id).then()
				.log().all().extract().response();
		return response;
	}

	public Response getassociatedspecialtyByInvalidId(String practiceid, String id) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/specialty/" + id).then()
				.log().all().extract().response();
		return response;
	}

	public Response specialityReorderPost(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/specialty/reorder")
				.then().log().all().extract().response();
		return response;
	}

	public Response specialityDeleteByid(String practiceid, String id) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().delete(practiceid + "/specialty/" + id).then()
				.log().all().extract().response();
		return response;
	}

	public Response associatedInsuranceCarrier(String practiceid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/associatedinsurancecarrier")
				.then().log().all().extract().response();
		return response;
	}

	public Response insuranceCarrierById(String practiceid, String id) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/insurancecarrier/" + id)
				.then().log().all().extract().response();
		return response;
	}

	public Response InsurancePractice(String practiceid, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + endPointPath).then().log()
				.all().extract().response();
		return response;
	}

	public Response insuranceDeleteById(String practiceid, String id) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/insurancecarrier/" + id)
				.then().log().all().extract().response();
		return response;
	}

	public Response insuranceReorder(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/insurancecarrier/reorder").then().log().all().extract().response();
		return response;
	}

	public Response insuranceSave(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/insurancecarrier")
				.then().log().all().extract().response();
		return response;
	}

	public Response customDataPost(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/customdata").then()
				.log().all().extract().response();
		return response;
	}

	public Response customDataGet(String practiceid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/customdata/LG_LOG").then()
				.log().all().extract().response();
		return response;
	}

	public Response customDataDelete(String practiceid, String id) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/customdata/" + id).then()
				.log().all().extract().response();
		return response;
	}

	public Response partnerCustommetaData(String practiceid, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + endPointPath).then().log()
				.all().extract().response();
		return response;
	}

	public Response specialitySave(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/specialty")
				.then().log().all().extract().response();
		return response;
	}

	
	public Response appointmenttypeConfgWithBookOff(String practiceid, String b,String appID) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/appointmenttype/"+appID+"/config")
		.then().log().all().extract().response();
		return response;
		}

	

	public Response getSessionTimeout(String practiceid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/getsessionconfiguration").then()
				.log().all().extract().response();
		return response;
	}
}
