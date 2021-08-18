package com.medfusion.pay.api.helpers;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import com.intuit.ifs.csscat.core.utils.ApiCommonUtil;
import com.medfusion.common.utils.PropertyFileLoader;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class ApiConstructor extends ApiCommonUtil {
	protected static PropertyFileLoader testData;
	public static RequestSpecification requestSpec;
	
	public void setupRequestSpecBuilder(String baseurl) throws IOException {
		RestAssured.baseURI = baseurl;
		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).and()
				.addFilter(new ResponseLoggingFilter()).addFilter(new RequestLoggingFilter()).build();
	}
	
	public Response getCardInfo(String binNumber) throws Exception {
		Response response = given().spec(requestSpec).when().get("v1/cardinfo?bin=" + binNumber)
				.then().extract().response();
		return response;
	}
	
	public Response updateBinData() throws Exception {
		Response response = given().spec(requestSpec).when().put("v1/binfile")
				.then().extract().response();
		return response;
	}
}
