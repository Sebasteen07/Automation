package com.intuit.ihg.product.community.page;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.community.page.AskAQuestion.AskAQuestionHistory;
import com.intuit.ihg.product.community.page.AskAQuestion.AskAQuestionQuestionType;
import com.intuit.ihg.product.community.page.AskAQuestion.AskAQuestionSelectDoctor;
import com.intuit.ihg.product.community.page.BillPay.BillPayChooseYourPractice;
import com.intuit.ihg.product.community.page.MakeAppointmentRequest.AppointmentRequestSelectDoctorPage;
import com.intuit.ihg.product.community.page.MyAccount.MyAccountMenuPage;
import com.intuit.ihg.product.community.page.RxRenewal.RxRenewalChoosePrescription;
import com.intuit.ihg.product.community.page.solutions.Messages.MessagePage;
import com.intuit.ihg.product.community.utils.CommunityUtils;
import com.intuit.ihg.product.portal.page.solutions.apptRequest.AppointmentRequestStep1Page;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class CommunityHomePage extends BasePageObject {

	/**
	 * @Author:Jakub Calabek
	 * @Date:3.5.2013
	 * @User Story ID in Rally : TA18005
	 * @StepsToReproduce:
	 * 
	 */

	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Help Center')]")
	public WebElement btn_Help_Center;

	@FindBy(how = How.XPATH, using = "//a[contains(text(),'My Account')]")
	public WebElement btn_My_Account;

	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Sign Out')]")
	public WebElement btn_Sign_Out;

	@FindBy(how = How.XPATH, using = ".//*[@id='frame']/ul/li[1]/a")
	public WebElement icon_Messages;

	@FindBy(how = How.XPATH, using = ".//*[@id='frame']/ul/li[3]/a")
	public WebElement icon_Bill_Payment;

	@FindBy(how = How.XPATH, using = ".//*[@id='frame']/ul/li[5]/a")
	public WebElement icon_Ask_A_Question;

	@FindBy(how = How.XPATH, using = ".//*[@id='frame']/ul/li[2]/a")
	public WebElement icon_Appointments;

	@FindBy(how = How.XPATH, using = ".//*[@id='frame']/ul/li[4]/a")
	public WebElement icon_Prescriptions;

	@FindBy(how = How.XPATH, using = ".//*[@id='frame']/ul/li[6]/a")
	public WebElement icon_Health_Forms;

	@FindBy(how = How.XPATH, using = "//a[contains(text(),'View all messages')]")
	public WebElement link_View_All_Messages;

	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Make a payment')]")
	public WebElement link_Make_A_Payment;

	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Payment history')]")
	public WebElement link_Payment_History;

	@FindBy(how = How.XPATH, using = "//a[contains(text(),'New question')]")
	public WebElement link_Ask_New_Question;

	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Question history')]")
	public WebElement link_Question_History;

	@FindBy(how = How.XPATH, using = "//a[contains(text(),'New appointment request')]")
	public WebElement link_New_Appointment_Request;

	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Appointment history')]")
	public WebElement link_Appointment_History;

	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Renewal request')]")
	public WebElement link_Renewal_Request;

	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Renewal history')]")
	public WebElement link_Renewal_History;

	@FindBy(how = How.XPATH, using = "//a[contains(text(),'View forms')]")
	public WebElement link_View_Forms;

	// @FindBy(how = How.XPATH, using = "//h2[contains(text(),'Success')]")
	@FindBy(how = How.XPATH, using = ("//div[@class='notifications successMsg']"))
	public WebElement successNotificationMessage;

	public CommunityHomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);

	}

	// Checks Notification Message display on Home Page

	public boolean checkSuccesNotification(WebDriver driver) {

		IHGUtil.waitForElement(driver, 60, successNotificationMessage);
		return successNotificationMessage.isDisplayed();
	}

	// Checks if the Messages link appears in Home Page
	public boolean isViewallmessagesLinkPresent(WebDriver driver)
			throws InterruptedException {
		IHGUtil.PrintMethodName();	
		Assert.assertEquals(
				"### It seems Community may be down at this moment .... Community Title what we ",
				CommunityUtils.PAGE_TITLE_INTUIT_HEALTH, driver.getTitle()
						.trim());
		IHGUtil.waitForElement(driver, 60, link_View_All_Messages);
		return link_View_All_Messages.isDisplayed();
		
	}

	// Clicks on Messages link
	public MessagePage clickMessages() throws InterruptedException {

		IHGUtil.PrintMethodName();
		driver.findElement(By.xpath("//a[contains(text(),'View all messages')]")).click();
		return PageFactory.initElements(driver, MessagePage.class);
	}

	// Log out
	public CommunityLoginPage logOutCommunity() throws InterruptedException {

		btn_Sign_Out.click();

		return PageFactory.initElements(driver, CommunityLoginPage.class);
	}

	// Clicks on New Appointment request link in Home Page
	public AppointmentRequestSelectDoctorPage clickAppointmentRequest()
			throws InterruptedException {

		IHGUtil.PrintMethodName();
		link_New_Appointment_Request.click();
		return PageFactory.initElements(driver,
				AppointmentRequestSelectDoctorPage.class);
	}

	// Clicks on New Question link in Home Page
	public AskAQuestionQuestionType clickAskAQuestion()
			throws InterruptedException {

		IHGUtil.PrintMethodName();
		link_Ask_New_Question.click();
		return PageFactory.initElements(driver, AskAQuestionQuestionType.class);
	}
	
	// Clicks on Ask a Question History link in Home Page
	public AskAQuestionHistory clickAskAQuestionHistorylink()
			throws InterruptedException {

		IHGUtil.PrintMethodName();
		link_Question_History.click();
		return PageFactory.initElements(driver, AskAQuestionHistory.class);
	}
	
	// Clicks on New Question link in Home Page
	public BillPayChooseYourPractice clickBillPay()
			throws InterruptedException {

		IHGUtil.PrintMethodName();
		link_Make_A_Payment.click();
		return PageFactory.initElements(driver, BillPayChooseYourPractice.class);
	}
	
	// Clicks on Renewal Request link in Home Page
	public RxRenewalChoosePrescription  clickRxRenewal()
			throws InterruptedException {
		IHGUtil.PrintMethodName();
		link_Renewal_Request.click();
		return PageFactory.initElements(driver, RxRenewalChoosePrescription.class);
	}
	
	// Clicks on My Account link in Home Page
	public MyAccountMenuPage  clickMyAccount()
			throws InterruptedException {
		IHGUtil.PrintMethodName();
		btn_My_Account.click();
		return PageFactory.initElements(driver, MyAccountMenuPage.class);
	}
}