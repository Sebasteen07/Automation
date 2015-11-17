package com.intuit.ihg.product.object.maps.mobile.page.solutions.inbox;

import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.object.maps.mobile.page.MobileBasePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by IntelliJ IDEA.
 * User: vvalsan
 * Date: 3/21/13
 * Time: 4:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class MessageDetailsPage extends MobileBasePage {

    @FindBy(css="a[href='#msgInbox']")
    private WebElement btnInbox;

	@FindBy(css="a[href='#msgReply']")
	private WebElement btnReply;

	@FindBy(xpath="//div[@class='messageSubject']")
	private WebElement subject;

    public MessageDetailsPage(WebDriver driver) {
        super(driver);
    }

    public String getSubject() throws InterruptedException {
        IHGUtil.waitForElement(driver, 60, subject);
        return this.subject.getText();
    }
}
