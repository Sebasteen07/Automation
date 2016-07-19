package com.medfusion.mdvip.mdvipautomation;

//import org.eclipse.jetty.util.log.Log;
//import org.eclipse.jetty.util.log.StdErrLog;
import org.testng.annotations.Test;

import com.medfusion.mdvip.objects.MDVIPLoginPage;
import com.medfusion.mdvip.angular.NgWebDriver;
import com.medfusion.mdvip.objects.CommonUtils;
import com.medfusion.mdvip.objects.MDVIPCreateAccountPage;
import com.medfusion.mdvip.objects.MDVIPPojos;
import com.medfusion.mdvip.objects.PropertyLoader;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
//import org.openqa.selenium.interactions.Actions;
//import org.seleniumhq.selenium.fluent.*;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;

import static org.testng.Assert.*;


public class MDVIPAcceptanceTests

{
	private static final Logger log = LogManager.getLogger(MDVIPLoginPage.class);

	private FirefoxDriver driver;
	private NgWebDriver ngWebDriver;
	private MDVIPPojos mdvipData;
	
	@BeforeSuite
	public void before_suite() throws Exception {		
			//log("Step 1: Get The Bean");
					ApplicationContext applicationContext = new AnnotationConfigApplicationContext(PropertyLoader.class);
					
					mdvipData = applicationContext.getBean(MDVIPPojos.class);

					driver = new FirefoxDriver();
					driver.manage().timeouts().setScriptTimeout(180, TimeUnit.SECONDS);
					ngWebDriver = new NgWebDriver(driver);
					
					driver.get(mdvipData.getUrl());
					ngWebDriver.waitForAngularRequestsToFinish();

					Thread.sleep(10000);
					
					List<WebElement> elementList = driver.findElements(By.className("slider-slide"));
					
					//System.out.println("List Size: "+elementList.size());
					for(int i =0 ; i< elementList.size() ; i++) {			
						WebElement element = elementList.get(i);
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						element.click();
					}
	}

	@AfterSuite
	public void after_suite() throws Exception {
		//driver.quit();
	}

	@Test
	public void testLoginInValidCredentials() {
		
		log.info("Testcase for verifying Invalid Login");
		
		MDVIPLoginPage loginPage = new MDVIPLoginPage(driver);
		
		assertTrue(loginPage.verifyLoginPageElements());
		
		log.info("UserName: " +mdvipData.getInvalidUserName());
		log.info("Password: " +mdvipData.getInvalidPassword());
		
		loginPage.login(mdvipData.getInvalidUserName(), mdvipData.getInvalidPassword());
		
		try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(CommonUtils.verifyTextPresent(driver, "valid username & password combination."));
		log.info("Test case executed successfully");
		
	}
	
	@Test
	public void testLoginValidCredentials() {
	
		log.info("Testcase for verifying Valid Login");
		
		MDVIPLoginPage loginPage = new MDVIPLoginPage(driver);
		assertTrue(loginPage.verifyLoginPageElements());
		
		log.info("UserName: " +mdvipData.getValidUserName());
		log.info("Password: " +mdvipData.getValidPassword());
		
		loginPage.login(mdvipData.getValidUserName(), mdvipData.getValidPassword());
		
		//assertTrue(MDVIPLoginPage.verifyTextNotPresent(driver, "Invalid username & password combination."));		
		
	}
	
	@Test
	public void testCreateUserExisting() {
	
		log.info("Testcase to verify creation of new account with existing username");
		
		MDVIPCreateAccountPage createuserPage = new MDVIPCreateAccountPage(driver);
		
		log.info("UserName: " +mdvipData.getValidUserName());
		log.info("Password: " +mdvipData.getValidPassword());
		
		createuserPage.createAccount(mdvipData.getValidUserName(), mdvipData.getValidPassword());
		
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String testCreateUserExisting = "Username " + mdvipData.getValidUserName() + " is already taken";
		
		assertTrue(CommonUtils.verifyTextPresent(driver, testCreateUserExisting));		
		log.info("Test case executed successfully");
	}
	
	@Test
	public void testCreateUserNew() {
	
		log.info("Testcase to verify creation of new account");
		
		MDVIPCreateAccountPage createuserPage = new MDVIPCreateAccountPage(driver);
					
		int nos = CommonUtils.generateRandomNumber();
		
		String username =  mdvipData.getEmail();
		
		String[] userNames = username.split("@");
		
		username = userNames[0] + "+" + nos + userNames[1];
		
		log.info("Generated Username: "+ username);
 				
		String password =  mdvipData.getPassword();
		
		log.info("Password: " + password);
				
		createuserPage.createAccount(username, password);		
				
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.info("Test case executed successfully");
	}

}
