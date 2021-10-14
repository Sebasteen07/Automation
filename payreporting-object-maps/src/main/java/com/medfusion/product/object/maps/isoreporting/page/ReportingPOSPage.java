//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved
package com.medfusion.product.object.maps.isoreporting.page;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.medfusion.common.utils.IHGUtil;

public class ReportingPOSPage extends ReportingPOSVCSMenu {

	public ReportingPOSPage(WebDriver driver) {
		super(driver);
	}

    @FindBy(how = How.ID, using = "downloadLink")
    private WebElement installerLink;

    @FindBy(how = How.ID, using = "amount")
    private WebElement amount;

    @FindBy(how = How.ID, using = "consumerName")
    private WebElement consumerName;

    @FindBy(how = How.ID, using = "consumerAccountNumber")
    private WebElement consumerAccountNumber;

    public boolean assessPageElements() {
        ArrayList<WebElement> allElements = new ArrayList<WebElement>();
        allElements.add(installerLink);
        allElements.add(amount);
        allElements.add(consumerName);
        allElements.add(consumerAccountNumber);
        return new IHGUtil(driver).assessAllPageElements(allElements, this.getClass());
    }


}
