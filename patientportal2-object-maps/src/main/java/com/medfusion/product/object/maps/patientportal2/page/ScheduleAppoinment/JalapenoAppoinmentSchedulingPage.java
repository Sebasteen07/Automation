package com.medfusion.product.object.maps.patientportal2.page.ScheduleAppoinment;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;

public class JalapenoAppoinmentSchedulingPage extends JalapenoMenu {

	private static final String EXTERNAL_URL = "https://www.medfusion.com/";
	private static final String PATIENT_PORTAL_URL = "https://dev3.dev.medfusion.net/jalapenoautomat-24109/portal/#/dashboard";

	@FindBy(how = How.ID, using = "actionButton")
	private WebElement continueButton;

	@FindBy(how = How.XPATH, using = "//h4[text()='Schedule an Appointment']")
	private WebElement scheduledanappoinmentheader;

	@FindBy(how = How.XPATH, using = "//div[@id='viewModalContent']/div/div/p")
	private WebElement popupmessage;

	@FindBy(how = How.XPATH, using = "//button[@class='close']")
	private WebElement closepopupmessage;

	public JalapenoAppoinmentSchedulingPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(continueButton);
		webElementsList.add(scheduledanappoinmentheader);
		webElementsList.add(popupmessage);
		webElementsList.add(closepopupmessage);
		return assessPageElements(webElementsList);
	}

	public void clickOnContinueButton() {
		IHGUtil.waitForElement(driver, 80, continueButton);
		javascriptClick(continueButton);
	}

	public boolean isScheduledAnAppoinmentPopUpDisplay() {

		try {
			log("Looking for scheduled an appoinment pop up");
			IHGUtil.waitForElement(driver, 40, scheduledanappoinmentheader);
			return scheduledanappoinmentheader.isDisplayed();
		} catch (Exception e) {
			log("Scheduled an appoinment pop up is not display");
			return false;
		}
	}

	public boolean isPopUpMessageDisplay() {

		try {
			log("Looking for pop up message");
			IHGUtil.waitForElement(driver, 40, popupmessage);
			return popupmessage.isDisplayed();
		} catch (Exception e) {
			log("pop up message is not display");
			return false;
		}
	}

	public boolean isClosePopUpMessageDisplay() {

		try {
			log("Looking for exist portal message on sso warning pop up");
			IHGUtil.waitForElement(driver, 40, closepopupmessage);
			return closepopupmessage.isDisplayed();
		} catch (Exception e) {
			log("exist portal message is not display");
			return false;
		}
	}

	public boolean isNewTabOpenDestinationUrl(WebDriver driver) {
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		String childURL = driver.getCurrentUrl();
		if (childURL.equalsIgnoreCase(EXTERNAL_URL)) {
			log("External_URL is same as Destination URL");
		} else {
			log("External_URL is not same as Destination URL");
		}
		driver.switchTo().window(tabs.get(0));
		String parentURL = driver.getCurrentUrl();
		if (parentURL.equalsIgnoreCase(PATIENT_PORTAL_URL)) {
			log("Parent url is same as patient portal url");
		} else {
			log("Parent url is same as patient portal url");
		}
		return true;
	}

}
