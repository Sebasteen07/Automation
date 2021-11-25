package com.medfusion.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


public class BasePage {

    protected WebDriver webDriver;

    public BasePage(WebDriver webDriver){

        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Runtime exception occurred while sleep");
        }
    }

    //TODO add it to some helper, basepage, etc
    public String getText(WebElement webElement) {
        sleep(100);

        String text = "";
        String tagType = webElement.getTagName();

        if (tagType.equals("input") && webElement.getAttribute("type").equals("checkbox")){
            text = (webElement.getAttribute("checked") == null)?"false":"true";
        } else {
            text = webElement.getText().trim();
        }
        return text;
    }

    public void setText(WebElement webElement, String text) {
        sleep(100);

        String tagType = webElement.getTagName();

        if (tagType.equals("input") && (webElement.getAttribute("type").equals("text")
                || webElement.getAttribute("type").equals("number")
                || webElement.getAttribute("type").equals("password"))) {
            webElement.clear();
            webElement.sendKeys(text);
        } else if (tagType.equals("select")) {
            webElement.sendKeys(text);
            Select selectElement = new Select(webElement);
            List<WebElement> options = selectElement.getOptions();
            for (WebElement option : options) {
                if (option.getAttribute("label").trim().equals(text)) {
                    selectElement.selectByVisibleText(text);
                } else if (option.getAttribute("value").endsWith(text)) {
                    selectElement.selectByVisibleText(getText(option));
                }
            }
            //Throw exception

        } else if (tagType.equals("input") && webElement.getAttribute("type").equals("checkbox")) {
            if (!getText(webElement).equals(text)) {
                webElement.click();
            }
        }
    }

    public void click(WebElement webElement) {
        WebDriverWait wait = new WebDriverWait(webDriver, 10, 200);
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
        webElement.click();
    }

}
