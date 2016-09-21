package com.medfusion.product.object.maps.patientportal1.page.solutions.virtualofficevisit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class VirtualOfficeVisitProviderPage extends BasePageObject {

	public static final String PAGE_NAME = "Virtual Office Visit Provider Page";

	@FindBy(name = "provider")
	private WebElement provider;

	@FindBy(xpath = "//input[@value='Continue']")
	private WebElement btnContinue;

	public VirtualOfficeVisitProviderPage(WebDriver driver) {
		super(driver);
	}


	/**
	 * Will select the supplied provider and click continue. If the provider argument is null or empty, the last provider in the list will be selected.
	 * 
	 * @param providerOption the visible text in the provider select box
	 * @return the VoV pharmacy page
	 */
	public VirtualOfficeVisitPharmacyPage chooseProviderAndContinue(String providerOption) {
		IHGUtil.PrintMethodName();
		// PortalUtil.setPortalFrame(driver);

		Select providerSelect = new Select(provider);

		if (providerOption == null || providerOption.isEmpty()) {
			providerSelect.selectByIndex(providerSelect.getOptions().size() - 1);
		} else {
			// Having an issue selecting provider by visible text
			providerSelect.selectByVisibleText(providerOption);
		}
		btnContinue.click();

		return PageFactory.initElements(driver, VirtualOfficeVisitPharmacyPage.class);
	}

	/**
	 * Gives indication if page is loaded
	 * 
	 * @return true or false
	 */
	public boolean isPageLoaded() {
		IHGUtil.PrintMethodName();

		boolean result = false;
		try {
			result = btnContinue.isDisplayed();
		} catch (Exception e) {

		}

		return result;
	}

}
