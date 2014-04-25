package com.intuit.ihg.product.portal.page.solutions.virtualofficevisit;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class VirtualOfficeVisitQuestionnairePage extends BasePageObject{

	public static final String PAGE_NAME = "Virtual Office Visit Questionnaire Page";
	
	@FindBy(xpath="//input[@value='Yes'][1]")
	private WebElement feverYes;
	
	// This page is very weird, and adding an index of the input 'No' did not make a difference
	@FindBy(xpath="//input[@value='No']")
	private List<WebElement> tobaccoNo;
	
	@FindBy(xpath="//input[@type='Text']")
	private WebElement medicationsInput;
	
	@FindBy(xpath="//input[@value='Continue']")
	private WebElement btnContinue;
	
	public VirtualOfficeVisitQuestionnairePage(WebDriver driver) {
		super(driver);
	}

	public VirtualOfficeVisitConfirmationPage completeQuestionnaire() {
		IHGUtil.PrintMethodName();
		// PortalUtil.setPortalFrame(driver);
		
		feverYes.click();
		tobaccoNo.get(1).click();
		medicationsInput.sendKeys("Advil, Tylenol");
		btnContinue.click();
		
		return PageFactory.initElements(driver, VirtualOfficeVisitConfirmationPage.class);
	}
}
