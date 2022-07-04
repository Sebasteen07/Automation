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
import org.w3c.dom.Node;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;

public class AMDCPayload {
	static String output;
	public static String messageID;
	public static String messageIdentifier;


	public static String getAMDCPayload(AMDC testData) throws InterruptedException, IOException {
		try {
			DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder icBuilder;
			icBuilder = icFactory.newDocumentBuilder();
			Document doc = icBuilder.newDocument();
			String schema = "http://schema.intuit.com/health/admin/v1";
			Thread.sleep(500);

			Element mainRootElement = doc.createElementNS(schema, "p:AdministrativeMessages");
			mainRootElement.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:schemaLocation", schema + " AdministrativeMessages.xsd");
			doc.appendChild(mainRootElement);

			Element Sender = doc.createElement("Sender");
			Sender.setAttribute("deviceLocalTime", "2001-12-31T12:00:00");
			Sender.setAttribute("deviceName", "");
			Sender.setAttribute("deviceUTCTime", "2001-12-31T12:00:00");
			Sender.setAttribute("deviceVersion", "");
			Sender.setAttribute("vendorName", "");

			Node DeviceArguments = doc.createElement("DeviceArguments");
			Sender.appendChild(DeviceArguments);
			Node KeyValuePair = doc.createElement("KeyValuePair");
			DeviceArguments.appendChild(KeyValuePair);

			Node Key = doc.createElement("Key");
			Key.appendChild(doc.createTextNode("Key"));
			KeyValuePair.appendChild(Key);
			Node Value = doc.createElement("Value");
			Value.appendChild(doc.createTextNode("Value"));
			KeyValuePair.appendChild(Value);

			mainRootElement.appendChild(Sender);
			// End Sender Node

			// Start Creating Partner Node
			Element Partner = doc.createElement("Partner");
			Partner.setAttribute("deviceLocalTime", "2001-12-31T12:00:00");
			Partner.setAttribute("deviceName", "");
			Partner.setAttribute("deviceUTCTime", "2001-12-31T12:00:00");
			Partner.setAttribute("deviceVersion", "");
			Partner.setAttribute("vendorName", "");

			DeviceArguments = doc.createElement("DeviceArguments");
			Partner.appendChild(DeviceArguments);
			KeyValuePair = doc.createElement("KeyValuePair");
			DeviceArguments.appendChild(KeyValuePair);

			Key = doc.createElement("Key");
			Key.appendChild(doc.createTextNode("Key"));
			KeyValuePair.appendChild(Key);
			Value = doc.createElement("Value");
			Value.appendChild(doc.createTextNode("Value"));
			KeyValuePair.appendChild(Value);

			mainRootElement.appendChild(Partner);

			// Create Destination Node
			Element Destination = doc.createElement("Destination");
			Destination.setAttribute("deviceLocalTime", "2001-12-31T12:00:00");
			Destination.setAttribute("deviceName", "");
			Destination.setAttribute("deviceUTCTime", "2001-12-31T12:00:00");
			Destination.setAttribute("deviceVersion", "");
			Destination.setAttribute("vendorName", "");

			DeviceArguments = doc.createElement("DeviceArguments");
			Destination.appendChild(DeviceArguments);
			KeyValuePair = doc.createElement("KeyValuePair");
			DeviceArguments.appendChild(KeyValuePair);

			Key = doc.createElement("Key");
			Key.appendChild(doc.createTextNode("Key"));
			KeyValuePair.appendChild(Key);
			Value = doc.createElement("Value");
			Value.appendChild(doc.createTextNode("Value"));
			KeyValuePair.appendChild(Value);

			mainRootElement.appendChild(Destination);
			// End of Destinationn

			messageID = getUUID();
			// SecureMessage
			Element SecureMessage = doc.createElement("SecureMessage");
			SecureMessage.setAttribute("messageId", messageID);
			mainRootElement.appendChild(SecureMessage);

			// From
			Element From = doc.createElement("From");
			From.appendChild(doc.createTextNode(testData.From));
			SecureMessage.appendChild(From);
			// To
			Element To = doc.createElement("To");
			To.appendChild(doc.createTextNode(testData.PatientExternalId));
			SecureMessage.appendChild(To);
			// Subject
			long timestamp = System.currentTimeMillis();
			 messageIdentifier = "Test " + timestamp;
			Element Subject = doc.createElement("Subject");
			Subject.appendChild(doc.createTextNode(messageIdentifier));
			SecureMessage.appendChild(Subject);
			
			// AllowReply
			Element AllowReply = doc.createElement("AllowReply");
			AllowReply.appendChild(doc.createTextNode(testData.AllowReply));
			SecureMessage.appendChild(AllowReply);
			// Message
			Element Message = doc.createElement("Message");
			Message.appendChild(doc.createTextNode(testData.Message));
			SecureMessage.appendChild(Message);
			if(testData.allowAttachment.equalsIgnoreCase("true")) {
				String[] catEnum = testData.categoryType.split(",");
				int maxLength=1;
				if(!testData.allowOnce.equalsIgnoreCase("true")) {
					maxLength=catEnum.length;
				}
				for(int i=0;i<maxLength;i++) {
					//Attachment
					Element Attachment = doc.createElement("Attachment");
					//FileName
					Element FileName = doc.createElement("FileName");
					FileName.appendChild(doc.createTextNode(i+testData.fileName));
					Attachment.appendChild(FileName);
					//MimeType
					Element MimeType = doc.createElement("MimeType");
					MimeType.appendChild(doc.createTextNode(testData.mimeType));
					Attachment.appendChild(MimeType);
					//Body
					Element Body = doc.createElement("Body");
					Body.appendChild(doc.createTextNode( ExternalFileReader.readFromFile(testData.attachmentBody)));
					Attachment.appendChild(Body);
					
					Element Category = doc.createElement("Category");
					Category.appendChild(doc.createTextNode(catEnum[i]));
					Attachment.appendChild(Category);
					
					SecureMessage.appendChild(Attachment);
				}
			}
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			// transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			DOMSource source = new DOMSource(doc);

			StringWriter writer = new StringWriter();
			transformer.transform(source, new StreamResult(writer));
			output = writer.toString();
		} catch (ParserConfigurationException pce) {
			Log4jUtil.log(pce.toString());
		} catch (TransformerException tfe) {
			Log4jUtil.log(tfe.toString());
		}
		return output;
	}
	
