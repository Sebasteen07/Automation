package com.medfusion.product.object.maps.pss2.page.ConfirmationPage;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;
import com.medfusion.product.object.maps.pss2.page.Scheduled.ScheduledAppointment;

public class ConfirmationPage extends PSS2MainPage {

	@FindAll({@FindBy(css = ".value-class")})
	private List<WebElement> appointmentScheduledDetails;

	@FindBy(how = How.ID, using = "everythingiscorrectbutton")
	private WebElement buttonAllGood;

	public ConfirmationPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(buttonAllGood);
		webElementsList.add(appointmentScheduledDetails.get(0));
		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}

	public ScheduledAppointment appointmentConfirmed() {
		buttonAllGood.click();
		return PageFactory.initElements(driver, ScheduledAppointment.class);
	}

	public List<WebElement> getAppointmentDetails() {
		return appointmentScheduledDetails;
	}

}