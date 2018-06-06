package com.medfusion.product.object.maps.pss2.page.Appointment.Main;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;

public class PrivacyPolicy extends PSS2MainPage {

	@FindBy(how = How.ID, using = "existingCheckbox")
	private WebElement checkBoxAccept;
	
	@FindBy(how = How.ID, using = "existingPrivacyCancelButton")
	private WebElement buttonCancel;
	
	@FindBy(how = How.ID, using = "existingPrivacyPreviousButton")
	private WebElement buttonPrevious;
	
	@FindBy(how = How.XPATH, using = "//*[@class=\"col-sm-12 privacybtns\"]/div[3]/button")
	private WebElement buttonSubmit;
	
	@FindBy(how = How.CLASS_NAME, using = "dismissbuttons")
	private WebElement buttonClosePopUp;

	public PrivacyPolicy(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		IHGUtil.PrintMethodName();
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
//		webElementsList.add(checkBoxAccept);
//		webElementsList.add(buttonCancel);
//		webElementsList.add(buttonPrevious);
//		webElementsList.add(buttonSubmit);
		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}

	public void closePopup() {
		buttonClosePopUp.click();
	}

	public HomePage submitPrivacyPage() {
		checkBoxAccept.click();
		buttonSubmit.click();
		return PageFactory.initElements(driver, HomePage.class);
	}
}
