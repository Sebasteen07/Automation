package com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class VaccinationsPage extends BasePageObject{
	
	@FindBy(xpath="//li[@data-section='surgerieshospitalizations_section']/a")
	private WebElement lnkSurgsHosps;
	
	@FindBy(id="save_config_form")              
	private WebElement btnSave;
	
	@FindBy(id = "hide_immunizations_check")
	private WebElement hideImmunizationsCheck;	

	@FindBy(id = "tetanusvaccination")
	private WebElement tetanusvaccination;	

	@FindBy(id = "hpvvaccination")
	private WebElement hpvvaccination;	

	@FindBy(id = "influeenzavaccination")
	private WebElement influeenzavaccination;	

	@FindBy(id = "pneumoniavaccination")
	private WebElement pneumoniavaccination;	

	@FindBy(id = "meningitis_immunization")
	private WebElement meningitisImmunization;	

	@FindBy(id = "pertussis_immunization")
	private WebElement pertussisImmunization;	

	@FindBy(id = "shingles_immunization")
	private WebElement shinglesImmunization;	

	@FindBy(id = "chickenpoxorvaricella_immunization")
	private WebElement chickenpoxorvaricellaImmunization;	

	@FindBy(id = "tdap_immunization")
	private WebElement tdapImmunization;	

	@FindBy(id = "hipatitisa_immunization")
	private WebElement hipatitisaImmunization;	

	@FindBy(id = "hipatitisb_immunization")
	private WebElement hipatitisbImmunization;	

	@FindBy(id = "mmr_immunization")
	private WebElement mmrImmunization;	

	@FindBy(id = "polio_immunization")
	private WebElement polioImmunization;	

	@FindBy(id = "immunizations_other")
	private WebElement immunizationsOther;	

	@FindBy(id = "immunizations_anythingelse")
	private WebElement immunizationsComments;
	
	
	public VaccinationsPage(WebDriver driver)
	{
		super(driver);
		// TODO Auto-generated constructor stub
	}

	
	
	/**
	 * Indicates if the search page is loaded
	 * 
	 * @return true or false
	 */
	public boolean isSearchPageLoaded()
	{

		IHGUtil.PrintMethodName();
		SitegenlUtil.setSiteGenFrame(driver);

		boolean result = false;
		try {
			result = IHGUtil.waitForElement(driver, 6, lnkSurgsHosps);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}
	
	/**
	 * Click on link - Surgeries and Hospitalizations
	 * @return
	 */
	
	public SurgeriesAndHospitalizationsPage clicklnkSurgsHosps()
	{	
		SitegenlUtil.setDefaultFrame(driver);
		IHGUtil.waitForElement(driver, 30, lnkSurgsHosps);
		lnkSurgsHosps.click();
		return PageFactory.initElements(driver, SurgeriesAndHospitalizationsPage.class);
	}
}



