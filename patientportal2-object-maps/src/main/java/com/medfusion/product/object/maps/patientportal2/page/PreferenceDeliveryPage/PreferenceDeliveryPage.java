package com.medfusion.product.object.maps.patientportal2.page.PreferenceDeliveryPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class PreferenceDeliveryPage extends BasePageObject {

	@FindBy(how = How.ID, using = "paymentPreference_Electronic")
	private WebElement inputPaymentElectronic;

	@FindBy(how = How.ID, using = "paymentPreference_Paper")
	private WebElement inputPaymentPaper;

	@FindBy(how = How.ID, using = "updateMissingInfoButton")
	private WebElement updateMissingInfoButton;
	
	
	public PreferenceDeliveryPage(WebDriver driver) {	
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void clickOKButton() {
		
		IHGUtil.PrintMethodName();
		log("Clicking on updateMissingInfoButton button");
		updateMissingInfoButton.click();
	}
	
	public void clickElectronicPaymentPreference() {
		
		IHGUtil.PrintMethodName();
		log("Clicking on Electronically radiobutton");
		inputPaymentElectronic.click();
	}
	
	public void clickPaperPaymentPreference() {
		
		IHGUtil.PrintMethodName();
		log("Clicking on In the mail radiobutton");
		inputPaymentPaper.click();
	}
	
	


}
