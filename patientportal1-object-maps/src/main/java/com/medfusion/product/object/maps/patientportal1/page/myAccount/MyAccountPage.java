package com.medfusion.product.object.maps.patientportal1.page.myAccount;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.IHGUtil.Gender;
import com.medfusion.product.object.maps.patientportal1.page.MyPatientPage;
import com.medfusion.product.object.maps.patientportal1.page.PortalLoginPage;
import com.medfusion.product.object.maps.patientportal1.page.myAccount.AccountActivity.ViewAccountActivityPage;
import com.medfusion.product.object.maps.patientportal1.page.myAccount.familyAccount.PatientsOnThisAccountPage;
import com.medfusion.product.object.maps.patientportal1.page.myAccount.insurance.InsurancePage;
import com.medfusion.product.object.maps.patientportal1.page.myAccount.manageHealthInfo.ManageHealthInfoPage;
import com.medfusion.product.object.maps.patientportal1.page.myAccount.preferences.PreferencesPage;
import com.medfusion.product.object.maps.patientportal1.page.myAccount.wallet.WalletPage;
import com.medfusion.product.patientportal1.utils.PortalUtil;

/**
 * 
 * @author bkrishnankutty
 * 
 */
public class MyAccountPage extends BasePageObject {

	public static final String PAGE_NAME = "My Account Page";

	@FindBy(xpath = "//a[contains(@href, 'home.cpanel')]")
	private WebElement lnkPreferences;

	@FindBy(xpath = "//a[contains(@href, 'home.family')]")
	private WebElement lnkFamily;

	@FindBy(xpath = "//a[contains(@href, 'home.wallet')]")
	private WebElement lnkWallet;

	@FindBy(linkText = "Manage Health Information")
	private WebElement lnkManageHealthInfo;

	@FindBy(linkText = "My Patient Page")
	private WebElement lnkMyPatientPage;

	@FindBy(xpath = "//a[contains(@href, 'ins.details')]")
	private WebElement insuranceLink;

	@FindBy(name = "inputs:10:input:input")
	private WebElement txtZipCode;

	@FindBy(name = "inputs:11:input:input")
	private WebElement txtHomePhone;

	@FindBy(name = "buttons:submit")
	private WebElement btnSubmit;

	@FindBy(css = "a[href*='exit.cfm']")
	private WebElement logout;

	@FindBy(name = "inputs:8:input:input")
	private WebElement txtCity;

	@FindBy(name = "inputs:6:input:input")
	private WebElement txtAddress1;

	@FindBy(name = "inputs:7:input:input")
	private WebElement txtAddress2;

	@FindBy(linkText = "Account Activity")
	private WebElement lnkAccountActivity;


	// @FindBy (xpath = ".//form[@id='editForm']/table/tbody/tr[20]/td[2]/select[@name='inputs:19:input:input']/option")
	// private WebElement preferredCommunicationMethod;

	@FindBy(xpath = "//select[@name='inputs:23:input:input']")
	private WebElement chooseCommunicationDropDrown;
	
	@FindBy(xpath = "//select[@name='inputs:16:input:input']")
	private WebElement chooseGenderIdentityDropDown;

	@FindBy(xpath = "//select[@name='inputs:18:input:input']")
	private WebElement chooseSexualOrientationDropDown;

	@FindBy(name = "inputs:2:input:input")
	private WebElement txtFirstName;

	@FindBy(name = "inputs:3:input:input")
	private WebElement txtMiddleName;

	@FindBy(name = "inputs:4:input:input")
	private WebElement txtLastName;

	@FindBy(name = "inputs:5:input:input")
	private WebElement preferredLanguageDropDown;

	@FindBy(name = "inputs:9:input:input")
	private WebElement stateDropDown;

	@FindBy(name = "inputs:12:input:input")
	private WebElement txtMobilePhone;

	@FindBy(name = "inputs:13:input:input")
	private WebElement txtWorkPhone;

	@FindBy(name = "inputs:14:input:input")
	private WebElement dateOfBirth;

	@FindBy(xpath = "//span[@fieldid='gender']//input[./following-sibling::label[text()='Male']]")
	private WebElement genderMaleRadio;

