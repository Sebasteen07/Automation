package com.intuit.ihg.product.sitegen.page.discreteforms;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
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

	@FindBy ( xpath = ".//div[@class = 'admin_inner']/div[@class ='new_discrete_form']/a[@class ='button teal']")
	private WebElement registrationHealthHistoryFormFormButton;

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

}
