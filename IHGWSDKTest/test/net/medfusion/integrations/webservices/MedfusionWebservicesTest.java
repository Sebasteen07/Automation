package net.medfusion.integrations.webservices;

import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import net.medfusion.encounter.ws.WebServiceConstants;
import net.medfusion.integrations.webservices.common.exceptions.WebServiceExceptionInvalidParameter;
import net.medfusion.integrations.webservices.common.query.Criteria;
import net.medfusion.integrations.webservices.commservice.CommserviceServiceLocator;
import net.medfusion.integrations.webservices.commservice.CommserviceSoapBindingStub;
import net.medfusion.integrations.webservices.framework.SystemConfigProperties;
import net.medfusion.integrations.webservices.medfusion.MedfusionServiceLocator;
import net.medfusion.integrations.webservices.medfusion.MedfusionSoapBindingStub;
import net.medfusion.integrations.webservices.medfusion.objects.ApptReq;
import net.medfusion.integrations.webservices.medfusion.objects.CFF;
import net.medfusion.integrations.webservices.medfusion.objects.CPTCode;
import net.medfusion.integrations.webservices.medfusion.objects.Comm;
import net.medfusion.integrations.webservices.medfusion.objects.CommAttachment;
import net.medfusion.integrations.webservices.medfusion.objects.CommDetail;
import net.medfusion.integrations.webservices.medfusion.objects.CustomForm;
import net.medfusion.integrations.webservices.medfusion.objects.DiagCode;
import net.medfusion.integrations.webservices.medfusion.objects.EM;
import net.medfusion.integrations.webservices.medfusion.objects.EMField;
import net.medfusion.integrations.webservices.medfusion.objects.EMFieldValue;
import net.medfusion.integrations.webservices.medfusion.objects.EMRecipient;
import net.medfusion.integrations.webservices.medfusion.objects.EMStatus;
import net.medfusion.integrations.webservices.medfusion.objects.EMTemplate;
import net.medfusion.integrations.webservices.medfusion.objects.ES;
import net.medfusion.integrations.webservices.medfusion.objects.Field;
import net.medfusion.integrations.webservices.medfusion.objects.Fields;
import net.medfusion.integrations.webservices.medfusion.objects.Location;
import net.medfusion.integrations.webservices.medfusion.objects.MemberLocked;
import net.medfusion.integrations.webservices.medfusion.objects.MemberMap;
import net.medfusion.integrations.webservices.medfusion.objects.NameValuePair;
import net.medfusion.integrations.webservices.medfusion.objects.NameValuePairComposite;
import net.medfusion.integrations.webservices.medfusion.objects.OBP;
import net.medfusion.integrations.webservices.medfusion.objects.Pharmacy;
import net.medfusion.integrations.webservices.medfusion.objects.Practice;
import net.medfusion.integrations.webservices.medfusion.objects.RouteCode;
import net.medfusion.integrations.webservices.medfusion.objects.RxRefillReq;
import net.medfusion.integrations.webservices.medfusion.objects.RxRefillReqEntry;
import net.medfusion.integrations.webservices.medfusion.objects.SA2;
import net.medfusion.integrations.webservices.medfusion.objects.SigCode;
import net.medfusion.integrations.webservices.medfusion.objects.Staff;
import net.medfusion.integrations.webservices.medfusion.objects.Statement;
import net.medfusion.integrations.webservices.medfusion.objects.VOV;
import net.medfusion.integrations.webservices.medfusion.objects.WebServiceReturnComposite;
import net.medfusion.integrations.webservices.utils.WebserviceUtils;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MedfusionWebservicesTest {

    private MedfusionServiceLocator locator = null;
    private MedfusionSoapBindingStub service = null;

    private CommserviceServiceLocator locator1 = null;
    private CommserviceSoapBindingStub service1 = null;
    private BufferedWriter buff = null;

    @BeforeClass
    public void oneTimeSetUp() throws Exception {
        // one-time initialization code
        System.out.println("@BeforeClass - oneTimeSetUp");
        locator = new MedfusionServiceLocator();

        locator.setmedfusionEndpointAddress(SystemConfigProperties.URL);

        service = (MedfusionSoapBindingStub) locator.getmedfusion();

        service.setUsername(SystemConfigProperties.username);
        service.setPassword(SystemConfigProperties.password);
        service.testConnection();
        if (!new File("dump.txt").exists())
            buff = new BufferedWriter(new FileWriter(new File("dump.txt")));
        else {
            new File("dump.txt").delete();
            buff = new BufferedWriter(new FileWriter(new File("dump.txt")));
        }

    }

    @AfterClass
    public void oneTimeTearDown() throws Exception {
        // one-time cleanup code
        System.out.println("@AfterClass - oneTimeTearDown");
        buff.close();
    }

    @Test
    public void testConnection() {

        try {
            System.out
                    .println("Testing the connection to the Medfusion web service...");
            service.testConnection();
            // lineBreak();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testPracticeLocationWebServices() {

        try {
            System.out.println("Get all Locations to which we have access");
            Location[] locations = service.getAllLocations();
            AssertJUnit.assertNotNull(locations);

            System.out.println("Get all practices to which we have access");
            Practice[] practices = service.getAllPractices();
            AssertJUnit.assertNotNull(practices);

            /*
             * System.out.println("Get all locations for a particular practice");
             * Location[] locations =
             * service.getLocationsForPractice(SystemConfigProperties
             * .WSFM_PRACTICEID); WebserviceUtils.dumpResults(locations);
             * 
             * System.out.println(
             * "Get all of the practices that a particular Medfusion user exists in"
             * ); Practice[] practicesForMember =
             * service.getPracticesForMedfusionId
             * (SystemConfigProperties.WSFM_MEMBERID1);
             * WebserviceUtils.dumpResults(practicesForMember);
             * 
             * System.out.println("Get a particular practice"); Practice
             * practice = service.getPractice(WSCARD_PRACTICEID);
             * System.out.println("Practice found: " + practice + "\n");
             * 
             * System.out.println("Get a particular location"); Location
             * location = service.getLocation(WSFM_LOCATIONID1);
             * System.out.println("Location found: " + location + "\n");
             */
        } catch (WebServiceExceptionInvalidParameter e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Test methods that are related to MemberMaps and Insurance
     * 
     * @param service
     */
    @Test
    public void testMemberWebServices() {
        try {
            // This is the kind of code you would run on a regular basis to
            // synchronize Medfusion user data with an external system
            System.out
                    .println("Find specific fields of all users created in the last 1 months ordered in a specific way");
            Calendar cal = new GregorianCalendar();
            cal.add(GregorianCalendar.MONTH, -1);
            Criteria[] memberCriteria = new Criteria[1];
            memberCriteria[0] = new Criteria(
                    WebServiceConstants.FIELD_MEMBER_DATECREATED,
                    WebServiceConstants.OPERATION_GREATERTHAN, cal.getTime());
            String[] memberFields = new String[4];
            memberFields[0] = WebServiceConstants.FIELD_MEMBER_FIRSTNAME;
            memberFields[1] = WebServiceConstants.FIELD_MEMBER_LASTNAME;
            memberFields[2] = WebServiceConstants.FIELD_MEMBER_DATECREATED;
            memberFields[3] = WebServiceConstants.FIELD_MEMBER_ID;
            String[] orderByFields = new String[3];
            orderByFields[0] = WebServiceConstants.FIELD_MEMBER_LASTNAME;
            orderByFields[1] = WebServiceConstants.FIELD_MEMBER_FIRSTNAME;
            orderByFields[2] = WebServiceConstants.FIELD_MEMBER_ID;
            MemberMap[] memberResults = service.getMemberMapForUsersByCriteria(
                    WebServiceConstants.FIELD_INT_NONE, memberFields,
                    memberCriteria, orderByFields);
            WebserviceUtils.dumpResults(memberResults);
            /*
             * System.out.println("Find date updated of a particular Member");
             * String[] memberUpdatedFields = new String[5];
             * memberUpdatedFields[0] =
             * WebServiceConstants.FIELD_MEMBER_FIRSTNAME;
             * memberUpdatedFields[1] =
             * WebServiceConstants.FIELD_MEMBER_LASTNAME; memberUpdatedFields[2]
             * = WebServiceConstants.FIELD_MEMBER_DATECREATED;
             * memberUpdatedFields[3] = WebServiceConstants.FIELD_MEMBER_ID;
             * memberUpdatedFields[4] =
             * WebServiceConstants.FIELD_MEMBER_DATEUPDATED; MemberMap
             * memberUpdatedResult =
             * service.getMemberMapForUser(memberUpdatedFields,
             * SystemConfigProperties.WSFM_MEMBERID2);
             * System.out.println("Date Updated result: " +
             * memberUpdatedResult);
             * 
             * System.out.println(
             * "Find communication preference of a particular Member"); String[]
             * commPrefFields = new String[6]; commPrefFields[0] =
             * WebServiceConstants.FIELD_MEMBER_FIRSTNAME; commPrefFields[1] =
             * WebServiceConstants.FIELD_MEMBER_LASTNAME; commPrefFields[2] =
             * WebServiceConstants.FIELD_MEMBER_ID; commPrefFields[3] =
             * WebServiceConstants.FIELD_PRACTICEMEMBER_COMMPREF;
             * commPrefFields[4] =
             * WebServiceConstants.FIELD_PRACTICEMEMBER_EMAILPREF;
             * commPrefFields[5] =
             * WebServiceConstants.FIELD_PRACTICEMEMBER_PROVIDERPREF; MemberMap
             * commPrefResult = service.getMemberMapForUser(commPrefFields,
             * SystemConfigProperties.WSFM_MEMBERID2);
             * System.out.println(commPrefResult);
             * 
             * 
             * System.out.println(
             * "Find preferred provider preference of a particular Member");
             * String[] memberPrefProviderFields = new String[4];
             * memberPrefProviderFields[0] =
             * WebServiceConstants.FIELD_MEMBER_FIRSTNAME;
             * memberPrefProviderFields[1] =
             * WebServiceConstants.FIELD_MEMBER_LASTNAME;
             * memberPrefProviderFields[2] =
             * WebServiceConstants.FIELD_MEMBER_ID; memberPrefProviderFields[3]
             * = WebServiceConstants.FIELD_PRACTICEMEMBER_PROVIDERPREF;
             * MemberMap providerPrefResult =
             * service.getMemberMapForUser(memberPrefProviderFields,
             * SystemConfigProperties.WSFM_MEMBERID2);
             * System.out.println(providerPrefResult);
             * 
             * System.out.println(
             * "Get insurance plans for a list of Medfusion users"); int[]
             * insMemberIds = new int[6]; insMemberIds[0] =
             * SystemConfigProperties.WSFM_MEMBERID1; insMemberIds[1] =
             * SystemConfigProperties.WSFM_MEMBERID2; insMemberIds[2] =
             * WSFM_MEMBERID3; insMemberIds[3] = 975; insMemberIds[4] = 976;
             * insMemberIds[5] = 977; Insurance2[] insurancesMult =
             * service.getInsurance2SForUsersByMedfusionIds(insMemberIds);
             * dumpResults(insurancesMult);
             */
        } catch (WebServiceExceptionInvalidParameter e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testMemberToUnlockWebServices() {
        try {
            System.out
                    .println("Get all Member Objects from the Last Month who have the locked field set to 'True'");
            Calendar cal = new GregorianCalendar();
            cal.add(GregorianCalendar.MONTH, -1);
            Criteria[] memberCriteria = new Criteria[2];
            memberCriteria[0] = new Criteria(
                    WebServiceConstants.FIELD_MEMBER_DATECREATED,
                    WebServiceConstants.OPERATION_GREATERTHAN, cal.getTime());
            memberCriteria[1] = new Criteria(
                    WebServiceConstants.FIELD_MEMBER_LOCKED,
                    WebServiceConstants.OPERATION_EQUALS, new Boolean(true));
            String[] memberFields = new String[4];
            memberFields[0] = WebServiceConstants.FIELD_MEMBER_FIRSTNAME;
            memberFields[1] = WebServiceConstants.FIELD_MEMBER_LASTNAME;
            memberFields[2] = WebServiceConstants.FIELD_MEMBER_DATECREATED;
            memberFields[3] = WebServiceConstants.FIELD_MEMBER_ID;
            String[] orderByFields = new String[3];
            orderByFields[0] = WebServiceConstants.FIELD_MEMBER_LASTNAME;
            orderByFields[1] = WebServiceConstants.FIELD_MEMBER_FIRSTNAME;
            orderByFields[2] = WebServiceConstants.FIELD_MEMBER_ID;
            MemberMap[] memberResults = service.getMemberMapForUsersByCriteria(
                    WebServiceConstants.FIELD_INT_NONE, memberFields,
                    memberCriteria, orderByFields);
            assertNotNull(memberResults);
            WebserviceUtils.dumpResults(memberResults);
        } catch (WebServiceExceptionInvalidParameter e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Test methods related to MemberLocked Creation and Unlocking
     * 
     * @param service
     */
    @Test
    public void testMemberLockedWebServices() throws Exception {
        try {
            System.out
                    .println("Get all MemberLocked Objects from the Last Month");
            Calendar cal = new GregorianCalendar();
            cal.add(GregorianCalendar.MONTH, -2);
            Criteria[] mlCriteria = new Criteria[1];
            mlCriteria[0] = new Criteria(
                    WebServiceConstants.FIELD_MEMBERLOCKED_DATECREATED,
                    WebServiceConstants.OPERATION_GREATERTHAN, cal.getTime());
            MemberLocked[] resultMemberLockeds = service
                    .getMemberLockedsByCriteria(
                            WebServiceConstants.FIELD_INT_NONE, mlCriteria);
            assertNotNull(resultMemberLockeds);
        } catch (WebServiceExceptionInvalidParameter e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Test methods that are related to ApptReqs.
     * 
     * @param service
     */
    @Test
    public void testApptReqWebServices() throws Exception {
        try {
            System.out.println("Get all 'Open' Appointment Requests");
            Criteria[] arCriteria = new Criteria[1];
            arCriteria[0] = new Criteria(WebServiceConstants.FIELD_AR_STATUS,
                    WebServiceConstants.OPERATION_EQUALS,
                    WebServiceConstants.FIELD_AR_STATUS_OPEN);
            ApptReq[] resultApptReqs = service.getApptReqsByCriteria(
                    WebServiceConstants.FIELD_INT_NONE, arCriteria);
            WebserviceUtils.dumpResults(resultApptReqs);
            assertTrue(resultApptReqs.length > 0);

            if (resultApptReqs != null && resultApptReqs.length > 0) {
                System.out
                        .println("Set first open appointment as processed externally");
                ApptReq appt = resultApptReqs[0];
                Integer apptReqId = (Integer) appt
                        .getFieldValue(WebServiceConstants.FIELD_AR_ID);
                int[] arIds = new int[1];
                arIds[0] = apptReqId.intValue();
                service.externallyProcessApptReqs(arIds,
                        SystemConfigProperties.WSFM_STAFFID1);

                // Find it again and dump it again to verify its status has been
                // set
                System.out
                        .println("Find that ApptReq again and show its information");
                Criteria[] arCriteria3 = new Criteria[1];
                arCriteria3[0] = new Criteria(WebServiceConstants.FIELD_AR_ID,
                        WebServiceConstants.OPERATION_EQUALS, apptReqId);
                ApptReq[] arResults2 = service.getApptReqsByCriteria(
                        WebServiceConstants.FIELD_INT_NONE, arCriteria3);
                WebserviceUtils.dumpResults(arResults2);
            }

            System.out
                    .println("Find appointment requests created in the last 6 Month for a particular practice.");
            Calendar cal = new GregorianCalendar();
            cal.add(GregorianCalendar.MONTH, -6);
            Criteria[] criteria = new Criteria[2];
            criteria[0] = new Criteria(
                    WebServiceConstants.FIELD_AR_REQUESTCREATIONDATE,
                    WebServiceConstants.OPERATION_GREATERTHAN, cal.getTime());
            criteria[1] = new Criteria(WebServiceConstants.FIELD_AR_PRACTICEID,
                    WebServiceConstants.OPERATION_EQUALS, new Integer(
                            SystemConfigProperties.WSFM_PRACTICEID));
            ApptReq[] results = service.getApptReqsByCriteria(
                    WebServiceConstants.FIELD_INT_NONE, criteria);
            WebserviceUtils.dumpResults(results);

            if (results != null && results.length > 0) {
                System.out
                        .println("Set appointment request scheduled date for the first one.");
                ApptReq appt = results[0];
                Integer apptReqId = (Integer) appt
                        .getFieldValue(WebServiceConstants.FIELD_AR_ID);
                SimpleDateFormat sdf = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                Date scheduledDate = sdf.parse("2006-11-01 12:30:00");
                cal = new GregorianCalendar();
                cal.setTime(scheduledDate);

                // this makes the STAFFID1 person the setter of this scheduled
                // date
                service.setApptReqScheduledDate(apptReqId.intValue(),
                        SystemConfigProperties.WSFM_STAFFID1, cal);

                // To "Close" the appointment, the line below should be called
                // service.setApptReqStatus(apptReqId.intValue(), WSFM_STAFFID1,
                // WebServiceConstants.FIELD_AR_STATUS_APPROVED);

                // Find it again and dump it again to verify its scheduled date
                // has been set
                System.out
                        .println("Find that ApptReq again and show its information");
                Criteria[] arCriteria3 = new Criteria[1];
                arCriteria3[0] = new Criteria(WebServiceConstants.FIELD_AR_ID,
                        WebServiceConstants.OPERATION_EQUALS, apptReqId);
                ApptReq[] arResults2 = service.getApptReqsByCriteria(
                        WebServiceConstants.FIELD_INT_NONE, arCriteria3);
                WebserviceUtils.dumpResults(arResults2);
            }
        } catch (WebServiceExceptionInvalidParameter e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Test Creation of MemberLocked Object with Minimum Requirements
     * 
     * @param service
     */
    @Test
    public void testMemberLockedCreateMinFieldsWebServices() {
        final SimpleDateFormat MMddyyyyFormat = new SimpleDateFormat(
                "MM/dd/yyyy");

        try {
            System.out
                    .println("Create a MemberLocked Object with the Minimum Required net.medfusion.integrations.webservices.medfusion.objects.Fields");
            MemberLocked memberLocked = new MemberLocked();

            Vector fieldVector = new Vector();

            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_PRACTICEMEMBER_PRACTICEID,
                            new Integer(SystemConfigProperties.WSFM_PRACTICEID)));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_SEX, new Integer(
                                    WebServiceConstants.FIELD_MEMBER_SEX_MALE)));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_LASTNAME,
                            "Jacobson"));

            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_EMAIL,
                            "test123@gmail.com"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_PRACTICEMEMBER_PATIENTID,
                            "PID12345"));

            Date dob = MMddyyyyFormat.parse("09/01/1961");
            Calendar birthdate = new GregorianCalendar();
            birthdate.setTime(dob);

            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_DOB, birthdate
                                    .getTime()));

            // add fields to MemberLocked
            net.medfusion.integrations.webservices.medfusion.objects.Fields theFields = new net.medfusion.integrations.webservices.medfusion.objects.Fields();
            theFields
                    .setFields((net.medfusion.integrations.webservices.medfusion.objects.Field[]) fieldVector
                            .toArray(new net.medfusion.integrations.webservices.medfusion.objects.Field[fieldVector
                                    .size()]));
            memberLocked.setFields(theFields);

            int memberLockedId = service.createMemberLocked(memberLocked);
            System.out.println("MemberLocked object created with id: "
                    + memberLockedId);
            System.out
                    .println("Get the MemberLocked object we just created with id: "
                            + memberLockedId);

            Criteria[] mlCriteria = new Criteria[1];
            mlCriteria[0] = new Criteria(
                    WebServiceConstants.FIELD_MEMBERLOCKED_ID,
                    WebServiceConstants.OPERATION_EQUALS, new Integer(
                            memberLockedId));
            MemberLocked[] resultMemberLockeds = service
                    .getMemberLockedsByCriteria(
                            WebServiceConstants.FIELD_INT_NONE, mlCriteria);
            WebserviceUtils.dumpResults(resultMemberLockeds);

            // update that MemberLocked, remember to add the Id we just got back
            // to the
            // net.medfusion.integrations.webservices.medfusion.objects.Fields
            // Vector
            System.out
                    .println("Whoops, I wanted to add some more information before we unlocked it.  Let's update that MemberLocked Object.");
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_ID,
                            new Integer(memberLockedId)));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_FIRSTNAME, "Mark"));

            theFields
                    .setFields((net.medfusion.integrations.webservices.medfusion.objects.Field[]) fieldVector
                            .toArray(new net.medfusion.integrations.webservices.medfusion.objects.Field[fieldVector
                                    .size()]));
            memberLocked.setFields(theFields);
            service.updateMemberLocked(memberLocked);
            System.out.println("MemberLocked object updated with id: "
                    + memberLockedId + ".  Let's take a look at it again.");
            resultMemberLockeds = service.getMemberLockedsByCriteria(
                    WebServiceConstants.FIELD_INT_NONE, mlCriteria);
            WebserviceUtils.dumpResults(resultMemberLockeds);

            System.out
                    .println("Now let's unlock that MemberLocked and send them an Unlock Email with a Random System Generated Code as the Unlock Key.");
            int newMemberId = service.unlockMemberLocked(memberLockedId,
                    SystemConfigProperties.WSFM_STAFFID1, "Mark", "Jacobson",
                    birthdate, SystemConfigProperties.UNLOCKEMAIL, "27707",
                    WebServiceConstants.PARM_KEYTYPE_RANDOMSYSTEMGENERATEDCODE,
                    null);

            System.out
                    .println("Get the Member Object of the MemberLocked that we just unlocked.");
            Criteria[] memberCriteria = new Criteria[1];
            memberCriteria[0] = new Criteria(
                    WebServiceConstants.FIELD_MEMBER_ID,
                    WebServiceConstants.OPERATION_EQUALS, new Integer(
                            newMemberId));
            String[] memberFields = new String[4];
            memberFields[0] = WebServiceConstants.FIELD_MEMBER_FIRSTNAME;
            memberFields[1] = WebServiceConstants.FIELD_MEMBER_LASTNAME;
            memberFields[2] = WebServiceConstants.FIELD_MEMBER_DATECREATED;
            memberFields[3] = WebServiceConstants.FIELD_MEMBER_ID;
            String[] orderByFields = new String[1];
            orderByFields[0] = WebServiceConstants.FIELD_MEMBER_ID;
            MemberMap[] memberResults = service.getMemberMapForUsersByCriteria(
                    WebServiceConstants.FIELD_INT_NONE, memberFields,
                    memberCriteria, orderByFields);
            WebserviceUtils.dumpResults(memberResults);

            System.out
                    .println("If we need to resend the Unlock Email to that Member, we need to call this method.");
            System.out
                    .println("Note: The previous method flipped the Member from a MemberLocked to a Member.");
            System.out
                    .println("Note: If the email was sent over 72 hours ago, the Unlock Key will be Regenerated according to the new KeyType specified");
            service.unlockMember(newMemberId,
                    SystemConfigProperties.WSFM_STAFFID1,
                    SystemConfigProperties.WSFM_PRACTICEID, "Mark", "Jacobson",
                    birthdate, SystemConfigProperties.UNLOCKEMAIL, "27707",
                    WebServiceConstants.PARM_KEYTYPE_LASTFOURDIGITSOFSSN,
                    "9999");

            System.out.println("Unlock Email re-sent to Member with ID : "
                    + newMemberId);
            lineBreak();
        } catch (WebServiceExceptionInvalidParameter e) {
            System.out.println("Error: " + e.getMessage());
            Assert.fail(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * This method tests creation of multiple MemberLocked objects in a single
     * call; also (optionally) sends each person the unlock invitation email.
     * The email body will be the standard version from TextConfig, or (if
     * provided) a custom email template & variables may be used.
     * 
     * @param service
     */
    @Test
    public void testCreateOrUpdateMultipleMemberLocked() {
        final SimpleDateFormat MMddyyyyFormat = new SimpleDateFormat(
                "MM/dd/yyyy");

        try {
            System.out
                    .println("entering testCreateOrUpdateMultipleMemberLocked()");

            MemberLocked memberLocked = new MemberLocked();
            Vector fieldVector = new Vector();
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_PRACTICEMEMBER_PRACTICEID,
                            new Integer(SystemConfigProperties.WSFM_PRACTICEID)));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_SEX, new Integer(
                                    WebServiceConstants.FIELD_MEMBER_SEX_MALE)));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_LASTNAME,
                            "Jacobson"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_FIRSTNAME, "Mark"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_EMAIL,
                            "dummyemail1@fake.net"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_HOMEPOSTALCODE,
                            "27605"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_DOB,
                            MMddyyyyFormat.parse("09/01/1960")));

            // to cause an update by MemberLocked Id, provide a MemberLocked Id
            // on the next line
            // fieldVector.add(new
            // net.medfusion.integrations.webservices.medfusion.objects.Field(WebServiceConstants.FIELD_MEMBERLOCKED_ID,
            // new Integer(100)));
            // to cause an update by Patient Id, provide it on the next line,
            // but do not provide a MemberLocked Id
            // fieldVector.add(new
            // net.medfusion.integrations.webservices.medfusion.objects.Field(WebServiceConstants.FIELD_PRACTICEMEMBER_PATIENTID,
            // "PID-001"));

            // add fields to MemberLocked
            net.medfusion.integrations.webservices.medfusion.objects.Fields theFields = new net.medfusion.integrations.webservices.medfusion.objects.Fields();
            theFields
                    .setFields((net.medfusion.integrations.webservices.medfusion.objects.Field[]) fieldVector
                            .toArray(new net.medfusion.integrations.webservices.medfusion.objects.Field[fieldVector
                                    .size()]));
            memberLocked.setFields(theFields);

            MemberLocked memberLocked2 = new MemberLocked();
            Vector fieldVector2 = new Vector();
            fieldVector2
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_PRACTICEMEMBER_PRACTICEID,
                            new Integer(SystemConfigProperties.WSFM_PRACTICEID)));
            fieldVector2
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_SEX, new Integer(
                                    WebServiceConstants.FIELD_MEMBER_SEX_MALE)));
            fieldVector2
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_LASTNAME,
                            "Jacobson2"));
            fieldVector2
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_FIRSTNAME, "Mark2"));
            fieldVector2
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_EMAIL,
                            "dummyemail2@fake.net"));
            fieldVector2
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_HOMEPOSTALCODE,
                            "27606"));
            fieldVector2
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_DOB,
                            MMddyyyyFormat.parse("09/02/1960")));
            // add fields to MemberLocked
            net.medfusion.integrations.webservices.medfusion.objects.Fields theFields2 = new net.medfusion.integrations.webservices.medfusion.objects.Fields();
            theFields2
                    .setFields((net.medfusion.integrations.webservices.medfusion.objects.Field[]) fieldVector2
                            .toArray(new net.medfusion.integrations.webservices.medfusion.objects.Field[fieldVector2
                                    .size()]));
            memberLocked2.setFields(theFields2);

            MemberLocked[] memberLockedArray = new MemberLocked[2];
            memberLockedArray[0] = memberLocked;
            memberLockedArray[1] = memberLocked2;

            String subjectTemplate = "test subject, A = ${A}, B = ${B}";
            String template = "test message, A = ${A}, B = ${B}, first_name = ${first_name}, last_name = ${last_name}";

            NameValuePair a = new NameValuePair("A", "a-value");
            NameValuePair b = new NameValuePair("B", "b-value");
            NameValuePair a2 = new NameValuePair("A", "a2-value");
            NameValuePair b2 = new NameValuePair("B", "b2-value");
            NameValuePair[] nvpArray1 = new NameValuePair[] { a, b };
            NameValuePair[] nvpArray2 = new NameValuePair[] { a2, b2 };
            NameValuePairComposite nvpComposite1 = new NameValuePairComposite(
                    nvpArray1);
            NameValuePairComposite nvpComposite2 = new NameValuePairComposite(
                    nvpArray2);

            NameValuePairComposite[] nvpCompositeArray = new NameValuePairComposite[] {
                    nvpComposite1, nvpComposite2 };

            // test case 1: sendUnlockEmail true, custom body template and
            // subject template
            WebServiceReturnComposite[] wsReturnCompositeArray = service
                    .createOrUpdateMultipleMemberLocked(
                            memberLockedArray,
                            SystemConfigProperties.WSFM_STAFFID1,
                            WebServiceConstants.PARM_KEYTYPE_RANDOMSYSTEMGENERATEDCODE,
                            null, true, subjectTemplate, template,
                            nvpCompositeArray);
            // test case 1b: sendUnlockEmail true, custom body template, null
            // subject template
            // WebServiceReturnComposite[] wsReturnCompositeArray =
            // service.createOrUpdateMultipleMemberLocked(memberLockedArray,
            // WSFM_STAFFID1,
            // WebServiceConstants.PARM_KEYTYPE_RANDOMSYSTEMGENERATEDCODE, null,
            // true, null, template, nvpCompositeArray);
            // test case 2: sendUnlockEmail true, standard template
            // WebServiceReturnComposite[] wsReturnCompositeArray =
            // service.createOrUpdateMultipleMemberLocked(memberLockedArray,
            // WSFM_STAFFID1,
            // WebServiceConstants.PARM_KEYTYPE_RANDOMSYSTEMGENERATEDCODE, null,
            // true, null, null, null);
            // test case 3: sendUnlockEmail false, custom template -> should
            // throw error
            // WebServiceReturnComposite[] wsReturnCompositeArray =
            // service.createOrUpdateMultipleMemberLocked(memberLockedArray,
            // WSFM_STAFFID1,
            // WebServiceConstants.PARM_KEYTYPE_RANDOMSYSTEMGENERATEDCODE, null,
            // false, subjectTemplate, null, nvpCompositeArray);
            // test case 4: sendUnlockEmail false, standard template
            // WebServiceReturnComposite[] wsReturnCompositeArray =
            // service.createOrUpdateMultipleMemberLocked(memberLockedArray,
            // WSFM_STAFFID1,
            // WebServiceConstants.PARM_KEYTYPE_RANDOMSYSTEMGENERATEDCODE, null,
            // false, null, null);

            System.out
                    .println("returned from createOrUpdateMultipleMemberLocked, wsReturnCompositeArray follows:");
            for (int i = 0; i < wsReturnCompositeArray.length; i++) {
                WebServiceReturnComposite wsrc = wsReturnCompositeArray[i];
                System.out.println("wsrc=" + wsrc);
            }

            lineBreak();
        } catch (WebServiceExceptionInvalidParameter e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * This method tests the ability to send multiple member unlock email
     * messages in a single web service call. The email body will be the
     * standard version from TextConfig, or (if provided) a custom email
     * template & variables may be used.
     * 
     * @param service
     */
    @Test
    public void testSendMultipleMemberUnlockEmail() {
        try {
            System.out.println("entering testSendMultipleMemberUnlockEmail()");

            String subjectTemplate = "my test subject, A = ${A}, B = ${B}";
            String template = "my test message, A = ${A}, B = ${B}, first_name = ${first_name}, last_name = ${last_name}";

            NameValuePair a = new NameValuePair("A", "a-value");
            NameValuePair b = new NameValuePair("B", "b-value");
            NameValuePair a2 = new NameValuePair("A", "a2-value");
            NameValuePair b2 = new NameValuePair("B", "b2-value");
            NameValuePair[] nvpArray1 = new NameValuePair[] { a, b };
            NameValuePair[] nvpArray2 = new NameValuePair[] { a2, b2 };
            NameValuePairComposite nvpComposite1 = new NameValuePairComposite(
                    nvpArray1);
            NameValuePairComposite nvpComposite2 = new NameValuePairComposite(
                    nvpArray2);

            NameValuePairComposite[] nvpCompositeArray = new NameValuePairComposite[] {
                    nvpComposite1, nvpComposite2 };

            // test calling sendMultipleMemberUnlockEmail() with a custom email
            // template
            WebServiceReturnComposite[] wsReturnCompositeArray = service
                    .sendMultipleMemberUnlockEmail(
                            new int[] {
                                    SystemConfigProperties.WSFM_MEMBERLOCKEDID1,
                                    SystemConfigProperties.WSFM_MEMBERLOCKEDID2 },
                            SystemConfigProperties.WSFM_STAFFID1,
                            WebServiceConstants.PARM_KEYTYPE_RANDOMSYSTEMGENERATEDCODE,
                            null, subjectTemplate, template, nvpCompositeArray);
            // test calling sendMultipleMemberUnlockEmail() without passing in a
            // custom email template (the system will use the standard template)
            // WebServiceReturnComposite[] wsReturnCompositeArray =
            // service.sendMultipleMemberUnlockEmail(new int[]
            // {SystemConfigProperties.WSFM_MEMBERID1,
            // SystemConfigProperties.WSFM_MEMBERID2}, WSFM_STAFFID1,
            // WebServiceConstants.PARM_KEYTYPE_RANDOMSYSTEMGENERATEDCODE, null,
            // null, null, null);

            System.out
                    .println("returned from sendMultipleMemberUnlockEmail, wsReturnCompositeArray follows:");
            for (int i = 0; i < wsReturnCompositeArray.length; i++) {
                WebServiceReturnComposite wsrc = wsReturnCompositeArray[i];
                System.out.println("wsrc=" + wsrc);
            }

            lineBreak();
        } catch (WebServiceExceptionInvalidParameter e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Test Creation of MemberLocked Object with all
     * net.medfusion.integrations.webservices.medfusion.objects.Fields
     * 
     * @param service
     */
    @Test
    public void testMemberLockedCreateAllFieldsWebServices() {
        final SimpleDateFormat MMddyyyyFormat = new SimpleDateFormat(
                "MM/dd/yyyy");

        try {
            System.out
                    .println("Create a MemberLocked Object with All net.medfusion.integrations.webservices.medfusion.objects.Fields");

            MemberLocked memberLocked = new MemberLocked();

            Vector fieldVector = new Vector();

            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_PRACTICEMEMBER_PRACTICEID,
                            new Integer(SystemConfigProperties.WSFM_PRACTICEID)));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_EMAIL,
                            SystemConfigProperties.UNLOCKEMAIL));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_FIRSTNAME,
                            "Catherine"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_MIDDLENAME,
                            "Marie"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_LASTNAME, "Wilson"));

            Date dob = MMddyyyyFormat.parse("09/01/1950");
            Calendar birthdate = new GregorianCalendar();
            birthdate.setTime(dob);

            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_DOB, birthdate
                                    .getTime()));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_SEX,
                            new Integer(
                                    WebServiceConstants.FIELD_MEMBER_SEX_FEMALE)));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_OCCUPATION,
                            "Teacher"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_MARITALSTATUS,
                            WebServiceConstants.FIELD_MEMBER_MARITALSTATUS_MARRIED));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_RACE,
                            WebServiceConstants.FIELD_MEMBER_RACE_WHITE));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_SSN, "111-11-1111"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_EMPLOYERNAME,
                            "North Carolina Public Schools"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_EMPLOYERADDRESS,
                            "123 Carolina Lane Raleigh, NC 25179"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_PRACTICEMEMBER_PATIENTID,
                            "PID12345"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_HOMEPHONE,
                            "111-111-1111"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_WORKPHONE,
                            "222-222-2222"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_MOBILEPHONE,
                            "333-333-3333"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_FAXPHONE,
                            "444-444-4444"));

            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_HOMEADDRESS1,
                            "789 Tarheel Avenue"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_HOMEADDRESS2, ""));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_HOMEADDRESS3, ""));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_HOMEADDRESS4, ""));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_HOMECITY,
                            "Chapel Hill"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_HOMESTATE, "NC"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_HOMEPOSTALCODE,
                            "25178"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_HOMECOUNTRY, "US")); // Defaults
                                                                                  // to
                                                                                  // US
                                                                                  // in
                                                                                  // Method

            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_WORKADDRESS1,
                            "123 Carolina Lane Raleigh"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_WORKADDRESS2, ""));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_WORKADDRESS3, ""));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_WORKADDRESS4, ""));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_WORKCITY,
                            "Raleigh"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_WORKSTATE, "NC"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_WORKPOSTALCODE,
                            "25179"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_WORKCOUNTRY, "US"));

            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_BILLINGADDRESS1,
                            "789 Tarheel Avenue"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_BILLINGADDRESS2,
                            ""));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_BILLINGADDRESS3,
                            ""));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_BILLINGADDRESS4,
                            ""));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_BILLINGCITY,
                            "Raleigh"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_BILLINGSTATE, "NC"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_BILLINGPOSTALCODE,
                            "25178"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_BILLINGCOUNTRY,
                            "US"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_BILLINGCONTACTPHONE,
                            "111-111-1111"));

            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_EMERCONTACTFIRSTNAME,
                            "Alexander"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_EMERCONTACTLASTNAME,
                            "Wilson"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_EMERCONTACTHOMEPHONE,
                            "111-111-1111"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_EMERCONTACTWORKPHONE,
                            "555-555-5555"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_EMERCONTACTMOBILEPHONE,
                            "666-666-6666"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_EMERCONTACTFAXPHONE,
                            "777-777-7777"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_EMERCONTACTADDRESS1,
                            "789 Tarheel Avenue"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_EMERCONTACTADDRESS2,
                            ""));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_EMERCONTACTADDRESS3,
                            ""));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_EMERCONTACTADDRESS4,
                            ""));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_EMERCONTACTCITY,
                            "Chapel Hill"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_EMERCONTACTSTATE,
                            "NC"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_EMERCONTACTPOSTALCODE,
                            "25178"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_EMERCONTACTCOUNTRY,
                            "US"));

            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_INSURANCEINSUREDRELATION,
                            WebServiceConstants.FIELD_MEMBERLOCKED_INSURANCEINSUREDRELATION_SPOUSE));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_INSURANCEINSUREDDOB,
                            birthdate.getTime()));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_INSURANCEINSUREDSSN,
                            "111-11-1111"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_INSURANCEINSUREDNAME,
                            "Catherine Wilson"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_INSURANCECOMPANY,
                            "United Health Care"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_INSURANCEPOLICYNUMBER,
                            "UHC123456"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_INSURANCEPLANNAME,
                            "UHC Standard"));

            Date dateEff = MMddyyyyFormat.parse("03/01/2007");
            Calendar dateEffective = new GregorianCalendar();
            dateEffective.setTime(dateEff);

            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_INSURANCEDATEEFFECTIVE,
                            dateEffective.getTime()));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_INSURANCEGROUPNUMBER,
                            "123"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_INSURANCECOPAY,
                            new BigDecimal(20.00)));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_INSURANCECLAIMSADDRESS1,
                            "PO Box 456"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_INSURANCECLAIMSADDRESS2,
                            ""));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_INSURANCECLAIMSADDRESS3,
                            ""));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_INSURANCECLAIMSADDRESS4,
                            ""));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_INSURANCECLAIMSCITY,
                            "Atlanta"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_INSURANCECLAIMSSTATE,
                            "GA"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_INSURANCECLAIMSPOSTALCODE,
                            "30315"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_INSURANCECLAIMSCOUNTRY,
                            "US"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_INSURANCECLAIMSPHONE,
                            "888-888-8888"));

            Date dateExp = MMddyyyyFormat.parse("03/01/2009");
            Calendar dateExpires = new GregorianCalendar();
            dateExpires.setTime(dateExp);

            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_INSURANCEDATEEXPIRY,
                            dateExpires.getTime()));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_INSURANCETYPE,
                            WebServiceConstants.FIELD_MEMBERLOCKED_INSURANCETYPE_PRIMARYHEALTHINSURANCE));

            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_INSURANCESAMEASGUARANTOR,
                            new Boolean(false)));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_GUARANTORPHONE,
                            "111-111-1111"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_GUARANTORADDRESS1,
                            "789 Tarheel Avenue"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_GUARANTORADDRESS2,
                            ""));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_GUARANTORADDRESS3,
                            ""));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_GUARANTORADDRESS4,
                            ""));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_GUARANTORCITY,
                            "Chapel Hill"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_GUARANTORSTATE,
                            "NC"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_GUARANTORPOSTALCODE,
                            "25178"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_GUARANTORCOUNTRY,
                            "US"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_GUARANTORFIRSTNAME,
                            "Alexander"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_GUARANTORMIDDLENAME,
                            "James"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_GUARANTORLASTNAME,
                            "Wilson"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_GUARANTOREMPLOYERNAME,
                            "New Egg"));

            Date guarDOB = MMddyyyyFormat.parse("06/01/1948");
            Calendar guarantorDOB = new GregorianCalendar();
            guarantorDOB.setTime(guarDOB);

            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_GUARANTORDOB,
                            guarantorDOB.getTime()));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_GUARANTOREMAIL,
                            SystemConfigProperties.UNLOCKEMAIL));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBERLOCKED_GUARANTORSSN,
                            "888-88-8888"));

            // add fields to MemberLocked
            net.medfusion.integrations.webservices.medfusion.objects.Fields theFields = new net.medfusion.integrations.webservices.medfusion.objects.Fields();
            theFields
                    .setFields((net.medfusion.integrations.webservices.medfusion.objects.Field[]) fieldVector
                            .toArray(new net.medfusion.integrations.webservices.medfusion.objects.Field[fieldVector
                                    .size()]));
            memberLocked.setFields(theFields);

            int memberLockedId = service.createMemberLocked(memberLocked);
            System.out.println("MemberLocked object created with id: "
                    + memberLockedId);
            System.out
                    .println("Get the MemberLocked object we just created with id: "
                            + memberLockedId);

            Criteria[] mlCriteria = new Criteria[1];
            mlCriteria[0] = new Criteria(
                    WebServiceConstants.FIELD_MEMBERLOCKED_ID,
                    WebServiceConstants.OPERATION_EQUALS, new Integer(
                            memberLockedId));
            MemberLocked[] resultMemberLockeds = service
                    .getMemberLockedsByCriteria(
                            WebServiceConstants.FIELD_INT_NONE, mlCriteria);
            WebserviceUtils.dumpResults(resultMemberLockeds);

            System.out
                    .println("Now let's unlock that MemberLocked and send them an Unlock Email with a Random System Generated Code as the Unlock Key.");
            int newMemberId = service.unlockMemberLocked(memberLockedId,
                    SystemConfigProperties.WSFM_STAFFID1, "Catherine",
                    "Wilson", birthdate, SystemConfigProperties.UNLOCKEMAIL,
                    "25178",
                    WebServiceConstants.PARM_KEYTYPE_RANDOMSYSTEMGENERATEDCODE,
                    null);

            System.out
                    .println("Get the Member Object of the MemberLocked that we just unlocked.");
            Criteria[] memberCriteria = new Criteria[1];
            memberCriteria[0] = new Criteria(
                    WebServiceConstants.FIELD_MEMBER_ID,
                    WebServiceConstants.OPERATION_EQUALS, new Integer(
                            newMemberId));
            String[] memberFields = new String[4];
            memberFields[0] = WebServiceConstants.FIELD_MEMBER_FIRSTNAME;
            memberFields[1] = WebServiceConstants.FIELD_MEMBER_LASTNAME;
            memberFields[2] = WebServiceConstants.FIELD_MEMBER_DATECREATED;
            memberFields[3] = WebServiceConstants.FIELD_MEMBER_ID;
            String[] orderByFields = new String[1];
            orderByFields[0] = WebServiceConstants.FIELD_MEMBER_ID;
            MemberMap[] memberResults = service.getMemberMapForUsersByCriteria(
                    WebServiceConstants.FIELD_INT_NONE, memberFields,
                    memberCriteria, orderByFields);
            WebserviceUtils.dumpResults(memberResults);
        } catch (WebServiceExceptionInvalidParameter e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Test CustomFormForm WebServices
     * 
     * @param service
     */
    @Test
    public void testCustomFormFormWebServices() throws Exception {
        try {
            System.out
                    .println("Retrieve all Published and Unpublished Forms from a Practice");
            CFF[] customFormForms = service
                    .getAllCFFs(SystemConfigProperties.WSFM_PRACTICEID);
            WebserviceUtils.dumpResults(customFormForms);
        } catch (WebServiceExceptionInvalidParameter e) {
            System.out.println("Error: " + e.getMessage());
            Assert.fail();
        }
    }

    /**
     * Test CustomForm with Answers WebServices
     * 
     * @param service
     */
    @Test
    public void testCustomFormWithAnswersWebServices() {
        try {
            System.out
                    .println("Retrieve a List of CustomForms from the last Month");
            Calendar cal = new GregorianCalendar();
            cal.add(GregorianCalendar.MONTH, -1);
            Criteria[] criteria = new Criteria[1];
            criteria[0] = new Criteria(
                    WebServiceConstants.FIELD_CUSTOMFORM_COMPLETEDDATE,
                    WebServiceConstants.OPERATION_GREATERTHAN, cal.getTime());
            CustomForm[] cfResults = service.getCustomFormsByCriteria(
                    WebServiceConstants.FIELD_INT_NONE, criteria);
            WebserviceUtils.dumpResults(cfResults);

            System.out.println("Let's get the Answers for the last one");
            CustomForm customForm = cfResults[cfResults.length - 1];
            Integer customFormFormId = customForm
                    .getFieldValueInt(WebServiceConstants.FIELD_CUSTOMFORM_CFFID);
            criteria = new Criteria[1];
            criteria[0] = new Criteria(
                    WebServiceConstants.FIELD_CUSTOMFORM_CFFID,
                    WebServiceConstants.OPERATION_EQUALS, customFormFormId);
            cfResults = service.getCustomFormsByCriteria(
                    WebServiceConstants.FIELD_INT_NONE, criteria, true);
            WebserviceUtils.dumpResults(cfResults);

            System.out
                    .println("Now that we have the Answers, let's get the structure of the Form too");
            Integer customFormId = customForm
                    .getFieldValueInt(WebServiceConstants.FIELD_CUSTOMFORM_ID);
            CFF customFormForm = service.getCFFForCustomForm(customFormId
                    .intValue());
            WebserviceUtils.dumpResults(customFormForm);
        } catch (WebServiceExceptionInvalidParameter e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Test methods related to Staff.
     * 
     * @param service
     */
    @Test
    public void testStaffWebServices() {
        try {
            System.out.println("Get all staff for a particular Practice");
            Staff[] staff = service
                    .getStaffForPractice(SystemConfigProperties.WSFM_PRACTICEID);
            WebserviceUtils.dumpResults(staff);
        } catch (WebServiceExceptionInvalidParameter e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Test methods related to OBPs
     * 
     * @param service
     */
    @Test
    public void testOnlineBillPayWebServices() {
        try {
            System.out
                    .println("Find All Pending Online Bill Payments in the last month");
            Calendar cal = new GregorianCalendar();
            cal.add(GregorianCalendar.MONTH, -1);
            Criteria[] criteria = new Criteria[2];
            criteria[0] = new Criteria(
                    WebServiceConstants.FIELD_OBP_CREATIONDATE,
                    WebServiceConstants.OPERATION_GREATERTHAN, cal.getTime());
            criteria[1] = new Criteria(WebServiceConstants.FIELD_OBP_STATUS,
                    WebServiceConstants.OPERATION_EQUALS,
                    WebServiceConstants.FIELD_OBP_STATUS_PENDING);
            OBP[] obpResults = service.getOBPByCriteria(
                    WebServiceConstants.FIELD_INT_NONE, criteria);
            WebserviceUtils.dumpResults(obpResults);

            if (obpResults != null && obpResults.length > 0) {
                OBP onlineBillPayment = obpResults[0];
                Integer obpId = (Integer) onlineBillPayment
                        .getFieldValue(WebServiceConstants.FIELD_OBP_ID);
                System.out.println("Working with OBP Id: " + obpId);

                Criteria[] matchCurrent = new Criteria[1];
                matchCurrent[0] = new Criteria(
                        WebServiceConstants.FIELD_OBP_ID,
                        WebServiceConstants.OPERATION_EQUALS, obpId);

                System.out
                        .println("Attempt to 'Reconcile' an Online Bill Payment that is 'Pending', should fail.");
                try {
                    service.reconcileOnlineBillPayment(obpId.intValue());
                    obpResults = service.getOBPByCriteria(
                            WebServiceConstants.FIELD_INT_NONE, matchCurrent);
                    WebserviceUtils.dumpResults(obpResults);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    lineBreak();
                }

                System.out
                        .println("Attempt to 'Post' an Online Bill Payment that is 'Pending' using method that allows multiple at once, should succeed");
                onlineBillPayment = obpResults[0];
                try {
                    int[] obpIds = new int[1];
                    obpIds[0] = obpId.intValue();
                    service.postOnlineBillPayments(obpIds);
                    obpResults = service.getOBPByCriteria(
                            WebServiceConstants.FIELD_INT_NONE, matchCurrent);
                    WebserviceUtils.dumpResults(obpResults);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

                System.out
                        .println("Attempt to 'Post' an Online Bill Payment that is 'Posted', should fail.");
                try {
                    service.postOnlineBillPayment(obpId.intValue());
                    obpResults = service.getOBPByCriteria(
                            WebServiceConstants.FIELD_INT_NONE, matchCurrent);
                    WebserviceUtils.dumpResults(obpResults);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    lineBreak();
                }

                System.out
                        .println("Attempt to 'Reconcile' an Online Bill Payment that is 'Posted', should succeed.");
                try {
                    service.reconcileOnlineBillPayment(obpId.intValue());
                    obpResults = service.getOBPByCriteria(
                            WebServiceConstants.FIELD_INT_NONE, criteria);
                    WebserviceUtils.dumpResults(obpResults);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

                System.out
                        .println("Attempt to 'Post' an Online Bill Payment that is 'Reconciled', should fail.");
                try {
                    service.postOnlineBillPayment(obpId.intValue());
                    obpResults = service.getOBPByCriteria(
                            WebServiceConstants.FIELD_INT_NONE, criteria);
                    WebserviceUtils.dumpResults(obpResults);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    lineBreak();
                }
            }
        } catch (WebServiceExceptionInvalidParameter e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testPharmacyWebServices() {
        try {
            // test creating or updating a pharmacy
            /*
             * Pharmacy pharmacy1 = new Pharmacy();
             * 
             * List<Field> fieldList = new ArrayList<Field>(); //
             * fieldList.add(new Field(WebServiceConstants.FIELD_PHARMACY_ID, //
             * new Integer(10014))); // set the pharmacy id here to attempt an
             * // update, or leave it out for a create
             * 
             * fieldList.add(new Field(WebServiceConstants.FIELD_PHARMACY_NAME,
             * "Walgreens - Main St.")); fieldList.add(new Field(
             * WebServiceConstants.FIELD_PHARMACY_PRACTICEID, Integer
             * .valueOf(SystemConfigProperties.WSFM_PRACTICEID)));
             * 
             * fieldList.add(new Field(
             * WebServiceConstants.FIELD_PHARMACY_LOCATIONIDS, new Integer[] {
             * SystemConfigProperties.WSFM_LOCATIONID1 })); // one // locations
             * // fieldList.add(new //
             * Field(WebServiceConstants.FIELD_PHARMACY_LOCATIONIDS, new //
             * Integer[] {WSFM_LOCATIONID1, //
             * SystemConfigProperties.WSFM_LOCATIONID2})); // two // locations
             * // fieldList.add(new //
             * Field(WebServiceConstants.FIELD_PHARMACY_LOCATIONIDS, new //
             * Integer[] {})); // no locations -> implies all locations
             * 
             * fieldList.add(new Field(
             * WebServiceConstants.FIELD_PHARMACY_PHONENUMBER, "111-111-1111"));
             * fieldList .add(new Field(
             * WebServiceConstants.FIELD_PHARMACY_FAXNUMBER, "222-22-2222"));
             * fieldList.add(new Field(
             * WebServiceConstants.FIELD_PHARMACY_ADDRESS1, "1234 Main St."));
             * fieldList.add(new Field(WebServiceConstants.FIELD_PHARMACY_CITY,
             * "Springfield")); fieldList.add(new
             * Field(WebServiceConstants.FIELD_PHARMACY_STATE, "IL"));
             * fieldList.add(new Field(
             * WebServiceConstants.FIELD_PHARMACY_POSTALCODE, "27600"));
             * 
             * net.medfusion.integrations.webservices.medfusion.objects.Fields
             * theFields = new Fields(); theFields.setFields((Field[])
             * fieldList.toArray(new Field[fieldList .size()]));
             * pharmacy1.setFields(theFields);
             * 
             * Pharmacy[] pharmacies = new Pharmacy[] { pharmacy1 };
             * 
             * WebServiceReturnComposite[] wsReturnCompositeArray = service
             * .createOrUpdatePharmacies(pharmacies);
             * 
             * System.out .println(
             * "returned from createOrUpdatePharmacies, wsReturnCompositeArray follows:"
             * ); for (int i = 0; i < wsReturnCompositeArray.length; i++) {
             * WebServiceReturnComposite wsrc = wsReturnCompositeArray[i];
             * System.out.println("wsrc=" + wsrc); }
             * 
             * lineBreak();
             */

            // test querying for a pharmacy, then deleting a pharmacy
            System.out.println("Get Pharmacies for Practice Id "
                    + SystemConfigProperties.WSFM_PRACTICEID
                    + " - Pharmacy Id > 0");
            Criteria[] pharmacyCriteria = new Criteria[1];
            pharmacyCriteria[0] = new Criteria(
                    WebServiceConstants.FIELD_PHARMACY_ID,
                    WebServiceConstants.OPERATION_GREATERTHAN,
                    Integer.valueOf(0));
            Pharmacy[] pharmacyResults = service.getPharmacyByCriteria(
                    SystemConfigProperties.WSFM_PRACTICEID, pharmacyCriteria);
            WebserviceUtils.dumpResults(pharmacyResults);

            System.out.println("Get Walgreens Pharmacies for Practice Id "
                    + SystemConfigProperties.WSFM_PRACTICEID);
            pharmacyCriteria = new Criteria[1];
            pharmacyCriteria[0] = new Criteria(
                    WebServiceConstants.FIELD_PHARMACY_NAME,
                    WebServiceConstants.OPERATION_STARTSWITH, "Walgreens");
            pharmacyResults = service.getPharmacyByCriteria(
                    SystemConfigProperties.WSFM_PRACTICEID, pharmacyCriteria);
            WebserviceUtils.dumpResults(pharmacyResults);

            if (pharmacyResults.length > 0) {
                System.out
                        .println("That last Walgreens is closing that particular location, we need to remove them from our list of available"
                                + " pharmacies so that patients don't try to request a prescription pickup at that location.");
                lineBreak();

                Pharmacy pharmacy = pharmacyResults[pharmacyResults.length - 1];

                Integer pharmacyId = pharmacy
                        .getFieldValueInt(WebServiceConstants.FIELD_PHARMACY_ID);
                int[] pharmacyIdsToDelete = new int[] { pharmacyId.intValue() };

                int[] locationIds = (int[]) pharmacy
                        .getFieldValue(WebServiceConstants.FIELD_PHARMACY_LOCATIONIDS);
                int[] locationIdsToDelete = new int[] { locationIds[0] };

                WebServiceReturnComposite[] wsrc = service.deletePharmacies(
                        SystemConfigProperties.WSFM_PRACTICEID,
                        locationIdsToDelete, pharmacyIdsToDelete);
                System.out
                        .println("Successfully deleted Pharmacies.  Displaying Return statement...");
                WebserviceUtils.dumpResults(wsrc);
                lineBreak();

                System.out
                        .println("Make sure we removed the Pharmacy with Id "
                                + pharmacyId
                                + " from Location Id "
                                + locationIds[0]
                                + " in Practice Id "
                                + SystemConfigProperties.WSFM_PRACTICEID
                                + ".  If the Pharmacy was at more than one location, we will get it back from the search, otherwise nothing will be returned.");

                pharmacyCriteria = new Criteria[1];
                pharmacyCriteria[0] = new Criteria(
                        WebServiceConstants.FIELD_PHARMACY_ID,
                        WebServiceConstants.OPERATION_EQUALS, pharmacyId);
                pharmacyResults = service.getPharmacyByCriteria(
                        SystemConfigProperties.WSFM_PRACTICEID,
                        pharmacyCriteria);
                WebserviceUtils.dumpResults(pharmacyResults);
            }
        } catch (WebServiceExceptionInvalidParameter e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Test methods related to RxRefillReqs
     * 
     * @param service
     */
    @Test
    public void testRxRefillReqWebServices() {
        try {
            System.out
                    .println("Find All 'Open' Rx Refill Requests for last year");
            Calendar cal = new GregorianCalendar();
            cal.add(GregorianCalendar.YEAR, -1);
            Criteria[] rxCriteria = new Criteria[2];
            rxCriteria[0] = new Criteria(
                    WebServiceConstants.FIELD_RXREFILLREQ_CREATIONDATE,
                    WebServiceConstants.OPERATION_GREATERTHAN, cal.getTime());
            rxCriteria[1] = new Criteria(
                    WebServiceConstants.FIELD_RXREFILLREQ_STATUS,
                    WebServiceConstants.OPERATION_EQUALS,
                    WebServiceConstants.FIELD_RXREFILLREQ_STATUS_OPEN);
            RxRefillReq[] rxRefillReqResults = service
                    .getRxRefillReqByCriteria(
                            WebServiceConstants.FIELD_INT_NONE, rxCriteria);
            WebserviceUtils.dumpResults(rxRefillReqResults);

            if (rxRefillReqResults != null && rxRefillReqResults.length > 0) {
                RxRefillReq req = rxRefillReqResults[0];
                Integer reqId = (Integer) req
                        .getFieldValue(WebServiceConstants.FIELD_RXREFILLREQ_ID);

                System.out
                        .println("Externally process the first Open RxRefillReq.");
                int[] rxIds = new int[1];
                rxIds[0] = reqId.intValue();
                service.externallyProcessRxRefillReqs(rxIds);

                // get it again to verify the status change
                Criteria[] matchCurrent = new Criteria[1];
                matchCurrent[0] = new Criteria(
                        WebServiceConstants.FIELD_RXREFILLREQ_ID,
                        WebServiceConstants.OPERATION_EQUALS, reqId);
                rxRefillReqResults = service.getRxRefillReqByCriteria(
                        WebServiceConstants.FIELD_INT_NONE, matchCurrent);
                WebserviceUtils.dumpResults(rxRefillReqResults);
            }

            System.out
                    .println("Again Find All 'Open' Rx Refill Requests for the last year");
            rxRefillReqResults = service.getRxRefillReqByCriteria(
                    WebServiceConstants.FIELD_INT_NONE, rxCriteria);
            WebserviceUtils.dumpResults(rxRefillReqResults);

            if (rxRefillReqResults != null && rxRefillReqResults.length > 0) {
                RxRefillReq req = rxRefillReqResults[0];
                Integer reqId = (Integer) req
                        .getFieldValue(WebServiceConstants.FIELD_RXREFILLREQ_ID);
                System.out.println("Working with Req Id: " + reqId);

                Criteria[] matchCurrent = new Criteria[1];
                matchCurrent[0] = new Criteria(
                        WebServiceConstants.FIELD_RXREFILLREQ_ID,
                        WebServiceConstants.OPERATION_EQUALS, reqId);

                System.out.println("Get a SigCode.");
                SigCode[] sigCodes = service
                        .getSigCodesByPracticeId(SystemConfigProperties.WSFM_PRACTICEID);
                Integer sigCodeId = null;

                if (sigCodes != null && sigCodes.length > 0) {
                    SigCode sigCode = sigCodes[0];
                    sigCodeId = (Integer) sigCode
                            .getFieldValue(WebServiceConstants.FIELD_SIGCODE_ID);
                    lineBreak();
                } else {
                    // if no SigCode exists, create one and use it
                    sigCodeId = new Integer(service.addSigCode("2TABPRN",
                            "Take two as needed",
                            SystemConfigProperties.WSFM_PRACTICEID));
                }

                System.out.println("Using SigCode Id " + sigCodeId);

                System.out.println("Get a RouteCode.");
                RouteCode[] routeCodes = service.getAllRouteCodes();
                Integer routeCodeId = null;

                if (routeCodes != null && routeCodes.length > 0) {
                    RouteCode routeCode = routeCodes[0];
                    routeCodeId = (Integer) routeCode
                            .getFieldValue(WebServiceConstants.FIELD_ROUTECODE_ID);
                    System.out.println("Using RouteCode Id " + routeCodeId);
                    lineBreak();
                }

                System.out
                        .println("Attempt to prescribe to patient using their requested prescription information.");
                try {
                    // go through each Entry one-by-one
                    RxRefillReqEntry[] entries = (RxRefillReqEntry[]) req
                            .getFieldValue(WebServiceConstants.FIELD_RXREFILLREQ_RENEWALLINEITEMS);

                    for (int i = 0; i < entries.length; i++) {
                        RxRefillReqEntry entry = entries[i];
                        Integer entryId = (Integer) entry
                                .getFieldValue(WebServiceConstants.FIELD_RXREFILLREQENTRY_ID);

                        service.prescribeRxRefillReqEntryFromPatient(
                                entryId.intValue(),
                                SystemConfigProperties.WSFM_STAFFID2, 12,
                                sigCodeId.intValue(), routeCodeId.intValue());
                    }

                    // create a Comm to send to the Member about this
                    // prescription

                    // pull out useful details
                    Integer memberId = (Integer) req
                            .getFieldValue(WebServiceConstants.FIELD_RXREFILLREQ_MEMBERID);

                    Comm communication = new Comm();

                    Vector fieldVector = new Vector();

                    fieldVector
                            .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                    WebServiceConstants.FIELD_COMM_MEMBERID,
                                    memberId));
                    fieldVector
                            .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                    WebServiceConstants.FIELD_COMM_PRACTICEID,
                                    new Integer(
                                            SystemConfigProperties.WSFM_PRACTICEID)));
                    fieldVector
                            .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                    WebServiceConstants.FIELD_COMM_SUBJECT,
                                    "Your prescription has been granted"));
                    fieldVector
                            .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                    WebServiceConstants.FIELD_COMM_MESSAGE,
                                    "Hello,\n\nYour prescription has been refilled and will be called into your Pharmacy soon.\n\nThanks, Your Practice"));
                    fieldVector
                            .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                    WebServiceConstants.FIELD_COMM_SERVICETYPE,
                                    WebServiceConstants.FIELD_COMM_SERVICETYPE_RXREFILLREQ));
                    fieldVector
                            .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                    WebServiceConstants.FIELD_COMM_CONTAINERID,
                                    reqId));

                    // build comm details
                    CommDetail[] cds = new CommDetail[2];
                    Vector detailFieldVector = new Vector();

                    detailFieldVector
                            .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                    WebServiceConstants.FIELD_COMMDETAIL_MEMBERID,
                                    null));
                    detailFieldVector
                            .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                    WebServiceConstants.FIELD_COMMDETAIL_STAFFID,
                                    new Integer(
                                            SystemConfigProperties.WSFM_STAFFID2)));
                    detailFieldVector
                            .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                    WebServiceConstants.FIELD_COMMDETAIL_TYPE,
                                    WebServiceConstants.FIELD_COMMDETAIL_TYPE_FROM));

                    net.medfusion.integrations.webservices.medfusion.objects.Fields theDetailFields = new net.medfusion.integrations.webservices.medfusion.objects.Fields();
                    theDetailFields
                            .setFields((net.medfusion.integrations.webservices.medfusion.objects.Field[]) detailFieldVector
                                    .toArray(new net.medfusion.integrations.webservices.medfusion.objects.Field[detailFieldVector
                                            .size()]));
                    cds[0] = new CommDetail();
                    cds[0].setFields(theDetailFields);

                    detailFieldVector = new Vector();
                    detailFieldVector
                            .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                    WebServiceConstants.FIELD_COMMDETAIL_MEMBERID,
                                    memberId));
                    detailFieldVector
                            .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                    WebServiceConstants.FIELD_COMMDETAIL_STAFFID,
                                    null));
                    detailFieldVector
                            .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                    WebServiceConstants.FIELD_COMMDETAIL_TYPE,
                                    WebServiceConstants.FIELD_COMMDETAIL_TYPE_TO));

                    theDetailFields = new net.medfusion.integrations.webservices.medfusion.objects.Fields();
                    theDetailFields
                            .setFields((net.medfusion.integrations.webservices.medfusion.objects.Field[]) detailFieldVector
                                    .toArray(new net.medfusion.integrations.webservices.medfusion.objects.Field[detailFieldVector
                                            .size()]));
                    cds[1] = new CommDetail();
                    cds[1].setFields(theDetailFields);

                    fieldVector
                            .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                    WebServiceConstants.FIELD_COMM_DETAILS, cds));

                    // add fields to Communication
                    net.medfusion.integrations.webservices.medfusion.objects.Fields theFields = new net.medfusion.integrations.webservices.medfusion.objects.Fields();
                    theFields
                            .setFields((net.medfusion.integrations.webservices.medfusion.objects.Field[]) fieldVector
                                    .toArray(new net.medfusion.integrations.webservices.medfusion.objects.Field[fieldVector
                                            .size()]));
                    communication.setFields(theFields);

                    // now process the entire RxRefillReq
                    service.communicateAndProcessRxRefillReq(
                            reqId.intValue(),
                            SystemConfigProperties.WSFM_STAFFID1,
                            true,
                            WebServiceConstants.FIELD_RXREFILLREQ_DELIVERYMETHOD_SENDTOTHECALLINQUEUE,
                            communication);

                    // get it again
                    rxRefillReqResults = service.getRxRefillReqByCriteria(
                            WebServiceConstants.FIELD_INT_NONE, matchCurrent);
                    WebserviceUtils.dumpResults(rxRefillReqResults);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    lineBreak();
                }
            }
        } catch (WebServiceExceptionInvalidParameter e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Test methods related to SA2s
     * 
     * @param service
     */
    @Test
    public void testSymptomAssessmentWebServices() {
        try {
            System.out
                    .println("Get All Symptom Assessments completed in the last 2 months");
            Calendar cal = new GregorianCalendar();
            cal.add(GregorianCalendar.MONTH, -2);
            Criteria[] criteria = new Criteria[1];
            criteria[0] = new Criteria(WebServiceConstants.FIELD_SA_ENDTIME,
                    WebServiceConstants.OPERATION_GREATERTHAN, cal.getTime());
            SA2[] saResults = service.getSA2SByCriteria(
                    WebServiceConstants.FIELD_INT_NONE, criteria);
            WebserviceUtils.dumpResults(saResults);
        } catch (WebServiceExceptionInvalidParameter e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Test methods related to various codes.
     * 
     * @param service
     */
    @Test
    public void testCodesWebServices() {
        try {
            System.out.println("Testing All RxRefillReq and VOV Codes");

            System.out.println("Get All RouteCodes");
            RouteCode[] rcResults = service.getAllRouteCodes();
            WebserviceUtils.dumpResults(rcResults);

            System.out.println("Get All DiagCodes");
            DiagCode[] dcResults = service.getAllDiagCodes();
            WebserviceUtils.dumpResults(dcResults);

            System.out.println("Get All CPTCodes");
            CPTCode[] cptResults = service.getAllCPTCodes();
            WebserviceUtils.dumpResults(cptResults);

            System.out.println("Get All SigCodes for a particular Practice");
            SigCode[] scResults = service
                    .getSigCodesByPracticeId(SystemConfigProperties.WSFM_PRACTICEID);
            WebserviceUtils.dumpResults(scResults);

            System.out.println("Add 2 SigCodes to a particular Practice");
            System.out.println("Abbr: '3BID', Desc: '3 tablets twice a day'");
            System.out.println("Abbr: '2TABPRN', Desc: 'Take two as needed'");

            int sigCodeId1 = service.addSigCode("3BID",
                    "3 tablets twice a day",
                    SystemConfigProperties.WSFM_PRACTICEID);
            int sigCodeId2 = service.addSigCode("2TABPRN",
                    "Take two as needed",
                    SystemConfigProperties.WSFM_PRACTICEID);

            System.out.println("Delete those same 2 SigCodes");
            service.deleteSigCode(sigCodeId1,
                    SystemConfigProperties.WSFM_PRACTICEID);
            service.deleteSigCode(sigCodeId2,
                    SystemConfigProperties.WSFM_PRACTICEID);
            lineBreak();
        } catch (WebServiceExceptionInvalidParameter e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Test methods related to CustomForms
     * 
     * @param service
     */
    @Test
    public void testCustomFormWebServices() {

        try {
            System.out
                    .println("Get All CustomForms completed in the last Month");
            Calendar cal = new GregorianCalendar();
            cal.add(GregorianCalendar.MONTH, -1);
            Criteria[] criteria = new Criteria[1];
            criteria[0] = new Criteria(
                    WebServiceConstants.FIELD_CUSTOMFORM_COMPLETEDDATE,
                    WebServiceConstants.OPERATION_GREATERTHAN, cal.getTime());
            CustomForm[] cfResults = service.getCustomFormsByCriteria(
                    WebServiceConstants.FIELD_INT_NONE, criteria, true);
            WebserviceUtils.dumpResults(cfResults);

            if (cfResults != null && cfResults.length > 0) {
                for (CustomForm cf : cfResults) {
                    Integer cfId = (Integer) cf
                            .getFieldValue(WebServiceConstants.FIELD_CUSTOMFORM_ID);
                    System.out.println("Getting renderings of Custom Form "
                            + cfId);

                    System.out.println("Get Custom Form Output as XML");
                    // \\byte[] xmlOutput =
                    // service.getCustomFormOutput(cfId.intValue(),
                    // WSFM_STAFFID1,
                    // WebServiceConstantsInternal.PARM_OUTPUTTYPE_XML);

                    System.out.println("Get Custom Form Output as PDF");
                    // byte[] pdfOutput =
                    // service.getCustomFormOutput(cfId.intValue(),
                    // WSFM_STAFFID1, WebServiceConstants.PARM_OUTPUTTYPE_PDF);

                    /*
                     * FileOutputStream fstream = new
                     * FileOutputStream("form"+i+".pdf");
                     * fstream.write(pdfOutput); fstream.close();
                     */

                    System.out.println("Get Custom Form Output as TIFF");
                    // byte[] tiffOutput =
                    // service.getCustomFormOutput(cfId.intValue(),
                    // WSFM_STAFFID1, WebServiceConstants.PARM_OUTPUTTYPE_TIFF);

                    System.out.println("Get Custom Form Output as CCR");
                    // byte[] ccrOutput =
                    // service.getCustomFormOutput(cfId.intValue(),
                    // WSFM_STAFFID1, WebServiceConstants.PARM_OUTPUTTYPE_CCR);

                    System.out.println("Get Custom Form Output as Text");
                    /*
                     * byte[] txtOutput =
                     * service.getCustomFormOutput(cfId.intValue(),
                     * WSFM_STAFFID1, WebServiceConstants.PARM_OUTPUTTYPE_TEXT);
                     * 
                     * FileOutputStream fstreamText = new
                     * FileOutputStream("form"+i+".txt");
                     * fstreamText.write(txtOutput); fstreamText.close();
                     */
                }
            }

            // Find a "Viewed" form
            Criteria[] openCriteria = new Criteria[1];
            openCriteria[0] = new Criteria(
                    WebServiceConstants.FIELD_CUSTOMFORM_STATUS,
                    WebServiceConstants.OPERATION_EQUALS,
                    WebServiceConstants.FIELD_CUSTOMFORM_STATUS_VIEWED);
            cfResults = service.getCustomFormsByCriteria(
                    WebServiceConstants.FIELD_INT_NONE, openCriteria);

            if (cfResults != null && cfResults.length > 0) {
                CustomForm cf = cfResults[0];
                Integer cfId = (Integer) cf
                        .getFieldValue(WebServiceConstants.FIELD_CUSTOMFORM_ID);

                System.out.println("Testing status changes with CustomForm id "
                        + cfId);

                try {
                    System.out
                            .println("Changing Custom Form from '"
                                    + WebServiceConstants.FIELD_CUSTOMFORM_STATUS_VIEWED
                                    + "' to '"
                                    + WebServiceConstants.FIELD_CUSTOMFORM_STATUS_OPEN
                                    + "', should fail.");
                    service.setCustomFormStatus(cfId.intValue(),
                            SystemConfigProperties.WSFM_STAFFID1,
                            WebServiceConstants.FIELD_CUSTOMFORM_STATUS_OPEN);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

                lineBreak();

                try {
                    System.out
                            .println("Changing Custom Form from '"
                                    + WebServiceConstants.FIELD_CUSTOMFORM_STATUS_VIEWED
                                    + "' to '"
                                    + WebServiceConstants.FIELD_CUSTOMFORM_STATUS_PRINTED
                                    + "', should succeed.");
                    service.setCustomFormStatus(cfId.intValue(),
                            SystemConfigProperties.WSFM_STAFFID1,
                            WebServiceConstants.FIELD_CUSTOMFORM_STATUS_PRINTED);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

                lineBreak();

                try {
                    System.out
                            .println("Changing Custom Form from '"
                                    + WebServiceConstants.FIELD_CUSTOMFORM_STATUS_PRINTED
                                    + "' to '"
                                    + WebServiceConstants.FIELD_CUSTOMFORM_STATUS_VIEWED
                                    + "', should fail.");
                    service.setCustomFormStatus(cfId.intValue(),
                            SystemConfigProperties.WSFM_STAFFID1,
                            WebServiceConstants.FIELD_CUSTOMFORM_STATUS_VIEWED);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

                lineBreak();

                try {
                    System.out
                            .println("Changing Custom Form from '"
                                    + WebServiceConstants.FIELD_CUSTOMFORM_STATUS_PRINTED
                                    + "' to '"
                                    + WebServiceConstants.FIELD_CUSTOMFORM_STATUS_COMPLETED
                                    + "', should succeed.");
                    service.setCustomFormStatus(
                            cfId.intValue(),
                            SystemConfigProperties.WSFM_STAFFID1,
                            WebServiceConstants.FIELD_CUSTOMFORM_STATUS_COMPLETED);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

                lineBreak();

                System.out.println("Finding same form again to verify status");
                criteria[0] = new Criteria(
                        WebServiceConstants.FIELD_CUSTOMFORM_ID,
                        WebServiceConstants.OPERATION_GREATERTHAN, cfId);
                cfResults = service.getCustomFormsByCriteria(
                        WebServiceConstants.FIELD_INT_NONE, criteria);
                WebserviceUtils.dumpResults(cfResults);
            }/**/
        } catch (WebServiceExceptionInvalidParameter e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Test methods related to Comms
     * 
     * @param service
     */
    @Test
    public void testCommWebServices() {
        try {
            System.out
                    .println("Get Communications that are unread from last week");
            Calendar cal = new GregorianCalendar();
            cal.add(GregorianCalendar.DAY_OF_WEEK, -7);
            Criteria[] commCrit = new Criteria[3];
            commCrit[0] = new Criteria(
                    WebServiceConstants.FIELD_COMM_CREATIONDATE,
                    WebServiceConstants.OPERATION_GREATERTHAN, cal.getTime());
            commCrit[1] = new Criteria(
                    WebServiceConstants.FIELD_COMMDETAIL_READFLAG,
                    WebServiceConstants.OPERATION_EQUALS, new Boolean(false));
            commCrit[2] = new Criteria(
                    WebServiceConstants.FIELD_COMMDETAIL_STAFFID,
                    WebServiceConstants.OPERATION_EQUALS, new Integer(
                            SystemConfigProperties.WSFM_STAFFID1));
            Comm[] resultComms = service.getCommsByCriteria(
                    SystemConfigProperties.WSFM_PRACTICEID, commCrit);
            WebserviceUtils.dumpResults(resultComms);

            System.out
                    .println("Send a Generic Communication from Staff to Member");
            Comm communication = new Comm();

            Vector fieldVector = new Vector();

            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_COMM_MEMBERID,
                            new Integer(SystemConfigProperties.WSFM_MEMBERID1)));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_COMM_PRACTICEID,
                            new Integer(SystemConfigProperties.WSFM_PRACTICEID)));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_COMM_SUBJECT,
                            "Hello there from Web Services!"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_COMM_MESSAGE,
                            "Just wanted to say hello from Web Services."));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_COMM_SERVICETYPE,
                            WebServiceConstants.FIELD_COMM_SERVICETYPE_GENERIC));

            // build comm details
            CommDetail[] cds = new CommDetail[2];
            Vector detailFieldVector = new Vector();
            detailFieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_COMMDETAIL_MEMBERID, null));
            detailFieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_COMMDETAIL_STAFFID,
                            new Integer(SystemConfigProperties.WSFM_STAFFID1)));
            detailFieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_COMMDETAIL_TYPE,
                            WebServiceConstants.FIELD_COMMDETAIL_TYPE_FROM));

            net.medfusion.integrations.webservices.medfusion.objects.Fields theDetailFields = new net.medfusion.integrations.webservices.medfusion.objects.Fields();
            theDetailFields
                    .setFields((net.medfusion.integrations.webservices.medfusion.objects.Field[]) detailFieldVector
                            .toArray(new net.medfusion.integrations.webservices.medfusion.objects.Field[detailFieldVector
                                    .size()]));
            cds[0] = new CommDetail();
            cds[0].setFields(theDetailFields);

            detailFieldVector = new Vector();
            detailFieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_COMMDETAIL_MEMBERID,
                            new Integer(SystemConfigProperties.WSFM_MEMBERID1)));
            detailFieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_COMMDETAIL_STAFFID, null));
            detailFieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_COMMDETAIL_TYPE,
                            WebServiceConstants.FIELD_COMMDETAIL_TYPE_TO));

            theDetailFields = new net.medfusion.integrations.webservices.medfusion.objects.Fields();
            theDetailFields
                    .setFields((net.medfusion.integrations.webservices.medfusion.objects.Field[]) detailFieldVector
                            .toArray(new net.medfusion.integrations.webservices.medfusion.objects.Field[detailFieldVector
                                    .size()]));
            cds[1] = new CommDetail();
            cds[1].setFields(theDetailFields);

            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_COMM_DETAILS, cds));

            // build comm attachment
            File testFile = new File(SystemConfigProperties.TESTFILE);
            if (testFile.exists()) {
                System.out.println("Building Comm Attachment...");
                CommAttachment[] ca = new CommAttachment[1];
                Vector attachmentFieldVector = new Vector();
                attachmentFieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_COMMATTACHMENT_NAME,
                                testFile.getName()));

                byte[] file = getBytesFromFile(testFile);
                attachmentFieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_COMMATTACHMENT_FILE,
                                file));

                net.medfusion.integrations.webservices.medfusion.objects.Fields theAttachmentFields = new net.medfusion.integrations.webservices.medfusion.objects.Fields();
                theAttachmentFields
                        .setFields((net.medfusion.integrations.webservices.medfusion.objects.Field[]) attachmentFieldVector
                                .toArray(new net.medfusion.integrations.webservices.medfusion.objects.Field[attachmentFieldVector
                                        .size()]));
                ca[0] = new CommAttachment();
                ca[0].setFields(theAttachmentFields);

                fieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_COMM_ATTACHMENTS, ca));
            }

            // add fields to Communication
            net.medfusion.integrations.webservices.medfusion.objects.Fields theFields = new net.medfusion.integrations.webservices.medfusion.objects.Fields();
            theFields
                    .setFields((net.medfusion.integrations.webservices.medfusion.objects.Field[]) fieldVector
                            .toArray(new net.medfusion.integrations.webservices.medfusion.objects.Field[fieldVector
                                    .size()]));
            communication.setFields(theFields);

            System.out
                    .println("Communication is : " + communication.toString());

            int commId = service.sendCommunication(communication);
            System.out.println("Got back CommId : " + commId);
            lineBreak();

            System.out.println("Get Communications created in the last week");
            cal = new GregorianCalendar();
            cal.add(GregorianCalendar.DAY_OF_WEEK, -7);
            commCrit = new Criteria[1];
            commCrit[0] = new Criteria(
                    WebServiceConstants.FIELD_COMM_CREATIONDATE,
                    WebServiceConstants.OPERATION_GREATERTHAN, cal.getTime());
            resultComms = service.getCommsByCriteria(
                    SystemConfigProperties.WSFM_PRACTICEID, commCrit);
            WebserviceUtils.dumpResults(resultComms);
        } catch (WebServiceExceptionInvalidParameter e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Test methods related to EMs
     * 
     * @param service
     */
    @Test
    public void testEMWebServices() {
        try {
            System.out.println("Get all EMFields");
            EMField[] emfields = service.getAllEMFields();
            WebserviceUtils.dumpResults(emfields);

            System.out.println("Get EMs created in the last month");
            Calendar cal = new GregorianCalendar();
            cal.add(GregorianCalendar.MONTH, -1);
            Criteria[] emCriteria = new Criteria[1];
            emCriteria[0] = new Criteria(
                    WebServiceConstants.FIELD_EM_DATECREATED,
                    WebServiceConstants.OPERATION_GREATERTHAN, cal.getTime());
            EM[] ems = service.getEMsByCriteria(
                    WebServiceConstants.FIELD_INT_NONE, emCriteria);
            WebserviceUtils.dumpResults(ems);

            if (ems != null && ems.length > 0) {
                System.out.println("Get EMStatuses for these messages");
                int[] emIds = new int[ems.length];
                for (int emCt = 0; emCt < ems.length; emCt++) {
                    emIds[emCt] = ((Integer) ems[emCt]
                            .getFieldValue(WebServiceConstants.FIELD_EM_ID))
                            .intValue();
                }
                EMStatus[] emstatuses = service.getEMStatusesByEMIds(emIds);
                WebserviceUtils.dumpResults(emstatuses);
            }

            System.out
                    .println("Get all EM Templates for a particular practice.");
            Criteria[] emTemplateCriteria = new Criteria[1];
            emTemplateCriteria[0] = new Criteria(
                    WebServiceConstants.FIELD_EMTEMPLATE_PRACTICEID,
                    WebServiceConstants.OPERATION_EQUALS, new Integer(
                            SystemConfigProperties.WSFM_PRACTICEID));
            EMTemplate[] emts = service.getEMTemplatesByCriteria(
                    WebServiceConstants.FIELD_INT_NONE, emTemplateCriteria);
            WebserviceUtils.dumpResults(emts);

            System.out.println("Create an Enhanced Message");
            EM msg = new EM();
            Vector emFieldVector = new Vector();
            emFieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_EM_DELIVERYMODE,
                            WebServiceConstants.FIELD_EM_DELIVERYMODE_SC));
            emFieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_EM_FREQTYPE,
                            WebServiceConstants.FIELD_EM_FREQTYPE_ONETIME));
            emFieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_EM_PRACTICEID,
                            new Integer(SystemConfigProperties.WSFM_PRACTICEID)));
            emFieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_EM_STAFFID, new Integer(
                                    SystemConfigProperties.WSFM_STAFFID1)));
            emFieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_EM_SUBJECT,
                            "This is a sample message"));
            emFieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_EM_TYPE,
                            WebServiceConstants.FIELD_EM_TYPE_OTHER));

            EMRecipient rec = new EMRecipient();
            Vector recFieldVector = new Vector();
            recFieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_EMRECIPIENT_TYPE,
                            WebServiceConstants.FIELD_EMRECIPIENT_TYPE_ALLPATIENTS));
            net.medfusion.integrations.webservices.medfusion.objects.Fields theEMRecipientFields = new net.medfusion.integrations.webservices.medfusion.objects.Fields();
            theEMRecipientFields
                    .setFields((net.medfusion.integrations.webservices.medfusion.objects.Field[]) recFieldVector
                            .toArray(new net.medfusion.integrations.webservices.medfusion.objects.Field[recFieldVector
                                    .size()]));
            rec.setFields(theEMRecipientFields);
            EMRecipient[] recipients = new EMRecipient[1];
            recipients[0] = rec;
            emFieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_EM_RECIPIENTS, recipients));

            net.medfusion.integrations.webservices.medfusion.objects.Fields theEMFields = new net.medfusion.integrations.webservices.medfusion.objects.Fields();
            theEMFields
                    .setFields((net.medfusion.integrations.webservices.medfusion.objects.Field[]) emFieldVector
                            .toArray(new net.medfusion.integrations.webservices.medfusion.objects.Field[emFieldVector
                                    .size()]));
            msg.setFields(theEMFields);

            EMTemplate msgTemplate = new EMTemplate();
            Vector emTemplateFieldVector = new Vector();
            emTemplateFieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_EMTEMPLATE_BODYTEXT,
                            "Dear ~PatientFirstName~,\nThis is a message for you from ~PracticeName~.\nThanks"));
            emTemplateFieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_EMTEMPLATE_NAME,
                            "A Test Message Template"));
            emTemplateFieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_EMTEMPLATE_PRACTICEID,
                            new Integer(SystemConfigProperties.WSFM_PRACTICEID)));
            emTemplateFieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_EMTEMPLATE_TYPE,
                            WebServiceConstants.FIELD_EM_TYPE_OTHER));
            net.medfusion.integrations.webservices.medfusion.objects.Fields theEMTemplateFields = new net.medfusion.integrations.webservices.medfusion.objects.Fields();
            theEMTemplateFields
                    .setFields((net.medfusion.integrations.webservices.medfusion.objects.Field[]) emTemplateFieldVector
                            .toArray(new net.medfusion.integrations.webservices.medfusion.objects.Field[emTemplateFieldVector
                                    .size()]));
            msgTemplate.setFields(theEMTemplateFields);

            int createdEmId = service.createEM(msg, msgTemplate);

            System.out.println("Delete the message just created.");
            service.deleteEM(createdEmId);

            try {
                System.out
                        .println("Delete a non-existant message, should fail.");
                service.deleteEM(5353553);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } catch (WebServiceExceptionInvalidParameter e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testVOVWebServices() {
        try {
            System.out.println("Get VOV's from the past Month");
            Calendar cal = new GregorianCalendar();
            cal.add(GregorianCalendar.MONTH, -1);
            Criteria[] vovCriteria = new Criteria[2];
            vovCriteria[0] = new Criteria(
                    WebServiceConstants.FIELD_VOV_CREATIONDATE,
                    WebServiceConstants.OPERATION_GREATERTHAN, cal.getTime());
            vovCriteria[1] = new Criteria(WebServiceConstants.FIELD_VOV_STATUS,
                    WebServiceConstants.OPERATION_EQUALS,
                    WebServiceConstants.FIELD_VOV_STATUS_OPEN);
            VOV[] vovResults = service.getVOVsByCriteria(
                    SystemConfigProperties.WSFM_PRACTICEID, vovCriteria);
            WebserviceUtils.dumpResults(vovResults);

            if (vovResults != null && vovResults.length > 0) {
                System.out
                        .println("Process a Virtual Office Visit with Advice");
                int lastEntry = vovResults.length - 1;
                VOV vov = (VOV) vovResults[lastEntry];
                Integer vovId = vov
                        .getFieldValueInt(WebServiceConstants.FIELD_VOV_ID);
                Integer staffId = vov
                        .getFieldValueInt(WebServiceConstants.FIELD_VOV_STAFFID);
                Integer memberId = vov
                        .getFieldValueInt(WebServiceConstants.FIELD_VOV_MEMBERID);
                String status = vov
                        .getFieldValueString(WebServiceConstants.FIELD_VOV_STATUS);
                System.out.println("Staff with Id : " + staffId
                        + " is working with VOV Id : " + vovId
                        + " that has status : " + status
                        + " for Member with Id : " + memberId);

                CPTCode[] cptResults = service.getAllCPTCodes();
                CPTCode cpt = (CPTCode) cptResults[0];
                Integer cptCodeId = cpt
                        .getFieldValueInt(WebServiceConstants.FIELD_CPTCODE_ID);
                String cptCodeName = cpt
                        .getFieldValueString(WebServiceConstants.FIELD_CPTCODE_NAME);
                System.out.println("Working with CPT Code Id : " + cptCodeId
                        + " and Name : " + cptCodeName);

                DiagCode[] dcResults = service.getAllDiagCodes();
                DiagCode dc = (DiagCode) dcResults[100];
                Integer diagCodeId = dc
                        .getFieldValueInt(WebServiceConstants.FIELD_DIAGCODE_ID);
                String diagCodeName = dc
                        .getFieldValueString(WebServiceConstants.FIELD_DIAGCODE_NAME);
                String diagCodeCode = dc
                        .getFieldValueString(WebServiceConstants.FIELD_DIAGCODE_CODE);
                System.out.println("Working with Diag Code Id : " + diagCodeId
                        + " and Name : " + diagCodeName + " and Code : "
                        + diagCodeCode);

                System.out
                        .println("Creating a simple communication to send the advice to the patient");
                // create a Communication to send along with the VOV (Will
                // contain the advice)
                Comm communication = new Comm();

                Vector fieldVector = new Vector();

                fieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_COMM_SUBJECT,
                                "Virtual Office Visit Response"));
                fieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_COMM_MESSAGE,
                                "Hello,\n\nThanks for using the Virtual Office Visit/Ask A Doctor service.\n\nThanks, Your Doctor"));

                // add fields to Communication
                net.medfusion.integrations.webservices.medfusion.objects.Fields theFields = new net.medfusion.integrations.webservices.medfusion.objects.Fields();
                theFields
                        .setFields((net.medfusion.integrations.webservices.medfusion.objects.Field[]) fieldVector
                                .toArray(new net.medfusion.integrations.webservices.medfusion.objects.Field[fieldVector
                                        .size()]));
                communication.setFields(theFields);

             //   System.out.println("Processing the VOV at the default charge");
              //  BigDecimal fee = WebServiceConstants.FIELD_VOV_DEFAULTPAYMENT;
              //  int commId = service.communicateAndProcessVOV(vovId.intValue(),
              //          staffId.intValue(), cptCodeId.intValue(),
               //         diagCodeId.intValue(), fee, communication);

                System.out
                        .println("Get the VOV we just processed and check it's status...");
                vovCriteria = new Criteria[1];
                vovCriteria[0] = new Criteria(WebServiceConstants.FIELD_VOV_ID,
                        WebServiceConstants.OPERATION_EQUALS, vovId);
                vovResults = service.getVOVsByCriteria(
                        SystemConfigProperties.WSFM_PRACTICEID, vovCriteria);
                WebserviceUtils.dumpResults(vovResults);
            }
        } catch (WebServiceExceptionInvalidParameter e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testVOVWithPrescriptionWebServices() {
        try {
            System.out.println("Get VOV's from the past Month");
            Calendar cal = new GregorianCalendar();
            cal.add(GregorianCalendar.MONTH, -1);
            Criteria[] vovCriteria = new Criteria[2];
            vovCriteria[0] = new Criteria(
                    WebServiceConstants.FIELD_VOV_CREATIONDATE,
                    WebServiceConstants.OPERATION_GREATERTHAN, cal.getTime());
            vovCriteria[1] = new Criteria(WebServiceConstants.FIELD_VOV_STATUS,
                    WebServiceConstants.OPERATION_EQUALS,
                    WebServiceConstants.FIELD_VOV_STATUS_OPEN);
            VOV[] vovResults = service.getVOVsByCriteria(
                    SystemConfigProperties.WSFM_PRACTICEID, vovCriteria);
            WebserviceUtils.dumpResults(vovResults);

            if (vovResults != null && vovResults.length > 0) {
                System.out
                        .println("Process a Virtual Office Visit/Ask a Doctor with Advice and a Prescription");
                int lastEntry = vovResults.length - 1;
                VOV vov = (VOV) vovResults[lastEntry];
                Integer vovId = vov
                        .getFieldValueInt(WebServiceConstants.FIELD_VOV_ID);
                Integer staffId = vov
                        .getFieldValueInt(WebServiceConstants.FIELD_VOV_STAFFID);
                Integer memberId = vov
                        .getFieldValueInt(WebServiceConstants.FIELD_VOV_MEMBERID);
                String status = vov
                        .getFieldValueString(WebServiceConstants.FIELD_VOV_STATUS);
                System.out.println("Staff with Id : " + staffId
                        + " is working with VOV Id : " + vovId
                        + " that has status : " + status
                        + " for Member with Id : " + memberId);

                CPTCode[] cptResults = service.getAllCPTCodes();
                CPTCode cpt = (CPTCode) cptResults[0];
                Integer cptCodeId = cpt
                        .getFieldValueInt(WebServiceConstants.FIELD_CPTCODE_ID);
                String cptCodeName = cpt
                        .getFieldValueString(WebServiceConstants.FIELD_CPTCODE_NAME);
                System.out.println("Working with CPT Code Id : " + cptCodeId
                        + " and Name : " + cptCodeName);

                // DiagCode[] dcResults = service.getAllDiagCodes();
                // DiagCode dc = (DiagCode) dcResults[187];
                Integer diagCodeId = new Integer(100); // dc.getFieldValueInt(WebServiceConstants.FIELD_DIAGCODE_ID);
                String diagCodeName = "Sample Diag Code Name"; // dc.getFieldValueString(WebServiceConstants.FIELD_DIAGCODE_NAME);
                String diagCodeCode = "Sample Diag Code Code"; // dc.getFieldValueString(WebServiceConstants.FIELD_DIAGCODE_CODE);
                System.out.println("Working with Diag Code Id : " + diagCodeId
                        + " and Name : " + diagCodeName + " and Code : "
                        + diagCodeCode);

                System.out
                        .println("Creating a simple communication to send the advice to the patient");
                // create a Communication to send along with the VOV (Will
                // contain the advice)
                Comm communication = new Comm();

                Vector fieldVector = new Vector();

                fieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_COMM_SUBJECT,
                                "Concerning your VOV/AAD"));
                fieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_COMM_MESSAGE,
                                "Hello,\n\nI advise that you fill the prescription I have prescribed for you.  Schedule an Appointment for a Neuro exam in the coming weeks.\n\nThanks, Your Doctor"));

                // add fields to Communication
                net.medfusion.integrations.webservices.medfusion.objects.Fields theFields = new net.medfusion.integrations.webservices.medfusion.objects.Fields();
                theFields
                        .setFields((net.medfusion.integrations.webservices.medfusion.objects.Field[]) fieldVector
                                .toArray(new net.medfusion.integrations.webservices.medfusion.objects.Field[fieldVector
                                        .size()]));
                communication.setFields(theFields);

                System.out.println("Get a SigCode.");
                SigCode[] sigCodes = service
                        .getSigCodesByPracticeId(SystemConfigProperties.WSFM_PRACTICEID);
                Integer sigCodeId = null;

                if (sigCodes != null && sigCodes.length > 0) {
                    SigCode sigCode = sigCodes[0];
                    sigCodeId = (Integer) sigCode
                            .getFieldValue(WebServiceConstants.FIELD_SIGCODE_ID);
                    lineBreak();
                } else {
                    // if no SigCode exists, create one and use it
                    sigCodeId = new Integer(service.addSigCode("2TABPRN",
                            "Take two as needed",
                            SystemConfigProperties.WSFM_PRACTICEID));
                }

                System.out.println("Using SigCode Id " + sigCodeId);

                System.out.println("Get a RouteCode.");
                RouteCode[] routeCodes = service.getAllRouteCodes();
                Integer routeCodeId = null;

                if (routeCodes != null && routeCodes.length > 0) {
                    RouteCode routeCode = routeCodes[0];
                    routeCodeId = (Integer) routeCode
                            .getFieldValue(WebServiceConstants.FIELD_ROUTECODE_ID);
                    System.out.println("Using RouteCode Id " + routeCodeId);
                    lineBreak();
                }

                System.out
                        .println("Creating a Prescription to prescribe to the Patient for their VOV/AAD");
                // create an RxRefillReqEntry to prescribe to the Patient
                RxRefillReqEntry rxRefillReqEntry = new RxRefillReqEntry();

                Vector rxFieldVector = new Vector();

                rxFieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_RXREFILLREQENTRY_DRUGNAME,
                                "Vicodin"));
                rxFieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_RXREFILLREQENTRY_DOSAGE,
                                "15mg"));
                rxFieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_RXREFILLREQENTRY_REFILLS,
                                new BigDecimal(1)));
                rxFieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_RXREFILLREQENTRY_SIGCODEID,
                                sigCodeId));
                rxFieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_RXREFILLREQENTRY_ROUTECODEID,
                                routeCodeId));
                rxFieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_RXREFILLREQENTRY_QUANTITY,
                                new Integer(20)));

                // Create the Pharmacy (From Known Pharmacy Data)
                Pharmacy pharm = new Pharmacy();

                Vector pharmacyFieldVector = new Vector();

                pharmacyFieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_PHARMACY_ID,
                                new Integer(10001)));
                pharmacyFieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_PHARMACY_NAME,
                                "Pharma"));

                net.medfusion.integrations.webservices.medfusion.objects.Fields thePharmacyFields = new net.medfusion.integrations.webservices.medfusion.objects.Fields();
                thePharmacyFields
                        .setFields((net.medfusion.integrations.webservices.medfusion.objects.Field[]) pharmacyFieldVector
                                .toArray(new net.medfusion.integrations.webservices.medfusion.objects.Field[pharmacyFieldVector
                                        .size()]));
                pharm.setFields(thePharmacyFields);

                rxFieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_RXREFILLREQENTRY_PHARMACY,
                                pharm));

                net.medfusion.integrations.webservices.medfusion.objects.Fields theRxFields = new net.medfusion.integrations.webservices.medfusion.objects.Fields();
                theRxFields
                        .setFields((net.medfusion.integrations.webservices.medfusion.objects.Field[]) rxFieldVector
                                .toArray(new net.medfusion.integrations.webservices.medfusion.objects.Field[rxFieldVector
                                        .size()]));
                rxRefillReqEntry.setFields(theRxFields);

                RxRefillReqEntry[] rxRefillReqEntries = new RxRefillReqEntry[1];
                rxRefillReqEntries[0] = rxRefillReqEntry;

                System.out
                        .println("Processing the VOV With Prescription at a Charge of $15.00");
                BigDecimal fee = new BigDecimal(15.00);
                int commId = service
                        .communicateAndProcessVOVWithPrescription(
                                vovId.intValue(),
                                staffId.intValue(),
                                cptCodeId.intValue(),
                                diagCodeId.intValue(),
                                fee,
                                rxRefillReqEntries,
                                WebServiceConstants.FIELD_RXREFILLREQ_DELIVERYMETHOD_CALLINTHERX,
                                communication);

                System.out
                        .println("Get the VOV we just processed and check it's status...");
                vovCriteria = new Criteria[1];
                vovCriteria[0] = new Criteria(WebServiceConstants.FIELD_VOV_ID,
                        WebServiceConstants.OPERATION_EQUALS, vovId);
                vovResults = service.getVOVsByCriteria(
                        SystemConfigProperties.WSFM_PRACTICEID, vovCriteria);
                WebserviceUtils.dumpResults(vovResults);
            }
        } catch (WebServiceExceptionInvalidParameter e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testStatementWebServices() {
        final SimpleDateFormat StatementDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd");

        try {
            Integer[] memberIds = {
                    new Integer(SystemConfigProperties.WSFM_MEMBERID1),
                    new Integer(SystemConfigProperties.WSFM_MEMBERID2),
                    new Integer(SystemConfigProperties.WSFM_MEMBERID3) };
            Integer[] locationIds = { null,
                    new Integer(SystemConfigProperties.WSFM_LOCATIONID1),
                    new Integer(SystemConfigProperties.WSFM_LOCATIONID2) };
            Date[] creationDates = { StatementDateFormat.parse("2007-12-01"),
                    StatementDateFormat.parse("2007-12-31"),
                    StatementDateFormat.parse("2008-01-05") };
            Date[] dueDates = { StatementDateFormat.parse("2008-01-05"),
                    StatementDateFormat.parse("2008-01-31"),
                    StatementDateFormat.parse("2008-02-05") };
            BigDecimal[] dueAmounts = { new BigDecimal(15.00),
                    new BigDecimal(75.00), new BigDecimal(5.00) };
            BigDecimal[] balanceAmounts = { null, new BigDecimal(150.00),
                    new BigDecimal(5.00) };
            String[] uniqueIds = { "SIDG123456A", "SIDG123456B", "SIDG123456C" };

            List statementIds = new ArrayList();

            System.out
                    .println("Create a few Statement Objects with known MemberIds");
            for (int i = 0; i < 3; i++) {
                Statement statement = new Statement();

                Vector fieldVector = new Vector();

                fieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_STATEMENT_PRACTICEID,
                                new Integer(
                                        SystemConfigProperties.WSFM_PRACTICEID)));
                fieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_STATEMENT_LOCATIONID,
                                locationIds[i]));
                fieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_STATEMENT_MEMBERID,
                                memberIds[i]));

                Calendar statementCreationDate = new GregorianCalendar();
                statementCreationDate.setTime(creationDates[i]);
                fieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_STATEMENT_CREATIONDATE,
                                statementCreationDate));

                Calendar statementDueDate = new GregorianCalendar();
                statementDueDate.setTime(dueDates[i]);

                fieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_STATEMENT_DUEDATE,
                                statementDueDate.getTime()));

                // Add Statement File
                File statementFile = new File(
                        SystemConfigProperties.STATEMENTFILE);
                if (statementFile.exists()) {
                    System.out
                            .println("Converting Statement File to byte array...");
                    byte[] file = getBytesFromFile(statementFile);
                    fieldVector
                            .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                    WebServiceConstants.FIELD_STATEMENT_FILE,
                                    file));
                    fieldVector
                            .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                    WebServiceConstants.FIELD_STATEMENT_FILETYPE,
                                    WebServiceConstants.FIELD_STATEMENT_FILETYPE_PDF));
                }

                fieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_STATEMENT_UNIQUEID,
                                uniqueIds[i]));
                fieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_STATEMENT_AMOUNTDUE,
                                dueAmounts[i]));
                fieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_STATEMENT_BALANCE,
                                balanceAmounts[i]));

                // add fields to Statement
                net.medfusion.integrations.webservices.medfusion.objects.Fields theFields = new net.medfusion.integrations.webservices.medfusion.objects.Fields();
                theFields
                        .setFields((net.medfusion.integrations.webservices.medfusion.objects.Field[]) fieldVector
                                .toArray(new net.medfusion.integrations.webservices.medfusion.objects.Field[fieldVector
                                        .size()]));
                statement.setFields(theFields);

                int statementId = service.createStatement(statement);
                statementIds.add(new Integer(statementId));
                System.out.println("Statement object created with id: "
                        + statementId);
            }

            System.out.println("Create a Statement Object with a Non-Member");
            Statement statement = new Statement();

            Vector fieldVector = new Vector();

            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_STATEMENT_PRACTICEID,
                            new Integer(SystemConfigProperties.WSFM_PRACTICEID)));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_STATEMENT_LOCATIONID,
                            new Integer(SystemConfigProperties.WSFM_LOCATIONID1)));

            Calendar statementCreationDate = new GregorianCalendar();
            statementCreationDate.setTime(StatementDateFormat
                    .parse("2007-12-01"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_STATEMENT_CREATIONDATE,
                            statementCreationDate));

            Calendar statementDueDate = new GregorianCalendar();
            statementDueDate.setTime(StatementDateFormat.parse("2008-01-05"));

            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_STATEMENT_DUEDATE,
                            statementDueDate.getTime()));

            // Add Statement File
            File statementFile = new File(SystemConfigProperties.STATEMENTFILE);
            if (statementFile.exists()) {
                System.out
                        .println("Converting Statement File to byte array...");
                byte[] file = getBytesFromFile(statementFile);
                fieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_STATEMENT_FILE, file));
                fieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_STATEMENT_FILETYPE,
                                WebServiceConstants.FIELD_STATEMENT_FILETYPE_PDF));
            }

            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_STATEMENT_UNIQUEID,
                            "SIDG123456D"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_STATEMENT_AMOUNTDUE,
                            new BigDecimal(25.00)));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_STATEMENT_BALANCE,
                            new BigDecimal(55.00)));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_STATEMENT_PATIENTPRESHAREDKEY,
                            "9999"));

            // add fields to Statement
            net.medfusion.integrations.webservices.medfusion.objects.Fields theFields = new net.medfusion.integrations.webservices.medfusion.objects.Fields();
            theFields
                    .setFields((net.medfusion.integrations.webservices.medfusion.objects.Field[]) fieldVector
                            .toArray(new net.medfusion.integrations.webservices.medfusion.objects.Field[fieldVector
                                    .size()]));
            statement.setFields(theFields);

            int statementId = service.createStatement(statement, "Jacob",
                    "Wilson", "55555", "SSID123456", "noreply@medfusion.net");
            statementIds.add(new Integer(statementId));
            System.out.println("Statement object created with id: "
                    + statementId);

            System.out.println("Get all 'New' Statements");
            Criteria[] stmtCriteria = new Criteria[1];
            stmtCriteria[0] = new Criteria(
                    WebServiceConstants.FIELD_STATEMENT_STATUS,
                    WebServiceConstants.OPERATION_EQUALS,
                    WebServiceConstants.FIELD_STATEMENT_STATUS_NEW);
            Statement[] resultStatements = service.getStatementsByCriteria(
                    WebServiceConstants.FIELD_INT_NONE, stmtCriteria);
            WebserviceUtils.dumpResults(resultStatements);

            System.out
                    .println("Let's update the Out of Date field on one of those Statements (The Staff Id is optional)");
            int last = statementIds.size() - 1;
            Integer lastStatementId = (Integer) statementIds.get(last);
            service.setStatementOutOfDate(lastStatementId.intValue(),
                    WebServiceConstants.FIELD_INT_NONE);

            System.out
                    .println("Let's get that Statement we just changed with Id "
                            + lastStatementId);
            stmtCriteria = new Criteria[1];
            stmtCriteria[0] = new Criteria(
                    WebServiceConstants.FIELD_STATEMENT_ID,
                    WebServiceConstants.OPERATION_EQUALS, lastStatementId);
            resultStatements = service.getStatementsByCriteria(
                    WebServiceConstants.FIELD_INT_NONE, stmtCriteria);
            WebserviceUtils.dumpResults(resultStatements);

            lineBreak();
        } catch (WebServiceExceptionInvalidParameter e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testExternalIdMethods() {
        try {
            try {
                System.out.println("Add external system to the practice");
                service.addESToPractice(SystemConfigProperties.WSFM_PRACTICEID,
                        SystemConfigProperties.EXTERNALSYSTEM_TESTPMS, true);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            lineBreak();

            System.out
                    .println("Get all external systems that a practice has access to");
            ES[] esResults = service
                    .getExternalSystemsForPractice(SystemConfigProperties.WSFM_PRACTICEID);
            WebserviceUtils.dumpResults(esResults);

            System.out
                    .println("Set the external Id for a Practice to a specific value");
            service.setExternalIdForPractice(
                    SystemConfigProperties.WSFM_PRACTICEID,
                    SystemConfigProperties.EXTERNALSYSTEM_TESTPMS,
                    SystemConfigProperties.WSFM_EXTERNALID1);
            lineBreak();

            System.out
                    .println("Get the external Id for the Practice that was just set");
            String externalId = service.getExternalIdForPractice(
                    SystemConfigProperties.WSFM_PRACTICEID,
                    SystemConfigProperties.EXTERNALSYSTEM_TESTPMS);
            System.out.println("External Id is: '" + externalId + "'");
            lineBreak();

            System.out
                    .println("Set the external Id for a Location to a specific value");
            service.setExternalIdForLocation(
                    SystemConfigProperties.WSFM_LOCATIONID1,
                    SystemConfigProperties.EXTERNALSYSTEM_TESTPMS,
                    SystemConfigProperties.WSFM_EXTERNALID1);
            lineBreak();

            System.out.println("Get the Location by the id that was just set");
            Location extLoc = service.getLocationByExternalId(
                    SystemConfigProperties.WSFM_PRACTICEID,
                    SystemConfigProperties.WSFM_EXTERNALID1,
                    SystemConfigProperties.EXTERNALSYSTEM_TESTPMS);
            System.out.println("Location:\n" + extLoc);
            lineBreak();

            System.out
                    .println("Set the external Id for a Member to a specific value");
            service.setExternalIdForMember(
                    SystemConfigProperties.WSFM_PRACTICEID,
                    SystemConfigProperties.WSFM_MEMBERID1,
                    SystemConfigProperties.EXTERNALSYSTEM_TESTPMS,
                    SystemConfigProperties.WSFM_EXTERNALID1);
            service.setExternalIdForMember(
                    SystemConfigProperties.WSFM_PRACTICEID,
                    SystemConfigProperties.WSFM_MEMBERID2,
                    SystemConfigProperties.EXTERNALSYSTEM_TESTPMS,
                    SystemConfigProperties.WSFM_EXTERNALID2);
            lineBreak();

            System.out
                    .println("Get Members with the set of external Ids that was just set");
            String[] externalIdsMember = new String[2];
            externalIdsMember[0] = SystemConfigProperties.WSFM_EXTERNALID1;
            externalIdsMember[1] = SystemConfigProperties.WSFM_EXTERNALID2;
            String[] memberFields = new String[4];
            memberFields[0] = WebServiceConstants.FIELD_MEMBER_FIRSTNAME;
            memberFields[1] = WebServiceConstants.FIELD_MEMBER_LASTNAME;
            memberFields[2] = WebServiceConstants.FIELD_MEMBER_DATECREATED;
            memberFields[3] = WebServiceConstants.FIELD_MEMBER_ID;
            MemberMap[] memberResults = service.getMemberMapsByExternalIds(
                    SystemConfigProperties.WSFM_PRACTICEID, memberFields,
                    SystemConfigProperties.EXTERNALSYSTEM_TESTPMS,
                    externalIdsMember);
            WebserviceUtils.dumpResults(memberResults);

            System.out
                    .println("Set the external Id for a Staff member to a specific value");
            service.setExternalIdForStaff(
                    SystemConfigProperties.WSFM_PRACTICEID,
                    SystemConfigProperties.WSFM_STAFFID1,
                    SystemConfigProperties.EXTERNALSYSTEM_TESTPMS,
                    SystemConfigProperties.WSFM_EXTERNALID1);
            service.setExternalIdForStaff(
                    SystemConfigProperties.WSFM_PRACTICEID,
                    SystemConfigProperties.WSFM_STAFFID2,
                    SystemConfigProperties.EXTERNALSYSTEM_TESTPMS,
                    SystemConfigProperties.WSFM_EXTERNALID2);
            lineBreak();

            System.out
                    .println("Get Staff with the set of external Ids that was just set");
            String[] externalIds = new String[2];
            externalIds[0] = SystemConfigProperties.WSFM_EXTERNALID1;
            externalIds[1] = SystemConfigProperties.WSFM_EXTERNALID2;
            Staff[] staffResults = service.getStaffByExternalIds(
                    SystemConfigProperties.WSFM_PRACTICEID,
                    SystemConfigProperties.EXTERNALSYSTEM_TESTPMS, externalIds);
            WebserviceUtils.dumpResults(staffResults);
        } catch (WebServiceExceptionInvalidParameter e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testEMDate() {
        System.out.println("+ sendMessage()");
        int createdEmId = 0;
        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:sszzz", Locale.US);

        try {
            System.out
                    .println("Get all EM Templates for a particular practice.");
            Criteria[] emTemplateCriteria = new Criteria[1];
            emTemplateCriteria[0] = new Criteria(
                    WebServiceConstants.FIELD_EMTEMPLATE_PRACTICEID,
                    WebServiceConstants.OPERATION_EQUALS, new Integer(
                            SystemConfigProperties.WSFM_PRACTICEID));
            emTemplateCriteria[0] = new Criteria(
                    WebServiceConstants.FIELD_EMTEMPLATE_NAME,
                    WebServiceConstants.OPERATION_EQUALS, "Date Test");
            EMTemplate[] emts = service.getEMTemplatesByCriteria(
                    WebServiceConstants.FIELD_INT_NONE, emTemplateCriteria);
            WebserviceUtils.dumpResults(emts);

            if (null != emts) {
                EMTemplate myTemplate = emts[0];
                Fields emTemplateFields = myTemplate.getFields();
                EMField[] templateFieldsField = (EMField[]) emTemplateFields
                        .getValue(WebServiceConstants.FIELD_EMTEMPLATE_FIELDS);
                Fields templateFields = templateFieldsField[0].getFields();
                WebserviceUtils.dumpResults(templateFieldsField);

                EM msg = new EM();
                Vector emFieldVector = new Vector();
                emFieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_EM_DELIVERYMODE,
                                WebServiceConstants.FIELD_EM_DELIVERYMODE_SC));
                emFieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_EM_FREQTYPE,
                                WebServiceConstants.FIELD_EM_FREQTYPE_ONETIME));
                emFieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_EM_PRACTICEID,
                                new Integer(
                                        SystemConfigProperties.WSFM_PRACTICEID)));
                emFieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_EM_STAFFID,
                                new Integer(
                                        SystemConfigProperties.WSFM_STAFFID1)));
                emFieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_EM_SUBJECT,
                                "This is a sample message"));
                emFieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_EM_TYPE,
                                WebServiceConstants.FIELD_EM_TYPE_OTHER));

                EMRecipient rec = new EMRecipient();
                Vector recFieldVector = new Vector();
                recFieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_EMRECIPIENT_TYPE,
                                WebServiceConstants.FIELD_EMRECIPIENT_TYPE_ALLPATIENTS));
                net.medfusion.integrations.webservices.medfusion.objects.Fields theEMRecipientFields = new net.medfusion.integrations.webservices.medfusion.objects.Fields();
                theEMRecipientFields
                        .setFields((net.medfusion.integrations.webservices.medfusion.objects.Field[]) recFieldVector
                                .toArray(new net.medfusion.integrations.webservices.medfusion.objects.Field[recFieldVector
                                        .size()]));
                rec.setFields(theEMRecipientFields);
                EMRecipient[] recipients = new EMRecipient[1];
                recipients[0] = rec;
                emFieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_EM_RECIPIENTS,
                                recipients));

                List emFieldValueList = new ArrayList();

                EMFieldValue emFieldValue = new EMFieldValue();
                emFieldValueList.add(emFieldValue);
                Fields fields = new Fields();
                emFieldValue.setFields(fields);
                List<Field> fieldList = new ArrayList<Field>();
                Field fieldId = new Field();
                fieldList.add(fieldId);
                fieldId.setFieldName(WebServiceConstants.FIELD_EMFIELDVALUE_FIELDID);
                fieldId.setFieldValue(templateFields
                        .getValue(WebServiceConstants.FIELD_EMFIELD_ID));
                Field fieldValue = new Field();
                fieldList.add(fieldValue);
                fieldValue
                        .setFieldName(WebServiceConstants.FIELD_EMFIELDVALUE_VALUE);
                fieldValue.setFieldValue("04/25/2011");
                fields.setFields(fieldList.toArray(new Field[fieldList.size()]));

                emFieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_EM_FIELDVALUES,
                                emFieldValueList
                                        .toArray(new EMFieldValue[emFieldValueList
                                                .size()])));

                net.medfusion.integrations.webservices.medfusion.objects.Fields theEMFields = new net.medfusion.integrations.webservices.medfusion.objects.Fields();
                theEMFields
                        .setFields((net.medfusion.integrations.webservices.medfusion.objects.Field[]) emFieldVector
                                .toArray(new net.medfusion.integrations.webservices.medfusion.objects.Field[emFieldVector
                                        .size()]));
                msg.setFields(theEMFields);

                int createdEmID = service
                        .createEMFromTemplate(
                                msg,
                                myTemplate
                                        .getFieldValueInt(WebServiceConstants.FIELD_EMTEMPLATE_ID));
            }

        } catch (WebServiceExceptionInvalidParameter e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("- sendMessage()" + createdEmId);

    }

    /**
     * This method tests creation of MemberLocked object using optional MU
     * fields(race,ethnicity, language and contacttype); also (optionally) sends
     * each person the unlock invitation email. The email body will be the
     * standard version from TextConfig, or (if provided) a custom email
     * template & variables may be used.
     * 
     * @param service
     */
    @Test
    public void testCreateOrUpdateMultipleMUMemberLocked() {

        final SimpleDateFormat MMddyyyyFormat = new SimpleDateFormat(
                "MM/dd/yyyy");

        try {
            System.out
                    .println("entering testCreateOrUpdateMultipleMUMemberLocked()");
            // test creating or updating a MemberLocked
            MemberLocked member1 = new MemberLocked();

            Vector<Field> fieldVector = new Vector<Field>();

            // set the memberinactive id here to attempt an update, or leave it
            // out for a create
            // fieldVector
            // .add(new Field(WebServiceConstants.FIELD_MEMBERLOCKED_ID,new
            // Integer(10137)));

            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_PRACTICEMEMBER_PRACTICEID,
                            new Integer(SystemConfigProperties.WSFM_PRACTICEID)));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_SEX, new Integer(
                                    WebServiceConstants.FIELD_MEMBER_SEX_MALE)));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_LASTNAME, "RTE"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_FIRSTNAME,
                            "ENGINE"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_EMAIL,
                            "fakeid3@fake.net"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_HOMEPOSTALCODE,
                            "27905"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_DOB,
                            MMddyyyyFormat.parse("08/08/1980")));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_PRACTICEMEMBER_PATIENTID,
                            "0101010067"));
            // set the optional MU fields(race,ethnicty,language and
            // contacttype)
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_RACE,
                            WebServiceConstants.FIELD_MEMBER_RACE_WHITE));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_ETHNICITY,
                            WebServiceConstants.FIELD_MEMBER_ETHNICITY_NOT_HISPANIC_OR_LATINO));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_LANGUAGE, "Arabic"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_MEMBER_CONTACTTYPE,
                            WebServiceConstants.FIELD_MEMBER_CONTACTTYPE_MOBILE_PHONE));
            net.medfusion.integrations.webservices.medfusion.objects.Fields theFields = new Fields();
            theFields.setFields(fieldVector.toArray(new Field[fieldVector
                    .size()]));
            member1.setFields(theFields);

            MemberLocked[] memberlockedArray = new MemberLocked[] { member1 };
            String subjectTemplate = "my test subject";
            String template = "my test message, first_name = ${first_name}, last_name = ${last_name}";

            NameValuePair a = new NameValuePair("A", "a-value");
            NameValuePair b = new NameValuePair("B", "b-value");
            NameValuePair[] nvpArray1 = new NameValuePair[] { a, b };
            NameValuePairComposite nvpComposite1 = new NameValuePairComposite(
                    nvpArray1);

            NameValuePairComposite[] nvpCompositeArray = new NameValuePairComposite[] { nvpComposite1 };

            WebServiceReturnComposite[] wsReturnCompositeArray = service
                    .createOrUpdateMultipleMUMemberLocked(
                            memberlockedArray,
                            SystemConfigProperties.WSFM_STAFFID1,
                            WebServiceConstants.PARM_KEYTYPE_RANDOMSYSTEMGENERATEDCODE,
                            null, true, subjectTemplate, template,
                            nvpCompositeArray);
            System.out
                    .println("returned from createOrUpdateMultipleMUMemberLocked, wsReturnCompositeArray follows:");
            for (WebServiceReturnComposite wsrc : wsReturnCompositeArray) {
                System.out.println("wsrc=" + wsrc);
            }

        } catch (WebServiceExceptionInvalidParameter e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /*
     * ********************************* UNIT TESTING
     */
    @Test
    public void testStatementWebServicesWithActiveInactiveMembers() {
        // update the various fields for your own practice
        final SimpleDateFormat StatementDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd");

        final Integer PRACTICE_ID = 10000;
        final Integer LOCATION_ID = 10000;

        try {
            // active member
            Statement statement = new Statement();

            Vector fieldVector = new Vector();

            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_STATEMENT_PRACTICEID,
                            PRACTICE_ID));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_STATEMENT_LOCATIONID,
                            LOCATION_ID));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_STATEMENT_MEMBERID, 10001));

            Calendar statementCreationDate = new GregorianCalendar();
            statementCreationDate.setTime(new Date());
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_STATEMENT_CREATIONDATE,
                            statementCreationDate));

            Calendar statementDueDate = new GregorianCalendar();
            statementDueDate.setTime(new Date());
            statementDueDate.add(Calendar.DAY_OF_MONTH, 7);

            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_STATEMENT_DUEDATE,
                            statementDueDate.getTime()));

            // Add Statement File
            File statementFile = new File(SystemConfigProperties.STATEMENTFILE);
            if (statementFile.exists()) {
                System.out
                        .println("Converting Statement File to byte array...");
                byte[] file = getBytesFromFile(statementFile);
                fieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_STATEMENT_FILE, file));
                fieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_STATEMENT_FILETYPE,
                                WebServiceConstants.FIELD_STATEMENT_FILETYPE_PDF));
            }

            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_STATEMENT_UNIQUEID,
                            "234234"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_STATEMENT_AMOUNTDUE,
                            new BigDecimal(234.43)));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_STATEMENT_BALANCE,
                            new BigDecimal(150.00)));

            // add fields to Statement
            net.medfusion.integrations.webservices.medfusion.objects.Fields theFields = new net.medfusion.integrations.webservices.medfusion.objects.Fields();
            theFields
                    .setFields((net.medfusion.integrations.webservices.medfusion.objects.Field[]) fieldVector
                            .toArray(new net.medfusion.integrations.webservices.medfusion.objects.Field[fieldVector
                                    .size()]));
            statement.setFields(theFields);

            int statementId = service.createStatement(statement);
            System.out.println("Statement object created with id: "
                    + statementId);

            lineBreak();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        try {
            // inactive member with inactivemember id
            Statement statement = new Statement();

            Vector fieldVector = new Vector();

            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_STATEMENT_PRACTICEID,
                            PRACTICE_ID));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_STATEMENT_LOCATIONID,
                            LOCATION_ID));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_STATEMENT_MEMBERLOCKEDID,
                            10001));

            Calendar statementCreationDate = new GregorianCalendar();
            statementCreationDate.setTime(new Date());
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_STATEMENT_CREATIONDATE,
                            statementCreationDate));

            Calendar statementDueDate = new GregorianCalendar();
            statementDueDate.setTime(new Date());
            statementDueDate.add(Calendar.DAY_OF_MONTH, 7);

            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_STATEMENT_DUEDATE,
                            statementDueDate.getTime()));

            // Add Statement File
            File statementFile = new File(SystemConfigProperties.STATEMENTFILE);
            if (statementFile.exists()) {
                System.out
                        .println("Converting Statement File to byte array...");
                byte[] file = getBytesFromFile(statementFile);
                fieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_STATEMENT_FILE, file));
                fieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_STATEMENT_FILETYPE,
                                WebServiceConstants.FIELD_STATEMENT_FILETYPE_PDF));
            }

            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_STATEMENT_UNIQUEID,
                            "234234"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_STATEMENT_AMOUNTDUE,
                            new BigDecimal(234.43)));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_STATEMENT_BALANCE,
                            new BigDecimal(150.00)));

            // add fields to Statement
            net.medfusion.integrations.webservices.medfusion.objects.Fields theFields = new net.medfusion.integrations.webservices.medfusion.objects.Fields();
            theFields
                    .setFields((net.medfusion.integrations.webservices.medfusion.objects.Field[]) fieldVector
                            .toArray(new net.medfusion.integrations.webservices.medfusion.objects.Field[fieldVector
                                    .size()]));
            statement.setFields(theFields);

            int statementId = service.createStatement(statement);
            System.out.println("Statement object created with id: "
                    + statementId);

            lineBreak();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        try {
            // inactive member with practice id
            Statement statement = new Statement();

            Vector fieldVector = new Vector();

            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_STATEMENT_PRACTICEID,
                            PRACTICE_ID));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_STATEMENT_LOCATIONID,
                            LOCATION_ID));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_STATEMENT_PATIENTSENDINGSYSTEMID,
                            "PID12345"));

            Calendar statementCreationDate = new GregorianCalendar();
            statementCreationDate.setTime(new Date());
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_STATEMENT_CREATIONDATE,
                            statementCreationDate));

            Calendar statementDueDate = new GregorianCalendar();
            statementDueDate.setTime(new Date());
            statementDueDate.add(Calendar.DAY_OF_MONTH, 7);

            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_STATEMENT_DUEDATE,
                            statementDueDate.getTime()));

            // Add Statement File
            File statementFile = new File(SystemConfigProperties.STATEMENTFILE);
            if (statementFile.exists()) {
                System.out
                        .println("Converting Statement File to byte array...");
                byte[] file = getBytesFromFile(statementFile);
                fieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_STATEMENT_FILE, file));
                fieldVector
                        .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                                WebServiceConstants.FIELD_STATEMENT_FILETYPE,
                                WebServiceConstants.FIELD_STATEMENT_FILETYPE_PDF));
            }

            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_STATEMENT_UNIQUEID,
                            "234234"));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_STATEMENT_AMOUNTDUE,
                            new BigDecimal(234.43)));
            fieldVector
                    .add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
                            WebServiceConstants.FIELD_STATEMENT_BALANCE,
                            new BigDecimal(150.00)));

            // add fields to Statement
            net.medfusion.integrations.webservices.medfusion.objects.Fields theFields = new net.medfusion.integrations.webservices.medfusion.objects.Fields();
            theFields
                    .setFields((net.medfusion.integrations.webservices.medfusion.objects.Field[]) fieldVector
                            .toArray(new net.medfusion.integrations.webservices.medfusion.objects.Field[fieldVector
                                    .size()]));
            statement.setFields(theFields);

            int statementId = service.createStatement(statement);
            System.out.println("Statement object created with id: "
                    + statementId);

            lineBreak();
        } catch (WebServiceExceptionInvalidParameter e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

    }

    // Returns the contents of the file in a byte array.

    public byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int) length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "
                    + file.getName());
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;
    }

    private static void lineBreak() {
        System.out
                .println("\n------------------------------------------------------------\n");
    }

}
