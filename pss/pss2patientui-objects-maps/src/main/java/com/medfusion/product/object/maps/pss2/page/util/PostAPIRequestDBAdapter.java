
// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.util;

import java.util.Map;
import static io.restassured.RestAssured.given;
import org.hamcrest.Matchers;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
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
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/announcement")
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


	public Response saveAppointmenttype(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/appointmenttype")
				.then().log().all().extract().response();
		return response;
	}

	public Response updateAppointmenttype(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/appointmenttype")
				.then().log().all().extract().response();
		return response;
	}

	public Response getAppointmentTypeById(String practiceid, String apptid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/appointmentbyid/" + apptid)
				.then().log().all().extract().response();
		return response;
	}
	
	public Response getAppointmentTypeByExtApptId(String practiceid, String apptid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/appointment/"+apptid).then().log().all().extract().response();
		return response;
	}
	
	public Response saveAppointment(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/appointment").then().log().all().extract().response();
		return response;
	}
	
	public Response updateAppointment(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid + "/updateappointment").then().log().all().extract().response();
		return response;
	}
	
	public Response getAppointmentsForPractice(String practiceid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/appointments")
				.then().log().all().extract().response();
		return response;
	}

	public Response getAppointmentByExtApptIdForPractice(String practiceid, String extapptid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/appointment/"+extapptid )
				.then().extract().response();
		return response;
	}
	
	public Response getUpcomingAppointmentsByPatientIdForPractice(String practiceid, String patientid, String fromdate) throws Exception {
		Response response = given().spec(requestSpec).queryParam("fromDate", fromdate).log().all().when()
				.get(practiceid + "/patientappointments/" + patientid).then().log().all().extract().response();
		return response;
	}

	public Response patientappointmentsbyrange(String practiceid, String patientid, String startdate, String enddate)
			throws Exception {
		Response response = given().spec(requestSpec).queryParam("startDate", startdate).queryParam("endDate",enddate)
				.log().all().when().get(practiceid + "/patientappointmentsbyrange/"+patientid).then().log().all().extract().response();
		return response;
	}

	public Response getAppointmentTypeConfig(String practiceid,String appttypeid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/appointmenttypeconfig/"+appttypeid).then().log().all().extract()
				.response();
		return response;
	}

	public Response getAppointmentTypesByLocationForNoBook(String practiceid, String locationid) throws Exception {
		Response response = given().spec(requestSpec).header("language", "es").log().all().when().get(practiceid + "/appointmenttype/location/"+locationid)
				.then().log().all().extract().response();
		return response;
	}

	public Response getAppointmentTypesForPractice(String practiceid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/apptype").then().log().all().extract()
				.response();
		return response;
	}

	public Response getAppTypeById(String practiceid, String apptypeid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/apptype/" + apptypeid)
				.then().log().all().extract().response();
		return response;
	}

	public Response getAppointmentTypesByBook(String practiceid, String bookid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/apptype/book/" + bookid)
				.then().log().all().extract().response();
		return response;
	}

	public Response getAppointmentTypesByLocation(String practiceid, String locationid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/apptype/location/"+locationid).then().log().all().extract().response();
		return response;
	}

	public Response getAppointmentTypesByLocationAndBooks(String practiceid, String bookid, String locationid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/apptype/location/"+locationid+"/book/"+bookid).then().log().all().extract().response();
		return response;
	}

	public Response getAppointmentTypesBySpecialty(String practiceid, String specialtyid) throws Exception {
		Response response = given().spec(requestSpec).header("language", "es").log().all().when()
				.get(practiceid + "/apptype/specialty/" + specialtyid).then().log().all().extract().response();
		return response;
	}

	public Response getAppointmentTypesBySpecialtyAndBook(String practiceid, String specialty, String bookid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/apptype/specialty/" + specialty + "/book/" + bookid).then().log().all().extract()
				.response();
		return response;
	}

	public Response getAppointmentTypesBySpecialtyAndBookAndLocation(String practiceid, String specialty, String bookid, String locationid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/apptype/specialty/"+specialty+"/book/"+bookid+"/location/"+locationid).then().log().all().extract()
				.response();
		return response;
	}

	public Response getAppointmentTypesBySpecialtyAndLocation(String practiceid, String specialtyid, String locationid)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/apptype/specialty/" + specialtyid + "/location/" + locationid).then().log().all()
				.extract().response();
		return response;
	}

	public Response getApptypeExtById(String practiceid, String extappttypeid) throws Exception {
		Response response = given().spec(requestSpec).header("language", "es").log().all().when()
				.get(practiceid + "/apptypebyextid/"+extappttypeid).then().log().all().extract().response();
		return response;
	}

	public Response getAppointmentTypesWithLanguageForPractice(String practiceid, String languagecode) throws Exception {
		Response response = given().spec(requestSpec).header("language", languagecode).log().all().when()
				.get(practiceid+"/apptypes").then().log().all()
				.extract().response();
		return response;
	}

	public Response getApptTypeLocConfigForApptTypeAndLoc(String practiceid, String apptype, String locationid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/apptypelocation/" + apptype+"/"+locationid)
				.then().log().all().extract().response();
		return response;
	}

	public Response getAppAuthUserByExtUserId(String practiceid, String extuserid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/authappuser/"+extuserid)
				.then().log().all().extract().response();
		return response;
	}
	
	public Response getAppAuthUserByUserId(String practiceid, String id) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/authappuserbyid/"+id)
				.then().log().all().extract().response();
		return response;
	}
	
	public Response getAppTypesAssociatedToBook (String practiceid, String bookid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/bookapptype/"+bookid)
				.then().log().all().extract().response();
		return response;
	}
	
	public Response getBookAppTypesAssociatedToBook(String practiceid, String bookid, String apptypeid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/bookapptype/"+bookid+"/"+apptypeid)
				.then().log().all().extract().response();
		return response;
	}
	
	public Response getAssociatedBookAppTypesByBookId(String practiceid, String bookid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/bookapptype/apptype/"+bookid)
				.then().log().all().extract().response();
		return response;
	}
	
	public Response getAssociatedBookAppTypesByAppTypeId(String practiceid, String apptype) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/bookapptype/book/"+apptype)
				.then().log().all().extract().response();
		return response;
	}
	public Response getLocationsAssociatedToBookAndAppointmentType(String practiceid, String bookid, String apptype) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/bookapptypelocation/"+bookid+"/"+apptype)
				.then().log().all().extract().response();
		return response;
	}

	public Response getBooksForPractice(String practiceid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/book/").then().log().all()
				.extract().response();
		return response;
	}

	public Response getBookById(String practiceid, int bookid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/book/" + bookid).then().log().all()
				.extract().response();
		return response;
	}

	public Response getBookExtById(String practiceid, String extbookid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/bookbyextid/" + extbookid).then().log().all()
				.extract().response();
		return response;
	}

	public Response getBookWithLinksById(String practiceid, String bookid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/booklinkbyid/"+bookid).then().log()
				.all().extract().response();
		return response;
	}

	public Response getBooksWithLanguageForPractice(String practiceid) throws Exception {
		Response response = given().spec(requestSpec).header("language", "es").log().all().when()
				.get(practiceid+"/books").then().log().all()
				.extract().response();
		return response;
	}
	
	public Response getBookByPracticeAndName(String practiceid, String bookname) throws Exception {
		Response response = given().spec(requestSpec).header("language", "es").log().all().when()
				.get(practiceid+"/books/"+bookname).then().log().all()
				.extract().response();
		return response;
	}
	
	public Response getBooksByAppType(String practiceid, String apptype) throws Exception {
		Response response = given().spec(requestSpec).header("language", "es").log().all().when()
				.get(practiceid+"/books/apptype/"+apptype).then().log().all()
				.extract().response();
		return response;
	}
	public Response getBooksByLocation(String practiceid, int locationid) throws Exception {
		Response response = given().spec(requestSpec).header("language", "es").log().all().when()
				.get(practiceid+"/books/location/"+locationid).then().log().all()
				.extract().response();
		return response;
	}
	public Response getBooksByLocationAndAppointmentTypes(String practiceid, String locationid, String apptypeid) throws Exception {
		Response response = given().spec(requestSpec).header("language", "es").log().all().when()
				.get(practiceid+"/books/location/"+locationid+"/apptype/"+apptypeid).then().log().all()
				.extract().response();
		return response;
	}
	public Response getBooksBySpeciality(String practiceid,String specialtyid) throws Exception {
		Response response = given().spec(requestSpec).header("language", "es").log().all().when()
				.get(practiceid+"/books/specialty/"+specialtyid).then().log().all()
				.extract().response();
		return response;
	}
	public Response getBooksBySpecialityAndAppointmentType(String practiceid, String specialty, String apptypeid) throws Exception {
		Response response = given().spec(requestSpec).header("language", "es").log().all().when()
				.get(practiceid+"/books/specialty/"+specialty+"/apptype/"+apptypeid).then().log().all()
				.extract().response();
		return response;
	}
	public Response getBooksBySpecialityAndLocation(String practiceid, String specialty, String locationid) throws Exception {
		Response response = given().spec(requestSpec).header("language", "es").log().all().when()
				.get(practiceid+"/books/specialty/"+specialty+"/location/"+locationid).then().log().all()
				.extract().response();
		return response;
	}
	public Response getBooksBySpecialityAndLocationAndAppointmentType(String practiceid, String specialty, String locationid, String apptypeid) throws Exception {
		Response response = given().spec(requestSpec).header("language", "es").log().all().when()
				.get(practiceid+"/books/specialty/"+specialty+"/location/"+locationid+"/apptype/"+apptypeid).then().log().all()
				.extract().response();
		return response;
	}
	public Response getbooklevel(String practiceid) throws Exception {
		Response response = given().spec(requestSpec).header("language", "es").queryParam("groupType", "RESOURCE_LEVEL").log().all().when()
				.get(practiceid+"/getbooklevel").then().log().all()
				.extract().response();
		return response;
	}
	
	//Cancellation Reason Controller
	
	public Response getCancellationReason(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid+ path).then().log().all()
				.extract().response();
		return response;
	}
	
	public Response getCancellationReasonById(String practiceid, int reasonid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid+ "/cancellationreason/"+reasonid).then().log().all()
				.extract().response();
		return response;
	}
	
	public Response cancellationReasonDelete(String practiceid, int reasonid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid+ "/cancellationreason/"+reasonid).then().log().all()
				.extract().response();
		return response;
	}
	
	public Response reorderCancellationReason(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when()
				.post(practiceid+ "/cancellationreason/reorder").then().log().all()
				.extract().response();
		return response;
	}
	
	//Care Team Book Controller
	public Response getBookAssociatedToCareTeam(String practiceid, int careteamid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid+"/careteambook/"+ careteamid).then().log().all()
				.extract().response();
		return response;
	}
	
	public Response associateCareTeamToBook(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).log().all().body(b).when()
				.post(practiceid+"/careteambook").then().log().all()
				.extract().response();
		return response;
	}
	
	public Response deleteCareTeamBook(String practiceid, int careteamid, int bookid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid+"/careteambook/"+ careteamid +"/book/"+ bookid).then().log().all()
				.extract().response();
		return response;
	}

	//Care Team Controller
	
	public Response getCareteamsForPractice(String practiceid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/careteam").then().log()
				.all().extract().response();
		return response;
	}

	//Category App Type Controller
	
	public Response getAppTypeForCategory(String practiceid, String categoryid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/categoryapptype/" + categoryid)
				.then().log().all().extract().response();
		return response;
	}
	
	public Response getCategoryAppTypeForCategoryAndAppttype(String practiceid, String categoryid, String apptype) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/categoryapptype/" + categoryid+ apptype)
				.then().log().all().extract().response();
		return response;
	}
	
	//Category App Type Location Controller
	
	public Response getLocationsForCategoryAppType(String practiceid, String careteamid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/categoryapptypelocation/" + careteamid)
				.then().log().all().extract().response();
		return response;
	}
	
	//Category Controller
	
	public Response getCategoryForPractice(String practiceid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/category")
				.then().log().all().extract().response();
		return response;
	}
	
	
	public Response getCategoryById(String practiceid, String categoryid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/category/" + categoryid)
				.then().log().all().extract().response();
		return response;
	}
	
	
	public Response getCategorysWithLanguageForPractice(String practiceid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/categorys")
				.then().log().all().extract().response();
		return response;
	}
	
	public Response getCategorysBySpecialty(String practiceid, String specialty) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/categorys/specialty/" + specialty)
				.then().log().all().extract().response();
		return response;
	}
	
	public Response saveCategory(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/category")
				.then().log().all().extract().response();
		return response;
	}
	
	public Response deleteCategory(String practiceid, int categoryid ) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().delete(practiceid + "/category/"+categoryid)
				.then().log().all().extract().response();
		return response;
	}
	
	public Response reorderCategory(String practiceid, String b ) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/category/reorder")
				.then().log().all().extract().response();
		return response;
	}
	
	public Response getVisitReason(String practiceid, String b ) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/getVisitReason")
				.then().log().all().extract().response();
		return response;
	}
	
	//Category Specialty Controller
	
	public Response saveCategorySpecialty(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().get(practiceid + "/categoryspecialty")
				.then().log().all().extract().response();
		return response;
	}
	
	public Response deleteCategorySpecialty(String practiceid, String categoryid, String specialty) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.delete(practiceid + "/categoryspecialty/category/" + categoryid + "/specialty/" + specialty).then().log()
				.all().extract().response();
		return response;
	}
	
	public Response getSpecialtiesAssociatedToCategory(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + "/categoryspecialtys")
				.then().log().all().extract().response();
		return response;
	}
	
	public Response getCareTeamById(String practiceid, int careteamid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/careteam/" + careteamid)
				.then().log().all().extract().response();
		return response;
	}
	
	public Response saveCareTeam(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).log().all().body(b).when().post(practiceid + "/careteam")
				.then().log().all().extract().response();
		return response;
	}
	
	public Response deleteCareTeamById(String practiceid, int careteamid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().delete(practiceid + "/careteam/" + careteamid)
				.then().log().all().extract().response();
		return response;
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
	
	public Response practiceMetaDataAll(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid+path).then().log().all()
				.extract().response();
		return response;
	}
	
	public Response saveBook(String practiceid, String path, String b) throws Exception {
		Response response = given().spec(requestSpec).log().all().body(b).when().post(practiceid+path).then().log().all()
				.extract().response();
		return response;
	}
	
	public Response deleteBook(String practiceid, String path, int bookid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().delete(practiceid+path+bookid).then().log().all()
				.extract().response();
		return response;
	}
	
	public Response saveBookLocation(String practiceid, String path, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid+path).then().log().all()
				.extract().response();
		return response;
	}
	
	public Response deleteBookLocation(String practiceid,int bookid, int locationid) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().delete(practiceid+"/booklocation/book/"+ bookid+"/location/"+ locationid).then().log().all()
				.extract().response();
		return response;
	}
	
	public Response bookLocations(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid+"/booklocations").then().log().all()
				.extract().response();
		return response;
	}
	
	public Response cancellationReasonSave(String practiceid, String b) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid+"/cancellationreason").then().log().all()
				.extract().response();
		return response;
	}

	public Response getCodeMapTranslation(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + path).then().log().all()
				.extract().response();
		return response;
	}

	public Response getGenderMapMaster(String practiceid, String path) throws Exception {
		Response response = given().queryParam("codeGroup", "gender").spec(requestSpec).log().all().when()
				.get(practiceid + path).then().log().all().extract().response();
		return response;
	}

	public Response getLookUpCodeValueForGroup(String practiceid, String path) throws Exception {
		Response response = given().queryParam("groupType", "genderrule").queryParam("name", "Gender rule")
				.spec(requestSpec).log().all().when().get(practiceid + path).then().log().all().extract().response();
		return response;
	}

	public Response getPartnerCodeValueForGroup(String practiceid, String path) throws Exception {
		Response response = given().queryParam("group", "gender").queryParam("pssCode", "UN").spec(requestSpec).log()
				.all().when().get(practiceid + path).then().log().all().extract().response();
		return response;
	}

	public Response getPssCodeValueForGroup(String practiceid, String path) throws Exception {
		Response response = given().queryParam("group", "gender").queryParam("partnerCode", "U").spec(requestSpec).log()
				.all().when().get(practiceid + path).then().log().all().extract().response();
		return response;
	}

	public Response getSpecialtyRule(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceid + path).then().log().all()
				.extract().response();
		return response;
	}

	public Response getCodesByPartnerAndGroup(String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(path).then().log().all().extract()
				.response();
		return response;
	}

	public Response gendermap(String practiceid, String b, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + endPointPath).then()
				.log().all().extract().response();
		return response;
	}

	public Response saveConfigurationsWithGroup(String practiceid, String b, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + endPointPath).then()
				.log().all().extract().response();
		return response;
	}

	public Response getAllConfigurationsByGroup(String practiceid, String b, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + endPointPath).then()
				.log().all().extract().response();
		return response;
	}

	public Response saveOrUpdateFlowIdentityByPractice(String practiceid, String b, String endPointPath)
			throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + endPointPath).then()
				.log().all().extract().response();
		return response;
	}

	public Response saveInsuranceCarrier(String practiceid, String b, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + endPointPath).then()
				.log().all().extract().response();
		return response;
	}

	public Response getinsuranceById(String practiceid, int insuranceId) throws Exception {
		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + "/insurancecarrier/" + insuranceId).then().log().all().extract().response();
		return response;
	}

	public Response deleteInsurance(String practiceid, String path, int insuranceId) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().delete(practiceid + path + insuranceId).then()
				.log().all().extract().response();
		return response;
	}

	public Response reorderInsuranceCarrier(String practiceid, String b, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + endPointPath).then()
				.log().all().extract().response();
		return response;
	}

	public Response saveSSOConfigurations(String practiceid, String b, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + endPointPath).then()
				.log().all().extract().response();
		return response;
	}

	public Response deleteSSO(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().delete(practiceid + path).then().log().all()
				.extract().response();
		return response;
	}

	public Response saveSpecialities(String practiceid, String b, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + endPointPath).then()
				.log().all().extract().response();
		return response;
	}

	public Response getSpecialityById(String practiceId, int specialityId) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceId + "/speciality/" + specialityId)
				.then().log().all().extract().response();
		return response;
	}

	public Response reorderSpeciality(String practiceid, String b, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + endPointPath).then()
				.log().all().extract().response();
		return response;
	}

	public Response deleteSpecility(String practiceid, String path, int insuranceId) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().delete(practiceid + path + insuranceId).then()
				.log().all().extract().response();
		return response;
	}

	public Response saveRule(String practiceid, String b, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + endPointPath).then()
				.log().all().extract().response();
		return response;
	}

	public Response deleteRule(String practiceid, String path, String ruleId) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().delete(practiceid + path + ruleId).then().log()
				.all().extract().response();
		return response;
	}

	public Response saveOrUpdateReseller(String practiceid, String b, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + endPointPath).then()
				.log().all().extract().response();
		return response;
	}

	public Response savePrerequisiteappointment(String practiceid, String b, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + endPointPath).then()
				.log().all().extract().response();
		return response;
	}

	public Response getPrerequisiteappointment(String practiceId, String endPointPath, String preAppId)
			throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get(practiceId + endPointPath + preAppId)
				.then().log().all().extract().response();
		return response;
	}

	public Response lockout(String practiceid, String b, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + endPointPath).then()
				.log().all().extract().response();
		return response;
	}

	public Response saveLockout(String practiceid, String b, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + endPointPath).then()
				.log().all().extract().response();
		return response;
	}

	public Response deletelockout(String practiceid, String path, int id) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().delete(practiceid + path + id).then().log()
				.all().extract().response();
		return response;
	}

	public Response loginless(String practiceid, String b, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + endPointPath).then()
				.log().all().extract().response();
		return response;
	}

	public Response patientInfo(String practiceid, String b, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + endPointPath).then()
				.log().all().extract().response();
		return response;
	}

	public Response patientmatch(String practiceid, String b, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + endPointPath).then()
				.log().all().extract().response();
		return response;
	}

	public Response links(String practiceid, String b, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + endPointPath).then()
				.log().all().extract().response();
		return response;
	}

	public Response reschedule(String practiceid, String b, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + endPointPath).then()
				.log().all().extract().response();
		return response;
	}

	public Response getfetchRule(String practiceid, String path) throws Exception {
		Response response = given().spec(requestSpec).log().all().when().get("rules/" + practiceid + path).then().log()
				.all().extract().response();
		return response;
	}

	public Response location(String practiceid, String b, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + endPointPath).then()
				.log().all().extract().response();
		return response;
	}

	public Response locationReorder(String practiceid, String b, String endPointPath) throws Exception {
		Response response = given().spec(requestSpec).body(b).log().all().when().post(practiceid + endPointPath).then()
				.log().all().extract().response();
		return response;
	}	
	
}


