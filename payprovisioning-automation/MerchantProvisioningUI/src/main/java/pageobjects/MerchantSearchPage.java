package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class MerchantSearchPage extends NavigationMenu {
	
	public MerchantSearchPage(WebDriver driver) {
		super(driver);
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}
	
	
	
	@FindBy(how=How.XPATH,using="/html/body/div[3]/div/form/div[2]/button/span")
	private WebElement merchantSearchButton;

}
