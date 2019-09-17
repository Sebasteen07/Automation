package objectmaps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class ModalPage extends HomePage {
	
	public ModalPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	// buttons
	@FindBy(how = How.ID, using= "mfCloseButton")
	public WebElement mfCloseButton;
	
	@FindBy(how = How.XPATH, using= "/html[1]/body[1]/div[2]/div[2]/div[1]/div[1]/div[3]/div[1]/div[1]/div[1]/h2[1]/button[1]")
	public WebElement existingAddConnectionButton;
	
	@FindBy(how = How.XPATH, using= "/html[1]/body[1]/div[2]/div[2]/div[1]/div[1]/div[3]/div[1]/div[1]/div[1]/button[1]")
	public WebElement newAddConnectionButton;
	
	@FindBy(how = How.XPATH, using= "/html[1]/body[1]/div[2]/div[2]/div[1]/div[1]/div[3]/div[1]/div[1]/ul[1]/a[1]")
	public WebElement doneMakingConnectionsButton;
	
}
