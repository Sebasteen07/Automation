package com.medfusion.product.practice.implementedExternals;

import org.openqa.selenium.WebDriver;

import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.virtualCardSwiper.VirtualCardSwiperPage;
import com.medfusion.product.practice.api.pojo.Practice;
import com.medfusion.product.practice.api.pojo.VCSPaymentInfo;


public class VCSPayment implements com.medfusion.product.practice.api.flows.IPayVCS{

	@Override
	public boolean simplePay(WebDriver driver, Practice practiceInfo, VCSPaymentInfo pay)
			throws InterruptedException {
		VirtualCardSwiperPage sPage = new PracticeLoginPage(driver, practiceInfo.url).login(practiceInfo.username, practiceInfo.password).clickVirtualCardSwiperTab();
		sPage.addCreditCardInfo(pay.cardHolderName, pay.creditCardNumber, pay.creditCardType, pay.creditCardExpirationMonth, pay.creditCardExpirationYear, pay.amountToCharge, pay.cVVCode, pay.cardholderZip, pay.accountNumber, pay.patientName, pay.paymentComment, pay.serviceLocation);		
		return sPage.getPaymentCompletedSuccessMsg().equals("Payment completed");
	}	
}