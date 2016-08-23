package com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.medfusion.product.object.maps.forms.page.questionnaires.PortalFormPage;

public class FormFamilyHistoryPage extends PortalFormPage {

	public FormFamilyHistoryPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "idonot_familymedicalhistory")
	WebElement noFamilyHistory;

	/**
	 * @Description: Set No Family History
	 */
	public void setNoFamilyHistory() throws Exception {
		noFamilyHistory.click();
	}

}
