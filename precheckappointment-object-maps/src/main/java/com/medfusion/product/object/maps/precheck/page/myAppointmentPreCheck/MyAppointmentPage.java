package com.medfusion.product.object.maps.precheck.page.myAppointmentPreCheck;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;

public class MyAppointmentPage extends BasePageObject  {

	@FindBy(how = How.ID, using = "demographicsHeader")
	private WebElement demographicsHeaderLink;
	
	@FindBy(how = How.ID, using = "insuranceHeader")
	private WebElement insuranceHeaderLink;
	
	@FindBy(how = How.ID, using = "coPayHeader")
	private WebElement coPayHeaderLink;
	
	@FindBy(how = How.ID, using = "balanceHeader")
	private WebElement balanceHeaderLink;
	
	@FindBy(how = How.ID, using = "calendarListItem")
	private WebElement calendarListItemDownload;
	
	public MyAppointmentPage(WebDriver driver) {
		super(driver);
	}
	
	public MyDemoGraphicsDetailPage gotoDemographicsDetailPage() {
		javascriptClick(demographicsHeaderLink);
		return PageFactory.initElements(driver, MyDemoGraphicsDetailPage.class);
	}
	
	public MyInsuranceDetailPage gotoInsuranceDetailPage() {
		javascriptClick(insuranceHeaderLink);
		return PageFactory.initElements(driver, MyInsuranceDetailPage.class);
	}
	
	public MyCoPayDetailPage gotoCoPayDetailPage() {
		coPayHeaderLink.click();
		return PageFactory.initElements(driver, MyCoPayDetailPage.class);
	}
	
	public MyBalanceDetailPage gotoBalanceDetailPage() {
		balanceHeaderLink.click();
		return PageFactory.initElements(driver, MyBalanceDetailPage.class);
	}
	
	public void downloadAppointmentCalander() {
		calendarListItemDownload.click();
	}
}