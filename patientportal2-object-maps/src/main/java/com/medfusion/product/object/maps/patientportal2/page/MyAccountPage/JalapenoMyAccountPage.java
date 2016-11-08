package com.medfusion.product.object.maps.patientportal2.page.MyAccountPage;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;

public abstract class JalapenoMyAccountPage extends JalapenoMenu {

	@FindBy(how = How.LINK_TEXT, using = "Profile")
	protected WebElement profileTab;

	@FindBy(how = How.LINK_TEXT, using = "Security")
	protected WebElement securityTab;

	@FindBy(how = How.LINK_TEXT, using = "Preferences")
	protected WebElement preferencesTab;

	@FindBy(how = How.ID, using = "prevStep")
	protected WebElement previousStep;

	@FindBy(how = How.ID, using = "saveAccountChanges")
	protected WebElement saveAccountChanges;

	protected JalapenoMyAccountPage(WebDriver driver) {
		super(driver);
	}

	protected boolean assessPageElements(boolean includeSaveChangesBtn) {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();

		webElementsList.add(profileTab);
		webElementsList.add(securityTab);
		webElementsList.add(preferencesTab);
		webElementsList.add(previousStep);
		if (includeSaveChangesBtn) {
			webElementsList.add(saveAccountChanges);
		}

		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}

}
