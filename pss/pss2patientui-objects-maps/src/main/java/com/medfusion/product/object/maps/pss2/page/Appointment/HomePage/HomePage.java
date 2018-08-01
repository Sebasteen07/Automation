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
public class HomePage extends PSS2MainPage {

	PSSPatientHeader patientheader;
	PSSPatientFooter patientfooter;

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

	@FindAll({@FindBy(css = ".btn.startingpoint-btn")})
	private List<WebElement> selectSpecialityList;

	@FindAll({@FindBy(css = ".btn-link")})
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

	@FindAll({@FindBy(css = ".dismissbuttons")})
	private List<WebElement> dismissButtons;

	@FindBy(how = How.XPATH, using = "//*[@id=\"myModalsss\"]/div/div/div[3]/button/span")
	private WebElement dismissIDPPopUp;

	@FindBy(how = How.XPATH, using = "//*[@id=\"upcomingappoitment\"]/div[1]/div/div[3]/div[2]/div/div/div/div[3]/div[2]/button/span")
	private WebElement cancelModalPopup;

	@FindBy(how = How.CLASS_NAME, using = "okbuttons")
	private WebElement cancelAppointmentConfirmed;

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
		IHGUtil.waitForElement(driver, 120, selectSpecialityList.get((selectSpecialityList.size()-1)));

		for (int i = 0; i < selectSpecialityList.size(); i++) {
			if (selectSpecialityList.get(i).getText().equalsIgnoreCase(specialityText)) {
				selectSpecialityList.get(i).click();
				return PageFactory.initElements(driver, Location.class);
			}
		}
		return null;
	}

	public Provider selectProvider(String specialityText) {
		for (int i = 0; i < selectSpecialityList.size(); i++) {
			if (selectSpecialityList.get(i).getText().equalsIgnoreCase(specialityText)) {
				selectSpecialityList.get(i).click();
				return PageFactory.initElements(driver, Provider.class);
			}
		}
		return null;
	}

	public AppointmentPage selectAppointment(String specialityText) {
		log(" selectSpecialityList " + selectSpecialityList.size());
		for (int i = 0; i < selectSpecialityList.size(); i++) {
			if (selectSpecialityList.get(i).getText().equalsIgnoreCase(specialityText)) {
				selectSpecialityList.get(i).click();
				return PageFactory.initElements(driver, AppointmentPage.class);
			}
		}
		return null;
	}

	public OnlineAppointmentScheduling logout() {
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

	public Boolean cancelAppointment(String popupTextMessage) {
		if (cancelAppointmentList.size() > 0) {
			log("cancelAppointmentList display =" + cancelAppointmentList.get(0).isDisplayed());
			cancelAppointmentList.get(0).click();
			IHGUtil.waitForElement(driver, 60, cancelModalPopup);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,400)", "");
			cancelModalPopup.click();
			if (cancelAppointmentConfirmed.isDisplayed()) {
				cancelAppointmentConfirmed.click();
			}
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
		for(int i=0;i<selectUpcomingApptList.size();i++) {
			log("upcomingListText  = " + selectUpcomingApptList.get(i).getText());
		}
	}

	public StartAppointmentInOrder skipInsurance(WebDriver driver) {
		UpdateInsurancePage updateinsurancepage = PageFactory.initElements(driver, UpdateInsurancePage.class);
		updateinsurancepage.skipInsuranceUpdateOnHomePage();
		return PageFactory.initElements(driver, StartAppointmentInOrder.class);
	}

	public Speciality skipInsuranceForSpeciality(WebDriver driver) {
		UpdateInsurancePage updateinsurancepage = PageFactory.initElements(driver, UpdateInsurancePage.class);
		updateinsurancepage.skipInsuranceUpdateOnHomePage();
		return PageFactory.initElements(driver, Speciality.class);
	}
}
