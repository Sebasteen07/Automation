//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.integrationplatform.utils;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;

public class MailinatorUtils extends MedfusionPage{
	
 	private static final String MAILINATOR_INBOX_TEMPLATE_URL = "https://www.mailinator.com/v4/public/inboxes.jsp?to=";

	private static final int TIME_TO_WAIT_MS = 10000;

	@FindBy(how = How.XPATH, using = "(//tr[@ng-repeat='email in emails'])[1]/td[3]")
	private WebElement firstRowSubject;

	@FindBy(how = How.XPATH, using = "//iframe[@id='html_msg_body']")
	private WebElement iframe;

	@FindBy(how = How.XPATH, using = "(//a[contains(@href,'https://www.mailinator.com/linker')])[2]")
	private WebElement linkInMailBody;

	public MailinatorUtils(WebDriver driver, String url) {
	    super(driver, url);
	}

	public MailinatorUtils(WebDriver driver) {
	    super(driver);
	}

	public String getLinkFromEmail(String username, String emailSubject, String findInEmail, int retries)
		throws InterruptedException {
	    this.driver.get(MAILINATOR_INBOX_TEMPLATE_URL + username);
	    for (int j = 1; j <= retries; j++) {
		try {
		    if (this.isTextVisible(emailSubject)) {
			this.clickOnElement(firstRowSubject);
			this.driver.switchTo().frame(iframe);

			String parentWindow = driver.getWindowHandle();

			try {
				driver.findElement(By.xpath("//a[contains(text(),'" + findInEmail + "')]")).click();
			} catch (Exception e) {
				log("Trying with the next link in email as first attempt failed with exception: " + e.getMessage());
				linkInMailBody.click();
			}

			Set<String> handles = driver.getWindowHandles();
			for (String windowHandle : handles) {
			    if (handles.size() > 1 && windowHandle.equals(parentWindow)) {
				driver.close(); // closing parent window
			    } else {
				driver.switchTo().window(windowHandle);
			    }
			}
			return this.driver.getCurrentUrl();
		    }
		} catch (Exception e) {
		    log("Exception found: " + e.getMessage());
		}
		logAttemptAndSleep(j, retries);
	    }

	    return null;
	}

	public boolean isMessageInInbox(String username, String emailSubject, String findInEmail, int retries)
		throws Exception {

	    for (int j = 1; j <= retries; j++) {
		this.driver.get(MAILINATOR_INBOX_TEMPLATE_URL + username);
		try {
		    if (this.isTextVisible(emailSubject)) {
			this.clickOnElement(firstRowSubject);
			this.driver.switchTo().frame(iframe);

			if (driver.findElement(By.xpath("//a[contains(text(),'" + findInEmail + "')]")).isDisplayed()) {
			    return true;

			}
		    }
		} catch (Exception e) {
		    log("Exception found: " + e.getMessage());
		}
		logAttemptAndSleep(j, retries);
	    }
	    return false;
	}

	private void logAttemptAndSleep(int currentAttempt, int maxAttempts) throws InterruptedException {
	    logAttemptAndSleep(currentAttempt, maxAttempts, false);
	}

	private void logAttemptAndSleep(int currentAttempt, int maxAttempts, boolean plural)
		throws InterruptedException {
	    Log4jUtil.log((plural ? "Emails were" : "Email was") + " not retrieved. Trial number " + currentAttempt
		    + "/" + maxAttempts + "."
		    + (currentAttempt != maxAttempts
			    ? " Waiting for e-mail" + (plural ? "s" : "") + " to arrive for " + TIME_TO_WAIT_MS / 1000
				    + " s."
			    : ""));
	    // we don't need to sleep after the last retry
	    if (currentAttempt != maxAttempts) {
		Thread.sleep(TIME_TO_WAIT_MS);
	    }
	}
}
