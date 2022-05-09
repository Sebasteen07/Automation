// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.integrationplatform.utils;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.UUID;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.intuit.ihg.product.integrationplatform.pojo.PIDCInfo;

public class sendPrecheckPatientSubscriberPayloadV4 {
	static String output;

	public String emailType;
	public String version = "v4";
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

		DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder icBuilder;
		try {
			icBuilder = icFactory.newDocumentBuilder();
			Document doc = icBuilder.newDocument();

			String schema = "http://www.intuit.com/qhg/hub/schemas/Messages";
			Thread.sleep(500);
			Element mainRootElement = doc.createElementNS(schema, "Message");
			mainRootElement.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:type", "PatientDemographicsMessageType");
			mainRootElement.setAttribute("majorVer", "1");
			mainRootElement.setAttribute("minorVer", "0");
			doc.appendChild(mainRootElement);
			zip = testData.getZipCode();

			date = testData.getBirthDay(); //"01/01/1987";
			String dt = date.substring(0, 2);
			String month = date.substring(3, 5);
			String year = date.substring(6);
			dateOfBirth = year + "-" + month + "-" + dt + "T12:00:01";
			if(portalVersion.contains("2.0")) {
				dateOfBirth = year + "-" + dt + "-" + month + "T12:00:01";
			}
			int batchSize = 1;
			
			String portalStackPatientUrnIdData = "urn:vnd:ihg:portal:patient:"+testData.getTestPatientIDUserName();
						
			String portalStackProviderUrnIdData = "urn:vnd:ihg:portal:practice:"+testData.getSubscriberPracticeID();
			for(int i=0;i<batchSize;i++) {
				//Adding wait time so that time stamp will have different values-=
				Thread.sleep(5000);
				Long timestamp = System.currentTimeMillis();
				firstName = "Name" + timestamp;
				lastName = "TestPatient" + timestamp;
				email = firstName + "@mailinator.com";
				firstNameGroup.add(firstName);
				lastNameGroup.add(lastName);
				emailGroup.add(email);
				zipGroup.add(zip);
				dateGroup.add(date);
				UUID patientuuid=UUID.randomUUID();
				
				//Start of Header Node
				Element Header = doc.createElement("Header");
				
				Element Type = doc.createElement("Type");
				Header.appendChild(Type);
				Type.appendChild(doc.createTextNode("PersonDemographics"));
					
				Element MsgId = doc.createElement("MsgId");
				Header.appendChild(MsgId);
				MsgId.appendChild(doc.createTextNode(patientuuid.toString()));

				
				Element Event = doc.createElement("Event");
				Header.appendChild(Event);
				Event.appendChild(doc.createTextNode("Update"));

				
				Element Source = doc.createElement("Source");
				Header.appendChild(Source);
				Source.appendChild(doc.createTextNode("PRECHECK"));

				//Start of Internal node
				Element Internal = doc.createElement("Internal");
				Header.appendChild(Internal);

				Element MajorVer = doc.createElement("MajorVer");
				Internal.appendChild(MajorVer);
				MajorVer.appendChild(doc.createTextNode("1"));
				
				Element MinorVer = doc.createElement("MinorVer");
				Internal.appendChild(MinorVer);
				MinorVer.appendChild(doc.createTextNode("0"));
				
				Element Status = doc.createElement("Status");
				Internal.appendChild(Status);
				Status.appendChild(doc.createTextNode("Ok"));
				
				Element StatusTime = doc.createElement("StatusTime");
				Internal.appendChild(StatusTime);
				StatusTime.appendChild(doc.createTextNode("2017-11-14T04:48:27.604-04:00"));	
				
				Element Guid = doc.createElement("Guid");
				Internal.appendChild(Guid);
				Guid.appendChild(doc.createTextNode(UUID.randomUUID().toString()));
				//End of Internal Node
				
				mainRootElement.appendChild(Header);
				
				Element Patient = doc.createElement("Guid");

				//PatientDemographics Node
				Element PatientDemographics = doc.createElement("PatientDemographics");
				mainRootElement.appendChild(PatientDemographics);
				
				Element FirstName = doc.createElement("FirstName");
				PatientDemographics.appendChild(FirstName);
				FirstName.appendChild(doc.createTextNode(firstName));
				
				Element LastName = doc.createElement("LastName");
				PatientDemographics.appendChild(LastName);
				LastName.appendChild(doc.createTextNode("LastName"));
								
				Element DOB = doc.createElement("DOB");
				PatientDemographics.appendChild(DOB);
				DOB.appendChild(doc.createTextNode(dateOfBirth));
				
				Element Gender = doc.createElement("Gender");
				PatientDemographics.appendChild(Gender);
				Gender.appendChild(doc.createTextNode(testData.patientDetailList.get(i+1).getGender()));
				
				Element GenderIdentity = doc.createElement("GenderIdentity");
				PatientDemographics.appendChild(GenderIdentity);
				GenderIdentity.appendChild(doc.createTextNode(testData.patientDetailList.get(i+1).getGender()));
				
				Element SexualOrientation = doc.createElement("SexualOrientation");
				PatientDemographics.appendChild(SexualOrientation);
				SexualOrientation.appendChild(doc.createTextNode("LESBIAN_GAY_OR_HOMOSEXUAL"));
				
				Element MaritalStatus = doc.createElement("MaritalStatus");
				PatientDemographics.appendChild(MaritalStatus);
				MaritalStatus.appendChild(doc.createTextNode("SINGLE"));
				
				Element Race = doc.createElement("Race");
				PatientDemographics.appendChild(Race);
				Race.appendChild(doc.createTextNode("Unknown"));
				
				Element Ethnicity = doc.createElement("Ethnicity");
				PatientDemographics.appendChild(Ethnicity);
				Ethnicity.appendChild(doc.createTextNode("Unknown"));
				
				Element PreferredLanguage = doc.createElement("PreferredLanguage");
				PatientDemographics.appendChild(PreferredLanguage);
				PreferredLanguage.appendChild(doc.createTextNode("Unknown"));
				
				//Start Addresses node
				Element Addresses = doc.createElement("Addresses");
				
				//Start Address node
				Element Address = doc.createElement("Address");
				Addresses.appendChild(Address);
				
				Element AddressType = doc.createElement("Type");
				Address.appendChild(AddressType);
				AddressType.appendChild(doc.createTextNode("HOME"));

				Element AddressLine1 = doc.createElement("AddressLine1");
				Address.appendChild(AddressLine1);
				AddressLine1.appendChild(doc.createTextNode("Line1"));

				Element AddressLine2 = doc.createElement("AddressLine2");
				Address.appendChild(AddressLine2);
				AddressLine2.appendChild(doc.createTextNode("Line2"));

				Element CityName = doc.createElement("CityName");
				Address.appendChild(CityName);
				CityName.appendChild(doc.createTextNode("City"));

				Element StateCode = doc.createElement("StateCode");
				Address.appendChild(StateCode);
				StateCode.appendChild(doc.createTextNode(testData.patientDetailList.get(i+1).getStateNodeValue().trim()));

				Element ZipCode = doc.createElement("ZipCode");
				Address.appendChild(ZipCode);
				ZipCode.appendChild(doc.createTextNode(zip));
				
				Element Country = doc.createElement("Country");
				Address.appendChild(Country);
				Country.appendChild(doc.createTextNode("Country"));

				PatientDemographics.appendChild(Addresses);
				//End Address Node
				
				// Patient Phones Number
				
				Element Phones = doc.createElement("Phones");
				
				Element Phone = doc.createElement("Phone");
				Phones.appendChild(Phone);
				
				
				Element PhoneType = doc.createElement("Type");
				Phone.appendChild(PhoneType);
				PhoneType.appendChild(doc.createTextNode("HOME"));

				Element Number = doc.createElement("Number");	
				Phone.appendChild(Number);
				Number.appendChild(doc.createTextNode("HomePhone"));
				
				PatientDemographics.appendChild(Phones);

				
				Element EmailAddress = doc.createElement("Email");
				PatientDemographics.appendChild(EmailAddress);
				EmailAddress.appendChild(doc.createTextNode(email));
				
				////personProviders Node
				Element personProviders = doc.createElement("personProviders");
				PatientDemographics.appendChild(personProviders);
				
				Element personProvider = doc.createElement("PersonProvider");
				personProviders.appendChild(personProvider);
				
				
				Element portalStackPatientUrnId = doc.createElement("portalStackPatientUrnId");
				personProvider.appendChild(portalStackPatientUrnId);
				
			
				Element patienturn = doc.createElement("urn");
				portalStackPatientUrnId.appendChild(patienturn);
				patienturn.appendChild(doc.createTextNode(portalStackPatientUrnIdData));
				
				
				Element portalStackProviderUrnId = doc.createElement("portalStackProviderUrnId");
				personProvider.appendChild(portalStackProviderUrnId);
				
				Element ProviderUrnId = doc.createElement("urn");
				portalStackProviderUrnId.appendChild(ProviderUrnId);
				ProviderUrnId.appendChild(doc.createTextNode(portalStackProviderUrnIdData));
				
				Element PreferredCommunication = doc.createElement("PreferredCommunication");
				Patient.appendChild(PreferredCommunication);
				PreferredCommunication.appendChild(doc.createTextNode(testData.patientDetailList.get(i+1).getPreferredCommunication()));
				
				// Preffered Pharmacy
				Element PreferredPharmacy = doc.createElement("PreferredPharmacy");
				PatientDemographics.appendChild(PreferredPharmacy);
				
				Element Pharmacy = doc.createElement("Pharmacy");
				PreferredPharmacy.appendChild(Pharmacy);				
				
				Element ExternalPharmacyId = doc.createElement("ExternalPharmacyId");
				Pharmacy.appendChild(ExternalPharmacyId);	
				ExternalPharmacyId.appendChild(doc.createTextNode("ExternalPharmacyId1"));
				
				Element Name = doc.createElement("Name");
				Pharmacy.appendChild(Name);	
				Name.appendChild(doc.createTextNode("TestPharmacyName1"));

				Element PharmacyStatus = doc.createElement("Status");
				Pharmacy.appendChild(PharmacyStatus);	
				PharmacyStatus.appendChild(doc.createTextNode("ACTIVE"));
				
				Element PharmacyAddress = doc.createElement("Address");
				Pharmacy.appendChild(PharmacyAddress);	
				
				Element PharmAddressType = doc.createElement("Type");
				PharmacyAddress.appendChild(PharmAddressType);
				PharmAddressType.appendChild(doc.createTextNode("HOME"));

				Element PharmAddressLine1 = doc.createElement("AddressLine1");
				PharmacyAddress.appendChild(PharmAddressLine1);
				PharmAddressLine1.appendChild(doc.createTextNode("Line1"));

				Element PharmAddressLine2 = doc.createElement("AddressLine2");
				PharmacyAddress.appendChild(PharmAddressLine2);
				PharmAddressLine2.appendChild(doc.createTextNode("Line2"));

				Element PharmCityName = doc.createElement("CityName");
				PharmacyAddress.appendChild(PharmCityName);
				PharmCityName.appendChild(doc.createTextNode("City"));

				Element PharmStateCode = doc.createElement("StateCode");
				PharmacyAddress.appendChild(PharmStateCode);
				PharmStateCode.appendChild(doc.createTextNode(testData.patientDetailList.get(i+1).getStateNodeValue().trim()));

				Element PharmZipCode = doc.createElement("ZipCode");
				PharmacyAddress.appendChild(PharmZipCode);
				PharmZipCode.appendChild(doc.createTextNode(zip));
				
				Element PharmCountry = doc.createElement("Country");
				PharmacyAddress.appendChild(PharmCountry);
				PharmCountry.appendChild(doc.createTextNode("Country"));
				//End of Pharmacy node
				
				// start Patient Insurance1				
				Element PatientInsurance = doc.createElement("PatientInsurance");
				mainRootElement.appendChild(PatientInsurance);

				Element InsurancePolicyKey = doc.createElement("InsurancePolicyKey");
				PatientInsurance.appendChild(InsurancePolicyKey);
				
				Element PayerId = doc.createElement("PayerId");
				InsurancePolicyKey.appendChild(PayerId);
				PayerId.appendChild(doc.createTextNode("67890947"));

				Element PolicyId = doc.createElement("PolicyId");
				InsurancePolicyKey.appendChild(PolicyId);
				PolicyId.appendChild(doc.createTextNode("InsurancePolicyId"));
				
				Element PatientRef = doc.createElement("PatientRef");
				InsurancePolicyKey.appendChild(PatientRef);
				
				Element PatientNum = doc.createElement("PatientNum");
				PatientRef.appendChild(PatientNum);
				PatientNum.appendChild(doc.createTextNode("PatientNum"));
				
				Element BillingAccountRef = doc.createElement("BillingAccountRef");
				PatientRef.appendChild(BillingAccountRef);
				
				Element PracticeId = doc.createElement("PracticeId");
				BillingAccountRef.appendChild(PracticeId);
				PracticeId.appendChild(doc.createTextNode(testData.getPracticeId()));
				
				Element BillingAcctNum = doc.createElement("BillingAcctNum");
				BillingAccountRef.appendChild(BillingAcctNum);
				BillingAcctNum.appendChild(doc.createTextNode("BillingAcctNum"));
				
				Element InsuranceCompany = doc.createElement("InsuranceCompany");
				PatientInsurance.appendChild(InsuranceCompany);
				InsuranceCompany.appendChild(doc.createTextNode("Aviva"));

				Element InsuranceClaimsPhone = doc.createElement("InsuranceClaimsPhone");
				PatientInsurance.appendChild(InsuranceClaimsPhone);

				Element InsNumber = doc.createElement("Number");
				InsuranceClaimsPhone.appendChild(InsNumber);
				InsNumber.appendChild(doc.createTextNode("9820999889"));
				
				Element InsuranceIdx = doc.createElement("InsuranceIdx");
				PatientInsurance.appendChild(InsuranceIdx);
				InsuranceIdx.appendChild(doc.createTextNode("PRIMARY"));
				
				Element MemberId = doc.createElement("MemberId");
				PatientInsurance.appendChild(MemberId);
				MemberId.appendChild(doc.createTextNode("001"));

				Element SubscriberId = doc.createElement("SubscriberId");
				PatientInsurance.appendChild(SubscriberId);
				SubscriberId.appendChild(doc.createTextNode("002"));
				
				Element GroupNum = doc.createElement("GroupNum");
				PatientInsurance.appendChild(GroupNum);
				GroupNum.appendChild(doc.createTextNode("003"));

				Element SubscriberFirstName = doc.createElement("SubscriberFirstName");
				PatientInsurance.appendChild(SubscriberFirstName);
				SubscriberFirstName.appendChild(doc.createTextNode("SubscriberFirstName"));

				Element SubscriberLastName = doc.createElement("SubscriberLastName");
				PatientInsurance.appendChild(SubscriberLastName);
				SubscriberLastName.appendChild(doc.createTextNode("SubscriberLastName"));
				
				Element SubscriberMiddleName = doc.createElement("SubscriberMiddleName");
				PatientInsurance.appendChild(SubscriberMiddleName);
				SubscriberMiddleName.appendChild(doc.createTextNode("SubscriberMiddleName"));
				
				Element SubscriberDoB = doc.createElement("SubscriberDoB");
				PatientInsurance.appendChild(SubscriberDoB);
				SubscriberDoB.appendChild(doc.createTextNode(dateOfBirth));
				
				Element SubscriberSSN = doc.createElement("SubscriberSSN");
				PatientInsurance.appendChild(SubscriberSSN);
				SubscriberSSN.appendChild(doc.createTextNode("SubscriberSSN"));

				Element ISSSN = doc.createElement("ISSSN");
				PatientInsurance.appendChild(ISSSN);
				ISSSN.appendChild(doc.createTextNode("false"));
				
				Element InsuredName = doc.createElement("InsuredName");
				PatientInsurance.appendChild(InsuredName);
				InsuredName.appendChild(doc.createTextNode("Max"));
				
				Element EffectiveDate = doc.createElement("EffectiveDate");
				PatientInsurance.appendChild(EffectiveDate);
				EffectiveDate.appendChild(doc.createTextNode("2001-12-31T12:00:00"));

				Element PatientRelationToSubscriber = doc.createElement("PatientRelationToSubscriber");
				PatientInsurance.appendChild(PatientRelationToSubscriber);
				PatientRelationToSubscriber.appendChild(doc.createTextNode("SELF"));

				Element PictureFront = doc.createElement("PictureFront");
				PatientInsurance.appendChild(PictureFront);
				PictureFront.appendChild(doc.createTextNode("123456"));
				
				Element PictureBack = doc.createElement("PictureBack");
				PatientInsurance.appendChild(PictureBack);
				PictureBack.appendChild(doc.createTextNode("123456"));
								
			}
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			DOMSource source = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			transformer.transform(source, new StreamResult(writer));
			output = writer.toString();
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}

}