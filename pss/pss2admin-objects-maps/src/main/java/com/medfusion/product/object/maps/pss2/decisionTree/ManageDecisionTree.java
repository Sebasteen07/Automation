// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.decisionTree;

import java.io.File;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;
import com.medfusion.product.object.maps.pss2.page.util.CommonMethods;

public class ManageDecisionTree extends PSS2MainPage {
	@FindBy(how = How.XPATH, using = "//input[@id='search-Category']")
	private WebElement searchDecisionTree;
	
	@FindBy(how = How.XPATH, using = "//table/tbody/tr/td/span/a")
	private WebElement selectByDecisionTreeName;
	
	@FindBy(how = How.XPATH, using = "//input[@id='file']")
	private WebElement importDecisiontreebtn;
	
	// Add Decision Tree
	@FindBy(how = How.XPATH, using = "//a[@title='Add Decision Tree']")
	private WebElement addDecisionTree;

	@FindBy(how = How.XPATH, using = "//*[@id='content']/div[2]/ng-component/div/div[1]/section/header/div/a")
	private WebElement reorderDecisionTree;

	@FindAll({ @FindBy(xpath = "//span[@class='fw-semi-bold ng-star-inserted']/a") })
	private List<WebElement> decisionTreeName;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//*[@id='content']/div[2]/ng-component/div/div/section/div/div/table/tbody/tr/td[2]/a") })
	private WebElement deleteDecisionTree;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//table/tbody/tr/td[1]/span[1]/a") })
	private List<WebElement> decisionTreeNameList;

	// Edit Decision Tree
	@FindBy(how = How.XPATH, using = "//input[@id='name']")
	private WebElement editDecisionTreeName;

	@FindBy(how = How.XPATH, using = "//div[@id='category.displayNamesEN']/textarea")
	private WebElement editEnglishDecisionTreeName;
	
	@FindBy(how = How.XPATH, using = "//textarea[@id='displayName']")
	private WebElement editDecisionTreeWhenESDisabled;
	
	@FindBy(how = How.XPATH, using = "//*[@id='tab13']/form/fieldset[1]/div[2]/div/ul/li[2]/a")
	private WebElement clickEspanolDecisionTreeName;
	
	@FindBy(how = How.XPATH, using = "//div[@id='category.displayNamesES']/textarea")
	private WebElement editEspanolDecisionTreeName;
	
	@FindBy(how = How.XPATH, using = "//button[@type='submit']")
	private WebElement buttonSaveDecisionTree;

	@FindBy(how = How.XPATH, using = "//button[@class='btn btn-inverse']")
	private WebElement buttonCancelDecisionTree;

	@FindBy(how = How.XPATH, using = "//*[@id='tab13']/form/legend/a")
	private WebElement generalInformationTab;

	@FindBy(how = How.XPATH, using = "//a[@title='Close']")
	private WebElement closeEditDecisionTree;

