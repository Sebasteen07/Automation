package com.intuit.ihg.product.practice.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.practice.page.apptrequest.ApptRequestSearchPage;
import com.intuit.ihg.product.practice.page.askstaff.AskAStaffSearchPage;
import com.intuit.ihg.product.practice.page.customform.SearchPatientFormsPage;
import com.intuit.ihg.product.practice.page.documentManagement.documentManagementpage;
import com.intuit.ihg.product.practice.page.fileSharing.FileSharingUploadPage;
import com.intuit.ihg.product.practice.page.onlinebillpay.OnlineBillPaySearchPage;
import com.intuit.ihg.product.practice.page.onlinebillpay.PayMyBillOnlinePage;
import com.intuit.ihg.product.practice.page.patientMessaging.PatientMessagingPage;
import com.intuit.ihg.product.practice.page.patientSearch.PatientSearchPage;
import com.intuit.ihg.product.practice.page.patientactivation.PatientactivationPage;
import com.intuit.ihg.product.practice.page.rxrenewal.RxRenewalSearchPage;
import com.intuit.ihg.product.practice.page.symptomassessment.SymptomAssessmentFilterPage;
import com.intuit.ihg.product.practice.page.treatmentplanpage.TreatmentPlansPage;
import com.intuit.ihg.product.practice.page.virtualCardSwiper.VirtualCardSwiperPage;
import com.intuit.ihg.product.practice.page.virtualofficevisit.VirtualOfficeVisitSearchPage;

public class PracticeHomePage extends BasePageObject {

	public static final String PAGE_NAME = "Practice Home Page";

	@FindBy(xpath=".//*[@id='pagetitle']/h1")
	private WebElement homePageTitle;
	private final String homePageTitleText = "My Home";

	@FindBy(xpath=".//a[contains(@title,'Sign Out')]")
	private WebElement signOut;

	/*
	 * Solution tabs
	 */
	@FindBy(xpath=".//a[contains(@href,'askadoc')]")
	private WebElement askAStaffTab;

	@FindBy(xpath="//div[@id='navigation']/ul/li/a")
	private WebElement apptRequestTab;

	@FindBy(xpath=".//a[contains(@href,'vov')]")
	private WebElement virtualOfficeVisitTab;

	@FindBy(xpath=".//a[contains(@href, 'home.forms2')]")
	private WebElement customform;

	@FindBy(xpath=".//a[contains(@href, 'home.imh')]")
	private WebElement symptomAssessmentTab;

	@FindBy(xpath=".//a[contains(@href, 'home.paymybill')]")
	private WebElement onlineBillPay;

	@FindBy(xpath=".//a[contains(@href, 'home.rx')]")
	private WebElement rxRenewal;

	@FindBy(xpath=".//a[contains(@href, 'home.docs')]")
	private WebElement docManagement;

	@FindBy(xpath=".//a[contains(@href, 'home.fsu')]")
	private WebElement fileSharing;
	
	@FindBy(xpath=".//a[contains(@href, 'home.activate')]")
	private WebElement patientactivation;

	@FindBy(xpath=".//a[contains(@href, 'home.tplan')]")
	private WebElement treatmentPlanTab;
	
	@FindBy( xpath = ".//a[contains(@href, 'home.vcs')]")
	private WebElement virtualCardSwiper;
	@FindBy(linkText="Patient Search")
	private WebElement patientSearchLinkText;
	
	@FindBy(xpath=".//a[contains(@href, 'home.em')]")
	private WebElement patientMessaging;
	
	@FindBy(linkText="Quick Send")
	private WebElement quickSendLinkText;
	
	@FindBy(css="a[href*='home.vcs']")
	private WebElement virtualCardSwiperTab;

	@FindBy(css="a[href*='home.fsu']")
	private WebElement fileSharingTab;


	@FindBy(xpath=".//a[contains(@href, 'paymybill.srch')]")
	private WebElement makePaymentForPatient;
	
	public PracticeHomePage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Gives indication of whether expected elements are found on the home page.
	 * 
	 * @return true or false
	 */
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

	/**
	 * Will logout the staff member.
	 * 
	 * @return the practice login page
	 * @see PracticeLoginPage
	 */
	public PracticeLoginPage logOut() {
		IHGUtil.PrintMethodName();
		IHGUtil.setDefaultFrame(driver);

		signOut.click();		
		return PageFactory.initElements(driver, PracticeLoginPage.class);
	}

