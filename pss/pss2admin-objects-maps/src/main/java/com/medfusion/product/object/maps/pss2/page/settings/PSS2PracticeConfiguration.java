package com.medfusion.product.object.maps.pss2.page.settings;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class PSS2PracticeConfiguration extends SettingsTab {

	@FindBy(how = How.XPATH, using = "/html/body/app/layout/div/main/div[2]/div/div/div/section/div/div/div[2]/div[1]/div[1]/div[1]/img")
	private WebElement clientlogo;

	@FindBy(how = How.XPATH, using = "/html/body/app/layout/nav[1]/div/div[1]/header/a/img")
	private WebElement companyLogo;

	@FindBy(how = How.CSS, using = ".badge.badge-primary")
	private WebElement practiceText;

	@FindAll({@FindBy(css = ".nav-chang-color")})
	public List<WebElement> colorType;

	@FindBy(how = How.NAME, using = "zone")
	private WebElement clientTimeZone;

	public PSS2PracticeConfiguration(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		return true;
	}

	public void clickOnLogo() {
		companyLogo.click();
	}

	public String checkLogoLink() {
		return clientlogo.getAttribute("src");
	}

	public String practiceIDLinkText() {
		return practiceText.getText();
	}

	public String clientTimeZone() {
		return clientTimeZone.getAttribute("ng-reflect-model");
	}

	public String getSelectedColor() {
		log("color length : " + colorType.size());
		for (int i = 0; i < colorType.size(); i++) {
			if (colorType.get(i).getAttribute("ng-reflect-ng-class").contains("active")) {
				return colorType.get(i).getCssValue("background-color");
			}
		}
		log("active color not found.");
		return null;
	}
}