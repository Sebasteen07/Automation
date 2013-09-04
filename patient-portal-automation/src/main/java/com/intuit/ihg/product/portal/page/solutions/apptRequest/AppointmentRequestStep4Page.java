package com.intuit.ihg.product.portal.page.solutions.apptRequest;

import junit.framework.Assert;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.portal.page.MyPatientPage;
import com.intuit.ihg.product.portal.utils.PortalUtil;


public class AppointmentRequestStep4Page extends BasePageObject {
	
	public static final String PAGE_NAME = "Appt Request Page - Step 4";
	
	// ?wicket:bookmarkablePage=:net.medfusion.encounter.patient.solutions.apptrequest.ApptReqHistory
		@FindBy(xpath="//a[contains(@href,'ApptReqHistory')]")
		private WebElement history;
		
		// /secure/portal/index.cfm?fuseaction=ars.sa
		@FindBy(xpath="//a[contains(@href,'ars.sa')]")
		private WebElement symptomAssessment;	// Click Here
        
		@FindBy(xpath="//a[contains(@href,'welcome.cfm')]")
		private WebElement lnkBackToMyPatientPage;
		
		public AppointmentRequestStep4Page(WebDriver driver) {
			super(driver);
			// TODO Auto-generated constructor stub
		}
		
		// TODO - return page object.
		public void clickHistory() {		
			IHGUtil.PrintMethodName();			
			history.click();
		}
		
		// TODO - return page object
		public void clickSymptomAssessment() {
			IHGUtil.PrintMethodName();			
			symptomAssessment.click();
		}
		
		public MyPatientPage clickBackToMyPatientPage() throws InterruptedException {			
			IHGUtil.PrintMethodName();
			PortalUtil.setPortalFrame(driver);			
			IHGUtil.waitForElement(driver,5, lnkBackToMyPatientPage);
								
			Assert.assertNotNull("Couldn't find My Patient Page link on Appt Request confirmation page.", 
					lnkBackToMyPatientPage.isDisplayed());
			
			lnkBackToMyPatientPage.click();			
			return PageFactory.initElements(driver, MyPatientPage.class);
		}


}
