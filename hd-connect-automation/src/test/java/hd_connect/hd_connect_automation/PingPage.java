package hd_connect.hd_connect_automation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import junit.framework.Assert;

public class PingPage extends VerifyPage {
	
	public PingPage(WebDriver driver) {
		super(driver);
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}

	@FindBy(how = How.ID, using = "loginUsername")
	private WebElement login_username;
	
	@FindBy(how = How.ID, using = "loginPassword")
	private WebElement login_password;
	
	@FindBy(how = How.LINK_TEXT, using = "Sign in with Medfusion")
	private WebElement sign_in;
	
	@FindBy(how = How.LINK_TEXT, using = "Forgot username")
	private WebElement forgot_username;
	
	@FindBy(how = How.LINK_TEXT, using = "Forgot password")
	private WebElement forgot_password;
	
	@FindBy(how = How.CLASS_NAME, using = "main-content__description")
	private WebElement errtext;
	
	@FindBy(how = How.CLASS_NAME, using = "logout-button")
	private WebElement logout_btn;
	
	
	
	public void login(String username, String password) {
		login_username.sendKeys(username);
		login_password.sendKeys(password);
		sign_in.click();
	}
	
	public String geterrorMessage() {
		return errtext.getText();
	}
	
	public void verifyPageElements() {
		
		Assert.assertEquals(true, login_username.isDisplayed());
		Assert.assertEquals(true, login_password.isDisplayed());
		Assert.assertEquals(true, sign_in.isDisplayed());
		Assert.assertEquals(true, forgot_username.isDisplayed());
		Assert.assertEquals(true, forgot_password.isDisplayed());
	}
	
	public boolean islogoutBtnDisplayed() {
		return logout_btn.isDisplayed();
	}
}
