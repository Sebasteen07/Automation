// Copyright 2018-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.integrationplatform.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;

import javax.xml.XMLConstants;
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
import com.medfusion.common.utils.IHGUtil;


public class CancelInvitePayLoad {
	public String output;


	public String prepareCancelInvite(String PracticePatientId) throws InterruptedException, IOException, ParseException {

	IHGUtil.PrintMethodName();
				try {
					DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder icBuilder;
					icBuilder = icFactory.newDocumentBuilder();
					Document doc = icBuilder.newDocument();
					String schema = "http://schema.medfusion.com/health/patient/v3";
					Element mainRootElement = doc.createElementNS(schema, "ExternalPatientIds");
					mainRootElement.setAttributeNS(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, "xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
					mainRootElement.setAttributeNS(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
					doc.appendChild(mainRootElement);

					Element nPracticePatientId = doc.createElement("PracticePatientId");
					nPracticePatientId.setAttribute("xmlns", "");
					mainRootElement.appendChild(nPracticePatientId);

					nPracticePatientId.appendChild(doc.createTextNode(PracticePatientId));

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
