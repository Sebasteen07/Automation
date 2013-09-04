package com.intuit.ihg.product.sitegen.page.Integrations;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.common.utils.IHGUtil;

/**
 * @author bkrishnankutty
 * @Date 7/3/2013
 * @Description :- Page Object for SiteGen Create IntegrationStep1
 * @Note :
 */
public class CreateIntegrationStep1Page extends BasePageObject {

	@FindBy(name = "externalSystemSelect")
	private WebElement dropDownExternalSystem;

	@FindBy(name = "channelSelect")
	private WebElement dropDownChannelSelect;

	@FindBy(name = "reviewTypeSelect")
	private WebElement dropDownReviewTypeSelect;

	@FindBy(name = "integrationName")
	private WebElement txtIntegrationName;

	@FindBy(name = "buttons:submit")
	private WebElement btnContinue;

	@FindBy(name = "buttons:cancel")
	private WebElement btnCancel;

	/**
	 * @author bkrishnankutty
	 * @Desc:- constructor for this Page
	 * @param driver
	 */
	public CreateIntegrationStep1Page(WebDriver driver) {
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
			result = txtIntegrationName.isDisplayed();
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- Add New IntegrationEngine
	 * @return CreateIntegrationStep2Page
	 * 
	 * @param externalSystem
	 * @param channel
	 * @param integrationName
	 * @param reviewType
	 * 
	 * @throws InterruptedException
	 */

	public CreateIntegrationStep2Page addNewIntegrationEng(
			String externalSystem, String channel, String integrationName,
			String reviewType) throws InterruptedException {
		IHGUtil.PrintMethodName();

		Select selExternalSystem = new Select(dropDownExternalSystem);
		selExternalSystem.selectByVisibleText(externalSystem);

		txtIntegrationName.sendKeys(integrationName);

		Select selReviewTypeSelect = new Select(dropDownReviewTypeSelect);
		selReviewTypeSelect.selectByVisibleText(reviewType);

		Thread.sleep(2000);
		Select selChannelSelect = new Select(dropDownChannelSelect);
		log("#######channel" + channel);
		selChannelSelect.selectByVisibleText(channel);

		btnContinue.click();

		return PageFactory.initElements(driver,
				CreateIntegrationStep2Page.class);

	}
}
