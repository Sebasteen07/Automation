package com.intuit.ihg.product.integrationplatform.utils;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.RandomStringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;

public class PharmacyPayload {
	public String output = null;

	public String getPharmacyAddPayload(Pharmacies testData, String ExternalPharmacyId, int count) {
		try {
			DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder icBuilder;
			icBuilder = icFactory.newDocumentBuilder();
			Document doc = icBuilder.newDocument();
			String schema = "http://schema.medfusion.com/medications/v3";
			Element mainRootElement = doc.createElementNS(schema, "p:Pharmacies");
			doc.appendChild(mainRootElement);

			for (int i = 0; i < count; i++) {
				Node pharmacy = doc.createElement("Pharmacy");
			mainRootElement.appendChild(pharmacy);

			Node pharmacyName = doc.createElement("PharmacyName");
			pharmacyName.appendChild(doc.createTextNode(testData.PharmacyName));
			pharmacy.appendChild(pharmacyName);

			Node externalPharmacyId = doc.createElement("ExternalPharmacyId");
			externalPharmacyId.appendChild(doc.createTextNode(ExternalPharmacyId));
			pharmacy.appendChild(externalPharmacyId);

			Node status = doc.createElement("Status");
			status.appendChild(doc.createTextNode(testData.Status));
			pharmacy.appendChild(status);

			Node pharmacyAddress = doc.createElement("PharmacyAddress");

			Node line1 = doc.createElement("Line1");
			line1.appendChild(doc.createTextNode(testData.Line1));
			pharmacyAddress.appendChild(line1);

			Node line2 = doc.createElement("Line2");
			line2.appendChild(doc.createTextNode(testData.Line2));
			pharmacyAddress.appendChild(line2);

			Node city = doc.createElement("City");
			city.appendChild(doc.createTextNode(testData.City));
			pharmacyAddress.appendChild(city);

			Node state = doc.createElement("State");
			state.appendChild(doc.createTextNode(testData.State));
			pharmacyAddress.appendChild(state);

			Node country = doc.createElement("Country");
			country.appendChild(doc.createTextNode(testData.Country));
			pharmacyAddress.appendChild(country);

			Node zipCode = doc.createElement("ZipCode");
			zipCode.appendChild(doc.createTextNode(testData.ZipCode));
			pharmacyAddress.appendChild(zipCode);

			pharmacy.appendChild(pharmacyAddress);

			Node pharmacyPhone = doc.createElement("PharmacyPhone");
			pharmacyPhone.appendChild(doc.createTextNode(testData.PharmacyPhone));
			pharmacy.appendChild(pharmacyPhone);

			Node pharmacyFaxNumber = doc.createElement("PharmacyFaxNumber");
			pharmacyFaxNumber.appendChild(doc.createTextNode(testData.PharmacyFaxNumber));
			pharmacy.appendChild(pharmacyFaxNumber);

		}

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			transformer.transform(source, new StreamResult(writer));
			output = writer.toString();

		} catch (Exception e) {
			Log4jUtil.log(e.toString());
		}
		return output;
	}

	public String getPharmacyAddPayload(Pharmacies testData, String ExternalPharmacyId) {
		try {
			DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder icBuilder;
			icBuilder = icFactory.newDocumentBuilder();
			Document doc = icBuilder.newDocument();
			String schema = "http://schema.medfusion.com/medications/v3";
			Element mainRootElement = doc.createElementNS(schema, "p:Pharmacies");
			doc.appendChild(mainRootElement);

			Node pharmacy = doc.createElement("Pharmacy");
			mainRootElement.appendChild(pharmacy);

			Node pharmacyName = doc.createElement("PharmacyName");
			pharmacyName.appendChild(doc.createTextNode(testData.PharmacyName));
			pharmacy.appendChild(pharmacyName);

			Node externalPharmacyId = doc.createElement("ExternalPharmacyId");
			externalPharmacyId.appendChild(doc.createTextNode(ExternalPharmacyId));
			pharmacy.appendChild(externalPharmacyId);

			Node status = doc.createElement("Status");
			status.appendChild(doc.createTextNode(testData.Status));
			pharmacy.appendChild(status);

			Node pharmacyAddress = doc.createElement("PharmacyAddress");

			Node line1 = doc.createElement("Line1");
			line1.appendChild(doc.createTextNode(testData.Line1));
			pharmacyAddress.appendChild(line1);

			Node line2 = doc.createElement("Line2");
			line2.appendChild(doc.createTextNode(testData.Line2));
			pharmacyAddress.appendChild(line2);

			Node city = doc.createElement("City");
			city.appendChild(doc.createTextNode(testData.City));
			pharmacyAddress.appendChild(city);

			Node state = doc.createElement("State");
			state.appendChild(doc.createTextNode(testData.State));
			pharmacyAddress.appendChild(state);

			Node country = doc.createElement("Country");
			country.appendChild(doc.createTextNode(testData.Country));
			pharmacyAddress.appendChild(country);

			Node zipCode = doc.createElement("ZipCode");
			zipCode.appendChild(doc.createTextNode(testData.ZipCode));
			pharmacyAddress.appendChild(zipCode);

			pharmacy.appendChild(pharmacyAddress);

			Node pharmacyPhone = doc.createElement("PharmacyPhone");
			pharmacyPhone.appendChild(doc.createTextNode(testData.PharmacyPhone));
			pharmacy.appendChild(pharmacyPhone);

			Node pharmacyFaxNumber = doc.createElement("PharmacyFaxNumber");
			pharmacyFaxNumber.appendChild(doc.createTextNode(testData.PharmacyFaxNumber));
			pharmacy.appendChild(pharmacyFaxNumber);

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


	public static String randomNumbers(int number) {
		String randomNumbers;
		randomNumbers = RandomStringUtils.random(number, false, true);
		randomNumbers = randomNumbers.replaceFirst("0", "5");
		return randomNumbers;
	}
}
