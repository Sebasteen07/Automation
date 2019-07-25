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

	public void clickMfPracticeBtn() {
		mfPracticeBtn.click();
	}
	
	public void clickMfDoctorBtn() {
		mfDoctorBtn.click();
	}
	
	public void clickMfPortalBtn() {
		mfPortalBtn.click();
	}
	
}
