package com.intuit.ihg.product.object.maps.community.page.RxRenewal;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;

public class RxRenewalSelectPharmacy extends BasePageObject{

	@FindBy(how = How.CSS, using = "a.show_search > strong")
	private WebElement searchForDifferentDoctor;

	@FindBy(how = How.ID, using = "drsearch")
	private WebElement searchTextBox;

	@FindBy(how = How.ID, using = "searchbtn")
	private WebElement searchButton;

	@FindBy(how = How.CSS, using = "button[type=submit]")
	private WebElement btnContinue;
	
	@FindBy(how = How.XPATH, using = "(//input[@name='existing_pharm' and @value='other_pharm' ])")
	private WebElement chooseNewPharmacy;
	
	@FindBy(how = How.ID, using = "addr_search")
	private WebElement addressTextBox;
	
	@FindBy(how = How.ID, using = "pharmacy_search_btn")
	private WebElement PharmacySearchButton;
	
	
	public RxRenewalSelectPharmacy(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void selectPharmacy(String sZipOrStateToSearch)
			throws InterruptedException {

		//Choose New  Pharmacy
		
		chooseNewPharmacy.click();
		addressTextBox.clear();
		Thread.sleep(1000);
		addressTextBox.sendKeys(sZipOrStateToSearch);
		PharmacySearchButton.click();
		
}
}
