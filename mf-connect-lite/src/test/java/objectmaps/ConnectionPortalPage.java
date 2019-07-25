package objectmaps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class ConnectionPortalPage extends HomePage{
	
	public ConnectionPortalPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(how = How.XPATH, using= "/html[1]/body[1]/div[2]/div[2]/div[1]/div[1]/div[3]/div[1]/div[1]/form[1]/input[1]") 
	public WebElement primaryURL;
	
	@FindBy(how = How.ID, using= "directorySearchBtn")
	public WebElement directorySearchBtn;
	
}
