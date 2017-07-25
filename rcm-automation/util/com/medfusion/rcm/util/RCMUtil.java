package com.medfusion.rcm.util;

import org.openqa.selenium.WebDriver;

import com.medfusion.common.utils.IHGUtil;

public class RCMUtil extends IHGUtil {

    protected WebDriver driver;
    
    
    public static int timeout = 0;
    public static String[] exeArg = null;

    public RCMUtil(WebDriver driver) {
        super(driver);
    }

    public WebDriver getDriver(WebDriver driver) {
        IHGUtil.PrintMethodName();
        return driver;
    }

}