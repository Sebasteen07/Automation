package com.intuit.ihg.product.object.maps.phr.page.phrEmergencyContactPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.product.phr.utils.PhrConstants;
import com.intuit.ihg.product.phr.utils.PhrUtil;

public class PhrEmergencyContactPage extends BasePageObject {


	public PhrEmergencyContactPage(WebDriver driver) {
		super(driver);
		// Auto-generated constructor stub
	}

	@FindBy(css = "input[id='firstName']")
	private WebElement firstName;

	@FindBy(css = "input[id='lastName']")
	private WebElement lastName;

	@FindBy(css = "input[name='address1']")
	private WebElement address1;

	@FindBy(css = "input[name='address2']")
	private WebElement address2;

	@FindBy(css = "input[name='city']")
	private WebElement city;

	@FindBy(css = "input[name='zipCode']")
	private WebElement zipCode;

	@FindBy(css = "input[name='phone1']")
	private WebElement phone1;

	@FindBy(css = "input[name='phone2']")
	private WebElement phone2;

	@FindBy(css = "input[name='phone3']")
	private WebElement phone3;

	@FindBy(css = "a[href*='doSaveAndContinue']")
	private WebElement btnSaveChanges;

	@FindBy(xpath = "//div[@class='space']//table/tbody/tr[@id='feedback']/td/div")
	public WebElement emergencyContacttxt;


	public void setFirstName(String contactFirstName) {

		PhrUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, firstName);

		firstName.clear();

		firstName.sendKeys(contactFirstName);
	}


	public void setLastName(String contactLastName) {

		PhrUtil.PrintMethodName();

		lastName.clear();

		lastName.sendKeys(contactLastName);
	}



	public void setAddress1(String contactAddress1) {

		PhrUtil.PrintMethodName();

		address1.clear();

		address1.sendKeys(contactAddress1);
	}



	public void setAddress2(String contactAddress2) {

		PhrUtil.PrintMethodName();

		address2.clear();

		address2.sendKeys(contactAddress2);
	}



	public void setCity(String contactCity) {

		PhrUtil.PrintMethodName();

		city.clear();

		city.sendKeys(contactCity);
	}


	public void setState(String contactState) {

		PhrUtil.PrintMethodName();

		Select sel = new Select(driver.findElement(By.name("state")));

		sel.selectByVisibleText(contactState);
	}



	public void setZipCode(String postalCode) {

		PhrUtil.PrintMethodName();

		zipCode.clear();

		zipCode.sendKeys(postalCode);
	}



	public void setPhone1(String homePhone) {

		PhrUtil.PrintMethodName();

		phone1.clear();

		phone1.sendKeys(homePhone);
	}



	public void setPhone2(String workPhone) {

		PhrUtil.PrintMethodName();

		phone2.clear();

		phone2.sendKeys(workPhone);
	}



	public void setPhone3(String cellPhone) {

		PhrUtil.PrintMethodName();

		phone3.clear();

		phone3.sendKeys(cellPhone);
	}



	public void setPatientRelation(String relation) {

		PhrUtil.PrintMethodName();

		Select sel = new Select(driver.findElement(By.id("patientRelation")));

		sel.selectByVisibleText(relation);
	}

	public void clickSaveChanges() {
		PhrUtil.PrintMethodName();
		btnSaveChanges.click();

	}

	public void setEmergencyContactFields() {
		setFirstName(IHGUtil.pickRandomString(PhrConstants.ArrayofFirstName));
		setLastName(IHGUtil.pickRandomString(PhrConstants.ArrayofLastName));
		setPatientRelation(IHGUtil.pickRandomString(PhrConstants.ArrayofRelationShip));
		clickSaveChanges();

	}


}
