// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.

package com.medfusion.product.object.maps.pss2.page.Appointment.Loginless;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.Appointment.CancResc.PatientIdentificationPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;
import com.medfusion.product.object.maps.pss2.page.util.CommonMethods;

public class DismissPage extends PSS2MainPage {
	
	@FindBy(how = How.XPATH, using = "//*[@id='myModalsssloginpopup']/div/div/div[3]/button")
	private WebElement dismissBtn;	
	
	@FindBy(how = How.XPATH, using = "//div[4]/div[1]/div[1]/div[2]/pre[1]/div[1]")
	private WebElement greetingMsg;	

	@FindBy(how = How.XPATH, using = "//*[@class='announcementmessage']/div")
	private WebElement popUpMessege;	
	
	@FindBy(how = How.XPATH, using = "//span[normalize-space()='Link is currently unavailable for the practice.']")
	private WebElement linkUnavailableMsg;

	public DismissPage(WebDriver driver, String url) {
		super(driver, url);
	}

	CommonMethods commonMethods = new CommonMethods(driver);

	public LoginlessPatientInformation clickDismiss() {
		IHGUtil.waitForElement(driver, 15, dismissBtn);
		commonMethods.highlightElement(dismissBtn);
		dismissBtn.click();
		return PageFactory.initElements(driver, LoginlessPatientInformation.class);
	}
	
	public String getDismissText() {
		IHGUtil.waitForElement(driver, 15, greetingMsg);
		commonMethods.highlightElement(greetingMsg);
		String dismissText=greetingMsg.getText();
		return dismissText;		
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
	
	public String verifyErrorPage() {
		commonMethods.highlightElement(linkUnavailableMsg);
		return linkUnavailableMsg.getText();
	}
	
	public String getGreetingsText(String greetingsText) {
		WebElement dismissText = driver.findElement(By.xpath("//div[text()='"+greetingsText+"']"));
		commonMethods.highlightElement(dismissText);
		String getGreetingsText=dismissText.getText();
		return getGreetingsText;		
	}

}
