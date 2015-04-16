	package com.intuit.ihg.product.integrationplatform.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Random;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.intuit.api.security.client.IOAuthTwoLeggedClient;
import com.intuit.api.security.client.OAuthTwoLeggedClient;
import com.intuit.api.security.client.TokenManager;
import com.intuit.api.security.client.properties.OAuthPropertyManager;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.common.utils.IHGUtil;


public class OauthUtils {
		
	/**
	 * Performs OAuth Get Request and saves the resposse
	 * @param strUrl server Get url
	 * @param responseFilePath path to save the response
	 * @throws IOException 
	 */
	public static void setupHttpGetRequest(String strUrl, String responseFilePath)
			throws IOException {
		IHGUtil.PrintMethodName();

		IOAuthTwoLeggedClient oauthClient = new OAuthTwoLeggedClient();
        Log4jUtil.log("Get Request Url: "+ strUrl);
        HttpGet httpGetReq = new HttpGet(strUrl);
        httpGetReq.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000)
        .setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
        httpGetReq.addHeader("ExternalSystemId", "79");
        HttpResponse resp = oauthClient.httpGetRequest(httpGetReq);
        //Log4jUtil.log("Response" +resp);
        HttpEntity entity = resp.getEntity();
        String sResp=null;
        if(entity!=null){
        	sResp = EntityUtils.toString(entity);
            Log4jUtil.log("Check for http 200 response");
            Assert.assertTrue(resp.getStatusLine().getStatusCode() == 200,
    				"Get Request response is " + resp.getStatusLine().getStatusCode() + " instead of "+ 200 +". Response message received:\n" + sResp);
             
           }
            else
            {
            	Log4jUtil.log("204 response found");
            	
            }
	}

	/**
	 * Reads the contents from an InputStream and captures them in a String
	 * @param xmlFilePath path where to store XML.
	 * @param xml String xml to store
	 */
	private static void writeFile(String xmlFilePath, String xml)
			throws IOException {
		FileWriter out = new FileWriter(xmlFilePath);
		out.write(xml);
		if (out != null) {
			out.close();
		}
	}



	public static String prepareSecureMessage(String xmlFileName, String from, String to, String subject, String messageID) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);
		
		//get message root element
		Node node = doc.getElementsByTagName(IntegrationConstants.SECURE_MESSAGE).item(0);
		Element elem = (Element) node;
		
		//set random message id
		elem.setAttribute(IntegrationConstants.MESSAGE_ID, elem.getAttribute(IntegrationConstants.MESSAGE_ID) + fourDigitRandom());
		
		//set other attributes
		Node nFrom = elem.getElementsByTagName(IntegrationConstants.FROM).item(0);
		Node nTo = elem.getElementsByTagName(IntegrationConstants.TO).item(0);
		Node nSubject = elem.getElementsByTagName(IntegrationConstants.SUBJECT).item(0);
		
		nFrom.setTextContent(from);
		nTo.setTextContent(to);
		nSubject.setTextContent(subject);
		if(messageID!=null){
		Node nMessageThreadId = elem.getElementsByTagName(IntegrationConstants.MESSAGE_THREAD_ID).item(0);
		nMessageThreadId.setTextContent(messageID);
		}
		return domToString(doc);
	}
	
	//generate four digit random for the message id
	public static int fourDigitRandom(){
		return (new Random()).nextInt(9000) + 1000;
	}
	
	public static String preparePatient(String xmlFileName, String practicePatientId, String firstName, String lastName, String email, String medfusionPatientID) throws ParserConfigurationException, SAXException, IOException, TransformerException{
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);
		
		Node idNode = doc.getElementsByTagName(IntegrationConstants.PRACTICE_PATIENT_ID).item(0);
		idNode.setTextContent(practicePatientId);
		
		Node patient = doc.getElementsByTagName(IntegrationConstants.PATIENT).item(0);
		NodeList childNodes = patient.getChildNodes();
		for(int i = 0; i < childNodes.getLength(); i++){
			if(childNodes.item(i).getNodeType() == Node.ELEMENT_NODE && childNodes.item(i).getNodeName().equals(IntegrationConstants.NAME)){
				Element name = (Element) childNodes.item(i);
				Node firstNameNode = name.getElementsByTagName(IntegrationConstants.FIRST_NAME).item(0);
				Node lastNameNode = name.getElementsByTagName(IntegrationConstants.LAST_NAME).item(0);
				firstNameNode.setTextContent(firstName);
				lastNameNode.setTextContent(lastName);
			}
			if(childNodes.item(i).getNodeType() == Node.ELEMENT_NODE && childNodes.item(i).getNodeName().equals(IntegrationConstants.EMAIL_ADDRESS)){
				childNodes.item(i).setTextContent(email);
			}
		}
		if(medfusionPatientID!=null){
		Node idNode1 = doc.getElementsByTagName(IntegrationConstants.MEDFUSIONPATIENTID).item(0);
		idNode1.setTextContent(medfusionPatientID);}
		
		return domToString(doc);
	}
	
	public static String domToString(Document doc) throws TransformerException{
		// convert prepared xml to String
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		transformer.transform(source, result);

		return writer.toString();
	}
	
	
	/**
	 * Performs OAuth Post Request and saves the resposse
	 * @param strUrl server Post url
	 * @param payload Post payload
	 * @param responseFilePath path to save the response
	 * @return Processing Status header from Response
	 * @throws IOException 
	 */
	public static String setupHttpPostRequest(String strUrl, String payload, String responseFilePath) throws IOException{
		IHGUtil.PrintMethodName();
    	
		IOAuthTwoLeggedClient oauthClient = new OAuthTwoLeggedClient();
        Log4jUtil.log("Post Request Url: "+ strUrl);
        HttpPost httpPostReq = new HttpPost(strUrl);
        httpPostReq.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000)
        .setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
        
        StringEntity se = new StringEntity(payload);
        httpPostReq.setEntity(se);
        httpPostReq.addHeader("Accept", "application/xml");
        httpPostReq.addHeader("Content-Type", "application/xml");
        httpPostReq.addHeader("ExternalSystemID", "79");
        Log4jUtil.log("Post Request Url4: ");
        HttpResponse resp = oauthClient.httpPostRequest(httpPostReq);

        String sResp = EntityUtils.toString(resp.getEntity());
        
		Log4jUtil.log("Check for http 200/202 response");
		Assert.assertTrue(resp.getStatusLine().getStatusCode() == 200
				|| resp.getStatusLine().getStatusCode() == 202,
				"Get Request response is "
						+ resp.getStatusLine().getStatusCode()
						+ " instead of 200/202. Response message:\n"
						+ sResp);
		Log4jUtil.log("Response Code" +resp.getStatusLine().getStatusCode());
		writeFile(responseFilePath, sResp);
		
		
			Header[] h=resp.getHeaders(IntegrationConstants.LOCATION_HEADER);
			return h[0].getValue();
		
    	}
	
	private static Document buildDOMXML(String xmlFileName)
			throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		File response = new File(xmlFileName);

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(response);
		doc.getDocumentElement().normalize();
		return doc;
	}

	

	public static String prepareCCD(String ccdPath) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Document doc = buildDOMXML(ccdPath);
		return domToString(doc);
		
	}
	
	
	/**
	 * 
	 * @param xmlFilePath
	 * @return
	 */
	public static String convertXMLFileToString(String fileName) {
		IHGUtil.PrintMethodName();
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
					.newInstance();
			InputStream inputStream = new FileInputStream(new File(fileName));
			org.w3c.dom.Document doc = documentBuilderFactory
					.newDocumentBuilder().parse(inputStream);
			StringWriter stw = new StringWriter();
			Transformer serializer = TransformerFactory.newInstance()
					.newTransformer();
			serializer.setOutputProperty("omit-xml-declaration", "yes");
			serializer.transform(new DOMSource(doc), new StreamResult(stw));
			return stw.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
	
	/*
	 * 
	 */
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
	
    
	public static boolean oauthSetup1O(String oAuthKeyStore, String oAuthProperty,
			String oAuthAppToken, String oAuthUsername, String oAuthPassword) throws IOException {
		// TODO Auto-generated method stub
		boolean found=true;
		emptyFile(oAuthKeyStore);		
		OAuthPropertyManager.init(oAuthProperty);
		System.out.println("appToken: " +oAuthAppToken);
		System.out.println("username: " +oAuthUsername);
		System.out.println("password: " +oAuthPassword);
		try {
			TokenManager.initializeTokenStore(oAuthAppToken, oAuthUsername, oAuthPassword);
		
		} catch (Exception hException) {
			// TODO Auto-generated catch block
			hException.getCause().printStackTrace();
			found=false;
			return found;
		}
		return found;
	}

}

