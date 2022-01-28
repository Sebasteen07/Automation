//Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.practice.page.patientMessaging;

import static org.testng.Assert.assertTrue;

import java.net.URL;
import java.util.ArrayList;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.practice.api.utils.PracticeConstants;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class PatientMessagingPage extends BasePageObject {

	@FindBy(xpath = "//table[@class='searchForm']//select[@name='delivery']")
	private WebElement deliveryMode;

	@FindBy(xpath = "//table[@class='searchForm']//select[@name='msgtype']")
	private WebElement messageType;

	@FindBy(xpath = "//table[//select[@name='deliverymode']")
	private WebElement buildDeliveryMode;

	@FindBy(xpath = "//select[@name='msgtype']")
	private WebElement buildMessageType;

	@FindBy(xpath = "//select[@name='msgtemplate']")
	private WebElement buildTemplate;

	@FindBy(xpath = "//input[@name='msgname']")
	private WebElement buildSubject;

	@FindBy(xpath = "//input[@name='next']")
	private WebElement nextButton;

	@FindBy(xpath = "//input[@value='Send Message']")
	private WebElement sendBuildMessage;

	@FindBy(xpath = "//table[@class='searchForm']//select[@name='msgtemplate']")
	private WebElement template;

	@FindBy(xpath = "//table[@class='searchForm']//input[@name='subject']")
	private WebElement subject;

	@FindBy(id = "msgattachment_1_1")
	private WebElement messageAttachment;

	@FindBy(xpath = "//input[@id='msgattachment_2_2']")
	private WebElement attachment2;

	@FindBy(xpath = "//input[@id='msgattachment_3_3']")
	private WebElement attachment3;

	@FindBy(xpath = "//small[text()='Add another attachment']")
	private WebElement addAnotherAttachment;

	@FindBy(xpath = "//a[@id='msgattachment_3_addlink']")
	private WebElement addAnotherAttachment3;

	@FindBy(xpath = "//table[@class='searchForm']//select[@name='recipienttype']")
	private WebElement recipientType;

	@FindBy(xpath = "//select[@name='recipienttype']")
	private WebElement buildRecipientType;

	@FindBy(xpath = "//table[@class='searchForm']//input[@name='firstname']")
	private WebElement firstName;

	@FindBy(xpath = "//input[@name='fname']")
	private WebElement buildFirstName;

	@FindBy(xpath = "//table[@class='searchForm']//input[@name='lastname']")
	private WebElement lastName;

	@FindBy(xpath = "//input[@name='lname']")
	private WebElement buildLastName;

	@FindBy(xpath = "//table[@class='searchForm']//input[@name='email']")
	private WebElement email;

	@FindBy(xpath = "//input[@name='email']")
	private WebElement buildEmail;

	@FindBy(xpath = "//table[@class='searchForm']//input[@value='Search for Patients']")
	private WebElement searchForPatients;

	@FindBy(xpath = "//input[@value='Search']")
	private WebElement buildSearchForPatients;

	@FindBy(xpath = "//table[@id='patresultshead']//tr[@title='Click to add.']/td[2]")
	private WebElement searchResult;

	@FindBy(xpath = "//tr[@title='Click to add recipient']")
	private WebElement buildSearchResult;

	@FindBy(xpath = "//input[@value='Select Recipients and Continue']")
	private WebElement buildSelectReceptientContinue;

	@FindBy(xpath = "//input[@value='Publish Message']")
	private WebElement publishMessage;

	@FindBy(xpath = "//input[@value='Skip and Continue']")
	private WebElement skipContinueButton;

	@FindBy(xpath = "//input[@value='Skip and continue']")
	private WebElement skipAndContinueButton;

	@FindBy(xpath = "//div[@class='feedbackContainer']/div/div/ul/li")
	public WebElement publishedSuccessfullyMessage;

	@FindBy(xpath = "/html/body/div[2]/table/tbody/tr/td/div[1]/form/fieldset[3]/table[1]/tbody/tr[1]/td[1]/table/tbody/tr[1]/td/input")
	private WebElement patientCanReplyButton;

	@FindBy(how = How.LINK_TEXT, using = "My Messages")
	private WebElement myMessages;

	@FindBy(id = "id19")
	private WebElement searchButton;

	@FindBy(how = How.LINK_TEXT, using = "Quick Send")
	private WebElement quickSendButton;

	@FindBy(xpath = "//*[contains(text(),'Message Published Successfully')]")
	private WebElement messagePublishedSuccessfully;

	@FindBy(xpath = "//table[@class='searchForm'][2]//tbody//a")
	private WebElement messageURL;

	@FindBy(how = How.LINK_TEXT, using = "This is testing URL")
	private WebElement buildMessageURL;

	@FindBy(xpath = "//td[@id='htmlTemplate']")
	private WebElement messageBody;

	@FindBy(xpath = "//td[b[text()='HTML Sample Message']]/following-sibling::td")
	private WebElement buildMessageBody;

	@FindBy(xpath = "//fieldset//div//p")
	private WebElement patientReply;

	private static int maxCount = 10;

	public PatientMessagingPage(WebDriver driver) {
		super(driver);
	}

	public void setDeliveryMode() {
		IHGUtil.PrintMethodName();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		Select sel = new Select(deliveryMode);
		sel.selectByVisibleText(PracticeConstants.DELIVERY_MODE);

	}

	public void setMessageType() {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		Select sel = new Select(messageType);
		sel.selectByVisibleText(PracticeConstants.MESSAGE_TYPE);
	}

	public void setTemplate() throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		Select sel = new Select(template);
		try {
			sel.selectByVisibleText(PracticeConstants.TEMPLATE1);
		} catch (Exception e) {
			sel.selectByVisibleText(PracticeConstants.TEMPLATE2);
		}
		Thread.sleep(5000);
	}

	public void setSubject() {
		this.setSubject(PracticeConstants.SUBJECT);
	}

	public void setSubject(String subjectText) {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		subject.clear();
		subject.sendKeys(subjectText);
	}

	public void setBuildSubject() {
		this.setBuildSubject(PracticeConstants.SUBJECT);
	}

	public void setBuildSubject(String subjectText) {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		buildSubject.clear();
		buildSubject.sendKeys(subjectText);
	}

	public void setRecipientType() {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		IHGUtil.waitForElement(driver, 10, recipientType);
		Select sel = new Select(recipientType);
		sel.selectByVisibleText(PracticeConstants.RECIPIENT_TYPE);
	}

	public void setBuildRecipientType() {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		IHGUtil.waitForElement(driver, 10, buildRecipientType);
		Select sel = new Select(buildRecipientType);
		sel.selectByVisibleText(PracticeConstants.RECIPIENT_TYPE);
	}

	public void setFirstName() {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		firstName.clear();
		firstName.sendKeys(PracticeConstants.PATIENT_FIRST_NAME);
	}

	public void setFirstName(String fname) {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		firstName.clear();
		firstName.sendKeys(fname);
	}

	public void setBuildMessageFirstName(String fname) {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		buildFirstName.clear();
		buildFirstName.sendKeys(fname);
	}

	public void setLastName() {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		lastName.clear();
		lastName.sendKeys(PracticeConstants.PATIENT_LAST_NAME);
	}

	public void setLastName(String lname) {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		lastName.clear();
		lastName.sendKeys(lname);
	}

	public void setBuildLastName(String lname) {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		buildLastName.clear();
		buildLastName.sendKeys(lname);
	}

	public void setEmail() {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		email.clear();
		email.sendKeys(PracticeConstants.PATIENT_EMAIL);
	}

	public void setEmail(String email) {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		this.email.clear();
		this.email.sendKeys(email);
	}

	public void setBuildEmail(String email) {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		this.buildEmail.clear();
		this.buildEmail.sendKeys(email);
	}

	public void setFieldsAndPublishMessage(String filePath) throws Exception {
		IHGUtil.PrintMethodName();
		Thread.sleep(5000);
		setDeliveryMode();
		setMessageType();
		setTemplate();

		setSubject();

		URL QuickSendPDFUrl = ClassLoader.getSystemResource(PracticeConstants.QUICK_SEND_PDF_FILE_PATH);
		messageAttachment.sendKeys(QuickSendPDFUrl.getPath());

		Thread.sleep(2000);
		setRecipientType();
		setFirstName();
		setLastName();
		searchForPatients.click();
		Thread.sleep(5000);
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		IHGUtil.waitForElement(driver, 60, searchResult);
		searchResult.click();
		Thread.sleep(12000);
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		email.click();
		publishMessage.click();
		Thread.sleep(3000);
	}

	public void setFieldsAndPublishMessage(String firstName, String lastName, String templateName, String subjectText) {
		setFieldsAndPublishMessage(firstName, lastName, "", templateName, subjectText);
	}

	public ArrayList<String> setFieldsAndPublishMessage(PropertyFileLoader testData, String templateName,
			String subjectText) {
		return setFieldsAndPublishMessage(testData.getFirstName(), testData.getProperty("last.name.build"),
				testData.getProperty("eamil.build.message"), templateName, subjectText);
	}

	public ArrayList<String> setFieldsAndPublishMessageforBuild(PropertyFileLoader testData, String templateName,
			String subjectText) throws NullPointerException, InterruptedException {
		return setFieldsAndPublishBuildMessage(testData.getFirstName(), testData.getProperty("last.name.build"),
				testData.getProperty("eamil.build.message"), templateName, subjectText);
	}

	public ArrayList<String> setFieldsAndPublishMessage(String firstName, String lastName, String email,
			String templateName, String subjectText) {
		IHGUtil.PrintMethodName();

		setMessageFields(templateName, subjectText);
		setRecipient(firstName, lastName, email);
		String msgurl = checkMessageURL();
		String msgBody = checkMessageBody();
		ArrayList<String> messages = new ArrayList<String>();
		messages.add(msgurl);
		messages.add(msgBody);
		publishMessage();
		return messages;

	}

	public ArrayList<String> setFieldsAndPublishBuildMessage(String firstName, String lastName, String email,
			String templateName, String subjectText) throws InterruptedException {
		IHGUtil.PrintMethodName();
		setBuildMessageFields(templateName, subjectText);
		setBuildRecipient(firstName, lastName, email);
		String msgurl = checkBuildMessageURL();
		String msgBody = checkBuildMessageBody();
		ArrayList<String> messages = new ArrayList<String>();
		messages.add(msgurl);
		messages.add(msgBody);
		sendBuildMessage();
		return messages;

	}

	public void setFieldsAndPublishMessageWithFile(String firstName, String lastName, String templateName,
			String subjectText, String filePath) {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		new WebDriverWait(driver, 120).until(ExpectedConditions.visibilityOf(messageAttachment));
		messageAttachment.sendKeys(filePath);
		setFieldsAndPublishMessage(firstName, lastName, "", templateName, subjectText);

	}

	public void setFieldsAndPublishMessageWithFile(PropertyFileLoader testData, String templateName, String subjectText,
			String filePath) {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		new WebDriverWait(driver, 120).until(ExpectedConditions.visibilityOf(messageAttachment));
		messageAttachment.sendKeys(filePath);
		setFieldsAndPublishMessage(testData.getProperty("documents.patient.first.name"),
				testData.getProperty("documents.patient.last.name"), "", templateName, subjectText);

	}

	public void setMessageFields(String templateName, String subjectText) {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		Select sel = new Select(messageType);
		sel.selectByVisibleText("Other");
		Select sel2 = new Select(template);
		sel2.selectByVisibleText(templateName);
		setSubject(subjectText);
		javascriptClick(patientCanReplyButton);
		setBuildRecipientType();
	}

	public void setBuildMessageFields(String templateName, String subjectText) {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		Select sel = new Select(buildMessageType);
		sel.selectByVisibleText("Other");
		Select sel2 = new Select(buildTemplate);
		sel2.selectByVisibleText(templateName);
		setBuildSubject(subjectText);
		javascriptClick(nextButton);
		setBuildRecipientType();
	}

	public void setRecipient(String firstName, String lastName, String email) {
		IHGUtil.PrintMethodName();
		setFirstName(firstName);
		setLastName(lastName);
		setEmail(email);
		searchForPatients.click();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		IHGUtil.waitForElement(driver, 60, searchResult);
		searchResult.click();
	}

	public void setBuildRecipient(String firstName, String lastName, String email) {
		IHGUtil.PrintMethodName();
		setBuildMessageFirstName(firstName);
		setBuildLastName(lastName);
		setBuildEmail(email);
		buildSearchForPatients.click();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		IHGUtil.waitForElement(driver, 60, buildSearchResult);
		buildSearchResult.click();
		IHGUtil.waitForElement(driver, 60, buildSelectReceptientContinue);
		buildSelectReceptientContinue.click();
		skipContinueButton.click();
		skipAndContinueButton.click();
		nextButton.click();

	}

	public void publishMessage() {
		IHGUtil.PrintMethodName();
		javascriptClick(publishMessage);
		IHGUtil.exists(driver, 30, messagePublishedSuccessfully);
	}

	public void sendBuildMessage() {
		IHGUtil.PrintMethodName();
		javascriptClick(sendBuildMessage);
		IHGUtil.exists(driver, 30, messagePublishedSuccessfully);
	}

	public boolean findMyMessage(String subject) throws Exception {
		IHGUtil.PrintMethodName();
		WebElement element;

		myMessages.click();

		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);

		log("Searching message with subject: " + subject);

		for (int count = 1; count <= maxCount; count++) {
			try {
				searchButton.click();
				element = driver.findElement(By.xpath("//*[contains(text(),'" + subject + "')]"));
				element.click();
				log("Message from patient found");
				return element.isDisplayed();
			} catch (Exception ex) {
				log("Searching for message: " + count + "/" + maxCount);
				searchButton.click();
			}
		}

		log("Message from patient not found");
		return false;
	}

	public String checkMessageURL() {

		log("Checking if the URL is present in message to be sent");
		assertTrue(messageURL.isDisplayed());
		log("The URL in Messsage Body sent from Practice Portal is: " + messageURL.getText());
		return messageURL.getText();

	}

	public String checkBuildMessageURL() {

		log("Checking if the URL is present in message to be sent");
		assertTrue(buildMessageURL.isDisplayed());
		log("The URL in Messsage Body sent from Practice Portal is: " + buildMessageURL.getText());
		return buildMessageURL.getText();

	}

	public String checkMessageBody() {
		log("The message sent to the patient is: " + messageBody.getText());
		return messageBody.getText();

	}

	public String checkBuildMessageBody() {
		log("The message sent to the patient is: " + buildMessageBody.getText());
		return buildMessageBody.getText();

	}

	public String checkReplyContent() {
		log("The patient reply Content is: " + patientReply.getText());
		return patientReply.getText();
	}

}