	/**
	 * Will take the staff member to the Ask A Staff search page
	 * where the staff member can search questions that need processing.
	 * 
	 * @return the Ask A Staff search page
	 * @see AskAStaffSearchPage
	 * 
	 * @throws Exception
	 */
	public AskAStaffSearchPage clickAskAStaffTab() throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 60, askAStaffTab);

		try {
			askAStaffTab.isDisplayed();
		} catch (Exception e) {
			// Helpful message about possible issues
			throw new Exception("Either there was a timeout on loading the page, " +
					"the staff member is not permissioned for 'Ask A Question' solution," +
					" or there was an error on login");
		}

		askAStaffTab.click();
		return PageFactory.initElements(driver, AskAStaffSearchPage.class);
	}

	/**
	 * Will take the staff member to the Online Bill Pay search page
	 * where the staff member can search payments that need processing.
	 * 
	 * @return the Online Bill Pay search page
	 * @see OnlineBillPaySearchPage
	 * 
	 * @throws Exception
	 */
	public OnlineBillPaySearchPage clickOnlineBillPayTab() throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 60, onlineBillPay);

		try {
			onlineBillPay.isDisplayed();
		} catch (Exception e) {
			// Helpful message about possible issues
			throw new Exception("Either there was a timeout on loading the page, " +
					"the staff member is not permissioned for 'Online Bill Pay' solution," +
					" or there was an error on login");
		}

		onlineBillPay.click();
		return PageFactory.initElements(driver, OnlineBillPaySearchPage.class);
	}

	/**
	 * Will take the staff member to the Online Bill Pay search page
	 * where the staff member can search payments that need processing.
	 * 
	 * @return the Online Bill Pay search page
	 * @see OnlineBillPaySearchPage
	 * 
	 * @throws Exception
	 */
	public PatientactivationPage clickPatientactivationTab() throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, patientactivation);

		try {
			patientactivation.isDisplayed();
		} catch (Exception e) {
			// Helpful message about possible issues
			throw new Exception("Either there was a timeout on loading the page, " +
					"the staff member is not permissioned for 'Online Bill Pay' solution," +
					" or there was an error on login");
		}

		patientactivation.click();
		return PageFactory.initElements(driver, PatientactivationPage.class);
	}
