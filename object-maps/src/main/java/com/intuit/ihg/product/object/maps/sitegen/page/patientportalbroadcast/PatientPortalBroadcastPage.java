//Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.sitegen.page.patientportalbroadcast;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class PatientPortalBroadcastPage extends BasePageObject {

	@FindBy(xpath = "//input[contains(@name,'addBroadcastButton')]")
	private WebElement addBroadcastButton;
	
	@FindBy(xpath="//input[contains(@name,'title')]")
	private WebElement titleField;
	
	@FindBy(xpath="//textarea[contains(@name,'message')]")
	private WebElement broadcastMessage;
	
	@FindBy(xpath="//input[contains(@name,'buttons:submit')]")
	private WebElement submitButton;
	
	@FindBy(xpath="//span[contains(text(),'Delete')]")
	private WebElement deleteBroadcast;
	
	@FindBy(xpath = "//option[contains(text(),'Automation-Location-1')]")
	private WebElement selectLocation;
	
	@FindBy(xpath="//input[contains(@name,'active')]")
	private WebElement activateMessage;

	public PatientPortalBroadcastPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		// TODO Auto-generated constructor stub
	}

	public void addBroadCast(String title,String message) throws InterruptedException {
		IHGUtil.waitForElement(driver, 20, addBroadcastButton);
		addBroadcastButton.click();
		IHGUtil.waitForElement(driver, 0, titleField);
		titleField.sendKeys(title);
		Thread.sleep(1000);
		broadcastMessage.sendKeys(message);
		selectLocation.click();
		activateMessage.click();
		submitButton.click();
	}
	
	public void deleteBroadCast() throws InterruptedException {
		try {
		IHGUtil.waitForElement(driver, 20, deleteBroadcast);
		deleteBroadcast.click();
		}
		catch(Exception e) {
			System.out.println("No previous broadcast messages available");
		}
	}
}
