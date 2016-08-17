package com.intuit.ihg.product.object.maps.sitegen.page.home;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;
import com.medfusion.common.utils.IHGUtil;

/**
 * @author bkrishnankutty
 * @Date 6/10/2013
 * @Description :- Page Object for SiteGen SiteGen HomePage
 * @Note :if you log in as a Normal user, you will land on this page
 */

public class SiteGenHomePage extends BasePageObject {

	@FindBy(how = How.CSS, using = "a.logoutlink > span")
	public WebElement lnklogout;

	@FindBy(how = How.CSS, using = ".homelink>span")
	public WebElement lnkHome;

	@FindBy(how = How.NAME, using = "btn_logout")
	public WebElement btnlogout;

	@FindBy(how = How.CSS, using = "a.helplink > span")
	public WebElement lnkHelp;

	@FindBy(how = How.CSS, using = "table>tbody>tr>td>ul>li>a>b")
	private WebElement lnkMedfusionSiteAdministration;

	@FindBy(xpath = ".//input[@id ='grpName']")
	private WebElement searchPratciceField;

	@FindBy(xpath = ".//input[@value ='Search']")
	private WebElement searchPratciceButton;

	/**
	 * @author bkrishnankutty
	 * @Desc:- constructor for this Page
	 * @param driver
	 */
	public SiteGenHomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:-Indicates if the search page is loaded
	 * @return true or false
	 */
	public boolean isSearchPageLoaded() {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);
		boolean result = false;
		try {
			result = IHGUtil.waitForElement(driver, 6, lnkHome);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}

	/**
	 * @author bkrishnankutty
	 * @Desc :- Click on Link MedfusionSiteAdministration
	 * @return SiteGenPracticeHomePage
	 */
	public SiteGenPracticeHomePage clickLinkMedfusionSiteAdministration() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, lnkMedfusionSiteAdministration);
		lnkMedfusionSiteAdministration.click();
		return PageFactory.initElements(driver, SiteGenPracticeHomePage.class);
	}

	public SiteGenPracticeHomePage searchPracticeFromSGAdmin(String practiceName) throws Exception {
		log("Automation Practice: " + practiceName);
		IHGUtil.waitForElement(driver, 30, this.searchPratciceField);
		searchPratciceField.sendKeys(practiceName);
		searchPratciceButton.click();
		String xpath = "//*[contains(text(),'" + practiceName + "')]";
		driver.findElement(By.xpath(xpath)).click();
		return PageFactory.initElements(driver, SiteGenPracticeHomePage.class);
	}
}
