//Copyright 2021 NXGN Management, LLC. All Rights Reserved.
/************************
 * 
 * @author Narora
 * 
 ************************/

package com.ng.product.integrationplatform.apiUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.codehaus.jackson.map.ObjectMapper;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.product.integrationplatform.utils.PropertyFileLoader;
import com.medfusion.common.utils.IHGUtil;
import com.ng.product.integrationplatform.pojo.LoginDefaults;
import com.ng.product.integrationplatform.utils.DBUtils;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class NGAPIRestUtils {

	private static String enterpriseUsername = "";
	private static String enterprisePassword = "";
	private static String enterpriseEmail = "";
	private static String baseURL = "";

	public NGAPIRestUtils(PropertyFileLoader propertyLoaderObj) throws Throwable {
		Log4jUtil.log("API Execution Mode " + propertyLoaderObj.getNGAPIexecutionMode());
		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			baseURL = getRelativeBaseUrl();
			enterpriseUsername = apiConfig.valueOf("QAMainEnterpriseUsername").getConfigProperty().toString();
			enterprisePassword = apiConfig.valueOf("QAMainEnterprisePassword").getConfigProperty().toString();
			enterpriseEmail = apiConfig.valueOf("QAMainEnterpriseEmail").getConfigProperty().toString();
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			baseURL = getRelativeBaseUrl();
			enterpriseUsername = apiConfig.valueOf("SITEnterpriseUsername").getConfigProperty().toString();
			enterprisePassword = apiConfig.valueOf("SITEnterprisePassword").getConfigProperty().toString();
			enterpriseEmail = apiConfig.valueOf("SITEnterpriseEmail").getConfigProperty().toString();
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}
	}

	public synchronized static String getAuthSignature(String finalURL, String apiMethod, String body,
			String queryParams) throws NoSuchAlgorithmException, IOException {
		Log4jUtil.log("Route URL  is " + finalURL);
		Log4jUtil.log("API Method to be performed " + apiMethod);
		String authSignature = EnterpriseSignature.SignatureGeneration(enterpriseUsername.toLowerCase(),
				enterprisePassword, enterpriseEmail, finalURL, body, queryParams, apiMethod);
		System.setProperty("AuthEnterpriseSignature", authSignature);
		Log4jUtil.log("Auth Signature is " + authSignature);
		return authSignature;
	}

	public static String getXNGDate() {
		final Date currentTime = new Date();
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		return sdf.format(currentTime).toString();
	}

	public static Map<String, String> addHeader(String argRouteURL) throws Throwable {

		String siteID = DBUtils.executeQueryOnDB("NGCoreDB",
				"Select option_value from configuration_options where app_name='API' and key_name='SiteId'");

		Map<String, String> m = new HashMap<String, String>();
		m.put("Accept", "*/*");
		m.put("content-type", "application/json");
		m.put("Authorization", "NEXTGEN-AMB-API-V2 Credential=" + enterpriseEmail.toLowerCase() + ", Signature="
				+ System.getProperty("AuthEnterpriseSignature"));
		m.put("X-NG-Date", EnterpriseSignature.NGTime);
		m.put("X-NG-Product", "NEXTGEN-AMB-API-V2");
		m.put("x-nge-site-id", siteID);

		return m;
	}

	public static String setupNGHttpPostRequest(String mode, String argRouteURL, String argPayload,
			int expectedStatusCode) throws Throwable {
		IHGUtil.PrintMethodName();
		String strLocationHeader = "";
		try {
			Log4jUtil.log("PostURL " + argRouteURL);

			getAuthSignature(argRouteURL, "POST", argPayload, "");
			Map<String, String> headers = addHeader(argRouteURL);

			RestAssured.baseURI = argRouteURL;
			Response response = RestAssured.given().log().all().headers(headers).body(argPayload).when().post().then()
					.log().all().assertThat().statusCode(expectedStatusCode).extract().response();

			for (Header headerRes : response.getHeaders()) {
				if (headerRes.getName().equalsIgnoreCase("Location")) {
					strLocationHeader = headerRes.getValue().split("/")[headerRes.getValue().split("/").length - 1];
					Log4jUtil.log("Request processed with ID as " + strLocationHeader);
				}
			}
			return strLocationHeader;

		} catch (Exception e) {
			Log4jUtil.log("Exception caught " + e.getMessage());
		}
		return null;
	}

	public static Response setupNGHttpGetRequest(String mode, String argRouteURL, int expectedStatusCode)
			throws Throwable {
		IHGUtil.PrintMethodName();
		Log4jUtil.log("GetURL " + argRouteURL);
		try {
			getAuthSignature(argRouteURL, "GET", "", "");
			Map<String, String> headers = addHeader(argRouteURL);

			RestAssured.baseURI = argRouteURL;
			Response response = RestAssured.given().log().all().headers(headers).when().get().then().log().all()
					.assertThat().statusCode(expectedStatusCode).extract().response();

			return response;
		} catch (Exception e) {
			Log4jUtil.log("Exception caught " + e.getMessage());
		}
		return null;
	}

	public static String setupNGHttpPutRequest(String mode, String argRouteURL, String requestbody,
			int expectedStatusCode) throws Throwable {
		IHGUtil.PrintMethodName();
		Log4jUtil.log("PutURL " + argRouteURL);
		String strLocationHeader = null;
		try {
			getAuthSignature(argRouteURL, "PUT", requestbody, "");
			Map<String, String> headers = addHeader(argRouteURL);

			RestAssured.baseURI = argRouteURL;
			Response response = RestAssured.given().log().all().headers(headers).body(requestbody).when().put().then()
					.log().all().assertThat().statusCode(expectedStatusCode).extract().response();

			for (Header headerRes : response.getHeaders()) {
				if (headerRes.getName().equalsIgnoreCase("Location")) {
					strLocationHeader = headerRes.getValue();
				}
			}
			return strLocationHeader;
		} catch (Exception e) {
			Log4jUtil.log("Exception caught " + e.getMessage());
		}
		return null;
	}

	public static void setupNGHttpDeleteRequest(String mode, String argRouteURL, int expectedStatusCode)
			throws Throwable {
		IHGUtil.PrintMethodName();
		Log4jUtil.log("DeleteURL " + argRouteURL);
		try {
			getAuthSignature(argRouteURL, "DELETE", "", "");
			Map<String, String> headers = addHeader(argRouteURL);

			RestAssured.baseURI = argRouteURL;
			RestAssured.given().log().all().headers(headers).when().delete().then().log().all().assertThat()
					.statusCode(expectedStatusCode).extract().response();
		} catch (Exception e) {
			Log4jUtil.log("Exception caught " + e.getMessage());
		}
	}

	public synchronized static void updateLoginDefaultTo(String mode, String enterpriseID, String practiceID)
			throws Throwable {
		Log4jUtil.log("Step Begins --- API Route Change the Login Defaults: enterpriseID -" + enterpriseID
				+ ", practiceID -" + practiceID);

		String strSqlQueryForUserDetails = "select top 1 user_id from user_mstr where email_login_id='"
				+ enterpriseEmail + "' order by create_timestamp";
		String currentUserID = DBUtils.executeQueryOnDB("NGCoreDB", strSqlQueryForUserDetails);

		String logInDefaultsUrl = baseURL
				+ apiRoutes.LogInDefaults.getRouteURL().toString().replace("userId", currentUserID);
		Response getLogInDefaultResponse = setupNGHttpGetRequest(mode, logInDefaultsUrl, 200);

		JsonPath jsonPath = getLogInDefaultResponse.jsonPath();

		String currentPracticeID = jsonPath.get("practiceId");
		if (!currentPracticeID.equalsIgnoreCase(practiceID)) {
			Log4jUtil.log("Expected practice id is different from current practice ID");

			LoginDefaults loginDefaults = new LoginDefaults();
			loginDefaults.setEnterpriseId(enterpriseID);
			loginDefaults.setPracticeId(practiceID);
			String requestbody = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(loginDefaults);
			Log4jUtil.log("Login Defaults request Body is \n" + requestbody);

			String logInDefaultsID = setupNGHttpPutRequest(mode, logInDefaultsUrl, requestbody, 200);
			Log4jUtil.log("Login default updated with id " + logInDefaultsID);
		} else
			Log4jUtil
					.log("Expected practice id " + practiceID + " is same as current practice ID " + currentPracticeID);
	}

	public static String getRelativeBaseUrl() throws Throwable {
		String baseURL = apiRoutes.BaseURL.getRouteURL().toString();
		Response response = setupNGHttpGetRequest("EnterpriseGateway", baseURL, 200);
		String actualURL = "";

		JsonPath jsonPath = response.jsonPath();
		Object productList = jsonPath.get("productName");
		int i = 0;
		String baseAPi = null;
		for (String productname : productList.toString().split(",")) {
			if (productname.contains("NextGen.Api.Edge")) {
				baseAPi = jsonPath.get("[" + i + "].relativeBaseApiUrl");
				break;
			}
			i++;
		}

		actualURL = baseURL.replace("/gateway", "") + baseAPi + "/";
		Log4jUtil.log("Actual URL is \n" + actualURL);

		return actualURL;
	}

}
