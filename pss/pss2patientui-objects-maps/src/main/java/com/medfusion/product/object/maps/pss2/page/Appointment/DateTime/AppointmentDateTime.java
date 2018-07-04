package com.medfusion.product.object.maps.pss2.page.Appointment.DateTime;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;
import com.medfusion.product.object.maps.pss2.page.ConfirmationPage.ConfirmationPage;
import com.medfusion.product.object.maps.pss2.page.Insurance.UpdateInsurancePage;

public class AppointmentDateTime extends PSS2MainPage {

	@FindAll({@FindBy(css = "a[class='rbc-event-content']")})
	public List<WebElement> appointmentList;

	@FindAll({@FindBy(css = ".time-btn")})
	public List<WebElement> appointmentTimeList;

	public AppointmentDateTime(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		return true;
	}

	public String selectDate(Boolean nextMonthBooking) {
		if (nextMonthBooking) {
			driver.findElement(By.className("rbc-next-month")).click();
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		}
		List<WebElement> appointmentList = driver.findElements(By.cssSelector(".rbc-event-content"));
		for (int i = 0; i < appointmentList.size(); i++) {
			if (appointmentList.get(i).isDisplayed()) {
				log("Appointment Date selected=" + appointmentList.get(i).getText());
				appointmentList.get(i).click();
				return appointmentList.get(i).getText();
				
			}
		}
		return null;
	}

	public UpdateInsurancePage selectAppointmentTimeIns() {
		List<WebElement> appointmentTimeList = driver.findElements(By.cssSelector(".time-btn"));

		for (int i = 0; i < appointmentTimeList.size(); i++) {
			if (appointmentTimeList.get(i).isDisplayed()) {
				log("Appointment Time selected=" + appointmentTimeList.get(i).getText());
				appointmentTimeList.get(i).click();
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
		return null;
	}

	public void selectAppointmentDateAndTime(WebDriver driver) {
		List<WebElement> appointmentTimeList = driver.findElements(By.cssSelector(".time-btn"));
		for (int i = 0; i < appointmentTimeList.size(); i++) {
			if (appointmentTimeList.get(i).isDisplayed()) {
				log("Appointment Time selected=" + appointmentTimeList.get(i).getText());
				appointmentTimeList.get(i).click();

			}
		}

	}

	public String getTimeDifference() {
		String slotTimePlusOne = appointmentTimeList.get(1).getText();
		String slotTime = appointmentTimeList.get(0).getText();
		String min2 = slotTimePlusOne.substring(slotTimePlusOne.indexOf(":") + 1, slotTimePlusOne.indexOf(" "));
		String min1 = slotTime.substring(slotTime.indexOf(":") + 1, slotTime.indexOf(" "));
		int minutesDiff = Integer.parseInt(min2) - Integer.parseInt(min1);
		return Integer.toString(minutesDiff);
	}
}
