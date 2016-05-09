package com.medfusion.product.object.maps.practice.page.treatmentplanpage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class TreatmentPlansPage extends BasePageObject {

	private static final String pageTitle = "My Home";


	@FindBy( xpath = ".//input[@value='Submit']")
	private WebElement submitButton;

	@FindBy( xpath = ".//*[@id='pagetitle']/h1")
	private WebElement pageTitleEle;

	@FindBy( xpath = ".//select[@name='featureid']")
	private WebElement featureDropDwn;

	@FindBy( id = "title")
	private WebElement titleField;
	
	@FindBy( id = "subject")
	private WebElement subjectField;
	
	@FindBy( xpath = ".//textarea[@name='body']")
	private WebElement bodyField;
	
	@FindBy( xpath = "//input[@value='Create Treatment Plan']")
	private WebElement createTreatmentPlanBtn;
	
	
	public TreatmentPlansPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public boolean checkTreatmentPlanPage() {
		IHGUtil.PrintMethodName();
		return pageTitleEle.getText().contains(pageTitle);
	}


	public void clickOnSubmitButton() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, submitButton);
		submitButton.click();
	}

	public void selectAppointmentRequest(){
		Select sel = new Select(featureDropDwn);
		sel.selectByValue("1");
	}

	public void createTreatmentPlanInfo(String title, String subject, String text) {
		IHGUtil.PrintMethodName();
		titleField.sendKeys(title);
		subjectField.sendKeys(subject);
		bodyField.sendKeys(text);
		createTreatmentPlanBtn.click();
	}
	
	public String checkTreatmentPlanSuccessMsg() {
		IHGUtil.PrintMethodName();
		String successMsg = driver.findElement(By.xpath(".//*[@id='content']/table/tbody/tr/td/strong")).getText();
		return successMsg;
	}
	

}
