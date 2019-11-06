package objectmaps;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebDriver;

public class ConnectionDoctorPage extends HomePage {

	public ConnectionDoctorPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(how = How.ID, using= "mfConnectBack")
	public WebElement mfConnectback;
	
	@FindBy(how = How.ID, using= "mfCloseButton")
	public WebElement mfCloseButton;
	
	@FindBy(how = How.ID, using = "searchBy")
	public WebElement searchBy;
	
	@FindBy(how = How.ID, using = "searchZip")
	public WebElement searchZip;
	
	@FindBy(how = How.ID, using = "directorySearchBtn")
	public WebElement directorySearchBtn;
	
}
