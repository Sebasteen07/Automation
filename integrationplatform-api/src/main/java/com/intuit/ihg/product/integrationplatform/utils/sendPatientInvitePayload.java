// Copyright 2018-2021 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.integrationplatform.utils;

import java.io.StringWriter;
import java.util.ArrayList;

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

import com.intuit.ihg.product.integrationplatform.pojo.PIDCInfo;

public class sendPatientInvitePayload {
	static String output;

	public String emailType;
	public String version = "v1";
	public String firstName;
	public String lastName;
	public String email;
	public String zip;
	public String dateOfBirth;
	public String date;
	
	public  ArrayList<String> firstNameGroup = new ArrayList<String>(100);
	
	public  ArrayList<String> lastNameGroup = new ArrayList<String>(100);
	
	public  ArrayList<String> emailGroup = new ArrayList<String>(100);
	
	public  ArrayList<String> zipGroup = new ArrayList<String>(100);
	
	public  ArrayList<String> dateGroup = new ArrayList<String>(100);
	

	public  String getPIDCPayload(PIDCInfo testData,String portalVersion) {
		
		if (testData.getRestUrl().contains("v1")) {
			version = "v1";
		} else {
			version = "v2";
		}
		DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder icBuilder;
		try {
			icBuilder = icFactory.newDocumentBuilder();
			Document doc = icBuilder.newDocument();

			String schema = "http://schema.intuit.com/health/patient/v2";
			if (version.contains("v1")) {
				schema = "http://schema.intuit.com/health/patient/v1";
			}
			Thread.sleep(500);
			Element mainRootElement = doc.createElementNS(schema, "ihg:PatientBatch");
			mainRootElement.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:schemaLocation", schema + " patient.xsd");
			doc.appendChild(mainRootElement);

			// Start Creating BatchID and BatchSize Document
			Element BatchId = doc.createElement("BatchId");
			BatchId.appendChild(doc.createTextNode("BatchId"));
			mainRootElement.appendChild(BatchId);

			Element BatchSize = doc.createElement("BatchSize");
			BatchSize.appendChild(doc.createTextNode(testData.getBatchSize()));
			mainRootElement.appendChild(BatchSize);
			// End BatchID and BatchSize

			// Start Creating Sender Node
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
			Key.appendChild(doc.createTextNode("Value"));
			KeyValuePair.appendChild(Key);
			Value = doc.createElement("Value");
			Value.appendChild(doc.createTextNode("Value"));
			KeyValuePair.appendChild(Value);

			mainRootElement.appendChild(Destination);
			
			zip = testData.getZipCode();

			date = testData.getBirthDay(); //"01/01/1987";
			String dt = date.substring(0, 2);
			String month = date.substring(3, 5);
			String year = date.substring(6);
			dateOfBirth = year + "-" + month + "-" + dt + "T12:00:01";
			if(portalVersion.contains("2.0")) {
				dateOfBirth = year + "-" + dt + "-" + month + "T12:00:01";
			}
			int batchSize = Integer.parseInt(testData.getBatchSize());
						
			for(int i=0;i<batchSize;i++) {
				//Adding wait time so that time stamp will have different values-=
				Thread.sleep(5000);
				Long timestamp = System.currentTimeMillis();
				firstName = "Name" + timestamp;
				lastName = "TestPatient" + timestamp;
				email = firstName + "@yopmail.com";
				firstNameGroup.add(firstName);
				lastNameGroup.add(lastName);
				emailGroup.add(email);
				zipGroup.add(zip);
				dateGroup.add(date);
				
				Element Patient = doc.createElement("Patient");
				
				Element PatientIdentifier = doc.createElement("PatientIdentifier");
				Patient.appendChild(PatientIdentifier);
				
				Element PracticePatientId = doc.createElement("PracticePatientId");
				PatientIdentifier.appendChild(PracticePatientId);
				PracticePatientId.appendChild(doc.createTextNode(firstName));

				Element PatientAccountNumber = doc.createElement("PatientAccountNumber");
				PatientIdentifier.appendChild(PatientAccountNumber);
				PatientAccountNumber.appendChild(doc.createTextNode("PatientAccountNumber"));

				Element IntuitPatientId = doc.createElement("IntuitPatientId");
				PatientIdentifier.appendChild(IntuitPatientId);
				IntuitPatientId.appendChild(doc.createTextNode(String.valueOf("20941")));

				Element PracticeIdentifier = doc.createElement("PracticeIdentifier");
				Patient.appendChild(PracticeIdentifier);

				Element PracticeId = doc.createElement("PracticeId");
				PracticeIdentifier.appendChild(PracticeId);
				PracticeId.appendChild(doc.createTextNode("PracticeId"));

				Element IntuitPracticeId = doc.createElement("IntuitPracticeId");
				PracticeIdentifier.appendChild(IntuitPracticeId);

				IntuitPracticeId.appendChild(doc.createTextNode(testData.getPracticeId()));

				Element LastUpdateDate = doc.createElement("LastUpdateDate");
				Patient.appendChild(LastUpdateDate);
				LastUpdateDate.appendChild(doc.createTextNode("2001-12-31T12:00:00"));
				Element ResourceRequestId = doc.createElement("ResourceRequestId");
				Patient.appendChild(ResourceRequestId);
				ResourceRequestId.appendChild(doc.createTextNode("ResourceRequestId"));

				// Name
				Element Name = doc.createElement("Name");
				Patient.appendChild(Name);

				Element Prefix = doc.createElement("Prefix");
				Name.appendChild(Prefix);
				Prefix.appendChild(doc.createTextNode("Prefix"));

				Element FirstName = doc.createElement("FirstName");
				Name.appendChild(FirstName);
				FirstName.appendChild(doc.createTextNode(firstName));

				Element MiddleName = doc.createElement("MiddleName");
				Name.appendChild(MiddleName);
				MiddleName.appendChild(doc.createTextNode("MiddleName"));

				Element LastName = doc.createElement("LastName");
				Name.appendChild(LastName);
				LastName.appendChild(doc.createTextNode(lastName));

				Element Suffix = doc.createElement("Suffix");
				Name.appendChild(Suffix);
				Suffix.appendChild(doc.createTextNode("Suffix"));

				Element DateOfBirth = doc.createElement("DateOfBirth");
				Patient.appendChild(DateOfBirth);
			    DateOfBirth.appendChild(doc.createTextNode(dateOfBirth));

				Element Race = doc.createElement("Race");
				Patient.appendChild(Race);
				//Race.appendChild(doc.createTextNode(testData.getRace().get(i)));
				Race.appendChild(doc.createTextNode(testData.patientDetailList.get(i+1).getRace()));
				
				Element Ethnicity = doc.createElement("Ethnicity");
				Patient.appendChild(Ethnicity);
				//System.out.println(i+"Race "+testData.getEthnicity().get(i));
				Ethnicity.appendChild(doc.createTextNode(testData.patientDetailList.get(i+1).getEthnicity()));

				Element Gender = doc.createElement("Gender");
				Patient.appendChild(Gender);
				Gender.appendChild(doc.createTextNode(testData.patientDetailList.get(i+1).getGender()));
				
				//Enable after implementation
				if(version.contains("v2_REMOVEME")) {
					Element GenderIdentity = doc.createElement("GenderIdentity");
					Patient.appendChild(GenderIdentity);
					
					Element ValueGI = doc.createElement("Value");
					GenderIdentity.appendChild(ValueGI);
					//ValueGI.appendChild(doc.createTextNode(testData.getGenderIdentity().get(i)));
					ValueGI.appendChild(doc.createTextNode(testData.patientDetailList.get(i+1).getGenderIdentity()));
					
					Element CommentGI = doc.createElement("Comment");
					GenderIdentity.appendChild(CommentGI);
					CommentGI.appendChild(doc.createTextNode(" "));
					
					Element SexualOrientation = doc.createElement("SexualOrientation");
					Patient.appendChild(SexualOrientation);
					
					Element ValueSO = doc.createElement("Value");
					SexualOrientation.appendChild(ValueSO);
					ValueSO.appendChild(doc.createTextNode(testData.patientDetailList.get(i+1).getSexualOrientation()));
					
					Element CommentSO = doc.createElement("Comment");
					SexualOrientation.appendChild(CommentSO);
					CommentSO.appendChild(doc.createTextNode(" "));
				}
				
				Element PreferredLanguage = doc.createElement("PreferredLanguage");
				Patient.appendChild(PreferredLanguage);
				PreferredLanguage.appendChild(doc.createTextNode(testData.patientDetailList.get(i+1).getPreferredLanguage()));

				Element PreferredCommunication = doc.createElement("PreferredCommunication");
				Patient.appendChild(PreferredCommunication);
				PreferredCommunication.appendChild(doc.createTextNode(testData.patientDetailList.get(i+1).getPreferredCommunication()));

				Element SocialSecurityNumber = doc.createElement("SocialSecurityNumber");
				Patient.appendChild(SocialSecurityNumber);
				SocialSecurityNumber.appendChild(doc.createTextNode("123456789"));

				Element EmailAddress = doc.createElement("EmailAddress");
				Patient.appendChild(EmailAddress);
				EmailAddress.appendChild(doc.createTextNode(email));

				// HomeAddress
				Element HomeAddress = doc.createElement("HomeAddress");
				Patient.appendChild(HomeAddress);
				Element Line1 = doc.createElement("Line1");
				HomeAddress.appendChild(Line1);
				Line1.appendChild(doc.createTextNode("Line1"));

				Element Line2 = doc.createElement("Line2");
				HomeAddress.appendChild(Line2);
				Line2.appendChild(doc.createTextNode("Line2"));

				Element City = doc.createElement("City");
				HomeAddress.appendChild(City);
				City.appendChild(doc.createTextNode("City"));

				Element State = doc.createElement("State");
				HomeAddress.appendChild(State);
				State.appendChild(doc.createTextNode(testData.patientDetailList.get(i+1).getStateNodeValue().trim()));

				Element Country = doc.createElement("Country");
				HomeAddress.appendChild(Country);
				Country.appendChild(doc.createTextNode("Country"));

				Element ZipCode = doc.createElement("ZipCode");
				HomeAddress.appendChild(ZipCode);
				ZipCode.appendChild(doc.createTextNode(zip));

				// WorkAddress
				Element WorkAddress = doc.createElement("WorkAddress");
				Patient.appendChild(WorkAddress);
				Line1 = doc.createElement("Line1");
				WorkAddress.appendChild(Line1);
				Line1.appendChild(doc.createTextNode("Line1"));

				Line2 = doc.createElement("Line2");
				WorkAddress.appendChild(Line2);
				Line2.appendChild(doc.createTextNode("Line2"));

				City = doc.createElement("City");
				WorkAddress.appendChild(City);
				City.appendChild(doc.createTextNode("City"));

				State = doc.createElement("State");
				WorkAddress.appendChild(State);
				State.appendChild(doc.createTextNode("NC"));

				Country = doc.createElement("Country");
				WorkAddress.appendChild(Country);
				Country.appendChild(doc.createTextNode("Country"));

				ZipCode = doc.createElement("ZipCode");
				WorkAddress.appendChild(ZipCode);
				ZipCode.appendChild(doc.createTextNode(zip));

				// Patient Telephone Number
				Element HomePhone = doc.createElement("HomePhone");
				Patient.appendChild(HomePhone);
				HomePhone.appendChild(doc.createTextNode("HomePhone"));
				Element MobilePhone = doc.createElement("MobilePhone");
				Patient.appendChild(MobilePhone);
				MobilePhone.appendChild(doc.createTextNode("MobilePhone"));
				Element WorkPhone = doc.createElement("WorkPhone");
				Patient.appendChild(WorkPhone);
				WorkPhone.appendChild(doc.createTextNode("WorkPhone"));
				// MaritalStatus
				Element MaritalStatus = doc.createElement("MaritalStatus");
				Patient.appendChild(MaritalStatus);
				MaritalStatus.appendChild(doc.createTextNode("SINGLE"));

				// EmergencyContact
				Element EmergencyContact = doc.createElement("EmergencyContact");
				Patient.appendChild(EmergencyContact);

				// Name
				Name = doc.createElement("Name");
				EmergencyContact.appendChild(Name);

				Prefix = doc.createElement("Prefix");
				Name.appendChild(Prefix);
				Prefix.appendChild(doc.createTextNode("Prefix"));

				FirstName = doc.createElement("FirstName");
				Name.appendChild(FirstName);
				FirstName.appendChild(doc.createTextNode("FirstName"));

				MiddleName = doc.createElement("MiddleName");
				Name.appendChild(MiddleName);
				MiddleName.appendChild(doc.createTextNode("MiddleName"));

				LastName = doc.createElement("LastName");
				Name.appendChild(LastName);
				LastName.appendChild(doc.createTextNode("LastName"));

				Suffix = doc.createElement("Suffix");
				Name.appendChild(Suffix);
				Suffix.appendChild(doc.createTextNode("Suffix"));

				// Address
				Element Address = doc.createElement("Address");
				EmergencyContact.appendChild(Address);
				Line1 = doc.createElement("Line1");
				Address.appendChild(Line1);
				Line1.appendChild(doc.createTextNode("Line1"));

				Line2 = doc.createElement("Line2");
				Address.appendChild(Line2);
				Line2.appendChild(doc.createTextNode("Line2"));

				City = doc.createElement("City");
				Address.appendChild(City);
				City.appendChild(doc.createTextNode("City"));

				State = doc.createElement("State");
				Address.appendChild(State);
				State.appendChild(doc.createTextNode("NC"));

				Country = doc.createElement("Country");
				Address.appendChild(Country);
				Country.appendChild(doc.createTextNode("Country"));

				ZipCode = doc.createElement("ZipCode");
				Address.appendChild(ZipCode);
				ZipCode.appendChild(doc.createTextNode("27560"));

				Element Phone = doc.createElement("Phone");
				EmergencyContact.appendChild(Phone);
				Phone.appendChild(doc.createTextNode("321511-126"));

				EmailAddress = doc.createElement("EmailAddress");
				EmergencyContact.appendChild(EmailAddress);
				EmailAddress.appendChild(doc.createTextNode("EmailAddress"));

				Element Employment = doc.createElement("Employment");
				Patient.appendChild(Employment);

				Name = doc.createElement("Name");
				Employment.appendChild(Name);
				Name.appendChild(doc.createTextNode("Name"));

				// Address
				Address = doc.createElement("Address");
				Employment.appendChild(Address);
				Line1 = doc.createElement("Line1");
				Address.appendChild(Line1);
				Line1.appendChild(doc.createTextNode("Line1"));

				Line2 = doc.createElement("Line2");
				Address.appendChild(Line2);
				Line2.appendChild(doc.createTextNode("Line2"));

				City = doc.createElement("City");
				Address.appendChild(City);
				City.appendChild(doc.createTextNode("City"));

				State = doc.createElement("State");
				Address.appendChild(State);
				State.appendChild(doc.createTextNode("NC"));

				Country = doc.createElement("Country");
				Address.appendChild(Country);
				Country.appendChild(doc.createTextNode("Country"));

				ZipCode = doc.createElement("ZipCode");
				Address.appendChild(ZipCode);
				ZipCode.appendChild(doc.createTextNode("27560"));

				Phone = doc.createElement("Phone");
				Employment.appendChild(Phone);
				Phone.appendChild(doc.createTextNode("Phone"));

				Element Billing = doc.createElement("Billing");
				Patient.appendChild(Billing);

				Element AccountNumber = doc.createElement("AccountNumber");
				Billing.appendChild(AccountNumber);
				AccountNumber.appendChild(doc.createTextNode("AccountNumber"));

				Element Guarantor = doc.createElement("Guarantor");
				Billing.appendChild(Guarantor);

				Name = doc.createElement("Name");
				Guarantor.appendChild(Name);

				Prefix = doc.createElement("Prefix");
				Name.appendChild(Prefix);
				Prefix.appendChild(doc.createTextNode("Prefix"));

				FirstName = doc.createElement("FirstName");
				Name.appendChild(FirstName);
				FirstName.appendChild(doc.createTextNode("FirstName"));

				MiddleName = doc.createElement("MiddleName");
				Name.appendChild(MiddleName);
				MiddleName.appendChild(doc.createTextNode("MiddleName"));

				LastName = doc.createElement("LastName");
				Name.appendChild(LastName);
				LastName.appendChild(doc.createTextNode("LastName"));

				Suffix = doc.createElement("Suffix");
				Name.appendChild(Suffix);
				Suffix.appendChild(doc.createTextNode("Suffix"));

				DateOfBirth = doc.createElement("DateOfBirth");
				Guarantor.appendChild(DateOfBirth);
				DateOfBirth.appendChild(doc.createTextNode("2001-12-31T12:00:00"));

				SocialSecurityNumber = doc.createElement("SocialSecurityNumber");
				Guarantor.appendChild(SocialSecurityNumber);
				SocialSecurityNumber.appendChild(doc.createTextNode("123456789"));

				// Address
				Address = doc.createElement("Address");
				Guarantor.appendChild(Address);
				Line1 = doc.createElement("Line1");
				Address.appendChild(Line1);
				Line1.appendChild(doc.createTextNode("Line1"));

				Line2 = doc.createElement("Line2");
				Address.appendChild(Line2);
				Line2.appendChild(doc.createTextNode("Line2"));

				City = doc.createElement("City");
				Address.appendChild(City);
				City.appendChild(doc.createTextNode("City"));

				State = doc.createElement("State");
				Address.appendChild(State);
				State.appendChild(doc.createTextNode("DE"));

				Country = doc.createElement("Country");
				Address.appendChild(Country);
				Country.appendChild(doc.createTextNode("Country"));

				ZipCode = doc.createElement("ZipCode");
				Address.appendChild(ZipCode);
				ZipCode.appendChild(doc.createTextNode("27560"));

				Phone = doc.createElement("Phone");
				Guarantor.appendChild(Phone);
				Phone.appendChild(doc.createTextNode("Phone"));

				Element Email = doc.createElement("Email");
				Guarantor.appendChild(Email);
				Email.appendChild(doc.createTextNode("Email"));

				Element PatientRelationToGuarantor = doc.createElement("PatientRelationToGuarantor");
				Guarantor.appendChild(PatientRelationToGuarantor);
				PatientRelationToGuarantor.appendChild(doc.createTextNode("SELF"));

				// Primary
				Element PrimaryInsurance = doc.createElement("PrimaryInsurance");
				Patient.appendChild(PrimaryInsurance);

				Element CompanyName = doc.createElement("CompanyName");
				PrimaryInsurance.appendChild(CompanyName);
				CompanyName.appendChild(doc.createTextNode("Aviva"));

				Element PlanName = doc.createElement("PlanName");
				PrimaryInsurance.appendChild(PlanName);
				PlanName.appendChild(doc.createTextNode("Plan123"));

				Element GroupNumber = doc.createElement("GroupNumber");
				PrimaryInsurance.appendChild(GroupNumber);
				GroupNumber.appendChild(doc.createTextNode("23123"));

				Element PolicyNumber = doc.createElement("PolicyNumber");
				PrimaryInsurance.appendChild(PolicyNumber);
				PolicyNumber.appendChild(doc.createTextNode("PolicyNumber"));

				Element EffectiveDate = doc.createElement("EffectiveDate");
				PrimaryInsurance.appendChild(EffectiveDate);
				EffectiveDate.appendChild(doc.createTextNode("2001-12-31T12:00:00"));

				Element ExpirationDate = doc.createElement("ExpirationDate");
				PrimaryInsurance.appendChild(ExpirationDate);
				ExpirationDate.appendChild(doc.createTextNode("2001-12-31T12:00:00"));

				Element Copay = doc.createElement("Copay");
				PrimaryInsurance.appendChild(Copay);
				Copay.appendChild(doc.createTextNode("0.2"));

				Element ClaimsAddress = doc.createElement("ClaimsAddress");
				PrimaryInsurance.appendChild(ClaimsAddress);

				Line1 = doc.createElement("Line1");
				ClaimsAddress.appendChild(Line1);
				Line1.appendChild(doc.createTextNode("addLine1"));

				Line2 = doc.createElement("Line2");
				ClaimsAddress.appendChild(Line2);
				Line2.appendChild(doc.createTextNode("addLine2"));

				City = doc.createElement("City");
				ClaimsAddress.appendChild(City);
				City.appendChild(doc.createTextNode("City1"));

				State = doc.createElement("State");
				ClaimsAddress.appendChild(State);
				State.appendChild(doc.createTextNode("NC"));

				Country = doc.createElement("Country");
				ClaimsAddress.appendChild(Country);
				Country.appendChild(doc.createTextNode("US"));

				ZipCode = doc.createElement("ZipCode");
				ClaimsAddress.appendChild(ZipCode);
				ZipCode.appendChild(doc.createTextNode("27560"));

				Element ClaimsPhone = doc.createElement("ClaimsPhone");
				PrimaryInsurance.appendChild(ClaimsPhone);
				ClaimsPhone.appendChild(doc.createTextNode("ClaimPhone"));

				Element MemberId = doc.createElement("MemberId");
				PrimaryInsurance.appendChild(MemberId);
				MemberId.appendChild(doc.createTextNode("MemberId"));

				Element PayerId = doc.createElement("PayerId");
				PrimaryInsurance.appendChild(PayerId);
				PayerId.appendChild(doc.createTextNode("PayerId"));

				Element SubscriberId = doc.createElement("SubscriberId");
				PrimaryInsurance.appendChild(SubscriberId);
				SubscriberId.appendChild(doc.createTextNode("SubscriberId"));

				Element SubscriberName = doc.createElement("SubscriberName");
				PrimaryInsurance.appendChild(SubscriberName);

				Prefix = doc.createElement("Prefix");
				SubscriberName.appendChild(Prefix);
				Prefix.appendChild(doc.createTextNode("Prefix"));

				FirstName = doc.createElement("FirstName");
				SubscriberName.appendChild(FirstName);
				FirstName.appendChild(doc.createTextNode("timon"));

				MiddleName = doc.createElement("MiddleName");
				SubscriberName.appendChild(MiddleName);
				MiddleName.appendChild(doc.createTextNode("sd12"));

				LastName = doc.createElement("LastName");
				SubscriberName.appendChild(LastName);
				LastName.appendChild(doc.createTextNode("south"));

				Suffix = doc.createElement("Suffix");
				SubscriberName.appendChild(Suffix);
				Suffix.appendChild(doc.createTextNode("Suffix"));

				Element SubscriberDateOfBirth = doc.createElement("SubscriberDateOfBirth");
				PrimaryInsurance.appendChild(SubscriberDateOfBirth);
				SubscriberDateOfBirth.appendChild(doc.createTextNode("2001-12-01T12:00:00"));

				Element SubscriberSocialSecurityNumber = doc.createElement("SubscriberSocialSecurityNumber");
				PrimaryInsurance.appendChild(SubscriberSocialSecurityNumber);
				SubscriberSocialSecurityNumber.appendChild(doc.createTextNode("123456789"));

				Element PatientRelationToSubscriber = doc.createElement("PatientRelationToSubscriber");
				PrimaryInsurance.appendChild(PatientRelationToSubscriber);
				PatientRelationToSubscriber.appendChild(doc.createTextNode("SELF"));

				// Secondary Insurance
				Element SecondaryInsurance = doc.createElement("SecondaryInsurance");
				Patient.appendChild(SecondaryInsurance);

				CompanyName = doc.createElement("CompanyName");
				SecondaryInsurance.appendChild(CompanyName);
				CompanyName.appendChild(doc.createTextNode("Aviva1"));

				PlanName = doc.createElement("PlanName");
				SecondaryInsurance.appendChild(PlanName);
				PlanName.appendChild(doc.createTextNode("Plan234"));

				GroupNumber = doc.createElement("GroupNumber");
				SecondaryInsurance.appendChild(GroupNumber);
				GroupNumber.appendChild(doc.createTextNode("23143"));

				PolicyNumber = doc.createElement("PolicyNumber");
				SecondaryInsurance.appendChild(PolicyNumber);
				PolicyNumber.appendChild(doc.createTextNode("PolicyNumber1"));

				EffectiveDate = doc.createElement("EffectiveDate");
				SecondaryInsurance.appendChild(EffectiveDate);
				EffectiveDate.appendChild(doc.createTextNode("2001-12-03T12:00:00"));

				ExpirationDate = doc.createElement("ExpirationDate");
				SecondaryInsurance.appendChild(ExpirationDate);
				ExpirationDate.appendChild(doc.createTextNode("2001-12-03T12:00:00"));

				Copay = doc.createElement("Copay");
				SecondaryInsurance.appendChild(Copay);
				Copay.appendChild(doc.createTextNode("0.3"));

				ClaimsAddress = doc.createElement("ClaimsAddress");
				SecondaryInsurance.appendChild(ClaimsAddress);

				Line1 = doc.createElement("Line1");
				ClaimsAddress.appendChild(Line1);
				Line1.appendChild(doc.createTextNode("addressLine1"));

				Line2 = doc.createElement("Line2");
				ClaimsAddress.appendChild(Line2);
				Line2.appendChild(doc.createTextNode("addressLine2"));

				City = doc.createElement("City");
				ClaimsAddress.appendChild(City);
				City.appendChild(doc.createTextNode("City2"));

				State = doc.createElement("State");
				ClaimsAddress.appendChild(State);
				State.appendChild(doc.createTextNode("MA"));

				Country = doc.createElement("Country");
				ClaimsAddress.appendChild(Country);
				Country.appendChild(doc.createTextNode("US"));

				ZipCode = doc.createElement("ZipCode");
				ClaimsAddress.appendChild(ZipCode);
				ZipCode.appendChild(doc.createTextNode("27561"));

				ClaimsPhone = doc.createElement("ClaimsPhone");
				SecondaryInsurance.appendChild(ClaimsPhone);
				ClaimsPhone.appendChild(doc.createTextNode("ClaimsPhone"));

				MemberId = doc.createElement("MemberId");
				SecondaryInsurance.appendChild(MemberId);
				MemberId.appendChild(doc.createTextNode("MemberId"));

				PayerId = doc.createElement("PayerId");
				SecondaryInsurance.appendChild(PayerId);
				PayerId.appendChild(doc.createTextNode("PayerId"));

				SubscriberId = doc.createElement("SubscriberId");
				SecondaryInsurance.appendChild(SubscriberId);
				SubscriberId.appendChild(doc.createTextNode("SubscriberId"));

				SubscriberName = doc.createElement("SubscriberName");
				SecondaryInsurance.appendChild(SubscriberName);

				Prefix = doc.createElement("Prefix");
				SubscriberName.appendChild(Prefix);
				Prefix.appendChild(doc.createTextNode("Pefix"));

				FirstName = doc.createElement("FirstName");
				SubscriberName.appendChild(FirstName);
				FirstName.appendChild(doc.createTextNode("FirstName"));

				MiddleName = doc.createElement("MiddleName");
				SubscriberName.appendChild(MiddleName);
				MiddleName.appendChild(doc.createTextNode("MiddleName"));

				LastName = doc.createElement("LastName");
				SubscriberName.appendChild(LastName);
				LastName.appendChild(doc.createTextNode("FirstName"));

				Suffix = doc.createElement("Suffix");
				SubscriberName.appendChild(Suffix);
				Suffix.appendChild(doc.createTextNode("Suffix"));

				SubscriberDateOfBirth = doc.createElement("SubscriberDateOfBirth");
				SecondaryInsurance.appendChild(SubscriberDateOfBirth);
				SubscriberDateOfBirth.appendChild(doc.createTextNode("2001-12-01T12:00:00"));

				SubscriberSocialSecurityNumber = doc.createElement("SubscriberSocialSecurityNumber");
				SecondaryInsurance.appendChild(SubscriberSocialSecurityNumber);
				SubscriberSocialSecurityNumber.appendChild(doc.createTextNode("123456790"));

				PatientRelationToSubscriber = doc.createElement("PatientRelationToSubscriber");
				SecondaryInsurance.appendChild(PatientRelationToSubscriber);
				PatientRelationToSubscriber.appendChild(doc.createTextNode("SELF"));

				// Tertiary Insurance
				Element TertiaryInsurance = doc.createElement("TertiaryInsurance");
				Patient.appendChild(TertiaryInsurance);

				CompanyName = doc.createElement("CompanyName");
				TertiaryInsurance.appendChild(CompanyName);
				CompanyName.appendChild(doc.createTextNode("Aviva3"));

				PlanName = doc.createElement("PlanName");
				TertiaryInsurance.appendChild(PlanName);
				PlanName.appendChild(doc.createTextNode("Plan345"));

				GroupNumber = doc.createElement("GroupNumber");
				TertiaryInsurance.appendChild(GroupNumber);
				GroupNumber.appendChild(doc.createTextNode("544212"));

				PolicyNumber = doc.createElement("PolicyNumber");
				TertiaryInsurance.appendChild(PolicyNumber);
				PolicyNumber.appendChild(doc.createTextNode("PolicyNumber2"));

				EffectiveDate = doc.createElement("EffectiveDate");
				TertiaryInsurance.appendChild(EffectiveDate);
				EffectiveDate.appendChild(doc.createTextNode("2001-12-31T12:00:00"));

				ExpirationDate = doc.createElement("ExpirationDate");
				TertiaryInsurance.appendChild(ExpirationDate);
				ExpirationDate.appendChild(doc.createTextNode("2001-12-31T12:00:00"));

				Copay = doc.createElement("Copay");
				TertiaryInsurance.appendChild(Copay);
				Copay.appendChild(doc.createTextNode("0.5"));

				ClaimsAddress = doc.createElement("ClaimsAddress");
				TertiaryInsurance.appendChild(ClaimsAddress);

				Line1 = doc.createElement("Line1");
				ClaimsAddress.appendChild(Line1);
				Line1.appendChild(doc.createTextNode("addssLine1"));

				Line2 = doc.createElement("Line2");
				ClaimsAddress.appendChild(Line2);
				Line2.appendChild(doc.createTextNode("addssLine2"));

				City = doc.createElement("City");
				ClaimsAddress.appendChild(City);
				City.appendChild(doc.createTextNode("City3"));

				State = doc.createElement("State");
				ClaimsAddress.appendChild(State);
				State.appendChild(doc.createTextNode("DE"));

				Country = doc.createElement("Country");
				ClaimsAddress.appendChild(Country);
				Country.appendChild(doc.createTextNode("US"));

				ZipCode = doc.createElement("ZipCode");
				ClaimsAddress.appendChild(ZipCode);
				ZipCode.appendChild(doc.createTextNode("27562"));

				ClaimsPhone = doc.createElement("ClaimsPhone");
				TertiaryInsurance.appendChild(ClaimsPhone);
				ClaimsPhone.appendChild(doc.createTextNode("1234567892"));

				MemberId = doc.createElement("MemberId");
				TertiaryInsurance.appendChild(MemberId);
				MemberId.appendChild(doc.createTextNode("MemberId"));

				PayerId = doc.createElement("PayerId");
				TertiaryInsurance.appendChild(PayerId);
				PayerId.appendChild(doc.createTextNode("PayerId"));

				SubscriberId = doc.createElement("SubscriberId");
				TertiaryInsurance.appendChild(SubscriberId);
				SubscriberId.appendChild(doc.createTextNode("SubscriberId"));

				SubscriberName = doc.createElement("SubscriberName");
				TertiaryInsurance.appendChild(SubscriberName);

				Prefix = doc.createElement("Prefix");
				SubscriberName.appendChild(Prefix);
				Prefix.appendChild(doc.createTextNode("Suffix"));

				FirstName = doc.createElement("FirstName");
				SubscriberName.appendChild(FirstName);
				FirstName.appendChild(doc.createTextNode("Tim3"));

				MiddleName = doc.createElement("MiddleName");
				SubscriberName.appendChild(MiddleName);
				MiddleName.appendChild(doc.createTextNode("M"));

				LastName = doc.createElement("LastName");
				SubscriberName.appendChild(LastName);
				LastName.appendChild(doc.createTextNode("Southee3"));

				Suffix = doc.createElement("Suffix");
				SubscriberName.appendChild(Suffix);
				Suffix.appendChild(doc.createTextNode("Suffix"));

				SubscriberDateOfBirth = doc.createElement("SubscriberDateOfBirth");
				TertiaryInsurance.appendChild(SubscriberDateOfBirth);
				SubscriberDateOfBirth.appendChild(doc.createTextNode("2001-12-01T12:00:00"));

				SubscriberSocialSecurityNumber = doc.createElement("SubscriberSocialSecurityNumber");
				TertiaryInsurance.appendChild(SubscriberSocialSecurityNumber);
				SubscriberSocialSecurityNumber.appendChild(doc.createTextNode("123465789"));

				PatientRelationToSubscriber = doc.createElement("PatientRelationToSubscriber");
				TertiaryInsurance.appendChild(PatientRelationToSubscriber);
				PatientRelationToSubscriber.appendChild(doc.createTextNode("SELF"));

				mainRootElement.appendChild(Patient);
			}

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