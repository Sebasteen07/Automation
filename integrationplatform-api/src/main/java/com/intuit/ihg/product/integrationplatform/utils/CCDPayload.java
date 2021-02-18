package com.intuit.ihg.product.integrationplatform.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class CCDPayload {
	static String output;
	public static String messageID = "";
	public static String CcdXmlString = "";

	public static String getCCDPayload(EHDC testData) throws InterruptedException, IOException, SAXException {
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
			Sender.setAttribute("deviceName", testData.deviceName);
			Sender.setAttribute("deviceVersion", testData.deviceVersion);
			Sender.setAttribute("vendorName", testData.vendorName);
			CcdMessageHeaders.appendChild(Sender);	
			Element LastUpdated = doc.createElement("LastUpdated");
			LastUpdated.appendChild(doc.createTextNode("2012-11-21T04:49:50.6773194-08:00"));
			CcdMessageHeaders.appendChild(LastUpdated);
			Element MessageId = doc.createElement("MessageId");
			MessageId.appendChild(doc.createTextNode(getUUID()));
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
			PracticePatientId.appendChild(doc.createTextNode(testData.PracticePatientId));
			PatientIdentifier.appendChild(PracticePatientId);
			PatientDemographics.appendChild(PatientIdentifier);

			// Name
			Element Name = doc.createElement("Name");
			Element Prefix = doc.createElement("Prefix");
			Prefix.appendChild(doc.createTextNode(testData.Prefix));
			Name.appendChild(Prefix);
			Element FirstName = doc.createElement("FirstName");
			FirstName.appendChild(doc.createTextNode(testData.FirstName));
			Name.appendChild(FirstName);
			Element MiddleName = doc.createElement("MiddleName");
			MiddleName.appendChild(doc.createTextNode(testData.MiddleName));
			Name.appendChild(MiddleName);
			Element LastName = doc.createElement("LastName");
			LastName.appendChild(doc.createTextNode(testData.LastName));
			Name.appendChild(LastName);
			PatientDemographics.appendChild(Name);
			// End
			// Address
			Element Address = doc.createElement("Address");
			PatientDemographics.appendChild(Address);
			Element Line1 = doc.createElement("Line1");
			Address.appendChild(Line1);
			Line1.appendChild(doc.createTextNode(testData.Line1));

			Element Line2 = doc.createElement("Line2");
			Address.appendChild(Line2);
			Line2.appendChild(doc.createTextNode(testData.Line2));

			Element City = doc.createElement("City");
			Address.appendChild(City);
			City.appendChild(doc.createTextNode(testData.City));

			Element State = doc.createElement("State");
			Address.appendChild(State);
			State.appendChild(doc.createTextNode(testData.State));

			Element Country = doc.createElement("Country");
			Address.appendChild(Country);
			Country.appendChild(doc.createTextNode(testData.Country));

			Element ZipCode = doc.createElement("ZipCode");
			Address.appendChild(ZipCode);
			ZipCode.appendChild(doc.createTextNode(testData.Zip));

			mainRootElement.appendChild(PatientDemographics);
			// End

			// PracticeInformation
			Element PracticeInformation = doc.createElement("PracticeInformation");
			PracticeInformation.setAttribute("xmlns", "");
			Element PracticeIdentifier = doc.createElement("PracticeIdentifier");
			PracticeInformation.appendChild(PracticeIdentifier);
			Element PracticeId = doc.createElement("PracticeId");
			PracticeId.appendChild(doc.createTextNode(testData.IntegrationPracticeID));
			PracticeIdentifier.appendChild(PracticeId);
			PracticeInformation.appendChild(PracticeIdentifier);
			mainRootElement.appendChild(PracticeInformation);
			// End
			// PracticeName
			Element PracticeName = doc.createElement("PracticeName");
			PracticeName.appendChild(doc.createTextNode(testData.EPracticeName));
			PracticeInformation.appendChild(PracticeName);

			Element Provider = doc.createElement("Provider");
			PracticeInformation.appendChild(Provider);

			Element ProviderIdentifier = doc.createElement("ProviderIdentifier");
			Provider.appendChild(ProviderIdentifier);

			Element PracticeProviderId = doc.createElement("PracticeProviderId");
			PracticeProviderId.appendChild(doc.createTextNode(testData.From));
			ProviderIdentifier.appendChild(PracticeProviderId);
			
			Element Ccd = doc.createElement("Ccd");
			Ccd.setAttribute("xmlns", "");
			mainRootElement.appendChild(Ccd);
			
			String workingDir = System.getProperty("user.dir");
			workingDir = workingDir + testData.ccdXMLPath;
			
			CcdXmlString = ExternalFileReader.readFromFile(workingDir);
					
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
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
		return output;
	}
	
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}
	
	public static String getCCDPayloadV3(EHDC testData) throws InterruptedException, IOException, SAXException {
		try {
			DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder icBuilder;
			icBuilder = icFactory.newDocumentBuilder();
			Document doc = icBuilder.newDocument();

			//String schema = "http://schema.intuit.com/health/ccd/v1";
			Element mainRootElement = doc.createElementNS("", "CcdExchange");
			doc.appendChild(mainRootElement);

			// CcdMessageHeaders
			Element CcdMessageHeaders = doc.createElement("CcdMessageHeaders");
			CcdMessageHeaders.setAttribute("xmlns", "");
			Element LastUpdated = doc.createElement("LastUpdated");
			LastUpdated.appendChild(doc.createTextNode("2012-11-21T04:49:50.6773194-08:00"));
			CcdMessageHeaders.appendChild(LastUpdated);
			Element MessageId = doc.createElement("MessageId");
			MessageId.appendChild(doc.createTextNode(getUUID()));
			CcdMessageHeaders.appendChild(MessageId);
			Element DataJob = doc.createElement("DataJob");
			CcdMessageHeaders.appendChild(DataJob);
			Element DataJobId = doc.createElement("DataJobId");
			DataJob.appendChild(DataJobId);
			mainRootElement.appendChild(CcdMessageHeaders);
			// End

			// PatientDemographics
			Element PatientDemographics = doc.createElement("PatientDemographics");
			Element PatientIdentifier = doc.createElement("PatientIdentifier");
			Element PracticePatientId = doc.createElement("PracticePatientId");
			PracticePatientId.appendChild(doc.createTextNode(testData.PracticePatientId));
			PatientIdentifier.appendChild(PracticePatientId);
			PatientDemographics.appendChild(PatientIdentifier);

			// Name
			Element Name = doc.createElement("Name");
			Element Prefix = doc.createElement("Prefix");
			Prefix.appendChild(doc.createTextNode(testData.Prefix));
			Name.appendChild(Prefix);
			Element FirstName = doc.createElement("FirstName");
			FirstName.appendChild(doc.createTextNode(testData.FirstName));
			Name.appendChild(FirstName);
			Element MiddleName = doc.createElement("MiddleName");
			MiddleName.appendChild(doc.createTextNode(testData.MiddleName));
			Name.appendChild(MiddleName);
			Element LastName = doc.createElement("LastName");
			LastName.appendChild(doc.createTextNode(testData.LastName));
			Name.appendChild(LastName);
			PatientDemographics.appendChild(Name);
			// End
			// Address
			Element Address = doc.createElement("Address");
			PatientDemographics.appendChild(Address);
			Element Line1 = doc.createElement("Line1");
			Address.appendChild(Line1);
			Line1.appendChild(doc.createTextNode(testData.Line1));

			Element Line2 = doc.createElement("Line2");
			Address.appendChild(Line2);
			Line2.appendChild(doc.createTextNode(testData.Line2));

			Element City = doc.createElement("City");
			Address.appendChild(City);
			City.appendChild(doc.createTextNode(testData.City));

			Element State = doc.createElement("State");
			Address.appendChild(State);
			State.appendChild(doc.createTextNode(testData.State));

			Element Country = doc.createElement("Country");
			Address.appendChild(Country);
			Country.appendChild(doc.createTextNode(testData.Country));

			Element ZipCode = doc.createElement("ZipCode");
			Address.appendChild(ZipCode);
			ZipCode.appendChild(doc.createTextNode(testData.Zip));

			mainRootElement.appendChild(PatientDemographics);
			// End

			// PracticeInformation
			Element PracticeInformation = doc.createElement("PracticeInformation");
			Element PracticeIdentifier = doc.createElement("PracticeIdentifier");
			PracticeInformation.appendChild(PracticeIdentifier);
			Element PracticeId = doc.createElement("PracticeId");
			PracticeId.appendChild(doc.createTextNode(testData.IntegrationPracticeID));
			PracticeIdentifier.appendChild(PracticeId);
			PracticeInformation.appendChild(PracticeIdentifier);
			mainRootElement.appendChild(PracticeInformation);
			// End
			// PracticeName
			Element PracticeName = doc.createElement("PracticeName");
			PracticeName.appendChild(doc.createTextNode(testData.EPracticeName));
			PracticeInformation.appendChild(PracticeName);

			Element Provider = doc.createElement("Provider");
			PracticeInformation.appendChild(Provider);

			Element ProviderIdentifier = doc.createElement("ProviderIdentifier");
			Provider.appendChild(ProviderIdentifier);

			Element PracticeProviderId = doc.createElement("PracticeProviderId");
			PracticeProviderId.appendChild(doc.createTextNode(testData.From));
			ProviderIdentifier.appendChild(PracticeProviderId);
					
			Element Ccd = doc.createElement("Ccd");
			mainRootElement.appendChild(Ccd);
			
			String workingDir = System.getProperty("user.dir");
			workingDir = workingDir + testData.ccdXMLPath;
			
			CcdXmlString = ExternalFileReader.readFromFile(workingDir);
					
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
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
		return output;
	}
	
	
}