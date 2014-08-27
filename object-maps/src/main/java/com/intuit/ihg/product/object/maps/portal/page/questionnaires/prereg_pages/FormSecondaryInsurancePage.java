package com.intuit.ihg.product.object.maps.portal.page.questionnaires.prereg_pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ihg.product.object.maps.portal.page.questionnaires.PortalFormPage;

public class FormSecondaryInsurancePage extends PortalFormPage {
	
	public FormSecondaryInsurancePage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(id = "idonot_secondary_insurance")
	private WebElement idonotSecondaryInsurance;
	
	/**
	 * @Description:Set no secondary insurance
	 * @throws Exception
	 */
	public void setNoSecondaryInsurance() throws Exception {
		idonotSecondaryInsurance.click();
	}
	
}
