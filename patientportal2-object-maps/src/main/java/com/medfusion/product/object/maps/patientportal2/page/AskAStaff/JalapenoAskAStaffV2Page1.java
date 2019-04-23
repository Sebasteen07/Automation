package com.medfusion.product.object.maps.patientportal2.page.AskAStaff;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;

public class JalapenoAskAStaffV2Page1 extends JalapenoMenu {
	// Navigation    
    @FindBy(how = How.ID, using = "historyButton")
    private WebElement historyButton;
    @FindBy(how = How.ID, using = "continueButton")
    private WebElement continueButton;
    
    @FindBy(how = How.ID, using = "subject")
    private WebElement subjectBox;
    @FindBy(how = How.ID, using = "question")
    private WebElement questionBox;
    
    //Custom component, use click -> sendkeys sequence to manipulate 
    @FindBy(how = How.XPATH, using = "//div[@id='locationField']/mf-combobox/div")
    private WebElement locationCombo;
    @FindBy(how = How.XPATH, using = "//div[@id='providerField']/mf-combobox/div")
    private WebElement providerCombo;
    
 
    // Credit card
    @FindBy(how = How.ID, using = "creditCardAddButton")
    private WebElement addCard;
    @FindBy(how = How.ID, using = "cvv")
    private WebElement cardCVV;    
 
    private long createdTS;
 
    public JalapenoAskAStaffV2Page1(WebDriver driver) {
        super(driver);
        IHGUtil.PrintMethodName();
        driver.manage().window().maximize();
        PageFactory.initElements(driver, this);
        createdTS = System.currentTimeMillis();
    }
 
    public long getCreatedTimeStamp() {
        return createdTS;
    }
    /**
     * fills the subject and question fields then continues to the next page and returns its page object
     * null or empty subject param will leave the default (= the aska label)
     * @param subject
     * @param question
     * @return
     */
    public JalapenoAskAStaffV2Page2 fillAndContinue(String subject, String question){
    	if(subject != null && !subject.trim().isEmpty()) { 
    		subjectBox.clear();
    		subjectBox.sendKeys(subject);
    	}
    	questionBox.sendKeys(question);
    	continueButton.click();
    	return PageFactory.initElements(driver, JalapenoAskAStaffV2Page2.class);
    }

	@Override
	public boolean areBasicPageElementsPresent() {
		//provider, location, and card info are all optional
		return subjectBox.isDisplayed() && questionBox.isDisplayed() && historyButton.isDisplayed() && continueButton.isDisplayed();
	}
	
	public JalapenoAskAStaffV2HistoryListPage clickOnHistory() {
        log("Clicking on Ask a Question menu button");
        historyButton.click();
        return PageFactory.initElements(driver, JalapenoAskAStaffV2HistoryListPage.class);
    }
}
