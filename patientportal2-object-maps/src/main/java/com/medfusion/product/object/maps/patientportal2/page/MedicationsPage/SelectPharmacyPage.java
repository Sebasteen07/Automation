// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.MedicationsPage;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;

public class SelectPharmacyPage extends MedfusionPage {

	public SelectPharmacyPage(WebDriver driver) {
		super(driver);

		IHGUtil.PrintMethodName();
		PageFactory.initElements(driver, this);

	}

	@FindBy(how = How.XPATH, using = "(//*[@class='pharmacy-radio-button'])[1]")
	private WebElement radioPharmacy;

	@FindBy(how = How.ID, using = "add-new-pharmacy")
	private WebElement addPharmacy;

	//@FindBy(how = How.XPATH, using = "//div[@class='form-buttons ng-scope']/button[@type='button']")
	@FindBy(how = How.XPATH, using = "//div[@class='form-buttons']/button[@type='button']")
	private WebElement btnBack;

	//@FindBy(how = How.XPATH, using = "//button[@class='btn btn-primary ng-binding']")
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Continue')]")
	private WebElement btnContinue;

	@FindBy(how = How.XPATH, using = "//a[@class='add-pharmacy-options'][2]")
	private WebElement addYourPharmacy;

	@FindBy(how = How.XPATH, using = "//a[text()='Providers suggested pharmacy']")
	private WebElement addProviderSuggestPharmacy;

	@FindBy(how = How.XPATH, using = "//div[@id='addPharmacyOptions']")
	private WebElement pharmacyPopup;

	@FindBy(how = How.ID, using = "nameOfPharmacy")
	private WebElement pharmacyName;

	@FindBy(how = How.ID, using = "phoneNumber1")
	private WebElement pharmacyPhone1;
	
	@FindBy(how = How.ID, using = "phoneNumber2")
	private WebElement pharmacyPhone2;
	
	@FindBy(how = How.ID, using = "phoneNumber3")
	private WebElement pharmacyPhone3;

	@FindBy(how = How.ID, using = "faxNumber")
	private WebElement pharmacyFax;

	@FindBy(how = How.ID, using = "address")
	private WebElement pharmacyAddress;

	@FindBy(how = How.ID, using = "city")
	private WebElement pharmacyCity;

	@FindBy(how = How.XPATH, using = "//input[@title='stateLabel']")
	private WebElement pharmacyState;

	@FindBy(how = How.ID, using = "zipcode")
	private WebElement pharmacyZip;

	@FindBy(how = How.XPATH, using = "//div[@class='modal-button']/button[@class='btn btn-secondary ng-binding']")
	private WebElement popupBackbtn;
	
	@FindBy(how = How.XPATH, using = "//button[@class='close']")
	private WebElement addPharmacyClosePopupbtn;
	
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Save & Continue')]")
	private WebElement popupContinueBtn;

	@FindBy(how = How.XPATH, using = "//*[@id='pharmacyDetails']")
	private WebElement EnterPharmacyNameActivate;

	@FindBy(how = How.XPATH, using = "//*[@class='ng-input']/input[@role='combobox']")
	private WebElement enterPharmacyName;

	@FindBy(how = How.XPATH, using = "//div[@class='ng-option ng-option-marked']")
	private WebElement selectPharmacyName;
	
	@FindBy(how = How.XPATH, using = "//span[@class='pharmacy-radio-button selected']/../../..//a[text()='Delete']")
	private WebElement btnDelete;
	
	@FindBy(how = How.ID, using = "removePharmacyOkButton")
	private WebElement btnRemovePharmacyOkButton;
	
	@FindBy(how = How.XPATH, using = "//span[@class='pharmacy-radio-button selected']/../span[@class='pharmacy-name']")
	private WebElement rdoPharmacy;
	
	@FindBy(how = How.XPATH, using = "//div[@id='pharmacy_list']//ol")
	private WebElement listOfPharmacies;
	
	@FindBy(how = How.XPATH, using = "//span[@class=\"pharmacy-radio-button selected\"]/ancestor::li/div//*[contains(text(),'Edit')]")
	private WebElement btnEditPharmacy;

	@FindBy(how = How.XPATH, using = "//span[@class=\"pharmacy-radio-button selected\"]/ancestor::li/div//*[@class=\"pharmacy-name\"]")
	private WebElement rdoEditedPharmacyName;

