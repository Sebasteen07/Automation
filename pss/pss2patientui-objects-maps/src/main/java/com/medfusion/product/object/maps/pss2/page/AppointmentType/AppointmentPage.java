// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.AppointmentType;

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
import com.medfusion.product.object.maps.pss2.page.util.CommonMethods;

public class AppointmentPage extends PSS2MainPage {

	@FindBy(how = How.XPATH, using = "//*[@class='form-control appointtype-search-control form-control-lg' or @id='searchappointmenttype1']")
	private WebElement searchAppointment;

	@FindBy(how = How.XPATH, using = "//*[@class='btn' or @class='appointtypewidthbtn' or @class='col-sm-6 col-xs-12 appointtypewidthbtn' or @class='btn appointmentType-btn handle-text-Overflow outer-div']")
	private WebElement selectAppointment;
	
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Go to next step')]")
	private WebElement gotoNextStep;

	@FindAll({@FindBy(xpath = "//div//button[@class='btn appointmentType-btn handle-text-Overflow outer-div']")})
	private List<WebElement> appointmentTypeList;
	
	@FindBy(how = How.XPATH, using = "//div[4]/div[2]/div[4]/div[1]/div[2]/div[1]/div[1]/div[2]")
	private WebElement preventApptSchedPopUp;
	
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'The practice does not allow this appointment to be scheduled within')]")
	private WebElement preventApptSchedPopUpMsg;
	
	@FindBy(how = How.XPATH, using = "(//a[@class='custombuttonexist'])[1]")
	private WebElement preventApptSchedOkBtn;
	
	@FindBy(how = How.XPATH, using = "//div[@id='providerwizard']//div[2]//a[1]//div[1]//div[2]//span[1]")
	private WebElement apptNextAvailable;
	
	@FindBy(how = How.XPATH, using = "//button[@class='btn appointmentType-btn handle-text-Overflow outer-div']")
	private WebElement apptBox;	

	public AppointmentPage(WebDriver driver) {
		super(driver);
	}
	
	CommonMethods commonMethods= new CommonMethods(driver);

	public AppointmentDateTime selectTypeOfAppointment(String appointmentType, Boolean isPopUpSelected) {
		searchAppointment.sendKeys(appointmentType);
		IHGUtil.waitForElement(driver, 30, selectAppointment);
		javascriptClick(selectAppointment);
		log("click on next step if present");
		selectNextStep(isPopUpSelected);
		return PageFactory.initElements(driver, AppointmentDateTime.class);
	}
	
	public AppointmentQuestionsPage selectApptTypeDecisionTree(String decisionTreeApptTypeName) {
		log("appointmentTypeList " + appointmentTypeList.size());
		for (int i = 0; i < appointmentTypeList.size(); i++) {
			if (appointmentTypeList.get(i).getText().contains(decisionTreeApptTypeName)) {
				appointmentTypeList.get(i).click();
				return PageFactory.initElements(driver, AppointmentQuestionsPage.class);
			}
		}
		log("no matching appointment found ");
		return PageFactory.initElements(driver, AppointmentQuestionsPage.class);
	}

	public AppointmentDateTime selectAppointmentandClick(String appointmentType, Boolean isPopUpSelected) {
		log("appointmentTypeList " + appointmentTypeList.size());
		for (int i = 0; i < appointmentTypeList.size(); i++) {
			if (appointmentTypeList.get(i).getText().contains(appointmentType)) {
				appointmentTypeList.get(i).click();
				selectNextStep(isPopUpSelected);
				return PageFactory.initElements(driver, AppointmentDateTime.class);
			}
		}
		log("no matching appointment found ");
		return PageFactory.initElements(driver, AppointmentDateTime.class);
	}
	
	public String preventAppointmentTypeMsg(String appointmentType) throws InterruptedException {
		log("appointmentTypeList " + appointmentTypeList.size());
		String preventSchMsg = null;
		for (int i = 0; i < appointmentTypeList.size(); i++) {

			if (appointmentTypeList.get(i).getText().contains(appointmentType)) {
				appointmentTypeList.get(i).click();
				commonMethods.highlightElement(preventApptSchedPopUp);
				IHGUtil.waitForElement(driver, 60, preventApptSchedPopUpMsg);
				preventSchMsg =preventApptSchedPopUpMsg.getText();
				log("Actual Prevent Scheduling Error Message- "+preventSchMsg);
			}
		}
		return preventSchMsg;
	}

	public Provider selectTypeOfProvider(String providerConfig, Boolean isPopUpSelected) {
		log("appointmentTypeList " + appointmentTypeList.size());
		for (int i = 0; i < appointmentTypeList.size(); i++) {
			if (appointmentTypeList.get(i).getText().contains(providerConfig)) {
				appointmentTypeList.get(i).click();
				selectNextStep(isPopUpSelected);
				return PageFactory.initElements(driver, Provider.class);
			}
		}
		log("no matching appointment found ");
		return PageFactory.initElements(driver, Provider.class);
	}
	
	public AppointmentDateTime selectAptType(String providerConfig, Boolean isPopUpSelected) {
		log("appointmentTypeList " + appointmentTypeList.size());
		for (int i = 0; i < appointmentTypeList.size(); i++) {
			if (appointmentTypeList.get(i).getText().contains(providerConfig)) {
				appointmentTypeList.get(i).click();
				selectNextStep(isPopUpSelected);
				return PageFactory.initElements(driver, AppointmentDateTime.class);
			}
		}
		log("no matching appointment found ");
		return PageFactory.initElements(driver, AppointmentDateTime.class);
	}

	public Location selectTypeOfLocation(String locationConfig, Boolean isPopUpSelected) {
		log("appointment type is " + locationConfig);
		for (int i = 0; i < appointmentTypeList.size(); i++) {
			if (appointmentTypeList.get(i).getText().contains(locationConfig)) {
				appointmentTypeList.get(i).click();
				selectNextStep(isPopUpSelected);
				return PageFactory.initElements(driver, Location.class);
			}
		}
		log("no matching appointment found ");
		return PageFactory.initElements(driver, Location.class);
	}

	public void selectNextStep(Boolean isPopUpSelected) {
		if (isPopUpSelected) {
			log("is popup");
			IHGUtil.waitForElement(driver, 60, gotoNextStep);
			jse.executeScript("arguments[0].setAttribute('style', 'background: white; border: 5px solid blue;');", gotoNextStep);
			gotoNextStep.click();
			log("successfully clicked on next step");

		}
	}

	public int appointmentListSize() {
		return appointmentTypeList.size();
	}

	public List<WebElement> getAppointmentNames() {
		return appointmentTypeList;
	}
	
	public String getNextavaliableText() {
		IHGUtil.waitForElement(driver, 10, apptNextAvailable);
		return apptNextAvailable.getText();
	}
	
	public String getNextAvailableOffText() {
		IHGUtil.waitForElement(driver, 5, apptBox);
		return apptBox.getText();
	}
	
	public void pressOkBtn() {
		IHGUtil.waitForElement(driver, 5, preventApptSchedOkBtn);
		preventApptSchedOkBtn.click();
	}
	
	public String selectTypeOfApp(String providerConfig, Boolean isPopUpSelected) {
		log("appointmentTypeList " + appointmentTypeList.size());
		for (int i = 0; i < appointmentTypeList.size(); i++) {
			if (appointmentTypeList.get(i).getText().contains(providerConfig)) {
				String a=appointmentTypeList.get(i).getText();
				return a;
			}
		}
		log("no matching appointment found ");
		return null;
	}

	public String selectTypeOfAppointment(String appName) {
		log("appointmentTypeList " + appointmentTypeList.size());
		for (int i = 0; i < appointmentTypeList.size(); i++) {
			if (appointmentTypeList.get(i).getText().equalsIgnoreCase(appName)) {
				String a = appointmentTypeList.get(i).getText();
				return a;
			}
		}
		log("no matching appointment found ");
		return null;
	}
	
	public boolean verifyApptTypePresent(String appointmentType) {		
		if (appointmentTypeList.contains(appointmentType)) {
			log("Appointment type present");
			return true;			
		}
		else {
			log("Appointment type not present");
			return false;
		}
	}
	
	public boolean verifyDecisionTreeApptTypePresent(String decisionTreeName) {		
		if (appointmentTypeList.contains(decisionTreeName)) {
			log("Decision tree present");
			return true;			
		}
		else {
			log("Decision tree not present");
			return false;
		}
	}
}
