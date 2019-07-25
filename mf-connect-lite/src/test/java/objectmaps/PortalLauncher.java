package objectmaps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class PortalLauncher extends HomePage {
	
	public PortalLauncher(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(how = How.ID, using = "mfConnectBack")
	public WebElement mfConnectBack;
	
	@FindBy(how = How.ID, using = "mgCloseButton")
	public WebElement mfCloseBtn;
	
	@FindBy(how = How.ID, using = "cancelBtn")
	public WebElement cancelBtn;
	
	@FindBy(how = How.ID, using = "launchBtn")
	public WebElement launchBtn;

}
