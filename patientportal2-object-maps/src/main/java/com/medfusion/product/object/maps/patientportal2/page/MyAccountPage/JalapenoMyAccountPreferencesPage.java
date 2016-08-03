package com.medfusion.product.object.maps.patientportal2.page.MyAccountPage;

import static org.testng.AssertJUnit.assertTrue;

import java.util.ArrayList;

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

	@FindBy(how = How.ID, using = "patientMessagingOptOut")
	private WebElement patientMessagingOptOut;

	@FindBy(how = How.ID, using = "apptRemindersOptOut")
	private WebElement apptRemindersOptOut;

	@FindBy(how = How.XPATH, using = "//p[text()='You have successfully updated your preferences.']")
	private WebElement successfulUpdateMessage;

	public JalapenoMyAccountPreferencesPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
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

	public boolean assessPageElements() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();

		webElementsList.add(preferredLocation);
		webElementsList.add(statementPreference);
		webElementsList.add(patientMessagingOptOut);
		webElementsList.add(apptRemindersOptOut);
		webElementsList.add(previousStep);
		webElementsList.add(saveAccountChanges);

		return super.assessPageElements(true) && new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}
}
