//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved
package com.medfusion.payreporting.tests;

import java.io.IOException;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.object.maps.isoreporting.page.AddNewTerminalPage;
import com.medfusion.product.object.maps.isoreporting.page.ReportingConfigureTerminalsPage;
import com.medfusion.product.object.maps.isoreporting.page.ReportingNavigationMenu;

public class ReportingConfigureTerminals extends ReportingAcceptanceTests {
	
	protected PropertyFileLoader testData;
	
  @Test
  public void addANewTerminal() throws InterruptedException, IOException {
	  testData = new PropertyFileLoader();
	  log("Navigate to Configure terminals page");
	  ReportingNavigationMenu menu = PageFactory.initElements(driver, ReportingNavigationMenu.class);
      ReportingConfigureTerminalsPage terminalsPage = menu.navigateConfigureTerminals();
      terminalsPage.assessPageElements();
      terminalsPage.navigateToAddTerminalPage();
      log("Configure terminals page fields are OK");
      
      log("Add a new terminal");
      AddNewTerminalPage terminal = PageFactory.initElements(driver, AddNewTerminalPage.class);
      terminal.addNewTerminal(testData.getProperty("activation.code"), testData.getProperty("label"),
    		  testData.getProperty("serial.number"),testData.getProperty("terminal.merchant"));
      
  }

  
  
  
}
