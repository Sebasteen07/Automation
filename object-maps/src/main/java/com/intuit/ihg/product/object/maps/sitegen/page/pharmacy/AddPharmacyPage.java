//Copyright 2013-2020 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.sitegen.page.pharmacy;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.sitegen.utils.SitegenConstants;
import com.medfusion.common.utils.IHGUtil;

public class AddPharmacyPage  extends BasePageObject{
	
    @FindBy(xpath="//input[@id='name']")
	private WebElement txtPharmacyName;
	
    @FindBy(xpath="//input[@id='address']")
	private WebElement txtPharmacyAddr;
	
    @FindBy(xpath="//input[@id='city']")
	private WebElement txtPharmacyCity;
	
    @FindBy(xpath="//select[@name='state']")
    private WebElement stateDropDown;
	
    @FindBy(xpath="//input[@id='zip']")
    private WebElement txtPharmacyZip;
	
    @FindBy(xpath="//input[@id='Phone']")
	private WebElement txtPharmacyPhone;
	
    @FindBy(xpath="//input[@id='fax1']")
	private WebElement txtPharmacyFax1;
    
    @FindBy(xpath="//input[@id='fax2']")
   	private WebElement txtPharmacyFax2;
	
    @FindBy(xpath="//input[@id='fax3']")
   	private WebElement txtPharmacyFax3;
    
    @FindBy(xpath="//select[@name='locations']")
	private WebElement locationDropDown;
	
    @FindBy(xpath="//table/tbody/tr[16]/td[3]/input")
	private WebElement externalPharmacyId;
	
    @FindBy(xpath="//input[@name='btn_confirm']")
	private WebElement btnConfirmPharmacy;
	
    @FindBy(xpath="//input[@name='btn_cancel']")
	private WebElement btnCancel;

    @FindBy(xpath="//li[@class='sg_err_text']")
    private WebElement sameIDErrorMsg;
    
	public AddPharmacyPage(WebDriver driver) {
		super(driver);
	}

	public String fillPharmacyDetails(String externalid,boolean flag) throws InterruptedException {
		IHGUtil.PrintMethodName();
		String successmsg= "Pharmacy added successfully";
		String PharmacyName= SitegenConstants.PHARMACYNAME+IHGUtil.createRandomNumericString();
		txtPharmacyName.sendKeys(PharmacyName);
		txtPharmacyAddr.sendKeys(SitegenConstants.ADDRESS);
		txtPharmacyCity.sendKeys(SitegenConstants.CITY);
		
		Select sel = new Select(stateDropDown);
		sel.selectByIndex(1);;
		
		txtPharmacyZip.sendKeys(SitegenConstants.ZIPCODE);
		txtPharmacyPhone.sendKeys(IHGUtil.createRandomNumericString(10));
		
		Select select = new Select(locationDropDown);
		select.selectByVisibleText(SitegenConstants.PHARMACYLOCATION);
		
		externalPharmacyId.sendKeys(externalid);
		btnConfirmPharmacy.click();
		Thread.sleep(2000);
		if(flag)
		{
			return successmsg;
	    }
		else {
		return sameIDErrorMsg.getText();
		}		
	}
}


