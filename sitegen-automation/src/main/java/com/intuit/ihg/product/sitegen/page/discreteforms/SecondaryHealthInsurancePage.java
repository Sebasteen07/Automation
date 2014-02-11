package com.intuit.ihg.product.sitegen.page.discreteforms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class SecondaryHealthInsurancePage extends BasePageObject{
	
	@FindBy(xpath="//form[@id='form_form']/div[2]/div[1]/ul/li[4]/a/em")
	private WebElement lnkSecondaryHealthInsurance;
	
	@FindBy(id = "hide_secondary_insurance_check")
	public WebElement hideSecondaryInsuranceCheck;	

	@FindBy(id = "secondary_insurance_company")
	public WebElement secondaryInsuranceCompany;	

	@FindBy(id = "secondary_insurance_company_phone")
	public WebElement secondaryInsuranceCompanyPhone;	

	@FindBy(id = "secondary_insurance_claims_address")
	public WebElement secondaryInsuranceClaimsAddress;	

	@FindBy(id = "secondary_insurance_claims_city")
	public WebElement secondaryInsuranceClaimsCity;	

	@FindBy(id = "secondary_insurance_claims_state")
	public WebElement secondaryInsuranceClaimsState;	

	@FindBy(id = "secondary_insurance_claims_zipcode")
	public WebElement secondaryInsuranceClaimsZipcode;	

	@FindBy(id = "secondary_policy_holder_firstname")
	public WebElement secondaryPolicyHolderFirstname;	

	@FindBy(id = "secondary_policy_holder_lastname")
	public WebElement secondaryPolicyHolderLastname;	

	@FindBy(id = "secondary_policy_holder_memberid")
	public WebElement secondaryPolicyHolderMemberid;	

	@FindBy(id = "secondary_group_or_plan_number")
	public WebElement secondaryGroupOrPlanNumber;	

	@FindBy(id = "secondary_policy_holder_employer")
	public WebElement secondaryPolicyHolderEmployer;	

	@FindBy(id = "secondary_policy_holder_dob")
	public WebElement secondaryPolicyHolderDob;	

	@FindBy(id = "secondary_policy_holder_ssn")
	public WebElement secondaryPolicyHolderSsn;	

	@FindBy(id = "secondary_policy_holder_phone")
	public WebElement secondaryPolicyHolderPhone;	

	@FindBy(id = "secondary_policy_holder_streetaddress")
	public WebElement secondaryPolicyHolderStreetaddress;	

	@FindBy(id = "secondary_policy_holder_city")
	public WebElement secondaryPolicyHolderCity;	

	@FindBy(id = "secondary_policy_holder_state")
	public WebElement secondaryPolicyHolderState;	

	@FindBy(id = "secondary_policy_holder_zipcode")
	public WebElement secondaryPolicyHolderZipcode;	
	
	@FindBy(id="save_config_form")              
	private WebElement btnSave;
		
	
	public SecondaryHealthInsurancePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}


/**
 * Click on link - Health Insurance Information	
 * @return
 */

public OtherDoctorsYouSeen clicklnkSecondaryHealthInsuranceInfo()
{	
	//SitegenlUtil.setDefaultFrame(driver);
	IHGUtil.waitForElement(driver, 30, lnkSecondaryHealthInsurance);
	lnkSecondaryHealthInsurance.click();
		
	IHGUtil.waitForElement(driver, 30, btnSave);
		btnSave.click();
	// Close the browser window
	return PageFactory.initElements(driver,OtherDoctorsYouSeen.class);
}


}