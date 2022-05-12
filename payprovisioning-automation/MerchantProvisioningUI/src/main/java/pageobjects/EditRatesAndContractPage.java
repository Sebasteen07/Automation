// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package pageobjects;

import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import com.medfusion.common.utils.PropertyFileLoader;

public class EditRatesAndContractPage extends NavigationMenu {
	protected static PropertyFileLoader testData;

	public EditRatesAndContractPage(WebDriver driver) throws IOException {
		super(driver);
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
		testData = new PropertyFileLoader();
	}

	@FindBy(how = How.NAME, using = "feeSettlementType")
	private WebElement feeSettelementTypeDropDownByName;

	@FindBy(how = How.XPATH, using = "//button[@data-ng-if='editMode']")
	private WebElement updateRateContractButton;

	public Select getSelectOptions(WebElement feeSettelementTypeDropDown) {
		return new Select(feeSettelementTypeDropDown);
	}

	public void editSettlementType(String settlementType) throws InterruptedException {

		Select objSelect = new Select(driver.findElement(By.id("feeSettlementType")));
		objSelect.selectByVisibleText("DAILY");

		updateRateContractButton.click();
	}

}
