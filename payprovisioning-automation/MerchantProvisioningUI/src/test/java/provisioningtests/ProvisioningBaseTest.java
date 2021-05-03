package provisioningtests;

import java.io.IOException;
import org.testng.annotations.BeforeMethod;
import pageobjects.LoginPage;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;

public class ProvisioningBaseTest extends BaseTestNGWebDriver {

	protected PropertyFileLoader testData;

	@BeforeMethod
	public void logIntoMP() throws IOException, NullPointerException, InterruptedException {
		logStep("Execution Environment: " + IHGUtil.getEnvironmentType());
		logStep("Execution Browser: " + TestConfig.getBrowserType());
		logStep("Getting Test Data");
		testData = new PropertyFileLoader();

		logStep("Navigating to Merchant provisioning");
		LoginPage loginPage = new LoginPage(driver, testData.getProperty("provisioning.url"));

		logStep("Login into merchant provisioning");
		loginPage.login(testData.getProperty("user.name"), testData.getProperty("password"));
		loginPage.loginbtn.click();

	}
}
