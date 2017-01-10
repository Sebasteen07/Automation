package com.medfusion.rcm.utils;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.WebPoster;

public class RCMUtil extends IHGUtil {

	protected WebDriver driver;


	public static int timeout = 0;
	public static String[] exeArg = null;

	public RCMUtil(WebDriver driver) {
		super(driver);
	}

	public WebDriver getDriver(WebDriver driver) {
		IHGUtil.PrintMethodName();
		return driver;
	}

	public void postStatementToPatient(String rcmStatementRest, String env) throws Exception {

		IHGUtil.PrintMethodName();

		WebPoster poster = new WebPoster();

		Assert.assertNotNull("### Test property rcmStatementRest not defined", rcmStatementRest);
		poster.setServiceUrl(rcmStatementRest.trim());

		poster.setContentType("application/json;");
		poster.addHeader("requestId", "stmtstaticpost");
		poster.addHeader("Authentication-Type", "2wayssl");
		log("Expected Status Code =#####");
		poster.setExpectedStatusCode(200); // HTTP Status Code
		log("send Statement to patient #####");
		poster.postFromResourceFile("testfiles/" + env + "/statement.txt");


	}
}
