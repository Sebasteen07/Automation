// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.integrationplatform.utils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.jdom.JDOMException;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.intuit.api.security.client.IOAuthTwoLeggedClient;
import com.intuit.api.security.client.OAuth20TokenManager;
import com.intuit.api.security.client.OAuth2Client;
import com.intuit.api.security.client.properties.OAuthPropertyManager;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.common.utils.mail.GmailBot;
import com.intuit.ihg.product.integrationplatform.pojo.PIDCInfo;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.patientportal2.utils.JalapenoConstants;

public class RestUtils {

	static Random random = new Random();
	public static String gnMessageID;
	public static String SigCodeAbbreviation;
	public static String SigCodeMeaning;
	public static String gnMessageThreadID;
	public static String paymentID;
	public static String headerUrl;
	public static int responseCode;
	public static List<String> patientDatails = new ArrayList<String>();

	/**
	 * Performs OAuth Get Request and saves the resposse
	 * 
	 * @param strUrl           server Get url
	 * @param responseFilePath path to save the response
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static String setupHttpGetRequest(String strUrl, String responseFilePath)
			throws IOException, InterruptedException {
		IHGUtil.PrintMethodName();

		IOAuthTwoLeggedClient oauthClient = new OAuth2Client();
		Log4jUtil.log("Get Request Url: " + strUrl);
		HttpGet httpGetReq = new HttpGet(strUrl);
		httpGetReq.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000)
				.setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
		// httpGetReq.addHeader("ExternalSystemId", "82");
		Thread.sleep(10000);
		HttpResponse resp = oauthClient.httpGetRequest(httpGetReq);
		Log4jUtil.log("Response" + resp);
		HttpEntity entity = resp.getEntity();
		String sResp = null;
		if (entity != null) {
			sResp = EntityUtils.toString(entity);
			Log4jUtil.log("Check for http 200 response");
			assertTrue(resp.getStatusLine().getStatusCode() == 200, "Get Request response is "
					+ resp.getStatusLine().getStatusCode() + " instead of 200. Response message received:\n" + sResp);
			Thread.sleep(10000);
			Log4jUtil.log("GET sResp=" + sResp);
			writeFile(responseFilePath, sResp);

			if (resp.containsHeader(IntegrationConstants.TIMESTAMP_HEADER)) {
				Header[] h = resp.getHeaders(IntegrationConstants.TIMESTAMP_HEADER);
				return h[0].getValue();

			}
		} else {
			Log4jUtil.log("204 response found");

		}
		return null;

	}

	/**
	 * Reads the contents from an InputStream and captures them in a String
	 * 
	 * @param xmlFilePath path where to store XML.
	 * @param xml         String xml to store
	 */
	public static void writeFile(String xmlFilePath, String xml) throws IOException {
		FileWriter out = new FileWriter(xmlFilePath);
		out.write(xml);
		if (out != null) {
			out.close();
		}
		IHGUtil.PrintMethodName();
	}

