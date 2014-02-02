package com.intuit.ihg.product.practice.page.fileSharing;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ifs.csscat.core.utils.BrowserTypeUtil;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.practice.utils.PracticeConstants;
import com.intuit.ihg.product.practice.utils.PracticeUtil;

/**
 * 
 * @author bbinisha
 *
 */

public class FileSharingUploadPage extends BasePageObject {

	@FindBy( xpath = "//input[@value='Upload File']")
	private WebElement uploadFileButton;

	@FindBy( xpath = "//input[@value='Upload']")
	private WebElement uploadButton;

	@FindBy( xpath = "//input[@name='fileids']")
	private WebElement fileUploadCheckBox;

	@FindBy (linkText = "Remove")
	private WebElement removeLink;

	@FindBy ( xpath = "//input[@value='Delete Selected Files']")
	private WebElement deleteSelectedFilesButton;

	@FindBy ( xpath = "//input[@name='uploadfile']")
	private WebElement browseButton;

	public FileSharingUploadPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	/**
	 * @author bbinisha
	 * @Desc : Click on Upload File button and navigate to upload page
	 */
	public void clickOnUploadFileButton() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, uploadFileButton);
		uploadFileButton.click();

	}

	/**
	 * @author bbinisha
	 * @throws Exception 
	 * @Desc : Selects the file and Click on the Upload button in the pload page
	 */
	public void browseAndUpload() throws Exception {
		IHGUtil.PrintMethodName();

		Thread.sleep(6000);
		IHGUtil.PrintMethodName();
		driver.switchTo().defaultContent();
		//		driver.switchTo().frame("iframe");
		IHGUtil.waitForElement(driver, 30, browseButton);
		
		URL textFilePath = ClassLoader.getSystemResource(PracticeConstants.textFilePath);
		browseButton.sendKeys(textFilePath.getPath());
		
		IHGUtil.waitForElement(driver, 30, uploadButton);
		uploadButton.click();

	}

	/**
	 * @author bbinisha
	 * @Desc :
	 */
	public void clickOnFileCheckBox() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, fileUploadCheckBox);
		fileUploadCheckBox.click();
	}

	/**
	 * @author bbinisha
	 *  @Desc : To Add a file in the directory
	 */
	public void addFile() {
		IHGUtil.PrintMethodName();
		driver.findElement(By.xpath(".//*[@id='content']/table/tbody/tr/td/table[3]/tbody/tr[4]/td[3]/a")).click();

	}

	/**
	 * @author bbinisha
	 *  @Desc : Checking whether the Remove link is present after adding file.
	 */
	public boolean isRemoveLinkPresent() {
		IHGUtil.PrintMethodName();
		return removeLink.isDisplayed();
	}

	/**
	 * @author bbinisha
	 *  @Desc : Deleting all the present files from the directory
	 */
	public void deleteAllExistingFiles() {
		IHGUtil.PrintMethodName();
		List<WebElement> fileList = driver.findElements(By.xpath("//input[@name='fileids']"));
		for(WebElement file : fileList) {
			file.click();
		}
		deleteSelectedFilesButton.click();
	}
}
