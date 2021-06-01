//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.MyAccountPage;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.patientportal2.pojo.StatementPreferenceType;

public class JalapenoMyAccountPreferencesPage extends JalapenoMyAccountPage {

		@FindBy(how = How.ID, using = "preferredLocation")
		private WebElement preferredLocation;

		@FindBy(how = How.ID, using = "statementPreference")
		private WebElement statementPreference;

		@FindBy(how = How.XPATH, using = "//ng-select[@id='languages']")
		private WebElement preferredLanguageSelect;
		
		@FindBy(how = How.XPATH, using = "//input[@id='preferredLanguage']")
		private WebElement preferredLanguageSelectTextbox;

		@FindBy(how = How.XPATH, using = "//ng-select[@id='languages']//span[text()='English']")
		private WebElement preferredLanguageEnglish;

		@FindBy(how = How.NAME, using = "provider")
		private WebElement preferredProvider;

		@FindBy(how = How.XPATH, using = "//ng-select[@id='listOfProviders']//span[2]")
		private List<WebElement> selectedPreferredProviders;

		@FindBy(how = How.XPATH, using = "//ng-select[@id='listOfProviders']//input")
		private WebElement addPreferredProvider;

		@FindBy(how = How.ID, using = "messagingOptOut")
		private WebElement patientMessagingOptOut;

		@FindBy(how = How.ID, using = "apptRemindersOptOut")
		private WebElement apptRemindersOptOut;

		@FindBy(how = How.XPATH, using = "//p[text()='You have successfully updated your preferences.']")
		private WebElement successfulUpdateMessage;

		private static final String ADD_PREFERRED_PROVIDER_LOCATOR_TEMPLATE = "//ng-select[@id='listOfProviders']//span[text()='%s']";
		private static final String REMOVE_PREFERRED_PROVIDER_LOCATOR = "//ng-select[@id='listOfProviders']//span[contains(text(),'×')]";

		public JalapenoMyAccountPreferencesPage(WebDriver driver) {
				super(driver);
				IHGUtil.PrintMethodName();
		}

		public boolean checkAndSetStatementPreference(WebDriver driver, StatementPreferenceType statementPreferenceType) {
				if (isStatementPreferenceSelected(statementPreferenceType)) {
						log("Statement preference already was set up to: " + statementPreferenceType);
						return false;
				} else {
						log("Setting up statement preference to: " + statementPreferenceType);
						setStatementPreference(statementPreferenceType);
						saveAccountChanges.click();
						checkIfStatementPreferenceWasSaved(driver, statementPreferenceType);
						return true;
				}
		}

		private boolean isStatementPreferenceSelected(StatementPreferenceType statementPreferenceType) {
				String selectedOption = new Select(this.statementPreference).getFirstSelectedOption().getAttribute("value");
				return selectedOption.equals(statementPreferenceType.name());
		}

		private void setStatementPreference(StatementPreferenceType statementPreferenceType) {
				Select statementSelect = new Select(this.statementPreference);
				statementSelect.selectByValue(statementPreferenceType.name());
		}

		private void checkIfStatementPreferenceWasSaved(WebDriver driver, StatementPreferenceType statementPreferenceType) {
				new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOf(successfulUpdateMessage));
				assertTrue(isStatementPreferenceSelected(statementPreferenceType));
				log("Statement preference is set up to: " + statementPreferenceType);
		}

		public boolean checkStatementPreferenceUpdated(StatementPreferenceType statementPreferenceType) {
				if (isStatementPreferenceSelected(statementPreferenceType)) {
						log("Statement preference is: " + statementPreferenceType);
						return true;
				} else {
						log("Statement preference is not updated.");
						return false;
				}
		}

		@Override
		public boolean areBasicPageElementsPresent() {
				ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();

				webElementsList.add(patientMessagingOptOut);
				webElementsList.add(apptRemindersOptOut);
				webElementsList.add(saveAccountChanges);

				return assessPageElements(webElementsList);
		}

		@Deprecated
		public boolean areStatementPreferenceAndPreferedLocationElementsPresent() {
				ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();

				webElementsList.add(preferredLocation);
				webElementsList.add(statementPreference);

				return assessPageElements(webElementsList);
		}

		//this method is not working, the element is not select but something customized
		@Deprecated
		private void setStatementLanguageT(String statementLanguageType) throws InterruptedException {
				//Select statementSelect = new Select(this.preferredLanguageSelect);
				//statementSelect.selectByVisibleText(statementLanguageType);
				log("Setting preferred language");
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("window.scrollBy(0,150)", "");
				preferredLanguageSelect.click();
				Thread.sleep(1000);
				preferredLanguageSelectTextbox.sendKeys(statementLanguageType);	
				preferredLanguageSelectTextbox.sendKeys(Keys.ENTER);

		}

		//this method is not working, the element is not select but something customized
		@Deprecated
		public void setStatementLanguage(WebDriver driver, String statementLanguageType) throws InterruptedException {
				setStatementLanguageT(statementLanguageType);
				javascriptClick(saveAccountChanges);
		}

		//TODO move to JalapenoMyAccountPage
		@Deprecated
		public JalapenoMyAccountActivityPage goToActivityTab(WebDriver driver) {
				log("Click on Activity");
				activityTab.click();
				return PageFactory.initElements(driver, JalapenoMyAccountActivityPage.class);
		}

		public String getPreferredLanguage() {
				return preferredLanguageSelect.getText();
		}

		public void setEnglishAsPreferredLanguageAndSave() {
				IHGUtil.PrintMethodName();
				wait.until(ExpectedConditions.elementToBeClickable(preferredLanguageSelect));
				preferredLanguageSelect.click();
				wait.until(ExpectedConditions.elementToBeClickable(preferredLanguageEnglish));
				preferredLanguageEnglish.click();
				javascriptClick(saveAccountChanges);
		}

		public List<String> getPreferredProviders() {
				IHGUtil.PrintMethodName();
				ArrayList<String> preferredProviders = new ArrayList<String>();
				for (WebElement e : selectedPreferredProviders) {
						preferredProviders.add(e.getText());
				}
				log("Preferred providers: " + preferredProviders);
				return preferredProviders;
		}

		public void addPreferredProviderAndSave(String name) throws InterruptedException{
				IHGUtil.PrintMethodName();
				log("Add " + name + " as preferred provider");
				addPreferredProvider.click();
				String providerLocator = String.format(ADD_PREFERRED_PROVIDER_LOCATOR_TEMPLATE, name);
				driver.findElement(By.xpath(providerLocator)).click();
				javascriptClick(saveAccountChanges);
		}

		public void removeAllPreferredProvidersAndSave() {
				IHGUtil.PrintMethodName();
				for (int i = 0; i < driver.findElements(By.xpath(REMOVE_PREFERRED_PROVIDER_LOCATOR)).size(); i++) {
						driver.findElement(By.xpath(REMOVE_PREFERRED_PROVIDER_LOCATOR)).click();
				}

				javascriptClick(saveAccountChanges);}
}
