package com.intuit.ihg.product.community.page.AskAQuestion;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.community.page.CommunityHomePage;

public class AskAQuestionMessage extends BasePageObject{
	
	@FindBy(how = How.XPATH, using = "//button[@type='submit']")
	public WebElement btnContinue;
	
	@FindBy(how =How.ID, using ="subject")
	public WebElement inputSubject;
	
	@FindBy(how = How.ID, using = "content")
	public WebElement inputContentElement;
	
	public AskAQuestionMessage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
		}
	public CommunityHomePage FilloutAskAQuestionDetails(String subject,String content )throws InterruptedException {
		
		inputSubject.sendKeys(subject);
		inputContentElement.sendKeys(content);
		btnContinue.click();
		return PageFactory.initElements(driver,CommunityHomePage.class);

}
}