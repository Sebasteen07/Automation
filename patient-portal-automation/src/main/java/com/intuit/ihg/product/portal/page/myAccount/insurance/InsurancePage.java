package com.intuit.ihg.product.portal.page.myAccount.insurance;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.portal.utils.Portal;
import com.intuit.ihg.product.portal.utils.PortalConstants;
import com.intuit.ihg.product.portal.utils.PortalUtil;
import com.intuit.ihg.product.portal.utils.TestcasesData;

public class InsurancePage extends BasePageObject {

	@FindBy(xpath = "//select[@name='content:categories:0:questions:0:question:insuranceForm:insuranceFieldsContainer:insuranceFieldsPanel:fieldsContainer:fields:0:field']")
	private WebElement drpdwninsuranceType;

	@FindBy(xpath = "//tr[2]/td[2]/select")
	private WebElement drpdwnInsuranceName;

	@FindBy(xpath = "//input[@class='null required text' and @name='content:categories:0:questions:0:question:insuranceForm:insuranceFieldsContainer:insuranceFieldsPanel:fieldsContainer:fields:3:field']")
	private WebElement txtclaimsAddressLine1;

	@FindBy(xpath = "//input[@class='null required text' and @name='content:categories:0:questions:0:question:insuranceForm:insuranceFieldsContainer:insuranceFieldsPanel:fieldsContainer:fields:5:field']")
	private WebElement txtclaimsAddressCity;

	@FindBy(xpath = "//select[@class='null required choice' and @name='content:categories:0:questions:0:question:insuranceForm:insuranceFieldsContainer:insuranceFieldsPanel:fieldsContainer:fields:6:field']")
	private WebElement drpdwnclaimsAddressState;

	@FindBy(xpath = "//input[@class='null required text' and @name='content:categories:0:questions:0:question:insuranceForm:insuranceFieldsContainer:insuranceFieldsPanel:fieldsContainer:fields:7:field']")
	private WebElement txtclaimsAddressZip;

	@FindBy(xpath = "//select[@class='null required choice' and @name='content:categories:0:questions:0:question:insuranceForm:insuranceFieldsContainer:insuranceFieldsPanel:fieldsContainer:fields:9:field']")
	private WebElement drpdwnrelationtoPolicyProvider;

	@FindBy(how = How.NAME, using = "content:categories:0:questions:0:question:insuranceForm:insuranceFieldsContainer:insuranceFieldsPanel:fieldsContainer:fields:12:field")
	private WebElement txtpolicyNumber;

	@FindBy(xpath = "//input[@class='submit button' and @value='Save Insurance']")
	private WebElement btnSaveInsurance;
	
	@FindBy(xpath = "//a[contains(text(),'Delete')]")
	private WebElement linkDeleteInsurance; 

	
	public InsurancePage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Add Insurance details for self 
	 * Entering all required fields 
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
		Thread.sleep(4000);
				
		log("Select Insurance Type");
		Select insuranceType = new Select(drpdwninsuranceType);
		insuranceType.selectByVisibleText(PortalConstants.InsuranceType);
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
	 * @throws InterruptedException
	 * @throws Exception
	 */

	public void deleteAllInsurances() throws InterruptedException, Exception {
		IHGUtil.PrintMethodName();
		log("delete existing insurances");
		PortalUtil.setPortalFrame(driver);
		List<WebElement> items = driver.findElements(By.xpath("//a[contains(text(),'Delete')]"));
		for ( int i = 0; i < items.size(); i++ ) {
			  deleteInsurance();
		}
	}


}



