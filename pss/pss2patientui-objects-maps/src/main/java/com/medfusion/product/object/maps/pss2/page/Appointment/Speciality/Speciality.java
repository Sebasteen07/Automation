package com.medfusion.product.object.maps.pss2.page.Appointment.Speciality;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.product.object.maps.pss2.page.AppEntryPoint.StartAppointmentInOrder;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;

public class Speciality extends PSS2MainPage {

	@FindAll({@FindBy(css = ".btn")})
	private List<WebElement> selectSpecialityList;

	@FindBy(how = How.ID, using = "specailtydashboard")
	private WebElement searchSpeciality;

	public Speciality(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		return true;
	}

	public StartAppointmentInOrder selectSpeciality(String specialityName) {
		for (int i = 0; i < selectSpecialityList.size(); i++) {
			if (selectSpecialityList.get(i).getText().contains(specialityName)) {
				selectSpecialityList.get(i).click();
				return PageFactory.initElements(driver, StartAppointmentInOrder.class);
			}
		}
		log("Speciality not found.");
		return null;
	}
}