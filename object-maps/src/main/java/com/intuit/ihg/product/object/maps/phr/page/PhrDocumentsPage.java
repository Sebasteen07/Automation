package com.intuit.ihg.product.object.maps.phr.page;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.EnvironmentTypeUtil.EnvironmentType;
import com.intuit.ihg.common.utils.IHGUtil;

public class PhrDocumentsPage extends BasePageObject {

	@FindBy(xpath="//em[text()='Log out']")
	private WebElement btnLogout;

	@FindBy(xpath = "(//td[@class='dtein'])[1]")
	private WebElement firstDate;

	@FindBy(xpath = "id('id0')")
	private WebElement btnNewHealthInformation;

	@FindBy(xpath = ".//a[@class = 'green_button']")
	private WebElement btnViewHealthInformation;

	@FindBy(linkText = "Send my information")
	private WebElement btnShareWithDoctor;

	@FindBy(xpath = "//html/body/div[2]/form/div[1]/input[@type='text']")
	private WebElement textBoxToEnterEmailAddress;

	@FindBy(xpath = "//form/div/button")
	private WebElement btnSendToShareTheHealthInformation;

	@FindBy(xpath = "//form/div/span")
	private WebElement textBoxResponseMsg;

	@FindBy(linkText = "Close")
	private WebElement btnClose;

	@FindBy(id = "closeCcd")
	private WebElement btnCloseViewer;


	String[] myDirectAddresses = { "ihg!!!qa@service.directaddress.net",
			"ihg_qa@service.address.net", "ihg_qa@gmail.com", "ihg_qa@direct.healthvault.com" };

	String[] myDirectResponses = {
			"ihg!!!qa@service.directaddress.net is invalid. Please correct the format.",
			"Service temporarily unavailable.",
			"Service temporarily unavailable.","Service temporarily unavailable."};

	String[] myDirectResponsesDev3 = {
			"ihg!!!qa@service.directaddress.net is invalid. Please correct the format.",
			"Service temporarily unavailable.",
			"Service temporarily unavailable.","Service temporarily unavailable."};

	public PhrDocumentsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get the first data or text (date) from the table list
	 * 
	 * @return
	 */
	public String getFirstDate() {
		IHGUtil.PrintMethodName();
		return firstDate.getText();
	}

	/**
	 * Click on first date in the table list
	 * @throws Exception 
	 */
	public void clickFirstCcdInTheList() throws Exception {
		IHGUtil.PrintMethodName();
		btnNewHealthInformation.click();
		Thread.sleep(5000);
	}

	/**
	 * Click on the link 'ViewHealthInformation'
	 */
	public void clickViewHealthInformation() throws InterruptedException {
		IHGUtil.PrintMethodName();
		btnViewHealthInformation.click();
		Thread.sleep(1000);
	}

	/**
	 * Click on the link 'ShareWithADoctor'
	 */
	public void clickOnShareWithADoctor() throws InterruptedException {
		IHGUtil.PrintMethodName();
		btnShareWithDoctor.click();
	}

	/**
	 * returns webelement 'textBoxToEnterEmailAddress'
	 */
	public WebElement enterDirectAddress() throws InterruptedException {
		IHGUtil.PrintMethodName();
		return textBoxToEnterEmailAddress;
	}

	/**
	 * click on button 'SendToShareTheHealthInformation'
	 */
	public void clickOnSendToShareWithAnotherDoctor()
	throws InterruptedException {
		IHGUtil.PrintMethodName();
		btnSendToShareTheHealthInformation.click();
	}

	/**
	 * returns webelement 'textBoxResponseMsg'
	 */
	public String getResponseAfterSending() throws InterruptedException {
		IHGUtil.PrintMethodName();
		return textBoxResponseMsg.getText();
	}

	/**
	 * click on button 'Close'
	 */
	public void clickOnCloseAfterSharingTheHealthInformation()
	throws InterruptedException {
		IHGUtil.PrintMethodName();
		if (btnClose.isDisplayed()) {
			btnClose.click();
		} else {
			log("### PhrDocumentsPage.btnClose  --- Could not find Close Button");
		}
	}

	/**
	 * click on button 'CloseViewer'
	 */
	public void clickOnCloseViewer() throws InterruptedException {
		IHGUtil.PrintMethodName();
		if (btnCloseViewer.isDisplayed()) {
			btnCloseViewer.click();
		} else {
			log("### PhrDocumentsPage.btnCloseViewer --- Could not find Close Viewer");
		}
	}

	/**
	 * Enter direct Address and validate the response
	 */
	public void addAddressesAndValidate() throws InterruptedException {
		for (int i = 0; i < myDirectAddresses.length; i++) {
			enterDirectAddress().sendKeys(myDirectAddresses[i]);
			clickOnSendToShareWithAnotherDoctor();
			Thread.sleep(20000);
			String textBoxResponseMsg = getResponseAfterSending();
			Assert.assertEquals(myDirectResponses[i], textBoxResponseMsg);
			enterDirectAddress().clear();
		}
	}


	public void addAddressesAndValidateDev3() throws InterruptedException {
		for (int i = 0; i < myDirectAddresses.length; i++) {
			enterDirectAddress().sendKeys(myDirectAddresses[i]);
			clickOnSendToShareWithAnotherDoctor();
			Thread.sleep(20000);
			String textBoxResponseMsg = getResponseAfterSending();
			Assert.assertEquals(myDirectResponsesDev3[i], textBoxResponseMsg);
			enterDirectAddress().clear();
		}
	}


	/**
	 * Methods return date ("MMMM d, yyyy") pst format
	 * 
	 * @return
	 */
	public String getPstTimings() {
		Date now = new Date();
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("MMMM d, yyyy");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("PST"));
		String expectedPST = dateFormatGmt.format(now);
		return expectedPST;
	}


	/**
	 * Refresh the document Page
	 * 
	 * @param driver
	 * @throws InterruptedException
	 */
	public void refreshPage(WebDriver driver) throws InterruptedException {
		driver.navigate().refresh();
		Thread.sleep(10000);
	}

	/**
	 * 
	 * If Non Consolidated CCd then the method will just Close the Viewer
	 * Else 
	 * will click On ShareWithADoctor
	 * Will type addresses and will Validate the response
	 * CloseAfterSharingTheHealthInformation
	 * CloseViewer
	 *  
	 * @throws InterruptedException
	 */
	public void closeViewer() throws InterruptedException {
		driver.switchTo().frame(0);
		IHGUtil util=new IHGUtil(driver);
		if (util.checkCcdType() == false) {
			clickOnCloseViewer();
		}

		else if (util.checkCcdType() == true) {
			clickOnShareWithADoctor();
			if(IHGUtil.getEnvironmentType().equals(EnvironmentType.DEV3)){
				addAddressesAndValidateDev3();
				clickOnCloseAfterSharingTheHealthInformation();
				Thread.sleep(2000);
				clickOnCloseViewer();
			}
			else{
				addAddressesAndValidate();
				clickOnCloseAfterSharingTheHealthInformation();
				Thread.sleep(2000);
				clickOnCloseViewer();
			}
		}
	}


	/**
	 * Click on log out button and return PHR LogIn page
	 * @return
	 */
	public PhrLoginPage clickLogout() {

		IHGUtil.PrintMethodName();
		IHGUtil.setDefaultFrame(driver);
		btnLogout.click();
		return PageFactory.initElements(driver, PhrLoginPage.class);
	}
}
