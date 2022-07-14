// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientui.utils;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;
import com.medfusion.product.object.maps.pss2.page.util.CommonMethods;

public class YopMailUtility extends PSS2MainPage{

	
	public YopMailUtility(WebDriver driver) {
		super(driver);
	}
	
	public String getCancelRescheduleLink(WebDriver driver, String em) throws InterruptedException {

		driver.get("https://yopmail.com/en/");
		driver.manage().window().maximize();

		CommonMethods cm = new CommonMethods(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		WebElement d = driver.findElement(By.xpath("//input[@id='login']"));
		d.sendKeys(em);
		Thread.sleep(1000);

		WebElement goInboxButton = driver.findElement(By.xpath("//button[@title='Check Inbox @yopmail.com']"));
		goInboxButton.click();
		Thread.sleep(1000);
		driver.switchTo().frame("ifinbox");
		Thread.sleep(1000);
		List<WebElement> len = driver.findElements(By.xpath("//div[@class='m']/button/div[2]"));
		int l = len.size();

		List<WebElement> box = driver.findElements(By.xpath("//div[@class='m']/button"));
		log("Length of emailSubjectList " + l);
		for (int i = 0; i < l; i++) {

			cm.highlightElement(len.get(i));
			String emailSubject = len.get(i).getText();

			if (emailSubject.equalsIgnoreCase("Your appointment is now scheduled")) {
				log("emailSubject " + emailSubject);
				jse.executeScript("arguments[0].click();", box.get(i));
				log("Clicked success");
			}
		}
		driver.switchTo().parentFrame();
		driver.switchTo().frame("ifmail");

		jse.executeScript("window.scrollBy(5000,0)", "");
		Thread.sleep(1000);
		WebElement linkCancelReschedule = driver.findElement(By.xpath("//tbody[1]/tr[1]/td[1]/a[1]"));
		String cancelRescheduleLink = linkCancelReschedule.getAttribute("href");
		log("Link- " + cancelRescheduleLink);
		return cancelRescheduleLink;
	}
	
	public void deleteEmail(WebDriver driver, String em) throws InterruptedException {
		driver.get("https://yopmail.com/en/");
		driver.manage().window().maximize();
		Thread.sleep(2000);
		CommonMethods cm = new CommonMethods(driver);
		WebElement d = driver.findElement(By.xpath("//input[@id='login']"));
		d.clear();
		d.sendKeys(em);
		Thread.sleep(2000);

		WebElement goInboxButton = driver.findElement(By.xpath("//button[@title='Check Inbox @yopmail.com']"));
		goInboxButton.click();
		Thread.sleep(2000);

		driver.switchTo().frame("ifinbox");

		List<WebElement> subList = driver.findElements(By.xpath("//div[@class='m']/button"));
		List<WebElement> chkList = driver.findElements(By.xpath("//input[@class='mc']"));

		String subject_line = "Your appointment is now scheduled";
		for (int i = 0; i < subList.size(); i++) {

			if (subList.get(i).getText().contains(subject_line)) {

				log(subList.get(i).getText() + "---Text");

				cm.highlightElement(chkList.get(i));
				chkList.get(i).click();
			}
		}
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(550,0)", "");
		Thread.sleep(1000);
		driver.switchTo().parentFrame();

		WebElement buttonDelete = driver.findElement(By.xpath("//button[@id='delsel']"));

		driver.manage().deleteAllCookies();
		cm.highlightElement(buttonDelete);
		buttonDelete.click();
	}

}
