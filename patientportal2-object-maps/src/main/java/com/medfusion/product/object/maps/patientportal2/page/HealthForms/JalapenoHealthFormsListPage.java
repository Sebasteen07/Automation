package com.medfusion.product.object.maps.patientportal2.page.HealthForms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;

public class JalapenoHealthFormsListPage extends MedfusionPage {

	/**
	 * @Author:Petr Hajek
	 * @Date:9.6.2015
	 */

	public JalapenoHealthFormsListPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}

	@Override
	public boolean assessBasicPageElements() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * automatically switches to corresponding iframe
	 * ({@link com.medfusion.product.object.maps.patientportal2.page.JalapenoPage#JalapenoNewCustomHealthFormPage(WebDriver driver)
	 * see customFormPage constructor})
	 * 
	 * @param formName
	 * @return
	 * @throws InterruptedException
	 */
	public JalapenoNewCustomHealthFormPage openForm(String formName) throws InterruptedException {
		driver.findElement(By.xpath("//a[@title='" + formName + "']")).click();
		return PageFactory.initElements(driver, JalapenoNewCustomHealthFormPage.class);
	}
}
