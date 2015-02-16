package com.intuit.ihg.product.object.maps.mobile.page;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: vvalsan
 * Date: 3/21/13
 * Time: 6:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class MobileBasePage extends BasePageObject {

    @FindBy(xpath = "//div[contains(@class,'ui-page-active')]/div/h1")
    private WebElement heading;

    @FindBy(xpath = "//div[contains(@class,'ui-page-active')]/div[@role='banner']/a[contains(@href,'#home')]")
    private WebElement home;

    @FindBy(xpath = "//div[contains(@class,'ui-page-active')]/div[@role='banner']/a[not(@href='#home') and not(@href='#')]")
    private WebElement back;

    @FindBy(xpath = "//div[contains(@class,'ui-page-active')]/div[@role='main']/*[contains(@class,'instruction')]")
    private WebElement instruction;

    @FindBy(xpath = "//*[@class='errorMsg']")
    private WebElement errorMsg;

    private final int waitTimeout = 15;
    public MobileBasePage(WebDriver driver) {

        super(driver);
        PageFactory.initElements(driver, this);
    }


    public String getHeaderText() {
        return heading.getText();
    }

    public String getErrorMsg() {
        IHGUtil iUtil = new IHGUtil(driver);
        if (iUtil.exists(errorMsg)) {
            return errorMsg.getText();
        } else return null;
    }

    public BasePageObject clickHome() throws InterruptedException {
        waitForHome(driver, 10);
        home.click();
        return PageFactory.initElements(driver, BasePageObject.class);
    }

    public BasePageObject clickBack() {
        back.click();
        return PageFactory.initElements(driver, BasePageObject.class);
    }

    public void waitForHome(WebDriver driver, int n) throws InterruptedException {
        IHGUtil.PrintMethodName();
        IHGUtil.waitForElement(driver, n, home);
    }

    public String getInstruction() {
        IHGUtil iUtil = new IHGUtil(driver);

        if (iUtil.exists(instruction)) {
            return instruction.getText();
        } else return null;
    }

    public boolean isErrorMsgPresent() throws InterruptedException {
        Thread.sleep(5000);
        IHGUtil iUtil = new IHGUtil(driver);
        return iUtil.exists(errorMsg);
    }

    public String processUrlForEnv(String url, String env) {
        if (env.equalsIgnoreCase("qa3")) {
            url = url.replace("dev3", "qa3");
        } else if (env.equalsIgnoreCase("prod")) {
            url = url.replace("dev3.dev", "www");
        } else if (env.equalsIgnoreCase("demo")) {
            url = url.replace("dev3.dev", "demo");
        } else if (env.equalsIgnoreCase("int")) {
            url = url.replace("dev3.dev", "int");
        }
        return url;
    }

    public boolean verifyPageLoaded() {        
        boolean pageLoaded = false;
        try{
        	IHGUtil.waitForElementByXpath(driver, "//*[@class='ui-mobile-viewport ui-overlay-c ui-mobile-viewport-transitioning viewport-slide']", waitTimeout);
        	pageLoaded = true;
        }
        catch (Exception e){
        	//If waitForElementByXpath throws any exception, it's obvious that the page isn't loaded 
        	//- this'll fail anyway in places where we assert that it is, so there's no need to propagate any exception
        	log("Something bad happened when waiting for the page to load! Step by step Login Logout please");
        }
        return pageLoaded;
    }

    //div[contains(@class,'ui-page-active')]/div/h1
    // home or cancel on all pages -  //div[contains(@class,'ui-page-active')]/div[@role="banner"]/a[@href="#home"]
    // back on all pages -  //div[contains(@class,'ui-page-active')]/div[@role="banner"]/a[not(@href="#home") and not(@href="#")]
    //add pharmacy has no home - its Done and back
    // Same with the cancel on Pharmacy details page - its Done or Back to Add Pharmacy page - these need to be handled in its own page
}
