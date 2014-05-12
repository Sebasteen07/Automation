package com.intuit.ihg.product.object.maps.portal.page.questionnaires;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class FormOtherProvidersPage extends BasePageObject
{


	public FormOtherProvidersPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id="idonot_referring_doctors")
	WebElement noOtherProviders;

	@FindBy(xpath="//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;

	/**
	 * @Description:Set No Providers
	 * @throws Exception
	 */
	public void setNoProviders() throws Exception 
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		noOtherProviders.click();

	}

	/**
	 * @Description:Click on Save and Continue Form Button
	 * @return
	 * @throws Exception
	 */
	public FormCurrentSymptomsPage clickSaveAndContinueButton() throws Exception
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(saveAndContinuebtn));
		saveAndContinuebtn.click();
		return PageFactory.initElements(driver, FormCurrentSymptomsPage.class);
	}

	/**
	 * @Description:Set Providers Form Fields
	 * @return FormCurrentSymptomsPage
	 * @throws Exception
	 */
	public FormCurrentSymptomsPage setNoProvidersOnPage() throws Exception
	{
		setNoProviders();

		clickSaveAndContinueButton();

		return PageFactory.initElements(driver, FormCurrentSymptomsPage.class);


	}

}
