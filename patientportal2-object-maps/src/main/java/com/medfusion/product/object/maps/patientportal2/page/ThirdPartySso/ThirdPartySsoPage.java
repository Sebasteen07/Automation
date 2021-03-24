package com.medfusion.product.object.maps.patientportal2.page.ThirdPartySso;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;

public class ThirdPartySsoPage extends JalapenoMenu {

	private static final String THIRD_PARTY_SSO_URL = "https://www.medfusion.com/";
	private static final String PATIENT_PORTAL_URL = "https://dev3.dev.medfusion.net/jalapenoautomat-24109/portal/#/dashboard";

	@FindBy(how = How.ID, using = "continueButton")
	private WebElement continueButton;

	@FindBy(how = How.XPATH, using = "//span[text()='You are leaving Medfusion']")
	private WebElement ssowarningbanner;

	@FindBy(how = How.XPATH, using = "//div[@class='destination']/span[2]")
	private WebElement destinationurl;

	@FindBy(how = How.XPATH, using = "//span[text()='You are now leaving ']")
	private WebElement existportalmessage;

	public ThirdPartySsoPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();

	}

	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(continueButton);
		webElementsList.add(ssowarningbanner);
		webElementsList.add(destinationurl);
		webElementsList.add(existportalmessage);
		return true;
	}

	public void clickOnContinueButton() {
		IHGUtil.waitForElement(driver, 80, continueButton);
		javascriptClick(continueButton);
	}

	public boolean isLeavingMedfusionBannerDisplay() {

		try {
			log("Looking for sso warning pop up");
			IHGUtil.waitForElement(driver, 40, ssowarningbanner);
			return ssowarningbanner.isDisplayed();
		} catch (Exception e) {
			log("sso warning banner is not display");
			return false;
		}
	}

	public boolean isDestinationUrlDisplay() {

		try {
			log("Looking for Destination URL on sso warning pop up");
			IHGUtil.waitForElement(driver, 40, destinationurl);
			return destinationurl.isDisplayed();
		} catch (Exception e) {
			log("Destination url is not display");
			return false;
		}
	}

	public boolean isExistPortalMessageDisplay() {

		try {
			log("Looking for exist portal message on sso warning pop up");
			IHGUtil.waitForElement(driver, 40, existportalmessage);
			return existportalmessage.isDisplayed();
		} catch (Exception e) {
			log("exist portal message is not display");
			return false;
		}
	}

	public boolean isNewTabOpenDestinationUrl(WebDriver driver) {
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		String childURL = driver.getCurrentUrl();
		if (childURL.equalsIgnoreCase(THIRD_PARTY_SSO_URL)) {
			log("SSO URL is same as Destination URL");
		} else {
			log("SSO URL is not same as Destination URL");
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
