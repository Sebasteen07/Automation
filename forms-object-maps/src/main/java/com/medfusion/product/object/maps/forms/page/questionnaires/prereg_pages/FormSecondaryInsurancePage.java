package com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.product.object.maps.forms.page.questionnaires.PortalFormPage;

public class FormSecondaryInsurancePage extends PortalFormPage {

	public FormSecondaryInsurancePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "idonot_secondary_insurance")
	private WebElement idonotSecondaryInsurance;
	
	@FindBy(id ="secondary_insurance_company")
	private WebElement secondarycompany;
	
	@FindBy(id ="secondary_policy_holder_firstname")
	private WebElement secondaryfirstname;
	
	@FindBy(xpath = "//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;

	/**
	 * @Description:Set no secondary insurance
	 * @throws Exception
	 */
	public void setNoSecondaryInsurance() throws Exception {
		idonotSecondaryInsurance.click();
	}
	public void setSecondaryInsuranceNAme(String NameofSecondInsurance,String Patientname) throws InterruptedException
	{
		 Thread.sleep(1000);
		 secondarycompany.clear();
		secondarycompany.sendKeys(NameofSecondInsurance );
		secondaryfirstname.clear();
		secondaryfirstname.sendKeys(Patientname);
		saveAndContinuebtn.click();
	}
	public FormMedicationsPage fillSecondInsurance(String NameofSecondInsurance,String Patientname) throws InterruptedException
	{
		setSecondaryInsuranceNAme(NameofSecondInsurance,Patientname);
		
		return PageFactory.initElements(driver, FormMedicationsPage.class);
		
	}

	@Override
	public boolean isPageLoaded() {
		return driver.findElement(By.xpath(String.format(PAGE_LOADED_XPATH_TEMPLATE, "Secondary Health Insurance Information"))).isDisplayed();
	}
}
