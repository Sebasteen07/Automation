//Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;

public class NGAppointmentPage extends JalapenoMenu {

	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Previous Requests')]")
	private WebElement previousAppoitmentRequestsButton;
	
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Upcoming')]")
	private WebElement Upcoming;
	
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Past')]")
	private WebElement Past;
	
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Request an Appointment')]")
	private WebElement RequestAppointment;
	
	public NGAppointmentPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(previousAppoitmentRequestsButton);
		webElementsList.add(Upcoming);
		webElementsList.add(Past);
		webElementsList.add(RequestAppointment);		
		return assessPageElements(webElementsList);
	}

	@Deprecated // same functionality as areBasicPageElementsPresent; used by integrations
	public boolean assessElements() {
		IHGUtil.PrintMethodName();

		log("Wait for elements");
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(previousAppoitmentRequestsButton);
		webElementsList.add(Upcoming);
		webElementsList.add(Past);
		webElementsList.add(RequestAppointment);

		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}
	
	public NGAppointmentRequestV2HistoryPage goToHistory(WebDriver driver) {
		IHGUtil.PrintMethodName();
		new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(previousAppoitmentRequestsButton)).click();
		return PageFactory.initElements(driver, NGAppointmentRequestV2HistoryPage.class);
	}
}
