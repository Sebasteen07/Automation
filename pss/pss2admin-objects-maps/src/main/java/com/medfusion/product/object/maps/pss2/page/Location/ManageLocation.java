package com.medfusion.product.object.maps.pss2.page.Location;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.PSS2MenuPage;

public class ManageLocation extends PSS2MenuPage {

	@FindBy(how = How.ID, using = "search-location")
	private WebElement specialitySearch;

	@FindBy(how = How.XPATH, using = "//*[@title=\"Add Practice Location\"]")
	private WebElement addLocation;

	@FindBy(how = How.XPATH, using = "//tfoot/tr/td/mfbootstrappaginator/mfpaginator/ul[2]/li[3]/a")
	private WebElement show50Location;

	@FindBy(how = How.XPATH, using = "//select[@name=\"profile\"]")
	private WebElement selectTimeZone;

	public ManageLocation(WebDriver driver) {
		super(driver);
	}

	public List<WebElement> getLocationNameList() {

		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		List<WebElement> locationListOfNames = driver.findElements(By.xpath("//*[@class=\"mt\"]/table/tbody/tr/td/span/a"));
		return locationListOfNames;
	}

	public void showMoreLocation() {
		IHGUtil.waitForElement(driver, 120, specialitySearch);
		javascriptClick(show50Location);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		return false;
	}
}
