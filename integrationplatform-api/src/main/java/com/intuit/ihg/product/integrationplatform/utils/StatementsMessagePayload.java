package com.intuit.ihg.product.integrationplatform.utils;

import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

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

public class StatementsMessagePayload {
	public String output;
	public String amountDue;
	public String patientID;	
	public String localDateTime;
	public String localTime; 
	public String formattedUTCTime;
	public String billAccountNumber;
	public String paymentPortalDueDate;

	public String getStatementsMessagePayload(StatementEventData testData) throws ParseException {
	
		DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder icBuilder;
	
		long timestamp = (System.currentTimeMillis());
		timestamp = timestamp-86400000;
		SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date resultdate = new Date(timestamp);

		formattedUTCTime = utcFormat.format(resultdate);

		Date localDate = utcFormat.parse(formattedUTCTime);
		DateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		localFormat.setTimeZone(TimeZone.getDefault());
		localTime = localFormat.format(localDate);
	
		portalDateFormat(localDate);
		Date paymentDueDate = utcFormat.parse(testData.PaymentDueDate);
		paymentPortalDueDate = portalDateFormat(paymentDueDate);

		amountDue = randomNumbers(3);
		amountDue = amountDue.replaceFirst("^0*", "");
		billAccountNumber = randomNumbers(2);
		if (testData.PatientID == "" || testData.PatientID.isEmpty()) {
		patientID = randomNumbers(11);
		patientID = patientID.replaceFirst ("^0*", "");
		testData.PatientID =patientID; 
	}
	else
	{
		patientID = testData.PatientID;
	}
	
	try {
		icBuilder = icFactory.newDocumentBuilder();
		Document doc = icBuilder.newDocument();
		String schema = "http://schema.medfusion.com/statements/v1";
		Thread.sleep(500);
		Element mainRootElement = doc.createElementNS(schema,"ns2:Statements" );
		
		doc.appendChild(mainRootElement);
		
		Element StatementMessageHeaders = doc.createElement("StatementMessageHeaders");
		Element Sender = doc.createElement("Sender");
		Sender.setAttribute("DeviceLocalTime", "2016-07-19T12:45:12-04:00");
		Sender.setAttribute("DeviceVersion", "8.2");
		Sender.setAttribute("DeviceName", "VertexDR");
		Sender.setAttribute("VendorName", "Meridian Medical Management");
		StatementMessageHeaders.appendChild(Sender);
		mainRootElement.appendChild(StatementMessageHeaders);
		
		Element Statement = doc.createElement("Statement");	
		Statement.setAttribute("id", randomNumbers(11));
		mainRootElement.appendChild(Statement);
		
		Node FileId = doc.createElement("FileId");
		FileId.appendChild(doc.createTextNode("1"));
		Statement.appendChild(FileId);
		
		Node PracticeManagementStatementId = doc.createElement("PracticeManagementStatementId");
		PracticeManagementStatementId.appendChild(doc.createTextNode(randomNumbers(11)));
		Statement.appendChild(PracticeManagementStatementId);
		
		Node StatementFormat = doc.createElement("StatementFormat");
		StatementFormat.appendChild(doc.createTextNode(testData.StatementFormat));
		Statement.appendChild(StatementFormat);
		
		Node StatementBillingAccountNumber = doc.createElement("StatementBillingAccountNumber");
		StatementBillingAccountNumber.appendChild(doc.createTextNode(billAccountNumber));
		Statement.appendChild(StatementBillingAccountNumber);
		
		Node PracticePatientId = doc.createElement("PracticePatientId");
		PracticePatientId.appendChild(doc.createTextNode(patientID));
		Statement.appendChild(PracticePatientId);
	
		if(!(testData.MFPatientID=="") || !testData.MFPatientID.isEmpty()){
			Node MedfusionMemberId = doc.createElement("MedfusionMemberId");
			MedfusionMemberId.appendChild(doc.createTextNode(testData.MFPatientID));
			Statement.appendChild(MedfusionMemberId);	
		}
		
		Node GuarantorInformation = doc.createElement("GuarantorInformation");
		
		Node GuarantorID = doc.createElement("GuarantorID");
		GuarantorID.appendChild(doc.createTextNode("9999200000259700"));
		GuarantorInformation.appendChild(GuarantorID);
		
		Node GuarantorFirstName = doc.createElement("GuarantorFirstName");
		GuarantorFirstName.appendChild(doc.createTextNode("BETTY"));
		GuarantorInformation.appendChild(GuarantorFirstName);
		Node GuarantorLastName = doc.createElement("GuarantorLastName");
		GuarantorLastName.appendChild(doc.createTextNode("YOUNG"));
		GuarantorInformation.appendChild(GuarantorLastName);
		Node GuarantorRelationshiptoPatient = doc.createElement("GuarantorRelationshiptoPatient");
		GuarantorRelationshiptoPatient.appendChild(doc.createTextNode("Parent"));
		GuarantorInformation.appendChild(GuarantorRelationshiptoPatient);
		
		Node GuarantorAddress = doc.createElement("GuarantorAddress");
		Node Address1 = doc.createElement("Address1");
		Address1.appendChild(doc.createTextNode("342 GREEN MANOR TERRACE"));
		GuarantorAddress.appendChild(Address1);
		Node Address2 = doc.createElement("Address2");
		Address2.appendChild(doc.createTextNode("Unit 1111"));
		GuarantorAddress.appendChild(Address2);
		Node City = doc.createElement("City");
		City.appendChild(doc.createTextNode("WINDSOR"));
		GuarantorAddress.appendChild(City);
		
		Node State = doc.createElement("State");
		State.appendChild(doc.createTextNode("CT"));
		GuarantorAddress.appendChild(State);
		Node Country = doc.createElement("Country");
		GuarantorAddress.appendChild(Country);
		Node ZipCode = doc.createElement("ZipCode");
		ZipCode.appendChild(doc.createTextNode("06095"));
		GuarantorAddress.appendChild(ZipCode);
		GuarantorInformation.appendChild(GuarantorAddress);	
		
		Statement.appendChild(GuarantorInformation);
		
		Node StatementDate = doc.createElement("StatementDate");
		StatementDate.appendChild(doc.createTextNode(formattedUTCTime));
		Statement.appendChild(StatementDate);
		
		Node PaymentDueDate = doc.createElement("PaymentDueDate");
		PaymentDueDate.appendChild(doc.createTextNode(testData.PaymentDueDate));
		Statement.appendChild(PaymentDueDate);
		
		Node NewCharges = doc.createElement("NewCharges");
		NewCharges.appendChild(doc.createTextNode(amountDue));
		Statement.appendChild(NewCharges);
		
		Node TotalCharges = doc.createElement("TotalCharges");
		TotalCharges.appendChild(doc.createTextNode(testData.TotalCharges));
		Statement.appendChild(TotalCharges);
		
		Node BalancesForward = doc.createElement("BalancesForward");
		Node BalanceForward = doc.createElement("BalanceForward");
		Node BalanceForwardType = doc.createElement("BalanceForwardType");
		BalanceForwardType.appendChild(doc.createTextNode("Insurance"));
		BalanceForward.appendChild(BalanceForwardType);
		Node BalanceForwardAmount = doc.createElement("BalanceForwardAmount");
		BalanceForwardAmount.appendChild(doc.createTextNode("0.00"));
		BalanceForward.appendChild(BalanceForwardAmount);
		BalancesForward.appendChild(BalanceForward);
		
		Statement.appendChild(BalancesForward);
		
		Node OutstandingBalance = doc.createElement("OutstandingBalance");
		OutstandingBalance.appendChild(doc.createTextNode("0.00"));
		Statement.appendChild(OutstandingBalance);
		
		Node AgingBalances = doc.createElement("AgingBalances");
		for(int i=0;i<12;i++) {
		
		Node AgingBalance = doc.createElement("AgingBalance");
		Node AgingBalanceType = doc.createElement("AgingBalanceType");
		AgingBalanceType.appendChild(doc.createTextNode("Insurance"));
		AgingBalance.appendChild(AgingBalanceType);
		
		Node AgingBalanceRange = doc.createElement("AgingBalanceRange");
		AgingBalanceRange.appendChild(doc.createTextNode("0-30"));
		AgingBalance.appendChild(AgingBalanceRange);
		
		Node AgingBalanceAmount = doc.createElement("AgingBalanceAmount");
		AgingBalanceAmount.appendChild(doc.createTextNode("100.00"));
		AgingBalance.appendChild(AgingBalanceAmount);
		
		AgingBalances.appendChild(AgingBalance);
		}
		
		Statement.appendChild(AgingBalances);
		
		Node InsurancePayments = doc.createElement("InsurancePayments");
		InsurancePayments.appendChild(doc.createTextNode("500.00"));
		Statement.appendChild(InsurancePayments);
		Node InsuranceAdjustments = doc.createElement("InsuranceAdjustments");
		InsuranceAdjustments.appendChild(doc.createTextNode("100.00"));
		Statement.appendChild(InsuranceAdjustments);
		Node InsuranceContributions = doc.createElement("InsuranceContributions");
		InsuranceContributions.appendChild(doc.createTextNode("0.00"));
		Statement.appendChild(InsuranceContributions);
		Node InsuranceBalance = doc.createElement("InsuranceBalance");
		InsuranceBalance.appendChild(doc.createTextNode("10.00"));
		Statement.appendChild(InsuranceBalance);
		Node OtherAdjustments = doc.createElement("OtherAdjustments");
		OtherAdjustments.appendChild(doc.createTextNode("20.00"));
		Statement.appendChild(OtherAdjustments);
		Node PatientPayments = doc.createElement("PatientPayments");
		PatientPayments.appendChild(doc.createTextNode("400.00"));
		Statement.appendChild(PatientPayments);
		Node OtherBalances = doc.createElement("OtherBalances");
		OtherBalances.appendChild(doc.createTextNode("100.00"));
		Statement.appendChild(OtherBalances);
		Node PatientBalance = doc.createElement("PatientBalance");
		PatientBalance.appendChild(doc.createTextNode(amountDue));
		Statement.appendChild(PatientBalance);
		Node Encounters = doc.createElement("Encounters");
		Node Encounter = doc.createElement("Encounter");
		
		Node PatientInformation = doc.createElement("PatientInformation");
		
		Node PatientAccountNumber = doc.createElement("PatientAccountNumber");
		PatientAccountNumber.appendChild(doc.createTextNode("XXXXX"));
		PatientInformation.appendChild(PatientAccountNumber);
		Node PatientName = doc.createElement("PatientName");
		PatientName.appendChild(doc.createTextNode("YOUNG, BETTY"));
		PatientInformation.appendChild(PatientName);
		Node PatientDateofBirth = doc.createElement("PatientDateofBirth");
		PatientDateofBirth.appendChild(doc.createTextNode("1928-09-24T00:00:00"));
		PatientInformation.appendChild(PatientDateofBirth);
		
		Encounter.appendChild(PatientInformation);
		
		Node ExternalEncounterID = doc.createElement("ExternalEncounterID");
		ExternalEncounterID.appendChild(doc.createTextNode("12345ab"));
		Encounter.appendChild(ExternalEncounterID);
		
		Node EncounterDateTime = doc.createElement("EncounterDateTime");
		EncounterDateTime.appendChild(doc.createTextNode("2014-01-01T17:17:08"));
		Encounter.appendChild(EncounterDateTime);
		
		Node EncounterLocationId = doc.createElement("EncounterLocationId");
		EncounterLocationId.appendChild(doc.createTextNode("12345west"));
		Encounter.appendChild(EncounterLocationId);
		
		Node EncounterLocation = doc.createElement("EncounterLocation");
		EncounterLocation.appendChild(doc.createTextNode("West Clinic"));
		Encounter.appendChild(EncounterLocation);
		
		Node EncounterDescription = doc.createElement("EncounterDescription");
		EncounterDescription.appendChild(doc.createTextNode("Pathology Lab"));
		Encounter.appendChild(EncounterDescription);
		
		Node PracticeProviderId = doc.createElement("PracticeProviderId");
		PracticeProviderId.appendChild(doc.createTextNode("ALEE"));
		Encounter.appendChild(PracticeProviderId);
		
		Node PracticeProviderName = doc.createElement("PracticeProviderName");
		PracticeProviderName.appendChild(doc.createTextNode(testData.PracticeProviderName));
		Encounter.appendChild(PracticeProviderName);
		
		Node EncounterComments = doc.createElement("EncounterComments");
		EncounterComments.appendChild(doc.createTextNode("Labs look good!"));
		Encounter.appendChild(EncounterComments);
		
		Node EncounterChargeTotal = doc.createElement("EncounterChargeTotal");
		EncounterChargeTotal.appendChild(doc.createTextNode("1000.00"));
		Encounter.appendChild(EncounterChargeTotal);
		
		
		Node EncounterPatientChargesTotal = doc.createElement("EncounterPatientChargesTotal");
		EncounterPatientChargesTotal.appendChild(doc.createTextNode("10.00"));
		Encounter.appendChild(EncounterPatientChargesTotal);
		
		Node EncounterPatientPaymentsTotal = doc.createElement("EncounterPatientPaymentsTotal");
		EncounterPatientPaymentsTotal.appendChild(doc.createTextNode("10.00"));
		Encounter.appendChild(EncounterPatientPaymentsTotal);
		
		Node EncounterInsuranceChargesTotal = doc.createElement("EncounterInsuranceChargesTotal");
		EncounterInsuranceChargesTotal.appendChild(doc.createTextNode("990.00"));
		Encounter.appendChild(EncounterInsuranceChargesTotal);
		
		Node EncounterInsurancePaymentsTotal = doc.createElement("EncounterInsurancePaymentsTotal");
		EncounterInsurancePaymentsTotal.appendChild(doc.createTextNode("990.00"));
		Encounter.appendChild(EncounterInsurancePaymentsTotal);
		
		
		Node EncounterInsuranceAdjustmentsTotal = doc.createElement("EncounterInsuranceAdjustmentsTotal");
		EncounterInsuranceAdjustmentsTotal.appendChild(doc.createTextNode("100.00"));
		Encounter.appendChild(EncounterInsuranceAdjustmentsTotal);
		
		
		Node EncounterInsuranceBalance = doc.createElement("EncounterInsuranceBalance");
		EncounterInsuranceBalance.appendChild(doc.createTextNode("0.00"));
		Encounter.appendChild(EncounterInsuranceBalance);
		
		Node EncounterPatientBalance = doc.createElement("EncounterPatientBalance");
		EncounterPatientBalance.appendChild(doc.createTextNode("0.00"));
		Encounter.appendChild(EncounterPatientBalance);
		
		Node EncounterInsuranceContribution = doc.createElement("EncounterInsuranceContribution");
		EncounterInsuranceContribution.appendChild(doc.createTextNode("0.00"));
		Encounter.appendChild(EncounterInsuranceContribution);
		
		Node EncounterOtherAdjustmentsTotal = doc.createElement("EncounterOtherAdjustmentsTotal");
		EncounterOtherAdjustmentsTotal.appendChild(doc.createTextNode("0.00"));
		Encounter.appendChild(EncounterOtherAdjustmentsTotal);
		
		Node EncountersICDCode = doc.createElement("EncounterICDCode");
		Node EncounterICDCode = doc.createElement("EncounterICDCode");
		EncounterICDCode.appendChild(doc.createTextNode("ICD9"));
		EncountersICDCode.appendChild(EncounterICDCode);
		Encounter.appendChild(EncountersICDCode);
		
		Node EncounterCharges = doc.createElement("EncounterCharges");
		Node EncounterCharge = doc.createElement("EncounterCharge");
		
		Node ExternalEncounterChargeID = doc.createElement("ExternalEncounterChargeID");
		ExternalEncounterChargeID.appendChild(doc.createTextNode("GUID"));
		EncounterCharge.appendChild(ExternalEncounterChargeID);
		
		Node EncounterChargeDateTime = doc.createElement("EncounterChargeDateTime");
		EncounterChargeDateTime.appendChild(doc.createTextNode("2014-01-01T17:17:08"));
		EncounterCharge.appendChild(EncounterChargeDateTime);
		
		Node EncounterChargeCPTCode = doc.createElement("EncounterChargeCPTCode");
		EncounterChargeCPTCode.appendChild(doc.createTextNode("99213"));
		EncounterCharge.appendChild(EncounterChargeCPTCode);
		
		Node EncounterChargeDescription = doc.createElement("EncounterChargeDescription");
		EncounterChargeDescription.appendChild(doc.createTextNode("LDL Lab"));
		EncounterCharge.appendChild(EncounterChargeDescription);
		
		Node TotalCharge = doc.createElement("TotalCharge");
		TotalCharge.appendChild(doc.createTextNode("70.00"));
		EncounterCharge.appendChild(TotalCharge);
		
		Node PatientCharge = doc.createElement("PatientCharge");
		PatientCharge.appendChild(doc.createTextNode("70.00"));
		EncounterCharge.appendChild(PatientCharge);
		
		
		Node PatientPaymentAmount = doc.createElement("PatientPaymentAmount");
		PatientPaymentAmount.appendChild(doc.createTextNode("0.00"));
		EncounterCharge.appendChild(PatientPaymentAmount);
		
		
		Node PatientPaymentMethod = doc.createElement("PatientPaymentMethod");
		PatientPaymentMethod.appendChild(doc.createTextNode("Cash"));
		EncounterCharge.appendChild(PatientPaymentMethod);
		
		Node InsuranceCharge = doc.createElement("InsuranceCharge");
		InsuranceCharge.appendChild(doc.createTextNode("92.00"));
		EncounterCharge.appendChild(InsuranceCharge);
		
		Node InsurancePaymentAmount = doc.createElement("InsurancePaymentAmount");
		InsurancePaymentAmount.appendChild(doc.createTextNode("0.00"));
		EncounterCharge.appendChild(InsurancePaymentAmount);
		
		Node EncounterChargeInsuranceAdjustmentsTotal = doc.createElement("EncounterChargeInsuranceAdjustmentsTotal");
		EncounterChargeInsuranceAdjustmentsTotal.appendChild(doc.createTextNode("0.00"));
		EncounterCharge.appendChild(EncounterChargeInsuranceAdjustmentsTotal);
		
		Node EncounterChargeOtherAdjustmentsTotal = doc.createElement("EncounterChargeOtherAdjustmentsTotal");
		EncounterChargeOtherAdjustmentsTotal.appendChild(doc.createTextNode("0.00"));
		EncounterCharge.appendChild(EncounterChargeOtherAdjustmentsTotal);
		
		EncounterCharges.appendChild(EncounterCharge);
		Encounter.appendChild(EncounterCharges);
		
		Encounters.appendChild(Encounter);
		Statement.appendChild(Encounters);
		
		Node StatementComment = doc.createElement("StatementComment");
		StatementComment.appendChild(doc.createTextNode(testData.StatementComment));
		Statement.appendChild(StatementComment);
		
		Node DunningMessage = doc.createElement("DunningMessage");
		DunningMessage.appendChild(doc.createTextNode(testData.DunningMessage));
		Statement.appendChild(DunningMessage);
		
		/*
		Node StatementPdfDetail = doc.createElement("StatementPdfDetail");
		StatementPdfDetail.appendChild(doc.createTextNode("cnRjbGNyZW9scg=="));
		Statement.appendChild(StatementPdfDetail);
		*/
		
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(doc);
		StringWriter writer = new StringWriter();
		transformer.transform(source, new StreamResult(writer));
		output = writer.toString();
		
	} catch (Exception e) {
		e.printStackTrace();
	}
		// System.out.print(output);
		return output;
	}
	
	public static String randomNumbers(int number) {
		return RandomStringUtils.random(number, false, true);
	}
	
	public String portalDateFormat(Date localDate) {
		
		DateFormat localDateFormat = new SimpleDateFormat("MM-dd-yyyy");
		localDateFormat.setTimeZone(TimeZone.getDefault());
		localDateTime =localDateFormat.format(localDate);
		localDateTime = localDateTime.replaceAll("-", "/");
		
		return localDateTime;
	}
	
	
}