package com.medfusion.product.object.maps.patientportal2.page;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Level;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil.SupportedWebElements;

/**
 * Page with general functionality. TODO Should be moved somewhere to general project later
 */
public abstract class MedfusionPage extends BasePageObject {

    public MedfusionPage(WebDriver driver) {
        super(driver);
    }

	public MedfusionPage(WebDriver driver, String url) {
		super(driver);
		log("Loading page");

		if (url != null) {
			String sanitizedUrl = url.trim();
			log("URL: " + sanitizedUrl);
			driver.get(sanitizedUrl);
		}

		// there's an issue related to hudson slave's resolution 1024x768 - can't click on
		// CreateNewPatient element
		driver.manage().window().maximize();
		printCookies();
		PageFactory.initElements(driver, this);

		if (!assessBasicPageElements()) {
			throw new UnsupportedOperationException("Page was not successfully loaded");
		}
	}

	/**
	 * TODO update in IHGUtil - there is System.out.println :( Usually this is just annoying so want
	 * to put this on debug level
	 */
	public void printCookies() {
		Set<Cookie> cookies = driver.manage().getCookies();
		log("Printing Cookies -------", Level.DEBUG);
		for (Cookie c : cookies) {
			log(c.toString(), Level.DEBUG);
		}
		log("--------------------------", Level.DEBUG);
	}

	public abstract boolean assessBasicPageElements();


	public String elementToString(WebElement element) {
		return "Element (id: " + element.getAttribute("id") + ", tag: " + element.getTagName() + ")";
	}

	@Override
	protected void log(String logText) {
		super.log(createFormattedLogMessage(logText));
	}

	@Override
	protected void log(String logText, Level level) {
		super.log(createFormattedLogMessage(logText), level);
	}

	private String createFormattedLogMessage(String logText) {
		return this.getClass().getSimpleName() + ": " + logText;
	}

    /**
     * Returns supported web element or throws error if not supported
     */
    public SupportedWebElements getSupportedWebElement(WebElement element) {
        SupportedWebElements swe;
        if ("select".equals(element.getTagName())) {
            swe = SupportedWebElements.SELECT;
        } else if ("input".equals(element.getTagName())) {
            swe = SupportedWebElements.TEXT;
        } else {
			throw new UnsupportedOperationException("Error when processing " + elementToString(element) + ". Element is not supported.");
		}

        log("Element evaluated as: " + swe, Level.DEBUG);
        return swe;
    }

    /**
     * Updates web elements values, submits form and validates submitted results
     */
    public boolean updateAndValidateWebElements(Map<WebElement, String> map, WebElement submitElement) {
        updateWebElements(map);
        clickOnElement(submitElement);
        return validateWebElements(map);
    }

    /**
     * Updates web elements values
     */
    public void updateWebElements(Map<WebElement, String> map) {
        for (Entry<WebElement, String> entry : map.entrySet()) {
            updateWebElement(entry.getKey(), entry.getValue());
        }
    }

    /**
	 * Clicks on web element
	 */
	public void clickOnElement(WebElement element) {
		if (element != null) {
			log("Click on: " + elementToString(element));
			element.click();
        } else {
			throw new UnsupportedOperationException("Error when clicking element - element is null.");
        }
    }

    /**
     * Updates web element value
     * Currently works with text and select elements only
     */
    public void updateWebElement(WebElement element, String value) {
        log("Updating element with id: " + element.getAttribute("id") + " to value: " + value, Level.DEBUG);

        SupportedWebElements swe = getSupportedWebElement(element);

        if (swe.equals(SupportedWebElements.SELECT)) {
            Select select = new Select(element);
            select.selectByVisibleText(value);
        } else {
            element.sendKeys(value);
        }

        if (!validateWebElement(element, value, swe)) {
			// TODO what exception should be here?
            throw new UnsupportedOperationException("Element was not set up correctly");
        }
    }

    /**
     * Validates that web elements has the same values as expected
     */
    public boolean validateWebElements(Map<WebElement, String> map) {
        for (Entry<WebElement, String> entry : map.entrySet()) {
            if (!validateWebElement(entry.getKey(), entry.getValue())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validates that web element has the same value as expected
     * Currently works with text and select elements only
     */
    public boolean validateWebElement(WebElement element, String value, SupportedWebElements swe) {
        switch (swe) {
            case TEXT:
                if (!value.equals(element.getAttribute("value"))) {
					// TODO duplicate code in cases
					log(elementToString(element) + " has value : " + element.getAttribute("value") + " but expected: " + value + " - ERROR");
                    return false;
                } else {
					log(elementToString(element) + " has value : " + element.getAttribute("value") + " - OK", Level.DEBUG);
                }
                break;
            case SELECT:
                Select select = new Select(element);
                String selectedText = select.getFirstSelectedOption().getText();
                if (!value.equals(selectedText)) {
					log(elementToString(element) + " has value : " + selectedText + " but expected: " + value + " - ERROR");
                    return false;
                } else {
					log(elementToString(element) + " has value : " + selectedText + " - OK");
                }
                log(select.getFirstSelectedOption().getText(), Level.DEBUG);
                break;
            default:
                break;
        }
        return true;
    }


	public boolean assessPageElements(ArrayList<WebElement> allElements) {
		log("Checking page elements");

		for (WebElement w : allElements) {
			int attempt = 1;
			while (attempt < 3) {
				log("Searching for element: " + w.toString(), Level.DEBUG);
				try {
					new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOf(w));
					log("Element " + w.toString() + " : is displayed", Level.DEBUG);
					attempt = 3;
				} catch (StaleElementReferenceException ex) {
					log("StaleElementReferenceException was catched, attempt: " + attempt++, Level.DEBUG);
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
}
