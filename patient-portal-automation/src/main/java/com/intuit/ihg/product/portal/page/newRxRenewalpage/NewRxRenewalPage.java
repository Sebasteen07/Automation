package com.intuit.ihg.product.portal.page.newRxRenewalpage;

import java.util.List;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.portal.utils.Portal;
import com.intuit.ihg.product.portal.utils.PortalConstants;
import com.intuit.ihg.product.portal.utils.PortalUtil;
import com.intuit.ihg.product.portal.utils.TestcasesData;

public class NewRxRenewalPage  extends BasePageObject {

	public NewRxRenewalPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}


	@FindBy(xpath = "//select[@name='providerContainer:providerDD']")
	private WebElement chooseProviderDropDrown;

	@FindBy(xpath = "//input[@class='button' and @value='Continue']")
	private WebElement clickContinuebtn;

	@FindBy(xpath = "//input[@class='button' and @value='Submit']")
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

	/**
	 * @Description:Choose the provider name from Drop Down
	 * @param pProvider
	 */
	public void chooseProvider(String pProvider)
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
				Select selectProvider=new Select(chooseProviderDropDrown);
				selectProvider.selectByIndex(count);
				break;
			}
			count++;
		}
		IHGUtil.waitForElement(driver,10,clickContinuebtn);
		clickContinuebtn.click();
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

}
