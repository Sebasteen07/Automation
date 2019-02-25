package com.medfusion.product.object.maps.patientportal2.page.AskAStaff;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;

public class JalapenoAskAStaffV2HistoryDetailPage extends JalapenoMenu {
    
    public JalapenoAskAStaffV2HistoryDetailPage(WebDriver driver) {
        super(driver);
        IHGUtil.PrintMethodName();
        driver.manage().window().maximize();
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.ID, using = "backButton")
    private WebElement backButton;
    
    @FindBy(how = How.XPATH, using = "//div[@id='askaHistoryDetail']/div[2]/fieldset/div/div/table/tbody/tr[1]/td[2]")
    private WebElement requestDetailDate;
    @FindBy(how = How.XPATH, using = "//div[@id='askaHistoryDetail']/div[2]/fieldset/div/div/table/tbody/tr[2]/td[2]")
    private WebElement requestDetailLocation;
    @FindBy(how = How.XPATH, using = "//div[@id='askaHistoryDetail']/div[2]/fieldset/div/div/table/tbody/tr[3]/td[2]")
    private WebElement requestDetailSubject;
    @FindBy(how = How.XPATH, using = "//div[@id='askaHistoryDetail']/div[2]/fieldset/div/div/table/tbody/tr[4]/td[2]")
    private WebElement requestDetailQuestion;
    @FindBy(how = How.XPATH, using = "//div[@id='askaHistoryDetail']/div[2]/fieldset/div/div/table/tbody/tr[5]/td[2]")
    private WebElement requestDetailStatus;
    
    @Override
    public boolean areBasicPageElementsPresent() {
        ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();

        webElementsList.add(homeMenu);
        webElementsList.add(signoutButton);
        webElementsList.add(backButton);
        webElementsList.add(requestDetailDate);
        webElementsList.add(requestDetailLocation);
        webElementsList.add(requestDetailSubject);
        webElementsList.add(requestDetailQuestion);
        webElementsList.add(requestDetailStatus);

        return assessPageElements(webElementsList);
    }
    
    public String getRequestDetailDate(){
        return requestDetailDate.getText();
    }
    public String getRequestDetailLocation(){
        return requestDetailLocation.getText();
    }
    public String getRequestDetailSubject(){
        return requestDetailSubject.getText();
    }
    public String getRequestDetailQuestion(){
        return requestDetailQuestion.getText();
    }
    public String getRequestDetailStatus(){
        return requestDetailStatus.getText();
    }
}
