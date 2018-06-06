package com.medfusion.product.object.maps.pss2.page.settings;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class AdminAppointment extends SettingsTab {

	@FindBy(how = How.ID, using = "cancelappointment")
	private WebElement cancelAppointment;

	@FindBy(how = How.ID, using = "blockpatientmonths")
	private WebElement blockPatientMonths;
	
	@FindBy(how = How.ID, using = "slotcount")
	private WebElement slotcount;
	
	@FindBy(how = How.ID, using = "maxappt")
	private WebElement maxAppt;
	
	@FindBy(how = How.ID, using = "maxslotmonths")
	private WebElement maxSlotMonths;
	
	@FindBy(how = How.ID, using = "showproviderimage")
	private WebElement showProviderImage;
	
	@FindBy(how = How.ID, using = "allowpcp")
	private WebElement allowPCP;
	
	@FindBy(how = How.ID, using = "searchlocation")
	private WebElement searchLocation;
	
	@FindBy(how = How.ID, using = "nextAvailable")
	private WebElement nextavailable;
	
	@FindBy(how = How.ID, using = "pastapptmonths")
	private WebElement pastApptMonths;
	
	@FindBy(how = How.ID, using = "radior0")
	private WebElement radioOption0;
	
	@FindBy(how = How.ID, using = "radior1")
	private WebElement radioOption1;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"appt\"]/form/fieldset/div/div/button")
	private WebElement buttonSave;
	
	public AdminAppointment(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		return true;
	}
	//ng-reflect-value
	
	
}
