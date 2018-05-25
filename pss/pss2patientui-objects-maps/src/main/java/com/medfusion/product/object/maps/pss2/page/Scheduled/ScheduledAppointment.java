package com.medfusion.product.object.maps.pss2.page.Scheduled;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;

public class ScheduledAppointment extends PSS2MainPage {

	
	@FindBy(how = How.ID, using = "gotodashboard")
	private WebElement buttonBackToAppointmentList;

	@FindBy(how = How.CSS, using = ".btn-link")
	private WebElement linkAddToCalander;

	@FindAll({@FindBy(css = "value-classschedule")})
	public List<WebElement> flowWizardList;

	public ScheduledAppointment(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		// webElementsList.add(flowWizardList.get(0));
		webElementsList.add(linkAddToCalander);
		webElementsList.add(buttonBackToAppointmentList);
		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}

	public void downloadCalander() {
		linkAddToCalander.click();
	}

}
