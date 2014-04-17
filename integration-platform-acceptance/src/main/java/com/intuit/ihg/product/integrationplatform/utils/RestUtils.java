	package com.intuit.ihg.product.integrationplatform.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
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
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.springframework.aop.ThrowsAdvice;
import org.testng.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.intuit.api.security.client.IOAuthTwoLeggedClient;
import com.intuit.api.security.client.OAuthTwoLeggedClient;
import com.intuit.api.security.client.OAuth20TokenManager;
import com.intuit.api.security.client.OAuth2Client;
import com.intuit.api.security.client.TokenManager;
import com.intuit.api.security.client.properties.OAuthPropertyManager;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ifs.csscat.core.BaseTestSoftAssert;

public class RestUtils {

	/**
	 * Performs OAuth Get Request and saves the resposse
	 * @param strUrl server Get url
	 * @param responseFilePath path to save the response
	 * @throws IOException 
	 */
	public static void setupHttpGetRequest(String strUrl, String responseFilePath)
			throws IOException {
		IHGUtil.PrintMethodName();

		IOAuthTwoLeggedClient oauthClient = new OAuth2Client();
        Log4jUtil.log("Get Request Url: "+ strUrl);
        HttpGet httpGetReq = new HttpGet(strUrl);
        httpGetReq.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000)
        .setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
        HttpResponse resp = oauthClient.httpGetRequest(httpGetReq);

		
        HttpEntity entity = resp.getEntity();
        String sResp = EntityUtils.toString(entity);
        
        Log4jUtil.log("Check for http 200 response");
		Assert.assertTrue(resp.getStatusLine().getStatusCode() == 200,
				"Get Request response is " + resp.getStatusLine().getStatusCode() + " instead of 200. Response message received:\n" + sResp);

		writeFile(responseFilePath, sResp);
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


	/**
	 * Reads the XML and checks asked Question if it complies
	 * @param xmlFileName XML to check
	 * @param Long timestamp of a sent Question to check
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public static void isQuestionResponseXMLValid(String xmlFileName, Long timestamp) throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);

		Log4jUtil.log("finding sent message");
		boolean found = false;
		NodeList nodes = doc.getElementsByTagName(IntegrationConstants.QUESTION_SUBJECT);
		Node node = null;
		for (int i = 0; i < nodes.getLength(); i++) {

			node = nodes.item(i);
			System.out.println("Searching: " + node.getChildNodes().item(0).getTextContent() + ", to be found: " + (timestamp.toString()));
			if (node.getChildNodes().item(0).getTextContent().contains(timestamp.toString())) {
				found = true;
				break;
			}
		}
		Assert.assertTrue(found, "Question was not found in response XML");
		
		Log4jUtil.log("finding QuestionType and Message element");
		boolean questionTypefound = false;
		boolean messageFound = false;
		Node parent = node.getParentNode();
		NodeList childrens = parent.getChildNodes();
		for (int j = 0; j < childrens.getLength(); j++) {

			if (childrens.item(j).getNodeType() == Node.ELEMENT_NODE) {

				if (childrens.item(j).getNodeName().equals(IntegrationConstants.QUESTION_TYPE)) {
					questionTypefound = true;
				}
				if (childrens.item(j).getNodeName().equals(IntegrationConstants.QUESTION_MESSAGE)) {
					messageFound = true;
				}
			}
		}
		Assert.assertTrue(questionTypefound, "QuestionType element was not found in response XML");
		Assert.assertTrue(messageFound, "Message element was not found in response XML");
		Log4jUtil.log("response is ok");
	}
	
	/**
	 * Reads the XML and checks REASON 
	 * @param xmlFileName XML to check
	 * @param Long timestamp of a sent Reason to check
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public static void isReasonResponseXMLValid(String xmlFileName, String reason) throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);

		Log4jUtil.log("finding reason message");
		boolean found = false;
		NodeList nodes = doc.getElementsByTagName(IntegrationConstants.REASON);
		Node node = null;
		for (int i = 0; i < nodes.getLength(); i++) 
		{
			node = nodes.item(i);
			Log4jUtil.log("Searching: " + node.getChildNodes().item(0).getTextContent() + ", to be found: " + (reason));
			if (node.getChildNodes().item(0).getTextContent().contains(reason))
			{
				found = true;
				Log4jUtil.log("Reason is found.");
				break;
			}
		}
		Assert.assertTrue(found, "Reason was not found in response XML");
		Log4jUtil.log("response is ok");
	}
	
	/**
	 * Reads the XML and checks asked Question if it complies
	 * @param xmlFileName XML question to prepare
	 * @param from sender of a message - external System ID
	 * @param to recipient of a Message - external Patient ID
	 * @param subject message subject
	 * @return XML message as a String
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws TransformerException 
	 */
	public static String prepareSecureMessage(String xmlFileName, String from, String to, String subject) throws ParserConfigurationException, SAXException, IOException, TransformerException {
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

		return domToString(doc);
	}
	
	//generate four digit random for the message id
	public static int fourDigitRandom(){
		return (new Random()).nextInt(9000) + 1000;
	}
	
	public static String preparePatient(String xmlFileName, String practicePatientId, String firstName, String lastName, String email) throws ParserConfigurationException, SAXException, IOException, TransformerException{
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
    	
    	IOAuthTwoLeggedClient oauthClient = new OAuth2Client();
        Log4jUtil.log("Post Request Url: "+ strUrl);
        HttpPost httpPostReq = new HttpPost(strUrl);
        httpPostReq.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000)
        .setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
        
