package com.medfusion.tests;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

/**
 * Created by lhrub on 25.11.2015.
 */
public abstract class BaseWebdriverTest extends BaseTest {

        protected WebDriver webDriver = new FirefoxDriver();


    @Before
    public void setUpBase() {
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.manage().window().maximize();
    }

    @After
    public void tearDownBase() {
        webDriver.quit();
    }

}
