package com.intuit.ihg.product.sitegen.page.permissionsAndPersonnelTypes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.page.home.SiteGenPracticeHomePage;
import com.intuit.ihg.product.sitegen.utils.SitegenConstants;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

/**
 * @author bkrishnankutty
 * @Date 6/10/2013
 * @Description :- Page Object for SiteGen Manage User Permissions Page
 * @Note :
 */
public class ManageUserPermissionsPage extends BasePageObject {

	@FindBy(name = "personneltype")
	private WebElement dropDownPersonneltypes;

	@FindBy(name = "solution")
	private WebElement dropDownSolutions;

	@FindBy(name = "location")
	private WebElement dropDownLocations;

	@FindBy(name = "user")
	private WebElement dropDownUsers;

	@FindBy(xpath = "//form/fieldset[1]/div[2]/table/tbody/tr[1]/td[1]/span")
	private WebElement textAutoSitgen;

	@FindBy(xpath = "//form/fieldset[1]/div[2]/table/tbody/tr[1]/td[2]/span//input[@type='checkbox']")
	private WebElement chkAll;

	@FindBy(xpath = ".//a[contains(@href,'generator.home')]")
	private WebElement lnkHome;

	/**
	 * @author bkrishnankutty
	 * @Desc:- constructor for this Page
	 * @param driver
	 */
	public ManageUserPermissionsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
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
			result = IHGUtil.waitForElement(driver, 6, dropDownPersonneltypes);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}

	/**
	 * 
	 * @author bkrishnankutty
	 * @Desc:- Give permission to the Nurses
	 * @return SiteGenPracticeHomePage
	 * 
	 * @param personneltype1
	 * @param personneltype2
	 * @param solutions
	 * @param locations
	 * @param users
	 * @return
	 * @throws Exception
	 */
	public SiteGenPracticeHomePage givePermission2Nurse(String personneltype1,
			String personneltype2, String solutions, String locations,
			String users) throws Exception {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);
		IHGUtil.waitForElementInDefaultFrame(driver,30,dropDownPersonneltypes);
		
		log("Wait for personnel types to get loaded");
		Thread.sleep(2000);
		Select personaltype = new Select(dropDownPersonneltypes);
		personaltype.selectByVisibleText(personneltype1);

		log("Wait for drop down solutions to get loaded ");
		Thread.sleep(2000);
		IHGUtil.waitForElement(driver, 30, dropDownSolutions);
		Select solution = new Select(dropDownSolutions);
		solution.selectByVisibleText(solutions);

		log("Wait for drop down locations to get loaded");
		Thread.sleep(2000);
		IHGUtil.waitForElement(driver,30,dropDownLocations);
		Select location = new Select(dropDownLocations);
		location.selectByVisibleText(locations);

		log("Wait for user to get loaded");
		Thread.sleep(2000);
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(presenceOfElementLocated(By.name("user")));
		
		log("Wait for drop down users to get loaded");
		Thread.sleep(2000);
		IHGUtil.waitForElement(driver, 30, dropDownUsers);
		Select user = new Select(dropDownUsers);	
		user.selectByVisibleText(users); // change

		Thread.sleep(5000);
		if (!chkAll.isSelected()) {
			chkAll.click();
			Thread.sleep(10000);
			SitegenlUtil sutil = new SitegenlUtil(driver);
			sutil.checkAlert(driver);
			Thread.sleep(5000);
		}
		else{
			log("Permission has been added");
		}
		IHGUtil.waitForElement(driver,30,lnkHome);
		lnkHome.click();
		return PageFactory.initElements(driver, SiteGenPracticeHomePage.class);

		

	}
	private static Function<WebDriver,WebElement> presenceOfElementLocated(final By locator) {
	    return new Function<WebDriver, WebElement>() {
	        @Override
	        public WebElement apply(WebDriver driver) {
	            return driver.findElement(locator);
	        }
	    };
	}

}
