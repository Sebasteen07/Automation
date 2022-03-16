// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.Appointment.DateTime;

import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.product.object.maps.pss2.page.Appointment.Anonymous.AnonymousPatientInformation;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;
import com.medfusion.product.object.maps.pss2.page.ConfirmationPage.ConfirmationPage;
import com.medfusion.product.object.maps.pss2.page.Insurance.UpdateInsurancePage;

public class AppointmentDateTime extends PSS2MainPage {

	@FindAll({@FindBy(xpath = "//div[@class='rbc-event-content']")})
	public List<WebElement> appointmentList;

	@FindAll({@FindBy(xpath = "//a[@id='containerDiv']")})
	public List<WebElement> appointmentTimeList;

	@FindBy(how = How.XPATH, using = "//*[@id=\"topdiv\"]/div[2]/div/div[2]/div[3]/div/div/div")
	private WebElement scrollBarCalander;

	@FindBy(how = How.XPATH, using = "//*[@class='rbc-event-content']")
	private WebElement dateFirst;

	@FindBy(how = How.XPATH, using = "//span[contains(text(),'No slots available')]")
	private WebElement noslotsAvaliable;

    @FindBy(how = How.XPATH, using = "//*[@id='calendarslot']/div/div/div/div/div[2]/div[2]/div[2]/div[1]/div[2]/a")
    private WebElement currentDaydisabled;
    
    @FindBy(how = How.XPATH, using = "//button[@class=' dropdown-toggle dropdownbutton']")
   	private WebElement settingBtn;

   	@FindBy(how = How.XPATH, using = "//ul[@class='dropdown-menu']//li//a")
   	private WebElement logoutBtn;

	public AppointmentDateTime(WebDriver driver) {
		super(driver);
	}

	public AppointmentDateTime selectDt(Boolean nextMonthBooking) {
		if (nextMonthBooking) {
			driver.findElement(By.className("rbc-next-month")).click();
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		}
		List<WebElement> appointmentList = driver.findElements(By.cssSelector(".rbc-event-content"));
		for (int i = 0; i < appointmentList.size(); i++) {
			if (appointmentList.get(i).isDisplayed()) {
				log("Appointment Date selected=" + appointmentList.get(i).getText());
				appointmentList.get(i).click();
				appointmentList.get(i).getText();
				return PageFactory.initElements(driver, AppointmentDateTime.class);
			}
		}
		return PageFactory.initElements(driver, AppointmentDateTime.class);
	}

	/*----New Method For Future Date APPT----*/
	public AppointmentDateTime selectFutureDate(Boolean nextMonthBooking) {
		if (nextMonthBooking) {
			driver.findElement(By.className("rbc-next-month")).click();
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		}
		List<WebElement> appointmentList = driver.findElements(By.cssSelector(".rbc-event-content"));
		for (int i = appointmentList.size() - 1; i >= 0; i--) {
			if (appointmentList.get(i).isDisplayed()) {
				log("Appointment Date selected=" + appointmentList.get(i).getText());
				log("Value of i is " + i);
				appointmentList.get(i).click();
				return PageFactory.initElements(driver, AppointmentDateTime.class);
			}
		}
		return PageFactory.initElements(driver, AppointmentDateTime.class);
	}

	public String selectDate(Boolean nextMonthBooking) {
		String dt = null;
		if (nextMonthBooking) {
			driver.findElement(By.className("rbc-next-month")).click();
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		}
		List<WebElement> appointmentList = driver.findElements(By.cssSelector(".rbc-event-content"));
		log("size is " + appointmentList.size());
		for (int i = 0; i < appointmentList.size(); i++) {
			if (appointmentList.get(0).isDisplayed()) {
				log("Appointment Date selected=" + appointmentList.get(0).getText());
				appointmentList.get(0).click();
				dt = appointmentList.get(0).getText();
				log("Date is " + dt);
				return appointmentList.get(0).getText();
			}
		}
		return dt;
	}
	
