package com.medfusion.product.object.maps.pss2.page.Appointment.Location;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.Appointment.DateTime.AppointmentDateTime;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Provider.Provider;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.AppointmentPage;


public class Location extends PSS2MainPage {

	@FindAll({@FindBy(className = "locationlinkclick")})
	public List<WebElement> locationList;

	public Location(WebDriver driver) {
		super(driver);
	}

	public AppointmentPage selectAppointment(String locationName) {
		log("location " + locationName);
		for (int i = 0; i < locationList.size(); i++) {
			// log("timeText.getText() " + locationList.get(i).getText());
			if (locationList.get(i).getText().contains(locationName)) {
				log("Location of user found at " + locationList.get(i).getText());
				javascriptClick(locationList.get(i));
				return PageFactory.initElements(driver, AppointmentPage.class);
			} else {
				//
			}
		}
		return null;
	}


	public Provider searchProvider(String locationName) {
		for (int i = 0; i < locationList.size(); i++) {
			if (locationList.get(i).getText().contains(locationName)) {
				javascriptClick(locationList.get(i));
				return PageFactory.initElements(driver, Provider.class);
			} else {
				//
			}
		}
		return null;
	}

	public AppointmentDateTime selectDatTime(String dateTime) {
		return PageFactory.initElements(driver, AppointmentDateTime.class);
	}
	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		// webElementsList.add(locationList.get(0));
		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());

	}

}