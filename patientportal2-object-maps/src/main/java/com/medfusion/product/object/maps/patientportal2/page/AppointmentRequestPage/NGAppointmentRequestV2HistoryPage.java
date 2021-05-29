//Copyright 2020-2021 NXGN Management, LLC. All Rights Reserved.
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

public class NGAppointmentRequestV2HistoryPage extends JalapenoMenu {

		@FindBy(how = How.XPATH, using = "//div[contains(@class,'appointment history')]//table")
		private WebElement fullTable;

		@FindBy(how = How.XPATH, using = "//label[text()='Reason']//parent::li")
		private WebElement reason;

		@FindBy(how = How.XPATH, using = "//label[text()='Preferred Time']//parent::li")
		private WebElement preferredTime;

		@FindBy(how = How.XPATH, using = "//label[text()='Request Day']//parent::li")
		private WebElement requestedDay;

		@FindBy(how = How.XPATH, using = "//label[text()='Request Time']//parent::li")
		private WebElement requestedTime;

		@FindBy(how = How.ID, using = "back_button")
		private WebElement backButton;
		
		@FindBy(how = How.XPATH, using = "//a[contains(text(),'Upcoming')]")
		private WebElement Upcoming;


		public NGAppointmentRequestV2HistoryPage(WebDriver driver) {
				super(driver);
				IHGUtil.PrintMethodName();
		}

		public boolean findAppointmentReasonAndOpen(String appointmentReason) {
				IHGUtil.PrintMethodName();

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
				Thread.sleep(5000);
				new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(reason));
				try {
						return (reason.getText().contains(appointmentReason) && preferredTime.getText().contains("Early Morning, Late Afternoon") && requestedDay.getText()
								.contains("Monday - Thursday") && requestedTime.getText().contains("Anytime"));
				} catch (Exception e) {
						log(e.getCause().toString());
						return false;
				}
		}
}
