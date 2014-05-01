package net.medfusion.integrations.webservices.framework;

public class SystemConfigProperties extends TestProperties {
    // ~ Static fields/initializers
    // -------------------------------------------------------------------------------------

    /**
     * These are some constants that match up with what exists in the Demo site.
     */
    public static final int WSFM_PRACTICEID = getInteger("WSFM_PRACTICEID");
    public static final int WSFM_LOCATIONID1 = getInteger("WSFM_LOCATIONID1");
    public static final int WSFM_LOCATIONID2 = getInteger("WSFM_LOCATIONID2");
    public static final int WSFM_LOCATIONID3 = getInteger("WSFM_LOCATIONID3");
    public static final int WSFM_STAFFID1 = getInteger("WSFM_STAFFID1");
    public static final int WSFM_STAFFID2 = getInteger("WSFM_STAFFID2");
    public static final int WSFM_MEMBERID1 = getInteger("WSFM_MEMBERID1");
    public static final int WSFM_MEMBERID2 = getInteger("WSFM_MEMBERID2");
    public static final int WSFM_MEMBERID3 = getInteger("WSFM_MEMBERID3");

    public static final int WSFM_MEMBERLOCKEDID1 = getInteger("WSFM_MEMBERLOCKEDID1");
    public static final int WSFM_MEMBERLOCKEDID2 = getInteger("WSFM_MEMBERLOCKEDID2");
    public static final String WSFM_EXTERNALID1 = getProperty("WSFM_EXTERNALID1");
    public static final String WSFM_EXTERNALID2 = getProperty("WSFM_EXTERNALID2");
    public static final String WSFM_INTEGRATIONID = getProperty("WSFM_INTEGRATIONID");

    public static final int WSCARD_PRACTICEID = getInteger("WSCARD_PRACTICEID");
    public static final int WSCARD_LOCATIONID1 = getInteger("WSCARD_LOCATIONID1");
    public static final int WSCARD_STAFFID1 = getInteger("WSCARD_STAFFID1");
    public static final int WSCARD_STAFFID2 = getInteger("WSCARD_STAFFID2");
    public static final int WSCARD_MEMBERID1 = getInteger("WSCARD_MEMBERID1");
    public static final int WSCARD_MEMBERID2 = getInteger("WSCARD_MEMBERID2");

    public static final int EXTERNALSYSTEM_TESTPMS = getInteger("EXTERNALSYSTEM_TESTPMS");
    public static final int EXTERNALSYSTEM_TESTEMR = getInteger("EXTERNALSYSTEM_TESTEMR");

    public static final String TESTFILE = getProperty("TEST_FILE");
    public static final String STATEMENTFILE = getProperty("STATEMENT_FILE");

    public static final String UNLOCKEMAIL = "noreply@medfusion.net";

    public static String URL = getProperty("URL");
    public static String intEngineURL = getProperty("IEURL");
    public static String username = getProperty("username");
    public static String password = getProperty("password");

} // end class SystemConfigProperties
