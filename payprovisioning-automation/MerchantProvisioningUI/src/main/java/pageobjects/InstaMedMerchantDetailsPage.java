package pageobjects;

import static org.testng.Assert.assertNotNull;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.PropertyFileLoader;

public class InstaMedMerchantDetailsPage extends NavigationMenu {
	protected static PropertyFileLoader testData;
	
	public InstaMedMerchantDetailsPage(WebDriver driver) throws IOException {
		super(driver);
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
		testData = new PropertyFileLoader();
		
	}
	public void verifyPageTitle() {

		String title = this.driver.getTitle();
		assertNotNull(title);

	}
	

	
}
