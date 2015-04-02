package com.medfusion.product.object.maps.jalapeno.page.HomePage;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.medfusion.product.object.maps.jalapeno.page.JalapenoLoginPage;

public class JalapenoHomePage extends BasePageObject {
	
	@FindBy(how = How.ID, using = "account")
	private WebElement myAccount;
	
	@FindBy(how = How.ID, using = "home")
	private WebElement home;
	
	@FindBy(how = How.ID, using = "feature_messaging")
	private WebElement messages;
	
	@FindBy(how = How.ID, using = "feature_appointment_request")
	private WebElement appointments;
	
	@FindBy(how = How.ID, using = "feature_ask_a_practitioner")
	private WebElement askAQuestion;
	
	@FindBy(how = How.ID, using = "feature_rx_renewal")
	private WebElement prescriptions;
	
	@FindBy(how = How.ID, using = "feature_bill_pay")
	private WebElement payments;
	
	@FindBy(how = How.ID, using = "feature_discrete_forms")
	private WebElement forms;
	
	/**
	 * @Author:Jakub Calabek
	 * @Date:24.7.2013
	 */
	
	public JalapenoHomePage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);	
	}

	public JalapenoLoginPage logout(WebDriver driver) {
		
		IHGUtil.PrintMethodName();
		log("Trying to click on Logout button - regular resolution");
		
		try {
			WebElement signoutButton = driver.findElement(By.id("signout"));
			signoutButton.click();
		}
		catch(Exception ex) {
			log("Did not find Logout button, trying mobile version size");
			WebElement open = driver.findElement(By.id("open-top-loggedIn-btn"));
			open.click();
			driver.findElement(By.id("signout_dropdown")).click();
		}
		
		return PageFactory.initElements(driver, JalapenoLoginPage.class);
	}
	
	public boolean assessHomePageElements() {

		boolean allElementsDisplayed = false;

		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
	
		webElementsList.add(messages);
		webElementsList.add(appointments);
		webElementsList.add(home);
		webElementsList.add(askAQuestion);
		webElementsList.add(prescriptions);
		webElementsList.add(payments);
		webElementsList.add(forms);

		for (WebElement w : webElementsList) {

			try {
				IHGUtil.waitForElement(driver, 60, w);
				log("Checking WebElement" + w.toString());
				if (w.isDisplayed()) {
					log("WebElement " + w.toString() + "is displayed");
					allElementsDisplayed = true;
				} else {
					log("WebElement " + w.toString() + "is NOT displayed");
					return false;
				}
			}

			catch (Throwable e) {
				log(e.getStackTrace().toString());
			}

		}
		return allElementsDisplayed;
	}
	
}
