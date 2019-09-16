package objectmaps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class OfficeSelectPage extends HomePage {

	public OfficeSelectPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(how = How.LINK_TEXT, using = "Back to search results")
	public WebElement mfConnectBack;
	
	@FindBy(how = How.ID, using = "mfCloseButton")
	public WebElement mfCloseBtn;
	
	@FindBy(how = How.XPATH, using = "/html[1]/body[1]/div[2]/div[2]/div[1]/div[1]/div[3]/div[1]/div[1]/ul[1]/li[1]")
	public WebElement firstElement;
}
