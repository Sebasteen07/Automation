// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.integrationplatform.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

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


public class BalancePayLoad {
	public String output = null;
	public String localDateTime;
	public String localTime; 
	public String formattedUTCTime;
	public String balanceAccountNumber, paymentPortalDueDate, amountDue, patientOutstandingBalance;
	
	
	public String getBalancePayLoad(StatementEventData testData, int batchSize, String patientExternalID) throws InterruptedException, IOException, ParseException {
		try {
			
			long timestamp = (System.currentTimeMillis());
			SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			Date resultdate = new Date(timestamp);

			formattedUTCTime = utcFormat.format(resultdate);

			Date localDate = utcFormat.parse(formattedUTCTime);
			DateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			localFormat.setTimeZone(TimeZone.getDefault());
			localTime = localFormat.format(localDate);
			
			amountDue = randomNumbers(3);
			balanceAccountNumber = randomNumbers(3);
			patientOutstandingBalance = randomNumbers(3);
			if (patientExternalID== "" || patientExternalID.isEmpty()) {
				patientExternalID = randomNumbers(11);
				patientExternalID = patientExternalID.replaceFirst ("^0*", "");
				testData.PatientID =patientExternalID; 
			}
			
			DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder icBuilder;
			icBuilder = icFactory.newDocumentBuilder();
			Document doc = icBuilder.newDocument();
			String schema = "http://schema.medfusion.com/statements/v1";
			Element mainRootElement = doc.createElementNS(schema, "ns2:Balances");
			doc.appendChild(mainRootElement);

			Element BalanceMessageHeaders = doc.createElement("BalanceMessageHeaders");
			Element Sender = doc.createElement("Sender");
			Sender.setAttribute("DeviceLocalTime", "2016-07-19T12:45:12-04:00");
			Sender.setAttribute("DeviceVersion", "8.2");
			Sender.setAttribute("DeviceName", "VertexDR");
			Sender.setAttribute("VendorName", "Meridian Medical Management");
			BalanceMessageHeaders.appendChild(Sender);
			mainRootElement.appendChild(BalanceMessageHeaders);

			for (int i = 0; i < batchSize; i++) {
				Element Balance = doc.createElement("Balance");
				Balance.setAttribute("id", randomNumbers(9));

				Node BalanceAccountNumber = doc.createElement("BalanceAccountNumber");
				BalanceAccountNumber.appendChild(doc.createTextNode(balanceAccountNumber));
				Balance.appendChild(BalanceAccountNumber);

				Node OutstandingPatientBalance = doc.createElement("OutstandingPatientBalance");
				OutstandingPatientBalance.appendChild(doc.createTextNode(patientOutstandingBalance));
				Balance.appendChild(OutstandingPatientBalance);

				Node OutstandingInsuranceBalance = doc.createElement("OutstandingInsuranceBalance");
				OutstandingInsuranceBalance.appendChild(doc.createTextNode(amountDue));
				Balance.appendChild(OutstandingInsuranceBalance);

				Node BalanceDate = doc.createElement("BalanceDate");
				BalanceDate.appendChild(doc.createTextNode(formattedUTCTime));
				Balance.appendChild(BalanceDate);

				Node BalanceDueDate = doc.createElement("BalanceDueDate");
				BalanceDueDate.appendChild(doc.createTextNode(formattedUTCTime));
				Balance.appendChild(BalanceDueDate);

				Node TransactionId = doc.createElement("TransactionId");
				TransactionId.appendChild(doc.createTextNode(randomNumbers(3)+"-" + randomNumbers(6) + "-55754-" + randomNumbers(5)));
				Balance.appendChild(TransactionId);

				Node PatientDemographics = doc.createElement("PatientDemographics");

				Node PatientIdentifier = doc.createElement("PatientIdentifier");
				Node PracticePatientId = doc.createElement("PracticePatientId");
				PracticePatientId.appendChild(doc.createTextNode(patientExternalID));
				PatientIdentifier.appendChild(PracticePatientId);
				if(!testData.MFPatientID.isEmpty() ) {
					Node MedfusionMemberId = doc.createElement("MedfusionMemberId");
					MedfusionMemberId.appendChild(doc.createTextNode(testData.MFPatientID));
					PatientIdentifier.appendChild(MedfusionMemberId);
				}
				PatientDemographics.appendChild(PatientIdentifier);

				Node Name = doc.createElement("Name");
				Node FirstName = doc.createElement("FirstName");
				FirstName.appendChild(doc.createTextNode("Fname"));
				Name.appendChild(FirstName);
				Node MiddleName = doc.createElement("MiddleName");
				Name.appendChild(MiddleName);
				Node LastName = doc.createElement("LastName");
				LastName.appendChild(doc.createTextNode("TestPatient01"));
				Name.appendChild(LastName);
				PatientDemographics.appendChild(Name);
				Balance.appendChild(PatientDemographics);

				mainRootElement.appendChild(Balance);
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
