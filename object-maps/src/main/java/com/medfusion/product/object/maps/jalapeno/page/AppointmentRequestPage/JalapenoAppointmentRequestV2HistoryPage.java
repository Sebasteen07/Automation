package com.medfusion.product.object.maps.jalapeno.page.AppointmentRequestPage;

import java.util.ArrayList;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.medfusion.product.object.maps.jalapeno.page.HomePage.JalapenoHomePage;


public class JalapenoAppointmentRequestV2HistoryPage extends BasePageObject{
	
	@FindBy(how = How.XPATH, using = "(//div[@id='frame']/table)")
	private WebElement fullTable;
	
	@FindBy(how = How.ID, using = "back_button")
	private WebElement backButton;
	
	@FindBy(how = How.ID, using = "home")
	private WebElement homeButton;
	
	
	public JalapenoAppointmentRequestV2HistoryPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);	
	}
	
	
	public boolean assessElements(){
		IHGUtil.PrintMethodName();
		
		log("Wait for elements");
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(fullTable);
		webElementsList.add(backButton);
	
		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}
	
	public boolean findAppointmentReasonAndOpen(String appointmentReason){
		IHGUtil.PrintMethodName();
		
		try {
			driver.findElement(By.xpath("//*[contains(text(),'" + appointmentReason + "')]")).click();
			return true;
		}catch(Exception e){
			log(e.getCause().toString());
			return false;
		}	
	}
	
	public boolean checkAppointmentDetails(String appointmentReason){
		IHGUtil.PrintMethodName();
		
		try{
			driver.findElement(By.xpath("//li[contains(@data-ng-show,'reason')]")).getText().contains(appointmentReason);
			driver.findElement(By.xpath("//li[contains(@data-ng-show,'preferredtime')]")).getText().contains("Early Morning, Late Afternoon");
			driver.findElement(By.xpath("//li[contains(@data-ng-show,'requestedday')]")).getText().contains("Monday - Thursday");
			driver.findElement(By.xpath("//li[contains(@data-ng-show,'requestedtime')]")).getText().contains("Anytime");
			return true;
		}catch(Exception e){
			log(e.getCause().toString());
			return false;
		}
		
	}
	
	public JalapenoHomePage returnToHomePage(WebDriver driver) {
		log("Return to dashboard");
		homeButton.click();
		IHGUtil.setDefaultFrame(driver);

		return PageFactory.initElements(driver, JalapenoHomePage.class);	
	}
	
}