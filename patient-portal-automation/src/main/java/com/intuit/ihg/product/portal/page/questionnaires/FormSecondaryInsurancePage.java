package com.intuit.ihg.product.portal.page.questionnaires;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class FormSecondaryInsurancePage extends BasePageObject 
{
	
	public FormSecondaryInsurancePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(id = "idonot_secondary_insurance")
	private WebElement idonotSecondaryInsurance;
	
	@FindBy(id = "nextPageButton")
	private WebElement saveContinue;	
	
	/**
	 * @Description:Set no secondary insurance
	 * @throws Exception
	 */

	public void setNoSecondaryInsurance() throws Exception 
	{
		Thread.sleep(2000);
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		idonotSecondaryInsurance.click();

	}
	
	/**
	 * @Description:Click on Save and Continue Button
	 * @return FormOtherProvidersPage
	 * @throws Exception
	 */
	public BasePageObject clickSaveAndContinueButton() throws Exception
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(saveContinue));
		saveContinue.click();
		return PageFactory.initElements(driver, FormOtherProvidersPage.class);
	
	}

	/**
	 * @Description:Set no secondary insurance and continue
	 * @return FormOtherProvidersPage
	 * @throws Exception
	 */
	public FormOtherProvidersPage SetnoSecondaryInsuranceAndContinue() throws Exception
	{
		setNoSecondaryInsurance();

		clickSaveAndContinueButton();

		return PageFactory.initElements(driver, FormOtherProvidersPage.class);

	}
}
