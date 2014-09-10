package com.intuit.ihg.product.community.utils;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ihg.common.utils.IHGUtil;


public class CommunityUtils extends BaseTestNGWebDriver{
	
	public String AssembleUrlForEnv(String practice, String env) {
        
		String url ="";
		
		if(env.equalsIgnoreCase("dev3")) {
			url = "https://"+practice+".dev3-ih.dev.medfusion.net/user/signin";
			
		} else if(env.equalsIgnoreCase("demo")) {
			url = "https://"+practice+".demo.intuithealth.com/user/signin";
			
		} else if (env.equalsIgnoreCase("prod")) {
				url = "https://"+practice+".intuithealth.com";
				
		} else if (env.equalsIgnoreCase("p10int")) {
			url = "https://"+practice+".int.intuithealth.com/user/signin";
		}
			 return url;
			
	}	

	public static final String PAGE_TITLE_INTUIT_HEALTH = new String("Medfusion");
	
	public static final String PAGE_TITLE_PRACTICE_PORTAL =  new String("Medfusion Secure Practice Portal");
	
	public static boolean validatePageTitle(WebDriver driver, String sTitle) throws InterruptedException{
		
		
		boolean titleFound = driver.getTitle().contains(sTitle);
		System.out.println("---Printing current URL: '"+driver.getCurrentUrl()+"' ---");
		System.out.println("---Printing current URL: '"+driver.getTitle()+"' ---");
		
		if (titleFound == false) {
			for (int i=1; i<10;i++) {
				Thread.sleep(1000);
				System.out.println("---Printing current URL: "+driver.getCurrentUrl()+"---");
				System.out.println("---Printing current PageTitle: "+driver.getTitle()+"---");
				titleFound = driver.getTitle().contains(sTitle);
				if (titleFound == true) {
					break;
				}
			}
			
		}
		return titleFound;
	}

	public String getTextError(WebDriver driver) {
		
		//Setting to 2 seconds since looking for the errors on loaded page
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		
		String error = driver.findElements(By.xpath("//div[@class='error']")).toString();
		
		if (error.equalsIgnoreCase("[]")){
			
			error = driver.findElements(By.cssSelector("div[class='error']")).toString();
		}
		
		if (error.equalsIgnoreCase("[]")) {
			error ="";
			return error;
		} 
		//Setting back to 30 as it is default value
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return error;
	}	
	
	//Creates random numbers
	
	public static int createRandomNumber() {

		IHGUtil.PrintMethodName();

		Random randomNumbers = new Random();

		int rnd = randomNumbers.nextInt(999999999);

		return rnd;
	}
	
		
	}
	
