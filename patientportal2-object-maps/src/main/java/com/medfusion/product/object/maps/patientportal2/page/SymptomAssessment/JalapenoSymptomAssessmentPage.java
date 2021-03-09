package com.medfusion.product.object.maps.patientportal2.page.SymptomAssessment;

import java.util.ArrayList;
import java.util.List;

import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.patientportal2.utils.PortalUtil2;

@Deprecated //functionality is no longer supported (PP-819)
public class JalapenoSymptomAssessmentPage extends JalapenoMenu {

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

		public JalapenoSymptomAssessmentPage(WebDriver driver) {
				super(driver);
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
						PortalUtil2.setPortalFrame(driver);
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
				PortalUtil2.setPortalFrame(driver);
				txtSymptom.sendKeys(symptom);
				btnContinue.click();
		}

		/**
		 * answerDoYouHaveSymptom (cough) Click No
		 *
		 * @throws InterruptedException
		 */
		public void answerDoYouHaveSymptom() throws InterruptedException {
				Thread.sleep(16000);
				IHGUtil.PrintMethodName();
				PortalUtil2.setPortalFrame(driver);
				driver.switchTo().frame("IMHInterviewFrame");
				btnDontHaveCough.click();
				Thread.sleep(16000);
				PortalUtil2.setPortalFrame(driver);
		}

		public JalapenoHomePage clickHome() {
				IHGUtil.PrintMethodName();
				driver.switchTo().frame("IMHInterviewFrame");
				clickOnMenuHome();
				driver.switchTo().defaultContent();
				try {
						Thread.sleep(3000);
				} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				}
				return PageFactory.initElements(driver, JalapenoHomePage.class);
		}


		@Override
		public boolean areBasicPageElementsPresent() {
				ArrayList<WebElement> webElementList = new ArrayList<WebElement>();
				webElementList.add(dropDownProvider);
				return assessPageElements(webElementList);
		}

}
