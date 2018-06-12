package com.medfusion.product.object.maps.pss2.page.Appointment.DateTime;

import java.util.List;

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

	@FindAll({@FindBy(css = "a[class='time-btn slotTime-btn']")})
	public List<WebElement> appointmentTimeList;

	public AppointmentDateTime(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		return true;
	}

	public void selectDate() {
		List<WebElement> appointmentList = driver.findElements(By.cssSelector(".rbc-event-content"));
		for (int i = 0; i < appointmentList.size(); i++) {
			if (appointmentList.get(i).isDisplayed()) {
				log("Appointment Date selected=" + appointmentList.get(i).getText());
				appointmentList.get(i).click();
				break;
			}
		}
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

	public ConfirmationPage selectAppointmentDateTime() {
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
}
