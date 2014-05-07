package com.intuit.ihg.product.object.maps.sitegen.page.home;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

/**
 * @author bkrishnankutty
 * @Date 6/10/2013
 * @Description :- Page Object for SiteGen AdminHomePage
 * @Note :if you log in  as a super user, you will land on this page
 */
public class SiteGenAdminHomePage extends BasePageObject {

	@FindBy(id ="grpName")
	private WebElement searchField;

	@FindBy(xpath = "//input[@value='Search']")
	private WebElement searchButton;


	/**
	 * constructor for this Page
	 * 
	 * @param driver
	 */
	public SiteGenAdminHomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	/**
	 * @author bbinisha
	 * @Desc : Search and navigate to the Practice(searchItem)
	 * @param searchItem : Exact name of the practise
	 */
	public SiteGenPracticeHomePage NavigateToPracticeViaSearch(String searchItem) throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElementInDefaultFrame(driver, 30, searchField);
		searchField.sendKeys(searchItem);
		List<WebElement> suggestionList = driver.findElements(By.xpath(".//div[@id='ac2update']/ul/li/a"));
		for(WebElement suggestion :suggestionList) {
			if(suggestion.isDisplayed() && suggestion.getText().equals(searchItem)) {
				try {
					suggestion.click();
				} catch (Exception e) {
					SitegenlUtil sUtil = new SitegenlUtil(driver);
					sUtil.pressEnterKey();
					break;
				}	
			}else{
				SitegenlUtil sUtil = new SitegenlUtil(driver);
				sUtil.pressTabKey();
				sUtil.pressEnterKey();
				Thread.sleep(3000);
				driver.findElement(By.linkText(searchItem));
				continue;
			}
		}
		return PageFactory.initElements(driver, SiteGenPracticeHomePage.class);
	}

}
