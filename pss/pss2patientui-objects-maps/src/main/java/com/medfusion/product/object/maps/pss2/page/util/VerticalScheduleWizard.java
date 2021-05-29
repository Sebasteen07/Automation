// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.util;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;

public class VerticalScheduleWizard extends PSS2MainPage {

	@FindAll({@FindBy(css = ".list-group-item")})
	private List<WebElement> verticalFlowList;

	public VerticalScheduleWizard(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, VerticalScheduleWizard.class);
	}

	public List<WebElement> getVerticalFlowList() {
		return verticalFlowList;
	}

}
