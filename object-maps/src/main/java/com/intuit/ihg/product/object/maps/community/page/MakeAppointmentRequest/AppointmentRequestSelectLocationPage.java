package com.intuit.ihg.product.object.maps.community.page.MakeAppointmentRequest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;

public class AppointmentRequestSelectLocationPage extends BasePageObject {
	
	//Page Objects
	
	@FindBy(how = How.XPATH, using = "//button[@type='submit']")
	public WebElement btnContinue;
	
	public AppointmentRequestSelectLocationPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
		}
	
	public AppointmentRequestScheduleAppointmentPage SelectLocation(String sLocation) throws InterruptedException {	
		
		//Selecting Location
		driver.findElement(By.xpath("// * [contains(text(),'" + sLocation + "')]")).click();				btnContinue.click();
		//log("DEBUG: URL After selecting the doctor [" + driver.getCurrentUrl() + "]");		
		return PageFactory.initElements(driver, AppointmentRequestScheduleAppointmentPage.class);
	}
	
}
