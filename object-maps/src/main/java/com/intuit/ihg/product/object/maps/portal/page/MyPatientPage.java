package com.intuit.ihg.product.object.maps.portal.page;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.intuit.ihg.product.object.maps.portal.page.healthform.HealthFormPage;
import com.intuit.ihg.product.object.maps.portal.page.inbox.ConsolidatedInboxPage;
import com.intuit.ihg.product.object.maps.portal.page.inbox.MessageCenterInboxPage;
import com.intuit.ihg.product.object.maps.portal.page.makePaymentpage.MakePaymentPage;
import com.intuit.ihg.product.object.maps.portal.page.myAccount.MyAccountPage;
import com.intuit.ihg.product.object.maps.portal.page.newRxRenewalpage.NewRxRenewalPage;
import com.intuit.ihg.product.object.maps.portal.page.phr.PHRPage;
import com.intuit.ihg.product.object.maps.portal.page.portaltophr.AcceptPhrTermsandConditions;
import com.intuit.ihg.product.object.maps.portal.page.questionnaires.FormWelcomePage;
import com.intuit.ihg.product.object.maps.portal.page.solutions.apptRequest.AppointmentRequestStep1Page;
import com.intuit.ihg.product.object.maps.portal.page.solutions.apptRequest.ApptRequestHistoryPage;
import com.intuit.ihg.product.object.maps.portal.page.solutions.askstaff.AskAStaffStep1Page;
import com.intuit.ihg.product.object.maps.portal.page.solutions.virtualofficevisit.VirtualOfficeVisitProviderPage;
import com.intuit.ihg.product.object.maps.portal.page.symptomAssessment.NewSymptomAssessmentPage;
import com.intuit.ihg.product.portal.utils.PortalConstants;
import com.intuit.ihg.product.portal.utils.PortalUtil;


import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ifs.csscat.core.BaseTestSoftAssert;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



/**
 *  
 * @author bkrishnankutty
 * 
 */

public class MyPatientPage  extends BasePageObject{
	
		public static final String PAGE_NAME = "My Patient Page";
	
	    @FindBy(xpath="//a[contains(@href, 'account.profile')]")
	    private  WebElement myaccountLink;
	
		@FindBy(xpath = "//a[contains(@href,'aska.askadoc')][1]")
		private WebElement askAStaffLink;

		@FindBy(xpath = "//a[contains(@href,'paybill.estatements')]")
		private WebElement eStatements;

		@FindBy(xpath = "//a[contains(@href,'paybill.start')]")
		private WebElement makePayment;
		
	    @FindBy(xpath = "//a[contains(@href,'phr.muphr')]")
		private WebElement phrLink;
	    
	    @FindBy(xpath = "//a[contains(@href,'pre-reg')]")
	    private WebElement registrationForm;
	    
		@FindBy(css="a[href*='exit.cfm']")
		private WebElement logout;

		@FindBy(css=".newmailmessage.launch_questionnaire > small>a")
		private WebElement btnViewallmessages;
		
		@FindBy(xpath="//a[contains(@href,'ars.start')]")
		private WebElement appointmentRequestTab;
		
		@FindBy(xpath="//a[contains(@href,'ars.prereqs')]")
		private WebElement lnkApptRequestHistory;
		
		@FindBy(xpath="//a[contains(@href,'vov.start')]")
		private WebElement lnkVirtualOfficeVisit;
		
		@FindBy(linkText="Fill Out Forms")
		private WebElement lnkFillOutForms;
		
		@FindBy(linkText="New Assessment")
		private WebElement lnkNewSymptomAssessment;
		
		@FindBy(linkText="View Health Information")
		private WebElement lnkViewMeaningfulUsePHR;
		
		@FindBy(xpath = "//a[@title='Prescription Renewal']")
	    private WebElement lnkPrescriptionRenewal;
		
		@FindBy(xpath = "//div[@id='iframecontent']/div/h2")
	    public WebElement txtMyPatientPage;
		
		@FindBy(css="button:contains('No thanks')")
		private WebElement surveyNoThanks;
		
		@FindBy(xpath = "//a[@title='Make Payment']")
	    private WebElement makePaymentlnk;
		
