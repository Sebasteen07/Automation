package objectmaps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class ConnectionLocationPage extends HomePage{
	
	public ConnectionLocationPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(how = How.ID, using = "searchBy")
	public WebElement searchBy;
	
	@FindBy(how = How.ID, using = "searchZip")
	public WebElement searchZip;
	
	@FindBy(how = How.ID, using = "directorySearchBtn")
	public WebElement directorySearchBtn;
	
	@FindBy(how = How.ID, using = "mfConnectBack")
	public WebElement mfConnectBack;

	public void clickMfConnectionBack() {
		mfConnectBack.click();
	}
	
	public void clickSearch() {
		directorySearchBtn.click();
	}
	
}
