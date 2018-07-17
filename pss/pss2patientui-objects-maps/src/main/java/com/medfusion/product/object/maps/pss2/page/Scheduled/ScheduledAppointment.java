package com.medfusion.product.object.maps.pss2.page.Scheduled;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;

public class ScheduledAppointment extends PSS2MainPage {
	
	@FindBy(how = How.ID, using = "gotodashboard")
	private WebElement buttonBackToAppointmentList;

	@FindBy(how = How.CSS, using = ".btn-link.addCalendar")
	private WebElement linkAddToCalander;

	@FindAll({@FindBy(css = "value-classschedule")})
	public List<WebElement> flowWizardList;

	@FindBy(how = How.XPATH, using = "//h1[@class=\"schedule\"]/text()")
	private WebElement appointmentID;

	@FindBy(how = How.XPATH, using = "//*[@id=\"appointmentconfirmationtop\"]/div/div[7]/div")
	private WebElement confirmationNumber;

	public ScheduledAppointment(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(linkAddToCalander);
		webElementsList.add(buttonBackToAppointmentList);
		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}

	public void downloadCalander() {
		linkAddToCalander.click();
	}

	public HomePage backtoHomePage() {
		buttonBackToAppointmentList.click();
		return PageFactory.initElements(driver, HomePage.class);
	}

	public String getAppointmentID() {
		return confirmationNumber.getText();
	}
}
