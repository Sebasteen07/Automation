package com.medfusion.product.object.maps.patientportal2.page.CcdPage;

import java.util.ArrayList;

import org.apache.log4j.Level;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;

public class DocumentsPage extends MedfusionPage {

	
	@FindBy(how = How.ID, using = "from-date-lbl")
	private WebElement dateFrom;
	@FindBy(how = How.ID, using = "downloadBtn0")
	private WebElement downloadBtn;
	@FindBy(how = How.XPATH, using = "//*[@id=\'documentsTable\']/tbody[1]/tr/td[1]")
	private WebElement fileName;
	
	
	
	public DocumentsPage(WebDriver driver) {
		super(driver);
	}
	
	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(dateFrom);
		
		
		log("Checking all elements on " + this.getClass().getSimpleName());

        for (WebElement w : webElementsList) {
            int attempt = 1;
            while (attempt < 3) {
                try {
                    new WebDriverWait(driver, 120).until(ExpectedConditions.visibilityOf(w));
                    log("Element " + w.toString() + " : is displayed", Level.DEBUG);
                    attempt = 3;
                } catch (StaleElementReferenceException ex) {
                    log("StaleElementReferenceException was caught, attempt: " + attempt++);
                } catch (TimeoutException ex) {
                    log(ex.getMessage());
                    return false;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return false;
                }
            }
        }

        return true;
	}

	public boolean checkLastImportedFileName(String name) {
		
		return name.equals(fileName.getText());
	}
	


}
