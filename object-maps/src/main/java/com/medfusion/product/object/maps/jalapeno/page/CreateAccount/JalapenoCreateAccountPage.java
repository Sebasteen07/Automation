package com.medfusion.product.object.maps.jalapeno.page.CreateAccount;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class JalapenoCreateAccountPage extends BasePageObject {
	
	/**
	 * @Author:Jakub Calabek
	 * @Date:24.7.2013
	 * @StepsToReproduce:
	 */
	
	@FindBy(how = How.ID, using = "patientfirstname")
	private WebElement inputPatientFirstName;
	
	@FindBy(how = How.ID, using = "patientlastname")
	private WebElement inputPatientLastName;
	
	@FindBy(how = How.ID, using = "emailaddress")
	private WebElement inputEmailAddresss;
	
	@FindBy(how = How.ID, using = "confirmemailaddress")
	private WebElement inputConfirmEmailAddresss;
	
	@FindBy(how = How.ID, using = "phoneone")
	private WebElement inputPhoneOne;
	
	@FindBy(how = How.ID, using = "phonetwo")
	private WebElement inputPhoneTwo;
	
	@FindBy(how = How.ID, using = "phone")
	private WebElement inputPhone;
	
	@FindBy(how = How.ID, using = "dateofbirthmonth")
	private WebElement inputDateOfBirthMonth;
	
	@FindBy(how = How.ID, using = "dateofbirthday")
	private WebElement inputDateOfBirthDay;
	
	@FindBy(how = How.ID, using = "dateofbirthyear")
	private WebElement inputDateOfBirthYear;
	
	@FindBy(how = How.ID, using = "gender")
	private WebElement radioGender;
	
	@FindBy(how = How.ID, using = "zipcode")
	private WebElement inputZipCode;
	
	@FindBy(how = How.CLASS_NAME, using = "arrow_prev ng-binding")
	private WebElement buttonCancel;
	
	@FindBy(how = How.CLASS_NAME, using = "callToAction arrow_next ng-binding")
	private WebElement buttonChooseUserId;
	
	public JalapenoCreateAccountPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

}
