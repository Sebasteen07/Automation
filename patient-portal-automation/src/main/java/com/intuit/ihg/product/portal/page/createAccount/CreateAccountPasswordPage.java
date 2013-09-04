package com.intuit.ihg.product.portal.page.createAccount;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.portal.page.MyPatientPage;
import com.intuit.ihg.product.portal.utils.PortalUtil;



public class CreateAccountPasswordPage extends BasePageObject{
	
	public static final String PAGE_NAME = "Create Account Password Page";
	
////////////////////////
// OLD SIGNUP
// TODO - remove old fields once obsolete
@FindBy( how = How.NAME, using="inputs:0:input:input" )
private WebElement txtuserid;
	
@FindBy( how = How.NAME, using="inputs:3:input:input")
private WebElement txtconfirmPassword;

@FindBy( how = How.NAME, using="inputs:2:input:input")
private WebElement txtpassword;

@FindBy( how = How.NAME, using="inputs:4:input:input")
private WebElement dropDownsecretQuestion;

@FindBy( how = How.NAME, using="inputs:5:input:input")
private WebElement txtsecurityAnswer;

@FindBy( how = How.NAME, using="inputs:6:input:input")
private WebElement dropDownpreferredLocation;

@FindBy( how = How.CSS, using="span[class*='first_row']" )
private WebElement providerFirstRow;

@FindBy( how = How.NAME, using="buttons:submit")
private WebElement submit;

//////////////////
// NEW SIGNUP

@FindBy( how = How.CSS, using="input[fieldid='security.user']" )
private WebElement txtuserid1;

/*@FindBy( how = How.NAME, using="usernamecheck")
private WebElement txtusernamecheck;*/

@FindBy( how = How.CSS, using="input[fieldid='security.password']" )
private WebElement txtpassword1;

@FindBy( how = How.CSS, using="input[fieldid='security.verifyPassword']" )
private WebElement txtconfirmPassword1;

@FindBy( how = How.CSS, using="select[fieldid='security.secretQuestion']" )
private WebElement dropDownsecretQuestion1;

@FindBy( how = How.CSS, using="input[fieldid='security.secretAnswer']" )
private WebElement txtsecurityAnswer1;

@FindBy( how = How.CSS, using="select[fieldid*='Location']" )
private WebElement dropDownpreferredLocation1;

@FindBy( how = How.XPATH, using="//input[@id='autocomplete']" )
private WebElement txtpreferredDoctor;



/////////////////////////
// Constructor

public CreateAccountPasswordPage(WebDriver driver) {
super(driver);
// TODO Auto-generated constructor stub
}



/**
* 
* @param sPassword
* @param sConfirmPassword
* @param sSecurityQuestion
* @param sSecurityAnswer
* @param bETA_PROVIDER 
* @param bETA_LOCATION 
* @param bAgreeTOS
* @return
* @throws Exception 
*/
 public MyPatientPage createPasswordSecurity( 
String sUserId,
String sPassword, 
String sSecurityQuestion,
String sSecurityAnswer,
String sLocation, 
String sProvider ) throws Exception {

IHGUtil.PrintMethodName();
driver.switchTo().defaultContent();
PortalUtil.setPortalFrame(driver);
// TODO - returns home page ???

System.out.println("DEBUG: START: createPasswordSecurity()");
txtuserid.sendKeys(sUserId);	
txtpassword.sendKeys(sPassword);
txtconfirmPassword.sendKeys(sPassword);
Select securityQuestion=new Select(dropDownsecretQuestion);
securityQuestion.selectByVisibleText(sSecurityQuestion);
txtsecurityAnswer.sendKeys(sSecurityAnswer);
Select preferredLocation=new Select(dropDownpreferredLocation);
preferredLocation.selectByVisibleText(sLocation);
txtpreferredDoctor.sendKeys(sProvider);
//txtpreferredDoctor.sendKeys(Keys.ARROW_DOWN);
Thread.sleep(200);
//txtpreferredDoctor.sendKeys(Keys.TAB);
providerFirstRow.click();
Thread.sleep(200);
submit.click();
return  PageFactory.initElements(driver, MyPatientPage.class);
}



}
