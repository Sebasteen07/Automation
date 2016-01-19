package com.medfusion.product.object.maps.jalapeno.page.FamillyAccountPage;

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
import com.intuit.ihg.common.utils.IHGUtil;


public class JalapenoCreateGuardianPage extends BasePageObject{
	
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
	
	
	public JalapenoCreateGuardianPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
	}
	
	public boolean checkDependantInfoRegisterPage (String name, String lastname, String email) {
		IHGUtil.PrintMethodName();
			
		WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'" + name + "')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'" + lastname + "')]")));
		
		if (this.inputEmail.getAttribute("value").equals(email)) {
			log("Name, lastname and email is correct");
			return true;
		}else{
			log(this.inputEmail.getAttribute("value") + " is not equal " + email);
			return false;
		}
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
	
	public JalapenoCreateGuardianPage2 continueToSecondPage(WebDriver driver) {
		IHGUtil.PrintMethodName();
		this.buttonContinue.click();
		return PageFactory.initElements(driver, JalapenoCreateGuardianPage2.class);
	}
}
	