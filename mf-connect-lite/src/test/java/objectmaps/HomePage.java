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

	// preselect portal area
	@FindBy(how = How.ID, using= "portal-preselect-check")
	public WebElement portalPreselectCheck;
	
	@FindBy(how = How.XPATH, using= "/html[1]/body[1]/div[1]/div[3]/form[1]/table[1]/tbody[1]/tr[2]/td[1]/input[1]")
	public WebElement portalId;
	
	@FindBy(how = How.XPATH, using= "/html[1]/body[1]/div[1]/div[3]/form[1]/table[1]/tbody[1]/tr[2]/td[2]/input[1]")
	public WebElement preselectDirLocCheck;
	
	@FindBy(how = How.XPATH, using= "/html[1]/body[1]/div[1]/div[3]/form[1]/table[1]/tbody[1]/tr[2]/td[3]/input[1]")
	public WebElement preDirLocId;
	
	@FindBy(how = How.XPATH, using= "/html[1]/body[1]/div[1]/div[3]/form[1]/table[1]/tbody[1]/tr[2]/td[4]/input[1]")
	public WebElement preDirLocType;
	
	// recommended portals area
	@FindBy(how = How.ID, using= "recommended-portals-check")
	public WebElement recPortalCheck;
	
	@FindBy(how = How.XPATH, using= "/html[1]/body[1]/div[1]/div[4]/form[1]/table[1]/tbody[1]/tr[2]/td[2]/input[1]")
	public WebElement recEnableCheck1;
	
	@FindBy(how = How.XPATH, using= "/html[1]/body[1]/div[1]/div[4]/form[1]/table[1]/tbody[1]/tr[2]/td[3]/input[1]")
	public WebElement recPortalId1;
	
	@FindBy(how = How.XPATH, using= "/html[1]/body[1]/div[1]/div[4]/form[1]/table[1]/tbody[1]/tr[2]/td[4]/input[1]")
	public WebElement recDirLoc1;
	
	@FindBy(how = How.XPATH, using= "/html[1]/body[1]/div[1]/div[4]/form[1]/table[1]/tbody[1]/tr[2]/td[5]/input[1]")
	public WebElement recDirLocId1;
	
	@FindBy(how = How.XPATH, using= "/html[1]/body[1]/div[1]/div[4]/form[1]/table[1]/tbody[1]/tr[2]/td[6]/input[1]")
	public WebElement recDirLocType1;
	
	@FindBy(how = How.XPATH, using= "/html[1]/body[1]/div[1]/div[4]/form[1]/table[1]/tbody[1]/tr[3]/td[2]/input[1]")
	public WebElement recEnableCheck2;
	
	@FindBy(how = How.XPATH, using= "/html[1]/body[1]/div[1]/div[4]/form[1]/table[1]/tbody[1]/tr[3]/td[3]/input[1]")
	public WebElement recPortalId2;
	
	@FindBy(how = How.XPATH, using= "/html[1]/body[1]/div[1]/div[4]/form[1]/table[1]/tbody[1]/tr[3]/td[4]/input[1]")
	public WebElement recDirLoc2;
	
	@FindBy(how = How.XPATH, using= "/html[1]/body[1]/div[1]/div[4]/form[1]/table[1]/tbody[1]/tr[3]/td[5]/input[1]")
	public WebElement recDirLocId2;
	
	@FindBy(how = How.XPATH, using= "/html[1]/body[1]/div[1]/div[4]/form[1]/table[1]/tbody[1]/tr[3]/td[6]/input[1]")
	public WebElement recDirLocType2;
	
	@FindBy(how = How.XPATH, using= "/html[1]/body[1]/div[1]/div[4]/form[1]/table[1]/tbody[1]/tr[4]/td[2]/input[1]")
	public WebElement recEnableCheck3;
	
	@FindBy(how = How.XPATH, using= "/html[1]/body[1]/div[1]/div[4]/form[1]/table[1]/tbody[1]/tr[4]/td[3]/input[1]")
	public WebElement recPortalId3;
	
	@FindBy(how = How.XPATH, using= "/html[1]/body[1]/div[1]/div[4]/form[1]/table[1]/tbody[1]/tr[4]/td[4]/input[1]")
	public WebElement recDirLoc3;
	
	@FindBy(how = How.XPATH, using= "/html[1]/body[1]/div[1]/div[4]/form[1]/table[1]/tbody[1]/tr[4]/td[5]/input[1]")
	public WebElement recDirLocId3;
	
	@FindBy(how = How.XPATH, using= "/html[1]/body[1]/div[1]/div[4]/form[1]/table[1]/tbody[1]/tr[4]/td[6]/input[1]")
	public WebElement recDirLocType3;
	
	@FindBy(how = How.XPATH, using= "/html[1]/body[1]/div[1]/div[4]/form[1]/table[1]/tbody[1]/tr[5]/td[2]/input[1]")
	public WebElement recEnableCheck4;
	
	@FindBy(how = How.XPATH, using= "/html[1]/body[1]/div[1]/div[4]/form[1]/table[1]/tbody[1]/tr[5]/td[3]/input[1]")
	public WebElement recPortalId4;
	
	@FindBy(how = How.XPATH, using= "/html[1]/body[1]/div[1]/div[4]/form[1]/table[1]/tbody[1]/tr[5]/td[4]/input[1]")
	public WebElement recDirLoc4;
	
	@FindBy(how = How.XPATH, using= "/html[1]/body[1]/div[1]/div[4]/form[1]/table[1]/tbody[1]/tr[5]/td[5]/input[1]")
	public WebElement recDirLocId4;
	
	@FindBy(how = How.XPATH, using= "/html[1]/body[1]/div[1]/div[4]/form[1]/table[1]/tbody[1]/tr[5]/td[6]/input[1]")
	public WebElement recDirLocType4;
	
	@FindBy(how = How.XPATH, using= "/html[1]/body[1]/div[1]/div[4]/form[1]/table[1]/tbody[1]/tr[6]/td[2]/input[1]")
	public WebElement recEnableCheck5;
	
	@FindBy(how = How.XPATH, using= "/html[1]/body[1]/div[1]/div[4]/form[1]/table[1]/tbody[1]/tr[6]/td[3]/input[1]")
	public WebElement recPortalId5;
	
	@FindBy(how = How.XPATH, using= "/html[1]/body[1]/div[1]/div[4]/form[1]/table[1]/tbody[1]/tr[6]/td[4]/input[1]")
	public WebElement recDirLoc5;
	
	@FindBy(how = How.XPATH, using= "/html[1]/body[1]/div[1]/div[4]/form[1]/table[1]/tbody[1]/tr[6]/td[5]/input[1]")
	public WebElement recDirLocId5;
	
	@FindBy(how = How.XPATH, using= "/html[1]/body[1]/div[1]/div[4]/form[1]/table[1]/tbody[1]/tr[6]/td[6]/input[1]")
	public WebElement recDirLocType5;
}
