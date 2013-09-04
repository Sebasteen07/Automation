package com.intuit.ihg.product.practice.utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Set;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;

public class PracticeUtil extends IHGUtil implements Runnable{

	public static String[] exeArg = null;
	public static int timeout = 0;

	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Dimension screenResolution = new Dimension(
			(int) toolkit.getScreenSize().getWidth(), 
			(int) toolkit.getScreenSize().getHeight() );

	Dimension halfWidthscreenResolution = new Dimension(
			(int) toolkit.getScreenSize().getWidth() / 2, 
			(int) toolkit.getScreenSize().getHeight() );

	protected WebDriver driver;

	public PracticeUtil(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	/**
	 * @author bbinisha
	 * Sets frame for Practice portal pages. This is typically Wicket pages.
	 * @param driver
	 */
	public static void setPracticeFrame(WebDriver driver) {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver,"iframebody");
	}

	/**
	 * @author bbinisha
	 * @Description : Set the Arguments
	 * @param args
	 */
	public void setExeArg(String[] args)
	{
		PracticeUtil.exeArg=null;
		PracticeUtil.exeArg=args;
		try
		{
			timeout=Integer.parseInt(PracticeUtil.exeArg[PracticeUtil.exeArg.length-1]);
		}
		catch(NumberFormatException nfe)
		{
			timeout=15000;
		}
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
	 * @description : Used to run the Autoit IT command in the command prompt.
	 * @return void
	 */ 
	public void run() {
		// TODO Auto-generated method stub
		String command="";
		for(int i=0;i<PracticeUtil.exeArg.length;i++)
		{
			if(i<PracticeUtil.exeArg.length)
			{
				if(PracticeUtil.exeArg[i].contains(" "))
				{
					PracticeUtil.exeArg[i]="\""+PracticeUtil.exeArg[i]+"\"";
				}
				command+=PracticeUtil.exeArg[i]+" ";
			}
			else
			{
				if(PracticeUtil.exeArg[i].contains(" "))
				{
					PracticeUtil.exeArg[i]="\""+PracticeUtil.exeArg[i]+"\"";
				}
				command+=PracticeUtil.exeArg[i];
			}
		}   
		try {
			Thread.sleep(timeout);
			ReadFilePath path=new ReadFilePath();

			Runtime.getRuntime().exec(path.getFilepath("AutoIT")+File.separator+command);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void tabBrowsing(int n) throws AWTException, InterruptedException {
		Robot rb = new Robot();
		Thread.sleep(3000);
		for ( int i=0 ; i<n ; i++) {
			rb.keyPress(KeyEvent.VK_TAB);
			rb.keyRelease(KeyEvent.VK_TAB);
		}
	}

	public void pressEnterKey() throws AWTException, InterruptedException {
		Robot rb = new Robot();
		Thread.sleep(5000);		
		rb.keyPress(KeyEvent.VK_ENTER);
		rb.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(5000);
	}

	public void pressDownKey() throws AWTException, InterruptedException {
		Robot rb = new Robot();
		rb.keyPress(KeyEvent.VK_DOWN);
		rb.keyRelease(KeyEvent.VK_DOWN);
		Thread.sleep(2000);
	}

	/**
	 * @author :bbinisha
	 * Description: This method switches the driver control to the print pop up window
	 * @throws InterruptedException
	 */
	public void switchToNewWindow() throws InterruptedException {
		Thread.sleep(2000);
		Set<String> availableWindows = driver.getWindowHandles();
		Object[] ls = availableWindows.toArray();
		driver.switchTo().window((String) ls[1]);
		Thread.sleep(2000);
	}

	public void tabBack(int n) throws AWTException, InterruptedException {
		Robot rb = new Robot();
		Thread.sleep(3000);
		for ( int i=0 ; i<n ; i++) {
			rb.keyPress(KeyEvent.VK_SHIFT);
			rb.keyPress(KeyEvent.VK_TAB);
			Thread.sleep(3000);
			rb.keyRelease(KeyEvent.VK_SHIFT);
			rb.keyRelease(KeyEvent.VK_TAB);
		}
	}

}
