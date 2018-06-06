package com.medfusion.product.object.maps.pss2.page.AppEntryPoint;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.Appointment.Location.Location;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Provider.Provider;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.AppointmentPage;

public class StartAppointmentInOrder extends PSS2MainPage {

	@FindAll({@FindBy(css = ".btn")})
	private List<WebElement> startingWith;

	public StartAppointmentInOrder(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		// webElementsList.add(startingWith.get(0));
		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}

	public Provider selectFirstProvider(String selectOrderWith) {
		for (int i = 1; i <= startingWith.size(); i++) {
			WebElement startingPoint = driver.findElement(By.xpath("//*[@id=\"startingpointwizard\"]/div/div/a[" + (i + 1) + "]/span"));
			if (startingPoint.getText().equalsIgnoreCase(selectOrderWith)) {
				startingPoint.click();
				return PageFactory.initElements(driver, Provider.class);
			}
		}
		log("Provider Not found");
		return null;
	}

	public AppointmentPage selectFirstAppointment(String selectOrderWith) {
		for (int i = 1; i <= startingWith.size(); i++) {
			WebElement startingPoint = driver.findElement(By.xpath("//*[@id=\"startingpointwizard\"]/div/div/a[" + (i + 1) + "]/span"));
			if (startingPoint.getText().equalsIgnoreCase(selectOrderWith)) {
				startingPoint.click();
				return PageFactory.initElements(driver, AppointmentPage.class);
			}
		}
		log("Appointment Not found");
		return null;
	}

	public Location selectFirstLocation(String selectOrderWith) {

		log("startingWith length " + startingWith.size());
		for (int i = 1; i <= startingWith.size(); i++) {
			WebElement startingPoint = driver.findElement(By.xpath("//*[@id=\"startingpointwizard\"]/div/div/a[" + (i + 1) + "]/span"));
			if (startingPoint.getText().equalsIgnoreCase(selectOrderWith)) {
				startingPoint.click();
				return PageFactory.initElements(driver, Location.class);
			}
		}
		log("Location Not found");
		return null;
	}
}
