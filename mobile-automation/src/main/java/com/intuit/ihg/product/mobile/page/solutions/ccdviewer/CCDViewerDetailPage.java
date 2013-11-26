package com.intuit.ihg.product.mobile.page.solutions.ccdviewer;

import com.intuit.ihg.product.mobile.page.MobileBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: vvalsan
 * Date: 3/21/13
 * Time: 4:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class CCDViewerDetailPage extends MobileBasePage{


    public CCDViewerDetailPage(WebDriver driver) {
        super(driver);
    }

    
    @FindBy(xpath="//div[@class='blood_pressure item']")
    private WebElement bloodPressure;

    @FindBy(xpath="//div[@class='height_weight item']")
    private WebElement heightWeight;
    
    @FindBy(xpath="//ul[@class='listing']")
    private WebElement basicInfo;
    
  

    public boolean assertHealthOverview(String verifyTextInBP,String verifyTextInHtWt){
        log("bloodPressure Text : " + bloodPressure.getText());
        log("heightWeight Text : " + heightWeight.getText());
        if(bloodPressure.getText().contains(verifyTextInBP)){
            if (heightWeight.getText().contains(verifyTextInHtWt))
                return true;
        }
        return false;
    }
    
    public boolean verifyBasicInfo(){
    	String[] infos = {"Name", "Date Of Birth", "Street Address", "City", "State"};
    	String infoWeb = basicInfo.getText();
    	for (String info : infos) {
    		if (!infoWeb.contains(info))
    			return false;
   		}
        return true;
    }



}
