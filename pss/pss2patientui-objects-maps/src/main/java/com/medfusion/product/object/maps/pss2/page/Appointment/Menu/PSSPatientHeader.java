package com.medfusion.product.object.maps.pss2.page.Appointment.Menu;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;

public class PSSPatientHeader extends PSS2MainPage {

	@FindBy(how = How.ID, using = "headerlogo")
	private WebElement companyLogo;

	@FindBy(how = How.XPATH, using = "//*[@class=\"country-flag\"]/img")
	private WebElement flagImage;

	@FindBy(how = How.CLASS_NAME, using = "country-label")
	private WebElement languageText;

	@FindBy(how = How.ID, using = "logoutbutton")
	private WebElement nameSettings;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Logout')]")
	private WebElement logout;

	public PSSPatientHeader(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		log("Verifying PSS Patient Header Elements");
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(companyLogo);
		webElementsList.add(flagImage);
		webElementsList.add(languageText);
		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}

	public void logout() {
		nameSettings.click();
		IHGUtil.waitForElement(driver, 60, logout);
		logout.click();
	}

}
