// Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.ConfirmationPage;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;
import com.medfusion.product.object.maps.pss2.page.Scheduled.ScheduledAppointment;
import com.medfusion.product.object.maps.pss2.page.Scheduled.ScheduledAppointmentAnonymous;
import com.medfusion.product.object.maps.pss2.page.util.CommonMethods;

public class ConfirmationPage extends PSS2MainPage {

	@FindAll({@FindBy(css = ".value-class")})
	private List<WebElement> appointmentScheduledDetails;

	@FindBy(how = How.XPATH, using = "//a[@id='everythingiscorrectbutton']")
	private WebElement buttonAllGood;

	@FindBy(how = How.XPATH, using = "//body/div[@id='root']/div[1]/div[1]/div[1]/div[4]/div[2]/div[1]/div[3]/div[1]/div[3]/div[1]/div[1]/div[1]")
	private WebElement DateConfirmation;
	
	public ConfirmationPage(WebDriver driver) {
		super(driver);
	}

	CommonMethods commonMethods = new CommonMethods(driver);

	@Override
	public boolean areBasicPageElementsPresent() {
		return true;
	}

	public ScheduledAppointment appointmentConfirmed() {
		commonMethods.highlightElement(buttonAllGood);
		buttonAllGood.click();
		return PageFactory.initElements(driver, ScheduledAppointment.class);
	}

	public ScheduledAppointmentAnonymous appointmentConfirmedAnonymous() {
		commonMethods.highlightElement(buttonAllGood);
		buttonAllGood.click();
		return PageFactory.initElements(driver, ScheduledAppointmentAnonymous.class);
	}

	public List<WebElement> getAppointmentDetails() {
		return appointmentScheduledDetails;
	}
	public String dateConfirm()
	{
		String datetext=DateConfirmation.getText();
		String replace1=datetext.replace(",", "");
		String nextDate1=replace1.substring(00,16);
		return nextDate1;
		
	}
	public HomePage confirmationPage()
	{
		
		return PageFactory.initElements(driver, HomePage.class);
		
	}

}
