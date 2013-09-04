package com.intuit.ihg.product.sitegen.page.Integrations;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

/**
 * @author bkrishnankutty
 * @Date 7/27/2013
 * @Description :- Page Object for CreateIntegrationStep2 Page
 * @Note :
 */
public class CreateIntegrationStep2Page extends BasePageObject {

	@FindBy(name = "integrationName")
	private WebElement txtIntegrationName;

	@FindBy(xpath = "//form[@id='editIntegrationForm']/fieldset/table/tbody/tr[7]/td[2]/span")
	private WebElement textIntegrationId;

	@FindBy(name = "buttons:submit")
	private WebElement btnSaveAndContinue;

	/**
	 * @author bkrishnankutty
	 * @Desc:- constructor for this Page
	 * @param driver
	 */
	public CreateIntegrationStep2Page(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:-Indicates if the search page is loaded
	 * @return true or false
	 */
	public boolean isSearchPageLoaded() {
		IHGUtil.PrintMethodName();

		boolean result = false;
		try {
			result = btnSaveAndContinue.isDisplayed();
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- click Button SaveAndContinue
	 * @return ViewIntegrationsPage
	 * 
	 */
	public ViewIntegrationsPage clickbtnSaveAndContinue() {
		IHGUtil.PrintMethodName();
		btnSaveAndContinue.click();
		return PageFactory.initElements(driver, ViewIntegrationsPage.class);
	}

}
