//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.sitegen.page.customforms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class CustomFormAddCategoriesPage extends BasePageObject {

	@FindBy(name = "categoryList")
	private WebElement dropDowncategoryList;

	@FindBy(xpath = "//input[@name='staffAccess[0]' and @value='W']")
	private WebElement radioBtnPracticeAdministratorAccess;

	@FindBy(xpath = "//input[@name='staffAccess[1]' and @value='W']")
	private WebElement radioBtnGroupSiteAdministratorAccess;

	@FindBy(xpath = "//input[@name='staffAccess[2]' and @value='W']")
	private WebElement radioBtnPractitionerAccess;

	@FindBy(xpath = "//input[@name='staffAccess[3]' and @value='N']")
	private WebElement radioBtnManagerAccess;

	@FindBy(xpath = "//input[@name='staffAccess[4]' and @value='R']")
	private WebElement radioBtnPhysicianAssistantsAccess;

	@FindBy(xpath = "//input[@name='staffAccess[5]' and @value='R']")
	private WebElement radioBtnNursesAccess;

	@FindBy(xpath = "//input[@class='html-control-text' and @value='Save']")
	private WebElement btnSave;

	public CustomFormAddCategoriesPage(WebDriver driver) {
		super(driver);
	}

	public boolean isSearchPageLoaded() {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);

		boolean result = false;
		try {
			result = IHGUtil.waitForElement(driver, 15, dropDowncategoryList);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}

	public AddQuestionsToCategoryPage addCategoriesDetails(String formcategory) throws Exception {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);

		log("Select Custom Form Category");
		Select personaltype = new Select(dropDowncategoryList);
		personaltype.selectByVisibleText(formcategory);

		log("Give staff permission to Practice Administrator");
		radioBtnPracticeAdministratorAccess.click();

		log("Give staff permission to Group Site Administrator");
		radioBtnGroupSiteAdministratorAccess.click();

		log("Give staff permission to Practitioner");
		radioBtnPractitionerAccess.click();

		log("Give staff permission to Manager");
		radioBtnManagerAccess.click();

		log("Give staff permission to physician Assistant");
		radioBtnPhysicianAssistantsAccess.click();

		log("Give staff permission to Nurse");
		radioBtnNursesAccess.click();

		IHGUtil.waitForElement(driver, 30, btnSave);
		btnSave.click();

		return PageFactory.initElements(driver, AddQuestionsToCategoryPage.class);
	}
}

