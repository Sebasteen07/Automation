// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.Appointment.CancResc;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;
import com.medfusion.product.object.maps.pss2.page.util.CommonMethods;

public class CancelRescheduleDecisionPage extends PSS2MainPage {
	
	@FindAll({@FindBy(how = How.XPATH, using = "//div[@class='col-sm-6']")})
	private List<WebElement> appointmentScheduledDetails;
	
	
	@FindBy(how = How.XPATH, using = "//button[@value='cancel']")
	private WebElement buttonCancel;
	
	
	@FindBy(how = How.XPATH, using = "//button[@value='reschedule']")
	private WebElement buttonReschedule;

	public CancelRescheduleDecisionPage(WebDriver driver) {
		super(driver);
	}
	
	CommonMethods commonMethods = new CommonMethods(driver);

	@Override
	public boolean areBasicPageElementsPresent() {
		
		ArrayList<WebElement> webElementList= new ArrayList<WebElement>();
		commonMethods.highlightElement(buttonCancel);
		webElementList.add(buttonCancel);
		commonMethods.highlightElement(buttonReschedule);
		return new IHGUtil(driver).assessAllPageElements(webElementList, this.getClass());
	}
	
	public void clickCancelll() {
		
		log("The previous Appointment details are as below");
		for (WebElement a:appointmentScheduledDetails) {
			log(a.getText());
		}
		buttonCancel.click();
	}
	
	public HomePage clickCancel() throws InterruptedException {
		
		log("The previous Appointment details are as below");
		for (WebElement a:appointmentScheduledDetails) {
			log(a.getText());
		}
		buttonCancel.click();
		Thread.sleep(1000);
		return PageFactory.initElements(driver, HomePage.class);
	}

}
