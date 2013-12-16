package com.intuit.ihg.product.sitegen.page.discreteforms;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenConstants;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

	/**
	 * 
	 * @author bbinisha
	 * @ Date : 11/12/2013
	 */

public class DiscreteFormsPage extends BasePageObject{

	@FindBy ( xpath = ".//div[@id='modalDialog']//div/button[@class = 'red yes']")
	private WebElement yesDeleteButton;

	@FindBy ( xpath = ".//div[@class = 'admin_inner']/div[@class ='new_discrete_form']/a[text() = 'Custom Form']")
	private WebElement customFormButton;


	/*@FindBy ( xpath = ".//div[@class = 'admin_inner']/div[@class ='new_discrete_form']/a[@class ='button blue']")
	private WebElement registrationHealthHistoryFormButton;*/
	@FindBy(xpath = "//a[contains(text(),'Registration & Health History Form')]")
	private WebElement registrationHealthHistoryFormButton;
	
	@FindBy(linkText="General Registration and Health History") 
	private WebElement lnkGeneralRegAndHealthHistory;
	
	@FindBy(xpath=".//div[@class='admin_inner']//table[@class = 'tablesorter tablesorter-default' ]/tbody/tr/td[@class='first']/a") 
	private WebElement lnkAutomationPracticeDiscreteForm;
	
	@FindBy(name="custom_form_name") 
	private WebElement lnkCustomForm;
	
	
	@FindBy(id="save_config_form") 
	private WebElement btnSaveForms;
	


	//Constructor
	public DiscreteFormsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	/**
	 * Description : Deletes all the unpublished forms present in the Discrete Forms page.
	 * @throws Exception
	 */
	public void deleteAllUnPublishedForms() throws Exception {
		IHGUtil.PrintMethodName();
		Thread.sleep(3000);
		String xpath = ".//div[@class='admin_inner']//table[@class = 'tablesorter tablesorter-default' ]/tbody/tr/td/a[@class='delete']";	
		int count = driver.findElements(By.xpath(xpath)).size();
		log("Number of UnPublished rows is :"+count);
		for( int i = 0; i<count; i++) {

			List<WebElement> deleteButtonList = driver.findElements(By.xpath(xpath));
			for(WebElement deleteButton : deleteButtonList) {
				deleteButton.click();
				Thread.sleep(3000);
				yesDeleteButton.click();
			}	
			Thread.sleep(6000);
			if (driver.findElements(By.xpath(xpath)).size()==0) {
				break;
			}
		}	

	}

	/**
	 * Description : Deletes all the published forms present in the Discrete Forms page.
	 * @throws Exception
	 */
	public void deleteAllPublishedForms() throws Exception {
		IHGUtil.PrintMethodName();
		Thread.sleep(3000);
		String xpath = ".//div[@class='admin_inner']//table[@class = 'tablesorter tablesorter-default' ]/tbody/tr/td/a[@class='unpublish']";	
		int count = driver.findElements(By.xpath(xpath)).size();
		log("Number of Published rows is :"+count);
		List<WebElement> unpublishButtonList = driver.findElements(By.xpath(xpath));

		for (WebElement unpublishButton : unpublishButtonList) {		
			unpublishButton.click();	
			Thread.sleep(5000);
		}

	}

	/**
	 *  Description : Creates new custom form
	 * @throws Exception
	 */
	public void createANewCustomForm () throws Exception {
		IHGUtil.PrintMethodName();
		customFormButton.click();
		Thread.sleep(5000);
	}

	/**
	 * Description : Open the newly created custom form.
	 * @return
	 * @throws Exception
	 */
	public CustomFormPage openCustomForm() throws Exception {
		IHGUtil.PrintMethodName();
		Thread.sleep(5000);
		driver.findElement(By.xpath(".//div[@class='admin_inner']//table[@class = 'tablesorter tablesorter-default' ]/tbody/tr/td[@class='first']/a")).click();
		Thread.sleep(5000);
		SitegenlUtil.switchToNewWindow(driver);
		return PageFactory.initElements(driver, CustomFormPage.class);
	}
	
	
	