	public static String getAMDCV3Payload(AMDC testData) throws InterruptedException, IOException {
		try {
			DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder icBuilder;
			icBuilder = icFactory.newDocumentBuilder();
			Document doc = icBuilder.newDocument();
			String schema = "http://schema.medfusion.com/health/adminmessage/v3";
			Thread.sleep(500);

			Element mainRootElement = doc.createElementNS(schema, "p:AdministrativeMessages");
			mainRootElement.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:schemaLocation", schema + " AdministrativeMessages.xsd");
			doc.appendChild(mainRootElement);

			messageID = getUUID();
			// SecureMessage
			Element SecureMessage = doc.createElement("SecureMessage");
			SecureMessage.setAttribute("messageId", messageID);
			mainRootElement.appendChild(SecureMessage);

			// From
			Element From = doc.createElement("From");
			From.appendChild(doc.createTextNode(testData.From));
			SecureMessage.appendChild(From);
			// To
			Element To = doc.createElement("To");
			To.appendChild(doc.createTextNode(testData.PatientExternalId));
			SecureMessage.appendChild(To);
			// Subject
			long timestamp = System.currentTimeMillis();
			 messageIdentifier = "Test " + timestamp;
			Element Subject = doc.createElement("Subject");
			Subject.appendChild(doc.createTextNode(messageIdentifier));
			SecureMessage.appendChild(Subject);
			
			// AllowReply
			Element AllowReply = doc.createElement("AllowReply");
			AllowReply.appendChild(doc.createTextNode(testData.AllowReply));
			SecureMessage.appendChild(AllowReply);
			// Message
			Element Message = doc.createElement("Message");
			Message.appendChild(doc.createTextNode(testData.Message));
			SecureMessage.appendChild(Message);
			if(testData.allowAttachment.equalsIgnoreCase("true")) {
				String[] catEnum = testData.categoryType.split(",");
				int maxLength=1;
				if(!testData.allowOnce.equalsIgnoreCase("true")) {
					maxLength=catEnum.length;
				}
				for(int i=0;i<maxLength;i++) {
					//Attachment
					Element Attachment = doc.createElement("Attachment");
					//FileName
					Element FileName = doc.createElement("FileName");
					FileName.appendChild(doc.createTextNode(i+testData.fileName));
					Attachment.appendChild(FileName);
					//MimeType
					Element MimeType = doc.createElement("MimeType");
					MimeType.appendChild(doc.createTextNode(testData.mimeType));
					Attachment.appendChild(MimeType);
					//Body
					Element Body = doc.createElement("Body");
					Body.appendChild(doc.createTextNode( ExternalFileReader.readFromFile(testData.attachmentBody)));
					Attachment.appendChild(Body);
					
					Element Category = doc.createElement("Category");
					Category.appendChild(doc.createTextNode(catEnum[i]));
					Attachment.appendChild(Category);
					
					SecureMessage.appendChild(Attachment);
				}
			}
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);

			StringWriter writer = new StringWriter();
			transformer.transform(source, new StreamResult(writer));
			output = writer.toString();
		}  catch (ParserConfigurationException pce) {
			Log4jUtil.log(pce.toString());
		} catch (TransformerException tfe) {
			Log4jUtil.log(tfe.toString());
		}
		return output;
	}

	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	public static String getAMDCAttachmentPayload(AMDC testData, String attachmentRefId) throws InterruptedException, IOException {
		try {
			DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder icBuilder;
			icBuilder = icFactory.newDocumentBuilder();
			Document doc = icBuilder.newDocument();
			String schema = "http://schema.medfusion.com/health/adminmessage/v3";
			Thread.sleep(500);

			Element mainRootElement = doc.createElementNS(schema, "p:AdministrativeMessages");
			mainRootElement.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:schemaLocation", schema + " AdministrativeMessages.xsd");
			doc.appendChild(mainRootElement);

			messageID = getUUID();
			// SecureMessage
			Element SecureMessage = doc.createElement("SecureMessage");
			SecureMessage.setAttribute("messageId", messageID);
			mainRootElement.appendChild(SecureMessage);

			// From
			Element From = doc.createElement("From");
			From.appendChild(doc.createTextNode(testData.From));
			SecureMessage.appendChild(From);
			// To
			Element To = doc.createElement("To");
			To.appendChild(doc.createTextNode(testData.PatientExternalId));
			SecureMessage.appendChild(To);
			// Subject
			long timestamp = System.currentTimeMillis();
			 messageIdentifier = "Test " + timestamp;
			Element Subject = doc.createElement("Subject");
			Subject.appendChild(doc.createTextNode(messageIdentifier));
			SecureMessage.appendChild(Subject);
			
			//priority
			Element Priority = doc.createElement("Priority");
			Priority.appendChild(doc.createTextNode("true"));
			SecureMessage.appendChild(Priority);
			
			// AllowReply
			Element AllowReply = doc.createElement("AllowReply");
			AllowReply.appendChild(doc.createTextNode(testData.AllowReply));
			SecureMessage.appendChild(AllowReply);
			
			//AllowAttachment
			Element AllowAttachment = doc.createElement("AllowAttachment");
			AllowAttachment.appendChild(doc.createTextNode(testData.allowAttachment));
			SecureMessage.appendChild(AllowAttachment);
			
			// Message
			Element Message = doc.createElement("Message");
			Message.appendChild(doc.createTextNode(testData.Message));
			SecureMessage.appendChild(Message);
						
			if(testData.allowAttachment.equalsIgnoreCase("true")) {
					//Attachment
					Element Attachment = doc.createElement("Attachment");
					//AttachmentRefId
					Element AttachmentRefId = doc.createElement("AttachmentRefId");
					AttachmentRefId.appendChild(doc.createTextNode(attachmentRefId));
					Attachment.appendChild(AttachmentRefId);
					
					SecureMessage.appendChild(Attachment);
				}
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);

			StringWriter writer = new StringWriter();
			transformer.transform(source, new StreamResult(writer));
			output = writer.toString();
		}  catch (ParserConfigurationException pce) {
			Log4jUtil.log(pce.toString());
		} catch (TransformerException tfe) {
			Log4jUtil.log(tfe.toString());
		}
		return output;
	}

	
	public static String getAMDCPayloadBatch(AMDC testData, int valid, int invalid) throws InterruptedException, IOException {
		try {
			DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder icBuilder;
			icBuilder = icFactory.newDocumentBuilder();
			Document doc = icBuilder.newDocument();
			String schema = "http://schema.intuit.com/health/admin/v1";
			Thread.sleep(500);

			Element mainRootElement = doc.createElementNS(schema, "p:AdministrativeMessages");
			mainRootElement.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:schemaLocation", schema + " AdministrativeMessages.xsd");
			doc.appendChild(mainRootElement);

			Element Sender = doc.createElement("Sender");
			Sender.setAttribute("deviceLocalTime", "2001-12-31T12:00:00");
			Sender.setAttribute("deviceName", "");
			Sender.setAttribute("deviceUTCTime", "2001-12-31T12:00:00");
			Sender.setAttribute("deviceVersion", "");
			Sender.setAttribute("vendorName", "");

			Node DeviceArguments = doc.createElement("DeviceArguments");
			Sender.appendChild(DeviceArguments);
			Node KeyValuePair = doc.createElement("KeyValuePair");
			DeviceArguments.appendChild(KeyValuePair);

			Node Key = doc.createElement("Key");
			Key.appendChild(doc.createTextNode("Key"));
			KeyValuePair.appendChild(Key);
			Node Value = doc.createElement("Value");
			Value.appendChild(doc.createTextNode("Value"));
			KeyValuePair.appendChild(Value);

			mainRootElement.appendChild(Sender);
			// End Sender Node

			// Start Creating Partner Node
			Element Partner = doc.createElement("Partner");
			Partner.setAttribute("deviceLocalTime", "2001-12-31T12:00:00");
			Partner.setAttribute("deviceName", "");
			Partner.setAttribute("deviceUTCTime", "2001-12-31T12:00:00");
			Partner.setAttribute("deviceVersion", "");
			Partner.setAttribute("vendorName", "");

			DeviceArguments = doc.createElement("DeviceArguments");
			Partner.appendChild(DeviceArguments);
			KeyValuePair = doc.createElement("KeyValuePair");
			DeviceArguments.appendChild(KeyValuePair);

			Key = doc.createElement("Key");
			Key.appendChild(doc.createTextNode("Key"));
			KeyValuePair.appendChild(Key);
			Value = doc.createElement("Value");
			Value.appendChild(doc.createTextNode("Value"));
			KeyValuePair.appendChild(Value);

			mainRootElement.appendChild(Partner);

			// Create Destination Node
			Element Destination = doc.createElement("Destination");
			Destination.setAttribute("deviceLocalTime", "2001-12-31T12:00:00");
			Destination.setAttribute("deviceName", "");
			Destination.setAttribute("deviceUTCTime", "2001-12-31T12:00:00");
			Destination.setAttribute("deviceVersion", "");
			Destination.setAttribute("vendorName", "");

			DeviceArguments = doc.createElement("DeviceArguments");
			Destination.appendChild(DeviceArguments);
			KeyValuePair = doc.createElement("KeyValuePair");
			DeviceArguments.appendChild(KeyValuePair);

			Key = doc.createElement("Key");
			Key.appendChild(doc.createTextNode("Key"));
			KeyValuePair.appendChild(Key);
			Value = doc.createElement("Value");
			Value.appendChild(doc.createTextNode("Value"));
			KeyValuePair.appendChild(Value);

			mainRootElement.appendChild(Destination);
			// End of Destinationn

			for (int i = 0; i < valid / 2; i++) {

				messageID = getUUID();
			// SecureMessage
			Element SecureMessage = doc.createElement("SecureMessage");
			SecureMessage.setAttribute("messageId", messageID);
			mainRootElement.appendChild(SecureMessage);

			// From
			Element From = doc.createElement("From");
			From.appendChild(doc.createTextNode(testData.From));
			SecureMessage.appendChild(From);
			// To
			Element To = doc.createElement("To");

			To.appendChild(doc.createTextNode(testData.PatientExternalId));
			SecureMessage.appendChild(To);
			// Subject
			long timestamp = System.currentTimeMillis();
			messageIdentifier = "Test " + timestamp;
			Element Subject = doc.createElement("Subject");
			Subject.appendChild(doc.createTextNode(messageIdentifier));
			SecureMessage.appendChild(Subject);

			// AllowReply
			Element AllowReply = doc.createElement("AllowReply");
			AllowReply.appendChild(doc.createTextNode(testData.AllowReply));
			SecureMessage.appendChild(AllowReply);
			// Message
			Element Message = doc.createElement("Message");
			Message.appendChild(doc.createTextNode(testData.Message + "valid1+" + i));
			SecureMessage.appendChild(Message);
			}

			for (int i = 0; i < valid / 2; i++) {
				messageID = getUUID();
				// SecureMessage
				Element SecureMessage = doc.createElement("SecureMessage");
				SecureMessage.setAttribute("messageId", messageID);
				mainRootElement.appendChild(SecureMessage);

				// From
				Element From = doc.createElement("From");
				From.appendChild(doc.createTextNode(testData.From));
				SecureMessage.appendChild(From);
				// To
				Element To = doc.createElement("To");

				To.appendChild(doc.createTextNode(testData.batchUsername1));
				SecureMessage.appendChild(To);
				// Subject
				long timestamp = System.currentTimeMillis();
				messageIdentifier = "Test " + timestamp;
				Element Subject = doc.createElement("Subject");
				Subject.appendChild(doc.createTextNode(messageIdentifier));
				SecureMessage.appendChild(Subject);

				// AllowReply
				Element AllowReply = doc.createElement("AllowReply");
				AllowReply.appendChild(doc.createTextNode(testData.AllowReply));
				SecureMessage.appendChild(AllowReply);
				// Message
				Element Message = doc.createElement("Message");
				Message.appendChild(doc.createTextNode(testData.Message + "valid2+" + i));
				SecureMessage.appendChild(Message);
			}

			for (int i = 0; i < invalid; i++) {
				messageID = getUUID();
				// SecureMessage
				Element SecureMessage = doc.createElement("SecureMessage");
				SecureMessage.setAttribute("messageId", messageID);
				mainRootElement.appendChild(SecureMessage);

				// From
				Element From = doc.createElement("From");
				From.appendChild(doc.createTextNode(testData.From));
				SecureMessage.appendChild(From);
				// To
				Element To = doc.createElement("To");

				To.appendChild(doc.createTextNode("invalidPatientId"));
				SecureMessage.appendChild(To);
				// Subject
				long timestamp = System.currentTimeMillis();
				messageIdentifier = "Test " + timestamp;
				Element Subject = doc.createElement("Subject");
				Subject.appendChild(doc.createTextNode(messageIdentifier));
				SecureMessage.appendChild(Subject);

				// AllowReply
				Element AllowReply = doc.createElement("AllowReply");
				AllowReply.appendChild(doc.createTextNode(testData.AllowReply));
				SecureMessage.appendChild(AllowReply);
				// Message
				Element Message = doc.createElement("Message");
				Message.appendChild(doc.createTextNode(testData.Message + "invalid+" + i));
				SecureMessage.appendChild(Message);
			}

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);

			StringWriter writer = new StringWriter();
			transformer.transform(source, new StreamResult(writer));
			output = writer.toString();
		}  catch (ParserConfigurationException pce) {
			Log4jUtil.log(pce.toString());
		} catch (TransformerException tfe) {
			Log4jUtil.log(tfe.toString());
		}
		return output;
	}

}