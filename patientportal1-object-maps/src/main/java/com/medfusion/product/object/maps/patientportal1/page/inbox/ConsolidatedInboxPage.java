package com.medfusion.product.object.maps.patientportal1.page.inbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.BaseTestSoftAssert;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.patientportal1.utils.PortalUtil;

public class ConsolidatedInboxPage extends BasePageObject {

	public static final String PAGE_NAME = "Consolidated Inbox Page";

	@FindBy(id = "inboxTab")
	private WebElement inboxTab;

	@FindBy(xpath = ".//tr[@id='id5b']/td[3]/span")
	private List<WebElement> inboxMessageTableList;

	@FindBy(xpath = ".//div[@id='msgInboxList']/div[@class='slick-viewport']//div[@class='slick-cell l2 r2']")
	public WebElement inboxMessageList;

	@FindBy(xpath = ".//table[@id='msgInboxList']//td[4]/span")
	private List<WebElement> inboxDateList;

	@FindBy(xpath = ".//div[@class='grid-canvas']/div/div[3]")
	private WebElement inboxMessage;

	@FindBy(xpath = ".//div[@id='msgInboxList']/div[5]/div[1]/div[1]/div[1]/div[@class='msgMessage']")
	private WebElement firstMessageRow;

	@FindBy(xpath = "//button[@id='refreshList']")
	private WebElement Refreshbutton;

	@FindBy(xpath = "//button[@id='newButton']")
	private WebElement btnNew;

	@FindBy(id = "showInboxTabs")
	private WebElement threeTabbedSolution;

	public ConsolidatedInboxPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Gives indication if the Inbox page loaded correctly
	 * 
	 * @return true or false
	 */
	public boolean isInboxLoaded() {
		IHGUtil.PrintMethodName();
		PortalUtil.setConsolidatedInboxFrame(driver);

		boolean result = false;
		try {
			result = inboxTab.isDisplayed();
		} catch (Exception e) {
			// Catch no element found error
		}

		return result;
	}

	/**
	 * Search inbox and open message based on unique string in message subject
	 * 
	 * @param uniqueSubString used to find specific message in Inbox
	 * @return Consolidated Inbox Message or null if no message is found
	 * @throws InterruptedException
	 */
	public ConsolidatedInboxMessage openMessageInInbox(String uniqueSubString) throws InterruptedException {
		IHGUtil.PrintMethodName();
		PortalUtil.setConsolidatedInboxFrame(driver);
		Thread.sleep(8000);
		if (uniqueSubString != null && !uniqueSubString.isEmpty()) {
			if (inboxMessageList.getText().contains(uniqueSubString)) {
				inboxMessageList.click();
				// return PageFactory.initElements(driver,
				// ConsolidatedInboxMessage.class);
			}
		}

		return PageFactory.initElements(driver, ConsolidatedInboxMessage.class);

		/*
		 * try { PortalUtil.setConsolidatedInboxFrame(driver); if (uniqueSubString != null && !uniqueSubString.isEmpty()) { if
		 * (inboxMessageList.getText().contains(uniqueSubString)) { inboxMessageList.click(); //return PageFactory.initElements(driver,
		 * ConsolidatedInboxMessage.class); } }
		 * 
		 * return PageFactory.initElements(driver, ConsolidatedInboxMessage.class); } catch(Exception e) { IHGUtil.setFrame(driver, "iframebody");
		 * clickMessageLink(); Thread.sleep(5000); clickOfficeMessagesLink(); Thread.sleep(10000); PortalUtil.setConsolidatedInboxFrame(driver); if (uniqueSubString
		 * != null && !uniqueSubString.isEmpty()) { for (WebElement message : inboxMessageTableList) { if (message.getText().contains(uniqueSubString)) {
		 * message.click(); System.out.println( "@#@#@#@#@# The Email exhists in patient portal with a subject:" +message.getText());
		 * 
		 * return PageFactory.initElements(driver, ConsolidatedInboxMessage.class); } } } return null; }
		 */
	}

