package com.intuit.ihg.product.sitegen.page.customforms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class CreateCustomForms extends BasePageObject{
	
	
	@FindBy(linkText="Create a Custom Form")
	private WebElement lnkCreateCustomForm;
	
	@FindBy( xpath = "//a[contains(@href,'../action/doManageQuestionnaire?method')]")
	private WebElement lnkManageYourForms;
	
	

	public CreateCustomForms(WebDriver driver) {
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
			result = IHGUtil.waitForElement(driver, 6, lnkCreateCustomForm);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}
	
	/**
	 * Click on link - Create custom form	
	 * @return
	 */
	
	public CreateCustomFormPage clicklnkCreateCustomForm()
	{	
		SitegenlUtil.setDefaultFrame(driver);
		IHGUtil.waitForElement(driver, 30, lnkCreateCustomForm);
		lnkCreateCustomForm.click();
		// Close the browser window
		return PageFactory.initElements(driver,CreateCustomFormPage.class);
	}
	
	
	

	/**
	 * * @author Shanthala
	 * @Desc:- click on Manage your forms
	 * @return ViewIntegrationsPage
	 * @throws InterruptedException
	 * 
	 */	
	public ManageYourFormsPage clicklnkManageCustomForm() throws InterruptedException {

		log("Clicking on Custom Forms");
		driver.switchTo().defaultContent();
		IHGUtil.waitForElement(driver, 30, lnkManageYourForms);
		try{
			lnkManageYourForms.click();
		} catch(Exception e) {
			Actions ac = new Actions(driver);
			ac.click(lnkManageYourForms).build().perform();
		}	
		for(String winHandle : driver.getWindowHandles()){
			driver.switchTo().window(winHandle);
		}

		return PageFactory.initElements(driver, ManageYourFormsPage.class);

	}


}
