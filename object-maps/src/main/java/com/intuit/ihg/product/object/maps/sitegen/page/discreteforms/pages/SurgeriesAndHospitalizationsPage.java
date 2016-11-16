package com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SurgeriesAndHospitalizationsPage extends ConfiguratorFormPage {

	@FindBy(id = "hide_surgerieshospitalizations_section_check")
	private WebElement hideSurgerieshospitalizationsSectionCheck;

	@FindBy(id = "surgerieshospitalizations_anythingelse_line")
	private WebElement surgerieshospitalizationsCommentsCheck;


	public SurgeriesAndHospitalizationsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
}

