// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage;

import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;

public class JalapenoAppointmentRequestV2Step2 extends JalapenoMenu {

	@FindBy(how = How.ID, using = "firstAvailableBtn")
	private WebElement firstAvailableTimeButton;

	@FindBy(how = How.XPATH, using = "//label[@for='firstavail']")
	private WebElement videoVisit;

	@FindBy(how = How.XPATH, using = "//label[@for='timeofday_any']")
	private WebElement timeOfDayAnyButton;

	@FindBy(how = How.XPATH, using = "//label[@for='timeofday_earlymorn']")
	private WebElement earlyMorningButton;

	@FindBy(how = How.XPATH, using = "//label[@for='timeofday_latemorn']")
	private WebElement lateMorningButton;

	@FindBy(how = How.XPATH, using = "//label[@for='timeofday_earlyaft']")
	private WebElement earlyAfternoonButton;

	@FindBy(how = How.XPATH, using = "//label[@for='timeofday_lateaft']")
	private WebElement lateAfternoonButton;

	@FindBy(how = How.XPATH, using = "//label[@for='dayofweek_any']")
	private WebElement dayOfWeekAnyButton;

	@FindBy(how = How.XPATH, using = "//label[@for='dayofweek_monday']")
	private WebElement mondayButton;

	@FindBy(how = How.XPATH, using = "//label[@for='dayofweek_tuesday']")
	private WebElement tuesdayButton;

	@FindBy(how = How.XPATH, using = "//label[@for='dayofweek_wednesday']")
	private WebElement wednesdayButton;

	@FindBy(how = How.XPATH, using = "//label[@for='dayofweek_thursday']")
	private WebElement thursdayButton;

	@FindBy(how = How.XPATH, using = "//label[@for='dayofweek_friday']")
	private WebElement fridayButton;

	@FindBy(how = How.XPATH, using = "//label[@for='week_any']")
	private WebElement weekAnyButton;

	@FindBy(how = How.XPATH, using = "//label[@for='week_this']")
	private WebElement weekThisButton;

	@FindBy(how = How.XPATH, using = "//label[@for='week_next']")
	private WebElement weekNextButton;

	@FindBy(how = How.ID, using = "apptreason")
	private WebElement appointmentReasonTextArea;

	@FindBy(how = How.ID, using = "cancel_button")
	private WebElement backButton;

	@FindBy(how = How.ID, using = "continue_button")
	private WebElement requestAppointmentButton;
	
	@FindBy(how = How.XPATH, using = "//label[@for='videoPreference']")
	private WebElement rdoVideoVisit;

	public JalapenoAppointmentRequestV2Step2(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
	}

	// sendKeys(" ") instead of click() because there is some problem with
	// resolution 1024*768
	public void fillAppointmentRequestForm(String appointmentReason) {
		IHGUtil.PrintMethodName();

		javascriptClick(videoVisit);

		log("Set Times of day: Early morning and Late afternoon");
		javascriptClick(earlyMorningButton);
		javascriptClick(lateAfternoonButton);

		log("Set Days of week: Monday - Thursday");
		javascriptClick(mondayButton);
		javascriptClick(tuesdayButton);
		javascriptClick(wednesdayButton);
		javascriptClick(thursdayButton);

		log("Leave Week: Any");

		log("Reason for visit: " + appointmentReason);
		appointmentReasonTextArea.sendKeys(appointmentReason);
	}

	public JalapenoHomePage submitAppointment(WebDriver driver) throws InterruptedException {
		IHGUtil.waitForElement(driver, 50, requestAppointmentButton);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", requestAppointmentButton);
		requestAppointmentButton.click();
		return PageFactory.initElements(driver, JalapenoHomePage.class);
	}
	
	public void fillAppointmentRequestReasonForm(String appointmentReason) {
		IHGUtil.PrintMethodName();
		javascriptClick(videoVisit);
		log("Reason for visit: " + appointmentReason);
		appointmentReasonTextArea.sendKeys(appointmentReason);
	}

	public boolean isVideoVisitDisplayed() throws TimeoutException {
		log("Verify Video Visit Radio Button");
		try {
			return rdoVideoVisit.isDisplayed();
		} catch (NoSuchElementException e) {
			log("Live Chat is not displayed");
			return false;
		}
	}
}