	WebDriverWait wait=new WebDriverWait(driver, 60);
	private boolean areBasicPopUpPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(addPharmacyClosePopupbtn);
		webElementsList.add(addYourPharmacy);
		webElementsList.add(addProviderSuggestPharmacy);
		return assessPageElements(webElementsList);
	}

	private boolean arePopupPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();

		webElementsList.add(pharmacyName);
		webElementsList.add(pharmacyPhone1);
		webElementsList.add(pharmacyFax);
		webElementsList.add(pharmacyAddress);
		webElementsList.add(pharmacyCity);
		webElementsList.add(pharmacyState);
		webElementsList.add(pharmacyZip);
		webElementsList.add(popupBackbtn);
		webElementsList.add(popupContinueBtn);
		return assessPageElements(webElementsList);

	}

	public void selectPharmacy() throws InterruptedException {

		javascriptClick(radioPharmacy);
		btnContinue.click();

	}

	public void addNewPharmacy(WebDriver driver) throws IOException, InterruptedException {
		PropertyFileLoader testData = new PropertyFileLoader();
		IHGUtil.PrintMethodName();
		log("Click on Add a Pharmacy button");
		Thread.sleep(2000);
		addPharmacy.click();
		log("Click on Add Your Pharmacy button from the popup");
		Thread.sleep(2000);
		addYourPharmacy.click();
		log("Verify all the popup elements are present");
		assertTrue(arePopupPageElementsPresent());
		log("Enter Pharmacy Details");
		pharmacyName.sendKeys(testData.getProperty("pharmacy.name") + IHGUtil.createRandomNumericString(4));
		pharmacyFax.sendKeys(IHGUtil.createRandomNumericString(10));
		pharmacyAddress.sendKeys(testData.getProperty("address1"));
		pharmacyCity.sendKeys(testData.getProperty("city"));
		pharmacyState.sendKeys(testData.getProperty("state"));
		pharmacyState.sendKeys(Keys.ENTER);
		pharmacyZip.sendKeys(testData.getProperty("zip.code"));;
		log("Verifying continue button is disabled since Phone number is mandatory");
		assertFalse(popupContinueBtn.isEnabled(), "Continue button is disabled");
		pharmacyPhone1.sendKeys(IHGUtil.createRandomNumericString(3));
		pharmacyPhone2.sendKeys(IHGUtil.createRandomNumericString(3));
		pharmacyPhone3.sendKeys(IHGUtil.createRandomNumericString(4));
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(popupContinueBtn));
		popupContinueBtn.click();
		log("Pharmacy is added");
		Thread.sleep(2000);// need to sleep because of modal disappearing time
		btnContinue.click();

	}

	public void addProviderSuggestedPharmacy(WebDriver driver, String enterPharmacy)
			throws IOException, InterruptedException {
		IHGUtil.PrintMethodName();
		Thread.sleep(8000);//need to sleep because of modal disappearing time
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		log("Click on Add a Pharmacy button");
		wait.until(ExpectedConditions.visibilityOf(addPharmacy));
		addPharmacy.click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOf(addProviderSuggestPharmacy));
		javascriptClick(addProviderSuggestPharmacy);
		wait.until(ExpectedConditions.visibilityOf(EnterPharmacyNameActivate));
		log("Click on Enter Pharmacy Name Activate TextBox");
		EnterPharmacyNameActivate.click();
		log("It enter the pharmacy name ");
		enterPharmacyName.sendKeys(enterPharmacy);
		selectPharmacyName.click();
		log("it clicked on the save and continue button ");
		wait.until(ExpectedConditions.visibilityOf(popupContinueBtn));
		popupContinueBtn.click();
		radioPharmacy.click();
		log("it clicked on the save and continue button ");
		Thread.sleep(2000);//need to sleep because of modal disappearing time
		btnContinue.click();

	}


	public void deletePharmacy() throws InterruptedException{
		log("Click on Delete a Pharmacy button");
		wait.until(ExpectedConditions.elementToBeClickable(btnDelete));
		javascriptClick(btnDelete);
		wait.until(ExpectedConditions.elementToBeClickable(btnRemovePharmacyOkButton));
		btnRemovePharmacyOkButton.click();
		log("Pharmacy is Deleted");

	}

	public void addPharmacyWithNameAndPhoneNumber(WebDriver driver) throws IOException, InterruptedException {
		PropertyFileLoader testData = new PropertyFileLoader();
		IHGUtil.PrintMethodName();
		log("Click on Add a Pharmacy button");
		Thread.sleep(2000);
		addPharmacy.click();
		log("Click on Add Your Pharmacy button from the popup");
		Thread.sleep(2000);
		if (new IHGUtil(driver).exists(addYourPharmacy)) {
			addYourPharmacy.click();
			log("Verify all the popup elements are present");
			assertTrue(arePopupPageElementsPresent());
		} 
//		addYourPharmacy.click();
//		log("Verify all the popup elements are present");
//		assertTrue(arePopupPageElementsPresent());
		log("Enter Pharmacy Details");
		pharmacyName.sendKeys(testData.getProperty("pharmacy.name") + IHGUtil.createRandomNumericString(4));
		pharmacyPhone1.sendKeys(IHGUtil.createRandomNumericString(3));
		pharmacyPhone2.sendKeys(IHGUtil.createRandomNumericString(3));
		pharmacyPhone3.sendKeys(IHGUtil.createRandomNumericString(4));
		popupContinueBtn.click();
		log("Pharmacy is added");
	}

	public String addNewPharmacy(WebDriver driver, String user) throws IOException, InterruptedException {
		PropertyFileLoader testData = new PropertyFileLoader();
		IHGUtil.PrintMethodName();
		log("Click on Add a Pharmacy button");
		Thread.sleep(2000);
		addPharmacy.click();
		log("Click on Add Your Pharmacy button from the popup");
		Thread.sleep(2000);
		addYourPharmacy.click();
		log("Verify all the popup elements are present");
		assertTrue(arePopupPageElementsPresent());
		log("Enter Pharmacy Details");
		String pharmaName= user+IHGUtil.createRandomNumericString(4);
		pharmacyName.sendKeys(pharmaName);
		pharmacyFax.sendKeys(IHGUtil.createRandomNumericString(10));
		pharmacyAddress.sendKeys(testData.getProperty("address1"));
		pharmacyCity.sendKeys(testData.getProperty("city"));
		pharmacyState.sendKeys(testData.getProperty("state"));
		pharmacyState.sendKeys(Keys.ENTER);
		pharmacyZip.sendKeys(testData.getProperty("zip.code"));;
		log("Verifying continue button is disabled since Phone number is mandatory");
		assertFalse(popupContinueBtn.isEnabled(), "Continue button is disabled");
		pharmacyPhone1.sendKeys(IHGUtil.createRandomNumericString(3));
		pharmacyPhone2.sendKeys(IHGUtil.createRandomNumericString(3));
		pharmacyPhone3.sendKeys(IHGUtil.createRandomNumericString(4));
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(popupContinueBtn));
		popupContinueBtn.click();
		log("Pharmacy is added");
		Thread.sleep(2000);// need to sleep because of modal disappearing time
		btnContinue.click();
		return pharmaName;
	}

	public boolean validateIfDependentPharmacyPresent(String selectedPharmacy) {
		List<WebElement> list = driver.findElements(By.xpath("//div[@id='pharmacy_list']//ol//li//div//label//span[@class='pharmacy-name']"));
		for(WebElement e : list)
		{
		System.out.println(e.getText());
		if(e.getText().equals(selectedPharmacy))
		{
			log("Dependent Pharmacy is found in Guardian account: FAIL");
			return false;
		}
		}
		return true;
		
	}

	
	public void addEditNewPharmacy(WebDriver driver) throws IOException, InterruptedException {
	PropertyFileLoader testData = new PropertyFileLoader();
	IHGUtil.PrintMethodName();
	log("Click on Add a Pharmacy button");
	Thread.sleep(2000);
	addPharmacy.click();
	log("Click on Add Your Pharmacy button from the popup");
	Thread.sleep(2000);
	addYourPharmacy.click();
	log("Verify all the popup elements are present");
	assertTrue(arePopupPageElementsPresent());
	log("Enter Pharmacy Details");
	String ab = testData.getProperty("pharmacy.name") + IHGUtil.createRandomNumericString(4);
	pharmacyName.sendKeys(ab);
	System.out.println(ab);
	pharmacyFax.sendKeys(IHGUtil.createRandomNumericString(10));
	pharmacyAddress.sendKeys(testData.getProperty("address1"));
	pharmacyCity.sendKeys(testData.getProperty("city"));
	pharmacyState.sendKeys(testData.getProperty("state"));
	pharmacyState.sendKeys(Keys.ENTER);
	pharmacyZip.sendKeys(testData.getProperty("zip.code"));;
	log("Verifying continue button is disabled since Phone number is mandatory");
	assertFalse(popupContinueBtn.isEnabled(), "Continue button is disabled");
	pharmacyPhone1.sendKeys(IHGUtil.createRandomNumericString(3));
	pharmacyPhone2.sendKeys(IHGUtil.createRandomNumericString(3));
	pharmacyPhone3.sendKeys(IHGUtil.createRandomNumericString(4));
	new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(popupContinueBtn));
	popupContinueBtn.click();
	log("Pharmacy is added");
	Thread.sleep(2000);// need to sleep because of modal disappearing time
	btnEditPharmacy.click();
	assertTrue(arePopupPageElementsPresent());
	log("Enter Pharmacy Details");
	String ab1 = testData.getProperty("updated.pharmacy.name") + IHGUtil.createRandomNumericString(4);
	pharmacyName.clear();
	pharmacyName.sendKeys(ab1);
	Thread.sleep(2000);
	popupContinueBtn.click();
	}
	
	public String getUpdatedPharmcyName() {
	String pharmcyName= rdoEditedPharmacyName.getText();
	btnContinue.click();
	return pharmcyName;
	}


}

