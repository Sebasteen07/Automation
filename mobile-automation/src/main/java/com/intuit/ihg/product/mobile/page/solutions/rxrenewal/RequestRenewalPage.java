package com.intuit.ihg.product.mobile.page.solutions.rxrenewal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.mobile.page.MobileBasePage;
import com.intuit.ihg.product.mobile.page.MobileHomePage;
import com.intuit.ihg.product.mobile.page.solutions.pharmacy.AddPharmacyPage;



public class RequestRenewalPage extends MobileBasePage{
	
	private WebElement pharmacy;
	
	@FindBy(xpath = "//a[@id='rxReqSubmit']/span")
	private WebElement rbsubmit;
	
	@FindBy(id = "addPharmacyLink")
	private WebElement addPharmacy;
	
	@FindBy(xpath = "//fieldset[@id='rxreqPharmacies']/div/div[1]/label/span")
	private WebElement firstPharmacy;
	
	@FindBy(linkText="Close")
	private WebElement closeBtn;

	public RequestRenewalPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
    public MobileBasePage selectPharmacy(String pharmacyName){
    	int i= 1;
    	pharmacy = driver.findElement(By.xpath("//fieldset[@id='rxreqPharmacies']/div/div["+i+"]/label/span"));
    	while(pharmacy.isDisplayed())
    	{
    		String pharmacy_str= pharmacy.getText().trim().replaceAll("\n","");
    		log("pharmacy_str======="+pharmacy_str);
    		
    		if(pharmacy_str.equalsIgnoreCase(pharmacyName))
    		{
    			pharmacy.click();
    			break;
    		}
    		else{
    			i=i+1;
    			pharmacy = driver.findElement(By.xpath("//fieldset[@id='rxreqPharmacies']/div/div["+i+"]/label/span"));
    		}
    	 //Claritin D
    	
    	}
    	
        return PageFactory.initElements(driver, MobileBasePage.class);
    }
    
    public MobileBasePage selectFirstPharmacy(){
    	IHGUtil.waitForElement(driver,6, firstPharmacy);
    	if(firstPharmacy.isDisplayed()){
    		firstPharmacy.click();
    	}    	
        return PageFactory.initElements(driver, MobileBasePage.class);
    }
    
    public AddPharmacyPage addNewPharmacy(){
    	IHGUtil.waitForElement(driver,6, addPharmacy);
    	if(addPharmacy.isDisplayed()){
    		addPharmacy.click();
    	}    	
        return PageFactory.initElements(driver, AddPharmacyPage.class);
    }

    public MobileBasePage clickButtonSubmit(){
    	IHGUtil.waitForElement(driver,6, rbsubmit);
    	rbsubmit.click();
    	return PageFactory.initElements(driver, MobileBasePage.class);
    }
    
    public MobileHomePage clickClose() throws InterruptedException {
        Thread.sleep(2000);
        waitForCloseBtn(driver, 20);
        IHGUtil.PrintMethodName();
        closeBtn.click();

        return PageFactory.initElements(driver, MobileHomePage.class);
    }
    
    public void waitForCloseBtn(WebDriver driver, int n) throws InterruptedException {
        IHGUtil.PrintMethodName();
        IHGUtil.waitForElement(driver, n, closeBtn);
    }
}
