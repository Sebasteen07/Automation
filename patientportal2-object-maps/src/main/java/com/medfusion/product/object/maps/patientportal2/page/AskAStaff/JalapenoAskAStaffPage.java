package com.medfusion.product.object.maps.patientportal2.page.AskAStaff;

import java.time.LocalDate;
import java.time.Month;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.portal.utils.PortalUtil;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;

public class JalapenoAskAStaffPage extends JalapenoMenu {

		// Navigation
		@FindBy(how = How.LINK_TEXT, using = "Ask (paid)")
		private WebElement askPaidTab;
		@FindBy(how = How.ID, using = "historyButton")
		private WebElement historyButton;
		@FindBy(how = How.ID, using = "continueButton")
		private WebElement continueButton;
		@FindBy(how = How.ID, using = "cardSubmitButton")
		private WebElement cardSubmitButton;

		@FindBy(how = How.ID, using = "subject")
		private WebElement subject;
		@FindBy(how = How.ID, using = "question")
		private WebElement question;

		// Credit card
		
		@FindBy(how = How.XPATH, using = "//a[contains(@Class,'creditCardEditButton')]")
		private WebElement cardEdit;
		@FindBy(how = How.XPATH, using = "//a[contains(@Class,'creditCardRemoveButton')]")
		private WebElement cardRemove;
		@FindBy(how = How.NAME, using = "nameOnCard")
		private WebElement cardName;
		@FindBy(how = How.NAME, using = "cardNumber")
		private WebElement cardNumber;
		@FindBy(how = How.NAME, using = "ccpanel:newccdetails:creditCardType")
		private WebElement cardType;
		@FindBy(how = How.XPATH, using = "//*[@id='expirationDate_month']")
		private WebElement cardDateMonth;
		@FindBy(how = How.ID, using = "expirationDate_year")
		private WebElement cardDateYear;
		@FindBy(how = How.NAME, using = "creditCardCVV")
		private WebElement cardCVV;
		@FindBy(how = How.NAME, using = "bill_zipcode")
		private WebElement cardZip;
		@FindBy(how = How.ID, using = "creditCardAddButton")
		private WebElement creditCardAddButton;
		@FindBy(how = How.ID, using = "cvv")
		private WebElement cvv;
		@FindBy(how = How.ID, using = "removeCardOkButton")
		private WebElement removeCardOkButton;

		private long createdTS;

		public JalapenoAskAStaffPage(WebDriver driver) {
				super(driver);
				IHGUtil.PrintMethodName();
				createdTS = System.currentTimeMillis();
		}

		@Override
		public boolean areBasicPageElementsPresent() {
				//TODO
				log("Method areBasicPageElementsPresent() is not implemented, so it is considered that all expected elements are present.");
				return true;
		}

		public long getCreatedTimeStamp() {
				return createdTS;
		}

		public boolean fillAndSubmitAskAStaff(WebDriver driver) throws InterruptedException {
				IHGUtil.PrintMethodName();

				log("Fill message and continue");
				new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='viewContent']")));
				subject.sendKeys("Ola! " + this.getCreatedTimeStamp());
				question.sendKeys("Ola Doc! Please help meh.");
				Thread.sleep(3000);
				log("Fill a new credit card and submit");

				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("window.scrollBy(0,400)");

				try {
						//the Delete button for an existing card - find text in table and then the button from its parent, click
						cardRemove.click();
						IHGUtil.waitForElement(driver, 2, removeCardOkButton);
						removeCardOkButton.click();
						log("Deleting already existing card");
						PortalUtil.acceptAlert(driver);
						Thread.sleep(3000);
				} catch (NoSuchElementException e) {
						log("Element not found, patient does not have the card at the moment");
				} catch (Exception e) {
						log("Exception caught when attempting to remove existing card, something bad happened");
						e.printStackTrace();
				}
/*
				try {
						List<WebElement> li = driver.findElements(By.name("ccpanel:existingccdetailscontainer:ccRadioGroup"));
						li.get(1).click();
				} catch (IndexOutOfBoundsException e1) {
				}
*/
				IHGUtil.waitForElement(driver, 20, creditCardAddButton);
				creditCardAddButton.click();
				cardName.sendKeys("Joffrey Baratheon Lannister");
				cardNumber.sendKeys("4111111111111111");
			    LocalDate currentDate = LocalDate.now();
			    Month month = currentDate.getMonth().plus(1);
			    String cardMonth = Integer.toString(month.getValue());
			    
				//Calendar cal = Calendar.getInstance();
				//String sYear = new Integer(cal.get(Calendar.YEAR) + 1).toString();
				//Select dropdown = new Select(cardType);
				//dropdown.selectByVisibleText("Visa");
				cardCVV.sendKeys("369");
				cardZip.sendKeys("36969");
			    IHGUtil.waitForElement(driver, 5, cardDateMonth);
			    Select dropdownYear = new Select(cardDateYear);
				dropdownYear.selectByIndex(2);
				Select dropdownMonth = new Select(cardDateMonth);
				dropdownMonth.selectByValue(cardMonth);
				cardSubmitButton.click();
				cvv.sendKeys("369");
				IHGUtil.waitForElement(driver, 5,continueButton );
				continueButton.click();
				IHGUtil.waitForElement(driver, 2,continueButton );
				continueButton.click();
				//submitQuestionBut.click();
				new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Thank you for submitting your question')]")));
				return true;
		}

		public boolean checkHistory(WebDriver driver) {
			historyButton.click();
				try {
						new WebDriverWait(driver, 50)
								.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), '" + "Ola! " + this.getCreatedTimeStamp() + "')]")));
				} catch (org.openqa.selenium.NoSuchElementException e2) {
						log("Couldn't find Subject: Ola! " + this.getCreatedTimeStamp());
						return false;
				}
				return true;
		}

		public JalapenoHomePage backToHomePage(WebDriver driver) {
				log("Get back to Home Page");
				driver.findElement(By.name("cancel")).click();
				IHGUtil.setDefaultFrame(driver);
				return PageFactory.initElements(driver, JalapenoHomePage.class);
		}

	public boolean fillAndSubmitAskyourDocUnpaid(WebDriver driver) throws InterruptedException {
		IHGUtil.PrintMethodName();

		log("Fill message and continue");
		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='viewContent']")));
		subject.sendKeys("Ola! " + this.getCreatedTimeStamp());
		question.sendKeys("Ola Doc! Please help meh.");
		Thread.sleep(3000);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,400)");
		IHGUtil.waitForElement(driver, 2, continueButton);
		continueButton.click();
		IHGUtil.waitForElement(driver, 2, continueButton);
		continueButton.click();
		new WebDriverWait(driver, 20)
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Thank you for submitting your question')]")));
		return true;
	}
}
