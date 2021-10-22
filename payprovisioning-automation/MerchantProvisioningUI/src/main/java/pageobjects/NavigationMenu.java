package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class NavigationMenu {

	protected WebDriver driver;

	public NavigationMenu(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(how = How.XPATH, using = "//*[@id='top-nav-logo']/div/span")
	public WebElement showMenu;

	@FindBy(how = How.XPATH, using = "/html/body/div[2]/nav/ul/li[1]/a")
	public WebElement dashboardButton;

	@FindBy(how = How.XPATH, using = "//body/div[2]/nav[1]/ul[1]/li[5]/a[1]")
	public WebElement achButton;

	@FindBy(how = How.XPATH, using = "//body/div[2]/nav[1]/ul[1]/li[6]/a[1]")
	public WebElement ledgerButton;

	@FindBy(how = How.XPATH, using = "//body/div[2]/nav[1]/ul[1]/li[7]/a[1]")
	public WebElement settlementButton;

	@FindBy(how = How.XPATH, using = "//body/div[2]/nav[1]/ul[1]/li[8]/a[1]")
	public WebElement feesButton;

	@FindBy(how = How.XPATH, using = "//a[@href='#/merchants/add']")
	public WebElement addMerchant;

	public boolean isMenuOpen(){
		return dashboardButton.isDisplayed();
	}
	public void openMenuIfNotOpened() throws InterruptedException {
		if (!isMenuOpen()) {
			showMenu.click();
			Thread.sleep(5000);
		}
	}
	public AddNewMerchantPage openAddMerchantPage() throws InterruptedException {
		openMenuIfNotOpened();
		addMerchant.click();
		return PageFactory.initElements(driver, AddNewMerchantPage.class);
	}

}
