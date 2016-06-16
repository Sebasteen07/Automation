package com.medfusion.product.object.maps.patientportal2.page;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Level;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.common.utils.IHGUtil;

/**
 * Universal page that can process WebElements automatically just by passing values to them
 * Should be moved to IHGUtil later
 */
public abstract class MedfusionPage extends IHGUtil {

    public MedfusionPage(WebDriver driver) {
        super(driver);
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
            throw new UnsupportedOperationException("Error when processing element with id: "
                    + element.getAttribute("id") + ". Tag: " + element.getTagName() + " is not supported.");
        }

        log("Element evaluated as: " + swe);
        return swe;
    }

    /**
     * Updates web elements values, submits form and validates submitted results
     */
    public boolean updateAndValidateWebElements(Map<WebElement, String> map, WebElement submitElement) {
        updateWebElements(map);
        submitForm(submitElement);
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
     * Submits form
     */
    public void submitForm(WebElement submitElement) {
        if (submitElement != null) {
            log("Submitting form");
            submitElement.click();
        } else {
            throw new UnsupportedOperationException(
                    "Error when submitting form - submit form element has not been set up.");
        }
    }

    /**
     * Updates web element value
     * Currently works with text and select elements only
     */
    public void updateWebElement(WebElement element, String value) {
        log("Updating element with id: " + element.getAttribute("id") + " to value: " + value);

        SupportedWebElements swe = getSupportedWebElement(element);

        if (swe.equals(SupportedWebElements.TEXT)) {
            element.clear();
        }

        if (swe.equals(SupportedWebElements.SELECT)) {
            Select select = new Select(element);
            select.selectByVisibleText(value);
        } else {
            element.sendKeys(value);
        }

        if (!validateWebElement(element, value)) {
            // TODO temp prasecina
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
    public boolean validateWebElement(WebElement element, String value) {
        switch (getSupportedWebElement(element)) {
            case TEXT:
                if (!value.equals(element.getAttribute("value"))) {
                    log("Element with id: " + element.getAttribute("id") + " has value : "
                            + element.getAttribute("value") + " but expected: " + value + " - ERROR");
                    return false;
                } else {
                    log("Element with id: " + element.getAttribute("id") + " has value : "
                            + element.getAttribute("value") + " - OK", Level.DEBUG);
                }
                break;
            case SELECT:
                Select select = new Select(element);
                String selectedText = select.getFirstSelectedOption().getText();
                if (!value.equals(selectedText)) {
                    log("Element with id: " + element.getAttribute("id") + " has value : " + selectedText
                            + " but expected: " + value + " - ERROR");
                    return false;
                } else {
                    log("Element with id: " + element.getAttribute("id") + " has value : " + selectedText + " - OK",
                            Level.DEBUG);
                }
                log(select.getFirstSelectedOption().getText());
                break;
            default:
                break;
        }
        return true;
    }
}
