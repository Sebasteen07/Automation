// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.oject.maps.pss2.page.CareTeam;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.product.object.maps.pss2.page.PSS2MenuPage;

public class ManageCareTeam extends PSS2MenuPage {
	@FindBy(how = How.ID, using = "search-careteam")
	private WebElement search_CareTeam;

	@FindBy(how = How.XPATH, using = "//a[@class='text-nowrap']")
	private WebElement careTeamNAME;

	@FindBy(how = How.XPATH, using = "//span[@class='glyphicon glyphicon-triangle-top']")
	private WebElement ascendingCARETEAMS;

	@FindBy(how = How.XPATH, using = "//span[@class='glyphicon glyphicon-triangle-bottom']")
	private WebElement decendingCARETEAMS;

	@FindAll({ @FindBy(xpath = "//table[contains(@class,'table table-hover')]//tbody//tr//td//span") })
	private List<WebElement> CareTeamList;

	@FindAll({ @FindBy(xpath = "//table[contains(@class,'table table-hover')]//tbody//tr//td//span//a") })
	private List<WebElement> CareTeamNameList;

	@FindAll({ @FindBy(how = How.XPATH, using = "//tbody//tr//td//a//i") })
	private List<WebElement> deleteCareTeam;

	// Add Care Team
	@FindBy(how = How.XPATH, using = "//i[@class='glyphicon glyphicon-plus']")
	private WebElement addCareTeam;

	// Edit Care Team
	@FindBy(how = How.XPATH, using = "//input[@id='name']")
	private WebElement editCareTeamName;

	@FindBy(how = How.XPATH, using = "//button[contains(text(),'Save')]")
	private WebElement buttonSaveCareTeam;

	@FindBy(how = How.XPATH, using = "//button[@class='btn btn-inverse']")
	private WebElement buttonCancelCareTeam;

	@FindBy(how = How.XPATH, using = "//span[@class='fa fa-external-link-square'][@title='Add Resource to a Care Team']")
	private WebElement gotoGeneralInformation;

	@FindBy(how = How.XPATH, using = "//div[@class='widget-controls']//i[@class='glyphicon glyphicon-remove']")
	private WebElement closeEditCareTeam;

	// Associate Resource to Care Team //Manage Resource By Specialty and Levels
	@FindBy(how = How.XPATH, using = "//a[@title='Back']")
	private WebElement backtoManageCareTeam;

	@FindBy(how = How.XPATH, using = "//select[@name='resourceSpecialty']")
	private WebElement selectResourceSpeciality;

	@FindBy(how = How.XPATH, using = "//select[@name='resourceLevel']")
	private WebElement selectResourceLevel;

	@FindBy(how = How.XPATH, using = "//button[contains(text(),'Search')]")
	private WebElement searchViaSpcltyandLevel;

	@FindBy(how = How.XPATH, using = "//input[@id='search-resource']")
	private WebElement searchForResource;

	@FindBy(how = How.XPATH, using = "//tbody//tr//td//div[@class='display-inline-block checkbox-ios']//label//input[@type='checkbox']")
	private WebElement toggleResourceONOFF;

	// Associated Resource
	@FindBy(how = How.XPATH, using = "//input[@id='search-book'][@name='search-book']")
	private WebElement searchResourceinCT;

	public ManageCareTeam(WebDriver driver) {
		super(driver);
	}

	public void DesendingListCARETEAM() {
		Actions actions = new Actions(driver);
		actions.doubleClick(careTeamNAME).perform();
	}

	public void selectSpecialityDropDown(String speciality) {
		Select statusDropdown = new Select(selectResourceSpeciality);
		statusDropdown.selectByVisibleText(speciality);
	}

	public void select_LevelDropDown(String Level) {
		Select statusDropdown = new Select(selectResourceLevel);
		statusDropdown.selectByVisibleText(Level);
	}

}
