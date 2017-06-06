package com.intuit.ihg.product.integrationplatform.utils;

import java.io.StringWriter;
import java.text.ParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DirectorySearchPayload {
	public String output;
	
	public String getDirectSearchPayload(DirectorySearch testData, int counter) throws ParseException {		
		DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder icBuilder;
		counter = counter+1;
	try {
		icBuilder = icFactory.newDocumentBuilder();
		Document doc = icBuilder.newDocument();
		String schema = "http://schema.medfusion.com/directmessage/v1";
		Element mainRootElement = doc.createElementNS(schema,"ns2:DirectorySearchRequest" );
		doc.appendChild(mainRootElement);		
		Element SearchType = doc.createElement("SearchType");

		SearchType.appendChild(doc.createTextNode(testData.DirectorySearchList.get(counter).getType()));
		mainRootElement.appendChild(SearchType);
		if(testData.DirectorySearchList.get(counter).getFirstName() !=null) {
			Element FirstName = doc.createElement("FirstName");
			FirstName.appendChild(doc.createTextNode(testData.DirectorySearchList.get(counter).getFirstName()));
			mainRootElement.appendChild(FirstName);			
		}
		if(testData.DirectorySearchList.get(counter).getLastName() !=null) {
			Element LastName = doc.createElement("LastName");
			LastName.appendChild(doc.createTextNode(testData.DirectorySearchList.get(counter).getLastName()));
			mainRootElement.appendChild(LastName);	
		}
		if(testData.DirectorySearchList.get(counter).getOrganizationName() !=null) {
			Element OrganizationName = doc.createElement("OrganizationName");
			OrganizationName.appendChild(doc.createTextNode(testData.DirectorySearchList.get(counter).getOrganizationName()));
			mainRootElement.appendChild(OrganizationName);	
		}
		if(testData.DirectorySearchList.get(counter).getNationalProviderId() !=null) {
			Element NationalProviderId = doc.createElement("NationalProviderId");
			NationalProviderId.appendChild(doc.createTextNode(testData.DirectorySearchList.get(counter).getNationalProviderId()));
			mainRootElement.appendChild(NationalProviderId);
		}
		if(testData.DirectorySearchList.get(counter).getSpecialtyType() !=null) {
			Element SpecialtyType = doc.createElement("SpecialtyType");
			SpecialtyType.appendChild(doc.createTextNode(testData.DirectorySearchList.get(counter).getSpecialtyType()));
			mainRootElement.appendChild(SpecialtyType);
		}
		if(testData.DirectorySearchList.get(counter).getSpecialtyClassification() !=null) {
			Element SpecialtyClassification = doc.createElement("SpecialtyClassification");
			SpecialtyClassification.appendChild(doc.createTextNode(testData.DirectorySearchList.get(counter).getSpecialtyClassification()));
			mainRootElement.appendChild(SpecialtyClassification	);	
		}
		if(testData.DirectorySearchList.get(counter).getSpecialtySpecialization() !=null) {
			Element SpecialtySpecialization = doc.createElement("SpecialtySpecialization");
			SpecialtySpecialization.appendChild(doc.createTextNode(testData.DirectorySearchList.get(counter).getSpecialtySpecialization()));
			mainRootElement.appendChild(SpecialtySpecialization);
		}
		if(testData.DirectorySearchList.get(counter).getStreet() !=null) {
			Element Street = doc.createElement("Street");
			Street.appendChild(doc.createTextNode(testData.DirectorySearchList.get(counter).getStreet()));
			mainRootElement.appendChild(Street);
		}
		if(testData.DirectorySearchList.get(counter).getCity() !=null) {
			Element City = doc.createElement("City");
			City.appendChild(doc.createTextNode(testData.DirectorySearchList.get(counter).getCity()));
			mainRootElement.appendChild(City);
		}
		if(testData.DirectorySearchList.get(counter).getState() !=null) {
			Element State = doc.createElement("State");
			State.appendChild(doc.createTextNode(testData.DirectorySearchList.get(counter).getState()));
			mainRootElement.appendChild(State);
		}
		
		if(testData.DirectorySearchList.get(counter).getZipcode() !=null) {
			Element ZipCode = doc.createElement("ZipCode");
			ZipCode.appendChild(doc.createTextNode(testData.DirectorySearchList.get(counter).getZipcode()));
			mainRootElement.appendChild(ZipCode);
		}
		
		if(testData.DirectorySearchList.get(counter).getDirectAddress() !=null) {
			Element DirectAddress = doc.createElement("DirectAddress");
			DirectAddress.appendChild(doc.createTextNode(testData.DirectorySearchList.get(counter).getDirectAddress()));
			mainRootElement.appendChild(DirectAddress);
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
