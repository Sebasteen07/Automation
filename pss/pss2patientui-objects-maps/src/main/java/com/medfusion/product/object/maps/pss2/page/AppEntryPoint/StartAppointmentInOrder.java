// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.AppEntryPoint;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.Appointment.Location.Location;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Provider.Provider;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.AppointmentPage;

public class StartAppointmentInOrder extends PSS2MainPage {

	@FindAll({@FindBy(xpath = "//a[@class='btn appointmentTypedashboardbtn' or @class='btn startingpoint-btn']")})
	private List<WebElement> startingWith;

	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Choose a starting point')]")
	private WebElement chooseStartPoint;
	
	@FindBy(how = How.XPATH, using = "//*[@id='locationRestrict']/div/div/div[2]/pre/div")
	private WebElement locationPopUp;


	public StartAppointmentInOrder(WebDriver driver) {
		super(driver);
	}

	public Provider selectFirstProvider(String selectOrderWith) {
		for (int i = 0; i < startingWith.size(); i++) {
			if (startingWith.get(i).getText().equalsIgnoreCase(selectOrderWith)) {
				log("Starting Point Selected is " + startingWith.get(i).getText());
				startingWith.get(i).click();
				return PageFactory.initElements(driver, Provider.class);
			}
		}
		log("Provider Not found");
		return null;
	}

	public AppointmentPage selectFirstAppointment(String selectOrderWith) throws InterruptedException {
		log("size of list=" + startingWith.size());
		log("selectOrderWith=" + selectOrderWith);
		for (int i = 0; i < startingWith.size(); i++) {
			if (startingWith.get(i).getText().equalsIgnoreCase(selectOrderWith)) {
				log("selectOrderWith=" + selectOrderWith);
				Thread.sleep(3000);
				startingWith.get(i).click();
				return PageFactory.initElements(driver, AppointmentPage.class);
			}
		}
		log("Appointment Not found");
		return null;
	}

	public Location selectFirstLocation(String selectOrderWith) {
		log("startingWith length " + startingWith.size());
		for (int i = 0; i < startingWith.size(); i++) {
			if (startingWith.get(i).getText().equalsIgnoreCase(selectOrderWith)) {
				startingWith.get(i).click();
				return PageFactory.initElements(driver, Location.class);
			}
		}
		log("Location Not found");
		return null;
	}

	public Boolean isstartPointpresent() {
		return chooseStartPoint.isDisplayed();
	}

	public String locationPopUp()
	{
		IHGUtil.waitForElement(driver,10 , locationPopUp);
		String popUpMessage=locationPopUp.getText();
		return popUpMessage;
	}
}
