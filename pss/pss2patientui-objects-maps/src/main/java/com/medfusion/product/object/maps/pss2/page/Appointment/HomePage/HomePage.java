package com.medfusion.product.object.maps.pss2.page.Appointment.HomePage;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.product.object.maps.pss2.page.AppEntryPoint.StartAppointmentInOrder;
import com.medfusion.product.object.maps.pss2.page.Appointment.Location.Location;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Menu.PSSPatientFooter;
import com.medfusion.product.object.maps.pss2.page.Appointment.Menu.PSSPatientHeader;
import com.medfusion.product.object.maps.pss2.page.Appointment.Provider.Provider;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.AppointmentPage;
public class HomePage extends PSS2MainPage {

	PSSPatientHeader patientheader;
	PSSPatientFooter patientfooter;

	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div[1]/div[2]/div[3]/div/div[2]/div/a[1]")
	public WebElement buttonSpeciality1;

	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div[1]/div[2]/div[3]/div/div[2]/div/a[2]")
	public WebElement buttonSpeciality2;

	@FindBy(how = How.CLASS_NAME, using = "topupcoming")
	public WebElement topUpComingList;

	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div[1]/div[1]/div[1]/div/div[2]/button/span[2]")
	public WebElement buttonNameCircle;

	@FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/div/div/div[1]/div[1]/div[1]/div/div[2]/button/span[1]")
	public WebElement labelPatientName;

	@FindBy(how = How.XPATH, using = "//*[@id=\"myModal\"]/div/div/div[3]/div[2]/button/span")
	public WebElement buttonCancelAppointment;

	@FindBy(how = How.XPATH, using = "//*[@id=\"myModal\"]/div/div/div[3]/div[3]/button/span")
	public WebElement buttonRevertCancelAppointment;

	@FindAll({@FindBy(css = "a[class='btn startingpoint-btn']")})
	public List<WebElement> selectSpecialityList;

	@FindAll({@FindBy(css = "a[class='btn specialtybtndashboard handle-text-Overflow ']")})
	public List<WebElement> selectUpcomingApptList;

	@FindAll({@FindBy(css = "a[class='btn specialtybtndashboard handle-text-Overflow ']")})
	public List<WebElement> selectPastApptList;

	@FindBy(how = How.XPATH, using = ".//*[@id='upcomingevents']/p/span")
	public WebElement noUpcomingText;

	@FindBy(how = How.XPATH, using = ".//*[@id='pastappointmentevent']/p/span")
	public WebElement noPastText;

	public HomePage(WebDriver driver) {
		super(driver);
		patientheader = PageFactory.initElements(driver, PSSPatientHeader.class);
		patientfooter = PageFactory.initElements(driver, PSSPatientFooter.class);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(buttonNameCircle);
		webElementsList.add(labelPatientName);
		return assessPageElements(webElementsList);
	}

	public StartAppointmentInOrder selectSpeciality(String specialityText) {
		for (int i = 0; i < selectSpecialityList.size(); i++) {
			if (selectSpecialityList.get(i).getText().equalsIgnoreCase(specialityText)) {
				selectSpecialityList.get(i).click();
				return PageFactory.initElements(driver, StartAppointmentInOrder.class);
			}
		}
		return null;
	}

	public Location selectLocation(String specialityText) {
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
		for (int i = 0; i < selectSpecialityList.size(); i++) {
			if (selectSpecialityList.get(i).getText().equalsIgnoreCase(specialityText)) {
				selectSpecialityList.get(i).click();
				return PageFactory.initElements(driver, AppointmentPage.class);
			}
		}
		return null;
	}

	public void logout() {
		patientheader.logout();
	}
}
