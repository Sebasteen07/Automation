package com.intuit.ihg.product.object.maps.sitegen.page.location;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

/**
 * @author bkrishnankutty
 * @Date 6/13/2013
 * @Description :- Page Object for SiteGen Practice HomePage
 * @Note :This is home page for practice
 */
public class ManageYourLocationsPage extends BasePageObject{
	
	
	@FindBy(name="btn_add")
	private WebElement btnAddNewLocation;
	
	@FindBy(linkText="Add Location")
	private WebElement lnkAddLocation;
	
	@FindBy(linkText="Edit Location")
	private WebElement lnkEditLocation;
		
	/**
	 * @author bkrishnankutty
	 * @Desc:- constructor for this Page
	 * @param driver
	 */
	public ManageYourLocationsPage(WebDriver driver) {
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
		SitegenlUtil.setSiteGenFrame(driver);
		
		boolean result = false;
		try {
			result = IHGUtil.waitForElement(driver, 6, btnAddNewLocation);
			} catch (Exception e) {
			// Catch any element not found errors
		}
		
		return result;
	}
	
	/**
	 * @author bkrishnankutty
	 * @Desc:- click on link AddLocation
	 * @return AddLocationPage
	 * 
	 */

	public AddLocationPage clicklnkAddLocation()
	{	
		SitegenlUtil.setDefaultFrame(driver);
		lnkAddLocation.click();
		return PageFactory.initElements(driver,AddLocationPage.class);
	}
	

	/**
	 * @author bkrishnankutty
	 * @Desc:- check the Data is present or not
	 * @return true or false
	 * 
	 * @param practiceName
	 * @param State
	 * @return
	 * @throws Exception
	 */
	
	public boolean checkTheData(String practiceName,String State) throws Exception
	{
	  boolean flag =false;
	  if((SitegenlUtil.verifyTextPresent(driver,practiceName,1000) == true) && (SitegenlUtil.verifyTextPresent(driver,State,00) == true))
		{
		  flag = true;
		  return flag;
		}
	  return flag;
	}
	
	/**
	 * @author bkrishnankutty
	 * @Desc:- Clean the data
	 * @return void
	  
	 * @param practiceName
	 * @param State
	 * @throws Exception
	 * Note :- assertion is here becoz this func is not important from deployment acceptance point of view
	 */
	public void  cleaningTestdata (String practiceName,String State) throws Exception
	{
		IHGUtil.PrintMethodName();
		SitegenlUtil.setSiteGenFrame(driver);
		
		if(checkTheData(practiceName,State) == true)
		{
			lnkEditLocation.click();
			EditSiteGenAutomationPage pEditSiteGenAutomationPage = PageFactory.initElements(driver, EditSiteGenAutomationPage.class);
			Assert.assertTrue(pEditSiteGenAutomationPage.isSearchPageLoaded(), "Expected the Edit your location Page  to be loaded, but it was not.");
			pEditSiteGenAutomationPage.deleteLocation();
			Assert.assertTrue(this.isSearchPageLoaded(), "Expected the Manage your location Page  to be loaded, but it was not.");
			Assert.assertFalse(SitegenlUtil.verifyTextPresent(driver,practiceName,1000),"Clean process is unable to clean data ,kindly check it manually");
			Assert.assertFalse(SitegenlUtil.verifyTextPresent(driver,State,1000),"Clean process is unable to clean data ,kindly check it manually");
		}
		else{
		     log("The data is already clean");
		}
	}
		

	
}
