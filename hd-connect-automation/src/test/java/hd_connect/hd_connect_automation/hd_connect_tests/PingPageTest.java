package hd_connect.hd_connect_automation.hd_connect_tests;

import org.testng.annotations.Test;
import com.medfusion.common.utils.PropertyFileLoader;
import hd_connect.hd_connect_automation.PingPage;
import junit.framework.Assert;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;

public class PingPageTest extends VerifyPageTest {
	
	protected PropertyFileLoader testData;

	@BeforeMethod(enabled = true)
	public void setup() throws Exception {
		testData = new PropertyFileLoader();
		PingPage ping = PageFactory.initElements(driver, PingPage.class);
		CorrectLoginTest();
		ping.verifyPageElements();
	}
	
	@Test(enabled = true)
	public void CorrectLogin() throws Exception {
		PingPage ping = PageFactory.initElements(driver, PingPage.class);
		String username = testData.getProperty("correctusername");
		String password = testData.getProperty("password");
		ping.login(username, password);
		Assert.assertEquals(true,ping.islogoutBtnDisplayed());
	}
	
	@Test(enabled = true)
	public void IncorrectLogin() throws Exception {
		PingPage ping = PageFactory.initElements(driver, PingPage.class);
		String username = testData.getProperty("incorrectusername");
		String password = testData.getProperty("password");
		String err = testData.getProperty("errtext");
		ping.login(username, password);
		Assert.assertEquals(err, ping.geterrorMessage());
	}

}
