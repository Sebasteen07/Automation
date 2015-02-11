package com.medfusion.product.object.maps.jalapeno.page.HomePage;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.medfusion.product.object.maps.jalapeno.page.JalapenoLoginPage;

public class JalapenoHomePage extends BasePageObject {
	
	@FindBy(how = How.ID, using = "signout")
	private WebElement signOutButton;
	
	@FindBy(how = How.ID, using = "account")
	private WebElement myAccount;
	
	@FindBy(how = How.ID, using = "home")
	private WebElement home;
	
	@FindBy(how = How.ID, using = "messages")
	private WebElement messages;
	
	@FindBy(how = How.ID, using = "appointments")
	private WebElement appointments;
	
	@FindBy(how = How.ID, using = "aska")
	private WebElement askAQuestion;
	
	@FindBy(how = How.ID, using = "prescriptions")
	private WebElement prescriptions;
	
	@FindBy(how = How.ID, using = "payments")
	private WebElement payments;
	
	@FindBy(how = How.ID, using = "forms")
	private WebElement forms;
	
	/**
	 * @Author:Jakub Calabek
	 * @Date:24.7.2013
	 */
	
	public JalapenoHomePage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		PageFactory.initElements(driver, this);
		
		}

	public JalapenoLoginPage logout(WebDriver driver) {
		
		IHGUtil.PrintMethodName();
		log("Clicking on Join In button");
		signOutButton.click();
		
		return PageFactory.initElements(driver, JalapenoLoginPage.class);
	}
	
	public boolean assessHomePageElements() {

		boolean allElementsDisplayed = false;

		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(signOutButton);
		webElementsList.add(myAccount);
		webElementsList.add(messages);
		webElementsList.add(appointments);
		webElementsList.add(home);
		webElementsList.add(askAQuestion);
		webElementsList.add(prescriptions);
		webElementsList.add(payments);
		webElementsList.add(forms);

		for (WebElement w : webElementsList) {

			try {
				IHGUtil.waitForElement(driver, 20, w);
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
