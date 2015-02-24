package com.intuit.ihg.common.utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.Locale;

import org.apache.log4j.Level;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ihg.common.entities.CcdType;
import com.intuit.ihg.common.utils.EnvironmentTypeUtil.EnvironmentType;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ifs.csscat.core.wait.WaitForWEIsDisplayedEnabled;

/**
 * Description : IHG Util will contain the methods for utilities those are specific to IHG.
 * @author BALA
 *
 */
public class IHGUtil extends BasePageObject {


	public static Properties properties = new Properties();

	public IHGUtil(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}


	/**
	 * Description: Concatenates Multiple lists
	 * @param <myList> : Multiple lists with comma (,) separation
	 * @param first
	 * @param rest
	 * @return <myList> : Concatenated list
	 */
	public static <myList> myList[] concatAllLists(myList[] first, myList[]... rest) {
		int totalLength = first.length;
		for (myList[] array : rest) {
			totalLength += array.length;
		}
		myList[] result = Arrays.copyOf(first, totalLength);
		int offset = first.length;
		for (myList[] array : rest) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}


	/**
	 * Description : verify if a element is present or not.
	 * Don't use findElement() statement as the parameter.
	 * @param testElement : By
	 * @return boolean : True if the element is Rendered in the page.
	 */
	public boolean isRendered(By by) {
		boolean bRendered = false;
		try {
			if (driver.findElement(by).isDisplayed()) {
				bRendered = true;
			}
		} catch (Exception e) {

		}

		return bRendered;
	}


	/**
	 * Description : verify if a element is present or not.
	 * @param we :-WebElement
	 * @return True if the element is Rendered in the page.
	 */
	public boolean isRendered(WebElement we) {
		boolean bRendered = false;
		try {
			if (we.isDisplayed()) {
				bRendered = true;
			}
		} catch (Exception e) {
			log(we + " was not found.");

		}

		return bRendered;
	}


	/**
	 * Description : This method will return true if the webElemnt exists in the page. 
	 * This method calls exists (By by, long maxTimeInSecondsToWait) with a default wait period of 2 seconds
	 * @param webelements : example "sdputil.exists(By.xpath("//input[@name='email']"))"
	 * @return boolean
	 */
	public boolean exists(By by) {
		long maxTimeInSecondsToWait = 2;
		return this.exists(by, maxTimeInSecondsToWait);
	}


	/**
	 * Will return true if Element exist else false
	 * @param element :- WebElement
	 * @return boolean
	 */

