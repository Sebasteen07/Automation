package com.medfusion.product.appt.precheck.Hooks;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.object.maps.appt.precheck.page.Login.AppointmentPrecheckLogin;
import com.medfusion.product.object.maps.appt.precheck.util.BaseTest;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {
	BaseTest baseTest = new BaseTest();
	PropertyFileLoader propertyData;
	AppointmentPrecheckLogin loginPage;

	@Before
	public void beforeSuite(Scenario method) throws Exception {
		baseTest.beforeSuite();
		baseTest.setUp();
		baseTest.testSetup(method);
	}

	@After
	public void postTestCase(Scenario result) throws Exception {
		baseTest.tearDown(result);
		baseTest.postTestCase(result);
		
	}
}
