package com.intuit.ihg.product.object.maps.smintegration.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;


public class AppointmentReqtStep2Page extends BasePageObject {

	public static final String PAGE_NAME = "Appt Request Page - Step 2";

	@FindBy(how = How.NAME, using = "timeframeWrapper:_body:timeframe")
	private WebElement dropDownPreferredTimeFrame;

	@FindBy(how = How.NAME, using = "prefdayWrapper:_body:prefday")
	private WebElement dropDownPreferredDay;

	@FindBy(how = How.NAME, using = "preftimeWrapper:_body:preftime")
	private WebElement choosePreferredTime;

	@FindBy(how = How.NAME, using = "reasonWrapper:_body:reason")
	private WebElement apptReason;

	@FindBy(how = How.NAME, using = "preferenceWrapper:_body:preference")
	private WebElement dropDownWhichIsMoreImportant;

	@FindBy(how = How.NAME, using = "homephoneWrapper:_body:homephone")
	private WebElement homePhone;

	// @FindBy( how = How.NAME, using="buttonpanel:submit")
	@FindBy(xpath = "//input[contains(@name,':submit')]")
	private WebElement btnContinue;

	private long createdTs;

	public AppointmentReqtStep2Page(WebDriver driver) {
		super(driver);
		this.createdTs = System.currentTimeMillis();
		PageFactory.initElements(driver, this);
	}

	public long getCreatedTs() {
		IHGUtil.PrintMethodName();
		return createdTs;
	}

	public String fillInForm(String sPreferredTimeFrame, String sPreferredDay, String sChoosePreferredTime, // Morning, Afternoon, 8AM - 11AM
			String sApptReason, String sWhichIsMoreImportant, // Specific Provider, Day of Week, Time of Day
			String sHomePhone) throws InterruptedException {

		IHGUtil.PrintMethodName();

		Select selectPreferredTimeFrame = new Select(dropDownPreferredTimeFrame);
		selectPreferredTimeFrame.selectByIndex(1); // TODO - use parameter

		Select selectPreferredDay = new Select(dropDownPreferredDay);
		selectPreferredDay.selectByIndex(1); // TODO - use parameter
		String appReason = sApptReason + " " + createdTs;
		choosePreferredTime.sendKeys(sChoosePreferredTime);
		apptReason.sendKeys(appReason);

		Select selectWhichIsMoreImportant = new Select(dropDownWhichIsMoreImportant);
		selectWhichIsMoreImportant.selectByIndex(1); // TODO - use parameter

		if (sHomePhone != null) {
			homePhone.clear(); // May be filled in if user set default.
			homePhone.sendKeys(sHomePhone);
		}

		btnContinue.click();
		return appReason;
	}

}
