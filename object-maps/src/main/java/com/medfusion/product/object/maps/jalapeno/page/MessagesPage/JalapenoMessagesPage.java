package com.medfusion.product.object.maps.jalapeno.page.MessagesPage;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.medfusion.product.object.maps.jalapeno.page.CcdViewer.JalapenoCcdPage;
import com.medfusion.product.object.maps.jalapeno.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.jalapeno.page.PayBillsStatementPage.JalapenoPayBillsStatementPage;

public class JalapenoMessagesPage extends BasePageObject {
	
	/*
	@FindBy(how = How.ID, using = "askatitle_link")
	private WebElement askAQuestionButton;
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
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"signin_form\"]/button[2]")
	private WebElement sendButton;
	
	@FindBy(how = How.XPATH, using = "//a[.='View health data']")
	private WebElement ccdDocument;
		
	@FindBy(how = How.XPATH, using = "//button[.='Archive']")
	private WebElement archiveMessageButton;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"messageContainer\"]/div[3]/div[2]/div/span[4]")
	private WebElement lableSent;
	
	public JalapenoMessagesPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);	
	}
	
	public boolean isMessageDisplayed(WebDriver driver, String subject) {
		IHGUtil.PrintMethodName();
		int count = 1;
		int maxCount = 10;
		WebElement element;
		
		while(count <= maxCount) {
			try {
				element = driver.findElement(By.xpath("(//*[contains(text(),'" + subject + "')])[1]"));
				log("Message with subject \"" + subject + "\" arrived");
				return element.isDisplayed();
			}
			catch(Exception ex) {
				log("Message didn't arrive. Attempt " + count++ + "/" + maxCount + ". Refreshing page.");
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
		
		while(count <= maxCount){
			try {				
				element = driver.findElement(By.partialLinkText("Click here to view your statement"));
				log("Message from eStatement found");
				return element.isDisplayed();				
			}
			 catch(Exception ex) {
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
	
	public void replyToMessage(WebDriver driver) {
		IHGUtil.PrintMethodName();

		log("Write a message");
		replyButton.click();
		replyBody.sendKeys("This is response to doctor's message");
		
		sendButton.click();
	
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		log("Message sent");
	}
	
	public JalapenoCcdPage findCcdMessage(WebDriver driver) {
		IHGUtil.PrintMethodName();

		ccdDocument.click();
		
		return PageFactory.initElements(driver, JalapenoCcdPage.class);
	}
	
	public JalapenoHomePage backToHomePage(WebDriver driver) {
		log("Get back to Home Page");
		
		driver.findElement(By.id("home")).click();
		
		return PageFactory.initElements(driver, JalapenoHomePage.class);
	}
	
	public JalapenoPayBillsStatementPage goToPayBillsPage(WebDriver driver) {
		log("Go to Pay Bills page");
		
		driver.findElement(By.xpath("//li[@id='payments_lhn']/a")).click();
		
		return PageFactory.initElements(driver, JalapenoPayBillsStatementPage.class);
	}
	
	public boolean assessMessagesElements() {

		boolean allElementsDisplayed = false;

		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		
		webElementsList.add(inboxFolder);
		webElementsList.add(sentFolder);
		webElementsList.add(archiveFolder);

		for (WebElement w : webElementsList) {

			try {
				IHGUtil.waitForElement(driver, 60, w);
				log("Checking WebElement" + w.toString());
				if (w.isDisplayed()) {
					log("WebElement " + w.toString() + "is displayed");
					allElementsDisplayed = true;
				} else {
					log("WebElement " + w.toString() + "is NOT displayed");
					return false;
				}
			}

			catch (Throwable e) {
				log(e.getStackTrace().toString());
			}

		}
		return allElementsDisplayed;
	}

	public void archiveOpenMessage() {		
		log("Archiving open message, button is displayed? " + archiveMessageButton.isDisplayed());
		archiveMessageButton.click();				
				
	}
	public String returnMessageSentDate(){
		IHGUtil.PrintMethodName();
		//PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver, 60, lableSent);
		return lableSent.getText().toString();	
	}
}
