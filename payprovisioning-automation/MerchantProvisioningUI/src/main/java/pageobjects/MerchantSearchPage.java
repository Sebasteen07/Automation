package pageobjects;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.common.utils.IHGUtil;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.common.utils.PropertyFileLoader;

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

	@FindAll({ @FindBy(how = How.XPATH, using = "//thead/tr/th") })
	private List<WebElement> tableColumn;

	@FindAll({ @FindBy(how = How.XPATH, using = "//tbody/tr") })
	private List<WebElement> MMIDRow;

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

	public void findByPracticeID(String practiceID) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(searchField));
		searchField.sendKeys(practiceID);
	}

	public void searchButtonClick() {
		searchButton.click();
	}

	public void duplicateRecords() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(merchantSearchTable));
		WebElement merchantIDRow = merchantSearchTable.findElement(By.xpath("//tbody/tr"));

		List<WebElement> col = tableColumn;
		List<WebElement> rows = MMIDRow;

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
		Log4jUtil.log("Assert value" +verify);

	}

	public static <TableData> boolean hasDuplicate(Iterable<TableData> rowset) {
		Set<TableData> set = new HashSet<TableData>();
		for (TableData each : rowset) {
			Log4jUtil.log("tabledata" +each);
			if (!set.add(each))

				return true;
		}
		return false;
	}

}
