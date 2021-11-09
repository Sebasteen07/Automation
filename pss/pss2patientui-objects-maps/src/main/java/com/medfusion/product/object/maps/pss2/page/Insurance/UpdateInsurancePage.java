// Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.Insurance;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.AppEntryPoint.StartAppointmentInOrder;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;
import com.medfusion.product.object.maps.pss2.page.ConfirmationPage.ConfirmationPage;
import com.medfusion.product.object.maps.pss2.page.util.CommonMethods;

public class UpdateInsurancePage extends PSS2MainPage {

	@FindBy(how = How.XPATH, using = "//div[@id=\"react-select-3--value\"]")
	private WebElement insuranceCarrier;
	
	@FindBy(how = How.XPATH, using = "//div[@class=\" css-1wy0on6\"]")
	private WebElement selectArrow;

	@FindBy(how = How.ID, using = "insurancecarrier")
	private WebElement inputInsuranceCarrier;

	@FindBy(how = How.ID, using = "memeberid")
	private WebElement inputMemberID;

	@FindBy(how = How.ID, using = "groupid")
	private WebElement inputGroupID;

	@FindBy(how = How.ID, using = "phoneNumber")
	private WebElement inputInsurancePhone;

	@FindBy(how = How.ID, using = "donotupdate")
	private WebElement buttonDontUpdateInsurance;

	@FindBy(how = How.ID, using = "updateinfo")
	private WebElement buttonUpdateInsuranceInfo;
	
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'css-1hwfws3')]")
	private WebElement dropdownInsuranceCar;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//div[@class=' css-1ub9c6o-option']") })
	private List<WebElement> insuranceCarrierDropDownList;

	public UpdateInsurancePage(WebDriver driver) {
		super(driver);
	}

	CommonMethods commonMethods = new CommonMethods(driver);

	public ConfirmationPage skipInsuranceUpdate() throws InterruptedException {
		jse.executeScript("window.scrollBy(0,350)", "");
		commonMethods.highlightElement(buttonDontUpdateInsurance);
		Thread.sleep(2000);
		buttonDontUpdateInsurance.click();
		return PageFactory.initElements(driver, ConfirmationPage.class);
	}

	public HomePage skipInsuranceUpdateAnonymous() throws InterruptedException {
		jse.executeScript("window.scrollBy(0,350)", "");
		commonMethods.highlightElement(buttonDontUpdateInsurance);
		Thread.sleep(2000);
		buttonDontUpdateInsurance.click();
		return PageFactory.initElements(driver, HomePage.class);
	}

	public void skipInsuranceUpdateOnHomePage() throws InterruptedException {
		log("don't update Insurance");
		Thread.sleep(2000);
		jse.executeScript("window.scrollBy(0,450)", "");
		commonMethods.highlightElement(buttonDontUpdateInsurance);
		buttonDontUpdateInsurance.click();
	}
	
	public void selectInsuranceCarrier() throws InterruptedException {
		List<WebElement> insuranceCarrierList = new ArrayList<WebElement>();
		Actions act = new Actions(driver);
		IHGUtil.waitForElement(driver, 5, dropdownInsuranceCar);
		commonMethods.highlightElement(dropdownInsuranceCar);
		selectArrow.click();
		IHGUtil.waitForElement(driver, 5, insuranceCarrierDropDownList.get(0));
		insuranceCarrierList = insuranceCarrierDropDownList;
		log("Save all the reschedule reason in rescheduleReasonlist ");
		if (insuranceCarrierList.size() > 0) {
			int length = insuranceCarrierList.size();
			log("There " + length + " number of Insurance reason ");
			for (WebElement a : insuranceCarrierList) {
				log(" Insurance Carrier - "+a.getText());
			}
			log("Selected Insurance Carrier Reason- " + insuranceCarrierList.get(length - 1).getText());
			act.moveToElement(insuranceCarrierList.get(length - 1)).click().build().perform();
		}
	}

	public void selectInsurance(String memberID, String groupID, String phoneNumber) throws InterruptedException {
		log("In selectInsurance of UpdateInsurance page.");
		selectInsuranceCarrier();
		inputMemberID.clear();
		commonMethods.highlightElement(inputMemberID);
		inputMemberID.sendKeys(memberID);
		inputGroupID.clear();
		commonMethods.highlightElement(inputGroupID);
		inputGroupID.sendKeys(groupID);
		inputInsurancePhone.clear();
		commonMethods.highlightElement(inputInsurancePhone);
		inputInsurancePhone.sendKeys(phoneNumber);
		scrollAndWait(0, 800, 500);
		commonMethods.highlightElement(buttonUpdateInsuranceInfo);
		buttonUpdateInsuranceInfo.click();
	}
	
	public ConfirmationPage selectInsuranceAtEnd(String memberID, String groupID, String phoneNumber) throws InterruptedException {
		log("In selectInsurance of UpdateInsurance page.");
		selectInsuranceCarrier();
		inputMemberID.clear();
		commonMethods.highlightElement(inputMemberID);
		inputMemberID.sendKeys(memberID);
		inputGroupID.clear();
		commonMethods.highlightElement(inputGroupID);
		inputGroupID.sendKeys(groupID);
		inputInsurancePhone.clear();
		commonMethods.highlightElement(inputInsurancePhone);
		inputInsurancePhone.sendKeys(phoneNumber);
		scrollAndWait(0, 800, 500);
		commonMethods.highlightElement(buttonUpdateInsuranceInfo);
		buttonUpdateInsuranceInfo.click();
		return PageFactory.initElements(driver, ConfirmationPage.class);
	}
}
