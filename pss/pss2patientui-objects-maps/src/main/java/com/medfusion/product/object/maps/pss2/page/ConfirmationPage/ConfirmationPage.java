package com.medfusion.product.object.maps.pss2.page.ConfirmationPage;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;
import com.medfusion.product.object.maps.pss2.page.Scheduled.ScheduledAppointment;
import com.medfusion.product.object.maps.pss2.page.Scheduled.ScheduledAppointmentAnonymous;
import com.medfusion.product.object.maps.pss2.page.util.CommonMethods;

public class ConfirmationPage extends PSS2MainPage {

	@FindAll({@FindBy(css = ".value-class")})
	private List<WebElement> appointmentScheduledDetails;

	@FindBy(how = How.XPATH, using = "//a[@id='everythingiscorrectbutton']")
	private WebElement buttonAllGood;

	public ConfirmationPage(WebDriver driver) {
		super(driver);

	}

	CommonMethods commonMethods = new CommonMethods(driver);

	@Override
	public boolean areBasicPageElementsPresent() {

		return true;
	}

	public ScheduledAppointment appointmentConfirmed() {
		commonMethods.highlightElement(buttonAllGood);
		buttonAllGood.click();
		return PageFactory.initElements(driver, ScheduledAppointment.class);
	}

	public ScheduledAppointmentAnonymous appointmentConfirmedAnonymous() {
		commonMethods.highlightElement(buttonAllGood);
		buttonAllGood.click();
		return PageFactory.initElements(driver, ScheduledAppointmentAnonymous.class);
	}

	public List<WebElement> getAppointmentDetails() {
		// for (int i = 0; i < appointmentScheduledDetails.size(); i++) {
		//
		// log("Appointment Details are -->");
		// log(appointmentScheduledDetails.get(i).getText());
		// }
		return appointmentScheduledDetails;
	}

}