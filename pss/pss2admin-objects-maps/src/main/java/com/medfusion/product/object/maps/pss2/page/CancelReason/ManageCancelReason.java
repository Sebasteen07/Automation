//Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.CancelReason;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.product.object.maps.pss2.page.PSS2MenuPage;
import com.medfusion.product.object.maps.pss2.page.util.CommonMethods;

public class ManageCancelReason extends PSS2MenuPage {

	@FindBy(how = How.XPATH, using = "/tbody[1]/tr")
	private WebElement rowcancel;

	public ManageCancelReason(WebDriver driver) {
		super(driver);

	}

	@Override
	public boolean areBasicPageElementsPresent() {
		return true;
	}

	CommonMethods commonMethods = new CommonMethods(driver);

	public ArrayList<String> fetchCancelReasonList(WebDriver driver) {

		ArrayList<String> list = new ArrayList<String>();
		list.add("Other");
		int noofreason = driver.findElements(By.xpath("//tbody[1]/tr")).size();
		Log4jUtil.log("length " + noofreason);

		for (int i = 1; i <= noofreason; i++) {

			WebElement reasonType = driver.findElement(By.xpath("//tbody[1]/tr[" + i + "]/td[2]/span"));
			Log4jUtil.log("Reason Type" + i + " -" + reasonType.getText());

			if (reasonType.getText().contains("Cancellation")) {

				WebElement cancellationReasonAdmin = driver
						.findElement(By.xpath("//tbody[1]/tr[" + i + "]/td[1]/span"));
				commonMethods.highlightElement(cancellationReasonAdmin);
				list.add(cancellationReasonAdmin.getText());
				Log4jUtil.log("Cancellation Reason from Admin" + i + " -" + cancellationReasonAdmin.getText());
			}
		}
		return list;
	}

}
