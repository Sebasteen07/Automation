package com.intuit.ihg.product.object.maps.sitegen.page.customforms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class CustomFormPreviewPage  extends BasePageObject {
	@FindBy(linkText="Publish")
	private WebElement publishLink;
	
	@FindBy(linkText="Unpublish")
	private WebElement unPublishLink;
		
	
	//This page to contain all validation points passed during form creation.
	public CustomFormPreviewPage(WebDriver driver) {
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
		SitegenlUtil.setDefaultFrame(driver);
		
		boolean result = false;
		try {
			SitegenlUtil.setSiteGenFrame(driver);
			result = IHGUtil.waitForElement(driver, 15, publishLink);
			} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}
	
	
	/**
	 * Indicates if the search page is loaded
	 * 
	 * @return true or false
	 */
	public boolean isSearchPageLoadedForUnpublishLink() {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);
		
		boolean result = false;
		try {
			SitegenlUtil.setSiteGenFrame(driver);
			result = IHGUtil.waitForElement(driver, 15, unPublishLink);
			} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}
	
	/**
	 * Click on publish link
	 * @return
	 * @throws Exception
	 */	
	public ManageYourFormsPage clickOnPublishLink() throws Exception {

		IHGUtil.PrintMethodName();
		log("Click on Publish link");
		IHGUtil.waitForElement(driver, 30, publishLink);
		publishLink.click();
		return PageFactory.initElements(driver, ManageYourFormsPage.class);
	}

	/**
	 * Click on Unpublish link
	 * @return
	 * @throws Exception
	 */
	public ManageYourFormsPage clickOnUnPublishLink() throws Exception {

		IHGUtil.PrintMethodName();
		log("Click on Unpublish link");
		SitegenlUtil.setSiteGenFrame(driver);
		IHGUtil.waitForElement(driver, 30, unPublishLink);
		unPublishLink.click();
		return PageFactory.initElements(driver, ManageYourFormsPage.class);
	}


	
}
