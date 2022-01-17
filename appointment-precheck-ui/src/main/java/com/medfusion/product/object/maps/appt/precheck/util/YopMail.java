package com.medfusion.product.object.maps.appt.precheck.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class YopMail extends BasePageObject {

	public YopMail(WebDriver driver) {
		super(driver);
	}

	public String unsubscribeEmail(String username) throws InterruptedException {
		driver.get("https://yopmail.com/en/");
		driver.manage().window().maximize();

		CommonMethods cm = new CommonMethods(driver);
		WebElement EmailNameTextBox = driver.findElement(By.xpath("//input[@id='login']"));
		Thread.sleep(2000);
		EmailNameTextBox.clear();
		EmailNameTextBox.sendKeys(username);
		Thread.sleep(2000);

		WebElement goInboxButton = driver.findElement(By.xpath("//button[@title='Check Inbox @yopmail.com']"));
		goInboxButton.click();
		Thread.sleep(2000);
		driver.switchTo().frame("ifinbox");

		List<WebElement> mailSubject = driver.findElements(By.xpath("//div[@class='m']/button/div[2]"));
		int size = mailSubject.size();
		
		for (int i = 0; i < size; i++) {
			String emailSubject = mailSubject.get(i).getText();
			if (emailSubject.equals("Your appointment is coming up!")) {
				cm.highlightElement(mailSubject.get(i));
				log("Email Subject " + emailSubject);
				jse.executeScript("arguments[0].click();", mailSubject.get(i));
				log("Clicked on email box");
			}
		}
		
		driver.switchTo().parentFrame();
		driver.switchTo().frame("ifmail");
		jse.executeScript("window.scrollBy(5000,0)", "");
		Thread.sleep(10000);
		
		WebElement cancelOrRescheduleLink = driver.findElement(By.xpath("//a[text()='unsubscribe']"));
		String cancelRescheduleLink = cancelOrRescheduleLink.getAttribute("href");
		log("Cancel or Reschedule Link- " + cancelRescheduleLink);
		jse.executeScript("arguments[0].click();", cancelOrRescheduleLink);
		
		Set<String> windows = driver.getWindowHandles();
		ArrayList<String> getWindows = new ArrayList<String>(windows);
		driver.switchTo().window(getWindows.get(1));
		String title = driver.getTitle();
		log("Title:- " + title);
		
		if(title.contains("Medfusion PreCheck")){
			IHGUtil.waitForElement(driver, 10, driver.findElement(By.xpath("//button[text()='Unsubscribe']")));
			WebElement unsubscribeButton = driver.findElement(By.xpath("//button[text()='Unsubscribe']"));
			unsubscribeButton.click();
		}
		
		IHGUtil.waitForElement(driver, 10, driver.findElement(By.xpath("//p[@class='mb-4']")));
		WebElement unsubscribeMessage = driver.findElement(By.xpath("//p[@class='mb-4']"));
		return unsubscribeMessage.getText();

	}

}
