package com.medfusion.product.object.maps.patientportal1.page.newRxRenewalpage;

import java.util.List;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.patientportal1.utils.PortalConstants;
import com.medfusion.product.patientportal1.utils.PortalUtil;

public class NewRxRenewalPage  extends BasePageObject {
	
	private long createdTs;
	public NewRxRenewalPage(WebDriver driver) {
		super(driver);
		createdTs = System.currentTimeMillis();
		// TODO Auto-generated constructor stub
	}


	@FindBy(xpath = "//select[@name='providerContainer:providerDD']")
	private WebElement chooseProviderDropDrown;

	@FindBy(xpath = "//input[@class='button' and @value='Continue']")
	private WebElement clickContinuebtn;

	@FindBy(name = "submitButton")
	private WebElement clickSubmitbtn;

	@FindBy(xpath = "//div[@id='medForm']/div/div[@class='fieldWrapper']/div[2]/input")
	private WebElement medicationName;

	@FindBy(xpath = "//div[@id='medForm']/div[2]/div[@class='fieldWrapper']/div[2]/input")
	private WebElement dosage;

	@FindBy(xpath = "//input[@name='pharmacyPanel:radioGroup']")
	private WebElement chooseFromList;


	@FindBy(xpath = ".//*[@class='customForm']/div[2]/div[2]/strong[2]")
	public WebElement medicineName1;
	
	@FindBy(xpath = ".//*[@class='customForm']/div[1]/div[2]/strong[2]")
	public WebElement medicineName0;

	@FindBy(xpath = ".//*[@class='customForm']/div[3]/div[2]/strong[2]")
	public WebElement medicineName2;

	@FindBy(xpath = "//div[@class='standardSet standardSetHeader']//strong/a[@class='edit']")
	private WebElement clickEdit;

	@FindBy(xpath = "//select[@name='pharmacyPanel:radioGroup:pharmacySearchContainer:pharmacySearchList']")
    	private WebElement pharmacySearchList;

	@FindBy(xpath = "//div[@class='paperPad']/div[4]/p")
	public WebElement renewalConfirmationmessage;
	@FindBy(xpath = "//div[@id='medForm']/div[3]/div[@class='fieldWrapper']/div[2]/input")
	private WebElement quantity;
	
	@FindBy(xpath = "//div[@id='medForm']/div[4]/div[@class='fieldWrapper']/div[2]/input")
	private WebElement numberOfRefills;
	
	@FindBy(xpath = "//div[@id='medForm']/div[5]/div[@class='fieldWrapper']/div[2]/input")
	private WebElement prescriptionNo;
	
	@FindBy(xpath = "//div[@id='medicationForm']/div[2]/div[@class='medicationBorder']/div[3]/div[@class='fieldWrapper']/div[2]/textarea")
	public WebElement additionalInformation;

	/**
	 * @Description:Choose the provider name from Drop Down
	 * @param pProvider
	 * @throws Exception 
	 */
	public void chooseProvider(String pProvider) throws Exception
	{
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver,10,chooseProviderDropDrown);
		List<WebElement> list = driver.findElements(By.xpath("//select[@name='providerContainer:providerDD']/option"));
		for(WebElement li : list)
		{
			int count=1;
			if(li.getText().contains(pProvider))
			{
				Thread.sleep(3000);
				PortalUtil.setPortalFrame(driver);
				Select selectProvider=new Select(chooseProviderDropDrown);
				selectProvider.selectByIndex(count);
				Thread.sleep(3000);
				break;
			}
			count++;
		}
		IHGUtil.waitForElement(driver,10,clickContinuebtn);
		clickContinuebtn.click();
		Thread.sleep(8000);
	}

	/**
	 * @Description:Set the Medication Fields
	 */
	public void setMedicationFields()
	{
		PortalUtil.setPortalFrame(driver);
		try{
			medicationName.clear();
			medicationName.sendKeys(PortalConstants.MedicationName);
			dosage.clear();
			dosage.sendKeys(PortalConstants.Dosage);
		}
		catch(Exception e){
			clickEdit.click();
			PortalUtil.setPortalFrame(driver);
			IHGUtil.waitForElement(driver,5,medicationName);
			medicationName.clear();
			medicationName.sendKeys(PortalConstants.MedicationName);
			dosage.clear();
			dosage.sendKeys(PortalConstants.Dosage);
		}
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
			List<WebElement> list = driver.findElements(By.xpath("//select[@name='pharmacyPanel:radioGroup']/option"));
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

	/**
	 * @Description:Click On Continue button
	 */
	public void clickContinuebtn()
	{
		PortalUtil.setPortalFrame(driver);
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver,10,clickContinuebtn);
		clickContinuebtn.click();
	}


	/**
	 * @Description:Click On Submit button
	 */
	public void clickSubmitbtn()
	{
		PortalUtil.setPortalFrame(driver);
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver,10,clickSubmitbtn);
		clickSubmitbtn.click();
	}

	/**
	 * @Description: Get medication Name
	 */

	public void checkMedication()
	{
		PortalUtil.setPortalFrame(driver);
		IHGUtil.PrintMethodName();
	}
	
	/**
	 * @return createdTs
	 */
	public long getCreatedTs() {
		IHGUtil.PrintMethodName();
		return createdTs;
	}
	/**
	 * @Description: Set Medication details for integration Platform project
	 */
	public void setMedicationDetails()
	{
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		medicationName.sendKeys(PortalConstants.MedicationName+""+createdTs);
		dosage.sendKeys(PortalConstants.Dosage);
		quantity.sendKeys(PortalConstants.Quantity);
		numberOfRefills.sendKeys(PortalConstants.No_Of_Refills);
		prescriptionNo.sendKeys(PortalConstants.Prescription_No);
		additionalInformation.sendKeys(PortalConstants.Additional_Info);
		
	}
}
