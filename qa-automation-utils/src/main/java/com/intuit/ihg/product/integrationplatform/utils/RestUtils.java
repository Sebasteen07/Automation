package com.intuit.ihg.product.integrationplatform.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.intuit.api.security.client.IOAuthTwoLeggedClient;
import com.intuit.api.security.client.OAuth20TokenManager;
import com.intuit.api.security.client.OAuth2Client;
import com.intuit.api.security.client.properties.OAuthPropertyManager;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.portal.utils.PortalConstants;
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
	 * Reads the XML and checks Medication Details_ 
	 * @param xmlFileName XML to check
	 * @param Long timestamp of a sent Medication Name to check
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public static void isMedicationDetailsResponseXMLValid(String xmlFileName, String medicationName) throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);

		Log4jUtil.log("finding Medication Name");
		boolean found = false;
		NodeList nodes = doc.getElementsByTagName(PortalConstants.Medication_Name_Tag);
		Node node = null;
		for (int i = 0; i < nodes.getLength(); i++) 
		{
			node = nodes.item(i);
			Log4jUtil.log("Searching: " + node.getChildNodes().item(0).getTextContent() + ", to be found: " + (medicationName));
			if (node.getChildNodes().item(0).getTextContent().contains(medicationName))
			{
				Element ele = (Element) nodes.item(i).getParentNode();
				Node nDosage=ele.getElementsByTagName(PortalConstants.Medication_Dosage).item(0);
				Node nQuantity=ele.getElementsByTagName(PortalConstants.Quantity_Tag).item(0);
				Node nRefillNumber=ele.getElementsByTagName(PortalConstants.Refill_Number_Tag).item(0);
				Node nPrescriptionNumber=ele.getElementsByTagName(PortalConstants.Prescription_Number_Tag).item(0);
				Node nAdditionalInformation=ele.getElementsByTagName(PortalConstants.Additional_Information_Tag).item(0);
				Assert.assertEquals(nDosage.getTextContent(), PortalConstants.Dosage.toString(), "The actual value of dosage doesnt equal the expected value");
				Assert.assertEquals(nQuantity.getTextContent(), PortalConstants.Quantity.toString(), "The actual value of quantity doesnt equal the expected value");
				Assert.assertEquals(nRefillNumber.getTextContent(), PortalConstants.No_Of_Refills.toString(), "The actual value of refill no. doesnt equal the expected value");
				Assert.assertEquals(nPrescriptionNumber.getTextContent(), PortalConstants.Prescription_No.toString(), "The actual value of prescription no. doesnt equal the expected value");
				Assert.assertEquals(nAdditionalInformation.getTextContent(), PortalConstants.Additional_Info.toString(), "The actual additional info doesnt equal the expected value");		
				found = true;
				break;
			}
		}
		Assert.assertTrue(found, "Medication Name was not found in response XML");
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
	public static String findValueOfChildNode(String xmlFileName,
			String parentNode,String reason,String subject,String reply)
			throws ParserConfigurationException, SAXException, IOException, TransformerException, DOMException, ParseException {

		IHGUtil.PrintMethodName();
		String getApt_req_id= null;
		String updatedXML=null;
		File xmlResponeFile = new File(xmlFileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlResponeFile);
		doc.getDocumentElement().normalize();

		NodeList pnode = doc.getElementsByTagName(parentNode);

		for (int i = 0; i < pnode.getLength(); i++) {
			Element element = (Element) pnode.item(i);	
			String reaString=element.getElementsByTagName("Reason").item(0).getFirstChild().getNodeValue();
			if (reaString.equalsIgnoreCase(reason))
				{		
				Node node=element.getElementsByTagName("Reason").item(0).getParentNode();
					node=node.getParentNode();
				if (node.hasAttributes()) {
					Attr attr=(Attr) node.getAttributes().getNamedItem("id");
					getApt_req_id=attr.getValue();
				}
				String getFrom=element.getElementsByTagName("From").item(0).getFirstChild().getNodeValue();
				String getTo=element.getElementsByTagName("To").item(0).getFirstChild().getNodeValue();
				element=(Element)element.getParentNode();
				String getCreatedDateTime=element.getElementsByTagName("CreatedDateTime").item(0).getFirstChild().getNodeValue();
				String getUpdatedDateTime=element.getElementsByTagName("UpdatedDateTime").item(0).getFirstChild().getNodeValue();
				xmlFileName=IntegrationConstants.APPOINTMENT_RESPONSE_PATH+"PostAppointment.xml";
				updatedXML=postAppointmentRequest(xmlFileName,getApt_req_id, getFrom,getTo,getCreatedDateTime,getUpdatedDateTime,subject,reply);
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
	public  static String postAppointmentRequest(String xmlFileName,
			String app_req_id, String From, String To, String createdDateTime, String updatedDateTime,String subject,String reply)
			throws ParserConfigurationException, SAXException, IOException, TransformerException, DOMException, ParseException {
		Document doc=buildDOMXML(xmlFileName);
		
		Node node=doc.getElementsByTagName(IntegrationConstants.APPOINTMENT).item(0);
		Element element=(Element) node;
		//update appointment id,createdDateTime,updatedDateTime,message_thread_id
		element.setAttribute(IntegrationConstants.APPOINTMENT_ID, app_req_id);
		Node ncreatedDateTime=element.getElementsByTagName(IntegrationConstants.CREATED_TIME).item(0);
		Node nupdatedDateTime=element.getElementsByTagName(IntegrationConstants.UPDATE_TIME).item(0);
		Node nmessaageThreadID=element.getElementsByTagName(IntegrationConstants.MESSAGE_THREAD_ID).item(0);
		ncreatedDateTime.setTextContent(createdDateTime);
		nupdatedDateTime.setTextContent(updatedDateTime);
		nmessaageThreadID.setTextContent(app_req_id);
		
		//update From,To
		node=doc.getElementsByTagName(IntegrationConstants.APPOINTMENT_REQUEST).item(0);
		Node nFrom=element.getElementsByTagName(IntegrationConstants.FROM).item(0);
		Node nTo=element.getElementsByTagName(IntegrationConstants.TO).item(0);
		nFrom.setTextContent(From);
		nTo.setTextContent(To);
		
		//update messageId,Subject,message_thread_id
		Node cNode=doc.getElementsByTagName(IntegrationConstants.COMMUNICATION).item(0);
		Element ele=(Element) cNode;
		ele.setAttribute(IntegrationConstants.MESSAGE_ID,"5a5346dc-4671-4355-9391-363fde62"+fourDigitRandom());
		Node ncFrom=ele.getElementsByTagName(IntegrationConstants.FROM).item(0);
		Node ncTo=ele.getElementsByTagName(IntegrationConstants.TO).item(0);
		ncFrom.setTextContent(To);
		ncTo.setTextContent(From);
		Node nSubject=element.getElementsByTagName(IntegrationConstants.SUBJECT).item(0);
		nSubject.setTextContent(subject);
		Node ncmessaageThreadID=ele.getElementsByTagName(IntegrationConstants.MESSAGE_THREAD_ID).item(0);
		ncmessaageThreadID.setTextContent(app_req_id);
		Node nmessage=element.getElementsByTagName(IntegrationConstants.QUESTION_MESSAGE).item(0);
		nmessage.setTextContent(reply);
		
		//update sent time,ScheduledDateTime
		Node nSent=element.getElementsByTagName(IntegrationConstants.SENT_DATE).item(0);
		nSent.setTextContent(SentDate(createdDateTime));
		Node nScheduledDate=element.getElementsByTagName(IntegrationConstants.SCHEDULED_DATE).item(0);
		nScheduledDate.setTextContent(ScheduledDate(createdDateTime));
		
		return domToString(doc);
	}
	
	/**
	 * 
	 * @param createdDateTime
	 * @return
	 * @throws ParseException 
	 */
	private static String ScheduledDate(String createdDateTime) throws ParseException {
		String scheduleDate=null;
		SimpleDateFormat formatter, FORMATTER;
		formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Date date = formatter.parse(createdDateTime);
		FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		scheduleDate=FORMATTER.format(date.getTime() + (3 *(1000 * 60 * 60 * 24)));
		scheduleDate=new StringBuffer(scheduleDate).insert(22, ":").toString();
		return scheduleDate;
	}

	/**
	 * 
	 * @param createdDateTime
	 * @return
	 * @throws ParseException
	 */
	private static String SentDate(String createdDateTime) throws ParseException {
		String sentDate=null;
		SimpleDateFormat formatter, FORMATTER;
		formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Date date = formatter.parse(createdDateTime);
		FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		long hour = 3600 * 2000;
		sentDate=FORMATTER.format(date.getTime() + hour);
		sentDate=new StringBuffer(sentDate).insert(22, ":").toString();
		return sentDate;
	}

	/**
	 * 
	 * @param xmlFilePath
	 * @return
	 */
	public static String fileToString(String xmlFilePath) {
		String xmlInString = convertXMLFileToString(xmlFilePath);
		return xmlInString;
	}
	
	/**
	 * 
	 * @param xmlFilePath
	 * @return
	 */
	public static String convertXMLFileToString(String fileName) {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
					.newInstance();
			InputStream inputStream = new FileInputStream(new File(fileName));
			org.w3c.dom.Document doc = documentBuilderFactory
					.newDocumentBuilder().parse(inputStream);
			StringWriter stw = new StringWriter();
			Transformer serializer = TransformerFactory.newInstance()
					.newTransformer();
			serializer.transform(new DOMSource(doc), new StreamResult(stw));
			return stw.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}