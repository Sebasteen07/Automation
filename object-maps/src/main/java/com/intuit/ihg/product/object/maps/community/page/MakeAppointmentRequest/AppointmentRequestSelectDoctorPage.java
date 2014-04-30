package com.intuit.ihg.product.object.maps.community.page.MakeAppointmentRequest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;

public class AppointmentRequestSelectDoctorPage extends BasePageObject {
	
	//Page Objects
	
	@FindBy(how = How.XPATH, using = "//button[@type='submit']")
	public WebElement btnContinue;
	
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Search for a different doctor or staff member')]")
	public WebElement serachForDifferentDoctor;
	
	public AppointmentRequestSelectDoctorPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
		}

	public AppointmentRequestSelectLocationPage SelectDoctor( String sDoctor) throws InterruptedException {	
		
		//Selecting Doctor
		driver.findElement(By.xpath("// * [contains(text(),'" + sDoctor + "')]")).click();		
		//log("DEBUG: URL After selecting the doctor [" + driver.getCurrentUrl() + "]");		btnContinue.click();
		return PageFactory.initElements(driver, AppointmentRequestSelectLocationPage.class);		
	}

}
