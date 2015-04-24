package com.medfusion.rcm.utils;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ifs.csscat.core.utils.BrowserTypeUtil.BrowserType;
import com.intuit.ihg.common.utils.IHGUtil;
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

	public void postStatementToPatient(String rcmStatementRest,String env) throws Exception {

		IHGUtil.PrintMethodName();

		WebPoster poster = new WebPoster();
				
		Assert.assertNotNull( 
				"### Test property rcmStatementRest not defined", 
				rcmStatementRest);
		poster.setServiceUrl( rcmStatementRest.trim() );
		
		poster.setContentType( "application/xml;" );
		poster.addHeader( "requestId", "3ab8ce87-5725-4849-ab03-7e03b5912b3f" );
		poster.addHeader( "Authentication-Type", "2wayssl" );
		log("Expected Status Code =#####");
		poster.setExpectedStatusCode( 202 );	// HTTP Status Code
		log("send Statement to patient #####");
		poster.postFromResourceFile( 
					"testfiles/" 
					+ env
					+ "/statement.xml" );
			
		log("sleep(10000) :- Need time to let system process the Statement #####");
		Thread.sleep(10000); 
				
		
	}
}
