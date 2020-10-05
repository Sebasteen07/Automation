// Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.Appointment.HomePage;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.AppEntryPoint.StartAppointmentInOrder;
import com.medfusion.product.object.maps.pss2.page.Appointment.Location.Location;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.OnlineAppointmentScheduling;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Menu.PSSPatientFooter;
import com.medfusion.product.object.maps.pss2.page.Appointment.Menu.PSSPatientHeader;
import com.medfusion.product.object.maps.pss2.page.Appointment.Provider.Provider;
import com.medfusion.product.object.maps.pss2.page.Appointment.Speciality.Speciality;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.AppointmentPage;
import com.medfusion.product.object.maps.pss2.page.Insurance.UpdateInsurancePage;
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

	@FindAll({@FindBy(xpath = "//button[@class='btn appointmentType-btn handle-text-Overflow outer-div']")})
	private List<WebElement> selectSpecialityList;
	
	@FindAll({@FindBy(xpath = "//*[@class='col-sm-6 col-xs-12 provider-width-btn'or @class='btn providerimage-btn handle-text-Overflow outer-div ']")})
	private List<WebElement> selectproviderList;
	
	@FindAll({@FindBy(xpath = "//div[@class='col-sm-6 col-xs-12 startingpointdata']")})
	private List<WebElement> selectstartpoint;

	@FindAll({@FindBy(xpath = "//*[@class=\"list-group-item listingOfappointments undefined\"]/div[3]/div[2]/button//span[contains(text(),'Cancel')]")})
	private List<WebElement> cancelAppointmentList;

	@FindAll({@FindBy(xpath = "//*[@id=\"upcomingappoitment\"]/div")})
	private List<WebElement> selectUpcomingApptList;

	@FindAll({@FindBy(xpath = "//*[@id=\"pastappointmentevent\"]/div/div")})
	private List<WebElement> selectPastApptList;

	@FindBy(how = How.XPATH, using = ".//*[@id='upcomingevents']/p/span")
	private WebElement noUpcomingText;

	@FindBy(how = How.ID, using = "searchspecialtydashboard")
	private WebElement specialitySearch;

	@FindBy(how = How.XPATH, using = ".//*[@id='pastappointmentevent']/p/span")
	private WebElement noPastText;

	@FindAll({@FindBy(xpath = "//div[@id='myModalsss']//button[@class='dismissbuttons']")})
	private List<WebElement> dismissButtons;

	@FindBy(how = How.XPATH, using = "//div[@id='myModalsss']//button[@class='dismissbuttons']")
	private WebElement dismissIDPPopUp;

	@FindBy(how = How.XPATH, using = "//input[@id='cancelReasonText']")
	private WebElement cancelReason;

	@FindBy(how = How.XPATH, using = "//div//button[@class='submitcancel']")
	private WebElement cancelSubmit;

	@FindBy(how = How.XPATH,
			using = "//body[@class='modal-open']/div[@id='root']/div/div/div[@class='container']/div/div[@id='dashboardmobileview']/div/div[@class='row']/div[@id='upcomingevents']/div[@id='upcomingappoitment']/div[1]/div[1]/div[3]/div[2]/div[1]/div[1]/div[1]/div[2]/span[1]")
	private WebElement cancelAppointmentConfirmed;

	@FindBy(how = How.XPATH, using = "//div[@id='appointmentCancleModal']//div[3]//div[4]//button//span[contains(text(),'Yes')]")
	private WebElement cancelYesButton;

	@FindBy(how = How.XPATH, using = "//button[@class='okbuttons']")
	private WebElement okCancelBtn;

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
		return dismissIDPPopUp.isDisplayed();
	}
	
	

	public void popUPIDPClick() {
		dismissIDPPopUp.click();;
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
				cancelYesButton.click();
				Thread.sleep(500);
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

	public void verifyAppointmentScheduledInPMSystem(String dateTimeText) {
		log("input text cancellationText " + dateTimeText);
		List<WebElement> upcomingAptDateTime = driver.findElements(By.xpath("//*[@id=\"upcomingappoitment\"]/div/div/div[1]/div[1]"));
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

	public StartAppointmentInOrder updateInsuranceInfo(WebDriver driver, String insuranceName, String memberID, String groupID, String phone) {
		log("In updateInsuranceInfo of HomePage.");
		UpdateInsurancePage updateinsurancepage = PageFactory.initElements(driver, UpdateInsurancePage.class);
		updateinsurancepage.selectInsurance(insuranceName, memberID, groupID, phone);
		return PageFactory.initElements(driver, StartAppointmentInOrder.class);
	}

	public Speciality updateInsuranceForSpeciality(WebDriver driver, String insuranceName, String memberID, String groupID, String phone) {
		log("In updateInsuranceForSpeciality of HomePage.");
		UpdateInsurancePage updateinsurancepage = PageFactory.initElements(driver, UpdateInsurancePage.class);
		updateinsurancepage.selectInsurance(insuranceName, memberID, groupID, phone);
		return PageFactory.initElements(driver, Speciality.class);
	}
	public StartAppointmentInOrder startpage()
	{
		log("It returns the StartAppointmentInOrder page ");
		return PageFactory.initElements(driver, StartAppointmentInOrder.class);
}
	public Provider providerpage()
	{
		log("it returns on provider present and return to provider page");
		return PageFactory.initElements(driver, Provider.class);
  }
	public Location locationpage()
	{
		log("it returns on provider present and return to provider page");
		return PageFactory.initElements(driver, Location.class);
  }
	public AppointmentPage appointmentpage()
	{
		log("it returns on provider present and return to provider page");
		return PageFactory.initElements(driver, AppointmentPage.class);
  }
}
