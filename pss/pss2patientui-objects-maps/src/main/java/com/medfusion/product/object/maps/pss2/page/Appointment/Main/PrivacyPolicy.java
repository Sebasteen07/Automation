package com.medfusion.product.object.maps.pss2.page.Appointment.Main;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.medfusion.common.utils.IHGUtil;

public class PrivacyPolicy extends PSS2MainPage {

	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div/div[3]/form/div[3]/input")
	public WebElement checkBoxAccept;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div/div[3]/form/div[4]/div[1]/a/span")
	public WebElement buttonCancel;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div/div[3]/form/div[4]/div[2]/a/span")
	public WebElement buttonPrevious;
	
	@FindBy(how = How.CLASS_NAME, using = "custombuttonsubmitprivacy buttontop privacysubmitdata")
	public WebElement buttonSubmit;
	
	@FindBy(how = How.CLASS_NAME, using = "dismissbuttonsprivacy")
	public WebElement buttonClosePopUp;

	public PrivacyPolicy(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		IHGUtil.PrintMethodName();
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(checkBoxAccept);
		webElementsList.add(buttonCancel);
		webElementsList.add(buttonPrevious);
		webElementsList.add(buttonSubmit);
		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}
}
