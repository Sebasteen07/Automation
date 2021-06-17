// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientmodulatorapi.test;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.product.object.maps.pss2.page.util.HeaderConfig;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestPatientMod;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

public class PSS2PatientModulatorrAcceptanceTests extends BaseTestNGWebDriver {
	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testApptDetailFromGuidGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		log("Verifying the PracticeId");
		String practiceId = postAPIRequest.apptDetailFromGuid(testData.getBasicURI(), headerConfig.defaultHeader(), testData.getApptDetailGuidId(),
				testData.getApptDetailDisplayName());
		Assert.assertEquals(practiceId, testData.getPracticeId(), "Practice id is wrong");
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeFromGuidAnonymousGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		log("Verifying the Ext Practice Id");
		String extPracticeId = postAPIRequest.practiceFromGuid(testData.getBasicURI(), headerConfig.defaultHeader(), testData.getAnonymousGuidId());
		Assert.assertEquals(extPracticeId, testData.getAnonymousPracticeId(), "Ext Practice Id is wrong");
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testLinksValueGuidGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		log("Verifying the practiceId");
		String practiceId = postAPIRequest.linksValueGuid(testData.getBasicURI(), headerConfig.defaultHeader(), testData.getLinksValueGuidId(),
				testData.getLinksValueGuidPracticeName());
		Assert.assertEquals(practiceId, testData.getPracticeId(), "practice Id is wrong");
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testLinksValueGuidAndPracticeGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		log("Verifying the practiceId");
		String practiceId = postAPIRequest.linksValueGuidAndPractice(testData.getBasicURI(), headerConfig.defaultHeader(),
				testData.getLinksValueGuidId(), testData.getLinksValueGuidPracticeName());
		Assert.assertEquals(practiceId, testData.getPracticeId(), "Practice Id is wrong");
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testLinksDetailGuidGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		log("Verifying the patientId");
		String patientId = postAPIRequest.linksDetailGuid(testData.getBasicURI(), headerConfig.defaultHeader(),testData.getLinksDetailGuidId());
		Assert.assertEquals(patientId, testData.getLinksDetailPatientId(), "Patient Id is wrong");
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testLinksDetailGuidAndPracticeGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		log("Verifying the patientId");
		String patientId = postAPIRequest.linksDetailGuidAndPractice(testData.getBasicURI(), headerConfig.defaultHeader(), testData.getLinksDetailGuidId());
		Assert.assertEquals(patientId, testData.getLinksDetailPatientId(), "Patient Id is wrong");
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testGuidForLogoutPatientGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		log("Verifying the Guid");
		String guidId = postAPIRequest.guidForLogoutPatient(testData.getBasicURI(), headerConfig.defaultHeader(), testData.getPracticeId());
		Assert.assertEquals(guidId, testData.getLogoutguidId(), "guid Id is wrong");
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeFromGuidLoginlessGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		log("Verifying the Ext Practice Id");
		String extPracticeId = postAPIRequest.practiceFromGuidLoginless(testData.getBasicURI(), headerConfig.defaultHeader(),testData.getLoginlessGuidId());
		Assert.assertEquals(extPracticeId, testData.getLoginlessPrcticeId(), "Ext PracticeId is wrong");
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testTokenForLoginlessGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		log("Verifying the Ext Practice Id");
		String extPracticeId = postAPIRequest.tokenForLoginless(testData.getBasicURI(), headerConfig.defaultHeader(), testData.getTokenForLoginlessGuidId());
		Assert.assertEquals(extPracticeId, testData.getPracticeId(), "Ext PracticeId is wrong");
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testHealthGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBaseUrlHealth());
		postAPIRequest.health(testData.getBaseUrlHealth(), headerConfig.defaultHeader());
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testLogoGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.logo(testData.getBasicURI(), headerConfig.defaultHeader(), testData.getPracticeId());
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testTimezonePracticeResourceGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		log("Verifying the practiceId");
		String practiceId = postAPIRequest.timezonePracticeResource(testData.getBasicURI(), headerConfig.defaultHeader(), testData.getPracticeId(),
				testData.getTimezonePracticeName());
		Assert.assertEquals(practiceId, testData.getPracticeId(), "Practice Id is wrong");
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeInfoGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		log("Verifying the Ext Practice Id");
		String extPracticeId =
				postAPIRequest.practiceInfo(testData.getBasicURI(), headerConfig.defaultHeader(), testData.getPracticeId(), testData.getLinksValueGuidPracticeName());
		Assert.assertEquals(extPracticeId, testData.getPracticeId(), "Ext PracticeId is wrong");
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testResellerLogoGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.resellerLogo(testData.getBasicURI(), headerConfig.defaultHeader(), testData.getPracticeId());
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testSessionConfigurationGE1() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		log("Verifying the Token Expiration Time");
		int tokenExpirationTime = postAPIRequest.sessionConfiguration(testData.getBasicURI(), headerConfig.defaultHeader(), testData.getPracticeId());
		String expirationTime = Integer.toString(tokenExpirationTime);
		Assert.assertEquals(expirationTime, testData.getSessionConfigurationExpirationTime(), "Token Expiration Time is wrong");
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeDetailGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		log("Verifying the Ext Practice Id");
		String extPracticeId = postAPIRequest.practiceDetail(testData.getBasicURI(), headerConfig.defaultHeader(), testData.getPracticeId(),
				testData.getLinksValueGuidPracticeName());
		Assert.assertEquals(extPracticeId, testData.getPracticeId(), "Ext PracticeId is wrong");
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeFromGuidSsoGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		log("Verifying the Ext Practice Id");
		String extPracticeId = postAPIRequest.practiceFromGuidSso(testData.getBasicURI(), headerConfig.defaultHeader(),testData.getPracticeFromGuidSsoId());
		Assert.assertEquals(extPracticeId, testData.getPracticeSsoId(), "Ext PracticeId is wrong");
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testTimeZoneResourceGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.timeZoneResource(testData.getBasicURI(), headerConfig.defaultHeader(), testData.getPatientId());
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testCreateTokenGET() throws IOException {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		String accessToken = postAPIRequest.createToken(testData.getAccessTokenURL());
		testData.setAccessToken(accessToken);
		log("The Accesssc Token is From the Test Method  " + testData.getAccessToken(), testData.getPracticeId());
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testUpcomingConfigurationGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.upcomingConfiguration(testData.getBasicURI(), headerConfig.defaultHeader(), testData.getPracticeId());
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testGetApptDetailPOST() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		PayloadPssPatientModulator payloadPatientMod = new PayloadPssPatientModulator();
		log("Base URL is   " + testData.getBasicURI());
		log("Payload- " + payloadPatientMod.getApptDetailPayload());
		postAPIRequest.getapptDetail(testData.getBasicURI(), payloadPatientMod.getApptDetailPayload(), headerConfig.defaultHeader(),
				testData.getPracticeId(), testData.getApptDetailLocationDisplayName(), testData.getApptDetailAppointmentTypeName());
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementByNameGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.announcementByName(testData.getBasicURI(), headerConfig.defaultHeader(), testData.getPracticeId());
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementByLanguageGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.announcementByLanguage(testData.getBasicURI(), headerConfig.defaultHeader(), testData.getPracticeId());
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementTypeGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.announcementType(testData.getBasicURI(), headerConfig.defaultHeader());
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testGetImagesGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.getImages(testData.getBasicURI(), headerConfig.defaultHeader(), testData.getPracticeId(), testData.getGetImagesBookId());
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testGetLanguagesGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.getLanguages(testData.getBasicURI(), headerConfig.defaultHeader(), testData.getPatientId());
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testDemographicsProfilesGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		log("Verifying the Demographics Id");
		String demographicsId = postAPIRequest.demographicsProfiles(testData.getBasicURI(), headerConfig.defaultHeader(), testData.getPracticeId(),
				testData.getPatientId(), testData.getLinksValueGuidPracticeName());
		Assert.assertEquals(demographicsId, testData.getPatientId(), "Demographics Id is wrong");
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientMatchGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		String accessToken = postAPIRequest.createToken(testData.getAccessTokenURL());
		testData.setAccessToken(accessToken);
		log("Base URL is  ---> " + testData.getBasicURI());
		log("Access Token --> " + testData.getAccessToken());
		postAPIRequest.matchPatient(testData.getBasicURI(), headerConfig.HeaderwithToken(testData.getAccessToken()), testData.getPracticeId(),
				testData.getPatientId());
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testFlowIdentityGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		String accessToken = postAPIRequest.createToken(testData.getAccessTokenURL());
		testData.setAccessToken(accessToken);
		log("Base URL is  ---> " + testData.getBasicURI());
		log("Access Token --> " + testData.getAccessToken());
		postAPIRequest.flowIdentity(testData.getBasicURI(), headerConfig.HeaderwithToken(testData.getAccessToken()), testData.getPracticeId(),
				testData.getPatientId());
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testGenderMappingGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		String accessToken = postAPIRequest.createToken(testData.getAccessTokenURL());
		testData.setAccessToken(accessToken);
		log("Base URL is  ---> " + testData.getBasicURI());
		log("Access Token --> " + testData.getAccessToken());
		postAPIRequest.genderMapping(testData.getBasicURI(), headerConfig.HeaderwithToken(testData.getAccessToken()), testData.getPracticeId(),
				testData.getPatientId());
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testGetStatesGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		String accessToken = postAPIRequest.createToken(testData.getAccessTokenURL());
		testData.setAccessToken(accessToken);
		log("Base URL is   ---> " + testData.getBasicURI());
		log("Access Token --> " + testData.getAccessToken());
		postAPIRequest.getStates(testData.getBasicURI(), headerConfig.HeaderwithToken(testData.getAccessToken()), testData.getPracticeId(),
				testData.getPatientId());
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientDemographicsGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		String accessToken = postAPIRequest.createToken(testData.getAccessTokenURL());
		testData.setAccessToken(accessToken);
		log("Base URL is  ---> " + testData.getBasicURI());
		log("Access Token --> " + testData.getAccessToken());
		log("Verifying the Demographics Id");
		String demographicsId = postAPIRequest.patientDemographics(testData.getBasicURI(), headerConfig.HeaderwithToken(testData.getAccessToken()),
				testData.getPracticeId(), testData.getPatientId(), testData.getPatientDemographicsFirstName());
		Assert.assertEquals(demographicsId, testData.getPatientId(), "Demographics Id is wrong");
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testValidateProviderLinkPost() throws IOException {
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
		log("Verifying the Provider link Id");
		int providerLinkId = postAPIRequest.validateProviderLink(testData.getBasicURI(), payloadPatientMod.validateProviderLinkPayload(),
				headerConfig.HeaderwithToken(testData.getAccessToken()), testData.getPracticeId(), testData.getPatientId(),
				testData.getValidateProviderLinkDisplayName());
		String linkId = Integer.toString(providerLinkId);
		Assert.assertEquals(linkId, testData.getValidateProviderLinkId(), "Link id is wrong");
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationsByNextAvailablePost() throws IOException {
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
		int practiceId = postAPIRequest.locationsByNextAvailable(testData.getBasicURI(), payloadPatientMod.locationsByNextAvailablePayload(),
				headerConfig.HeaderwithToken(testData.getAccessToken()), testData.getPracticeId(), testData.getPatientId(), testData.getLocationsByNextAvailableId());
		String locationPracticeId = Integer.toString(practiceId);
		Assert.assertEquals(locationPracticeId, testData.getLocationsByNextAvailableId(), "location practice id is wrong");
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationsByRulePost() throws IOException {
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
		postAPIRequest.locationsByRule(testData.getBasicURI(), payloadPatientMod.locationsByRulePayload(), headerConfig.HeaderwithToken(testData.getAccessToken()),
				testData.getPracticeId(), testData.getPatientId());
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAnonymousMatchAndCreatePatientPost() throws IOException {
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
		log("Verifying the patient Id");
		String patientId = postAPIRequest.anonymousMatchAndCreatePatient(testData.getBasicURI(), payloadPatientMod.anonymousMatchAndCreatePatientPayload(),
				headerConfig.HeaderwithToken(testData.getAccessToken()), testData.getPracticeId(), testData.getPatientId());
		Assert.assertEquals(patientId, testData.getPatientId(), "patient Id is wrong");
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testIdentifyPatientForReschedulePost() throws IOException {
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
		log("Verifying the patient Id");
		String patientId = postAPIRequest.identifyPatientForReschedule(testData.getBasicURI(), payloadPatientMod.identifyPatientForReschedulePayload(),
				headerConfig.HeaderwithToken(testData.getAccessToken()), testData.getPracticeId(), testData.getPatientId());
		Assert.assertEquals(patientId, testData.getPatientId(), "patient Id is wrong");
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialtyByRulePost() throws IOException {
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
		postAPIRequest.specialtyByRule(testData.getBasicURI(), payloadPatientMod.specialtyByRulePayload(), headerConfig.HeaderwithToken(testData.getAccessToken()),
				testData.getPracticeId(), testData.getSpecialtyByRulePatientId());
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testCreateTokenPost() throws IOException {
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
				headerConfig.HeaderwithToken(testData.getAccessToken()), testData.getPracticeId());
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationsBasedOnZipcodeAndRadiusPost() throws IOException {
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
				headerConfig.HeaderwithToken(testData.getAccessToken()), testData.getPracticeId(), testData.getPatientId());
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		log("Verifying the patient Id");
		String bookName = postAPIRequest.appointment(testData.getBasicURI(), headerConfig.defaultHeader(), testData.getPatientId(),
				testData.getAppointmentId(), testData.getPatientId(), testData.getAppointmentLocationName());
		Assert.assertEquals(bookName, testData.getAppointmentPracticeName(), "practice Name is wrong");
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentForIcsGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.appointmentForIcs(testData.getBasicURI(), headerConfig.defaultHeader(), testData.getPracticeId(), testData.getAppointmentId());
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testUpcomingAppointmentsByPageGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		String accessToken = postAPIRequest.createToken(testData.getAccessTokenURL());
		testData.setAccessToken(accessToken);
		log("Base URL is  ---> " + testData.getBasicURI());
		log("Access Token --> " + testData.getAccessToken());
		postAPIRequest.upcomingAppointmentsByPage(testData.getBasicURI(), headerConfig.HeaderwithToken(testData.getAccessToken()),
				testData.getPracticeId(), testData.getAppointmentId());
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testInsuranceCarrierGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		String accessToken = postAPIRequest.createToken(testData.getAccessTokenURL());
		testData.setAccessToken(accessToken);
		log("Base URL is  ---> " + testData.getBasicURI());
		log("Access Token --> " + testData.getAccessToken());
		postAPIRequest.insuranceCarrier(testData.getBasicURI(), headerConfig.HeaderwithToken(testData.getAccessToken()), testData.getPracticeId(),
				testData.getPatientId());
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testCancellationReasonGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		String accessToken = postAPIRequest.createToken(testData.getAccessTokenURL());
		testData.setAccessToken(accessToken);
		log("Base URL is  ---> " + testData.getBasicURI());
		log("Access Token --> " + testData.getAccessToken());
		postAPIRequest.cancellationReason(testData.getBasicURI(), headerConfig.HeaderwithToken(testData.getAccessToken()),
				testData.getPracticeId(), testData.getPatientId());
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testRescheduleReasonGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		String accessToken = postAPIRequest.createToken(testData.getAccessTokenURL());
		testData.setAccessToken(accessToken);
		log("Base URL is  ---> " + testData.getBasicURI());
		log("Access Token --> " + testData.getAccessToken());
		postAPIRequest.rescheduleReason(testData.getBasicURI(), headerConfig.HeaderwithToken(testData.getAccessToken()), testData.getPracticeId(),
				testData.getPatientId());

	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testApptTypeNextAvailablePost() throws IOException {
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
		int nextAvailableId = postAPIRequest.apptTypeNextAvailable(testData.getBasicURI(), payloadPatientMod.apptTypeNextAvailablePayload(),
				headerConfig.HeaderwithToken(testData.getApptTypeNextAvailableAccessTokenUrl()), testData.getApptTypeNextAvailablePracticeId(),
				testData.getApptTypeNextAvailablePatientId(), testData.getApptTypeNextAvailableId());
		String slotId = Integer.toString(nextAvailableId);
		Assert.assertEquals(slotId, testData.getApptTypeNextAvailableId(), "Next Available slotId id is wrong");
	}

	@Test(enabled = true, groups = {"APItest"}, retryAnalyzer = RetryAnalyzer.class)
	public void testBooksByNextAvailablePost() throws IOException {
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
		int bookByNextAvailableId = postAPIRequest.booksBynextAvailable(testData.getBasicURI(), payloadPatientMod.booksByNextAvailablePayload(),
				headerConfig.HeaderwithToken(testData.getBookByNextAvailableAccessTokenUrl()), testData.getBooksBynextAvailablePracticeId(),
				testData.getBooksBynextAvailablePatientId(), testData.getBooksBynextAvailableId());
		String slotId = Integer.toString(bookByNextAvailableId);
		Assert.assertEquals(slotId, testData.getBooksBynextAvailableId(), "Next Available slotId id is wrong");
	}

}
