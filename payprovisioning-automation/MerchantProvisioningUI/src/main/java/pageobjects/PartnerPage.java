//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved
package pageobjects;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.io.IOException;
import java.util.List;

public class PartnerPage extends MerchantDetailsPage{

    protected static PropertyFileLoader testData;

    protected static String addedUserName;
    protected static String editedUserName;
    protected static int tableSize;

    @FindBy(how = How.LINK_TEXT, using = " Edit Partners ")
    private WebElement editPartnerWebElement;

    @FindBy(how = How.XPATH, using = "//span/*['Partners']")
    private WebElement partnersPageWebElement;

    @FindBy(how = How.XPATH, using = "//*[text()=' Add Partner ']")
    private WebElement addPartnerButtton;

    @FindBy(how = How.XPATH, using = "//button[text()='Add']")
    private WebElement addButtton;

    @FindBy(how = How.XPATH, using = "//input[@data-ng-model='partnerFields.username']")
    private WebElement addPartnerUsername;

    @FindAll({ @FindBy(how = How.XPATH, using = "//table/tbody/tr") })
    private List<WebElement> addedPartnerTable;

    @FindBy(how = How.XPATH, using = "//button[text()='Delete']")
    private WebElement deleteButtton;

    @FindBy(how = How.XPATH, using = "//div[text()='Partner Delete Success']")
    private WebElement deleteSuccessMessage;

    @FindBy(how = How.XPATH, using = "//button[text()='Edit']")
    private WebElement editButtton;

    public PartnerPage(WebDriver driver) throws IOException {
        super(driver);
        driver.manage().window().maximize();
        PageFactory.initElements(driver, this);
        testData = new PropertyFileLoader();
    }

    public void verifyNavigatedToPartnerPage() {

        waitForPageToLoad(partnersPageWebElement, 5);
    }

    public void clickOnAddPartner() throws InterruptedException {

        addPartnerButtton.click();

    }

    public void addUsernameClickOnAdd() {

        String count = IHGUtil.createRandomNumericString(3);
        addedUserName = "AddedPartner" + count;
        addPartnerUsername.sendKeys(addedUserName);
        addButtton.click();

    }

    public void verifyAddPartner() {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOf(addPartnerButtton));

        String xpath=getLastRowOfPartners();

        System.out.println(" xpath" + xpath);
        String xpathAdd = xpath + "/td[2]";
        String text = driver.findElement(By.xpath(xpathAdd)).getText();

        Assert.assertEquals(text, addedUserName);
    }

    public String getLastRowOfPartners() {

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(addPartnerButtton));
        driver.navigate().refresh();
        tableSize = addedPartnerTable.size();
        String row = "//table/tbody/" + "tr[" + tableSize + "]";

        return row;
    }

    public void deleteLastRowOfPartners() throws InterruptedException {

        String row = getLastRowOfPartners();
        String xpath = row + "/td[4]/button[3]";

        WebElement element = driver.findElement(By.xpath(xpath));

        element.isDisplayed();
        element.click();

    }

    public void clickOnDeleteDialogue() {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOf(deleteButtton));
        deleteButtton.click();
    }

    public void waitForPageToLoad(WebElement element, int time) {
        WebDriverWait wait = new WebDriverWait(driver, time);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void verifyDeletePartner() {
        deleteSuccessMessage.isDisplayed();
    }

    public void editLastRowOfPartners() {
        String row = getLastRowOfPartners();
        String xpathEditButton = row + "/td[4]/button[1]";

        WebElement element = driver.findElement(By.xpath(xpathEditButton));

        element.isDisplayed();
        element.click();
    }

    public void editUsernameClickOnEdit() {

        String count = IHGUtil.createRandomNumericString(3);
        editedUserName = "EditedPartner" + count;
        addPartnerUsername.sendKeys(editedUserName);
        editButtton.click();

    }

    public void verifyEditedPartner() {
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.visibilityOf(addPartnerButtton));

        driver.navigate().refresh();
        tableSize = addedPartnerTable.size() ;
        String row = "tr[" + tableSize + "]";
        String xpath = row + "/td[2]";

        WebElement element = driver.findElement(By.xpath("//table/tbody/" + xpath));

        String text = element.getText();
        Assert.assertEquals(element.getText(), editedUserName);

    }

}
