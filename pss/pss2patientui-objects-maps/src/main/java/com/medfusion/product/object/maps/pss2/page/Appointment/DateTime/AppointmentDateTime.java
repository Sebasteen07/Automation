//Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.Appointment.DateTime;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
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

	public AppointmentDateTime(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		if (appointmentList.size() != 0) {
			IHGUtil.waitForElement(driver, 120, appointmentList.get((appointmentList.size() - 1)));
		}
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		for (int i = 0; i < appointmentList.size(); i++) {
			webElementsList.add(appointmentList.get(i));
		}

		return assessPageElements(webElementsList);
	}

	public String selectDate(Boolean nextMonthBooking) {
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
				//appointmentTimeList.clear();
				return appointmentList.get(i).getText();
				
			}
		}
		return dt;
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
		// List<WebElement> appointmentTimeList = driver.findElements(By.cssSelector(".time-btn"));
		log("Available time slots are " + appointmentTimeList.size());
		for (int i = 0; i < appointmentTimeList.size(); i++) {
			if (appointmentTimeList.get(i).isDisplayed()) {
				log("Appointment Time selected=" + appointmentTimeList.get(i).getText());
				appointmentTimeList.get(i).click();

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
}
