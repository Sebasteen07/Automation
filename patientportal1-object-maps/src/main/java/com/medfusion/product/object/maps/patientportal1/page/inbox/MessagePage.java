package com.medfusion.product.object.maps.patientportal1.page.inbox;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal1.page.MyPatientPage;
import com.medfusion.product.patientportal1.utils.PortalUtil;

public class MessagePage extends BasePageObject {

	public static final String PAGE_NAME = "Consolidated Inbox Message Page";

	@FindBy(xpath = "//div[@class='messageArea']/div[@class='messageBody']/div[@class='commMessageHeader']/div[@class='subject']")
	private WebElement practiceResponseSubject;

	@FindBy(name = "buttons:4:button")
	private WebElement btnReply;

	@FindBy(name = "body")
	private WebElement replyBody;
	private static final String REPLY_TEXT = "This reply was generated from patient portal by IHGQA automation test";

	@FindBy(name = "buttons:0:button")
	private WebElement btnSend;

	@FindBy(linkText = "View health data")
	private WebElement btnReviewHealthInformation;

	@FindBy(linkText = "Close")
	private WebElement btnClose;

	@FindBy(linkText = "My Patient Page")
	private WebElement lnkMyPatientPage;

	@FindBy(xpath = "//iframe[contains(@title, 'Health Information Details')]")
	private WebElement webframe;

	@FindBy(id = "closeCcd")
	private WebElement btnCloseViewer;

	@FindBy(linkText = "Send my information securely by Direct Protocol")
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

	@FindBy(xpath = "//span[@fieldid='instructions']")
	private WebElement sigCodeInstructions;

	@FindBy(xpath = "//input[@type='file']")
	private WebElement browseButton;

	@FindBy(xpath = "//*[@id='pageContent']/div/div[5]/div[2]/div[2]/div[1]/div[3]/div[2]")
	private WebElement lableTo;

	@FindBy(xpath = "//*[@id='pageContent']/div/div[5]/div[2]/div[2]/div[1]/div[2]/div[2]")
	private WebElement lableFrom;

	@FindBy(xpath = ".//*[@id='pageContent']/div/div[5]/div[2]/div[2]/div[1]/div[4]/div[2]")
	private WebElement lableSent;

	@FindBy(css = "div.commMessageText")
	private WebElement txtMessage;

	String[] myDirectAddresses = {"ihg!!!qa@service.directaddress.net", "ihg_qa@service.address.net", "ihg_qa@gmail.com", "ihg_qa@direct.healthvault.com"};

	String[] myDirectResponsesPortal =
			{"ihg!!!qa@service.directaddress.net is invalid. Please correct the format.", "This Direct Address appears to be invalid. Your message was not sent.",
					"This Direct Address appears to be invalid. Your message was not sent.", "Your health information was sent to ihg_qa@direct.healthvault.com!"};

	public MessagePage(WebDriver driver) {
		super(driver);
	}


	/**
	 * Returns the subject for the most recent practice response in the message thread
	 * 
	 * @return the subject text
	 */
	public String getPracticeReplyMessageTitle() {
		IHGUtil.PrintMethodName();
		driver.switchTo().frame("iframebody");
		IHGUtil.waitForElement(driver, 30, practiceResponseSubject);
		return practiceResponseSubject.getText();
	}


	/**
	 * Will reply to a message using the current message subject, and adding the supplied text for the message body.
	 * 
	 * @param body the text for the body of the message
	 * @return the consolidated inbox
	 * 
	 */
	public MessageCenterInboxPage replyToMessage(String body, String fileName) {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);

		btnReply.click();
		if (body == null || body.isEmpty()) {
			body = REPLY_TEXT;
		}
		replyBody.sendKeys(body);
		if (fileName != null) {
			IHGUtil.waitForElement(driver, 120, browseButton);
			browseButton.sendKeys(fileName);
		}
		btnSend.click();

		return PageFactory.initElements(driver, MessageCenterInboxPage.class);
	}


	public MyPatientPage clickMyPatientPage() {
		IHGUtil.PrintMethodName();
		PortalUtil.setDefaultFrame(driver);
		IHGUtil.waitForElement(driver, 120, lnkMyPatientPage);
		lnkMyPatientPage.click();
		return PageFactory.initElements(driver, MyPatientPage.class);
	}


	/**
	 * Click on the link Review Health Information
	 */
	public void clickBtnReviewHealthInformation() throws InterruptedException {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver, 60, btnReviewHealthInformation);
		btnReviewHealthInformation.click();
	}

	/**
	 * 
	 * If Non Consolidated CCd then the method will just Close the Viewer Else will click On ShareWithADoctor Will type addresses and will Validate the response
	 * CloseAfterSharingTheHealthInformation CloseViewer
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
		driver.manage().window().maximize();
		Thread.sleep(2000);
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
			Assert.assertEquals(textBoxResponseMsg.getText(), myDirectResponsesPortal[i]);
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
	public void generateTransmitEvent(String email) throws InterruptedException {
		IHGUtil.PrintMethodName();
		driver.switchTo().defaultContent();
		IHGUtil.waitForElement(driver, 20, webframe);
		driver.switchTo().frame(webframe);
		btnShareWithDoctor.click();
		IHGUtil.waitForElement(driver, 90, textBoxToEnterEmailAddress);
		textBoxToEnterEmailAddress.sendKeys(email);
		btnSendToShareTheHealthInformation.click();
		IHGUtil.waitForElement(driver, 70, textBoxResponseMsg);
		String successMessage = "Your health information was sent to " + email + "!";
		log(textBoxResponseMsg.getText().toString());
		Assert.assertEquals(textBoxResponseMsg.getText(), successMessage);
	}

	public boolean isSubjectLocated(String subject) throws InterruptedException {
		IHGUtil.PrintMethodName();
		Thread.sleep(9000); // wait for loaded
		PortalUtil.setPortalFrame(driver);
		log("Looking for message with Header " + subject);
		WebElement messageHeader = wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//*[contains(text(),'" + subject + "')]"))));
		if (messageHeader.isDisplayed()) {
			log("Subject found");
			return true;
		} else {
			log("Subject not found");
			return false;
		}
	}

	/**
	 * verify sidcode meaning in patient portal
	 * 
	 * @param sigCodeMeaning
	 * @throws InterruptedException
	 */

	public String readSigCode(String sigCodeMeaning) throws InterruptedException {
		IHGUtil.PrintMethodName();
		Thread.sleep(120000);
		PortalUtil.setFrame(driver, "iframebody");
		IHGUtil.waitForElement(driver, 60, sigCodeInstructions);
		log("Searching: SigCode Meaning is:" + sigCodeMeaning + ", and Actual SigCode Meaning is:" + sigCodeInstructions.getText().toString());
		return sigCodeInstructions.getText().toString();
	}

	/**
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public String returnSenderName() throws InterruptedException {
		IHGUtil.PrintMethodName();
		Thread.sleep(60000);
		PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver, 60, lableFrom);
		return lableFrom.getText().toString();
	}

	/**
	 * 
	 * @return
	 */
	public String returnRecipientName() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver, 60, lableTo);
		return lableTo.getText().toString();
	}

	/**
	 * 
	 * @return
	 */
	public String returnMessage() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver, 60, txtMessage);
		return txtMessage.getText().toString();
	}

	/**
	 * 
	 * @return
	 */
	public String returnMessageSentDate() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver, 60, lableSent);
		return lableSent.getText().toString();
	}

}
