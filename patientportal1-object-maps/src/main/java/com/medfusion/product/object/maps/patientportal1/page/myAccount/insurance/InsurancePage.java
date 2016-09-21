package com.medfusion.product.object.maps.patientportal1.page.myAccount.insurance;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.patientportal1.pojo.Portal;
import com.medfusion.product.patientportal1.utils.PortalConstants;
import com.medfusion.product.patientportal1.utils.PortalUtil;
import com.medfusion.product.patientportal1.utils.TestcasesData;

public class InsurancePage extends BasePageObject {

	@FindBy(
			xpath = "//select[@name='content:categories:0:questions:0:question:insuranceForm:insuranceFieldsContainer:insuranceFieldsPanel:fieldsContainer:fields:0:field']")
	private WebElement drpdwninsuranceType;

	@FindBy(xpath = "//tr[2]/td[2]/select")
	private WebElement drpdwnInsuranceName;

	@FindBy(
			xpath = "//input[@class='null required text' and @name='content:categories:0:questions:0:question:insuranceForm:insuranceFieldsContainer:insuranceFieldsPanel:fieldsContainer:fields:3:field']")
	private WebElement txtclaimsAddressLine1;

	@FindBy(
			xpath = "//input[@class='text' and @name='content:categories:0:questions:0:question:insuranceForm:insuranceFieldsContainer:insuranceFieldsPanel:fieldsContainer:fields:4:field']")
	private WebElement txtclaimsAddressLine2;

	@FindBy(
			xpath = "//input[@class='null required text' and @name='content:categories:0:questions:0:question:insuranceForm:insuranceFieldsContainer:insuranceFieldsPanel:fieldsContainer:fields:5:field']")
	private WebElement txtclaimsAddressCity;

	@FindBy(
			xpath = "//select[@class='null required choice' and @name='content:categories:0:questions:0:question:insuranceForm:insuranceFieldsContainer:insuranceFieldsPanel:fieldsContainer:fields:6:field']")
	private WebElement drpdwnclaimsAddressState;

	@FindBy(
			xpath = "//input[@class='null required text' and @name='content:categories:0:questions:0:question:insuranceForm:insuranceFieldsContainer:insuranceFieldsPanel:fieldsContainer:fields:7:field']")
	private WebElement txtclaimsAddressZip;

	@FindBy(
			xpath = "//input[@class='text' and @name='content:categories:0:questions:0:question:insuranceForm:insuranceFieldsContainer:insuranceFieldsPanel:fieldsContainer:fields:8:field']")
	private WebElement txtcustomerServicePhoneNumber;

	@FindBy(
			xpath = "//input[@class='text' and @name='content:categories:0:questions:0:question:insuranceForm:insuranceFieldsContainer:insuranceFieldsPanel:fieldsContainer:fields:11:field']")
	private WebElement txtinsuredSSN;

	@FindBy(
			xpath = "//input[@class='text' and @name='content:categories:0:questions:0:question:insuranceForm:insuranceFieldsContainer:insuranceFieldsPanel:fieldsContainer:fields:12:field']")
	private WebElement txtgroupNumber;

	@FindBy(
			xpath = "//input[@class='text hasDatepicker' and @name='content:categories:0:questions:0:question:insuranceForm:insuranceFieldsContainer:insuranceFieldsPanel:fieldsContainer:fields:14:field']")
	private WebElement dteffectiveDate;

	@FindBy(
			xpath = "//input[@class='text' and @name='content:categories:0:questions:0:question:insuranceForm:insuranceFieldsContainer:insuranceFieldsPanel:fieldsContainer:fields:15:field']")
	private WebElement txtcoPay;

	@FindBy(
			xpath = "//select[@class='null required choice'and @name='content:categories:0:questions:0:question:insuranceForm:insuranceFieldsContainer:insuranceFieldsPanel:fieldsContainer:fields:9:field']")
	private WebElement drpdwnrelationtoPolicyProvider;

	@FindBy(
			xpath = "//input[@class='null required text' and @name='content:categories:0:questions:0:question:insuranceForm:insuranceFieldsContainer:insuranceFieldsPanel:fieldsContainer:fields:11:field']")
	private WebElement txtpolicyNumber;

	@FindBy(xpath = "//input[@class='submit button' and @value='Save Insurance']")
	private WebElement btnSaveInsurance;

	@FindBy(xpath = "//a[contains(text(),'Delete')]")
	private WebElement linkDeleteInsurance;

	// Timeout for waiting methods in seconds
	private int waitingTime = 20;

	// Class of the Add Insurance button - present when nonzero amount of insurances exists on Insurances page
	private String addInsuranceButtonClass = "add";
	private String addInsuranceButtonText = "Add Insurance - Click here to add an entry";

	// Class and value of the Submit Insurance button - present either after clicking Add Insurance or by default, when no Insurances are defined for patient
	private String submitInsuranceButtonClass = "submit";
	private String submitInsuranceButtonValue = "Save Insurance";



	public InsurancePage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Add Insurance details for self Entering all required fields
	 * 
	 * @throws InterruptedException
	 * @throws Exception
	 */
	public void addInsuranceDetails() throws InterruptedException, Exception {
		IHGUtil.PrintMethodName();

		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);
		PortalUtil.setPortalFrame(driver);


