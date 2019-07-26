package objectmaps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class HomePage extends BasePageObject {
	
	protected WebDriver driver;
	
	public HomePage(WebDriver driver, String url) {
		super(driver);
		IHGUtil.PrintMethodName();
		String sanitizedUrl = url.trim();
		driver.get(sanitizedUrl);
		driver.manage().window().maximize();
		IHGUtil.printCookies(driver);
		PageFactory.initElements(driver, this);
	}
	
	public HomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	// header
	@FindBy(how = How.ID, using= "newUserBtn")
	public WebElement newUserBtn;
	
	@FindBy(how = How.ID, using= "existingUserBtn")
	public WebElement existingUserBtn;
	
	@FindBy(how = How.ID, using= "mfConnectBtn")
	public WebElement mfConnectBtn;

}
