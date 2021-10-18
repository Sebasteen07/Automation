package pageobjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.common.utils.PropertyFileLoader;

public class EditRatesAndContractPage extends NavigationMenu {
	protected static PropertyFileLoader testData;

	public EditRatesAndContractPage(WebDriver driver) throws IOException {
		super(driver);
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
		testData = new PropertyFileLoader();
	}

	@FindBy(how = How.ID, using = "feeSettlementType")
	private WebElement feeSettelementTypeDropDown;

	@FindBy(how = How.XPATH, using = "//button[@data-ng-if='editMode']")
	private WebElement updateRateContractButton;

	public Select getSelectOptions(WebElement feeSettelementTypeDropDown) {
		return new Select(feeSettelementTypeDropDown);
	}

	public void editSettlementType(String settlementType) throws InterruptedException {

		Select sec = new Select(feeSettelementTypeDropDown);
		sec.selectByVisibleText(settlementType);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		updateRateContractButton.click();
		
	

	}

}
