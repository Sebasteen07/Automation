package com.medfusion.product.object.maps.pss2.page.Appointment.Loginless;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;

public class DismissPage extends PSS2MainPage {
	@FindBy(how = How.XPATH, using = "//button[@class='dismissbuttons']//span[contains(text(),'Dismiss')]")
	private WebElement dismissBtn;

	public DismissPage(WebDriver driver, String url) {
		super(driver, url);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		return true;
	}

	public LoginlessPatientInformation clickDismiss() {
		dismissBtn.click();
		return PageFactory.initElements(driver, LoginlessPatientInformation.class);
	}
}
