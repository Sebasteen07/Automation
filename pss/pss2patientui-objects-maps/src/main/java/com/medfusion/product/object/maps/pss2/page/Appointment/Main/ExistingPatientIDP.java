package com.medfusion.product.object.maps.pss2.page.Appointment.Main;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;

public class ExistingPatientIDP extends PSS2MainPage {

	@FindBy(how = How.ID, using = "loginUsername")
	private WebElement inputLoginUsername;

	@FindBy(how = How.ID, using = "loginPassword")
	private WebElement inputLoginPassword;

	@FindBy(how = How.XPATH, using = "/html/body/div[3]/div[1]/div[1]/form/a/span")
	private WebElement buttonSignIn;

	public ExistingPatientIDP(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(inputLoginUsername);
		webElementsList.add(inputLoginPassword);
		webElementsList.add(buttonSignIn);
		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}

	public HomePage patientSignIn(String uname, String pwd) {
		inputLoginUsername.sendKeys(uname);
		inputLoginPassword.sendKeys(pwd);
		buttonSignIn.click();
		return PageFactory.initElements(driver, HomePage.class);
	}

}
