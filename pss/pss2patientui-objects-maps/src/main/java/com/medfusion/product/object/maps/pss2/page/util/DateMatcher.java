// Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.util;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;

public class DateMatcher {
	public DateMatcher() {

	}

	public void selectDate(String dob, WebDriver driver) {
		String date_ent = dob;
		String date_ent1[] = date_ent.split("-");
		String shipFDay = date_ent1[0];
		int shipFDayInt = Integer.parseInt(shipFDay);
		Log4jUtil.log("Day=" + shipFDayInt);
		String shipFMonth = date_ent1[1];
		String shipFYear = date_ent1[2];
		String date_pres = driver.findElement(By.xpath(".//th[@title=\"Select Decade\"]")).getText();
		Log4jUtil.log("date_pres " + date_pres);
		Log4jUtil.log("Enabled ?" + driver.findElement(By.xpath(".//th[@title=\"Select Decade\"]")).isEnabled());
		Log4jUtil.log("Displayed ? " + driver.findElement(By.xpath(".//th[@title=\"Select Decade\"]")).isDisplayed());
		Log4jUtil.log("date >>>> " + date_pres);

		String dp[] = date_pres.split("-");
		Log4jUtil.log(shipFYear + "  " + dp[0]);
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		dp[0] = Integer.toString(currentYear);

		if (Integer.parseInt(shipFYear) < Integer.parseInt(dp[0])) {
			double diff = Double.parseDouble(dp[0]) - Double.parseDouble(shipFYear);
			Log4jUtil.log("Difference is " + diff);
			double diffInDec = diff / 10;
			int clicks = (int) Math.round(diffInDec);
			Log4jUtil.log("clicks :" + clicks);
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			for (int i = 0; i < clicks; i++) {
				driver.findElement(By.xpath(".//*[@title=\"Previous Decade\"]")).click();
			}
			// year
			List<WebElement> yearElem = driver.findElements(By.xpath(".//span[@class=\"year\"]"));
			for (WebElement e : yearElem) {
				// Log4jUtil.log(e.getText());
				if (e.getText().equalsIgnoreCase(shipFYear)) {
					e.click();
					break;
				}
			}

			// month
			List<WebElement> monthElem = driver.findElements(By.xpath(".//span[@class=\"month\"]"));
			for (WebElement e : monthElem) {
				// Log4jUtil.log(e.getText());
				if (e.getText().equalsIgnoreCase(shipFMonth)) {
					e.click();
					break;
				}
			}

			// day
			Boolean dayFound = false;
			List<WebElement> dayElem = driver.findElements(By.xpath(".//td[@class=\"day\"]"));
			for (WebElement e : dayElem) {
				// Log4jUtil.log(e.getText());
				if (e.getText().equalsIgnoreCase(Integer.toString(shipFDayInt))) {
					e.click();
					dayFound = true;
					break;
				}
			}

			// day weekend
			if (!dayFound) {
				List<WebElement> dayWkElem = driver.findElements(By.xpath(".//td[@class=\"day weekend\"]"));
				for (WebElement e : dayWkElem) {
					// Log4jUtil.log(e.getText());
					if (e.getText().equalsIgnoreCase(Integer.toString(shipFDayInt))) {
						e.click();
						break;
					}
				}
			}

			Log4jUtil.log("date is selected	");
		}
	}
}
