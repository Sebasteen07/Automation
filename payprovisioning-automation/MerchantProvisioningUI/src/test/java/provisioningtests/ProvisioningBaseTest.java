//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved
package provisioningtests;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import org.testng.annotations.BeforeMethod;
import pageobjects.LoginPage;

import java.io.IOException;

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
