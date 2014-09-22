package com.intuit.ihg.product.object.maps.portal.page.questionnaires.prereg_pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ihg.product.object.maps.portal.page.questionnaires.PortalFormPage;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class FormFamilyHistoryPage extends PortalFormPage 
{

	public FormFamilyHistoryPage(WebDriver driver)  {
		super(driver);
	}


	@FindBy(id="idonot_familymedicalhistory")
	WebElement noFamilyHistory;

	@FindBy(xpath="//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;

	/**
	 * @Description:Set No Family History
	 * @return
	 * @throws Exception
	 */
	public void setNoFamilyHistory() throws Exception {
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		noFamilyHistory.click();

	}

}
