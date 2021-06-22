//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.settings;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

public class AlertNotification extends SettingsTab {

	@FindAll({@FindBy(xpath = "//*[@id=\"alert\"]/div/div/table/tbody/tr/td/span/a")})
	private List<WebElement> activeAnnouncement;
	
	@FindAll({@FindBy(xpath = "//*[@id=\"alert\"]/div/div/table/tbody/tr/td[3]/a/i")})
	private List<WebElement> removeAllActiveAnnouncements;
	
	public AlertNotification(WebDriver driver) {
		super(driver);
	}
	
	public void getActiveAnnounvementName() {
		for(int i=0;i<activeAnnouncement.size();i++ ) {
			log(activeAnnouncement.get(i).getText());
		}
	}
	
	public void removeAllAnnouncements() {
		for(int i=removeAllActiveAnnouncements.size();i>=0;i-- ) {
			removeAllActiveAnnouncements.get(i).click();
		}
	}

}
