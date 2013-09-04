package com.intuit.ihg.product.sitegen.page.customforms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class CustomFormLayoutPage  extends BasePageObject {

	@FindBy(name="pageSeq[0]")
	private WebElement dropDownPageSeq;

	@FindBy(xpath = "//input[@class='html-control-text' and @value='Save']")
	private WebElement btnSave;    

	//This page to contain all validation points passed during form creation.
	public CustomFormLayoutPage(WebDriver driver) {
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
			result = IHGUtil.waitForElement(driver, 15, dropDownPageSeq);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}

	/**
	 * Enter form layout page number
	 * @param formpage
	 * @param formcategory
	 * @return
	 * @throws Exception
	 */
	public CustomFormPreviewPage addFormLayout(String formpage,String formcategory) throws Exception {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);

		log("Enter form layout page number");
		Select dropdownquestion1 =new Select(dropDownPageSeq);
		dropdownquestion1.selectByVisibleText(formpage);
		IHGUtil.waitForElement(driver, 30, btnSave)		;
		btnSave.click();

		return PageFactory.initElements(driver, CustomFormPreviewPage.class);

	}

}
