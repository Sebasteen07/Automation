// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.Appointment.HomePage;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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

	@FindBy(how = How.XPATH, using = "/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[4]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[1]/div[3]/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]/pre[1]/div[1]")
	private WebElement cancelAppointmentPopup;	
	
	@FindBy(how = How.XPATH, using = "//div[@id='appointmentCancleModal']")
	private WebElement cancelpopupEmail;
	
	@FindBy(how = How.XPATH, using = "//span[normalize-space()='Are you sure you want to cancel your appointment?']")
	private WebElement cancelConfirmationviaEmail;

	@FindBy(how = How.XPATH, using = "/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[4]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[1]/div[3]/div[2]/div[1]/div[1]/div[1]/div[2]/span[1]")
	private WebElement cancelAppointmentPopupMSG;
	
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'We understand that there are times when you must m')]")
	private WebElement cancelAppointmentPopupMSGviaEmail;
	
	@FindAll({ @FindBy(xpath = "//div[@class=' css-11unzgr']/div") })
	private List<WebElement> cancelAppointmentdropdownlist;

	@FindBy(how = How.XPATH, using = "//span[normalize-space()='Select cancel reason']")
	private WebElement cancelAppointmentdropdownbox;

	@FindBy(how = How.XPATH, using = "//div[@class=' canceldropdown form-group']/div/div/div[2]")
	private WebElement selectarrowzone;

	@FindBy(how = How.XPATH, using = "//input[@id='cancelReasonText']")
	private WebElement cancelReason;

	@FindBy(how = How.XPATH, using = "//div//button[@class='submitcancel']")
	private WebElement cancelSubmit;	
	
	@FindBy(how = How.XPATH, using = "//button[@class='submitcancelbtn col-sm-3']")
	private WebElement cancelSubmitwithEmail;	

	@FindBy(how = How.XPATH, using = "//div[@id='appointmentCancleModal']")
	private WebElement cancelAppointmentConfirmedPopUp;	

	@FindBy(how = How.XPATH, using = "//div[@id='appointmentCancleModal']/div[2]/span")
	private WebElement cancelApptConfirmMsg;

	@FindBy(how = How.XPATH, using = "//button[@id='cancleNo']")
	private WebElement cancelNoBtnEmail;
	
	@FindBy(how = How.XPATH, using = "//div[@id='myModal']//div[4]//button[1]")
	private WebElement cancelYesBtnEmail;

	@FindBy(how = How.XPATH, using = "//button[@id='gotodashboard']//span[contains(text(),'Ok')]")
	private WebElement okCancelBtnEmail;
	
	@FindBy(how = How.XPATH, using = "//div[@id='appointmentCancleModal']")
	private WebElement cancelAppointmentConfirmed;	
	
	@FindBy(how = How.XPATH, using = "//div[@id='cancelAppnt']/div/div/div[2]")
	private WebElement cancelAppointmentConfirmedviaEmail;
	
	@FindBy(how = How.XPATH, using = "//span[normalize-space()='Yes']")
	private WebElement cancelYesButton;	

	@FindBy(how = How.XPATH, using = "//button[@class='okbuttons']")
	private WebElement okCancelBtn;	
	
	@FindBy(how = How.XPATH, using = "//button[@id='gotodashboard']")
	private WebElement okCancelBtnviaEmail;
	
	@FindAll({ @FindBy(xpath = "//a[@class='btn specialtybtndashboard handle-text-Overflow outer-div']") })
	private List<WebElement> selectSpecialityList;

	@FindAll({
			@FindBy(xpath = "//*[@class='col-sm-6 col-xs-12 provider-width-btn'or @class='btn providerimage-btn handle-text-Overflow outer-div ']") })
	private List<WebElement> selectproviderList;

	@FindAll({ @FindBy(xpath = "//div[@class='col-sm-6 col-xs-12 startingpointdata']") })
	private List<WebElement> selectstartpoint;

	@FindAll({ @FindBy(xpath = "//button//span[contains(text(),'Cancel')]") })
	private List<WebElement> cancelAppointmentList;
	
	@FindAll({ @FindBy(xpath = "//div[@id='upcomingevents']//div//div[1]//div[3]//div[2]//button[2]") })
	private List<WebElement> rescheduleAppointmentList;

	@FindAll({ @FindBy(xpath = "//*[@id=\"upcomingappoitment\"]/div") })
	private List<WebElement> selectUpcomingApptList;

	@FindAll({ @FindBy(xpath = "//*[@id='pastappointmentevent']/div/div") })
	private List<WebElement> selectPastApptList;

	@FindBy(how = How.XPATH, using = ".//*[@id='upcomingevents']/p/span")
	private WebElement noUpcomingText;

	@FindBy(how = How.ID, using = "searchspecialtydashboard")
	private WebElement specialitySearch;

	@FindBy(how = How.XPATH, using = ".//*[@id='pastappointmentevent']/p/span")
	private WebElement noPastText;

	@FindAll({@FindBy(xpath = "//body/div[@id='root']/div[1]/div[1]/div[1]/div[3]/div[1]/div[1]/div[1]/div[3]/button[1]") })
	private List<WebElement> dismissButtons;

	@FindBy(how = How.XPATH, using = "//*[@id='closeAlertPopup']")
	private WebElement dismissPopUp;

	
	@FindBy(how = How.XPATH, using = "//*[@id='upcomingevents']/h1/span")
    private WebElement upCmgAptLabel;  
	
	@FindAll({ @FindBy(xpath = "//*[@id='upcomingevents']/h1/span") })
	private List<WebElement> upcomingAptList;
	
	@FindAll({ @FindBy(xpath = "//*[@id='pastappointmentevent']/h1/span") })
	private List<WebElement> pastAptList;
	
	@FindBy(how = How.XPATH, using = "//*[@id='pastappointmentevent']/h1/span")
	private WebElement pastAptLabel;
	
	@FindBy(how = How.ID, using = "startScheduling")
	private WebElement btnstartScheduling;
	
	@FindAll({@FindBy(how = How.ID, using = "startScheduling")})
	private List<WebElement> btnstartSchedulingList;
	
	@FindBy(how = How.XPATH, using = "//span[@class='circle']")
	private WebElement logoutCircle;

	@FindBy(how = How.XPATH, using = "//*[@class='dropdown-menu']//a")
	private WebElement logout;

	@FindAll({ @FindBy(xpath = "//div[@class='row up-datediv']/b") })
	private List<WebElement> pastAptDateList;

	@FindBy(how = How.XPATH, using = "//div[@class='value-wizard']//div")
	private WebElement locationPreSelected;
	
	@FindBy(how = How.XPATH, using = "//*[@id='wizardwebview']/ol/li[4]/div/div[1]/div")
	private WebElement providerPreSelected;
	
	@FindBy(how = How.XPATH, using = "//div[@id='patientmatch']//span[contains(text(),'Message')]")
	private WebElement lockoutPopUp;	
	
	@FindBy(how = How.XPATH, using = "//div[@id='alertModalheader']//span[contains(text(),'Alert')]")
	private WebElement alertPopUp;
	
	@FindBy(how = How.CSS, using = "div[id='alertModalheader'] div[class='modal-body'] div")
	private WebElement alertPopUpMsg;
	
	@FindBy(how = How.XPATH, using = "//div[@id='patientmatch']//div[@class='modal-body']/p/pre")
	private WebElement lockoutPopUpMsg;
	
	@FindBy(how = How.ID, using = "closeAlertPopup")
	private WebElement alertDismiss;


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
	
	public String fetchPastAptDate() {
		IHGUtil.waitForElement(driver, 10, pastAptLabel);
		String pastAptDate=pastAptDateList.get(0).getText();
		log("Last Past Appointment Date- "+pastAptDate);
		return pastAptDate;
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
	
	public boolean isbtnstartSchedulingPresent() throws InterruptedException {
		boolean present = false;
		try {
			Thread.sleep(4000);
			present = btnstartScheduling.isDisplayed();
			Thread.sleep(4000);

		} catch (NoSuchElementException e) {
			throw new RuntimeException("This is where you put the message");
		}
		return present;
	}
	
	public void btnStartSchedClick() throws InterruptedException {
		if (isbtnstartSchedulingPresent() == true) {
			commonMethods.highlightElement(btnstartScheduling);
			btnstartScheduling.click();
			log("Clicked on Start Scheduling Button successfully");
		} else {
			log("Start Scheduling Button is not present on the screen");
		}
	}
	
	public static boolean isElementPresent(By by, WebDriver driver) 
    {
      boolean present;
      try
        {
    	  driver.findElement(by);
          present = true;
        }catch (NoSuchElementException e)
        {
          present = false;
         }
     return present;
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
	
	public boolean isCancelAppointmentListPresent() {
		if (cancelAppointmentList.size() > 0) {
			log("cancelAppointmentList display =" + cancelAppointmentList.get(0).isDisplayed());
			cancelAppointmentList.get(0).click();
			//IHGUtil.waitForElement(driver, 60, cancelReason);
			return true;
		} else {
			log("No Appointments found to cancel.");
			return false;
		}

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
	
	public Boolean cancelAppointmentWithEmail(String popupTextMessage) throws InterruptedException {

		IHGUtil.waitForElement(driver, 60, cancelReason);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,400)", "");
		cancelReason.sendKeys("Cancel Appointment to test the function");
		log("Send the below text in cancel input box --->Cancel Appointment to test the function ");
		
		commonMethods.highlightElement(cancelSubmitwithEmail);
		cancelSubmitwithEmail.click();
		Thread.sleep(3000);
		jse.executeScript("window.scrollBy(0,400)", "");
		log("Clicked on Submit Cancel button");
		if (cancelAppointmentConfirmedPopUp.isDisplayed()) {
			
			Thread.sleep(1000);
			commonMethods.highlightElement(cancelNoBtnEmail);
			commonMethods.highlightElement(cancelYesBtnEmail);
			cancelYesBtnEmail.click();
			Thread.sleep(1000);
			okCancelBtnEmail.click();
			log("appointment cancelled Successfully...");
		}
		Thread.sleep(3000);
		log("appointment cancelled...");
		return true;

	}
	
	public void clickRescheduleLink() throws InterruptedException {

		if (rescheduleAppointmentList.size() > 0) {
			log("rescheduleAppointmentList display =" + rescheduleAppointmentList.get(0).isDisplayed());
			rescheduleAppointmentList.get(0).click();

		} else {
			log("No Appointments found to Reschedule.");

		}
	}


	public void cancelAppointmentPMReason(ArrayList<String> listAdminCancelReason) throws InterruptedException {
		if (cancelAppointmentList.size() > 0) {
			log("cancelAppointmentList display =" + cancelAppointmentList.get(0).isDisplayed());
			cancelAppointmentList.get(0).click();

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,100)", "");

			Actions act = new Actions(driver);

			List<WebElement> cancelreasonlist = new ArrayList<WebElement>();

			IHGUtil.waitForElement(driver, 6, cancelAppointmentdropdownbox);
			commonMethods.highlightElement(cancelAppointmentdropdownbox);

			jse.executeScript("window.scrollBy(0,100)", "");
			Thread.sleep(100);
			cancelAppointmentdropdownbox.click();
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

			assertEquals(text, listAdminCancelReason, "The List are not atching Hence test case failed");
			log("The Cancel list from Admin and the Cancel list from Patient UI matched");
			log("The Cancel reasons are displaying as per the Admin COnfig");

			log("Displayed 01 " + cancelreasonlist.get(0).isDisplayed());
			log("Cancel Reason- " + cancelreasonlist.get(l - 2).getText());

			act.moveToElement(cancelreasonlist.get(l - 2)).click().build().perform();

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

	public void cancelAppointmentPMReasonviaEmail(ArrayList<String> listAdminCancelReason) throws InterruptedException {

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,100)", "");

		Actions act = new Actions(driver);

		List<WebElement> cancelreasonlist = new ArrayList<WebElement>();

		IHGUtil.waitForElement(driver, 5, cancelAppointmentdropdownbox);
		commonMethods.highlightElement(cancelAppointmentdropdownbox);

		jse.executeScript("window.scrollBy(0,100)", "");
		Thread.sleep(100);
		cancelAppointmentdropdownbox.click();
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

		assertEquals(text, listAdminCancelReason, "The List are not atching Hence test case failed");
		log("The Cancel list from Admin and the Cancel list from Patient UI matched");
		log("The Cancel reasons are displaying as per the Admin COnfig");

		log("Displayed 01 " + cancelreasonlist.get(0).isDisplayed());
		log("Cancel Reason- " + cancelreasonlist.get(l - 2).getText());

		act.moveToElement(cancelreasonlist.get(l - 2)).click().build().perform();

		cancelSubmitwithEmail.click();
		Thread.sleep(3000);
		jse.executeScript("window.scrollBy(0,400)", "");
		log("Clicked on Submit Cancel button");
		if (cancelAppointmentConfirmed.isDisplayed()) {
			Thread.sleep(1000);
			cancelYesButton.click();
			Thread.sleep(1000);
			okCancelBtnviaEmail.click();
			log("appointment cancelled Successfully...");
		}
		Thread.sleep(1000);

		log("appointment cancelled...");
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

			act.moveToElement(cancelreasonlist.get(l - 2)).click().build().perform();
			
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


	public void defaultcancelAppointmentviaEmail(String popupTextMessage, String cancelConfirmMessage)
			throws InterruptedException {

			IHGUtil.waitForElement(driver, 60, cancelpopupEmail);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,400)", "");

			commonMethods.highlightElement(cancelAppointmentPopupMSGviaEmail);
			commonMethods.highlightElement(cancelConfirmationviaEmail);

			assertEquals(cancelAppointmentPopupMSGviaEmail.getText().trim(), popupTextMessage.trim(), "Text not matched");
			
			assertEquals(cancelConfirmationviaEmail.getText().trim(), cancelConfirmMessage.trim());

			Thread.sleep(1000);
			jse.executeScript("window.scrollBy(0,400)", "");

			if (cancelAppointmentConfirmed.isDisplayed()) {
				Thread.sleep(1000);
				cancelYesButton.click();
				Thread.sleep(1000);
				okCancelBtnviaEmail.click();

				log("appointment cancelled Successfully...");
			}
			Thread.sleep(3000);
			log("appointment cancelled...");

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

			assertEquals(cancelAppointmentPopUpMessage.trim(), popupTextMessage.trim(), "Text not matched");

			assertEquals(cancelAppointmentPopupMSG.getText().trim(), cancelConfirmMessage.trim());

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

	public AppointmentPage skipInsurancepage(WebDriver driver) throws InterruptedException {
		UpdateInsurancePage updateinsurancepage = PageFactory.initElements(driver, UpdateInsurancePage.class);
		updateinsurancepage.skipInsuranceUpdateOnHomePage();
		return PageFactory.initElements(driver, AppointmentPage.class);
	}


	public Speciality skipInsuranceForSpeciality(WebDriver driver) throws InterruptedException {
		UpdateInsurancePage updateinsurancepage = PageFactory.initElements(driver, UpdateInsurancePage.class);
		updateinsurancepage.skipInsuranceUpdateOnHomePage();
		return PageFactory.initElements(driver, Speciality.class);
	}

	public StartAppointmentInOrder updateInsuranceInfo(WebDriver driver, String memberID,
			String groupID, String phone) throws InterruptedException {
		log("In updateInsuranceInfo of HomePage.");
		UpdateInsurancePage updateInsurancePage = PageFactory.initElements(driver, UpdateInsurancePage.class);
		updateInsurancePage.selectInsurance(memberID, groupID, phone);
		return PageFactory.initElements(driver, StartAppointmentInOrder.class);
	}

	public Speciality updateInsuranceForSpeciality(WebDriver driver, String memberID,String groupID, String phone) throws InterruptedException {
		log("In updateInsuranceForSpeciality of HomePage.");
		UpdateInsurancePage updateInsurancePage = PageFactory.initElements(driver, UpdateInsurancePage.class);
		updateInsurancePage.selectInsurance(memberID, groupID, phone);
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
	
	public void patientLogout(WebDriver driver) throws InterruptedException {
		Thread.sleep(1000);
		jse.executeScript("window.scrollBy(1000,0)");
		Thread.sleep(500);
		logoutCircle.click();
		Thread.sleep(500);
		IHGUtil.waitForElement(driver, 6, logout);
		logout.click();
		driver.manage().deleteAllCookies();
	}
	
	public String getLocationText()
	{
		String locationText=locationPreSelected.getText();
		log("Location preselected is" +locationText);
		return locationText;
	}
	public String getProviderText()
	{
		String providerText=providerPreSelected.getText();
		log("Provider preselected is" +providerText);
		return providerText;
	}
	
	public boolean isUpcomingAptPresent() {

		if (upcomingAptList.size() > 0 & pastAptList.size()>0) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	public String getTextLockoutPopUpMsg() {
		
		IHGUtil.waitForElement(driver, 10, lockoutPopUpMsg);
		commonMethods.highlightElement(lockoutPopUpMsg);
		String popupText=lockoutPopUpMsg.getText();
		return popupText;		
	}
	
	public String getTextAlertPopUpMsg() {
		
		IHGUtil.waitForElement(driver, 10, alertPopUpMsg);
		commonMethods.highlightElement(alertPopUpMsg);
		String popupText=alertPopUpMsg.getText();
		return popupText;		
	}
	
	public void clickAlertPopUp() {
		
		commonMethods.highlightElement(alertDismiss);
		alertDismiss.click();
	}

}
