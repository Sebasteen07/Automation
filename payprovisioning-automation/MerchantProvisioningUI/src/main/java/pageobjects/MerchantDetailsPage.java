package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class MerchantDetailsPage extends NavigationMenu{

	public MerchantDetailsPage(WebDriver driver) {
		super(driver);
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(how=How.XPATH,using="/html/body/div[3]/div/div[3]/div[5]/div/fieldset/legend/div[2]/div/div/")
	private WebElement collectChargebackButton;

	@FindBy(how=How.XPATH,using="//*[@id='top-nav']/div/div[2]/div[1]/span[1]")
	private WebElement mmid;


	public String getMMID(){
		return mmid.getText();
	}



}
