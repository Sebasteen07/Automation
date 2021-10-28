package pageobjects;

import static org.testng.Assert.assertFalse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.medfusion.common.utils.IHGUtil;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;

public class MerchantSearchPage extends NavigationMenu {

	@FindBy(how = How.ID, using = "merchantSearchField")
	private WebElement searchField;

	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Search')]")
	private WebElement searchButton;

	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Download Full Merchant List')]")
	private WebElement downloadMerchantList;

	@FindBy(how = How.XPATH, using = "//tbody/tr")
	private WebElement merchantRows;

	@FindBy(how = How.XPATH, using = "//body/div[@id='viewContent']/div[@id='merchantList']/table[1]")
	private WebElement merchantSearchTable;

	@FindBy(how = How.XPATH, using = "//tbody/tr")
	private WebElement tableRow;

	@FindBy(how = How.XPATH, using = "//*[@class='btn btn-primary btn-xs']")
	private WebElement viewDetailsButton;

	@FindAll({ @FindBy(how = How.XPATH, using = "//thead/tr/th") })
	private List<WebElement> tableColumn;

	@FindAll({ @FindBy(how = How.XPATH, using = "//tbody/tr") })
	private List<WebElement> mmidRow;

	public MerchantSearchPage(WebDriver driver) {
		super(driver);
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	public boolean assessSearchMerchantElements() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(searchField);
		webElementsList.add(searchButton);
		webElementsList.add(downloadMerchantList);
		IHGUtil util = new IHGUtil(driver);
		return util.assessAllPageElements(webElementsList, MerchantSearchPage.class);
	}

	public void findByPracticeID(String practiceId) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(searchField));
		searchField.sendKeys(practiceId);
	}

	public void findByMerchantId(String merchantId) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(searchField));
		driver.navigate().refresh();
		searchField.sendKeys(Keys.TAB);
		searchField.sendKeys(Keys.ENTER);
		searchField.sendKeys(merchantId);

	}

	public void TypeInField(WebElement element, String value) throws InterruptedException {
		String val = value;

		element.clear();

		for (int i = 0; i < val.length(); i++) {
			char c = val.charAt(i);
			String s = new StringBuilder().append(c).toString();
			element.sendKeys(s);
			wait();
		}
	}

	public void searchButtonClick() {

		searchButton.click();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(viewDetailsButton));

	}

	public void viewDetailsButtonClick() throws InterruptedException {

		viewDetailsButton.click();
		
	}

	public void duplicateRecords() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(merchantSearchTable));
		WebElement merchantIDRow = merchantSearchTable.findElement(By.xpath("//tbody/tr"));

		List<WebElement> col = tableColumn;
		List<WebElement> rows = mmidRow;

		int noOfCol = col.size();
		int noOfRows = rows.size();

		List<String> list = new ArrayList<String>();

		for (int i = 1; i < noOfRows - 1; i++) {
			WebElement cell = tableRow.findElement(By.xpath("//tbody/tr[" + i + "]/td[1]"));
			String mmid = cell.getText();
			Log4jUtil.log("mmid = " + mmid);
			list.add(mmid);
		}
		Boolean verify = hasDuplicate(list);
		assertFalse(verify);
		Log4jUtil.log("Assert value" + verify);

	}

	public static <TableData> boolean hasDuplicate(Iterable<TableData> rowset) {
		Set<TableData> set = new HashSet<TableData>();
		for (TableData each : rowset) {
			Log4jUtil.log("tabledata" + each);
			if (!set.add(each))

				return true;
		}
		return false;
	}

}
