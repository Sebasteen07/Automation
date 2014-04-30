package com.intuit.ihg.product.object.maps.community.page.RxRenewal;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;


public class RxRenewalChooseDoctor extends BasePageObject{
	
	@FindBy(how = How.CSS, using = "a.show_search > strong")
	public WebElement searchForDifferentDoctor;

	@FindBy(how = How.ID, using = "drsearch")
	public WebElement searchTextBox;

	@FindBy(how = How.ID, using = "searchbtn")
	public WebElement searchButton;

	@FindBy(how = How.CSS, using = "button[type=submit]")
	public WebElement btnContinue;
	
	public RxRenewalChooseDoctor(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	
}
