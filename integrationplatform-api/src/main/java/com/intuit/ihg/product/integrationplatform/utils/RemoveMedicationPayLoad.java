// Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.integrationplatform.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;

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


public class RemoveMedicationPayLoad {
	public String output = null;


	public String getRemoveMedicationPayLoad(MedicationTestData testData) throws InterruptedException, IOException, ParseException {

		try {

			DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder icBuilder;
			icBuilder = icFactory.newDocumentBuilder();
			Document doc = icBuilder.newDocument();
			String schema = "http://schema.medfusion.com/medications/v3";
			Element root = doc.createElementNS(schema, "ns2:MedicationPatientIds");
			doc.appendChild(root);

			Element MedicationPatientId = doc.createElement("MedicationPatientId");
			root.appendChild(MedicationPatientId);

			Element PracticePatientId = doc.createElement("PracticePatientId");
			MedicationPatientId.appendChild(PracticePatientId);

			PracticePatientId.appendChild(doc.createTextNode(testData.getUserName()));

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

}
