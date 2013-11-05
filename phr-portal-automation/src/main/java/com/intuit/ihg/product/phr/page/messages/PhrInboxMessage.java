package com.intuit.ihg.product.phr.page.messages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class PhrInboxMessage extends BasePageObject{

	@FindBy(xpath = ".//*[@id='msgDetail']/div[@class='msgHeader'][1]/div[@class='msgTitle']")
	private WebElement practiceResponseSubject;
	
	@FindBy(linkText = "Review Health Information")
	private WebElement btnReviewHealthInformation;
	
	@FindBy(id = "basicInfo")
	private WebElement ccdBasicInfo;
	
	@FindBy(css = "#lightbox > iframe:nth-child(1)")
	public WebElement CCDViewFrame;
	
	@FindBy(id = "closeCcd")
	private WebElement btnCloseViewer;
	
	
	
	public PhrInboxMessage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	
	 public String getPhrMessageSubject()
	 {
		 IHGUtil.PrintMethodName();
		 driver.switchTo().defaultContent();
		 driver.switchTo().frame("externalframe");
		 return practiceResponseSubject.getText().toString().trim();
	 }
	 
		public void clickBtnReviewHealthInformationPhr() throws InterruptedException {
			IHGUtil.PrintMethodName();
			driver.switchTo().defaultContent();
			driver.switchTo().frame("externalframe");
			//PortalUtil.setConsolidatedInboxFramePhr(driver);
			btnReviewHealthInformation.click();
			Thread.sleep(2000);
		}
		
		public void verifyCCDViewerAndClosePhr() throws InterruptedException {
			IHGUtil.PrintMethodName();
			driver.switchTo().defaultContent();
			driver.switchTo().frame(CCDViewFrame);
			if (ccdBasicInfo.isDisplayed() && btnCloseViewer.isDisplayed()) {
				btnCloseViewer.click();
			} else {
				Assert.fail("CCD Viewer not present: Could not find CCD Basic Info/Close Viewer Button");
			}
		}
}
