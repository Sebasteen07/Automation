package com.medfusion.product.object.maps.pss2.page.Appointment.Menu;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;

public class PSSPatientHeader extends PSS2MainPage {

	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div[1]/div[1]/div[2]/a/img")
	private WebElement companyLogo;

	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div[1]/div[1]/div[1]/div/div/div/span[1]/img")
	private WebElement flagImage;

	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div[1]/div[1]/div[1]/div/div/div/span[1]/span")
	private WebElement languageText;

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

}
