package com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.ConfiguratorFormPage;
import com.medfusion.common.utils.IHGUtil;

public class HealthInsuranceInformationPage extends ConfiguratorFormPage {

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

	@FindBy(id = "hide_insurance_check")
	private WebElement chckHideInsurance;

	@FindBy(id = "save_config_form")
	private WebElement btnSave;

	@FindBy(xpath = "//li[input[@id='primary_insurance_company']]/a")
	private WebElement primaryInsuranceCompanyAsterisk;


	public HealthInsuranceInformationPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Click on Insurance company name checkbox and make it optional to fill
	 */

	public void selectInsuranceCompanyQuestion() {
		makeInsurancePageAppear();
		if (primaryInsuranceCompany.isSelected() == false)
			primaryInsuranceCompany.click();
		if (primaryInsuranceCompanyAsterisk.isSelected())
			primaryInsuranceCompanyAsterisk.click();
	}

	/**
	 * Unselects the hide this page checkbox
	 */

	public void makeInsurancePageAppear() {
		IHGUtil.waitForElement(driver, 30, hideInsuranceCheck);
		if (hideInsuranceCheck.isSelected())
			hideInsuranceCheck.click();
	}

}
