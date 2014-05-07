package com.intuit.ihg.product.object.maps.portal.page.questionnaires;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.portal.utils.PortalConstants;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class FormSurgeriesHospitalizationsPage extends BasePageObject
{


	public FormSurgeriesHospitalizationsPage(WebDriver driver)  {
		super(driver);
		// TODO Auto-generated constructor stub
	}


	@FindBy(id="surgeries_other_field_autocomplete")
	WebElement surgeryName;

	@FindBy(xpath="//div[@id='autocomplete']/ul/li")
	WebElement autoComplete;

	@FindBy(id="surgeries_other_field_frequency_type")
	WebElement surgeryTimeFrame;

	@FindBy(id="hospitalizations_other_field_autocomplete")
	WebElement hospitalizationReason;

	@FindBy(id="hospitalizations_other_field_frequency_type")
	WebElement hospitalizationTimeFrame;

	@FindBy(xpath="//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;

	/**
	 * @Description:Set Surgery Name
	 * @return
	 * @throws Exception
	 */	

	public void setSurgeryName(String type) throws Exception 
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		surgeryName.clear();
		surgeryName.sendKeys(type);
		surgeryName.sendKeys(Keys.TAB);
		Thread.sleep(3000);
		autoComplete.click();
	}

	/**
	 * @Description:Set Surgery TimeFrame
	 * @return
	 * @throws Exception
	 */	
	public void setSurgeryTimeFrame(String type) throws Exception 
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		Select selector = new Select(surgeryTimeFrame);
		selector.selectByVisibleText(type);
	}

	/**
	 * @Description:Set Hospitalization Reason
	 * @return
	 * @throws Exception
	 */	
	public void setHospitalizationReason(String type) throws Exception 
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		hospitalizationReason.clear();
		hospitalizationReason.sendKeys(type);
		hospitalizationReason.sendKeys(Keys.TAB);
		Thread.sleep(3000);
		autoComplete.click();
	}

	/**
	 * @Description:Set Hospitalization TimeFrame
	 * @return
	 * @throws Exception
	 */	
	public void setHospitalizationTimeFrame(String type) throws Exception 
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		Select selector = new Select(hospitalizationTimeFrame);
		selector.selectByVisibleText(type);
	}

	/**
	 * @Description:Click on Save and Continue Form Button
	 * @return
	 * @throws Exception
	 */	
	public FormPreviousExamsPage clickSaveAndContinueButton() throws Exception
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(saveAndContinuebtn));
		saveAndContinuebtn.click();
		return PageFactory.initElements(driver,FormPreviousExamsPage.class);
	}

	/**
	 * @Description:Set Surgeries Form Fields
	 * @return FormPreviousExamsPage
	 * @throws Exception
	 */
	public FormPreviousExamsPage setSurgeriesFormFields() throws Exception
	{
		setSurgeryName(PortalConstants.SurgeryName);
		setSurgeryTimeFrame(PortalConstants.SurgeryTimeFrame);
		setHospitalizationReason(PortalConstants.HospitalizationReason);
		setHospitalizationTimeFrame(PortalConstants.HospitalizationTimeFrame);
		clickSaveAndContinueButton();
		return PageFactory.initElements(driver,FormPreviousExamsPage.class);


	}
}
