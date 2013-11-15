package com.intuit.ihg.product.mobile.page.makepayment;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.mobile.page.MobileBasePage;


public class NewCard extends MobileBasePage{
	
	@FindBy( id = "payNewCardNumber")
	private WebElement txtnewCardNumber;
	
	@FindBy( id = "payNewCardCVV")
	private WebElement txtnewCardCVV;
	
	@FindBy( id = "payNewCardExpirationYear")
	private WebElement SelectnewCardExpirationYear;
	
	@FindBy( id = "payNewCardExpirationMonth")
	private WebElement SelectnewCardExpirationMonth;

	@FindBy( css = "#paymentNewCardSubmit > span.ui-btn-inner.ui-btn-corner-all > span.ui-btn-text")
	private WebElement submit;

	public NewCard(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public void setNewCardNumber(String ccNo)
	{
		IHGUtil.PrintMethodName();
		txtnewCardNumber.clear();
		txtnewCardNumber.sendKeys(ccNo);
	}

	public void setnewCardCVV(String cvv)
	{
		IHGUtil.PrintMethodName();
		txtnewCardCVV.clear();
		txtnewCardCVV.sendKeys(cvv);
	}
	
	public void selectNewCardExpirationYear(String newCardExpirationYear)
	{
		IHGUtil.PrintMethodName();
		selectFromDropDown(SelectnewCardExpirationYear, newCardExpirationYear);
	}
	
	public void selectNewCardExpirationMonth(String newCardExpirationMonth)
	{
		IHGUtil.PrintMethodName();
		selectFromDropDown(SelectnewCardExpirationMonth, newCardExpirationMonth);
	}
	
	public void clickBtnSubmit()
	{
		IHGUtil.PrintMethodName();
		submit.click();
	}
	
	public NewCreditCardBillingInformation fillCardDetails(String ccNo,String cvv,String newCardExpirationYear,String newCardExpirationMonth)
	{
		IHGUtil.PrintMethodName();
		setNewCardNumber(ccNo);
		setnewCardCVV(cvv);
		selectNewCardExpirationYear(newCardExpirationYear);
		selectNewCardExpirationMonth(newCardExpirationMonth);
		clickBtnSubmit();
		return PageFactory.initElements(driver, NewCreditCardBillingInformation.class);
	}
	
}
