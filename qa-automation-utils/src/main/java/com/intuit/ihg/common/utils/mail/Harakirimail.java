package com.intuit.ihg.common.utils.mail;

import org.openqa.selenium.*;

public class Harakirimail {

	private static WebDriver driver;

	public Harakirimail(WebDriver driver) {
		Harakirimail.driver = driver;
	}

	public String email(String username, String emailSubject, String findInEmail) {

		System.out.println("Navigation to https://harakirimail.com/inbox/" + username);
		driver.navigate().to("https://harakirimail.com/inbox/" + username);

		int maxCount = 12;
		int count = 1;

		WebElement element;
		while (true) {
			try {
				System.out.println("Finding element");
				element = driver.findElement(By.linkText(emailSubject));
				System.out.println("Element found, click");
				element.click();
				break;
			} catch (NoSuchElementException ex) {
				System.out.println("Refreshing page " + count + "/" + maxCount);
				driver.navigate().refresh();
			}

			if (count == maxCount) {
				System.out.println("Error: Email was not found");
				return null;
			}

			count++;
		}

		System.out.println("Finding patient email address");
		element = driver.findElement(By.xpath("//*[contains(.,'" + username + "@harakirimail.com" + "')]"));

		if (element == null) {
			System.out.println("Error: Patient email address is not showed");
			return null;
		}

		System.out.println("Patient email address was found");

		System.out.println("Finding patient reset password URL");
		element = driver.findElement(By.linkText(findInEmail));

		return element.getAttribute("href");
	}

	public boolean isMessageInInbox(String username, String emailSubject, String findInEmail, int retries) {

		System.out.println("Navigation to https://harakirimail.com/inbox/" + username);
		driver.navigate().to("https://harakirimail.com/inbox/" + username);

		int maxCount = retries;
		int count = 1;

		WebElement element;
		while (true) {
			try {
				System.out.println("Finding element");
				element = driver.findElement(By.linkText(emailSubject));
				System.out.println("Element found, click");
				element.click();
				break;
			} catch (NoSuchElementException ex) {
				System.out.println("Refreshing page " + count + "/" + maxCount);
				driver.navigate().refresh();
			}

			if (count == maxCount) {
				System.out.println("Error: Email was not found");
				return false;
			}

			count++;
		}
		element = driver.findElement(By.linkText(findInEmail));
		return element.isEnabled();
	}

	/**
	 * A method to catch new email messages even if the harakiri inbox fills up. accepts inbox name, message subject, link text to find in email and link url to
	 * verify against found link. if the inbox is full (10) of messages with the desired subject, tracks change in the first email date column caveat: will crash
	 * if an undesired message is received if the inbox was full to start with (a case I need not cover for our current need)
	 **/
	public boolean catchNewMessageCheckLinkUrl(String username, String emailSubject, String findInEmail, String targetUrl, int retries) {

		System.out.println("Navigation to https://harakirimail.com/inbox/" + username);
		driver.navigate().to("https://harakirimail.com/inbox/" + username);

		int maxCount = retries;
		int count = 1;
		boolean pagefull = false;
		String lastmessagedate = "";
		WebElement element;
		int elementCount = driver.findElements(By.linkText(emailSubject)).size();

		System.out.println("Matching messages count = " + elementCount);
		if (elementCount == 10) {
			pagefull = true;
			System.out.println("Inbox full of statement messages! Need to verify message date changing!");
			lastmessagedate = driver.findElement(By.xpath("//td[@class='time_column']")).getText();
			System.out.println("Date check running: Current is " + lastmessagedate);
		}

		System.out.println("Messages identified, waiting the initial 15s ");
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		while (true) {
			try {
				driver.navigate().refresh();
				System.out.println("Checking for new message");
				int currentCount = driver.findElements(By.linkText(emailSubject)).size();

				/*
				 * if a new message that does NOT match is received while inbox is full, current count will fall to 9 and not increase again even if the next would
				 * match = crash
				 */
				if (pagefull && currentCount == 10) {
					String newmessagedate = driver.findElement(By.xpath("//td[@class='time_column']")).getText();
					System.out.println("First message date found: " + newmessagedate);
					if (!newmessagedate.equals(lastmessagedate)) {
						System.out.println("Matching message with different date found in first slot!");
						element = driver.findElement(By.linkText(emailSubject));
						element.click();
						break;
					}
				}
				if ((!pagefull) && (currentCount > elementCount)) {
					System.out.println("New message found!");
					element = driver.findElement(By.linkText(emailSubject));
					element.click();
					break;
				}
			} catch (NoSuchElementException ex) {
				System.out.println("No messages found?" + count + "/" + maxCount);
			}

			if (count == maxCount) {
				System.out.println("Error: no new matching message was not found");
				return false;
			}
			System.out.println("No new message, need to refresh " + count + "/" + maxCount);
			count++;
		}
		element = driver.findElement(By.linkText(findInEmail));
		System.out.println(element.getAttribute("href"));
		System.out.println("Checking if link is enabled and targets as expected");
		if ((element.isEnabled()) && (targetUrl.equals(element.getAttribute("href")))) {
			return true;
		}
		return false;
	}
}
