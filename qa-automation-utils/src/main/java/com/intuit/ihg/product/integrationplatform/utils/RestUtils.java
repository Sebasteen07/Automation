	package com.intuit.ihg.product.integrationplatform.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

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
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
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
import com.intuit.ifs.csscat.core.BaseTestSoftAssert;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.mail.GmailBot;

import com.intuit.ihg.product.portal.utils.PortalConstants;


public class RestUtils {

	static Random random = new Random();
	public static String gnMessageID;
	public static String SigCodeAbbreviation;
	public static String SigCodeMeaning;
	public static String gnMessageThreadID;
	public static String paymentID;
	public static int responseCode;
	public static List<String> patientDatails=new ArrayList<String>(); 
	/**
	 * Performs OAuth Get Request and saves the resposse
	 * @param strUrl server Get url
	 * @param responseFilePath path to save the response
	 * @return 
	 * @throws IOException 
	 */
	public static String setupHttpGetRequest(String strUrl, String responseFilePath)
			throws IOException {
		IHGUtil.PrintMethodName();

		IOAuthTwoLeggedClient oauthClient = new OAuth2Client();
        Log4jUtil.log("Get Request Url: "+ strUrl);
        HttpGet httpGetReq = new HttpGet(strUrl);
        httpGetReq.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000)
        .setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
        //httpGetReq.addHeader("ExternalSystemId", "82");
        HttpResponse resp = oauthClient.httpGetRequest(httpGetReq);
        //Log4jUtil.log("Response" +resp);
        HttpEntity entity = resp.getEntity();
        String sResp=null;
        if(entity!=null){
        sResp = EntityUtils.toString(entity);
        Log4jUtil.log("Check for http 200 response");
		Assert.assertTrue(resp.getStatusLine().getStatusCode() == 200,
				"Get Request response is " + resp.getStatusLine().getStatusCode() + " instead of 200. Response message received:\n" + sResp);

		writeFile(responseFilePath, sResp);
        
		if(resp.containsHeader(IntegrationConstants.TIMESTAMP_HEADER)){
			Header[] h=resp.getHeaders(IntegrationConstants.TIMESTAMP_HEADER);
			return h[0].getValue();
			
			}
        }
        else
        {
        	Log4jUtil.log("204 response found");
        	
        }
        return null;
		
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
				Element ele = (Element) nodes.item(i).getParentNode();
				Node messageThreadID=ele.getElementsByTagName(IntegrationConstants.MESSAGE_THREAD_ID).item(0);
				gnMessageThreadID=messageThreadID.getTextContent();
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
	
	
	//retrive message Thread ID
		public static String messageThreadID(){
			return gnMessageThreadID;
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
	public static String prepareSecureMessage(String xmlFileName, String from, String to, String subject, String messageID) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);
		
		//get message root element
		Node node = doc.getElementsByTagName(IntegrationConstants.SECURE_MESSAGE).item(0);
		Element elem = (Element) node;
		
		//set random message id
		elem.setAttribute(IntegrationConstants.MESSAGE_ID, elem.getAttribute(IntegrationConstants.MESSAGE_ID) + fourDigitRandom());
		
		gnMessageID=elem.getAttribute(IntegrationConstants.MESSAGE_ID).toString();
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
	
	//retrive new message ID
	public static String newMessageID(){
		return gnMessageID;
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
    	
    	IOAuthTwoLeggedClient oauthClient = new OAuth2Client();
        Log4jUtil.log("Post Request Url: "+ strUrl);
        HttpPost httpPostReq = new HttpPost(strUrl);
        httpPostReq.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000)
        .setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
        
        StringEntity se = new StringEntity(payload);
        httpPostReq.setEntity(se);
        httpPostReq.addHeader("Accept", "application/xml");
        httpPostReq.addHeader("Content-Type", "application/xml");
        httpPostReq.addHeader("Noun", "Encounter");
        httpPostReq.addHeader("Verb", "Completed");
        //httpPostReq.addHeader("ExternalSystemId", "79");
        //GW CCD
       //httpPostReq.addHeader("ExternalSystemId", "82"); 
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
		
