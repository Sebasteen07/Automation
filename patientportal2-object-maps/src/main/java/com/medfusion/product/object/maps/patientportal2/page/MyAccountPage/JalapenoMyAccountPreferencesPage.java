package com.medfusion.product.object.maps.patientportal2.page.MyAccountPage;

import static org.testng.AssertJUnit.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
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

		@FindBy(how = How.NAME, using = "preferredLanguage")
		private WebElement preferredLanguageSelect;

		@FindBy(how = How.XPATH, using = "//div[contains(@input-id,'preferredLanguage')]//div[text()='English']")
		private WebElement preferredLanguageEnglish;

		@FindBy(how = How.NAME, using = "provider")
		private WebElement preferredProvider;

		@FindBy(how = How.XPATH, using = "//div[contains(@name,'provider')]//div/span/span/span/span[2]/span")
		private List<WebElement> selectedPreferredProviders;

		@FindBy(how = How.XPATH, using = "//div[contains(@name,'provider')]//input")
		private WebElement addPreferredProvider;

		@FindBy(how = How.ID, using = "messagingOptOut")
		private WebElement patientMessagingOptOut;

		@FindBy(how = How.ID, using = "apptRemindersOptOut")
		private WebElement apptRemindersOptOut;

		@FindBy(how = How.XPATH, using = "//p[text()='You have successfully updated your preferences.']")
		private WebElement successfulUpdateMessage;

		private static final String ADD_PREFERRED_PROVIDER_LOCATOR_TEMPLATE = "//div[contains(@name,'provider')]//div[text()='%s']";
		private static final String REMOVE_PREFERRED_PROVIDER_LOCATOR = "//div[contains(@name,'provider')]//span[contains(@class,'close ui-select-match-close')]";

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
				webElementsList.add(previousStep);
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
		private void setStatementLanguage(String statementLanguageType) {
				Select statementSelect = new Select(this.preferredLanguageSelect);
				statementSelect.selectByVisibleText(statementLanguageType);
		}

		//this method is not working, the element is not select but something customized
		@Deprecated
		public void setStatementLanguage(WebDriver driver, String statementLanguageType) {
				setStatementLanguage(statementLanguageType);
				saveAccountChanges.click();
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
				saveAccountChanges.click();
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

		public void addPreferredProviderAndSave(String name) {
				IHGUtil.PrintMethodName();
				log("Add " + name + " as preferred provider");
				addPreferredProvider.click();
				String providerLocator = String.format(ADD_PREFERRED_PROVIDER_LOCATOR_TEMPLATE, name);
				driver.findElement(By.xpath(providerLocator)).click();
				saveAccountChanges.click();
		}

		public void removeAllPreferredProvidersAndSave() {
				IHGUtil.PrintMethodName();
				for (int i = 0; i < driver.findElements(By.xpath(REMOVE_PREFERRED_PROVIDER_LOCATOR)).size(); i++) {
						driver.findElement(By.xpath(REMOVE_PREFERRED_PROVIDER_LOCATOR)).click();
				}
				saveAccountChanges.click();
		}
}
