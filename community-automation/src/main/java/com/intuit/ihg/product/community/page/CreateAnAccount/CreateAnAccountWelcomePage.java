package com.intuit.ihg.product.community.page.CreateAnAccount;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.community.page.CommunityHomePage;

public class CreateAnAccountWelcomePage extends BasePageObject {

	@FindBy(xpath = "//div[@class='first_use']")
	WebElement firstUseContainer;
	
	@FindBy(xpath="//*[contains(text(),'Congratulations')]")
	WebElement welcomeMessage;

	public CreateAnAccountWelcomePage(WebDriver driver) {
		super(driver);
	}

	public boolean findCongratulationsMessage(String sFirstName) throws InterruptedException {

		driver.switchTo().defaultContent();
		// Waiting up to 30 seconds for message to load
		WebElement congratulationMessage = (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By
						.xpath("// * [contains(text(),'Congratulations " + sFirstName + "')]")));
		log("Looking for Welcome message");
		if (congratulationMessage.isDisplayed() == true) {
			return congratulationMessage.isDisplayed();
		} else {
			for (int i = 1; i < 10; i++) {
				log("Looking for the welcome message for the: " + i);
				congratulationMessage = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By
								.xpath("// * [contains(text(),'Congratulations " + sFirstName + "')]")));
				if (congratulationMessage.isDisplayed() == true) {
					return congratulationMessage.isDisplayed();
				}
			}
		}

		return false;
	}
	
	public boolean findWelcomeMessage(WebDriver driver) throws InterruptedException {
		IHGUtil.waitForElement(driver, 60, welcomeMessage);
		return welcomeMessage.isDisplayed();
	}

	public boolean findFirstUserContainer(WebDriver driver) throws InterruptedException {

		IHGUtil.waitForElement(driver, 120, firstUseContainer);
		Thread.sleep(10000);

		return firstUseContainer.isDisplayed();
	}

	public boolean findFeaturesContainer() throws InterruptedException {
		IHGUtil.PrintMethodName();
		driver.switchTo().defaultContent();
		return driver.findElement(By.className("first_use_container")).isDisplayed();
	}

	public boolean findIconMessages() throws InterruptedException {
		driver.switchTo().defaultContent();
		return driver.findElement(By.xpath("//h5[contains(text(),'Messages')]")).isDisplayed();
	}

	public boolean findIconAppointments() throws InterruptedException {
		driver.switchTo().defaultContent();
		return driver.findElement(By.xpath("//h5[contains(text(),'Appointments')]")).isDisplayed();
	}

	public boolean findIconBillPayment() throws InterruptedException {
		driver.switchTo().defaultContent();
		return driver.findElement(By.xpath("//h5[contains(text(),'Bill Payment')]")).isDisplayed();
	}

	public boolean findIconForms() throws InterruptedException {
		driver.switchTo().defaultContent();
		return driver.findElement(By.xpath("//h5[contains(text(),'Forms')]")).isDisplayed();
	}

	public boolean findIconPrescriptions() throws InterruptedException {
		driver.switchTo().defaultContent();
		return driver.findElement(By.xpath("//h5[contains(text(),'Prescriptions')]")).isDisplayed();
	}

	public boolean findIconAskAQuestion() throws InterruptedException {
		driver.switchTo().defaultContent();
		return driver.findElement(By.xpath("//h5[contains(text(),'Ask A Question')]")).isDisplayed();
	}

	public void clickMoreFeatures() throws InterruptedException {

		driver.findElement(By.xpath("// * [contains(text(),'See More Features')]")).click();
		Thread.sleep(5000);

	}

	public CommunityHomePage clickStartusingPortal() throws InterruptedException {
		IHGUtil.PrintMethodName();
		driver.findElement(By.xpath("// * [contains(text(),'Start Using The Portal!')]")).click();
		return PageFactory.initElements(driver, CommunityHomePage.class);
	}
}
