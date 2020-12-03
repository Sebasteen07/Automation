// Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.ConfirmationPage;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;
import com.medfusion.product.object.maps.pss2.page.Scheduled.ScheduledAppointment;
import com.medfusion.product.object.maps.pss2.page.Scheduled.ScheduledAppointmentAnonymous;
import com.medfusion.product.object.maps.pss2.page.util.CommonMethods;

public class ConfirmationPage extends PSS2MainPage {

	@FindAll({@FindBy(css = ".value-class")})
	private List<WebElement> appointmentScheduledDetails;

	@FindBy(how = How.XPATH, using = "//a[@id='everythingiscorrectbutton']")
	private WebElement buttonAllGood;

	@FindBy(how = How.XPATH, using = "//body/div[@id='root']/div[1]/div[1]/div[1]/div[4]/div[2]/div[1]/div[3]/div[1]/div[3]/div[1]/div[1]/div[1]")
	private WebElement DateConfirmation;

	@FindBy(how = How.XPATH, using = "//body/div[@id='root']/div[1]/div[1]/div[1]/div[4]/div[2]/div[1]/div[3]/div[1]/div[3]/div[1]/div[2]/div[1]")
	private WebElement timeConfirmation;
	
	@FindBy(how = How.XPATH, using = "//div[@id='confirmdatalist']/div/div/h1/span[contains(text(),'Appointment to')]")
	private WebElement rescheduleApptHeading;
	
	@FindBy(how = How.XPATH, using = "//span[normalize-space()='Provide a cancelation reason']")
	private WebElement rescheduleReasonLabel;
	
	@FindBy(how = How.XPATH, using = "//input[@id='cancelReasonText']")
	private WebElement rescheduleReasonInputBox;
	
	@FindBy(how = How.XPATH, using = "//span/span[@class=\"Select-arrow\"]")
	private WebElement selectArrow;
	
	@FindBy(how = How.XPATH, using = "//div[@class='Select-placeholder']")
	private WebElement dropdownReschedule;	
	
	@FindAll({@FindBy(how = How.XPATH, using = "//div[@class=\"Select-menu\"]/div")})
	private List<WebElement> rescheduleReasondropDownList;
	
	@FindBy(how = How.XPATH, using = "//span[contains(text(),\"You're about to confirm the following appointment.\")]")
	private WebElement confirmaApptToolti;
	
	@FindBy(how = How.XPATH, using = "//h1[@class='h1color']//span[contains(text(),'Confirm your appointment')]")
	private WebElement confirmApptHeading;
	
	@FindBy(how = How.XPATH, using = "//textarea[@class='form-control textareaconfirm']")
	private WebElement confirmApptReasonInputBox;
	
	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Confirmation number')]")
	private WebElement confirmationNumberLabelPreviousAppt;
	
	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Confirmation number')]/following-sibling::div")
	private WebElement confirmationNumberPreviousAppt;	

	public ConfirmationPage(WebDriver driver) {
		super(driver);
	}

	CommonMethods commonMethods = new CommonMethods(driver);
	

	@Override
	public boolean areBasicPageElementsPresent() {

		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();	
		
		commonMethods.highlightElement(confirmApptHeading);
		webElementsList.add(confirmApptHeading);
		webElementsList.add(confirmaApptToolti);
		return assessPageElements(webElementsList);
		
	}
	
	public void sendRescheduleReason() throws InterruptedException {
		commonMethods.highlightElement(rescheduleReasonInputBox);
		rescheduleReasonInputBox.sendKeys("Rescheduling the appointment because I want to change the date and time");
		Thread.sleep(1000);				
	}
	
	public void selectRescheduleReason() throws InterruptedException {

		List<WebElement> rescheduleReasonlist = new ArrayList<WebElement>();
		Actions act = new Actions(driver);
		
		IHGUtil.waitForElement(driver, 5, dropdownReschedule);
		
		selectArrow.click();

		rescheduleReasonlist = rescheduleReasondropDownList;
		log("Save all the reschedule reason in rescheduleReasonlist ");

		if (rescheduleReasonlist.size() > 0) {

			int length = rescheduleReasonlist.size();

			log("There " + length + " number of reschedule reason ");
			for (WebElement a : rescheduleReasonlist) {
				log(a.getText());			
			}
			log("Selected Reschedule Reason- " + rescheduleReasonlist.get(1).getText());

			act.moveToElement(rescheduleReasonlist.get(1)).click().build().perform();
		}

		Thread.sleep(1000);
	}
	
	public int maxLengthRescheduleReason() throws InterruptedException {
		String l=rescheduleReasonInputBox.getAttribute("maxlength");	
		return Integer.parseInt(l);
	}

	public ScheduledAppointment appointmentConfirmed() {
		commonMethods.highlightElement(buttonAllGood);
		buttonAllGood.click();
		return PageFactory.initElements(driver, ScheduledAppointment.class);
	}
	
	public ScheduledAppointment rescheduleAppointmentConfirmed() throws InterruptedException {
		commonMethods.highlightElement(buttonAllGood);		
		jse.executeScript("window.scrollBy(0,550)", "");
		Thread.sleep(1000);
		jse.executeScript("arguments[0].click();", buttonAllGood);
		log("Click on Everything is Correct button");		
		return PageFactory.initElements(driver, ScheduledAppointment.class);
	}

	public ScheduledAppointmentAnonymous appointmentConfirmedAnonymous() throws InterruptedException {
		commonMethods.highlightElement(buttonAllGood);
		Thread.sleep(1000);
		jse.executeScript("arguments[0].click();", buttonAllGood);
		//buttonAllGood.click();
		return PageFactory.initElements(driver, ScheduledAppointmentAnonymous.class);
	}

	public List<WebElement> getAppointmentDetails() {
		return appointmentScheduledDetails;
	}

	public String dateConfirm() {
		String datetext = DateConfirmation.getText();
		String nextDate1 = datetext.substring(00, 17);
		String confirmdate = nextDate1.replace(" ", "");
		return confirmdate;
	}

	public String timeConfirm() {
		String datetext = timeConfirmation.getText();
		String nextDate1 = datetext.substring(00, 05);
		String confirmtime = nextDate1.replace(" ", "");
		return confirmtime;
	}

	public HomePage confirmationPage() {
		return PageFactory.initElements(driver, HomePage.class);
	}

}
