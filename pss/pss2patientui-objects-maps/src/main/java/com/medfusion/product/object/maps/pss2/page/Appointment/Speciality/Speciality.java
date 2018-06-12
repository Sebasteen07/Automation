package com.medfusion.product.object.maps.pss2.page.Appointment.Speciality;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;

public class Speciality extends PSS2MainPage {

	public Speciality(WebDriver driver) {
		super(driver);
	}

	@FindAll({@FindBy(className = "btn specialtybtndashboard handle-text-Overflow")})
	public List<WebElement> selectSpecialityList;

	@Override
	public boolean areBasicPageElementsPresent() {
		return true;
	}

}