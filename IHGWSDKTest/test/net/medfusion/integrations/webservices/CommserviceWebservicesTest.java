package net.medfusion.integrations.webservices;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.medfusion.integrations.webservices.commservice.CommserviceServiceLocator;
import net.medfusion.integrations.webservices.commservice.CommserviceSoapBindingStub;
import net.medfusion.integrations.webservices.framework.SystemConfigProperties;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CommserviceWebservicesTest {

    private static SimpleDateFormat tsdf = new SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SS");
    private CommserviceServiceLocator locator1 = null;
    private CommserviceSoapBindingStub service1 = null;
//    private BufferedWriter buff = null;

    @BeforeClass
    public void oneTimeSetUp() throws Exception {
        // one-time initialization code
        System.out.println("@BeforeClass - oneTimeSetUp");
        locator1 = new CommserviceServiceLocator();

        locator1.setcommserviceEndpointAddress(SystemConfigProperties.URL);

        service1 = (CommserviceSoapBindingStub) locator1.getcommservice();

        service1.setUsername(SystemConfigProperties.username);
        service1.setPassword(SystemConfigProperties.password);

    }

    @AfterClass
    public void oneTimeTearDown() throws Exception {
        // one-time cleanup code
        System.out.println("@AfterClass - oneTimeTearDown");

    }

    @Test
    public void testpostNewCommunication() {

        try {
            System.out.println("Testing the Post New Communication...");

            service1.postNewCommunication("Saurav", "Ghosh", "05231987", null,
                    "A message", "This is a message to send",
                    tsdf.format(new Date()), 8);
            // lineBreak();
        } catch (Exception e) {
            e.printStackTrace(System.out);

        }
    }
}
