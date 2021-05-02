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

public class AttachmentPayload {
	static String output;
	

	public static String getAttachmentPayload(Attachment testData,AMDC aMDCtestData, String externalAttachmentID) throws InterruptedException, IOException {
		try {
			DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder icBuilder;
			icBuilder = icFactory.newDocumentBuilder();
			Document doc = icBuilder.newDocument();
			String schema = "http://schema.medfusion.com/health/attachmentrequest/v3";
			Thread.sleep(500);

			Element mainRootElement = doc.createElementNS(schema, "p:AttachmentRequest");
			mainRootElement.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:schemaLocation", schema + " AttachmentRequest.xsd");
			doc.appendChild(mainRootElement);
			
			// ExternalPatientId
			Element ExternalPatientId = doc.createElement("ExternalPatientId");
			ExternalPatientId.setTextContent(testData.PatientExternalId);
			mainRootElement.appendChild(ExternalPatientId);

			// ExternalAttachmentID
		
			 
			Element ExternalAttachmentID = doc.createElement("ExternalAttachmentID");
			ExternalAttachmentID.setTextContent(externalAttachmentID);
			mainRootElement.appendChild(ExternalAttachmentID);
			
			// AttachmentType
			Element AttachmentType = doc.createElement("AttachmentType");
			AttachmentType.setTextContent("image/pdf");
			mainRootElement.appendChild(AttachmentType);
			
			// AttachmentType
			Element AttachmentName = doc.createElement("AttachmentName");
			String attachmentName = "TestResults_"+externalAttachmentID+".pdf";
			AttachmentName.setTextContent(attachmentName);
			mainRootElement.appendChild(AttachmentName);
		
			// AttachmentBytes
			Element AttachmentBytes = doc.createElement("AttachmentBytes");
			AttachmentBytes.setTextContent(ExternalFileReader.readFromFile(aMDCtestData.attachmentBody));
			mainRootElement.appendChild(AttachmentBytes);
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
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
}