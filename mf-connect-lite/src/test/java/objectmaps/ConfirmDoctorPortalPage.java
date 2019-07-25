package objectmaps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class ConfirmDoctorPortalPage extends HomePage {
	
	public ConfirmDoctorPortalPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(how = How.ID, using = "mfConnectBack")
	public WebElement mfConnectBack;
	
	@FindBy(how = How.ID, using = "mfCloseButton")
	public WebElement mfCloseBtn;
	
}
