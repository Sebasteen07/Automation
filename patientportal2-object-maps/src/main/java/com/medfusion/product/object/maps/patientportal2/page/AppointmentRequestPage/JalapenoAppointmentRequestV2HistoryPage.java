//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage;

import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.common.utils.IHGUtil;

public class JalapenoAppointmentRequestV2HistoryPage extends JalapenoMenu {

		@FindBy(how = How.XPATH, using = "//div[@id='frame']/div/table")
		private WebElement fullTable;

		@FindBy(how = How.XPATH, using = "//li[label[text()='Reason']]")
		private WebElement reason;

		@FindBy(how = How.XPATH, using = "//li[label[text()='Preferred Time']]")
		private WebElement preferredTime;

		@FindBy(how = How.XPATH, using = "//li[label[text()='Request Day']]")
		private WebElement requestedDay;

		@FindBy(how = How.XPATH, using = "//li[label[text()='Request Time']]")
		private WebElement requestedTime;

		@FindBy(how = How.ID, using = "back_button")
		private WebElement backButton;
		
		@FindBy(how = How.XPATH, using = "//th[text()=\"Reason\"]")
		private WebElement tbReason;


		public JalapenoAppointmentRequestV2HistoryPage(WebDriver driver) {
				super(driver);
				IHGUtil.PrintMethodName();
		}

		public boolean findAppointmentReasonAndOpen(String appointmentReason) {
				IHGUtil.PrintMethodName();
				new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOf(tbReason));
				try {
						driver.findElement(By.xpath("//*[contains(text(),'" + appointmentReason + "')]")).click();
						return true;
				} catch (Exception e) {
						log(e.getCause().toString());
						return false;
				}
		}

		public boolean checkAppointmentDetails(String appointmentReason) throws InterruptedException {
				IHGUtil.PrintMethodName();
				new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOf(reason));
				try {
						return (reason.getText().contains(appointmentReason) && preferredTime.getText().contains("Early Morning, Late Afternoon") && requestedDay.getText()
								.contains("Monday - Thursday") && requestedTime.getText().contains("Anytime"));
				} catch (Exception e) {
						log(e.getCause().toString());
						return false;
				}
		}
		
		public boolean checkAppointmentDetailsOfFirstAvailableTime(String appointmentReason) throws InterruptedException {
			IHGUtil.PrintMethodName();
			new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOf(reason));
			try {
				return (reason.getText().contains(appointmentReason) && preferredTime.getText().contains("Any Time Of Day") && requestedDay.getText()
						.contains("Monday - Friday") && requestedTime.getText().contains("First Available"));
			} catch (Exception e) {
				log(e.getCause().toString());
				return false;
			}
		}

}
