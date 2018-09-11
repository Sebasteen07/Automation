package com.medfusion.product.object.maps.pss2.page.Insurance;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;
import com.medfusion.product.object.maps.pss2.page.ConfirmationPage.ConfirmationPage;

public class UpdateInsurancePage extends PSS2MainPage {

	@FindBy(how = How.ID, using = "insurancecarrier")
	private WebElement inputInsuranceCarrier;

	@FindBy(how = How.ID, using = "memberID")
	private WebElement inputMemberID;

	@FindBy(how = How.ID, using = "groupID")
	private WebElement inputGroupID;

	@FindBy(how = How.ID, using = "insurancePhone")
	private WebElement inputInsurancePhone;

	@FindBy(how = How.CLASS_NAME, using = "btn-ins")
	private WebElement buttonDontUpdateInsurance;

	@FindBy(how = How.ID, using = "updateinfo")
	private WebElement buttonUpdateInsuranceInfo;

	public UpdateInsurancePage(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(inputInsuranceCarrier);
		webElementsList.add(inputMemberID);
		webElementsList.add(inputGroupID);
		webElementsList.add(inputInsurancePhone);
		webElementsList.add(buttonDontUpdateInsurance);
		webElementsList.add(buttonUpdateInsuranceInfo);
		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}

	public ConfirmationPage skipInsuranceUpdate() {
		buttonDontUpdateInsurance.click();
		return PageFactory.initElements(driver, ConfirmationPage.class);
	}

}
