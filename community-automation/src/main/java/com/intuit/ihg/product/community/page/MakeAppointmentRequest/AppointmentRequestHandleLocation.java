package com.intuit.ihg.product.community.page.MakeAppointmentRequest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;


import com.intuit.ifs.csscat.core.pageobject.BasePageObject;

public class AppointmentRequestHandleLocation extends BasePageObject {
	
	public AppointmentRequestHandleLocation(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
public boolean checkPageTitle(){
		
		if (driver.findElements(By.xpath("//h2[contains(text(),'Select A Location')]") ).size() != 0) 
		{
			return true;	
		}
		
	 return false;
	}
	

}
