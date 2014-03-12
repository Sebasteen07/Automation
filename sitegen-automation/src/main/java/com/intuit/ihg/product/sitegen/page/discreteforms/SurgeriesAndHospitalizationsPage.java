package com.intuit.ihg.product.sitegen.page.discreteforms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class SurgeriesAndHospitalizationsPage extends BasePageObject{
	
	@FindBy(xpath="//li[@data-section='procedures_section']/a")
	private WebElement lnkProcedures;
	
	@FindBy(id="save_config_form")              
	private WebElement btnSave;
	
	@FindBy(id = "hide_surgerieshospitalizations_section_check")
	private WebElement hideSurgerieshospitalizationsSectionCheck;	

	@FindBy(id = "surgerieshospitalizations_anythingelse_line")
	private WebElement surgerieshospitalizationsCommentsCheck;
	
	
	public SurgeriesAndHospitalizationsPage(WebDriver driver) 
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
			result = IHGUtil.waitForElement(driver, 6, lnkProcedures);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}
	
	/**
	 * Click on link - Exams and Procedures
	 * @return
	 */
	
	public ExamsTestsAndProceduresPage clicklnkProcedures()
	{	
		SitegenlUtil.setDefaultFrame(driver);
		IHGUtil.waitForElement(driver, 30, lnkProcedures);
		lnkProcedures.click();
		return PageFactory.initElements(driver, ExamsTestsAndProceduresPage.class);
	}
}

