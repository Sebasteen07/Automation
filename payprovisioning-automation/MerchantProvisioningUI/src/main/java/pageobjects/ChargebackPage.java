package pageobjects;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class ChargebackPage extends MerchantDetailsPage {

	public ChargebackPage(WebDriver driver) throws IOException {
		super(driver);
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(how=How.XPATH,using="/html/body/div[3]/div/fieldset/div[1]/div[2]/input")
	private WebElement originalTransactionIdField;

}
