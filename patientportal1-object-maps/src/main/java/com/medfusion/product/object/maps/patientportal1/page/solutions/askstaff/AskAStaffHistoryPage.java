package com.medfusion.product.object.maps.patientportal1.page.solutions.askstaff;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.patientportal1.utils.PortalUtil;

public class AskAStaffHistoryPage extends BasePageObject {

	public static final String PAGE_NAME = "Ask A Staff History Page";

	@FindBy(xpath = ".//table[@id='MfAjaxFallbackDefaultDataTable']/tbody/tr/td[3]/span/table/tbody/tr/td[2]/span")
	private List<WebElement> askAStaffHistory;

	public AskAStaffHistoryPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Given a unique value this method will validate that the question is listed on the History page
	 * 
	 * @return boolean
	 */
	public boolean isAskAStaffOnHistoryPage(String subjectSearchString) {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);

		for (WebElement item : askAStaffHistory) {
			if (item.getText().contains(subjectSearchString)) {
				return true;
			}
		}

		return false;
	}

}
