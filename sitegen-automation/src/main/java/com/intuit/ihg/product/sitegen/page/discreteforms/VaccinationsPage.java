package com.intuit.ihg.product.sitegen.page.discreteforms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class VaccinationsPage extends BasePageObject{
	
	@FindBy(xpath="//form[@id='form_form']/div[2]/div/ul/li[8]/a/em")
	private WebElement lnkVaccination;
	
	@FindBy(id="save_config_form")              
	private WebElement btnSave;
	
	@FindBy(id = "hide_immunizations_check")
	public WebElement hideImmunizationsCheck;	

	@FindBy(id = "tetanusvaccination")
	public WebElement tetanusvaccination;	

	@FindBy(id = "hpvvaccination")
	public WebElement hpvvaccination;	

	@FindBy(id = "influeenzavaccination")
	public WebElement influeenzavaccination;	

	@FindBy(id = "pneumoniavaccination")
	public WebElement pneumoniavaccination;	

	@FindBy(id = "meningitis_immunization")
	public WebElement meningitisImmunization;	

	@FindBy(id = "pertussis_immunization")
	public WebElement pertussisImmunization;	

	@FindBy(id = "shingles_immunization")
	public WebElement shinglesImmunization;	

	@FindBy(id = "chickenpoxorvaricella_immunization")
	public WebElement chickenpoxorvaricellaImmunization;	

	@FindBy(id = "tdap_immunization")
	public WebElement tdapImmunization;	

	@FindBy(id = "hipatitisa_immunization")
	public WebElement hipatitisaImmunization;	

	@FindBy(id = "hipatitisb_immunization")
	public WebElement hipatitisbImmunization;	

	@FindBy(id = "mmr_immunization")
	public WebElement mmrImmunization;	

	@FindBy(id = "polio_immunization")
	public WebElement polioImmunization;	

	@FindBy(id = "immunizations_other")
	public WebElement immunizationsOther;	

	@FindBy(id = "immunizations_anythingelse")
	public WebElement immunizationsComments;
	
	
	public VaccinationsPage(WebDriver driver) {
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
			result = IHGUtil.waitForElement(driver, 6, lnkVaccination);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}
	
	/**
	 * Click on link - Vaccination
	 * @return
	 */
	
	public SurgeriesAndHospitalizationsPage clicklnkVaccinaion()
	{	
		SitegenlUtil.setDefaultFrame(driver);
		IHGUtil.waitForElement(driver, 30, lnkVaccination);
		lnkVaccination.click();
		
		IHGUtil.waitForElement(driver, 30, btnSave);
		btnSave.click();
		
		
		// Close the browser window
		return PageFactory.initElements(driver,SurgeriesAndHospitalizationsPage.class);
	}
}



