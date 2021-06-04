// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pssPatientModulatorAPI.test;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.medfusion.product.object.maps.pss2.page.util.HeaderConfig;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestPatientMod;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

public class PssPatientModulatorTest extends BaseTestNGWebDriver {
	@Test
	public void testApptDetailFromGuidGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.apptDetailFromGuid(testData.getBasicURI(), headerConfig.defaultHeader());
		log("Verifying the PracticeId");
		String practiceId = postAPIRequest.apptDetailFromGuid(testData.getBasicURI(), headerConfig.defaultHeader());
		Assert.assertEquals(practiceId, "205604", "Practice is wrong");
	}

	@Test
	public void practiceFromGuidAnonymousGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.practiceFromGuid(testData.getBasicURI(), headerConfig.defaultHeader());
		log("Verifying the Ext Practice Id");
		String extPracticeId = postAPIRequest.practiceFromGuid(testData.getBasicURI(), headerConfig.defaultHeader());
		Assert.assertEquals(extPracticeId, "24829", "Ext Practice Id is wrong");
	}

	@Test
	public void linksValueGuidGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.linksValueGuid(testData.getBasicURI(), headerConfig.defaultHeader());
		log("Verifying the practiceId");
		String practiceId = postAPIRequest.linksValueGuid(testData.getBasicURI(), headerConfig.defaultHeader());
		Assert.assertEquals(practiceId, "24333", "practiceId is wrong");
	}

	@Test
	public void linksValueGuidAndPracticeGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.linksValueGuidAndPractice(testData.getBasicURI(), headerConfig.defaultHeader());
		log("Verifying the practiceId");
		String practiceId = postAPIRequest.linksValueGuidAndPractice(testData.getBasicURI(), headerConfig.defaultHeader());
		Assert.assertEquals(practiceId, "24333", "PracticeId is wrong");
	}

	@Test
	public void linksDetailGuidGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.linksDetailGuid(testData.getBasicURI(), headerConfig.defaultHeader());
		log("Verifying the patientId");
		String patientId = postAPIRequest.linksDetailGuid(testData.getBasicURI(), headerConfig.defaultHeader());
		Assert.assertEquals(patientId, "27641", "PatientId is wrong");
	}

	@Test
	public void linksDetailGuidAndPracticeGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.linksDetailGuidAndPractice(testData.getBasicURI(), headerConfig.defaultHeader());
		log("Verifying the patientId");
		String patientId = postAPIRequest.linksDetailGuidAndPractice(testData.getBasicURI(), headerConfig.defaultHeader());
		Assert.assertEquals(patientId, "27641", "PatientId is wrong");
	}

	@Test
	public void guidForLogoutPatientGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.guidForLogoutPatient(testData.getBasicURI(), headerConfig.defaultHeader());
		log("Verifying the Guid");
		String guidId = postAPIRequest.guidForLogoutPatient(testData.getBasicURI(), headerConfig.defaultHeader());
		Assert.assertEquals(guidId, "53085385-2d3b-462f-a474-1e501b5a6a21", "guid Id is wrong");
	}

	@Test
	public void practiceFromGuidLoginlessGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.practiceFromGuidLoginless(testData.getBasicURI(), headerConfig.defaultHeader());
		log("Verifying the Ext Practice Id");
		String extPracticeId = postAPIRequest.practiceFromGuidLoginless(testData.getBasicURI(), headerConfig.defaultHeader());
		Assert.assertEquals(extPracticeId, "24249", "Ext PracticeId is wrong");
	}

	@Test
	public void tokenForLoginlessGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.tokenForLoginless(testData.getBasicURI(), headerConfig.defaultHeader());
		log("Verifying the Ext Practice Id");
		String extPracticeId = postAPIRequest.tokenForLoginless(testData.getBasicURI(), headerConfig.defaultHeader());
		Assert.assertEquals(extPracticeId, "24333", "Ext PracticeId is wrong");
	}

	@Test
	public void healthGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBaseUrlHealth());
		postAPIRequest.health(testData.getBaseUrlHealth(), headerConfig.defaultHeader());
	}

	@Test
	public void logoGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.logo(testData.getBasicURI(), headerConfig.defaultHeader());
	}

	@Test
	public void timezonePracticeResourceGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.timezonePracticeResource(testData.getBasicURI(), headerConfig.defaultHeader());
		log("Verifying the practiceId");
		String practiceId = postAPIRequest.timezonePracticeResource(testData.getBasicURI(), headerConfig.defaultHeader());
		Assert.assertEquals(practiceId, "24248", "PracticeId is wrong");
	}

	@Test
	public void practiceInfoGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.practiceInfo(testData.getBasicURI(), headerConfig.defaultHeader());
		log("Verifying the Ext Practice Id");
		String extPracticeId = postAPIRequest.practiceInfo(testData.getBasicURI(), headerConfig.defaultHeader());
		Assert.assertEquals(extPracticeId, "24333", "Ext PracticeId is wrong");
	}

	@Test
	public void resellerLogoGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.resellerLogo(testData.getBasicURI(), headerConfig.defaultHeader());
	}

	@Test
	public void sessionConfigurationGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.sessionConfiguration(testData.getBasicURI(), headerConfig.defaultHeader());
		log("Verifying the Token Expiration Time");
		int tokenExpirationTime = postAPIRequest.sessionConfiguration(testData.getBasicURI(), headerConfig.defaultHeader());
		Assert.assertEquals(tokenExpirationTime, 600, "Token Expiration Time is wrong");
	}

	@Test
	public void practiceDetailGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.practiceDetail(testData.getBasicURI(), headerConfig.defaultHeader());
		log("Verifying the Ext Practice Id");
		String extPracticeId = postAPIRequest.practiceDetail(testData.getBasicURI(), headerConfig.defaultHeader());
		Assert.assertEquals(extPracticeId, "24333", "Ext PracticeId is wrong");
	}

	@Test
	public void practiceFromGuidSsoGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.practiceFromGuidSso(testData.getBasicURI(), headerConfig.defaultHeader());
		log("Verifying the Ext Practice Id");
		String extPracticeId = postAPIRequest.practiceFromGuidSso(testData.getBasicURI(), headerConfig.defaultHeader());
		Assert.assertEquals(extPracticeId, "24829", "Ext PracticeId is wrong");
	}

	@Test
	public void timeZoneResourceGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.timeZoneResource(testData.getBasicURI(), headerConfig.defaultHeader());
	}

	@Test
	public void createTokenGET() throws IOException {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		String accessToken = postAPIRequest.createToken(testData.getAccessTokenURL());
		testData.setAccessToken(accessToken);
		log("The Accesssc Token is From the Test Method  " + testData.getAccessToken());
	}

	@Test
	public void upcomingConfigurationGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.upcomingConfiguration(testData.getBasicURI(), headerConfig.defaultHeader());
	}

	@Test
	public void getApptDetailPOST() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		PayloadPssPatientModulator payloadPatientMod = new PayloadPssPatientModulator();
		log("Base URL is   " + testData.getBasicURI());
		log("Payload- " + payloadPatientMod.getApptDetailPayload());
		postAPIRequest.getapptDetail(testData.getBasicURI(), payloadPatientMod.getApptDetailPayload(), headerConfig.defaultHeader());

	}

	@Test
	public void announcementByNameGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.announcementByName(testData.getBasicURI(), headerConfig.defaultHeader());
	}

	@Test
	public void announcementByLanguageGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.announcementByLanguage(testData.getBasicURI(), headerConfig.defaultHeader());
	}

	@Test
	public void announcementTypeGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.announcementType(testData.getBasicURI(), headerConfig.defaultHeader());
	}

	@Test
	public void getImagesGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.getImages(testData.getBasicURI(), headerConfig.defaultHeader());
	}

	@Test
	public void getLanguagesGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.getLanguages(testData.getBasicURI(), headerConfig.defaultHeader());
	}

	@Test
	public void demographicsProfilesGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.demographicsProfiles(testData.getBasicURI(), headerConfig.defaultHeader());
		log("Verifying the Demographics Id");
		String demographicsId = postAPIRequest.demographicsProfiles(testData.getBasicURI(), headerConfig.defaultHeader());
		Assert.assertEquals(demographicsId, "26854", "Demographics Id is wrong");
	}

	@Test
	public void patientMatchGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		String accessToken = postAPIRequest.createToken(testData.getAccessTokenURL());
		testData.setAccessToken(accessToken);
		log("Base URL is  ---> " + testData.getBasicURI());
		log("Access Token --> " + testData.getAccessToken());
		postAPIRequest.matchPatient(testData.getBasicURI(), headerConfig.HeaderwithToken(testData.getAccessToken()));
	}

	@Test
	public void flowIdentityGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		String accessToken = postAPIRequest.createToken(testData.getAccessTokenURL());
		testData.setAccessToken(accessToken);
		log("Base URL is  ---> " + testData.getBasicURI());
		log("Access Token --> " + testData.getAccessToken());
		postAPIRequest.flowIdentity(testData.getBasicURI(), headerConfig.HeaderwithToken(testData.getAccessToken()));
	}

	@Test
	public void genderMappingGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		String accessToken = postAPIRequest.createToken(testData.getAccessTokenURL());
		testData.setAccessToken(accessToken);
		log("Base URL is  ---> " + testData.getBasicURI());
		log("Access Token --> " + testData.getAccessToken());
		postAPIRequest.genderMapping(testData.getBasicURI(), headerConfig.HeaderwithToken(testData.getAccessToken()));
	}

	@Test
	public void getStatesGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		String accessToken = postAPIRequest.createToken(testData.getAccessTokenURL());
		testData.setAccessToken(accessToken);
		log("Base URL is   ---> " + testData.getBasicURI());
		log("Access Token --> " + testData.getAccessToken());
		postAPIRequest.getStates(testData.getBasicURI(), headerConfig.HeaderwithToken(testData.getAccessToken()));
	}

	@Test
	public void patientDemographicsGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		String accessToken = postAPIRequest.createToken(testData.getAccessTokenURL());
		testData.setAccessToken(accessToken);
		log("Base URL is  ---> " + testData.getBasicURI());
		log("Access Token --> " + testData.getAccessToken());
		postAPIRequest.patientDemographics(testData.getBasicURI(), headerConfig.HeaderwithToken(testData.getAccessToken()));
		log("Verifying the Demographics Id");
		String demographicsId = postAPIRequest.patientDemographics(testData.getBasicURI(), headerConfig.HeaderwithToken(testData.getAccessToken()));
		Assert.assertEquals(demographicsId, "26854", "Demographics Id is wrong");
	}

	@Test
	public void validateProviderLinkPost() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		PayloadPssPatientModulator payloadPatientMod = new PayloadPssPatientModulator();
		String accessToken = postAPIRequest.createToken(testData.getAccessTokenURL());
		testData.setAccessToken(accessToken);
		log("Base URL is  ---> " + testData.getBasicURI());
		log("Access Token --> " + testData.getAccessToken());
		log("Payload- " + payloadPatientMod.validateProviderLinkPayload());
		postAPIRequest.validateProviderLink(testData.getBasicURI(), payloadPatientMod.validateProviderLinkPayload(),
				headerConfig.HeaderwithToken(testData.getAccessToken()));
		log("Verifying the Provider link Id");
		int providerLinkId = postAPIRequest.validateProviderLink(testData.getBasicURI(), payloadPatientMod.validateProviderLinkPayload(),
				headerConfig.HeaderwithToken(testData.getAccessToken()));
		Assert.assertEquals(providerLinkId, 205300, "Demographics Id");
	}

	@Test
	public void locationsByNextAvailablePost() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		PayloadPssPatientModulator payloadPatientMod = new PayloadPssPatientModulator();
		String accessToken = postAPIRequest.createToken(testData.getAccessTokenURL());
		testData.setAccessToken(accessToken);
		log("Base URL is  ---> " + testData.getBasicURI());
		log("Access Token --> " + testData.getAccessToken());
		log("Payload- " + payloadPatientMod.locationsByNextAvailablePayload());
		postAPIRequest.locationsByNextAvailable(testData.getBasicURI(), payloadPatientMod.locationsByNextAvailablePayload(),
				headerConfig.HeaderwithToken(testData.getAccessToken()));
	}

	@Test
	public void locationsByRulePost() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		PayloadPssPatientModulator payloadPatientMod = new PayloadPssPatientModulator();
		String accessToken = postAPIRequest.createToken(testData.getAccessTokenURL());
		testData.setAccessToken(accessToken);
		log("Base URL is  ---> " + testData.getBasicURI());
		log("Access Token --> " + testData.getAccessToken());
		log("Payload- " + payloadPatientMod.locationsByRulePayload());
		postAPIRequest.locationsByRule(testData.getBasicURI(), payloadPatientMod.locationsByRulePayload(), headerConfig.HeaderwithToken(testData.getAccessToken()));
	}

	@Test
	public void anonymousMatchAndCreatePatientPost() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		PayloadPssPatientModulator payloadPatientMod = new PayloadPssPatientModulator();
		String accessToken = postAPIRequest.createToken(testData.getAccessTokenURL());
		testData.setAccessToken(accessToken);
		log("Base URL is  ---> " + testData.getBasicURI());
		log("Access Token --> " + testData.getAccessToken());
		log("Payload- " + payloadPatientMod.anonymousMatchAndCreatePatientPayload());
		postAPIRequest.anonymousMatchAndCreatePatient(testData.getBasicURI(), payloadPatientMod.anonymousMatchAndCreatePatientPayload(),
				headerConfig.HeaderwithToken(testData.getAccessToken()));
		log("Verifying the patient Id");
		String patientId = postAPIRequest.anonymousMatchAndCreatePatient(testData.getBasicURI(), payloadPatientMod.anonymousMatchAndCreatePatientPayload(),
				headerConfig.HeaderwithToken(testData.getAccessToken()));
		Assert.assertEquals(patientId, "27698", "patient Id is wrong");
	}

	@Test
	public void identifyPatientForReschedulePost() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		PayloadPssPatientModulator payloadPatientMod = new PayloadPssPatientModulator();
		String accessToken = postAPIRequest.createToken(testData.getAccessTokenURL());
		testData.setAccessToken(accessToken);
		log("Base URL is  ---> " + testData.getBasicURI());
		log("Access Token --> " + testData.getAccessToken());
		log("Payload- " + payloadPatientMod.identifyPatientForReschedulePayload());
		postAPIRequest.identifyPatientForReschedule(testData.getBasicURI(), payloadPatientMod.identifyPatientForReschedulePayload(),
				headerConfig.HeaderwithToken(testData.getAccessToken()));
		log("Verifying the patient Id");
		String patientId = postAPIRequest.identifyPatientForReschedule(testData.getBasicURI(), payloadPatientMod.identifyPatientForReschedulePayload(),
				headerConfig.HeaderwithToken(testData.getAccessToken()));
		Assert.assertEquals(patientId, "27699", "patient Id is wrong");
	}

	@Test
	public void specialtyByRulePost() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		PayloadPssPatientModulator payloadPatientMod = new PayloadPssPatientModulator();
		String accessToken = postAPIRequest.createToken(testData.getAccessTokenURL());
		testData.setAccessToken(accessToken);
		log("Base URL is  ---> " + testData.getBasicURI());
		log("Access Token --> " + testData.getAccessToken());
		log("Payload- " + payloadPatientMod.specialtyByRulePayload());
		postAPIRequest.specialtyByRule(testData.getBasicURI(), payloadPatientMod.specialtyByRulePayload(), headerConfig.HeaderwithToken(testData.getAccessToken()));
	}

	@Test
	public void createTokenPost() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		PayloadPssPatientModulator payloadPatientMod = new PayloadPssPatientModulator();
		String accessToken = postAPIRequest.createToken(testData.getAccessTokenURL());
		log("Base URL is  ---> " + testData.getBasicURI());
		log("Payload- " + payloadPatientMod.createTokenPayload(accessToken));
		postAPIRequest.createToken(testData.getBasicURI(), payloadPatientMod.createTokenPayload(accessToken),
				headerConfig.HeaderwithToken(testData.getAccessToken()));
	}

	@Test
	public void locationsBasedOnZipcodeAndRadiusPost() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		PayloadPssPatientModulator payloadPatientMod = new PayloadPssPatientModulator();
		String accessToken = postAPIRequest.createToken(testData.getAccessTokenURL());
		testData.setAccessToken(accessToken);
		log("Base URL is  ---> " + testData.getBasicURI());
		log("Access Token --> " + testData.getAccessToken());
		log("Payload- " + payloadPatientMod.locationsBasedOnZipcodeAndRadiusPayload());
		postAPIRequest.locationsBasedOnZipcodeAndRadius(testData.getBasicURI(), payloadPatientMod.locationsBasedOnZipcodeAndRadiusPayload(),
				headerConfig.HeaderwithToken(testData.getAccessToken()));
	}

	@Test
	public void appointmentGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.appointment(testData.getBasicURI(), headerConfig.defaultHeader());
		log("Verifying the patient Id");
		String bookName = postAPIRequest.appointment(testData.getBasicURI(), headerConfig.defaultHeader());
		Assert.assertEquals(bookName, "Jennifer Brown", "patient Id is wrong");
	}

	@Test
	public void appointmentForIcsGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.appointmentForIcs(testData.getBasicURI(), headerConfig.defaultHeader());
	}

	@Test
	public void upcomingAppointmentsByPageGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		String accessToken = postAPIRequest.createToken(testData.getAccessTokenURL());
		testData.setAccessToken(accessToken);
		log("Base URL is  ---> " + testData.getBasicURI());
		log("Access Token --> " + testData.getAccessToken());
		postAPIRequest.upcomingAppointmentsByPage(testData.getBasicURI(), headerConfig.HeaderwithToken(testData.getAccessToken()));
	}

	@Test
	public void insuranceCarrierGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		String accessToken = postAPIRequest.createToken(testData.getAccessTokenURL());
		testData.setAccessToken(accessToken);
		log("Base URL is  ---> " + testData.getBasicURI());
		log("Access Token --> " + testData.getAccessToken());
		postAPIRequest.insuranceCarrier(testData.getBasicURI(), headerConfig.HeaderwithToken(testData.getAccessToken()));
	}

	@Test
	public void cancellationReasonGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		String accessToken = postAPIRequest.createToken(testData.getAccessTokenURL());
		testData.setAccessToken(accessToken);
		log("Base URL is  ---> " + testData.getBasicURI());
		log("Access Token --> " + testData.getAccessToken());
		postAPIRequest.cancellationReason(testData.getBasicURI(), headerConfig.HeaderwithToken(testData.getAccessToken()));
	}

	@Test
	public void rescheduleReasonGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		String accessToken = postAPIRequest.createToken(testData.getAccessTokenURL());
		testData.setAccessToken(accessToken);
		log("Base URL is  ---> " + testData.getBasicURI());
		log("Access Token --> " + testData.getAccessToken());
		postAPIRequest.rescheduleReason(testData.getBasicURI(), headerConfig.HeaderwithToken(testData.getAccessToken()));

	}

	@Test
	public void apptTypeNextAvailablePost() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		PayloadPssPatientModulator payloadPatientMod = new PayloadPssPatientModulator();
		String accessToken = postAPIRequest.createToken(testData.getApptTypeNextAvailableAccessTokenUrl());
		testData.setApptTypeNextAvailableAccessTokenUrl(accessToken);
		log("Base URL is  ---> " + testData.getBasicURI());
		log("Access Token --> " + testData.getApptTypeNextAvailableAccessTokenUrl());
		log("Payload- " + payloadPatientMod.apptTypeNextAvailablePayload());
		postAPIRequest.apptTypeNextAvailable(testData.getBasicURI(), payloadPatientMod.apptTypeNextAvailablePayload(),
				headerConfig.HeaderwithToken(testData.getApptTypeNextAvailableAccessTokenUrl()));
	}

	@Test
	public void booksBynextAvailablePost() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		PayloadPssPatientModulator payloadPatientMod = new PayloadPssPatientModulator();
		String accessToken = postAPIRequest.createToken(testData.getBookByNextAvailableAccessTokenUrl());
		testData.setBookByNextAvailableAccessTokenUrl(accessToken);
		log("Base URL is  ---> " + testData.getBasicURI());
		log("Access Token --> " + testData.getBookByNextAvailableAccessTokenUrl());
		log("Payload- " + payloadPatientMod.booksByNextAvailablePayload());
		postAPIRequest.booksBynextAvailable(testData.getBasicURI(), payloadPatientMod.booksByNextAvailablePayload(),
				headerConfig.HeaderwithToken(testData.getBookByNextAvailableAccessTokenUrl()));
	}

}
