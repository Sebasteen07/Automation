package com.medfusion.product.object.maps.patientportal2.page.MyAccountPage;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;

public class JalapenoMyAccountDevicesPage extends JalapenoMyAccountPage{

	//*[@id="devicemanagement"]/div[2]/div/div/p
	@FindBy(how = How.XPATH, using = "//*[@id='devicemanagement']/div[2]/div/div/p")
	private WebElement devicesTable;
	
	public JalapenoMyAccountDevicesPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}
	
	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		
		webElementsList.add(devicesTable);
		
		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}

}
