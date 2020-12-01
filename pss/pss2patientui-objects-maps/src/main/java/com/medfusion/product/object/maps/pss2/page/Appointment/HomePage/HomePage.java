// Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.Appointment.HomePage;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.AppEntryPoint.StartAppointmentInOrder;
import com.medfusion.product.object.maps.pss2.page.Appointment.DateTime.AppointmentDateTime;
import com.medfusion.product.object.maps.pss2.page.Appointment.Location.Location;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.OnlineAppointmentScheduling;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Menu.PSSPatientFooter;
import com.medfusion.product.object.maps.pss2.page.Appointment.Menu.PSSPatientHeader;
import com.medfusion.product.object.maps.pss2.page.Appointment.Provider.Provider;
import com.medfusion.product.object.maps.pss2.page.Appointment.Speciality.Speciality;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.AppointmentPage;
import com.medfusion.product.object.maps.pss2.page.ConfirmationPage.ConfirmationPage;
import com.medfusion.product.object.maps.pss2.page.Insurance.UpdateInsurancePage;
import com.medfusion.product.object.maps.pss2.page.RescheduleAppointment.RescheduleAppointment;
import com.medfusion.product.object.maps.pss2.page.util.CommonMethods;

public class HomePage extends PSS2MainPage {

	PSSPatientHeader patientheader;
	PSSPatientFooter patientfooter;

	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Insurance information')]")
	private WebElement insurancetext;

	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div[1]/div[2]/div[3]/div/div[2]/div/a[1]")
	private WebElement buttonSpeciality1;

	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div[1]/div[2]/div[3]/div/div[2]/div/a[2]")
	private WebElement buttonSpeciality2;

	@FindBy(how = How.CLASS_NAME, using = "topupcoming")
	private WebElement topUpComingList;

	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div[1]/div[1]/div[1]/div/div[2]/button/span[2]")
	private WebElement buttonNameCircle;

	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div[1]/div[1]/div[1]/div/div[2]/button/span[1]")
	private WebElement labelPatientName;

	@FindBy(how = How.XPATH, using = "//*[@id=\"myModal\"]/div/div/div[3]/div[2]/button/span")
	private WebElement buttonCancelAppointment;

	@FindBy(how = How.XPATH, using = "//*[@id=\"myModal\"]/div/div/div[3]/div[3]/button/span")
	private WebElement buttonRevertCancelAppointment;

	@FindBy(how = How.XPATH, using = "/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[4]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[1]/div[3]/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]/pre[1]/div[1]")
	private WebElement cancelAppointmentPopup;

	@FindBy(how = How.XPATH, using = "/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[4]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[1]/div[3]/div[2]/div[1]/div[1]/div[1]/div[2]/span[1]")
	private WebElement cancelAppointmentPopupMSG;

	@FindAll({ @FindBy(xpath = "//div[@id='react-select-4--list']/div") })
	private List<WebElement> cancelAppointmentdropdownlist;

	@FindBy(how = How.XPATH, using = "//span[normalize-space()='Select cancel reason']")
	private WebElement cancelAppointmentdropdownbox;

	@FindBy(how = How.XPATH, using = "//div[@class=' canceldropdown form-group']/div/div/span/span")
	private WebElement selectarrowzone;

	@FindBy(how = How.XPATH, using = "//input[@id='cancelReasonText']")
	private WebElement cancelReason;

	@FindBy(how = How.XPATH, using = "//div//button[@class='submitcancel']")
	private WebElement cancelSubmit;

	@FindBy(how = How.XPATH, using = "//body[@class='modal-open']/div[@id='root']/div/div/div[@class='container']/div/div[@id='dashboardmobileview']/div/div[@class='row']/div[@id='upcomingevents']/div[@id='upcomingappoitment']/div[1]/div[1]/div[3]/div[2]/div[1]/div[1]/div[1]/div[2]/span[1]")
	private WebElement cancelAppointmentConfirmed;

	@FindBy(how = How.XPATH, using = "//div[1]/div[1]/div[3]/div[2]/div[1]/div[1]/div[1]/div[3]/div[4]/button[1]/span[1]")
	private WebElement cancelYesButton;

	@FindBy(how = How.XPATH, using = "//button[@class='okbuttons']")
	private WebElement okCancelBtn;

