package com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;
import com.medfusion.common.utils.IHGUtil;

public class BasicInformationAboutYouPage extends BasePageObject {

	public BasicInformationAboutYouPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(xpath = "//li[@data-section='demographics']/a")
	private WebElement lnkBasicInformationAboutYou;

	@FindBy(xpath = "//li[@data-section='emergencycontact']/a")
	private WebElement lnkEmergencyContactInformation;

	@FindBy(id = "save_config_form")
	private WebElement btnSave;

	@FindBy(id = "streetaddr1")
	private WebElement chckAddress;

	@FindBy(id = "city")
	private WebElement chckCity;

	@FindBy(id = "state")
	private WebElement chckState;

	@FindBy(id = "postalcode")
	private WebElement chckZIP;

	@FindBy(id = "primaryphone")
	private WebElement chckPhoneNumber;

	@FindBy(id = "gender")
	private WebElement chckGender; // sex

	/**
	 * Indicates if the search page is loaded
	 * 
	 * @return true or false
	 */
	public boolean isSearchPageLoaded() {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setSiteGenFrame(driver);

		boolean result = false;
		try {
			result = IHGUtil.waitForElement(driver, 6, lnkBasicInformationAboutYou);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}

	/**
	 * Click on link - Basic Information About You
	 */

	public void clicklnkBasicInfoAboutYourPage() {
		SitegenlUtil.setDefaultFrame(driver);
		IHGUtil.waitForElement(driver, 30, lnkBasicInformationAboutYou);
		lnkBasicInformationAboutYou.click();
	}

	/**
	 * Select basic info about the patient to appear Address items, phone number, gender
	 */

	public void selectBasicInfo() {
		// wait for the element to load
		IHGUtil.waitForElement(driver, 30, chckAddress);

		chckAddress.click();
		chckCity.click();
		chckState.click();
		chckZIP.click();
		chckPhoneNumber.click();
		chckGender.click();
	}

	/**
	 * Selects more attributes for patient information
	 */
	public void selectAdditionalInfo() {
		selectBasicInfo();

	}

	/**
	 * Click on next page, which is Emergency contact page
	 * 
	 * @return PageFactory initialization of EmergencyContactPage class
	 * @throws InterruptedException
	 */

	public EmergencyContactInformationPage clickLnkEmergency() throws InterruptedException {
		scrollAndWait(0, 0, 500);
		lnkEmergencyContactInformation.click();
		return PageFactory.initElements(driver, EmergencyContactInformationPage.class);
	}

	public void clickSave() {
		btnSave.click();
	}

}