		@FindBy(xpath = ".//a[contains(@href , '/consumer/index.php/forms/start/pre-reg')]")
	    private WebElement startRegistrationlnk;
		
		@FindBy(xpath = "//a[@id='formsComplete']/big")
	    private WebElement registrationConfirmationtext;
		
		@FindBy(linkText="My Messages")
		private WebElement Mymessages;

		
	    PortalUtil pPortalUtil=new PortalUtil(driver);
	    
		public MyPatientPage(WebDriver driver) {
			super(driver);
			// TODO Auto-generated constructor stub
		}
		
		public WebElement gettxtMyPatientPage()
		{
			return txtMyPatientPage;
		}
		
		public MyAccountPage clickMyAccountLink() throws InterruptedException {
			IHGUtil.PrintMethodName();
			Thread.sleep(6000);
			PortalUtil.setPortalFrame(driver);
			IHGUtil.waitForElement(driver,6, myaccountLink);
			myaccountLink.click();
      		return PageFactory.initElements(driver,MyAccountPage.class);
		}
		
	    public PortalLoginPage logout(WebDriver driver) throws InterruptedException,IOException {
			
			IHGUtil.PrintMethodName();
			//pPortalUtil.maximize(driver);
			clickLogout(driver);
	        // TODO - deal with random survey
	        return PageFactory.initElements(driver, PortalLoginPage.class);
	       }
	
	
	/**
	 * Verify if the ViewAll button presents on page or not
	 * @param driver
	 * @return
	 * @throws InterruptedException
	 */
	
	public boolean isViewallmessagesButtonPresent(WebDriver driver) throws InterruptedException
	{
		PortalUtil.setPortalFrame(driver);
		return IHGUtil.waitForElement(driver,6, myaccountLink);
	}
	
	
	public PortalLoginPage clickLogout(WebDriver driver) throws InterruptedException {
		
		IHGUtil.PrintMethodName();
		//IHGUtil.setDefaultFrame(driver);
		driver.switchTo().defaultContent();
		if(pPortalUtil.isFoundBasedOnCssSelector( "a[href*='exit.cfm']",driver )) {
			System.out.println( "DEBUG: LOGOUT ELEMENT FOUND." );
			// DEBUG
			driver.manage().timeouts().implicitlyWait( PortalConstants.SELENIUM_IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);
			logout.click();
		} else {
			// Look in frame.
			 PortalUtil.setPortalFrame(driver);
			if( pPortalUtil.isFoundBasedOnCssSelector( "a[href*='exit.cfm']",driver) ) {
				System.out.println( "DEBUG: LOGOUT ELEMENT FOUND." );
				// DEBUG
				driver.manage().timeouts().implicitlyWait( PortalConstants.SELENIUM_IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);
				logout.click();
				}
			System.out.println( "### WARNING: LOGOUT ELEMENT NOT FOUND." );
		}
		
		PortalLoginPage homePage = PageFactory.initElements(driver, PortalLoginPage.class);
		
		System.out.println( "### DELETE ALL COOKIES" );
		driver.manage().deleteAllCookies();
		return homePage;
	}
			
	public AppointmentRequestStep1Page clickAppointmentRequestTab() {	
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		
		appointmentRequestTab.click();
		return PageFactory.initElements(driver, AppointmentRequestStep1Page.class);
	}
	
