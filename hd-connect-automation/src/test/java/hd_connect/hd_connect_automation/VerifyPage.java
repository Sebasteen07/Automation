package hd_connect.hd_connect_automation;

//import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class VerifyPage extends BasePageObject {

	protected WebDriver driver;
	
	public VerifyPage(WebDriver driver){
		super(driver);
		PageFactory.initElements(driver, this); 
	}
	
	public VerifyPage(WebDriver driver, String url) {
		super(driver);
		String sanitizedUrl = url.trim();
		driver.get(sanitizedUrl);
		driver.manage().window().maximize();
		IHGUtil.printCookies(driver);
		PageFactory.initElements(driver,  this);
	}
	
	@FindBy(how = How.ID, using = "zipCode")
	private WebElement zipCode;
	
	@FindBy(how = How.ID, using = "dob-Month")
	private WebElement dobMonth;
	
	@FindBy(how = How.ID, using = "dob-Day")
	private WebElement dobDay;
	
	@FindBy(how = How.ID, using = "dob-Year")
	private WebElement dobYear;
	
	@FindBy(how = How.CLASS_NAME, using = "submit-button-wrapper")
	private WebElement confirmButton;
	
	@FindBy(how = How.CLASS_NAME, using = "alert")
	private WebElement errMessage;
	
	@FindBy(how = How.ID, using = "loginUsername")
	private WebElement login_username;
	
	public void selectMonth(String month) {
		Select dMonth = new Select(dobMonth);
		dMonth.selectByValue(month);
	}
	
	public void selectDay(String day) {
		Select dDay = new Select(dobDay);
		dDay.selectByValue(day);
	}
	
	public void fillDetails (String zcode, String month, String day, String year) {
		
		zipCode.sendKeys(zcode);
		selectMonth(month);
		selectDay(day);
		dobYear.sendKeys(year);
		confirmButton.click();
	}
	
	public String getVerifyPageStatus () {
		return errMessage.getText();
		
	}
	
	public void verifyPageElements () {
		
		Assert.assertEquals(zipCode.isDisplayed(), true);
		Assert.assertEquals(dobMonth.isDisplayed(), true);
		Assert.assertEquals(dobDay.isDisplayed(), true);
		Assert.assertEquals(dobYear.isDisplayed(), true);
		Assert.assertEquals(confirmButton.isDisplayed(), true);

	}
	public boolean isloginUsernameDisplayed() {
		return login_username.isDisplayed();
	}

}
