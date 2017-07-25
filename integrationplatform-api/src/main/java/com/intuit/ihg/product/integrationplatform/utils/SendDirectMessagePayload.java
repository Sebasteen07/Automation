package com.intuit.ihg.product.integrationplatform.utils;

import java.io.StringWriter;
import java.text.ParseException;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SendDirectMessagePayload {
	public String output;
	public String partnerMessageId;
	public String getSendDirectMessagePayload(SendDirectMessage testData) throws ParseException {		
		DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder icBuilder;
	
	try {
		icBuilder = icFactory.newDocumentBuilder();
		Document doc = icBuilder.newDocument();
		String schema = "http://schema.medfusion.com/directmessage/v1";
		Element mainRootElement = doc.createElementNS(schema,"DirectMessage" );
		mainRootElement.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:schemaLocation", schema);
		doc.appendChild(mainRootElement);		
		Element DirectMessageHeader = doc.createElement("DirectMessageHeader");
		DirectMessageHeader.setAttribute("xmlns", "");
		Element Sender = doc.createElement("Sender");
		Sender.setAttribute("DeviceLocalTime", "2017-07-19T12:45:12-04:00");
		Sender.setAttribute("DeviceVersion", "1.0");
		Sender.setAttribute("DeviceName", "GE Centricity PM");
		Sender.setAttribute("VendorName", "GE Centricity PM");
		DirectMessageHeader.appendChild(Sender);
		mainRootElement.appendChild(DirectMessageHeader);
		
		Element Message = doc.createElement("Message");	
		partnerMessageId =  generateUUID();
		Message.setAttribute("partnerMessageId",partnerMessageId);
		Message.setAttribute("xmlns", "");
		mainRootElement.appendChild(Message);
		
		Node SenderAddress = doc.createElement("SenderAddress");
		Node SenderEmailAddress = doc.createElement("EmailAddress");
		SenderEmailAddress.appendChild(doc.createTextNode(testData.FromEmalID));
		SenderAddress.appendChild(SenderEmailAddress);
		Message.appendChild(SenderAddress);
		
		Node ReceiverAddress = doc.createElement("ReceiverAddress");
		Node RecieverEmailAddress = doc.createElement("EmailAddress");
		RecieverEmailAddress.appendChild(doc.createTextNode(testData.ToEmalID));
		ReceiverAddress.appendChild(RecieverEmailAddress);
		Message.appendChild(ReceiverAddress);
		
		Node ApplicationName = doc.createElement("ApplicationName");
		ApplicationName.appendChild(doc.createTextNode(testData.ApplicationName));
		Message.appendChild(ApplicationName);
		
		long timestamp = System.currentTimeMillis();
		testData.Subject = testData.Subject+timestamp;
		Node Subject = doc.createElement("Subject");
		Subject.appendChild(doc.createTextNode(testData.Subject));
		Message.appendChild(Subject);
		
		testData.MessageBody= testData.MessageBody +timestamp;
		Node MessageBody = doc.createElement("MessageBody");
		MessageBody.appendChild(doc.createTextNode(testData.MessageBody));
		Message.appendChild(MessageBody);
		
		Node PatientDemographics = doc.createElement("PatientDemographics");
		Node PatientId = doc.createElement("PatientId");
		PatientId.appendChild(doc.createTextNode("1235"));
		PatientDemographics.appendChild(PatientId);
		Message.appendChild(PatientDemographics);
		
		
		if(testData.AttachmentType!=null && !testData.AttachmentType.isEmpty() && !testData.AttachmentType.equalsIgnoreCase("none")) {
			Node Attachment = doc.createElement("Attachment");
			Node FileName = doc.createElement("FileName");
			FileName.appendChild(doc.createTextNode(testData.FileName));
			Attachment.appendChild(FileName);
			Node Body = doc.createElement("Body");
			String workingDir = System.getProperty("user.dir");
			String filepathLocation= "";
			if(testData.AttachmentType.equalsIgnoreCase("pdf")) {
				filepathLocation = testData.PDFAttachmentFileLocation;
			}
			if(testData.AttachmentType.equalsIgnoreCase("xml")) {
				filepathLocation = testData.XMLAttachmentFileLocation;
			}
			if(testData.AttachmentType.equalsIgnoreCase("png")) {
				filepathLocation = testData.PNGAttachmentFileLocation;
			}
			workingDir = workingDir + filepathLocation;
			String FileToAttach = ExternalFileReader.readFromFile(workingDir);
			
			Body.appendChild(doc.createTextNode(FileToAttach));
			Attachment.appendChild(Body);
			Message.appendChild(Attachment);		
		}
		
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(doc);
		StringWriter writer = new StringWriter();
		transformer.transform(source, new StreamResult(writer));
		output = writer.toString();
		
	} catch (Exception e) {
		e.printStackTrace();
	}
		return output;
	}
	
	public String generateUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid;
	}
}