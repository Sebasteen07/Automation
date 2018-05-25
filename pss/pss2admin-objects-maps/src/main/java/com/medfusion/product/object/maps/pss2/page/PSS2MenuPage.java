package com.medfusion.product.object.maps.pss2.page;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;

public class PSS2MenuPage extends PSS2MainPage {

	@FindBy(how = How.XPATH, using = "/html/body/app/layout/nav[2]/ul[2]/li[3]/a")
	private WebElement settingsLogout;

	@FindBy(how = How.XPATH, using = "/html/body/app/layout/nav[2]/ul[2]/li[3]/ul/li/a/i")
	private WebElement logout;

	@FindBy(how = How.XPATH, using = "/html/body/app/layout/nav[1]/div/div[1]/ul/li[6]/a/span/i")
	private WebElement linkSettings;

	@FindBy(how = How.XPATH, using = "/html/body/app/layout/nav[1]/div/div[1]/ul/li[5]/a/span/i")
	private WebElement linkLockout;

	@FindBy(how = How.XPATH, using = "/html/body/app/layout/nav[1]/div/div[1]/ul/li[4]/a/span/i")
	private WebElement linkSpecialty;

	@FindBy(how = How.XPATH, using = "/html/body/app/layout/nav[1]/div/div[1]/ul/li[3]/a/span/i")
	private WebElement linkAppointmenttype;

	@FindBy(how = How.XPATH, using = "/html/body/app/layout/nav[1]/div/div[1]/ul/li[2]/a/span/i")
	private WebElement linkResource;

	@FindBy(how = How.XPATH, using = "/html/body/app/layout/nav[1]/div/div[1]/ul/li[1]/a/span/i")
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
		linkSettings.click();
	}

	public void gotoLocation() {
		linkLocation.click();
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
