package com.intuit.ihg.product.integrationplatform.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;

public class RequestUtils {

	private HttpURLConnection postConnection;
	private URLConnection getConnection;
	private static String processingURL;
	private static String getResponse;
	private String output = "";

	private void sendPost(String url, String payload,String externalSystemID, String token) throws Exception {
		try {
		
		Log4jUtil.log("resturl  : " + url);
		//Log4jUtil.log("payload  : " + payload);
		//Log4jUtil.log("externalSystemID  : " + externalSystemID);
		
		URL obj = new URL(url);
		postConnection = (HttpURLConnection) obj.openConnection();

		postConnection.setUseCaches(false);
		postConnection.setRequestMethod("POST");
		postConnection.setRequestProperty("Content-Type", "application/xml");
		postConnection.setRequestProperty("Authorization", "Bearer "+token);
		postConnection.setRequestProperty("ExternalSystemId", externalSystemID);
		postConnection.setDoOutput(true);
		postConnection.setDoInput(true);
		
		Log4jUtil.log("calling sendPost...");
		
		DataOutputStream writeStreamToServer = new DataOutputStream(postConnection.getOutputStream());

		writeStreamToServer.writeBytes(payload);
		writeStreamToServer.flush();
		writeStreamToServer.close();

		int responseCode = postConnection.getResponseCode();
		//Log4jUtil.log("\nSending 'POST' request to URL : " + url);
		Log4jUtil.log("Post Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		Map<String, List<String>> map = postConnection.getHeaderFields();
		for (Map.Entry<String, List<String>> entry : map.entrySet()) {
			if ("Location".equals(entry.getKey())) {
				processingURL = entry.getValue().get(0);
			}
		}
		postConnection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			postConnection.disconnect();
		}
	}

	private String getProcessingUrlStatus(String url,String externalSystemID, String token) throws Exception {
		Log4jUtil.log("Getting processingurl Status:- " + url);
		try {
			URL processingURL = new URL(url);
			getConnection = processingURL.openConnection();
			((HttpURLConnection) getConnection).setRequestMethod("GET");
			getConnection.setRequestProperty("Authorization", "Bearer "+token);
			getConnection.setRequestProperty("ExternalSystemId", externalSystemID);
			
			int responseCode = ((HttpURLConnection) getConnection).getResponseCode();
			Log4jUtil.log("Get Response Code : " + responseCode);
			BufferedReader in = new BufferedReader(new InputStreamReader(getConnection.getInputStream()));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = in.readLine()) != null) {
				response.append(line);
			}
			in.close();
			output = response.toString();
			getStatusMessage(output);
			((HttpURLConnection) getConnection).disconnect();
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} finally {
			((HttpURLConnection) getConnection).disconnect();
		}
		return output;
	}

	public static String getStatus(String url, String payload, String externalSystemID,String token) throws Exception {
		RequestUtils http = new RequestUtils();
		http.sendPost(url, payload,externalSystemID,token);
		Thread.sleep(25000);
		getResponse = http.getProcessingUrlStatus(processingURL,externalSystemID, token);
		Log4jUtil.log("Response is " + getResponse);
		return getResponse;
	}

	public static void getStatusMessage(String xml) throws SAXException, IOException, ParserConfigurationException, InterruptedException {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource inputSrc = new InputSource();
		inputSrc.setCharacterStream(new StringReader(xml));
		Document doc = builder.parse(inputSrc);

		NodeList nodes = doc.getElementsByTagName("State");
		NodeList Errornode = doc.getElementsByTagName("Error");
		
		for (int i = 0; i < nodes.getLength(); i++) {
			if (nodes.item(i).getTextContent().equals("COMPLETED") || nodes.item(i).getTextContent().equals("INPROGRESS")) {
				Log4jUtil.log("Status of "+(i+1)+" node is "+nodes.item(i).getTextContent());
			}
			else
			{
				Log4jUtil.log("Error while processing response: " + Errornode.item(0).getTextContent());
			}
		}
	}
}