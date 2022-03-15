//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.practice.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.practice.page.apptrequest.ApptRequestSearchPage;
import com.medfusion.product.object.maps.practice.page.askstaff.AskAStaffSearchPage;
import com.medfusion.product.object.maps.practice.page.customform.SearchPatientFormsPage;
import com.medfusion.product.object.maps.practice.page.familyManagement.AgeOutReportPage;
import com.medfusion.product.object.maps.practice.page.onlinebillpay.OnlineBillPaySearchPage;
import com.medfusion.product.object.maps.practice.page.onlinebillpay.PayMyBillOnlinePage;
import com.medfusion.product.object.maps.practice.page.patientMessaging.PatientMessagingPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientSearchPage;
import com.medfusion.product.object.maps.practice.page.patientactivation.PatientActivationPage;
import com.medfusion.product.object.maps.practice.page.referrals.ReferralsPage;
import com.medfusion.product.object.maps.practice.page.rxrenewal.RxRenewalSearchPage;
import com.medfusion.product.object.maps.practice.page.symptomassessment.SymptomAssessmentFilterPage;
import com.medfusion.product.object.maps.practice.page.treatmentplanpage.TreatmentPlansPage;
import com.medfusion.product.object.maps.practice.page.virtualCardSwiper.VirtualCardSwiperPage;
import com.medfusion.product.object.maps.practice.page.virtualofficevisit.VirtualOfficeVisitSearchPage;

public class PracticeHomePage extends BasePageObject {

	public static final String PAGE_NAME = "Practice Home Page";

	@FindBy(xpath = ".//*[@id='pagetitle']/h1")
	private WebElement homePageTitle;
	private final String homePageTitleText = "My Home";

	@FindBy(xpath = ".//a[contains(@title,'Sign Out')]")
	private WebElement signOut;

	/*
	 * Solution tabs
	 */
	@FindBy(xpath = ".//a[contains(@href,'askadoc')]")
	private WebElement askAStaffTab;

	@FindBy(xpath = "//div[@id='navigation']/ul/li/a")
	private WebElement apptRequestTab;

	@FindBy(xpath = ".//a[contains(@href,'vov')]")
	private WebElement virtualOfficeVisitTab;

	@FindBy(xpath = ".//a[contains(@href, 'home.forms2')]")
	private WebElement customform;

	@FindBy(xpath = ".//a[contains(@href, 'home.imh')]")
	private WebElement symptomAssessmentTab;

	@FindBy(xpath = ".//a[contains(@href, 'home.paymybill')]")
	private WebElement onlineBillPay;

	@FindBy(xpath = ".//a[contains(@href, 'home.rx')]")
	private WebElement rxRenewal;

	@FindBy(xpath = ".//a[contains(@href, 'home.docs')]")
	private WebElement docManagement;

	@FindBy(xpath = ".//a[contains(@href, 'home.fsu')]")
	private WebElement fileSharing;

	@FindBy(xpath = ".//a[contains(@href, 'home.activate')]")
	private WebElement patientactivation;

	@FindBy(xpath = ".//a[contains(@href, 'home.tplan')]")
	private WebElement treatmentPlanTab;

	@FindBy(xpath = ".//a[contains(@href, 'home.vcs')]")
	private WebElement virtualCardSwiper;

	@FindBy(linkText = "Patient Search")
	private WebElement patientSearchLinkText;

	@FindBy(xpath = ".//a[contains(@href, 'home.em')]")
	private WebElement patientMessaging;

	@FindBy(linkText = "Quick Send")
	private WebElement quickSendLinkText;

	@FindBy(linkText = "Build a Message")
	private WebElement buildaMessageLinkText;

	@FindBy(css = "a[href*='home.vcs']")
	private WebElement virtualCardSwiperTab;

	@FindBy(css = "a[href*='home.fsu']")
	private WebElement fileSharingTab;

	@FindBy(xpath = ".//a[contains(@href, 'paymybill.srch')]")
	private WebElement makePaymentForPatient;

	@FindBy(xpath = ".//a[contains(@href, 'home.fa')]")
	private WebElement familyManagement;

	@FindBy(xpath = ".//a[contains(@href, 'home.referral')]")
	private WebElement referralsTab;

