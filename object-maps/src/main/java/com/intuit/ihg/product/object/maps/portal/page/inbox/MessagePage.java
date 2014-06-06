package com.intuit.ihg.product.object.maps.portal.page.inbox;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.object.maps.portal.page.MyPatientPage;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class MessagePage extends BasePageObject {

	public static final String PAGE_NAME = "Consolidated Inbox Message Page";

	@FindBy(xpath = ".//div[@class='messageArea']/div[@class='messageBody'][1]/div[@class='commMessageHeader']/div[@class='subject']")
	private WebElement practiceResponseSubject;

	@FindBy(name = "buttons:2:button")
	private WebElement btnReply;

	@FindBy(name = "body")
	private WebElement replyBody;
	private static final String REPLY_TEXT = "This reply was generated from patient portal by IHGQA automation test";

	@FindBy(name = "buttons:0:button")
	private WebElement btnSend;
	
	@FindBy(linkText = "Review Health Information")
	private WebElement btnReviewHealthInformation;

	@FindBy(linkText = "Close")
	private WebElement btnClose;

	@FindBy(linkText = "My Patient Page")
	private WebElement lnkMyPatientPage;	
	
	@FindBy(xpath = "//iframe[contains(@title, 'Health Information Details')]")
	private WebElement webframe;
	
	@FindBy(id = "closeCcd")
	private WebElement btnCloseViewer;
	
	@FindBy(linkText = "Send my information")
	private WebElement btnShareWithDoctor;
	
	@FindBy(xpath = "//form/div/button")
	private WebElement btnSendToShareTheHealthInformation;
	
	@FindBy(xpath = "//html/body/div[2]/form/div[1]/input[@type='text']")
	private WebElement textBoxToEnterEmailAddress;
	
	@FindBy(xpath = "//form/div/span")
	private WebElement textBoxResponseMsg;
	
	@FindBy(id = "basicInfo")
	private WebElement ccdBasicInfo;
	
	@FindBy(id = "savePdf")
	private WebElement pdfDownload;
	String[] myDirectAddresses = { "ihg!!!qa@service.directaddress.net",
			"ihg_qa@service.address.net", "ihg_qa@gmail.com" , "ihg_qa@direct.healthvault.com"};
	
	String[] myDirectResponsesPortal = {
			"ihg!!!qa@service.directaddress.net is invalid. Please correct the format.",
			"This Direct Address appears to be invalid. Your message was not sent.",
			"This Direct Address appears to be invalid. Your message was not sent.",
			"Your health information was sent to ihg_qa@direct.healthvault.com!"};

	public MessagePage(WebDriver driver) {
		super(driver);
	}
	
	
	/**
	 * Returns the subject for the most recent practice response in the message thread
	 * @return the subject text
	 */
	public String getPracticeReplyMessageTitle() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver,10,practiceResponseSubject);
		return practiceResponseSubject.getText();
	}
	

	/**
	 * Will reply to a message using the current message subject, and adding the supplied text for the message body.
	 * @param body the text for the body of the message
	 * @return the consolidated inbox
	 */
	public MessageCenterInboxPage replyToMessage(String body) {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		
		btnReply.click();		
		if (body == null || body.isEmpty()) {
			body = REPLY_TEXT;
		}
		replyBody.sendKeys(body);
		btnSend.click();
		
		return PageFactory.initElements(driver, MessageCenterInboxPage.class);
	}
	
	
	public MyPatientPage clickMyPatientPage() {
	 	IHGUtil.PrintMethodName();
		PortalUtil.setDefaultFrame(driver);
		lnkMyPatientPage.click();
		return PageFactory.initElements(driver, MyPatientPage.class);
	}
	
	
	/**
	 * Click on the link Review Health Information
	 */
	public void clickBtnReviewHealthInformation() throws InterruptedException {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		btnReviewHealthInformation.click();	
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
		IHGUtil.waitForElement(driver, 20, webframe);
		driver.switchTo().frame(webframe);
       
		if (util.checkCcdType() == false) {
		  IHGUtil.waitForElement(driver, 10, btnCloseViewer);
		  btnCloseViewer.click();
		} else if (util.checkCcdType() == true) {
			btnShareWithDoctor.click();
			addAddressesAndValidateInPortal();
			IHGUtil.waitForElement(driver, 10, btnClose);
			btnClose.click();
			IHGUtil.waitForElement(driver, 10, btnCloseViewer);
			btnCloseViewer.click();
		}
	}
	
	/**
	 * verify CCD Viewer and click on button 'CloseViewer'
	 */
	public void verifyCCDViewerAndClose() throws InterruptedException {
		IHGUtil.PrintMethodName();
		driver.switchTo().defaultContent();
		IHGUtil.waitForElement(driver, 20, webframe);
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
	public void addAddressesAndValidateInPortal() throws InterruptedException {
		IHGUtil.PrintMethodName();
		for (int i = 0; i < myDirectAddresses.length; i++) {
			textBoxToEnterEmailAddress.sendKeys(myDirectAddresses[i]);
			btnSendToShareTheHealthInformation.click();
			IHGUtil.waitForElement(driver, 20, textBoxResponseMsg);
			Assert.assertEquals(textBoxResponseMsg.getText(),myDirectResponsesPortal[i]);
			textBoxToEnterEmailAddress.clear();
		}
	}
	/**
	 * click on PDF button
	 */
	public void clickOnPDF() throws InterruptedException {
		IHGUtil.PrintMethodName();
		driver.switchTo().defaultContent();
		IHGUtil.waitForElement(driver, 60, webframe);
		driver.switchTo().frame(webframe);
		pdfDownload.click();
		
	}
	
	/**
	 * Enter EmailID for transmit and send  
	 */
	public void generateTransmitEvent(String email) throws InterruptedException{
		  IHGUtil.PrintMethodName();
		  driver.switchTo().defaultContent();
		  IHGUtil.waitForElement(driver, 20, webframe);
		  driver.switchTo().frame(webframe);
		  btnShareWithDoctor.click();
		  IHGUtil.waitForElement(driver, 20, textBoxToEnterEmailAddress);
		  textBoxToEnterEmailAddress.sendKeys(email);
		  btnSendToShareTheHealthInformation.click();
		  IHGUtil.waitForElement(driver, 70, textBoxResponseMsg);
		  String successMessage="Your health information was sent to "+email+"!";
		  log(textBoxResponseMsg.getText().toString());
		  Assert.assertEquals(textBoxResponseMsg.getText(), successMessage);
		 }
}
