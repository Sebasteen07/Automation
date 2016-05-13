package com.intuit.ihg.product.object.maps.smintegration.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.medfusion.product.patientportal1.utils.PortalUtil;

public class AppointmentRequestFirstPage extends BasePageObject{
	
	
	public AppointmentRequestFirstPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy( how = How.NAME, using=":submit")
	private WebElement btnContinue;
	
	
	/**
	 * @Description:Click on Continue Button
	 */
	public void clickContinueBtn()
	{
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		btnContinue.click();
		
		
	}

}
