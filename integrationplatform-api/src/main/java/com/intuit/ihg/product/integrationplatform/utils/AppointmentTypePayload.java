package com.intuit.ihg.product.integrationplatform.utils;

import java.io.StringWriter;

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

public class AppointmentTypePayload {
	static String output = null;

	public static String getAppointmentTypePayload(AppointmentData testData) {
		try {
			DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder icBuilder;
			icBuilder = icFactory.newDocumentBuilder();
			Document doc = icBuilder.newDocument();
			String schema = "http://schema.medfusion.com/appointmenttypes/v3";
			Element mainRootElement = doc.createElementNS(schema, "ns2:AppointmentTypes");
			doc.appendChild(mainRootElement);
			Node appointmentType = doc.createElement("AppointmentType");
			mainRootElement.appendChild(appointmentType);

			Node appointmentTypeName = doc.createElement("AppointmentTypeName");
			appointmentTypeName.appendChild(doc.createTextNode(testData.AppointmentTypeName));
			appointmentType.appendChild(appointmentTypeName);

			Node appointmentTypeID = doc.createElement("AppointmentTypeID");
			appointmentTypeID.appendChild(doc.createTextNode(testData.AppointmentTypeID));
			appointmentType.appendChild(appointmentTypeID);

			Node appointmentCategoryName = doc.createElement("AppointmentCategoryName");
			appointmentCategoryName.appendChild(doc.createTextNode(testData.AppointmentCategoryName));
			appointmentType.appendChild(appointmentCategoryName);

			Node appointmentCategoryID = doc.createElement("AppointmentCategoryID");
			appointmentCategoryID.appendChild(doc.createTextNode(testData.AppointmentCategoryID));
			appointmentType.appendChild(appointmentCategoryID);

			Node comment = doc.createElement("Comment");
			comment.appendChild(doc.createTextNode(testData.Comment));
			appointmentType.appendChild(comment);

			Node activeFlag = doc.createElement("ActiveFlag");
			activeFlag.appendChild(doc.createTextNode(testData.ActiveFlag));
			appointmentType.appendChild(activeFlag);

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

}
