// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.Appointment.Speciality;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.AppEntryPoint.StartAppointmentInOrder;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;

public class Speciality extends PSS2MainPage {

	@FindAll({@FindBy(xpath = "//*[@class='col-sm-6 col-xs-12 margin-right-specailty' or @class='btn specialtybtndashboard handle-text-Overflow outer-div']")})
	private List<WebElement> selectSpecialityList;

	@FindBy(how = How.ID, using = "specailtydashboard")
	private WebElement searchSpeciality;
	
	
	@FindBy(how = How.XPATH, using = "//*[@id='restrictSpecialty']/div/div/div[2]/pre/div")
	private WebElement linkSpecialityText;
	
	@FindBy(how = How.XPATH, using = "//*[@id='restrictSpecialty']/div/div/div[3]/button[1]/span")
	private WebElement linkSpecialityOkBtn;

	public Speciality(WebDriver driver) {
		super(driver);
	}

	public StartAppointmentInOrder selectSpeciality(String specialityName) {
		for (int i = 0; i < selectSpecialityList.size(); i++) {
			if (selectSpecialityList.get(i).getText().contains(specialityName)) {
				log("selectSpeciality Selected is " + selectSpecialityList.get(i).getText());

				jse.executeScript("arguments[0].setAttribute('style', 'background: green; border: 3px solid blue;');", selectSpecialityList.get(i));
				selectSpecialityList.get(i).click();
				return PageFactory.initElements(driver, StartAppointmentInOrder.class);
			}
		}
		log("Speciality not found.");
		return null;
	}

	public String selectSpeciality1(String specialityName) {
		String nameSpecility = "";
		for (int i = 0; i < selectSpecialityList.size(); i++) {
			if (selectSpecialityList.get(i).getText().contains(specialityName)) {
				log("selectSpeciality Selected is " + selectSpecialityList.get(i).getText());
				jse.executeScript("arguments[0].setAttribute('style', 'background: green; border: 3px solid blue;');", selectSpecialityList.get(i));
				nameSpecility = selectSpecialityList.get(i).getText();
				log("Specility Name is " + nameSpecility);

			}
		}
		return nameSpecility;
	}
	
	public String linkSpecialityText() {
		IHGUtil.waitForElement(driver, 10, linkSpecialityText);
		String str = linkSpecialityText.getText();
		return str;
	}
	
	public void linkSpecialityOkBtn() {
		IHGUtil.waitForElement(driver, 10, linkSpecialityOkBtn);
	    linkSpecialityOkBtn.click();
	}
}
