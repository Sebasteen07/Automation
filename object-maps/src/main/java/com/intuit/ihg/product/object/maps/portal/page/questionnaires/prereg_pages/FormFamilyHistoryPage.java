package com.intuit.ihg.product.object.maps.portal.page.questionnaires.prereg_pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ihg.product.object.maps.portal.page.questionnaires.PortalFormPage;

public class FormFamilyHistoryPage extends PortalFormPage 
{

	public FormFamilyHistoryPage(WebDriver driver)  {
		super(driver);
	}

	@FindBy(id="idonot_familymedicalhistory")
	WebElement noFamilyHistory;

	/**
	 * @Description: Set No Family History
	 */
	public void setNoFamilyHistory() throws Exception {
		noFamilyHistory.click();
	}

}
