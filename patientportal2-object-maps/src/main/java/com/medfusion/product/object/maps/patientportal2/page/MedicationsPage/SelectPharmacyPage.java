// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.MedicationsPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;

public class SelectPharmacyPage {

	
	public SelectPharmacyPage(WebDriver driver) {
		super();
		IHGUtil.PrintMethodName();
		PageFactory.initElements(driver, this);
		
	}

	@FindBy(how=How.XPATH,using="//span[@class='pharmacy-radio-button']")
	private WebElement radioPharmacy;
	
	@FindBy(how=How.XPATH,using="//div[@class='form-buttons ng-scope']/button[@type='button']")
	private WebElement btnBack;
	
	@FindBy(how=How.XPATH,using="//button[@class='btn btn-primary ng-binding']")
	private WebElement btnContinue;
	
	@FindBy(how = How.ID, using = "add-new-pharmacy")
	private WebElement addPharmacy;

	public void selectPharmacy() throws InterruptedException {
	
		radioPharmacy.click();
		btnContinue.click();
		
	}
	
	
}
