package com.intuit.ihg.product.object.maps.sitegen.page.discreteforms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class MedicationsPage extends BasePageObject{
	
	@FindBy(xpath="//li[@data-section='allergies']/a")
	private WebElement lnkAllergies;
	
	@FindBy(id="save_config_form")              
	private WebElement btnSave;
	
	public MedicationsPage(WebDriver driver) 
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
			result = IHGUtil.waitForElement(driver, 6, lnkAllergies);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}
	
	/**
	 * Click on link - Allergies	
	 * @return
	 */
	
	public Allergiespage clicklnkAllergies()
	{	
		SitegenlUtil.setDefaultFrame(driver);
		IHGUtil.waitForElement(driver, 30, lnkAllergies);
		lnkAllergies.click();
		return PageFactory.initElements(driver,Allergiespage.class);
	}
	
}