		log("Enter Insurance details for Self");
		Select relationtoPolicyProvider = new Select(drpdwnrelationtoPolicyProvider);
		relationtoPolicyProvider.selectByVisibleText(PortalConstants.InsuranceRelation);

		log("Select Insurance Type");
		Select insuranceType = new Select(drpdwninsuranceType);
		insuranceType.selectByVisibleText(PortalConstants.InsuranceType);
		// UI swaps here, performs better than checking the swap
		Thread.sleep(4000);

		log("Select Insurance name");
		Select insuranceName = new Select(drpdwnInsuranceName);
		insuranceName.selectByVisibleText(PortalConstants.InsuranceName);

		log("Enter Address");
		txtclaimsAddressLine1.sendKeys(testcasesData.getAddress());

		log("Enter City");
		txtclaimsAddressCity.sendKeys(testcasesData.getAddressCity());

		log("Select State");
		Select claimsAddressState = new Select(drpdwnclaimsAddressState);
		claimsAddressState.selectByVisibleText(testcasesData.getAddressState());

		log("Enter Zip");
		txtclaimsAddressZip.sendKeys(testcasesData.getZip());

		log("Enter Insurance Policy Number");
		txtpolicyNumber.sendKeys(testcasesData.getSSN());

		btnSaveInsurance.click();
	}

	/**
	 * Indicates Insurance deleted successfully.
	 * 
	 * @throws InterruptedException
	 * @throws Exception
	 */
	public void deleteInsurance() throws InterruptedException, Exception {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);

		log("delete insurance");
		linkDeleteInsurance.click();

		Alert alert = driver.switchTo().alert();
		alert.accept();
	}

	/**
	 * Check for any existing Insurance and deletes them
	 * 
	 * @throws InterruptedException
	 * @throws Exception
	 */

	public void deleteAllInsurances() throws InterruptedException, Exception {
		IHGUtil.PrintMethodName();
		log("delete existing insurances");
		PortalUtil.setPortalFrame(driver);
		List<WebElement> items = driver.findElements(By.xpath("//a[contains(text(),'Delete')]"));
		for (int i = 0; i < items.size(); i++) {
			deleteInsurance();
		}
	}

	/**
	 * 
	 * @param Insurancetype
	 * @param InsuranceName
	 * @param Relation
	 * @throws InterruptedException
	 */


	public void allInsuranceDetails(String InsuranceName, String Insurancetype, String Relation, List<String> insurancelist) throws InterruptedException {

		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);

		log("Enter Insurance details for Self");
		Select relationtoPolicyProvider = new Select(drpdwnrelationtoPolicyProvider);
		relationtoPolicyProvider.selectByVisibleText(Relation);


		log("Select Insurance Type");
		Select insuranceType = new Select(drpdwninsuranceType);
		insuranceType.selectByVisibleText(Insurancetype);
		Thread.sleep(4000);

		log("Select Insurance name");
		Select insuranceName = new Select(drpdwnInsuranceName);
		insuranceName.selectByValue(InsuranceName);
		// UI swaps here, performs better than checking the swap
		Thread.sleep(4000);

		log("Enter Address");
		txtclaimsAddressLine1.sendKeys(insurancelist.get(3));

		log("Enter Address 2");
		txtclaimsAddressLine2.sendKeys(insurancelist.get(4));

		log("Enter City");
		txtclaimsAddressCity.sendKeys(insurancelist.get(5));

		log("Select State");
		Select claimsAddressState = new Select(drpdwnclaimsAddressState);
		claimsAddressState.selectByVisibleText("Alaska");

		log("Enter Zip");
		txtclaimsAddressZip.sendKeys(insurancelist.get(6));

		log("Enter Customer Service Phone Number");
		txtcustomerServicePhoneNumber.sendKeys(insurancelist.get(14));

		/*
		 * log("Enter Insured SSN"); txtinsuredSSN.sendKeys(insurancelist.get(15));
		 */

		log("Enter Insurance Policy Number");
		txtpolicyNumber.sendKeys(insurancelist.get(16));

		log("Enter Group Number");
		txtgroupNumber.sendKeys(insurancelist.get(17));

		/*
		 * log("Enter Effective Date"); dteffectiveDate.sendKeys(insurancelist.get(18));
		 * 
		 * log("Enter Co Pay"); txtcoPay.sendKeys(insurancelist.get(19));
		 */

		btnSaveInsurance.click();
	}



	// Waiting methods for Insurance page buttons
	public void waitForAddInsuranceButton() {
		log("Waiting for Add Insurance button!");
		try {
			IHGUtil.waitForElementByClassAndText(driver, addInsuranceButtonClass, addInsuranceButtonText, waitingTime);
		} catch (Exception e) {
			log("Element either not found in time or inaccessible, or something even worse happenned while waiting for Add Insurance button");
			e.printStackTrace();
		}
		log("Add Insurance button found, proceeding!");
	}

	public void waitForSubmitInsuranceButton() {
		log("Waiting for Submit Insurance button!");
		try {
			IHGUtil.waitForElementByClassAndValue(driver, submitInsuranceButtonClass, submitInsuranceButtonValue, waitingTime);
		} catch (Exception e) {
			log("Element either not found in time or inaccessible, or something even worse happenned while waiting for Submit Insurance button");
			e.printStackTrace();
		}
		log("Submit Insurance button found, proceeding!");
	}

}


