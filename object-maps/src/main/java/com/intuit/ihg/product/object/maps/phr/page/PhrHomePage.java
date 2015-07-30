package com.intuit.ihg.product.object.maps.phr.page;

import java.io.IOException;
import java.net.URISyntaxException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.downloads.RequestMethod;
import com.intuit.ihg.common.utils.downloads.URLStatusChecker;
import com.intuit.ihg.product.object.maps.phr.page.messages.PhrMessagesPage;
import com.intuit.ihg.product.object.maps.phr.page.phrHealthInformationPage.PhrHealthInformationPage;
import com.intuit.ihg.product.object.maps.phr.page.phrRegistrationInformationPage.PhrRegistrationInformationPage;
import com.intuit.ihg.product.object.maps.phr.page.profile.PhrProfilePage;
import com.intuit.ihg.product.phr.utils.PhrUtil;




public class PhrHomePage extends BasePageObject{
	
	public static final String PAGE_NAME = "PHR HOME Page";
	
	@FindBy(xpath="//em[text()='Log out']")
	private WebElement btnLogout;
	
	@FindBy(xpath="//em[text()='Profile']")
	private WebElement btnProfile;
	
	@FindBy(xpath="//em[text()='Health Information']")
	private WebElement btnHealthInformation;
	
	@FindBy(xpath="//em[text()='Sharing']")
	private WebElement btnSharing;
	
	@FindBy(xpath="//em[text()='Documents']")
	private WebElement btnDocuments;
		
	@FindBy(xpath = ".//div[@id='bluebutton']/p[1]/a")
	private WebElement btnBlueButtonDownloadPdf;
	
	@FindBy(id="bluebuttontxtlink")
	private WebElement btnBlueButtonDownloadtext;
		
	@FindBy(linkText = "Medications")
	private WebElement Medicationlnktext;

	@FindBy(xpath = "//a[contains(@href,'/phr/ui/action/ihr/medication.do')]")
	private WebElement HealthInformation;
		
	@FindBy(xpath="//div[@class='survey_dialog_buttons']/button[2]")
	private WebElement survey;
	
	@FindBy(xpath = "//a[contains(@href,'/phr/ui/action/ihr/registration.do')]")
	private WebElement profile;
	
	@FindBy ( xpath = ".//*[@id='healthinfo']/a")
	private WebElement helathInformationMenu;
	
	@FindBy(linkText = "Surgeries and Procedures")
	private WebElement surgeriesandProcedureslnktext;
	
	@FindBy(linkText = "Immunizations")
	private WebElement immunizationslnktext;
	
	@FindBy(linkText = "View all messages")
	private WebElement viewAllMessageslnktext;
	
