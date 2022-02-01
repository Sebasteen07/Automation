//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.sitegen.page.customforms;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;
import com.medfusion.common.utils.IHGUtil;

public class ManageYourFormsPage extends BasePageObject {

	@FindBy(xpath = "//a[@class='action' and text() = 'Publish']")
	private List<WebElement> xpath_publishLinks;
	public static final String FORMROW_IDENTIFIER = "(//fieldset)[%d]//tr[(@class='dark-row' or @class='light-row') and contains(descendant::td/text(),'%s')]";

	public ManageYourFormsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public void unpublishFormsNamedLike(String partOfFormName) {
		WebElement foundRow = getRowOfPublishedFormNamedLike(partOfFormName);
		while (foundRow != null) {
			foundRow.findElement(By.xpath("((./td)[4]/a)[3]")).click();
			driver.switchTo().alert().accept();
			foundRow = getRowOfPublishedFormNamedLike(partOfFormName);
		}
	}

	private WebElement getRowOfPublishedFormNamedLike(String partOfFormName) {
		try {
			WebElement formRow = driver.findElement(By.xpath(String.format(FORMROW_IDENTIFIER, 1, partOfFormName)));
			return formRow;
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	public void deleteFormsNamedLike(String partOfFormName) {
		WebElement foundRow = getRowOfUnPublishedFormNamedLike(partOfFormName);
		while (foundRow != null) {
			foundRow.findElement(By.xpath("((./td)[3]/a)[3]")).click();
			driver.switchTo().alert().accept();
			foundRow = getRowOfUnPublishedFormNamedLike(partOfFormName);
		}
	}

	private WebElement getRowOfUnPublishedFormNamedLike(String partOfFormName) {
		try {
			WebElement formRow = driver.findElement(By.xpath(String.format(FORMROW_IDENTIFIER, 2, partOfFormName)));
			return formRow;
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	public boolean checkForPublishedPage(String formTitle) throws Exception {

		IHGUtil.PrintMethodName();
		// driver.switchTo().defaultContent();

		Boolean isPresent = false;
		String xpath_Published = ".//fieldset//strong[text() = 'Published Forms']/ancestor::fieldset/table/tbody/tr[@class='dark-row' or @class='light-row' ]";
		List<WebElement> allList = driver.findElements(By.xpath(xpath_Published));
		for (WebElement list : allList) {
			try {
				if (list.findElement(By.className("html-control-label-left")).getText().contains(formTitle)) {
					isPresent = true;
					break;
				}
			} catch (Exception e) {
				log("Couldn't find the " + formTitle);
			}
		}
		return isPresent;
	}

	public void unPublishThepublishedForm(String formTitle) throws Exception {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);
		log("Check for published custom form is present in Published Form table and unpublish it");

		String xpath_Published = "//fieldset//strong[text() = 'Published Forms']/ancestor::fieldset/table/tbody/tr[@class='dark-row' or @class='light-row' ]";
		List<WebElement> allList = driver.findElements(By.xpath(xpath_Published));
		for (WebElement list : allList) {
			try {
				if (list.findElement(By.className("html-control-label-left")).getText().contains(formTitle)) {
					list.findElement(By.linkText("Unpublish")).click();
					driver.switchTo().alert().accept();
				}
			} catch (Exception e) {
				// Do Nothing
			}
		}

	}

	public boolean isUnPublished(String formTitle) {
		Boolean isPresent = false;
		String xpath_Published = "//fieldset//strong[text() = 'Published Forms']/ancestor::fieldset/table/tbody/tr[@class='dark-row' or @class='light-row' ]";
		List<WebElement> allList = driver.findElements(By.xpath(xpath_Published));
		for (WebElement list : allList) {
			try {
				if (list.findElement(By.className("html-control-label-left")).getText().contains(formTitle)) {
					if (list.findElement(By.linkText("Unpublish")) == null) {
						isPresent = true;
					} else {
						isPresent = false;
					}
				}
			} catch (Exception e) {
				// Do nothing
			}
		}
		return isPresent;
	}

	public void deleteUnpublishedForm(String formTitle) throws Exception {
		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);
		String xpath_UnPublished = "//fieldset//strong[text() = 'Unpublished Forms']/ancestor::fieldset/table/tbody/tr[@class='dark-row' or @class='light-row' ]";
		List<WebElement> allList = driver.findElements(By.xpath(xpath_UnPublished));
		for (WebElement list : allList) {
			try {
				if (list.findElement(By.className("html-control-label-left")).getText().contains(formTitle)) {
					list.findElement(By.linkText("Delete")).click();
					driver.switchTo().alert().accept();
				} else {
					continue;
				}
			} catch (Exception e) {
				// Do nothing
			}
		}
	}

	public CustomFormPreviewPage clickOnPublishedFormPreviewLink(String formTitle) throws Exception {
		IHGUtil.PrintMethodName();
		// SitegenlUtil.setDefaultFrame(driver);
		log("Check for published custom form is present in UnPublished Form table and then click on delete link ");
		String xpath_Published = "//fieldset//strong[text() = 'Published Forms']/ancestor::fieldset/table/tbody/tr[@class='dark-row' or @class='light-row' ]";

		List<WebElement> allList = driver.findElements(By.xpath(xpath_Published));
		for (WebElement list : allList) {
			try {
				if (list.findElement(By.className("html-control-label-left")).getText().contains(formTitle)) {
					list.findElement(By.linkText("Preview")).click();
					log("clicked on preview");
				} else {
					continue;
				}
			} catch (Exception e) {
				// Do nothing
			}
		}
		return PageFactory.initElements(driver, CustomFormPreviewPage.class);
	}

	public CustomFormPreviewPage clickOnUnpublishedFormPreviewLink(String formTitle) throws Exception {
		IHGUtil.PrintMethodName();
		// SitegenlUtil.setDefaultFrame(driver);
		log("Check for published custom form is present in UnPublished Form table and then click on delete link ");

		String xpath_UnPublished = "//fieldset//strong[text() = 'Unpublished Forms']/ancestor::fieldset/table/tbody/tr[@class='dark-row' or @class='light-row' ]";
		List<WebElement> allList = driver.findElements(By.xpath(xpath_UnPublished));
		for (WebElement list : allList) {
			try {
				if (list.findElement(By.className("html-control-label-left")).getText().contains(formTitle)) {
					list.findElement(By.linkText("Preview")).click();
					log("clicked on preview");
				} else {
					continue;
				}
			} catch (Exception e) {
				// Do nothing
			}
		}
		Thread.sleep(5000);
		return PageFactory.initElements(driver, CustomFormPreviewPage.class);
	}

	public void clickOnUnpublishedFormPublishLink(String formTitle) throws Exception {
		IHGUtil.PrintMethodName();
		// SitegenlUtil.setDefaultFrame(driver);
		log("Check for published custom form is present in UnPublished Form table and then click on delete link ");

		String xpath_UnPublished = "//fieldset//strong[text() = 'Unpublished Forms']/ancestor::fieldset/table/tbody/tr[@class='dark-row' or @class='light-row' ]";
		List<WebElement> allList = driver.findElements(By.xpath(xpath_UnPublished));
		for (WebElement list : allList) {
			try {
				if (list.findElement(By.className("html-control-label-left")).getText().contains(formTitle)) {
					list.findElement(By.linkText("Publish")).click();
					log("clicked on preview");
				} else {
					continue;
				}
			} catch (Exception e) {
				// Do nothing
			}
		}
	}

}
