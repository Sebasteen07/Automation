package com.medfusion.product.patientportal2.implementedExternals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentsPage.JalapenoAppointmentsPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.patientportal2.flows.IAppointments;
import com.medfusion.product.patientportal2.pojo.Appointment;
import com.medfusion.product.patientportal2.pojo.Jalapeno;
import com.medfusion.product.patientportal2.pojo.PatientInfo;

public class Appointments implements IAppointments {

    @Override
    public List<Appointment> getAllAppointmentsForPatient(WebDriver driver, Jalapeno portal, PatientInfo patientInfo)
            throws ParseException {
        JalapenoAppointmentsPage appointmentsPage = logInAndGoToAppointmentsPage(driver, portal.url,
                patientInfo.username, patientInfo.password);
        List<Appointment> allAppointments = parseWebElementsToAppointments(appointmentsPage.getAppointments());
        appointmentsPage.goToPastAppointments();
        allAppointments.addAll(parseWebElementsToAppointments(appointmentsPage.getAppointments()));

        return allAppointments;
    }

    @Override
    public List<Appointment> getUpcomingAppointmentsForPatient(WebDriver driver, Jalapeno portal,
            PatientInfo patientInfo) throws ParseException {
        JalapenoAppointmentsPage appointmentsPage = logInAndGoToAppointmentsPage(driver, portal.url,
                patientInfo.username, patientInfo.password);

        return parseWebElementsToAppointments(appointmentsPage.getAppointments());
    }

    @Override
    public List<Appointment> getPastAppointmentsForPatient(WebDriver driver, Jalapeno portal, PatientInfo patientInfo)
            throws ParseException {
        JalapenoAppointmentsPage appointmentsPage = logInAndGoToAppointmentsPage(driver, portal.url,
                patientInfo.username, patientInfo.password);
        appointmentsPage.goToPastAppointments();

        return parseWebElementsToAppointments(appointmentsPage.getAppointments());
    }

    private JalapenoAppointmentsPage logInAndGoToAppointmentsPage(WebDriver driver, String portalURL, String username,
            String password) throws ParseException {
        JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, portalURL);
        JalapenoHomePage homePage = loginPage.login(username, password);

        // TODO: use method for getting to this page through button after the button is added
        return homePage.goToAppointmentsPage(portalURL);
    }

    private List<Appointment> parseWebElementsToAppointments(List<WebElement> elements) throws ParseException {
        List<Appointment> appointments = new ArrayList<Appointment>();
        for (WebElement element : elements) {
            DateFormat df = new SimpleDateFormat("M/d/yy h:mm a", Locale.US);
            Date date = df.parse(element.findElement(By.xpath("./div[1]")).getText());
            String provider = element.findElement(By.xpath("./div[2]/div[1]")).getText();
            String location = element.findElement(By.xpath("./div[2]/div[2]")).getText();
            boolean canceled = "Cancelled".equals(element.findElement(By.xpath("./div[3]")).getText());
            appointments.add(new Appointment(date, provider, location, canceled));
        }
        return appointments;
    }

}