	@FindBy(xpath="//em[starts-with(text(), 'Messages')]")
	private WebElement btnMessages;
	
	
/*	<a class="clickable1" href="/phr/ui/action/taskmanager.do">View all messages</a>*/
	
	
	public PhrHomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	/**
	 * @author bkrishnankutty
	 * @Desc:-Indicates if the search page is loaded
	 * @return true or false
	 */
	public boolean isSearchPageLoaded() {

		IHGUtil.PrintMethodName();
		log("Waiting for the element btnProfile, max wait time is 60 seconds");
		return IHGUtil.waitForElement(driver, 60, btnProfile);

	}
	
	
	// Currently pops up annoying survey
	// TODO - return page object
	/**
	 * Click on log out button and return PHR LogIn page
	 * @return
	 */
	public PhrLoginPage clickLogout() {
		
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 60, btnLogout);
		btnLogout.click();
		return PageFactory.initElements(driver, PhrLoginPage.class);
	}
	
	/** 
	 * Returns TRUE if survey was found.
	 * @return
	 * @throws InterruptedException
	 */
	public boolean clearSurveyIfAny() throws InterruptedException {
		
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver,4, survey);
		if(survey.isDisplayed()){
			survey.click();
			return true;
		}
				
		return false;
	}

	
	/**
	 * Verify if the profile button presents on page or not
	 * @param driver
	 * @return
	 * @throws InterruptedException
	 */
	
	public boolean waitforbtnProfile(WebDriver driver,int n) throws InterruptedException
	{   
		IHGUtil.PrintMethodName();
		return IHGUtil.waitForElement(driver,n, btnProfile);
	}
	

	/**
	 * Clicks on Blue button download pdf
	 * @param driver
	 * @return
	 * @throws InterruptedException
	 * @throws IOException 
	 * @throws URISyntaxException 
	 */
	
	public int clickBlueButtonDownloadPdf() throws InterruptedException, URISyntaxException, IOException
	{   
		IHGUtil.PrintMethodName();
		return validateBlueButtonDownload(btnBlueButtonDownloadPdf.getAttribute("href"), RequestMethod.GET);
	}

	/**
	 * Clicks on Blue button
	 * @param driver
	 * @return
	 * @throws InterruptedException
	 * @throws IOException 
	 * @throws URISyntaxException 
	 */
	
	public int clickBlueButtonDownloadtext() throws InterruptedException, URISyntaxException, IOException
	{   
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
	
	
	
	/**
	 * Click on profile Link
	 * return PHR Profile Page
	 */
	
	public PhrProfilePage clickProfileButton()
	{   
		IHGUtil.PrintMethodName();
		btnProfile.click();
		return PageFactory.initElements(driver, PhrProfilePage.class);
	}
	/**
	 * Click on profile Link
	 * return PHR Profile Page
	 */
	
	public PhrAlleryPage clickHelthInformation()
	{   
		IHGUtil.PrintMethodName();
		btnHealthInformation.click();
		return PageFactory.initElements(driver, PhrAlleryPage.class);
	}
	
	/**
	 * Click on Document  Link
	 * return Document Page
	 */
	public PhrDocumentsPage clickDocuments() {
		
		IHGUtil.PrintMethodName();
		
		btnDocuments.click();
		
		return PageFactory.initElements(driver, PhrDocumentsPage.class );
	}
	
	
	/**
	 * Calls the method 'ccdImportFromAllScripts' which will 
	 * Post consolidated CCd request
	 * @param allScriptAdapterURL
	 * @throws Exception
	 */
	public void postCCdRequest(String allScriptAdapterURL) throws Exception
	{
		PhrUtil pPhrUtil=new PhrUtil(driver);
		pPhrUtil.ccdImportFromAllScripts(IHGUtil.getConsolidatedCCD(), allScriptAdapterURL, IHGUtil.getEnvironmentType().toString());
	}
	
	
		
	/**
	 * Calls the method 'ccdImportFromAllScripts' which will 
	 * Post consolidated CCd request
	 * @param allScriptAdapterURL
	 * @throws Exception
	 */
	public void postNonCCdRequest(String allScriptAdapterURL) throws Exception
	{
		PhrUtil pPhrUtil=new PhrUtil(driver);
		pPhrUtil.ccdImportFromAllScripts(IHGUtil.getNonConsolidatedCCD(), allScriptAdapterURL, IHGUtil.getEnvironmentType().toString());
	}
	public void postElektaCCdRequest(String EHDCAdapterURL) throws Exception
	{
		PhrUtil pPhrUtil=new PhrUtil(driver);
		pPhrUtil.ccdImportFromElekta(IHGUtil.getElektaCCD(), EHDCAdapterURL, IHGUtil.getEnvironmentType().toString());
	}
	
	/**
	 * @Description: Click on Medications Link
	 * @return
	 */
	public PhrHealthInformationPage clickMedications(){
		PhrUtil.PrintMethodName();
		HealthInformation.click();
		IHGUtil.waitForElement(driver,10,Medicationlnktext);
		Medicationlnktext.click();
		return PageFactory.initElements(driver, PhrHealthInformationPage.class );

	}
	
	
	/**
	 * @Description: Click on Profile Tab
	 * @return
	 */
	public PhrRegistrationInformationPage clickProfile(){
		PhrUtil.PrintMethodName();
		profile.click();
		return PageFactory.initElements(driver, PhrRegistrationInformationPage.class );
		
	}
	
	/**
	 * @author bbinisha
	 * @Desc : To navigate to Health Information Page
	 */
	public PhrHealthInformationPage clickOnHealthInformationTab() {
		IHGUtil.PrintMethodName();
		log("Navigating to 'Health Information' page");
		try{
			IHGUtil.waitForElement(driver, 30, helathInformationMenu);
			helathInformationMenu.click();
		} catch (Exception e) {
			helathInformationMenu.click();
		}
		return PageFactory.initElements(driver, PhrHealthInformationPage.class);
	}
	
	/**
	 * @Description: Click on surgeriesandProcedures Link
	 * @return
	 */
	public PhrHealthInformationPage clickSurgeriesandProcedures(){
		PhrUtil.PrintMethodName();
		HealthInformation.click();
		IHGUtil.waitForElement(driver,10,Medicationlnktext);
		surgeriesandProcedureslnktext.click();
		return PageFactory.initElements(driver, PhrHealthInformationPage.class );

	}
	
	
	/**
	 * @Description: Click on Immunizations Link
	 * @return
	 */
	public PhrHealthInformationPage clickImmunizations(){
		PhrUtil.PrintMethodName();
		HealthInformation.click();
		IHGUtil.waitForElement(driver,10,immunizationslnktext);
		immunizationslnktext.click();
		return PageFactory.initElements(driver, PhrHealthInformationPage.class );

	}
	
	
	/**
	 * Click on sharing
	 */

	public PhrSharingPage clickSharing() {
		IHGUtil.PrintMethodName();
		btnSharing.click();
		return PageFactory.initElements(driver, PhrSharingPage.class );
	}
	
	/**
	 * Click on View All Messages
	 */

	public PhrMessagesPage clickOnViewAllMessages() {
		IHGUtil.PrintMethodName();
		viewAllMessageslnktext.click();
		return PageFactory.initElements(driver, PhrMessagesPage.class );
	}
	
	public PhrMessagesPage clickOnMyMessages() {
		IHGUtil.PrintMethodName();
		btnMessages.click();
		return PageFactory.initElements(driver, PhrMessagesPage.class );
	}
	
}
