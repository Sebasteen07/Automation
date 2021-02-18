//Copyright 2013-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.MessagesPage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;
import com.medfusion.product.object.maps.patientportal2.page.CcdPage.JalapenoCcdViewerPage;
import com.medfusion.product.object.maps.patientportal2.page.CcdPage.NGCcdViewerPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage.JalapenoPayBillsStatementPdfPage;
import com.medfusion.product.object.maps.patientportal2.page.PayBillsStatementPage.JalapenoPayBillsStatementPage;

public class JalapenoMessagesPage extends JalapenoMenu {

	/*
	 * @FindBy(how = How.ID, using = "askatitle_link") private WebElement
	 * askAQuestionButton;
	 */

	@FindBy(how = How.XPATH, using = "//li[@id='inboxFolder']/a")
	private WebElement inboxFolder;

	@FindBy(how = How.ID, using = "sentFolder")
	private WebElement sentFolder;
    
    @FindBy(how = How.XPATH, using = "//li[@id='archiveFolder']/a")
	private WebElement archiveFolder;

	@FindBy(how = How.ID, using = "replyButton")
	private WebElement replyButton;

	@FindBy(how = How.ID, using = "replyBody")
	private WebElement replyBody;

	@FindBy(how = How.XPATH, using = "//button[contains(text(),'Send')]")
	private WebElement sendButton;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Your reply was successfully sent')]")
	private WebElement successMsg;

	@FindBy(how = How.XPATH, using = "//a[.='View health data']")
	private WebElement ccdDocument;

