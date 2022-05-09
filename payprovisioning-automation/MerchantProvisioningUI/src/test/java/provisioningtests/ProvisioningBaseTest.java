package provisioningtests;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import pageobjects.LoginPage;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;

public class ProvisioningBaseTest extends BaseTestNGWebDriver {

	protected static PropertyFileLoader testData;


	
	@BeforeMethod
	public void logIntoMP() throws IOException, NullPointerException, InterruptedException {
		
		
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		logStep("Getting Test Data");
		testData = new PropertyFileLoader();

		logStep("Navigating to Merchant provisioning");
		LoginPage loginPage = new LoginPage(driver, testData.getProperty("provisioning.url"));

		logStep("Login into merchant provisioning");
		loginPage.login(testData.getProperty("user.name"), testData.getProperty("password"));
		loginPage.loginButton.click();

	}
}
