package com.medfusion.product.object.maps.pss2.page.AppointmentType;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.Appointment.DateTime.AppointmentDateTime;
import com.medfusion.product.object.maps.pss2.page.Appointment.Location.Location;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Provider.Provider;

public class AppointmentPage extends PSS2MainPage {

	@FindBy(how = How.ID, using = "searchappointmenttype1")
	private WebElement searchAppointment;

	@FindBy(how = How.CSS, using = ".btn")
	private WebElement selectAppointment;

	@FindBy(how = How.CSS, using = ".pull-right.gotobutton")
	private WebElement gotoNextStep;

	@FindAll({@FindBy(xpath = ".//a[@class='btn appointmentType-btn ']/span")})
	public List<WebElement> appointmentTypeList;

	public AppointmentPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		// webElementsList.add(appointmentTypeList.get(0));
		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}

	public AppointmentDateTime selectTypeOfAppointment(String appointmentType, Boolean isPopUpSelected) {
		searchAppointment.sendKeys(appointmentType);
		IHGUtil.waitForElement(driver, 30, selectAppointment);
		javascriptClick(selectAppointment);
		IHGUtil.waitForElement(driver, 30, gotoNextStep);
		if (isPopUpSelected) {
			gotoNextStep.click();
		}
		return PageFactory.initElements(driver, AppointmentDateTime.class);
	}

	public Provider selectTypeOfProvider(String providerConfig, Boolean isPopUpSelected) {

		for (int i = 0; i < appointmentTypeList.size(); i++) {
			if (appointmentTypeList.get(i).getText().contains(providerConfig)) {
				javascriptClick(appointmentTypeList.get(i));
				return PageFactory.initElements(driver, Provider.class);
			}
		}
		log("no matching appointment found ");
		return null;
	}

	public Location selectTypeOfLocation(String locationConfig, Boolean isPopUpSelected) {

		for (int i = 0; i < appointmentTypeList.size(); i++) {
			if (appointmentTypeList.get(i).getText().contains(locationConfig)) {
				javascriptClick(appointmentTypeList.get(i));
				return PageFactory.initElements(driver, Location.class);
			}
		}
		log("no matching appointment found ");
		return null;
	}
}
