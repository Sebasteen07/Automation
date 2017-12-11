package com.medfusion.product.object.maps.precheck.page.myInsuranceImage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class InsuranceImagePage extends BasePageObject {

	@FindBy(how = How.XPATH, using = ".//*[@id='insuranceFormWrapper']/form/div[1]/div/data-mf-camera[1]/label/input")
	private WebElement primaryInsuranceFrontImageInput;
	
	@FindBy(how = How.XPATH, using = ".//*[@id='insuranceFormWrapper']/form/div[1]/div/data-mf-camera[2]/label/input")
	private WebElement primaryInsuranceBackImageInput;
	
	@FindBy(how = How.XPATH, using = ".//*[@id='insuranceFormWrapper']/form/div[2]/div/data-mf-camera[1]/label/input")
	private WebElement secondaryInsuranceFrontImageInput;
	
	@FindBy(how = How.XPATH, using = ".//*[@id='insuranceFormWrapper']/form/div[2]/div/data-mf-camera[2]/label/input")
	private WebElement secondaryInsuranceBackImageInput;
	
	@FindBy(how = How.XPATH, using = ".//*[@id='insuranceFormWrapper']/form/div[3]/div/data-mf-camera[1]/label/input")
	private WebElement tertiaryInsuranceFrontImageInput;
	
	@FindBy(how = How.XPATH, using = ".//*[@id='insuranceFormWrapper']/form/div[3]/div/data-mf-camera[2]/label/input")
	private WebElement tertiaryInsuranceBackImageInput;
	
	@FindBy(how = How.ID, using = "insuranceConfirmButton")
	private WebElement insuranceConfirmButton;
	
	@FindBy(how = How.XPATH, using = ".//*[@id='insuranceTabs']/li[2]")
	private WebElement secondaryInsuranceImageTab;
	
	@FindBy(how = How.XPATH, using = ".//*[@id='insuranceTabs']/li[3]")
	private WebElement tertiaryInsuranceImageTab;
	
	public InsuranceImagePage(WebDriver driver) {
		super(driver);
	}
	
	public void uploadPrimaryInsuranceFrontPhotoInput(String insuranceCardPath) {
		primaryInsuranceFrontImageInput.sendKeys(insuranceCardPath);
		
	}

	public void uploadPrimaryInsuranceBackPhotoInput(String insuranceCardPath) {
		primaryInsuranceBackImageInput.sendKeys(insuranceCardPath);
	}

	public void gotoSecondaryInsuranceImageTab() {
		IHGUtil.waitForElement(driver, 60, secondaryInsuranceImageTab);
		secondaryInsuranceImageTab.click();
	}

	public void uploadSecondaryInsuranceFrontPhotoInput(String insuranceCardPath) {
		secondaryInsuranceFrontImageInput.sendKeys(insuranceCardPath);
	}

	public void uploadSecondaryInsuranceBackPhotoInput(String insuranceCardPath) {
		secondaryInsuranceBackImageInput.sendKeys(insuranceCardPath);
	}

	public void gotoTertiaryInsuranceImageTab() {
		IHGUtil.waitForElement(driver, 60, tertiaryInsuranceImageTab);
		tertiaryInsuranceImageTab.click();
	}

	public void uploadTertiaryInsuranceFrontPhotoInput(String insuranceCardPath) {
		tertiaryInsuranceFrontImageInput.sendKeys(insuranceCardPath);
	}

	public void uploadTertiaryInsuranceBackPhotoInput(String insuranceCardPath) {
		tertiaryInsuranceBackImageInput.sendKeys(insuranceCardPath);
	}

	public void submitInsuranceImage() {
		insuranceConfirmButton.click();
	}

}
