package com.medfusion.product.object.maps.patientportal2.page.FamillyAccountPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;


public class JalapenoCreateGuardianPage extends BasePageObject{
	
	@FindBy(how = How.XPATH, using = "(//input[@id='password'])[1]")
	private WebElement inputPassword;
	
	@FindBy(how = How.XPATH, using = "(//input[@id='userid'])[1]")
	private WebElement inputUserId;
	
	@FindBy(how = How.XPATH, using = "(//select[@id='relationshipToPatient'])[1]")
	private WebElement inputRelationshipFirst;
	
	@FindBy(how = How.XPATH, using = "(//input[@id='email'])[2]")
	private WebElement inputEmail;
	
	@FindBy(how = How.XPATH, using = "(//input[@id='firstName'])[2]")
	private WebElement inputName;
	
	@FindBy(how = How.XPATH, using = "(//input[@id='lastName'])[2]")
	private WebElement inputLastname;
	
	@FindBy(how = How.XPATH, using = "(//select[@id='relationshipToPatient'])[2]")
	private WebElement inputRelationshipSecond;
	
	@FindBy(how = How.ID, using = "next")
	private WebElement buttonContinue;
	
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Enter Portal')]")
	private WebElement buttonEnterPortal;
	
	@FindBy(how = How.ID, using = "paymentPreference_Electronic")
	private WebElement electronicPaymentPreference;
	
	@FindBy(how = How.ID, using = "updateStatementPrefButton")
	private WebElement okButton;
	
	
	public JalapenoCreateGuardianPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
	}
	
	public void checkDependentInfoRegisterPage (String name, String lastname, String email) {
        IHGUtil.PrintMethodName();

        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'" + name + "')]")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'" + lastname + "')]")));
        wait.until(ExpectedConditions.textToBePresentInElementValue(inputEmail, email));
	}
	
	public void createGuardianOnlyFirstPage (String name, String lastname, String relationship) {
		IHGUtil.PrintMethodName();
		
		this.inputName.sendKeys(name);
		this.inputLastname.sendKeys(lastname);
		log("Guardian name: " + name + " " + lastname);
		
		Select relationshipPatient = new Select(this.inputRelationshipSecond);
		relationshipPatient.selectByVisibleText(relationship);
		log("Guardian relationship:" + relationship);
	}
	
	public void createGuardianLinkToExistingPatient (String login, String password, String relationship){
		IHGUtil.PrintMethodName();
		
		this.inputUserId.sendKeys(login);
		this.inputPassword.sendKeys(password);
		log("Guardian login / password: " + login + " / " + password);
		
		Select relationshipPatient = new Select(this.inputRelationshipFirst);
		relationshipPatient.selectByVisibleText(relationship);
		log("Guardian relationship:" + relationship);
	}
	
	
	public JalapenoCreateGuardianPage2 continueToSecondPage(WebDriver driver) {
		IHGUtil.PrintMethodName();
		this.buttonContinue.click();
		return PageFactory.initElements(driver, JalapenoCreateGuardianPage2.class);
	}
	
	public JalapenoHomePage continueToPortal(WebDriver driver) {
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
	