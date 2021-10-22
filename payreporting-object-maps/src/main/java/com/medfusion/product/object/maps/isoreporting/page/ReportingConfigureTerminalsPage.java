//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved
package com.medfusion.product.object.maps.isoreporting.page;

import java.util.ArrayList;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.medfusion.common.utils.IHGUtil;

public class ReportingConfigureTerminalsPage extends ReportingNavigationMenu{

	public ReportingConfigureTerminalsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(how=How.XPATH,using="//button[normalize-space()='+ Add New Terminal']")
	private WebElement addNewTerminalButton;
	
	@FindBy(how=How.ID,using="pageSize")
	private WebElement pageSizeDropdown;

	public boolean assessPageElements() {
		ArrayList<WebElement> allElements = new ArrayList<WebElement>();
        allElements.add(addNewTerminalButton);
        allElements.add(pageSizeDropdown);
        return new IHGUtil(driver).assessAllPageElements(allElements, this.getClass());
	}
	
	public void clickWebElement(WebElement webelement) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", webelement);
	}

	public void navigateToAddTerminalPage() {
		addNewTerminalButton.click();
		
	}

}
