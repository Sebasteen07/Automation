package com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.medfusion.portal.utils.PortalUtil;
import com.medfusion.product.object.maps.forms.page.questionnaires.PortalFormPage;

public class FormIllnessConditionsPage extends PortalFormPage {

	public FormIllnessConditionsPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "idonot_conditions")
	WebElement noConditions;

	@FindBy(id = "mononucleosis_condition_other")
	WebElement monoCheckbox;

	@FindBy(xpath = "//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;

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
	 * @Description:Set Illness Condition Form Fields
	 * @return FormFamilyHistoryPage
	 * @throws Exception
	 */
	public FormFamilyHistoryPage setIllnessConditionFormFields() throws Exception {
		setNoConditions();
		return clickSaveContinue(FormFamilyHistoryPage.class);
	}
}
