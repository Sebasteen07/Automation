package com.medfusion.product.object.maps.pss2.page.AppointmentType;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.PSS2MenuPage;

public class ManageAppointmentType extends PSS2MenuPage {

	@FindBy(how = How.ID, using = "search-appointmenttype")
	private WebElement searchAppointment;

	@FindAll({@FindBy(xpath = "//*[@class=\"table table-hover \"]/tbody/tr")})
	private List<WebElement> appointmentTypeList;

	@FindAll({@FindBy(xpath = "//*[@class=\"table table-hover \"]/tbody/tr/td/span/a")})
	private List<WebElement> appointmentTypeNameList;

	@FindBy(how = How.XPATH, using = "//table[@class=\"table table-hover \"]/tbody/tr/td[3]/a")
	private WebElement aptTypeClose;

	@FindBy(how = How.ID, using = "name")
	private WebElement aptTypeName;

	@FindBy(how = How.ID, using = "extAppointmentTypeId")
	private WebElement aptTypeID;

	@FindBy(how = How.ID, using = "appointmentType.displayNamesEN")
	private WebElement aptTypeDisplayName;

	@FindBy(how = How.ID, using = "categoryName")
	private WebElement aptTypeCategoryName;

	public ManageAppointmentType(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		return false;
	}

	public void searchByAptTypeName(String appointmentName) {
		searchAppointment.sendKeys(appointmentName);
	}

	public List<WebElement> aptNameList() {
		IHGUtil.waitForElement(driver, 120, searchAppointment);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		List<WebElement> aptNameList = driver.findElements(By.xpath("//*[@class=\"table table-hover \"]/tbody/tr/td/span/a"));
		return aptNameList;
	}
}
