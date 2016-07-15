package com.intuit.ihg.product.integrationplatform.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
public class MassAdmin {
	
	@Value("${url}")
	private String Url;

	@Value("${PatientLogin}")
	private String PatientLogin;
	
	@Value("${PatientPassword}")
	private String PatientPassword;
	
	@Value("${Patient}")
	private String Patient;
	
	@Value("${massAdminRestUrl}")
	private String RestUrl;

	@Value("${ResponsePath}")
	private String ResponsePath;
	
	@Value("${From}")
	private String From;
	
	private String SecureMessagePath;
	@Value("${OAuthProperty}")
	
	private String OAuthProperty;
	@Value("${OAuthKeystore}")
	
	private String OAuthKeyStore;
	@Value("${OAuthApptoken}")
	private String OAuthAppToken;
	
	@Value("${OAuthUsername}")
	private String OAuthUsername;
	
	@Value("${OAuthPassword}")
	private String OAuthPassword;
	
	@Value("${ReadCommunicationURL}")
	private String ReadCommuniationURL;
	
	@Value("${Sender}")
	private String Sender;
	
	@Value("${IntegrationPracticeID}")
	private String IntegrationPracticeID;
	
	@Value("${PatientFName}")
	private String PatientName;
	
	@Value("${GmailUserName}")
	private String GmailUserName;
	
	@Value("${GmailPassword}")
	private String GmailPassword;
	
	@Value("${massAdminPayload}")
	private String massAdminPayload;
	
	@Value("${message}")
	private String message;
	
	public String getUrl() {
		return Url;
	}

	public String getPatientPassword() {
		return PatientPassword;
	}
	
	public String getPatientLogin() {
		return PatientLogin;
	}

	public String getPatient() {
		return Patient;
	}

	public String getRestUrl() {
		return RestUrl;
	}

	public String getResponsePath() {
		return ResponsePath;
	}

	public String getFrom() {
		return From;
	}

	public String getSecureMessagePath() {
		return SecureMessagePath;
	}

	public String getOAuthProperty() {
		return OAuthProperty;
	}

	public String getOAuthKeyStore() {
		return OAuthKeyStore;
	}

	public String getOAuthAppToken() {
		return OAuthAppToken;
	}

	public String getOAuthUsername() {
		return OAuthUsername;
	}

	public String getOAuthPassword() {
		return OAuthPassword;
	}

	public String getReadCommuniationURL() {
		return ReadCommuniationURL;
	}

	public String getSender() {
		return Sender;
	}

	public String getIntegrationPracticeID() {
		return IntegrationPracticeID;
	}

	public String getPatientName() {
		return PatientName;
	}

	public String getGmailUserName() {
		return GmailUserName;
	}

	public String getGmailPassword() {
		return GmailPassword;
	}

	public String getMassAdminPayload() {
		return massAdminPayload;
	}

	public String getMessage() {
		return message;
	}

	public List<PatientDetails> getPatientDetailsList() {
		List<PatientDetails> patientDetailsList = new ArrayList<PatientDetails>();
		String[] patient = Patient.split(",");
		String[] patientLogin = PatientLogin.split(",");
		String[] patientName = PatientName.split(",");
		for(int i=0; i<patient.length; i++)
		{
			PatientDetails patientDetailsObj = new PatientDetails();
			patientDetailsObj.setPatient(patient[i]);
			patientDetailsObj.setPatientLogin(patientLogin[i]);
			patientDetailsObj.setPatientName(patientName[i]);
			patientDetailsList.add(patientDetailsObj);
		}
		return patientDetailsList;
	}
}
