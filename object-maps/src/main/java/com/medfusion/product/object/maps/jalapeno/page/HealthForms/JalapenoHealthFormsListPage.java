package com.medfusion.product.object.maps.jalapeno.page.HealthForms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import com.intuit.ihg.common.utils.IHGUtil;
import com.medfusion.product.object.maps.jalapeno.page.JalapenoPage;

public class JalapenoHealthFormsListPage extends JalapenoPage {

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

}
