package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class NavigationMenu {

	protected WebDriver driver;

	public NavigationMenu(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(how = How.XPATH, using = "/html/body/div[2]/nav/ul/li[1]/a")
	public WebElement menuBarButton;

	@FindBy(how = How.XPATH, using = "//body/div[2]/nav[1]/ul[1]/li[5]/a[1]")
	public WebElement ACHbutton;

	@FindBy(how = How.XPATH, using = "//body/div[2]/nav[1]/ul[1]/li[6]/a[1]")
	public WebElement Ledgerbutton;

	@FindBy(how = How.XPATH, using = "//body/div[2]/nav[1]/ul[1]/li[7]/a[1]")
	public WebElement SettlementButton;

	@FindBy(how = How.XPATH, using = "//body/div[2]/nav[1]/ul[1]/li[8]/a[1]")
	public WebElement Feesbutton;

	// Fill in the rest

}
