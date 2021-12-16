//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.integrationplatform.utils;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;

public class MailinatorUtils extends MedfusionPage{
	
 	private static final String MAILINATOR_INBOX_TEMPLATE_URL = "https://www.mailinator.com/v4/public/inboxes.jsp?to=";

	@FindBy(how = How.XPATH, using = "(//tr[@ng-repeat='email in emails'])[1]/td[3]")
	private WebElement firstRowSubject;

	private WebElement signInToView;

	@FindBy(how = How.XPATH, using = "//iframe[@id='html_msg_body']")
	private WebElement iframe;

	@FindBy(how = How.ID, using = "paymentPreference_Electronic")
	private WebElement electronicPaymentPreference;

	public MailinatorUtils(WebDriver driver, String url) {
	    super(driver, url);
	}

	public MailinatorUtils(WebDriver driver) {
	    super(driver);
	}

	public String getLinkFromEmail(String username, String emailSubject, String findInEmail, int retries) {
	    String link = "";
	    this.driver.get(MAILINATOR_INBOX_TEMPLATE_URL + username);
	    try {
		if (this.isTextVisible(emailSubject)) {
		    this.clickOnElement(firstRowSubject);
		    this.driver.switchTo().frame(iframe);

		    String parentWindow = driver.getWindowHandle();
		    driver.findElement(By.xpath("//a[contains(text(),'" + findInEmail + "')]")).click();

		    Set<String> handles = driver.getWindowHandles();
		    for (String windowHandle : handles) {
			if (windowHandle.equals(parentWindow)) {
			    driver.close(); // closing parent window
			} else {
			    driver.switchTo().window(windowHandle);
			}
		    }
		    link = this.driver.getCurrentUrl();
		}
	    } catch (Exception e) {
		log("Exception found: " + e.getMessage());
	    }
	    return link;
	}

}
