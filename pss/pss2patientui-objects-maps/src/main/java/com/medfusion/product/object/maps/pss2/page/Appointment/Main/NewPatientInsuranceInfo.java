package com.medfusion.product.object.maps.pss2.page.Appointment.Main;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;

public class NewPatientInsuranceInfo extends PSS2MainPage {
	@FindBy(how = How.ID, using = "InsuranceCarrier")
	private WebElement inputInsuranceCarrier;
	@FindBy(how = How.ID, using = "memberID")
	private WebElement inputMemberID;
	@FindBy(how = How.ID, using = "groupID")
	private WebElement inputGroupID;
	@FindBy(how = How.ID, using = "insurancePhone")
	private WebElement inputInsurancePhone;
	@FindBy(how = How.CLASS_NAME, using = "custombuttoncancelins")
	private WebElement buttonCancel;
	@FindBy(how = How.CLASS_NAME, using = "custombuttonprevins")
	private WebElement buttonPrevious;
	@FindBy(how = How.CLASS_NAME, using = "custombuttonsubmitins")
	private WebElement buttonSubmit;
	@FindBy(how = How.XPATH, using = ".//div[@class='activewizard']/span/span")
	private WebElement insuranceInformation;
	@FindBy(how = How.XPATH, using = ".//a[@class='wizard-link']")
	private WebElement patientInfoLink;

	public NewPatientInsuranceInfo(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(insuranceInformation);
		webElementsList.add(buttonSubmit);
		webElementsList.add(buttonPrevious);
		webElementsList.add(patientInfoLink);
		webElementsList.add(inputGroupID);
		webElementsList.add(inputInsuranceCarrier);
		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}

	public HomePage fillNewPatientInsuranceInfo(String insuranceC, String memberId, String groupId, String primaryPhone) {
		inputInsuranceCarrier.sendKeys(insuranceC);
		inputMemberID.sendKeys(memberId);
		inputGroupID.sendKeys(groupId);
		inputInsurancePhone.sendKeys(primaryPhone);
		buttonSubmit.click();
		return PageFactory.initElements(driver, HomePage.class);
	}
}
