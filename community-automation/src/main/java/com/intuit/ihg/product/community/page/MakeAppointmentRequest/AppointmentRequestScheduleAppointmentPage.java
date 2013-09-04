package com.intuit.ihg.product.community.page.MakeAppointmentRequest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;import com.intuit.ihg.common.utils.IHGUtil;import com.intuit.ihg.product.community.page.CommunityHomePage;import com.intuit.ihg.product.portal.page.solutions.apptRequest.AppointmentRequestStep3Page;

public class AppointmentRequestScheduleAppointmentPage extends BasePageObject {
	
	//Page Objects
	
		@FindBy(how = How.ID, using = "reasonforappointmnt")
		public WebElement inputReasonForAppointment;
	
		@FindBy(how = How.XPATH, using = "//button[@type='submit']")
		public WebElement btnContinue;
		
		public AppointmentRequestScheduleAppointmentPage(WebDriver driver) {
			super(driver);
			PageFactory.initElements(driver, this);
			
			}		public CommunityHomePage filloutAppoitmentDetails(String reason) throws  InterruptedException {	IHGUtil.PrintMethodName();	inputReasonForAppointment.sendKeys(reason);	btnContinue.click();	return PageFactory.initElements(driver, CommunityHomePage.class);	}

}
