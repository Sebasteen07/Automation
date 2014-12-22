package com.intuit.ihg.product.mu2.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.testng.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.intuit.api.security.client.IOAuthTwoLeggedClient;
import com.intuit.api.security.client.OAuthTwoLeggedClient;
import com.intuit.api.security.client.properties.OAuthPropertyManager;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.common.utils.IHGUtil;

	public class UtilityFunctions {
		
		public static List<String> eventList=new ArrayList<String>();
	/**
	 * Sends HTTP Get request and writes response into response.xml	
	 * @param strUrl
	 * @param xmlFilePath
	 * @throws IOException
	 */
	public static void setupHttpGetRequest(String strUrl, String xmlFilePath) throws IOException{
		IHGUtil.PrintMethodName();
		String response="";
    	HttpResponse resp;
    	try{     
    	if(strUrl.contains("exporteventreport")||(xmlFilePath.contains("push_response.xml"))){
    		HttpClient client = new DefaultHttpClient();  
	        Log4jUtil.log("Push API URL"+strUrl);
	        HttpGet httpGetReq = new HttpGet(strUrl);
	        httpGetReq.addHeader("Accept", "application/xml");
	        httpGetReq.addHeader("Content-Type", "application/xml");
	        resp = client.execute(httpGetReq); 
	        Log4jUtil.log("Check for Push API 202 response");
	        Assert.assertTrue(isSuccessfulResponse(resp,MU2Constants.PUSH_API_EXPECTED_RESPONSE),"Push API response is not 202");
    	} else {
	      
        IOAuthTwoLeggedClient oauthClient = new OAuthTwoLeggedClient();
        Log4jUtil.log("Pull API URL"+strUrl);
        HttpGet httpGetReq = new HttpGet(strUrl);
        resp = oauthClient.httpGetRequest(httpGetReq);
        Log4jUtil.log("Check for Pull API 200 response");
        Assert.assertTrue(isSuccessfulResponse(resp,MU2Constants.PULL_API_EXPECTED_RESPONSE),"Pull API response is not 200");
    	}
    	
        HttpEntity entity = resp.getEntity();
        response=readResponse(entity.getContent());
      
        FileWriter out = new FileWriter(xmlFilePath);  
        out.write(response);  
        if (out != null) {
            out.close();
        }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 	
 	}     
   	/**
	 * Reads the contents from an InputStream and captures them in a String
	 * @param is InputStream object
	 * @return String that contains the content read from InputStream
	 */
	public static String readResponse(InputStream is) {
		IHGUtil.PrintMethodName();
		StringBuilder response = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			reader.close();
		} catch (IOException ioe) {
		}
		return response.toString();
	}
		
	
	
	
	
	/**
	 * Get http response in xml format and check for VDT events in response xml
	 * @param xmlFileName
	 * @param event
	 * @param resourceType
	 * @param action
	 * @return
	 */
	public static String FindEventInResonseXML(String xmlFileName,String event,String resourceType,String action,Long timeStamp,String practicePatientID) {
		IHGUtil.PrintMethodName();

		String ActionTimestamp = null;
		try {
		
		File stocks = new File(xmlFileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(stocks);
		doc.getDocumentElement().normalize();
	
		NodeList nodes = doc.getElementsByTagName(event);
		for (int i = 0; i < nodes.getLength(); i++) {
			
		Node node = nodes.item(i);
		if (node.getNodeType() == Node.ELEMENT_NODE) {
		Element element = (Element) node;
		String readValue;
		readValue=getValue(MU2Constants.EVENT_RECORDED_TIMESTAMP,element);
		Long recordedTimeStamp=Long.valueOf(readValue);
		if(recordedTimeStamp >= timeStamp)
		{
			
		if(getValue(MU2Constants.RESOURCE_TYPE_NODE, element).equalsIgnoreCase(resourceType)&& getValue(MU2Constants.ACTION_NODE, element).equalsIgnoreCase(action)&&getValue(MU2Constants.INTUIT_PATIENT_ID, element).equalsIgnoreCase(practicePatientID))
			{
			
			ActionTimestamp=getValue(MU2Constants.ACTION_TIMESTAMP,element);
			
			break;
			}
		}
		else
		{
			//Log4jUtil.log("Event is not found in the pull events response XML");
		}
		}
		}
		} catch (Exception ex) {
		ex.printStackTrace();
		}
	return ActionTimestamp;
	
	}

		private static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
		}
		
		
		
		/**
		 * Get the URL (the common part of it for all messages) switches from testcase and 
		 * return the constructed URL
		 * 
		 * @return String that represents the URL
		 */
		public static String getURL(String protocol,String host,String port,String apipath) {
			IHGUtil.PrintMethodName();
			return protocol + "://" +host + ":"	+ port + "/" +apipath;
		}
		
		
		
		/**
		 * Get the URL (the common part of it for all messages) switches from testcase and 
		 * return the constructed URL
		 * 
		 * @return String that represents the URL
		 */
		public static String getURL(String protocol,String host,String port,String apipath,String subject) {
			IHGUtil.PrintMethodName();
			return protocol + "://" +host + ":"	+ port + "/" +apipath + ":"+subject;
		}
		
		
		/**
		 * Check for 200 response in Push API response.
		 * @param response
		 * @return
		 */
		
		public static boolean isSuccessfulResponse(HttpResponse response,String expectedResponse) {
					
			if(response.getStatusLine().toString().equalsIgnoreCase(expectedResponse))	
				return true;
			else
				return false;
		
		}
		
		
		/**
		 * Oauth Setup 
		 * @throws Exception 
		 */
		
		public static void oauthSetup(String oAuthKeySStorePath,String oAuthProperty, String responsePath) throws Exception {
			IHGUtil.PrintMethodName();
			emptyFile(oAuthKeySStorePath);		
			OAuthPropertyManager.init(oAuthProperty);
			OAuthClientBootstrapMain.main(null);
			emptyFile(responsePath);
		} 
		
		public static void emptyFile(String file) throws IOException {
			File outputFile = new File(file);
			try {
				if(outputFile.exists()){
					//logger.debug("deleting target file if present");
				outputFile.delete();
				}	
				} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		
		
		/**
		 * 
		 * @param xmlFileName
		 * @param patientFirstName
		 * @param patientLastName
		 * @param action
		 * @return
		 *		
		 */
				
		public static boolean findPushEventsInResonseXML(String xmlFileName,String patientFirstName,String patientLastName,String action) {
			boolean isEventPresent = false;
			try {
			IHGUtil.PrintMethodName();
			File stocks = new File(xmlFileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(stocks);
			doc.getDocumentElement().normalize();
		
			NodeList nodes = doc.getElementsByTagName(MU2Constants.PUSH_API_ACTIVITY_NODE);
			
			for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			
			if(getValue(MU2Constants.PUSH_API_PATIENT_FIRSTNAME_NODE, element).equalsIgnoreCase(patientFirstName)&& getValue(MU2Constants.PUSH_API_PATIENT_LASTNAME_NODE, element).equalsIgnoreCase(patientLastName)
					&&(getValue(MU2Constants.PUSH_API_ACTION_NODE, element).equalsIgnoreCase(action))){
				isEventPresent= true;
				break;
					}
				}
			}
			} catch (Exception ex) {
			ex.printStackTrace();
			}
		return isEventPresent;		
		
		}
		
		
		/**
		 * Generate Portal event time according to event time in Response 
		 * @param actualTimeStamp
		 * @throws ParseException 
		 * 
		 */
		
		public static String generateDate(String actualTimeStamp) throws ParseException{
			
			final long HOUR = 3600*1000;// in milli-seconds.
		   	DateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		   	Date now = FORMATTER.parse(actualTimeStamp);
		   	long hours=now.getTime() - 5 * HOUR;
		   	long expecteDate = Long.parseLong(Long.toString(hours));
		    Date date = new Date(expecteDate);   	
		  	DateFormat formatDate = new SimpleDateFormat("MM/dd/yyyy");
		    DateFormat formatTime = new SimpleDateFormat("HH:mm");		  	
		  	String joinedDate=new StringBuilder(formatDate.format(date).toString()).append(" at ").append(formatTime.format(date).toString()).toString();
			return joinedDate;
		}
		
		public static List<String> eventList() {
			eventList.add(MU2Constants.VIEW_ACTION);
			eventList.add(MU2Constants.DOWNLOAD_ACTION);
			eventList.add(MU2Constants.TRANSMIT_ACTION);
			return eventList;
		}  
		
}
