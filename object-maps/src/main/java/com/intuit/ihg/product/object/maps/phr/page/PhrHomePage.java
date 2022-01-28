//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.phr.page;

import java.io.IOException;
import java.net.URISyntaxException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.downloads.RequestMethod;
import com.intuit.ihg.common.utils.downloads.URLStatusChecker;
import com.intuit.ihg.product.object.maps.phr.page.messages.PhrMessagesPage;
import com.intuit.ihg.product.object.maps.phr.page.phrHealthInformationPage.PhrHealthInformationPage;
import com.intuit.ihg.product.object.maps.phr.page.phrRegistrationInformationPage.PhrRegistrationInformationPage;
import com.intuit.ihg.product.object.maps.phr.page.profile.PhrProfilePage;
import com.intuit.ihg.product.phr.utils.PhrUtil;

public class PhrHomePage extends BasePageObject {

	public static final String PAGE_NAME = "PHR HOME Page";

	@FindBy(xpath = "//em[text()='Log out']")
	private WebElement btnLogout;

	@FindBy(xpath = "//em[text()='Profile']")
	private WebElement btnProfile;

	@FindBy(xpath = "//em[text()='Health Information']")
	private WebElement btnHealthInformation;

	@FindBy(xpath = "//em[text()='Sharing']")
	private WebElement btnSharing;

	@FindBy(xpath = "//em[text()='Documents']")
	private WebElement btnDocuments;

	@FindBy(xpath = ".//div[@id='bluebutton']/p[1]/a")
	private WebElement btnBlueButtonDownloadPdf;

	@FindBy(id = "bluebuttontxtlink")
	private WebElement btnBlueButtonDownloadtext;

	@FindBy(linkText = "Medications")
	private WebElement Medicationlnktext;

	@FindBy(xpath = "//a[contains(@href,'/phr/ui/action/ihr/medication.do')]")
	private WebElement HealthInformation;

	@FindBy(xpath = "//div[@class='survey_dialog_buttons']/button[2]")
	private WebElement survey;

	@FindBy(xpath = "//a[contains(@href,'/phr/ui/action/ihr/registration.do')]")
	private WebElement profile;

	@FindBy(xpath = ".//*[@id='healthinfo']/a")
	private WebElement helathInformationMenu;

	@FindBy(linkText = "Surgeries and Procedures")
	private WebElement surgeriesandProcedureslnktext;

	@FindBy(linkText = "Immunizations")
	private WebElement immunizationslnktext;

	@FindBy(linkText = "View all messages")
	private WebElement viewAllMessageslnktext;

	@FindBy(xpath = "//em[starts-with(text(), 'Messages')]")
	private WebElement btnMessages;

	public PhrHomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public boolean isSearchPageLoaded() {

		IHGUtil.PrintMethodName();
		log("Waiting for the element btnProfile, max wait time is 60 seconds");
		return IHGUtil.waitForElement(driver, 60, btnProfile);

	}

