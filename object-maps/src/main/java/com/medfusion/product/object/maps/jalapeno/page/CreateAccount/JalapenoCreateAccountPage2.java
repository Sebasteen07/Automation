package com.medfusion.product.object.maps.jalapeno.page.CreateAccount;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.medfusion.product.object.maps.jalapeno.page.HomePage.JalapenoHomePage;

public class JalapenoCreateAccountPage2 extends BasePageObject {

	/**
	 * @Author:Jakub Calabek
	 * @Date:24.7.2013
	 */
	
	@FindBy(how = How.ID, using = "userid")
	private WebElement inputUserId;
	
	@FindBy(how = How.ID, using = "password")
	private WebElement inputPassword;
	
	@FindBy(how = How.ID, using = "secretQuestion")
	private WebElement inputSecretQuestion;
	
	@FindBy(how = How.ID, using = "secretAnswer")
	private WebElement inputSecretAnswer;
	
	@FindBy(how = How.ID, using = "phone1")
	private WebElement inputPhone1;
	
	@FindBy(how = How.ID, using = "phone2")
	private WebElement inputPhone2;
	
	@FindBy(how = How.ID, using = "phone3")
	private WebElement inputPhone3;
	
	@FindBy(how = How.ID, using = "phone_type")
	private WebElement inputPhoneType;
	
	@FindBy(how = How.ID, using = "prevStep")
	private WebElement prevStep;
	
	@FindBy(how = How.ID, using = "finishStep")
	private WebElement finishStep;
	
	public JalapenoCreateAccountPage2(WebDriver driver) {
		super(driver);
	}
	
	
	public JalapenoHomePage fillInDataPage2(String userId, String password, String secretQuestion, String secretAnswer, String phoneNumber) {
		IHGUtil.PrintMethodName();
		
		log("Setting User Name as " + userId);
		inputUserId.sendKeys(userId);
		log("Setting Password as " + password);
		inputPassword.sendKeys(password);
				
		log("Secret Question as " + secretQuestion);
		inputSecretQuestion.sendKeys(secretQuestion);
		
		log("Secret Answer as " + secretAnswer);
		inputSecretAnswer.sendKeys(secretAnswer);
		
		log("Phone number as " + phoneNumber);
		
		//splitting phone to the textboxes
		String phone1 = phoneNumber.substring(0, 3);
        String phone2 = phoneNumber.substring(3,6);
        String phone3 = phoneNumber.substring(6,10);
		
		inputPhone1.sendKeys(phone1);
		inputPhone2.sendKeys(phone2);
		inputPhone3.sendKeys(phone3);
		
		finishStep.click();
		
		return PageFactory.initElements(driver, JalapenoHomePage.class);
	}
	
	public boolean assessCreateAccountPage2Elements() {

		boolean allElementsDisplayed = false;

		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(inputUserId);
		webElementsList.add(inputPassword);
		webElementsList.add(inputSecretQuestion);
		webElementsList.add(inputSecretAnswer);
		webElementsList.add(inputPhone1);
		webElementsList.add(inputPhone2);
		webElementsList.add(inputPhone3);
		webElementsList.add(inputPhoneType);
		webElementsList.add(prevStep);
		webElementsList.add(finishStep);

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