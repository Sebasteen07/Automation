//Copyright 2013-2020 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.onlinesolutions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class ManageSolutionsPage extends BasePageObject {

	@FindBy(xpath = "//input[@name='btn_edit']")
	private WebElement btnEdit;
	
	@FindBy(xpath = "//td/b[contains(text(),'Patient Support')]/../following-sibling::td/input[@type='Checkbox']")
	private WebElement	chckBox;
	
	@FindBy(xpath = "//td/b[text()='Patient Support']/../following-sibling::td/input[@type='Checkbox']/..")
	private WebElement	chckBoxName;
	
	@FindBy(xpath = "//input[@name='btn_done']")
	private WebElement btnConfirmChanges;
	
	public ManageSolutionsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public ManageSolutionsPage clickOnEdit() throws InterruptedException {
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		jse.executeScript("scroll(0, document.body.scrollHeight);");
	    IHGUtil.setFrame(driver,"iframebody");
		IHGUtil.waitForElement(driver, 10,btnEdit);
		Thread.sleep(1000);
		btnEdit.click();
		IHGUtil.setDefaultFrame(driver);
		return PageFactory.initElements(driver, ManageSolutionsPage.class);
		}
	
	public ManageSolutionsPage clickActivateCheckbox() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver,"iframebody");
		IHGUtil.waitForElement(driver, 10, chckBox);
		System.out.println(chckBox.getText());
		if(chckBoxName.getText().contains("Activate"))
		{
			Thread.sleep(1000);
			chckBox.click();
		}
		
		IHGUtil.setDefaultFrame(driver);
		return PageFactory.initElements(driver, ManageSolutionsPage.class);
	}
	
	public ManageSolutionsPage clickDeActivateCheckbox() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver,"iframebody");
		IHGUtil.waitForElement(driver, 10, chckBox);
		if(chckBoxName.getText().contains("Deactivate"))
		{
			Thread.sleep(1000);
			chckBox.click();
		}
		
		IHGUtil.setDefaultFrame(driver);
		return PageFactory.initElements(driver, ManageSolutionsPage.class);
	}
	
	public ManageSolutionsPage confirmChanges() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver,"iframebody");
		IHGUtil.waitForElement(driver, 10, btnConfirmChanges);
		btnConfirmChanges.click();
		IHGUtil.setDefaultFrame(driver);
		return PageFactory.initElements(driver, ManageSolutionsPage.class);
	}
	
}
