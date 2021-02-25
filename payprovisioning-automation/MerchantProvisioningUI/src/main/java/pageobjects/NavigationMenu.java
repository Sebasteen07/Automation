package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class NavigationMenu {
	
	protected WebDriver driver;
	
	public NavigationMenu(WebDriver driver){
		this.driver = driver;
	}
	
	@FindBy(how = How.XPATH, using = "/html/body/div[2]/nav/ul/li[1]/a")
	public WebElement menuBarButton;
	
	//Fill in the rest

}
