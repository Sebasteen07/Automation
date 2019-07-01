package com.medfusion.product.object.maps.patientportal2.page.AskAStaff;

import java.util.Calendar;
import java.util.List;

import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;
import org.openqa.selenium.By;
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
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;

public class JalapenoAskAStaffPage extends JalapenoMenu {

		// Navigation
		@FindBy(how = How.LINK_TEXT, using = "Ask (paid)")
		private WebElement askPaidTab;
		@FindBy(how = How.LINK_TEXT, using = "History")
		private WebElement historyBut;
		@FindBy(how = How.NAME, using = ":submit")
		private WebElement continueBut;
		@FindBy(how = How.NAME, using = "buttons:submit")
		private WebElement submitQuestionBut;

		@FindBy(how = How.NAME, using = "subjectWrapper:_body:subject")
		private WebElement subject;
		@FindBy(how = How.NAME, using = "questionWrapper:_body:question")
		private WebElement question;

		// Credit card
		@FindBy(how = How.NAME, using = "ccpanel:newccdetails:nameOnCreditCard")
		private WebElement cardName;
		@FindBy(how = How.NAME, using = "ccpanel:newccdetails:creditCardNumber")
		private WebElement cardNumber;
		@FindBy(how = How.NAME, using = "ccpanel:newccdetails:creditCardType")
		private WebElement cardType;
		@FindBy(how = How.NAME, using = "ccpanel:newccdetails:expirationMonth")
		private WebElement cardDateMonth;
		@FindBy(how = How.NAME, using = "ccpanel:newccdetails:expirationYear")
		private WebElement cardDateYear;
		@FindBy(how = How.NAME, using = "ccpanel:newccdetails:newccdetailscvv:cvvCode")
		private WebElement cardCVV;
		@FindBy(how = How.NAME, using = "ccpanel:newccdetails:addressZip")
		private WebElement cardZip;

		private long createdTS;

		public JalapenoAskAStaffPage(WebDriver driver) {
				super(driver);
				IHGUtil.PrintMethodName();
				driver.manage().window().maximize();
				PageFactory.initElements(driver, this);
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

		public boolean fillAndSubmitAskAStaff(WebDriver driver) {
				IHGUtil.PrintMethodName();
				try {
						askPaidTab.click();
				} catch (org.openqa.selenium.NoSuchElementException e0) {
						log("--------------------------------------------------------------");
						log("I couldn't find 'Ask (paid)' button, please check at practice!");
						return false;
				}
				log("Fill message and continue");
				new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='viewContent']/div[1]/h2")));
				IHGUtil.setFrame(driver, "iframebody");
				subject.sendKeys("Ola! " + this.getCreatedTimeStamp());
				question.sendKeys("Ola Doc! Please help meh.");
				continueBut.click();

				log("Fill a new credit card and submit");
				IHGUtil.waitForElement(driver, 20, submitQuestionBut);
				try {
						//the Delete button for an existing card - find text in table and then the button from its parent, click
						driver.findElement(By.xpath("//table[@class='recent']/tbody/tr/td[contains(text(),'Joffrey Baratheon Lannister')]/../td[6]/a[2]")).click();
						log("Deleting already existing card");
						PortalUtil.acceptAlert(driver);
						Thread.sleep(3000);
				} catch (NoSuchElementException e) {
						log("Element not found, patient does not have the card at the moment");
				} catch (Exception e) {
						log("Exception caught when attempting to remove existing card, something bad happened");
						e.printStackTrace();
				}

				try {
						List<WebElement> li = driver.findElements(By.name("ccpanel:existingccdetailscontainer:ccRadioGroup"));
						li.get(1).click();
				} catch (IndexOutOfBoundsException e1) {
				}
				cardName.sendKeys("Joffrey Baratheon Lannister");
				cardNumber.sendKeys("4111111111111111");
				Calendar cal = Calendar.getInstance();
				String sYear = new Integer(cal.get(Calendar.YEAR) + 1).toString();
				Select dropdown = new Select(cardType);
				dropdown.selectByVisibleText("Visa");
				dropdown = new Select(cardDateMonth);
				dropdown.selectByVisibleText("February");
				dropdown = new Select(cardDateYear);
				dropdown.selectByVisibleText(sYear);
				cardCVV.sendKeys("369");
				cardZip.sendKeys("36969");
				submitQuestionBut.click();
				new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Home')]")));
				return true;
		}

		public boolean checkHistory(WebDriver driver) {
				historyBut.click();
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
}
