package com.medfusion.product.object.maps.pss2.page.Appointment.Menu;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;

public class PSSPatientFooter extends PSS2MainPage {

	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div[2]/footer/div[1]/object")
	private WebElement imagePartner;

	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div[2]/footer/div[2]/p")
	private WebElement copyRightText;

	public PSSPatientFooter(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		log("Verifying Footer Elements");
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(imagePartner);
		webElementsList.add(copyRightText);
		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}

}
