
package net.medfusion.integrations.webservices.framework;

import java.io.IOException;
import java.util.Properties;




public class TestPropertiesPlaceholderResolver {
       
    //~ Methods --------------------------------------------------------------------------------------------------------



    public void loadProperties(Properties props)
            throws IOException {
        /* read test properties file */
        String propertiesFile = System.getProperty("webservicesclient.properties");

        if (propertiesFile != null) {
            TestPropertiesLoader loader = new TestPropertiesLoader(propertiesFile, props);
            loader.loadProperties();
        } else {
            System.err.println("WARNING: No test.propertiesfile was specified");
        } // end if-else
    }
} // end class TestPropertiesPlaceholderResolver