	public void selectDateOnly(Boolean nextMonthBooking) {
		String dt = null;
		if (nextMonthBooking) {
			driver.findElement(By.className("rbc-next-month")).click();
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		}
		List<WebElement> appointmentList = driver.findElements(By.cssSelector(".rbc-event-content"));
		log("size is " + appointmentList.size());
		for (int i = 0; i < appointmentList.size(); i++) {
			if (appointmentList.get(0).isDisplayed()) {
				log("Appointment Date selected=" + appointmentList.get(0).getText());
				appointmentList.get(0).click();
				dt = appointmentList.get(0).getText();
				log("Date is " + dt);

			}
		}
	
	}

	public UpdateInsurancePage selectAppointmentTimeIns() {
		List<WebElement> appointmentTimeList = driver.findElements(By.cssSelector(".time-btn"));
		for (int i = 0; i < appointmentTimeList.size(); i++) {
			if (appointmentTimeList.get(i).isDisplayed()) {
				log("Appointment Time selected=" + appointmentTimeList.get(i).getText());
				appointmentTimeList.get(i).click();
				appointmentTimeList.clear();
				return PageFactory.initElements(driver, UpdateInsurancePage.class);
			}
		}
		return null;
	}

	public ConfirmationPage selectAppointmentDateTime(Boolean nextMonthBooking) {
		List<WebElement> appointmentTimeList = driver.findElements(By.cssSelector(".time-btn"));

		for (int i = 0; i < appointmentTimeList.size(); i++) {

			if (appointmentTimeList.get(i).isDisplayed()) {

				log("Appointment Time selected=" + appointmentTimeList.get(i).getText());
				appointmentTimeList.get(i).click();
				return PageFactory.initElements(driver, ConfirmationPage.class);
			}
		}
		appointmentTimeList.clear();
		return PageFactory.initElements(driver, ConfirmationPage.class);
	}

	public ConfirmationPage selectFutureApptDateTime(Boolean nextMonthBooking) {
		List<WebElement> appointmentTimeList = driver.findElements(By.cssSelector(".time-btn"));
		int slotsize = appointmentTimeList.size();
		for (int i = 0; i < slotsize; i++) {
			if (appointmentTimeList.get(slotsize - 1).isDisplayed()) {
				log("Appointment Time selected=" + appointmentTimeList.get(slotsize - 1).getText());
				appointmentTimeList.get(slotsize - 1).click();
				return PageFactory.initElements(driver, ConfirmationPage.class);
			}
		}
		appointmentTimeList.clear();
		return PageFactory.initElements(driver, ConfirmationPage.class);
	}

	public AnonymousPatientInformation selectAppointmentTimeSlot(Boolean nextMonthBooking) {
		List<WebElement> appointmentTimeList = driver.findElements(By.cssSelector(".time-btn"));
		for (int i = 0; i < appointmentTimeList.size(); i++) {
			if (appointmentTimeList.get(i).isDisplayed()) {
				log("Appointment Time selected=" + appointmentTimeList.get(i).getText());
				appointmentTimeList.get(i).click();
				return PageFactory.initElements(driver, AnonymousPatientInformation.class);
			}
		}
		appointmentTimeList.clear();
		return PageFactory.initElements(driver, AnonymousPatientInformation.class);
	}

	public UpdateInsurancePage selectAppointmentDateAndTime(WebDriver driver) {
		log("Available time slots are " + appointmentTimeList.size());
		for (int i = 0; i < appointmentTimeList.size(); i++) {
			int slotsize = appointmentTimeList.size();
			if (appointmentTimeList.get(slotsize - 1).isDisplayed()) {
				log("Appointment Time selected=" + appointmentTimeList.get(slotsize - 1).getText());
				appointmentTimeList.get(slotsize - 1).click();
			}
		}
		return PageFactory.initElements(driver, UpdateInsurancePage.class);
	}

	public String getTimeDifference() {
		String slotTimePlusOne = appointmentTimeList.get(1).getText();
		String slotTime = appointmentTimeList.get(0).getText();
		String min2 = slotTimePlusOne.substring(slotTimePlusOne.indexOf(":") + 1, slotTimePlusOne.indexOf(" "));
		String min1 = slotTime.substring(slotTime.indexOf(":") + 1, slotTime.indexOf(" "));
		int minutesDiff = Integer.parseInt(min2) - Integer.parseInt(min1);
		return Integer.toString(minutesDiff);
	}

