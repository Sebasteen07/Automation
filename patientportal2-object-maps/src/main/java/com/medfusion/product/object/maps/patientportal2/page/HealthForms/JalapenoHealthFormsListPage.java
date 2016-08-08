package com.medfusion.product.object.maps.patientportal2.page.HealthForms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;


//TODO not used anywhere - is needed?
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

}
