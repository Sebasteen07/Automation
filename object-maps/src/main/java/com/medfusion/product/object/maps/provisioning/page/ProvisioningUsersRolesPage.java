package com.medfusion.product.object.maps.provisioning.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class ProvisioningUsersRolesPage extends BasePageObject {

	public ProvisioningUsersRolesPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(how = How.XPATH, using="//div[@id='usersDetail']/fieldset/div/div/mf-user-modal-dialog/button")
	public WebElement buttonNewUser;
	@FindBy(how = How.XPATH, using="//input[@id='practicestaffId']")
	public WebElement addUserId;
	@FindBy(how = How.XPATH, using="//input[@id='userName']")
	public WebElement addUserName;
	@FindBy(how = How.XPATH, using="//input[@id='role.abbreviation']")
	public WebElement addUserRole;
	@FindBy(how = How.XPATH, using="//button[text()='Add User']")
	public WebElement buttonSubmitUser;
	@FindBy(how = How.XPATH, using="//div[@id='usersDetail']/table/tbody/tr/td[1]")
	public WebElement valueUserId;
	@FindBy(how = How.XPATH, using="//div[@id='usersDetail']/fieldset/div/div/button")
	public WebElement buttonCancle;
	@FindBy(how = How.XPATH, using="//div[@id='usersDetail']/table/tbody/tr/td[3]/mf-user-modal-dialog[2]/button")
	public WebElement buttonRemove;
	@FindBy(how = How.XPATH, using="//button[text()='Remove']")
	public WebElement buttonRemove2;
	@FindBy(how = How.XPATH, using="//*[text()='No roles have been added.']")
	public WebElement valueRoles;
	
	public void waitTillLoadedUsers() {		
		log("Loading, waiting for Merchant name displayed");
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//div[@id='usersDetail']/span/h1"))));		
	}

	public void clickCancle(){
		IHGUtil.PrintMethodName();
		buttonCancle.click();
	}
	
	public void deleteOldUsers(){
		IHGUtil.PrintMethodName();
		while(!IHGUtil.exists(driver, 1, valueRoles)){
			log("User was found. Deleting user with ID: " + valueUserId.getText());
			buttonRemove.click();
			buttonRemove2.click();
			waitTillLoadedUsers();
			log("Sucess!");
		}
	}
	
	public boolean addNewUserAndVerify (String userId, String userName){
		IHGUtil.PrintMethodName();
		buttonNewUser.click();
		log("User: " + userName);
		addUserId.sendKeys(userId);
		addUserName.sendKeys(userName);
		addUserRole.click();
		buttonSubmitUser.click();
		waitTillLoadedUsers();
		return valueUserId.getText().equals(userId);
	}
	
	
	public boolean deleteUserAndVerify(){
		buttonRemove.click();
		buttonRemove2.click();
		waitTillLoadedUsers();
		return valueRoles.getText().equals("No roles have been added.");
	}

}
