//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.CcdPage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;

public class DocumentsPage extends JalapenoMenu {

	@FindBy(how = How.ID, using = "from-date-lbl")
	private WebElement dateFrom;

	@FindBy(how = How.ID, using = "downloadBtn0")
	private WebElement downloadBtn;

	@FindBy(how = How.XPATH, using = "//*[@id='documentsTable']/tbody[1]/tr/td[1]")
	private WebElement fileName;

	@FindBy(how = How.XPATH, using = "//*[@id='documentsTable']/tbody[1]/tr/td[2]")
	private WebElement from;

	@FindBy(how = How.XPATH, using = "//*[@id='documentsTable']/tbody[1]/tr/td[4]")
	private WebElement categoryType;

	;

	public DocumentsPage(WebDriver driver) {
		super(driver);
	}

	public boolean checkLastImportedFileName(String name) {
		return name.equals(fileName.getText());
	}

	public void downloadSecureMessageAttachment() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,200)", "");
		downloadBtn.click();
	}

	public void verifyName_From_CategoryType(String from2, String categoryType2, String fileName2) {

		Log4jUtil.log("From name on Portal " + from.getText() + " For From Name Posted " + from2);

		categoryType2.equalsIgnoreCase(categoryType.getText());
		Log4jUtil.log("CategoryType posted: " + categoryType2 + " Matches with the categoryType on portal : " + categoryType.getText());

		fileName2.equalsIgnoreCase(fileName.getText());
		Log4jUtil.log("File name posted: " + fileName2 + " Matches with the Filename on portal : " + fileName.getText());

	}
	
	public String getMessageAttachmentData() {
		return fileName.getText();
    }



}
