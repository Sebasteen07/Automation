package com.intuit.ihg.product.object.maps.phr.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class PhrEmergencyAccessPage extends BasePageObject{

	//	<input type="button" value="Print Emergency Responder Card" onclick="printCard();">
	@FindBy(css="input[type='button']")
	private WebElement btnPrintEmergencyResponderCard;
	
	public PhrEmergencyAccessPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public void clickEmergencyResponderCard() {
		IHGUtil.PrintMethodName();
		btnPrintEmergencyResponderCard.click();
	}
}
