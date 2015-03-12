package com.intuit.ihg.product.object.maps.sitegen.page.discreteforms;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

/**
 * 
 * @author Petr Hajek
 * @ Date : 16/2/2015
 */

public class CalculatedFormDirectory extends BasePageObject {

	@FindBy( id = "formsSearchString")
	private WebElement searchField;
	
	@FindBy ( css = "button.green.search")
	private WebElement searchButton;
	
	@FindBy ( id = "addCalculatedFormsButton")
	private WebElement addButton;
	
	@FindBy ( css = "a.closeDialog.red")
	private WebElement closeButton;	
	
	@FindBy(xpath = "//tr[@class='calculatedRow']/td/input[@class='markCalculated']")
	private WebElement checkBox;

	@FindBy(xpath = "//input[@class='markCalculated']")
	private WebElement checkBox2;
	
	//Constructor
	public CalculatedFormDirectory(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}	
	
	public boolean searchForm(String formName) throws Exception	{
		
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, searchField);
		IHGUtil iHGUtil = new IHGUtil(driver);
		searchField.sendKeys(formName);
		searchButton.click();
		if (iHGUtil.exists(checkBox2)) {
			log("Second xpath also works.");
		} else {
			log("Second xpath doesnt work!");
		}
		return iHGUtil.exists(checkBox);

	}
	
	public void selectFound()	{
		
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, checkBox);
		if (!checkBox.isSelected())
			checkBox.click();
		
	}
	
	public void addSelected()	{
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, addButton);
		addButton.click();
	}
	
	public void closeDirectory() {
		closeButton.click();
	}
	
	
	
	
}
