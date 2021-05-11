package pageobjects;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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

	@FindBy(how = How.ID, using = "loginPassword")
	public WebElement passWord;

	@FindBy(how = How.ID, using = "loginButton")
	public WebElement loginButton;

	@FindBy(how = How.XPATH, using = "//h1[contains(text(),'Welcome')]")
	public WebElement welcomeText;

	@FindBy(how = How.XPATH, using = "//p[contains(text(),'Please enter your login credentials to access the ')]")
	public WebElement homePageText;

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
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	public boolean assessLoginPageElements() {
		// TODO Auto-generated method stub
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(userName);
		webElementsList.add(passWord);
		webElementsList.add(loginButton);
		webElementsList.add(welcomeText);
		webElementsList.add(homePageText);
		IHGUtil util = new IHGUtil(driver);
		return util.assessAllPageElements(webElementsList, LoginPage.class);
	}

	public void login(String username, String password) throws InterruptedException {
		// TODO Auto-generated method stub​​​​​​​
		userName.clear();
		userName.sendKeys(username);

		passWord.clear();
		passWord.sendKeys(password);

		loginButton.click();

	}
}
