package com.intuit.ihg.product.phr.page.phrHealthInformationPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class LaboratoryAndTestResultPage extends BasePageObject{

	private static String title= "Laboratory and Test Results";
	private static String noResultText = "No Test Results Added";

	@FindBy( xpath = "//*[@class='content_hdr']")
	private WebElement laboratoryAndTestResultHeader;

	@FindBy( id = "month")
	private WebElement monthDropDwn;

	@FindBy( id = "day")
	private WebElement dayDropDwn;

	@FindBy( id = "year")
	private WebElement yearDropDwn;

	@FindBy( id = "testName")
	private WebElement testNameField;

	@FindBy(id = "resultInterpretation")
	private WebElement resultInterpretationDropDwn;

	@FindBy(id ="testResult")
	private WebElement resultField;

	@FindBy( xpath=".//select[@name='resultUnits']")
	private WebElement resultUnitsDropDwn;

	@FindBy(id ="comments")
	private WebElement commentsField;

	@FindBy(xpath = ".//input[@value = 'Save Test Results']")
	private WebElement saveTestResultButton;

	@FindBy(xpath = "//input[@value='Add Test Results']")
	private WebElement addTestResultButton;


	public LaboratoryAndTestResultPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public boolean checkLaboratoryAndTestResult() throws Exception {
		IHGUtil.PrintMethodName();
		Thread.sleep(5000);
		return laboratoryAndTestResultHeader.getText().contains(title);
	}

	public void addLaboratoryTestResult(String testName, String resultInterpretation) {
		WebElement tableList = driver.findElement(By.xpath(".//*[@id='custom_table_list_id']/tbody/tr/td"));
		if(tableList.getText().contains(noResultText)) {
			addValuesForLaboratoryResult(testName, resultInterpretation);
		} else if(addTestResultButton.isDisplayed()) {
			addTestResultButton.click();
			addValuesForLaboratoryResult(testName, resultInterpretation);
		}
	}

	private void addValuesForLaboratoryResult(String testName, String resultInterpretation) {
		Select selMonth = new Select(monthDropDwn);
		selMonth.selectByValue("01");
		Select selDay = new Select(dayDropDwn);
		selDay.selectByValue("01");
		Select selYear = new Select(yearDropDwn);
		selYear.selectByIndex(1);

		testNameField.sendKeys(testName);
		Select selResult = new Select(resultInterpretationDropDwn);
		selResult.selectByValue(resultInterpretation);
		saveTestResultButton.click();
	}

	public void removeTestResult(String test) throws Exception {
		Thread.sleep(3000);
		log("removing all results present");
		driver.switchTo().defaultContent();

//		int row_Size = driver.findElements(By.xpath(".//*[@id='custom_table_list_id']/tbody/tr/td/a/span")).size();
		int table_Size = driver.findElements(By.xpath(".//*[@id='custom_table_list_id']/tbody/tr")).size();
//		int iValue = (table_Size-row_Size)+1;

		for( int i=2; i<=table_Size; i++) { 
			WebElement  ele = driver.findElement(By.xpath(".//*[@id='custom_table_list_id']/tbody/tr["+i+"]/td/a/span"));
			log("test :"+test);
			if (ele.getText().contains(test)) {
				try {
					driver.findElement(By.xpath(".//*[@id='custom_table_list_id']/tbody/tr["+i+"]/td/a/img")).click();
				} catch (Exception e) {
					driver.findElement(By.xpath(".//*[@id='custom_table_list_id']/tbody/tr["+i+"]/td/a/img")).click();
				}
				driver.switchTo().alert().accept();
				table_Size--;
				Thread.sleep(10000);
			}else {
				continue;
			}

		}
	}



}
