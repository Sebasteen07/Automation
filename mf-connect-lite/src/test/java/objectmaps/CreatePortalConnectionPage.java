package objectmaps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class CreatePortalConnectionPage extends HomePage {

	public CreatePortalConnectionPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(how = How.ID, using= "mfConnectBack")
	public WebElement mfConnectback;
	
	@FindBy(how = How.ID, using= "mfCloseButton")
	public WebElement mfCloseButton;
	
	@FindBy(how = How.ID, using= "connectionCredentials_username")
	public WebElement ccUsername;
	
	@FindBy(how = How.ID, using= "connectionCredentials_password")
	public WebElement ccPassword;
	
	@FindBy(how = How.ID, using= "createConnectionBtn")
	public WebElement createConnectionBtn;
	
	@FindBy(how = How.ID, using = "resetPassword")
	public WebElement resetPassword;
	
}
