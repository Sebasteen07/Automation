package com.medfusion.product.object.maps.pss2.page;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.ManageAppointmentType;
import com.medfusion.product.object.maps.pss2.page.Location.ManageLocation;
import com.medfusion.product.object.maps.pss2.page.Lockout.ManageLockoutRules;
import com.medfusion.product.object.maps.pss2.page.Resource.ManageResource;
import com.medfusion.product.object.maps.pss2.page.Specialty.ManageSpecialty;

public class PSS2MenuPage extends PSS2MainPage {

	// @FindBy(how = How.XPATH, using = "/html/body/app/layout/nav[2]/ul[2]/li[3]/a")
	// private WebElement settingsLogout;

	@FindBy(how = How.XPATH, using = "//a[@class='nav-link dropdown-toggle']/i")
	private WebElement settingsLogout;

	// @FindBy(how = How.XPATH, using = "/html/body/app/layout/nav[2]/ul[2]/li[3]/ul/li/a/i")
	// private WebElement logout;


	@FindBy(how = How.XPATH, using = "//li[@class='nav-item dropdown open show']/ul")
	private WebElement logout;


	@FindBy(how = How.XPATH, using = "//a[@href=\"#/app/settings\"]")
	private WebElement linkSettings;

	@FindBy(how = How.XPATH, using = "//a[@href=\"#/app/lockout\"]")
	private WebElement linkLockout;

	@FindBy(how = How.XPATH, using = "//a[@href=\"#/app/specialty\"]")
	private WebElement linkSpecialty;

	@FindBy(how = How.XPATH, using = "//a[@href=\"#/app/appointmenttype\"]")
	private WebElement linkAppointmenttype;

	@FindBy(how = How.XPATH, using = "//a[@href=\"#/app/resource\"]")
	private WebElement linkResource;

	@FindBy(how = How.XPATH, using = "//a[@href=\"#/app/location\"]")
	private WebElement linkLocation;

	public PSS2MenuPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(linkLockout);
		webElementsList.add(linkSpecialty);
		webElementsList.add(linkAppointmenttype);
		webElementsList.add(linkResource);
		webElementsList.add(linkLocation);
		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}

	public void gotoSettings() {
		javascriptClick(linkSettings);
	}

	public ManageLocation gotoLocation() {
		javascriptClick(linkLocation);
		return PageFactory.initElements(driver, ManageLocation.class);
	}

	public ManageLockoutRules gotoLockOut() {
		javascriptClick(linkLockout);
		return PageFactory.initElements(driver, ManageLockoutRules.class);
	}

	public ManageSpecialty gotoSpeciality() {
		javascriptClick(linkSpecialty);
		return PageFactory.initElements(driver, ManageSpecialty.class);
	}

	public ManageAppointmentType gotoAppointment() {
		javascriptClick(linkAppointmenttype);
		return PageFactory.initElements(driver, ManageAppointmentType.class);
	}

	public ManageResource gotoResource() {
		javascriptClick(linkResource);
		return PageFactory.initElements(driver, ManageResource.class);
	}

	public void logout() {
		log("logging out from admin...need to add logic to check which page it redirects too");
		try {
			settingsLogout.click();
			logout.click();	
		}catch(Exception E) {
			log("Exception occured while logging out. "+E);
		}
	}

}
