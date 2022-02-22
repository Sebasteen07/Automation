package com.medfusion.product.object.maps.appt.precheck.util;

import static io.restassured.RestAssured.given;

import java.util.Map;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PostAPIRequestMfisFilterService extends BaseTestNGWebDriver {
	private static PostAPIRequestMfisFilterService postAPIRequest = new PostAPIRequestMfisFilterService();

	private PostAPIRequestMfisFilterService() {
	}

	public static PostAPIRequestMfisFilterService getPostAPIRequestMfisFilterService() {
		return postAPIRequest;
	}

	public static RequestSpecification requestSpec;

	public void setupRequestSpecBuilder(String baseurl) {
		RestAssured.baseURI = baseurl;
		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
	}

	public Response getFilterPracticeIdPost(String payload, Map<String, String> Header, String practiceId) {
		log("Execute post request Filter practice id");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.post("filter/" + practiceId).then().log().all().extract().response();
		return response;
	}

	public Response getFilterPracticeIdPut(String payload, Map<String, String> Header, String practiceId) {
		log("Execute put request Filter practice id");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.put("filter/" + practiceId).then().log().all().extract().response();
		return response;
	}

	public Response getDeleteFilterPracticeId(Map<String, String> Header, String practiceId) {
		log("Execute delete request Filter practice id");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().delete("filter/" + practiceId)
				.then().log().all().extract().response();
		return response;
	}

	public Response getFilterPracticeId(Map<String, String> Header, String practiceId) {
		log("Execute get request Filter practice id");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().get("filter/" + practiceId)
				.then().log().all().extract().response();
		return response;
	}

	public Response getFilterSetId(Map<String, String> Header, String practiceId, String filterSetId) {
		log("Execute get request Filter set id");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("filter/" + practiceId + "/filterset/" + filterSetId).then().log().all().extract().response();
		return response;
	}

	public Response getDeleteMappingFilterPracticeId(Map<String, String> Header, String practiceId) {
		log("Execute delete request Mapping Filter practice id");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.delete("mapping/practice/" + practiceId + "/filters").then().log().all().extract().response();
		return response;
	}

	public Response getMappingFilterPracticeIdPost(String payload, Map<String, String> Header, String practiceId) {
		log("Execute post request Mapping Filter practice id");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.post("mapping/practice/" + practiceId + "/filters").then().log().all().extract().response();
		return response;
	}

	public Response getMappingFilterPracticeId(Map<String, String> Header, String practiceId) {
		log("Execute get request Mapping Filter practice id");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("mapping/practice/" + practiceId + "/filters").then().log().all().extract().response();
		return response;
	}

	public Response getMappingFilterPracticeIdPut(String payload, Map<String, String> Header, String practiceId) {
		log("Execute put request Mapping Filter practice id");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.put("mapping/practice/" + practiceId + "/filters").then().log().all().extract().response();
		return response;
	}

	public Response getMappingFilterId(Map<String, String> Header, String practiceId, String mappingFilterId) {
		log("Execute get request mapping Filter id");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("mapping/practice/" + practiceId + "/filters/mappingFilter/" + mappingFilterId).then().log().all()
				.extract().response();
		return response;
	}

	public Response getDeleteMappingFilterId(Map<String, String> Header, String practiceId, String mappingFilterId) {
		log("Execute delete request Mapping Filter id");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.delete("mapping/practice/" + practiceId + "/filters/mappingFilter/" + mappingFilterId).then().log()
				.all().extract().response();
		return response;
	}

	public Response getDeleteMappingPracticeIdMaps(Map<String, String> Header, String practiceId) {
		log("Execute delete request Mapping practice id maps");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.delete("mapping/practice/" + practiceId + "/maps").then().log().all().extract().response();
		return response;
	}

	public Response getMappingPracticeIdMapsPost(String payload, Map<String, String> Header, String practiceId) {
		log("Execute post request Mapping practice id maps");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.post("mapping/practice/" + practiceId + "/maps").then().log().all().extract().response();
		return response;
	}

	public Response getMappingPracticeIdMaps(Map<String, String> Header, String practiceId) {
		log("Execute get request Mapping practice id maps");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("mapping/practice/" + practiceId + "/maps").then().log().all().extract().response();
		return response;
	}

	public Response getMappingPracticeIdMapsPut(String payload, Map<String, String> Header, String practiceId) {
		log("Execute put request Mapping practice id maps");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.put("mapping/practice/" + practiceId + "/maps").then().log().all().extract().response();
		return response;
	}

	public Response getResourceMapSetIdGet(Map<String, String> Header, String practiceId, String resourceMapSetId) {
		log("Execute get request Resource Map Set Id");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("mapping/practice/" + practiceId + "/maps/resourceMapSet/" + resourceMapSetId).then().log().all()
				.extract().response();
		return response;
	}

	public Response getDeleteResourceMapSetId(Map<String, String> Header, String practiceId, String resourceMapSetId) {
		log("Execute delete request Resource Map Set Id");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.delete("mapping/practice/" + practiceId + "/maps/resourceMapSet/" + resourceMapSetId).then().log()
				.all().extract().response();
		return response;
	}
}
