// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.Appointment.HomePage;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;

public class ChoodStartingPoint extends PSS2MainPage {

	@FindAll({@FindBy(xpath = "//div[@id='startingpointdashboard']//div//a")})
	private List<WebElement> chooseStartPoint;

	public ChoodStartingPoint(WebDriver driver) {
		super(driver);
	}

	public void chooseRule(String choosePoint) {
		log(chooseStartPoint.size() + " ChoosePoint ");
		for (int i = 0; i < chooseStartPoint.size(); i++) {
			if (chooseStartPoint.get(i).getText().equalsIgnoreCase(choosePoint)) {
				chooseStartPoint.get(i).click();
			}
		}
	}

}
