package com.medfusion.product.object.maps.patientportal2.page.MyAccountPage;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.common.utils.IHGUtil;

public class JalapenoMyAccountActivityPage extends JalapenoMyAccountPage {

	
	@FindBy(how = How.XPATH, using = "//*[@id='frame']/p")
	private WebElement tableUserLabel;
	
	public JalapenoMyAccountActivityPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}
	
	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		
		new WebDriverWait(driver, 80).until(ExpectedConditions.visibilityOf(tableUserLabel));
		webElementsList.add(tableUserLabel);
		
		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}

	public JalapenoMyAccountDevicesPage goToDevicesTab(WebDriver driver) {
		log("Click on My Devices");
		devicesTab.click();
		return PageFactory.initElements(driver, JalapenoMyAccountDevicesPage.class);
	}
	

}
