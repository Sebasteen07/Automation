package com.medfusion.product.object.maps.pss2.page.Appointment.Provider;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.Appointment.DateTime.AppointmentDateTime;
import com.medfusion.product.object.maps.pss2.page.Appointment.Location.Location;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.AppointmentPage;

public class Provider extends PSS2MainPage {

	@FindAll({@FindBy(css = ".btn")})
	public List<WebElement> providerList;

	public Provider(WebDriver driver) {
		super(driver);
		log("Provider called...");
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(providerList.get(0));
		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}

	public Location selectLocation(String providerName) {
		log("in selectLocation providerList" + providerName);
		for (int i = 0; i <= providerList.size(); i++) {
			log(providerList.get(i).getText() + " match " + providerList.get(i).getText().equalsIgnoreCase(providerName));
			if (providerList.get(i).getText().equalsIgnoreCase(providerName)) {
				providerList.get(i).click();
				return PageFactory.initElements(driver, Location.class);
			}
		}
		return null;
	}

	public AppointmentPage selectAppointment(String providerName) {
		log("size= " + providerList.size());
		log("Text= " + providerList.get(0).getText());
		for (int i = 0; i <= providerList.size(); i++) {
			log(providerList.get(i).getText() + " match " + providerName + " = " + providerList.get(i).getText().equalsIgnoreCase(providerName));
			if (providerList.get(i).getText().equalsIgnoreCase(providerName)) {
				providerList.get(i).click();
				return PageFactory.initElements(driver, AppointmentPage.class);
			}
		}
		return null;
	}

	public AppointmentDateTime selectDateTime(String providerName) {
		log("size= " + providerList.size());
		log("Text= " + providerList.get(0).getText());
		for (int i = 0; i <= providerList.size(); i++) {
			log(providerList.get(i).getText() + " match " + providerList.get(i).getText().equalsIgnoreCase(providerName));
			if (providerList.get(i).getText().equalsIgnoreCase(providerName)) {
				providerList.get(i).click();
				return PageFactory.initElements(driver, AppointmentDateTime.class);
			}
		}
		return null;
	}
}