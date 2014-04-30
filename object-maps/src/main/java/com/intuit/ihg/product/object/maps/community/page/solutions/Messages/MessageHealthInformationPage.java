package com.intuit.ihg.product.object.maps.community.page.solutions.Messages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class MessageHealthInformationPage extends BasePageObject{
	
	@FindBy(how = How.ID, using = "shiViewer")
	public WebElement headerCCDViewer;
	
	@FindBy(how = How.CLASS_NAME, using = "footer_downloads")
	public WebElement footerCCDViewer;
	
	@FindBy(how = How.ID, using = "closeCcd")
	public WebElement btnCloseViewer;
	
	public MessageHealthInformationPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);

	}	
	
	//Checking whether elements are displayed on the page
	public boolean areElementsLocated() {
		IHGUtil.PrintMethodName();
		
		boolean result = false;
		
		try {
			result = headerCCDViewer.isDisplayed();
			if (result == true) {
				result = footerCCDViewer.isDisplayed();
				if (result == true) {
					result = btnCloseViewer.isDisplayed();
				}
			} 
		} catch (Exception e) {
			// Catch any element not found errors
		}
		
		return result;
	}

}