	@FindAll({ @FindBy(xpath = "//a[@class='btn specialtybtndashboard handle-text-Overflow outer-div']") })
	private List<WebElement> selectSpecialityList;

	@FindAll({
			@FindBy(xpath = "//*[@class='col-sm-6 col-xs-12 provider-width-btn'or @class='btn providerimage-btn handle-text-Overflow outer-div ']") })
	private List<WebElement> selectproviderList;

	@FindAll({ @FindBy(xpath = "//div[@class='col-sm-6 col-xs-12 startingpointdata']") })
	private List<WebElement> selectstartpoint;

	@FindAll({
			@FindBy(xpath = "//*[@class=\"list-group-item listingOfappointments undefined\"]/div[3]/div[2]/button//span[contains(text(),'Cancel')]") })
	private List<WebElement> cancelAppointmentList;
	
	@FindAll({ @FindBy(xpath = "//div[@id='upcomingevents']//div//div[1]//div[3]//div[2]//button[2]") })
	private List<WebElement> rescheduleAppointmentList;

	@FindAll({ @FindBy(xpath = "//*[@id=\"upcomingappoitment\"]/div") })
	private List<WebElement> selectUpcomingApptList;

	@FindAll({ @FindBy(xpath = "//*[@id=\"pastappointmentevent\"]/div/div") })
	private List<WebElement> selectPastApptList;

	@FindBy(how = How.XPATH, using = ".//*[@id='upcomingevents']/p/span")
	private WebElement noUpcomingText;

	@FindBy(how = How.ID, using = "searchspecialtydashboard")
	private WebElement specialitySearch;

	@FindBy(how = How.XPATH, using = ".//*[@id='pastappointmentevent']/p/span")
	private WebElement noPastText;

	@FindAll({
			@FindBy(xpath = "//body/div[@id='root']/div[1]/div[1]/div[1]/div[3]/div[1]/div[1]/div[1]/div[3]/button[1]") })
	private List<WebElement> dismissButtons;

	@FindBy(how = How.XPATH, using = "//*[@id='closeAlertPopup']")
	private WebElement dismissPopUp;

	@FindBy(how = How.XPATH, using = "//*[@id=\"upcomingevents\"]/h2/span")
	private WebElement upCmgAptLabel;	

	public HomePage(WebDriver driver) {
		super(driver);
		patientheader = PageFactory.initElements(driver, PSSPatientHeader.class);
		patientfooter = PageFactory.initElements(driver, PSSPatientFooter.class);
	}

	public HomePage(WebDriver driver, String currentUrl) {
		super(driver, currentUrl);
		patientheader = PageFactory.initElements(driver, PSSPatientHeader.class);
		patientfooter = PageFactory.initElements(driver, PSSPatientFooter.class);
		PageFactory.initElements(driver, this);
	}

	CommonMethods commonMethods = new CommonMethods(driver);

	@Override
	public boolean areBasicPageElementsPresent() {
		if (selectSpecialityList.size() != 0) {
			IHGUtil.waitForElement(driver, 120, selectSpecialityList.get((selectSpecialityList.size() - 1)));
		}
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(upCmgAptLabel);
		return assessPageElements(webElementsList);
	}

	public StartAppointmentInOrder selectSpeciality(String specialityText) {
		log(selectSpecialityList.size() + " specialityText " + specialityText);
		for (int i = 0; i < selectSpecialityList.size(); i++) {
			if (selectSpecialityList.get(i).getText().equalsIgnoreCase(specialityText)) {
				selectSpecialityList.get(i).click();
				return PageFactory.initElements(driver, StartAppointmentInOrder.class);
			}
		}
		return null;
	}

	public Location selectLocation(String specialityText) {
		IHGUtil.waitForElement(driver, 120, selectSpecialityList.get((selectSpecialityList.size() - 1)));

		for (int i = 0; i < selectSpecialityList.size(); i++) {
			if (selectSpecialityList.get(i).getText().equalsIgnoreCase(specialityText)) {
				selectSpecialityList.get(i).click();
				return PageFactory.initElements(driver, Location.class);
			}
		}
		return null;
	}

	public Provider selectProvider(String specialityText) {
		for (int i = 0; i < selectproviderList.size(); i++) {
			if (selectproviderList.get(i).getText().equalsIgnoreCase(specialityText)) {
				selectproviderList.get(i).click();
				return PageFactory.initElements(driver, Provider.class);
			}
		}
		return null;
	}

