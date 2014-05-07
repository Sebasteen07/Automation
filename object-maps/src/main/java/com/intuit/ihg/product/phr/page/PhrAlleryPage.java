package com.intuit.ihg.product.phr.page;

import java.util.List;
import java.util.NoSuchElementException;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class PhrAlleryPage extends BasePageObject{
	
	@FindBy(linkText = "Allergies")
	private WebElement allergy;
	
	@FindBy(xpath = ".//img[@alt='ADD ALLERGIES']")
	private WebElement addAllergy; 
	
	private static String addAnotherXpath = ".//input[@type='text' and @name='other0']";
	
	@FindBy(xpath = ".//a//img[@alt='ADD ALLERGIES']")
	private WebElement addAllergiesSubmit;
	
	public PhrAlleryPage(WebDriver driver) {
		super(driver);
		// Auto-generated constructor stub
	}
	
	public void selectAllergy(){
		IHGUtil.PrintMethodName();
		allergy.click();
	}
	public boolean addAllergyButton(){
		IHGUtil.PrintMethodName();
		addAllergy.isDisplayed();
		return true;
	}
	public void add_Allergy(){
		IHGUtil.PrintMethodName();
		addAllergy.click();
	}
	public  List<WebElement> findAllCheckBoxes() {
		
		IHGUtil.PrintMethodName();
		String xpath = "//input[@type='checkbox' and @class!='skipLine']";
		return driver.findElements(By.xpath(xpath));
	}
	
	public void selectFirstCheckBox(boolean input) throws InterruptedException {
		IHGUtil.PrintMethodName();

		List<WebElement> listCheckBoxes = findAllCheckBoxes();
		for(WebElement el : listCheckBoxes){
			if (input && !el.isSelected()){
				el.click();
				break;
			}
			else if (!input && el.isSelected()){
				el.click();
				break;
			}
		}
	}
	
	public void setAnyOtherField(String input) {
		IHGUtil.PrintMethodName();
		WebElement anyOtherField = driver.findElement(By.xpath(addAnotherXpath));
		System.out.println("###going to clear");
		anyOtherField.clear();
		System.out.println("###cleared");
		anyOtherField.sendKeys(input);
	}
	
	public void submitAllergy(){
		IHGUtil.PrintMethodName();
		addAllergiesSubmit.click();
	}
	
	public void addAllergy() throws InterruptedException
	{
		if(this.addAllergyButton())
		{
			this.add_Allergy();
			this.selectFirstCheckBox(true);
			this.setAnyOtherField("No more allergy to share");
			this.submitAllergy();
		}
		else
		{
			this.selectFirstCheckBox(true);
			this.setAnyOtherField("No more allergy to share");
			this.submitAllergy();
		}
	}
	public void RemoveValues() throws InterruptedException,NoSuchElementException {
		boolean visible = false;
		try{
			WebElement tablePath = driver.findElement(By.xpath("//table[@id='custom_table_list_id']"));
			List<WebElement> rows = tablePath.findElements(By.tagName("tr"));
			int rowSize = rows.size();
			while(rowSize>1){
				WebElement removeValuesFromTable = driver.findElement(By.xpath("//a/img[@alt='REMOVE']"));
				IHGUtil.waitForElement(driver, 10, removeValuesFromTable);
				if(removeValuesFromTable.isDisplayed())
				{
					visible = true;
					removeValuesFromTable.click();
					Thread.sleep(5000);
					driver.switchTo().alert().accept();
					Thread.sleep(5000);
					rowSize--;
					Thread.sleep(5000);
				}
				else
				{
					System.out.println("###PhrHealthInformationPage ---No more rows to remove");
				}
			}
		}
		catch(NoSuchElementException e)
		{
			Assert.assertFalse(visible);
			System.out.println("###No such element is present");
		}
	}
	
}
