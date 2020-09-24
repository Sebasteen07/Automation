//Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.Resource;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.PSS2MenuPage;

public class ManageResource extends PSS2MenuPage {

	@FindBy(how = How.CSS, using = ".table.table-hover ")
	private WebElement resourceTable;

	@FindBy(how = How.ID, using = "search-resource")
	private WebElement searchResource;

	@FindBy(how = How.XPATH, using = "//table/tbody/tr/td[2]/span/a")
	private WebElement searchedResourceName;

	@FindAll({@FindBy(xpath = "//*[@class=\"table table-hover \"]/tbody/tr")})
	private List<WebElement> locationRowList;

	@FindBy(how = How.XPATH, using = "//a[contains(text(),'General')]")
	private WebElement editGeneralTab;

	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Location')]")
	private WebElement editLocationTab;

	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Specialty')]")
	private WebElement editSpecialityTab;

	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Appointment Type')]")
	private WebElement editAptTypeTab;

	@FindBy(how = How.NAME, using = "slotSize")
	private WebElement slotSizeValue;

	@FindBy(how = How.ID, using = "status")
	private WebElement resourceActiveState;

	@FindBy(how = How.ID, using = "sharePatients")
	private WebElement resourceSharePatients;

	@FindBy(how = How.ID, using = "search-specialty")
	private WebElement resourceSearchSpeciality;

	@FindBy(how = How.XPATH, using = "//*[@id=\"tab33\"]/table/tbody/tr[1]/td[3]/div/label/input")
	private WebElement resourceSpecilaityEnabled;

	@FindBy(how = How.ID, using = "search-appointmenttype")
	private WebElement resourceSearchApt;

	@FindBy(how = How.XPATH, using = "//*[@id=\"tab43\"]/table/tbody/tr[1]/td[3]/div/label/input")
	private WebElement resourceAptEnabled;

	@FindBy(how = How.ID, using = "acceptComment")
	private WebElement resourceAcceptsComments;

	@FindBy(how = How.ID, using = "isageRule")
	private WebElement resourceAgeRuleChecked;

	@FindBy(how = How.XPATH, using = "//button[@type=\"submit\"]")
	private WebElement resourceSave;

	@FindBy(how = How.XPATH, using = "//button[@type=\"button\"]")
	private WebElement resourceCancel;

	@FindBy(how = How.ID, using = "search-location")
	private WebElement resourceSearchLocation;

	@FindBy(how = How.XPATH, using = "//*[@id=\"tab23\"]/table/tbody/tr[1]/td[3]/div/label/input")
	private WebElement resourceSearchedLocation;

	public ManageResource(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		return true;
	}

	public void searchResource(String resourceName) {
		searchResource.sendKeys(resourceName);
	}

	public void selectResource(String resourceName) {
		searchResource(resourceName);
		IHGUtil.waitForElement(driver, 60, searchedResourceName);
		searchedResourceName.click();
	}

	public Boolean isSharedPatientTrueForResource() {
		return Boolean.valueOf(resourceSharePatients.getAttribute("ng-reflect-model"));
	}

	public Boolean isResourceActive() {
		return Boolean.valueOf(resourceActiveState.getAttribute("ng-reflect-model"));
	}

	public Boolean isResourceAcceptComments() {
		return Boolean.valueOf(resourceAcceptsComments.getAttribute("ng-reflect-model"));
	}

	public Boolean isResourceAgeRuleChecked() {
		return Boolean.valueOf(resourceAgeRuleChecked.getAttribute("ng-reflect-model"));
	}

	public String isResourceLocationEnabled() {
		String enabledValue = resourceSearchedLocation.getAttribute("ng-reflect-model");
		log("Location Enabled for the resource ? " + enabledValue);
		return enabledValue;
	}
}