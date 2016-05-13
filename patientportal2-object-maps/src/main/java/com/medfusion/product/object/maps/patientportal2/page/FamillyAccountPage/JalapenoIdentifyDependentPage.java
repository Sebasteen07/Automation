package com.medfusion.product.object.maps.patientportal2.page.FamillyAccountPage;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;


public class JalapenoIdentifyDependentPage extends BasePageObject{
	
	@FindBy(how = How.XPATH, using = "(//input[@id='postalCode'])[2]")
	private WebElement inputZipCode;
	
	@FindBy(how = How.XPATH, using = "(//select[@id='birthDate_month'])[2]")
	private WebElement inputDOBMonth;

	@FindBy(how = How.XPATH, using = "(//input[@id='birthDate_day'])[2]")
	private WebElement inputDOBDay;

	@FindBy(how = How.XPATH, using = "(//input[@id='birthDate_year'])[2]")
	private WebElement inputDOBYear;
	
	@FindBy(how = How.XPATH, using = "(//button[@id='nextStep'])[2]")
	private WebElement buttonContinue;
	
	
	public JalapenoIdentifyDependentPage(WebDriver driver, String url) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.get(url);
		log("Go to URL: " + url);
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}
	
	public boolean assessElements() {	
		IHGUtil.PrintMethodName();
		
		log("Wait for elements");
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(inputZipCode);
		webElementsList.add(inputDOBMonth);
		webElementsList.add(inputDOBDay);
		webElementsList.add(inputDOBYear);
		webElementsList.add(buttonContinue);
		
		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}
		
	public void fillPatientIdentifyInfo (String zipCode, String month, String day, String year) {
		IHGUtil.PrintMethodName();
			
		log("Fill inputs");
		this.inputZipCode.sendKeys(zipCode);
		Select dobMonth = new Select(inputDOBMonth);
		dobMonth.selectByValue(month);
		this.inputDOBDay.sendKeys(day);
		this.inputDOBYear.sendKeys(year);
	}
	
	public JalapenoCreateGuardianPage continueToCreateGuardianPage(WebDriver driver) {
		IHGUtil.PrintMethodName();
		this.buttonContinue.click();
		return PageFactory.initElements(driver, JalapenoCreateGuardianPage.class);
	}
	
	
}