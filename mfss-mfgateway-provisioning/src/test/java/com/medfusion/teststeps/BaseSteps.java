package com.medfusion.teststeps;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

/**
 * Created by lubson on 23.12.15.
 */
public class BaseSteps {

    protected WebDriver webDriver;
    protected Logger logger;

    public BaseSteps(WebDriver webDriver, Logger logger) {
        this.webDriver = webDriver;
        this.logger = logger;
    }
}
