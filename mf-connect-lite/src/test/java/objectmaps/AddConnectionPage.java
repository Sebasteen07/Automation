package objectmaps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class AddConnectionPage extends HomePage {
	
	public AddConnectionPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	// buttons
	@FindBy(how = How.ID, using= "mfCloseButton")
	public WebElement mfCloseButton;
	
	@FindBy(how = How.ID, using= "mfConnectBack")
	public WebElement mfConnectBack;

	@FindBy(how = How.ID, using= "mfPracticeBtn")
	public WebElement mfPracticeBtn;
	
	@FindBy(how = How.ID, using= "mfDoctorBtn")
	public WebElement mfDoctorBtn;
	
	@FindBy(how = How.ID, using= "mfPortalBtn")
	public WebElement mfPortalBtn;
	
	@FindBy(how = How.XPATH, using = "/html[1]/body[1]/div[2]/div[2]/div[1]/div[1]/div[3]/div[1]/div[1]/div[2]/ul[1]/li[1]/p[1]")
	public WebElement firstRecPortal;
	
}
