package com.medfusion.product.object.maps.patientportal1.page.solutions.apptRequest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;



public class AppointmentRequestStep3Page extends BasePageObject {
	
	public static final String PAGE_NAME = "Appt Request Page - Step 3";
	
	@FindBy( how = How.NAME, using=":submit")
	private WebElement submit;
	
	@FindBy( how = How.NAME, using=":change")
	private WebElement change;
	
	@FindBy( how = How.NAME, using=":cancel")
	private WebElement cancel;

	public AppointmentRequestStep3Page(WebDriver driver) {
		super(driver);
	}

	public AppointmentRequestStep4Page clickSubmit() {
		IHGUtil.waitForElement(driver,5, submit);
		submit.click();
		return PageFactory.initElements(driver, AppointmentRequestStep4Page.class);
	}
	
	// TODO - return page element.
	public void clickChange() {	
		change.click();
	}
	
	// TODO - return page element.
	public void clickCancel() {	
		cancel.click();
	}
	

}