	@FindBy(xpath = "//a[text()='Budget Payment Plan Search']")
	private WebElement budgetPaymentPlanSearch;

	public PracticeHomePage(WebDriver driver) {
		super(driver);
	}

	public boolean isHomePageLoaded() {
		IHGUtil.PrintMethodName();

		boolean result = false;
		try {
			result = homePageTitle.getText().equalsIgnoreCase(homePageTitleText);
		} catch (Exception e) {
			// just catch any element not found errors
		}

		return result;
	}

	public PracticeLoginPage logOut() {
		IHGUtil.PrintMethodName();
		IHGUtil.setDefaultFrame(driver);

		signOut.click();
		return PageFactory.initElements(driver, PracticeLoginPage.class);
	}

	public AskAStaffSearchPage clickAskAStaffTab() throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 60, askAStaffTab);

		try {
			askAStaffTab.isDisplayed();
		} catch (Exception e) {
			// Helpful message about possible issues
			throw new Exception("Either there was a timeout on loading the page, "
					+ "the staff member is not permissioned for 'Ask A Question' solution,"
					+ " or there was an error on login");
		}

		askAStaffTab.click();
		return PageFactory.initElements(driver, AskAStaffSearchPage.class);
	}

	public OnlineBillPaySearchPage clickOnlineBillPayTab() throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 60, onlineBillPay);

		try {
			onlineBillPay.isDisplayed();
		} catch (Exception e) {
			// Helpful message about possible issues
			throw new Exception("Either there was a timeout on loading the page, "
					+ "the staff member is not permissioned for 'Online Bill Pay' solution,"
					+ " or there was an error on login");
		}

		onlineBillPay.click();
		return PageFactory.initElements(driver, OnlineBillPaySearchPage.class);
	}

	public PatientActivationPage clickPatientactivationTab() throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, patientactivation);

		try {
			patientactivation.isDisplayed();
		} catch (Exception e) {
			// Helpful message about possible issues
			throw new Exception("Either there was a timeout on loading the page, "
					+ "the staff member is not permissioned for 'Online Bill Pay' solution,"
					+ " or there was an error on login");
		}

		patientactivation.click();
		return PageFactory.initElements(driver, PatientActivationPage.class);
	}

	public VirtualCardSwiperPage clickVirtualCardSwiperTab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, virtualCardSwiperTab);

		try {
			virtualCardSwiperTab.isDisplayed();
		} catch (WebDriverException e) {
			// Helpful message about possible issues
			throw new WebDriverException(
					"Either there was a timeout on loading the page, " + " or virtualCardSwiperTab not found");
		}

		virtualCardSwiperTab.click();
		return PageFactory.initElements(driver, VirtualCardSwiperPage.class);
	}

	public PatientActivationPage clickFileSharingTab() throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, fileSharingTab);

		try {
			fileSharingTab.isDisplayed();
		} catch (Exception e) {
			// Helpful message about possible issues
			throw new Exception("Either there was a timeout on loading the page, " + " or fileSharingTab not found");
		}

		fileSharingTab.click();
		return PageFactory.initElements(driver, PatientActivationPage.class);
	}

	public RxRenewalSearchPage clickonRxRenewal() throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 60, rxRenewal);

		try {
			rxRenewal.isDisplayed();
		} catch (Exception e) {
			// Helpful message about possible issues
			throw new Exception("Either there was a timeout on loading the page, "
					+ "the staff member is not permissioned for 'Rx Renewal' solution,"
					+ " or there was an error on login");
		}

		rxRenewal.click();
		return PageFactory.initElements(driver, RxRenewalSearchPage.class);
	}

	public ReferralsPage clickOnReferrals() throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElementInDefaultFrame(driver, 30, referralsTab);
		referralsTab.click();
		return PageFactory.initElements(driver, ReferralsPage.class);
	}

	public ApptRequestSearchPage clickApptRequestTab() throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 60, apptRequestTab);

		try {
			apptRequestTab.isDisplayed();
		} catch (Exception e) {
			// Helpful message about possible issues
			throw new Exception("Either there was a timeout on loading the page, "
					+ "the staff member is not permissioned for 'Appt Request' solution,"
					+ " or there was an error on login");
		}

		apptRequestTab.click();
		return PageFactory.initElements(driver, ApptRequestSearchPage.class);
	}

	public VirtualOfficeVisitSearchPage clickVirtualOfficeVisitTab() throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, virtualOfficeVisitTab);

		try {
			virtualOfficeVisitTab.isDisplayed();
		} catch (Exception e) {
			// Helpful message about possible issues
			throw new Exception("Either there was a timeout on loading the page, "
					+ "the staff member is not permissioned for 'Virtual Office Visit' solution,"
					+ " or there was an error on login");
		}

		virtualOfficeVisitTab.click();
		return PageFactory.initElements(driver, VirtualOfficeVisitSearchPage.class);
	}

	public SearchPatientFormsPage clickCustomFormTab() throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, customform);

		try {
			customform.isDisplayed();
		} catch (Exception e) {
			// Helpful message about possible issues
			throw new Exception("Either there was a timeout on loading the page, " + " or there was an error on login");
		}
		customform.click();
		return PageFactory.initElements(driver, SearchPatientFormsPage.class);
	}

	public SymptomAssessmentFilterPage clicksymptomAssessmentTab() throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, symptomAssessmentTab);

		try {
			symptomAssessmentTab.isDisplayed();
		} catch (Exception e) {
			// Helpful message about possible issues
			throw new Exception("Either there was a timeout on loading the page, " + " or there was an error on login");
		}
		symptomAssessmentTab.click();
		return PageFactory.initElements(driver, SymptomAssessmentFilterPage.class);
	}

	public boolean verifyAptRequestTab() {
		IHGUtil.PrintMethodName();
		IHGUtil util = new IHGUtil(driver);
		IHGUtil.waitForElement(driver, 20, apptRequestTab);
		return util.isRendered(this.apptRequestTab);

	}

	public TreatmentPlansPage clickOnManageTreatmentPlan() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElementInDefaultFrame(driver, 30, treatmentPlanTab);
		treatmentPlanTab.click();
		return PageFactory.initElements(driver, TreatmentPlansPage.class);
	}

	public VirtualCardSwiperPage clickOnVirtualCardSwiper() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, virtualCardSwiper);
		virtualCardSwiper.click();
		return PageFactory.initElements(driver, VirtualCardSwiperPage.class);
	}

	public PatientSearchPage clickPatientSearchLink() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElementInDefaultFrame(driver, 20, patientSearchLinkText);
		patientSearchLinkText.click();
		return PageFactory.initElements(driver, PatientSearchPage.class);

	}

	public PatientMessagingPage clickPatientMessagingTab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElementInDefaultFrame(driver, 20, patientMessaging);
		patientMessaging.click();
		IHGUtil.waitForElementInDefaultFrame(driver, 10, quickSendLinkText);
		quickSendLinkText.click();
		return PageFactory.initElements(driver, PatientMessagingPage.class);
	}

	public PatientMessagingPage clickPatienBuildtMessagingTab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElementInDefaultFrame(driver, 20, patientMessaging);
		patientMessaging.click();
		IHGUtil.waitForElementInDefaultFrame(driver, 10, buildaMessageLinkText);
		buildaMessageLinkText.click();
		return PageFactory.initElements(driver, PatientMessagingPage.class);
	}

	public PayMyBillOnlinePage clickMakePaymentForPatient() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElementInDefaultFrame(driver, 20, onlineBillPay);
		onlineBillPay.click();
		IHGUtil.waitForElementInDefaultFrame(driver, 10, makePaymentForPatient);
		makePaymentForPatient.click();
		return PageFactory.initElements(driver, PayMyBillOnlinePage.class);
	}

	public AgeOutReportPage clickFamilyManagementTab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElementInDefaultFrame(driver, 20, familyManagement);
		familyManagement.click();
		return PageFactory.initElements(driver, AgeOutReportPage.class);
	}

	public void budgetPaymentPlanSearch() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElementInDefaultFrame(driver, 20, budgetPaymentPlanSearch);
		budgetPaymentPlanSearch.click();
	}
}
