//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.sitegen.page.Integrations;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class ViewIntegrationsPage extends BasePageObject {

	@FindBy(xpath = "(//a[contains(@href, '../ie/CreateIntegrationPage')])[2]")
	private WebElement lnkCreateIntegration;

	@FindBy(xpath = "(//fieldset/table/tbody/tr[2]/td[1]")
	private WebElement vTextIntegrationName;

	@FindBy(xpath = "(//fieldset/table/tbody/tr[2]/td[2]")
	private WebElement vTextIntegrationId;

	@FindBy(linkText = "Delete")
	private WebElement lnkDelete;

	@FindBy(xpath = "//input[@value='Yes, Delete This Integration']")
	private WebElement btnConfirmDelete;

	@FindBy(xpath = "//input[@value='Cancel']")
	private WebElement btnCancel;

	@FindBy(xpath = "//table[contains(@class,'dataview clear intengine-permissions')]")
	private WebElement tableIntegrationsInformation;

	@FindBy(xpath = "//table[contains(@class,'dataview clear intengine-permissions')]/tbody/tr")
	private WebElement tableRowIntegrationsInformation;

	public final String table_row = "//table[contains(@class,'dataview clear intengine-permissions')]/tbody/tr";

	public ViewIntegrationsPage(WebDriver driver) {
		super(driver);
	}

	public boolean isSearchPageLoaded() {
		IHGUtil.PrintMethodName();

		boolean result = false;
		try {
			result = lnkCreateIntegration.isDisplayed();
		} catch (Exception e) {
			// Catch any element not found errors
		}
		return result;
	}

	public CreateIntegrationStep1Page clickLnkCreateIntegration() throws InterruptedException {
		IHGUtil.PrintMethodName();
		lnkCreateIntegration.click();
		return PageFactory.initElements(driver, CreateIntegrationStep1Page.class);
	}

	public boolean verifyIfIntegrationsEngineIsAdded(String integrationName) {

		int flag = 0;
		List<WebElement> tr_collection = tableIntegrationsInformation.findElements(By.xpath(table_row));

		System.out.println("NUMBER OF ROWS IN THIS TABLE = " + tr_collection.size());
		int row_num, col_num;
		row_num = 1;
		for (WebElement trElement : tr_collection) {
			List<WebElement> td_collection = trElement.findElements(By.xpath("td"));
			System.out.println("NUMBER OF COLUMNS=" + td_collection.size());
			col_num = 2;
			for (WebElement tdElement : td_collection) {
				System.out.println("row # " + row_num + ", col # " + col_num + "text=" + tdElement.getText());

				if (tdElement.getText().equals(integrationName)) {
					flag = flag + 1;
				}
				col_num++;
			}
			row_num++;
		}
		if (flag > 0) {
			return true;
		}
		return false;
	}

	public void cleanIntegrationTestData() {
		IHGUtil.PrintMethodName();
		IHGUtil util = new IHGUtil(driver);
		try {

			while (util.isRendered(lnkDelete)) {
				lnkDelete.click();
				WebDriverWait wait = new WebDriverWait(driver, 2);
				wait.until(ExpectedConditions.visibilityOf(btnCancel));
				btnConfirmDelete.click();
			}

			log("Removed all IntegrationEngine and clean the test data");
		} catch (org.openqa.selenium.NoSuchElementException c) {
			log("The test data was already clean");
		} catch (Exception e) {
			log("The test data was already clean");
		}
	}
}
