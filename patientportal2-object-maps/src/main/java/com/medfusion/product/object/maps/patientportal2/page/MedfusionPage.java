package com.medfusion.product.object.maps.patientportal2.page;

import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil.SupportedWebElements;

import static java.lang.Thread.sleep;

/**
 * Page with general functionality. TODO Should be moved somewhere to BasePageObject later
 */
public abstract class MedfusionPage extends BasePageObject {

		@FindBy(how = How.ID, using = "ng-app")
		private WebElement htmlTag;

		@FindBy(how = How.XPATH, using = "/html")
		private WebElement ccdViewerHtmlTag;

		@FindBy(how = How.ID,using = "updateMissingInformation_form")
		private WebElement weNeedToConfirmSomethingModal;

		@FindBy(how = How.ID, using = "updateMissingInfoButton")
		private WebElement okButton;

		public MedfusionPage(WebDriver driver) {
				this(driver, null);
		}

		public MedfusionPage(WebDriver driver, String url) {
				super(driver);
				log("Loading page");

				if (url != null) {
						String sanitizedUrl = url.trim();
						log("URL: " + sanitizedUrl);
						driver.get(sanitizedUrl);
				}
				System.out.println("Size of window before maximizing: " + driver.manage().window().getSize());
				// there's an issue related to hudson slave's resolution 1024x768 - can't click on CreateNewPatient element
				//driver.manage().window().maximize();
				//System.out.println("Size of window after maximizing: " + driver.manage().window().getSize());
				printCookies();
				PageFactory.initElements(driver, this);

				if (!areBasicPageElementsPresent()) {
						throw new UnsupportedOperationException("Page was not successfully loaded");
				}
		}

		public void printCookies() {
				Set<Cookie> cookies = driver.manage().getCookies();
				log("Printing Cookies -------", Level.DEBUG);
				for (Cookie c : cookies) {
						log(c.toString(), Level.DEBUG);
				}
				log("--------------------------", Level.DEBUG);
		}

		/**
		 * Will check basic elements in page constructor. Should be properly overwritten if you want to make this check
		 */
		public abstract boolean areBasicPageElementsPresent();

