package com.medfusion.product.object.maps.patientportal2.page.AppointmentsPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class JalapenoAppointmentsPage extends BasePageObject {
	
	private static final String NO_APPOINTMENTS_TEXT_ID = "noAppointments";

	@FindBy(how = How.LINK_TEXT, using = "Upcoming")
	public WebElement upcomingAppointmentsButton;
	
	@FindBy(how = How.LINK_TEXT, using = "Past")
	public WebElement pastAppointmentsButton;
	
	@FindBy(how = How.LINK_TEXT, using = "Previous Requests")
	public WebElement previousAppointmentsRequestsButton;
	
	@FindBy(how = How.ID, using = "appointmentSolutionBtn")
	public WebElement appointmentSolutionButton;
	
	@FindBy(how = How.ID, using = NO_APPOINTMENTS_TEXT_ID)
	public WebElement noAppointmentsText;
	
	@FindBy(how = How.XPATH, using = "//ul[contains(@class, 'myAccountList')]//div[@class='row']")
	public WebElement appointments;
	
	public JalapenoAppointmentsPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}
	
	public List<WebElement> getAppointments() {
		IHGUtil util = new IHGUtil(driver);
		if (util.isFoundBy(By.id(NO_APPOINTMENTS_TEXT_ID), 5)) {
			return Collections.emptyList();
		}
		List<WebElement> result = appointments.findElements(By.xpath("//li//div[@class='row']"));
		return result;
	}
	
	public void goToUpcomingAppointments() {
		if (upcomingAppointmentsButton.isDisplayed()) {
			log("Going to upcoming appointments page");
			upcomingAppointmentsButton.click();
		} else {
			log("Upcoming appointments button not found");
		}
	}
	
	public void goToPastAppointments() {
		if (pastAppointmentsButton.isDisplayed()) {
			log("Going to past appointments page");
			pastAppointmentsButton.click();
		} else {
			log("Past appointments button not found");
		}
	}
	
	private ArrayList<WebElement> getBasicAppointmentsPageElements() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(upcomingAppointmentsButton);
		webElementsList.add(pastAppointmentsButton);
		webElementsList.add(previousAppointmentsRequestsButton);
		webElementsList.add(appointmentSolutionButton);
		
		return webElementsList;
	}
	
	public boolean assessAppointmentsPageElementsNoAppointments() {
		ArrayList<WebElement> webElementsList = getBasicAppointmentsPageElements();
		webElementsList.add(noAppointmentsText);

		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}
	
	public boolean assessAppointmentsPageElementsWithAppointments() {
		ArrayList<WebElement> webElementsList = getBasicAppointmentsPageElements();		

		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}
}
