package com.medfusion.product.object.maps.precheck.page.patient;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class PatientLoginPage extends BasePageObject {

	@FindBy(how = How.ID, using = "zipCode")
	private WebElement zipInput;

	@FindBy(how = How.ID, using = "dob")
	private WebElement dobInput;

	@FindBy(how = How.ID, using = "loginButton")
	private WebElement buttonLogin;

	public PatientLoginPage(WebDriver driver, String url) {

		super(driver);
		IHGUtil.PrintMethodName();
		log("Loading Patient login page");
		driver.get(url);
		driver.manage().window().maximize();
		IHGUtil.printCookies(driver);
		PageFactory.initElements(driver, this);
	}

	public PatientLoginPage(WebDriver driver) {

		super(driver);
		PageFactory.initElements(driver, this);
	}

	public boolean assessLoginPageElements() {

		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(zipInput);
		webElementsList.add(dobInput);
		webElementsList.add(buttonLogin);

		for (WebElement w : webElementsList) {
			try {
				IHGUtil.waitForElement(driver, 30, w);
				if (!(w.isDisplayed())) {
					log("WebElement " + w.toString() + "is NOT displayed");
					return false;
				}
			} catch (Throwable e) {
				log(e.getStackTrace().toString());
				return false;
			}
		}
		return true;
	}

	/**
	 * @param zip
	 * @param dob - only in format ddmmyy or ddmmyyyy
	 * @return Home page of patient side
	 */
	public PatientHomePage login(String zip, String dob) {

		IHGUtil.PrintMethodName();
		log("Login Credentials: ZIP:[" + zip + "] DOB:[" + dob + "]");
		zipInput.sendKeys(zip);
		dobInput.sendKeys(dob);
		buttonLogin.click();
		return PageFactory.initElements(driver, PatientHomePage.class);
	}

}
