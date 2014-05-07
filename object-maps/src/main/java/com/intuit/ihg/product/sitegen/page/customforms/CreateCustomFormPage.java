package com.intuit.ihg.product.sitegen.page.customforms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class CreateCustomFormPage extends BasePageObject {

	@FindBy(name="type")
	private WebElement dropDownFormType;

	@FindBy(name="title[0]")
	private WebElement txtFormTitle;

	@FindBy(name="instructions[0]")
	private WebElement txtFormInstruction;

	@FindBy(name="endMessage[0]")
	private WebElement txtFormEndMessage;

	@FindBy(xpath = "//input[@class='html-control-text' and @value='Save']")
	private WebElement btnSave;

	public CreateCustomFormPage(WebDriver driver) {
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
			result = IHGUtil.waitForElement(driver, 6, dropDownFormType);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}

	/**
	 * Create a custom form with below parameters
	 * @param formtype
	 * @param formtitle
	 * @param forminstructions
	 * @param formmessage
	 * @return
	 * @throws Exception
	 */
	public CustomFormAddCategoriesPage enterCustomFormDetails(String formtype, String formtitle, String forminstructions, String formmessage) throws Exception {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);

		log("Select Custom Form Type");
		Select personaltype =new Select(dropDownFormType);
		personaltype.selectByVisibleText(formtype);

		log("Enter form title");
		txtFormTitle.clear();
		txtFormTitle.sendKeys(formtitle);

		log("Enter form instructions");
		txtFormInstruction.sendKeys(forminstructions);

		log("Enter form message");
		txtFormEndMessage.sendKeys(formmessage);

		IHGUtil.waitForElement(driver, 30, btnSave);
		btnSave.click();


		return PageFactory.initElements(driver, CustomFormAddCategoriesPage.class);

	}




}
