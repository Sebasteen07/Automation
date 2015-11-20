package com.medfusion.product.object.maps.jalapeno.page.CcdViewer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.downloads.RequestMethod;
import com.intuit.ihg.common.utils.downloads.URLStatusChecker;
import com.medfusion.product.object.maps.jalapeno.page.MessagesPage.JalapenoMessagesPage;


public class JalapenoCcdPage extends BasePageObject {

	@FindBy(how = How.ID, using = "closeCcd")
	private WebElement closeButton;
	
	@FindBy(how = How.ID, using = "savePdf")
	private WebElement savePdfButton;
	
	@FindBy(how = How.ID, using = "saveRaw")
	private WebElement saveRawButton;
	
	@FindBy(how = How.LINK_TEXT, using = "Send my information")
	private WebElement sendInformationLink;
	
	@FindBy(how = How.ID, using = "directaddr")
	private WebElement directAddressBox;
	
	@FindBy(how = How.XPATH, using = "//button[.='Send']")
	private WebElement sendInformationButton;
	
	@FindBy(how = How.ID, using = "healthOverview")
	private WebElement healthOverview;
	
	@FindBy(how = How.ID, using = "basicInfo")
	private WebElement basicInformation;
	
	@FindBy(how = How.ID, using = "careteam")
	private WebElement careTeamMembers;
	
	@FindBy(how = How.ID, using = "idp5824848")
	private WebElement history;
	
	@FindBy(how = How.ID, using = "idp5829488")
	private WebElement problems;
	
	@FindBy(how = How.ID, using = "idp6074208")
	private WebElement medication;
	
	@FindBy(how = How.ID, using = "idp6124944")
	private WebElement allergies;
	
	@FindBy(how = How.ID, using = "idp6147776")
	private WebElement pastMedicalHistory;
	
	@FindBy(how = How.ID, using = "idp6173024")
	private WebElement procedures;
	
	@FindBy(how = How.ID, using = "idp6205744")
	private WebElement vitalSigns;
	
	@FindBy(how = How.ID, using = "idp6335632")
	private WebElement results;
	
	@FindBy(how = How.ID, using = "idp6433952")
	private WebElement advanceDirectives;
	
	@FindBy(how = How.ID, using = "idp6454816")
	private WebElement insurance;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"messageContainer\"]/div[3]/div[2]/div[3]/div[1]/div/a")
	private WebElement BtnViewHealthData;
	
	@FindBy(id = "basicInfo")
	private WebElement ccdBasicInfo;
	
	public JalapenoCcdPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);	
	}
	
	public JalapenoMessagesPage closeCcd(WebDriver driver) {
		log("Closing Ccd form, returning to Messages page");
		
		closeButton.click();
		
		return PageFactory.initElements(driver, JalapenoMessagesPage.class);
	}
	
	public boolean sendInformation(String emailAddress) {
		log("Click on sending information");
		sendInformationLink.click();
		
		log("Input the direct email address: " + emailAddress);
		directAddressBox.sendKeys(emailAddress);
		
		log("Send the information");
		sendInformationButton.click();
		
		try {
			Thread.sleep(10000);
			try {
				if(driver.findElement(By.xpath("//span[.='Your health information was sent to " + emailAddress + "!']")) != null) {
					log("Message was succesfully sent to " + emailAddress);
					return true;
				}
			}
			catch (NoSuchElementException e) {
				log("Error appears during sending health information");
				return false;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			log("Something went wrong with Thread.sleep");
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean checkPdfToDownload(WebDriver driver) throws IOException, URISyntaxException {
		IHGUtil.PrintMethodName();
		String pdfUrl = savePdfButton.getAttribute("href");
		
		URLStatusChecker status = new URLStatusChecker(driver);
		status.setURIToCheck(pdfUrl);
		status.setHTTPRequestMethod(RequestMethod.GET);
		
		Integer statusResult = status.getDownloadStatusCode(pdfUrl, RequestMethod.GET);
		
		if(statusResult.equals(200)) {
			log("The PDF file is available");
			return true;
		}
		else {
			log("The file is not available, return code: " + statusResult);
			return false;
		}
	}
	
	public boolean checkRawToDownload(WebDriver driver) throws IOException, URISyntaxException {
		IHGUtil.PrintMethodName();
		String rawUrl = saveRawButton.getAttribute("href");
		
		URLStatusChecker status = new URLStatusChecker(driver);
		status.setURIToCheck(rawUrl);
		status.setHTTPRequestMethod(RequestMethod.GET);
		
		Integer statusResult = status.getDownloadStatusCode(rawUrl, RequestMethod.GET);
		
		if(statusResult.equals(200)) {
			log("The RAW file is available");
			return true;
		}
		else {
			log("The file is not available, return code: " + statusResult);
			return false;
		}
	}
	
	public boolean assessCcdElements() {

		boolean allElementsDisplayed = false;

		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
	
		webElementsList.add(closeButton);
		webElementsList.add(savePdfButton);
		webElementsList.add(saveRawButton);
		webElementsList.add(sendInformationLink);
		webElementsList.add(healthOverview);
		webElementsList.add(basicInformation);
		webElementsList.add(careTeamMembers);
	/*	webElementsList.add(history);
		webElementsList.add(problems);
		webElementsList.add(medication);
		webElementsList.add(allergies);
		webElementsList.add(pastMedicalHistory);
		webElementsList.add(procedures);
		webElementsList.add(vitalSigns);
		webElementsList.add(results);
		webElementsList.add(advanceDirectives);
		webElementsList.add(insurance);
	*/
		for (WebElement w : webElementsList) {

			try {
				IHGUtil.waitForElement(driver, 60, w);
				log("Checking WebElement" + w.toString());
				if (w.isDisplayed()) {
					log("WebElement " + w.toString() + "is displayed");
					allElementsDisplayed = true;
				} else {
					log("WebElement " + w.toString() + "is NOT displayed");
					return false;
				}
			}

			catch (Throwable e) {
				log(e.getStackTrace().toString());
			}

		}
		return allElementsDisplayed;
	}
	
	
	/**
	 * Click on the View health data 
	 */
	public void clickBtnViewHealthData() throws InterruptedException {
		IHGUtil.PrintMethodName();
		//PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver, 60, BtnViewHealthData);
		BtnViewHealthData.click();	
	}	
	
	public void verifyCCDViewerAndClose() throws InterruptedException {
		IHGUtil.PrintMethodName();
		driver.switchTo().defaultContent();
		
		if (ccdBasicInfo.isDisplayed() && closeButton.isDisplayed()) {
			closeButton.click();
		} else {
			Assert.fail("CCD Viewer not present: Could not find CCD Basic Info/Close Viewer Button");
		}
	}
}
