package com.medfusion.product.object.maps.patientportal1.page.myAccount.preferences;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.patientportal1.utils.PortalUtil;


public class PreferencesPage extends BasePageObject {

	public static final String PAGE_NAME = "Account Preference Page";

	@FindBy(xpath = "//input[@name='buttons:submit' and @value='Update My Preferences']")
	private WebElement but_updateYourPreferences;

	@FindBy(xpath = "//select[@id='addOption']")
	private WebElement preferredproviderDropDown;

	@FindBy(xpath = "//input[@name='inputs:3:input:input' and @value='2']")
	private WebElement rb_EmailFormathtml;

	@FindBy(xpath = "//input[@name='inputs:3:input:input' and @value='1']")
	private WebElement rb_EmailFormatText;


	@FindBy(xpath = "//input[@name='inputs:2:input:input' and @value='3']")
	private WebElement rb_statementDeliveryBoth;

	@FindBy(xpath = "//input[@name='inputs:2:input:input' and @value='2']")
	private WebElement rb_statementDeliveryE_Statement;

	@FindBy(xpath = "//input[@name='inputs:2:input:input' and @value='1']")
	private WebElement rb_statementDeliveryPaper;

	@FindBy(xpath = "//a[contains(text(),'Remove')]")
	private WebElement removeLink;

	@FindBy(xpath = "//*[@id='currentList']")
	private WebElement table_preferredProvider;


	public PreferencesPage(WebDriver driver) {
		super(driver);
	}



	public void waitforbut_updateYourPreferences(WebDriver driver, int n) {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, n, but_updateYourPreferences);
	}

	public void clickupdateYourPreferences() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);

		but_updateYourPreferences.click();
		waitforbut_updateYourPreferences(driver, 2);
	}

	/**
	 * Searches to see if the given provider is already listed as a preferred provider. If so, the 'Remove' link will be clicked to remove the given provider as a
	 * preferred provider.
	 * 
	 * @param getpreferredprovider The provider's name
	 */
	public void searchAndRemovethePreferences(String getpreferredprovider) {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);

		waitforbut_updateYourPreferences(driver, 60);
		String providerLastName = getpreferredprovider.split(",")[0];

		// Find all the preferred providers and iterate over them
		List<WebElement> allRows = table_preferredProvider.findElements(By.tagName("tr"));
		for (WebElement row : allRows) {
			List<WebElement> cells = row.findElements(By.tagName("td"));
			int cellCount = 0;
			for (WebElement cell : cells) {
				log("Found preferred provider: " + cell.getText().toString());

				if (cell.getText().toString().contains(providerLastName)) {
					cells.get(++cellCount).findElement(By.linkText("Remove")).click();
					return;
				}
			}
		}
	}

	public boolean isProviderPreferred(String preferredProvider) {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);

		boolean found = false;
		String providerLastName = preferredProvider.split(",")[0];

		// Find all the preferred providers and iterate over them
		List<WebElement> allRows = table_preferredProvider.findElements(By.tagName("tr"));
		for (WebElement row : allRows) {
			List<WebElement> cells = row.findElements(By.tagName("td"));
			for (WebElement cell : cells) {
				log("Searching for provider: " + preferredProvider + ", Found preferred provider: " + cell.getText().toString());

				if (cell.getText().toString().contains(providerLastName)) {
					found = true;
				}
			}
		}

		return found;
	}

	public void statementDeliveryNEmailFormatText() {
		try {
			IHGUtil.PrintMethodName();
			PortalUtil.setPortalFrame(driver);
			rb_statementDeliveryE_Statement.click();
			rb_EmailFormatText.click();
		} catch (Exception e) {
			Log4jUtil.log("The Manditory Fields are already Selected");
		}
	}


	/**
	 * Will find the given provider's name in the select box and select it.
	 * 
	 * @param provider the provider's name as it is displayed in the select box
	 * @throws Exception An exception be thrown if the select box can't be found or if the provider's name can't be found as an option.
	 */
	public String updateYourPreferencesProvider(String provider) throws Exception {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);

		// Provider will typically come in as: Last, First
		// Portal is not consistent on spacing so it could be: Last,First | Last, First
		// So we should split on the ',' and use the last name
		String providerLastName = provider.split(",")[0];

		Select sel = new Select(preferredproviderDropDown);
		for (WebElement option : sel.getOptions()) {

			Log4jUtil.log("Looking for preferred provider name that contains: " + providerLastName);
			Log4jUtil.log("Data found: " + option.getText());
			if (option.getText().contains(providerLastName)) {
				option.click();
				Thread.sleep(5000);
				// sel.selectByVisibleText(option.getText().toString());
				// preferredproviderDropDown.sendKeys(option.getText().toString());
				return providerLastName;
			}
		}

		// If we get here we didn't find a provider matching the one supplied
		throw new Exception("The provider name [" + provider + "] was not found as an option.");
	}

	/**
	 * Sets Statement Delivery Preference
	 * 
	 * @param Pref
	 */

	public enum p {
		E_STATEMENT, PAPER, BOTH;
	}

	public void setStatementPreference(String Pref) {
		try {
			IHGUtil.PrintMethodName();
			PortalUtil.setPortalFrame(driver);
			switch (p.valueOf(Pref)) {
				case E_STATEMENT:
					rb_statementDeliveryE_Statement.click();
					break;

				case PAPER:
					rb_statementDeliveryPaper.click();
					break;

				case BOTH:
					rb_statementDeliveryBoth.click();
					break;
			}
			rb_EmailFormatText.click();
		} catch (Exception e) {
			Log4jUtil.log("Set appropriate Statement Delievery Preference");
		}
	}


	public void checkStatementPreference(String Pref) {
		try {
			IHGUtil.PrintMethodName();
			PortalUtil.setPortalFrame(driver);
			String pref = null;
			if (rb_statementDeliveryE_Statement.isSelected())
				pref = "E_STATEMENT";
			else if (rb_statementDeliveryPaper.isSelected())
				pref = "PAPER";
			else if (rb_statementDeliveryBoth.isSelected())
				pref = "BOTH";
			Assert.assertEquals(pref, Pref, "Statement Delivery Preference not updated");
		} catch (Exception e) {
			Log4jUtil.log("No Statement Delievery Preference selected");
		}
	}

}
