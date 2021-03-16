// Copyright 2020 NXGN Management, LLC. All Rights Reserved.

package com.medfusion.product.object.maps.pss2.page.Appointment.Loginless;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.product.object.maps.pss2.page.Appointment.CancResc.PatientIdentificationPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;
import com.medfusion.product.object.maps.pss2.page.util.CommonMethods;

public class DismissPage extends PSS2MainPage {
	
	@FindBy(how = How.XPATH, using = "//*[@id='myModalsssloginpopup']/div/div/div[3]/button")
	private WebElement dismissBtn;	

	@FindBy(how = How.XPATH, using = "//*[@class='announcementmessage']/div")
	private WebElement popUpMessege;


	public DismissPage(WebDriver driver, String url) {
		super(driver, url);
	}

	CommonMethods commonMethods = new CommonMethods(driver);

	@Override
	public boolean areBasicPageElementsPresent() {
		return true;
	}

	public LoginlessPatientInformation clickDismiss() {
		commonMethods.highlightElement(dismissBtn);
		dismissBtn.click();
		return PageFactory.initElements(driver, LoginlessPatientInformation.class);
	}
	
	public String popUpMessage() {
		commonMethods.highlightElement(popUpMessege);
		String messege = popUpMessege.getText();
		return messege;
	}

	public HomePage clickDismisse() {
		commonMethods.highlightElement(dismissBtn);
		dismissBtn.click();
		return PageFactory.initElements(driver, HomePage.class);
	}
	
	public PatientIdentificationPage clickDismissToPI() {
		commonMethods.highlightElement(dismissBtn);
		dismissBtn.click();
		return PageFactory.initElements(driver, PatientIdentificationPage.class);
	}
	
	

}
