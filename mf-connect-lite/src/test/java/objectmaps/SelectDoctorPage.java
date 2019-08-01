package objectmaps;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebDriver;


public class SelectDoctorPage extends HomePage {
	
	public SelectDoctorPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(how = How.ID, using = "mfConnectBack")
	public WebElement mfConnectBack;
	
	@FindBy(how = How.ID, using = "mfCloseButton")
	public WebElement mfCloseBtn;
	
}
