package com.intuit.ihg.product.object.maps.phr.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class PhrEmergencyTermsOfUse extends BasePageObject {

	// <input id="acknowledgement" type="checkbox" value="on" name="acknowledgement">
	@FindBy(css = "input[id='acknowledgement']")
	private WebElement checkAcknowledgement;

	// <input class="custom_text_button" type="submit" value="Continue">
	@FindBy(css = "input[type='submit']")
	private WebElement btnSubmit;



	public PhrEmergencyTermsOfUse(WebDriver driver) {
		super(driver);
	}

	public void clickAcknowledgement() {
		IHGUtil.PrintMethodName();
		checkAcknowledgement.click();
	}

	public void clickSubmit() {
		IHGUtil.PrintMethodName();
		btnSubmit.click();
	}


}
