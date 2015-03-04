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
				System.out.println("Refreshing page");
				driver.navigate().refresh();
			}		
		}
		
		element = driver.findElement(By.linkText(findInEmail));
		return element.getAttribute("href");	
	}		
}
