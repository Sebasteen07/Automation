package hd_connect.hd_connect_automation.hd_connect_tests;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.medfusion.common.utils.PropertyFileLoader;
import hd_connect.hd_connect_automation.VerifyPage;

@Listeners(com.medfusion.listenerpackage.Listener.class)


public class VerifyPageTest extends BaseTestNGWebDriver {
	
	protected PropertyFileLoader testData;
	
	@BeforeMethod(enabled=true)
	public void setUp() throws Exception{
		log("Opening the browser");
		testData = new PropertyFileLoader();
		String url = testData.getProperty("url");
		VerifyPage verify = new VerifyPage(driver, url);
		verify.verifyPageElements();
	}
	
	@Test(enabled = true, groups = {"VerifyPageTest"})
	public void IncorrectLoginTest() throws Exception {
		VerifyPage verify = PageFactory.initElements(driver, VerifyPage.class);
		String zip = testData.getProperty("zip");
		String month = testData.getProperty("month");
		String day = testData.getProperty("day");
		String year = testData.getProperty("year1");
		String err = testData.getProperty("errormsg");
		verify.fillDetails(zip, month, day, year);
		Assert.assertEquals(err, verify.getVerifyPageStatus());
		
	    
	}
	
	@Test(enabled = true, groups = {"VerifyPageTest"})
	public void CorrectLoginTest() {
		VerifyPage verify = PageFactory.initElements(driver, VerifyPage.class);
		String zip = testData.getProperty("zip");
		String month = testData.getProperty("month");
		String day = testData.getProperty("day");
		String year = testData.getProperty("year2");
		verify.fillDetails(zip, month, day, year);
		Assert.assertTrue(verify.isloginUsernameDisplayed());

	  
  }
}
