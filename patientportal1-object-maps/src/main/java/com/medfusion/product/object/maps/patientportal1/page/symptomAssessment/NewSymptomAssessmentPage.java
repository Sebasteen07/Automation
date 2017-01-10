package com.medfusion.product.object.maps.patientportal1.page.symptomAssessment;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.patientportal1.utils.PortalUtil;

public class NewSymptomAssessmentPage extends BasePageObject {

	public static final String PAGE_NAME = "New Symptom Assessment Page";

	@FindBy(xpath = "//select[@name='providers']")
	private WebElement dropDownProvider;

	@FindBy(how = How.NAME, using = "submitButton:submit")
	private WebElement btnSubmit;

	@FindBy(how = How.NAME, using = "saConditionInputWrapper:_body:saConditionInput")
	private WebElement txtSymptom;

	@FindBy(how = How.NAME, using = "submitButtonWrapper:_body:submitButton:submit")
	private WebElement btnContinue;
	
	@FindBy(xpath = "//button[@id='Button2'][4]")
	private WebElement btnDontHaveCough;

	@FindBy(css = ".paperPad>div>p")
	private WebElement verifyText;


	public NewSymptomAssessmentPage(WebDriver driver) {
		super(driver);
	}


	/**
	 * Gives an indication if the page loaded as expected
	 * 
	 * @return true or false
	 */
	public boolean isPageLoaded() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		boolean result = false;
		try {
			result = dropDownProvider.isDisplayed();
		} catch (Exception e) {
			// Catch element not found error
		}
		return result;
	}


	/**
	 * selectProvider select a provider from drop down and submit
	 * 
	 * @param doctor
	 */
	/*
	 * public void selectProvider(String doctor) { IHGUtil.PrintMethodName(); PortalUtil.setPortalFrame(driver); Select provider=new Select(dropDownProvider);
	 * provider.selectByVisibleText(doctor); btnSubmit.click(); }
	 */
	public void selectProvider(String doctor) {
		try {
			String vals = "", valsex = "";
			boolean flag = true;
			IHGUtil.PrintMethodName();
			PortalUtil.setPortalFrame(driver);
			List<WebElement> list = driver.findElements(By.xpath("//select[@name='providers']/option"));
			for (WebElement li : list) {
				int count = 1;
				String strval = li.getText();
				String[] strArray = strval.split(",");
				for (String str : strArray) {
					String val = str.trim();
					vals = vals + "," + val;

				}

				if (flag) {
					String[] strArrayex = doctor.split(",");
					for (String str : strArrayex) {
						String valex = str.trim();
						valsex = valsex + "," + valex;

					}
					valsex = Left(valsex, valsex.length());
				}

				if (vals.contains(valsex)) {
					Select provider = new Select(dropDownProvider);
					provider.selectByIndex(count);
					break;
				}
				flag = false;
				count++;
			}
			btnSubmit.click();
		} catch (Exception e) {
			btnSubmit.click();
		}

	}

	public static String Left(String text, int length) {
		return text.substring(1, length);
	}

	/**
	 * typeYourSymptom Type your Symptom and submit
	 * 
	 * @param symptom
	 */
	public void typeYourSymptom(String symptom) {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		txtSymptom.sendKeys(symptom);
		btnContinue.click();
	}

	/**
	 * answerDoYouHaveSymptom (cough) Click No
	 * 
	 * @throws InterruptedException
	 *
	 */
	public void answerDoYouHaveSymptom() throws InterruptedException {
		Thread.sleep(16000);
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		driver.switchTo().frame("IMHInterviewFrame");
		btnDontHaveCough.click();
		Thread.sleep(16000);
		PortalUtil.setPortalFrame(driver);
		/* log("============+++++++++"+verifyText.getText()); */
	}

}
