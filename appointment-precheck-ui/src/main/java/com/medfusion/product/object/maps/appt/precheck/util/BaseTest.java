package com.medfusion.product.object.maps.appt.precheck.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ifs.csscat.core.WebDriverFactory;
import com.intuit.ifs.csscat.core.utils.BrowserTypeUtil;
import com.intuit.ifs.csscat.core.utils.BrowserUtil;
import com.intuit.ifs.csscat.core.utils.FileUtil;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ifs.csscat.core.utils.WindowProcessKiller;
import com.medfusion.common.utils.EnvironmentTypeUtil;
import com.medfusion.common.utils.IHGUtil;

import io.cucumber.java.Scenario;
import net.jsourcerer.webdriver.jserrorcollector.JavaScriptError;

public class BaseTest {
	public static WebDriver driver;
	private int stepCounter;

	public void setUp() throws Exception {
		LogManager.getLogger("com.intuit.ifs.csscat").setLevel(Log4jUtil.getLogLevel());
		if (TestConfig.getBrowserType() == BrowserTypeUtil.BrowserType.chrome)
			BrowserUtil.setupChromeDriver();
		FileUtil.createDirectory(TestConfig.getTestResultScreenshot(), false);
		FileUtil.createDirectory(TestConfig.getTestResultVideos(), false);
		FileUtil.createDirectory(TestConfig.getJSCapture(), false);
	}

	public void beforeSuite() {
		log(TestConfig.dumpTestConfigInfo(), Level.INFO);
	}

	public void testSetup(Scenario method) throws Exception {
		if (TestConfig.getBrowserType() == BrowserTypeUtil.BrowserType.iexplore) {
			BrowserUtil.setupIEDriver();
		}

		this.driver = WebDriverFactory.getWebDriver();
		this.driver.manage().timeouts().implicitlyWait(20L, TimeUnit.SECONDS);
		if (TestConfig.isClearBrowserCache()) {
			BrowserUtil.clearBrowserCache(this.driver);
		}

		logTestEnvironmentInfo(method);

		log("Resetting step counter");
		stepCounter = 0;
	}

	public void tearDown(Scenario scenario) throws Exception {
		if (scenario.isFailed()) {
			String screenshotName = scenario.getName().replaceAll("", "_");
			byte[] sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(sourcePath, "image/png", screenshotName);
		}

	}

	public void postTestCase(Scenario scenario) throws ClassNotFoundException {
		String testMethodName = scenario.getName();

		if (scenario.isFailed()) {
			try {
				if (TestConfig.isCaptureScreenshot()) {
					String filename = TestConfig.getTestResultScreenshot() + File.separator + "FAILED_" + "."
							+ testMethodName + ".html";
					BufferedWriter out = new BufferedWriter(new FileWriter(filename));
					out.write(driver.getPageSource());
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (TestConfig.isJavascriptCaptureEnabled()
				&& TestConfig.getBrowserType() == BrowserTypeUtil.BrowserType.firefox) {
			try {
				DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
				df.setTimeZone(TimeZone.getTimeZone("PST"));
				final List<JavaScriptError> jsErrors = JavaScriptError.readErrors(driver);

				if (!jsErrors.isEmpty()) {
					String fileName = TestConfig.getJSCapture() + File.separator + "JS_Errors_" + df.format(new Date())
							+ ".txt";
					File jsErrorFile = new File(fileName);
					FileWriter fw = new FileWriter(jsErrorFile.getAbsoluteFile(), true);
					BufferedWriter bw = new BufferedWriter(fw);

					for (JavaScriptError error : jsErrors) {
						bw.write(" Test Method: " + testMethodName);
						bw.newLine();
						bw.write("Line: " + error.getLineNumber());
						bw.newLine();
						bw.write("Error: " + error.getErrorMessage());
						bw.newLine();
						bw.write("Source: " + error.getSourceName());
						bw.newLine();
						bw.write("--------------------------------------------------");
						bw.newLine();
					}
					bw.close();
					fw.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			driver.quit();

			if (TestConfig.getBrowserType() == BrowserTypeUtil.BrowserType.iexplore) {
				// Killing the ie driver and all instances of internet explorer
				String processName = "IEDriverServer.exe";
				if (WindowProcessKiller.isProcessRuning(processName)) {
					WindowProcessKiller.killProcess(processName);
				}
				String ie_process = "iexplore.exe";
				if (WindowProcessKiller.isProcessRuning(ie_process)) {
					WindowProcessKiller.killProcess(ie_process);
				}
			}

		} catch (Exception e) {
			log("ERROR: Unable to close browser(s), no browser is currently open.");
		}

		log("TEST RESULT:");
		System.out.println(new Date() + " environment=" + IHGUtil.getEnvironmentType() + " test=" + testMethodName
				+ " result=" + scenario.getStatus());
	}

	protected void logStep(String logText) {
		log("STEP " + ++stepCounter + ": " + logText);
	}

	protected void log(String message) {
		Log4jUtil.log(message);
	}

	protected void log(String message, String level) {
		Log4jUtil.log(message, level);
	}

	protected void log(String message, Level level) {
		Log4jUtil.log(message, level);
	}

	private void logTestEnvironmentInfo(Scenario method) {
		logTestEnvironmentInfo(method.getName(), IHGUtil.getEnvironmentType(), TestConfig.getBrowserType());
	}

	private void logTestEnvironmentInfo(String method, EnvironmentTypeUtil.EnvironmentType environment,
			BrowserTypeUtil.BrowserType browser) {
		log("Test case: " + method);
		log("Execution Environment: " + environment);
		log("Execution Browser: " + browser);
	}

	public void maxWindow() {
		if (!(TestConfig.getBrowserType() == BrowserTypeUtil.BrowserType.iexplore)) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String script = "if (window.screen){window.moveTo(0, 0);window.resizeTo(window.screen.availWidth,window.screen.availHeight);};";
			js.executeScript(script);
		}
	}

	public void scrollAndWait(int x, int y, int ms) throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("scroll(" + x + ", " + y + ");");
		Thread.sleep(ms);
	}

	protected void waitForPageTitle(final String title, long sec) {
		WebDriverWait wait = new WebDriverWait(driver, sec);
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getTitle().contains(title);
			}
		});
	}

	protected boolean switchToWindowUsingTitle(String title) throws InterruptedException {
		int retry = 0;
		String currentWindow = driver.getWindowHandle();
		while (retry < 3) {
			Set<String> availableWindows = driver.getWindowHandles();
			if (!availableWindows.isEmpty()) {
				for (String windowId : availableWindows) {
					driver.switchTo().window(windowId).getTitle();
					log("Current Window Title:  " + driver.getTitle(), Level.DEBUG);
					if (driver.getTitle().contains(title)) {
						log("Switch to " + driver.getTitle(), Level.INFO);
						return true;
					}
				}
			}
			retry++;
			Thread.sleep(5000);
		}
		log("Cannot find Window: " + title, Level.ERROR);
		driver.switchTo().window(currentWindow);
		return false;
	}

}
