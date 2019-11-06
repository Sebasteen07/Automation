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
	
	@FindBy(how = How.XPATH, using = "/html[1]/body[1]/div[2]/div[2]/div[1]/div[1]/div[3]/div[1]/div[1]/ul[1]/li[1]/p[1]")
	public WebElement firstElement;
	
}
