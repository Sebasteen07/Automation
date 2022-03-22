// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.AppointmentType;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.Appointment.DateTime.AppointmentDateTime;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Provider.Provider;

public class AppointmentQuestionsPage extends PSS2MainPage {
	@FindBy(how = How.XPATH, using = "//*[@id='selectOptions']/label/span")
	private List<WebElement> selectOption;
	
	@FindBy(how = How.XPATH, using = "//*[@id='continuebtn']/span")
	private WebElement continueBtn;
	
	@FindBy(how = How.XPATH, using = "//*[@id='gobackbtn']/span")
	private WebElement backBtn;
	
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Go to next step')]")
	private WebElement gotoNextStep;
	
	public AppointmentQuestionsPage(WebDriver driver) {
		super(driver);
	}
	
	public AppointmentDateTime selectAnswerFromOptions(String decisionTreeAnswer, Boolean isPopUpSelected) {
		log("options available " + selectOption.size());
		for (int i = 0; i < selectOption.size(); i++) {
			if (selectOption.get(i).getText().contains(decisionTreeAnswer)) {
				selectOption.get(i).click();
				continueBtn.click();
				goToNextStep(isPopUpSelected);
				return PageFactory.initElements(driver, AppointmentDateTime.class);
			}
		}
		log("No options matching");
		return PageFactory.initElements(driver, AppointmentDateTime.class);
	}
	
	public Provider selectAnswerFromQue(String decisionTreeAnswer, Boolean isPopUpSelected) {
		log("options available " + selectOption.size());
		for (int i = 0; i < selectOption.size(); i++) {
			if (selectOption.get(i).getText().contains(decisionTreeAnswer)) {
				selectOption.get(i).click();
				continueBtn.click();
				goToNextStep(isPopUpSelected);
				return PageFactory.initElements(driver, Provider.class);
			}
		}
		log("No options matching");
		return PageFactory.initElements(driver, Provider.class);
	}
	
	public void goToNextStep(Boolean isPopUpSelected) {
		if (isPopUpSelected) {
			log("is popup");
			IHGUtil.waitForElement(driver, 60, gotoNextStep);
			jse.executeScript("arguments[0].setAttribute('style', 'background: white; border: 5px solid blue;');", gotoNextStep);
			gotoNextStep.click();
			log("successfully clicked on next step");

		}
	}

}
