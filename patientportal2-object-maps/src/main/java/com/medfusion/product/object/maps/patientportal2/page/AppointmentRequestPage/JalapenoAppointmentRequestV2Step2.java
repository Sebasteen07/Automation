package com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;

public class JalapenoAppointmentRequestV2Step2 extends BasePageObject{

	
	@FindBy(how = How.ID, using = "firstAvailableBtn")
	private WebElement firstAvibleTimeButton;
	
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
		
	
	public JalapenoAppointmentRequestV2Step2(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);	
	}	
	
	
	public boolean assessElements() {	
		IHGUtil.PrintMethodName();
		
		log("Wait for elements");
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(firstAvibleTimeButton);
		webElementsList.add(timeOfDayAnyButton);
		webElementsList.add(earlyMorningButton);
		webElementsList.add(lateMorningButton);
		webElementsList.add(earlyAfternoonButton);
		webElementsList.add(lateAfternoonButton);
		webElementsList.add(dayOfWeekAnyButton);
		webElementsList.add(mondayButton);
		webElementsList.add(tuesdayButton);
		webElementsList.add(wednesdayButton);
		webElementsList.add(thursdayButton);
		webElementsList.add(fridayButton);
		webElementsList.add(weekAnyButton);
		webElementsList.add(weekThisButton);
		webElementsList.add(weekNextButton);
		webElementsList.add(appointmentReasonTextArea);
		webElementsList.add(backButton);
		webElementsList.add(requestAppointmentButton);
		
		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}
	
	//sendKeys(" ") instead of click() because there is some problem with resolution 1024*768
	public void fillAppointmentRequestForm(String appointmentReason) {
		IHGUtil.PrintMethodName();
		
		log("Set Times of day: Early morning and Late afternoon");
		earlyMorningButton.sendKeys(" ");
		lateAfternoonButton.sendKeys(" ");
		
		log("Set Days of week: Monday - Thursday");
		mondayButton.sendKeys(" ");
		tuesdayButton.sendKeys(" ");
		wednesdayButton.sendKeys(" ");
		thursdayButton.sendKeys(" ");
		
		log("Leave Week: Any");
		
		log("Reason for visit: " + appointmentReason);
		appointmentReasonTextArea.sendKeys(appointmentReason);
	}
	
	public JalapenoHomePage submitAppointment (WebDriver driver) {
		requestAppointmentButton.click();
		return PageFactory.initElements(driver, JalapenoHomePage.class);
	}	
}