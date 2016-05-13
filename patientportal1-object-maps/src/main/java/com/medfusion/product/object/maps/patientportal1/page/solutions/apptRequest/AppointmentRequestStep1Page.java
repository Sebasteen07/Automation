package com.medfusion.product.object.maps.patientportal1.page.solutions.apptRequest;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.common.utils.IHGUtil;
import com.medfusion.product.patientportal1.utils.PortalUtil;


public class AppointmentRequestStep1Page extends BasePageObject{
	
	public static final String PAGE_NAME = "Appt Request Page - Step 1";
	
	@FindBy( how = How.NAME, using="locprovider:locationWrapper:_body:location")
	private WebElement dropDownLocation;
	
	@FindBy( how = How.NAME, using="locprovider:providerWrapper:_body:provider")
	private WebElement provider;
	
	@FindBy( how = How.NAME, using="insuranceWrapper:_body:insurance")
	private WebElement insurance;
	
	@FindBy( how = How.NAME, using=":submit")
	private WebElement btnContinue;

	public AppointmentRequestStep1Page(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public AppointmentRequestStep2Page requestAppointment( String title,String sLocation, String sProvider, String sInsurance ) throws InterruptedException {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		
		// Set Location if available (it could default if only one location)
		if (dropDownLocation.isDisplayed()) {
			Select selectLocation = new Select(dropDownLocation);
			selectLocation.selectByVisibleText(sLocation);
		}
		
		// Set provider
		if( sProvider != null && provider.isDisplayed() ) {
			try {
				Select providerSelect = new Select(provider);
				
				for (WebElement option : providerSelect.getOptions()) {
					Log4jUtil.log("Appt Request Provider Option: " + option.getText() + ", Needs to be: " + sProvider);
					if (option.getText().contains(sProvider)) {
						option.click();
					}
				}
				// rperkinsjr - Commenting line below as for automation practices this seems to be a select box
				// 				so code will follow select procedures
				// provider.sendKeys( sProvider );
			} catch( Exception ex ) {
				log("Couldn't set provider - may be read only: " + ex.getMessage(), "WARN");
			}
		}
		
		if( sInsurance != null )  {
			insurance.sendKeys( sInsurance );
		}

		btnContinue.click();
		return PageFactory.initElements(driver, AppointmentRequestStep2Page.class);
	}


}