	/**
	 *  Description : Creates new discrete form
	 * @throws Exception
	 */
	public void createNewDiscreteForm() throws Exception {
		IHGUtil.PrintMethodName();
		Thread.sleep(5000);
		System.out.println("CLICK ON DISCRETE FORM");
		System.out.println("CHECKING CURRENT URL"+driver.getCurrentUrl());
		System.out.println("CHECKING CURRENT url source code"+driver.getPageSource());
		registrationHealthHistoryFormButton.click();
		Thread.sleep(5000);
	}

	
	
	/**
	 *  Description : Rename the created discrete form
	 * @throws Exception
	 */
	public void renameDiscreteForm(String uniqueDiscreteFormName) throws Exception {
		IHGUtil.PrintMethodName();
	
		lnkGeneralRegAndHealthHistory.click();
		System.out.println("@@@@@@@after clicking on gen reg and health history link");
		Thread.sleep(3000);
		
		lnkCustomForm.clear();
		Thread.sleep(5000);
		
		lnkCustomForm.sendKeys(uniqueDiscreteFormName);
		Thread.sleep(5000);
		btnSaveForms.click();	
	}
	
	
	/**
	 * Description : Publish the Saved Form.
	 * @param formName : Form name of the form which needs to be deleted.
	 * @throws Exception
	 */
	public void publishTheSavedForm(String uniqueDiscreteFormName) throws Exception {
		IHGUtil.PrintMethodName();
		driver.findElement(By.xpath(".//a[text()='"+uniqueDiscreteFormName+"']/ancestor::td/following-sibling::td/a[@class='publish']")).click();
		Thread.sleep(3000);
	}
	
	
	/**
	 * Description : Open the newly created discrete Form.
	 * @return
	 * @throws Exception
	 */
	public BasicInformationAboutYouPage openDiscreteForm() throws Exception {
		IHGUtil.PrintMethodName();
		Thread.sleep(5000);
		driver.findElement(By.xpath(".//div[@class='admin_inner']//table[@class = 'tablesorter tablesorter-default' ]/tbody/tr/td[@class='first']/a")).click();
		Thread.sleep(5000);
		SitegenlUtil.switchToNewWindow(driver);
		return PageFactory.initElements(driver, BasicInformationAboutYouPage.class);
	}
	


/**
	 * * @author Shanthala
	 * @Desc:- click on Link Custom Form
	 * @return ViewIntegrationsPage
	 * @throws InterruptedException
	 * 
	 */

	//public DiscreteFormConfigurationUtilityPage clickLnkDiscreteForms() throws Exception {
/*	public DiscreteFormsPage clickLnkDiscreteForms() throws Exception {

		log("Clicking on Discrete forms");
		IHGUtil.waitForElement(driver, 50, lnkDiscreteForms);
		try {
			lnkDiscreteForms.click();
		}catch(Exception e) {
			lnkDiscreteForms.click();
		}
		Thread.sleep(2000);
		for(String winHandle : driver.getWindowHandles()){
			driver.switchTo().window(winHandle);
		}
		if(TestConfig.getBrowserType().equals(BrowserTypeUtil.BrowserType.iexplore)) {
			driver.manage().window().maximize();
		}

		return PageFactory.initElements(driver, DiscreteFormsPage.class);

	}*/
	
	
	/**
	 * Click on link - General Registration and Health History	
	 * @return
	 * @throws InterruptedException 
	 */
	
	public BasicInformationAboutYouPage clicklnkAutomationPracticeDiscreteForm() throws InterruptedException
	{	
		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);
		
		IHGUtil.waitForElement(driver, 30, lnkAutomationPracticeDiscreteForm);
		lnkAutomationPracticeDiscreteForm.click();
		
		/*
		lnkGeneralRegAndHealthHistory.click();
		
		IHGUtil.waitForElement(driver, 30, lnkUnPublishForm);
		lnkUnPublishForm.click();
		Thread.sleep(2000);
		lnkDeletePublishForm.click();
		
		*/
			
		// Close the browser window
		return PageFactory.initElements(driver,BasicInformationAboutYouPage.class);
	}
	



}
