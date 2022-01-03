//Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package pageobjects;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.PropertyFileLoader;

public class AddUsersAndRolesPage extends MerchantDetailsPage{
    protected static PropertyFileLoader testData;
    
    public AddUsersAndRolesPage(WebDriver driver) throws IOException {
        super(driver);
        driver.manage().window().maximize();
        PageFactory.initElements(driver, this);
        testData = new PropertyFileLoader();
    }
	
    @FindBy(how = How.XPATH, using ="//*[text()=' Add User/Role ']")
	private WebElement addUsersOrRoleButton;

    @FindBy(how = How.XPATH, using ="//input[@id='practicestaffId']")
	private WebElement userID;
    
    @FindBy(how = How.XPATH, using ="//input[@id='userName']")
	private WebElement userName;
    
    @FindBy(how = How.XPATH, using ="(//*[text()=' Add User/Role '])[2]")
	private WebElement addUsersAndRoleButton;
    
    @FindBy(how = How.XPATH, using ="//*[text()=' Return to Merchant Details ']")
	private WebElement returnToMerchantDetailsButton;
    
    @FindBy(how = How.XPATH, using ="//*[text()=' Update ']")
	private WebElement updateButton;
    
    @FindBy(how = How.XPATH, using ="//*[text()=' Remove ']")
	private WebElement removeButton;
    
    @FindBy(how = How.XPATH, using ="(//*[text()=' Remove '])[2]")
   	private WebElement finalRemoveButton;
    
    public void editPracticeStaffId(String practicestaffId) {
    	userID.clear();
    	userID.sendKeys(practicestaffId);
    }
    
    public void editUserName(String practicestaffName) {
    	userName.clear();
    	userName.sendKeys(practicestaffName);
    }
    
    public Boolean verifyButtonsPresent(){
        if(addUsersOrRoleButton.isDisplayed() && returnToMerchantDetailsButton.isDisplayed()){
            return true;
        }else{return false;}
    }
    
    public AddUsersAndRolesPage clickAddUsersOrRolesButton() {
    	addUsersOrRoleButton.click();
		return PageFactory.initElements(driver, AddUsersAndRolesPage.class);
	}
    
    public Boolean verifyUserInfoFields(){
        if(userID.isDisplayed() && userName.isDisplayed()){
            return true;
        }else{return false;}
    }
    
    public void checkRolesCheckboxes() {
    	List<WebElement>checkboxes=driver.findElements(By.xpath("//input[@type='checkbox']"));
    	for(WebElement chbox: checkboxes){	
    		boolean isDisplayed = chbox.isDisplayed();
        	boolean isSelected = chbox.isSelected();
        	boolean isEnabled = chbox.isDisplayed();
    		if(isDisplayed == true && isSelected == false && isEnabled == true) {
    		chbox.click();
    		}
    	}
    }
    
    public AddUsersAndRolesPage clickAddUsersAndRoleButton() {
    	addUsersAndRoleButton.click();
		return PageFactory.initElements(driver, AddUsersAndRolesPage.class);
	}
    
    public Boolean verifyUpdateAndRemoveButtonsPresent(){
        if(updateButton.isDisplayed() && removeButton.isDisplayed()){
            return true;
        }else{return false;}
    }
    
    public AddUsersAndRolesPage clickRemoveUserRoles() {
    	removeButton.click();
		return PageFactory.initElements(driver, AddUsersAndRolesPage.class);
	}
    
    public AddUsersAndRolesPage confirmClickOnRemoveUserRoles() {
    	finalRemoveButton.click();
		return PageFactory.initElements(driver, AddUsersAndRolesPage.class);
	}
}

