package com.intuit.ihg.product.object.maps.portal.page.solutions.virtualofficevisit;

import java.util.Date;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class VirtualOfficeVisitHistoryPage extends BasePageObject {
	
	public static final String PAGE_NAME = "Virtual Office Visit History Page";
	
	@FindBy(xpath=".//a/span")
	private List<WebElement> vovHistoryList;
	
	@FindBy(xpath="//div[3]/div/table[2]/tbody/tr[1]/td[2]")
	private WebElement vovHistory;
	
	public VirtualOfficeVisitHistoryPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Find the Virtual Office Visit where the date is greater than the supplied date.
	 * Note: there is no adjustment for timezones.
	 * @param sentDate should be a date taken right before the Virtual Office Visit is submitted
	 * @return the Vov History Detail page
	 * @throws Exception
	 */
	public VirtualOfficeVisitHistoryDetailPage viewVirtualOfficeVisitHistoryDetails(Date sentDate) throws Exception {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		vovHistory.click();
		/*if (sentDate == null) throw new Exception("No sent date was provided");
		
		// Loop through all of the dates on the page and find the one that is larger sentDate
		WebElement historyItem = null;		
		for (WebElement item : vovHistoryList) {
			try {
				Date receivedDate = formatter.parse(item.getText());
				log("Received Date: " + item.getText() + ", Sent Date: " + formatter.format(sentDate) + ", ReceivedDateOlder?: " + sentDate.compareTo(receivedDate));
				
				if (sentDate.compareTo(receivedDate) <= 0) {
					historyItem = item;
					break;
				}
			} catch (ParseException pe) {
				// The xpath may pick up a link to the next page(s) instead of the date. Page has no great way to limit this.
				// So just catch the error and continue on.
			}
		}
		
		if (historyItem != null) {
			historyItem.click();
		} else {
			throw new Exception("No Virtual Office Visit History item was found based on given date");
		}*/
		
		return PageFactory.initElements(driver, VirtualOfficeVisitHistoryDetailPage.class);
		
	}
}
