package com.medfusion.product.object.maps.patientportal2.page.AskAStaff;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;

public class JalapenoAskAStaffV2HistoryListPage extends JalapenoMenu {

    @FindBy(how = How.ID, using = "backButton")
    private WebElement backButton;
    
    public JalapenoAskAStaffV2HistoryListPage(WebDriver driver) {
        super(driver);
        IHGUtil.PrintMethodName();
    }

    @Override
    public boolean areBasicPageElementsPresent() {
        ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();

        webElementsList.add(signoutButton);
        webElementsList.add(backButton);

        return assessPageElements(webElementsList);
    }
    
    public JalapenoAskAStaffV2HistoryDetailPage goToDetailByReason(String reason){
        log("Searching table Reasons for " + reason);
        driver.findElement(By.xpath("//table/tbody/tr/td[contains(text(),'" + reason + "')]/..")).click();
        return PageFactory.initElements(driver, JalapenoAskAStaffV2HistoryDetailPage.class);
    }

}
