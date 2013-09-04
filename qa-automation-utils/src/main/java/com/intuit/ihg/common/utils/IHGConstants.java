package com.intuit.ihg.common.utils;

import org.openqa.selenium.WebDriver;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;

public class IHGConstants extends BasePageObject{
	
	
	public IHGConstants(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	
	public final static int FIND_ELEMENTS_MAX_WAIT_SECONDS = 3;
	public final static int SELENIUM_IMPLICIT_WAIT_SECONDS = 5;
}
