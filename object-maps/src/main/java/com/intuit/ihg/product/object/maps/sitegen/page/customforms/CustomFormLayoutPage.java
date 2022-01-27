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

public class CustomFormLayoutPage extends BasePageObject {

	@FindBy(name = "pageSeq[0]")
	private WebElement dropDownPageSeq0;

	@FindBy(name = "pageSeq[1]")
	private WebElement dropDownPageSeq1;

	@FindBy(name = "pageSeq[2]")
	private WebElement dropDownPageSeq2;

	@FindBy(xpath = "//input[@class='html-control-text' and @value='Save']")
	private WebElement btnSave;

	@FindBy(xpath = "/html/body/table/tbody/tr[2]/td[2]/form/table/tbody/tr[2]/td[1]")
	private WebElement firstRow;

	@FindBy(xpath = "/html/body/table/tbody/tr[2]/td[2]/form/table/tbody/tr[3]/td[1]")
	private WebElement secondRow;

	@FindBy(xpath = "/html/body/table/tbody/tr[2]/td[2]/form/table/tbody/tr[4]/td[1]")
	private WebElement thirdRow;

	@FindBy(name = "categorySeq[1]")
	private WebElement secondSeq;

	@FindBy(name = "categorySeq[2]")
	private WebElement thirdSeq;


	// This page to contain all validation points passed during form creation.
	public CustomFormLayoutPage(WebDriver driver) {
		super(driver);
	}

	public boolean isSearchPageLoaded() {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);

		boolean result = false;
		try {
			result = IHGUtil.waitForElement(driver, 15, dropDownPageSeq2);
		} catch (Exception e) {
			// Catch any element not found errors
		}
		return result;
	}

	public void addFormLayout(String formpage, String formcategory) throws Exception {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);

		log("Enter form layout page number");
		if (firstRow.getText().equals(formcategory)) {
			Select dropdownquestion1 = new Select(dropDownPageSeq0);
			dropdownquestion1.selectByVisibleText(formpage);
		}

		if (secondRow.getText().equals(formcategory)) {
			Select dropdownquestion1 = new Select(dropDownPageSeq1);
			dropdownquestion1.selectByVisibleText(formpage);
		}

		if (thirdRow.getText().equals(formcategory)) {
			Select dropdownquestion1 = new Select(dropDownPageSeq2);
			dropdownquestion1.selectByVisibleText(formpage);
		}
	}

	public void categorySequence() throws Exception {
		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);

		Select dropdownseq1 = new Select(secondSeq);
		dropdownseq1.selectByVisibleText("1");

		Select dropdownseq2 = new Select(thirdSeq);
		dropdownseq2.selectByVisibleText("2");
	}

	public CustomFormPreviewPage saveFormLayout() throws Exception {
		IHGUtil.waitForElement(driver, 30, btnSave);
		btnSave.click();

		return PageFactory.initElements(driver, CustomFormPreviewPage.class);
	}
}
