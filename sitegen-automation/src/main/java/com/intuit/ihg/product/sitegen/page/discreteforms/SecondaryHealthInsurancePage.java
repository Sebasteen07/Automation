package com.intuit.ihg.product.sitegen.page.discreteforms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class SecondaryHealthInsurancePage extends BasePageObject{
	
	@FindBy(xpath="//li[@data-section='secondary_insurance']/a")
	private WebElement lnkSecondaryHealthInsurance;
	
	@FindBy(id = "hide_secondary_insurance_check")
	private WebElement hideSecondaryInsuranceCheck;	

	@FindBy(id = "secondary_insurance_company")
	private WebElement secondaryInsuranceCompany;	

	@FindBy(id = "secondary_insurance_company_phone")
	private WebElement secondaryInsuranceCompanyPhone;	

	@FindBy(id = "secondary_insurance_claims_address")
	private WebElement secondaryInsuranceClaimsAddress;	

	@FindBy(id = "secondary_insurance_claims_city")
	private WebElement secondaryInsuranceClaimsCity;	

	@FindBy(id = "secondary_insurance_claims_state")
	private WebElement secondaryInsuranceClaimsState;	

	@FindBy(id = "secondary_insurance_claims_zipcode")
	private WebElement secondaryInsuranceClaimsZipcode;	

	@FindBy(id = "secondary_policy_holder_firstname")
	private WebElement secondaryPolicyHolderFirstname;	

	@FindBy(id = "secondary_policy_holder_lastname")
	private WebElement secondaryPolicyHolderLastname;	

	@FindBy(id = "secondary_policy_holder_memberid")
	private WebElement secondaryPolicyHolderMemberid;	

	@FindBy(id = "secondary_group_or_plan_number")
	private WebElement secondaryGroupOrPlanNumber;	

	@FindBy(id = "secondary_policy_holder_employer")
	private WebElement secondaryPolicyHolderEmployer;	

	@FindBy(id = "secondary_policy_holder_dob")
	private WebElement secondaryPolicyHolderDob;	

	@FindBy(id = "secondary_policy_holder_ssn")
	private WebElement secondaryPolicyHolderSsn;	

	@FindBy(id = "secondary_policy_holder_phone")
	private WebElement secondaryPolicyHolderPhone;	

	@FindBy(id = "secondary_policy_holder_streetaddress")
	private WebElement secondaryPolicyHolderStreetaddress;	

	@FindBy(id = "secondary_policy_holder_city")
	private WebElement secondaryPolicyHolderCity;	

	@FindBy(id = "secondary_policy_holder_state")
	private WebElement secondaryPolicyHolderState;	

	@FindBy(id = "secondary_policy_holder_zipcode")
	private WebElement secondaryPolicyHolderZipcode;	
	
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
	
	IHGUtil.waitForElement(driver, 30, hideSecondaryInsuranceCheck);
	if (hideSecondaryInsuranceCheck.isSelected())
		hideSecondaryInsuranceCheck.click();
		
	secondaryInsuranceCompany.click();
	
	IHGUtil.waitForElement(driver, 30, btnSave);
		btnSave.click();
	// Close the browser window
	return PageFactory.initElements(driver,OtherDoctorsYouSeen.class);
}


}