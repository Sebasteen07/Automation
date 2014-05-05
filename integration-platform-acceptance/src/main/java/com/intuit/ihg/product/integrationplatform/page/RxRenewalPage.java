package com.intuit.ihg.product.integrationplatform.page;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.integrationplatform.utils.IntegrationConstants;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class RxRenewalPage extends BasePageObject {

	private long createdTs;
	public RxRenewalPage(WebDriver driver) {
		super(driver);
		this.createdTs = System.currentTimeMillis();
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(xpath = "//select[@name='insuranceContainer:insuranceDD']")
	private WebElement chooseInsurnaceDropDown;
	
	@FindBy(xpath = "//input[@class='button' and @value='Continue']")
	private WebElement clickContinuebtn;
	
	@FindBy(xpath = "//div[@id='medForm']/div/div[@class='fieldWrapper']/div[2]/input")
	private WebElement medicationName;
	
	@FindBy(xpath = "//div[@id='medForm']/div[2]/div[@class='fieldWrapper']/div[2]/input")
	private WebElement dosage;
	
	@FindBy(xpath = "//div[@id='medForm']/div[3]/div[@class='fieldWrapper']/div[2]/input")
	private WebElement quantity;
	
	@FindBy(xpath = "//div[@id='medForm']/div[4]/div[@class='fieldWrapper']/div[2]/input")
	private WebElement numberOfRefills;
	
	@FindBy(xpath = "//div[@id='medForm']/div[5]/div[@class='fieldWrapper']/div[2]/input")
	private WebElement prescriptionNo;
	
	@FindBy(xpath = "//select[@name='pharmacyPanel:radioGroup:pharmacySearchContainer:pharmacySearchList']")
	private WebElement pharmacySearchList;
	
	@FindBy(xpath = "//input[@name='pharmacyPanel:radioGroup']")
	private WebElement chooseFromList;
	
	@FindBy(xpath = "//input[@class='button' and @value='Submit']")
	private WebElement clickSubmitbtn;
	
	@FindBy(xpath = "//div[@class='paperPad']/div[4]/p")
	public WebElement renewalConfirmationmessage;
	
	@FindBy(xpath = "//div[@id='medicationForm']/div[2]/div[@class='medicationBorder']/div[3]/div[@class='fieldWrapper']/div[2]/textarea")
	public WebElement additionalInformation;
	
	public long getCreatedTs() {
		IHGUtil.PrintMethodName();
		return createdTs;
	}

	/*
	 * Set Medication Details
	 */
	public void setMedication(){
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		medicationName.sendKeys(IntegrationConstants.MEDICATION_NAME+""+createdTs);
		dosage.sendKeys(IntegrationConstants.DOSAGE);
		quantity.sendKeys(IntegrationConstants.QUANTITY);
		numberOfRefills.sendKeys(IntegrationConstants.NO_OF_REFILLS);
		prescriptionNo.sendKeys(IntegrationConstants.PRESCRIPTION_NO);
		additionalInformation.sendKeys(IntegrationConstants.ADDITIONAL_INFO);
				
	}
	
	/**
	 * @Description:Set the Pharmacy Fields
	 */
	public void setPharmacyFields()
	{
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		chooseFromList.click();
		try{
			List<WebElement> list = driver.findElements(By.xpath("//select[@name='pharmacyPanel:radioGroup:pharmacySearchContainer:pharmacySearchList']/option"));
			for(WebElement li : list)
			{
				int count=1;
				if(li.getText().contains("CVS"))
				{
					Select selectProvider=new Select(pharmacySearchList);
					selectProvider.selectByIndex(count);
					break;
				}
				count++;
			}
		}
		catch(Exception e)
		{
			log("Pharmacy List is not displayed");
		}
		IHGUtil.waitForElement(driver,10,clickContinuebtn);
		clickContinuebtn.click();
		IHGUtil.waitForElement(driver,10,clickSubmitbtn);
		clickSubmitbtn.click();
	}
		
}
