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

public class AddQuestionsToCategoryPage extends BasePageObject {

	@FindBy(name = "questionText[0]")
	private WebElement dropDownQuestionText0;

	@FindBy(name = "questionLabel0[0]")
	private WebElement txtDownQuestionLabel0;

	@FindBy(name = "type[0]")
	private WebElement dropDownStdAnswerSet0;

	@FindBy(name = "questionText[1]")
	private WebElement dropDownQuestionText1;

	@FindBy(name = "questionLabel1[0]")
	private WebElement txtDownQuestionLabel1;

	@FindBy(name = "type[1]")
	private WebElement dropDownStdAnswerSet1;

	@FindBy(name = "questionText[2]")
	private WebElement dropDownQuestionText2;

	@FindBy(name = "questionLabel2[0]")
	private WebElement txtDownQuestionLabel2;

	@FindBy(name = "type[2]")
	private WebElement dropDownStdAnswerSet2;

	@FindBy(xpath = "//input[@class='html-control-text' and @value='Save Questions']")
	private WebElement btnSave;

	@FindBy(xpath = "//ul[@id='tabnav']//a[contains(@href,'../action/doQnaireLayout?method=init&sessionRequired=false')]")
	private WebElement formLayOutTab;

	@FindBy(xpath = "//ul[@id='tabnav']//a[contains(@href,'../action/doAddCategoryToQ?method=init&sessionRequired=false')]")
	private WebElement formAddCategoriesTab;


	public AddQuestionsToCategoryPage(WebDriver driver) {
		super(driver);
	}

	public boolean isSearchPageLoaded() {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);

		boolean result = false;
		try {
			result = IHGUtil.waitForElement(driver, 15, dropDownQuestionText0);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}

	public boolean addQuestion1ToCategory(String formquestion1) throws Exception {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);

		log("Select question");
		Select dropdownquestion1 = new Select(dropDownQuestionText0);
		dropdownquestion1.selectByVisibleText(formquestion1);
		dropDownQuestionText0.click();
		SitegenlUtil sutil = new SitegenlUtil(driver);
		sutil.pressTabKey();
		IHGUtil.waitForElement(driver, 30, txtDownQuestionLabel0);
		String questionLabell = txtDownQuestionLabel0.getText();
		log("Text :" + questionLabell);
		if (questionLabell.equalsIgnoreCase(formquestion1))
			return true;
		else
			return false;

	}

	public void addAnswerForQuestion1(String formanswerset1) {
		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);
		log("Select question answer.");
		Select dropdownanswer1 = new Select(dropDownStdAnswerSet0);
		dropdownanswer1.selectByVisibleText(formanswerset1);

	}

	public void saveCategoryQuestions() throws Exception {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);
		IHGUtil.waitForElement(driver, 30, btnSave);
		btnSave.click();
	}

	public CustomFormLayoutPage clickCustomFormLayoutPage() throws Exception {
		IHGUtil.waitForElement(driver, 30, formLayOutTab);
		formLayOutTab.click();
		Thread.sleep(9000);
		return PageFactory.initElements(driver, CustomFormLayoutPage.class);
	}

	public CustomFormAddCategoriesPage clickCustomFormAddCategoriesPage() throws Exception {
		IHGUtil.waitForElement(driver, 30, formAddCategoriesTab);
		formAddCategoriesTab.click();
		Thread.sleep(9000);
		return PageFactory.initElements(driver, CustomFormAddCategoriesPage.class);
	}

}
