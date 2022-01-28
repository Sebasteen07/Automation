//Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.phr.utils;

import static org.testng.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.openqa.selenium.WebDriver;

import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ifs.csscat.core.utils.BrowserTypeUtil.BrowserType;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.intuit.ihg.common.utils.WebPoster;
import com.medfusion.pojos.CcdType;

public class PhrUtil extends IHGUtil {

	protected WebDriver driver;


	public static int timeout = 0;
	public static String[] exeArg = null;

	public PhrUtil(WebDriver driver) {
		super(driver);
	}

	public WebDriver getDriver(WebDriver driver) {
		IHGUtil.PrintMethodName();
		return driver;
	}

	/*
	 * 
	 */
	public static void DeleteAllBrowsingDataIE(WebDriver driver) throws Exception {

		if (TestConfig.getBrowserType() == BrowserType.iexplore) {

			// driver.navigate().to("");

			System.out.println("Browser is IE");

			Runtime rt = Runtime.getRuntime();
			// Clear temporary Internet files
			Process proc8 = rt.exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 8");
			proc8.waitFor();
			// Clear Cookies
			Process proc2 = rt.exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 2");
			proc2.waitFor();
			// Clear History
			Process proc1 = rt.exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 1");
			proc1.waitFor();

			// Delete ALL
			Process proc255 = rt.exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 255");
			proc255.waitFor();

			// Delete Password
			Process proc32 = rt.exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 32");
			proc32.waitFor();

			// Delete Form data
			Process proc16 = rt.exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 16");
			proc16.waitFor();

		}

		else
			System.out.println("Browser is NOT IE");
	}

	public void ccdImportFromAllScripts(CcdType ccdType, String allScriptAdapterURL, String env) throws Exception {

		IHGUtil.PrintMethodName();

		WebPoster poster = new WebPoster();

		assertNotNull("### Test property PHR_ALLSCRIPTS_ADAPTER_URL not defined", allScriptAdapterURL);

		log("Before setting the allscript adapter url#####");
		poster.setServiceUrl(allScriptAdapterURL.trim());

		log("Before setting the content type#####");
		poster.setContentType("text/xml;charset=UTF-8");

		log("Before setting Expected codee#####");
		poster.setExpectedStatusCode(200); // HTTP Status Code

		log("Add headers #####");
		poster.addHeader("Noun", "Encounter");
		poster.addHeader("Verb", "Completed");

		if (ccdType == CcdType.CONSOLIDATED_CCD) {

			log("send consolidated CCD #####");
			poster.postFromResourceFile("testfiles/" + env + "/ccd/C-ccd.xml");

			log("sleep(10000) :- Need time to let system process CCD #####");
			Thread.sleep(10000);

		} else if (ccdType == CcdType.NON_CONSOLIDATED_CCD) {

			log("send non consolidated CCD. Sharing with another doctor is not possible for non C-CCD #####");
			poster.postFromResourceFile("testfiles/" + env + "/ccd/NonC-ccd.xml");
			log("sleep(10000) :- Need time to let system process CCD #####");
			Thread.sleep(10000);
		}


	}

	public void ccdImportFromElekta(CcdType ccdType, String EHDCAdapterURL, String env) throws Exception {

		IHGUtil.PrintMethodName();

		WebPoster poster = new WebPoster();

		assertNotNull("### Test property PHR_EHDC_ADAPTER_URL not defined", EHDCAdapterURL);

		log("Before setting the EHDC adapter url#####");
		poster.setServiceUrl(EHDCAdapterURL.trim());

		log("Before setting the content type#####");
		poster.setContentType("application/xml;");

		log("Before setting Expected codee#####");
		poster.setExpectedStatusCode(202); // HTTP Status Code

		log("Add headers #####");
		poster.addHeader("ExternalSystemId", "79");
		poster.addHeader("Authentication-Type", "2wayssl");

		if (ccdType == CcdType.ELEKTA_CCD) {

			log("send Elekta CCD #####");
			poster.postFromResourceFile("testfiles/" + env + "/ccd/Elekta-ccd.xml");

			log("sleep(10000) :- Need time to let system process CCD #####");
			Thread.sleep(10000);

		}


	}

	public static void ccdImportFromElekta(PropertyFileLoader testData, String token) throws Exception {

		IHGUtil.PrintMethodName();		
		
		WebPoster poster = new WebPoster();

		assertNotNull("### Test property PHR_EHDC_ADAPTER_URL not defined", testData.getProperty("EHDCAdapterURL"));
		poster.setServiceUrl(testData.getProperty("EHDCAdapterURL"));
		poster.setContentType("application/xml;");
		poster.setExpectedStatusCode(202); // HTTP Status Code
		poster.addHeader("ExternalSystemId", testData.getProperty("elektaSystemId"));
		poster.addHeader("Authorization", "bearer " + token);		
		poster.postFromResourceFile("testfiles/" + IHGUtil.getEnvironmentType().toString() + "/ccd/Elekta-ccd.xml");
		System.out.println("sleep(10000) :- Need time to let system process CCD #####");
		Thread.sleep(10000);
	}

	public String getFilepath(String directoryName) throws Exception {

		String filePath = "";
		File targetDataDrivenFile = null;
		targetDataDrivenFile =
				new File(TestConfig.getTestRoot() + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + directoryName);

		// To extract the excel sheet from the jar and use it

		if (targetDataDrivenFile.exists()) {
			filePath = String.valueOf(targetDataDrivenFile.toString() + File.separator).trim();
		} else {
			new File(targetDataDrivenFile.getParent() + "/" + directoryName + "/").mkdirs();
			File destination = new File(targetDataDrivenFile.getParent() + "/" + directoryName + "/");
			copyResourcesRecursively(super.getClass().getResource("/" + directoryName + "/"), destination);
			filePath = String.valueOf(destination.toString()).trim();

		}
		return filePath;
	}

	public static boolean copyResourcesRecursively(final URL originUrl, final File destination) {
		try {
			final URLConnection urlConnection = originUrl.openConnection();
			if (urlConnection instanceof JarURLConnection) {
				return ReadFilePath.copyJarResourcesRecursively(destination, (JarURLConnection) urlConnection);
			} else {
				return ReadFilePath.copyFilesRecusively(new File(originUrl.getPath()), destination);
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String[] getExeArg() {
		return exeArg;
	}
 


}
