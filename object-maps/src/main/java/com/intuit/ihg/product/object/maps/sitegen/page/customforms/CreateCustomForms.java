//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.sitegen.page.customforms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class CreateCustomForms extends BasePageObject {


	@FindBy(linkText = "Create a Custom Form")
	private WebElement lnkCreateCustomForm;

	@FindBy(xpath = "//*[contains(text(),'Manage Your Forms')]")
	private WebElement lnkManageYourForms;


	public CreateCustomForms(WebDriver driver) {
		super(driver);
	}

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

	public CreateCustomFormPage clicklnkCreateCustomForm() {
		SitegenlUtil.setDefaultFrame(driver);
		IHGUtil.waitForElement(driver, 30, lnkCreateCustomForm);
		lnkCreateCustomForm.click();
		// Close the browser window
		return PageFactory.initElements(driver, CreateCustomFormPage.class);
	}

	public ManageYourFormsPage clicklnkManageCustomForm() throws InterruptedException {
		IHGUtil.PrintMethodName();
		log("Clicking on Manage Custom Forms");
		driver.switchTo().defaultContent();
		Thread.sleep(2000);
		IHGUtil.waitForElement(driver, 50, lnkManageYourForms);
		log("Waited for link -> Manage your forms");
		try {
			lnkManageYourForms.click();
		} catch (Exception e) {
			Actions ac = new Actions(driver);
			ac.click(lnkManageYourForms).build().perform();
		}
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
		return PageFactory.initElements(driver, ManageYourFormsPage.class);
	}
}
