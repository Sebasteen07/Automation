package com.medfusion.product.patientportal2.flows;

import org.openqa.selenium.WebDriver;

import com.medfusion.product.patientportal2.pojo.Jalapeno;
import com.medfusion.product.patientportal2.pojo.StatementPreferenceType;

/**
 * PI API for setting statement preference.
 * 
 * @author Martin
 */

public interface IStatementPreference {

	/**
	 * Changes patient's statement preference
	 * 
	 * @param driver
	 * @param portal
	 *            URL, username and password are needed
	 * @param statementPreferenceType
	 *            preference to be set
	 * @return true if statementPreferenceType was changed to given value <br/>
	 *         false if statementPreferenceType were the same as given value
	 */
	boolean updateStatementPreferenceFromMyAccount(WebDriver driver, Jalapeno portal,
			StatementPreferenceType statementPreferenceType);
}
