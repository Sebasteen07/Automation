//Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.mu2.utils;

import static org.testng.Assert.assertTrue;

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
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.intuit.api.security.client.IOAuthTwoLeggedClient;
import com.intuit.api.security.client.OAuthTwoLeggedClient;
import com.intuit.api.security.client.properties.OAuthPropertyManager;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.common.utils.IHGUtil;

public class UtilityFunctions {

	public static List<String> eventList = new ArrayList<String>();
	
	public static void setupHttpGetRequest(String strUrl, String xmlFilePath) throws IOException {
		IHGUtil.PrintMethodName();
		String response = "";
		HttpResponse resp;
		try {
			if (strUrl.contains("exporteventreport") || (xmlFilePath.contains("push_response.xml"))) {
				HttpClient client = new DefaultHttpClient();
				Log4jUtil.log("Push API URL" + strUrl);
				HttpGet httpGetReq = new HttpGet(strUrl);
				httpGetReq.addHeader("Accept", "application/xml");
				httpGetReq.addHeader("Content-Type", "application/xml");
				resp = client.execute(httpGetReq);
				Log4jUtil.log("Check for Push API 202 response");
				assertTrue(isSuccessfulResponse(resp, MU2Constants.PUSH_API_EXPECTED_RESPONSE), "Push API response is not 202");
			} else {

				IOAuthTwoLeggedClient oauthClient = new OAuthTwoLeggedClient();
				Log4jUtil.log("Pull API URL" + strUrl);
				HttpGet httpGetReq = new HttpGet(strUrl);
				resp = oauthClient.httpGetRequest(httpGetReq);
				Log4jUtil.log("Check for Pull API 200 response");
				assertTrue(isSuccessfulResponse(resp, MU2Constants.PULL_API_EXPECTED_RESPONSE), "Pull API response is not 200");
			}

			HttpEntity entity = resp.getEntity();
			response = readResponse(entity.getContent());

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

	public static String FindEventInResonseXML(String xmlFileName, String event, String resourceType, String action, Long timeStamp, String practicePatientID) {
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
					readValue = getValue(MU2Constants.EVENT_RECORDED_TIMESTAMP, element);
					Long recordedTimeStamp = Long.valueOf(readValue);
					if (recordedTimeStamp >= timeStamp) {

						if (getValue(MU2Constants.RESOURCE_TYPE_NODE, element).equalsIgnoreCase(resourceType)
								&& getValue(MU2Constants.ACTION_NODE, element).equalsIgnoreCase(action)
								&& getValue(MU2Constants.INTUIT_PATIENT_ID, element).equalsIgnoreCase(practicePatientID)) {

							ActionTimestamp = getValue(MU2Constants.EVENT_RECORDED_TIMESTAMP, element);

							break;
						}
					} else {
						// Log4jUtil.log("Event is not found in the pull events response XML");
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
	
	public static String getURL(String protocol, String host, String port, String apipath) {
		IHGUtil.PrintMethodName();
		return protocol + "://" + host + ":" + port + "/" + apipath;
	}

	public static String getURL(String protocol, String host, String port, String apipath, String subject) {
		IHGUtil.PrintMethodName();
		return protocol + "://" + host + ":" + port + "/" + apipath + ":" + subject;
	}

	public static boolean isSuccessfulResponse(HttpResponse response, String expectedResponse) {
		if (response.getStatusLine().toString().equalsIgnoreCase(expectedResponse))
			return true;
		else
			return false;
	}

	public static void oauthSetup(String oAuthKeySStorePath, String oAuthProperty, String responsePath) throws Exception {
		IHGUtil.PrintMethodName();
		emptyFile(oAuthKeySStorePath);
		OAuthPropertyManager.init(oAuthProperty);
		OAuthClientBootstrapMain.main(null);
		emptyFile(responsePath);
	}

	public static void emptyFile(String file) throws IOException {
		File outputFile = new File(file);
		try {
			if (outputFile.exists()) {
				// logger.debug("deleting target file if present");
				outputFile.delete();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean findPushEventsInResonseXML(String xmlFileName, String patientFirstName, String patientLastName, String action) {
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

					if (getValue(MU2Constants.PUSH_API_PATIENT_FIRSTNAME_NODE, element).equalsIgnoreCase(patientFirstName)
							&& getValue(MU2Constants.PUSH_API_PATIENT_LASTNAME_NODE, element).equalsIgnoreCase(patientLastName)
							&& (getValue(MU2Constants.PUSH_API_ACTION_NODE, element).equalsIgnoreCase(action))) {
						isEventPresent = true;
						break;
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return isEventPresent;

	}

	public static String generateDate(String actualTimeStamp) throws ParseException {
		long EventRecordedTimestamp = Long.parseLong(actualTimeStamp);
		Date date = new Date(EventRecordedTimestamp);
		DateFormat gmtFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm ");
		TimeZone estTime = TimeZone.getTimeZone("America/New_York");
		gmtFormat.setTimeZone(estTime);
		String data[] = gmtFormat.format(date).split(" ");
		String joinedDate = new StringBuilder(data[0]).append(" at ").append(data[1]).toString();
		return joinedDate;
	}

	public static List<String> eventList() {
		eventList.add(MU2Constants.VIEW_ACTION);
		eventList.add(MU2Constants.DOWNLOAD_ACTION);
		eventList.add(MU2Constants.TRANSMIT_ACTION);
		return eventList;
	}

}
