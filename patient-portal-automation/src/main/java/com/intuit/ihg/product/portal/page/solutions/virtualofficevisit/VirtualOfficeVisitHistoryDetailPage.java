package com.intuit.ihg.product.portal.page.solutions.virtualofficevisit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class VirtualOfficeVisitHistoryDetailPage extends BasePageObject {

	public static final String PAGE_NAME = "Virtual Office Visit History Detail Page";
	
	private static final String VALID_MESSAGE_TEXT = "I'm requesting an Virtual Office Visit.";
	
	@FindBy(xpath=".//div[@class='msgBody']/span")
	private WebElement msgBody;
	
	public VirtualOfficeVisitHistoryDetailPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Returns true if the History Details is loaded.
	 * @return true or false
	 */
	public boolean didHistoryDetailsLoad() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		
		return msgBody.getText().contains(VALID_MESSAGE_TEXT);
	}

}
