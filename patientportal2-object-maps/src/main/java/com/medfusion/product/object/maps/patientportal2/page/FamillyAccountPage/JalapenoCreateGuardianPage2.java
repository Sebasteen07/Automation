package com.medfusion.product.object.maps.patientportal2.page.FamillyAccountPage;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;

public class JalapenoCreateGuardianPage2 extends BasePageObject{
	
	@FindBy(how = How.XPATH, using = "(//input[@id='userid'])[2]")
	private WebElement inputUserId;
	
	@FindBy(how = How.XPATH, using = "(//input[@id='password'])[2]")
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
	
	@FindBy(how = How.ID, using = "finishStep")
	private WebElement buttonEnterPortal;
	
	@FindBy(how = How.ID, using = "paymentPreference_Electronic")
	private WebElement electronicPaymentPreference;
	
	@FindBy(how = How.ID, using = "updateStatementPrefButton")
	private WebElement okButton;
	
	
	public JalapenoCreateGuardianPage2(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
	}
	
	public boolean assessElements() {
		log("Wait for elements");
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(this.inputUserId);
		webElementsList.add(this.inputPassword);
		webElementsList.add(this.inputSecretQuestion);
		webElementsList.add(this.inputSecretAnswer);
		webElementsList.add(this.inputPhone1);
		webElementsList.add(this.inputPhone2);
		webElementsList.add(this.inputPhone3);
		webElementsList.add(this.buttonEnterPortal);
		
		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}	
		
	public void fillGuardianSecurityDetails (String userId, String password, String secretQuestion, String secretAnswer, String phoneNumber) {
		IHGUtil.PrintMethodName();		
		log("Fill inputs:");
		log("Setting User Name as " + userId);
		inputUserId.sendKeys(userId);
		log("Setting Password as " + password);
		inputPassword.sendKeys(password);
				
		log("Secret Question as " + secretQuestion);
		inputSecretQuestion.sendKeys(secretQuestion);
		
		log("Secret Answer as " + secretAnswer);
		inputSecretAnswer.sendKeys(secretAnswer);
		
		log("Phone number as " + phoneNumber);
		inputPhone1.sendKeys(phoneNumber.substring(0,3));
		inputPhone2.sendKeys(phoneNumber.substring(3,6));
		inputPhone3.sendKeys(phoneNumber.substring(6,10));
	}	
	
	public JalapenoHomePage clickEnterPortal(WebDriver driver) {
		IHGUtil.PrintMethodName();
		this.buttonEnterPortal.click();
		
		selectStatementIfRequired();
		return PageFactory.initElements(driver, JalapenoHomePage.class);
	}
	
	private void selectStatementIfRequired() {
		if ( new IHGUtil(driver).exists(electronicPaymentPreference) ) {
			log("Statement delivery preference lightbox is displayed");
			electronicPaymentPreference.click();
			okButton.click();
		}
	}
}