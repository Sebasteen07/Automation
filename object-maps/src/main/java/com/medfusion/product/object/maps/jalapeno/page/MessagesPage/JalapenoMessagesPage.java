package com.medfusion.product.object.maps.jalapeno.page.MessagesPage;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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

public class JalapenoMessagesPage extends BasePageObject {
	
	@FindBy(how = How.ID, using = "askatitle_link")
	private WebElement askAQuestionButton;
	
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
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"messageContainer\"]/div[3]/div[2]/div[3]/h6/a")
	private WebElement ccdDocument;
		
	public JalapenoMessagesPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);	
	}
	
	public boolean isMessageFromDoctorDisplayed(WebDriver driver) {
		IHGUtil.PrintMethodName();
		int count = 1;
		int maxCount = 10;
		WebElement element;
		
		while(count <= maxCount){
			try {
				 driver.navigate().refresh();
				 element = driver.findElement(By.xpath("(//*[contains(text(),'Quick Send')])[1]"));
				 log("Message from doctor arrived");
				 return element.isDisplayed();
				 }
			 catch(Exception ex) {
				 log("Not arrived: " + count + "/" + maxCount + "| Refreshing page");
				 count++; 
			 }
		}
		
		log("Message from doctor didn't arrive");
		return false;
	}
	
	public void replyToMessage(WebDriver driver) {
		IHGUtil.PrintMethodName();

		log("Write a message");
		replyButton.click();
		replyBody.sendKeys("This is response to doctor's message");
		
		sendButton.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

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
	
	public boolean assessMessagesElements() {

		boolean allElementsDisplayed = false;

		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
	
		webElementsList.add(askAQuestionButton);
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
}
