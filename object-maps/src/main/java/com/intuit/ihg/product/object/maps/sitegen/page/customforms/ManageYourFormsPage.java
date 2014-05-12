package com.intuit.ihg.product.object.maps.sitegen.page.customforms;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class ManageYourFormsPage  extends BasePageObject {


	@FindBy(xpath=".//fieldset//strong[text() = 'Published Forms']")
	private List<WebElement> publishedtableRows ;

	@FindBy(xpath=".//fieldset//strong[text() = 'Unpublished Forms']")
	private List<WebElement> unpublishedtableRows ;
	
	@FindBy( xpath = "//a[@class='action' and text() = 'Publish']")
	private WebElement xpath_publishLinks;

	public ManageYourFormsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	/**
	 * @author bbinisha
	 * Indicates if the search page is loaded
	 * 
	 * @return true or false
	 */
	public boolean isSearchPageLoaded() {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);
		boolean result = false;
		try {
			result = IHGUtil.waitForElementInDefaultFrame(driver, 30, publishedtableRows.get(0));
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}


	/**
	 * @author bbinisha
	 * Indicates if the search page is loaded
	 * 
	 * @return true or false
	 */
	public boolean isSearchPageLoadedForUnpublishedTable() {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);

		boolean result = false;
		try {
			result = IHGUtil.waitForElement(driver, 15, unpublishedtableRows.get(0));
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}


	/**
	 * @author : bbinihsa
	 * Verify that published custom form is present in Manage Your Forms ->Published Table
	 * @param formTitle
	 * @return true or false
	 * @throws Exception
	 */	
	public boolean checkForPublishedPage(String formTitle) throws Exception {

		IHGUtil.PrintMethodName();
//		driver.switchTo().defaultContent();

		Boolean isPresent = false;
		String xpath_Published = ".//fieldset//strong[text() = 'Published Forms']/ancestor::fieldset/table/tbody/tr[@class='dark-row' or @class='light-row' ]";
		List<WebElement> allList = driver.findElements(By.xpath(xpath_Published));
		for (WebElement list : allList) {
			try {
				if(list.findElement(By.className("html-control-label-left")).getText().contains(formTitle)) {
					isPresent=  true;
					break;
				}else {
					isPresent = false;
					continue;
				}
			}catch(Exception e) {
				log("Couldn't find the "+formTitle);
			}
		}
		return isPresent;
	}


	/**
	 * @author bbinisha
	 * verify that published form can be unpublished by clicking on 'Unpublish' link
	 * @param formTitle
	 * @return true or false
	 * @throws Exception
	 */
	public void unPublishThepublishedForm(String formTitle) throws Exception {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);
		log("Check for published custom form is present in Published Form table and unpublish it");

		String xpath_Published = "//fieldset//strong[text() = 'Published Forms']/ancestor::fieldset/table/tbody/tr[@class='dark-row' or @class='light-row' ]";
		List<WebElement> allList = driver.findElements(By.xpath(xpath_Published));
		for (WebElement list : allList) {
			try {
				if(list.findElement(By.className("html-control-label-left")).getText().contains(formTitle)) {
					list.findElement(By.linkText("Unpublish")).click();
					driver.switchTo().alert().accept();	
				}
			}catch(Exception e) {
				//Do Nothing
			}
		}

	}

	/**
	 * @author bbinisha
	 * @param formTitle
	 * @return
	 */
	public boolean isUnPublished(String formTitle) {
		Boolean isPresent = false;
		String xpath_Published = "//fieldset//strong[text() = 'Published Forms']/ancestor::fieldset/table/tbody/tr[@class='dark-row' or @class='light-row' ]";
		List<WebElement> allList = driver.findElements(By.xpath(xpath_Published));
		for (WebElement list : allList) {
			try {
				if(list.findElement(By.className("html-control-label-left")).getText().contains(formTitle)) {
					if(list.findElement(By.linkText("Unpublish"))==null){
						isPresent= true;
					}else{
						isPresent = false;
					}
				}
			}catch(Exception e) {
				//Do nothing
			}
		}	
		return isPresent;
	}



	/**
	 * @author bbinisha
	 * Verify that unpublished form deleted by clicking on 'Delete' link
	 * @param formTitle
	 * @return true or false
	 * @throws Exception
	 */    
	public void deleteUnpublishedForm(String formTitle) throws Exception {
		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);
		String xpath_UnPublished = "//fieldset//strong[text() = 'Unpublished Forms']/ancestor::fieldset/table/tbody/tr[@class='dark-row' or @class='light-row' ]";
		List<WebElement> allList = driver.findElements(By.xpath(xpath_UnPublished));
		for (WebElement list : allList) {
			try {
				if(list.findElement(By.className("html-control-label-left")).getText().contains(formTitle)) {
					list.findElement(By.linkText("Delete")).click();
					driver.switchTo().alert().accept();
				}else{
					continue;
				}
			} catch(Exception e) {
				//Do nothing
			}
		}
	}



	/**
	 * @author bbinisha
	 * Verify that 'Preview' has been clicked for published form
	 * @param formTitle
	 * @return true or false
	 * @throws Exception
	 */    
	public CustomFormPreviewPage clickOnPublishedFormPreviewLink(String formTitle) throws Exception {
		IHGUtil.PrintMethodName();
//		SitegenlUtil.setDefaultFrame(driver);
		log("Check for published custom form is present in UnPublished Form table and then click on delete link ");
		String xpath_Published = "//fieldset//strong[text() = 'Published Forms']/ancestor::fieldset/table/tbody/tr[@class='dark-row' or @class='light-row' ]";
		
		List<WebElement> allList = driver.findElements(By.xpath(xpath_Published));
		for (WebElement list : allList) {
			try {
				if(list.findElement(By.className("html-control-label-left")).getText().contains(formTitle)) {
					list.findElement(By.linkText("Preview")).click();
					log("clicked on preview");
				}else {
					continue;
				}
			} catch(Exception e) {
				//Do nothing
			}
		}
		return PageFactory.initElements(driver, CustomFormPreviewPage.class);
	}


	/** 
	 * @author bbinisha
	 * Verify that 'Preview' has been clicked for unpublished form
	 * @param formTitle
	 * @return true or false
	 * @throws Exception
	 */    
	public CustomFormPreviewPage clickOnUnpublishedFormPreviewLink(String formTitle) throws Exception {
		IHGUtil.PrintMethodName();
//		SitegenlUtil.setDefaultFrame(driver);
		log("Check for published custom form is present in UnPublished Form table and then click on delete link ");

		String xpath_UnPublished = "//fieldset//strong[text() = 'Unpublished Forms']/ancestor::fieldset/table/tbody/tr[@class='dark-row' or @class='light-row' ]";
		List<WebElement> allList = driver.findElements(By.xpath(xpath_UnPublished));
		for (WebElement list : allList) {
			try {
				if(list.findElement(By.className("html-control-label-left")).getText().contains(formTitle)) {
					list.findElement(By.linkText("Preview")).click();
					log("clicked on preview");
				}else {
					continue;
				}
			} catch(Exception e) {
				//Do nothing
			}
		}
		Thread.sleep(5000);
		return PageFactory.initElements(driver, CustomFormPreviewPage.class);
	}

	/** 
	 * @author bbinisha
	 * Verify that 'Preview' has been clicked for unpublished form
	 * @param formTitle
	 * @return true or false
	 * @throws Exception
	 */    
	public void clickOnUnpublishedFormPublishLink(String formTitle) throws Exception {
		IHGUtil.PrintMethodName();
//		SitegenlUtil.setDefaultFrame(driver);
		log("Check for published custom form is present in UnPublished Form table and then click on delete link ");

		String xpath_UnPublished = "//fieldset//strong[text() = 'Unpublished Forms']/ancestor::fieldset/table/tbody/tr[@class='dark-row' or @class='light-row' ]";
		List<WebElement> allList = driver.findElements(By.xpath(xpath_UnPublished));
		for (WebElement list : allList) {
			try {
				if(list.findElement(By.className("html-control-label-left")).getText().contains(formTitle)) {
					list.findElement(By.linkText("Publish")).click();
					log("clicked on preview");
				}else {
					continue;
				}
			} catch(Exception e) {
				//Do nothing
			}
		}
	}

}
