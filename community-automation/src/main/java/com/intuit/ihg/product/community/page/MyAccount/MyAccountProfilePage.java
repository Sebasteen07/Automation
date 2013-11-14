package com.intuit.ihg.product.community.page.MyAccount;

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
import com.intuit.ihg.common.entities.Patient;
import com.intuit.ihg.common.entities.TestObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.community.utils.GmailMessage;
import com.intuit.ihg.product.community.utils.PracticeUrl;

public class MyAccountProfilePage extends BasePageObject {

	// Page Objects
	@FindBy(how = How.ID, using = "streetaddress")
	public WebElement streetAddress;

	@FindBy(how = How.ID, using = "city")
	public WebElement city;

	@FindBy(how = How.ID, using = "state")
	public WebElement state;

	@FindBy(how = How.ID, using = "zip")
	public WebElement zip;

	@FindBy(how = How.ID, using = "phonenumber")
	public WebElement phoneNumber;

	@FindBy(how = How.ID, using = "phonetype")
	public WebElement phoneType;

	@FindBy(how = How.ID, using = "sex")
	public WebElement sex;

	@FindBy(how = How.ID, using = "maritalstatus")
	public WebElement maritalStatus;

	@FindBy(how = How.ID, using = "preferredcommunication")
	public WebElement preferredCommunication;

	@FindBy(how = How.ID, using = "preferredlanguage")
	public WebElement preferredLanguage;

	@FindBy(how = How.ID, using = "race")
	public WebElement race;

	@FindBy(how = How.ID, using = "ethnicity")
	public WebElement ethnicity;

	@FindBy(how = How.XPATH, using = "//div[@id='sidebar_profile']//button[@type='submit']")
	public WebElement btnSaveChanges;
	
	@FindBy(how = How.XPATH, using = "//h2[contains(text(),'Success')]")
	public WebElement successNotification;

	public MyAccountProfilePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);

	}

	public boolean areElementsDisplayed() throws InterruptedException {

		WebElement[] elements = { streetAddress, city, state, zip, phoneNumber, phoneType, sex, maritalStatus, preferredCommunication,
				preferredLanguage, race, ethnicity };

		for (WebElement element : elements) {
			if (!element.isDisplayed()) {
				log("Element :"+element.toString()+" is not displayed");
				return false;
			}
		}
		return true;
	}
	
	public void setProfile(WebDriver driver,TestObject test, PracticeUrl practiceUrl,
					GmailMessage gmailMessage, Patient patient) throws Exception {
		
		// Setting up Profile Data
		// Every filling is postponed by the Thread.sleep due the issue with filling the inputs without any delay
				Thread.sleep(1000);
				streetAddress.sendKeys(patient.getStreetAddress());
				Thread.sleep(1000);
				city.sendKeys(patient.getCity());
				Thread.sleep(1000);
				state.sendKeys(patient.getState());
				Thread.sleep(1000);
				zip.clear();
				Thread.sleep(1000);
				zip.sendKeys(patient.getZipCode());
				Thread.sleep(1000);
				phoneNumber.clear();
				Thread.sleep(1000);
				phoneNumber.sendKeys(patient.getPhoneNumber());
				Thread.sleep(1000);
				phoneType.sendKeys(patient.getPhoneType());
				Thread.sleep(1000);
				sex.sendKeys(patient.getSex());
				Thread.sleep(1000);
				maritalStatus.sendKeys(patient.getMaritalStatus());
				Thread.sleep(1000);
				log(patient.getCommunicationMethod());
				preferredCommunication.sendKeys(patient.getCommunicationMethod());
				Thread.sleep(1000);
				preferredLanguage.sendKeys(patient.getPrefferedLanguage());
				Thread.sleep(1000);
				race.sendKeys(patient.getRace());
				Thread.sleep(1000);
				ethnicity.sendKeys(patient.getEthnicity());
				Thread.sleep(1000);

				log("Clicking on Save button");
				btnSaveChanges.click();
	}
	
	public boolean sucessNotification(WebDriver driver) throws Exception {

		WebElement notification = (new WebDriverWait(driver, 60)).until(ExpectedConditions.presenceOfElementLocated(By
						.xpath("//h2[contains(text(),'Success')]")));
		
		return successNotification.isDisplayed();
	}
	
	 
public void updateProfile(String streetaddress)throws Exception {
		
		IHGUtil.PrintMethodName();
		streetAddress.click();
		streetAddress.clear();
		log ("Clearing  street address and the value is now :" +streetAddress.getAttribute("value"));
		streetAddress.sendKeys(streetaddress);
		Select maritalStatusDrp = new Select(maritalStatus);
		maritalStatusDrp.selectByValue("MARRIED");
		Select preferredCmmn = new Select(preferredCommunication);
		preferredCmmn.selectByValue("MOBILE_PHONE");
		log ("Entering Street adrress and saving the value :" +streetAddress.getAttribute("value"));
		log("Clicking on Save button");
		btnSaveChanges.click();
		
}
	public boolean checkProfileSave(String randomstreet)throws Exception{
		IHGUtil.PrintMethodName();
		
		IHGUtil.waitForElement(driver, 30,streetAddress );
		log ("street address value now :" +streetAddress.getAttribute("value"));
		if (streetAddress.getAttribute("value").contains(randomstreet)){
			return true;
		}
			else
				return false;
			
		}

	
	
	
	}
	

