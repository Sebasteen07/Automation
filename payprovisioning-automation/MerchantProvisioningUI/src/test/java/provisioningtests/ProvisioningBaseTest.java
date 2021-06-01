package provisioningtests;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageobjects.LoginPage;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;


public class ProvisioningBaseTest extends BaseTestNGWebDriver{
	
	protected PropertyFileLoader testData;
	
	
	@BeforeMethod
	public void logIntoGWR() throws IOException, NullPointerException, InterruptedException {
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		log("Getting Test Data");
		testData = new PropertyFileLoader();
		
		log("step 1: Navigating to Merchant provisioning");
		LoginPage loginPage = new LoginPage(driver, testData.getProperty("provisioningurl"));
		assertTrue(loginPage.assessLoginPageElements());
		
		log("step 2: Log in");	
		//TODO;
	}
}
