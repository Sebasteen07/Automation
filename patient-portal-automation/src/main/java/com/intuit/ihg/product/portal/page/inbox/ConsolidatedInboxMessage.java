package com.intuit.ihg.product.portal.page.inbox;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.portal.page.MyPatientPage;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class ConsolidatedInboxMessage extends BasePageObject {

	public static final String PAGE_NAME = "Consolidated Inbox Message Page";

	@FindBy(xpath = ".//*[@id='msgDetail']/div[@class='msgHeader'][1]/div[@class='msgTitle']")
	private WebElement practiceResponseSubject;

	@FindBy(id = "msgReplyButton")
	private WebElement btnReply;

	@FindBy(xpath = "//iframe[contains(@title, 'Health Information Details')]")
	private WebElement webframe;

	@FindBy(id = "msgArchive")
	private WebElement btnArchive;

	@FindBy(id = "subject")
	private WebElement replySubject;

	@FindBy(id = "replyText")
	private WebElement replyBody;
	private static final String REPLY_TEXT = "This reply was generated from patient portal by IHGQA automation test";

	@FindBy(id = "msgSend")
	private WebElement btnSend;

	@FindBy(id = "msgDiscard")
	private WebElement btnDiscard;

	@FindBy(linkText = "Review Health Information")
	private WebElement btnReviewHealthInformation;

	@FindBy(linkText = "Send my information")
	private WebElement btnShareWithDoctor;

	@FindBy(xpath = "//html/body/div[2]/form/div[1]/input[@type='text']")
	private WebElement textBoxToEnterEmailAddress;

	@FindBy(xpath = "//form/div/button")
	private WebElement btnSendToShareTheHealthInformation;

	@FindBy(xpath = "//form/div/span")
	private WebElement textBoxResponseMsg;

	@FindBy(linkText = "Close")
	private WebElement btnClose;

	@FindBy(id = "closeCcd")
	private WebElement btnCloseViewer;
	
	@FindBy(linkText = "My Patient Page")
	private WebElement lnkMyPatientPage;
	
	@FindBy(id = "basicInfo")
	private WebElement ccdBasicInfo;
	
	@FindBy(css = "#lightbox > iframe:nth-child(1)")
	public WebElement CCDViewFrame;

	String[] myDirectAddresses = { "ihg!!!qa@service.directaddress.net",
			"ihg_qa@service.address.net", "ihg_qa@gmail.com" , "ihg_qa@direct.healthvault.com"};
	
	
	String[] myDirectResponses = {
			"ihg!!!qa@service.directaddress.net is invalid. Please correct the format.",
			"",
			"", };
	
	String[] myDirectResponsesPortal = {
			"ihg!!!qa@service.directaddress.net is invalid. Please correct the format.",
			"This Direct Address appears to be invalid. Your message was not sent.",
			"This Direct Address appears to be invalid. Your message was not sent.",
			"Your health information was sent to ihg_qa@direct.healthvault.com!"};


	public ConsolidatedInboxMessage(WebDriver driver) {
		super(driver);
	}
	
	
	/**
	 * Returns the subject for the most recent practice response in the message thread
	 * @return the subject text
	 */
	public String getPracticeReplyMessageTitle() {
		IHGUtil.PrintMethodName();
		PortalUtil.setConsolidatedInboxFrame(driver);
		IHGUtil.waitForElement(driver,400,practiceResponseSubject);
		return practiceResponseSubject.getText();
	}
	
	
	// reloads inbox after successful send
	// goes back to message after discard (ie send, discard, reply subject, reply body are not visible)
	/**
	 * Will reply to a message using the current message subject, and adding the supplied text for the message body.
	 * @param body the text for the body of the message
	 * @return the consolidated inbox
	 */
	public ConsolidatedInboxPage replyToMessage(String body) {
		IHGUtil.PrintMethodName();
		PortalUtil.setConsolidatedInboxFrame(driver);
		
		btnReply.click();		
		if (body == null || body.isEmpty()) {
			body = REPLY_TEXT;
		}
		replyBody.sendKeys(body);
		btnSend.click();
		
		return PageFactory.initElements(driver, ConsolidatedInboxPage.class);
	}
	
	
	/**
	 * Will initiate a reply and then discard the message.
	 * @return true if the 'Send' button is still displayed, false if not
	 */
	public boolean initiateReplyAndThenDiscard() {
		IHGUtil.PrintMethodName();
		PortalUtil.setConsolidatedInboxFrame(driver);
		
		btnReply.click();
		btnDiscard.click();
		
		boolean result = true;
		try {
			result = btnSend.isDisplayed();
		} catch (Exception e) {
			// Catch any element not found
			result = false;
		}
		
		return result;		
	}
	
	/**
	 * returns Email subject
	 * @return
	 */
	
	 public String getMessageSubject()
	 {
		 IHGUtil.PrintMethodName();
		 PortalUtil.setConsolidatedInboxFrame(driver);
		 return practiceResponseSubject.getText().toString().trim();
	 }
	 
	 
	/**
	 * Click on the link Review Health Information
	 */

	public void clickBtnReviewHealthInformation() throws InterruptedException {
		IHGUtil.PrintMethodName();
		PortalUtil.setConsolidatedInboxFrame(driver);
		btnReviewHealthInformation.click();
		Thread.sleep(2000);
	}
	

	/**
	 * Click on the link 'ShareWithADoctor'
	 */
	public void clickOnShareWithADoctor() throws InterruptedException {
		IHGUtil.PrintMethodName();
		btnShareWithDoctor.click();
	}
	

	/**
	 * returns webelement 'textBoxToEnterEmailAddress'
	 */
	public WebElement enterDirectAddress() throws InterruptedException {
		IHGUtil.PrintMethodName();
		return textBoxToEnterEmailAddress;
	}
	

	/**
	 * click on button 'SendToShareTheHealthInformation'
	 */
	public void clickOnSendToShareWithAnotherDoctor()
			throws InterruptedException {
		IHGUtil.PrintMethodName();
		btnSendToShareTheHealthInformation.click();
	}
	

	/**
	 * returns webelement 'textBoxResponseMsg'
	 */
	public String getResponseAfterSending() throws InterruptedException {
		IHGUtil.PrintMethodName();
		return textBoxResponseMsg.getText();
	}
	

	/**
	 * click on button 'Close'
	 */
	public void clickOnCloseAfterSharingTheHealthInformation()
			throws InterruptedException {
		IHGUtil.PrintMethodName();
		if (btnClose.isDisplayed()) {
			btnClose.click();
		} else {
			log("### PhrDocumentsPage.btnClose  --- Could not find Close Button");
		}
	}

	/**
	 * click on button 'CloseViewer'
	 */
	public void clickOnCloseViewer() throws InterruptedException {
		IHGUtil.PrintMethodName();
		if (btnCloseViewer.isDisplayed()) {
			btnCloseViewer.click();
		} else {
			log("### PhrDocumentsPage.btnCloseViewer --- Could not find Close Viewer");
		}
	}
	
	/**
	 * verify CCD Viewer and click on button 'CloseViewer'
	 */
	public void verifyCCDViewerAndClose() throws InterruptedException {
		IHGUtil.PrintMethodName();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(webframe);
		if (ccdBasicInfo.isDisplayed() && btnCloseViewer.isDisplayed()) {
			btnCloseViewer.click();
		} else {
			Assert.fail("CCD Viewer not present: Could not find CCD Basic Info/Close Viewer Button");
		}
	}
	


	/**
	 * Enter direct Address and validate the response
	 */
	public void addAddressesAndValidate() throws InterruptedException {
		for (int i = 0; i < myDirectAddresses.length; i++) {
			enterDirectAddress().sendKeys(myDirectAddresses[i]);
			clickOnSendToShareWithAnotherDoctor();
			Thread.sleep(20000);
			String textBoxResponseMsg = getResponseAfterSending();
			Assert.assertEquals(myDirectResponses[i], textBoxResponseMsg);
			enterDirectAddress().clear();
		}
	}
	
	
	/**
	 * Enter direct Address and validate the response
	 */
	public void addAddressesAndValidateInPortal() throws InterruptedException {
		for (int i = 0; i < myDirectAddresses.length; i++) {
			enterDirectAddress().sendKeys(myDirectAddresses[i]);
			clickOnSendToShareWithAnotherDoctor();
			Thread.sleep(20000);
			String textBoxResponseMsg = getResponseAfterSending();
			Assert.assertEquals(textBoxResponseMsg,myDirectResponsesPortal[i]);
			enterDirectAddress().clear();
		}
	}

	/**
	 * 
	 * If Non Consolidated CCd then the method will just Close the Viewer Else
	 * will click On ShareWithADoctor Will type addresses and will Validate the
	 * response CloseAfterSharingTheHealthInformation CloseViewer
	 * 
	 * @throws InterruptedException
	 */
	public void closeViewer() throws InterruptedException {
		IHGUtil util = new IHGUtil(driver);

		driver.switchTo().defaultContent();
		driver.switchTo().frame(webframe);
       
		if (util.checkCcdType() == false) {
		  clickOnCloseViewer();
		}

		else if (util.checkCcdType() == true) {
			clickOnShareWithADoctor();
			addAddressesAndValidateInPortal();
			clickOnCloseAfterSharingTheHealthInformation();
			Thread.sleep(2000);
			clickOnCloseViewer();
		}
	}
	
	public MyPatientPage clickMyPatientPage() {
	 	IHGUtil.PrintMethodName();
		PortalUtil.setDefaultFrame(driver);
		lnkMyPatientPage.click();
		return PageFactory.initElements(driver, MyPatientPage.class);
}

}
