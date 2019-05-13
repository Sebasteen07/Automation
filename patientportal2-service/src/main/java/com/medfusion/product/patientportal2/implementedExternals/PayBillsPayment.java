package com.medfusion.product.patientportal2.implementedExternals;

import com.medfusion.product.patientportal2.pojo.PortalBasic;
import org.openqa.selenium.WebDriver;

import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage.JalapenoPayBillsConfirmationPage;
import com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage.JalapenoPayBillsMakePaymentPage;
import com.medfusion.product.patientportal2.flows.IPayBillsPayment;
import com.medfusion.product.patientportal2.pojo.CreditCard;
import com.medfusion.product.patientportal2.pojo.PatientInfo;

public class PayBillsPayment implements IPayBillsPayment {

		@Override
		public String payBillsPayment(WebDriver driver, PortalBasic portInfo, PatientInfo patInfo, CreditCard creditCard, String amount, String location) {
				try {
						System.out.println("Initiate payment data");

						System.out.println("Load login page");
						JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, portInfo.url);

						JalapenoHomePage homePage = loginPage.login(patInfo.username, patInfo.password);
						JalapenoPayBillsMakePaymentPage payBillsPage = homePage.clickOnNewPayBills(driver);
						// remove all cards just in case
						payBillsPage.removeAllCards();

						// page break = failed
						if (!payBillsPage.assessPayBillsMakePaymentPageElements())
								return "";

						JalapenoPayBillsConfirmationPage confirmationPage = payBillsPage.fillPaymentInfo(amount, "" + patInfo.billingAccountNumber, creditCard, location);

						// page break = failed
						if (!confirmationPage.assessPayBillsConfirmationPageElements())
								return "";

						homePage = confirmationPage.commentAndSubmitPayment("Testing payment from number: " + patInfo.billingAccountNumber);
						// page break = failed
						String found = homePage.getConfirmationNumberFromPayment();
						if (found.equals(""))
								return "";
						else
								return found;
				} catch (Exception e) {
						System.out.println("Exception encountered during pay flow: " + e);
						return "";
				}
		}

		@Override
		public String payBillsPayment(WebDriver driver, PortalBasic portInfo, PatientInfo patInfo, CreditCard creditCard, String amount) {
				try {
						System.out.println("Initiate payment data");

						System.out.println("Load login page");
						JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, portInfo.url);

						JalapenoHomePage homePage = loginPage.login(patInfo.username, patInfo.password);
						JalapenoPayBillsMakePaymentPage payBillsPage = homePage.clickOnNewPayBills(driver);
						// remove all cards just in case
						payBillsPage.removeAllCards();

						// page break = failed
						if (!payBillsPage.assessPayBillsMakePaymentPageElements())
								return "";

						JalapenoPayBillsConfirmationPage confirmationPage = payBillsPage.fillPaymentInfo(amount, "" + patInfo.billingAccountNumber, creditCard);

						// page break = failed
						if (!confirmationPage.assessPayBillsConfirmationPageElements())
								return "";

						homePage = confirmationPage.commentAndSubmitPayment("Testing payment from number: " + patInfo.billingAccountNumber);
						// page break = failed
						String found = homePage.getConfirmationNumberFromPayment();
						if (found.equals(""))
								return "";
						else
								return found;
				} catch (Exception e) {
						System.out.println("Exception encountered during pay flow: " + e);
						return "";
				}
		}

}
