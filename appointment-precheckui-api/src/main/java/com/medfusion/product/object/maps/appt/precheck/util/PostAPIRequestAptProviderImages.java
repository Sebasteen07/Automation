package com.medfusion.product.object.maps.appt.precheck.util;

import static io.restassured.RestAssured.given;

import java.util.Map;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PostAPIRequestAptProviderImages extends BaseTestNGWebDriver {
	private static PostAPIRequestAptProviderImages postAPIRequest = new PostAPIRequestAptProviderImages();

	private PostAPIRequestAptProviderImages() {
	}

	public static PostAPIRequestAptProviderImages getPostAPIRequestAptProviderImages() {
		return postAPIRequest;
	}

	APIVerification apiVerification = new APIVerification();
	public static RequestSpecification requestSpec;

	public void setupRequestSpecBuilder(String baseurl) {
		RestAssured.baseURI = baseurl;
		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
	}

	public Response providerListByPractice(Map<String, String> Header, String practiceId) {
		log("Execute Get request for Get provider list by practice.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().get("practice/" + practiceId)
				.then().log().all().extract().response();
		return response;
	}

	public Response providerListByPracticeWithoutPracticeId(Map<String, String> Header) {
		log("Execute Get request for Get provider list by practice.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().get("practice/")
				.then().log().all().extract().response();
		return response;
	}

	public Response createProviderForPractice(String payload, Map<String, String> Header, String practiceId) {
		log("Execute Post request for Create provider for a practice.");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.post("practice/" + practiceId + "/provider")
				.then().log().all().extract().response();
		return response;
	}

	public Response providerDetails(Map<String, String> Header, String practiceId, String providerId) {
		log("Execute Get request for Get provider details.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("practice/" + practiceId + "/provider/" + providerId).then().log().all().extract().response();
		return response;
	}

	public Response providerDetailsWithoutPracticeId(Map<String, String> Header, String providerId) {
		log("Execute Get request for Get provider details.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("practice/provider/" + providerId).then().log().all().extract().response();
		return response;
	}

	public Response providerDetailsWithoutProviderId(Map<String, String> Header, String practiceId) {
		log("Execute Get request for Get provider details.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("practice/" + practiceId + "/provider/").then().log().all().extract().response();
		return response;
	}

	public Response updateAnExistingProvider(String payload, Map<String, String> Header, String practiceId,
			String providerId) {
		log("Execute Get request for Update an Existing Provider");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.put("practice/" + practiceId + "/provider/" + providerId).then().log().all().extract().response();
		return response;
	}

	public Response deleteExistingProvider(Map<String, String> Header, String practiceId, String providerId) {
		log("Execute delete request for Delete an existing provider.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.delete("practice/" + practiceId + "/provider/" + providerId).then().log().all().extract().response();
		return response;
	}

	public Response deleteExistingProviderWithoutPracticeId(Map<String, String> Header, String providerId) {
		log("Execute delete request for Delete an existing provider.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.delete("practice/provider/" + providerId).then().log().all().extract().response();
		return response;
	}

	public Response deleteExistingProviderWithoutProviderId(Map<String, String> Header, String practiceId) {
		log("Execute delete request for Delete an existing provider.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.delete("practice/" + practiceId + "/provider/").then().log().all().extract().response();
		return response;
	}

	public Response getsTheImageData(Map<String, String> Header, String practiceId, String providerId, String size,
			String thumbnail) {
		log("Execute delete request for Gets the image data for a provider.");
		Response response = given().spec(requestSpec).log().all().queryParam("size", size)
				.queryParam("thumbnail", thumbnail).headers(Header).when()
				.get("practice/" + practiceId + "/provider/" + providerId + "/image").then().log().all().extract()
				.response();
		return response;
	}

	public Response getsTheImageDataWithoutPracticeId(Map<String, String> Header, String providerId,
			String size, String thumbnail) {
		log("Execute delete request for Gets the image data for a provider.");
		Response response = given().spec(requestSpec).log().all().queryParam("size", size)
				.queryParam("thumbnail", thumbnail).headers(Header).when()
				.get("practice/provider/" + providerId + "/image").then().log().all().extract()
				.response();
		return response;
	}

	public Response getsTheImageDataWithoutProviderId(Map<String, String> Header, String PracticeId, String size,
			String thumbnail) {
		log("Execute delete request for Gets the image data for a provider.");
		Response response = given().spec(requestSpec).log().all().queryParam("size", size)
				.queryParam("thumbnail", thumbnail).headers(Header).when()
				.get("practice/" + PracticeId + "/provider/image").then().log().all().extract().response();
		return response;
	}

	public Response getsTheImageDataWithoutSizeAndThumbnail(Map<String, String> Header, String PracticeId,
			String providerId) {
		log("Execute Get request for Gets the image data for a provider.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("practice/" + PracticeId + "/provider/" + providerId + "/image").then().log().all().extract()
				.response();
		return response;
	}
}
