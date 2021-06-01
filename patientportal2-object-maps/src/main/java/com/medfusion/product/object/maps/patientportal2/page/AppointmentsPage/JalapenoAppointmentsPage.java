//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.AppointmentsPage;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.common.utils.IHGUtil;

public class JalapenoAppointmentsPage extends MedfusionPage {

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

		@FindBy(how = How.XPATH, using = "//ul[contains(@class, 'myAccountList')]")
		public WebElement appointments;

		public JalapenoAppointmentsPage(WebDriver driver) {
				super(driver);
				IHGUtil.PrintMethodName();
		}

		public List<WebElement> getAppointments() {
				IHGUtil util = new IHGUtil(driver);
				if (util.isFoundBy(By.id(NO_APPOINTMENTS_TEXT_ID), 5)) {
						return Collections.emptyList();
				}
				List<WebElement> result = appointments.findElements(By.xpath(".//div[@class='row']"));
				return result;
		}

		public void goToUpcomingAppointments() {
				IHGUtil.waitForElement(driver, 90, upcomingAppointmentsButton);
				if (upcomingAppointmentsButton.isDisplayed()) {
						log("Going to upcoming appointments page");
						upcomingAppointmentsButton.click();
				} else {
						log("Upcoming appointments button not found");
				}
		}

		public void goToPastAppointments() {
				IHGUtil.waitForElement(driver, 120, pastAppointmentsButton);
				if (pastAppointmentsButton.isDisplayed()) {
						log("Going to past appointments page");
						pastAppointmentsButton.click();
				} else {
						log("Past appointments button not found");
				}
		}

		public boolean assessAppointmentsPageElementsNoAppointments() {
				ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
				webElementsList.add(noAppointmentsText);

				return assessPageElements(webElementsList);
		}
		
		public boolean verifyAppointment(String appointmentDate,String appointmentTime,String appointmentProvider){
			IHGUtil.PrintMethodName();
			List<WebElement> appointmentList = getAppointments();
			Boolean status = false;
			
			for(int i =0;i<appointmentList.size();i++){
				String XPath = "(//ul[contains(@class, 'myAccountList')]//div[@class='row'])["+(i+1)+"]";
				
				Log4jUtil.log("Row "+(i+1)+" Date " +driver.findElement(By.xpath(XPath+"//div[@class='col-sm-6 col-md-6'][1]")).getText());
				Log4jUtil.log("Row "+(i+1)+" Time " +driver.findElement(By.xpath(XPath+"//div[@class='col-sm-6 col-md-6'][2]")).getText());
				
				if(driver.findElement(By.xpath(XPath+"//div[@class='col-sm-6 col-md-6'][1]")).getText().contains(appointmentDate) &&
						driver.findElement(By.xpath(XPath+"//div[@class='col-sm-6 col-md-6'][2]")).getText().contains(appointmentTime)){	
					Log4jUtil.log("Actual Date "+driver.findElement(By.xpath(XPath+"//div[@class='col-sm-6 col-md-6'][1]")).getText());
					Log4jUtil.log("Actual Time "+driver.findElement(By.xpath(XPath+"//div[@class='col-sm-6 col-md-6'][2]")).getText());
					
						WebElement provider =driver.findElement(By.xpath(XPath)).findElement(By.xpath("//*[contains(text(),'"+appointmentProvider+"')]"));
						if(provider.isDisplayed()){
							status = true;
							Log4jUtil.log("Booked Appointment is received by Patient");
							break;
						}
				}
			}
			return status;
		}
		
		public boolean verifyAppointmentisDeleted(String appointmentDate, String appointmentTime){
			IHGUtil.PrintMethodName();
			List<WebElement> appointmentList = getAppointments();
			Boolean status = true;
			for(int i =0;i<appointmentList.size();i++){
				String XPath = "(//ul[contains(@class, 'myAccountList')]//div[@class='row'])["+(i+1)+"]";
					if(driver.findElement(By.xpath(XPath+"//div[@class='col-sm-6 col-md-6'][1]")).getText().contains(appointmentDate)){
						Log4jUtil.log("Actual Date "+driver.findElement(By.xpath(XPath+"//div[@class='col-sm-6 col-md-6'][1]")).getText());
						Log4jUtil.log("Actual Time "+driver.findElement(By.xpath(XPath+"//div[@class='col-sm-6 col-md-6'][2]")).getText());						
						if(driver.findElement(By.xpath(XPath+"//div[@class='col-sm-6 col-md-6'][2]")).getText().contains(appointmentTime)){
						status = false;	
						Log4jUtil.log("Booked Appointment is not deleted");
						break;
						}
				}
			}
			assertTrue(status, "Booked Appointment is not deleted");
			return status;			
		}
}
