package com.intuit.ihg.product.object.maps.portal.page.questionnaires.prereg_pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ihg.product.object.maps.portal.page.questionnaires.PortalFormPage;

public class FormSocialHistoryPage extends PortalFormPage
{

	public FormSocialHistoryPage(WebDriver driver)  {
		super(driver);
	}
	
	@FindBy(xpath="//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;

	@FindBy(xpath="//*[@id='container']//section/div[@class='done_frame']/a")
	private WebElement submitForm;

	@FindBy( xpath = ".//iframe[@title ='Forms']")
	private WebElement iframe;
	
	/**
	 * @Description: Continue through the form and submit it
	 * @return FormSocialHistoryPage
	 * @throws Exception
	 */
	public void submitFromSocialHistory() throws Exception {	
		clickSaveAndContinueButton(null);
		submitForm();
	}

}
