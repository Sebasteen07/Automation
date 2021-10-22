//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved
package com.medfusion.product.object.maps.isoreporting.page;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.common.utils.IHGUtil;

public class AddNewTerminalPage extends ReportingConfigureTerminalsPage{

	public AddNewTerminalPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(how=How.ID,using="activationCode")
	private WebElement activationCodeField;
	
	@FindBy(how=How.ID,using="terminalLabel")
	private WebElement terminalLabelField;
	
	@FindBy(how=How.ID,using="serialNumber")
	private WebElement serialNumberField;
	
	@FindBy(how=How.ID,using="medfusionMerchantId")
	private WebElement merchantDropdown;
	
	@FindBy(how=How.ID,using="closeBtn")
	private WebElement cancelButton;
	
	@FindBy(how=How.ID,using="refBtn")
	private WebElement saveAndApplyButton;

	public void addNewTerminal(String activationcode, String label,String serialnumber, String merchant) {
		assessAddTerminalsPageElements();
		enterTerminalDetails(activationcode, label, serialnumber, merchant);
		Boolean isButtonEnabled = isSaveEnabled();
		if(isButtonEnabled.equals(true)){
			saveAndApplyButton.click();
		}
		else{
			//TODO after UI is wired to backend
		}
		
	}

	public Boolean isSaveEnabled() {
		Boolean saveButtonStatus = saveAndApplyButton.isEnabled();
		return saveButtonStatus;
		
	}

	public void enterTerminalDetails(String activationcode, String label,
			String serialnumber, String merchant) {
		activationCodeField.sendKeys(activationcode);
		terminalLabelField.sendKeys(label);
		serialNumberField.sendKeys(serialnumber);
		selectTerminalMerchant(merchant);
		
	}

	public void selectTerminalMerchant(String merchant) {
		Select select = new Select(merchantDropdown);
		select.selectByVisibleText(merchant);
		
	}

	public boolean assessAddTerminalsPageElements() {
		ArrayList<WebElement> allElements = new ArrayList<WebElement>();
        allElements.add(activationCodeField);
        allElements.add(terminalLabelField);
        allElements.add(serialNumberField);
        allElements.add(merchantDropdown);
        allElements.add(cancelButton);
        allElements.add(saveAndApplyButton);
        return new IHGUtil(driver).assessAllPageElements(allElements, this.getClass());
		
	}

}
