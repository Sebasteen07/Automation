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

import org.apache.commons.lang.RandomStringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


public class MedicationPayLoad {
	public String output = null;
	public String genericName = "HYDROCORTISONE SOD SUCCINATE";
	public String doseUnit = "MG";
	public String rxNormCode = "208947";
	public String dosage = "5";
	public String ndcCode = "187065142";
	
	public String getMedicationPayLoad(MedicationTestData testData, int batchSize, String productName, String MFPatientID, String status, String MedicationID)
			throws InterruptedException, IOException, ParseException {

		try {
			
			DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder icBuilder;
			icBuilder = icFactory.newDocumentBuilder();
			Document doc = icBuilder.newDocument();
			String schema = "http://schema.medfusion.com/medications/v3";
			Element mainRootElement = doc.createElementNS(schema, "ns2:Medications");
			doc.appendChild(mainRootElement);

			for (int i = 0; i < batchSize; i++) {
				Element Medication = doc.createElement("Medication");
				Medication.setAttribute("id", MedicationID);

				Node GenericName = doc.createElement("GenericName");
				GenericName.appendChild(doc.createTextNode(genericName));
				Medication.appendChild(GenericName);

				Node ProductName = doc.createElement("ProductName");
				ProductName.appendChild(doc.createTextNode(productName));
				Medication.appendChild(ProductName);

				Node Status = doc.createElement("Status");
				Status.appendChild(doc.createTextNode(status));
				Medication.appendChild(Status);

				Node Dosage = doc.createElement("Dosage");
				Dosage.appendChild(doc.createTextNode(dosage));
				Medication.appendChild(Dosage);

				Node DoseUnit = doc.createElement("DoseUnit");
				DoseUnit.appendChild(doc.createTextNode(doseUnit));
				Medication.appendChild(DoseUnit);


				Node RXNormCode = doc.createElement("RXNormCode");
				RXNormCode.appendChild(doc.createTextNode(rxNormCode));
				Medication.appendChild(RXNormCode);

				Node NDCCode = doc.createElement("NDCCode");
				NDCCode.appendChild(doc.createTextNode(ndcCode));
				Medication.appendChild(NDCCode);

				Node PatientDemographics = doc.createElement("PatientDemographics");
				Node PatientIdentifier = doc.createElement("PatientIdentifier");
				
				Node PracticePatientId = doc.createElement("PracticePatientId");
				PracticePatientId.appendChild(doc.createTextNode(testData.getUserName()));
				PatientIdentifier.appendChild(PracticePatientId);
				if (!testData.getMFPatientID().isEmpty()) {
					Node MedfusionMemberId = doc.createElement("MedfusionMemberId");
					MedfusionMemberId.appendChild(doc.createTextNode(MFPatientID));
					PatientIdentifier.appendChild(MedfusionMemberId);
				}
				PatientDemographics.appendChild(PatientIdentifier);

				Node Name = doc.createElement("Name");
				Node FirstName = doc.createElement("FirstName");
				FirstName.appendChild(doc.createTextNode(testData.getFirstName()));
				Name.appendChild(FirstName);

				Node LastName = doc.createElement("LastName");
				LastName.appendChild(doc.createTextNode(testData.getLastName()));
				Name.appendChild(LastName);
				PatientDemographics.appendChild(Name);
				Medication.appendChild(PatientDemographics);

				mainRootElement.appendChild(Medication);
			}
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

	public static String randomNumbers(int number) {
		String randomNumbers;
		randomNumbers = RandomStringUtils.random(number, false, true);
		randomNumbers = randomNumbers.replaceFirst ("^0*", "");
		return randomNumbers;
	}
}
