package com.intuit.ihg.product.phr.page.profile;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.phr.page.PhrLoginPage;

public class PhrProfilePage extends BasePageObject{
	
	public static final String PAGE_NAME = "PHR PROFILE PAGE";
	
	@FindBy(xpath="//em[text()='Log out']")
	private WebElement btnLogout;
	
	@FindBy(id="city")
	private WebElement txtCity;
	
	@FindBy(id="zipCode")
	private WebElement txtzipCode;
	
	@FindBy(css="img[alt='SAVE CHANGES']")
	private WebElement btnSaveChanges;
	
	

	public PhrProfilePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 *
	 * The method asserts patient data in the PHR site
	 * After modification in the Portal site
	 * 
	 * @param secondarycity [the data is from PHR sheet]
	 * @param secondarycity [the data is from PHR sheet]
	 */
	public void assertDataCityAndZip(String secondarycity,String secondaryzip)
	{
		IHGUtil.PrintMethodName();
		log("###########Value of City :-"+txtCity.getAttribute("value").toString());
		log("###########Value of Zip  :-"+txtzipCode.getAttribute("value").toString());
		
		//force Test case failuare
		Assert.assertEquals(txtCity.getAttribute("value").toString(),secondarycity, "The City is not updated in PHR ##### ");
		Assert.assertEquals(txtzipCode.getAttribute("value"), secondaryzip, "The Zip code is not updated in PHR  ##### ");
	}
	
	
	/**
	 * The method will modify data in the PHR site with
	 * Primary User Data ie from Portal sheet
	 * 
	 * @param secondayCity [the data is from Portal sheet]
	 * @param secondaryzip [the data is from Portal sheet]
	 * @throws Exception 
	 */
	
	public void modifyPatientInfoInPhr(String portalCity,String portalZip) throws Exception
	{
		IHGUtil.PrintMethodName();
		txtCity.clear();
		txtCity.sendKeys(portalCity);
		txtzipCode.clear();
		txtzipCode.sendKeys(portalZip);
		btnSaveChanges.click();
		Thread.sleep(5000);
		WebElement msg = driver.findElement(By.xpath(".//*[@class='info']"));
		IHGUtil.waitForElement(driver, 30, msg);
	}
	
	
	/**
	 * Click on log out button and return PHR LogIn page
	 * @return
	 */
	public PhrLoginPage clickLogout() {
		
		IHGUtil.PrintMethodName();
		btnLogout.click();
		return PageFactory.initElements(driver, PhrLoginPage.class);
	}

}
