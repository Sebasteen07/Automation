//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuti.ihg.product.object.maps.sitegen.page.onlineBillPay;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class EstatementPage extends BasePageObject {

	@FindBy(xpath = "//td[label[text()='Enable Statement Delivery']]/following-sibling::td/input")
	private WebElement chkEnableStatementDelivery;

	@FindBy(xpath = "//td[label[text()='Disable Paper Only']]/following-sibling::td/input")
	private WebElement chkDisablePaperOnly;

	@FindBy(xpath = "//td[label[text()='Allow patients to choose both ']]/following-sibling::td/input")
	private WebElement chkBothPaperAndElectronic;

	@FindBy(xpath = "//select[@name='defaultDelivery']")
	private WebElement drpDefaultDeliveryOption;

	@FindBy(xpath = "//input[@value='Submit']")
	private WebElement submitButton;
	

	public EstatementPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		// TODO Auto-generated constructor stub
	}

	public void enableStatementDelivery(String option) {

		try {
			if (option == "check" && !chkEnableStatementDelivery.isSelected()) {
				chkEnableStatementDelivery.click();
			} else if (option == "uncheck" && chkEnableStatementDelivery.isSelected()) {
				chkEnableStatementDelivery.click();
			}
		} catch (Exception e) {

		}
	}

	public void bothPaperAndElectronic(String option) {

		try {
			if (option == "check" && !chkBothPaperAndElectronic.isSelected()) {
				chkBothPaperAndElectronic.click();
			} else if (option == "uncheck" && chkBothPaperAndElectronic.isSelected()) {
				chkBothPaperAndElectronic.click();
			}
		} catch (Exception e) {

		}
	}

	public void disablePaperOnly(String option) {

		try {
			if (option == "check" && !chkDisablePaperOnly.isSelected()) {
				chkDisablePaperOnly.click();
			} else if (option == "uncheck" && chkDisablePaperOnly.isSelected()) {
				chkDisablePaperOnly.click();
			}
		} catch (Exception e) {

		}
	}
	
	public String defaultDeliveryOption(String DefaultOption) {
		Select select = new Select(drpDefaultDeliveryOption);
		select.selectByVisibleText(DefaultOption);
		return DefaultOption;
	}

	public void submitButton() throws InterruptedException {
		IHGUtil.waitForElement(driver, 20, submitButton);
		scrollAndWait(0, 500, 10);
		submitButton.click();
	}
}
