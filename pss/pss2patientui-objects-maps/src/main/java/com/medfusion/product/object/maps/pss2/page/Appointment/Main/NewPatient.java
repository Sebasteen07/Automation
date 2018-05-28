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
	public WebElement inputFirstName;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div/div[1]/div[3]/form/div[1]/div[2]/div/input")
	public WebElement inputLastName;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"datetimepicker9\"]/span")
	public WebElement selectDatePicker;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"sel1\"]")
	public WebElement selectGender;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div/div[1]/div[3]/form/div[3]/div[1]/div/input")
	public WebElement inputPrimaryPhone;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div/div[1]/div[3]/form/div[3]/div[2]/div/input")
	public WebElement inputSecondaryPhone;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div/div[1]/div[3]/form/div[4]/div[1]/div/input")
	public WebElement inputEmail;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div/div[1]/div[3]/form/div[4]/div[2]/div/input")
	public WebElement inputStreetl;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div/div[1]/div[3]/form/div[5]/div[1]/div/input")
	public WebElement inputCity;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div/div[1]/div[3]/form/div[5]/div[2]/div/select")
	public WebElement selectState;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"picker\"]")
	public WebElement inputZip;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div/div[1]/div[3]/form/div[6]/div/div[1]/div/a/div/span")
	public WebElement buttonCancel;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div/div[1]/div[3]/form/div[6]/div/div[2]/div/a/span")
	public WebElement buttonNext;
	
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