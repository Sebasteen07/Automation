package com.medfusion.product.object.maps.precheck.page.myAppointmentsList;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.product.object.maps.precheck.page.myAppointmentPreCheck.MyAppointmentPage;

public class AppointmentsListPage extends BasePageObject {

	@FindBy(how = How.XPATH, using = "/html/body/div[3]/div/div/h1[1]")
	private WebElement myAppointmentsLabel;
	
	public AppointmentsListPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public MyAppointmentPage selectAppointment(String appointmentDateToSelect) {
		List<WebElement> appointmentList = driver.findElements(By.xpath("//*[@id=\"appointments\"]/ul[1]/li"));	
		log("appointment Time is = "+appointmentDateToSelect);
		for(int i=0;i<appointmentList.size();i++) {
			WebElement timeText = driver.findElement(By.xpath("//*[@id=\"appointments\"]/ul[1]/li["+(i+1)+"]/h5"));
			if(timeText.getText().contains(appointmentDateToSelect)) {
				log("Appointment time Found at "+timeText.getText());
				timeText.click();
				return PageFactory.initElements(driver, MyAppointmentPage.class);
			}
			else {
				//log("Appointment time not found in upcoming appointment list.");
			}
		}
		return null;
	}
}
