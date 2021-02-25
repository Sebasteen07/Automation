package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class LoginPage extends BasePageObject {
	
	@FindBy(how = How.ID, using = "loginName")
	public WebElement userName;
	
	//Fill in the rest

	public LoginPage(WebDriver driver, String url) {
		super(driver);
		IHGUtil.PrintMethodName();
		log("Loading login page");
		String sanitizedUrl = url.trim();
		log("URL: " + sanitizedUrl);
		driver.get(sanitizedUrl);
		driver.manage().window().maximize();
		IHGUtil.printCookies(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(how = How.ID, using = "username")
	public WebElement inputUserId;

	public boolean assessLoginPageElements() {
		// TODO Auto-generated method stub
		return false;
	}

	public void login(String property, String property2) {
		// TODO Auto-generated method stub
		
	}
}
