package com.medfusion.product.object.maps.precheck.page.HomePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.medfusion.product.object.maps.precheck.page.DashboardLoginPage;
import com.medfusion.product.object.maps.precheck.page.AppointmentDetails.AppointmentDetailsPage;

public class HomePage extends BasePageObject {
	
	
	@FindBy(how = How.XPATH, using = ".//*[@id='body']/div[1]/span")
	public WebElement rightNavMenu;
	
	@FindBy(how = How.XPATH, using = ".//*[@id='menu']/a[2]/span[2]")
	public WebElement createNewAppointmentButton;
	
	@FindBy(how = How.XPATH, using = "//a[@data-ng-click='logout()']")
	public WebElement signOutButton;
	

	public HomePage(WebDriver driver) {

		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}
	
	public AppointmentDetailsPage addNewAppointment() {

		IHGUtil.PrintMethodName();
		rightNavMenu.click();
		createNewAppointmentButton.click();
		
		return PageFactory.initElements(driver, AppointmentDetailsPage.class);
	}
	
	public DashboardLoginPage logOut() {

		IHGUtil.PrintMethodName();
		rightNavMenu.click();
		signOutButton.click();
		
		return PageFactory.initElements(driver, DashboardLoginPage.class);
	}
	



}
