package com.medfusion.product.object.maps.patientportal2.page.HealthForms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoPage;

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