	@FindBy(how = How.XPATH, using = "//a[@title='Back']//*[local-name()='svg']")
	private WebElement backtoManageDecisionTree;
	
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'displayNames as null')]")
	private WebElement validationAlertMessage;
	
	@FindBy(how = How.XPATH, using = "//*[@id='tabs3']/li[1]/a")
	private WebElement editGeneralTab;
	
	@FindBy(how = How.XPATH, using = "//*[@id='tabs3']/li[2]/a")
	private WebElement editSpecialityTab;
	
	@FindBy(how = How.XPATH, using = "//*[@id='tab33']/table/tbody/tr/td[1]")
	private WebElement selectSpecialityName;

	@FindBy(how = How.XPATH, using = "//input[@id='search-specialty']")
	private WebElement searchForSpeciality;

	@FindBy(how = How.XPATH, using = "//label[@class='switch']/i")
	private WebElement toggleSpecialityONOFF;
	
	@FindBy(how = How.XPATH, using = "//*[@id='app']/nav[2]/ul[2]/li[4]/a")
	private WebElement settingBtn;

	@FindBy(how = How.XPATH, using = "//*[@id='app']/nav[2]/ul[2]/li[4]/ul")
	private WebElement logoutBtn;
	
	CommonMethods commonMethods = new CommonMethods(driver);
	
	public ManageDecisionTree(WebDriver driver) {
		super(driver);
	}
	
	public void clickOnGeneralTab() {
		commonMethods.highlightElement(editGeneralTab);
		editGeneralTab.click();
		log("Clicked On General Tab From Decision Tree ");
	}
	
	public void searchSpecility(String specilityName) {
		log("Enter the speciality name and Search speciality");
		IHGUtil.waitForElement(driver, 60, searchForSpeciality);
		searchForSpeciality.sendKeys(specilityName);
	}
	
	public void selectSpecility(String specilityName) throws InterruptedException {
		clickOnSpecialtyTab();
		Thread.sleep(2000);
		searchSpecility(specilityName);
		clickOnSpecialtyToggle();
		log("clicked on Specility");
		}
	
	public void clickOnSpecialtyTab() {
		commonMethods.highlightElement(editSpecialityTab);
		IHGUtil.waitForElement(driver, 60, editSpecialityTab);
		editSpecialityTab.click();
		log("Clicked On Specialty Tab From Decision Tree ");
	}

	public void clickOnSpecialtyToggle() {
		IHGUtil.waitForElement(driver, 60, toggleSpecialityONOFF);
		toggleSpecialityONOFF.click();
		log("Clicked on Specialty Toggle button");
	}
	
	public void addDecisionTree(String decisionTreeName) throws InterruptedException {
		Thread.sleep(2000);
		addDecisionTree.click();
		log("Add Decision tree Button clicked");
		editDecisionTreeName.sendKeys(decisionTreeName);
		editEnglishDecisionTreeName.sendKeys(decisionTreeName);
		clickEspanolDecisionTreeName.click();
		editEspanolDecisionTreeName.sendKeys(decisionTreeName);
		buttonSaveDecisionTree.click();
		log("Decision Tree is added successfully.");	
	}
	
	public void addDecisionTreeWhenSpanishIsDisabled(String decisionTreeName) throws InterruptedException {
		Thread.sleep(2000);
		addDecisionTree.click();
		log("Add Decision tree Button clicked");
		editDecisionTreeName.sendKeys(decisionTreeName);
		editDecisionTreeWhenESDisabled.sendKeys(decisionTreeName);
		buttonSaveDecisionTree.click();
		log("Decision Tree is added successfully.");	
	}
	
	public ManageGeneralInformation goToGeneralInformation() {
		javascriptClick(generalInformationTab);
		log("General Information Tab clicked.....");
		return PageFactory.initElements(driver, ManageGeneralInformation.class);
	}
	
	public void clickOnBackArrow() {
		commonMethods.highlightElement(backtoManageDecisionTree);
		backtoManageDecisionTree.click();
	}
	
	public void importDecisionTree(String decisionTreeName) throws InterruptedException {
		importDecisiontreebtn.sendKeys(getFile(decisionTreeName));
		log("Decision Tree is imported");
		editDecisionTreeName.sendKeys(decisionTreeName);
		editEnglishDecisionTreeName.sendKeys(decisionTreeName);
		clickEspanolDecisionTreeName.click();
		editEspanolDecisionTreeName.sendKeys(decisionTreeName);
		buttonSaveDecisionTree.click();
		log("Imported Decision Tree is saved successfully.");
	}
	
	public void importDecisionTreeWithNullValues(String decisionTreeWithNullValues) throws InterruptedException {
		importDecisiontreebtn.sendKeys(getFile(decisionTreeWithNullValues));
		editDecisionTreeName.sendKeys(decisionTreeWithNullValues);
		editEnglishDecisionTreeName.sendKeys(decisionTreeWithNullValues);
		clickEspanolDecisionTreeName.click();
		editEspanolDecisionTreeName.sendKeys(decisionTreeWithNullValues);
		buttonSaveDecisionTree.click();
		log("Can not import "+decisionTreeWithNullValues+" Decision Tree");
	}

	String getFile(String decisionTreeName) {
		return new File(".//src//test//resources//"+decisionTreeName+".json").getAbsolutePath();
	}
	
	public void searchByDecisionTreeName(String decisionTreeName) throws InterruptedException {
		Thread.sleep(4000);
		searchDecisionTree.sendKeys(decisionTreeName);
	}
	
	public String captureValidationAlertMessage() {
		String alertMessage = validationAlertMessage.getText();
		String decisionTreeAlertMessage = alertMessage.replaceAll("[()\\[\\]]", "");
		return decisionTreeAlertMessage;
	}
	
	public void selectDecisionTree(String decisionTreeName) throws InterruptedException {
		searchByDecisionTreeName(decisionTreeName);
		log("Decision tree selected is "+decisionTreeName);
		Thread.sleep(2000);
		IHGUtil.waitForElement(driver, 60, selectByDecisionTreeName);
		selectByDecisionTreeName.click();
		log("clicked on Decision tree");
	} 
	
	public void removedDecisionTree(String decisionTreeName) throws InterruptedException {
		searchByDecisionTreeName(decisionTreeName);
		log("Decision tree selected is "+decisionTreeName);
		Thread.sleep(5000);
		deleteDecisionTree.click();
		log(" Decision tree Removed");
	}
	
	public void removedSpecialityFromDecisionTree(String decisionTreeName, String specilityName) throws InterruptedException {
		selectDecisionTree(decisionTreeName);
		selectSpecility(specilityName);
		log(" Speciality Removed from Decision tree");
		IHGUtil.waitForElement(driver, 60, deleteDecisionTree);
		deleteDecisionTree.click();
		log(" Decision tree Removed");
	}
	
	public List<WebElement> decisionTreeNameList() {
		IHGUtil.waitForElement(driver, 120, searchDecisionTree);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		return decisionTreeNameList;
	}
	
	public void logout() throws InterruptedException {
		settingBtn.click();
		logoutBtn.click();
	}
	
}

