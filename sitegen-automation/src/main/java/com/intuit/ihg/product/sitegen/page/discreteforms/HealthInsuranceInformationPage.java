package com.intuit.ihg.product.sitegen.page.discreteforms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class HealthInsuranceInformationPage extends BasePageObject{
	
	@FindBy(xpath="//li[@data-section='insurance']/a")
	private WebElement lnkHealthInsuranceInfoPage;
	
	@FindBy(id = "hide_insurance_check")
	private WebElement hideInsuranceCheck;	

	@FindBy(id = "primary_insurance_company")
	private WebElement primaryInsuranceCompany;	

	@FindBy(id = "primary_insurance_company_phone")
	private WebElement primaryInsuranceCompanyPhone;	

	@FindBy(id = "primary_insurance_claims_address")
	private WebElement primaryInsuranceClaimsAddress;	

	@FindBy(id = "primary_insurance_claims_city")
	private WebElement primaryInsuranceClaimsCity;	

	@FindBy(id = "primary_insurance_claims_state")
	private WebElement primaryInsuranceClaimsState;	

	@FindBy(id = "primary_insurance_claims_zipcode")
	private WebElement primaryInsuranceClaimsZipcode;	

	@FindBy(id = "primary_policy_holder_firstname")
	private WebElement primaryPolicyHolderFirstname;	

	@FindBy(id = "primary_policy_holder_lastname")
	private WebElement primaryPolicyHolderLastname;	

	@FindBy(id = "primary_policy_holder_memberid")
	private WebElement primaryPolicyHolderMemberid;	

	@FindBy(id = "primary_group_or_plan_number")
	private WebElement primaryGroupOrPlanNumber;	

	@FindBy(id = "primary_policy_holder_employer")
	private WebElement primaryPolicyHolderEmployer;	

	@FindBy(id = "primary_policy_holder_dob")
	private WebElement primaryPolicyHolderDob;	

	@FindBy(id = "primary_policy_holder_ssn")
	private WebElement primaryPolicyHolderSsn;	

	@FindBy(id = "primary_policy_holder_phone")
	private WebElement primaryPolicyHolderPhone;	

	@FindBy(id = "primary_policy_holder_streetaddress")
	private WebElement primaryPolicyHolderStreetaddress;	

	@FindBy(id = "primary_policy_holder_city")
	private WebElement primaryPolicyHolderCity;	

	@FindBy(id = "primary_policy_holder_state")
	private WebElement primaryPolicyHolderState;	

	@FindBy(id = "primary_policy_holder_zipcode")
	private WebElement primaryPolicyHolderZipcode;	

	@FindBy(id = "primary_guarantor_firstname")
	private WebElement primaryGuarantorFirstname;	

	@FindBy(id = "primary_guarantor_lastname")
	private WebElement primaryGuarantorLastname;	

	@FindBy(id = "primary_guarantor_ssn")
	private WebElement primaryGuarantorSsn;	

	@FindBy(id = "primary_guarantor_streetaddress")
	private WebElement primaryGuarantorStreetaddress;	

	@FindBy(id = "primary_guarantor_city")
	private WebElement primaryGuarantorCity;	

	@FindBy(id = "primary_guarantor_state")
	private WebElement primaryGuarantorState;	

	@FindBy(id = "primary_guarantor_zipcode")
	private WebElement primaryGuarantorZipcode;	

	@FindBy(id="hide_insurance_check")
	private WebElement chckHideInsurance;
	
	@FindBy(id="save_config_form")              
	private WebElement btnSave;
	
	
	@FindBy(xpath = "//li[input[@id='primary_insurance_company']]/a")
	private WebElement primaryInsuranceCompanyAsterisk;
	
	
	public HealthInsuranceInformationPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * Indicates if the search page is loaded
	 * 
	 * @return true or false
	 */
	public boolean isSearchPageLoaded() {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setSiteGenFrame(driver);

		boolean result = false;
		try {
			result = IHGUtil.waitForElement(driver, 6, lnkHealthInsuranceInfoPage);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}
	
	/**
	 * Click on link - Health Insurance Information	
	 * @return
	 */
	
	public SecondaryHealthInsurancePage clicklnkHealthInsuranceInfo()
	{	
		//SitegenlUtil.setDefaultFrame(driver);
		IHGUtil.waitForElement(driver, 30, lnkHealthInsuranceInfoPage);
		lnkHealthInsuranceInfoPage.click();
		
		IHGUtil.waitForElement(driver, 30, chckHideInsurance);
		if (chckHideInsurance.isSelected())
			chckHideInsurance.click();
		
		selectInsuranceCompanyAppearance();
		
		IHGUtil.waitForElement(driver, 30, btnSave);
			btnSave.click();
		// Close the browser window
		return PageFactory.initElements(driver,SecondaryHealthInsurancePage.class);
	}
	
	/**
	 * Click on Insurance company name checkbox and makes it optional to fill
	 */
	
	public void selectInsuranceCompanyAppearance()
	{
		IHGUtil.waitForElement(driver, 30, primaryInsuranceCompany);
		if (primaryInsuranceCompany.isSelected() == false)
			primaryInsuranceCompany.click();
		if (primaryInsuranceCompany.isSelected() == false)
			
			primaryInsuranceCompanyAsterisk.click();
	}
	

}