	public boolean findUnreadMessage(String sSubject) throws InterruptedException {

		IHGUtil.PrintMethodName();
		PortalUtil.setConsolidatedInboxFrame(driver);
		Thread.sleep(8000);
		boolean unreadMessageFound = false;

		// Looking for the unread messages with specific which can be
		// located by specific CSS selector
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		List<WebElement> message = driver.findElements(By.cssSelector(".slick-row.unread"));

		for (WebElement webElement : message) {

			if (webElement.getText().contains(sSubject)) {
				unreadMessageFound = true;
			}
		}
		// Setting back to 30 as it is default value
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return unreadMessageFound;
	}

	/**
	 * Search inbox and open message based on unique string in message subject
	 * 
	 * @param uniqueSubString used to find specific message in Inbox
	 * @return Consolidated Inbox Message or null if no message is found
	 * @throws InterruptedException
	 */
	public ConsolidatedInboxMessage clickMessageLinkOpenMessageInInbox(String uniqueSubString) throws InterruptedException {

		IHGUtil.PrintMethodName();
		try {
			PortalUtil.setConsolidatedInboxFrame(driver);
			IHGUtil.waitForElement(driver, 300, inboxMessage);
			inboxMessage.click();
			Thread.sleep(10000);
			return PageFactory.initElements(driver, ConsolidatedInboxMessage.class);
		} catch (Exception e) {
			IHGUtil.setFrame(driver, "iframebody");
			clickMessageLink();
			Thread.sleep(5000);
			clickOfficeMessagesLink();
			Thread.sleep(10000);
			PortalUtil.setConsolidatedInboxFrame(driver);
			List<Object> list =
					IHGUtil.searchResultTable(driver, "//table[@id='MfAjaxFallbackDefaultDataTable']/tbody", new ArrayList<String>(Arrays.asList(uniqueSubString)));
			if (!list.isEmpty()) {
				BaseTestSoftAssert.assertTrue(((Boolean) list.get(1)).booleanValue());
				driver
						.findElement(By.xpath("//table[@id='MfAjaxFallbackDefaultDataTable']/tbody".concat("/tr[" + Integer.parseInt(list.get(0).toString()) + "]/td[3]")))
						.click();
			} else {
				BaseTestSoftAssert.assertTrue(false, "Message Not Found");
			}
			return PageFactory.initElements(driver, ConsolidatedInboxMessage.class);
		}

	}

	public void clickMessageLink() {
		driver.findElement(By.xpath("//div[@id='inboxLink']/a")).click();
	}

	public void clickOfficeMessagesLink() {
		driver.findElement(By.xpath("//div[@id='inboxTabs']/input[@value='Office Messages']")).click();
	}

	/**
	 * Open the first mail in the inbox
	 * 
	 * @return
	 */
	public ConsolidatedInboxMessage clickFirstMessageRow() {
		firstMessageRow.click();
		return PageFactory.initElements(driver, ConsolidatedInboxMessage.class);
	}

	public void ClickRefreshButton() {
		try {
			Refreshbutton.click();
			System.out.println("###Refresh button found");
		} catch (Exception ex) {
			System.out.println("### WARNING: Refresh button not found (probably old template)");
		}

	}

	public void ClickNewButton() {
		try {
			IHGUtil.setFrame(driver, "iframebody");
			btnNew.click();
			System.out.println("###The New button found");
		} catch (Exception ex) {
			System.out.println("### WARNING: The New button not found (probably old template)");
		}

	}

	public ThreeTabbedSolutionPage clickThreeTabbedSolution() {

		try {
			IHGUtil.setFrame(driver, "iframebody");
			threeTabbedSolution.click();
			System.out.println("###The Link was found");
		} catch (Exception ex) {
			System.out.println("### WARNING: The Link not found (CI not enabled)");
		}

		return PageFactory.initElements(driver, ThreeTabbedSolutionPage.class);
	}

}
