//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.integrationplatform.utils;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;

public class YopMailUtils extends MedfusionPage{

	private static final String YOPMAIL_URL = "https://yopmail.com/en/";

	private static final int TIME_TO_WAIT_MS = 10000;

	@FindBy(how = How.XPATH, using = "//button[@title='Check Inbox @yopmail.com']")
	private WebElement goToMailbox;

	@FindBy(how = How.XPATH, using = "//input[@title='Login']")
	private WebElement mailIdTextBox;

	@FindBy(how = How.XPATH, using = "(// body[@class='bodyinbox yscrollbar']//button)[1]")
	private WebElement firstRowSubject;

	@FindBy(how = How.XPATH, using = "// body[@class='bodyinbox yscrollbar']//button)")
	private List<WebElement> inboxRows;

	@FindBy(how = How.XPATH, using = "//iframe[@id='ifmail']")
	private WebElement iframe;

	@FindBy(how = How.XPATH, using = "//iframe[@id='ifinbox']")
	private WebElement iframeInbox;

	@FindBy(how = How.XPATH, using = "//p[contains(.,'activate')]")
	private WebElement linkInMailBody;

	public YopMailUtils(WebDriver driver, String url) {
	    super(driver, url);
	}

	public YopMailUtils(WebDriver driver) {
	    super(driver);
	}

	public boolean isTextVisible(String text) {
		return driver.findElement(By.xpath("// body[@class='bodyinbox yscrollbar']//div[contains(text(),'" + text + "')]")).isDisplayed();
	}

	public String getLinkFromEmail(String username, String emailSubject, String findInEmail, int retries)
		throws InterruptedException {
		this.driver.get(YOPMAIL_URL);
		mailIdTextBox.sendKeys(username);
		this.clickOnElement(goToMailbox);

	    for (int j = 1; j <= retries; j++) {
		try {
			this.driver.switchTo().frame(iframeInbox);
		    if (this.isTextVisible(emailSubject)) {
			this.clickOnElement(firstRowSubject);
			this.driver.switchTo().defaultContent();
			this.driver.switchTo().frame(iframe);

			String parentWindow = driver.getWindowHandle();

			try {
				driver.findElement(By.xpath("//body[@class='bodymail yscrollbar']//*[contains(text(),'" + findInEmail + "')]")).click();
			} catch (Exception e) {
				log("Trying with the next link in email as first attempt failed with exception: " + e.getMessage());
				return linkInMailBody.getText().trim();
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
				this.driver.get(YOPMAIL_URL);
				mailIdTextBox.sendKeys(username);
				this.clickOnElement(goToMailbox);
		try {
			this.driver.switchTo().frame(iframeInbox);
			if (this.isTextVisible(emailSubject)) {
				this.clickOnElement(firstRowSubject);
				this.driver.switchTo().defaultContent();
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

	public boolean catchNewMessageCheckLinkUrl(String username, String emailSubject, String findInEmail, String targetUrl, int retries) throws Exception {
		return catchNewMessage(username, emailSubject, findInEmail, targetUrl, retries) != null;
	}

	public String catchNewMessage(String username, String emailSubject, String findInEmail, String targetUrl, int retries) throws Exception {
		log("catchNewMessage()- with ", username);
		this.driver.get(YOPMAIL_URL);
		mailIdTextBox.sendKeys(username);
		this.clickOnElement(goToMailbox);

		try {
			this.driver.switchTo().frame(iframeInbox);
			int initialMailCount = inboxRows.size();

			if (this.isTextVisible(emailSubject)) {
				this.clickOnElement(firstRowSubject);
				this.driver.switchTo().defaultContent();
				this.driver.switchTo().frame(iframe);

				String parentWindow = driver.getWindowHandle();

				try {
					driver.findElement(By.xpath("//body[@class='bodymail yscrollbar']//*[contains(text(),'" + findInEmail + "')]")).click();
				} catch (Exception e) {
					log("Trying with the next link in email as first attempt failed with exception: " + e.getMessage());
					return linkInMailBody.getText().trim();
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
		return "";
}


}