	public ApptRequestHistoryPage clickApptRequestHistoryLink() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		lnkApptRequestHistory.click();		
		return PageFactory.initElements(driver, ApptRequestHistoryPage.class);
	}
	
	public AskAStaffStep1Page clickAskAStaffLink() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver,20, askAStaffLink);
		askAStaffLink.click();
		return PageFactory.initElements(driver, AskAStaffStep1Page.class);
	}
	
	/**
	 * Click on Button Viewallmessages
	 * @return
	 */
	public ConsolidatedInboxPage clickViewAllMessages() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		btnViewallmessages.click();
		return PageFactory.initElements(driver, ConsolidatedInboxPage.class);
	}
	
	public MessageCenterInboxPage clickViewAllMessagesInMessageCenter() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		btnViewallmessages.click();
		return PageFactory.initElements(driver, MessageCenterInboxPage.class);
	}

	public VirtualOfficeVisitProviderPage clickVirtualOfficeVisitLink() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		lnkVirtualOfficeVisit.click();
		return PageFactory.initElements(driver, VirtualOfficeVisitProviderPage.class);
	}
	
	public HealthFormPage clickFillOutFormsLink() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		WebDriverWait wait = new WebDriverWait(driver, 15); // we need to wait until the form window disappears
		
		wait.until(ExpectedConditions.visibilityOf(lnkFillOutForms));
		lnkFillOutForms.click();
		return PageFactory.initElements(driver, HealthFormPage.class);
	}
	
	public NewSymptomAssessmentPage clickNewSymptomAssessmentLink() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		lnkNewSymptomAssessment.click();
		return PageFactory.initElements(driver, NewSymptomAssessmentPage.class);
	}
	
	
	/**
	 * Click on PHR link and redirect to page AcceptPhrTermsandConditions
	 * @return
	 */
	public AcceptPhrTermsandConditions clickViewMeaningfulUsePHRLink() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		lnkViewMeaningfulUsePHR.click();
		return PageFactory.initElements(driver, AcceptPhrTermsandConditions.class);
	}
	
	/**
	 * Click on Prescription Renewal link and redirect to page New Rx Renewal Page
	 * @return NewRxRenewalPage
	 */
	 public NewRxRenewalPage clickPrescriptionRenewal() {
		 	IHGUtil.PrintMethodName();
			PortalUtil.setPortalFrame(driver);
			lnkPrescriptionRenewal.click();
			return PageFactory.initElements(driver, NewRxRenewalPage.class);
	    }
	
	 /**
		 * Click on Online Bill Pay link and redirect to Make Payment Page
		 * @return MakePaymentPage
		 */
		 public MakePaymentPage clickMakePaymentLnk() {
			 	IHGUtil.PrintMethodName();
		    	
				PortalUtil.setPortalFrame(driver);
				makePaymentlnk.click();
				return PageFactory.initElements(driver, MakePaymentPage.class);
		    }
	

		
			/**
			 * @Description:Click on Start Registration Button 
			 * @param pdriver
			 * @return
			 * @throws InterruptedException
			 */
			public FormWelcomePage clickStartRegistrationButton(WebDriver pdriver) throws Exception
			{
				Thread.sleep(5000);
				PortalUtil.setPortalFrame(pdriver);
				IHGUtil.waitForElement(pdriver, 15, startRegistrationlnk);
				startRegistrationlnk.click();
				PortalUtil.setquestionnarieFrame(driver);
				return PageFactory.initElements(pdriver,FormWelcomePage.class);
			}

			/**
			 * @Description:Verify Registration Confirmation Text
			 * @throws Exception
			 */
			public void verifyRegistrationConfirmationText() throws Exception
			{
				Thread.sleep(12000);
				IHGUtil.PrintMethodName();
				PortalUtil.setPortalFrame(driver);
				log("Confirmation Text :"+registrationConfirmationtext.getText());
				BaseTestSoftAssert.verifyEquals(true,registrationConfirmationtext.getText().contains("Thank you for filling out your registration and health history information!"));
			}
			 public ConsolidatedInboxPage clickMymessages() {
				 	IHGUtil.PrintMethodName();
					PortalUtil.setDefaultFrame(driver);
					Mymessages.click();
					return PageFactory.initElements(driver, ConsolidatedInboxPage.class);
		    }
			
		/**
			 * @Description:Click On PHR Link
			 */
		public PHRPage clickPHR(WebDriver pdriver)
			{
				IHGUtil.PrintMethodName();
				PortalUtil.setPortalFrame(driver);
				phrLink.click();
				return PageFactory.initElements(pdriver,PHRPage.class);
			}
		
		/**
		 * @Description:Click On PHR Link
		 */
	public void clickPHRWithoutInit(WebDriver pdriver)
		{
			IHGUtil.PrintMethodName();
			PortalUtil.setPortalFrame(driver);
			phrLink.click();
		}
}
