package com.medfusion.product.object.maps.jalapeno.page.PayBillsStatementPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.medfusion.product.object.maps.jalapeno.page.JalapenoLoginPage;

//TODO THIS PAGE ISN'T FULLY REFACTORED
public class JalapenoPayBillsStatementPage extends BasePageObject {
	
	@FindBy(how = How.LINK_TEXT, using = "My Account")
	private WebElement myAccount;
	
	@FindBy(how = How.ID, using = "statementDiv")
	private WebElement statementBlock;
	
	@FindBy(how = How.XPATH, using = "//div[@id='balanceDue']/span[@class='amountDue ng-binding']")
	private WebElement balanceDue;
	
	@FindBy(how = How.ID, using = "open-top-loggedIn-btn")
	private WebElement rightDropdownButton;
	
	@FindBy(how = How.ID, using = "signout_dropdown")
	private WebElement signoutDropdownButton;
	
	@FindBy(how = How.ID, using = "makepayment")
	private WebElement makePaymentButton;
	
	/**
	 * @Author:Jakub Calabek
	 * @Date:24.7.2013
	 */
	
	public JalapenoPayBillsStatementPage(WebDriver driver) {
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
			rightDropdownButton.click();
			signoutDropdownButton.click();
		}
		
		return PageFactory.initElements(driver, JalapenoLoginPage.class);
	}
	
	public String getBalanceDue(WebDriver driver){
		try{
			WebElement balance = driver.findElement(By.xpath("//div[@id='balanceDue']/span/span"));
			log("Displayed? " + balance.isDisplayed() + " amount? " + balance.getText());
			return balance.getText();
		}
		catch (Exception ex) {
			log("Exception from element caught, rechecking");
			WebElement balance = driver.findElement(By.xpath("//div[@id='balanceDue']/span/span"));
			log("Displayed? " + balance.isDisplayed() + " amount? " + balance.getText());
			return balance.getText();
		}
	}
	
}
