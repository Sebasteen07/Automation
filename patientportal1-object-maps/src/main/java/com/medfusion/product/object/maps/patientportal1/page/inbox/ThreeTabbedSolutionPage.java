package com.medfusion.product.object.maps.patientportal1.page.inbox;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class ThreeTabbedSolutionPage extends BasePageObject {

	@FindBy(name = "allMessages")
	private WebElement allMessages;

	@FindBy(name = "officeMessages")
	private WebElement officeMessages;

	@FindBy(name = "healthMessages")
	private WebElement healthMessages;

	public ThreeTabbedSolutionPage(WebDriver driver) {
		super(driver);
	}

	public ConsolidatedInboxPage clickAllMessages() {

		try {
			IHGUtil.setFrame(driver, "iframebody");
			allMessages.click();
			System.out.println("###The Link was found");
		} catch (Exception ex) {
			System.out.println("### WARNING: The Link not found");
		}

		return PageFactory.initElements(driver, ConsolidatedInboxPage.class);
	}

	public OfficeMessagesPage clickOfficeMessages() {

		try {
			IHGUtil.setFrame(driver, "iframebody");
			officeMessages.click();
			System.out.println("###The Link was found");
		} catch (Exception ex) {
			System.out.println("### WARNING: The Link not found");
		}

		return PageFactory.initElements(driver, OfficeMessagesPage.class);
	}
	
	public void clickHealthMessages() {

		try {
			IHGUtil.setFrame(driver, "iframebody");
			healthMessages.click();
			System.out.println("###The Link was found");
		} catch (Exception ex) {
			System.out.println("### WARNING: The Link not found");
		}

		//return PageFactory.initElements(driver, ConsolidatedInboxPage.class);
	}

}