	public PhrLoginPage clickLogout() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 60, btnLogout);
		btnLogout.click();
		return PageFactory.initElements(driver, PhrLoginPage.class);
	}

	public boolean clearSurveyIfAny() throws InterruptedException {

		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 4, survey);
		if (survey.isDisplayed()) {
			survey.click();
			return true;
		}

		return false;
	}

	public boolean waitforbtnProfile(WebDriver driver, int n) throws InterruptedException {
		IHGUtil.PrintMethodName();
		return IHGUtil.waitForElement(driver, n, btnProfile);
	}

	public int clickBlueButtonDownloadPdf() throws InterruptedException, URISyntaxException, IOException {
		IHGUtil.PrintMethodName();
		return validateBlueButtonDownload(btnBlueButtonDownloadPdf.getAttribute("href"), RequestMethod.GET);
	}

	public int clickBlueButtonDownloadtext() throws InterruptedException, URISyntaxException, IOException {
		IHGUtil.PrintMethodName();
		return validateBlueButtonDownload(btnBlueButtonDownloadtext.getAttribute("href"), RequestMethod.GET);
	}

	private int validateBlueButtonDownload(String url, RequestMethod method) throws URISyntaxException, IOException {
		URLStatusChecker urlChecker = new URLStatusChecker(driver);

		urlChecker.setURIToCheck(url);
		urlChecker.setHTTPRequestMethod(RequestMethod.GET);
		urlChecker.mimicWebDriverCookieState(true);

		return urlChecker.getHTTPStatusCode();
	}

	public PhrProfilePage clickProfileButton() {
		IHGUtil.PrintMethodName();
		btnProfile.click();
		return PageFactory.initElements(driver, PhrProfilePage.class);
	}

	public PhrAlleryPage clickHelthInformation() {
		IHGUtil.PrintMethodName();
		btnHealthInformation.click();
		return PageFactory.initElements(driver, PhrAlleryPage.class);
	}

	public PhrDocumentsPage clickDocuments() {

		IHGUtil.PrintMethodName();

		btnDocuments.click();

		return PageFactory.initElements(driver, PhrDocumentsPage.class);
	}

	public void postCCdRequest(String allScriptAdapterURL) throws Exception {
		PhrUtil pPhrUtil = new PhrUtil(driver);
		pPhrUtil.ccdImportFromAllScripts(IHGUtil.getConsolidatedCCD(), allScriptAdapterURL,
				IHGUtil.getEnvironmentType().toString());
	}

	public void postNonCCdRequest(String allScriptAdapterURL) throws Exception {
		PhrUtil pPhrUtil = new PhrUtil(driver);
		pPhrUtil.ccdImportFromAllScripts(IHGUtil.getNonConsolidatedCCD(), allScriptAdapterURL,
				IHGUtil.getEnvironmentType().toString());
	}

	public void postElektaCCdRequest(String EHDCAdapterURL) throws Exception {
		PhrUtil pPhrUtil = new PhrUtil(driver);
		pPhrUtil.ccdImportFromElekta(IHGUtil.getElektaCCD(), EHDCAdapterURL, IHGUtil.getEnvironmentType().toString());
	}

	public PhrHealthInformationPage clickMedications() {
		PhrUtil.PrintMethodName();
		HealthInformation.click();
		IHGUtil.waitForElement(driver, 10, Medicationlnktext);
		Medicationlnktext.click();
		return PageFactory.initElements(driver, PhrHealthInformationPage.class);
	}

	public PhrRegistrationInformationPage clickProfile() {
		PhrUtil.PrintMethodName();
		profile.click();
		return PageFactory.initElements(driver, PhrRegistrationInformationPage.class);

	}

	public PhrHealthInformationPage clickOnHealthInformationTab() {
		IHGUtil.PrintMethodName();
		log("Navigating to 'Health Information' page");
		try {
			IHGUtil.waitForElement(driver, 30, helathInformationMenu);
			helathInformationMenu.click();
		} catch (Exception e) {
			helathInformationMenu.click();
		}
		return PageFactory.initElements(driver, PhrHealthInformationPage.class);
	}

	public PhrHealthInformationPage clickSurgeriesandProcedures() {
		PhrUtil.PrintMethodName();
		HealthInformation.click();
		IHGUtil.waitForElement(driver, 10, Medicationlnktext);
		surgeriesandProcedureslnktext.click();
		return PageFactory.initElements(driver, PhrHealthInformationPage.class);

	}

	public PhrHealthInformationPage clickImmunizations() {
		PhrUtil.PrintMethodName();
		HealthInformation.click();
		IHGUtil.waitForElement(driver, 10, immunizationslnktext);
		immunizationslnktext.click();
		return PageFactory.initElements(driver, PhrHealthInformationPage.class);
	}

	public PhrSharingPage clickSharing() {
		IHGUtil.PrintMethodName();
		btnSharing.click();
		return PageFactory.initElements(driver, PhrSharingPage.class);
	}

	public PhrMessagesPage clickOnViewAllMessages() {
		IHGUtil.PrintMethodName();
		viewAllMessageslnktext.click();
		return PageFactory.initElements(driver, PhrMessagesPage.class);
	}

	public PhrMessagesPage clickOnMyMessages() {
		IHGUtil.PrintMethodName();
		btnMessages.click();
		return PageFactory.initElements(driver, PhrMessagesPage.class);
	}

}
