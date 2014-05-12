package com.intuit.ihg.product.object.maps.phr.page.phrRegistrationInformationPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.object.maps.phr.page.phrEmergencyContactPage.PhrEmergencyContactPage;
import com.intuit.ihg.product.phr.utils.PhrUtil;

public class PhrRegistrationInformationPage extends BasePageObject{
	
	
	
	public PhrRegistrationInformationPage(WebDriver driver) {
		super(driver);
		// Auto-generated constructor stub
	}
	

	@FindBy(linkText = "Emergency Contact")
	private WebElement emergencyContactlnktext;
	
	
	
	/**
	 * @Description:Click on Emergency Contact Link
	 * @return
	 */
	public PhrEmergencyContactPage clickEmergencyContact()
	{
		PhrUtil.PrintMethodName();
		IHGUtil.waitForElement(driver,10,emergencyContactlnktext);
		emergencyContactlnktext.click();
		return PageFactory.initElements(driver, PhrEmergencyContactPage.class );
		
	}

}