		public boolean isElementVisible(WebElement element, int timeOutInSeconds){
				try{
						new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.visibilityOf(element));
				}catch (Exception ex){
						log("Element is not visible.");
						return false;
				}
				log("Element " + elementToString(element) + " is visible.");
				return true;
		}
		//handles modal dialogs in Portal (accepting NPP, statement preference selection)
		public void handleWeNeedToConfirmSomethingModal(){
				log("Checking if some confirmation needed");
				while (isElementVisible(weNeedToConfirmSomethingModal, 10)){
						log("We need to confirm something modal window shown");
						okButton.click();

						try {
								//wait to modal view disappear
								sleep(2000);
						} catch (InterruptedException e) {
								e.printStackTrace();
						}
				}
		}


		public String elementToString(WebElement element) {
				return "Element (id: " + element.getAttribute("id") + ", tag: " + element.getTagName() + ")";
		}

		@Override
		protected void log(String logText) {
				super.log(createFormattedLogMessage(logText));
		}

		@Override
		protected void log(String logText, Level level) {
				super.log(createFormattedLogMessage(logText), level);
		}

		private String createFormattedLogMessage(String logText) {
				return this.getClass().getSimpleName() + ": " + logText;
		}

		/**
		 * Returns supported web element or throws error if not supported
		 */
		public SupportedWebElements getSupportedWebElement(WebElement element) {
				SupportedWebElements supportedWebElement;
				if ("select".equals(element.getTagName())) {
						supportedWebElement = SupportedWebElements.SELECT;
				} else if ("input".equals(element.getTagName())) {
						supportedWebElement = SupportedWebElements.TEXT;
				} else {
						throw new UnsupportedOperationException("Error when processing " + elementToString(element) + ". Element is not supported.");
				}

				log("Element evaluated as: " + supportedWebElement, Level.DEBUG);
				return supportedWebElement;
		}

		/**
		 * Updates web elements values, submits form and validates submitted results
		 */
		public boolean updateAndValidateWebElements(Map<WebElement, String> map, WebElement submitElement) {
				updateWebElements(map);
				clickOnElement(submitElement);
				return validateWebElements(map);
		}

		/**
		 * Updates web elements values
		 */
		public void updateWebElements(Map<WebElement, String> map) {
				for (Entry<WebElement, String> entry : map.entrySet()) {
						updateWebElement(entry.getKey(), entry.getValue());
				}
		}

		/**
		 * Clicks on web element
		 */
		public void clickOnElement(WebElement element) {
				if (element != null) {
						log("Click on: " + elementToString(element));
						element.click();
				} else {
						throw new UnsupportedOperationException("Error when clicking element - element is null. " + elementToString(element));
				}
		}

		/**
		 * Updates web element value Currently works with text and select elements only
		 */
		public void updateWebElement(WebElement element, String value) {
				log(elementToString(element) + ": updating to value: " + value, Level.DEBUG);

				SupportedWebElements swe = getSupportedWebElement(element);

				if (swe.equals(SupportedWebElements.SELECT)) {
						Select select = new Select(element);
						select.selectByVisibleText(value);
				} else if (swe.equals(SupportedWebElements.TEXT)) {
						if (StringUtils.isNotEmpty(element.getAttribute("value"))) {
								element.clear();
						}
						element.sendKeys(value);
				}

				if (!validateWebElement(element, value, swe)) {
						throw new UnsupportedOperationException("Element was not set up correctly");
				}
		}

		/**
		 * Validates that web elements has the same values as expected
		 */
		public boolean validateWebElements(Map<WebElement, String> map) {
				for (Entry<WebElement, String> entry : map.entrySet()) {
						if (!validateWebElement(entry.getKey(), entry.getValue())) {
								return false;
						}
				}
				return true;
		}

		private String createValidateWebElementErrorMessage(WebElement element, String value) {
				return elementToString(element) + " has value : " + element.getAttribute("value") + " but expected: " + value + " - ERROR";
		}

		private String createValidateWebElementOkMessage(WebElement element, String value) {
				return elementToString(element) + " has value : " + element.getAttribute("value") + " - OK";
		}

		/**
		 * Validates that web element has the same value as expected Currently works with text and select elements only
		 */
		public boolean validateWebElement(WebElement element, String value) {
				SupportedWebElements swe = getSupportedWebElement(element);
				return validateWebElement(element, value, swe);
		}

		public boolean validateWebElement(WebElement element, String value, SupportedWebElements swe) {
				switch (swe) {
						case TEXT:
								if (!value.equals(element.getAttribute("value"))) {
										log(createValidateWebElementErrorMessage(element, value));
										return false;
								} else {
										log(createValidateWebElementOkMessage(element, value), Level.DEBUG);
								}
								break;
						case SELECT:
								Select select = new Select(element);
								String selectedText = select.getFirstSelectedOption().getText();
								if (!value.equals(selectedText)) {
										log(createValidateWebElementErrorMessage(element, value));
										return false;
								} else {
										log(createValidateWebElementOkMessage(element, value), Level.DEBUG);
								}
								log(select.getFirstSelectedOption().getText(), Level.DEBUG);
								break;
				}
				return true;
		}

		public boolean assessPageElements(ArrayList<WebElement> allElements){
				return assessPageElements(allElements, 20);
		}

		public boolean assessPageElements(ArrayList<WebElement> allElements, int timeOutInSeconds) {
				log("Checking page elements", Level.DEBUG);

				for (WebElement element : allElements) {
						log("Searching for element: " + element.toString(), Level.DEBUG);
						try {
								new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.visibilityOf(element));
								log(elementToString(element) + " is displayed.", Level.INFO);
						} catch (StaleElementReferenceException ex) {
								log("StaleElementReferenceException was caught while searching for " + elementToString(element) + "." + ex.toString(), Level.ERROR);
								return false;
						} catch (TimeoutException ex) {
								log("TimeoutException was caught while searching for " + elementToString(element) + "." + ex.toString(), Level.ERROR);
								return false;
						} catch (Exception ex) {
								log("Exception was caught while searching for " + elementToString(element) + "." + ex.toString(), Level.ERROR);
								ex.printStackTrace();
								return false;
						}
				}
				return true;
		}

		public boolean isTextVisible(String text) {
				return driver.findElement(By.xpath("// * [contains(text(),'" + text + "')]")).isDisplayed();
		}

		public StringSelection getHtmlSource() {
				new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOf(htmlTag));
				return new StringSelection("<!DOCTYPE html>" + htmlTag.getAttribute("outerHTML"));
		}

		public StringSelection getIframeSource() {
				new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(ccdViewerHtmlTag));
				return new StringSelection("<!DOCTYPE html>" + ccdViewerHtmlTag.getAttribute("outerHTML"));
		}

}
