package com.intuit.ihg.product.portal.page.myAccount;

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
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.portal.page.myAccount.AccountActivity.ViewAccountActivityPage;
import com.intuit.ihg.product.portal.page.myAccount.familyAccount.PatientsOnThisAccountPage;
import com.intuit.ihg.product.portal.page.myAccount.insurance.InsurancePage;
import com.intuit.ihg.product.portal.page.myAccount.manageHealthInfo.ManageHealthInfoPage;
import com.intuit.ihg.product.portal.page.myAccount.preferences.PreferencesPage;
import com.intuit.ihg.product.portal.page.myAccount.wallet.WalletPage;
import com.intuit.ihg.product.portal.utils.PortalUtil;

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

	@FindBy(xpath = "//a[contains(@href, 'ins.details')]")
	private WebElement insuranceLink;

	@FindBy(name = "inputs:10:input:input")
	private WebElement txtZipCode;

	@FindBy(name = "inputs:11:input:input")
	private WebElement txtHomePhone;

	@FindBy(name = "buttons:submit")
	private WebElement btnSubmit;

	@FindBy(css="a[href*='exit.cfm']")
	private WebElement logout;

	@FindBy(name = "inputs:8:input:input")
	private WebElement txtCity;

	@FindBy(name = "inputs:6:input:input")
	private WebElement txtAddress1;
	
	@FindBy(name = "inputs:7:input:input")
	private WebElement txtAddress2;
	
	@FindBy(linkText = "Account Activity")
	private WebElement lnkAccountActivity;
	

//	@FindBy (xpath = ".//form[@id='editForm']/table/tbody/tr[20]/td[2]/select[@name='inputs:19:input:input']/option")
//	private WebElement preferredCommunicationMethod;
	
	@FindBy(xpath = "//select[@name='inputs:19:input:input']")
	private WebElement chooseCommunicationDropDrown;

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

	public PatientsOnThisAccountPage clickfamilyLink() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver, 60, lnkFamily);
		lnkFamily.click();
		return PageFactory
		.initElements(driver, PatientsOnThisAccountPage.class);
	}

	public WalletPage clickWalletLink() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver, 60, lnkWallet);
		lnkWallet.click();
		return PageFactory.initElements(driver, WalletPage.class);
	}

	public ManageHealthInfoPage clickManageHealthInfoLink() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver, 60, lnkManageHealthInfo);
		lnkManageHealthInfo.click();

		return PageFactory.initElements(driver, ManageHealthInfoPage.class);
	}

	public InsurancePage addInsuranceLink() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);

		waitforPreferencesLink(driver, 60);
		insuranceLink.click();
		return PageFactory.initElements(driver, InsurancePage.class);
	}
	
	
	
	
	public ViewAccountActivityPage addAccountActivityLink() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		waitforPreferencesLink(driver, 60);
		lnkAccountActivity.click();
		return PageFactory.initElements(driver, ViewAccountActivityPage.class);
	}
	
	

	/**
	 * The method compare data in the Portal with excel sheet.
	 * If not proper, will update and force the script to fail 
	 * 
	 * @param city [the data is from Portal sheet]
	 * @param zip [the data is from Portal sheet]
	 */

	public void  assertDataWithExcel(String primarycity,String primaryzip)
	{
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		if((!txtCity.getAttribute("value").equals(primarycity)) || (!txtZipCode.getAttribute("value").equals(primaryzip)))
		{
			log("##### Warning :- The test data city and Zip code is not in sink with that in Excel ##### ");
			log("##### Script will automatically upadate data ##### ");
			log("##### Re-Run your Script. Best of luck! #####");

			String strCity=txtCity.getAttribute("value").toString();
			log("###########strCity :-"+strCity);
			String stryZip=txtZipCode.getAttribute("value").toString();
			log("###########stryZip :-"+stryZip);

			txtCity.clear();
			log("###########City :-"+primarycity);
			txtCity.sendKeys(primarycity);
			txtZipCode.clear();
			log("###########zip :-"+primaryzip);
			txtZipCode.sendKeys(primaryzip);
			btnSubmit.click();
			Assert.assertTrue(driver.getPageSource().contains("Your Profile has been updated"), "The values City and zip code doesnt got updated###");

			//force Test case failuare
			Assert.assertEquals(strCity,primarycity, "The test data City is not in sink with that in Excel ##### ");
			Assert.assertEquals(stryZip, primaryzip, "The test data zip code is not in sink with that in Excel ##### ");
		}
	}

	/**
	 * The method will modify data in the portal site with
	 * secondary User Data ie from PHR sheet
	 * 
	 * @param secondayCity [the data is from PHR sheet]
	 * @param secondaryzip [the data is from PHR sheet]
	 */

	public void modifyCityAndZip(String secondayCity, String secondaryzip) {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		txtCity.clear();
		txtCity.sendKeys(secondayCity);
		txtZipCode.clear();
		txtZipCode.sendKeys(secondaryzip);
//		btnSubmit.click();
	}
	
	public void modifyAndSubmitAddressLines(String firstLine, String secondLine) {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		txtAddress1.clear();
		txtAddress1.sendKeys(firstLine);
		txtAddress2.clear();
		txtAddress2.sendKeys(secondLine);
		btnSubmit.click();
		Assert.assertTrue(driver.getPageSource().contains("Your Profile has been updated"), "The address values didnt get updated");
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
		PortalUtil pPortalUtil=new PortalUtil(driver);
		pPortalUtil.clickLogout(driver, logout);
	}

	/**
	 *
	 * The method asserts patient data in the portal site
	 * After modification in the PHR site
	 * 
	 * @param portalcity [the data is from Portal sheet]
	 * @param portalzip [the data is from Portal sheet]
	 */
	public void  assertDataCityAndZipInPortal(String portalcity,String portalzip)
	{
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		log("###########Value of City :-"+txtCity.getAttribute("value").toString());
		log("###########Value of Zip  :-"+txtZipCode.getAttribute("value").toString());
		Assert.assertEquals(txtCity.getAttribute("value").toString(),portalcity, "The City is not updated in PHR ##### ");
		Assert.assertEquals(txtZipCode.getAttribute("value"), portalzip, "The Zip code is not updated in PHR  ##### ");
	}

	public void chooseCommunicationMethod(String pCommunicationOption)
	{
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		List<WebElement> list = driver.findElements(By.xpath("//select[@name='inputs:19:input:input']/option"));
		for(WebElement li : list)
		{
			int count=1;
			if(li.getText().contains(pCommunicationOption))
			{
				Select selectProvider=new Select(chooseCommunicationDropDrown);
				selectProvider.selectByIndex(count);
				break;
			}
			count++;
		}
		IHGUtil.waitForElement(driver,10,btnSubmit);
		btnSubmit.click();
	}
}
