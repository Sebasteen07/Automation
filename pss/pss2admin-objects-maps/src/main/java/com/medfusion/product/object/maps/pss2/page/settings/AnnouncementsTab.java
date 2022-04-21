//Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.medfusion.product.object.maps.pss2.page.settings;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.common.utils.IHGUtil;

public class AnnouncementsTab extends SettingsTab  {
	
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'100')]")
	private WebElement announcementSubTab;
	
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'100')]")
	private WebElement announcementSettingsSubTab;
	
	@FindBy(how = How.XPATH, using = "//a[@title='Add Announcement']")
	private WebElement addAnnouncementBtn;
	
	@FindBy(how = How.XPATH, using = "//tr//td[3]//a[@class='ng-star-inserted']")
	private WebElement deleteExistingAnnouncementLink;
	
	@FindBy(how = How.XPATH, using = "//strong[normalize-space()='Manage Announcement']")
	private WebElement headingManageAnnouncement;
	
	@FindBy(how = How.XPATH, using = "//label[normalize-space()='Purpose of message']")
	private WebElement messagePurposeLabel;
	
	@FindBy(how = How.XPATH, using = "//textarea[@id='displayPurpose']")
	private WebElement messagePurpose;
	
	
	@FindBy(how = How.XPATH, using = "//label[normalize-space()='Message *']")
	private WebElement announcementMessageLabel;
	
	@FindBy(how = How.XPATH, using = "//textarea[@id='announcementObject.textEN']")
	private WebElement announcementMessage;
	
	@FindBy(how = How.XPATH, using = "//textarea[@id='displayNameen']")
	private WebElement announcementMessage1;
	
	@FindBy(how = How.XPATH, using = "//button[normalize-space()='Save']")
	private WebElement announcementSave;
	
	@FindBy(how = How.XPATH, using = "//select[@name='type']")
	private WebElement announcementType;	
	
	@FindBy(how = How.XPATH, using = "//a[@title='Edit']//*[local-name()='svg']")
	private WebElement editMessage;

	@FindAll({ @FindBy(xpath = "//select/option") })
	private List<WebElement> announcementMsgDropdownList;
	

	public AnnouncementsTab(WebDriver driver) {
		super(driver);

	}
	
	
	public ArrayList<WebElement> verifyLabelOnManageAnnouncementsPage(WebDriver driver) {

		ArrayList<WebElement> we = new ArrayList<WebElement>();
		we.add(headingManageAnnouncement);
		we.add(announcementMessageLabel);
		we.add(messagePurpose);
		return we;
	}
	
	public void addAnnouncementMsg() throws InterruptedException {
		
		IHGUtil.waitForElement(driver, 10, addAnnouncementBtn);
		commonMethods.highlightElement(addAnnouncementBtn);
		addAnnouncementBtn.click();
		
		IHGUtil.waitForElement(driver, 10, announcementType);
		Select announcementList = new Select(announcementType);

		for (int i = 0; i < announcementMsgDropdownList.size(); i++) {			
			announcementList.selectByIndex(i);
			log("Dropdown"+i+" "+ announcementList.getFirstSelectedOption().getText());
			log("-----------Wait to loadPage-----");
			Thread.sleep(4000);
			commonMethods.highlightElement(messagePurpose);
			commonMethods.highlightElement(editMessage);
			editMessage.click();
			commonMethods.highlightElement(announcementMessage);
			announcementMessage.sendKeys("Automation " + i);
			commonMethods.highlightElement(announcementSave);
			announcementSave.click();
			Thread.sleep(3000);
			addAnnouncementBtn.click();
			Thread.sleep(2000);
		}
		
	}
	
	public void addAnnouncementWhenSpanishISDisabled(WebDriver driver,String greetingsText) throws InterruptedException {
		IHGUtil.waitForElement(driver, 10, addAnnouncementBtn);
		commonMethods.highlightElement(addAnnouncementBtn);
		addAnnouncementBtn.click();
		
		IHGUtil.waitForElement(driver, 10, announcementType);
		Select announcementList = new Select(announcementType);
		
		announcementList.selectByIndex(7);
		log("Greetings from Dropdown is selected");
		IHGUtil.waitForElement(driver, 10, editMessage);
		commonMethods.highlightElement(editMessage);
		editMessage.click();
		IHGUtil.waitForElement(driver, 10, announcementMessage1);
		announcementMessage1.clear();
		announcementMessage1.sendKeys(greetingsText);
		IHGUtil.waitForElement(driver, 10, announcementSave);
		commonMethods.highlightElement(announcementSave);
		announcementSave.click();
	}
	
}
