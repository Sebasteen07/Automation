 // Copyright 2021 NXGN Management, LLC. All Rights Reserved.
 package com.medfusion.product.object.maps.patientportal2.page.MedicationsPage;
 
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import java.io.IOException;
import java.util.ArrayList;
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
 

	@FindBy(how=How.XPATH,using="//span[@class='pharmacy-radio-button']")
 	private WebElement radioPharmacy;
 	
 	@FindBy(how = How.ID, using = "add-new-pharmacy")
 	private WebElement addPharmacy;
 	
 	@FindBy(how=How.XPATH,using="//div[@class='form-buttons ng-scope']/button[@type='button']")
	private WebElement btnBack;
	
	@FindBy(how=How.XPATH,using="//button[@class='btn btn-primary ng-binding']")
	private WebElement btnContinue;
	
	@FindBy(how = How.XPATH, using = "//a[@class='add-pharmacy-options ng-binding'][2]")
	private WebElement addYourPharmacy;
	
	@FindBy(how = How.XPATH, using = "//div[@id='addPharmacyOptions']")
	private WebElement pharmacyPopup;
	
	@FindBy(how = How.ID, using = "nameOfPharmacy")
	private WebElement pharmacyName;
	
	@FindBy(how = How.ID, using = "phoneNumber")
	private WebElement pharmacyPhone;
	
	@FindBy(how = How.ID, using = "faxNumber")
	private WebElement pharmacyFax;

	@FindBy(how = How.ID, using = "address")
	private WebElement pharmacyAddress;
	
	@FindBy(how = How.ID, using = "city")
	private WebElement pharmacyCity;
	
	@FindBy(how = How.XPATH, using = "//input[@title='State']")
	private WebElement pharmacyState;
	
	@FindBy(how = How.ID, using = "zipcode")
	private WebElement pharmacyZip;
	
	@FindBy(how = How.XPATH, using = "//div[@class='modal-button']/button[@class='btn btn-secondary ng-binding']")
	private WebElement popupBackbtn;	
	
	@FindBy(how = How.XPATH, using = "//div[@class='modal-button']/button[@class='btn btn-primary ng-binding']")
	private WebElement popupContinueBtn;
	
	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(btnBack);
		webElementsList.add(btnContinue);
		webElementsList.add(addPharmacy);
		return assessPageElements(webElementsList);
	}
	
	private boolean arePopupPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		
		webElementsList.add(pharmacyName);
		webElementsList.add(pharmacyPhone);
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
 	
	public void addNewPharmacy(WebDriver driver) throws IOException, InterruptedException
	{
		PropertyFileLoader testData = new PropertyFileLoader();
		IHGUtil.PrintMethodName();
		
		log("Click on Add a Pharmacy button");
		Thread.sleep(2000);
		addPharmacy.click();
		log("Click on Add Your Pharmacy button from the popup");
		addYourPharmacy.click();
		
		log("Verify all the popup elements are present");
		assertTrue(arePopupPageElementsPresent());
		
		log("Enter Pharmacy Details");
		pharmacyName.sendKeys(testData.getProperty("pharmacyName")+ IHGUtil.createRandomNumericString(4));
		pharmacyFax.sendKeys(IHGUtil.createRandomNumericString(10));
		pharmacyAddress.sendKeys(testData.getProperty("Address1"));
		pharmacyCity.sendKeys(testData.getProperty("City"));
		pharmacyState.sendKeys(testData.getProperty("State"));
		pharmacyState.sendKeys(Keys.ENTER);
		pharmacyZip.sendKeys(testData.getProperty("ZipCode"));
		
		log("Verifying continue button is disabled since Phone number is mandatory");
		assertFalse(popupContinueBtn.isEnabled(), "Continue button is disabled");
		
		pharmacyPhone.sendKeys(IHGUtil.createRandomNumericString(10));
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(popupContinueBtn));
		popupContinueBtn.click();
		log("Pharmacy is added");	
		Thread.sleep(2000);// need to sleep because of modal disappearing time

		btnContinue.click();
		
	}	
 	
 }