	public Provider selectStartPoint(String specialityText) {
		for (int i = 0; i < selectstartpoint.size(); i++) {
			if (selectstartpoint.get(i).getText().equalsIgnoreCase(specialityText)) {
				selectstartpoint.get(i).click();
				return PageFactory.initElements(driver, Provider.class);
			}
		}
		return null;
	}

	public AppointmentPage selectAppointment(String specialityText) {
		log(" selectSpecialityList " + selectSpecialityList.size());
		for (int i = 0; i < selectSpecialityList.size(); i++) {
			if (selectSpecialityList.get(i).getText().equalsIgnoreCase(specialityText)) {

				log("Speciality Selected is" + selectSpecialityList.get(i).getText());
				selectSpecialityList.get(i).click();

				return PageFactory.initElements(driver, AppointmentPage.class);
			}
		}

		return PageFactory.initElements(driver, AppointmentPage.class);
	}

	public OnlineAppointmentScheduling logout() throws InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(1000,0)");
		patientheader.logout();
		return PageFactory.initElements(driver, OnlineAppointmentScheduling.class);
	}

	public void companyLogoClick() {
		patientheader.backToHomePage();
	}

	public Boolean isPopUP() {
		waitForPageToLoad();
		for (int i = 0; i < dismissButtons.size(); i++) {
			if (dismissButtons.get(i).isDisplayed() == true) {
				return true;
			}
		}
		return false;
	}

	public Boolean isInsuranceVisible() {
		return insurancetext.isDisplayed();
	}

	public void popUPClick() {
		for (int j = 0; j < dismissButtons.size(); j++) {
			if (dismissButtons.get(j).isDisplayed() == true) {
				dismissButtons.get(j).click();
			}
		}
	}

	public Boolean isIDPPopUp() {
		return dismissPopUp.isDisplayed();
	}

	public void popUPIDPClick() {
		dismissPopUp.click();
		;
	}

	public int getFutureAppointmentListSize() {
		return selectUpcomingApptList.size();
	}

	public int getPastAppointmentListSize() {
		return selectPastApptList.size();
	}

	public Boolean cancelAppointment(String popupTextMessage) throws InterruptedException {

		if (cancelAppointmentList.size() > 0) {
			log("cancelAppointmentList display =" + cancelAppointmentList.get(0).isDisplayed());
			cancelAppointmentList.get(0).click();
			IHGUtil.waitForElement(driver, 60, cancelReason);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,400)", "");
			cancelReason.sendKeys("Cancel Appointment to test the function");
			log("Send the below text in cancel input box --->Cancel Appointment to test the function ");
			cancelSubmit.click();
			Thread.sleep(3000);
			jse.executeScript("window.scrollBy(0,400)", "");
			log("Clicked on Submit Cancel button");
			if (cancelAppointmentConfirmed.isDisplayed()) {
				Thread.sleep(1000);
				cancelYesButton.click();
				Thread.sleep(1000);
				okCancelBtn.click();

				log("appointment cancelled Successfully...");
			}
			Thread.sleep(3000);
			log("appointment cancelled...");
			return true;
		} else {
			log("No Appointments found to cancel.");
			return false;
		}
	}
	
	public void clickRescheduleLink() throws InterruptedException {

		if (rescheduleAppointmentList.size() > 0) {
			log("rescheduleAppointmentList display =" + rescheduleAppointmentList.get(0).isDisplayed());
			rescheduleAppointmentList.get(0).click();

		} else {
			log("No Appointments found to Reschedule.");

		}
	}
	
	public RescheduleAppointment clickRescheduleLinkTrueFalse() throws InterruptedException {

		if (rescheduleAppointmentList.size() > 0) {
			log("rescheduleAppointmentList display =" + rescheduleAppointmentList.get(0).isDisplayed());
			rescheduleAppointmentList.get(0).click();

		} else {
			log("No Appointments found to Reschedule.");
		}

		return PageFactory.initElements(driver, RescheduleAppointment.class);
	}

	public void cancelAppointmentPMReason(ArrayList<String> listAdminCancelReason) throws InterruptedException {

		if (cancelAppointmentList.size() > 0) {
			log("cancelAppointmentList display =" + cancelAppointmentList.get(0).isDisplayed());
			cancelAppointmentList.get(0).click();

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,100)", "");

			Actions act = new Actions(driver);

			List<WebElement> cancelreasonlist = new ArrayList<WebElement>();

			IHGUtil.waitForElement(driver, 5, cancelAppointmentdropdownbox);
			commonMethods.highlightElement(cancelAppointmentdropdownbox);

			commonMethods.highlightElement(selectarrowzone);
			jse.executeScript("window.scrollBy(0,100)", "");
			Thread.sleep(100);
			selectarrowzone.click();
			Thread.sleep(1000);
			cancelreasonlist = cancelAppointmentdropdownlist;

			int l = cancelreasonlist.size();
			log("Cancel Reason length- " + l);

			ArrayList<String> text = new ArrayList<String>();

			int k = 0;
			// Storing List elements text into String array
			for (WebElement a : cancelreasonlist) {
				text.add(a.getText());
				k++;
			}

			Assert.assertEquals(text, listAdminCancelReason, "The List are not atching Hence test case failed");
			log("The Cancel list from Admin and the Cancel list from Patient UI matched");
			log("The Cancel reasons are displaying as per the Admin COnfig");

			log("Displayed 01 " + cancelreasonlist.get(0).isDisplayed());
			log("Cancel Reason- " + cancelreasonlist.get(l - 1).getText());

			act.moveToElement(cancelreasonlist.get(l - 1)).click().build().perform();

			cancelSubmit.click();
			Thread.sleep(3000);
			jse.executeScript("window.scrollBy(0,400)", "");
			log("Clicked on Submit Cancel button");
			if (cancelAppointmentConfirmed.isDisplayed()) {
				Thread.sleep(1000);
				cancelYesButton.click();
				Thread.sleep(1000);
				okCancelBtn.click();
				log("appointment cancelled Successfully...");
			}
			Thread.sleep(3000);

			log("appointment cancelled...");

		} else {
			log("No Appointments found to cancel.");
		}
	}
	
	public void cancelAppointmentPMReason() throws InterruptedException {

		if (cancelAppointmentList.size() > 0) {
			log("cancelAppointmentList display =" + cancelAppointmentList.get(0).isDisplayed());
			cancelAppointmentList.get(0).click();

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,100)", "");

			Actions act = new Actions(driver);

			List<WebElement> cancelreasonlist = new ArrayList<WebElement>();

			IHGUtil.waitForElement(driver, 5, cancelAppointmentdropdownbox);
			commonMethods.highlightElement(cancelAppointmentdropdownbox);

			commonMethods.highlightElement(selectarrowzone);
			jse.executeScript("window.scrollBy(0,100)", "");
			Thread.sleep(100);
			selectarrowzone.click();
			Thread.sleep(1000);
			cancelreasonlist = cancelAppointmentdropdownlist;

			int l = cancelreasonlist.size();
			log("Cancel Reason length- " + l);
			log("Displayed 01 " + cancelreasonlist.get(0).isDisplayed());
			log("Cancel Reason- " + cancelreasonlist.get(l - 1).getText());

			act.moveToElement(cancelreasonlist.get(l - 1)).click().build().perform();
			
			cancelSubmit.click();
			Thread.sleep(3000);
			jse.executeScript("window.scrollBy(0,400)", "");
			log("Clicked on Submit Cancel button");
			if (cancelAppointmentConfirmed.isDisplayed()) {
				Thread.sleep(1000);
				cancelYesButton.click();
				Thread.sleep(1000);
				okCancelBtn.click();
				log("appointment cancelled Successfully...");
			}
			Thread.sleep(3000);

			log("appointment cancelled...");

		} else {
			log("No Appointments found to cancel.");
		}
	}


	public void defaultcancelAppointment(String popupTextMessage, String cancelConfirmMessage)
			throws InterruptedException {

		if (cancelAppointmentList.size() > 0) {
			log("cancelAppointmentList display =" + cancelAppointmentList.get(0).isDisplayed());
			cancelAppointmentList.get(0).click();
			IHGUtil.waitForElement(driver, 60, cancelAppointmentPopup);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,400)", "");

			commonMethods.highlightElement(cancelAppointmentPopup);
			commonMethods.highlightElement(cancelAppointmentPopupMSG);

			String cancelAppointmentPopUpMessage = cancelAppointmentPopup.getText();

			Assert.assertEquals(cancelAppointmentPopUpMessage.trim(), popupTextMessage.trim(), "Text not matched");

			Assert.assertEquals(cancelAppointmentPopupMSG.getText().trim(), cancelConfirmMessage.trim());

			Thread.sleep(1000);
			jse.executeScript("window.scrollBy(0,400)", "");

			if (cancelAppointmentConfirmed.isDisplayed()) {
				Thread.sleep(1000);
				cancelYesButton.click();
				Thread.sleep(1000);
				okCancelBtn.click();

				log("appointment cancelled Successfully...");
			}
			Thread.sleep(3000);
			log("appointment cancelled...");

		} else {
			log("No Appointments found to cancel.");

		}
	}

	public void verifyAppointmentScheduledInPMSystem(String dateTimeText) {
		log("input text cancellationText " + dateTimeText);
		List<WebElement> upcomingAptDateTime = driver
				.findElements(By.xpath("//*[@id=\"upcomingappoitment\"]/div/div/div[1]/div[1]"));
		for (int i = 0; i < upcomingAptDateTime.size(); i++) {

			if (dateTimeText.contains(upcomingAptDateTime.get(i).getText())) {
				log("PM scheduled Text " + dateTimeText);
				driver.findElements(By.xpath("//*[@id=\"upcomingappoitment\"]/div/div/div[3]/div[2]")).get(i).getText();
			}
		}
	}

	public void waitForPageToLoad() {
		IHGUtil.waitForElement(driver, 120, upCmgAptLabel);
	}

	public void bookedAppointmentInUpcomingList(String aptFromPM) {
		for (int i = 0; i < selectUpcomingApptList.size(); i++) {
			log("upcomingListText  = " + selectUpcomingApptList.get(i).getText());
		}
	}

	public StartAppointmentInOrder skipInsurance(WebDriver driver) throws InterruptedException {
		UpdateInsurancePage updateinsurancepage = PageFactory.initElements(driver, UpdateInsurancePage.class);
		updateinsurancepage.skipInsuranceUpdateOnHomePage();
		return PageFactory.initElements(driver, StartAppointmentInOrder.class);
	}

	public Speciality skipInsuranceForSpeciality(WebDriver driver) throws InterruptedException {
		UpdateInsurancePage updateinsurancepage = PageFactory.initElements(driver, UpdateInsurancePage.class);
		updateinsurancepage.skipInsuranceUpdateOnHomePage();
		return PageFactory.initElements(driver, Speciality.class);
	}

	public StartAppointmentInOrder updateInsuranceInfo(WebDriver driver, String insuranceName, String memberID,
			String groupID, String phone) {
		log("In updateInsuranceInfo of HomePage.");
		UpdateInsurancePage updateinsurancepage = PageFactory.initElements(driver, UpdateInsurancePage.class);
		updateinsurancepage.selectInsurance(insuranceName, memberID, groupID, phone);
		return PageFactory.initElements(driver, StartAppointmentInOrder.class);
	}

	public Speciality updateInsuranceForSpeciality(WebDriver driver, String insuranceName, String memberID,
			String groupID, String phone) {
		log("In updateInsuranceForSpeciality of HomePage.");
		UpdateInsurancePage updateinsurancepage = PageFactory.initElements(driver, UpdateInsurancePage.class);
		updateinsurancepage.selectInsurance(insuranceName, memberID, groupID, phone);
		return PageFactory.initElements(driver, Speciality.class);
	}

	public StartAppointmentInOrder startpage() {
		log("It returns the StartAppointmentInOrder page ");
		return PageFactory.initElements(driver, StartAppointmentInOrder.class);
	}

	public Provider providerpage() {
		log("it returns on provider present and return to provider page");
		return PageFactory.initElements(driver, Provider.class);
	}

	public Location locationpage() {
		log("it returns on provider present and return to provider page");
		return PageFactory.initElements(driver, Location.class);
	}

	public AppointmentPage appointmentpage() {
		log("it returns on provider present and return to provider page");
		return PageFactory.initElements(driver, AppointmentPage.class);
	}

	public Speciality specilitypage() {
		log("it returns on specility page");
		return PageFactory.initElements(driver, Speciality.class);
	}
	
}
