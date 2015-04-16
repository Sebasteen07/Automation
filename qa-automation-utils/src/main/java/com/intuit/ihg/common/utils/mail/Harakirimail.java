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
		while(true) {
			try{
				System.out.println("Finding element");
				element = driver.findElement(By.linkText(emailSubject));	
				System.out.println("Element found, click");
				element.click();
				break;
			}
			catch(NoSuchElementException ex){
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

		if(element == null) {
			System.out.println("Error: Patient email address is not showed");
			return null;
		}
		
		System.out.println("Patient email address was found");
		
		System.out.println("Finding patient reset password URL");
		element = driver.findElement(By.linkText(findInEmail));
		
		return element.getAttribute("href");	
	}
	
	public boolean isMessageInInbox(String username, String emailSubject, String findInEmail, int retries ) {		
		
		System.out.println("Navigation to https://harakirimail.com/inbox/" + username);
		driver.navigate().to("https://harakirimail.com/inbox/" + username);
		
		int maxCount = retries;
		int count = 1;
		
		WebElement element;
		while(true) {
			try{
				System.out.println("Finding element");
				element = driver.findElement(By.linkText(emailSubject));	
				System.out.println("Element found, click");
				element.click();
				break;
			}
			catch(NoSuchElementException ex){
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
}
