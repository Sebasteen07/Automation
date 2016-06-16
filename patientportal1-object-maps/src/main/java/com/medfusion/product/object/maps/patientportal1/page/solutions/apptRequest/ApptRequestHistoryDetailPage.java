package com.medfusion.product.object.maps.patientportal1.page.solutions.apptRequest;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.patientportal1.utils.PortalUtil;

public class ApptRequestHistoryDetailPage extends BasePageObject {

    public static final String PAGE_NAME = "Appt Request History Detail Page";
    public static final String PAGE_NAME_Adb = "IHGQA Automation NonIntegrated";

    @FindBy(linkText = "View as PDF")
    private WebElement lnkViewAsPdf;

    public ApptRequestHistoryDetailPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Clicks the 'View As PDF' link from the Appt Request History detail page.
     * Will attempt to return the text in the PDF by copying all the text from
     * the PDF for validation. The PDF contained in the page via the HTML 'embed' tag.
     * 
     * @return contents of the PDF
     * @throws InterruptedException
     */
    public String clickViewAsPdfLink() throws InterruptedException {
        String str = "";
        IHGUtil.PrintMethodName();
        PortalUtil.setPortalFrame(driver);

        Thread.sleep(10000);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", lnkViewAsPdf);
        // lnkViewAsPdf.sendKeys(Keys.ENTER);
        Thread.sleep(20000);

        String mainWindowHandle = driver.getWindowHandle();
        Set<String> windowIterator = driver.getWindowHandles();
        Iterator<String> ite = windowIterator.iterator();

        while (ite.hasNext()) {
            String popupHandle = ite.next().toString();
            if (!popupHandle.contains(mainWindowHandle)) {
                driver.switchTo().window(popupHandle);
                str = driver.getTitle();
                System.out.println("Window name after clicking View as pdf link:" + str);

            }
        }
        return str;

        // Set<String> currentWindowHandle = driver.getWindowHandles();
        // driver.switchTo().window(currentWindowHandle.s);
        // String str=driver.getTitle();
        // System.out.println("11111111111"+str);
        /*
         * String currentWindowHandle = driver.getWindowHandle();
         * Set<String> windowHandles = driver.getWindowHandles();
         * for (String handle : windowHandles) {
         * driver.switchTo().window(handle);
         * if(!currentWindowHandle.equalsIgnoreCase(handle)) {
         * driver.switchTo().window(handle);
         * }
         * }
         */

        /*
         * WebElement body = driver.findElement(By.tagName("body"));
         * body.sendKeys(Keys.chord(Keys.CONTROL, "a"));
         * body.sendKeys(Keys.chord(Keys.CONTROL, "c"));
         */

        /*
         * TextTransfer clipboard = new TextTransfer();
         * return clipboard.getClipboardContents();
         */
    }

}
