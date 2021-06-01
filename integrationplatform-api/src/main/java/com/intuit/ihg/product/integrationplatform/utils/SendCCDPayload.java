//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.integrationplatform.utils;

import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringEscapeUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.intuit.ihg.product.integrationplatform.pojo.EHDCInfo;

public class SendCCDPayload {

	static String output;
	public static String ccdMessageID;
	public static String CcdXmlString;

	public static String getCCDPayload(EHDCInfo testData) throws InterruptedException, IOException, SAXException {
		try {
			DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder icBuilder;
			icBuilder = icFactory.newDocumentBuilder();
			Document doc = icBuilder.newDocument();

			String schema = "http://schema.intuit.com/health/ccd/v1";
			Element mainRootElement = doc.createElementNS(schema, "CcdExchange");
			mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			doc.appendChild(mainRootElement);

			// CcdMessageHeaders
			Element CcdMessageHeaders = doc.createElement("CcdMessageHeaders");
			CcdMessageHeaders.setAttribute("xmlns", "");
			Element Sender = doc.createElement("Sender");
			Sender.setAttribute("deviceName", "Elekta:OIM");
			Sender.setAttribute("deviceVersion", "MOSAIQ: Version ACC_2.50.02B2, OIM: Version 2.92B1");
			Sender.setAttribute("vendorName", "Elekta");
			CcdMessageHeaders.appendChild(Sender);	
			Element LastUpdated = doc.createElement("LastUpdated");
			LastUpdated.appendChild(doc.createTextNode("2012-11-21T04:49:50.6773194-08:00"));
			CcdMessageHeaders.appendChild(LastUpdated);
			ccdMessageID = random20Numbers();
			Element MessageId = doc.createElement("MessageId");
			MessageId.appendChild(doc.createTextNode(ccdMessageID));
			CcdMessageHeaders.appendChild(MessageId);
			Element DataJob = doc.createElement("DataJob");
			CcdMessageHeaders.appendChild(DataJob);
			Element DataJobId = doc.createElement("DataJobId");
			DataJob.appendChild(DataJobId);
			mainRootElement.appendChild(CcdMessageHeaders);
			// End

			// PatientDemographics
			Element PatientDemographics = doc.createElement("PatientDemographics");
			PatientDemographics.setAttribute("xmlns", "");
			Element PatientIdentifier = doc.createElement("PatientIdentifier");
			Element PracticePatientId = doc.createElement("PracticePatientId");
			PracticePatientId.appendChild(doc.createTextNode(testData.getPracticePatientId()));
			PatientIdentifier.appendChild(PracticePatientId);
			PatientDemographics.appendChild(PatientIdentifier);

			// Name
			Element Name = doc.createElement("Name");
			Element Prefix = doc.createElement("Prefix");
			Prefix.appendChild(doc.createTextNode("Mr"));
			Name.appendChild(Prefix);
			Element FirstName = doc.createElement("FirstName");
			FirstName.appendChild(doc.createTextNode("ABC"));
			Name.appendChild(FirstName);
			Element MiddleName = doc.createElement("MiddleName");
			MiddleName.appendChild(doc.createTextNode(""));
			Name.appendChild(MiddleName);
			Element LastName = doc.createElement("LastName");
			LastName.appendChild(doc.createTextNode("XYZ"));
			Name.appendChild(LastName);
			PatientDemographics.appendChild(Name);
			// End

			// Address
			// WorkAddress
			Element Address = doc.createElement("Address");
			PatientDemographics.appendChild(Address);
			Element Line1 = doc.createElement("Line1");
			Address.appendChild(Line1);
			Line1.appendChild(doc.createTextNode("123"));

			Element Line2 = doc.createElement("Line2");
			Address.appendChild(Line2);
			Line2.appendChild(doc.createTextNode("Park Lane"));

			Element City = doc.createElement("City");
			Address.appendChild(City);
			City.appendChild(doc.createTextNode("BOSTON"));

			Element State = doc.createElement("State");
			Address.appendChild(State);
			State.appendChild(doc.createTextNode("MA"));

			Element Country = doc.createElement("Country");
			Address.appendChild(Country);
			Country.appendChild(doc.createTextNode("USA"));

			Element ZipCode = doc.createElement("ZipCode");
			Address.appendChild(ZipCode);
			ZipCode.appendChild(doc.createTextNode("02139"));

			mainRootElement.appendChild(PatientDemographics);
			// End

			// PracticeInformation
			Element PracticeInformation = doc.createElement("PracticeInformation");
			PracticeInformation.setAttribute("xmlns", "");
			Element PracticeIdentifier = doc.createElement("PracticeIdentifier");
			PracticeInformation.appendChild(PracticeIdentifier);
			Element PracticeId = doc.createElement("PracticeId");
			PracticeId.appendChild(doc.createTextNode(testData.getIntegrationPracticeID()));
			PracticeIdentifier.appendChild(PracticeId);
			PracticeInformation.appendChild(PracticeIdentifier);
			mainRootElement.appendChild(PracticeInformation);
			// End
			// PracticeName
			Element PracticeName = doc.createElement("PracticeName");
			PracticeName.appendChild(doc.createTextNode("Elekta"));
			PracticeInformation.appendChild(PracticeName);

			Element Provider = doc.createElement("Provider");
			PracticeInformation.appendChild(Provider);

			Element ProviderIdentifier = doc.createElement("ProviderIdentifier");
			Provider.appendChild(ProviderIdentifier);

			Element PracticeProviderId = doc.createElement("PracticeProviderId");
			PracticeProviderId.appendChild(doc.createTextNode(testData.getFrom()));
			ProviderIdentifier.appendChild(PracticeProviderId);
			
			Element Ccd = doc.createElement("Ccd");
			Ccd.setAttribute("xmlns", "");
			mainRootElement.appendChild(Ccd);
			
			String workingDir = System.getProperty("user.dir");
			workingDir = workingDir + testData.getCcdXMLPath();

			CcdXmlString = readFromFile(workingDir);

			Element CcdXml = doc.createElement("CcdXml");
			CcdXml.appendChild(doc.createTextNode(CcdXmlString));
			Ccd.appendChild(CcdXml);

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			DOMSource source = new DOMSource(doc);

			StringWriter writer = new StringWriter();
			transformer.transform(source, new StreamResult(writer));
			output = writer.toString();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
			assertTrue(false, "ParserConfigurationException: Issue while parsing the xml");
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
			assertTrue(false, "TransformerException: Issue while Transforming the dom");
		} catch (NullPointerException NPE) {
			NPE.printStackTrace();
			assertTrue(false, "Check if all input values are defined");
		}
		return output;
	}

	public static String random20Numbers() {
		return RandomStringUtils.random(20, false, true);
	}

	public static String readFromFile(String filePath) throws IOException {
		String textFileString = null;
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			textFileString = sb.toString();
		} finally {
			br.close();
		}
		textFileString = StringEscapeUtils.unescapeXml(textFileString);
		return textFileString;
	}
}