package com.medfusion.product.object.maps.practice.page.customform;


import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.PropertyFileLoader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchPartiallyFilledPage extends BasePageObject {
	@FindBy(xpath = "//input[@type='submit' and @value='Search']")
	private WebElement searchButton;

	@FindBy(xpath = "")
	private WebElement lineWithPatientName;

	public SearchPartiallyFilledPage(WebDriver driver) {
		super(driver);
	}

	public void clickSearch() {
		driver.switchTo().frame("iframe");
		searchButton.click();
	}

	public ViewPatientFormPage selectPatientsFirstForm() throws Exception {
		PropertyFileLoader testData = new PropertyFileLoader();
		String patientsName = testData.getFirstName() + ' ' + testData.getLastName();

		driver.findElement(By.xpath("//*[contains(text(), '" + patientsName + "')]")).click();
		return PageFactory.initElements(driver, ViewPatientFormPage.class);
	}
}
