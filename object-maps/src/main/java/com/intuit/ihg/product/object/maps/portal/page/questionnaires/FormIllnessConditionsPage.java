package com.intuit.ihg.product.object.maps.portal.page.questionnaires;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class FormIllnessConditionsPage extends BasePageObject
{

	public FormIllnessConditionsPage(WebDriver driver)  {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id="idonot_conditions")
	WebElement noConditions;
	
	@FindBy(id="mononucleosis_condition_other")
	WebElement monoCheckbox;

	@FindBy(xpath="//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;
	
	@FindBy(xpath="//*[@id='container']//section/div[@class='done_frame']/a")
	private WebElement submitForm;

	@FindBy(xpath = ".//iframe[@title ='Forms']")
	private WebElement iframe;
	
	@FindBy(id = "closeFormButton")
	private WebElement closeButton;

	/**
	 * @Description:Set No Conditions
	 * @return
	 * @throws Exception
	 */
	public void setNoConditions() throws Exception {
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		noConditions.click();
	}

	public void checkMononucleosis() throws Exception {
		if (monoCheckbox.isSelected() == false)
			monoCheckbox.click();
	}
	
	/**
	 * @Description:Click on Submit Form Button
	 * @return
	 * @throws Exception
	 */
	public void submitForm() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 15);	
		
		submitForm.click();
		wait.until(ExpectedConditions.elementToBeClickable(closeButton));	
		closeButton.click();
	}
	
	/**
	 * @description Clicks on Continue Button
	 * @param nextPageClass Class of the following page in the form
	 * @return initialized PageObject for the next page
	 * @throws Exception
	 */
	public <T extends BasePageObject> T clickSaveAndContinueButton(Class<T> nextPageClass) throws Exception {
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(saveAndContinuebtn));
		saveAndContinuebtn.click();
		return nextPageClass == null ? null : PageFactory.initElements(driver, nextPageClass);
	}

	/**
	 * @Description:Set Illness Condition Form Fields
	 * @return FormFamilyHistoryPage
	 * @throws Exception
	 */
	public FormFamilyHistoryPage setIllnessConditionFormFields() throws Exception {
		setNoConditions();
		return  clickSaveAndContinueButton(FormFamilyHistoryPage.class);
	}
}
