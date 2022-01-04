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
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.RandomStringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

//import com.intuit.ihg.product.integrationplatform.test.testData;

public class AppointmentDataPayload {
	public String output = null;
	public String[] localDay = new String[50];
	public String[] localTime = new String[50];
	public String[] locationIdentifier = new String[50];
	public String[] providerNamePlace = new String[50];

	public String formattedTime = null;
	public String appointmentLocation = null;

	public static void main(String args[]) throws TransformerConfigurationException, InterruptedException, IOException,
			TransformerFactoryConfigurationError {
		AppointmentData testData = new AppointmentData();
		AppointmentDataPayload apObj = new AppointmentDataPayload();
		apObj.getAppointmentDataPayload(testData);
	}

	public String getAppointmentDataPayload(AppointmentData testData) throws InterruptedException, IOException,
			TransformerConfigurationException, TransformerFactoryConfigurationError {
		try {

			if (testData.Status.equalsIgnoreCase("CANCEL")) {
				localDay[0] = "";
				localTime[0] = "";
				locationIdentifier[0] = "";
				providerNamePlace[0] = "";
			} else {
				localDay[1] = localDay[0];
				localTime[1] = localTime[0];
				locationIdentifier[1] = locationIdentifier[0];
				providerNamePlace[1] = providerNamePlace[0];
			}

			Long timestamp = System.currentTimeMillis();
			DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder icBuilder;
			icBuilder = icFactory.newDocumentBuilder();
			Document doc = icBuilder.newDocument();
			String schema = "http://schema.medfusion.com/appointmentsdata/v1";
			Thread.sleep(500);
			if (testData.Status.isEmpty()) {
				testData.Status = "NEW";
			}
			Element mainRootElement = doc.createElementNS(schema, "ns2:AppointmentData");
			doc.appendChild(mainRootElement);
			// Start AppointmentMessageHeader Node
			Element AppointmentMessageHeader = doc.createElement("AppointmentMessageHeader");

			Element Sender = doc.createElement("Sender");
			Sender.setAttribute("VendorName", "Some Medical");
			Sender.setAttribute("DeviceLocalTime", "2017-02-03T12:45:12-04:00");
			Sender.setAttribute("DeviceVersion", "8.2");
			Sender.setAttribute("DeviceName", "Some Device");
			AppointmentMessageHeader.appendChild(Sender);

			mainRootElement.appendChild(AppointmentMessageHeader);
			// End AppointmentMessageHeader Node

			String[] time = { testData.Time, testData.appointmentDetailList.get(2).getTime() };
			String[] status = { testData.Status, testData.appointmentDetailList.get(2).getStatus() };

			String[] pId = { testData.PatientPracticeId, testData.PatientPracticeId };
			String[] fN = { testData.FirstName, testData.FirstName };
			String[] lN = { testData.LastName, testData.LastName };
			String[] email = { testData.EmailUserName, testData.EmailUserName };

			String[] typeAppointment = { testData.Type, testData.appointmentDetailList.get(2).getType() };
			String[] reasonAppointment = { testData.Reason, testData.appointmentDetailList.get(2).getReason() };
			String[] descriptionAppointment = { testData.Description,
					testData.appointmentDetailList.get(2).getDescription() };

			// Start Appointments Node
			int batchSize = Integer.parseInt(testData.BatchSize);

			for (int i = 0; i < batchSize; i++) {
				Thread.sleep(8000);
				timestamp = System.currentTimeMillis();
				// Start Appointments Node
				Node Appointments = doc.createElement("Appointments");
				Node PracticeIdentifier = doc.createElement("PracticeIdentifier");
				Node IntegrationPracticeId = doc.createElement("IntegrationPracticeId");
				IntegrationPracticeId.appendChild(doc.createTextNode(testData.PracticeName));
				PracticeIdentifier.appendChild(IntegrationPracticeId);
				Appointments.appendChild(PracticeIdentifier);

				if (testData.MFPracticeId != null && testData.MFPracticeId != "") {
					Node MFPracticeId = doc.createElement("MFPracticeId");
					MFPracticeId.appendChild(doc.createTextNode(testData.MFPracticeId));
					PracticeIdentifier.appendChild(MFPracticeId);
				}

				// End PracticeIdentifier Node
				String appointmentID;
				if (testData.PreviousAppointmentId != null && testData.PreviousAppointmentId != "") {
					appointmentID = testData.PreviousAppointmentId;
				} else {
					appointmentID = random11Numbers();
					testData.PreviousAppointmentId = appointmentID;
				}

				if (i > 0) {
					appointmentID = random11Numbers();
				}

				Node AppointmentId = doc.createElement("AppointmentId");
				AppointmentId.appendChild(doc.createTextNode(appointmentID));
				Appointments.appendChild(AppointmentId);

				Node Status = doc.createElement("Status");

				Status.appendChild(doc.createTextNode(status[i]));
				Appointments.appendChild(Status);

				Node Type = doc.createElement("Type");
				Type.appendChild(doc.createTextNode(typeAppointment[i]));
				Appointments.appendChild(Type);

				Node Reason = doc.createElement("Reason");
				Reason.appendChild(doc.createTextNode(reasonAppointment[i]));
				Appointments.appendChild(Reason);

				Node Description = doc.createElement("Description");
				Description.appendChild(doc.createTextNode(descriptionAppointment[i]));
				Appointments.appendChild(Description);

				String appointmentTime = time[i]; // "2017-02-07T15:30:59.999Z";
				// appointmentTime = "2017-03-07T15:30:59.999Z";
				Node Time = doc.createElement("Time");
				Time.appendChild(doc.createTextNode(appointmentTime));
				Appointments.appendChild(Time);

				try {

					DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
					utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

					Date date1 = utcFormat.parse(appointmentTime);
					// DateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
					DateFormat localFormat = new SimpleDateFormat("M-dd-yy h:mm a");
					localFormat.setTimeZone(TimeZone.getDefault());

					formattedTime = localFormat.format(date1);
					// System.out.println("Raw : " + formattedTime);

					formattedTime = formattedTime.replace("-", "/");

					String[] patientAppointmentTime = formattedTime.split(" ");
					localDay[i] = patientAppointmentTime[0];

					localTime[i] = patientAppointmentTime[1] + " " + patientAppointmentTime[2];

				} catch (ParseException e) {
					e.printStackTrace();
				}

				locationIdentifier[i] = testData.Location + timestamp;
				// System.out.print("location : "+locationIdentifier[i]);
				Node Location = doc.createElement("Location");
				Location.appendChild(doc.createTextNode(locationIdentifier[i]));
				Appointments.appendChild(Location);

				Node AppointmentAddress = doc.createElement("AppointmentAddress");
				Node Address1 = doc.createElement("Address1");
				Address1.appendChild(doc.createTextNode(testData.appointmentDetailList.get(i + 1).getAddress1()));
				AppointmentAddress.appendChild(Address1);
				Node Address2 = doc.createElement("Address2");
				Address2.appendChild(doc.createTextNode(testData.appointmentDetailList.get(i + 1).getAddress2()));
				AppointmentAddress.appendChild(Address2);
				Node City = doc.createElement("City");
				City.appendChild(doc.createTextNode(testData.appointmentDetailList.get(i + 1).getCity()));
				AppointmentAddress.appendChild(City);

				Node State = doc.createElement("State");
				State.appendChild(doc.createTextNode(testData.appointmentDetailList.get(i + 1).getState()));
				AppointmentAddress.appendChild(State);
				Node ZipCode = doc.createElement("ZipCode");
				ZipCode.appendChild(doc.createTextNode(testData.appointmentDetailList.get(i + 1).getZipCode()));
				AppointmentAddress.appendChild(ZipCode);
				Appointments.appendChild(AppointmentAddress);

				Node Patient = doc.createElement("Patient");

				Node PatientId = doc.createElement("PatientId");
				PatientId.appendChild(doc.createTextNode(pId[i]));
				Patient.appendChild(PatientId);

				if (testData.MFPatientId != null && testData.MFPatientId != "") {
					Node MFPatientId = doc.createElement("MFPatientId");
					MFPatientId.appendChild(doc.createTextNode(testData.MFPatientId));
					Patient.appendChild(MFPatientId);
				}
				Node FirstName = doc.createElement("FirstName");
				FirstName.appendChild(doc.createTextNode(fN[i]));
				Patient.appendChild(FirstName);
				Node LastName = doc.createElement("LastName");
				LastName.appendChild(doc.createTextNode(lN[i]));
				Patient.appendChild(LastName);
				Node DOB = doc.createElement("DOB");
				DOB.appendChild(doc.createTextNode("1990-01-01T12:00:01"));
				Patient.appendChild(DOB);
				Node Email = doc.createElement("Email");
				Email.appendChild(doc.createTextNode(email[i]));
				Patient.appendChild(Email);

				Node Address = doc.createElement("Address");
				Node PAddress1 = doc.createElement("Address1");
				PAddress1.appendChild(doc.createTextNode("Walnut Street"));
				Address.appendChild(PAddress1);
				Node PAddress2 = doc.createElement("Address2");
				PAddress2.appendChild(doc.createTextNode(""));
				Address.appendChild(PAddress2);

				Node PCity = doc.createElement("City");
				PCity.appendChild(doc.createTextNode("irvine"));
				Address.appendChild(PCity);
				Node PState = doc.createElement("State");
				PState.appendChild(doc.createTextNode("CA"));
				Address.appendChild(PState);
				Node PZipCode = doc.createElement("ZipCode");
				PZipCode.appendChild(doc.createTextNode("92602"));
				Address.appendChild(PZipCode);

				Patient.appendChild(Address);
				Node Insurance = doc.createElement("Insurance");
				Node InsuranceInfo = doc.createElement("InsuranceInfo");
				Node PStatus = doc.createElement("Status");
				PStatus.appendChild(doc.createTextNode("Complete"));
				InsuranceInfo.appendChild(PStatus);
				Node GroupNumber = doc.createElement("GroupNumber");
				GroupNumber.appendChild(doc.createTextNode("21231"));
				InsuranceInfo.appendChild(GroupNumber);
				Node MemberId = doc.createElement("MemberId");
				MemberId.appendChild(doc.createTextNode("559495"));
				InsuranceInfo.appendChild(MemberId);
				Insurance.appendChild(InsuranceInfo);

				Patient.appendChild(Insurance);
				Appointments.appendChild(Patient);
				// End Patient

				// Start Participant
				Node Participant = doc.createElement("Participant");

				Node PType = doc.createElement("Type");
				PType.appendChild(doc.createTextNode("PROVIDER"));
				Participant.appendChild(PType);
				Node Identifier = doc.createElement("Identifier");
				Identifier.appendChild(doc.createTextNode(testData.From));
				Participant.appendChild(Identifier);
				Node ProviderName = doc.createElement("ProviderName");
				providerNamePlace[i] = "Kelvin Jlores" + timestamp;
				ProviderName.appendChild(doc.createTextNode(providerNamePlace[i]));
				Participant.appendChild(ProviderName);
				Appointments.appendChild(Participant);

				Node Copayment = doc.createElement("Copayment");
				Node Amount = doc.createElement("Amount");
				Amount.appendChild(doc.createTextNode("23.50"));
				Copayment.appendChild(Amount);
				Appointments.appendChild(Copayment);

				Node Balance = doc.createElement("Balance");
				Node DueBy = doc.createElement("DueBy");
				DueBy.appendChild(doc.createTextNode("2020-01-01T12:00:01"));
				Balance.appendChild(DueBy);
				Node BAmount = doc.createElement("Amount");
				BAmount.appendChild(doc.createTextNode("123.80"));
				Balance.appendChild(BAmount);
				Appointments.appendChild(Balance);
				mainRootElement.appendChild(Appointments);

			}

			// End Appointments Node

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			DOMSource source = new DOMSource(doc);

			StringWriter writer = new StringWriter();
			transformer.transform(source, new StreamResult(writer));
			output = writer.toString();
			// System.out.println(output);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
			System.out.println("ParserConfigurationException, Unable to Parse");
		} catch (TransformerException e) {
			e.printStackTrace();
			System.out.println("TransformerException, Unable to Transform the DOM");
		}
		// System.out.print(output);
		return output;

	}