	public void getScrollBarDetails() {
		Long clientHeight = Long.parseLong(scrollBarCalander.getAttribute("clientHeight"));
		Long scrollHeight = Long.parseLong(scrollBarCalander.getAttribute("scrollHeight"));
		log("clientHeight= " + clientHeight + " and scrollHeight = " + scrollHeight);
		Boolean isScrollBarPresent = scrollHeight > clientHeight;
		List<WebElement> appointmentTimeList = driver.findElements(By.cssSelector(".time-btn"));
		log("Slot List size " + appointmentTimeList.size());
		log("isScrollBarPresent " + isScrollBarPresent);
		if (appointmentTimeList.size() > 10) {
			assertTrue(isScrollBarPresent, "Scrollbar not found.");
		}
		appointmentTimeList.clear();
	}

	public int getAppointmentTimeList() {
		return appointmentTimeList.size();
	}

	public int getAppointmentDateList() {
		return appointmentList.size();
	}

	public void getfirstdate() {
		for (int i = 0; i < appointmentList.size(); i++) {
			log("First Appointment is" + appointmentList.get(i));

		}
	}

	public String getfirsttime() {
		String time = "";
		for (int i = 0; i < appointmentTimeList.size(); i++) {
			time = appointmentTimeList.get(0).getText();
		}
		return time;

	}

	public String selectdateforreserve() {
		log("Total Dates present on patientUI is " + getAppointmentDateList());
		String dt = null;
		List<WebElement> appointmentList = driver.findElements(By.cssSelector(".rbc-event-content"));
		for (int i = 0; i < appointmentList.size(); i++) {
			if (appointmentList.get(i).isDisplayed()) {
				log("Appointment Date selected=" + appointmentList.get(i).getText());
				appointmentList.get(i).click();
				dt = appointmentList.get(i).getText();
				return appointmentList.get(i).getText();
			}
		}
		return dt;
	}

	public String gettextNoslot() {
		String text = noslotsAvaliable.getText();
		log("text is" + text);
		return text;
	}

	public boolean disabledate() {
		return currentDaydisabled.isDisplayed();
	}

	public String selectDateforMaxPDay(Boolean nextMonthBooking) {
		String dt = null;
		if (nextMonthBooking) {
			driver.findElement(By.className("rbc-next-month")).click();
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		}
		List<WebElement> appointmentList = driver.findElements(By.cssSelector(".rbc-event-content"));
		for (int i = 0; i < appointmentList.size(); i++) {
			if (appointmentList.get(i).isDisplayed()) {
				log("Appointment Date selected=" + appointmentList.get(i).getText());
				appointmentList.get(i).click();
				dt = appointmentList.get(i).getText();
				log("Current date is disabled  " + disabledate());
				assertTrue(disabledate());
				return appointmentList.get(i).getText();
			}
		}
		return dt;
	}
	
	public String getFirstTimeWithHour() {
		String time = "";
		for (int i = 0; i < appointmentTimeList.size(); i++) {
			time = appointmentTimeList.get(0).getText();
		}		return time.substring(0,2);

	}
	public String getFirstTimeWithMinute() {
		String time = "";
		for (int i = 0; i < appointmentTimeList.size(); i++) {
			time = appointmentTimeList.get(0).getText();
		}
		return time.substring(3,5);

	}
	public String getNextTimeWithMinute() {
		String time = "";
		for (int i = 0; i < appointmentTimeList.size(); i++) {
			time = appointmentTimeList.get(1).getText();
		}
		return time.substring(0,5);

	}
	
	public String getFirstTimeWithHHMM() {
		String time = "";
		for (int i = 0; i < appointmentTimeList.size(); i++) {
			time = appointmentTimeList.get(0).getText();
		}
		return time.substring(0,5);
	}

	
	public void logout() throws InterruptedException {
		settingBtn.click();
		logoutBtn.click();
		log("logged out");
	}
}
