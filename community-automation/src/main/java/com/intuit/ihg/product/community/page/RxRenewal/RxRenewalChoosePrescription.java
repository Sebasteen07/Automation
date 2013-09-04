package com.intuit.ihg.product.community.page.RxRenewal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class RxRenewalChoosePrescription extends BasePageObject {

	@FindBy(how = How.CSS, using = "button[type=submit]")
	public WebElement btnContinue;

	@FindBy(how = How.ID, using = "medication_and_dosage")
	public WebElement medicationName;

	// FindBy(how = How.XPATH, using = "(//div[@id='autocomplete']/ul/li)"
	@FindBy(xpath = "(//div[@id='autocomplete']/ul/li)")
	public WebElement listIMO;

	@FindBy(how = How.ID, using = "quantity")
	public WebElement medicineQunatity;

	@FindBy(how = How.ID, using = "rx_number")
	public WebElement rxNumber;

	public RxRenewalChoosePrescription(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public RxRenewalChoosePrescription SelectNewPrescription() throws InterruptedException {

		// Selecting New Prescription
		driver.findElement(By.xpath("// * [contains(text(),'Enter a new prescription...')]")).click();
		log("DEBUG: URL After Selecting Practice [" + driver.getCurrentUrl() + "]");
		Thread.sleep(2000);
		return PageFactory.initElements(driver, RxRenewalChoosePrescription.class);

	}

public boolean locateEnterNewPrescription(){
		
		if (driver.findElements(By.xpath("// * [contains(text(),'Enter a new prescription...')]") ).size() != 0) 
		{
			return true;	
		}
		
	 return false;
	}

	public void fillPrescription(String sMedicine, String sDosage,String sRxnumber) throws InterruptedException {

		// Fill Medication, Quantity, and Rx details

		medicationName.sendKeys(sMedicine);
		Thread.sleep(2000);
		listIMO.click();
		Thread.sleep(2000);
		medicineQunatity.sendKeys(sDosage);
		Thread.sleep(2000);
		rxNumber.sendKeys(sRxnumber);
		Thread.sleep(2000);
		
		
		btnContinue.click();
	}
}
