package com.intuit.ihg.product.object.maps.sitegen.page.customforms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class CustomFormPreviewPage  extends BasePageObject {
	
	private final String publishLinkText = "Publish";
	
	private final String unpublishLinkText = "Unpublish";
	
	private final int waitTimeout = 15;
	
	@FindBy(linkText=publishLinkText)
	private WebElement publishLink;
	
	@FindBy(linkText=unpublishLinkText)
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
			result = IHGUtil.waitForElement(driver, waitTimeout, publishLink);
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
			result = IHGUtil.waitForElement(driver, waitTimeout, unPublishLink);
			} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}
	public void waitForPublishLink() throws InterruptedException{
		log("Waiting for Publish link");		
		SitegenlUtil.setSiteGenFrame(driver);
		try{			
			IHGUtil.waitForLinkByText(driver, publishLinkText, waitTimeout);
		}
		catch(Exception e){
			e.printStackTrace();
			throw new InterruptedException("Publish link not found, taking too long, or something very bad happened");
		}
		log("Publish link found successfuly");
	}
	public void waitForUnpublishLink() throws InterruptedException{	
		log("Waiting for any Unpublish link");
		
		try{			
			IHGUtil.waitForLinkByText(driver, unpublishLinkText, waitTimeout);
		}
			catch(Exception e){
			e.printStackTrace();
			throw new InterruptedException("Unpublish link not found, taking too long, or something very bad happened");
		}
		log("Unpublish link found successfuly");
	}
	/**
	 * Click on publish link
	 * @return
	 * @throws Exception
	 */	
	public ManageYourFormsPage clickOnPublishLink() throws Exception {

		IHGUtil.PrintMethodName();
		log("Click on Publish link");
		IHGUtil.waitForElement(driver, waitTimeout, publishLink);
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
		IHGUtil.waitForElement(driver, waitTimeout, unPublishLink);
		unPublishLink.click();
		return PageFactory.initElements(driver, ManageYourFormsPage.class);
	}

	
}