		if(resp.containsHeader(IntegrationConstants.LOCATION_HEADER)){
			Header[] h=resp.getHeaders(IntegrationConstants.LOCATION_HEADER);
			return h[0].getValue();
			}
			return null;
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
		for(int i=0;i < nodes.getLength() ;i++)
		{
			Assert.assertTrue(nodes.item(i).getTextContent().equals(IntegrationConstants.STATE_COMPLETED),
					"Processing Status is failed for No '"+ i +"' message");
		}
		return true;
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
			if(i== nodes.getLength() - 1)
			{
				Assert.fail("Patient was not Found");
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


	public static void isPatientRegistered(String xmlFileName, String practicePatientId, String firstName, String lastName, String patientID) throws ParserConfigurationException, SAXException, IOException {
		Document doc = buildDOMXML(xmlFileName);
		NodeList patients = doc.getElementsByTagName(IntegrationConstants.PRACTICE_PATIENT_ID);
		boolean found = false;
		for(int i = 0; i < patients.getLength(); i++){
			if(patients.item(i).getTextContent().equals(practicePatientId)){
				Log4jUtil.log("Searching: External Patient ID:" + practicePatientId + ", and Actual External Patient ID is:" + patients.item(i).getTextContent().toString());
				Element patient = (Element) patients.item(i).getParentNode().getParentNode();
				Node status = patient.getElementsByTagName(IntegrationConstants.STATUS).item(0);
				Assert.assertEquals(status.getTextContent(), IntegrationConstants.REGISTERED, "Patient has different status than expected. Status is: " + status.getTextContent());
				Node nfirstName = patient.getElementsByTagName(IntegrationConstants.FIRST_NAME).item(0);
				Log4jUtil.log("Searching: Patient FirstName:" + firstName + ", and Actual Patient FirstName is:" + nfirstName.getTextContent().toString());
				Assert.assertEquals(nfirstName.getTextContent(), firstName, "Patient has different FirstName than expected. FirstName is: " + nfirstName.getTextContent());
				Node nlastName = patient.getElementsByTagName(IntegrationConstants.LAST_NAME).item(0);
				Log4jUtil.log("Searching: Patient LastName:" + lastName + ", and Actual Patient LastName is:" + nlastName.getTextContent().toString());
				Assert.assertEquals(nlastName.getTextContent(), lastName, "Patient has different LastName than expected. LastName is: " + nlastName.getTextContent());
				if(patientID!=null)
				{
					Node nPatientId = patient.getElementsByTagName(IntegrationConstants.MEDFUSIONID).item(0);
					Assert.assertEquals(nPatientId.getTextContent(), patientID, "Patient has different MedfusionPatientId than expected. MedfusionPatientId is: " + nPatientId.getTextContent());
					Log4jUtil.log("Searching: Medfusion Patient ID:" + patientID + ", and Actual Medfusion Patient ID is:" + nPatientId.getTextContent().toString());
				}
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
			String parentNode,String reason,String subject,String reply, String appointment)
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
				updatedXML=postAppointmentRequest(appointment,getApt_req_id, getFrom,getTo,getCreatedDateTime,getUpdatedDateTime,subject,reply);
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
	 * @return scheduleDate
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
	 * @return sentDate
	 * @throws ParseException
	 */
	private static String SentDate(String createdDateTime) throws ParseException {
		String sentDate=null;
		SimpleDateFormat formatter,FORMATTER;
		formatter=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Date date = formatter.parse(createdDateTime);
		FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		sentDate=FORMATTER.format(date.getTime() + (5 *60000));
		sentDate=new StringBuffer(sentDate).insert(22, ":").toString();
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
	public static String findValueOfMedicationNode(String xmlFileName,String parentNode, String medication, String rxSMSubject, String rxSMBody,
			String prescriptionPath) throws ParserConfigurationException, SAXException, IOException, DOMException, ParseException, TransformerException {
		IHGUtil.PrintMethodName();
		String getPrescription_id= null;
		String updatedXML=null;
		File xmlResponeFile = new File(xmlFileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlResponeFile);
		doc.getDocumentElement().normalize();
		ArrayList<String> medication_details = new ArrayList<String>();

		NodeList pnode = doc.getElementsByTagName(parentNode);

		for (int i = 0; i < pnode.getLength(); i++) {
			Element element = (Element) pnode.item(i);	
			String reaString=element.getElementsByTagName("MedicationName").item(0).getFirstChild().getNodeValue();
			if (reaString.equalsIgnoreCase(medication))
				{		
				Node node=element.getElementsByTagName("MedicationName").item(0).getParentNode();
				medication_details.add(element.getElementsByTagName("MedicationName").item(0).getFirstChild().getNodeValue().toString());
				medication_details.add(element.getElementsByTagName("MedicationDosage").item(0).getFirstChild().getNodeValue().toString());
				medication_details.add(element.getElementsByTagName("Quantity").item(0).getFirstChild().getNodeValue().toString());
				medication_details.add(element.getElementsByTagName("RefillNumber").item(0).getFirstChild().getNodeValue().toString());
				medication_details.add(element.getElementsByTagName("PrescriptionNumber").item(0).getFirstChild().getNodeValue().toString());
				medication_details.add(element.getElementsByTagName("AdditionalInformation").item(0).getFirstChild().getNodeValue().toString());
				node=node.getParentNode();
				element=(Element)element.getParentNode();
				medication_details.add(element.getElementsByTagName("RequestedLocation").item(0).getFirstChild().getNodeValue().toString());
				medication_details.add(element.getElementsByTagName("RequestedProvider").item(0).getFirstChild().getNodeValue().toString());
				medication_details.add(element.getElementsByTagName("To").item(0).getFirstChild().getNodeValue().toString());
				medication_details.add(element.getElementsByTagName("From").item(0).getFirstChild().getNodeValue().toString());
				node=node.getParentNode();				   
				if (node.hasAttributes()) {
					Attr attr=(Attr) node.getAttributes().getNamedItem("id");
					getPrescription_id=attr.getValue();
				}
				element=(Element)element.getParentNode();
				String getCreatedDateTime=element.getElementsByTagName("CreatedDateTime").item(0).getFirstChild().getNodeValue();
				String getUpdatedDateTime=element.getElementsByTagName("UpdatedDateTime").item(0).getFirstChild().getNodeValue();
				updatedXML=postMedicationRequest(prescriptionPath,getPrescription_id, medication_details,getCreatedDateTime,getUpdatedDateTime,rxSMSubject,rxSMBody);
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
	private static String postMedicationRequest(String prescriptionPath,
			String getPrescription_id, ArrayList<String> medication_details,
			String getCreatedDateTime, String getUpdatedDateTime,
			String rxSMSubject, String rxSMBody) throws DOMException, ParseException, TransformerException, ParserConfigurationException, SAXException, IOException {
		
		
		Document doc=buildDOMXML(prescriptionPath);
		
		Node node=doc.getElementsByTagName(IntegrationConstants.PRESCRIPTION).item(0);
		Element element=(Element) node;
		//update appointment id,createdDateTime,updatedDateTime,message_thread_id
		element.setAttribute(IntegrationConstants.ID, getPrescription_id);
		Node ncreatedDateTime=element.getElementsByTagName(IntegrationConstants.CREATED_TIME).item(0);
		Node nupdatedDateTime=element.getElementsByTagName(IntegrationConstants.UPDATE_TIME).item(0);
		ncreatedDateTime.setTextContent(getCreatedDateTime);
		nupdatedDateTime.setTextContent(getUpdatedDateTime);
				
		//update From,To
		node=doc.getElementsByTagName(IntegrationConstants.PRESCRIPTION_RENEWAL_REQUEST).item(0);
		Node nFrom=element.getElementsByTagName(IntegrationConstants.FROM).item(0);
		Node nTo=element.getElementsByTagName(IntegrationConstants.TO).item(0);
		Node nRequestedProvider=element.getElementsByTagName(IntegrationConstants.REQUESTED_PROVIDER).item(0);
		Node nRequestedLocation=element.getElementsByTagName(IntegrationConstants.REQUESTED_LOCATION).item(0);
		
		nFrom.setTextContent(medication_details.get(9));
		nTo.setTextContent(medication_details.get(8));
		nRequestedProvider.setTextContent(medication_details.get(7));
		nRequestedLocation.setTextContent(medication_details.get(6));
		
		//update medication details
		node=doc.getElementsByTagName(IntegrationConstants.MEDICATION).item(0);
		Node nMedicationName=element.getElementsByTagName(IntegrationConstants.MEDICATION_NAME_TAG).item(0);
		Node nMedicationDosage=element.getElementsByTagName(IntegrationConstants.DOSAGE_TAG).item(0);
		Node nQuantity=element.getElementsByTagName(IntegrationConstants.QUANTITY_TAG).item(0);
		Node nRefillNumber=element.getElementsByTagName(IntegrationConstants.REFILL_NUMBER_TAG).item(0);
		Node nPrescriptionNumber=element.getElementsByTagName(IntegrationConstants.PRESCRIPTION_NUMBER_TAG).item(0);
		Node nAdditionalInformation=element.getElementsByTagName(IntegrationConstants.ADDITIONAL_INFO_TAG).item(0);
		
		nAdditionalInformation.setTextContent(medication_details.get(5));
		nPrescriptionNumber.setTextContent(medication_details.get(4));
		nRefillNumber.setTextContent(medication_details.get(3));
		nQuantity.setTextContent(medication_details.get(2));
		nMedicationDosage.setTextContent(medication_details.get(1));
		nMedicationName.setTextContent(medication_details.get(0));
		
		String SigCode=generateRandomString();
		Node nSigCodeAbbreviation=element.getElementsByTagName("SigCodeAbbreviation").item(0);
		Node nSigCodeMeaning=element.getElementsByTagName("SigCodeMeaning").item(0);
		
		nSigCodeAbbreviation.setTextContent(SigCode);
		nSigCodeMeaning.setTextContent(SigCode.concat(" Daily"));
		
		SigCodeAbbreviation= nSigCodeAbbreviation.getTextContent().toString();
		SigCodeMeaning = nSigCodeMeaning.getTextContent().toString();
		//update messageId,Subject,message_thread_id
		Node cNode=doc.getElementsByTagName(IntegrationConstants.COMMUNICATION).item(0);
		Element ele=(Element) cNode;
		ele.setAttribute(IntegrationConstants.MESSAGE_ID,"11623b2e-6824-4c0e-9ed2-0ceb6f6a"+fourDigitRandom());
		Node ncFrom=ele.getElementsByTagName(IntegrationConstants.FROM).item(0);
		Node ncTo=ele.getElementsByTagName(IntegrationConstants.TO).item(0);
		ncFrom.setTextContent(medication_details.get(8));
		ncTo.setTextContent(medication_details.get(9));
		Node nSubject=element.getElementsByTagName(IntegrationConstants.SUBJECT).item(0);
		nSubject.setTextContent(rxSMSubject);
		Node ncmessaageThreadID=ele.getElementsByTagName(IntegrationConstants.MESSAGE_THREAD_ID).item(0);
		ncmessaageThreadID.setTextContent(getPrescription_id);
		Node nmessage=element.getElementsByTagName(IntegrationConstants.QUESTION_MESSAGE).item(0);
		nmessage.setTextContent(rxSMBody);
		
		//update sent time
		Node nSent=element.getElementsByTagName(IntegrationConstants.SENT_DATE).item(0);
		nSent.setTextContent(SentDate(getCreatedDateTime));
		
		return domToString(doc);
		
	}
	
	/**
	 * 
	 * @param epoch
	 * @return readGMTtime
	 */
	public static String readTime(long epoch)
	{
		String time=String.valueOf(epoch);
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		Date now = new Date(Long.parseLong(time)*1000);
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
	public static void isReadCommunicationMessage(String xmlFileName, String messageID , String readdatetimestamp) throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		
		File stocks = new File(xmlFileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(stocks);
		doc.getDocumentElement().normalize();
		
		Log4jUtil.log("Expected Read DateTimestamp: "+readdatetimestamp);
		NodeList nodes = doc.getElementsByTagName(IntegrationConstants.READCOMMUNICATION);
		for (int i = 0; i < nodes.getLength(); i++) {
			
		Node node = nodes.item(i);
		if (node.getNodeType() == Node.ELEMENT_NODE)
		{
			Element element = (Element) node;
			String readValue;
			readValue=getValue(IntegrationConstants.MESSAGE_ID,element);
			if(readValue.equalsIgnoreCase(messageID))
			{
				Log4jUtil.log("Message ID is found in read communication response");
				getValue(IntegrationConstants.READDATETIMESTAMP, element).contains(readdatetimestamp);
				Log4jUtil.log("Actual Read DateTimestamp: "+getValue(IntegrationConstants.READDATETIMESTAMP, element));
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
	public static String setupHttpPostRequestExceptOauth(String strUrl, String payload, String responseFilePath,String externalSystemID) throws IOException, URISyntaxException{
		IHGUtil.PrintMethodName();
    	
		HttpClient client = new DefaultHttpClient();
        Log4jUtil.log("Post Request Url: "+ strUrl);
        
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
		Assert.assertTrue(response.getStatusLine().getStatusCode() == 200
				|| response.getStatusLine().getStatusCode() == 202,
				"Get Request response is "
						+ response.getStatusLine().getStatusCode()
						+ " instead of 200/202. Response message:\n"
						+ sResp);
		Log4jUtil.log("Response Code" +response.getStatusLine().getStatusCode());
		writeFile(responseFilePath, sResp);
		
		
		
		if(response.containsHeader(IntegrationConstants.LOCATION_HEADER)){
		Header[] h=response.getHeaders(IntegrationConstants.LOCATION_HEADER);
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
	public static boolean isCCDProcessingCompleted(String responsePath) throws ParserConfigurationException, SAXException, IOException {
		Document doc = buildDOMXML(responsePath);

		NodeList nodes = doc.getElementsByTagName(IntegrationConstants.TRANSPORTSTATUS);
		{
		Assert.assertTrue(nodes.item(0).getTextContent().equals(IntegrationConstants.CCDSTATUS),
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
	public static void isPatientAppeared(String responsePath,
			String externalPatientID, String medfusionID, String firstname) throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(responsePath);
		
		NodeList nodes = doc.getElementsByTagName(IntegrationConstants.PRACTICE_ID);
		for (int i = 0; i < nodes.getLength(); i++){
			if(nodes.item(i).getTextContent().equals(externalPatientID)){
				Log4jUtil.log("Searching: External Patient ID:" + externalPatientID + ", and Actual External Patient ID is:" + nodes.item(i).getTextContent().toString());
				NodeList node=doc.getElementsByTagName(IntegrationConstants.MEDFUSIONPATIENTID);
				Log4jUtil.log("Searching: Medfusion Patient ID:" + medfusionID + ", and Actual Medfusion Patient ID is: " + node.item(i).getTextContent().toString());
				Assert.assertTrue(node.item(i).getTextContent().equals(medfusionID),"Medfusion Patient Id was not found");
				node=doc.getElementsByTagName(IntegrationConstants.CCDTAG);
				Assert.assertTrue(node.item(i).getTextContent().contains("<given>"+firstname+"</given>"),"CCD DATA was not Found");
				Log4jUtil.log("Expected '"+"<given>"+firstname+"</given>"+"' is found in CCD XML.");
				break;
			}
			if(i == nodes.getLength() - 1){
				Assert.fail("Patient was not found");
			}
		}
		
		
	}
	/**
	 * Generate random String for Sig Code 
	 * @return
	 */
	public static String generateRandomString(){
		String CHAR_LIST ="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer randStr = new StringBuffer();
        for(int i=0; i<4; i++){
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
	public static List<String> genrateRandomData(String SSN,String Email,String Gender)
	{
		List<String> updatelist=new ArrayList<String>();
		updatelist.add("FName"+random.nextInt(100));//FirstName
		updatelist.add("TestPatient1"+random.nextInt(100));//Last name
		updatelist.add("243456789"+random.nextInt(10));//home phone
		updatelist.add("Address1"+random.nextInt(100));//Street 1 
		updatelist.add("Address2"+random.nextInt(100));//Street 2
		updatelist.add("City"+random.nextInt(100));//City
		updatelist.add("1456"+random.nextInt(10));//zipcode
		
		updatelist.add(SSN);//SSN
		updatelist.add(Gender);//Gender
		updatelist.add(Email);//EMail
		
		updatelist.add("MName"+random.nextInt(100));//Middle Name
		updatelist.add("242456789"+random.nextInt(10));//Mobile phone
		updatelist.add("989456789"+random.nextInt(10));//Work phone
		updatelist.add("01/01/199"+random.nextInt(10));//Date of birth
		
		
		//additional data For Insurance Detials
		updatelist.add("444-456-786"+random.nextInt(10));//Customer Service phone number
		updatelist.add("123-45-67"+random.nextInt(100));//Insured SSN
		updatelist.add("PolicyNumber"+random.nextInt(100));//Policy number
		updatelist.add("GroupNumber"+random.nextInt(100));//Group number
		updatelist.add("01/01/200"+random.nextInt(10));//Effective Date
		updatelist.add("12"+random.nextInt(100));//Co Pay
		
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
	public static void verifyPatientDetails(String xmlFileName, String practicePatientId, List<String> list, String insuranceName) throws ParserConfigurationException, SAXException, IOException, ParseException {
		Document doc = buildDOMXML(xmlFileName);
		NodeList patients = doc.getElementsByTagName(IntegrationConstants.PRACTICE_PATIENT_ID);
		//boolean found = false;
		for(int i = 0; i < patients.getLength(); i++){
			if(patients.item(i).getTextContent().equals(practicePatientId)){
				Element patient = (Element) patients.item(i).getParentNode().getParentNode();
							
				Log4jUtil.log("Checking Patient FirstName, Last Name, SSN, Gender");
				Node FirstName = patient.getElementsByTagName(IntegrationConstants.FIRST_NAME).item(0);
				BaseTestSoftAssert.verifyEquals(FirstName.getTextContent(), list.get(0), "Patient has different FirstName than expected. FirstName is: " + FirstName.getTextContent());
				Node LastName = patient.getElementsByTagName(IntegrationConstants.LAST_NAME).item(0);
				BaseTestSoftAssert.verifyEquals(LastName.getTextContent(), list.get(1), "Patient has different LastName than expected. LastName is: " + LastName.getTextContent());
				/*Node SSN = patient.getElementsByTagName(IntegrationConstants.SSN).item(0);
				BaseTestSoftAssert.verifyEquals(SSN.getTextContent(), list.get(7), "Patient has different SSN than expected. Gender is: " + SSN.getTextContent());*/
				Node Gender = patient.getElementsByTagName(IntegrationConstants.GENDER).item(0);
				BaseTestSoftAssert.verifyEquals(Gender.getTextContent(), list.get(8), "Patient has different Gender than expected. Gender is: " + Gender.getTextContent());
				Log4jUtil.log("Checking Patient HomePhone, Email Address");
				Node HomePhone = patient.getElementsByTagName(IntegrationConstants.HOMEPHONE).item(0);
				BaseTestSoftAssert.verifyEquals(HomePhone.getTextContent(), list.get(2), "Patient has different HomePhone than expected. HomePhone is: " + HomePhone.getTextContent());
				Node EmailAddress = patient.getElementsByTagName(IntegrationConstants.EMAIL_ADDRESS).item(0);
				BaseTestSoftAssert.verifyEquals(EmailAddress.getTextContent(), list.get(9), "Patient has different EmailAddress than expected. EmailAddress is: " + EmailAddress.getTextContent());
				Log4jUtil.log("Checking Patient Address1, Address2, City, ZipCode");
				Node Line1 = patient.getElementsByTagName(IntegrationConstants.LINE1).item(0);
				BaseTestSoftAssert.verifyEquals(Line1.getTextContent(), list.get(3), "Patient has different Address1 than expected. Address2 is: " + Line1.getTextContent());
				Node Line2 = patient.getElementsByTagName(IntegrationConstants.LINE2).item(0);
				BaseTestSoftAssert.verifyEquals(Line2.getTextContent(), list.get(4), "Patient has different Address2 than expected. Address1 is: " + Line2.getTextContent());
				Node City = patient.getElementsByTagName(IntegrationConstants.CITY).item(0);
				BaseTestSoftAssert.verifyEquals(City.getTextContent(), list.get(5), "Patient has different City than expected. City is: " + City.getTextContent());
				Node ZipCode = patient.getElementsByTagName(IntegrationConstants.ZIPCODE).item(0);
				BaseTestSoftAssert.verifyEquals(ZipCode.getTextContent(), list.get(6), "Patient has different ZipCode than expected. ZipCode is: " + ZipCode.getTextContent());
				if(insuranceName!=null)
				{			
					String birthdate=convertDate(list.get(13))+"T00:00:00";
					Log4jUtil.log("Checking Patient Date of Birth, Middle Name");
					Node DateOfBirth = patient.getElementsByTagName(IntegrationConstants.DATEOFBIRTH).item(0);
					BaseTestSoftAssert.verifyEquals(DateOfBirth.getTextContent(), birthdate, "Patient has different DateOfBirth than expected. DateOfBirth is: " + DateOfBirth.getTextContent());
					Node MiddleName = patient.getElementsByTagName(IntegrationConstants.MIDDLENAME).item(0);
					BaseTestSoftAssert.verifyEquals(MiddleName.getTextContent(), list.get(10), "Patient has different MiddleName than expected. MiddleName is: " + MiddleName.getTextContent());
					Log4jUtil.log("Checking Patient Mobile No, WorkPhone");
					Node MobilePhone = patient.getElementsByTagName(IntegrationConstants.MOBILEPHONE).item(0);
					BaseTestSoftAssert.verifyEquals(MobilePhone.getTextContent(), list.get(11), "Patient has different MobilePhone than expected. MobilePhone is: " + MobilePhone.getTextContent());
					Node WorkPhone = patient.getElementsByTagName(IntegrationConstants.WORKPHONE).item(0);
					BaseTestSoftAssert.verifyEquals(WorkPhone.getTextContent(), list.get(12), "Patient has different WorkPhone than expected. WorkPhone is: " + WorkPhone.getTextContent());
					Log4jUtil.log("Checking Patient Preferred Language, Race, Ethnicity, MaritalStatus, ChooseCommunication");
					Node PreferredLanguage = patient.getElementsByTagName(IntegrationConstants.PREFERREDLANGUAGE).item(0);
					BaseTestSoftAssert.verifyEquals(PreferredLanguage.getTextContent(), list.get(20), "Patient has different PreferredLanguage than expected. PreferredLanguage is: " + PreferredLanguage.getTextContent());
					Node Race = patient.getElementsByTagName(IntegrationConstants.RACE).item(0);
					BaseTestSoftAssert.verifyEquals(Race.getTextContent(), list.get(21), "Patient has different Race than expected. Race is: " + Race.getTextContent());
					Node Ethnicity = patient.getElementsByTagName(IntegrationConstants.ETHINICITY).item(0);
					BaseTestSoftAssert.verifyEquals(Ethnicity.getTextContent(), list.get(22), "Patient has different Ethnicity than expected. Ethnicity is: " + Ethnicity.getTextContent());
					Node MaritalStatus = patient.getElementsByTagName(IntegrationConstants.MARRITALSTATUS).item(0);
					BaseTestSoftAssert.verifyEquals(MaritalStatus.getTextContent(), list.get(23).toUpperCase(), "Patient has different MaritalStatus than expected. MaritalStatus is: " + MaritalStatus.getTextContent());
					Node ChooseCommunication = patient.getElementsByTagName(IntegrationConstants.CHOOSECOMMUNICATION).item(0);
					BaseTestSoftAssert.verifyEquals(ChooseCommunication.getTextContent(), list.get(24), "Patient has different ChooseCommunication than expected. ChooseCommunication is: " + ChooseCommunication.getTextContent());
					/*Node state = patient.getElementsByTagName(IntegrationConstants.PROCESSING_STATE).item(0);
					BaseTestSoftAssert.verifyEquals(state.getTextContent(), list.get(25), "Patient has different state than expected. state is: " + state.getTextContent());
					*/
					//PRIMARY INSURANCE DETAILS 
					Log4jUtil.log("Checking Insurance PolicyNumber, Insurance Name");
					Node cNode=patient.getElementsByTagName(IntegrationConstants.PRIMARYINSURANCE).item(0);
					Element ele=(Element) cNode;
					Node PolicyNumber = ele.getElementsByTagName(IntegrationConstants.POLICYNUMBER).item(0);
					BaseTestSoftAssert.verifyEquals(PolicyNumber.getTextContent(), list.get(16), "Patient has different PolicyNumber than expected. PolicyNumber is: " + PolicyNumber.getTextContent());
				    Node CompanyName = ele.getElementsByTagName(IntegrationConstants.COMPANYNAME).item(0);
					BaseTestSoftAssert.verifyEquals(CompanyName.getTextContent(), insuranceName, "Patient has different Insurance Name than expected. CompanyName is: " + CompanyName.getTextContent());
					Log4jUtil.log("Checking Patient Address1, Address2, City, ZipCode");
					Node InsuranceAddressLine1 = ele.getElementsByTagName(IntegrationConstants.LINE1).item(0);
					BaseTestSoftAssert.verifyEquals(InsuranceAddressLine1.getTextContent(), list.get(3), "Patient has different InsuranceAddressLine1 than expected. InsuranceAddressLine1 is: " + InsuranceAddressLine1.getTextContent());
					Node InsuranceAddressLine2 = ele.getElementsByTagName(IntegrationConstants.LINE2).item(0);
					BaseTestSoftAssert.verifyEquals(InsuranceAddressLine2.getTextContent(), list.get(4), "Patient has different InsuranceAddressLine2 than expected. InsuranceAddressLine1 is: " + InsuranceAddressLine2.getTextContent());
					Node InsuranceCity = ele.getElementsByTagName(IntegrationConstants.CITY).item(0);
					BaseTestSoftAssert.verifyEquals(InsuranceCity.getTextContent(), list.get(5), "Patient has different InsuranceCity than expected. InsuranceCity is: " + InsuranceCity.getTextContent());
					Node InsuranceZipCode = ele.getElementsByTagName(IntegrationConstants.ZIPCODE).item(0);
					BaseTestSoftAssert.verifyEquals(InsuranceZipCode.getTextContent(), list.get(6), "Patient has different InsuranceZipCode than expected. InsuranceZipCode is: " + InsuranceZipCode.getTextContent());
					Log4jUtil.log("Checking Patient Relation To Subscriber, Subscriber Date Of Birth, Group Number");
					Node PatientRelationToSubscriber = ele.getElementsByTagName(IntegrationConstants.PATIENTRELATIONTOSUBSCRIBER).item(0);
					BaseTestSoftAssert.verifyEquals(PatientRelationToSubscriber.getTextContent(), list.get(26).toUpperCase(), "Patient has different PatientRelationToSubscriber than expected. InsuranceZipCode is: " + PatientRelationToSubscriber.getTextContent());
					Node SubscriberDateOfBirth = ele.getElementsByTagName(IntegrationConstants.SUBSCRIBERDATEOFBIRTH).item(0);
					BaseTestSoftAssert.verifyEquals(SubscriberDateOfBirth.getTextContent(), birthdate, "Patient has different SubscriberDateOfBirth than expected. SubscriberDateOfBirth is: " + SubscriberDateOfBirth.getTextContent());
					/*Node ClaimsPhone = ele.getElementsByTagName(IntegrationConstants.CLAIMSPHONE).item(0);
					BaseTestSoftAssert.verifyEquals(ClaimsPhone.getTextContent(), list.get(14), "Patient has different ClaimsPhone than expected. ClaimsPhone is: " + ClaimsPhone.getTextContent());
					Node SubscriberSocialSecurityNumber = ele.getElementsByTagName(IntegrationConstants.SUBSCRIBERSSN).item(0);
					BaseTestSoftAssert.verifyEquals(SubscriberSocialSecurityNumber.getTextContent(), list.get(15), "Patient has different SubscriberSocialSecurityNumber than expected. SubscriberSocialSecurityNumber is: " + SubscriberSocialSecurityNumber.getTextContent());*/
					Node GroupNumber = ele.getElementsByTagName(IntegrationConstants.GROUPNUMBER).item(0);
					BaseTestSoftAssert.verifyEquals(GroupNumber.getTextContent(), list.get(17), "Patient has different GroupNumber than expected. GroupNumber is: " + GroupNumber.getTextContent());
					break;
				}
				
				
			}
			
		}
		
		//Assert.assertTrue(found, "Patient was not found in the response XML");
		
				
	}
/**
 * 
 * @param dateString
 * @return
 * @throws ParseException
 */
	private static String convertDate(String dateString) throws ParseException {
		SimpleDateFormat givenFormat=new SimpleDateFormat("dd/mm/yyyy");
		SimpleDateFormat expectedFormat=new SimpleDateFormat("yyyy-MM-dd");
		Date date=givenFormat.parse(dateString);
		String expectedDate=expectedFormat.format(date);
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
	public static String prepareCCD(String xmlFileName, String practicePatientId, String medfusionPatientID) throws ParserConfigurationException, SAXException, IOException, TransformerException{
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);
		
		Node pidNode = doc.getElementsByTagName(IntegrationConstants.PRACTICE_PATIENT_ID).item(0);
		pidNode.setTextContent(practicePatientId);
		Node mdNode = doc.getElementsByTagName(IntegrationConstants.MEDFUSIONPATIENTID).item(0);
		mdNode.setTextContent(medfusionPatientID);
		
		return domToString(doc);
	}
	/**
	 * Generate Batch PIDC xml with unique values of Patient ExternalID , First Name & Last Name  
	 * @param xmlFileName
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public static String generateBatchPIDC(String xmlFileName) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);
		
		NodeList pnode=doc.getElementsByTagName(IntegrationConstants.PATIENT);
		for(int i=0;i < pnode.getLength();i++)
		{
			String randomNo=IHGUtil.createRandomNumericString();
			String practicePatientID="Patient"+randomNo;
			Node pidNode = doc.getElementsByTagName(IntegrationConstants.PRACTICE_PATIENT_ID).item(i);
			pidNode.setTextContent(practicePatientID);
			testData(practicePatientID);
			Element ele1=(Element) pnode.item(i);
			NodeList nameNode = ele1.getElementsByTagName(IntegrationConstants.NName);
			Element ele=(Element) nameNode.item(0);
			{
				String fName="FNAME&apos;"+randomNo;
				Node fnameNode = ele.getElementsByTagName(IntegrationConstants.FIRST_NAME).item(0);
				fnameNode.setTextContent(fName);
				
				testData(escapeXml(fName));
				String lName="TestPatient1&amp;"+randomNo;
				Node LnameNode = ele.getElementsByTagName(IntegrationConstants.LAST_NAME).item(0);
				LnameNode.setTextContent(lName);
				testData(escapeXml(lName));
			}
			
		}
		
		return domToString(doc);
	}
	/**
	 * add patient details 
	 * @param data
	 */
	public static void testData(String data) {
		patientDatails.add(data);
	}
	
	/** remove the special characters from string and return normal string
	 * 
	 * @param specialDataString - Given string with special character
	 * @return
	 */
	public static String escapeXml(String specialDataString) {
	    return specialDataString.replaceAll("&amp;", "&").replaceAll("&gt;", ">").replaceAll("&lt;", "<").replaceAll("&quot;", "\"").replaceAll("&apos;", "'");
	}


	/**
	 * 
	 * @param responsePath
	 * @return 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static void isPaymentAppeared(String responsePath,String patientAccountNumber, String status, String confirmationNumber) throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(responsePath);

		Log4jUtil.log("finding Payment by Account Number");
		boolean found = false;
		NodeList accountNumber = doc.getElementsByTagName(IntegrationConstants.ACCOUNTNUMBER);
		for(int i = 0; i < accountNumber.getLength(); i++){
			if(accountNumber.item(i).getTextContent().equals(patientAccountNumber)){
				Log4jUtil.log("Searching: Patient Account Number:" + patientAccountNumber + ", and Actual Patient Account Number is:" + accountNumber.item(i).getTextContent().toString());
				Element payment = (Element) accountNumber.item(i).getParentNode();
				Node paymentType=payment.getElementsByTagName(IntegrationConstants.PAYMENTTYPE).item(0);
				Log4jUtil.log("Searching: Bill Payment Type:" + "BillPayment" + ", and Actual Bill Payment Type is:" + paymentType.getTextContent().toString());
				BaseTestSoftAssert.verifyEquals(paymentType.getTextContent(),"BillPayment", "Bill Payment Type has different than expected. Type is: " + paymentType.getTextContent());
				paymentType=paymentType.getParentNode();
				if(paymentType.hasAttributes())
				{
					Attr attr=(Attr) paymentType.getAttributes().getNamedItem("id");
					paymentID=attr.getValue();
				}
				Node paymentStatus=payment.getElementsByTagName(IntegrationConstants.PAYMENTSTATUS).item(0);
				Log4jUtil.log("Searching: Payment Status:" + status + ", and Actual Payment Status is:" + paymentStatus.getTextContent().toString());
				BaseTestSoftAssert.verifyEquals(paymentStatus.getTextContent(), status, "Payment Status has different than expected. Type is: " + paymentStatus.getTextContent());
				Log4jUtil.log("Checking Payment Amount & Card Last digit Information");
				Node cNode=payment.getElementsByTagName(IntegrationConstants.PAYMENTINFO).item(0);
				Element ele=(Element) cNode;
				Node amount = ele.getElementsByTagName(IntegrationConstants.AMOUNT).item(0);
				BaseTestSoftAssert.verifyEquals(amount.getTextContent(), "100.00", "Payment has different amount than expected. Amount is: " + amount.getTextContent());
				Node digits = ele.getElementsByTagName(IntegrationConstants.LASTDIGITS).item(0);
				BaseTestSoftAssert.verifyEquals(digits.getTextContent(), "1111", "Payment has different last digit than expected. Amount is: " + digits.getTextContent());
				Node ccType = ele.getElementsByTagName(IntegrationConstants.CCTYPE).item(0);
				Log4jUtil.log("Searching: CC Type:" + "Visa" + ", and Actual CC Type is:" + ccType.getTextContent().toString());
				BaseTestSoftAssert.verifyEquals(ccType.getTextContent(), "Visa", "Payment has different amount than expected. Amount is: " + ccType.getTextContent());
				Node nconfirmationNumber = ele.getElementsByTagName(IntegrationConstants.CONFIRMNUMBER).item(0);
				BaseTestSoftAssert.verifyEquals(nconfirmationNumber.getTextContent(), confirmationNumber, "Payment has different confirmation Number than expected. Confirmation Number is: " + nconfirmationNumber.getTextContent());
				
				found = true ;
			    break;
				}
				
				
				
		}
		Assert.assertTrue(found, "Payment Account Number Node was not found in the response XML");
		
		
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
	public static String preparePayment(String xmlFile,String paymentID,String amonunt,String type) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{
		IHGUtil.PrintMethodName();
		Document doc=buildDOMXML(xmlFile);
		Node node=doc.getElementsByTagName(IntegrationConstants.PAYMENT).item(0);
		Element element=(Element) node;
		element.setAttribute(IntegrationConstants.ID, paymentID);
		Node nodeType = element.getElementsByTagName(IntegrationConstants.PAYMENTTYPE).item(0);
		nodeType.setTextContent(type);
		if(amonunt!=null)
		{
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
	public static void validateNode(String xmlFileName,String value,char nodeName, String patientID) throws ParserConfigurationException, SAXException, IOException
	{
		IHGUtil.PrintMethodName();
		boolean found = false;
		Document doc=buildDOMXML(xmlFileName);
		NodeList patient=doc.getElementsByTagName(IntegrationConstants.PRACTICE_PATIENT_ID);
		for(int i=0;i < patient.getLength();i++){
		if(patient.item(i).getTextContent().equalsIgnoreCase(patientID))
		{
			Element ele = (Element) patient.item(i).getParentNode().getParentNode();
			Node node=null;
			switch (nodeName) 
				{
				case 'R':
					node=ele.getElementsByTagName(IntegrationConstants.RACE).item(0);
					break;
				case 'E':
					node=ele.getElementsByTagName(IntegrationConstants.ETHINICITY).item(0);
					break;
				case 'L':
					node=ele.getElementsByTagName(IntegrationConstants.PREFERREDLANGUAGE).item(0);
					break;
				case 'M':
					node=ele.getElementsByTagName(IntegrationConstants.MARRITALSTATUS).item(0);
					break;
				case 'C':
					node=ele.getElementsByTagName(IntegrationConstants.CHOOSECOMMUNICATION).item(0);
					break;
				default:
					break;
				}
				if(node==null)
				{
					BaseTestSoftAssert.verifyTrue(found, "Node Not Found");
					found = true;
					break;
				}
				Log4jUtil.log("Expected Value: " + value + ", and Actual Value is: " + node.getTextContent());
				BaseTestSoftAssert.verifyTrue(node.getTextContent().equalsIgnoreCase(value), "Value mismatched");
				
				found = true;
				break;
			}
		
		}
		Assert.assertTrue(found, "Patient was not found in the response XML");
			
	}
	public static void setupHttpGetRequestExceptOauth(String strUrl, String responseFilePath) throws IOException, URISyntaxException{
		IHGUtil.PrintMethodName();
		HttpClient client = new DefaultHttpClient();
        Log4jUtil.log("Post Request Url: "+ strUrl);
        
        HttpGet httpGetReq = new HttpGet(strUrl);
        httpGetReq.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000)
        .setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
        httpGetReq.setURI(new URI(strUrl));
        httpGetReq.addHeader("Authentication-Type", "2wayssl");
        httpGetReq.addHeader("Content-Type", "application/xml");
        HttpResponse resp = client.execute(httpGetReq);
        HttpEntity entity = resp.getEntity();
        String sResp = EntityUtils.toString(entity);
        
        Log4jUtil.log("Check for http 200 response");
		Assert.assertTrue(resp.getStatusLine().getStatusCode() == 200,
				"Get Request response is " + resp.getStatusLine().getStatusCode() + " instead of 200. Response message received:\n" + sResp);
		writeFile(responseFilePath, sResp);
		       
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
	public static String generateBatchAMDC(String xmlFileName, List newdata) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);
		
		NodeList pnode=doc.getElementsByTagName(IntegrationConstants.SECURE_MESSAGE);
		for(int i=0;i < pnode.getLength();i++)
		{
			Node node = doc.getElementsByTagName(IntegrationConstants.SECURE_MESSAGE).item(i);
			Element elem = (Element) node;
			//set random message id
			elem.setAttribute(IntegrationConstants.MESSAGE_ID, elem.getAttribute(IntegrationConstants.MESSAGE_ID) + fourDigitRandom());
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
			for(int k=0;k<4;k++)
			{
				newdata.remove(0);
			}
		}
		return domToString(doc);
	}
	
	public static void verifyPayment(String responsePath,String amount, String status, String Type, String confirmationNumber) throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(responsePath);

		Log4jUtil.log("finding Payment by Amount");
		boolean found = false;
		NodeList namount = doc.getElementsByTagName(IntegrationConstants.AMOUNT);
		for(int i = 0; i < namount.getLength(); i++){
			if(namount.item(i).getTextContent().equals(amount)){
				Log4jUtil.log("Searching: Patient Amount :" + amount + ", and Actual Patient Account is:" + namount.item(i).getTextContent().toString());
				Element payment = (Element) namount.item(i).getParentNode().getParentNode();
				Node paymentType=payment.getElementsByTagName(IntegrationConstants.PAYMENTTYPE).item(0);
				Log4jUtil.log("Searching: Bill Payment Type:" + Type + ", and Actual Bill Payment Type is:" + paymentType.getTextContent().toString());
				BaseTestSoftAssert.verifyEquals(paymentType.getTextContent(), Type , "Bill Payment Type has different than expected. Type is: " + paymentType.getTextContent());
				paymentType=paymentType.getParentNode(); 
				if(paymentType.hasAttributes())
				{
					Attr attr=(Attr) paymentType.getAttributes().getNamedItem("id");
					paymentID=attr.getValue();
				}
				Node paymentStatus=payment.getElementsByTagName(IntegrationConstants.PAYMENTSTATUS).item(0);
				Log4jUtil.log("Searching: Payment Status:" + status + ", and Actual Payment Status is:" + paymentStatus.getTextContent().toString());
				BaseTestSoftAssert.verifyEquals(paymentStatus.getTextContent(), status, "Payment Status has different than expected. Type is: " + paymentStatus.getTextContent());
				Log4jUtil.log("Checking Payment Amount & Card Last digit Information");
				Node cNode=payment.getElementsByTagName(IntegrationConstants.PAYMENTINFO).item(0);
				Element ele=(Element) cNode;
				/*Node amount = ele.getElementsByTagName(IntegrationConstants.AMOUNT).item(0);
				BaseTestSoftAssert.verifyEquals(amount.getTextContent(), "100.00", "Payment has different amount than expected. Amount is: " + amount.getTextContent());
				Node digits = ele.getElementsByTagName(IntegrationConstants.LASTDIGITS).item(0);
				BaseTestSoftAssert.verifyEquals(digits.getTextContent(), "1111", "Payment has different last digit than expected. Amount is: " + digits.getTextContent());*/
				Node ccType = ele.getElementsByTagName(IntegrationConstants.CCTYPE).item(0);
				Log4jUtil.log("Searching: CC Type:" + "Visa" + ", and Actual CC Type is:" + ccType.getTextContent().toString());
				BaseTestSoftAssert.verifyEquals(ccType.getTextContent(), "Visa", "Payment has different amount than expected. Amount is: " + ccType.getTextContent());
				if(confirmationNumber!=null){
				/*Node nconfirmationNumber = ele.getElementsByTagName(IntegrationConstants.CONFIRMNUMBER).item(0);
				Log4jUtil.log("Searching: Confirmation Number:" + confirmationNumber + ", and Actual Confirmation Number is:" + nconfirmationNumber.getTextContent().toString());
				BaseTestSoftAssert.verifyEquals(nconfirmationNumber.getTextContent(), confirmationNumber, "Payment has different confirmation Number than expected. Amount is: " + nconfirmationNumber.getTextContent());*/
				}
				found = true ;
			    break;
				}
				
				
				
		}
		Assert.assertTrue(found, "Payment Amount was not found in the response XML");
		
		
	}
	
	/**
	 * 
	 * @param strUrl
	 * @param responseFilePath
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static String setupHttpGetRequestExceptoAuth(String strUrl, String responseFilePath) throws IOException, URISyntaxException{
		IHGUtil.PrintMethodName();
		HttpClient client = new DefaultHttpClient();
        Log4jUtil.log("Get Request Url: "+ strUrl);
        
        HttpGet httpGetReq = new HttpGet(strUrl);
        httpGetReq.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000)
        .setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
        httpGetReq.setURI(new URI(strUrl));
        httpGetReq.addHeader("Authentication-Type", "2wayssl");
        httpGetReq.addHeader("Content-Type", "application/xml");
        HttpResponse resp = client.execute(httpGetReq);
        HttpEntity entity = resp.getEntity();
        String sResp=null;
        if(entity!=null){
        sResp = EntityUtils.toString(entity);
        Log4jUtil.log("Check for http 200 response");
        responseCode=resp.getStatusLine().getStatusCode();
        Assert.assertTrue(resp.getStatusLine().getStatusCode() == 200,
				"Get Request response is " + resp.getStatusLine().getStatusCode() + " instead of "+ 200 +". Response message received:\n" + sResp);
        writeFile(responseFilePath, sResp);
          
        if(resp.containsHeader(IntegrationConstants.TIMESTAMP_HEADER)){
			Header[] h=resp.getHeaders(IntegrationConstants.TIMESTAMP_HEADER);
			return h[0].getValue();
			}
        }
        else
        {
        	responseCode=204;
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
	public static void checkPatientRegistered(String xmlFileName, List<String> updateData) throws ParserConfigurationException, SAXException, IOException {
		Document doc = buildDOMXML(xmlFileName);
		NodeList nfirstName = doc.getElementsByTagName(IntegrationConstants.FIRST_NAME);
		boolean found = false;
		for(int i = 0; i < nfirstName.getLength(); i++){
			if(nfirstName.item(i).getTextContent().equals(updateData.get(0))){
				Log4jUtil.log("Searching: Medfusion Patient First Name:" + updateData.get(0) + ", and Actual Medfusion Patient First Name is:" + nfirstName.item(i).getTextContent().toString());
				BaseTestSoftAssert.verifyEquals(nfirstName.item(i).getTextContent(), updateData.get(0), "Medfusion Patient First Name has different than expected. First Name is: " + nfirstName.item(i).getTextContent());
				Element nPatient = (Element) nfirstName.item(i).getParentNode().getParentNode();
				Node nlastName = nPatient.getElementsByTagName(IntegrationConstants.LAST_NAME).item(0);
				Log4jUtil.log("Searching: Patient Last Name:" + updateData.get(1) + ", and Actual Patient LastName is:" + nlastName.getTextContent().toString());
				BaseTestSoftAssert.verifyEquals(nlastName.getTextContent(), updateData.get(1), "Medfusion Patient Last Name has different than expected. Last Name is: " + nlastName.getTextContent());
				try {
					if (updateData.get(2)!=null)
					{
					Node nMiddleName = nPatient.getElementsByTagName(IntegrationConstants.MIDDLENAME).item(0);
					Log4jUtil.log("Searching: Patient Middle Name:" + updateData.get(10) + ", and Actual Patient Middle Name is:" + nMiddleName.getTextContent().toString());
					BaseTestSoftAssert.verifyEquals(nMiddleName.getTextContent(), updateData.get(10), "Medfusion Patient Middle Name has different than expected. Middle Name is: " + nMiddleName.getTextContent());
					Node nAddress1 = nPatient.getElementsByTagName(IntegrationConstants.LINE1).item(0);
					Log4jUtil.log("Searching: Patient Address1 :" + updateData.get(2) + ", and Actual Patient Address1 is:" + nAddress1.getTextContent().toString());
					BaseTestSoftAssert.verifyEquals(nAddress1.getTextContent(), updateData.get(2), "Medfusion Patient Address1 has different than expected. Address1 is: " + nAddress1.getTextContent());
					Node nAddress2 = nPatient.getElementsByTagName(IntegrationConstants.LINE2).item(0);
					Log4jUtil.log("Searching: Patient Address2 :" + updateData.get(3) + ", and Actual Patient Address2 is:" + nAddress2.getTextContent().toString());
					BaseTestSoftAssert.verifyEquals(nAddress2.getTextContent(), updateData.get(3), "Medfusion Patient Address2 has different than expected. Address2 is: " + nAddress2.getTextContent());
					Node nHomePhone = nPatient.getElementsByTagName(IntegrationConstants.HOMEPHONE).item(0);
					Log4jUtil.log("Searching: Patient Home Phone :" + updateData.get(4) + ", and Actual Patient Home Phone is:" + nHomePhone.getTextContent().toString());
					BaseTestSoftAssert.verifyEquals(nHomePhone.getTextContent(), updateData.get(4), "Medfusion Patient Home Phone has different than expected. HomePhone is: " + nHomePhone.getTextContent());
					Node nDOB = nPatient.getElementsByTagName(IntegrationConstants.DATEOFBIRTH).item(0);
					Log4jUtil.log("Searching: Patient Date of Birth :" + updateData.get(5) + ", and Actual Patient Date of Birth is:" + nDOB.getTextContent().toString());
					Node nRace = nPatient.getElementsByTagName(IntegrationConstants.RACE).item(0);
					Log4jUtil.log("Searching: Race Value :" + updateData.get(7) + ", and Actual Patient Date of Birth is:" + nRace.getTextContent().toString());
					BaseTestSoftAssert.verifyEquals(nRace.getTextContent(), updateData.get(7), "Race has different than expected. Race is: " + nRace.getTextContent());
					Node nEthnicity = nPatient.getElementsByTagName(IntegrationConstants.ETHINICITY).item(0);
					Log4jUtil.log("Searching: Ethnicity Value :" + updateData.get(8) + ", and Actual Ethnicity is:" + nEthnicity.getTextContent().toString());
					BaseTestSoftAssert.verifyEquals(nEthnicity.getTextContent(), updateData.get(8), "Ethnicity has different than expected. Ethnicity is: " + nEthnicity.getTextContent());
					Node nChooseCommunication = nPatient.getElementsByTagName(IntegrationConstants.CHOOSECOMMUNICATION).item(0);
					Log4jUtil.log("Searching: Preferred Communication Value :" + updateData.get(9) + ", and Actual communication value is:" + nChooseCommunication.getTextContent().toString());
					BaseTestSoftAssert.verifyEquals(nChooseCommunication.getTextContent(), updateData.get(9), "Patient has different ChooseCommunication than expected. ChooseCommunication is: " + nChooseCommunication.getTextContent());
					
					}
				} catch (Exception e) {
					Log4jUtil.log("#####");
				}
				found = true;
				break;
				}
		}
   		Assert.assertTrue(found, "Patient was not found in the response XML");
		
		
				
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
	
	public static void verifyHealthPatientInsuranceDetails(String xmlFileName,
			String patientID, List<String> insuranceData, String insurance_Name) throws ParserConfigurationException, SAXException, IOException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);
		boolean found=false;
		NodeList patients = doc.getElementsByTagName(IntegrationConstants.MEDFUSIONID);
		for(int i = 0; i < patients.getLength(); i++){
			if(patients.item(i).getTextContent().equals(patientID)){
				Element patient = (Element) patients.item(i).getParentNode().getParentNode();
				Log4jUtil.log("Checking Insurance PolicyNumber, Insurance Name");
				Node cNode=patient.getElementsByTagName(IntegrationConstants.PRIMARYINSURANCE).item(0);
				Element ele=(Element) cNode;
				Node PolicyNumber = ele.getElementsByTagName(IntegrationConstants.POLICYNUMBER).item(0);
				BaseTestSoftAssert.verifyEquals(PolicyNumber.getTextContent(), insuranceData.get(16), "Patient has different PolicyNumber than expected. PolicyNumber is: " + PolicyNumber.getTextContent());
			    Node CompanyName = ele.getElementsByTagName(IntegrationConstants.COMPANYNAME).item(0);
				BaseTestSoftAssert.verifyEquals(CompanyName.getTextContent(), insurance_Name, "Patient has different Insurance Name than expected. CompanyName is: " + CompanyName.getTextContent());
				Log4jUtil.log("Checking Patient Address1, Address2, City, ZipCode");
				Node InsuranceAddressLine1 = ele.getElementsByTagName(IntegrationConstants.LINE1).item(0);
				BaseTestSoftAssert.verifyEquals(InsuranceAddressLine1.getTextContent(), insuranceData.get(3), "Patient has different InsuranceAddressLine1 than expected. InsuranceAddressLine1 is: " + InsuranceAddressLine1.getTextContent());
				Node InsuranceAddressLine2 = ele.getElementsByTagName(IntegrationConstants.LINE2).item(0);
				BaseTestSoftAssert.verifyEquals(InsuranceAddressLine2.getTextContent(), insuranceData.get(4), "Patient has different InsuranceAddressLine2 than expected. InsuranceAddressLine1 is: " + InsuranceAddressLine2.getTextContent());
				Node InsuranceCity = ele.getElementsByTagName(IntegrationConstants.CITY).item(0);
				BaseTestSoftAssert.verifyEquals(InsuranceCity.getTextContent(), insuranceData.get(5), "Patient has different InsuranceCity than expected. InsuranceCity is: " + InsuranceCity.getTextContent());
				Node InsuranceZipCode = ele.getElementsByTagName(IntegrationConstants.ZIPCODE).item(0);
				BaseTestSoftAssert.verifyEquals(InsuranceZipCode.getTextContent(), insuranceData.get(6), "Patient has different InsuranceZipCode than expected. InsuranceZipCode is: " + InsuranceZipCode.getTextContent());
				Log4jUtil.log("Checking Patient Group Number");
				Node GroupNumber = ele.getElementsByTagName(IntegrationConstants.GROUPNUMBER).item(0);
				BaseTestSoftAssert.verifyEquals(GroupNumber.getTextContent(), insuranceData.get(17), "Patient has different GroupNumber than expected. GroupNumber is: " + GroupNumber.getTextContent());
				found = true;
				break;
				}
		}
   		Assert.assertTrue(found, "Patient was not found in the response XML");
	}

	public static String verifyEmailNotification(String gmailUserName,String gmailPassword, String recipient,int minute ) throws Exception
	{
		GmailBot gBot = new GmailBot();
		String emailMessageLink = null;
		
		Log4jUtil.log("Gmail User Name :"+ gmailUserName + "Password :"+gmailPassword);
		try {
			
		emailMessageLink=gBot.findInboxEmailLink(gmailUserName,gmailPassword,"New message from "+recipient,IntegrationConstants.EMAIL_BODY_LINK, minute, false, true);
		
		} catch (Exception e) {
			Log4jUtil.log("Error :"+e);
		}
		
		BaseTestSoftAssert.verifyTrue(emailMessageLink.length()!=0,"New secure message was not found in patient inbox ");
		
		Log4jUtil.log("Delete all messages from Inbox");
		gBot.deleteAllMessagesFromInbox(gmailUserName, gmailPassword);
		

		return emailMessageLink;
		
	}
	
	/** Generate PIDC payload with contains special characters data in given parameter except practice patient ID.
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
	public static String generatePIDCSpecialCharacter(String xmlFileName, String practicePatientId, String fName, String mName, String lName, String address1, String address2, String email) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		IHGUtil.PrintMethodName();
		Document doc = buildDOMXML(xmlFileName);
		NodeList pnode=doc.getElementsByTagName(IntegrationConstants.PATIENT);
		Node pidNode = doc.getElementsByTagName(IntegrationConstants.PRACTICE_PATIENT_ID).item(0);
		pidNode.setTextContent(practicePatientId);
		Node emailNode = doc.getElementsByTagName(IntegrationConstants.EMAIL_ADDRESS).item(0);
		emailNode.setTextContent(email);
		testData(practicePatientId);
		Element ele1=(Element) pnode.item(0);
		NodeList nameNode = ele1.getElementsByTagName(IntegrationConstants.NName);
		Element ele=(Element) nameNode.item(0);
		Node fnameNode = ele.getElementsByTagName(IntegrationConstants.FIRST_NAME).item(0);
	    fnameNode.setTextContent(fName);
		testData(escapeXml(fName));
		Node mnameNode = ele.getElementsByTagName(IntegrationConstants.MIDDLENAME).item(0);
		mnameNode.setTextContent(mName);
		testData(escapeXml(mName));
		Node LnameNode = ele.getElementsByTagName(IntegrationConstants.LAST_NAME).item(0);
		LnameNode.setTextContent(lName);
		testData(escapeXml(lName));
			 
		NodeList homeAddressNode=ele1.getElementsByTagName(IntegrationConstants.HOME_ADDRESS);
		ele=(Element) homeAddressNode.item(0);
		Node line1Node = ele.getElementsByTagName(IntegrationConstants.LINE1).item(0);
		line1Node.setTextContent(address1);
		testData(escapeXml(address1));
		Node line2Node = ele.getElementsByTagName(IntegrationConstants.LINE2).item(0);
		line2Node.setTextContent(address2);
		testData(escapeXml(address2));
		
		
		return domToString(doc);
			
		}
	
	/** Verify recent CCD message in patient portal.
	 * 
	 * @param ccdDate //actual CCD date displayed in patient portal
	 * @param ccdSendTimestamp  //POST CCD send timestamp
	 * @return
	 * @throws ParseException
	 */
	public static boolean verifyCCDMessageDate(String ccdDate, long ccdSendTimestamp) throws ParseException
	{
		IHGUtil.PrintMethodName();

		
		SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy hh:mm aaa");
		Date requiredDate=sdf.parse(ccdDate);
		Log4jUtil.log("Before Set TimeZone "+requiredDate.getTime());
		sdf.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
		requiredDate=sdf.parse(ccdDate);
		if(requiredDate.getTime()>=ccdSendTimestamp)
		{
			Log4jUtil.log("CCD sent date & time is :"+ccdDate);
			return true;
		}
		else
		{
			Log4jUtil.log("Recent CCD is not availble in patient Portal . Last CCD sent date & time is: "+ccdDate);
			return false;
		}
				
		
			
	}

}

