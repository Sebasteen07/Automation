package com.intuit.ihg.product.portal.page.healthform;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.portal.utils.PortalUtil;
import com.intuit.ihg.product.practice.utils.PracticeUtil;
	/**
	 * 
	 * @author bbinisha
	 * @Date : 11/12/2013
	 */
public class CustomFormPageForSitegen extends BasePageObject {

	@FindBy( id = "custom_question_customsecond_section_0")
	private WebElement howDoYouFeelField;
	
	@FindBy(id = "nextPageButton")
	private WebElement saveAndContinue;
	
	@FindBy( id = "custom_question_customsecond_section_1_2")
	private WebElement scalingRadioButton;
	
	@FindBy( id = "custom_question_customthird_section_0")
	private WebElement howDoYouFeelTextArea;
	
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
		IHGUtil.waitForElement(driver, 10, saveAndContinue);
		saveAndContinue.click();
		PortalUtil.setquestionnarieFrame(driver);
		IHGUtil.waitForElement(driver, 10, howDoYouFeelField);
		howDoYouFeelField.sendKeys("Better");
		PortalUtil.setquestionnarieFrame(driver);
		scalingRadioButton.click();
		IHGUtil.waitForElement(driver, 10, saveAndContinue);
		saveAndContinue.click();
		IHGUtil.waitForElement(driver, 10, howDoYouFeelTextArea);
		howDoYouFeelTextArea.sendKeys("Better");
		PortalUtil.setquestionnarieFrame(driver);
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
	public boolean isFormDisplayedAsPDF(String formName) throws Exception {
		IHGUtil.PrintMethodName();
		Thread.sleep(3000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iframe");
		WebElement formInPDF = driver.findElement(By.xpath(".//span[text()='"+formName+"']/ancestor::li/table/tbody/tr/td/a[@class='pdf text']"));
		return PracticeUtil.isExistsElement(driver, formInPDF);
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
