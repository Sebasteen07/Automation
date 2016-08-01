package com.medfusion.product.patientportal2.implementedExternals;

import org.openqa.selenium.WebDriver;

import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.AccountPage.JalapenoAccountPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountProfilePage;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountPreferencesPage;
import com.medfusion.product.patientportal2.flows.IStatementPreference;
import com.medfusion.product.patientportal2.pojo.Jalapeno;
import com.medfusion.product.patientportal2.pojo.StatementPreferenceType;

public class StatementPreference implements IStatementPreference {

	@Override
	public boolean updateStatementPreferenceFromMyAccount(WebDriver driver, Jalapeno portal,
			StatementPreferenceType statementPreferenceType) {
		JalapenoMyAccountPreferencesPage preferencesPage = logInAndGoToMyPreferencesPage(driver, portal.url, portal.username,
				portal.password);
		return preferencesPage.checkAndSetStatementPreference(driver, statementPreferenceType);
	}

	private JalapenoMyAccountPreferencesPage logInAndGoToMyPreferencesPage(WebDriver driver, String portalURL, String username,
			String password) {
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, portalURL);
		JalapenoHomePage homePage = loginPage.login(username, password);
		JalapenoAccountPage accountPage = homePage.clickOnAccount(driver);
		JalapenoMyAccountProfilePage myAccountPage = accountPage.clickOnEditMyAccount();

		return myAccountPage.goToPreferencesTab(driver);
	}
}