	public boolean exists(WebElement element) {
		try {
			Actions builder = new Actions(driver);
			builder.moveToElement(element).build().perform();
			Point p = element.getLocation();
			log("Where on the page is the top left-hand corner of the rendered element"+p);
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	/**
	 * Desc:-Will fisrt wait implicitly then will check if element exist or not . Will return true if Element exist else false
	 * @param element
	 * @param maxTimeInSecondsToWait
	 * @return
	 */

	public boolean exists(WebElement element, long maxTimeInSecondsToWait) {
		boolean bexists = false;
		try {
			driver.manage().timeouts().implicitlyWait(maxTimeInSecondsToWait, TimeUnit.SECONDS);
			Actions builder = new Actions(driver);
			builder.moveToElement(element).build().perform();
			Point p = element.getLocation();
			log("Where on the page is the top left-hand corner of the rendered element"+p);
			bexists = true;
		} catch (Exception e) {
			log("Element was not found.");
		}

		finally {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		return bexists;
	}

	/**
	 * Description : This method will return true if the webelemnt exists in the page. User has to provide the 
	 * element as webelements for example  to test if the element with xpath "//input[@name='email']" is present or not 
	 * user has to write the code like "sdputil.exists(By.xpath("//input[@name='email']"))"
	 * The function will wait till the element is displayed or till the timeout which ever comes first.
	 * 
	 * @param webelements
	 * @param maxTimeInSecondsToWait : timeout for the function. Max time to wait.
	 * @return boolean
	 */
	public boolean exists(By by, long maxTimeInSecondsToWait) {
		boolean bexists = false;

		List<WebElement> webelements;
		try {
			driver.manage().timeouts().implicitlyWait(maxTimeInSecondsToWait, TimeUnit.SECONDS);
			webelements = driver.findElements(by);

			if (webelements.size() > 0) {
				bexists = true;
				//log ("element "+by+" found");
			}
		}

		catch (Exception e) {
			log("Element was not found.");
		}

		finally {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		return bexists;

	}


	/**
	 * Description : This method converts the string to list provided the delimiter
	 * @param inputString String 
	 * @param dlimiter String
	 * @return List
	 * @throws Exception
	 */
	public List<String> convertStringToList(String inputString, String dlimiter) throws Exception {

		List<String> NewList = new ArrayList<String>();

		String[] pieces = inputString.split(dlimiter);
		// 		for (int i = pieces.length - 1; i >= 0; i--) {
		// 		pieces[i] = pieces[i].trim();
		// 		NewList.add(pieces[i]);
		// 		}
		for (int i = 0; i <= pieces.length - 1; i++) {
			pieces[i] = pieces[i].trim();
			NewList.add(pieces[i]);
		}
		return NewList;

	}


	/**Description : Method to get the Current Date in yyyy-MMM-dd format
	 * @return
	 * @throws Exception
	 */
	public static String getCurrentDate() throws Exception {
		DateFormat dfDate = new SimpleDateFormat("yyyy-MMMM-dd");
		Date today = Calendar.getInstance().getTime();
		String currentDate = dfDate.format(today);
		return currentDate;
	}

	/**
	 * Description : Method to get the Current Date in required Format,
	 * user has to provide the date format i.e "MM/dd/yyyy", dd/MM/yyyy etc
	 * @param typeOfDateFormat [String] 
	 * @return
	 * @throws Exception
	 */
	public static String getFormattedCurrentDate(String typeOfDateFormat) throws Exception {
		Date today = Calendar.getInstance().getTime();
		DateFormat dfDate = new SimpleDateFormat(typeOfDateFormat);
		String formattedCurrentDateTime = dfDate.format(today);
		int Len = formattedCurrentDateTime.length();
		System.out.println("***Before Modification ***" + formattedCurrentDateTime);
		if (typeOfDateFormat.equals("dd")) {
			if (formattedCurrentDateTime.charAt(0) == '0') {
				formattedCurrentDateTime = formattedCurrentDateTime.substring(1);
			}
		}

		else if (typeOfDateFormat.equals("MMMMM dd, yyyy hh:mm")) {
			// This Modification is required for Event Logger as it displays time like 9:51:23 instead 09:51:23, this removes 0 in front of 9
			if (Len > 10 && formattedCurrentDateTime.charAt(Len - 5) == '0') {
				String str1 = formattedCurrentDateTime.substring(0, (Len - 5));
				String str2 = formattedCurrentDateTime.substring((Len - 4), Len);
				formattedCurrentDateTime = str1.concat(str2);
				System.out.println("***After Modification ***" + formattedCurrentDateTime);
			}
		}

		else if (typeOfDateFormat.equals("MM/dd/yyyy hh:mm")) {
			String eventDate = formattedCurrentDateTime.substring(0, (Len - 6));
			String eventTime = formattedCurrentDateTime.substring((Len - 5), Len);
			if (eventTime.charAt(0) == '0') {
				eventTime = eventTime.substring(1);
			}
			//String eventPeriod = formattedCurrentDateTime.substring(17);
			formattedCurrentDateTime = eventDate + " " + "@" + " " + eventTime;
			System.out.println("***After Modification ***" + formattedCurrentDateTime);
		}

		return formattedCurrentDateTime;
	}


	/**
	 * Description : Method to add or subtract days from current date
	 * @param dateFormat [String] date format i.e "MM/dd/yyyy", dd/MM/yyyy etc
	 * @param days [int] number of days i.e. -7, 15
	 * @return
	 * @throws Exception
	 */
	public static String getRequiredDate(String dateFormat, int days) throws Exception {
		DateFormat dfDate = new SimpleDateFormat(dateFormat);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days); //Subtracting 1 day to current date
		String newdate = dfDate.format(cal.getTime());

		if (newdate.charAt(0) == '0') {
			newdate = newdate.substring(1);
		}
		return newdate;
	}



	/** Method to convert from one date format to another	 * 
	 * @throws Exception
	 */
	public static String convertDate(String srcDate, String srcDateFormat, String destDateformat) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat(srcDateFormat);
		Date dateStr = formatter.parse(srcDate);
		formatter = new SimpleDateFormat(destDateformat);
		String FormattedDate = formatter.format(dateStr);
		System.out.println("FormattedDate" + FormattedDate);
		return FormattedDate;
	}



	/** Method to get the current date and time in yyyyMMMMddhhmmss format
	 * @return
	 * @throws Exception
	 */
	public static String getCurrentDateTime() throws Exception {
		SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyyMMMMddhhmmss");
		Date now = Calendar.getInstance().getTime();
		String currentDateTime = sdfDateTime.format(now);
		return currentDateTime;
	}



	/**
	 * Description : This Method will return the difference between two dates in days 
	 * @param a [Date]
	 * @param b [Date]
	 * @return
	 */
	public static int calculate_Date_Difference(Date a, Date b) {
		int tempDifference = 0;
		int difference = 0;
		Calendar earlier = Calendar.getInstance();
		Calendar later = Calendar.getInstance();

		if (a.compareTo(b) < 0) {
			earlier.setTime(a);
			later.setTime(b);
		} else {
			earlier.setTime(b);
			later.setTime(a);
		}

		while (earlier.get(Calendar.YEAR) != later.get(Calendar.YEAR)) {
			tempDifference = 365 * (later.get(Calendar.YEAR) - earlier.get(Calendar.YEAR));
			difference += tempDifference;

			earlier.add(Calendar.DAY_OF_YEAR, tempDifference);
		}

		if (earlier.get(Calendar.DAY_OF_YEAR) != later.get(Calendar.DAY_OF_YEAR)) {
			tempDifference = later.get(Calendar.DAY_OF_YEAR) - earlier.get(Calendar.DAY_OF_YEAR);
			difference += tempDifference;

			earlier.add(Calendar.DAY_OF_YEAR, tempDifference);
		}

		return difference;
	}



	/**
	 * Description : executes a java script when ever is needed. some times we need to execute a Js to 
	 * for example to execute the js "top.body.Ext.getCmp('dateRangeMenu').items.get('defaultDateRangeButton').disabled"
	 * @param javascript
	 * @return
	 * @throws Exception
	 */
	public Object executeJavascript(String javascript) throws Exception {

		JavascriptExecutor js = (JavascriptExecutor) driver;

		return js.executeScript("return " + javascript + "");

	}

	/**
	 * Description: Finds the position of the string in the Array & returns it. 
	 * @param listToSearchFrom: List from which the item is to be searched.
	 * @param searchItem: Item that has to be searched in the list.
	 * @return Index: Position of the String in the array. Null if the string is not found in the array.
	 */
	public Integer findItemInList(String[] listToSearchFrom, String searchItem) {
		for (int n = 0; n < listToSearchFrom.length; n++) {
			if (listToSearchFrom[n].equals(searchItem))
				return n;
		}
		return null;

	}


	/**
	 * Generates a partial xpath expression that matches an element whose 'class' attribute
	 * contains the given CSS className. So to match &lt;div class='foo bar'&gt; you would
	 * say 
	 *   "//div[" + SDPUtil.xPathHasClass("foo") + "]" 
	 * or 
	 *   String.format("//div[%s]", SDPUtil.xPathHasClass("foo"))
	 * 
	 * From http://pivotallabs.com/users/alex/blog/articles/427-xpath-css-class-matching
	 *
	 * @param className CSS class name
	 * @return XPath fragment, e.g., contains(concat(' ',normalize-space(@class),' '),' foo ')
	 */
	public static String xPathHasClass(String className) {
		return String.format("contains(concat(' ',normalize-space(@class),' '),' %s ')", className);
	}


	/**
	 * Finds all the elements that have the given class from a parent element
	 * 
	 * @param parentEl element under which to search
	 * @param cssClass CSS class name to match
	 * @return all elements under the given element which have the given CSS class
	 */
	public static List<WebElement> getElementsByClass(WebElement parentEl, String cssClass) {
		return parentEl.findElements(By.xpath(String.format(".//*[%s]", xPathHasClass(cssClass))));
	}


	/**
	 * Check to see whether a WebElement object has a particular css class.
	 * This normalizes the class attribute so that the class name can be found.
	 * Elements may have multiple css classes; ensure that "foo" can be found in
	 * class="foo" or class="foo bar baz"
	 * 
	 * @param el		The WebElement that we want to check has a particular class
	 * @param cssClass	The CSS class we want to check in the WebElement
	 * @return whether the given element has the given CSS class
	 */
	public static boolean hasClass(WebElement el, String cssClass) {
		return ((" " + el.getAttribute("class") + " ").contains(" " + cssClass + " "));
	}


	/**
	 * Interface to create a condition that can be checked against.
	 * 
	 * @see IHGUtil#checkForCondition(Condition, long)
	 */
	public interface Condition {
		public boolean met();
	}


	/**
	 * Waits for a condition to be met and will keep waiting until the condition
	 * is met or the timeout has elapsed.
	 * 
	 * @param bySelector	The by selector for the element we're checking for
	 * @param timeout		The amount of time to wait in milliseconds
	 * @return if the condition is met before the timeout expires
	 * @throws InterruptedException
	 */
	private boolean checkForCondition(Condition condition, long timeout) throws InterruptedException {
		long remainingTime = timeout;
		long decrementingValue = 500;
		while (!condition.met() && remainingTime > 0) {
			Thread.sleep(decrementingValue);
			remainingTime -= decrementingValue;
		}
		return condition.met();
	}


	/**
	 * Check to see if an element is nonexistent, and wait until it is until the timeout expires.
	 * 
	 * @param bySelector	The by selector for the element we're checking for
	 * @param timeout		The amount of time to wait in milliseconds
	 * @return if the element exists before the timeout expires
	 * @throws InterruptedException
	 */
	public boolean elementIsNonexistent(final By bySelector, long timeout) throws InterruptedException {
		return checkForCondition(new Condition() {
			public boolean met() {
				try {
					driver.findElement(bySelector);
				} catch (NoSuchElementException e) {
					// This is desired - we want the element to not be found
					return true;
				}
				return false;
			}
		}, timeout);
	}


	/**
	 * Check to see if an element is hidden, and wait until it's hidden until the timeout expires.
	 * 
	 * @param bySelector	The by selector for the element we're checking is hidden
	 * @param timeout		The amount of time to wait in milliseconds
	 * @return if the element is displayed before the timeout expires
	 * @throws InterruptedException
	 */
	public boolean elementIsHidden(final By bySelector, long timeout) throws InterruptedException {
		return checkForCondition(new Condition() {
			public boolean met() {
				WebElement element = driver.findElement(bySelector);
				return !element.isDisplayed();
			}
		}, timeout);
	}


	/**
	 * Description : This method will update the value of a property in the testconfigproperties.java.  
	 * The property value will only be updated in memory, the contents of the testConfig.properties file will not be modified.
	 * @param Properties [object], propertyname [String], propertyValue[String]
	 * @return  boolean
	 * @throws Exception
	 * Usage : setPropertyValue(properties, "sdp.applicationType", "IB")
	 */
	public static boolean setPropertyValue(Properties properties, String propertyName, String propertyValue) throws Exception {
		boolean setValue = true;
		String PROP_FILE = null;
		String env_testConfig = System.getProperty("testConfigFile");
		String testRoot = System.getProperty("user.dir");

		if (env_testConfig != null)
			PROP_FILE = env_testConfig.trim();
		else 
			PROP_FILE = "testConfig.properties";

		try {
			FileInputStream in = new FileInputStream(testRoot + File.separator + PROP_FILE);
			properties.load(in);

			if (properties.setProperty(propertyName, propertyValue).equals(null))
				setValue = false;

			//properties.store(new FileOutputStream(testRoot + File.separator + PROP_FILE), null);
			in.close();
		}

		catch (IOException e) {
			System.err.println("Failed to read from " + testRoot + File.separator + PROP_FILE + " file.");
			setValue = false;
		}

		return setValue;

	}



	/**
	 * Description : This method will update the value of a property in the testconfigproperties.java.  
	 * The property value will only be updated in memory, the contents of the testConfig.properties file will not be modified.
	 * @param Properties [object], firstPropertyname [String], firstPropertyValue[String], secondPropertyname [String], secondPropertyValue[String]
	 * @return  boolean
	 * @throws Exception
	 * Usage : setPropertyValue(properties, "sdp.applicationType", "IB", "firefox.assumeUntrustedCertificateIssuer", "true")
	 */
	public static boolean setPropertyValue(Properties properties, String firstPropertyName, String firstPropertyValue, String secondPropertyName, String secondPropertyValue) throws Exception {
		boolean setValue = true;
		String PROP_FILE = null;
		String env_testConfig = System.getProperty("testConfigFile");
		String testRoot = System.getProperty("user.dir");

		if (env_testConfig != null)
			PROP_FILE = env_testConfig.trim();
		else 
			PROP_FILE = "testConfig.properties";

		try {
			FileInputStream in = new FileInputStream(testRoot + File.separator + PROP_FILE);
			properties.load(in);

			if (properties.setProperty(firstPropertyName, firstPropertyValue).equals(null))
				setValue = false;

			if (properties.setProperty(secondPropertyName, secondPropertyValue).equals(null))
				setValue = false;

			//properties.store(new FileOutputStream(testRoot + File.separator + PROP_FILE), null);
			in.close();
		}

		catch (IOException e) {
			System.err.println("Failed to read from " + testRoot + File.separator + PROP_FILE + " file.");
			setValue = false;
		}

		return setValue;

	}





	/**
	 * Generate a decimal string representation of a random number within the
	 * supplied bounds.
	 * 
	 * @param random
	 *            the random object (if null, a new one will be created)
	 * @param lowerBound
	 *            the lower bound, inclusive
	 * @param upperBound
	 *            the upper bound, inclusive
	 * @param decimalPlaces
	 *            the decimal places of the result
	 * @return the formatted string
	 */
	public static String getRandomValue(final Random random,
			final int lowerBound,
			final int upperBound,
			final int decimalPlaces){

		if(lowerBound < 0 || upperBound <= lowerBound || decimalPlaces < 0){
			throw new IllegalArgumentException("Put error message here");
		}

		final double dbl =
			((random == null ? new Random() : random).nextDouble() //
					* (upperBound - lowerBound))
					+ lowerBound;
		return String.format("%." + decimalPlaces + "f", dbl);

	}

	//Overloading existing Dates_in_Range method with custom date range as parameter
	/**
	 * Description : This Method will return if the list of dates are in range or not 
	 * for example if we want to check if a date list from NHP is with in range of custom dates. This Takes in input as List of string 
	 * that we get from the NHP page, removes the pending transactions and verify 
	 * @author Chiranjeev
	 * @param dateList [List<String>]
	 * @param range [int]
	 * @return 
	 * @return [boolean]
	 * @throws Exception
	 */
	public boolean Dates_in_Range(List<String> dateList, String fromDate, String toDate) throws Exception {
//		IHGUtil sdputil = new IHGUtil(driver);
		boolean bret = true;

		for (String sdate : dateList) {
			if (!sdate.equals("Pending")) {
				DateFormat formatter;
				formatter = new SimpleDateFormat("MM/dd/yyyy");
				Date date1 = (Date) formatter.parse(sdate);
				//log (""+date1);

				Date fdate = (Date) formatter.parse(fromDate);
				Date tdate = (Date) formatter.parse(toDate);


				// Check if date in between				
				bret = date1.equals(fdate)||date1.equals(tdate)||(date1.after(fdate)&&date1.before(tdate));

				if (!bret) {
					log("**WARN : Date {" + date1 + "} is not in between {" + fromDate + "} and {"+toDate+"}");
				}

			}
		}

		return bret;

	}


	public static List<String> JoinLists (String delim, List<List<String>> lls){
		List<String> retList = new ArrayList<String>();
		int listsize = lls.get(0).size();
		int totallist = lls.size();
		String str = "";
		for(int i=0;i<listsize;i++){
			str = "";
			for(int j=0;j<totallist;j++){
				str = str+delim+lls.get(j).get(i);
			}
			retList.add(i,str);
		}
		return retList;
	}

	/**
	 * Checking if the String list is sorted or not
	 * @param ls
	 * @return
	 */
	public boolean isListSorted(List<String> ls){
		Boolean retValue = false;
		List<String> ls1 = ls;
		Collections.sort(ls1);
		if (ls.equals(ls1)){
			retValue = true;
		}
		return retValue;
	}

	/**
	 * Webdriver  :-switchTo differnt window
	 * @throws InterruptedException
	 */
	public void switchToPrintPopUp(int n) throws InterruptedException {
		Set<String> availableWindows = driver.getWindowHandles();
		Object[] ls = availableWindows.toArray();
		driver.switchTo().window((String) ls[n]);
		Thread.sleep(2000);
	}

	/**
	 * Robot script for closePrintDialog
	 * @throws Exception
	 */

	public void closePrintDialog() throws Exception{
		log("*****Explicitly waiting for 30 seconds for appearance of Print dialog*****");
		Thread.sleep(30000);
		Robot rb = new Robot();
		rb.keyPress(KeyEvent.VK_ALT);
		rb.keyPress(KeyEvent.VK_F4);
		rb.keyRelease(KeyEvent.VK_F4);
		rb.keyRelease(KeyEvent.VK_ALT);
		Thread.sleep(5000);
	}

	/**
	 * Robot script for pasting content
	 * @throws Exception
	 */

	public void copyContent() throws Exception{
		Thread.sleep(2000);
		Robot rb = new Robot();
		rb.keyPress(KeyEvent.VK_CONTROL);
		rb.keyPress(KeyEvent.VK_A);
		rb.keyRelease(KeyEvent.VK_A);
		rb.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(2000);
		rb.keyPress(KeyEvent.VK_CONTROL);
		rb.keyPress(KeyEvent.VK_C);
		rb.keyRelease(KeyEvent.VK_C);
		rb.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(2000);
	}

	/**
	 * Robot script for copying content
	 * @throws Exception
	 */

	public void pasteContent() throws Exception{
		Thread.sleep(2000);
		Robot rb = new Robot();
		rb.keyPress(KeyEvent.VK_CONTROL);
		rb.keyPress(KeyEvent.VK_V);
		rb.keyRelease(KeyEvent.VK_V);
		rb.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(2000);
	}


	/**
	 * Robot script for copying content
	 * @throws Exception
	 */

	public void closeModaldialog() throws Exception{
		Thread.sleep(2000);
		Robot rb = new Robot();
		rb.keyPress(KeyEvent.VK_ENTER);
		rb.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(2000);
	}


	/**
	 * Print name of calling method.

	 * NOTE: This only works if you call the method directly from a another method with no other method in between.
	 * The index of [2] represents the position of the calling method in the  trace
	 * In this case, that would be the method that directly calls this method.

	 */
	public static void PrintMethodName() {

		System.out.println("###>>> --- METHOD: "
				+ Thread.currentThread().getStackTrace()[2].getClassName()
				+ "."
				+ Thread.currentThread().getStackTrace()[2].getMethodName());
	}


	/**
	 * Wait will ignore instances of NotFoundException that are encountered (thrown) by default 
	 * in the 'until' condition, and immediately propagate all others. 
	 * You can add more to the ignore list by calling ignoring(exceptions to add).
	 * 
	 * @param driver 
	 * @param n :- how much time u want to wait
	 * @param ele :- for which element u want to wait
	 */
	public static boolean waitForElement(WebDriver driver,int n,WebElement ele )
	{
		IHGUtil.PrintMethodName();
		WebDriverWait wait = new WebDriverWait(driver, n);
		boolean found = false;
		try {
			found = wait.until(new WaitForWEIsDisplayedEnabled(ele));
		} catch (Exception e) {
			// 
		}
		return found;
	}

	/**
	 * Wait will ignore instances of NotFoundException that are encountered (thrown) by default 
	 * in the 'until' condition, and immediately propagate all others. 
	 * You can add more to the ignore list by calling ignoring(exceptions to add).
	 * 
	 * @param driver 
	 * @param n :- how much time u want to wait
	 * @param ele :- for which element u want to wait
	 */
	public static boolean waitForElementInDefaultFrame(WebDriver driver,int n,WebElement ele )
	{
		IHGUtil.PrintMethodName();
		driver.switchTo().defaultContent();
		return waitForElement(driver, n, ele);
	}


	/**
	 *author:-bkrishnankutty
	 *Date:-3-4-2013
	 *Desc:- Method will get the value of "test.environment" from testConfig.properties
	 *
	 */
	public static EnvironmentType getEnvironmentType() {
		String env = TestConfig.getUserDefinedProperty("test.environment");

		if (env != null) {
			return EnvironmentTypeUtil.getEnvironmentType(env);
		}
		else {
			return EnvironmentTypeUtil.getEnvironmentType(String.valueOf(
					properties.getProperty("test.environment")).trim());
		}
	}

	/**
	 *author:-vvalsan
	 *Date:-3-22-2013
	 *Desc:- Method will get the value of "test.mail.loop" from testConfig.properties
	 * Default value is set to 5. (Number of retries to check for email)
	 *
	 */
	public static int getTestMailLoop() {
		String loop = TestConfig.getUserDefinedProperty("test.mail.loop");
		try{
			return Integer.parseInt(loop);
		}catch (Exception e){
			//suppress numberformat exception
			System.out.println("NumberFormat Exception "+ e);
		}
		return 15;          //arbitrary default number of tries
	}
	/**
	 *author:-bkrishnankutty
	 *Date:-3-4-2013
	 *Desc:- Method will set the frame.Frist will set to default Content and then to str frame
	 *
	 */
	public static void setFrame(WebDriver driver,String str) {
		IHGUtil.PrintMethodName();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(str); 
	}

	/**
	 * auther:-rperkinsjr
	 * Date:-4-18-2013
	 * Desc:- Some functionality is embedded in multiple iFrames. This method will take a list of
	 * frames and keep switching to them.
	 * @param driver
	 * @param frames list of frames (specified in order from parent first to deepest child)
	 */
	public static void setFrameChain(WebDriver driver, List<String> frames) {
		IHGUtil.PrintMethodName();

		driver.switchTo().defaultContent();
		for (String frame : frames) {
			driver.switchTo().frame(frame);
		}
	}

	/**
	 *author:-bkrishnankutty
	 *Date:-3-4-2013
	 *Desc:- Method will set to default Content
	 *
	 */

	public static void setDefaultFrame(WebDriver driver) {
		IHGUtil.PrintMethodName();
		driver.switchTo().defaultContent();
	}


	/**
	 *author:-bkrishnankutty
	 *Date:June 3, 2013
	 *Desc:- Method give u date in Format("MMMM d, yyyy")
	 *
	 */
	public static String getDate_MMM_d_yyyy(){
		Date now = new Date();
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("PST"));
		String expectedPST = dateFormatGmt.format(now);
		return expectedPST;
	}

	/**
	 *author:-bkrishnankutty
	 *Date:-5-4-2013
	 *Desc:- Method give u date in Format("MMMM")
	 *
	 */

	public static String getDate_Month(){
		Date now = new Date();
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("MMMM", Locale.ENGLISH);
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("PST"));
		String expectedPST = dateFormatGmt.format(now);
		System.out.println("expectedPST"+expectedPST);
		return expectedPST;
	}

