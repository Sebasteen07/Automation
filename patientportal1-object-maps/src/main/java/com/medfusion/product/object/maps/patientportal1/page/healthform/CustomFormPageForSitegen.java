package com.medfusion.product.object.maps.patientportal1.page.healthform;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.patientportal1.utils.PortalUtil;
	/**
	 * 
	 * @author bbinisha
	 * @Date : 11/12/2013
	 */
public class CustomFormPageForSitegen extends BasePageObject {

	@FindBy( id = "custom_question_customsecond_section_0")
	private WebElement secondSectionFirstField;
	
	@FindBy(id = "continueWelcomePageButton")
	private WebElement saveAndContinue;
	
	@FindBy(id = "nextPageButton")
	private WebElement nextPage;
	
	@FindBy( id = "custom_question_customsecond_section_1_2")
	private WebElement scalingRadioButton;
	
	@FindBy( id = "custom_question_customthird_section_0")
	private WebElement thirdSectionFirstTextArea;
	
	@FindBy( xpath = ".//a[contains(text(),'Submit')]")
	private WebElement submitButton;

	@FindBy( xpath = ".//iframe[@title ='Forms']")
	private WebElement iframe;
	
	//Constructor
	public CustomFormPageForSitegen(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	/**
	 * Description : Fill out the custom form in patient portal.
	 * @param formName
	 * @throws Exception
	 */
	public void fillOutCustomForm(String formName) throws Exception {
		IHGUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		//IHGUtil.waitForElement(driver, 10, saveAndContinue);
		saveAndContinue.click();
		nextPage.click();
		PortalUtil.setquestionnarieFrame(driver);
		//IHGUtil.waitForElement(driver, 10, secondSectionFirstField);
		secondSectionFirstField.sendKeys("Better");
		PortalUtil.setquestionnarieFrame(driver);
		scalingRadioButton.click();
		nextPage.click();
		IHGUtil.waitForElement(driver, 10, thirdSectionFirstTextArea);
		thirdSectionFirstTextArea.sendKeys("Better");
		PortalUtil.setquestionnarieFrame(driver);
		IHGUtil.waitForElement(driver, 10, saveAndContinue);
		PortalUtil.setquestionnarieFrame(driver);
		nextPage.click();	
	}
	

	
	/**
	 * Description : Fill out the custom form in patient portal.
	 * @param formName
	 * @throws Exception
	 */
	public void fillOutCustomForm2(String formName) throws Exception {
		IHGUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		IHGUtil.waitForElement(driver, 10, saveAndContinue);
		saveAndContinue.click();
		Thread.sleep(3000);
		saveAndContinue.click();
		String errorText = driver.findElement(By.xpath(".//div[@id='errorContainer']/p")).getText();
		Assert.assertEquals(errorText.contains("Please fill in the following fields to continue: "),true, "Error text for filling all mandatory fields is not displayed.");
		PortalUtil.setquestionnarieFrame(driver);
		IHGUtil.waitForElement(driver, 10, secondSectionFirstField);
		secondSectionFirstField.sendKeys("White");
		PortalUtil.setquestionnarieFrame(driver);
		driver.findElement(By.id("custom_question_customsecond_section_1")).sendKeys("Testing");
		driver.findElement(By.id("custom_question_customsecond_section_2_0")).click();
		driver.findElement(By.id("custom_question_customsecond_section_3_1")).click();
		IHGUtil.waitForElement(driver, 10, saveAndContinue);
		saveAndContinue.click();
		IHGUtil.waitForElement(driver, 10, thirdSectionFirstTextArea);
		thirdSectionFirstTextArea.sendKeys("White");
		PortalUtil.setquestionnarieFrame(driver);
		driver.findElement(By.id("custom_question_customthird_section_1")).sendKeys("Testing Form");
		driver.findElement(By.id("custom_question_customthird_section_2_0")).click();
		driver.findElement(By.id("custom_question_customthird_section_3_0")).click();
		IHGUtil.waitForElement(driver, 10, saveAndContinue);
		PortalUtil.setquestionnarieFrame(driver);
		saveAndContinue.click();
		Thread.sleep(3000);
		
	}
	
	
	/**
	 * Description : Verify whether the form is displayed in the Health forms page.
	 * @param formName
	 * @return
	 * @throws Exception
	 */
	public boolean isFormDisplayedAsPDF() throws Exception {
		String xpath = "//a[contains(text(), 'View as PDF')]";
		
		//IHGUtil.PrintMethodName();
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iframe");
		WebElement formInPDF = driver.findElement(By.xpath(xpath));
		return PortalUtil.isExistsElement(driver, formInPDF);
	}
	
	/*
	 * Last saved on
	 */
	public boolean isLastSaveDate(String formName) throws Exception
	{
		IHGUtil.PrintMethodName();
		Thread.sleep(3000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iframe");
		WebElement lastSaveDate = driver.findElement(By.className("completedDate"));
		return PortalUtil.isExistsElement(driver, lastSaveDate);
		
	}
	
	/**
	 * Description : Submit the completed form.
	 * @throws Exception
	 */
	public void submitCustomForm() throws Exception {
		PortalUtil.setquestionnarieFrame(driver);
		try{
		IHGUtil.waitForElement(driver, 30, submitButton);
		submitButton.click();
		}catch (Exception e) {
			log("Exception Occured while submitting the form");
		}
		Thread.sleep(8000);
	}
	
}
