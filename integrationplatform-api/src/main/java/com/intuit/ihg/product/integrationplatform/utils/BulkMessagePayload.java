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

public class BulkMessagePayload {
	static String output;
	public static String messageId;
	public static String subject;

	public static Boolean checkWithPrevioudBulkMessageID = false;
	public static int messageIdCounter = 0;

	public static String getBulkMessagePayload(BulkAdmin testData) throws InterruptedException, IOException {
		
		try{
			DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder icBuilder;
			icBuilder = icFactory.newDocumentBuilder();
			Document doc = icBuilder.newDocument();
			Long timestamp = System.currentTimeMillis();
			String schema = "http://schema.intuit.com/health/bulkSecureMessages/v1";
			Thread.sleep(500);

			Element mainRootElement = doc.createElementNS(schema, "p:BulkSecureMessages");
			mainRootElement.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:schemaLocation", schema + " bulkSecureMessages.xsd");
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
			// End of Destination

			String BulkMessageIdValue = "";
			//System.out.println("flag is "+checkWithPrevioudBulkMessageID);
			//System.out.println("prev id "+testData.previousBulkMessageId);
			if (checkWithPrevioudBulkMessageID) {
				messageIdCounter++;
				BulkMessageIdValue = testData.previousBulkMessageId;
			}
			else
			{
				BulkMessageIdValue = getUUID();
			}
			// BulkMessageId
			//System.out.println("BulkMessageIdValue: "+BulkMessageIdValue);
			Element BulkMessageId = doc.createElement("BulkMessageId");
			BulkMessageId.appendChild(doc.createTextNode(BulkMessageIdValue));
			mainRootElement.appendChild(BulkMessageId);
			// From
			Element From = doc.createElement("From");
			From.appendChild(doc.createTextNode(testData.From));
			mainRootElement.appendChild(From);
			// Subject
			subject = "Test " + timestamp;
			testData.Subject = subject;
			Element Subject = doc.createElement("Subject");
			Subject.appendChild(doc.createTextNode(subject));
			mainRootElement.appendChild(Subject);
			// Message
			String bulkMessage = String.format(testData.MessageBulk, timestamp);
			Element Message = doc.createElement("Message");
			Message.appendChild(doc.createTextNode(bulkMessage));
			mainRootElement.appendChild(Message);
			if (testData.AddAttachment.contains("yes")) {
				//System.out.println("adding Attachment :" + testData.AddAttachment);
			// Attachment
				for (int i = 1; i <= Integer.parseInt(testData.NumberOfAttachments); i++) {
					String workingDir = System.getProperty("user.dir");
					workingDir = workingDir + testData.AttachmentLocation + i + ".txt";
					
					String pdf = ExternalFileReader.readFromFile(workingDir);
					Thread.sleep(3000);
					Element Attachment = doc.createElement("Attachment");
					mainRootElement.appendChild(Attachment);
					if (testData.FileName == null) {
						testData.FileName = "bulk";
					}
					Element FileName = doc.createElement("FileName");
					Attachment.appendChild(FileName);
					FileName.appendChild(doc.createTextNode(testData.FileName + i + ".pdf"));
					Element Body = doc.createElement("Body");
					Attachment.appendChild(Body);
					Body.appendChild(doc.createTextNode(pdf));
					String[] catValue = testData.categoryType.split(",");
					Element Category = doc.createElement("Category");
					Category.appendChild(doc.createTextNode(catValue[0]));
					Attachment.appendChild(Category);
				}
				// End of Attachment
			}
			
			Element Patients = doc.createElement("Patients");
			mainRootElement.appendChild(Patients);
			// Patients
			for (int j = 0; j < Integer.parseInt(testData.MaxPatients); j++) {
				messageId = getUUID();
				Element Patient = doc.createElement("Patient");
				Patients.appendChild(Patient);
				Patient.setAttribute("messageId", messageId);

				Element PracticePatientId = doc.createElement("PracticePatientId");
				Patient.appendChild(PracticePatientId);
				PracticePatientId.appendChild(doc.createTextNode(testData.PatientsIDArray[j]));
				// Params
				Element Params = doc.createElement("Params");
				Patient.appendChild(Params);
				// Param
				for (int k = 1; k <= Integer.parseInt(testData.NumberOfParams); k++) {
					// add different name value pair
					Element Param = doc.createElement("Param");
					Params.appendChild(Param);
					// Name and Value Pair

					Element Name = doc.createElement("Name");
					Param.appendChild(Name);
					Name.appendChild(doc.createTextNode(testData.ParamNameArray[k - 1]));
					Element ValuePatient = doc.createElement("Value");
					Param.appendChild(ValuePatient);
					ValuePatient.appendChild(doc.createTextNode(testData.ParamValueArray[k - 1]));
				}
			}
			// End patients

		// write the content into xml file
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			// transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
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