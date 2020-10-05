package com.medfusion.product.object.maps.patientportal2.page.CcdPage;

import java.util.ArrayList;

import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class DocumentsPage extends JalapenoMenu {

		@FindBy(how = How.ID, using = "from-date-lbl")
		private WebElement dateFrom;

		@FindBy(how = How.ID, using = "downloadBtn0")
		private WebElement downloadBtn;

		@FindBy(how = How.XPATH, using = "//*[@id='documentsTable']/tbody[1]/tr/td[1]")
		private WebElement fileName;

		public DocumentsPage(WebDriver driver) {
				super(driver);
		}

		@Override
		public boolean areBasicPageElementsPresent() {
				ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
				webElementsList.add(dateFrom);
				return assessPageElements(webElementsList);
		}

		public boolean checkLastImportedFileName(String name) {
				return name.equals(fileName.getText());
		}

		public void downloadSecureMessageAttachment() {    
		 JavascriptExecutor jse = (JavascriptExecutor)driver;
	     jse.executeScript("window.scrollBy(0,200)", "");
			downloadBtn.click();
        }



}