        StringEntity se = new StringEntity(payload);
        httpPostReq.setEntity(se);
        httpPostReq.addHeader("Accept", "application/xml");
        httpPostReq.addHeader("Content-Type", "application/xml");
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
		writeFile(responseFilePath, sResp);
		
        Header[] h = resp.getHeaders(IntegrationConstants.LOCATION_HEADER);
        return h[0].getValue();
    	}
	
	/**
	 * Sets up OAuth for requests
	 * @param oAuthKeySStorePath
	 * @param oAuthProperty 
	 * @param responsePath
	 * @throws Exception 
	 */
	public static void oauthSetup(String oAuthKeySStorePath,String oAuthProperty, String appToken, String username, String password) throws Exception {
		IHGUtil.PrintMethodName();
		emptyFile(oAuthKeySStorePath);		
		OAuthPropertyManager.init(oAuthProperty);
		System.out.println("appToken: " +appToken);
		System.out.println("username: " +username);
		System.out.println("password: " +password);
		try {
			OAuth20TokenManager.initializeTokenStore(appToken, username, password);
		} catch (Exception hException) {
			// TODO Auto-generated catch block
			hException.getCause().printStackTrace();
		}
		//System.out.println("appToken: " +appToken);
		//System.out.println("username: " +username);
		//System.out.println("password: " +password);
		
		//emptyFile(responsePath);
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
	
	public static boolean isMessageProcessingCompleted(String xmlFileName) throws ParserConfigurationException, SAXException, IOException {
		Document doc = buildDOMXML(xmlFileName);

		NodeList nodes = doc.getElementsByTagName(IntegrationConstants.PROCESSING_STATE);
		BaseTestSoftAssert.verifyTrue(nodes.getLength() == 2, "There should be 2 State elements in processing status response");
		
		return (nodes.item(0).getTextContent().equals(IntegrationConstants.STATE_COMPLETED) && nodes.item(1).getTextContent().equals(IntegrationConstants.STATE_COMPLETED));
	}

	/**
	 * Checks if the patient address lines are the same as in xml response
	 * @param xmlFileName response xml path
	 * @param patientId id of a patient to check
	 * @param firstLine first address line
	 * @param secondLine second address line
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 */
	public static void isPatientUpdated(String xmlFileName, String patientId , String firstLine, String secondLine) throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);
		
		NodeList nodes = doc.getElementsByTagName(IntegrationConstants.PRACTICE_ID);
		for (int i = 0; i < nodes.getLength(); i++){
			if(nodes.item(i).getTextContent().equals(patientId)){
				Element patient = (Element) nodes.item(i).getParentNode().getParentNode();
				Element homeAddress = (Element) patient.getElementsByTagName(IntegrationConstants.HOME_ADDRESS).item(0);
				Node line1 = homeAddress.getElementsByTagName(IntegrationConstants.LINE1).item(0);
				Node line2 = homeAddress.getElementsByTagName(IntegrationConstants.LINE2).item(0);
				Assert.assertEquals(line1.getTextContent(), firstLine, "The actual value of Line 1 address doesnt equal the updated value");
				Assert.assertEquals(line2.getTextContent(), secondLine, "The actual value of Line 2 address doesnt equal the updated value");
				break;
			}
			if(i == nodes.getLength() - 1){
				Assert.fail("Patient was not found");
			}
		}
		
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

	
	public static void isReplyPresent(String responsePath, String messageIdentifier) throws ParserConfigurationException, SAXException, IOException {
		Document doc = buildDOMXML(responsePath);

		Log4jUtil.log("finding sent message");
		boolean found = false;
		NodeList nodes = doc.getElementsByTagName(IntegrationConstants.QUESTION_SUBJECT);
		Node node = null;
		for (int i = 0; i < nodes.getLength(); i++) {

			node = nodes.item(i);
			Log4jUtil.log("Searching: " + node.getChildNodes().item(0).getTextContent() + ", to be found: " + (messageIdentifier.toString()));
			if (node.getChildNodes().item(0).getTextContent().contains(messageIdentifier.toString())) {
				Element question = (Element) node.getParentNode();
				Node message = question.getElementsByTagName(IntegrationConstants.QUESTION_MESSAGE).item(0);
				Assert.assertEquals(message.getChildNodes().item(0).getTextContent(), IntegrationConstants.MESSAGE_REPLY, "Received reply is not the same as sent");
				found = true;
				break;
			}
		}
		Assert.assertTrue(found, "Reply was not found in response XML");
	}

	public static void isPatientRegistered(String xmlFileName, String practicePatientId) throws ParserConfigurationException, SAXException, IOException {
		Document doc = buildDOMXML(xmlFileName);
		NodeList patients = doc.getElementsByTagName(IntegrationConstants.PRACTICE_PATIENT_ID);
		boolean found = false;
		for(int i = 0; i < patients.getLength(); i++){
			if(patients.item(i).getTextContent().equals(practicePatientId)){
				Element patient = (Element) patients.item(i).getParentNode().getParentNode();
				Node status = patient.getElementsByTagName(IntegrationConstants.STATUS).item(0);
				Assert.assertEquals(status.getTextContent(), IntegrationConstants.REGISTERED, "Patient has different status than expected. Status is: " + status.getTextContent());
				found = true;
				break;
			}
		}
		Assert.assertTrue(found, "Patient was not found in the response XML");
		
		
				
	}

	public static String prepareCCD(String ccdPath) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Document doc = buildDOMXML(ccdPath);
		return domToString(doc);
		
	}
	
		
}