	public static String random11Numbers() {
		return RandomStringUtils.random(11, false, true);
	}

	public String getAppointmentDataV3Payload(AppointmentData testData) throws InterruptedException, IOException,
			TransformerConfigurationException, TransformerFactoryConfigurationError {
		try {

			if (testData.Status.equalsIgnoreCase("CANCEL")) {
				localDay[0] = "";
				localTime[0] = "";
				locationIdentifier[0] = "";
				providerNamePlace[0] = "";
			} else {
				localDay[1] = localDay[0];
				localTime[1] = localTime[0];
				locationIdentifier[1] = locationIdentifier[0];
				providerNamePlace[1] = providerNamePlace[0];
			}

			Long timestamp = System.currentTimeMillis();
			DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder icBuilder;
			icBuilder = icFactory.newDocumentBuilder();
			Document doc = icBuilder.newDocument();
			String schema = "http://schema.medfusion.com/appointmentsdata/v3";
			Thread.sleep(500);
			if (testData.Status.isEmpty()) {
				testData.Status = "NEW";
			}
			Element mainRootElement = doc.createElementNS(schema, "ns2:AppointmentData");
			doc.appendChild(mainRootElement);

			String[] time = { testData.Time, testData.appointmentDetailList.get(2).getTime() };
			String[] status = { testData.Status, testData.appointmentDetailList.get(2).getStatus() };

			String[] pId = { testData.PatientPracticeId, testData.PatientPracticeId };
			String[] fN = { testData.FirstName, testData.FirstName };
			String[] lN = { testData.LastName, testData.LastName };
			String[] email = { testData.EmailUserName, testData.EmailUserName };

			String[] typeAppointment = { testData.Type, testData.appointmentDetailList.get(2).getType() };
			String[] reasonAppointment = { testData.Reason, testData.appointmentDetailList.get(2).getReason() };
			String[] descriptionAppointment = { testData.Description,
					testData.appointmentDetailList.get(2).getDescription() };

			int batchSize = Integer.parseInt(testData.BatchSize);

			for (int i = 0; i < batchSize; i++) {
				Thread.sleep(8000);
				timestamp = System.currentTimeMillis();
				// Start Appointments Node
				Node Appointments = doc.createElement("Appointments");
				Node PracticeIdentifier = doc.createElement("PracticeIdentifier");

				// Start PracticeIdentifier Node
				Node IntegrationPracticeId = doc.createElement("IntegrationPracticeId");
				IntegrationPracticeId.appendChild(doc.createTextNode(testData.PracticeName));
				PracticeIdentifier.appendChild(IntegrationPracticeId);
				Appointments.appendChild(PracticeIdentifier);

				if (testData.MFPracticeId != null && testData.MFPracticeId != "") {
					Node MFPracticeId = doc.createElement("MFPracticeId");
					MFPracticeId.appendChild(doc.createTextNode(testData.MFPracticeId));
					PracticeIdentifier.appendChild(MFPracticeId);
				}
				// End PracticeIdentifier Node

				String appointmentID;
				if (testData.PreviousAppointmentId != null && testData.PreviousAppointmentId != "") {
					appointmentID = testData.PreviousAppointmentId;
				} else {
					appointmentID = random11Numbers();
					testData.PreviousAppointmentId = appointmentID;
				}

				if (i > 0) {
					appointmentID = random11Numbers();
				}

				Node AppointmentId = doc.createElement("AppointmentId");
				AppointmentId.appendChild(doc.createTextNode(appointmentID));
				Appointments.appendChild(AppointmentId);

				Node Status = doc.createElement("Status");
				Status.appendChild(doc.createTextNode(status[i]));
				Appointments.appendChild(Status);

				Node Type = doc.createElement("Type");
				Type.appendChild(doc.createTextNode(typeAppointment[i]));
				Appointments.appendChild(Type);

				Node Reason = doc.createElement("Reason");
				Reason.appendChild(doc.createTextNode(reasonAppointment[i]));
				Appointments.appendChild(Reason);

				Node Description = doc.createElement("Description");
				Description.appendChild(doc.createTextNode(descriptionAppointment[i]));
				Appointments.appendChild(Description);

				String appointmentTime = time[i];
				Node Time = doc.createElement("Time");
				Time.appendChild(doc.createTextNode(appointmentTime));
				Appointments.appendChild(Time);

				try {

					DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
					utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

					Date date1 = utcFormat.parse(appointmentTime);
					DateFormat localFormat = new SimpleDateFormat("M-dd-yy h:mm a");
					localFormat.setTimeZone(TimeZone.getDefault());

					formattedTime = localFormat.format(date1);
					formattedTime = formattedTime.replace("-", "/");

					String[] patientAppointmentTime = formattedTime.split(" ");
					localDay[i] = patientAppointmentTime[0];

					localTime[i] = patientAppointmentTime[1] + " " + patientAppointmentTime[2];

				} catch (ParseException e) {
					e.printStackTrace();
				}

				locationIdentifier[i] = testData.Location + timestamp;
				Node Location = doc.createElement("Location");
				Location.appendChild(doc.createTextNode(locationIdentifier[i]));
				Appointments.appendChild(Location);

				// Start AppointmentAddress Node
				Node AppointmentAddress = doc.createElement("AppointmentAddress");
				Node Address1 = doc.createElement("Address1");
				Address1.appendChild(doc.createTextNode(testData.appointmentDetailList.get(i + 1).getAddress1()));
				AppointmentAddress.appendChild(Address1);
				Node Address2 = doc.createElement("Address2");
				Address2.appendChild(doc.createTextNode(testData.appointmentDetailList.get(i + 1).getAddress2()));
				AppointmentAddress.appendChild(Address2);
				Node City = doc.createElement("City");
				City.appendChild(doc.createTextNode(testData.appointmentDetailList.get(i + 1).getCity()));
				AppointmentAddress.appendChild(City);
				Node State = doc.createElement("State");
				State.appendChild(doc.createTextNode(testData.appointmentDetailList.get(i + 1).getState()));
				AppointmentAddress.appendChild(State);
				Node ZipCode = doc.createElement("ZipCode");
				ZipCode.appendChild(doc.createTextNode(testData.appointmentDetailList.get(i + 1).getZipCode()));
				AppointmentAddress.appendChild(ZipCode);
				Appointments.appendChild(AppointmentAddress);
				// End AppointmentAddress Node

				// Start Patient Node
				Node Patient = doc.createElement("Patient");

				Node PatientId = doc.createElement("PatientId");
				PatientId.appendChild(doc.createTextNode(pId[i]));
				Patient.appendChild(PatientId);

				if (testData.MFPatientId != null && testData.MFPatientId != "") {
					Node MFPatientId = doc.createElement("MFPatientId");
					MFPatientId.appendChild(doc.createTextNode(testData.MFPatientId));
					Patient.appendChild(MFPatientId);
				}
				Node FirstName = doc.createElement("FirstName");
				FirstName.appendChild(doc.createTextNode(fN[i]));
				Patient.appendChild(FirstName);
				Node LastName = doc.createElement("LastName");
				LastName.appendChild(doc.createTextNode(lN[i]));
				Patient.appendChild(LastName);
				Node DOB = doc.createElement("DOB");
				DOB.appendChild(doc.createTextNode("1990-01-01T12:00:01"));
				Patient.appendChild(DOB);
				Node Email = doc.createElement("Email");
				Email.appendChild(doc.createTextNode(email[i]));
				Patient.appendChild(Email);

				// Start Address Node
				Node Address = doc.createElement("Address");
				Node PAddress1 = doc.createElement("Address1");
				PAddress1.appendChild(doc.createTextNode("Walnut Street"));
				Address.appendChild(PAddress1);
				Node PAddress2 = doc.createElement("Address2");
				PAddress2.appendChild(doc.createTextNode(""));
				Address.appendChild(PAddress2);
				Node PCity = doc.createElement("City");
				PCity.appendChild(doc.createTextNode("irvine"));
				Address.appendChild(PCity);
				Node PState = doc.createElement("State");
				PState.appendChild(doc.createTextNode("CA"));
				Address.appendChild(PState);
				Node PZipCode = doc.createElement("ZipCode");
				PZipCode.appendChild(doc.createTextNode("92602"));
				Address.appendChild(PZipCode);
				Patient.appendChild(Address);
				// End Address Node

				// Start Insurance Node
				Node Insurance = doc.createElement("Insurance");
				Node InsuranceInfo = doc.createElement("InsuranceInfo");
				Node PStatus = doc.createElement("Status");
				PStatus.appendChild(doc.createTextNode("Complete"));
				InsuranceInfo.appendChild(PStatus);
				Node GroupNumber = doc.createElement("GroupNumber");
				GroupNumber.appendChild(doc.createTextNode("21231"));
				InsuranceInfo.appendChild(GroupNumber);
				Node MemberId = doc.createElement("MemberId");
				MemberId.appendChild(doc.createTextNode("559495"));
				InsuranceInfo.appendChild(MemberId);
				Insurance.appendChild(InsuranceInfo);
				// End Insurance Node

				Patient.appendChild(Insurance);
				Appointments.appendChild(Patient);
				// End Patient Node

				// Start Participant Node
				Node Participant = doc.createElement("Participant");

				Node PType = doc.createElement("Type");
				PType.appendChild(doc.createTextNode("PROVIDER"));
				Participant.appendChild(PType);
				Node Identifier = doc.createElement("Identifier");
				Identifier.appendChild(doc.createTextNode(testData.From));
				Participant.appendChild(Identifier);
				Node ProviderName = doc.createElement("ProviderName");
				providerNamePlace[i] = "Kelvin Jlores" + timestamp;
				ProviderName.appendChild(doc.createTextNode(providerNamePlace[i]));
				Participant.appendChild(ProviderName);
				Appointments.appendChild(Participant);
				// End Participant Node

				Node Copayment = doc.createElement("Copayment");
				Node Amount = doc.createElement("Amount");
				Amount.appendChild(doc.createTextNode("23.50"));
				Copayment.appendChild(Amount);
				Appointments.appendChild(Copayment);

				Node Balance = doc.createElement("Balance");
				Node DueBy = doc.createElement("DueBy");
				DueBy.appendChild(doc.createTextNode("2020-01-01T12:00:01"));
				Balance.appendChild(DueBy);
				Node BAmount = doc.createElement("Amount");
				BAmount.appendChild(doc.createTextNode("123.80"));
				Balance.appendChild(BAmount);
				Appointments.appendChild(Balance);

				Node Source = doc.createElement("Source");
				Source.appendChild(doc.createTextNode("APPOINTMENTS"));
				Appointments.appendChild(Source);
				mainRootElement.appendChild(Appointments);
				// End Appointments Node
			}

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			DOMSource source = new DOMSource(doc);
 
			StringWriter writer = new StringWriter();
			transformer.transform(source, new StreamResult(writer));
			output = writer.toString();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
			System.out.println("ParserConfigurationException, Unable to Parse");
		} catch (TransformerException e) {
			e.printStackTrace();
			System.out.println("TransformerException, Unable to Transform the DOM");
		}
		return output;
	}
	
