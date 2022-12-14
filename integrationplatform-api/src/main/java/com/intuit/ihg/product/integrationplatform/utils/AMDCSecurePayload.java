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

import com.intuit.ihg.product.integrationplatform.pojo.AMDCInfo;

public class AMDCSecurePayload {
	static String output;
	public static String messageID = "";
	public static String messageIdentifier = "";


	public static String getAMDCPayload(AMDCInfo testData) throws InterruptedException, IOException {
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
			From.appendChild(doc.createTextNode(testData.getFrom()));
			SecureMessage.appendChild(From);
			// To
			Element To = doc.createElement("To");
			To.appendChild(doc.createTextNode(testData.getPatientExternalId()));
			SecureMessage.appendChild(To);
			// Subject
			long timestamp = System.currentTimeMillis();
			messageIdentifier = "Test " + timestamp;
			Element Subject = doc.createElement("Subject");
			Subject.appendChild(doc.createTextNode(messageIdentifier));
			SecureMessage.appendChild(Subject);

			// AllowReply
			Element AllowReply = doc.createElement("AllowReply");
			AllowReply.appendChild(doc.createTextNode("true"));
			SecureMessage.appendChild(AllowReply);
			// Message
			Element Message = doc.createElement("Message");
			Message.appendChild(doc.createTextNode("This is test msg generated from sendSecureMessage Automation API"));
			SecureMessage.appendChild(Message);

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			DOMSource source = new DOMSource(doc);

			StringWriter writer = new StringWriter();
			transformer.transform(source, new StreamResult(writer));
			output = writer.toString();

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
			System.out.println("ParserConfigurationException, Unable to Parse");
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
			System.out.println("TransformerException, Unable to Transform the DOM");
		}
		return output;
	}
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}
}