	@FindBy(how = How.XPATH, using = "//button[@class='btn btn-default'][2]")
	private WebElement archiveMessageButton;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"messageContainer\"]/div[2]/div[2]/div/span[4]")
	private WebElement lableSent;

	@FindBy(how = How.XPATH, using = "//*[@id=\"messageContainer\"]/div[2]/div[2]/div[2]/a")
	private WebElement statementLinkText;

	@FindBy(how = How.XPATH, using = "//div[@class='messageMetadata clearfix']/span[1]")
	private WebElement msgSubject;

	@FindBy(xpath = "//div[@class='messageContent']//a")
	private WebElement messageURL;

	@FindBy(xpath = "//a[contains(text(),'QuickSend.pdf')]")
	private WebElement attachmentPdfFile;

	@FindBy(xpath = "//div[@class='messageContent']")
	private WebElement inboxMessageBody;
	
	@FindBy(how = How.XPATH, using = "//button[@class='btn btn-default'][2]")
	private WebElement archiveButton;
	
	@FindBy(how = How.XPATH, using = "//span[@class='messageFrom ng-binding']")
	private WebElement senderName;

	private static final int maxCount = 15;
	private static final String replyContent = "This is response to doctor's message";
	
	@FindBy(how = How.CSS, using = "span[class='messageSubject']")
	private WebElement messageSubjectText;
	
	@FindBy(how = How.ID, using = "messages")
	private WebElement messageList;
	
	@FindBy(how = How.XPATH, using = "//button[@class='btn btn-default']/img")
	private WebElement unArchive;

	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();

		webElementsList.add(inboxFolder);
		webElementsList.add(sentFolder);
		webElementsList.add(archiveFolder);

		return assessPageElements(webElementsList);
	}

	public JalapenoMessagesPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
	}

	public boolean isMessageDisplayed(WebDriver driver, String subject) {
		IHGUtil.PrintMethodName();
		WebElement element;

		for (int count = 1; count <= maxCount; count++) {
			try {
				element = driver.findElement(By.xpath("//*/ul/li/a/span[contains(text(),'" + subject + "')]"));
				log("Message with subject \"" + subject + "\" arrived.");
				return element.isDisplayed();
			} catch (Exception ex) {
				log("Message with subject \"" + subject + "\" didn't arrive. Attempt " + count + "/" + maxCount
						+ ". Refreshing page.");
				driver.navigate().refresh();
			}
		}

		log("Message with subject \"" + subject + "\" didn't arrive at all.");
		return false;
	}

	public boolean isMessageFromEstatementsDisplayed(WebDriver driver) throws InterruptedException {
		IHGUtil.PrintMethodName();
		int count = 1;
		int maxCount = 10;
		WebElement element;

		while (count <= maxCount) {
			try {
				element = driver.findElement(By.partialLinkText("Click here to view your statement"));
				log("Message from eStatement found");
				return element.isDisplayed();
			} catch (Exception ex) {
				log("Not found: " + count + "/" + maxCount + "| Refreshing page");
				driver.navigate().refresh();
				Thread.sleep(1000);
				count++;
			}
		}
		log("Couldn't find eStatement secure message!");
		log(driver.getPageSource());
		return false;
	}

	public boolean replyToMessage(WebDriver driver) throws InterruptedException {
		IHGUtil.PrintMethodName();

		log("Write a message");

		replyButton.click();
		replyBody.sendKeys(replyContent);
		Thread.sleep(5000);
		//sendButton.click();
		javascriptClick(sendButton);
		Thread.sleep(5000);
		boolean value = isElementVisible(successMsg, 10);
		System.out.println(value);

		try {
			IHGUtil.waitForElementByXpath(driver, "//*[contains(text(),'Your reply was successfully sent')]", 30);
			log("Message sent");
			return true;
		} catch (Exception e) {
			log(e.getCause().toString());
			return false;
		}
	}

	public JalapenoCcdViewerPage findCcdMessage(WebDriver driver) {
		IHGUtil.PrintMethodName();

		ccdDocument.click();

		return PageFactory.initElements(driver, JalapenoCcdViewerPage.class);
	}

	@Deprecated // use methods from JalapenoMenu to interact with menu elements!
	public JalapenoHomePage backToHomePage(WebDriver driver) {
		log("Get back to Home Page");

		return clickOnMenuHome();
	}

	public JalapenoPayBillsStatementPage goToPayBillsPage(WebDriver driver) {
		log("Go to Pay Bills page");

		driver.findElement(By.xpath("//li[@id='payments_lhn']/a")).click();

		return PageFactory.initElements(driver, JalapenoPayBillsStatementPage.class);
	}

	public void archiveOpenMessage() {
		log("Archiving open message, button is displayed? " + archiveMessageButton.isDisplayed());
		archiveMessageButton.click();
	}
	public void goToInboxMessage() throws InterruptedException {
		log("Navigating to Inbox folder");
		javascriptClick(inboxFolder);
		WebDriverWait wait= new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.elementToBeClickable(msgSubject));
		Thread.sleep(5000);
		}
	

	public String returnMessageSentDate() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 60, lableSent);
		return lableSent.getText().toString();
	}

	public JalapenoPayBillsStatementPdfPage openPDFStatement() {
		log("Statement: " + statementLinkText.getText());
		statementLinkText.click();
		return PageFactory.initElements(driver, JalapenoPayBillsStatementPdfPage.class);
	}

	public int checkSubjectLength() {
		log("The subject of the message recieved at Patient inbox is " + msgSubject.getText());
		return msgSubject.getText().length();
	}

	public String getMessageBody() {
		return inboxMessageBody.getText();
	}

	public String getMessageURL() {
		return messageURL.getText();
	}

	public static String getPatientReply() {
		return replyContent;
	}

	public String getAttachmentPdfFile() {
		return attachmentPdfFile.getText();
	}
	
	
	public void goToArchivedMessages() throws InterruptedException {
		log("Navigating to Archived folder");
		javascriptClick(archiveFolder);
		WebDriverWait wait= new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.elementToBeClickable(msgSubject));
		}

    public String returnSubjectMessage() {
	log("Getting email subject text");
	return messageSubjectText.getText().toString();
	
        }
	public int MessageCount() throws InterruptedException 
	{
		WebElement ul_element = driver.findElement(By.xpath("//ul[@id='messages']"));
        List<WebElement> li_All = ul_element.findElements(By.tagName("li"));
        return (li_All.size());      		
		
	}
	
	public NGCcdViewerPage findNGCcdMessage(WebDriver driver) {
		IHGUtil.PrintMethodName();

		ccdDocument.click();

		return PageFactory.initElements(driver, NGCcdViewerPage.class);
	}
	
	public void archiveMessage() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 60, archiveButton);
		javascriptClick(archiveButton);
		Thread.sleep(2000);
	}
	
	public void verifyMessageContent(WebDriver driver, String subject,String body) {
		IHGUtil.PrintMethodName();
		WebElement element;
		String actualBody =null;
			try {
				element = driver.findElement(By.xpath("//*/ul/li/a/span[contains(text(),'" + subject + "')]"));
				log("Message with subject \"" + subject + "\" displayed. Clicking to message");
				Thread.sleep(10000);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
				Thread.sleep(10000);
				actualBody= getMessageBody();
			} catch (Exception ex) {
				log("Message with subject " + subject+"is not clickable");
				log(ex.getMessage());
			}
			Assert.assertEquals(actualBody, body);
	}
	
	public void OpenMessage(WebDriver driver, String subject) {
		IHGUtil.PrintMethodName();
		WebElement element;
			try {
				element = driver.findElement(By.xpath("//*/ul/li/a/span[contains(text(),'" + subject + "')]"));
				log("Message with subject \"" + subject + "\" displayed. Clicking to message");
				Thread.sleep(10000);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
				Thread.sleep(5000);
			} catch (Exception ex) {
				log("Message with subject " + subject+"is not clickable");
				log(ex.getMessage());
			}
	}
	
	public void verifyMessageAttachment(WebDriver driver, String attachmentName) {
		IHGUtil.PrintMethodName();
		WebElement element;
		Boolean status= false;
			try {
				element = driver.findElement(By.xpath("//a[contains(text(),'" + attachmentName + "')]"));
				log("Message with subject \"" + attachmentName + "\" displayed. Clicking to message");
				Thread.sleep(10000);
				if(element.isDisplayed())
					status= true;
			} catch (Exception ex) {
				log("Message with subject " + attachmentName+"is not clickable");
				log(ex.getMessage());
				Assert.assertTrue(status, "Attachment is not found in message");
			}
	}
	
	public Boolean verifyReplyButton(WebDriver driver) {
		IHGUtil.PrintMethodName();
		Boolean status= false;
			try {
				log("Verify Reply Button should not be displayed");
				Thread.sleep(5000);
				if(replyButton.isDisplayed())
					status= false;
			} catch (Exception ex) {
				status= true;
				log("Reply Button is not displayed");
				log(ex.getMessage());
				Assert.assertTrue(status, "Reply Button is not displayed");
			}
			return status;
	}
	
	public Boolean verifySenderInfo(WebDriver driver,String senderFirstName, String senderLastName) {
		IHGUtil.PrintMethodName();
		Boolean status= false;
			try {
				log("Verify Sender Name");
				Thread.sleep(5000);
				String acutalSenderName = senderName.getText();
				if((acutalSenderName.contains(senderFirstName))&&((acutalSenderName.contains(senderLastName))))			
					status= true;
			} catch (Exception ex) {
				status= false;
				log("Sender Name is not correct");
				log(ex.getMessage());
			}
			Assert.assertTrue(status, "Sender Name is not correct");
			log("Sender Name is correct");
			return status;
	}
	public void verifyPrescriptionResponse(WebDriver driver, String prescritonRenewalResponse) {
		IHGUtil.PrintMethodName();
		WebElement element;
		Boolean status= false;
			try {
				element = driver.findElement(By.xpath("//*[contains(text(),'" + prescritonRenewalResponse + "')]"));
				Thread.sleep(5000);
				if(element.isDisplayed()){
					status= true;
				    log("PrescritonRenewalResponse added by Practice is displayed in Portal");}
			} catch (Exception ex) {
				log("PrescritonRenewalResponse added by Practice is not displayed in Portal");
				log(ex.getMessage());
				Assert.assertTrue(status, "PrescritonRenewalResponse added by Practice is not displayed in Portal");
			}
	}
	
	public void verifyMedicationStatus(WebDriver driver, String renewalStatus) {
		IHGUtil.PrintMethodName();
		WebElement element;
		Boolean status= false;
			try {
				element = driver.findElement(By.xpath("//*[contains(text(),'" + renewalStatus + "')]"));
				Thread.sleep(5000);
				if(element.isDisplayed()){
					status= true;
				    log("Medication status is "+renewalStatus);}
			} catch (Exception ex) {
				log("Medication status is not displayed in Prescription renewal message");
				log(ex.getMessage());
				Assert.assertTrue(status, "Medication status is not displayed in Prescription renewal message");
			}}

	public void clickOnUnArchive() throws InterruptedException {
			log("Clicking on the unarchive from Archive Tab");
			WebDriverWait wait= new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.elementToBeClickable(unArchive));
			unArchive.click();
			
			
		
	}
}

