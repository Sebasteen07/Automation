//Copyright 2013-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.MessagesPage;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
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

	@FindBy(how = How.ID, using = "inboxFolder")
	private WebElement inboxFolder;

	@FindBy(how = How.ID, using = "sentFolder")
	private WebElement sentFolder;

	@FindBy(how = How.ID, using = "archiveFolder")
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

	@FindBy(how = How.XPATH, using = "//button[.='Archive']")
	private WebElement archiveMessageButton;

	@FindBy(how = How.XPATH, using = "//*[@id=\"messageContainer\"]/div[3]/div[2]/div/span[4]")
	private WebElement lableSent;

	@FindBy(how = How.XPATH, using = "//*[@id=\"messageContainer\"]/div[3]/div[2]/div[2]/a")
	private WebElement statementLinkText;

	@FindBy(how = How.XPATH, using = "//div[@class='messageMetadata clearfix']/span[1]")
	private WebElement msgSubject;

	@FindBy(xpath = "//div[@class='messageContent ng-binding']//a")
	private WebElement messageURL;

	@FindBy(xpath = "//a[contains(text(),'QuickSend.pdf')]")
	private WebElement attachmentPdfFile;

	@FindBy(xpath = "//div[@class='messageContent ng-binding']")
	private WebElement inboxMessageBody;
	
	@FindBy(how = How.XPATH, using = "//button[@class='btn btn-default ng-binding ng-scope']")
	private WebElement archiveButton;

	private static final int maxCount = 15;
	private static final String replyContent = "This is response to doctor's message";

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
		sendButton.click();
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
	
	public void archiveMessage() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 60, archiveButton);
		archiveButton.click();
	}
	
	public NGCcdViewerPage findNGCcdMessage(WebDriver driver) {
		IHGUtil.PrintMethodName();

		ccdDocument.click();

		return PageFactory.initElements(driver, NGCcdViewerPage.class);
	}
}
