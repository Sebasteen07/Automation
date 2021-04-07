//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.ng.product.integrationplatform.apiUtils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.product.integrationplatform.utils.PropertyFileLoader;
import com.medfusion.common.utils.IHGUtil;
import com.ng.product.integrationplatform.pojo.LoginDefaults;
import com.ng.product.integrationplatform.utils.CommonUtils;
import com.ng.product.integrationplatform.utils.DBUtils;

/************************
 * 
 * @author Narora
 * 
 ************************/

public class NGAPIUtils {
	
	private static String TokenGenerationURL="";
	private static String EnterpriseUsername="";
	private static String EnterprisePassword="";
	private static String EnterpriseEmail="";
	private static String EnterpriseID="";
	private static String PracticeID="";
	private static String BaseURL="";
	private static String XNGSessionID="";
	
	public NGAPIUtils(PropertyFileLoader PropertyLoaderObj) throws Throwable {
		Log4jUtil.log("API Execution Mode "+PropertyLoaderObj.getNGAPIexecutionMode());	
		XNGSessionID = DBUtils.executeQueryOnDB("NGCoreDB","Select option_value from configuration_options where app_name='API' and key_name='SiteId'");
		if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
			BaseURL =getRelativeBaseUrl();
			TokenGenerationURL =apiRoutes.QAMainTokenGenerationURL.getRouteURL().toString();
		    EnterpriseUsername= apiConfig.valueOf("QAMainEnterpriseUsername").getConfigProperty().toString();
		    EnterprisePassword= apiConfig.valueOf("QAMainEnterprisePassword").getConfigProperty().toString();
		    EnterpriseEmail= apiConfig.valueOf("QAMainEnterpriseEmail").getConfigProperty().toString();
		    EnterpriseID= PropertyLoaderObj.getNGAPIQAMainEnterpriseID();
		    PracticeID= PropertyLoaderObj.getNGAPIQAMainPracticeID();
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			BaseURL =getRelativeBaseUrl();
			TokenGenerationURL =apiRoutes.SITTokenGenerationURL.getRouteURL().toString();
			EnterpriseUsername= apiConfig.valueOf("SITEnterpriseUsername").getConfigProperty().toString();
			EnterprisePassword= apiConfig.valueOf("SITEnterprisePassword").getConfigProperty().toString();
			EnterpriseEmail= apiConfig.valueOf("SITEnterpriseEmail").getConfigProperty().toString();
			EnterpriseID= PropertyLoaderObj.getNGAPISITEnterpriseID();
		    PracticeID= PropertyLoaderObj.getNGAPISITPracticeID();
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}
	}
	
	public static String getToken() throws IllegalStateException, IOException{
		CloseableHttpResponse httpResponse = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(TokenGenerationURL);
		httpPost.addHeader("content-type", "application/x-www-form-urlencoded");
        
        try {
			httpResponse = httpClient.execute(httpPost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			Log4jUtil.log(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log4jUtil.log(e.getMessage());
		}
        httpPost.releaseConnection();
        Log4jUtil.log("Status code of Token geneartion "+httpResponse.getStatusLine().getStatusCode());
        if(httpResponse.getStatusLine().getStatusCode()==200){
        	Log4jUtil.log("The Token generated successfully");
        }else{
        	Log4jUtil.log("Unable to genrate the token"); 
        }
        assertTrue(httpResponse.getStatusLine().getStatusCode() == 200);
        
        String sResp = EntityUtils.toString(httpResponse.getEntity());        
        JsonObject jsonObject = new JsonParser().parse(sResp).getAsJsonObject();        
        String token = jsonObject.get("access_token").toString().replace("\"", "");
        Log4jUtil.log("Bearer token "+token);
		return token;
	}
	
	public static String getXNGSession(String token) throws ClientProtocolException, IOException, JSONException{
		String strLocationHeader = null;
		CloseableHttpResponse httpResponse = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpPut httpPut = new HttpPut(apiRoutes.valueOf("SelectEnterprisePractice").getRouteURL());
	
		LoginDefaults loginDefaults = new LoginDefaults();
		loginDefaults.setEnterpriseId(EnterpriseID);
		loginDefaults.setPracticeId(PracticeID);
		
		ObjectMapper objMap = new ObjectMapper();
        String requestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(loginDefaults);
        Log4jUtil.log("Login Defaults request Body is \n" + requestbody);
             
		StringEntity entity=new StringEntity(requestbody);
		
		httpPut.setHeader("Accept", "*/*");
		httpPut.setHeader("Authorization", "Bearer "+token);
		httpPut.setHeader("Content-type", "application/json");
		
//	    Set the body    
		httpPut.setEntity(entity);
		
//      Execute the Request
		httpResponse = httpClient.execute(httpPut);
	
		Log4jUtil.log("Status code for LoginDefaults "+httpResponse.getStatusLine().getStatusCode());
		 if(httpResponse.getStatusLine().getStatusCode()==200){
			 Log4jUtil.log("Log in to Enterprise and practice is successfully");
	        }else{
	        	Log4jUtil.log("Unable to log in to enterprise and practice");
	        }
		 assertTrue(httpResponse.getStatusLine().getStatusCode() == 200);
		 
	        for(Header headerRes: httpResponse.getAllHeaders()){
	            if(headerRes.getName().equalsIgnoreCase("X-NG-SessionId")){
	                strLocationHeader= headerRes.getValue().toString();
	                Log4jUtil.log("X-NG-SessionId is " + headerRes.getValue());
	            }
	        }
			return strLocationHeader;
	}
	
	public static String getXNGDate()
	{
		final Date currentTime = new Date();
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		return sdf.format(currentTime).toString();
	}
	
	public synchronized static String getAuthSignature(String FinalURL ,String apiMethod,String body, String queryParams) throws NoSuchAlgorithmException, IOException {
        String authSignature = EnterpriseSignature.SignatureGeneration(EnterpriseUsername.toLowerCase(), EnterprisePassword, EnterpriseEmail, FinalURL ,body,queryParams,apiMethod);

        System.setProperty("AuthEnterpriseSignature",authSignature);
        System.setProperty("Email",EnterpriseEmail);
        Log4jUtil.log("Route URL  is " + FinalURL);
        Log4jUtil.log("API Method to be performed " +  apiMethod);
        Log4jUtil.log("Auth Signature is " + authSignature);
        return authSignature;
    }
	
	public static String setupNGHttpFirstPostRequest(String mode,String argRouteURL, String argPayload, int ExpectedStatusCode) throws IOException {
		IHGUtil.PrintMethodName();
		try {
			StringEntity arg_Payload=new StringEntity(argPayload, ContentType.APPLICATION_JSON);
		
			String token = getToken();
			String XNGDate = getXNGDate();
			String XNGSessionId= getXNGSession(token);
			System.setProperty("BearerToken", "Bearer "+token);
			System.setProperty("XNGSessionId",XNGSessionId);

			Log4jUtil.log("PostURL "+argRouteURL);

			CloseableHttpResponse httpResponse = null;
	        CloseableHttpClient httpClient = HttpClients.createDefault();

	        String strLocationHeader="";
	        String Email= EnterpriseEmail;
	        HttpPost httpPost = new HttpPost(argRouteURL);

	        httpPost.addHeader("Accept", "*/*");
	        httpPost.addHeader("Content-type", "application/json");
	        if(mode.equalsIgnoreCase("CAGateway")){
	        httpPost.addHeader("Authorization", System.getProperty("BearerToken"));
	        httpPost.addHeader("X-NG-Date",XNGDate);
	        httpPost.addHeader("X-NG-SessionId",System.getProperty("XNGSessionId"));

	       }
	        else if(mode.equalsIgnoreCase("EnterpriseGateway")){
	        	getAuthSignature(argRouteURL,"POST",argPayload, "");
	        	httpPost.addHeader("Authorization", "NEXTGEN-AMB-API-V2 Credential=" + Email + ", Signature="+ System.getProperty("AuthEnterpriseSignature"));
	        	httpPost.addHeader("X-NG-Date", EnterpriseSignature.NGTime);
	        	httpPost.addHeader("X-NG-Product", "NEXTGEN-AMB-API-V2");
	        	httpPost.addHeader("x-nge-site-id",XNGSessionID);
	        }
	        	        
	        httpPost.setEntity(arg_Payload);
	        httpResponse = httpClient.execute(httpPost);
	        if (mode.equals("CAGateway")) {
	        httpPost.releaseConnection();}

	        Log4jUtil.log("Status code " + httpResponse.getStatusLine().getStatusCode());
	        
	        if(httpResponse.getStatusLine().getStatusCode()==ExpectedStatusCode){
	        	Log4jUtil.log("Patient created successfully");
	        }else{
	        	Log4jUtil.log("Unable to create patient");
	            assertEquals(httpResponse.getStatusLine().getStatusCode(), ExpectedStatusCode);
	        }
	        
	        for(Header headerRes: httpResponse.getAllHeaders()){
	            if(headerRes.getName().equalsIgnoreCase("Location")){
	                strLocationHeader= headerRes.getValue().split("/")[headerRes.getValue().split("/").length-1];
	                Log4jUtil.log("Request processed with ID as " + headerRes.getValue().split("/")[headerRes.getValue().split("/").length-1]);
	            }
	        }
	        return strLocationHeader;

		}
		catch (Exception E) {
			Log4jUtil.log("Exception caught "+E.getMessage());
		}
			return null;
	}
	
	public static String setupNGHttpPostRequest(String mode,String argRouteURL, String argPayload, int ExpectedStatusCode) throws IOException {
		IHGUtil.PrintMethodName();
		try {
			StringEntity arg_Payload=new StringEntity(argPayload, ContentType.APPLICATION_JSON);
		
			String XNGDate = getXNGDate();
			Log4jUtil.log("\n Payload "+arg_Payload.toString());
			Log4jUtil.log("PostURL "+argRouteURL);

			CloseableHttpResponse httpResponse = null;
	        CloseableHttpClient httpClient = HttpClients.createDefault();

	        String strLocationHeader="";
	        String Email= EnterpriseEmail;
	        HttpPost httpPost = new HttpPost(argRouteURL);

	        httpPost.addHeader("Accept", "*/*");
	        httpPost.addHeader("Content-type", "application/json");
	        if(mode.equalsIgnoreCase("CAGateway")){
	        httpPost.addHeader("Authorization", System.getProperty("BearerToken"));
	        httpPost.addHeader("X-NG-Date",XNGDate);
	        httpPost.addHeader("X-NG-SessionId",System.getProperty("XNGSessionId"));
	       }
	        else if(mode.equalsIgnoreCase("EnterpriseGateway")){
	        	getAuthSignature(argRouteURL,"POST",argPayload, "");
	        	httpPost.addHeader("Authorization", "NEXTGEN-AMB-API-V2 Credential=" + Email.toLowerCase() + ", Signature="+ System.getProperty("AuthEnterpriseSignature"));
	        	httpPost.addHeader("X-NG-Date", EnterpriseSignature.NGTime);
	        	httpPost.addHeader("X-NG-Product", "NEXTGEN-AMB-API-V2");
	        	httpPost.addHeader("x-nge-site-id",XNGSessionID);
	        }
	        
	        httpPost.setEntity(arg_Payload);
	        httpResponse = httpClient.execute(httpPost);
	        if (mode.equals("CAGateway")) {
	        httpPost.releaseConnection();}

	        Log4jUtil.log("Status code of Post Request " + httpResponse.getStatusLine().getStatusCode());
	        if(ExpectedStatusCode!=0){
	        if(httpResponse.getStatusLine().getStatusCode()==ExpectedStatusCode){
	        	Log4jUtil.log("Post request completed successfully");
	        }else{
	        	Log4jUtil.log("Unable to post the request, Response is \n"+EntityUtils.toString(httpResponse.getEntity()));
	            assertEquals(httpResponse.getStatusLine().getStatusCode(), ExpectedStatusCode);
	        }}
	        
	        for(Header headerRes: httpResponse.getAllHeaders()){
	            if(headerRes.getName().equalsIgnoreCase("Location")){
	                strLocationHeader= headerRes.getValue().split("/")[headerRes.getValue().split("/").length-1];
	                Log4jUtil.log("Request processed with ID as " + headerRes.getValue().split("/")[headerRes.getValue().split("/").length-1]);
	            }
	        }
	        return strLocationHeader;

		}
		catch (Exception E) {
			Log4jUtil.log("Exception caught "+E.getMessage());
		}
				return null;
	}
	
	
	public static String setupNGHttpGetRequest(String mode,String argRouteURL, int ExpectedStatusCode) throws IOException {
		IHGUtil.PrintMethodName();
		Log4jUtil.log("GetURL "+argRouteURL);
		try {
	    HttpGet httpGet;
		httpGet = new HttpGet(argRouteURL);	
		CloseableHttpResponse httpResponse = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
		
		String XNGDate = getXNGDate();
		String Email= EnterpriseEmail;
		
     	httpGet.addHeader("Accept", "*/*");
     	
	    if(mode.equalsIgnoreCase("CAGateway")){
		httpGet.addHeader("Authorization", System.getProperty("BearerToken"));
		httpGet.addHeader("X-NG-SessionId",System.getProperty("XNGSessionId"));
		httpGet.addHeader("X-NG-Date", XNGDate);
		}
        else if(mode.equalsIgnoreCase("EnterpriseGateway")){
        	getAuthSignature(argRouteURL,"GET","", "");
        	httpGet.addHeader("Authorization", "NEXTGEN-AMB-API-V2 Credential=" + Email.toLowerCase() + ", Signature="+ System.getProperty("AuthEnterpriseSignature"));
        	httpGet.addHeader("X-NG-Date", EnterpriseSignature.NGTime);
        	httpGet.addHeader("X-NG-Product", "NEXTGEN-AMB-API-V2");
        	httpGet.addHeader("x-nge-site-id",XNGSessionID);
        }	
	    httpResponse = httpClient.execute(httpGet);
	    
	    if (mode.equals("CAGateway")) {
		   httpGet.releaseConnection();
	    }
        
	    Log4jUtil.log("Status code of Get request "+httpResponse.getStatusLine().getStatusCode());
	    if(ExpectedStatusCode!=0){
	    if(httpResponse.getStatusLine().getStatusCode()==ExpectedStatusCode){
	    	Log4jUtil.log("Get request completed successfully");
        }else{
        	Log4jUtil.log("Unable to get the request");
            assertEquals(httpResponse.getStatusLine().getStatusCode(), ExpectedStatusCode);
        }}
	
	    HttpEntity entity = httpResponse.getEntity();

        String response = EntityUtils.toString(entity);
        Log4jUtil.log("Get Response "+response);
       return response;
		}catch (Exception E) {
		Log4jUtil.log("Exception caught "+E.getMessage());
	}
	return null;
    }
	
	public static String setupNGHttpPutRequest(String mode,String argRouteURL,String requestbody, int ExpectedStatusCode) throws IOException {
		IHGUtil.PrintMethodName();
		Log4jUtil.log("PutURL "+argRouteURL);
		String strLocationHeader = null;
		try { 	
    		CloseableHttpResponse httpResponse = null;
            CloseableHttpClient httpClient = HttpClients.createDefault();
    		HttpPut httpPut = new HttpPut(argRouteURL);   
    		StringEntity entity=new StringEntity(requestbody);
    	
    		String XNGDate = getXNGDate();
    		String Email= EnterpriseEmail;
    		
    		httpPut.addHeader("Accept", "*/*");
    	    if(mode.equalsIgnoreCase("CAGateway")){
    	    	httpPut.addHeader("Authorization", System.getProperty("BearerToken"));
    	    	httpPut.addHeader("X-NG-SessionId",System.getProperty("XNGSessionId"));
    	    	httpPut.addHeader("X-NG-Date", XNGDate);
    		}
            else if(mode.equalsIgnoreCase("EnterpriseGateway")){
            	getAuthSignature(argRouteURL,"PUT",requestbody, "");
            	httpPut.addHeader("Authorization", "NEXTGEN-AMB-API-V2 Credential=" + Email.toLowerCase() + ", Signature="+ System.getProperty("AuthEnterpriseSignature"));
            	httpPut.addHeader("X-NG-Date", EnterpriseSignature.NGTime);
            	httpPut.addHeader("X-NG-Product", "NEXTGEN-AMB-API-V2");
            	httpPut.addHeader("x-nge-site-id",XNGSessionID);
            }
    		httpPut.setHeader("Content-type", "application/json");
  
    		httpPut.setEntity(entity);
    		httpResponse = httpClient.execute(httpPut);
    	
    		if (mode.equals("CAGateway")) {
    			httpPut.releaseConnection();
    		}
    	        
    		Log4jUtil.log("Status code for Put request "+httpResponse.getStatusLine().getStatusCode());
    		if(ExpectedStatusCode!=0){
    		    if(httpResponse.getStatusLine().getStatusCode()==ExpectedStatusCode){
    		    	Log4jUtil.log("Put request completed successfully");
    	        }else{
    	        	Log4jUtil.log("Unable to put the request");
    	            assertEquals(httpResponse.getStatusLine().getStatusCode(), ExpectedStatusCode);
    	        }}

    	    for(Header headerRes: httpResponse.getAllHeaders()){
    	        if(headerRes.getName().equalsIgnoreCase("Location")){
    	            strLocationHeader= headerRes.getValue().toString();
    	         }
    	    }
        return strLocationHeader;
		}catch (Exception E) {
		Log4jUtil.log("Exception caught "+E.getMessage());
	}
	return null;
    }
	
	public synchronized static void updateLoginDefaultTo(String mode,String enterpriseID, String practiceID) throws Throwable {
		Log4jUtil.log("Step Begins --- API Route Change the Login Defaults: enterpriseID -"+enterpriseID+", practiceID -"+practiceID);

		String strSqlQueryForUserDetails="select top 1 user_id from user_mstr where email_login_id='" + EnterpriseEmail + "' order by create_timestamp";
        String CurrentUserID =DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForUserDetails);

        String LogInDefaultsUrl= BaseURL+apiRoutes.LogInDefaults.getRouteURL().toString().replace("userId", CurrentUserID);
        String GetLogInDefaultResponse = setupNGHttpGetRequest(mode,LogInDefaultsUrl,200);

        String currentPracticeID = CommonUtils.getResponseKeyValue(GetLogInDefaultResponse, "practiceId");
        if(!currentPracticeID.equalsIgnoreCase(practiceID)){
        	Log4jUtil.log("Expected practice id is different from current practice ID");
    	
    		LoginDefaults loginDefaults = new LoginDefaults();
    		loginDefaults.setEnterpriseId(enterpriseID);
    		loginDefaults.setPracticeId(practiceID);
    		ObjectMapper objMap = new ObjectMapper();
            String requestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(loginDefaults);
            Log4jUtil.log("Login Defaults request Body is \n" + requestbody);
            
            String LogInDefaultsID =setupNGHttpPutRequest(mode,LogInDefaultsUrl,requestbody, 200);
            Log4jUtil.log("Login default updated with id " + LogInDefaultsID);   
            }else
            	Log4jUtil.log("Expected practice id "+practiceID+" is same as current practice ID "+currentPracticeID);
    }
	
	public String getRelativeBaseUrl() throws IOException, JSONException{
		String baseURL = apiRoutes.BaseURL.getRouteURL().toString();
	    String response = setupNGHttpGetRequest("EnterpriseGateway",baseURL, 200);
	    String relativeBaseURL ="";
	    String value ="";
	    String actualURL ="";
	    if(!response.isEmpty()){
			JSONArray jsonArr = new JSONArray(response);			
			for(int i =0; i< jsonArr.length(); i++){
				JsonObject jsonObject = new JsonParser().parse(jsonArr.get(i).toString()).getAsJsonObject();
				value = jsonObject.get("productName").toString();
				if(value.contains("NextGen.Api.Edge")){
					relativeBaseURL= jsonObject.get("relativeBaseApiUrl").toString();
					break;
				}
			 }
		}
		else
			Log4jUtil.log("Response is empty");
	    
        if(relativeBaseURL.isEmpty())
        	Log4jUtil.log("Relative Base URL is empty");
        	
	    actualURL = baseURL.replace("/gateway", "")+relativeBaseURL.replace("\"", "")+"/";	    
	    Log4jUtil.log("BaseURL is \n"+actualURL);
	    
	    return actualURL;
	    }
	
	public static void setupNGHttpDeleteRequest(String mode, String argRouteURL, int ExpectedStatusCode) throws IOException {
		IHGUtil.PrintMethodName();
		Log4jUtil.log("DeleteURL "+argRouteURL);
		try { 	
    		CloseableHttpResponse httpResponse = null;
            CloseableHttpClient httpClient = HttpClients.createDefault();            
            HttpDelete htttpDelete = new HttpDelete(argRouteURL);               
    		    	
    		String XNGDate = getXNGDate();
    		String Email= EnterpriseEmail;    		
    		htttpDelete.addHeader("Accept", "*/*");
    	    if(mode.equalsIgnoreCase("CAGateway")){
    	    	htttpDelete.addHeader("Authorization", System.getProperty("BearerToken"));
    	    	htttpDelete.addHeader("X-NG-SessionId",System.getProperty("XNGSessionId"));
    	    	htttpDelete.addHeader("X-NG-Date", XNGDate);
    		}
            else if(mode.equalsIgnoreCase("EnterpriseGateway")){
            	getAuthSignature(argRouteURL,"DELETE","", "");
            	htttpDelete.addHeader("Authorization", "NEXTGEN-AMB-API-V2 Credential=" + Email.toLowerCase() + ", Signature="+ System.getProperty("AuthEnterpriseSignature"));
            	htttpDelete.addHeader("X-NG-Date", EnterpriseSignature.NGTime);
            	htttpDelete.addHeader("X-NG-Product", "NEXTGEN-AMB-API-V2");
            	htttpDelete.addHeader("x-nge-site-id",XNGSessionID);
            }
    	    htttpDelete.setHeader("Content-type", "application/json");

    		httpResponse = httpClient.execute(htttpDelete);
    	
    		if (mode.equals("CAGateway")) {
    			htttpDelete.releaseConnection();
    		}
    	        
    		Log4jUtil.log("Status code for Delete request "+httpResponse.getStatusLine().getStatusCode());
    		if(ExpectedStatusCode!=0){
    		    if(httpResponse.getStatusLine().getStatusCode()==ExpectedStatusCode){
    		    	Log4jUtil.log("Delete request completed successfully");
    	        }else{
    	        	Log4jUtil.log("Unable to delete the request");
    	            assertEquals(httpResponse.getStatusLine().getStatusCode(), ExpectedStatusCode);
    	        }}
		}catch (Exception E) {
		Log4jUtil.log("Exception caught "+E.getMessage());
	}
    }
}
