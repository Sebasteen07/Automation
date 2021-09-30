package com.medfusion.product.object.maps.appt.precheck.page.Setting;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.appt.precheck.util.BaseTest;

public class NotificationsPage extends BaseTest {

	@FindBy(how = How.XPATH, using = "//li[@class='mf-nav__item mf-nav__secondary-menu__item']/a")
	private WebElement notificationTab;

	@FindBy(how = How.CSS, using = "div.notification-features-checkbox > div:nth-child(3) > input")
	private WebElement displayPatientFirstNameCheckbox;

	@FindBy(how = How.XPATH, using = "//button[text()='Save']")
	private WebElement saveButton;

	public NotificationsPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void clickOnNotificationTab() {
		IHGUtil.waitForElement(driver, 5, notificationTab);
		notificationTab.click();
		log("Switch on notification tab");
	}

	public void displayPatientFirstNameCheckbox() throws InterruptedException {
		boolean selected = displayPatientFirstNameCheckbox.isSelected();
		if (!selected) {
			log("Enable 'Display patient's' first name' checkbox");
			displayPatientFirstNameCheckbox.click();
			Thread.sleep(3000);
		}
	}

	public void saveNotification() {
		IHGUtil.waitForElement(driver, 5, saveButton);
		saveButton.click();
	}

	public void enablePatientFirstNameCheckbox() throws InterruptedException {
		clickOnNotificationTab();
		displayPatientFirstNameCheckbox();
		saveNotification();
	}

}
