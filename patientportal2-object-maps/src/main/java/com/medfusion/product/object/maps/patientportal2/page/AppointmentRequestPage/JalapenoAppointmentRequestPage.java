package com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage;

import java.util.ArrayList;

import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import org.openqa.selenium.support.ui.WebDriverWait;

public class JalapenoAppointmentRequestPage extends JalapenoMenu {

		@FindBy(how = How.LINK_TEXT, using = "Request Appointment")
		private WebElement requestTab;

		@FindBy(how = How.NAME, using = "timeframeWrapper:_body:timeframe")
		private WebElement prefTimeFrame;

		@FindBy(how = How.NAME, using = "prefdayWrapper:_body:prefday")
		private WebElement prefDay;

		@FindBy(how = How.NAME, using = "preftimeWrapper:_body:preftime")
		private WebElement prefTime;

		@FindBy(how = How.NAME, using = "reasonWrapper:_body:reason")
		private WebElement appointmentReasonText;

		@FindBy(how = How.NAME, using = "preferenceWrapper:_body:preference")
		private WebElement preference;

		@FindBy(how = How.NAME, using = "homephoneWrapper:_body:homephone")
		private WebElement homePhone;

		@FindBy(how = How.NAME, using = ":submit")
		private WebElement continueButton;

		@FindBy(how = How.ID, using = "id1b")
		private WebElement homeButton; //this is not Home button from Jalapeno Menu

		public JalapenoAppointmentRequestPage(WebDriver driver) {
				super(driver);
				IHGUtil.PrintMethodName();
				driver.manage().window().maximize();
				PageFactory.initElements(driver, this);
		}

		@Override
		public boolean areBasicPageElementsPresent() {
				ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
				//webElementsList.add(requestTab); //TODO it is failing because the page is iframed
				return assessPageElements(webElementsList);
		}


		public void clickOnContinueButton(WebDriver driver) {
				log("Click on Continue button");
				IHGUtil.setFrame(driver, "iframebody");

				javascriptClick(continueButton);
		}

		public JalapenoHomePage returnToHomePage(WebDriver driver) {
				log("Return to dashboard");
				homeButton.click();
				IHGUtil.setDefaultFrame(driver);

				return PageFactory.initElements(driver, JalapenoHomePage.class);
		}

		public boolean fillAndSendTheAppointmentRequest(WebDriver driver, String appointmentReason) {
				IHGUtil.setFrame(driver, "iframebody");
				log("Filling the appointment form");
				//unfortunately the most stable way to wait for styles and full display of the appointment iframe is to wait out the first two seconds...
				try {
						Thread.sleep(2000);
				} catch (InterruptedException e1) {
						e1.printStackTrace();
				}
				Select dropdown = new Select(prefTimeFrame);
				dropdown.selectByVisibleText("First Available");

				dropdown = new Select(prefDay);
				dropdown.selectByVisibleText("Monday");

				prefTime.sendKeys("Morning");
				appointmentReasonText.sendKeys(appointmentReason);

				dropdown = new Select(preference);
				dropdown.selectByVisibleText("Specific Provider");

				if (homePhone.getAttribute("value").equals("")) {
						homePhone.sendKeys("1234567890");
				}

				log("Click on Continue button");
				continueButton.click();

				log("Submit the request");
				for (int i = 1; i <= 5; i++) {
						try {
								log("Find Submit the Request button, trial: " + i);
								javascriptClick(new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.name(":submit"))));
								log("Click on Submit the Request was successful");
								break;
						} catch (StaleElementReferenceException ex) {
								log("Stale Element Reference Exception was thrown.");
						} catch (TimeoutException e) {
								log("Timeout Exception was thrown.");
						}
				}

				try {
						IHGUtil.waitForElement(driver, 60, homeButton);
						log("Checking WebElement" + homeButton.toString());
						if (homeButton.isDisplayed()) {
								log(homeButton.toString() + "is displayed");
								log("Request was successfully submitted");
								return true;
						} else {
								log(homeButton.toString() + "is NOT displayed");
								return false;
						}
				} catch (Throwable e) {
						log(e.getStackTrace().toString());
				}

				IHGUtil.setDefaultFrame(driver);
				return false;
		}
}
