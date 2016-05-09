package com.medfusion.product.object.maps.practice.page.symptomassessment;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class SymptomAssessmentDetailsPage extends BasePageObject  {
	
	@FindBy(name="subject")
	private WebElement txtEmailsubject;
	
	@FindBy(name="body")
	private WebElement txtEmailBody;
	
	@FindBy(css="input.menuitem")
	private WebElement btnSendCommunication;


	public SymptomAssessmentDetailsPage(WebDriver driver) {
		super(driver);
	}
	/**
	 * author :bkrishnankutty
	 * Date: 5/9/2013
	 * Desc :- This method will send Message to patient
	 * @return String emailSubject (ie unique subject)
	 */
	
	public String sentMessage(){
		log("DEBUG: START: sentMessage()");
		String str = IHGUtil.getRandomValue(null, 2, 8, 4);
		txtEmailsubject.sendKeys("test"+str);	
		txtEmailBody.sendKeys("test"+str);
	 	btnSendCommunication.click();
	 	String emailSubject = "test"+str;
		return emailSubject;
	}

}
