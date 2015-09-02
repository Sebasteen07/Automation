package com.medfusion.product.object.maps.jalapeno.page.AppointmentRequestPage;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.medfusion.product.object.maps.jalapeno.page.HomePage.JalapenoHomePage;
import org.openqa.selenium.support.ui.WebDriverWait;

public class JalapenoAppointmentRequestPage extends BasePageObject{

	@FindBy(how = How.LINK_TEXT, using = "Request Appointment")
	private WebElement requestTab;
	
	@FindBy(how = How.NAME, using = "timeframeWrapper:_body:timeframe")
	private WebElement prefTimeFrame;
	
	@FindBy(how = How.NAME, using = "prefdayWrapper:_body:prefday")
	private WebElement prefDay;
	
	@FindBy(how = How.NAME, using = "preftimeWrapper:_body:preftime")
	private WebElement prefTime;
	
	@FindBy(how = How.NAME, using = "reasonWrapper:_body:reason")
	private WebElement appointmentReason;
	
	@FindBy(how = How.NAME, using = "preferenceWrapper:_body:preference")
	private WebElement preference;
	
	@FindBy(how = How.NAME, using = "homephoneWrapper:_body:homephone")
	private WebElement homePhone;
	
	@FindBy(how = How.NAME, using = ":submit")
	private WebElement continueButton;
	
	@FindBy(how = How.ID, using = "id1b")
	private WebElement homeButton;
	
	public JalapenoAppointmentRequestPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);	
	}
	
	public void clickOnContinueButton(WebDriver driver) {
		log("Click on Continue button");
		IHGUtil.setFrame(driver, "iframebody");

		continueButton.click();
	}
	
	public JalapenoHomePage returnToHomePage(WebDriver driver) {
		log("Return to dashboard");
		homeButton.click();
		IHGUtil.setDefaultFrame(driver);

		return PageFactory.initElements(driver, JalapenoHomePage.class);	
	}
	
	public boolean fillAndSendTheAppointmentRequest(WebDriver driver) {
		IHGUtil.setFrame(driver, "iframebody");
		log("Filling the appointment form");
		
		Select dropdown = new Select(prefTimeFrame);
		dropdown.selectByVisibleText("First Available");
		
		dropdown = new Select(prefDay);
		dropdown.selectByVisibleText("Monday");
		
		prefTime.sendKeys("Morning");
		appointmentReason.sendKeys("Illness");
		
		dropdown = new Select(preference);
		dropdown.selectByVisibleText("Specific Provider");
		
		if(homePhone.getAttribute("value").equals("")) {
			homePhone.sendKeys("1234567890");
		}
		
		log("Click on Continue button");
		continueButton.click();
		
		log("Submit the request");
		int i = 1;
		while(i < 5) {
			try{
				log("Find Submit the Request button " + i);
				new WebDriverWait(driver, 20).until(
						ExpectedConditions.elementToBeClickable(By.name(":submit"))).click();
				log("Click on Submit the Request was successful");
				i = 5;
			}
			catch(StaleElementReferenceException ex) {
				i++;
			}
		}
		
		try {
			IHGUtil.waitForElement(driver, 60, homeButton);
			log("Checking WebElement" + homeButton.toString());
			if (homeButton.isDisplayed()) {
				log(homeButton.toString() + "is displayed");
				log("Request was succesfully submitted");
				return true;
			} else {
				log(homeButton.toString() + "is NOT displayed");
				return false;
			}
		}
		catch (Throwable e) {
			log(e.getStackTrace().toString());
		}
		
		IHGUtil.setDefaultFrame(driver);
		return false;
	}
	
	public boolean assessAppointmentPageElements() {

		boolean allElementsDisplayed = false;

		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
	
		webElementsList.add(requestTab);

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
