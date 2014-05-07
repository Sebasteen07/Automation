package com.intuit.ihg.product.object.maps.mobile.page.solutions.ccdviewer;

import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.object.maps.mobile.page.MobileBasePage;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by IntelliJ IDEA.
 * User: vvalsan
 * Date: 3/21/13
 * Time: 4:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class CCDViewerListPage extends MobileBasePage{
    public CCDViewerListPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(linkText="Health Overview")
    private WebElement btnHealthOverview;

    @FindBy(linkText="Basic Information About You")
    private WebElement basicInfo;

    @FindBy(linkText="Care Team Members")
    private WebElement careTeam;

    @FindBy(linkText="Vital Signs")
    private WebElement vitalSigns;

    @FindBy(linkText="Exams, Tests & Procedures")
    private WebElement examsTests;

    @FindBy(linkText="Medications")
    private WebElement medications;

    @FindBy(linkText="Allergies")
    private WebElement allergies;

    @FindBy(linkText="Illnesses & Conditions")
    private WebElement illness;

    @FindBy(linkText="History Of Present Illness")
    private WebElement historyOfIllness;

    @FindBy(linkText="Advance Directives")
    private WebElement advanceDirectives;

    @FindBy(linkText="Past Medical History")
    private WebElement pastMedical;

    public CCDViewerDetailPage clicBasicInfo() throws InterruptedException {
        waitForBtn(driver, 20);
        IHGUtil.PrintMethodName();
        basicInfo.click();
        return PageFactory.initElements(driver, CCDViewerDetailPage.class);
    }

    public void waitForBtn(WebDriver driver, int n) throws InterruptedException {
        IHGUtil.PrintMethodName();
        try{
            IHGUtil.waitForElement(driver, n, basicInfo);
        }catch (TimeoutException e){
          log("WARN : Timeout occurred waiting for CCD Viewer button");
        }

    }
}
