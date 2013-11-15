package com.intuit.ihg.product.mobile.page.makepayment;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.mobile.page.MobileBasePage;

public class NewCreditCardBillingInformation extends MobileBasePage{

	@FindBy(id = "payNewBillingName")
	private WebElement txtPayNewBillingName;
	
	@FindBy(id = "payNewBillingAddress1")
	private WebElement txtPayNewBillingAddress1;
	
	@FindBy(id = "payNewBillingAddress2")
	private WebElement txtPayNewBillingAddress2;
	
	@FindBy(id = "payNewBillingZip")
	private WebElement txtPayNewBillingZip;
	
	@FindBy(css = "#paymentNewCardBillingSubmit > span.ui-btn-inner.ui-btn-corner-all > span.ui-btn-text")
	private WebElement btnSubmit;

	
  public NewCreditCardBillingInformation(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
  
  public void setCardBillingName(String name)
  {
	  IHGUtil.PrintMethodName();
	  txtPayNewBillingName.clear();
	  txtPayNewBillingName.sendKeys(name);
  }
  
  public void setBillingAddress1(String address1)
  {
	  IHGUtil.PrintMethodName();
	  txtPayNewBillingAddress1.clear();
	  txtPayNewBillingAddress1.sendKeys(address1);
  }
  
  public void setBillingAddress2(String address2)
  {
	  IHGUtil.PrintMethodName();
	  txtPayNewBillingAddress2.clear();
	  txtPayNewBillingAddress2.sendKeys(address2);
  }

  
  public void setCardBillingZip(String zip)
  {
	  IHGUtil.PrintMethodName();
	  txtPayNewBillingZip.clear();
	  txtPayNewBillingZip.sendKeys(zip);
  }
  
  public void clickbtnSubmit()
  {
	  IHGUtil.PrintMethodName();
	  btnSubmit.click();
  }
  
  /*
   *Name:- AutoDemoLN,AutoDemoFN
   *setAddress1("123 XYZ Ave");
   *setAddress2("Mountain View");
   *setZip("94043");
   */
  public MakeAPayment fillCardBillingInformation(String name,String address1, String address2,String zip)
  {
	 IHGUtil.PrintMethodName();
	 setCardBillingName(name);
	 setBillingAddress1(address1);
	 setBillingAddress2(address2);
	 setCardBillingZip(zip);
	 clickbtnSubmit();
	 return PageFactory.initElements(driver, MakeAPayment.class);

  }
	

}
