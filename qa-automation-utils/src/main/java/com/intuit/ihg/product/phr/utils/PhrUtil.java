package com.intuit.ihg.product.phr.utils;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ifs.csscat.core.utils.BrowserTypeUtil.BrowserType;
import com.intuit.ihg.common.entities.CcdType;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.WebPoster;

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
			
		//driver.navigate().to("");
		
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
		
		//Delete ALL
		Process proc255 = rt.exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 255");
		proc255.waitFor();
		
		//Delete Password
		Process proc32 = rt.exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 32");
		proc32.waitFor();
		
		//Delete Form data
		Process proc16 = rt.exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 16");
		proc16.waitFor();
			
		}
		
		else
		System.out.println("Browser is NOT IE");
	}
		
	
	  /**
	   * 
	   * //////////////////////////////////////
		// REFERENCE: 
		// http://ihgportal.corp.intuit.net/engineering/Engineering%20Wiki/CCD%20Import%20-%20Test%20Procedure.aspx

		//////////////////////////////////////////////////
		// REST EASY
	   * 
	   *  
	   * @param ccdType
	   * @param allScriptAdapterURL
	   * @param env
	   * @throws Exception
	   */

	public void ccdImportFromAllScripts(CcdType ccdType,String allScriptAdapterURL,String env) throws Exception {

		IHGUtil.PrintMethodName();

		WebPoster poster = new WebPoster();
				
		Assert.assertNotNull( 
				"### Test property PHR_ALLSCRIPTS_ADAPTER_URL not defined", 
				allScriptAdapterURL);
			
		log("Before setting the allscript adapter url#####");
		poster.setServiceUrl( allScriptAdapterURL.trim() );
		
		log("Before setting the content type#####");
		poster.setContentType( "text/xml;charset=UTF-8" );

		log("Before setting Expected codee#####");
		poster.setExpectedStatusCode( 200 );	// HTTP Status Code

		log("Add headers #####");
		poster.addHeader( "Noun", "Encounter" );
		poster.addHeader( "Verb", "Completed" );

		if(ccdType == CcdType.CONSOLIDATED_CCD){
			
			log("send consolidated CCD #####");
			poster.postFromResourceFile( 
					"testfiles/" 
					+ env
					+ "/ccd/C-ccd.xml" );
			
			log("sleep(10000) :- Need time to let system process CCD #####");
			Thread.sleep(10000); 
			
		}
		else if (ccdType == CcdType.NON_CONSOLIDATED_CCD){
			
			log("send non consolidated CCD. Sharing with another doctor is not possible for non C-CCD #####");
			poster.postFromResourceFile( 
					"testfiles/" 
					+ env
					+ "/ccd/NonC-ccd.xml" );
			log("sleep(10000) :- Need time to let system process CCD #####");
			Thread.sleep(10000); 
			}

		
	}
	
	/**
	 * @author bbinisha
	 * @Description : To get the filepath.
	 * @param directoryName
	 * @return
	 * @throws Exception
	 */
	public String getFilepath (String directoryName ) throws Exception {

		String filePath="";
		File targetDataDrivenFile = null;
		targetDataDrivenFile = new File(TestConfig.getTestRoot() 
				+ File.separator + "src" 
				+ File.separator + "test" 
				+ File.separator + "resources" 
				+ File.separator + directoryName);

		// To extract the excel sheet from the jar and use it         

		if (targetDataDrivenFile.exists())
		{
			filePath = String.valueOf(targetDataDrivenFile.toString()+File.separator).trim();
		}
		else {
			new File(targetDataDrivenFile.getParent()+"/"+directoryName+"/").mkdirs();
			File destination=new File(targetDataDrivenFile.getParent()+"/"+directoryName+"/");
			copyResourcesRecursively(super.getClass().getResource("/"+directoryName+"/"),destination);
			filePath = String.valueOf(destination.toString()).trim();

		} 
		return filePath;
	}
	
	
	/**
	 * @author bbinisha
	 * @Description Copy the content of Resource in the Jar files from a source to the destination directory recursively
	 * @param originUrl
	 * @param destination
	 * @return Returns true if all files are moved else returns false
	 */
	public static boolean copyResourcesRecursively( 
			final URL originUrl, final File destination) {
		try {
			final URLConnection urlConnection = originUrl.openConnection();
			if (urlConnection instanceof JarURLConnection) {
				return ReadFilePath.copyJarResourcesRecursively(destination,
						(JarURLConnection) urlConnection);
			} else {
				return ReadFilePath.copyFilesRecusively(new File(originUrl.getPath()),
						destination);
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @author bbinisha
	 * @Description : Get the Arguments.
	 * @return
	 */
	public String[] getExeArg()
	{
		return exeArg;
	}
	
	
	
	
}
