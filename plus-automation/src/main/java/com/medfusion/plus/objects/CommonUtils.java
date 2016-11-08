package com.medfusion.plus.objects;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CommonUtils {
	static public int generateRandomNumber() {
		Random r = new Random(System.currentTimeMillis());
		return r.nextInt(10000);
	}
	
	public static boolean verifyTextPresent(WebDriver driver, String value) throws InterruptedException {
		//return driver.getPageSource().contains(value);
		Thread.sleep(2000);
		String bodyText = driver.findElement(By.tagName("body")).getText();	
		return bodyText.contains(value);
	}
}