	public String getAppointmentDataV4Payload(AppointmentData testData) throws InterruptedException, IOException,
	TransformerConfigurationException, TransformerFactoryConfigurationError {
try {

	if (testData.Status.equalsIgnoreCase("CANCEL")) {
		localDay[0] = "";
		localTime[0] = "";
		locationIdentifier[0] = "";
		providerNamePlace[0] = "";
	} else {
		localDay[1] = localDay[0];
		localTime[1] = localTime[0];
		locationIdentifier[1] = locationIdentifier[0];
		providerNamePlace[1] = providerNamePlace[0];
	}

	Long timestamp = System.currentTimeMillis();
	DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder icBuilder;
	icBuilder = icFactory.newDocumentBuilder();
	Document doc = icBuilder.newDocument();
	String schema = "http://schema.medfusion.com/appointmentsdata/v4";
	Thread.sleep(500);
	if (testData.Status.isEmpty()) {
		testData.Status = "NEW";
	}
	Element mainRootElement = doc.createElementNS(schema, "ns2:AppointmentData");
	doc.appendChild(mainRootElement);

	String[] time = { testData.Time, testData.appointmentDetailList.get(2).getTime() };
	String[] status = { testData.Status, testData.appointmentDetailList.get(2).getStatus() };

	String[] pId = { testData.PatientPracticeId, testData.PatientPracticeId };
	String[] fN = { testData.FirstName, testData.FirstName };
	String[] lN = { testData.LastName, testData.LastName };
	String[] email = { testData.EmailUserName, testData.EmailUserName };

	String[] typeAppointment = { testData.Type, testData.appointmentDetailList.get(2).getType() };
	String[] reasonAppointment = { testData.Reason, testData.appointmentDetailList.get(2).getReason() };
	String[] descriptionAppointment = { testData.Description,
			testData.appointmentDetailList.get(2).getDescription() };

	int batchSize = Integer.parseInt(testData.BatchSize);

	for (int i = 0; i < batchSize; i++) {
		Thread.sleep(8000);
		timestamp = System.currentTimeMillis();
		// Start Appointments Node
		Node Appointments = doc.createElement("Appointments");
		Node PracticeIdentifier = doc.createElement("PracticeIdentifier");

		// Start PracticeIdentifier Node
		Node IntegrationPracticeId = doc.createElement("IntegrationPracticeId");
		IntegrationPracticeId.appendChild(doc.createTextNode(testData.PracticeName));
		PracticeIdentifier.appendChild(IntegrationPracticeId);
		Appointments.appendChild(PracticeIdentifier);

		if (testData.MFPracticeId != null && testData.MFPracticeId != "") {
			Node MFPracticeId = doc.createElement("MFPracticeId");
			MFPracticeId.appendChild(doc.createTextNode(testData.MFPracticeId));
			PracticeIdentifier.appendChild(MFPracticeId);
		}
		// End PracticeIdentifier Node

		String appointmentID;
		if (testData.PreviousAppointmentId != null && testData.PreviousAppointmentId != "") {
			appointmentID = testData.PreviousAppointmentId;
		} else {
			appointmentID = random11Numbers();
			testData.PreviousAppointmentId = appointmentID;
		}

		if (i > 0) {
			appointmentID = random11Numbers();
		}

		Node AppointmentId = doc.createElement("AppointmentId");
		AppointmentId.appendChild(doc.createTextNode(appointmentID));
		Appointments.appendChild(AppointmentId);

		Node Status = doc.createElement("Status");
		Status.appendChild(doc.createTextNode(status[i]));
		Appointments.appendChild(Status);

		Node Type = doc.createElement("Type");
		Type.appendChild(doc.createTextNode(typeAppointment[i]));
		Appointments.appendChild(Type);

		Node Reason = doc.createElement("Reason");
		Reason.appendChild(doc.createTextNode(reasonAppointment[i]));
		Appointments.appendChild(Reason);

		Node Description = doc.createElement("Description");
		Description.appendChild(doc.createTextNode(descriptionAppointment[i]));
		Appointments.appendChild(Description);

		String appointmentTime = time[i];
		Node Time = doc.createElement("Time");
		Time.appendChild(doc.createTextNode(appointmentTime));
		Appointments.appendChild(Time);

		try {

			DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

			Date date1 = utcFormat.parse(appointmentTime);
			DateFormat localFormat = new SimpleDateFormat("M-dd-yy h:mm a");
			localFormat.setTimeZone(TimeZone.getDefault());

			formattedTime = localFormat.format(date1);
			formattedTime = formattedTime.replace("-", "/");

			String[] patientAppointmentTime = formattedTime.split(" ");
			localDay[i] = patientAppointmentTime[0];

			localTime[i] = patientAppointmentTime[1] + " " + patientAppointmentTime[2];

		} catch (ParseException e) {
			e.printStackTrace();
		}

		locationIdentifier[i] = testData.Location + timestamp;
		Node Location = doc.createElement("Location");
		Location.appendChild(doc.createTextNode(locationIdentifier[i]));
		Appointments.appendChild(Location);

		// Start AppointmentAddress Node
		Node AppointmentAddress = doc.createElement("AppointmentAddress");
		Node Address1 = doc.createElement("Address1");
		Address1.appendChild(doc.createTextNode(testData.appointmentDetailList.get(i + 1).getAddress1()));
		AppointmentAddress.appendChild(Address1);
		Node Address2 = doc.createElement("Address2");
		Address2.appendChild(doc.createTextNode(testData.appointmentDetailList.get(i + 1).getAddress2()));
		AppointmentAddress.appendChild(Address2);
		Node City = doc.createElement("City");
		City.appendChild(doc.createTextNode(testData.appointmentDetailList.get(i + 1).getCity()));
		AppointmentAddress.appendChild(City);
		Node State = doc.createElement("State");
		State.appendChild(doc.createTextNode(testData.appointmentDetailList.get(i + 1).getState()));
		AppointmentAddress.appendChild(State);
		Node ZipCode = doc.createElement("ZipCode");
		ZipCode.appendChild(doc.createTextNode(testData.appointmentDetailList.get(i + 1).getZipCode()));
		AppointmentAddress.appendChild(ZipCode);
		Appointments.appendChild(AppointmentAddress);
		// End AppointmentAddress Node

		// Start Patient Node
		Node Patient = doc.createElement("Patient");

		Node PatientId = doc.createElement("PatientId");
		PatientId.appendChild(doc.createTextNode(pId[i]));
		Patient.appendChild(PatientId);

		if (testData.MFPatientId != null && testData.MFPatientId != "") {
			Node MFPatientId = doc.createElement("MFPatientId");
			MFPatientId.appendChild(doc.createTextNode(testData.MFPatientId));
			Patient.appendChild(MFPatientId);
		}
		Node FirstName = doc.createElement("FirstName");
		FirstName.appendChild(doc.createTextNode(fN[i]));
		Patient.appendChild(FirstName);
		Node LastName = doc.createElement("LastName");
		LastName.appendChild(doc.createTextNode(lN[i]));
		Patient.appendChild(LastName);
		Node DOB = doc.createElement("DOB");
		DOB.appendChild(doc.createTextNode("1990-01-01T12:00:01"));
		Patient.appendChild(DOB);
		Node Email = doc.createElement("Email");
		Email.appendChild(doc.createTextNode(email[i]));
		Patient.appendChild(Email);

		// Start Address Node
		Node Address = doc.createElement("Address");
		Node PAddress1 = doc.createElement("Address1");
		PAddress1.appendChild(doc.createTextNode("Walnut Street"));
		Address.appendChild(PAddress1);
		Node PAddress2 = doc.createElement("Address2");
		PAddress2.appendChild(doc.createTextNode(""));
		Address.appendChild(PAddress2);
		Node PCity = doc.createElement("City");
		PCity.appendChild(doc.createTextNode("irvine"));
		Address.appendChild(PCity);
		Node PState = doc.createElement("State");
		PState.appendChild(doc.createTextNode("CA"));
		Address.appendChild(PState);
		Node PZipCode = doc.createElement("ZipCode");
		PZipCode.appendChild(doc.createTextNode("92602"));
		Address.appendChild(PZipCode);
		Patient.appendChild(Address);
		// End Address Node

		// Start Insurance Node
		Node Insurance = doc.createElement("Insurance");
		Node InsuranceInfo = doc.createElement("InsuranceInfo");
		Node name = doc.createElement("Name");
		name.appendChild(doc.createTextNode("Insurance1"));
		InsuranceInfo.appendChild(name);
		Node GroupNumber = doc.createElement("GroupNumber");
		GroupNumber.appendChild(doc.createTextNode("21231"));
		InsuranceInfo.appendChild(GroupNumber);
		Node MemberId = doc.createElement("MemberId");
		MemberId.appendChild(doc.createTextNode("559495"));
		InsuranceInfo.appendChild(MemberId);
		
		Node Copayment = doc.createElement("Copayment");
		Node Amount = doc.createElement("Amount");
		Amount.appendChild(doc.createTextNode("23"));
		Copayment.appendChild(Amount);
		Node StatusCopay = doc.createElement("Status");
		StatusCopay.appendChild(doc.createTextNode("Complete"));
		Copayment.appendChild(StatusCopay);
		InsuranceInfo.appendChild(Copayment);
		
		Node type = doc.createElement("Type");
		type.appendChild(doc.createTextNode("TERTIARY"));
		InsuranceInfo.appendChild(type);
		Node PayerId = doc.createElement("PayerId");
		PayerId.appendChild(doc.createTextNode("559495"));
		InsuranceInfo.appendChild(PayerId);
		
		Insurance.appendChild(InsuranceInfo);
		// End Insurance Node

		Patient.appendChild(Insurance);
		Appointments.appendChild(Patient);
		// End Patient Node

		//Start Pharmacy node
		
		Node PreferredPharmacy= doc.createElement("PreferredPharmacy");
		Node Pharmacy= doc.createElement("Pharmacy");
		Node ExternalPharmacyId= doc.createElement("ExternalPharmacyId");
		ExternalPharmacyId.appendChild(doc.createTextNode("ExternalPharmacyId1"));
		Pharmacy.appendChild(ExternalPharmacyId);
		Node Name= doc.createElement("Name");
		Name.appendChild(doc.createTextNode("Test Name Pharma1"));
		Pharmacy.appendChild(Name);

		Node PhAddress= doc.createElement("Address");
		Node PhAddress1= doc.createElement("Address1");
		PhAddress1.appendChild(doc.createTextNode("5501-Dillard Drive"));
		PhAddress.appendChild(PhAddress1);
		
		Node PhAddress2= doc.createElement("Address2");
		PhAddress2.appendChild(doc.createTextNode("501-Address2"));
		PhAddress.appendChild(PhAddress2);
		
		Node PhCity= doc.createElement("City");
		PhCity.appendChild(doc.createTextNode("Cary2"));
		PhAddress.appendChild(PhCity);
			
		Node PhState= doc.createElement("State");
		PhState.appendChild(doc.createTextNode("NC"));
		PhAddress.appendChild(PhState);
		
		Node PhZipCode= doc.createElement("ZipCode");
		PhZipCode.appendChild(doc.createTextNode("27601"));
		PhAddress.appendChild(PhZipCode);
		
		Node Number= doc.createElement("Number");
		Number.appendChild(doc.createTextNode("1234567891"));
		Pharmacy.appendChild(Number);

		PreferredPharmacy.appendChild(Pharmacy);
	Appointments.appendChild(PreferredPharmacy);
		//End of Pharmacy node
	
		//End of Pharmacy node
		
		// Start Participant Node
		Node Participant = doc.createElement("Participant");

		Node PType = doc.createElement("Type");
		PType.appendChild(doc.createTextNode("PROVIDER"));
		Participant.appendChild(PType);
		Node Identifier = doc.createElement("Identifier");
		Identifier.appendChild(doc.createTextNode(testData.From));
		Participant.appendChild(Identifier);
		Node ProviderName = doc.createElement("ProviderName");
		providerNamePlace[i] = "Kelvin Jlores" + timestamp;
		ProviderName.appendChild(doc.createTextNode(providerNamePlace[i]));
		Participant.appendChild(ProviderName);
		Appointments.appendChild(Participant);
		// End Participant Node

//		Node Copayment = doc.createElement("Copayment");
//		Node Amount = doc.createElement("Amount");
//		Amount.appendChild(doc.createTextNode("23.50"));
//		Copayment.appendChild(Amount);
//		Appointments.appendChild(Copayment);

		Node Balance = doc.createElement("Balance");
		Node DueBy = doc.createElement("DueBy");
		DueBy.appendChild(doc.createTextNode("2020-01-01T12:00:01"));
		Balance.appendChild(DueBy);
		Node BAmount = doc.createElement("Amount");
		BAmount.appendChild(doc.createTextNode("123.80"));
		Balance.appendChild(BAmount);
		Appointments.appendChild(Balance);

		Node Source = doc.createElement("Source");
		Source.appendChild(doc.createTextNode("APPOINTMENTS"));
		Appointments.appendChild(Source);
		mainRootElement.appendChild(Appointments);
		// End Appointments Node
	}

	Transformer transformer = TransformerFactory.newInstance().newTransformer();
	transformer.setOutputProperty(OutputKeys.INDENT, "yes");

	DOMSource source = new DOMSource(doc);

	StringWriter writer = new StringWriter();
	transformer.transform(source, new StreamResult(writer));
	output = writer.toString();
} catch (ParserConfigurationException pce) {
	pce.printStackTrace();
	System.out.println("ParserConfigurationException, Unable to Parse");
} catch (TransformerException e) {
	e.printStackTrace();
	System.out.println("TransformerException, Unable to Transform the DOM");
}
return output;

}
}