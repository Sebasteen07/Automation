// Copyright 2020 NXGN Management, LLC. All Rights Reserved.

package com.medfusion.product.object.maps.pss2.page.Appointment.Main;

import java.util.ArrayList;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.medfusion.common.utils.IHGUtil;

public class NewPatient extends PSS2MainPage {

	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div/div[1]/div[3]/form/div[1]/div[1]/div/input")
	private WebElement inputFirstName;

	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div/div[1]/div[3]/form/div[1]/div[2]/div/input")
	private WebElement inputLastName;

	@FindBy(how = How.XPATH, using = "//*[@id=\"datetimepicker9\"]/span")
	private WebElement selectDatePicker;

	@FindBy(how = How.XPATH, using = "//*[@id=\"sel1\"]")
	private WebElement selectGender;

	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div/div[1]/div[3]/form/div[3]/div[1]/div/input")
	private WebElement inputPrimaryPhone;

	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div/div[1]/div[3]/form/div[3]/div[2]/div/input")
	private WebElement inputSecondaryPhone;

	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div/div[1]/div[3]/form/div[4]/div[1]/div/input")
	private WebElement inputEmail;

	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div/div[1]/div[3]/form/div[4]/div[2]/div/input")
	private WebElement inputStreetl;

	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div/div[1]/div[3]/form/div[5]/div[1]/div/input")
	private WebElement inputCity;

	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div/div[1]/div[3]/form/div[5]/div[2]/div/select")
	private WebElement selectState;

	@FindBy(how = How.XPATH, using = "//*[@id=\"picker\"]")
	private WebElement inputZip;

	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div/div[1]/div[3]/form/div[6]/div/div[1]/div/a/div/span")
	private WebElement buttonCancel;

	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div/div[1]/div[3]/form/div[6]/div/div[2]/div/a/span")
	private WebElement buttonNext;

	public NewPatient(WebDriver driver) {
		super(driver);
		jse = (JavascriptExecutor) driver;
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(buttonNext);
		webElementsList.add(inputZip);
		webElementsList.add(inputCity);
		webElementsList.add(inputEmail);
		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}
}