/**
	 * Will take the staff member to the Online Bill Pay search page
	 * where the staff member can search payments that need processing.
	 * 
	 * @return the Online Bill Pay search page
	 * @see OnlineBillPaySearchPage
	 * 
	 * @throws Exception
	 */
	public PatientactivationPage clickVirtualCardSwiperTab() throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, virtualCardSwiperTab);
		
		try {
			virtualCardSwiperTab.isDisplayed();
		} catch (Exception e) {
			// Helpful message about possible issues
			throw new Exception("Either there was a timeout on loading the page, " +
					" or virtualCardSwiperTab not found");
		}
		
		virtualCardSwiperTab.click();
		return PageFactory.initElements(driver, PatientactivationPage.class);
	}
	
	/**
	 * Will take the staff member to the Online Bill Pay search page
	 * where the staff member can search payments that need processing.
	 * 
	 * @return the Online Bill Pay search page
	 * @see OnlineBillPaySearchPage
	 * 
	 * @throws Exception
	 */
	public PatientactivationPage clickFileSharingTab() throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, fileSharingTab);
		
		try {
			fileSharingTab.isDisplayed();
		} catch (Exception e) {
			// Helpful message about possible issues
			throw new Exception("Either there was a timeout on loading the page, " +
			" or fileSharingTab not found");
		}
		
		fileSharingTab.click();
		return PageFactory.initElements(driver, PatientactivationPage.class);
	}
	/**
	 * Will take the staff member to the Rx Renewal Pay search page
	 * where the staff member can search rx removals that need processing.
	 * 
	 * @return the Online Bill Pay search page
	 * @see OnlineBillPaySearchPage
	 * 
	 * @throws Exception
	 */
	public RxRenewalSearchPage clickonRxRenewal() throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 60, rxRenewal);

		try {
			rxRenewal.isDisplayed();
		} catch (Exception e) {
			// Helpful message about possible issues
			throw new Exception("Either there was a timeout on loading the page, " +
					"the staff member is not permissioned for 'Rx Renewal' solution," +
					" or there was an error on login");
		}

		rxRenewal.click();
		return PageFactory.initElements(driver, RxRenewalSearchPage.class);
	}

	/**
	 * Will take the staff member to the Appt Request search page
	 * where the staff member can search questions that need processing.
	 * 
	 * @return the Appt Request search page
	 * 
	 * @throws Exception
	 */
	public ApptRequestSearchPage clickApptRequestTab() throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 60, apptRequestTab);

		try {
			apptRequestTab.isDisplayed();
		} catch (Exception e) {
			// Helpful message about possible issues
			throw new Exception("Either there was a timeout on loading the page, " +
					"the staff member is not permissioned for 'Appt Request' solution," +
					" or there was an error on login");
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
			throw new Exception("Either there was a timeout on loading the page, " +
					"the staff member is not permissioned for 'Virtual Office Visit' solution," +
					" or there was an error on login");
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
			throw new Exception("Either there was a timeout on loading the page, " +
					" or there was an error on login");	}
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
			throw new Exception("Either there was a timeout on loading the page, " +
					" or there was an error on login");	}
		symptomAssessmentTab.click();
		return PageFactory.initElements(driver, SymptomAssessmentFilterPage.class);
	}

	public boolean verifyAptRequestTab() {
		IHGUtil.PrintMethodName();
		IHGUtil util =new IHGUtil(driver);
		IHGUtil.waitForElement(driver,20,apptRequestTab);
		return util.isRendered(this.apptRequestTab);

	}

	/**
	 * @author bbinisha
	 * @Desc : To Navigate to Document Management Page.
	 * @return
	 */
	public documentManagementpage clickOnDocManagement() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElementInDefaultFrame(driver, 30, docManagement);
		docManagement.click();
		return PageFactory.initElements(driver, documentManagementpage.class);
	}

	/**
	 * @author bbinisha
	 * @Desc : To Navigate to File Sharing Page.
	 * @return
	 */
	public FileSharingUploadPage clickOnFileSharing() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElementInDefaultFrame(driver, 30, fileSharing);
		fileSharing.click();
		return PageFactory.initElements(driver, FileSharingUploadPage.class);
	}

	/**
	 * @author bbinisha
	 * @Desc : To Navigate to Manage Treatment Plan Page.
	 * @return
	 */
	public TreatmentPlansPage clickOnManageTreatmentPlan() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElementInDefaultFrame(driver, 30, treatmentPlanTab);	
		treatmentPlanTab.click();
		return PageFactory.initElements(driver, TreatmentPlansPage.class);
	}

	/**
	 * @author bbinisha
	 * @Desc : To Navigate to Virtual Card Swiper Page.
	 * @return
	 */
	public VirtualCardSwiperPage clickOnVirtualCardSwiper() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, virtualCardSwiper);
		virtualCardSwiper.click();
		return PageFactory.initElements(driver, VirtualCardSwiperPage.class);
	}

/**
	 * @Description:Click Patient Search Link
	 * @return
	 */
	public PatientSearchPage clickPatientSearchLink()
	{
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElementInDefaultFrame(driver,20,patientSearchLinkText);
		patientSearchLinkText.click();
		return PageFactory.initElements(driver, PatientSearchPage.class);
		
	}
	
	/**
	 * @Description:Click Patient Messaging Tab
	 * @return
	 */
	public PatientMessagingPage clickPatientMessagingTab()
	{
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElementInDefaultFrame(driver,20,patientMessaging);
		patientMessaging.click();
		IHGUtil.waitForElementInDefaultFrame(driver,10,quickSendLinkText);
		quickSendLinkText.click();
		return PageFactory.initElements(driver, PatientMessagingPage.class);
	
	}
	
		/**
	 * @Description:Click Make Payment For Patient
	 * @return
	 */
	public PayMyBillOnlinePage clickMakePaymentForPatient()
	{
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElementInDefaultFrame(driver,20,onlineBillPay);
		onlineBillPay.click();
		IHGUtil.waitForElementInDefaultFrame(driver,10,makePaymentForPatient);
		makePaymentForPatient.click();
		return PageFactory.initElements(driver, PayMyBillOnlinePage.class);
		
	}
}