	/**
	 *author:-bkrishnankutty
	 *Date:-5-4-2013
	 *Desc:- Method give u date in Format("d")
	 *
	 */
	public static String getDate_d(){
		Date now = new Date();
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("d", Locale.ENGLISH);
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("PST"));
		String expectedPST = dateFormatGmt.format(now);
		System.out.println("expectedPST"+expectedPST);
		return expectedPST;
	}

	/**
	 *author:-bkrishnankutty
	 *Date:-5-4-2013
	 *Desc:- Method give u date in Format("yyyy")
	 *
	 */

	public static String getDate_y(){
		Date now = new Date();
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy", Locale.ENGLISH);
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("PST"));
		String expectedPST = dateFormatGmt.format(now);
		return expectedPST;
	}


	/**
	 *author:-bkrishnankutty
	 *Date:-5-4-2013
	 *Desc:- Method give u date in Format("yyyy")
	 *
	 */

	public static String getEstTiming(){
		Date now = new Date();
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("EST"));
		String expectedPST = dateFormatGmt.format(now);
		return expectedPST;
	}


	/**
	 * return true for Consolidated CCD and false for Non consolidated CCD
	 * 
	 * @return
	 */
	public boolean checkCcdType() throws InterruptedException {
		log("###checking ccd type");
		boolean shareWithDoctorPresent;
		try {
			log("###I am inside try");
			shareWithDoctorPresent = true;
			Thread.sleep(5000);

			driver.findElement(By.linkText("Send my information"));
			log("###this is a consolidated Ccd."
					+ shareWithDoctorPresent);
			return shareWithDoctorPresent;
		} catch (NoSuchElementException e) {
			shareWithDoctorPresent = false;
			log("### this is a non consolidated Ccd.You cannot share with another doctor"
					+ shareWithDoctorPresent);
			return shareWithDoctorPresent;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			shareWithDoctorPresent = false;
			log("### this is a non consolidated Ccd.You cannot share with another doctor"
					+ shareWithDoctorPresent);
			return shareWithDoctorPresent;
		}
	}


	/**
	 * return true for Consolidated CCD 
	 * 
	 * @return
	 */

	public static CcdType getConsolidatedCCD()
	{
		return CcdType.CONSOLIDATED_CCD;
	}


	/**
	 * return true for NONConsolidated CCD 
	 * 
	 * @return
	 */

	public static CcdType getNonConsolidatedCCD()
	{
		return CcdType.NON_CONSOLIDATED_CCD;
	}

	public static String createRandomEmailAddress(String email) {

		IHGUtil.PrintMethodName();

		Random randomNumbers = new Random();

		int rnd = randomNumbers.nextInt(999999999);

		String[] tmp = email.split("@");

		System.out.println("dynamic Email address" + tmp[0] + "+" + rnd + "@"
				+ tmp[1]);

		return tmp[0] + "+" + rnd + "@" + tmp[1];
	}

	public static int createRandomNumber() {

		IHGUtil.PrintMethodName();

		Random randomNumbers = new Random();

		int rnd = randomNumbers.nextInt(999999999);

		return rnd;
	}

	public static String createRandomNumericString() {

		IHGUtil.PrintMethodName();

		Random randomNumbers = new Random();

		int rnd = randomNumbers.nextInt(999999999);

		return ( "" + rnd );
	}
	/**
	 * Description: This method searches for the appropriate case in the case table based on the filtration criteria
	 * @param Xpath of the table,List of case search criteria
	 * @return Row number where the case matching with the search criteria is present
	 */	
	public static List<Object> searchResultTable(WebDriver pDriver,String pTableXpath,ArrayList<String> pMyArr_expected){
		int i=0;
		int rowNumber=0;
		boolean flag=false;
		List<Object> list = new ArrayList<Object>();	
		ArrayList<String> myArr_actual = new ArrayList<String>();
		WebElement table =pDriver.findElement(By.xpath(pTableXpath));
		List<WebElement> rows=table.findElements(By.tagName("tr"));
		mainLoop:for(WebElement row:rows){
			i++;
			myArr_actual.clear();
			List<WebElement> columns = row.findElements(By.tagName("td"));
			for(WebElement column: columns){
				myArr_actual.add(column.getText().toString());				
			}
			for(int j=0;j<pMyArr_expected.size();j++){
				if(!myArr_actual.contains(pMyArr_expected.get(j))){
					flag=false;
					break;	
				}				
				else{
					flag=true;
					Log4jUtil.log (pMyArr_expected.get(j)+"is present in row :"+i, Level.INFO);					
				}				
			}
			if(flag==true){
				rowNumber=i;
				list.add(rowNumber);
				list.add(flag);
				break mainLoop;
			}	
		}
		return list;
	}


	/**
	 * Description: This method searches for the appropriate case in the case table based on the filtration criteria (a substring)
	 * @param Xpath of the table,List of case search criteria
	 * @return Row number where the exact case matching with the search criteria is present
	 */	
	public static List<Object> searchResultsSubstring(WebDriver pDriver,String pTableXpath,ArrayList<String> pMyArr_expected){
		int i=0;
		int rowNumber=0;
		boolean flag=false;
		List<Object> list = new ArrayList<Object>();	
		ArrayList<String> myArr_actual = new ArrayList<String>();
		WebElement table =pDriver.findElement(By.xpath(pTableXpath));
		List<WebElement> rows=table.findElements(By.tagName("tr"));
		mainLoop:for(WebElement row:rows){	
			i++;
			myArr_actual.clear();
			List<WebElement> columns = row.findElements(By.tagName("td"));
			for(WebElement column: columns){
				myArr_actual.add(column.getText().toString());				
			}
			for(int j=0;j<pMyArr_expected.size();j++){
				if(!myArr_actual.contains(pMyArr_expected.get(j))){
					flag=false;
					break;	
				}				
				else{
					flag=true;
					if(myArr_actual.get(2).contains(pMyArr_expected.get(1))){
					     Log4jUtil.log (pMyArr_expected.get(j)+"is present in row :"+i, Level.INFO);  
					     break;		
					     }		
				}				
			}
			if(flag==true){
				rowNumber=i;
				list.add(rowNumber);
				list.add(flag);
				break mainLoop;
			}	
		}
		return list;
	}


	/**
	 * @author:-bkrishnankutty
	 * @Date:- 7-july-2013
	 * @Desc:- Will return u data in EST format
	 * @return :- String
	 * 
	 */
	public static String getEstTimingWithTime() {
		Date now = new Date();
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat();
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("EST"));
		String expectedEST = dateFormatGmt.format(now);
		System.out.println("expectedEST=========================="
				+ expectedEST);
		return expectedEST;
	}

	/**
	 * @author:-bkrishnankutty
	 * @Date:- 7-july-2013
	 * @Description : This Method will return the difference between two dates
	 *              in hours
	 * @param a
	 *            [Date]
	 * @param b
	 *            [Date]
	 * @return
	 */
	public static int calculate_Date_Difference_in_Hours(Date a, Date b) {
		int tempDifference = 0;
		int difference = 0;
		Calendar earlier = Calendar.getInstance();
		Calendar later = Calendar.getInstance();

		if (a.compareTo(b) < 0) {
			earlier.setTime(a);
			later.setTime(b);
		} else {
			earlier.setTime(b);
			later.setTime(a);
		}

		if (earlier.get(Calendar.DAY_OF_YEAR) != later
				.get(Calendar.DAY_OF_YEAR)) {
			tempDifference = later.get(Calendar.DAY_OF_YEAR)
			- earlier.get(Calendar.DAY_OF_YEAR);
			difference += tempDifference;

			earlier.add(Calendar.HOUR_OF_DAY, tempDifference);
		}

		if (earlier.get(Calendar.HOUR_OF_DAY) != later
				.get(Calendar.HOUR_OF_DAY)) {
			tempDifference = later.get(Calendar.HOUR_OF_DAY)
			- earlier.get(Calendar.HOUR_OF_DAY);
			difference += tempDifference;

			earlier.add(Calendar.HOUR_OF_DAY, tempDifference);
		}

		System.out.println("difference================" + difference);
		return difference;
	}

	
	/**
	 * @Description:Pick the Random String
	 * @param array
	 * @return
	 */
	public static String pickRandomString( String[] array ) {

		IHGUtil.PrintMethodName();

		Random r = new Random();

		return array[r.nextInt(array.length)];
	}
	
	
	public boolean isFoundBasedOnCssSelector(final String cssPath ) throws InterruptedException {

		return isFoundBasedOnCssSelector( cssPath,IHGConstants.FIND_ELEMENTS_MAX_WAIT_SECONDS );
	}

	public boolean isFoundBasedOnCssSelector(final String cssPath, int timeOutInSeconds )
	throws InterruptedException {

		IHGUtil.PrintMethodName();

		System.out.println("DEBUG: Looking for cssSelector: " + cssPath);

		return isFoundBy(By.cssSelector(cssPath), timeOutInSeconds );

		/*
		 * // Chrome issue - locks up on findElements // Opera issue - internal
		 * error if thread sleep too short Thread.sleep(
		 * CommonProperties.FIND_ELEMENTS_SLEEP );
		 * 
		 * List<WebElement> elementList = driver.findElements( By.cssSelector(
		 * cssPath ) );
		 */

		/*
		 * List<WebElement> elementList = (new WebDriverWait(driver, 10))
		 * .until(new ExpectedCondition<List<WebElement>>() { public
		 * List<WebElement> apply(WebDriver d) { return
		 * d.findElements(By.cssSelector(cssPath)); } });
		 */

		/*
		 * System.out.println("DEBUG: List<WebElement>: " + elementList);
		 * 
		 * System.out.println("DEBUG: List<WebElement>.size(): " +
		 * elementList.size());
		 * 
		 * return (elementList.size() > 0);
		 */
	}
	
	public boolean isFoundBy(final By by, int timeOutInSeconds ) {

		IHGUtil.PrintMethodName();
		
		driver.manage()
		.timeouts()
		.implicitlyWait( 0, TimeUnit.SECONDS);

		Boolean found = false;
		
		try {

			WebDriverWait wait = new WebDriverWait( driver, timeOutInSeconds );
			
			found = wait.until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					
					// This causes problems for some drivers - need to use plural (findElements) and check size.
					
					// return driver.findElement(by) != null;
					
					List<WebElement> list = driver.findElements(by);
					
					if( list != null ) {
						
						if( list.size() > 0 ) return true;
					}
					
					return false;
				}

			});
			
		} catch (TimeoutException ex) {

			System.out.println("### element not found: " + ex.getMessage());
		}
		
		// Restore implicit wait.
		
		driver.manage()
		.timeouts()
		.implicitlyWait(
				IHGConstants.SELENIUM_IMPLICIT_WAIT_SECONDS,
				TimeUnit.SECONDS);

		return found;
	}

	/**
	 * @Description:Handling print dialog by pressing ESC key
	 */
	public static void hadlePrintDialog() {
		
		Robot r;
		
		try {
			r = new Robot();
			r.keyPress(KeyEvent.VK_ESCAPE);
			r.keyRelease(KeyEvent.VK_ESCAPE);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void printCookies(WebDriver driver) {
		
		Set<Cookie> cookies = driver.manage().getCookies();
		System.out.println("Printing Cookies -------");
		for (Cookie c : cookies) {
			System.out.println(c.toString());
		}
		System.out.println("--------------------------");
	}

    /**
     * A method that will just wait until an element disappears for dynamic pages
     * @param element WebElement that will be disappearing
     * @param periodInMilliseconds determines how long is one waiting cycle
     * @param maxWaitingTimeInSeconds maximal waiting time in seconds
     * @throws InterruptedException, TimeoutException
     */
    public <U> void waitForElementToDisappear(
            U element, long periodInMilliseconds, long maxWaitingTimeInSeconds)
            throws InterruptedException, TimeoutException {

        long sum = 0;
        long maxWaitInMillis = TimeUnit.MILLISECONDS.convert(maxWaitingTimeInSeconds, TimeUnit.SECONDS);

        // while the element exists - be it specified by By class or by WebElement class
        while (By.class.isAssignableFrom(element.getClass()) ? exists((By) element) : exists((WebElement) element)) {
            sum += periodInMilliseconds;
            if (sum > maxWaitInMillis)
                throw new TimeoutException(
                        "Waiting for element to disappear is taking too long and exceeded the limit");

            TimeUnit.MILLISECONDS.sleep(periodInMilliseconds);
        }
    }
    public static void waitForElementByClassAndText(WebDriver driver,final String classToFind, final String textToFind, int secondsToWait){
        WebDriverWait wdw = new WebDriverWait(driver, secondsToWait);
        ExpectedCondition<Boolean> condition = new ExpectedCondition<Boolean>() {
        	@Override
        	public Boolean apply(WebDriver d) {
        		WebElement result = d.findElement(By.className(classToFind));
        		return textToFind.equals(result.getText());
        	}
        };
        wdw.until(condition); // Won't get past here till timeout or element is found
    }
    public static void waitForElementByClassAndValue(WebDriver driver,final String classToFind, final String valueToFind, int secondsToWait) {         
        WebDriverWait wdw = new WebDriverWait(driver, secondsToWait);
        ExpectedCondition<Boolean> condition = new ExpectedCondition<Boolean>() {
        	@Override
            public Boolean apply(WebDriver d) {
            	WebElement result = d.findElement(By.className(classToFind));  
                return valueToFind.equals(result.getAttribute("value"));
            }
        };
        wdw.until(condition); // Won't get past here till timeout or element is found
    }
        
    public static void waitForLinkByText(WebDriver driver, final String text, int secondsToWait) {         
        WebDriverWait wdw = new WebDriverWait(driver, secondsToWait);       
        wdw.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(text))); 
    }   

    public static void waitForElementByXpath(WebDriver driver, final String expression, int secondsToWait) {         
        WebDriverWait wdw = new WebDriverWait(driver, secondsToWait);       
        wdw.until(ExpectedConditions.presenceOfElementLocated(By.xpath(expression)));
    }

    /**
     * Adds specific cookie so that automated tests won't be tracked by Google Analytics
     * This has been implemented only for forms so far (as of 24/2/2015)
     */
    public void addCookieForGoogleAnalytics() {
        log("Creating and adding cookie so that this test wont be tracked by Google Analytics");
        if (driver.manage().getCookieNamed("MF_TEST") == null) {
            // getting tomorrow's date
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, 1);
            date = calendar.getTime();

            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            String domain = (String) jsExecutor.executeScript("return document.domain");

            Cookie autoTestCookie = new Cookie("MF_TEST", "true", domain, "/", date);
            driver.manage().addCookie(autoTestCookie);
        }
    }
}