	/**
	 * Reads the XML and checks asked Question if it complies
	 * 
	 * @param xmlFileName XML to check
	 * @param Long        timestamp of a sent Question to check
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static void isQuestionResponseXMLValid(String xmlFileName, Long timestamp)
			throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);

		Log4jUtil.log("finding sent message");
		boolean found = false;
		NodeList nodes = doc.getElementsByTagName(IntegrationConstants.QUESTION_SUBJECT);
		Node node = null;
		for (int i = 0; i < nodes.getLength(); i++) {

			node = nodes.item(i);
			System.out.println("Searching: " + node.getChildNodes().item(0).getTextContent() + ", to be found: "
					+ (timestamp.toString()));
			if (node.getChildNodes().item(0).getTextContent().contains(timestamp.toString())) {
				Element ele = (Element) nodes.item(i).getParentNode();
				Node messageThreadID = ele.getElementsByTagName(IntegrationConstants.MESSAGE_THREAD_ID).item(0);
				gnMessageThreadID = messageThreadID.getTextContent();
				found = true;
				break;
			}
		}
		assertTrue(found, "Question was not found in response XML");

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
		assertTrue(questionTypefound, "QuestionType element was not found in response XML");
		assertTrue(messageFound, "Message element was not found in response XML");
		Log4jUtil.log("response is ok");
	}

	// retrive message Thread ID
	public static String messageThreadID() {
		return gnMessageThreadID;
	}

	/**
	 * Reads the XML and checks REASON
	 * 
	 * @param xmlFileName XML to check
	 * @param Long        timestamp of a sent Reason to check
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	
	public static String GetAppointmentId(String xmlFileName)
			throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);

		String Id = "";
		Log4jUtil.log("Finding Appointment Id");
		NodeList nodes = doc.getElementsByTagName(IntegrationConstants.APPOINTMENTID);
		Node node = null;
		for (int i = 0; i < nodes.getLength(); i++) {
			node = nodes.item(i);
			Element element = (Element) node;
			Id = element.getAttribute("id");
			Log4jUtil.log("Appointment Id is :" + Id);
		}
		Log4jUtil.log("response is ok");
		return Id;
	}

	public static String VerifyAppointmentId(String xmlFileName, String AppointmentId)
			throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);

		Log4jUtil.log("Finding Appointment Id");
		boolean found = false;
		NodeList nodes = doc.getElementsByTagName(IntegrationConstants.APPOINTMENTIDHEADER);
		Node node = null;
		for (int i = 0; i < nodes.getLength(); i++) {
			node = nodes.item(i);
			Log4jUtil.log("Searching: " + node.getChildNodes().item(0).getTextContent() + ", to be found: "
					+ (AppointmentId));
			AppointmentId = node.getChildNodes().item(0).getTextContent();
			Log4jUtil.log("AppointmentId header :" + " " + AppointmentId);
			if (node.getChildNodes().item(0).getTextContent().contains(AppointmentId)) {
				found = true;
				Log4jUtil.log("Appointment Id verified is found and verified.");
				break;
			}
		}

		assertTrue(found, "Appointment Id was not verified in response XML");
		Log4jUtil.log("response is ok");
		return AppointmentId;
	}

	public static void isReasonResponseXMLValid(String xmlFileName, String reason)
			throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);

		Log4jUtil.log("finding reason message");
		boolean found = false;
		boolean VideoPref = false;
		NodeList nodes = doc.getElementsByTagName(IntegrationConstants.REASON);
		NodeList nodes1 = doc.getElementsByTagName(IntegrationConstants.VIDEOPREFERENCE);
		Node node = null;
		for (int i = 0; i < nodes.getLength(); i++) {
			node = nodes.item(i);
			Log4jUtil.log("Searching: " + node.getChildNodes().item(0).getTextContent() + ", to be found: " + (reason));
			if (node.getChildNodes().item(0).getTextContent().contains(reason)) {
				found = true;
				Log4jUtil.log("Reason is found.");
				break;
			}
		}
		for (int i = 0; i < nodes1.getLength(); i++) {
			node = nodes1.item(i);
			Log4jUtil.log("Validating selection: " + node.getChildNodes().item(0).getTextContent() + ", to be found: "
					+ (VideoPref));
			Log4jUtil.log("Video visit is selected");
		}

		assertTrue(found, "Reason was not found in response XML");
		Log4jUtil.log("response is ok");
	}

	/**
	 * Reads the XML and checks Medication Details_
	 * 
	 * @param xmlFileName XML to check
	 * @param Long        timestamp of a sent Medication Name to check
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static void isMedicationDetailsResponseXMLValid(String xmlFileName, String medicationName)
			throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);

		Log4jUtil.log("finding Medication Name");
		boolean found = false;
		NodeList nodes = doc.getElementsByTagName(JalapenoConstants.MEDICATION_NAME_TAG);
		Node node = null;
		for (int i = 0; i < nodes.getLength(); i++) {
			node = nodes.item(i);
			Log4jUtil.log("Searching: " + node.getChildNodes().item(0).getTextContent() + ", to be found: "
					+ (medicationName));
			if (node.getChildNodes().item(0).getTextContent().contains(medicationName)) {
				Element ele = (Element) nodes.item(i).getParentNode();
				Node nDosage = ele.getElementsByTagName(JalapenoConstants.MEDICATION_DOSAGE).item(0);
				Node nQuantity = ele.getElementsByTagName(JalapenoConstants.QUANTITY_TAG).item(0);
				Node nPrescriptionNumber = ele.getElementsByTagName(JalapenoConstants.PRESCRIPTION_NUMBER_TAG).item(0);
				Node nRefillNumber = ele.getElementsByTagName(JalapenoConstants.REFILL_NUMBER_TAG).item(0);
				Node nAdditionalInformation = ele.getElementsByTagName(JalapenoConstants.ADDITIONAL_INFO_TAG)
						.item(0);
				assertEquals(nDosage.getTextContent(), JalapenoConstants.DOSAGE,
						"The actual value of dosage doesnt equal the expected value");
				assertEquals(nQuantity.getTextContent(), JalapenoConstants.QUANTITY,
						"The actual value of quantity doesnt equal the expected value");
				assertEquals(nRefillNumber.getTextContent(), JalapenoConstants.NO_OF_REFILLS,
						"The actual value of refill no. doesnt equal the expected value");
				assertEquals(nPrescriptionNumber.getTextContent(), JalapenoConstants.PRESCRIPTION_NO,
						"The actual value of prescription no. doesnt equal the expected value");
				assertEquals(nAdditionalInformation.getTextContent(), JalapenoConstants.ADDITIONAL_INFO,
						"The actual additional info doesnt equal the expected value");
				found = true;
				break;
			}
		}
		assertTrue(found, "Medication Name was not found in response XML");
		Log4jUtil.log("response is ok");
	}

	/**
	 * Reads the XML and checks asked Question if it complies
	 * 
	 * @param xmlFileName XML question to prepare
	 * @param from        sender of a message - external System ID
	 * @param to          recipient of a Message - external Patient ID
	 * @param subject     message subject
	 * @return XML message as a String
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws TransformerException
	 */
	public static String prepareSecureMessage(String xmlFileName, String from, String to, String subject,
			String messageID) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);

		// get message root element
		Node node = doc.getElementsByTagName(IntegrationConstants.SECURE_MESSAGE).item(0);
		Element elem = (Element) node;

		// set random message id
		long msgid = System.currentTimeMillis() / 10;
		elem.setAttribute(IntegrationConstants.MESSAGE_ID, elem.getAttribute(IntegrationConstants.MESSAGE_ID) + msgid);

		gnMessageID = elem.getAttribute(IntegrationConstants.MESSAGE_ID).toString();
		// set other attributes
		Node nFrom = elem.getElementsByTagName(IntegrationConstants.FROM).item(0);
		Node nTo = elem.getElementsByTagName(IntegrationConstants.TO).item(0);
		Node nSubject = elem.getElementsByTagName(IntegrationConstants.SUBJECT).item(0);

		nFrom.setTextContent(from);
		nTo.setTextContent(to);
		nSubject.setTextContent(subject);
		if (messageID != null) {
			Node nMessageThreadId = elem.getElementsByTagName(IntegrationConstants.MESSAGE_THREAD_ID).item(0);
			nMessageThreadId.setTextContent(messageID);
		}
		return domToString(doc);
	}

	// retrive new message ID
	public static String newMessageID() {
		return gnMessageID;
	}

	// generate four digit random for the message id
	public static int fourDigitRandom() {
		return (new Random()).nextInt(9000) + 1000;
	}

	public static String preparePatient(String xmlFileName, String practicePatientId, String firstName, String lastName,
			String dt, String month, String year, String email, String zip, String medfusionPatientID)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);
		String DOB = year + "-" + month + "-" + dt + "T12:00:01";

		Node idNode = doc.getElementsByTagName(IntegrationConstants.PRACTICE_PATIENT_ID).item(0);
		idNode.setTextContent(practicePatientId);

		Node patient = doc.getElementsByTagName(IntegrationConstants.PATIENT).item(0);
		NodeList childNodes = patient.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE
					&& childNodes.item(i).getNodeName().equals(IntegrationConstants.NAME)) {
				Element name = (Element) childNodes.item(i);
				Node firstNameNode = name.getElementsByTagName(IntegrationConstants.FIRST_NAME).item(0);
				Node lastNameNode = name.getElementsByTagName(IntegrationConstants.LAST_NAME).item(0);
				firstNameNode.setTextContent(firstName);
				lastNameNode.setTextContent(lastName);
			}
			if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE
					&& childNodes.item(i).getNodeName().equals(IntegrationConstants.DATEOFBIRTH)) {
				childNodes.item(i).setTextContent(DOB);
			}
			if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE
					&& childNodes.item(i).getNodeName().equals(IntegrationConstants.EMAIL_ADDRESS)) {
				childNodes.item(i).setTextContent(email);
			}
			if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE
					&& childNodes.item(i).getNodeName().equals(IntegrationConstants.HOME_ADDRESS)) {
				Element zipcode = (Element) childNodes.item(i);
				Node zipcodenode = zipcode.getElementsByTagName(IntegrationConstants.ZIPCODE).item(0);
				zipcodenode.setTextContent(zip);
			}

		}
		if (medfusionPatientID != null) {
			Node idNode1 = doc.getElementsByTagName(IntegrationConstants.MEDFUSIONPATIENTID).item(0);
			idNode1.setTextContent(medfusionPatientID);
		}

		if (medfusionPatientID != null && doc.getElementsByTagName(IntegrationConstants.MEDFUSIONID).item(0) != null) {
			Node idNode2 = doc.getElementsByTagName(IntegrationConstants.MEDFUSIONID).item(0);
			idNode2.setTextContent(medfusionPatientID);
		} else {
			if (doc.getElementsByTagName(IntegrationConstants.MEDFUSIONID).item(0) != null) {
				Element mfID = (Element) doc.getElementsByTagName(IntegrationConstants.MEDFUSIONID).item(0);
				mfID.getParentNode().removeChild(mfID);
			}
		}

		return domToString(doc);
	}

	public static String domToString(Document doc) throws TransformerException {
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
	 * 
	 * @param strUrl           server Post url
	 * @param payload          Post payload
	 * @param responseFilePath path to save the response
	 * @return Processing Status header from Response
	 * @throws IOException
	 */
	public static String setupHttpPostRequest(String strUrl, String payload, String responseFilePath)
			throws IOException {
		IHGUtil.PrintMethodName();
		try {
			IOAuthTwoLeggedClient oauthClient = new OAuth2Client();
			Log4jUtil.log("Post Request Url: " + strUrl);
			HttpPost httpPostReq = new HttpPost(strUrl);
			httpPostReq.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000)
					.setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
			Thread.sleep(30000);
			StringEntity se = new StringEntity(payload);
			httpPostReq.setEntity(se);
			httpPostReq.addHeader("Accept", "application/xml");
			httpPostReq.addHeader("Content-Type", "application/xml");
			httpPostReq.addHeader("Noun", "Encounter");
			httpPostReq.addHeader("Verb", "Completed");

			Log4jUtil.log("Post Request Url4: ");
			HttpResponse resp = oauthClient.httpPostRequest(httpPostReq);

			String sResp = EntityUtils.toString(resp.getEntity());
			Log4jUtil.log("Check opst response: " + sResp);

			Log4jUtil.log("Check for http 200/202 response");
			assertTrue(
					resp.getStatusLine().getStatusCode() == 200 || resp.getStatusLine().getStatusCode() == 202,
					"Get Request response is " + resp.getStatusLine().getStatusCode()
							+ " instead of 200/202. Response message:\n" + sResp);
			Log4jUtil.log("Response Code" + resp.getStatusLine().getStatusCode());
			writeFile(responseFilePath, sResp);

			if (resp.containsHeader(IntegrationConstants.LOCATION_HEADER)) {
				Header[] h = resp.getHeaders(IntegrationConstants.LOCATION_HEADER);
				return h[0].getValue();
			}

		} catch (Exception E) {
			Log4jUtil.log("Exception caught " + E);
			E.getCause().printStackTrace();
		}
		return null;
	}

	public static void oauthSetup(String oAuthKeySStorePath, String oAuthProperty, String appToken, String username,
			String password) throws Exception {
		IHGUtil.PrintMethodName();
		emptyFile(oAuthKeySStorePath);
		OAuthPropertyManager.init(oAuthProperty);
		System.out.println("appToken: " + appToken);
		System.out.println("username: " + username);
		System.out.println("password: " + password);
		try {
			OAuth20TokenManager.initializeTokenStore(appToken, username, password);
		} catch (Exception hException) {
			// TODO Auto-generated catch block
			hException.getCause().printStackTrace();
		}
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

	public static boolean isMessageProcessingCompleted(String xmlFileName)
			throws ParserConfigurationException, SAXException, IOException {
		Document doc = buildDOMXML(xmlFileName);

		NodeList nodes = doc.getElementsByTagName(IntegrationConstants.PROCESSING_STATE);
		NodeList Errornode = doc.getElementsByTagName("Error");

		for (int i = 0; i < nodes.getLength(); i++) {
			if (!nodes.item(i).getTextContent().equals(IntegrationConstants.STATE_COMPLETED)) {
				Log4jUtil.log("Error while processing response: " + Errornode.item(0).getTextContent());
			}
			assertTrue(nodes.item(i).getTextContent().equals(IntegrationConstants.STATE_COMPLETED),
					"Processing Status is failed for No '" + i + "' message");
		}
		return true;
	}

	/**
	 * Checks if the patient address lines are the same as in xml response
	 * 
	 * @param xmlFileName response xml path
	 * @param patientId   id of a patient to check
	 * @param firstLine   first address line
	 * @param secondLine  second address line
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 */
	public static void isPatientUpdated(String xmlFileName, String patientId, String firstLine, String secondLine)
			throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);

		NodeList nodes = doc.getElementsByTagName(IntegrationConstants.PRACTICE_ID);
		for (int i = 0; i < nodes.getLength(); i++) {
			if (nodes.item(i).getTextContent().equals(patientId)) {
				Element patient = (Element) nodes.item(i).getParentNode().getParentNode();
				Element homeAddress = (Element) patient.getElementsByTagName(IntegrationConstants.HOME_ADDRESS).item(0);
				Node line1 = homeAddress.getElementsByTagName(IntegrationConstants.LINE1).item(0);
				Node line2 = homeAddress.getElementsByTagName(IntegrationConstants.LINE2).item(0);
				assertEquals(line1.getTextContent(), firstLine,
						"The actual value of Line 1 address doesnt equal the updated value");
				assertEquals(line2.getTextContent(), secondLine,
						"The actual value of Line 2 address doesnt equal the updated value");
				break;
			}
			if (i == nodes.getLength() - 1) {
				fail("Patient was not Found");
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

	public static void isReplyPresent(String responsePath, String messageIdentifier)
			throws ParserConfigurationException, SAXException, IOException {
		Document doc = buildDOMXML(responsePath);

		Log4jUtil.log("finding sent message");
		boolean found = false;
		NodeList nodes = doc.getElementsByTagName(IntegrationConstants.QUESTION_SUBJECT);
		Node node = null;
		for (int i = 0; i < nodes.getLength(); i++) {

			node = nodes.item(i);
			Log4jUtil.log("Searching: " + node.getChildNodes().item(0).getTextContent() + ", to be found: "
					+ (messageIdentifier.toString()));
			if (node.getChildNodes().item(0).getTextContent().contains(messageIdentifier.toString())) {
				Element question = (Element) node.getParentNode();
				Node message = question.getElementsByTagName(IntegrationConstants.QUESTION_MESSAGE).item(0);
				assertEquals(message.getChildNodes().item(0).getTextContent(),
						IntegrationConstants.MESSAGE_REPLY, "Received reply is not the same as sent");
				found = true;
				break;
			}
		}
		assertTrue(found, "Reply was not found in response XML");
	}

	public static void isPatientRegistered(String xmlFileName, String practicePatientId, String firstName,
			String lastName, String patientID) throws ParserConfigurationException, SAXException, IOException {
		Document doc = buildDOMXML(xmlFileName);
		NodeList patients = doc.getElementsByTagName(IntegrationConstants.PRACTICE_PATIENT_ID);
		boolean found = false;
		for (int i = 0; i < patients.getLength(); i++) {
			if (patients.item(i).getTextContent().equals(practicePatientId)) {
				Log4jUtil.log("Searching: External Patient ID:" + practicePatientId
						+ ", and Actual External Patient ID is:" + patients.item(i).getTextContent().toString());
				Element patient = (Element) patients.item(i).getParentNode().getParentNode();
				Node status = patient.getElementsByTagName(IntegrationConstants.STATUS).item(0);
				assertEquals(status.getTextContent(), IntegrationConstants.REGISTERED,
						"Patient has different status than expected. Status is: " + status.getTextContent());
				Node nfirstName = patient.getElementsByTagName(IntegrationConstants.FIRST_NAME).item(0);
				Log4jUtil.log("Searching: Patient FirstName:" + firstName + ", and Actual Patient FirstName is:"
						+ nfirstName.getTextContent().toString());
				assertEquals(nfirstName.getTextContent(), firstName,
						"Patient has different FirstName than expected. FirstName is: " + nfirstName.getTextContent());
				Node nlastName = patient.getElementsByTagName(IntegrationConstants.LAST_NAME).item(0);
				Log4jUtil.log("Searching: Patient LastName:" + lastName + ", and Actual Patient LastName is:"
						+ nlastName.getTextContent().toString());
				assertEquals(nlastName.getTextContent(), lastName,
						"Patient has different LastName than expected. LastName is: " + nlastName.getTextContent());
				if (patientID != null) {
					Node nPatientId = patient.getElementsByTagName(IntegrationConstants.MEDFUSIONID).item(0);
					assertEquals(nPatientId.getTextContent(), patientID,
							"Patient has different MedfusionPatientId than expected. MedfusionPatientId is: "
									+ nPatientId.getTextContent());
					Log4jUtil.log("Searching: Medfusion Patient ID:" + patientID
							+ ", and Actual Medfusion Patient ID is:" + nPatientId.getTextContent().toString());
				}
				found = true;
				break;
			}
		}
		assertTrue(found, "Patient was not found in the response XML");

	}

	@SuppressWarnings("rawtypes")
	public static void isPatientRegistered(String xmlFileName, ArrayList practicePatientId, ArrayList firstName,
			ArrayList lastName, ArrayList patientID, ArrayList gender, ArrayList race, ArrayList ethnicity,
			ArrayList preferredLanguage, ArrayList preferredCommunication)
			throws ParserConfigurationException, SAXException, IOException {
		Document doc = buildDOMXML(xmlFileName);
		NodeList patients = doc.getElementsByTagName(IntegrationConstants.PRACTICE_PATIENT_ID);
		boolean found = false;
		for (int i = 0; i < patients.getLength(); i++) {
			for (int j = 0; j < practicePatientId.size(); j++)
				if (patients.item(i).getTextContent().equals(practicePatientId.get(j))) {
					Log4jUtil.log("For Patient: " + patients.item(i).getTextContent());
					Log4jUtil.log("Searching: External Patient ID:" + practicePatientId.get(j)
							+ ", and Actual External Patient ID is:" + patients.item(i).getTextContent().toString());
					Element patient = (Element) patients.item(i).getParentNode().getParentNode();
					Node status = patient.getElementsByTagName(IntegrationConstants.STATUS).item(0);
					assertEquals(status.getTextContent(), IntegrationConstants.REGISTERED,
							"Patient has different status than expected. Status is: " + status.getTextContent());
					Node nfirstName = patient.getElementsByTagName(IntegrationConstants.FIRST_NAME).item(0);
					Log4jUtil.log("Searching: Patient FirstName:" + firstName.get(j)
							+ ", and Actual Patient FirstName is:" + nfirstName.getTextContent().toString());
					assertEquals(nfirstName.getTextContent(), firstName.get(j),
							"Patient has different FirstName than expected. FirstName is: "
									+ nfirstName.getTextContent());
					Node nlastName = patient.getElementsByTagName(IntegrationConstants.LAST_NAME).item(0);
					Log4jUtil.log("Searching: Patient LastName:" + lastName.get(j) + ", and Actual Patient LastName is:"
							+ nlastName.getTextContent().toString());
					assertEquals(nlastName.getTextContent(), lastName.get(j),
							"Patient has different LastName than expected. LastName is: " + nlastName.getTextContent());

					// Addition of Gender, Ethnicity, Race, PreferredCommunication,
					// PreferredLanguage
					Node ngender = patient.getElementsByTagName(IntegrationConstants.GENDER).item(0);
					Log4jUtil.log("Searching: Patient Gender:" + gender.get(j) + ", and Actual Patient Gedner is:"
							+ ngender.getTextContent().toString());
					assertEquals(ngender.getTextContent(), gender.get(j),
							"Patient has different Gender than expected. Gender is: " + ngender.getTextContent());
					Node nrace = patient.getElementsByTagName(IntegrationConstants.RACE).item(0);
					Log4jUtil.log("Searching: Patient Race:" + race.get(j) + ", and Actual Patient race is:"
							+ nrace.getTextContent().toString());
					assertEquals(nrace.getTextContent(), race.get(j),
							"Patient has different race than expected. race is: " + nrace.getTextContent());
					Node nethnicity = patient.getElementsByTagName(IntegrationConstants.ETHINICITY).item(0);
					Log4jUtil.log("Searching: Patient Ethnicity:" + ethnicity.get(j)
							+ ", and Actual Patient ethnicity is:" + nethnicity.getTextContent().toString());
					assertEquals(nethnicity.getTextContent(), ethnicity.get(j),
							"Patient has different ethnicity than expected. ethnicity is: "
									+ nethnicity.getTextContent());
					Node npreferredLanguage = patient.getElementsByTagName(IntegrationConstants.PREFERREDLANGUAGE)
							.item(0);
					Log4jUtil.log("Searching: Patient Language:" + preferredLanguage.get(j)
							+ ", and Actual Patient preferredLanguage is:"
							+ npreferredLanguage.getTextContent().toString());
					assertEquals(npreferredLanguage.getTextContent(), preferredLanguage.get(j),
							"Patient has different preferredLanguage than expected. preferredLanguage is: "
									+ npreferredLanguage.getTextContent());
					Node npreferredCommunication = patient
							.getElementsByTagName(IntegrationConstants.CHOOSECOMMUNICATION).item(0);
					Log4jUtil.log("Searching: Patient Communication:" + preferredCommunication.get(j)
							+ ", and Actual Patient preferredCommunication is:"
							+ npreferredCommunication.getTextContent().toString());
					Log4jUtil.log(
							"------------------------------------------------------------------------------------------------------:");
					assertEquals(npreferredCommunication.getTextContent(), preferredCommunication.get(j),
							"Patient has different preferredCommunication than expected. preferredCommunication is: "
									+ npreferredCommunication.getTextContent());

					if (patientID != null) {
						Node nPatientId = patient.getElementsByTagName(IntegrationConstants.MEDFUSIONID).item(0);
						assertEquals(nPatientId.getTextContent(), patientID,
								"Patient has different MedfusionPatientId than expected. MedfusionPatientId is: "
										+ nPatientId.getTextContent());
						Log4jUtil.log("Searching: Medfusion Patient ID:" + patientID
								+ ", and Actual Medfusion Patient ID is:" + nPatientId.getTextContent().toString());
					}
					found = true;
					break;
				}
		}
		assertTrue(found, "Patient was not found in the response XML");

	}

	@SuppressWarnings("rawtypes")
	public static void isPatientRegistered(String xmlFileName, ArrayList practicePatientId, ArrayList firstName,
			ArrayList lastName, ArrayList patientID, PIDCInfo testData)
			throws ParserConfigurationException, SAXException, IOException {
		Document doc = buildDOMXML(xmlFileName);
		NodeList patients = doc.getElementsByTagName(IntegrationConstants.PRACTICE_PATIENT_ID);
		boolean found = false;
		for (int i = 0; i < patients.getLength(); i++) {
			for (int j = 0; j < practicePatientId.size(); j++)
				if (patients.item(i).getTextContent().equals(practicePatientId.get(j))) {
					Log4jUtil.log("For Patient: " + patients.item(i).getTextContent());
					Log4jUtil.log("Searching: External Patient ID:" + practicePatientId.get(j)
							+ ", and Actual External Patient ID is:" + patients.item(i).getTextContent().toString());
					Element patient = (Element) patients.item(i).getParentNode().getParentNode();
					Node status = patient.getElementsByTagName(IntegrationConstants.STATUS).item(0);
					assertEquals(status.getTextContent(), IntegrationConstants.REGISTERED,
							"Patient has different status than expected. Status is: " + status.getTextContent());
					Node nfirstName = patient.getElementsByTagName(IntegrationConstants.FIRST_NAME).item(0);
					Log4jUtil.log("Searching: Patient FirstName:" + firstName.get(j)
							+ ", and Actual Patient FirstName is:" + nfirstName.getTextContent().toString());
					assertEquals(nfirstName.getTextContent(), firstName.get(j),
							"Patient has different FirstName than expected. FirstName is: "
									+ nfirstName.getTextContent());
					Node nlastName = patient.getElementsByTagName(IntegrationConstants.LAST_NAME).item(0);
					Log4jUtil.log("Searching: Patient LastName:" + lastName.get(j) + ", and Actual Patient LastName is:"
							+ nlastName.getTextContent().toString());
					assertEquals(nlastName.getTextContent(), lastName.get(j),
							"Patient has different LastName than expected. LastName is: " + nlastName.getTextContent());

					// Addition of Gender, Ethnicity, Race, PreferredCommunication,
					// PreferredLanguage
					Node ngender = patient.getElementsByTagName(IntegrationConstants.GENDER).item(0);
					Log4jUtil.log("Searching: Patient Gender:" + testData.patientDetailList.get(j + 1).getGender()
							+ ", and Actual Patient Gedner is:" + ngender.getTextContent().toString());
					assertEquals(ngender.getTextContent(), testData.patientDetailList.get(j + 1).getGender(),
							"Patient has different Gender than expected. Gender is: " + ngender.getTextContent());
					Node nrace = patient.getElementsByTagName(IntegrationConstants.RACE).item(0);
					Log4jUtil.log("Searching: Patient Race:" + testData.patientDetailList.get(j + 1).getRace()
							+ ", and Actual Patient race is:" + nrace.getTextContent().toString());
					assertEquals(nrace.getTextContent(), testData.patientDetailList.get(j + 1).getRace(),
							"Patient has different race than expected. race is: " + nrace.getTextContent());
					Node nethnicity = patient.getElementsByTagName(IntegrationConstants.ETHINICITY).item(0);
					Log4jUtil.log("Searching: Patient Ethnicity:" + testData.patientDetailList.get(j + 1).getEthnicity()
							+ ", and Actual Patient ethnicity is:" + nethnicity.getTextContent().toString());
					assertEquals(nethnicity.getTextContent(),
							testData.patientDetailList.get(j + 1).getEthnicity(),
							"Patient has different ethnicity than expected. ethnicity is: "
									+ nethnicity.getTextContent());
					Node npreferredLanguage = patient.getElementsByTagName(IntegrationConstants.PREFERREDLANGUAGE)
							.item(0);
					Log4jUtil.log("Searching: Patient Language:"
							+ testData.patientDetailList.get(j + 1).getPreferredLanguage()
							+ ", and Actual Patient preferredLanguage is:"
							+ npreferredLanguage.getTextContent().toString());
					assertEquals(npreferredLanguage.getTextContent(),
							testData.patientDetailList.get(j + 1).getPreferredLanguage(),
							"Patient has different preferredLanguage than expected. preferredLanguage is: "
									+ npreferredLanguage.getTextContent());
					Node npreferredCommunication = patient
							.getElementsByTagName(IntegrationConstants.CHOOSECOMMUNICATION).item(0);
					Log4jUtil.log("Searching: Patient Communication:"
							+ testData.patientDetailList.get(j + 1).getPreferredCommunication()
							+ ", and Actual Patient preferredCommunication is:"
							+ npreferredCommunication.getTextContent().toString());
					assertEquals(npreferredCommunication.getTextContent(),
							testData.patientDetailList.get(j + 1).getPreferredCommunication(),
							"Patient has different preferredCommunication than expected. preferredCommunication is: "
									+ npreferredCommunication.getTextContent());
					Node nstateNodeValue = patient.getElementsByTagName(IntegrationConstants.STATE).item(0);
					if (testData.patientDetailList.get(j + 1).getStateNodeValue().trim().length() >= 3) {
						testData.patientDetailList.get(j + 1).setStateNodeValue(
								testData.patientDetailList.get(j + 1).getStateNodeValue().trim().substring(0, 2));
					}
					Log4jUtil.log("Searching: Patient State:"
							+ testData.patientDetailList.get(j + 1).getStateNodeValue().trim()
							+ ", and Actual Patient State is:" + nstateNodeValue.getTextContent().toString());
					assertEquals(nstateNodeValue.getTextContent(),
							testData.patientDetailList.get(j + 1).getStateNodeValue().trim(),
							"Patient has different State than expected. State is: " + nstateNodeValue.getTextContent());
					Log4jUtil.log(
							"------------------------------------------------------------------------------------------------------:");
					if (patientID != null) {
						Node nPatientId = patient.getElementsByTagName(IntegrationConstants.MEDFUSIONID).item(0);
						assertEquals(nPatientId.getTextContent(), patientID,
								"Patient has different MedfusionPatientId than expected. MedfusionPatientId is: "
										+ nPatientId.getTextContent());
						Log4jUtil.log("Searching: Medfusion Patient ID:" + patientID
								+ ", and Actual Medfusion Patient ID is:" + nPatientId.getTextContent().toString());
					}
					found = true;
					break;
				}
		}
		assertTrue(found, "Patient was not found in the response XML");

	}

	public static String prepareCCD(String ccdPath)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Document doc = buildDOMXML(ccdPath);
		return domToString(doc);

	}

	/**
	 * 
	 * @param xmlFileName
	 * @param parentNode
	 * @param childNode
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 * @throws ParseException
	 * @throws DOMException
	 */
	public static String findValueOfChildNode(String xmlFileName, String parentNode, String reason, String subject,
			String reply, String appointment) throws ParserConfigurationException, SAXException, IOException,
			TransformerException, DOMException, ParseException {

		IHGUtil.PrintMethodName();
		String getApt_req_id = null;
		String updatedXML = null;
		File xmlResponeFile = new File(xmlFileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlResponeFile);
		doc.getDocumentElement().normalize();

		NodeList pnode = doc.getElementsByTagName(parentNode);
		for (int i = 0; i < pnode.getLength(); i++) {
			Element element = (Element) pnode.item(i);
			String reaString = element.getElementsByTagName("Reason").item(0).getFirstChild().getNodeValue();
			if (reaString.equalsIgnoreCase(reason)) {
				Node node = element.getElementsByTagName("Reason").item(0).getParentNode();
				node = node.getParentNode();
				if (node.hasAttributes()) {
					Attr attr = (Attr) node.getAttributes().getNamedItem("id");
					getApt_req_id = attr.getValue();
				}
				Node node1 = element.getElementsByTagName("VideoPreference").item(0).getParentNode();
				node1 = node1.getParentNode();
				if (node1.hasAttributes()) {
					node1.getAttributes().getNamedItem("id");
				}

				String getFrom = element.getElementsByTagName("From").item(0).getFirstChild().getNodeValue();
				String getTo = element.getElementsByTagName("To").item(0).getFirstChild().getNodeValue();
				element = (Element) element.getParentNode();
				String getCreatedDateTime = element.getElementsByTagName("CreatedDateTime").item(0).getFirstChild()
						.getNodeValue();
				String getUpdatedDateTime = element.getElementsByTagName("UpdatedDateTime").item(0).getFirstChild()
						.getNodeValue();
				updatedXML = postAppointmentRequest(appointment, getApt_req_id, getFrom, getTo, getCreatedDateTime,
						getUpdatedDateTime, subject, reply);
				break;
			}

		}
		return updatedXML;

	}

	/**
	 * 
	 * @param xmlFileName
	 * @param parentNode
	 * @param childNode
	 * @param attribute
	 * @param updatedDateTime
	 * @param createdDateTime
	 * @param to
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 * @throws ParseException
	 * @throws DOMException
	 */
	public static String postAppointmentRequest(String xmlFileName, String app_req_id, String From, String To,
			String createdDateTime, String updatedDateTime, String subject, String reply)
			throws ParserConfigurationException, SAXException, IOException, TransformerException, DOMException,
			ParseException {
		Document doc = buildDOMXML(xmlFileName);

		Node node = doc.getElementsByTagName(IntegrationConstants.APPOINTMENT).item(0);
		Element element = (Element) node;
		// update appointment id,createdDateTime,updatedDateTime,message_thread_id
		element.setAttribute(IntegrationConstants.APPOINTMENT_ID, app_req_id);
		Node ncreatedDateTime = element.getElementsByTagName(IntegrationConstants.CREATED_TIME).item(0);
		Node nupdatedDateTime = element.getElementsByTagName(IntegrationConstants.UPDATE_TIME).item(0);
		Node nmessaageThreadID = element.getElementsByTagName(IntegrationConstants.MESSAGE_THREAD_ID).item(0);
		ncreatedDateTime.setTextContent(createdDateTime);
		nupdatedDateTime.setTextContent(updatedDateTime);
		nmessaageThreadID.setTextContent(app_req_id);

		// update From,To
		node = doc.getElementsByTagName(IntegrationConstants.APPOINTMENT_REQUEST).item(0);
		Node nFrom = element.getElementsByTagName(IntegrationConstants.FROM).item(0);
		Node nTo = element.getElementsByTagName(IntegrationConstants.TO).item(0);
		nFrom.setTextContent(From);
		nTo.setTextContent(To);

		// update messageId,Subject,message_thread_id
		Node cNode = doc.getElementsByTagName(IntegrationConstants.COMMUNICATION).item(0);
		Element ele = (Element) cNode;
		long msgid = System.currentTimeMillis() / 10;
		ele.setAttribute(IntegrationConstants.MESSAGE_ID, "5a5346dc-4671-4355-9391-" + msgid);
		Node ncFrom = ele.getElementsByTagName(IntegrationConstants.FROM).item(0);
		Node ncTo = ele.getElementsByTagName(IntegrationConstants.TO).item(0);
		ncFrom.setTextContent(To);
		ncTo.setTextContent(From);
		Node nSubject = element.getElementsByTagName(IntegrationConstants.SUBJECT).item(0);
		nSubject.setTextContent(subject);
		Node ncmessaageThreadID = ele.getElementsByTagName(IntegrationConstants.MESSAGE_THREAD_ID).item(0);
		ncmessaageThreadID.setTextContent(app_req_id);
		Node nmessage = element.getElementsByTagName(IntegrationConstants.QUESTION_MESSAGE).item(0);
		nmessage.setTextContent(reply);

		// update sent time,ScheduledDateTime
		Node nSent = element.getElementsByTagName(IntegrationConstants.SENT_DATE).item(0);
		nSent.setTextContent(SentDate(createdDateTime));
		Node nScheduledDate = element.getElementsByTagName(IntegrationConstants.SCHEDULED_DATE).item(0);
		nScheduledDate.setTextContent(ScheduledDate(createdDateTime));

		return domToString(doc);
	}

	/**
	 * 
	 * @param createdDateTime
	 * @return scheduleDate
	 * @throws ParseException
	 */
	private static String ScheduledDate(String createdDateTime) throws ParseException {
		String scheduleDate = null;
		SimpleDateFormat formatter, FORMATTER;
		formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Date date = formatter.parse(createdDateTime);
		FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		scheduleDate = FORMATTER.format(date.getTime() + (3 * (1000 * 60 * 60 * 24)));
		scheduleDate = new StringBuffer(scheduleDate).insert(22, ":").toString();
		return scheduleDate;
	}

	/**
	 * 
	 * @param createdDateTime
	 * @return sentDate
	 * @throws ParseException
	 */
	private static String SentDate(String createdDateTime) throws ParseException {
		String sentDate = null;
		SimpleDateFormat formatter, FORMATTER;
		formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Date date = formatter.parse(createdDateTime);
		FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		sentDate = FORMATTER.format(date.getTime() + (5 * 60000));
		sentDate = new StringBuffer(sentDate).insert(22, ":").toString();
		return sentDate;
	}

	/**
	 * 
	 * @param xmlFilePath
	 * @return
	 */
	public static String fileToString(String xmlFilePath) {
		IHGUtil.PrintMethodName();
		String xmlInString = convertXMLFileToString(xmlFilePath);
		return xmlInString;
	}

	/**
	 * 
	 * @param xmlFilePath
	 * @return
	 */
	public static String convertXMLFileToString(String fileName) {
		IHGUtil.PrintMethodName();
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			InputStream inputStream = new FileInputStream(new File(fileName));
			org.w3c.dom.Document doc = documentBuilderFactory.newDocumentBuilder().parse(inputStream);
			StringWriter stw = new StringWriter();
			Transformer serializer = TransformerFactory.newInstance().newTransformer();
			serializer.setOutputProperty("omit-xml-declaration", "yes");
			serializer.transform(new DOMSource(doc), new StreamResult(stw));
			return stw.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param xmlFileName
	 * @param parentNode
	 * @param medication
	 * @param rxSMSubject
	 * @param prescriptionPath
	 * @return updatedXML
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws TransformerException
	 * @throws ParseException
	 * @throws DOMException
	 */
	public static String findValueOfMedicationNode(String xmlFileName, String parentNode, String medication,
			String rxSMSubject, String rxSMBody, String prescriptionPath) throws ParserConfigurationException,
			SAXException, IOException, DOMException, ParseException, TransformerException {
		IHGUtil.PrintMethodName();
		String getPrescription_id = null;
		String updatedXML = null;
		File xmlResponeFile = new File(xmlFileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlResponeFile);
		doc.getDocumentElement().normalize();
		ArrayList<String> medication_details = new ArrayList<String>();

		NodeList pnode = doc.getElementsByTagName(parentNode);

		for (int i = 0; i < pnode.getLength(); i++) {
			Element element = (Element) pnode.item(i);
			String reaString = element.getElementsByTagName("MedicationName").item(0).getFirstChild().getNodeValue();
			if (reaString.equalsIgnoreCase(medication)) {
				Node node = element.getElementsByTagName("MedicationName").item(0).getParentNode();
				medication_details.add(element.getElementsByTagName("MedicationName").item(0).getFirstChild()
						.getNodeValue().toString());
				medication_details.add(element.getElementsByTagName("MedicationDosage").item(0).getFirstChild()
						.getNodeValue().toString());
				medication_details.add(
						element.getElementsByTagName("Quantity").item(0).getFirstChild().getNodeValue().toString());
				medication_details.add(
						element.getElementsByTagName("RefillNumber").item(0).getFirstChild().getNodeValue().toString());
				medication_details.add(element.getElementsByTagName("PrescriptionNumber").item(0).getFirstChild()
						.getNodeValue().toString());
				medication_details.add(element.getElementsByTagName("AdditionalInformation").item(0).getFirstChild()
						.getNodeValue().toString());
				node = node.getParentNode();
				element = (Element) element.getParentNode();
				medication_details.add(element.getElementsByTagName("RequestedLocation").item(0).getFirstChild()
						.getNodeValue().toString());
				medication_details.add(element.getElementsByTagName("RequestedProvider").item(0).getFirstChild()
						.getNodeValue().toString());
				medication_details
						.add(element.getElementsByTagName("To").item(0).getFirstChild().getNodeValue().toString());
				medication_details
						.add(element.getElementsByTagName("From").item(0).getFirstChild().getNodeValue().toString());
				node = node.getParentNode();
				if (node.hasAttributes()) {
					Attr attr = (Attr) node.getAttributes().getNamedItem("id");
					getPrescription_id = attr.getValue();
				}
				element = (Element) element.getParentNode();
				String getCreatedDateTime = element.getElementsByTagName("CreatedDateTime").item(0).getFirstChild()
						.getNodeValue();
				String getUpdatedDateTime = element.getElementsByTagName("UpdatedDateTime").item(0).getFirstChild()
						.getNodeValue();
				updatedXML = postMedicationRequest(prescriptionPath, getPrescription_id, medication_details,
						getCreatedDateTime, getUpdatedDateTime, rxSMSubject, rxSMBody);
				break;
			}

		}
		return updatedXML;
	}

	/**
	 * 
	 * @param prescriptionPath
	 * @param getApt_req_id
	 * @param medication_details
	 * @param getCreatedDateTime
	 * @param getUpdatedDateTime
	 * @param rxSMSubject
	 * @return
	 * @throws ParseException
	 * @throws DOMException
	 * @throws TransformerException
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	private static String postMedicationRequest(String prescriptionPath, String getPrescription_id,
			ArrayList<String> medication_details, String getCreatedDateTime, String getUpdatedDateTime,
			String rxSMSubject, String rxSMBody) throws DOMException, ParseException, TransformerException,
			ParserConfigurationException, SAXException, IOException {

		Document doc = buildDOMXML(prescriptionPath);

		Node node = doc.getElementsByTagName(IntegrationConstants.PRESCRIPTION).item(0);
		Element element = (Element) node;
		// update appointment id,createdDateTime,updatedDateTime,message_thread_id
		element.setAttribute(IntegrationConstants.ID, getPrescription_id);
		Node ncreatedDateTime = element.getElementsByTagName(IntegrationConstants.CREATED_TIME).item(0);
		Node nupdatedDateTime = element.getElementsByTagName(IntegrationConstants.UPDATE_TIME).item(0);
		ncreatedDateTime.setTextContent(getCreatedDateTime);
		nupdatedDateTime.setTextContent(getUpdatedDateTime);

		// update From,To
		node = doc.getElementsByTagName(IntegrationConstants.PRESCRIPTION_RENEWAL_REQUEST).item(0);
		Node nFrom = element.getElementsByTagName(IntegrationConstants.FROM).item(0);
		Node nTo = element.getElementsByTagName(IntegrationConstants.TO).item(0);
		Node nRequestedProvider = element.getElementsByTagName(IntegrationConstants.REQUESTED_PROVIDER).item(0);
		Node nRequestedLocation = element.getElementsByTagName(IntegrationConstants.REQUESTED_LOCATION).item(0);

		nFrom.setTextContent(medication_details.get(9));
		nTo.setTextContent(medication_details.get(8));
		nRequestedProvider.setTextContent(medication_details.get(7));
		nRequestedLocation.setTextContent(medication_details.get(6));

		// update medication details
		node = doc.getElementsByTagName(IntegrationConstants.MEDICATION).item(0);
		Node nMedicationName = element.getElementsByTagName(IntegrationConstants.MEDICATION_NAME_TAG).item(0);
		Node nMedicationDosage = element.getElementsByTagName(IntegrationConstants.DOSAGE_TAG).item(0);
		Node nQuantity = element.getElementsByTagName(IntegrationConstants.QUANTITY_TAG).item(0);
		Node nRefillNumber = element.getElementsByTagName(IntegrationConstants.REFILL_NUMBER_TAG).item(0);
		Node nPrescriptionNumber = element.getElementsByTagName(IntegrationConstants.PRESCRIPTION_NUMBER_TAG).item(0);
		Node nAdditionalInformation = element.getElementsByTagName(IntegrationConstants.ADDITIONAL_INFO_TAG).item(0);

		nAdditionalInformation.setTextContent(medication_details.get(5));
		nPrescriptionNumber.setTextContent(medication_details.get(4));
		nRefillNumber.setTextContent(medication_details.get(3));
		nQuantity.setTextContent(medication_details.get(2));
		nMedicationDosage.setTextContent(medication_details.get(1));
		nMedicationName.setTextContent(medication_details.get(0));

		String SigCode = generateRandomString();
		Node nSigCodeAbbreviation = element.getElementsByTagName("SigCodeAbbreviation").item(0);
		Node nSigCodeMeaning = element.getElementsByTagName("SigCodeMeaning").item(0);

		nSigCodeAbbreviation.setTextContent(SigCode);
		nSigCodeMeaning.setTextContent(SigCode.concat(" Daily"));

		SigCodeAbbreviation = nSigCodeAbbreviation.getTextContent().toString();
		SigCodeMeaning = nSigCodeMeaning.getTextContent().toString();
		// update messageId,Subject,message_thread_id
		Node cNode = doc.getElementsByTagName(IntegrationConstants.COMMUNICATION).item(0);
		Element ele = (Element) cNode;
		ele.setAttribute(IntegrationConstants.MESSAGE_ID, "11623b2e-6824-4c0e-9ed2-0ceb6f6a" + fourDigitRandom());
		Node ncFrom = ele.getElementsByTagName(IntegrationConstants.FROM).item(0);
		Node ncTo = ele.getElementsByTagName(IntegrationConstants.TO).item(0);
		ncFrom.setTextContent(medication_details.get(8));
		ncTo.setTextContent(medication_details.get(9));
		Node nSubject = element.getElementsByTagName(IntegrationConstants.SUBJECT).item(0);
		nSubject.setTextContent(rxSMSubject);
		Node ncmessaageThreadID = ele.getElementsByTagName(IntegrationConstants.MESSAGE_THREAD_ID).item(0);
		ncmessaageThreadID.setTextContent(getPrescription_id);
		Node nmessage = element.getElementsByTagName(IntegrationConstants.QUESTION_MESSAGE).item(0);
		nmessage.setTextContent(rxSMBody);

		// update sent time
		Node nSent = element.getElementsByTagName(IntegrationConstants.SENT_DATE).item(0);
		nSent.setTextContent(SentDate(getCreatedDateTime));

		return domToString(doc);

	}

	/**
	 * 
	 * @param epoch
	 * @return readGMTtime
	 */
	public static String readTime(long epoch) {
		String time = String.valueOf(epoch);
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		Date now = new Date(Long.parseLong(time) * 1000);
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		String readGMTtime = dateFormatGmt.format(now);
		return readGMTtime;
	}

	/**
	 * 
	 * @param xmlFileName
	 * @param messageID
	 * @param readdatetimestamp
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static void isReadCommunicationMessage(String xmlFileName, String messageID, String readdatetimestamp)
			throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();

		File stocks = new File(xmlFileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(stocks);
		doc.getDocumentElement().normalize();

		Log4jUtil.log("Expected Read DateTimestamp: " + readdatetimestamp);
		NodeList nodes = doc.getElementsByTagName(IntegrationConstants.READCOMMUNICATION);
		for (int i = 0; i < nodes.getLength(); i++) {

			Node node = nodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String readValue;
				readValue = getValue(IntegrationConstants.MESSAGE_ID, element);
				if (readValue.equalsIgnoreCase(messageID)) {
					Log4jUtil.log("Message ID is found in read communication response");
					getValue(IntegrationConstants.READDATETIMESTAMP, element).contains(readdatetimestamp);
					Log4jUtil.log(
							"Actual Read DateTimestamp: " + getValue(IntegrationConstants.READDATETIMESTAMP, element));
					break;

				}
				Log4jUtil.log("Message ID is not found in read communication response");
			}
		}

	}

	/**
	 * 
	 * @param tag
	 * @param element
	 * @return
	 */
	private static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}

	/**
	 * 
	 * @param strUrl
	 * @param payload
	 * @param responseFilePath
	 * @return
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static String setupHttpPostRequestExceptOauth(String strUrl, String payload, String responseFilePath,
			String externalSystemID) throws IOException, URISyntaxException {
		IHGUtil.PrintMethodName();

		HttpClient client = new DefaultHttpClient();
		Log4jUtil.log("Post Request Url: " + strUrl);

		HttpPost request = new HttpPost();
		request.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000)
				.setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
		request.setURI(new URI(strUrl));
		request.setEntity(new StringEntity(payload));
		request.setHeader("Noun", "Encounter");
		request.setHeader("Verb", "Completed");
		request.addHeader("Authentication-Type", "2wayssl");
		request.addHeader("Content-Type", "application/xml");
		request.setHeader("ExternalSystemID", externalSystemID);
		Log4jUtil.log("Post Request Url4: ");
		HttpResponse response = client.execute(request);
		String sResp = EntityUtils.toString(response.getEntity());
		Log4jUtil.log("Check for http 200/202 response");
		assertTrue(
				response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 202,
				"Get Request response is " + response.getStatusLine().getStatusCode()
						+ " instead of 200/202. Response message:\n" + sResp);
		Log4jUtil.log("Response Code" + response.getStatusLine().getStatusCode());
		writeFile(responseFilePath, sResp);

		if (response.containsHeader(IntegrationConstants.LOCATION_HEADER)) {
			Header[] h = response.getHeaders(IntegrationConstants.LOCATION_HEADER);
			return h[0].getValue();
		}
		return null;

	}

	/**
	 * 
	 * @param responsePath
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static boolean isCCDProcessingCompleted(String responsePath)
			throws ParserConfigurationException, SAXException, IOException {
		Document doc = buildDOMXML(responsePath);

		NodeList nodes = doc.getElementsByTagName(IntegrationConstants.TRANSPORTSTATUS);
		{
			assertTrue(nodes.item(0).getTextContent().equals(IntegrationConstants.CCDSTATUS),
					"There should be 1 Status element in processing status response");
		}
		return true;
	}

	/**
	 * 
	 * @param responsePath
	 * @param externalPatientID
	 * @param firstname
	 * @param medfusionID
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public static void isPatientAppeared(String responsePath, String externalPatientID, String medfusionID,
			String firstname) throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(responsePath);

		NodeList nodes = doc.getElementsByTagName(IntegrationConstants.PRACTICE_ID);
		for (int i = 0; i < nodes.getLength(); i++) {
			if (nodes.item(i).getTextContent().equals(externalPatientID)) {
				Log4jUtil.log("Searching: External Patient ID:" + externalPatientID
						+ ", and Actual External Patient ID is:" + nodes.item(i).getTextContent().toString());
				NodeList node = doc.getElementsByTagName(IntegrationConstants.MEDFUSIONPATIENTID);
				Log4jUtil.log("Searching: Medfusion Patient ID:" + medfusionID
						+ ", and Actual Medfusion Patient ID is: " + node.item(i).getTextContent().toString());
				assertTrue(node.item(i).getTextContent().equals(medfusionID),
						"Medfusion Patient Id was not found");
				node = doc.getElementsByTagName(IntegrationConstants.CCDTAG);
				assertTrue(node.item(i).getTextContent().contains("<given>" + firstname + "</given>"),
						"CCD DATA was not Found");
				Log4jUtil.log("Expected '" + "<given>" + firstname + "</given>" + "' is found in CCD XML.");
				break;
			}
			if (i == nodes.getLength() - 1) {
				fail("Patient was not found");
			}
		}

	}

	/**
	 * Generate random String for Sig Code
	 * 
	 * @return
	 */
	public static String generateRandomString() {
		String CHAR_LIST = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuffer randStr = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			int randomNo = 0;
			Random randomGenerator = new Random();
			randomNo = randomGenerator.nextInt(26);
			char ch = CHAR_LIST.charAt(randomNo);
			randStr.append(ch);
		}
		return randStr.toString();
	}

	/**
	 * 
	 */
	public static List<String> genrateRandomData(String SSN, String Email, String Gender) {
		List<String> updatelist = new ArrayList<String>();
		updatelist.add("FName" + random.nextInt(100));// FirstName
		updatelist.add("TestPatient1" + random.nextInt(100));// Last name
		updatelist.add("243456789" + random.nextInt(10));// home phone
		updatelist.add("Address1" + random.nextInt(100));// Street 1
		updatelist.add("Address2" + random.nextInt(100));// Street 2
		updatelist.add("City" + random.nextInt(100));// City
		updatelist.add("1456" + random.nextInt(10));// zipcode

		updatelist.add(SSN);// SSN
		updatelist.add(Gender);// Gender
		updatelist.add(Email);// EMail

		updatelist.add("MName" + random.nextInt(100));// Middle Name
		updatelist.add("242456789" + random.nextInt(10));// Mobile phone
		updatelist.add("989456789" + random.nextInt(10));// Work phone
		updatelist.add("01/01/199" + random.nextInt(10));// Date of birth

		// additional data For Insurance Detials
		updatelist.add("444-456-786" + random.nextInt(10));// Customer Service phone number
		updatelist.add("123-45-67" + random.nextInt(100));// Insured SSN
		updatelist.add("PolicyNumber" + random.nextInt(100));// Policy number
		updatelist.add("GroupNumber" + random.nextInt(100));// Group number
		updatelist.add("01/01/200" + random.nextInt(10));// Effective Date
		updatelist.add("12" + random.nextInt(100));// Co Pay

		return updatelist;
	}

	/**
	 * 
	 * @param xmlFileName
	 * @param practicePatientId
	 * @param list
	 * @param insuredlist
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void verifyPatientDetails(String xmlFileName, String practicePatientId, List<String> list,
			String insuranceName) throws ParserConfigurationException, SAXException, IOException, ParseException {
		Document doc = buildDOMXML(xmlFileName);
		NodeList patients = doc.getElementsByTagName(IntegrationConstants.PRACTICE_PATIENT_ID);
		// boolean found = false;
		for (int i = 0; i < patients.getLength(); i++) {
			if (patients.item(i).getTextContent().equals(practicePatientId)) {
				Element patient = (Element) patients.item(i).getParentNode().getParentNode();

				Log4jUtil.log("Checking Patient FirstName, Last Name, SSN, Gender");
				Node FirstName = patient.getElementsByTagName(IntegrationConstants.FIRST_NAME).item(0);
				assertEquals(FirstName.getTextContent(), list.get(0),
						"Patient has different FirstName than expected. FirstName is: " + FirstName.getTextContent());
				Node LastName = patient.getElementsByTagName(IntegrationConstants.LAST_NAME).item(0);
				assertEquals(LastName.getTextContent(), list.get(1),
						"Patient has different LastName than expected. LastName is: " + LastName.getTextContent());
				/*
				 * Node SSN = patient.getElementsByTagName(IntegrationConstants.SSN).item(0);
				 * assertEquals(SSN.getTextContent(), list.get(7),
				 * "Patient has different SSN than expected. Gender is: " +
				 * SSN.getTextContent());
				 */
				Node Gender = patient.getElementsByTagName(IntegrationConstants.GENDER).item(0);
				assertEquals(Gender.getTextContent(), list.get(8),
						"Patient has different Gender than expected. Gender is: " + Gender.getTextContent());
				Log4jUtil.log("Checking Patient HomePhone, Email Address");
				Node HomePhone = patient.getElementsByTagName(IntegrationConstants.HOMEPHONE).item(0);
				assertEquals(HomePhone.getTextContent(), list.get(2),
						"Patient has different HomePhone than expected. HomePhone is: " + HomePhone.getTextContent());
				Node EmailAddress = patient.getElementsByTagName(IntegrationConstants.EMAIL_ADDRESS).item(0);
				assertEquals(EmailAddress.getTextContent(), list.get(9),
						"Patient has different EmailAddress than expected. EmailAddress is: "
								+ EmailAddress.getTextContent());
				Log4jUtil.log("Checking Patient Address1, Address2, City, ZipCode");
				Node Line1 = patient.getElementsByTagName(IntegrationConstants.LINE1).item(0);
				assertEquals(Line1.getTextContent(), list.get(3),
						"Patient has different Address1 than expected. Address2 is: " + Line1.getTextContent());
				Node Line2 = patient.getElementsByTagName(IntegrationConstants.LINE2).item(0);
				assertEquals(Line2.getTextContent(), list.get(4),
						"Patient has different Address2 than expected. Address1 is: " + Line2.getTextContent());
				Node City = patient.getElementsByTagName(IntegrationConstants.CITY).item(0);
				assertEquals(City.getTextContent(), list.get(5),
						"Patient has different City than expected. City is: " + City.getTextContent());
				Node ZipCode = patient.getElementsByTagName(IntegrationConstants.ZIPCODE).item(0);
				assertEquals(ZipCode.getTextContent(), list.get(6),
						"Patient has different ZipCode than expected. ZipCode is: " + ZipCode.getTextContent());
				if (insuranceName != null) {
					String birthdate = convertDate(list.get(13)) + "T00:00:00";
					Log4jUtil.log("Checking Patient Date of Birth, Middle Name");
					Node DateOfBirth = patient.getElementsByTagName(IntegrationConstants.DATEOFBIRTH).item(0);
					assertEquals(DateOfBirth.getTextContent(), birthdate,
							"Patient has different DateOfBirth than expected. DateOfBirth is: "
									+ DateOfBirth.getTextContent());
					Node MiddleName = patient.getElementsByTagName(IntegrationConstants.MIDDLENAME).item(0);
					assertEquals(MiddleName.getTextContent(), list.get(10),
							"Patient has different MiddleName than expected. MiddleName is: "
									+ MiddleName.getTextContent());
					Log4jUtil.log("Checking Patient Mobile No, WorkPhone");
					Node MobilePhone = patient.getElementsByTagName(IntegrationConstants.MOBILEPHONE).item(0);
					assertEquals(MobilePhone.getTextContent(), list.get(11),
							"Patient has different MobilePhone than expected. MobilePhone is: "
									+ MobilePhone.getTextContent());
					Node WorkPhone = patient.getElementsByTagName(IntegrationConstants.WORKPHONE).item(0);
					assertEquals(WorkPhone.getTextContent(), list.get(12),
							"Patient has different WorkPhone than expected. WorkPhone is: "
									+ WorkPhone.getTextContent());
					Log4jUtil.log(
							"Checking Patient Preferred Language, Race, Ethnicity, MaritalStatus, ChooseCommunication");
					Node PreferredLanguage = patient.getElementsByTagName(IntegrationConstants.PREFERREDLANGUAGE)
							.item(0);
					assertEquals(PreferredLanguage.getTextContent(), list.get(20),
							"Patient has different PreferredLanguage than expected. PreferredLanguage is: "
									+ PreferredLanguage.getTextContent());
					Node Race = patient.getElementsByTagName(IntegrationConstants.RACE).item(0);
					assertEquals(Race.getTextContent(), list.get(21),
							"Patient has different Race than expected. Race is: " + Race.getTextContent());
					Node Ethnicity = patient.getElementsByTagName(IntegrationConstants.ETHINICITY).item(0);
					assertEquals(Ethnicity.getTextContent(), list.get(22),
							"Patient has different Ethnicity than expected. Ethnicity is: "
									+ Ethnicity.getTextContent());
					Node MaritalStatus = patient.getElementsByTagName(IntegrationConstants.MARRITALSTATUS).item(0);
					assertEquals(MaritalStatus.getTextContent(), list.get(23).toUpperCase(),
							"Patient has different MaritalStatus than expected. MaritalStatus is: "
									+ MaritalStatus.getTextContent());
					Node ChooseCommunication = patient.getElementsByTagName(IntegrationConstants.CHOOSECOMMUNICATION)
							.item(0);
					assertEquals(ChooseCommunication.getTextContent(), list.get(24),
							"Patient has different ChooseCommunication than expected. ChooseCommunication is: "
									+ ChooseCommunication.getTextContent());
					/*
					 * Node state =
					 * patient.getElementsByTagName(IntegrationConstants.PROCESSING_STATE).item(0);
					 * assertEquals(state.getTextContent(), list.get(25),
					 * "Patient has different state than expected. state is: " +
					 * state.getTextContent());
					 */
					// PRIMARY INSURANCE DETAILS
					Log4jUtil.log("Checking Insurance PolicyNumber, Insurance Name");
					Node cNode = patient.getElementsByTagName(IntegrationConstants.PRIMARYINSURANCE).item(0);
					Element ele = (Element) cNode;
					Node PolicyNumber = ele.getElementsByTagName(IntegrationConstants.POLICYNUMBER).item(0);
					assertEquals(PolicyNumber.getTextContent(), list.get(16),
							"Patient has different PolicyNumber than expected. PolicyNumber is: "
									+ PolicyNumber.getTextContent());
					Node CompanyName = ele.getElementsByTagName(IntegrationConstants.COMPANYNAME).item(0);
					assertEquals(CompanyName.getTextContent(), insuranceName,
							"Patient has different Insurance Name than expected. CompanyName is: "
									+ CompanyName.getTextContent());
					Log4jUtil.log("Checking Patient Address1, Address2, City, ZipCode");
					Node InsuranceAddressLine1 = ele.getElementsByTagName(IntegrationConstants.LINE1).item(0);
					assertEquals(InsuranceAddressLine1.getTextContent(), list.get(3),
							"Patient has different InsuranceAddressLine1 than expected. InsuranceAddressLine1 is: "
									+ InsuranceAddressLine1.getTextContent());
					Node InsuranceAddressLine2 = ele.getElementsByTagName(IntegrationConstants.LINE2).item(0);
					assertEquals(InsuranceAddressLine2.getTextContent(), list.get(4),
							"Patient has different InsuranceAddressLine2 than expected. InsuranceAddressLine1 is: "
									+ InsuranceAddressLine2.getTextContent());
					Node InsuranceCity = ele.getElementsByTagName(IntegrationConstants.CITY).item(0);
					assertEquals(InsuranceCity.getTextContent(), list.get(5),
							"Patient has different InsuranceCity than expected. InsuranceCity is: "
									+ InsuranceCity.getTextContent());
					Node InsuranceZipCode = ele.getElementsByTagName(IntegrationConstants.ZIPCODE).item(0);
					assertEquals(InsuranceZipCode.getTextContent(), list.get(6),
							"Patient has different InsuranceZipCode than expected. InsuranceZipCode is: "
									+ InsuranceZipCode.getTextContent());
					Log4jUtil.log("Checking Patient Relation To Subscriber, Subscriber Date Of Birth, Group Number");
					Node PatientRelationToSubscriber = ele
							.getElementsByTagName(IntegrationConstants.PATIENTRELATIONTOSUBSCRIBER).item(0);
					assertEquals(PatientRelationToSubscriber.getTextContent(),
							list.get(26).toUpperCase(),
							"Patient has different PatientRelationToSubscriber than expected. InsuranceZipCode is: "
									+ PatientRelationToSubscriber.getTextContent());
					Node SubscriberDateOfBirth = ele.getElementsByTagName(IntegrationConstants.SUBSCRIBERDATEOFBIRTH)
							.item(0);
					assertEquals(SubscriberDateOfBirth.getTextContent(), birthdate,
							"Patient has different SubscriberDateOfBirth than expected. SubscriberDateOfBirth is: "
									+ SubscriberDateOfBirth.getTextContent());
					/*
					 * Node ClaimsPhone =
					 * ele.getElementsByTagName(IntegrationConstants.CLAIMSPHONE).item(0);
					 * assertEquals(ClaimsPhone.getTextContent(), list.get(14),
					 * "Patient has different ClaimsPhone than expected. ClaimsPhone is: " +
					 * ClaimsPhone.getTextContent()); Node SubscriberSocialSecurityNumber =
					 * ele.getElementsByTagName(IntegrationConstants.SUBSCRIBERSSN).item(0);
					 * assertEquals(SubscriberSocialSecurityNumber.getTextContent
					 * (), list.get(15),
					 * "Patient has different SubscriberSocialSecurityNumber than expected. SubscriberSocialSecurityNumber is: "
					 * + SubscriberSocialSecurityNumber.getTextContent());
					 */
					Node GroupNumber = ele.getElementsByTagName(IntegrationConstants.GROUPNUMBER).item(0);
					assertEquals(GroupNumber.getTextContent(), list.get(17),
							"Patient has different GroupNumber than expected. GroupNumber is: "
									+ GroupNumber.getTextContent());
					break;
				}

			}

		}

		// assertTrue(found, "Patient was not found in the response XML");

	}

	/**
	 * 
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	private static String convertDate(String dateString) throws ParseException {
		SimpleDateFormat givenFormat = new SimpleDateFormat("dd/mm/yyyy");
		SimpleDateFormat expectedFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = givenFormat.parse(dateString);
		String expectedDate = expectedFormat.format(date);
		return expectedDate;
	}

	/**
	 * 
	 * @param xmlFileName
	 * @param practicePatientId
	 * @param medfusionPatientID
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public static String prepareCCD(String xmlFileName, String practicePatientId, String medfusionPatientID)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);

		Node pidNode = doc.getElementsByTagName(IntegrationConstants.PRACTICE_PATIENT_ID).item(0);
		pidNode.setTextContent(practicePatientId);
		Node mdNode = doc.getElementsByTagName(IntegrationConstants.MEDFUSIONPATIENTID).item(0);
		mdNode.setTextContent(medfusionPatientID);

		return domToString(doc);
	}

	/**
	 * Generate Batch PIDC xml with unique values of Patient ExternalID , First Name
	 * & Last Name
	 * 
	 * @param xmlFileName
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public static String generateBatchPIDC(String xmlFileName)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);

		NodeList pnode = doc.getElementsByTagName(IntegrationConstants.PATIENT);
		for (int i = 0; i < pnode.getLength(); i++) {
			String randomNo = IHGUtil.createRandomNumericString();
			String practicePatientID = "Patient" + randomNo;
			Node pidNode = doc.getElementsByTagName(IntegrationConstants.PRACTICE_PATIENT_ID).item(i);
			pidNode.setTextContent(practicePatientID);
			testData(practicePatientID);
			Element ele1 = (Element) pnode.item(i);
			NodeList nameNode = ele1.getElementsByTagName(IntegrationConstants.NName);
			Element ele = (Element) nameNode.item(0);
			{
				String fName = "FNAME" + randomNo;
				Node fnameNode = ele.getElementsByTagName(IntegrationConstants.FIRST_NAME).item(0);
				fnameNode.setTextContent(fName);

				testData(escapeXml(fName));
				String lName = "TestPatient1" + randomNo;
				Node LnameNode = ele.getElementsByTagName(IntegrationConstants.LAST_NAME).item(0);
				LnameNode.setTextContent(lName);
				testData(escapeXml(lName));
			}

		}

		return domToString(doc);
	}

	/**
	 * add patient details
	 * 
	 * @param data
	 */
	public static void testData(String data) {
		patientDatails.add(data);
	}

	/**
	 * remove the special characters from string and return normal string
	 * 
	 * @param specialDataString - Given string with special character
	 * @return
	 */
	public static String escapeXml(String specialDataString) {
		return specialDataString.replaceAll("&amp;", "&").replaceAll("&gt;", ">").replaceAll("&lt;", "<")
				.replaceAll("&quot;", "\"").replaceAll("&apos;", "'");
	}

	/**
	 * 
	 * @param responsePath
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static void isPaymentAppeared(String responsePath, String patientAccountNumber, String amt, String CClastdig,
			String CCtype, String status, String confirmationNumber)
			throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(responsePath);

		Log4jUtil.log("finding Payment by Account Number");
		boolean found = false;
		NodeList accountNumber = doc.getElementsByTagName(IntegrationConstants.ACCOUNTNUMBER);
		for (int i = 0; i < accountNumber.getLength(); i++) {
			if (accountNumber.item(i).getTextContent().equals(patientAccountNumber)) {
				Log4jUtil.log("Searching: Patient Account Number:" + patientAccountNumber
						+ ", and Actual Patient Account Number is:"
						+ accountNumber.item(i).getTextContent().toString());
				Element payment = (Element) accountNumber.item(i).getParentNode();
				Node paymentType = payment.getElementsByTagName(IntegrationConstants.PAYMENTTYPE).item(0);
				Log4jUtil.log("Searching: Bill Payment Type:" + "BillPayment" + ", and Actual Bill Payment Type is:"
						+ paymentType.getTextContent().toString());
				assertEquals(paymentType.getTextContent(), "BillPayment",
						"Bill Payment Type has different than expected. Type is: " + paymentType.getTextContent());
				paymentType = paymentType.getParentNode();
				if (paymentType.hasAttributes()) {
					Attr attr = (Attr) paymentType.getAttributes().getNamedItem("id");
					paymentID = attr.getValue();
				}
				Node paymentStatus = payment.getElementsByTagName(IntegrationConstants.PAYMENTSTATUS).item(0);
				Log4jUtil.log("Searching: Payment Status:" + status + ", and Actual Payment Status is:"
						+ paymentStatus.getTextContent().toString());
				assertEquals(paymentStatus.getTextContent(), status,
						"Payment Status has different than expected. Type is: " + paymentStatus.getTextContent());
				Log4jUtil.log("Checking Payment Amount & Card Last digit Information");
				Node cNode = payment.getElementsByTagName(IntegrationConstants.PAYMENTINFO).item(0);
				Element ele = (Element) cNode;
				Node amount = ele.getElementsByTagName(IntegrationConstants.AMOUNT).item(0);
				assertEquals(amount.getTextContent(), amt,
						"Payment has different amount than expected. Amount is: " + amount.getTextContent());
				Node digits = ele.getElementsByTagName(IntegrationConstants.LASTDIGITS).item(0);
				assertEquals(digits.getTextContent(), CClastdig,
						"Payment has different last digit than expected. Amount is: " + digits.getTextContent());
				Node ccType = ele.getElementsByTagName(IntegrationConstants.CCTYPE).item(0);
				// Log4jUtil.log("Searching: CC Type:" + "Visa" + ", and Actual CC Type is:" +
				// ccType.getTextContent().toString());
				assertEquals(ccType.getTextContent(), CCtype,
						"Payment has different amount than expected. Amount is: " + ccType.getTextContent());
				Node nconfirmationNumber = ele.getElementsByTagName(IntegrationConstants.CONFIRMNUMBER).item(0);
				assertEquals(nconfirmationNumber.getTextContent(), confirmationNumber,
						"Payment has different confirmation Number than expected. Confirmation Number is: "
								+ nconfirmationNumber.getTextContent());

				found = true;
				break;
			}

		}
		assertTrue(found, "Payment Account Number Node was not found in the response XML");

	}

	/**
	 * 
	 * @param xmlFile
	 * @param paymentID
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public static String preparePayment(String xmlFile, String paymentID, String amonunt, String type)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFile);
		Node node = doc.getElementsByTagName(IntegrationConstants.PAYMENT).item(0);
		Element element = (Element) node;
		element.setAttribute(IntegrationConstants.ID, paymentID);
		Node nodeType = element.getElementsByTagName(IntegrationConstants.PAYMENTTYPE).item(0);
		nodeType.setTextContent(type);
		if (amonunt != null) {
			Node nodeAmount = element.getElementsByTagName(IntegrationConstants.AMOUNT).item(0);
			nodeAmount.setTextContent(amonunt);
		}
		return domToString(doc);

	}

	/**
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * 
	 */
	public static void validateNode(String xmlFileName, String value, char nodeName, String patientID)
			throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		boolean found = false;
		Document doc = buildDOMXML(xmlFileName);
		NodeList patient = doc.getElementsByTagName(IntegrationConstants.PRACTICE_PATIENT_ID);
		for (int i = 0; i < patient.getLength(); i++) {
			if (patient.item(i).getTextContent().equalsIgnoreCase(patientID)) {
				Element ele = (Element) patient.item(i).getParentNode().getParentNode();
				Node node = null;
				Log4jUtil.log("nodeName " + nodeName);
				switch (nodeName) {
				case 'R':
					node = ele.getElementsByTagName(IntegrationConstants.RACE).item(0);
					break;
				case 'E':
					node = ele.getElementsByTagName(IntegrationConstants.ETHINICITY).item(0);
					break;
				case 'L':
					node = ele.getElementsByTagName(IntegrationConstants.PREFERREDLANGUAGE).item(0);
					break;
				case 'M':
					node = ele.getElementsByTagName(IntegrationConstants.MARRITALSTATUS).item(0);
					break;
				case 'C':
					node = ele.getElementsByTagName(IntegrationConstants.CHOOSECOMMUNICATION).item(0);
					break;
				case 'G':
					node = ele.getElementsByTagName(IntegrationConstants.GENDER).item(0);
					break;
				case 'I':
					node = ele.getElementsByTagName(IntegrationConstants.GENDERIDENTITY).item(0);
					break;
				case 'S':
					node = ele.getElementsByTagName(IntegrationConstants.SEXUALORIENTATION).item(0);
					break;
				default:
					break;
				}
				if (node == null) {
					if (nodeName == 'I' || nodeName == 'S') {
						Log4jUtil.log("GI/SO node not presnt of v1");
						found = true;
					}
					assertTrue(found, "Node Not Found");
					found = true;
					break;
				}
				Log4jUtil.log(
						"Expected Value: " + value + ", and Actual Value is: " + node.getTextContent().trim() + ".");
				assertTrue(node.getTextContent().trim().equalsIgnoreCase(value), "Value mismatched");

				found = true;
				break;
			}

		}
		assertTrue(found, "Patient was not found in the response XML");

	}

	public static void setupHttpGetRequestExceptOauth(String strUrl, String responseFilePath)
			throws IOException, URISyntaxException {
		IHGUtil.PrintMethodName();
		HttpClient client = new DefaultHttpClient();
		Log4jUtil.log("Post Request Url: " + strUrl);

		HttpGet httpGetReq = new HttpGet(strUrl);
		httpGetReq.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000)
				.setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
		httpGetReq.setURI(new URI(strUrl));
		httpGetReq.addHeader("Authentication-Type", "2wayssl");
		httpGetReq.addHeader("Content-Type", "application/xml");
		HttpResponse resp = client.execute(httpGetReq);
		HttpEntity entity = resp.getEntity();
		String sResp = "";
		if (entity != null) {
			sResp = EntityUtils.toString(entity);
			Log4jUtil.log("Check for http 200 response");
		} else {
			Log4jUtil.log("Check for http 204 response");
		}

		assertTrue(resp.getStatusLine().getStatusCode() == 200 || resp.getStatusLine().getStatusCode() == 204,
				"Get Request response is " + resp.getStatusLine().getStatusCode()
						+ " instead of 200. Response message received:\n" + sResp);
		writeFile(responseFilePath, sResp);
		if (resp.containsHeader("Next-URI")) {
			Header[] h = resp.getHeaders("Next-URI");
			headerUrl = h[0].getValue();
		}
	}

	/**
	 * 
	 * @param xmlFileName
	 * @param from
	 * @param to
	 * @param subject
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public static String generateBatchAMDC(String xmlFileName, List<?> newdata)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);

		NodeList pnode = doc.getElementsByTagName(IntegrationConstants.SECURE_MESSAGE);
		for (int i = 0; i < pnode.getLength(); i++) {
			Node node = doc.getElementsByTagName(IntegrationConstants.SECURE_MESSAGE).item(i);
			Element elem = (Element) node;
			// set random message id
			long msgid = System.currentTimeMillis() / 100;
			elem.setAttribute(IntegrationConstants.MESSAGE_ID,
					elem.getAttribute(IntegrationConstants.MESSAGE_ID) + msgid + i);
			Node fromNode = doc.getElementsByTagName(IntegrationConstants.FROM).item(i);
			fromNode.setTextContent(newdata.get(0).toString());
			testData(fromNode.getTextContent());
			Node toNode = doc.getElementsByTagName(IntegrationConstants.TO).item(i);
			toNode.setTextContent(newdata.get(1).toString());
			testData(toNode.getTextContent());
			Node subjectNode = doc.getElementsByTagName(IntegrationConstants.SUBJECT).item(i);
			subjectNode.setTextContent(newdata.get(2).toString());
			testData(subjectNode.getTextContent());
			Node messageNode = doc.getElementsByTagName(IntegrationConstants.QUESTION_MESSAGE).item(i);
			messageNode.setTextContent(newdata.get(3).toString());
			testData(messageNode.getTextContent());
			for (int k = 0; k < 4; k++) {
				newdata.remove(0);
			}
		}
		return domToString(doc);
	}

	public static void verifyPayment(String responsePath, String amount, String status, String Type,
			String confirmationNumber) throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(responsePath);

		Log4jUtil.log("finding Payment by Amount");
		boolean found = false;
		NodeList namount = doc.getElementsByTagName(IntegrationConstants.AMOUNT);
		for (int i = 0; i < namount.getLength(); i++) {
			if (namount.item(i).getTextContent().equals(amount)) {
				Log4jUtil.log("Searching: Patient Amount :" + amount + ", and Actual Patient Account is:"
						+ namount.item(i).getTextContent().toString());
				Element payment = (Element) namount.item(i).getParentNode().getParentNode();
				Node paymentType = payment.getElementsByTagName(IntegrationConstants.PAYMENTTYPE).item(0);
				Log4jUtil.log("Searching: Bill Payment Type:" + Type + ", and Actual Bill Payment Type is:"
						+ paymentType.getTextContent().toString());
				assertEquals(paymentType.getTextContent(), Type,
						"Bill Payment Type has different than expected. Type is: " + paymentType.getTextContent());
				paymentType = paymentType.getParentNode();
				if (paymentType.hasAttributes()) {
					Attr attr = (Attr) paymentType.getAttributes().getNamedItem("id");
					paymentID = attr.getValue();
				}
				Node paymentStatus = payment.getElementsByTagName(IntegrationConstants.PAYMENTSTATUS).item(0);
				Log4jUtil.log("Searching: Payment Status:" + status + ", and Actual Payment Status is:"
						+ paymentStatus.getTextContent().toString());
				assertEquals(paymentStatus.getTextContent(), status,
						"Payment Status has different than expected. Type is: " + paymentStatus.getTextContent());
				Log4jUtil.log("Checking Payment Amount & Card Last digit Information");
				Node cNode = payment.getElementsByTagName(IntegrationConstants.PAYMENTINFO).item(0);
				Element ele = (Element) cNode;
				/*
				 * Node amount = ele.getElementsByTagName(IntegrationConstants.AMOUNT).item(0);
				 * assertEquals(amount.getTextContent(), "100.00",
				 * "Payment has different amount than expected. Amount is: " +
				 * amount.getTextContent()); Node digits =
				 * ele.getElementsByTagName(IntegrationConstants.LASTDIGITS).item(0);
				 * assertEquals(digits.getTextContent(), "1111",
				 * "Payment has different last digit than expected. Amount is: " +
				 * digits.getTextContent());
				 */
				Node ccType = ele.getElementsByTagName(IntegrationConstants.CCTYPE).item(0);
				Log4jUtil.log("Searching: CC Type:" + "Visa" + ", and Actual CC Type is:"
						+ ccType.getTextContent().toString());
				assertEquals(ccType.getTextContent(), "Visa",
						"Payment has different amount than expected. Amount is: " + ccType.getTextContent());
				if (confirmationNumber != null) {
					/*
					 * Node nconfirmationNumber =
					 * ele.getElementsByTagName(IntegrationConstants.CONFIRMNUMBER).item(0);
					 * Log4jUtil.log("Searching: Confirmation Number:" + confirmationNumber +
					 * ", and Actual Confirmation Number is:" +
					 * nconfirmationNumber.getTextContent().toString());
					 * assertEquals(nconfirmationNumber.getTextContent(),
					 * confirmationNumber,
					 * "Payment has different confirmation Number than expected. Amount is: " +
					 * nconfirmationNumber.getTextContent());
					 */
				}
				found = true;
				break;
			}

		}
		assertTrue(found, "Payment Amount was not found in the response XML");

	}

	/**
	 * 
	 * @param strUrl
	 * @param responseFilePath
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static String setupHttpGetRequestExceptoAuth(String strUrl, String responseFilePath)
			throws IOException, URISyntaxException {
		IHGUtil.PrintMethodName();
		HttpClient client = new DefaultHttpClient();
		Log4jUtil.log("Get Request Url: " + strUrl);

		HttpGet httpGetReq = new HttpGet(strUrl);
		httpGetReq.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000)
				.setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
		httpGetReq.setURI(new URI(strUrl));
		httpGetReq.addHeader("Authentication-Type", "2wayssl");
		httpGetReq.addHeader("Content-Type", "application/xml");
		HttpResponse resp = client.execute(httpGetReq);
		HttpEntity entity = resp.getEntity();
		String sResp = null;
		if (entity != null) {
			sResp = EntityUtils.toString(entity);
			Log4jUtil.log("Check for http 200 response");
			responseCode = resp.getStatusLine().getStatusCode();
			assertTrue(resp.getStatusLine().getStatusCode() == 200,
					"Get Request response is " + resp.getStatusLine().getStatusCode() + " instead of " + 200
							+ ". Response message received:\n" + sResp);
			writeFile(responseFilePath, sResp);

			if (resp.containsHeader(IntegrationConstants.TIMESTAMP_HEADER)) {
				Header[] h = resp.getHeaders(IntegrationConstants.TIMESTAMP_HEADER);
				return h[0].getValue();
			}
		} else {
			responseCode = 204;
			Log4jUtil.log("204 response found");

		}
		return null;

	}

	/**
	 * 
	 * @param xmlFileName
	 * @param practicePatientId
	 * @param firstName
	 * @param lastName
	 * @param patientID
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static void checkPatientRegistered(String xmlFileName, List<String> updateData)
			throws ParserConfigurationException, SAXException, IOException {
		Document doc = buildDOMXML(xmlFileName);
		NodeList nfirstName = doc.getElementsByTagName(IntegrationConstants.FIRST_NAME);
		boolean found = false;
		for (int i = 0; i < nfirstName.getLength(); i++) {
			if (nfirstName.item(i).getTextContent().equals(updateData.get(0))) {
				Log4jUtil.log("Searching: Medfusion Patient First Name:" + updateData.get(0)
						+ ", and Actual Medfusion Patient First Name is:"
						+ nfirstName.item(i).getTextContent().toString());
				assertEquals(nfirstName.item(i).getTextContent(), updateData.get(0),
						"Medfusion Patient First Name has different than expected. First Name is: "
								+ nfirstName.item(i).getTextContent());
				Element nPatient = (Element) nfirstName.item(i).getParentNode().getParentNode();
				Node nlastName = nPatient.getElementsByTagName(IntegrationConstants.LAST_NAME).item(0);
				Log4jUtil.log("Searching: Patient Last Name:" + updateData.get(1) + ", and Actual Patient LastName is:"
						+ nlastName.getTextContent().toString());
				assertEquals(nlastName.getTextContent(), updateData.get(1),
						"Medfusion Patient Last Name has different than expected. Last Name is: "
								+ nlastName.getTextContent());
				try {
					if (updateData.get(2) != null) {
						if (updateData.get(10) != null) {
							Node nMiddleName = nPatient.getElementsByTagName(IntegrationConstants.MIDDLENAME).item(0);
							Log4jUtil.log("Searching: Patient Middle Name:" + updateData.get(10)
									+ ", and Actual Patient Middle Name is:" + nMiddleName.getTextContent().toString());
							assertEquals(nMiddleName.getTextContent(), updateData.get(10),
									"Medfusion Patient Middle Name has different than expected. Middle Name is: "
											+ nMiddleName.getTextContent());

						}
						Node nAddress1 = nPatient.getElementsByTagName(IntegrationConstants.LINE1).item(0);
						Log4jUtil.log("Searching: Patient Address1 :" + updateData.get(2)
								+ ", and Actual Patient Address1 is:" + nAddress1.getTextContent().toString());
						assertEquals(nAddress1.getTextContent(), updateData.get(2),
								"Medfusion Patient Address1 has different than expected. Address1 is: "
										+ nAddress1.getTextContent());
						Node nAddress2 = nPatient.getElementsByTagName(IntegrationConstants.LINE2).item(0);
						Log4jUtil.log("Searching: Patient Address2 :" + updateData.get(3)
								+ ", and Actual Patient Address2 is:" + nAddress2.getTextContent().toString());
						assertEquals(nAddress2.getTextContent(), updateData.get(3),
								"Medfusion Patient Address2 has different than expected. Address2 is: "
										+ nAddress2.getTextContent());
						Node nHomePhone = nPatient.getElementsByTagName(IntegrationConstants.HOMEPHONE).item(0);
						Log4jUtil.log("Searching: Patient Home Phone :" + updateData.get(4)
							+ ", and Actual Patient Home Phone is:"
							+ StringUtils.remove(StringUtils.remove(
								StringUtils.remove(StringUtils
									.remove(nHomePhone.getTextContent(), "("), ")"),
								"-"), " "));
						assertEquals(
							StringUtils.remove(StringUtils.remove(
								StringUtils.remove(StringUtils
									.remove(nHomePhone.getTextContent(), "("), ")"),
								"-"), " "),
							updateData.get(4),
							"Medfusion Patient Home Phone has different than expected. HomePhone is: "
								+ nHomePhone.getTextContent());
						Node nDOB = nPatient.getElementsByTagName(IntegrationConstants.DATEOFBIRTH).item(0);
						Log4jUtil.log("Searching: Patient Date of Birth :" + updateData.get(5)
								+ ", and Actual Patient Date of Birth is:" + nDOB.getTextContent().toString());
						Node nRace = nPatient.getElementsByTagName(IntegrationConstants.RACE).item(0);
						Log4jUtil.log("Searching: Race Value :" + updateData.get(7)
								+ ", and Actual Patient Date of Birth is:" + nRace.getTextContent().toString());
						assertEquals(nRace.getTextContent(), updateData.get(7),
								"Race has different than expected. Race is: " + nRace.getTextContent());
						Node nEthnicity = nPatient.getElementsByTagName(IntegrationConstants.ETHINICITY).item(0);
						Log4jUtil.log("Searching: Ethnicity Value :" + updateData.get(8) + ", and Actual Ethnicity is:"
								+ nEthnicity.getTextContent().toString());
						assertEquals(nEthnicity.getTextContent(), updateData.get(8),
								"Ethnicity has different than expected. Ethnicity is: " + nEthnicity.getTextContent());
						if (updateData.get(9) != null) {
							Node nChooseCommunication = nPatient
									.getElementsByTagName(IntegrationConstants.CHOOSECOMMUNICATION).item(0);
							Log4jUtil.log("Searching: Preferred Communication Value :" + updateData.get(9)
									+ ", and Actual communication value is:"
									+ nChooseCommunication.getTextContent().toString());
							assertEquals(nChooseCommunication.getTextContent(), updateData.get(9),
									"Patient has different ChooseCommunication than expected. ChooseCommunication is: "
											+ nChooseCommunication.getTextContent());
						}

					}
				} catch (Exception e) {
					Log4jUtil.log("#####");
				}
				found = true;
				break;
			}
		}
		assertTrue(found, "Patient was not found in the response XML");

	}

	/**
	 * 
	 * @param xmlFileName
	 * @param patientID
	 * @param insuranceData
	 * @param insurance_Name
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */

	public static void verifyHealthPatientInsuranceDetails(String xmlFileName, String patientID,
			List<String> insuranceData, String insurance_Name)
			throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);
		boolean found = false;
		NodeList patients = doc.getElementsByTagName(IntegrationConstants.MEDFUSIONID);
		for (int i = 0; i < patients.getLength(); i++) {
			if (patients.item(i).getTextContent().equals(patientID)) {
				Element patient = (Element) patients.item(i).getParentNode().getParentNode();
				Log4jUtil.log("Checking Insurance PolicyNumber, Insurance Name");
				Node cNode = patient.getElementsByTagName(IntegrationConstants.PRIMARYINSURANCE).item(0);
				Element ele = (Element) cNode;
				Node PolicyNumber = ele.getElementsByTagName(IntegrationConstants.POLICYNUMBER).item(0);
				assertEquals(PolicyNumber.getTextContent(), insuranceData.get(16),
						"Patient has different PolicyNumber than expected. PolicyNumber is: "
								+ PolicyNumber.getTextContent());
				Node CompanyName = ele.getElementsByTagName(IntegrationConstants.COMPANYNAME).item(0);
				assertEquals(CompanyName.getTextContent(), insurance_Name,
						"Patient has different Insurance Name than expected. CompanyName is: "
								+ CompanyName.getTextContent());
				Log4jUtil.log("Checking Patient Address1, Address2, City, ZipCode");
				Node InsuranceAddressLine1 = ele.getElementsByTagName(IntegrationConstants.LINE1).item(0);
				assertEquals(InsuranceAddressLine1.getTextContent(), insuranceData.get(3),
						"Patient has different InsuranceAddressLine1 than expected. InsuranceAddressLine1 is: "
								+ InsuranceAddressLine1.getTextContent());
				Node InsuranceAddressLine2 = ele.getElementsByTagName(IntegrationConstants.LINE2).item(0);
				assertEquals(InsuranceAddressLine2.getTextContent(), insuranceData.get(4),
						"Patient has different InsuranceAddressLine2 than expected. InsuranceAddressLine1 is: "
								+ InsuranceAddressLine2.getTextContent());
				Node InsuranceCity = ele.getElementsByTagName(IntegrationConstants.CITY).item(0);
				assertEquals(InsuranceCity.getTextContent(), insuranceData.get(5),
						"Patient has different InsuranceCity than expected. InsuranceCity is: "
								+ InsuranceCity.getTextContent());
				Node InsuranceZipCode = ele.getElementsByTagName(IntegrationConstants.ZIPCODE).item(0);
				assertEquals(InsuranceZipCode.getTextContent(), insuranceData.get(6),
						"Patient has different InsuranceZipCode than expected. InsuranceZipCode is: "
								+ InsuranceZipCode.getTextContent());
				Log4jUtil.log("Checking Patient Group Number");
				Node GroupNumber = ele.getElementsByTagName(IntegrationConstants.GROUPNUMBER).item(0);
				assertEquals(GroupNumber.getTextContent(), insuranceData.get(17),
						"Patient has different GroupNumber than expected. GroupNumber is: "
								+ GroupNumber.getTextContent());
				found = true;
				break;
			}
		}
		assertTrue(found, "Patient was not found in the response XML");
	}

	public static String verifyEmailNotification(String gmailUserName, String gmailPassword, String recipient,
			int minute, String portal) throws Exception {
		GmailBot gBot = new GmailBot();
		String emailMessageLink = null;

		Log4jUtil.log("Gmail User Name :" + gmailUserName + "Password :" + gmailPassword);
		try {
			if (portal == "Portal 1.0") {
				emailMessageLink = gBot.findInboxEmailLink(gmailUserName, gmailPassword,
						"New message from " + recipient, IntegrationConstants.EMAIL_BODY_LINK, minute, false, true);
			} else if (portal == "Portal 2.0") {
				emailMessageLink = gBot.findInboxEmailLink(gmailUserName, gmailPassword,
						"New message from " + recipient, IntegrationConstants.PI_EMAIL_BODY_LINK, minute, false, true);
				if (emailMessageLink.contains("?redirectoptout=true")) {
					emailMessageLink = emailMessageLink.replace("?redirectoptout=true", "");
				}
			}
		} catch (Exception e) {
			Log4jUtil.log("Error :" + e);
		}

		assertTrue(emailMessageLink.length() != 0,
				"New secure message was not found in patient inbox ");

		Log4jUtil.log("Delete all messages from Inbox");
		gBot.deleteAllMessagesFromInbox(gmailUserName, gmailPassword);

		return emailMessageLink;

	}

	/**
	 * Generate PIDC payload with contains special characters data in given
	 * parameter except practice patient ID.
	 * 
	 * @param xmlFileName
	 * @param practicePatientId
	 * @param fName
	 * @param mName
	 * @param lName
	 * @param address1
	 * @param address2
	 * @param email
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public static String generatePIDCSpecialCharacter(String xmlFileName, String practicePatientId, String fName,
			String mName, String lName, String address1, String address2, String email)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);
		NodeList pnode = doc.getElementsByTagName(IntegrationConstants.PATIENT);
		Node pidNode = doc.getElementsByTagName(IntegrationConstants.PRACTICE_PATIENT_ID).item(0);
		pidNode.setTextContent(practicePatientId);
		Node emailNode = doc.getElementsByTagName(IntegrationConstants.EMAIL_ADDRESS).item(0);
		emailNode.setTextContent(email);
		testData(practicePatientId);
		Element ele1 = (Element) pnode.item(0);
		NodeList nameNode = ele1.getElementsByTagName(IntegrationConstants.NName);
		Element ele = (Element) nameNode.item(0);
		Node fnameNode = ele.getElementsByTagName(IntegrationConstants.FIRST_NAME).item(0);
		fnameNode.setTextContent(fName);
		testData(escapeXml(fName));
		Node mnameNode = ele.getElementsByTagName(IntegrationConstants.MIDDLENAME).item(0);
		mnameNode.setTextContent(mName);
		testData(escapeXml(mName));
		Node LnameNode = ele.getElementsByTagName(IntegrationConstants.LAST_NAME).item(0);
		LnameNode.setTextContent(lName);
		testData(escapeXml(lName));

		NodeList homeAddressNode = ele1.getElementsByTagName(IntegrationConstants.HOME_ADDRESS);
		ele = (Element) homeAddressNode.item(0);
		Node line1Node = ele.getElementsByTagName(IntegrationConstants.LINE1).item(0);
		line1Node.setTextContent(address1);
		testData(escapeXml(address1));
		Node line2Node = ele.getElementsByTagName(IntegrationConstants.LINE2).item(0);
		line2Node.setTextContent(address2);
		testData(escapeXml(address2));

		return domToString(doc);

	}

	/**
	 * Verify recent CCD message in patient portal.
	 * 
	 * @param ccdDate          //actual CCD date displayed in patient portal
	 * @param ccdSendTimestamp //POST CCD send timestamp
	 * @return
	 * @throws ParseException
	 */
	public static boolean verifyCCDMessageDate(String ccdDate, long ccdSendTimestamp) throws ParseException {
		IHGUtil.PrintMethodName();

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa");
		Date requiredDate = sdf.parse(ccdDate);
		Log4jUtil.log("Before Set TimeZone " + requiredDate.getTime());
		sdf.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
		requiredDate = sdf.parse(ccdDate);
		if (requiredDate.getTime() >= ccdSendTimestamp) {
			Log4jUtil.log("CCD sent date & time is :" + ccdDate);
			return true;
		} else {
			Log4jUtil.log("Recent CCD is not availble in patient Portal . Last CCD sent date & time is: " + ccdDate);
			return false;
		}

	}

	public static String getmonthstr(String month) {
		int intmth = Integer.parseInt(month);
		String monthString = new DateFormatSymbols().getMonths()[intmth - 1];
		return monthString;
	}

	/*
	 * public static String prepareMassAdminMessage(MassAdmin massAdmin) throws
	 * ParserConfigurationException, SAXException, IOException, TransformerException
	 * { IHGUtil.PrintMethodName(); String xmlFileName =
	 * massAdmin.getMassAdminPayload(); if(xmlFileName == null) {
	 * System.out.println("XML File name not Found"); } URL url =
	 * ClassLoader.getSystemResource(xmlFileName); String fileName = url.getFile();
	 * System.out.println("File Name: "+fileName); Document doc =
	 * buildDOMXML(fileName);
	 * 
	 * //get message root element Node node =
	 * doc.getElementsByTagName(IntegrationConstants.MASS_MESSAGE).item(0); Element
	 * elem = (Element) node;
	 * 
	 * Node nFrom = elem.getElementsByTagName(IntegrationConstants.FROM).item(0);
	 * Node nSubject =
	 * elem.getElementsByTagName(IntegrationConstants.SUBJECT).item(0); Node
	 * nMessage = elem.getElementsByTagName(IntegrationConstants.MESSAGE).item(0);
	 * 
	 * nFrom.setTextContent(massAdmin.getFrom());
	 * nSubject.setTextContent("Mass Admin Message");
	 * nMessage.setTextContent(massAdmin.getMessage());
	 * 
	 * Node nPatients =
	 * elem.getElementsByTagName(IntegrationConstants.PATIENTS).item(0);
	 * List<PatientDetails> patientDetailsList = massAdmin.getPatientDetailsList();
	 * for (PatientDetails patientDetails : patientDetailsList) { // server elements
	 * Element newPatient = doc.createElement(IntegrationConstants.PATIENT);
	 * 
	 * String no1 = String.valueOf(fourDigitRandom()) +
	 * String.valueOf(fourDigitRandom()); String no2 =
	 * String.valueOf(fourDigitRandom()); String no3 =
	 * String.valueOf(fourDigitRandom()); String no4 =
	 * String.valueOf(fourDigitRandom()); String no5 =
	 * String.valueOf(fourDigitRandom()) + String.valueOf(fourDigitRandom()) +
	 * String.valueOf(fourDigitRandom());
	 * 
	 * String messageid = no1+"-"+no2+"-"+no3+"-"+no4+"-"+no5;
	 * System.out.println("Messageid: "+messageid);
	 * newPatient.setAttribute(IntegrationConstants.MESSAGE_ID, messageid);
	 * 
	 * Element practicePatientId =
	 * doc.createElement(IntegrationConstants.PRACTICE_PATIENT_ID);
	 * practicePatientId.appendChild(doc.createTextNode(patientDetails.getPatient())
	 * ); newPatient.appendChild(practicePatientId);
	 * 
	 * Element params = doc.createElement("Params"); Element param =
	 * doc.createElement("Param"); Element name = doc.createElement("Name"); Element
	 * value = doc.createElement("Value");
	 * name.appendChild(doc.createTextNode("PATIENT.FIRSTNAME"));
	 * value.appendChild(doc.createTextNode(patientDetails.getPatientName()));
	 * param.appendChild(name); param.appendChild(value);
	 * 
	 * params.appendChild(param); newPatient.appendChild(params);
	 * 
	 * nPatients.appendChild(newPatient); } return domToString(doc); }
	 */

	/**
	 * Checks if the patient statement delivery preference is correct
	 * 
	 * @param xmlFileName response xml path
	 * @param MFId        is id of a patient to check
	 * @param Pref        is Statement Delivery Preference selected by patient
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 */
	public static void isStatementPreferenceCorrect(String xmlFileName, String MFId, String Pref)
			throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);

		NodeList nodes = doc.getElementsByTagName(IntegrationConstants.MEDFUSION_MEMBER_ID);
		for (int i = 0; i < nodes.getLength(); i++) {
			if (nodes.item(i).getTextContent().equals(MFId)) {
				Element stpref = (Element) nodes.item(i).getParentNode();
				Element prefs = (Element) stpref.getElementsByTagName(IntegrationConstants.PREFERENCES).item(0);
				Element pref = (Element) prefs.getElementsByTagName(IntegrationConstants.PREFERENCE).item(0);
				Node pref_name = pref.getElementsByTagName(IntegrationConstants.PREF_NAME).item(0);
				Node pref_value = pref.getElementsByTagName(IntegrationConstants.PREF_VALUE).item(0);
				assertEquals(pref_name.getTextContent(), "STATEMENT_DELIVERY_PREF",
						"Not on Statement Delivery Preference Node");
				assertEquals(pref_value.getTextContent(), Pref, "Statement Delivery Preference does not match");
				break;
			}
			if (i == nodes.getLength() - 1) {
				fail("Patient was not Found");
			}
		}

	}

	/**
	 * Reads the XML and checks asked Question if it complies
	 * 
	 * @param xmlFileName XML Statement Preference POST Payload
	 * @param MFId        - Medfusion Member Id
	 * @param extId       - external Patient ID
	 * @param Pref        - Statement Preference
	 * @return XML message as a String
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws TransformerException
	 */
	public static String preparePostStatementPreference(String xmlFileName, String MFId, String extId, String Pref)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);

		// get message root element
		Node node = doc.getElementsByTagName(IntegrationConstants.STMT_PREF).item(0);
		Element elem = (Element) node;

		// set other attributes
		Node PracticePatientId = elem.getElementsByTagName(IntegrationConstants.PRACTICE_PATIENT_ID).item(0);
		PracticePatientId.setTextContent(extId);
		Node MedfusionMemberId = elem.getElementsByTagName(IntegrationConstants.MEDFUSION_MEMBER_ID).item(0);
		MedfusionMemberId.setTextContent(MFId);

		Element prefs = (Element) elem.getElementsByTagName(IntegrationConstants.PREFERENCES).item(0);
		Element pref = (Element) prefs.getElementsByTagName(IntegrationConstants.PREFERENCE).item(0);
		// Node pref_name =
		// pref.getElementsByTagName(IntegrationConstants.PREF_NAME).item(0);
		Node pref_value = pref.getElementsByTagName(IntegrationConstants.PREF_VALUE).item(0);

		// Element prefname = (Element) pref_name;
		Element prefvalue = (Element) pref_value;

		prefvalue.setTextContent(Pref);

		return domToString(doc);
	}

	public static String verifyDirectMessageResponse(String xmlFileName, String PartnerMessageId)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);

		Node MFnode = doc.getElementsByTagName("MFMessageId").item(0);
		Element mfElem = (Element) MFnode;
		Log4jUtil.log("Searching for MFMessageId :" + mfElem.getTextContent().toString());

		Node PartnerNode = doc.getElementsByTagName("PartnerMessageId").item(0);
		Element partnerElem = (Element) PartnerNode;
		Log4jUtil.log("Verifying PartnerMessageId actual " + partnerElem.getTextContent().toString() + " with Expected "
				+ PartnerMessageId);
		assertEquals(partnerElem.getTextContent().toString(), PartnerMessageId);

		Node StatusNode = doc.getElementsByTagName("StatusCode").item(0);
		Element statusElem = (Element) StatusNode;
		Log4jUtil.log("Verifying Status actual " + statusElem.getTextContent().toString() + " with Expected 200");
		assertEquals(statusElem.getTextContent().toString(), "200");

		return mfElem.getTextContent().toString();
	}

	public static void verifyDirectMessageGetStatus(String xmlFileName, String MFMessageId, String fromAddress,
			String toAddress) throws ParserConfigurationException, SAXException, IOException, TransformerException {

		Document doc = buildDOMXML(xmlFileName);
		NodeList messageStatusNode = doc.getElementsByTagName("messageStatus");
		Log4jUtil.log("Verifying messageStatus actual  " + messageStatusNode.item(0).getTextContent()
				+ " with expected as confirmed");
		assertTrue(
				messageStatusNode.item(0).getTextContent().equalsIgnoreCase("confirmed")
						|| messageStatusNode.item(0).getTextContent().equalsIgnoreCase("accepted"),
				"Get Request response is " + messageStatusNode.item(0).getTextContent());

		NodeList fromAddressNode = doc.getElementsByTagName("fromAddress");
		Log4jUtil.log("Verifying fromAddress actual " + fromAddressNode.item(0).getTextContent() + " with expected as "
				+ fromAddress);
		assertEquals(fromAddressNode.item(0).getTextContent(), fromAddress);

		NodeList toAddressNode = doc.getElementsByTagName("toAddress");
		Log4jUtil.log("Verifying toAddress actual  " + toAddressNode.item(0).getTextContent() + " with expected as "
				+ toAddress);
		assertEquals(toAddressNode.item(0).getTextContent(), toAddress);

		NodeList StatusCode = doc.getElementsByTagName("StatusCode");
		Log4jUtil.log("Verifying toAddress actual " + StatusCode.item(0).getTextContent() + " with expected as 200");
		assertEquals(StatusCode.item(0).getTextContent(), "200");
	}

	public static boolean isSendDirectMessageProcessed(String xmlFileName)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Document doc = buildDOMXML(xmlFileName);
		NodeList messageStatusNode = doc.getElementsByTagName("messageStatus");
		Log4jUtil.log("Verifying messageStatus actual  " + messageStatusNode.item(0).getTextContent()
				+ " with expected as confirmed");

		assertTrue(
				messageStatusNode.item(0).getTextContent().equalsIgnoreCase("confirmed")
						|| messageStatusNode.item(0).getTextContent().equalsIgnoreCase("accepted"),
				"Get Request response is " + messageStatusNode.item(0).getTextContent());

		NodeList mdnConfirmationDateNode = doc.getElementsByTagName("mdnConfirmationDate");
		Log4jUtil
				.log("Verifying if mdnConfirmationDate is present " + mdnConfirmationDateNode.item(0).getTextContent());
		assertTrue(!mdnConfirmationDateNode.item(0).getTextContent().isEmpty());

		return true;
	}

	public static boolean validateDirectorySearchResponse(String xmlFileName, String firstName, String lastName,
			String organizationName, String nationalProvider, String specialityType, String classification,
			String specialization, String street, String city, String state, String zipCode, String directAddress)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Document doc = buildUTFDOMXML(xmlFileName);
		NodeList StatusCode = doc.getElementsByTagName("StatusCode");
		Log4jUtil.log("Verifying StatusCode actual  " + StatusCode.item(0).getTextContent() + " with expected as 200");
		assertEquals(StatusCode.item(0).getTextContent(), "200");

		NodeList nodes = doc.getElementsByTagName("DirectoryInfo");

		for (int i = 0; i < nodes.getLength(); i++) {
			Element DirInfo = (Element) nodes.item(i);

			Element FirstNameElem = (Element) DirInfo.getElementsByTagName("FirstName").item(0);
			if (firstName != null) {
				Log4jUtil.log("FirstName : " + FirstNameElem.getTextContent() + "  :  " + firstName.trim());
				assertTrue(
						FirstNameElem.getTextContent().toLowerCase().contains(firstName.trim().toLowerCase()));
			}

			Element LastNameElem = (Element) DirInfo.getElementsByTagName("LastName").item(0);
			if (lastName != null) {
				Log4jUtil.log("LastName : " + LastNameElem.getTextContent() + "  : " + lastName.trim());
				assertEquals(LastNameElem.getTextContent().toLowerCase(), lastName.trim().toLowerCase());
			}

			Element OrgElem = (Element) DirInfo.getElementsByTagName("OrganizationName").item(0);
			if (organizationName != null) {
				Log4jUtil.log("OrganizationName : " + OrgElem.getTextContent() + "   :   " + organizationName.trim());
				assertEquals(OrgElem.getTextContent().toLowerCase(), organizationName.trim().toLowerCase());
			}

			Element NationalProviderIdElem = (Element) DirInfo.getElementsByTagName("NationalProviderId").item(0);
			if (nationalProvider != null) {
				Log4jUtil.log("NationalProviderId : " + NationalProviderIdElem.getTextContent() + " : "
						+ nationalProvider.trim());
				assertEquals(NationalProviderIdElem.getTextContent().toLowerCase(),
						nationalProvider.trim().toLowerCase());
			}

			Element SpecialtyTypeElem = (Element) DirInfo.getElementsByTagName("SpecialtyType").item(0);
			if (specialityType != null) {
				Log4jUtil.log("SpecialtyType : " + SpecialtyTypeElem.getTextContent() + " specialityType "
						+ specialityType.trim());
				assertEquals(SpecialtyTypeElem.getTextContent().toLowerCase(),
						specialityType.trim().toLowerCase());
			}

			Element SpecialtyClassificationElem = (Element) DirInfo.getElementsByTagName("SpecialtyClassification")
					.item(0);
			if (classification != null) {
				Log4jUtil.log("SpecialtyClassification : " + SpecialtyClassificationElem.getTextContent() + "  : "
						+ classification.trim());
				assertEquals(SpecialtyClassificationElem.getTextContent().toLowerCase(),
						classification.trim().toLowerCase());
			}

			Element SpecialtySpecializationElem = (Element) DirInfo.getElementsByTagName("SpecialtySpecialization")
					.item(0);
			if (specialization != null) {
				Log4jUtil.log("SpecialtySpecialization : " + SpecialtySpecializationElem.getTextContent() + " : "
						+ specialization.trim());
				assertEquals(SpecialtySpecializationElem.getTextContent().toLowerCase(),
						specialization.trim().toLowerCase());
			}

			Element StreetElem = (Element) DirInfo.getElementsByTagName("Street").item(0);
			if (street != null) {
				Log4jUtil.log("Street : " + StreetElem.getTextContent() + "  :  " + street.trim());
				assertTrue((StreetElem.getTextContent().toLowerCase().contains(street.trim().toLowerCase())));
			}

			Element CityElem = (Element) DirInfo.getElementsByTagName("City").item(0);
			if (city != null) {
				Log4jUtil.log("City : " + CityElem.getTextContent() + "  :  " + city.trim());
				assertTrue(CityElem.getTextContent().toLowerCase().contains(city.trim().toLowerCase()));
			}

			Element StateElem = (Element) DirInfo.getElementsByTagName("State").item(0);
			if (state != null) {
				Log4jUtil.log("State : " + StateElem.getTextContent() + "  :  " + state.trim());
				assertEquals(StateElem.getTextContent().toLowerCase(), state.trim().toLowerCase());
			}

			Element ZipCodeElem = (Element) DirInfo.getElementsByTagName("ZipCode").item(0);
			if (zipCode != null) {
				Log4jUtil.log("ZipCode : " + ZipCodeElem.getTextContent() + "  :  " + zipCode.trim());
				assertTrue(ZipCodeElem.getTextContent().toLowerCase().contains(zipCode.trim().toLowerCase()));
			}
		}
		return true;
	}

	private static Document buildUTFDOMXML(String xmlFileName)
			throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		InputStream inputStream = new FileInputStream(xmlFileName);
		Reader reader = new InputStreamReader(inputStream, "UTF-8");
		InputSource is = new InputSource(reader);
		is.setEncoding("UTF-8");

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(is);
		doc.getDocumentElement().normalize();
		return doc;
	}

	public static int readUnseenMessages(String xmlFileName, String messageStatusUpdateURL)
			throws ParserConfigurationException, SAXException, IOException, TransformerException, InterruptedException {
		IHGUtil.PrintMethodName();
		Document doc = buildUTFDOMXML(xmlFileName);
		Node StatusNode = doc.getElementsByTagName("StatusCode").item(0);

		Element statusElem = (Element) StatusNode;
		Log4jUtil.log(
				"Verifying Response Status actual " + statusElem.getTextContent().toString() + " with Expected 200");
		assertEquals(statusElem.getTextContent().toString(), "200");

		NodeList MessageHeader = doc.getElementsByTagName("DirectMessageHeader");

		Log4jUtil.log("MessageHeader: " + MessageHeader.getLength());
		if (MessageHeader.getLength() > 0) {
			Thread.sleep(3000);
			for (int i = 0; i < MessageHeader.getLength(); i++) {
				Element stpref = (Element) MessageHeader.item(i).getParentNode();
				Element MessageUniqueid = (Element) stpref.getElementsByTagName("MessageUid").item(i);
				Log4jUtil.log("MessageId: " + MessageUniqueid.getTextContent());

				String messageUpdateURL = messageStatusUpdateURL + "/" + MessageUniqueid.getTextContent()
						+ "/status/READ";
				Thread.sleep(2000);
				setupHttpPostRequest(messageUpdateURL, " ", xmlFileName);
			}
		}

		return MessageHeader.getLength();
	}

	public static String verifyUnseenMessageListAndGetMessageUID(String xmlFileName, String subject)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {
		IHGUtil.PrintMethodName();
		Document doc = buildUTFDOMXML(xmlFileName);
		String messageUid = "";

		Node StatusNode = doc.getElementsByTagName("StatusCode").item(0);
		Element statusElem = (Element) StatusNode;
		Log4jUtil.log("Verifying Status actual " + statusElem.getTextContent().toString() + " with Expected 200");
		assertEquals(statusElem.getTextContent().toString(), "200");

		NodeList MessageHeader = doc.getElementsByTagName("DirectMessageHeader");
		for (int i = 0; i < MessageHeader.getLength(); i++) {
			Element stpref = (Element) MessageHeader.item(i).getParentNode();
			Element MessageUniqueid = (Element) stpref.getElementsByTagName("MessageUid").item(i);
			// Log4jUtil.log("MessageId: "+MessageUniqueid.getTextContent());
			Element PublicSubject = (Element) stpref.getElementsByTagName("PublicSubject").item(i);

			if (subject.equalsIgnoreCase(PublicSubject.getTextContent())) {
				Log4jUtil.log("PublicSubject Message Found : " + PublicSubject.getTextContent());
				Log4jUtil.log("MessageId: " + MessageUniqueid.getTextContent());
				messageUid = MessageUniqueid.getTextContent();
			}
		}
		return messageUid;
	}

	public static void verifyUnseenMessageBody(String xmlFileName, String subject, String fileName, String to,
			String from, String messageBody, String attachFile)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {
		IHGUtil.PrintMethodName();
		Document doc = buildUTFDOMXML(xmlFileName);
		Node StatusNode = doc.getElementsByTagName("StatusCode").item(0);
		Element statusElem = (Element) StatusNode;
		Log4jUtil.log("Verifying Status actual " + statusElem.getTextContent().toString() + " with Expected 200");
		assertEquals(statusElem.getTextContent().toString(), "200");

		Node ToNode = doc.getElementsByTagName("Recipients").item(0);
		Log4jUtil.log("Verifying Recipient Address actual : " + ToNode.getChildNodes().item(1).getTextContent()
				+ " with Expected " + to);
		if (ToNode.getChildNodes().item(1).getTextContent().contains(to)) {
			assertTrue(true, "Recipents Matched !!");
		}
		Node FromNode = doc.getElementsByTagName("FromAddress").item(0);
		Log4jUtil.log("Verifying From Address actual : " + FromNode.getChildNodes().item(1).getTextContent()
				+ " with Expected " + from);
		if (FromNode.getChildNodes().item(1).getTextContent().contains(to)) {
			assertTrue(true, "From Address Matched !!");
		}

		Node PublicSubject = doc.getElementsByTagName("PublicSubject").item(0);
		Element subjectElem = (Element) PublicSubject;
		Log4jUtil.log(
				"Verifying Subject actual " + subjectElem.getTextContent().toString() + " with Expected " + subject);
		assertEquals(subjectElem.getTextContent().toString(), subject);

		Node BodyText = doc.getElementsByTagName("BodyText").item(0);
		Element bodyElem = (Element) BodyText;
		Log4jUtil
				.log("Verifying Body actual " + bodyElem.getTextContent().toString() + " with Expected " + messageBody);
		assertEquals(bodyElem.getTextContent().toString(), messageBody);

		if (!fileName.contains("none")) {
			Node Attachment = doc.getElementsByTagName("Attachment").item(0);

			Log4jUtil.log("Verifying Attachment actual " + Attachment.getChildNodes().item(0).getTextContent()
					+ " with Expected " + fileName);
			if (Attachment.getChildNodes().item(0).getTextContent().contains(fileName)) {
				assertTrue(true, "Attachment Matched !!");
			} else {
				assertTrue(false, "Attachment Not Found !!");
			}
			Log4jUtil.log("Verifying Attachment in response with Attachment in request body");
			Boolean AttachmentMatch = matchBase64String(Attachment.getChildNodes().item(2).getTextContent(),
					attachFile);
			Log4jUtil.log("Attachment Matched " + AttachmentMatch);
			assertTrue(AttachmentMatch, "Attachment Content Matched !!");

			if (!Attachment.getChildNodes().item(2).getTextContent().isEmpty()) {
				Log4jUtil.log("Attachment Body not Empty");
				assertTrue(true, "Attachment Found!!");
			}

		}
	}

	public static int setupHttpGetRequestInvalid(String strUrl, String responseFilePath) throws IOException {
		IHGUtil.PrintMethodName();
		int responseCode = 0;
		IOAuthTwoLeggedClient oauthClient = new OAuth2Client();
		Log4jUtil.log("Get Request Url: " + strUrl);
		HttpGet httpGetReq = new HttpGet(strUrl);
		try {

			httpGetReq.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000)
					.setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
			HttpResponse resp = oauthClient.httpGetRequest(httpGetReq);
			HttpEntity entity = resp.getEntity();
			String sResp = null;
			if (entity != null) {
				sResp = EntityUtils.toString(entity);
				Log4jUtil.log("Check for http response " + resp.getStatusLine().getStatusCode());
				responseCode = resp.getStatusLine().getStatusCode();

				writeFile(responseFilePath, sResp);

			} else {
				Log4jUtil.log("204 response found");

			}
			EntityUtils.consume(entity);
		} catch (Exception Error) {
			Log4jUtil.log("Exception with Get Call " + Error);
		} finally {
			httpGetReq.releaseConnection();
		}

		return responseCode;
	}

	public static int setupHttpPostInvalidRequest(String strUrl, String payload, String responseFilePath)
			throws IOException {
		IHGUtil.PrintMethodName();
		int statusCode = 0;
		try {
			IOAuthTwoLeggedClient oauthClient = new OAuth2Client();
			Log4jUtil.log("Post Request Url: " + strUrl);
			HttpPost httpPostReq = new HttpPost(strUrl);
			httpPostReq.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000)
					.setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);

			StringEntity se = new StringEntity(payload);
			httpPostReq.setEntity(se);
			httpPostReq.addHeader("Accept", "application/xml");
			httpPostReq.addHeader("Content-Type", "application/xml");
			httpPostReq.addHeader("Noun", "Encounter");
			httpPostReq.addHeader("Verb", "Completed");
			HttpResponse resp = oauthClient.httpPostRequest(httpPostReq);

			String sResp = EntityUtils.toString(resp.getEntity());

			Log4jUtil.log("Response Code" + resp.getStatusLine().getStatusCode());
			statusCode = resp.getStatusLine().getStatusCode();
			writeFile(responseFilePath, sResp);

		} catch (Exception E) {
			Log4jUtil.log("Exception caught " + E);
			E.getCause().printStackTrace();
		}
		return statusCode;
	}

	public static boolean matchBase64String(String target, String source) {

		int target_len = target.length();
		int source_len = source.length();
		boolean found = false;

		for (int i = 0; (i < source_len && !found); ++i) {

			int j = 0;

			while (!found) {

				if (j >= target_len) {
					break;
				}

				else if (target.charAt(j) != source.charAt(i + j)) {
					break;
				} else {
					++j;
					if (j == target_len) {
						found = true;
					}
				}
			}

		}

		return found;

	}

	public static void verifyPatientCCDFormInfo(String responsepath, List<String> list)
			throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException,
			JDOMException {
		{
			IHGUtil.PrintMethodName();
			Document doc = buildDOMXML(responsepath);
			Log4jUtil.log("finding CCDFORM INFO");
			NodeList nodes = doc.getElementsByTagName(IntegrationConstants.CCDTAG);
			Node node2 = nodes.item(0);

			String Responsexml = node2.getTextContent();

			Source xmlSource = new StreamSource(responsepath);
			Source xsltSource = new StreamSource(new StringReader(Responsexml));

			File resultFile = File.createTempFile("Streams", ".xml");
			Result result = new StreamResult(resultFile);

			Log4jUtil.log("Results will go to: " + resultFile.getAbsolutePath());
			TransformerFactory transFact = TransformerFactory.newInstance();

			Transformer trans = transFact.newTransformer(xsltSource);

			trans.transform(xmlSource, result);

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = factory.newDocumentBuilder();
			Document doc1 = docBuilder.parse(resultFile.getAbsolutePath());
			Log4jUtil.log("---------------Verifying Basic Information-------------------------");
			Node SEXName = doc1.getElementsByTagName(IntegrationConstants.ADMINISTRATIVEGENDERCODE).item(0);
			Attr attr = (Attr) SEXName.getAttributes().getNamedItem(IntegrationConstants.ATRRIBUTE1);
			assertTrue(attr.getValue().equals("MALE"), "Gender values different from Expected");
			Log4jUtil.log("Verifying gender Value actual " + attr.getValue().toString().toLowerCase()
					+ " with Expected " + list.get(21));
			Log4jUtil.log("---------------Verifying Emergency Contacts Information---------------");
			Node entityrel = doc1.getElementsByTagName(IntegrationConstants.ASSOCIATEDENTITY).item(0);
			Element relation1 = (Element) entityrel;
			Element relationname = (Element) relation1.getElementsByTagName("originalText").item(0);
			assertEquals(relationname.getTextContent(), list.get(2),
					"Relation Actual values are different from expected");
			Log4jUtil.log("Verifying Relation actual " + relationname.getTextContent().toString() + " with Expected "
					+ list.get(2));
			Node entitynode = doc1.getElementsByTagName(IntegrationConstants.ASSOCIATEDPERSON).item(0);
			Element name = (Element) entitynode;

			Element relfirstname1 = (Element) name.getElementsByTagName(IntegrationConstants.GIVENNAME).item(0);
			assertEquals(relfirstname1.getTextContent(), list.get(0),
					"Relfirstname Actual values are different from expected");
			Log4jUtil.log("Verifying RelFirstName actual " + relfirstname1.getTextContent().toString()
					+ " with Expected " + list.get(0));
			Element relLAstname1 = (Element) name.getElementsByTagName(IntegrationConstants.FAMILY).item(0);
			assertEquals(relLAstname1.getTextContent(), list.get(1),
					"RelLAstname Actual values are different from expected");
			Log4jUtil.log("Verifying RelLastName actual " + relLAstname1.getTextContent().toString() + " with Expected "
					+ list.get(1));
			Node assignedentity = doc1.getElementsByTagName(IntegrationConstants.DOCUMENTATIONOF).item(0);
			assertEquals(assignedentity.getTextContent(), list.get(17),
					"Providername's Actual values are different from expected");
			Log4jUtil.log("Verifying Providers actual " + assignedentity.getTextContent().toString() + " with Expected "
					+ list.get(17));
			Node Insurancename1 = doc1.getElementsByTagName(IntegrationConstants.REPRESENTEDORGANIZATION).item(1);
			Log4jUtil.log(
					"---------------Verifying Primary and Secondary Insurance Information------------------------");
			assertEquals(Insurancename1.getTextContent(), list.get(5),
					"PrimaryInsurance Actual values are different from expected");
			Log4jUtil.log("Verifying PrimaryInsurance actual " + Insurancename1.getTextContent().toString()
					+ " with Expected " + list.get(5));
			Node Insurancename2 = doc1.getElementsByTagName(IntegrationConstants.REPRESENTEDORGANIZATION).item(2);
			assertEquals(Insurancename2.getTextContent(), list.get(6),
					"SecondaryInsurance Actual values are different from expected");
			Log4jUtil.log("Verifying SecondaryInsurance actual " + Insurancename2.getTextContent().toString()
					+ " with Expected " + list.get(6));

			Log4jUtil.log("------------------Verifying Vaccinations information---------------------------");
			NodeList Tetanus1 = doc1.getElementsByTagName(IntegrationConstants.SUBSTANCEADMINISTRATION);

			Element substancetext = (Element) Tetanus1.item(1);
			Element substanceTetanus1 = (Element) substancetext
					.getElementsByTagName(IntegrationConstants.ENTRYRELATIONSHIP).item(0);
			assertEquals(substanceTetanus1.getTextContent(), list.get(7),
					"Tetanus Actual values are different from expected");
			Log4jUtil.log("Verifying Tetanus actual " + substanceTetanus1.getTextContent().toString()
					+ " with Expected " + list.get(7));

			Element substancetextHPV = (Element) Tetanus1.item(2);
			Element substancetextHPV1 = (Element) substancetextHPV
					.getElementsByTagName(IntegrationConstants.ENTRYRELATIONSHIP).item(0);
			assertEquals(substancetextHPV1.getTextContent(), list.get(8),
					"HPV's Actual values are different from expected");
			Log4jUtil.log("Verifying HPV's actual " + substancetextHPV1.getTextContent().toString() + " with Expected "
					+ list.get(8));

			Element substancetextInfluenza = (Element) Tetanus1.item(3);
			Element substancetextInfluenza1 = (Element) substancetextInfluenza
					.getElementsByTagName(IntegrationConstants.ENTRYRELATIONSHIP).item(0);
			assertEquals(substancetextInfluenza1.getTextContent(), list.get(9),
					"Influenza's Actual values are different from expected");
			Log4jUtil.log("Verifying Influenza1 actual " + substancetextInfluenza1.getTextContent().toString()
					+ " with Expected " + list.get(9));

			Element substancetextPneumonia = (Element) Tetanus1.item(4);
			Element substancetextPneumonia1 = (Element) substancetextPneumonia
					.getElementsByTagName(IntegrationConstants.ENTRYRELATIONSHIP).item(0);
			assertEquals(substancetextPneumonia1.getTextContent(), list.get(10),
					"Pneumonia's Actual values are different from expected");
			Log4jUtil.log("Verifying Pneumonia1 actual " + substancetextPneumonia1.getTextContent().toString()
					+ " with Expected " + list.get(10));

			Log4jUtil.log(
					"--------------------Verifying Surgeries & Hosptalization Information--------------------------");
			Node SurgeryName = doc1.getElementsByTagName(IntegrationConstants.COMPONENT).item(6);
			Element surgery = (Element) SurgeryName;
			Element SurgeryName1 = (Element) surgery.getElementsByTagName(IntegrationConstants.CONTENT).item(0);
			assertTrue(SurgeryName1.getTextContent().contains(list.get(11)),
					"SurgeryName Actual values are different from expected");
			Log4jUtil.log("Verifying SurgeryName actual " + SurgeryName1.getTextContent().toString() + " with Expected "
					+ list.get(11));

			Element SurgeryNametime = (Element) surgery.getElementsByTagName(IntegrationConstants.CONTENT).item(1);
			assertEquals(SurgeryNametime.getTextContent().toLowerCase(), list.get(12),
					"SurgeryNametime Actual values are different from expected");
			Log4jUtil.log("Verifying Surgerytimeframe actual "
					+ SurgeryNametime.getTextContent().toString().toLowerCase() + " with Expected " + list.get(12));

			Node Hospitalization = doc1.getElementsByTagName(IntegrationConstants.COMPONENT).item(9);
			Element Hospitalization1 = (Element) Hospitalization;
			Element Hospitalizationreason = (Element) Hospitalization1
					.getElementsByTagName(IntegrationConstants.CONTENT).item(0);
			assertTrue(Hospitalizationreason.getTextContent().contains("Pneumococcal arthritis"),
					"Hospitalizationreason Actual values are different from expected");
			Log4jUtil.log("Verifying Hospitalizationreason actual " + Hospitalizationreason.getTextContent().toString()
					+ " with Expected " + list.get(13));

			Element Hospitalizationreasontime = (Element) Hospitalization1
					.getElementsByTagName(IntegrationConstants.CONTENT).item(1);
			assertEquals(Hospitalizationreasontime.getTextContent().toLowerCase(), list.get(14),
					"Hospitalizationreasontime Actual values are different from expected");
			Log4jUtil.log("Verifying Hospitalizationtimeframe actual "
					+ Hospitalizationreasontime.getTextContent().toString().toLowerCase() + " with Expected "
					+ list.get(14));

			Log4jUtil.log("------------------Verifying PreviousTest Information-------------------------");
			Element Testname = (Element) surgery.getElementsByTagName(IntegrationConstants.CONTENT).item(2);
			assertEquals(Testname.getTextContent(), list.get(15),
					"Test Actual values are different from expected");
			Log4jUtil.log(
					"Verifying Test actual " + Testname.getTextContent().toString() + " with Expected " + list.get(15));

			Element Testtime = (Element) surgery.getElementsByTagName(IntegrationConstants.CONTENT).item(3);
			assertEquals(Testtime.getTextContent().toLowerCase(), list.get(16),
					"Testtime Actual values are different from expected");
			Log4jUtil.log("Verifying Testtime actual " + Testtime.getTextContent().toString().toLowerCase()
					+ " with Expected " + list.get(16));

			Log4jUtil.log("----------------------Verifying Medication Information-----------------------------");
			Node Dosage = doc1.getElementsByTagName(IntegrationConstants.COMPONENT).item(3);
			Element Dosage1 = (Element) Dosage;
			Element Dosagename = (Element) Dosage1.getElementsByTagName(IntegrationConstants.CONTENT).item(0);
			assertTrue(Dosagename.getTextContent().contains("Crestor 20 mg Tab"),
					"Dosage Actual values are different from expected");
			Log4jUtil.log("Verifying Dosage actual " + Dosagename.getTextContent().toString() + " with Expected "
					+ list.get(18));

			Log4jUtil.log("-----------------------Verifying Other Medical History Information--------------------");
			Node OtherMedicalhistory = doc1.getElementsByTagName(IntegrationConstants.COMPONENT).item(11);
			Element OtherMedicalhistory1 = (Element) OtherMedicalhistory;
			Element OtherMedicalhistoryname = (Element) OtherMedicalhistory1
					.getElementsByTagName(IntegrationConstants.CONTENT).item(0);
			assertEquals(OtherMedicalhistoryname.getTextContent(), list.get(19),
					"OtherMedicalhistory Actual values are different from expected");
			Log4jUtil.log("Verifying OtherMedical history actual " + OtherMedicalhistoryname.getTextContent().toString()
					+ " with Expected " + list.get(19));

			Element FamilyRelation1 = (Element) OtherMedicalhistory1.getElementsByTagName(IntegrationConstants.CONTENT)
					.item(1);
			assertEquals(FamilyRelation1.getTextContent(), list.get(20),
					"FamilyRelation Actual values are different from expected");
			Log4jUtil.log("Verifying FamilyRelation history actual " + FamilyRelation1.getTextContent().toString()
					+ " with Expected " + list.get(20));

		}
	}

	public static void verifyCCDHeaderDetails(String responsepath, String FormValue)
			throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(responsepath);
		Node CcdMessageHeaders = doc.getElementsByTagName(IntegrationConstants.CCDMESSAGEHEADERS).item(0);
		Element ccdheaders = (Element) CcdMessageHeaders;
		Node Formname = ccdheaders.getElementsByTagName(IntegrationConstants.ROUTINGMAP).item(0);
		Element Forms = (Element) Formname;
		NodeList KeyValuePairList = doc.getElementsByTagName(IntegrationConstants.KEYVALUEPAIR);
		for (int i = 0; i < KeyValuePairList.getLength(); i++) {
			Node FormTypeValue = Forms.getElementsByTagName(IntegrationConstants.VALUE).item(i);
			if (FormTypeValue.getTextContent().contains(FormValue)) {
				assertEquals(FormTypeValue.getTextContent(), FormValue, "Form Name is different from expected ");
			}
			if (FormTypeValue.getTextContent().contains(IntegrationConstants.FORMTYPE)) {
				assertTrue(FormTypeValue.getTextContent().contains(IntegrationConstants.FORMTYPE),
						"Form Type is different than expected");
				break;
			}
		}
	}

	public static String verifyCCDHeaderDetailsandGetURL(String responsepath, String externalID)
			throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(responsepath);
		Log4jUtil.log("finding CCDFORM INFO");
		String PDfURL1 = "";
		NodeList CcdMessageHeaders = doc.getElementsByTagName("ns2:CcdExchange");
		for (int i = 0; i < CcdMessageHeaders.getLength(); i++) {
			Element ccdheaders = (Element) CcdMessageHeaders.item(i);
			NodeList PatientDemo = ccdheaders.getElementsByTagName("PatientDemographics");
			for (int j = 0; j < PatientDemo.getLength(); j++) {
				Element childs = (Element) PatientDemo.item(j);

				NodeList PatientIdentifier = childs.getElementsByTagName("PracticePatientId");
				if (PatientIdentifier.item(j) != null) {
					Element external = (Element) PatientIdentifier.item(j);
					Log4jUtil.log("Patient External ID is " + external.getTextContent());
					ArrayList<String> names = new ArrayList<String>(Arrays.asList(external.getTextContent()));
					for (int n = 0; n < names.size(); n++) {
						if (names.get(n).contains(externalID)) {
							// Log4jUtil.log("Elements are in the condition "+names.get(n));
							Node Formname = ccdheaders.getElementsByTagName(IntegrationConstants.ROUTINGMAP).item(0);
							Element Forms = (Element) Formname;
							Node FormURL = Forms.getElementsByTagName(IntegrationConstants.KEYVALUEPAIR).item(2);
							NodeList KeyValuePairList = doc.getElementsByTagName(IntegrationConstants.KEYVALUEPAIR);
							for (int m = 0; i < KeyValuePairList.getLength(); m++) {
								Node FormTypeValue = Forms.getElementsByTagName(IntegrationConstants.VALUE).item(m);
								Node FormTypeKey = Forms.getElementsByTagName("Key").item(m);
								if (FormTypeKey.getTextContent().contains("FormsPdfLink")) {
									Log4jUtil.log("value of pdf link " + FormTypeValue.getTextContent());
									PDfURL1 = FormTypeValue.getTextContent();
									break;
								}
							}
							break;
						}
					}
				}
			}
		}
		return PDfURL1;
	}

	public static void verifyPDFBatchDetails(String responsepath, String externalPatientID, String medfusionID)
			throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException,
			JDOMException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(responsepath);
		Log4jUtil.log("Getting CCDPDFBatch URL INFO");
		NodeList nodes1 = doc.getElementsByTagName(IntegrationConstants.PATIENTFORM);
		for (int i = 0; i < nodes1.getLength(); i++) {
			Element member = (Element) nodes1.item(i);
			NodeList MfmemberID = member.getElementsByTagName(IntegrationConstants.MEDFUSIONMEMBERID);
			for (int j = 0; j < MfmemberID.getLength(); j++) {
				Element MedfusionID = (Element) MfmemberID.item(j);
				if (MedfusionID.getTextContent().equals(medfusionID)) {
					assertEquals(MedfusionID.getTextContent(), medfusionID,
							"Medfusion MemberID is  different from expected");
					Log4jUtil.log("Verifying MedfusionMemberID " + MedfusionID.getTextContent() + " with Expected"
							+ medfusionID);
					Node ExternalID = member.getElementsByTagName(IntegrationConstants.PRACTICEPATIENTID).item(0);
					assertEquals(ExternalID.getTextContent(), externalPatientID,
							"External patient ID is different from expected");
					Log4jUtil.log("Verifying ExternalID " + ExternalID.getTextContent() + " with Expected"
							+ externalPatientID);
					Node GetPDFUrl = member.getElementsByTagName(IntegrationConstants.PDFURLLINK).item(0);
					String PDFURL = GetPDFUrl.getTextContent();
					Log4jUtil.log("Get URL is" + PDFURL);
				}
			}
		}
	}

	public static Response setupHttpGetRequestForPDF(String strUrl, String responseFilePath)
			throws IOException, URISyntaxException, TransformerException {
		IHGUtil.PrintMethodName();
		IOAuthTwoLeggedClient oauthClient = new OAuth2Client();
		Log4jUtil.log("Get Request Url: " + strUrl);
		HttpGet httpGetReq = new HttpGet(strUrl);
		HttpResponse resp = oauthClient.httpGetRequest(httpGetReq);

		httpGetReq.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000)
				.setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
		InputStream is = resp.getEntity().getContent();
		byte[] byteArray = IOUtils.toByteArray(is);
		FileOutputStream fos = new FileOutputStream(responseFilePath);
		fos.write(byteArray);
		fos.flush();
		fos.close();
		return Response.status(200).entity(responseFilePath).build();
	}

	public static void comparePDFfiles(String file1, String file2) throws Exception {
		String pdfFromPortal = ExternalFileReader.base64Encoder(file1, false);
		String pdfFromGet = ExternalFileReader.base64Encoder(file2, false);
		Log4jUtil.log("pdfFromPortal----------------");
		Log4jUtil.log(pdfFromPortal);
		Log4jUtil.log("pdfFromGet----------------");
		Log4jUtil.log(pdfFromGet);
		Log4jUtil.log("----------------------------");
		Boolean pdfMatch = matchBase64String(pdfFromPortal, pdfFromGet);
		Log4jUtil.log("Is Pdf Matched : " + pdfMatch);
		assertTrue(pdfMatch, "Portal PDF Did not Matched with PDF in ccdExchangePdf call");
	}

	public static int setupHttpDeleteRequestExceptOauth(String strUrl, String responseFilePath, String token)
			throws org.apache.http.ParseException, IOException, URISyntaxException {
		IHGUtil.PrintMethodName();
		HttpClient client = new DefaultHttpClient();
		Log4jUtil.log("Get Request Url: " + strUrl);

		HttpDelete httpDelReq = new HttpDelete(strUrl);
		httpDelReq.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000)
				.setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
		httpDelReq.setURI(new URI(strUrl));
		httpDelReq.addHeader("Authorization", "Bearer " + token);
		httpDelReq.addHeader("Content-Type", "application/xml");

		HttpResponse resp = client.execute(httpDelReq);
		HttpEntity entity = resp.getEntity();
		String sResp = "";
		if (entity != null) {
			sResp = EntityUtils.toString(entity);
			Log4jUtil.log("Check for http response");
		} else {
			Log4jUtil.log("http 204 response is empty");
		}
		writeFile(responseFilePath, sResp);
		return resp.getStatusLine().getStatusCode();
	}

	public static void isPreCheckPatientAppeared(String responsePath, String externalPatientID, String firstname)
			throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(responsePath);

		NodeList nodes = doc.getElementsByTagName(IntegrationConstants.PRACTICE_ID);
		for (int i = 0; i < nodes.getLength(); i++) {
			if (nodes.item(i).getTextContent().equals(externalPatientID)) {
				Log4jUtil.log("Searching: External Patient ID:" + externalPatientID
						+ ", and Actual External Patient ID is:" + nodes.item(i).getTextContent().toString());
				NodeList node = doc.getElementsByTagName(IntegrationConstants.MEDFUSIONPATIENTID);
				node = doc.getElementsByTagName(IntegrationConstants.CCDTAG);
				Log4jUtil.log("Expected '" + "<given>" + firstname + "</given>" + "' is found in CCD XML.");
				Boolean patientFound = node.item(i).getTextContent().contains("<given>" + firstname + "</given>");
				Log4jUtil.log("Is Patient appeared = " + patientFound);
				if (patientFound == true) {
					assertTrue(patientFound, "Updated patient name appeared");
					break;
				}
			}
			if (i == nodes.getLength() - 1) {
				fail("Patient was not found");
			}
		}
	}

	public static String verifyPreCheckPDFBatchDetails(String responsepath, String externalPatientID)
			throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException,
			JDOMException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(responsepath);
		String PatientPDFUrl = "";
		NodeList nodes1 = doc.getElementsByTagName(IntegrationConstants.PATIENTFORM);
		for (int i = 0; i < nodes1.getLength(); i++) {
			Element member = (Element) nodes1.item(i);
			if ((i + 1) == nodes1.getLength()) {
				Node ExternalID = member.getElementsByTagName(IntegrationConstants.PRACTICEPATIENTID).item(0);
				assertEquals(ExternalID.getTextContent(), externalPatientID,
						"External patient ID is different from expected");
				Log4jUtil.log(
						"Verifying ExternalID " + ExternalID.getTextContent() + " with Expected" + externalPatientID);
				Node GetPDFUrl = member.getElementsByTagName(IntegrationConstants.PDFURLLINK).item(0);
				String PDFURL = GetPDFUrl.getTextContent();
				Log4jUtil.log("Get URL is " + PDFURL);
				PatientPDFUrl = PDFURL;
			}
		}
		return PatientPDFUrl;
	}

	public static String setupHttpGetRequestWithEmptyResponse(String strUrl, String responseFilePath)
			throws IOException {
		IHGUtil.PrintMethodName();
		IOAuthTwoLeggedClient oauthClient = new OAuth2Client();
		Log4jUtil.log("Get Request Url: " + strUrl);
		HttpGet httpGetReq = new HttpGet(strUrl);
		httpGetReq.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000)
				.setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
		HttpResponse resp = oauthClient.httpGetRequest(httpGetReq);

		HttpEntity entity = resp.getEntity();
		String sResp = null;
		if (entity != null) {
			sResp = EntityUtils.toString(entity);
			Log4jUtil.log("Check for http 200 response");
			assertTrue(
					resp.getStatusLine().getStatusCode() == 200 || resp.getStatusLine().getStatusCode() == 204,
					"Get Request response is " + resp.getStatusLine().getStatusCode()
							+ " instead of 200. Response message received:\n" + sResp);
			writeFile(responseFilePath, sResp);
		} else {
			Log4jUtil.log("204 response found");
		}
		if (resp.containsHeader(IntegrationConstants.NEXTURI)) {
			Header[] h = resp.getHeaders(IntegrationConstants.NEXTURI);
			return h[0].getValue();
		}
		return Integer.toString(resp.getStatusLine().getStatusCode());
	}

	public static String setupHttpJSONPostRequest(String strUrl, String payload, String responseFilePath,
			String accessToken) throws IOException, URISyntaxException {
		IHGUtil.PrintMethodName();

		HttpClient client = new DefaultHttpClient();
		Log4jUtil.log("Post Request Url: " + strUrl);

		HttpPost request = new HttpPost();
		request.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000)
				.setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
		request.setURI(new URI(strUrl));
		request.setEntity(new StringEntity(payload));
		request.setHeader("Noun", "Encounter");
		request.setHeader("Verb", "Completed");
		request.addHeader("Authorization", "Bearer "+accessToken);
		request.addHeader("Content-Type", "application/json");
		HttpResponse response = client.execute(request);
		String sResp = EntityUtils.toString(response.getEntity());
		Log4jUtil.log("Check for http 200/202 response");
		assertTrue(
				response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 202,
				"Get Request response is " + response.getStatusLine().getStatusCode()
						+ " instead of 200/202. Response message:\n" + sResp);
		Log4jUtil.log("Response Code" + response.getStatusLine().getStatusCode());
		writeFile(responseFilePath, sResp);

		if (response.containsHeader(IntegrationConstants.LOCATION_HEADER)) {
			Header[] h = response.getHeaders(IntegrationConstants.LOCATION_HEADER);
			return h[0].getValue();
		}
		return null;
	}

	public static void verifyPatientDetailsForPrecheckAppointment(String xmlFileName, String practicePatientId,
			List<?> list) throws ParserConfigurationException, SAXException, IOException, ParseException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);
		NodeList patients = doc.getElementsByTagName(IntegrationConstants.PRACTICE_PATIENT_ID);
		// boolean found = false;
		for (int i = 0; i < patients.getLength(); i++) {
			if (patients.item(i).getTextContent().equals(practicePatientId)) {
				Log4jUtil
						.log("Validating patient number " + (i + 1) + " with practice patient id " + practicePatientId);
				Element patient = (Element) patients.item(i).getParentNode().getParentNode();
				if (!patient.getElementsByTagName(IntegrationConstants.FIRST_NAME).item(0).getTextContent().isEmpty()
						&& list.get(0) != null) {

					Node FirstName = patient.getElementsByTagName(IntegrationConstants.FIRST_NAME).item(0);
					// Log4jUtil.log("Patient FirstName"+FirstName.getTextContent());
					if (FirstName.getTextContent().equalsIgnoreCase(list.get(0).toString())) {

						assertEquals(FirstName.getTextContent(), list.get(0),
								"Patient has different FirstName than expected. FirstName is: "
										+ FirstName.getTextContent());
						Node LastName = patient.getElementsByTagName(IntegrationConstants.LAST_NAME).item(0);
						assertEquals(LastName.getTextContent(), list.get(1),
								"Patient has different LastName than expected. LastName is: "
										+ LastName.getTextContent());
						Log4jUtil.log(
								"Checking Patient Address1, Address2, MiddleName, DateOfBirth, City, State, Zipcode");
						Node Line1 = patient.getElementsByTagName(IntegrationConstants.LINE1).item(0);
						assertEquals(Line1.getTextContent(), list.get(2),
								"Patient has different Address1 than expected. Address1 is: " + Line1.getTextContent());
						Node Line2 = patient.getElementsByTagName(IntegrationConstants.LINE2).item(0);
						assertEquals(Line2.getTextContent(), list.get(3),
								"Patient has different Address2 than expected. Address2 is: " + Line2.getTextContent());
						Node MiddleN = patient.getElementsByTagName(IntegrationConstants.MIDDLENAME).item(0);
						assertEquals(MiddleN.getTextContent(), list.get(4),
								"Patient has different MiddleName than expected. MiddleName is: "
										+ MiddleN.getTextContent());
						String bDate = list.get(5).toString().replaceAll("01 ", "01/");
						String birthdate = convertDate(bDate) + "T00:00:00";
						Log4jUtil.log("Checking Patient Date of Birth, Middle Name");
						Node DateOfBirth = patient.getElementsByTagName(IntegrationConstants.DATEOFBIRTH).item(0);
						assertEquals(DateOfBirth.getTextContent(), birthdate,
								"Patient has different DateOfBirth than expected. DateOfBirth is: "
										+ DateOfBirth.getTextContent());

						Node City = patient.getElementsByTagName(IntegrationConstants.CITY).item(0);
						assertEquals(City.getTextContent(), list.get(6),
								"Patient has different City than expected. City is: " + City.getTextContent());
						Node State = patient.getElementsByTagName(IntegrationConstants.STATE).item(0);
						assertEquals(State.getTextContent(), list.get(7),
								"Patient has different State than expected. Stateis: " + State.getTextContent());
						Node ZipCode = patient.getElementsByTagName(IntegrationConstants.ZIPCODE).item(0);
						assertEquals(ZipCode.getTextContent(), list.get(8),
								"Patient has different ZipCode than expected. ZipCode is: " + ZipCode.getTextContent());
						Node HomePhone = patient.getElementsByTagName(IntegrationConstants.HOMEPHONE).item(0);
						assertEquals(HomePhone.getTextContent(), list.get(9),
								"Patient has different HomePhone than expected. HomePhone is: "
										+ HomePhone.getTextContent());
						Node EmailAddress = patient.getElementsByTagName(IntegrationConstants.EMAIL_ADDRESS).item(0);
						assertEquals(EmailAddress.getTextContent(), list.get(10),
								"Patient has different EmailAddress than expected. EmailAddress is: "
										+ EmailAddress.getTextContent());
					} else {
						Log4jUtil.log(
								patient.getElementsByTagName(IntegrationConstants.FIRST_NAME).item(0).getTextContent()
										+ "Demographics update received without change." + list.get(0));
					}
				} else {
					if (patient.getElementsByTagName(IntegrationConstants.FIRST_NAME).item(0).getTextContent()
							.isEmpty()) {
						Log4jUtil.log("No updates for Demographics.");
					} else {
						Log4jUtil.log(
								patient.getElementsByTagName(IntegrationConstants.FIRST_NAME).item(0).getTextContent()
										+ "Demographics update received without change.");
					}
				}
				Log4jUtil.log("---------------------------------------------------------------------------------");
				Log4jUtil.log("Verifying Precheck Patient Status");
				Node PatientStatus = patient.getElementsByTagName(IntegrationConstants.STATUS).item(0);
				assertEquals(PatientStatus.getTextContent(), list.get(11),
						"Patient has different status than expected. status is: " + PatientStatus.getTextContent());
				Node PatientSource = patient.getElementsByTagName(IntegrationConstants.SOURCE).item(0);
				assertEquals(PatientSource.getTextContent(), list.get(12),
						"Patient has different source value than expected. source is: "
								+ PatientSource.getTextContent());
				Log4jUtil.log("---------------------------------------------------------------------------------");

				Node pNode = patient.getElementsByTagName(IntegrationConstants.PRIMARYINSURANCE).item(0);
				Element eleP = (Element) pNode;

				if (list.get(17) != null && !eleP.getElementsByTagName(IntegrationConstants.COMPANYNAME).item(0)
						.getTextContent().isEmpty()) {
					Log4jUtil.log("Verifying Patient Insurance Details");
					Log4jUtil.log(
							"Patient Primary Insurance Details Insurance Name, Subscriber Name n ID, Group Number, Date Issued, Customer Service Phone Number");
					Node CompanyName = eleP.getElementsByTagName(IntegrationConstants.COMPANYNAME).item(0);
					assertEquals(CompanyName.getTextContent(), list.get(13),
							"Patient has different Primary Insurance Name than expected. CompanyName is: "
									+ CompanyName.getTextContent());
					Node subNode = patient.getElementsByTagName(IntegrationConstants.SUBSCRIBERNAME).item(0);
					Element eleSub = (Element) subNode;

					Node SubscriberFirstName = eleSub.getElementsByTagName(IntegrationConstants.FIRSTNAME).item(0);
					assertEquals(SubscriberFirstName.getTextContent(), list.get(14),
							"Patient has different Primary Subscriber FirstName than expected. SubscriberFirstName is: "
									+ SubscriberFirstName.getTextContent());

					Node SubscriberId = eleP.getElementsByTagName(IntegrationConstants.SUBSCRIBERID).item(0);
					assertEquals(SubscriberId.getTextContent(), list.get(15),
							"Patient has different Primary SubscriberId than expected. SubscriberId is: "
									+ SubscriberId.getTextContent());

					Node GroupNumber = eleP.getElementsByTagName(IntegrationConstants.GROUPNUMBER).item(0);
					assertEquals(GroupNumber.getTextContent(), list.get(16),
							"Patient has different Primary GroupNumber than expected. GroupNumber is: "
									+ GroupNumber.getTextContent());

					String iDate = list.get(17).toString().replaceAll("01 ", "01/");
					String effectiveD = convertDate(iDate) + "T00:00:00";

					Node DateIssued = eleP.getElementsByTagName(IntegrationConstants.EFFECTIVEDATE).item(0);
					assertEquals(DateIssued.getTextContent(), effectiveD,
							"Patient has different Primary DateIssued than expected. DateIssued is: "
									+ DateIssued.getTextContent());

					Node ClaimsPhone = eleP.getElementsByTagName(IntegrationConstants.CLAIMSPHONE).item(0);
					assertEquals(ClaimsPhone.getTextContent(), list.get(18),
							"Patient has different Primary ClaimsPhone than expected. DateIssued is: "
									+ ClaimsPhone.getTextContent());
				} else {
					if (eleP.getElementsByTagName(IntegrationConstants.COMPANYNAME).item(0).getTextContent()
							.isEmpty()) {
						Log4jUtil.log("No updates for Primary Insurance.");
					}
				}

				Log4jUtil.log("---------------------------------------------------------------------------------");

				Node sNode = patient.getElementsByTagName(IntegrationConstants.SECONDARYINSURANCE).item(0);
				Element eleS = (Element) sNode;
				if (list.get(17) != null && !eleS.getElementsByTagName(IntegrationConstants.COMPANYNAME).item(0)
						.getTextContent().isEmpty()) {
					Log4jUtil.log(
							"Patient Secondary Insurance Details - Insurance Name, Subscriber Name n ID, Group Number, Date Issued, Customer Service Phone Number");
					Node CompanyName = eleS.getElementsByTagName(IntegrationConstants.COMPANYNAME).item(0);
					assertEquals(CompanyName.getTextContent(), list.get(19),
							"Patient has different Primary Insurance Name than expected. CompanyName is: "
									+ CompanyName.getTextContent());
					Node subNode = patient.getElementsByTagName(IntegrationConstants.SUBSCRIBERNAME).item(1);
					Element eleSub = (Element) subNode;

					Node SubscriberFirstName = eleSub.getElementsByTagName(IntegrationConstants.FIRSTNAME).item(0);
					assertEquals(SubscriberFirstName.getTextContent(), list.get(20),
							"Patient has different Primary Subscriber FirstName than expected. SubscriberFirstName is: "
									+ SubscriberFirstName.getTextContent());

					Node SubscriberId = eleS.getElementsByTagName(IntegrationConstants.SUBSCRIBERID).item(0);
					assertEquals(SubscriberId.getTextContent(), list.get(21),
							"Patient has different Primary SubscriberId than expected. SubscriberId is: "
									+ SubscriberId.getTextContent());

					Node GroupNumber = eleS.getElementsByTagName(IntegrationConstants.GROUPNUMBER).item(0);
					assertEquals(GroupNumber.getTextContent(), list.get(22),
							"Patient has different Primary GroupNumber than expected. GroupNumber is: "
									+ GroupNumber.getTextContent());

					String iDate = list.get(23).toString().replaceAll("01 ", "01/");
					String effectiveD = convertDate(iDate) + "T00:00:00";

					Node DateIssued = eleS.getElementsByTagName(IntegrationConstants.EFFECTIVEDATE).item(0);
					assertEquals(DateIssued.getTextContent(), effectiveD,
							"Patient has different Primary DateIssued than expected. DateIssued is: "
									+ DateIssued.getTextContent());

					Node ClaimsPhone = eleS.getElementsByTagName(IntegrationConstants.CLAIMSPHONE).item(0);
					assertEquals(ClaimsPhone.getTextContent(), list.get(24),
							"Patient has different Primary ClaimsPhone than expected. DateIssued is: "
									+ ClaimsPhone.getTextContent());
				} else {
					if (eleS.getElementsByTagName(IntegrationConstants.COMPANYNAME).item(0).getTextContent()
							.isEmpty()) {
						Log4jUtil.log("No updates for Secondary Insurance.");
					}
				}

				Log4jUtil.log("---------------------------------------------------------------------------------");

				Node tNode = patient.getElementsByTagName(IntegrationConstants.TERTIARYINSURANCE).item(0);
				Element eleT = (Element) tNode;
				if (list.get(17) != null && !eleT.getElementsByTagName(IntegrationConstants.COMPANYNAME).item(0)
						.getTextContent().isEmpty()) {
					Log4jUtil.log(
							"Patient Tertiary Insurance Details - Insurance Name, Subscriber Name n ID, Group Number, Date Issued, Customer Service Phone Number");
					Node CompanyName = eleT.getElementsByTagName(IntegrationConstants.COMPANYNAME).item(0);
					assertEquals(CompanyName.getTextContent(), list.get(25),
							"Patient has different Primary Insurance Name than expected. CompanyName is: "
									+ CompanyName.getTextContent());
					Node subNode = patient.getElementsByTagName(IntegrationConstants.SUBSCRIBERNAME).item(2);
					Element eleSub = (Element) subNode;

					Node SubscriberFirstName = eleSub.getElementsByTagName(IntegrationConstants.FIRSTNAME).item(0);
					assertEquals(SubscriberFirstName.getTextContent(), list.get(26),
							"Patient has different Primary Subscriber FirstName than expected. SubscriberFirstName is: "
									+ SubscriberFirstName.getTextContent());

					Node SubscriberId = eleT.getElementsByTagName(IntegrationConstants.SUBSCRIBERID).item(0);
					assertEquals(SubscriberId.getTextContent(), list.get(27),
							"Patient has different Primary SubscriberId than expected. SubscriberId is: "
									+ SubscriberId.getTextContent());

					Node GroupNumber = eleT.getElementsByTagName(IntegrationConstants.GROUPNUMBER).item(0);
					assertEquals(GroupNumber.getTextContent(), list.get(28),
							"Patient has different Primary GroupNumber than expected. GroupNumber is: "
									+ GroupNumber.getTextContent());

					String iDate = list.get(29).toString().replaceAll("01 ", "01/");
					String effectiveD = convertDate(iDate) + "T00:00:00";

					Node DateIssued = eleT.getElementsByTagName(IntegrationConstants.EFFECTIVEDATE).item(0);
					assertEquals(DateIssued.getTextContent(), effectiveD,
							"Patient has different Primary DateIssued than expected. DateIssued is: "
									+ DateIssued.getTextContent());

					Node ClaimsPhone = eleT.getElementsByTagName(IntegrationConstants.CLAIMSPHONE).item(0);
					assertEquals(ClaimsPhone.getTextContent(), list.get(30),
							"Patient has different Primary ClaimsPhone than expected. DateIssued is: "
									+ ClaimsPhone.getTextContent());
				} else {
					if (eleT.getElementsByTagName(IntegrationConstants.COMPANYNAME).item(0).getTextContent()
							.isEmpty()) {
						Log4jUtil.log("No updates for Tertiary Insurance.");
					}
				}
				Log4jUtil.log("---------------------------------------------------------------------------------");
			}
		}
	}

	public static void verifyEGQUpdatedValuesInCCDExchangeBatch(String responsepath, String EGQValue, char EGQType)
			throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(responsepath);
		if (EGQType == 'I') {
			Node GenderIdentity = doc.getElementsByTagName(IntegrationConstants.GENDERIDENTITY).item(0);
			Element GenderIdentityEle = (Element) GenderIdentity;
			Node EGQGINode = GenderIdentityEle.getElementsByTagName(IntegrationConstants.VALUE).item(0);
			Log4jUtil.log("GI node value= " + EGQGINode.getTextContent() + "   EGQValue = " + EGQValue);
			assertTrue(EGQGINode.getTextContent().trim().equalsIgnoreCase(EGQValue),
					"Value mismatched");
		}
		if (EGQType == 'S') {
			Node SexualOrientation = doc.getElementsByTagName(IntegrationConstants.SEXUALORIENTATION).item(0);
			Element SexualOrientationEle = (Element) SexualOrientation;
			Node EGQSONode = SexualOrientationEle.getElementsByTagName(IntegrationConstants.VALUE).item(0);
			Log4jUtil.log("SO node value = " + EGQSONode.getTextContent() + "   EGQValue = " + EGQValue);
			assertTrue(EGQSONode.getTextContent().trim().equalsIgnoreCase(EGQValue),
					"Value mismatched");
		}
	}

	public static ArrayList<String> verifyInsuranceCardImageInGetPIDC(String xmlFileName, String practicePatientId)
			throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		ArrayList<String> consolidatedList = new ArrayList<String>();
		Document doc = buildDOMXML(xmlFileName);
		NodeList patients = doc.getElementsByTagName(IntegrationConstants.PRACTICE_PATIENT_ID);
		for (int i = 0; i < patients.getLength(); i++) {
			if (patients.item(i).getTextContent().equals(practicePatientId)) {
				Element patient = (Element) patients.item(i).getParentNode().getParentNode();

				Node InsuranceImages = patient.getElementsByTagName(IntegrationConstants.INSURANCEIMAGES).item(0);
				Element InsuranceImgEle = (Element) InsuranceImages;

				if (InsuranceImgEle.getElementsByTagName(IntegrationConstants.PRIMARYINSURANCE).item(0) != null) {
					Node PrimaryInsurance = InsuranceImgEle.getElementsByTagName(IntegrationConstants.PRIMARYINSURANCE)
							.item(0);
					Element PrimaryInsuranceEle = (Element) PrimaryInsurance;
					Node PFrontImageLink = PrimaryInsuranceEle.getElementsByTagName(IntegrationConstants.FRONTIMAGELINK)
							.item(0);
					Node PBackImageLink = PrimaryInsuranceEle.getElementsByTagName(IntegrationConstants.BACKIMAGELINK)
							.item(0);
					consolidatedList.add(PFrontImageLink.getTextContent());
					consolidatedList.add(PBackImageLink.getTextContent());
					Log4jUtil.log("Patient Primary FrontImageLink =" + PFrontImageLink.getTextContent());
					Log4jUtil.log("Patient Primary BackImageLink =" + PBackImageLink.getTextContent());
				}
				if (InsuranceImgEle.getElementsByTagName(IntegrationConstants.SECONDARYINSURANCE).item(0) != null) {

					Node SecondaryInsurance = InsuranceImgEle
							.getElementsByTagName(IntegrationConstants.SECONDARYINSURANCE).item(0);
					Element SecondaryInsuranceEle = (Element) SecondaryInsurance;
					Node SFrontImageLink = SecondaryInsuranceEle
							.getElementsByTagName(IntegrationConstants.FRONTIMAGELINK).item(0);
					Node SBackImageLink = SecondaryInsuranceEle.getElementsByTagName(IntegrationConstants.BACKIMAGELINK)
							.item(0);
					consolidatedList.add(SFrontImageLink.getTextContent());
					consolidatedList.add(SBackImageLink.getTextContent());
					Log4jUtil.log("Patient Secondary FrontImageLink =" + SFrontImageLink.getTextContent());
					Log4jUtil.log("Patient Secondary BackImageLink =" + SBackImageLink.getTextContent());
				}
				if (InsuranceImgEle.getElementsByTagName(IntegrationConstants.TERTIARYINSURANCE).item(0) != null) {
					Node TertiaryInsurance = InsuranceImgEle
							.getElementsByTagName(IntegrationConstants.TERTIARYINSURANCE).item(0);
					Element TertiaryInsuranceEle = (Element) TertiaryInsurance;
					Node TFrontImageLink = TertiaryInsuranceEle
							.getElementsByTagName(IntegrationConstants.FRONTIMAGELINK).item(0);
					Node TBackImageLink = TertiaryInsuranceEle.getElementsByTagName(IntegrationConstants.BACKIMAGELINK)
							.item(0);
					consolidatedList.add(TFrontImageLink.getTextContent());
					consolidatedList.add(TBackImageLink.getTextContent());
					Log4jUtil.log("Patient Tertiary FrontImageLink =" + TFrontImageLink.getTextContent());
					Log4jUtil.log("Patient Tertiary BackImageLink =" + TBackImageLink.getTextContent());
				}
			}
		}
		return consolidatedList;
	}

	public static void verifyInsuranceImageDetailsBase64(String xmlFileName, String uploadedImagBase64, String fileName,
			String fileType) throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);
		Node InsuranceImageDetail = doc.getElementsByTagName(IntegrationConstants.CONTENT).item(0);
		Node InsuranceFileName = doc.getElementsByTagName(IntegrationConstants.FILENAME).item(0);
		Node InsuranceFileType = doc.getElementsByTagName(IntegrationConstants.MIMETYPE).item(0);

		Element attachmentContentEle = (Element) InsuranceImageDetail;
		Element InsuranceFileNameEle = (Element) InsuranceFileName;
		Element InsuranceFileTypeEle = (Element) InsuranceFileType;

		Log4jUtil.log("Matching Actual FileName : " + InsuranceFileNameEle.getTextContent() + "   containing Expected "
				+ fileName + " in order.");
		assertTrue(InsuranceFileNameEle.getTextContent().contains(fileName), "File name did not Matched");
		Log4jUtil.log(
				"Matching Actual FileType : " + InsuranceFileTypeEle.getTextContent() + " with Expected " + fileType);
		assertTrue(InsuranceFileTypeEle.getTextContent().equalsIgnoreCase(fileType),
				"File type did not Matched");

		Boolean base64FileMatch = matchBase64String(attachmentContentEle.getTextContent(), uploadedImagBase64);
		Log4jUtil.log("Does insurance image card Matched : " + base64FileMatch);
		assertTrue(base64FileMatch,
				"Image uploaded Did not Matched with image in the insurance detail api call.");
	}

	public static Boolean deleteFile(String fileName) {
		Boolean isFileDeleted = false;
		try {
			File file = new File(fileName);
			if (file.delete()) {
				Log4jUtil.log(file.getName() + " is deleted!");
				isFileDeleted = true;
			} else {
				Log4jUtil.log("Delete operation is failed please delete the file manually.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isFileDeleted;
	}

	public static boolean isOnDemandRequestSubmitted(String xmlFileName, String patientId)
			throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);
		NodeList nodes = doc.getElementsByTagName(IntegrationConstants.PATIENTID);
		{
			Log4jUtil.log(nodes.item(0).getTextContent() + " Expectd Patient Id and Actual PatientId  : " + patientId);
			assertTrue(nodes.item(0).getTextContent().equals(patientId), "The Patient Id should match");
		}
		return true;
	}

	public static void isPatientDeactivatedorDeleted(String xmlFileName, String practicePatientId, String firstName,
			String lastName, String patientID, String portalStatus)
			throws ParserConfigurationException, SAXException, IOException {
		System.out.println(xmlFileName + " " + practicePatientId + " " + firstName + " " + lastName + " " + patientID);
		Document doc = buildDOMXML(xmlFileName);
		NodeList patients = doc.getElementsByTagName(IntegrationConstants.PRACTICE_PATIENT_ID);
		boolean found = false;
		for (int i = 0; i < patients.getLength(); i++) {
			if (patients.item(i).getTextContent().equals(practicePatientId)) {
				Log4jUtil.log("Searching: External Patient ID:" + practicePatientId
						+ ", and Actual External Patient ID is:" + patients.item(i).getTextContent().toString());
				Element patient = (Element) patients.item(i).getParentNode().getParentNode();
				Node status = patient.getElementsByTagName(IntegrationConstants.STATUS).item(0);
				assertEquals(status.getTextContent(), IntegrationConstants.REGISTERED,
						"Patient has different status than expected. Status is: " + status.getTextContent());

				Node portalstatus = patient.getElementsByTagName(IntegrationConstants.PORTALSTATUS).item(0);
				if (portalStatus.equalsIgnoreCase("DEACTIVATED")) {
					assertEquals(portalstatus.getTextContent(), IntegrationConstants.DEACTIVATED,
							"Patient has different portal status than expected. Portal Status is: "
									+ portalstatus.getTextContent());
					Log4jUtil.log("Patient Portal Status is: " + portalstatus.getTextContent());
				} else if (portalStatus.equalsIgnoreCase("DELETED")) {
					assertEquals(portalstatus.getTextContent(), IntegrationConstants.DELETED,
							"Patient has different portal status than expected. Portal Status is: "
									+ portalstatus.getTextContent());
					Log4jUtil.log("Patient Portal Status is: " + portalstatus.getTextContent());
				} else {
					Log4jUtil.log("Invalid Portal Status");
				}

				Node nfirstName = patient.getElementsByTagName(IntegrationConstants.FIRST_NAME).item(0);
				Log4jUtil.log("Searching: Patient FirstName:" + firstName + ", and Actual Patient FirstName is:"
						+ nfirstName.getTextContent().toString());
				assertEquals(nfirstName.getTextContent(), firstName,
						"Patient has different FirstName than expected. FirstName is: " + nfirstName.getTextContent());
				Node nlastName = patient.getElementsByTagName(IntegrationConstants.LAST_NAME).item(0);
				Log4jUtil.log("Searching: Patient LastName:" + lastName + ", and Actual Patient LastName is:"
						+ nlastName.getTextContent().toString());
				assertEquals(nlastName.getTextContent(), lastName,
						"Patient has different LastName than expected. LastName is: " + nlastName.getTextContent());
				if (patientID != null) {
					Node nPatientId = patient.getElementsByTagName(IntegrationConstants.MEDFUSIONID).item(0);
					assertEquals(nPatientId.getTextContent(), patientID,
							"Patient has different MedfusionPatientId than expected. MedfusionPatientId is: "
									+ nPatientId.getTextContent());
					Log4jUtil.log("Searching: Medfusion Patient ID:" + patientID
							+ ", and Actual Medfusion Patient ID is:" + nPatientId.getTextContent().toString());
				}
				found = true;
				break;
			}
		}
		assertTrue(found, "Patient was not found in the response XML");
	}

	public static boolean verifyRequestStartDateAndEndDate(String xmlFileName, String StartDate, String EndDate)
			throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);
		NodeList Startdatenodes = doc.getElementsByTagName(IntegrationConstants.STARTDATE);
		NodeList Enddatenodes = doc.getElementsByTagName(IntegrationConstants.ENDDATE);
		{
			Log4jUtil.log("Start Date for the Requested Health Data: " + StartDate);
			Log4jUtil.log("Start Date returned in response: "
					+ Startdatenodes.item(Startdatenodes.getLength() - 1).getTextContent().substring(0, 10));
			assertTrue(Startdatenodes.item(Startdatenodes.getLength() - 1).getTextContent().substring(0, 10)
					.equals(StartDate), "Start date should matched");
		}
		{
			Log4jUtil.log("End Date for the Requested Health Data: " + EndDate);
			Log4jUtil.log("End Date returned in response: "
					+ Enddatenodes.item(Enddatenodes.getLength() - 1).getTextContent().substring(0, 10));
			assertTrue(
					Enddatenodes.item(Enddatenodes.getLength() - 1).getTextContent().substring(0, 10).equals(EndDate),
					"End date should matched");
		}
		return true;
	}

	public static void verifyOnDemandRequestSubmitted(String xmlFileName, String patientId)
			throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Boolean found = false;
		Document doc = buildDOMXML(xmlFileName);
		NodeList nodes = doc.getElementsByTagName(IntegrationConstants.PATIENTID);
		for (int i = 0; i < nodes.getLength(); i++) {
			if (nodes.item(i).getTextContent().equals(patientId)) {
				Log4jUtil.log(nodes.item(i).getTextContent() + " Expectd Patient Id equals to Actual PatientId  : "
						+ patientId);
				found = true;
				break;
			}
		}
		assertTrue(found, "CCDA Request was not found in the response XML");
	}

	public static String isReplyPresentReturnMessageThreadID(String responsePath, String messageIdentifier,
			String expectedBody) throws ParserConfigurationException, SAXException, IOException {
		Document doc = buildDOMXML(responsePath);

		Log4jUtil.log("finding sent message");
		boolean found = false;
		String MessageID = null;
		NodeList nodes = doc.getElementsByTagName(IntegrationConstants.QUESTION_SUBJECT);
		Node node = null;
		for (int i = 0; i < nodes.getLength(); i++) {
			node = nodes.item(i);
			Log4jUtil.log("Searching: " + node.getChildNodes().item(0).getTextContent() + ", to be found: "
					+ (messageIdentifier.toString()));
			if (node.getChildNodes().item(0).getTextContent().contains(messageIdentifier.toString())) {
				Element question = (Element) node.getParentNode();
				Node message = question.getElementsByTagName(IntegrationConstants.QUESTION_MESSAGE).item(0);
				assertEquals(message.getChildNodes().item(0).getTextContent(), expectedBody,
						"Received reply is not the same as sent");
				Node messageId = question.getElementsByTagName("MessageThreadId").item(0);
				MessageID = messageId.getChildNodes().item(0).getTextContent();
				found = true;
				break;
			}
		}
		assertTrue(found, "Reply was not found in response XML");
		return MessageID;
	}

	public static String isAppointmentReasonResponseXMLValid(String xmlFileName, String reason)
			throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);
		String appointmentID = "";

		Log4jUtil.log("finding reason message");
		boolean found = false;
		NodeList nodes = doc.getElementsByTagName(IntegrationConstants.REASON);
		Node node = null;
		for (int i = 0; i < nodes.getLength(); i++) {
			node = nodes.item(i);
			Log4jUtil.log("Searching: " + node.getChildNodes().item(0).getTextContent() + ", to be found: " + (reason));
			if (node.getChildNodes().item(0).getTextContent().contains(reason)) {
				found = true;
				Log4jUtil.log("Reason is found.");
				Element appointment = (Element) node.getParentNode().getParentNode();
				appointmentID = appointment.getAttribute(IntegrationConstants.APPOINTMENT_ID);
				Log4jUtil.log("Appointment ID is " + appointmentID);
				break;
			}
		}
		assertTrue(found, "Reason was not found in response XML");
		Log4jUtil.log("response is ok");
		return appointmentID;
	}

	public static String isPrescriptionRenewalRequestPresent(String xmlFileName, String reason)
			throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);
		String PrescriptionID = "";

		Log4jUtil.log("finding Prescription reason");
		boolean found = false;
		NodeList nodes = doc.getElementsByTagName(IntegrationConstants.ADDITIONAL_INFO_TAG);
		Node node = null;
		for (int i = 0; i < nodes.getLength(); i++) {
			node = nodes.item(i);
			Log4jUtil.log("Searching: " + node.getChildNodes().item(0).getTextContent() + ", to be found: " + (reason));
			if (node.getChildNodes().item(0).getTextContent().contains(reason)) {
				found = true;
				Log4jUtil.log("Reason is found.");
				Element Prescription = (Element) node.getParentNode().getParentNode().getParentNode();
				PrescriptionID = Prescription.getAttribute(IntegrationConstants.ID);
				Log4jUtil.log("Prescription ID is " + PrescriptionID);
				break;
			}
		}
		assertTrue(found, "Prescription Reason was not found in response XML");
		Log4jUtil.log("response is ok");
		return PrescriptionID;
	}

	public static String isReplyPresentReturnMessageID(String responsePath, String messageIdentifier,
			String expectedBody) throws ParserConfigurationException, SAXException, IOException {
		Document doc = buildDOMXML(responsePath);

		Log4jUtil.log("finding sent message");
		boolean found = false;
		String MessageID = null;
		NodeList nodes = doc.getElementsByTagName(IntegrationConstants.QUESTION_SUBJECT);
		Node node = null;
		for (int i = 0; i < nodes.getLength(); i++) {
			node = nodes.item(i);
			Log4jUtil.log("Searching: " + node.getChildNodes().item(0).getTextContent() + ", to be found: "
					+ (messageIdentifier.toString()));
			if (node.getChildNodes().item(0).getTextContent().contains(messageIdentifier.toString())) {
				Element question = (Element) node.getParentNode();
				Node message = question.getElementsByTagName(IntegrationConstants.QUESTION_MESSAGE).item(0);
				assertEquals(message.getChildNodes().item(0).getTextContent(), expectedBody,
						"Received reply is not the same as sent");
				MessageID = question.getAttribute(IntegrationConstants.MESSAGE_ID);
				found = true;
				break;
			}
		}
		assertTrue(found, "Reply was not found in response XML");
		return MessageID;
	}

	public static void isLoginEventValidated(String xmlFileName, String ResourceType_tag, long timestamp)
			throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);

		Log4jUtil.log("finding Event Login Name");
		NodeList nodes = doc.getElementsByTagName(IntegrationConstants.RESOURCETYPE);
		NodeList nodes1 = doc.getElementsByTagName(IntegrationConstants.EVENTRECORDEDTIMESTAMP);
		NodeList nodes2 = doc.getElementsByTagName(IntegrationConstants.PRACTICEPATIENTID_login);

		Node node = null;
		for (int i = 0; i < nodes.getLength(); i++) {
			node = nodes1.item(i);
			Log4jUtil.log(
					"Searching: " + node.getChildNodes().item(0).getTextContent() + ", to be found: " + (timestamp));
			long EventRecordedTimestamp = Long.parseLong(node.getChildNodes().item(0).getTextContent());
			Log4jUtil.log("TimestampValue" + " " + timestamp);
			Log4jUtil.log("Finding EventRecordedTimestamp" + " " + EventRecordedTimestamp);

			if (EventRecordedTimestamp > timestamp) {
				Log4jUtil.log("EVENTRECORDEDTIMESTAMP value is greater than since time");
				break;
			}
		}

		for (int i = 0; i < nodes.getLength(); i++) {
			node = nodes.item(i);
			Log4jUtil.log("Searching: " + node.getChildNodes().item(0).getTextContent() + ", to be found: "
					+ (ResourceType_tag.toString()));
			if (node.getChildNodes().item(0).getTextContent().contains(ResourceType_tag.toString())) {
				Log4jUtil.log("Resource type is found.");
				break;
			}
		}

		for (int k = 0; k < nodes2.getLength(); k++) {
			node = nodes2.item(k);
			String NodeName = node.getNodeName();
			Log4jUtil.log("Searching" + " " + NodeName);
			if (NodeName.contains("PracticePatientId")) {
				Log4jUtil.log("PracticePatientId is found.");
				break;
			}

		}

	}

	public static String getPrescriptionID(String xmlFileName)
			throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);
		Node prescrNode = doc.getElementsByTagName("Prescription").item(0);
		Element prescrElem = (Element) prescrNode;
		String PrescriptionId = prescrElem.getAttribute("id").toString();
		Log4jUtil.log("Prescription Id : " + PrescriptionId);
		return PrescriptionId;
	}

	public static String getPrescriptionHeaderID(String xmlFileName)
			throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);
		Node preHdNode = doc.getElementsByTagName("PrescriptionRequestIds").item(0);
		String PrescriptionHeaderId = preHdNode.getChildNodes().item(0).getTextContent();
		Log4jUtil.log("Searching Prescription Header ID: " + PrescriptionHeaderId);
		return PrescriptionHeaderId;
	}
	
	public static void isMedicationDetailsNewResponseXMLValid(String xmlFileName, String medicationName, String additionalComment)
			throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);

		Log4jUtil.log("finding Medication Name");
		boolean found = false;
		NodeList nodes = doc.getElementsByTagName(JalapenoConstants.MEDICATION_NAME_TAG);
		Node node = null;
		for (int i = 0; i < nodes.getLength(); i++) {
			node = nodes.item(i);
			Log4jUtil.log("Searching: " + node.getChildNodes().item(0).getTextContent() + ", to be found: "
					+ (medicationName));
			if (node.getChildNodes().item(0).getTextContent().contains(medicationName)) {
				Element ele = (Element) nodes.item(i).getParentNode();
				Node nDosage = ele.getElementsByTagName(IntegrationConstants.DOSAGE_TAG).item(0);
				Node additionalCommentNode =ele.getElementsByTagName(IntegrationConstants.ADDITIONAL_INFO_TAG).item(0);
				assertEquals(nDosage.getTextContent(), JalapenoConstants.DOSAGE,
						"The actual value of dosage doesnt equal the expected value");
				assertEquals(additionalCommentNode.getTextContent(),additionalComment,"The actual value of Additional commnet doesnt equal the expected value");
				found = true;
				break;
			}
		}
		assertTrue(found, "Medication Name was not found in response XML");
		Log4jUtil.log("response is ok");
	}
	
	public static String findValueOfMedicationNodeNew(String xmlFileName, String parentNode, String medication,
			String rxSMSubject, String rxSMBody, String prescriptionPath) throws ParserConfigurationException,
			SAXException, IOException, DOMException, ParseException, TransformerException {
		IHGUtil.PrintMethodName();
		String getPrescription_id = null;
		String updatedXML = null;
		File xmlResponeFile = new File(xmlFileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlResponeFile);
		doc.getDocumentElement().normalize();
		ArrayList<String> medication_details = new ArrayList<String>();

		NodeList pnode = doc.getElementsByTagName(parentNode);

		for (int i = 0; i < pnode.getLength(); i++) {
			Element element = (Element) pnode.item(i);
			String medicationId = element.getElementsByTagName("ExternalMedicationId").item(0).getTextContent();
			
			String reaString = element.getElementsByTagName("MedicationName").item(0).getFirstChild().getNodeValue();
			if (reaString.equalsIgnoreCase(medication)) {
				Node node = element.getElementsByTagName("MedicationName").item(0).getParentNode();
				medication_details.add(element.getElementsByTagName("MedicationName").item(0).getFirstChild()
						.getNodeValue().toString());
				medication_details.add(element.getElementsByTagName("MedicationDosage").item(0).getFirstChild()
						.getNodeValue().toString());
				
				element = (Element) element.getParentNode();
				medication_details.add(element.getElementsByTagName("RequestedLocation").item(0).getFirstChild()
						.getNodeValue().toString());
				medication_details.add(element.getElementsByTagName("RequestedProvider").item(0).getFirstChild()
						.getNodeValue().toString());
				medication_details
						.add(element.getElementsByTagName("To").item(0).getFirstChild().getNodeValue().toString());
				medication_details
						.add(element.getElementsByTagName("From").item(0).getFirstChild().getNodeValue().toString());
				medication_details.add(element.getElementsByTagName("AdditionalInformation").item(0).getTextContent().toString());
				medication_details.add(element.getElementsByTagName("ExternalMedicationId").item(0).getTextContent().toString());
				medication_details.add(element.getElementsByTagName("ExternalSystemId").item(0).getTextContent().toString());
				node = node.getParentNode().getParentNode();
				
				Log4jUtil.log("Node name for prescription:"+node.getNodeName());
				if (node.hasAttributes()) {
					Attr attr = (Attr) node.getAttributes().getNamedItem("id");
					getPrescription_id = attr.getValue();
				}
				
				element = (Element) element.getParentNode();
				String getCreatedDateTime = element.getElementsByTagName("CreatedDateTime").item(0).getFirstChild()
						.getNodeValue();
				String getUpdatedDateTime = element.getElementsByTagName("UpdatedDateTime").item(0).getFirstChild()
						.getNodeValue();
				Log4jUtil.log(medication_details.toString());
				updatedXML = postMedicationRequestNew(prescriptionPath, getPrescription_id, medication_details,
						getCreatedDateTime, getUpdatedDateTime, rxSMSubject, rxSMBody);
				break;
			}

		}
		return updatedXML;
	}
	
	private static String postMedicationRequestNew(String prescriptionPath, String getPrescription_id,
			ArrayList<String> medication_details, String getCreatedDateTime, String getUpdatedDateTime,
			String rxSMSubject, String rxSMBody) throws DOMException, ParseException, TransformerException,
			ParserConfigurationException, SAXException, IOException {

		Document doc = buildDOMXML(prescriptionPath);

		Node node = doc.getElementsByTagName(IntegrationConstants.PRESCRIPTION).item(0);
		Element element = (Element) node;
		// update appointment id,createdDateTime,updatedDateTime,message_thread_id
		element.setAttribute(IntegrationConstants.ID, getPrescription_id);
		Node ncreatedDateTime = element.getElementsByTagName(IntegrationConstants.CREATED_TIME).item(0);
		Node nupdatedDateTime = element.getElementsByTagName(IntegrationConstants.UPDATE_TIME).item(0);
		ncreatedDateTime.setTextContent(getCreatedDateTime);
		nupdatedDateTime.setTextContent(getUpdatedDateTime);

		// update From,To
		node = doc.getElementsByTagName(IntegrationConstants.PRESCRIPTION_RENEWAL_REQUEST).item(0);
		Node nFrom = element.getElementsByTagName(IntegrationConstants.FROM).item(0);
		Node nTo = element.getElementsByTagName(IntegrationConstants.TO).item(0);
		Node nRequestedProvider = element.getElementsByTagName(IntegrationConstants.REQUESTED_PROVIDER).item(0);
		Node nRequestedLocation = element.getElementsByTagName(IntegrationConstants.REQUESTED_LOCATION).item(0);

		nFrom.setTextContent(medication_details.get(5));
		nTo.setTextContent(medication_details.get(4));
		nRequestedProvider.setTextContent(medication_details.get(3));
		nRequestedLocation.setTextContent(medication_details.get(2));

		// update medication details
		node = doc.getElementsByTagName(IntegrationConstants.MEDICATION).item(0);
		Node nMedicationName = element.getElementsByTagName(IntegrationConstants.MEDICATION_NAME_TAG).item(0);
		Node nMedicationDosage = element.getElementsByTagName(IntegrationConstants.DOSAGE_TAG).item(0);
		Node nQuantity = element.getElementsByTagName(IntegrationConstants.QUANTITY_TAG).item(0);
		Node nRefillNumber = element.getElementsByTagName(IntegrationConstants.REFILL_NUMBER_TAG).item(0);
		Node nPrescriptionNumber = element.getElementsByTagName(IntegrationConstants.PRESCRIPTION_NUMBER_TAG).item(0);
		Node nAdditionalInformation = element.getElementsByTagName(IntegrationConstants.ADDITIONAL_INFO_TAG).item(0);
		Node nExternalMedicationID = element.getElementsByTagName(IntegrationConstants.EXTERNAL_MEDICATION_ID).item(0);
		Node nExternalSystemID = element.getElementsByTagName(IntegrationConstants.EXTERNAL_SYSTEM_ID).item(0);

		
		nPrescriptionNumber.setTextContent((String) IntegrationConstants.PRESCRIPTION_NO);
		nRefillNumber.setTextContent((String) IntegrationConstants.NO_OF_REFILLS);
		nQuantity.setTextContent((String) IntegrationConstants.QUANTITY);
		nMedicationDosage.setTextContent(medication_details.get(1));
		nMedicationName.setTextContent(medication_details.get(0));
		nAdditionalInformation.setTextContent(medication_details.get(6));
		nExternalMedicationID.setTextContent(medication_details.get(7));
		nExternalSystemID.setTextContent(medication_details.get(8));

		String SigCode = "IWVH";
		Node nSigCodeAbbreviation = element.getElementsByTagName("SigCodeAbbreviation").item(0);
		Node nSigCodeMeaning = element.getElementsByTagName("SigCodeMeaning").item(0);

		nSigCodeAbbreviation.setTextContent(SigCode);
		nSigCodeMeaning.setTextContent(SigCode.concat(" Daily"));

		SigCodeAbbreviation = nSigCodeAbbreviation.getTextContent().toString();
		SigCodeMeaning = nSigCodeMeaning.getTextContent().toString();
		// update messageId,Subject,message_thread_id
		Node cNode = doc.getElementsByTagName(IntegrationConstants.COMMUNICATION).item(0);
		Element ele = (Element) cNode;
		ele.setAttribute(IntegrationConstants.MESSAGE_ID, "11623b2e-6824-4c0e-9ed2-0ceb6f6a" + fourDigitRandom());
		Node ncFrom = ele.getElementsByTagName(IntegrationConstants.FROM).item(0);
		Node ncTo = ele.getElementsByTagName(IntegrationConstants.TO).item(0);
		ncFrom.setTextContent(medication_details.get(4));
		ncTo.setTextContent(medication_details.get(5));
		Node nSubject = element.getElementsByTagName(IntegrationConstants.SUBJECT).item(0);
		nSubject.setTextContent(rxSMSubject);
		Node ncmessaageThreadID = ele.getElementsByTagName(IntegrationConstants.MESSAGE_THREAD_ID).item(0);
		ncmessaageThreadID.setTextContent(getPrescription_id);
		Node nmessage = element.getElementsByTagName(IntegrationConstants.QUESTION_MESSAGE).item(0);
		nmessage.setTextContent(rxSMBody);

		// update sent time
		Node nSent = element.getElementsByTagName(IntegrationConstants.SENT_DATE).item(0);
		nSent.setTextContent(SentDate(getCreatedDateTime));

		return domToString(doc);

	}
	
	public static String getAttachmentRefId(String responseFilePath)
			throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(responseFilePath);
		Node InternalAttachmentID = doc.getElementsByTagName("InternalAttachmentID").item(0);
		String attchmentRefId = InternalAttachmentID.getTextContent().toString();
		return attchmentRefId;
	}
	
	public static boolean isResponseContainsErrorNode(String xmlFileName)
			throws ParserConfigurationException, SAXException, IOException {
		Document doc = buildDOMXML(xmlFileName);

		NodeList nodes = doc.getElementsByTagName(IntegrationConstants.PROCESSING_STATE);
		NodeList errorNode = doc.getElementsByTagName("Error");

		for (int i = 0; i < nodes.getLength()-1; i++) {
			if (!nodes.item(i).getTextContent().equals(IntegrationConstants.STATE_COMPLETED)) {
				Log4jUtil.log("Error while processing response: " + errorNode.item(0).getTextContent());
			}
			assertTrue(nodes.item(i).getTextContent().equals(IntegrationConstants.STATE_COMPLETED),
					"Processing Status is failed for No '" + i + "' message");
		}
		
		assertTrue(nodes.item(nodes.getLength()-1).getTextContent().equals(IntegrationConstants.STATE_ERRORED),"Response Contains Node with Error State for invalid data");
		Log4jUtil.log("Error Node Contains : "+doc.getElementsByTagName("Description").item(0).getTextContent() +" with severity "+doc.getElementsByTagName("Severity").item(0).getTextContent());
		return true;
	}

	public static String isResponseContainsValidAttachmentURL(String xmlFileName)
			throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);
		Log4jUtil.log("finding AttachmentURL");
		NodeList nodes = doc.getElementsByTagName(IntegrationConstants.ATTACHMENT_URL);
		String attchmentURL = nodes.item(0).getTextContent();
		Log4jUtil.log("response is ok");
		Log4jUtil.log("Attachement URL :   "+attchmentURL);
		return attchmentURL;
	}
	
	public static void validateAttachementName(String xmlFileName,String fileName)
			throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);
		Log4jUtil.log("finding AttachmentURL");
		NodeList nodes = doc.getElementsByTagName(IntegrationConstants.FILE_NAME);
		String attchmentName = nodes.item(0).getTextContent().replaceAll("\\+", " ");
		Log4jUtil.log("Filename in respons: "+attchmentName);
		assertTrue(attchmentName.equals(fileName), "Attachment File Names are Equal");
	}
	
	public static void isPatientPresent(String responsePath, String TestPatientIDUserName)
			throws ParserConfigurationException, SAXException, IOException {
		Document doc = buildDOMXML(responsePath);

		Log4jUtil.log("Step 5: finding posted Patient");
		boolean found = false;
		NodeList nodes = doc.getElementsByTagName(IntegrationConstants.PRACTICEPATIENTID);
		Node node = null;
		for (int i = 0; i < nodes.getLength(); i++) {

			node = nodes.item(i);
			Log4jUtil.log("Searching: " + node.getChildNodes().item(0).getTextContent() + ", to be found: "
					+ (TestPatientIDUserName.toString()));
			if (node.getChildNodes().item(0).getTextContent().contains(TestPatientIDUserName.toString())) {
				found = true;
				break;
			}
		}
		assertTrue(found, "Patient was found in GET call with 200 Response");
	}

		
}
