// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
/************************
 * 
 * @author Narora
 * 
 ************************/
package com.ng.product.integrationplatform.apiUtils;

import static org.testng.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import com.google.common.base.Objects;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PAMAuthentication {
	
	public static String getAccessToken(String username, String password) throws IOException, URISyntaxException {
		String accessToken = null;
		try {

			String redirectURL = pamAPIRoutes.valueOf("redirectURL").getRouteURL();
			String authURL = pamAPIRoutes.valueOf("AuthURL").getRouteURL();
			String authToken = pamAPIRoutes.valueOf("AuthToken").getRouteURL();
			String clientID = pamAPIRoutes.valueOf("clientID").getRouteURL();
			String clientSecret = pamAPIRoutes.valueOf("clientSecret").getRouteURL();

			CloseableHttpClient httpClient;
			CloseableHttpResponse httpResponse = null;
			Header[] httpHeaders;

			// ---------------1st call --------------------------
			URIBuilder builder = new URIBuilder(authURL);
			builder.setParameter("response_type", "code");
			builder.setParameter("state", "authCode");
			builder.setParameter("redirect_uri", redirectURL);
			builder.setParameter("client_id", clientID);
			builder.setParameter("scope", "obo");

			String sessionData = null, code = null, requestVerificationToken = null, matchString = null;

			httpClient = HttpClients.createDefault();

			HttpGet httpGet = new HttpGet(builder.build());
			httpGet.addHeader("Content-Type",
					"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
			httpResponse = httpClient.execute(httpGet);

			httpHeaders = httpResponse.getAllHeaders();
			String cookies = (httpHeaders[9].toString().split("Set-Cookie: ")[0]).toString().split(";")[0];

			int responseCode = httpResponse.getStatusLine().getStatusCode();

			StringBuilder sb = getSessionData(httpResponse, responseCode);

			if (sb.toString().contains("SessionData")) {
				matchString = "SessionData\" name=\"SessionData\" type=\"hidden\" value=\"";
				sessionData = (((sb.toString().split(matchString))[1].split("\" /><"))[0]);

				matchString = "__RequestVerificationToken\" type=\"hidden\" value=\"";
				requestVerificationToken = (((sb.toString().split(matchString))[1].split("\" /><"))[0]);
			}

			httpGet.releaseConnection();

			// -------------------------------2nd call ----------------------

			OkHttpClient client = new OkHttpClient().newBuilder().build();

			RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
					.addFormDataPart("Username", username).addFormDataPart("Password", password)
					.addFormDataPart("SessionData", sessionData).addFormDataPart("ClientId", clientID)
					.addFormDataPart("__RequestVerificationToken", requestVerificationToken).build();

			String reqUrl = authURL + "/login";

			Request request = new Request.Builder().url(reqUrl).method("POST", body)
					.addHeader("Content-Type", "multipart/form-data").addHeader("Cookie", cookies).build();

			Response response11 = client.newCall(request).execute();

			BufferedReader reader12 = new BufferedReader(new InputStreamReader(response11.body().byteStream()), 2048);
			StringBuilder sb12 = new StringBuilder();
			if (Objects.equal(response11.code(), 200)) {
				if (response11 != null) {
					String line;
					while ((line = reader12.readLine()) != null) {
						sb12.append(line);
					}

					if (sb12.toString().contains("code")) {
						matchString = "hidden\" id=\"code\" name=\"code\" value=\"";
						code = (((sb12.toString().split(matchString))[1].split("\" />")[0]));
					}
				}
			} else {
				assertEquals(response11.code(), "200");
			}

			// -------------------------------3rd call ---------------------------------

			httpClient = HttpClients.createDefault();
			URIBuilder builder6 = new URIBuilder(authToken);
			builder6.setParameter("grant_type", "authorization_code");
			builder6.setParameter("code", code);
			builder6.setParameter("redirect_uri", redirectURL);
			builder6.setParameter("client_id", clientID);
			builder6.setParameter("client_secret", clientSecret);

			HttpPost httpPost3 = new HttpPost(builder6.build());
			httpPost3.addHeader("Content-Type", "application/x-www-form-urlencoded");
			httpResponse = httpClient.execute(httpPost3);
			httpPost3.releaseConnection();
			httpHeaders = httpResponse.getAllHeaders();

			responseCode = httpResponse.getStatusLine().getStatusCode();

			StringBuilder sb6 = getSessionData(httpResponse, responseCode);
			JSONObject jObject = new JSONObject(sb6.toString());
			Log4jUtil.log("After 6th call Response is " + jObject);
			accessToken = (String) jObject.get("access_token");
			Log4jUtil.log("Refresh token is " + jObject.get("refresh_token").toString());
			Log4jUtil.log("Access token is " + accessToken);

		} catch (Exception e) {
			Log4jUtil.log(e.getMessage());
		}
		return accessToken;
	}
	
	public static StringBuilder getSessionData(CloseableHttpResponse httpResponse, int responseCode) throws IllegalStateException, IOException {
		String line;
		BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()),
				2048);
		StringBuilder sb = new StringBuilder();
		if ((responseCode == 200) && (httpResponse != null)) {				
				while ((line = reader.readLine()) != null) {
					sb.append(line);					
				}
		}
		return sb;
	}
}