	@FindBy(xpath = "//span[@fieldid='gender']//input[./following-sibling::label[text()='Female']]")
	private WebElement genderFemaleRadio;

	@FindBy(name = "inputs:20:input:input")
	private WebElement raceDropDown;

	@FindBy(name = "inputs:21:input:input")
	private WebElement ethnicityDropDown;

	@FindBy(name = "inputs:22:input:input")
	private WebElement maritalStatusDropDown;
	
	public MyAccountPage(WebDriver driver) {
		super(driver);
	}

	public void waitforPreferencesLink(WebDriver driver, int n) {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, n, lnkPreferences);
	}

	public PreferencesPage clickpreferencesLink() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		waitforPreferencesLink(driver, 60);
		lnkPreferences.click();
		return PageFactory.initElements(driver, PreferencesPage.class);
	}

	public MyPatientPage clickMyPatientPage() {
		IHGUtil.PrintMethodName();
		PortalUtil.setDefaultFrame(driver);
		IHGUtil.waitForElement(driver, 60, lnkMyPatientPage);
		lnkMyPatientPage.click();
		return PageFactory.initElements(driver, MyPatientPage.class);
	}


	public PatientsOnThisAccountPage clickfamilyLink() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver, 60, lnkFamily);
		lnkFamily.click();
		return PageFactory.initElements(driver, PatientsOnThisAccountPage.class);
	}

	public WalletPage clickWalletLink() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver, 60, lnkWallet);
		lnkWallet.click();
		return PageFactory.initElements(driver, WalletPage.class);
	}

	public InsurancePage addInsuranceLink() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);

		waitforPreferencesLink(driver, 60);
		insuranceLink.click();
		return PageFactory.initElements(driver, InsurancePage.class);
	}



	public ManageHealthInfoPage clickManageHealthInfoLink() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver, 60, lnkManageHealthInfo);
		lnkManageHealthInfo.click();

		return PageFactory.initElements(driver, ManageHealthInfoPage.class);
	}

	public ViewAccountActivityPage addAccountActivityLink() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		waitforPreferencesLink(driver, 60);
		lnkAccountActivity.click();
		return PageFactory.initElements(driver, ViewAccountActivityPage.class);
	}



	/**
	 * The method will modify data in the portal site
	 * 
	 * @param secondayCity
	 * @param secondaryzip
	 */

	public void modifyCityAndZip(String city, String zip) {
		IHGUtil.PrintMethodName();
		log("Setting city to " + city);
		log("Setting Zip to " + zip);
		PortalUtil.setPortalFrame(driver);
		txtCity.clear();
		txtCity.sendKeys(city);
		txtZipCode.clear();
		txtZipCode.sendKeys(zip);
	}

	public void modifyMobilePhone(String phoneNumber) {
		IHGUtil.PrintMethodName();
		log("Setting Phone number to " + phoneNumber);
		PortalUtil.setPortalFrame(driver);
		txtMobilePhone.clear();
		txtMobilePhone.sendKeys(phoneNumber);
	}

	public void modifyHomePhone(String phoneNumber) {
		IHGUtil.PrintMethodName();
		log("Setting Phone number to " + phoneNumber);
		PortalUtil.setPortalFrame(driver);
		txtHomePhone.clear();
		txtHomePhone.sendKeys(phoneNumber);
	}

	public void modifyAndSubmitAddressLines(String firstLine, String secondLine) {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		txtAddress1.clear();
		txtAddress1.sendKeys(firstLine);
		txtAddress2.clear();
		txtAddress2.sendKeys(secondLine);
		this.submit();
	}

	/**
	 * Logout from the Application
	 * 
	 * @param driver
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public void logout(WebDriver driver) throws InterruptedException, IOException {
		IHGUtil.PrintMethodName();
		PortalLoginPage portalLoginPage = new PortalLoginPage(driver);
		portalLoginPage.clickLogout(driver, logout);
	}

	/**
	 *
	 * The method asserts patient data in the portal site After modification in the PHR site
	 * 
	 * @param city
	 * @param zip
	 */
	public void assertDataCityAndZip(String city, String zip) {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		log("Value of City: " + txtCity.getAttribute("value"));
		log("Value of Zip: " + txtZipCode.getAttribute("value"));
		Assert.assertEquals(txtCity.getAttribute("value"), city, "The City is not updated in PHR ##### ");
		Assert.assertEquals(txtZipCode.getAttribute("value"), zip, "The Zip code is not updated in PHR  ##### ");
	}

	public void assertMobilePhoneNumber(String phoneNumber) {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		String portalMobileNumber = txtMobilePhone.getAttribute("value");
		log("Value of Mobile Phone number: " + portalMobileNumber);

		Assert.assertEquals(portalMobileNumber.replaceAll("\\D", ""), phoneNumber, "The Mobile Phone number is not updated in PHR ##### ");
	}

	public void assertHomePhoneNumber(String phoneNumber) {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		String portalHomeNumber = txtHomePhone.getAttribute("value");
		log("Value of Home Phone number: " + portalHomeNumber);

		Assert.assertEquals(portalHomeNumber.replaceAll("\\D", ""), phoneNumber, "The Home Phone number is not updated in PHR ##### ");
	}


	public void chooseCommunicationMethod(String pCommunicationOption) {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		List<WebElement> list = driver.findElements(By.xpath("//select[@name='inputs:19:input:input']/option"));
		for (WebElement li : list) {
			int count = 1;
			if (li.getText().contains(pCommunicationOption)) {
				Select selectProvider = new Select(chooseCommunicationDropDrown);
				selectProvider.selectByIndex(count);
				break;
			}
			count++;
		}
		IHGUtil.waitForElement(driver, 10, btnSubmit);
		this.submit();
	}

	/**
	 * 
	 * @param list
	 */
	public void fillPatientDetails(List<String> list) {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		txtFirstName.clear();
		txtFirstName.sendKeys(list.get(0));
		txtMiddleName.clear();
		txtMiddleName.sendKeys(list.get(10));
		txtLastName.clear();
		txtLastName.sendKeys(list.get(1));
		txtAddress1.clear();
		txtAddress1.sendKeys(list.get(3));
		txtAddress2.clear();
		txtAddress2.sendKeys(list.get(4));
		txtCity.clear();
		txtCity.sendKeys(list.get(5));
		txtZipCode.clear();
		txtZipCode.sendKeys(list.get(6));
		txtHomePhone.clear();
		txtHomePhone.sendKeys(list.get(2));
		txtMobilePhone.clear();
		txtMobilePhone.sendKeys(list.get(11));
		txtWorkPhone.clear();
		txtWorkPhone.sendKeys(list.get(12));
		dateOfBirth.clear();
		dateOfBirth.sendKeys(list.get(13));
		Select dropDownElement = new Select(preferredLanguageDropDown);
		dropDownElement.selectByVisibleText(list.get(20));

		Select stateDropDownElement = new Select(stateDropDown);
		stateDropDownElement.selectByVisibleText(list.get(25));

		Select raceDropDownElement = new Select(raceDropDown);
		raceDropDownElement.selectByVisibleText(list.get(21));

		Select ethnicityDropDownElement = new Select(ethnicityDropDown);
		ethnicityDropDownElement.selectByVisibleText(list.get(22));

		Select maritalStatusDropDownElement = new Select(maritalStatusDropDown);
		maritalStatusDropDownElement.selectByVisibleText(list.get(23));

		Select chooseCommunicationDropDrownElement = new Select(chooseCommunicationDropDrown);
		chooseCommunicationDropDrownElement.selectByVisibleText(list.get(24));
		this.submit();

	}

	/**
	 * Count the no of values of the drop down
	 * 
	 * @param key
	 * @return
	 */
	public int countDropDownValue(char key) {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		Select select = null;
		int size = 0;
		switch (key) {
			case 'R':
				select = new Select(raceDropDown);
				break;
			case 'E':
				select = new Select(ethnicityDropDown);
				break;
			case 'L':
				select = new Select(preferredLanguageDropDown);
				break;
			case 'M':
				select = new Select(maritalStatusDropDown);
				break;
			case 'C':
				select = new Select(chooseCommunicationDropDrown);
				break;
			case 'G':
				select = new Select(chooseGenderIdentityDropDown);
				break;
			case 'S':
				select = new Select(chooseSexualOrientationDropDown);
				break;
			default:
				break;
		}
		List<WebElement> element = select.getOptions();
		size = element.size();

		return size;

	}

	/**
	 * Update the values of dropdown
	 * 
	 * @param i
	 * @param key
	 * @return
	 */
	public String updateDropDownValue(int i, char key) {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		Select select = null;
		String changeValue = null;
		switch (key) {
			case 'R':
				select = new Select(raceDropDown);
				break;
			case 'E':
				select = new Select(ethnicityDropDown);
				break;
			case 'L':
				select = new Select(preferredLanguageDropDown);
				break;
			case 'M':
				select = new Select(maritalStatusDropDown);
				break;
			case 'C':
				select = new Select(chooseCommunicationDropDrown);
				break;
			case 'G':
				select = new Select(chooseGenderIdentityDropDown);
				break;
			case 'S':
				select = new Select(chooseSexualOrientationDropDown);
				break;
			default:

				break;
		}
		select.selectByIndex(i);
		WebElement option = select.getFirstSelectedOption();
		changeValue = option.getText();
		this.submit();
		return changeValue;
	}

	/**
	 * 
	 * @param updateData
	 */
	public void updateDemographics(List<String> updateData) {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		txtFirstName.clear();
		txtFirstName.sendKeys(updateData.get(0));
		txtMiddleName.clear();
		txtMiddleName.sendKeys(updateData.get(10));
		txtLastName.clear();
		txtLastName.sendKeys(updateData.get(1));
		txtAddress1.clear();
		txtAddress1.sendKeys(updateData.get(2));
		txtAddress2.clear();
		txtAddress2.sendKeys(updateData.get(3));
		txtHomePhone.clear();
		txtHomePhone.sendKeys(updateData.get(4));
		dateOfBirth.clear();
		dateOfBirth.sendKeys(updateData.get(5));

		Select raceDropDownElement = new Select(raceDropDown);
		raceDropDownElement.selectByVisibleText(updateData.get(7));

		Select ethnicityDropDownElement = new Select(ethnicityDropDown);
		ethnicityDropDownElement.selectByVisibleText(updateData.get(8));
		this.submit();
	}

	public String getDOB() throws Exception {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		return dateOfBirth.getAttribute("value");
	}

	/**
	 * Verify patient details in the My Account page of the Patient Portal
	 * 
	 * @param patientData
	 */
	public void verifyPatientDemographicsOnMyAccount(List<String> patientData) {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		Assert.assertTrue(txtFirstName.getAttribute("value").toString().contains(patientData.get(1)),
				"Actual Patient First Name is" + txtFirstName.getAttribute("value").toString());
		Assert.assertTrue(txtMiddleName.getAttribute("value").toString().contains(patientData.get(2)),
				"Actual Patient Middle Name is" + txtMiddleName.getAttribute("value").toString());
		Assert.assertTrue(txtLastName.getAttribute("value").toString().contains(patientData.get(3)),
				"Actual Patient Last Name is" + txtLastName.getAttribute("value").toString());
		Assert.assertTrue(txtAddress1.getAttribute("value").toString().contains(patientData.get(4)),
				"Actual Patient Address1 Name is" + txtAddress1.getAttribute("value").toString());
		Assert.assertTrue(txtAddress2.getAttribute("value").toString().contains(patientData.get(5)),
				"Actual Patient Address2 Name is" + txtAddress2.getAttribute("value").toString());

	}

	public void submit() {
		btnSubmit.click();
		Assert.assertTrue(driver.getPageSource().contains("Your Profile has been updated"), "New values didnt get updated");
	}

	/**
	 * 
	 * @return proper gender or null if not specified
	 */
	public Gender getGender() {
		PortalUtil.setPortalFrame(driver);
		if (genderMaleRadio.isSelected())
			return Gender.MALE;
		if (genderFemaleRadio.isSelected())
			return Gender.FEMALE;
		return null;
	}
}
