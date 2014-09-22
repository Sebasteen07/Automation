package net.medfusion.integrations.webservices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.medfusion.encounter.ws.WebServiceConstants;
import net.medfusion.integrations.webservices.common.exceptions.WebServiceExceptionInvalidParameter;
import net.medfusion.integrations.webservices.framework.SystemConfigProperties;
import net.medfusion.integrations.webservices.intengine.IntengineServiceLocator;
import net.medfusion.integrations.webservices.intengine.IntengineSoapBindingStub;
import net.medfusion.integrations.webservices.intengine.objects.Change;
import net.medfusion.integrations.webservices.intengine.objects.ChangeGroup;
import net.medfusion.integrations.webservices.intengine.objects.ChangeQuestion;
import net.medfusion.integrations.webservices.intengine.objects.Field;
import net.medfusion.integrations.webservices.medfusion.objects.WebServiceReturnComposite;
import net.medfusion.integrations.webservices.utils.WebserviceUtils;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IntegrationEngineTest {
    private IntengineServiceLocator ieLocator = null;
    private IntengineSoapBindingStub ieService = null;

    // Change Constants
    // Question Group and Question Constants
    private static Integer HEALTHHISTORYQUESTIONGROUPID = 16;
    private static Integer HEALTHHISTORYQUESTIONID_MEMBERID = 1600;

    private static Map<String, String> healthHistoryQuestionFields = new HashMap<String, String>();

    static {
        healthHistoryQuestionFields.put(
                Integer.toString(HEALTHHISTORYQUESTIONID_MEMBERID),
                Integer.toString(SystemConfigProperties.WSFM_MEMBERID1));
    }

    private static Integer MEDICATIONQUESTIONGROUPID = 17;
    private static Integer MEDICATIONQUESTIONID_MEMBERID = 5000;
    private static Integer MEDICATIONQUESTIONID_MEDICATIONID = 5001;
    private static Integer MEDICATIONQUESTIONID_MEDICATIONNAME = 5002;
    private static Integer MEDICATIONQUESTIONID_MEDICATIONSTATUS = 5003;
    private static Integer MEDICATIONQUESTIONID_MEDICATIONDOSAGE = 5004;
    private static Integer MEDICATIONQUESTIONID_MEDICATIONDOSAGEUNIT = 5005;
    private static Integer MEDICATIONQUESTIONID_MEDICATIONFREQUENCY = 5006;

    // private static Map<String, String> medicationQuestionFields = new
    // HashMap<String, String>();
    //
    // static
    // {
    // medicationQuestionFields.put(Integer.toString(MEDICATIONQUESTIONID_MEMBERID),
    // Integer.toString(WSFM_MEMBERID1));
    // medicationQuestionFields.put(Integer.toString(MEDICATIONQUESTIONID_MEDICATIONID),
    // "24");
    // medicationQuestionFields.put(Integer.toString(MEDICATIONQUESTIONID_MEDICATIONNAME),
    // "Vicodin-Update");
    // medicationQuestionFields.put(Integer.toString(MEDICATIONQUESTIONID_MEDICATIONSTATUS),
    // "Active");
    // medicationQuestionFields.put(Integer.toString(MEDICATIONQUESTIONID_MEDICATIONDOSAGE),
    // "50");
    // medicationQuestionFields.put(Integer.toString(MEDICATIONQUESTIONID_MEDICATIONDOSAGEUNIT),
    // "mg");
    // medicationQuestionFields.put(Integer.toString(MEDICATIONQUESTIONID_MEDICATIONFREQUENCY),
    // "After Meals");
    // }

    private static Map<String, String> getMedicationQuestionFields(
            Integer memberId, String medicationId, String medicationName,
            String medicationStatus, String medicationDosage,
            String medicationDosageUnit, String medicationFrequency) {
        Map<String, String> medicationQuestionFields = new HashMap<String, String>();

        medicationQuestionFields.put(
                Integer.toString(MEDICATIONQUESTIONID_MEMBERID),
                Integer.toString(memberId));
        if (medicationId != null)
            medicationQuestionFields.put(
                    Integer.toString(MEDICATIONQUESTIONID_MEDICATIONID),
                    medicationId);
        medicationQuestionFields.put(
                Integer.toString(MEDICATIONQUESTIONID_MEDICATIONNAME),
                medicationName);
        medicationQuestionFields.put(
                Integer.toString(MEDICATIONQUESTIONID_MEDICATIONSTATUS),
                medicationStatus);
        medicationQuestionFields.put(
                Integer.toString(MEDICATIONQUESTIONID_MEDICATIONDOSAGE),
                medicationDosage);
        medicationQuestionFields.put(
                Integer.toString(MEDICATIONQUESTIONID_MEDICATIONDOSAGEUNIT),
                medicationDosageUnit);
        medicationQuestionFields.put(
                Integer.toString(MEDICATIONQUESTIONID_MEDICATIONFREQUENCY),
                medicationFrequency);

        return medicationQuestionFields;
    }

    /**
     * Helper method to create Integration Engine Field objects
     * 
     * @param fieldName
     * @param fieldValue
     * @param fieldDataType
     * @return net.medfusion.integrations.webservices.intengine.objects.Field
     */
    private static net.medfusion.integrations.webservices.intengine.objects.Field ieField(
            String fieldName, Object fieldValue, String fieldDataType) {
        return new net.medfusion.integrations.webservices.intengine.objects.Field(
                fieldName, fieldValue, fieldDataType);
    }

    private static ChangeGroup createChangeGroup(Integer questionGroupId,
            Map<String, String> questionFields) {
        ChangeGroup changeGroup = new ChangeGroup();

        List<Field> fieldList = new ArrayList<Field>();
        fieldList.add(ieField(WebServiceConstants.FIELD_CHQG_QUESTIONGROUPID,
                questionGroupId, WebServiceConstants.FIELD_DATATYPE_INT));

        ChangeQuestion[] changeQuestions = createChangeQuestions(questionFields);
        fieldList.add(ieField(WebServiceConstants.FIELD_CHQG_CHANGEQUESTIONS,
                changeQuestions, WebServiceConstants.FIELD_DATATYPE_ANY));

        net.medfusion.integrations.webservices.intengine.objects.Fields theFields = new net.medfusion.integrations.webservices.intengine.objects.Fields();
        theFields
                .setFields((net.medfusion.integrations.webservices.intengine.objects.Field[]) fieldList
                        .toArray(new net.medfusion.integrations.webservices.intengine.objects.Field[fieldList
                                .size()]));
        changeGroup.setFields(theFields);

        return changeGroup;
    }

    /**
     * Create array of ChangeQuestion objects - Add ChangeQuestion for each
     * Field
     * 
     * @param questionFields
     * @return ChangeQuestion[]
     */
    private static ChangeQuestion[] createChangeQuestions(
            Map<String, String> questionFields) {
        ChangeQuestion[] changeQuestionArray = new ChangeQuestion[questionFields
                .size()];

        int i = 0;
        Set<String> keys = questionFields.keySet();
        for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
            ChangeQuestion changeQuestion = new ChangeQuestion();

            String setKey = (String) iterator.next();
            String setValue = questionFields.get(setKey);

            List<Field> fieldList = new ArrayList<Field>();
            fieldList.add(ieField(WebServiceConstants.FIELD_CHQ_QUESTIONID,
                    Integer.valueOf(setKey),
                    WebServiceConstants.FIELD_DATATYPE_INT));
            fieldList.add(ieField(WebServiceConstants.FIELD_CHQ_VALUE,
                    setValue, WebServiceConstants.FIELD_DATATYPE_STRING));

            net.medfusion.integrations.webservices.intengine.objects.Fields theFields = new net.medfusion.integrations.webservices.intengine.objects.Fields();
            theFields
                    .setFields((net.medfusion.integrations.webservices.intengine.objects.Field[]) fieldList
                            .toArray(new net.medfusion.integrations.webservices.intengine.objects.Field[fieldList
                                    .size()]));
            changeQuestion.setFields(theFields);

            changeQuestionArray[i++] = changeQuestion;
        }

        return changeQuestionArray;
    }

    @BeforeClass
    public void oneTimeSetUp() throws Exception {
        // one-time initialization code
        System.out.println("@BeforeClass - oneTimeSetUp");

        ieLocator = new IntengineServiceLocator();

        ieLocator
                .setintengineEndpointAddress(SystemConfigProperties.intEngineURL);

        ieService = (IntengineSoapBindingStub) ieLocator.getintengine();

        ieService.setUsername(SystemConfigProperties.username);
        ieService.setPassword(SystemConfigProperties.password);

    }

    /**
     * Test Change search methods
     * 
     * @param service
     */
    @Test
    public void testChangeServices() {
        try {
            System.out.println("Retrieve all of the available Changes");
            Change[] chResults = ieService.getOutboundChanges(
                    WebServiceConstants.FIELD_INT_NONE,
                    WebServiceConstants.FIELD_INT_NONE);
            WebserviceUtils.dumpResults(chResults);

            /*
             * if (chResults != null && chResults.length > 0) {
             * System.out.println("Set the first change to Error"); Change ch =
             * chResults[0]; Integer changeId =
             * ch.getFieldValueInt(WebServiceConstants.FIELD_CHANGE_ID);
             * service.setChangeAsError(changeId.intValue(),
             * "This is a sample error message"); }
             * 
             * if (chResults != null && chResults.length > 1) {
             * System.out.println("Set the second change to Processed"); Change
             * ch = chResults[1]; Integer changeId =
             * ch.getFieldValueInt(WebServiceConstants.FIELD_CHANGE_ID); int[]
             * changeIds = new int[1]; changeIds[0] = changeId.intValue();
             * service.setChangesAsProcessed(changeIds); }
             */
        } catch (WebServiceExceptionInvalidParameter e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Test the Create Inbound Change Integration Engine Web Services
     * 
     * @param service
     */
    @Test
    public void testCreateOrUpdateInboundMedicationServices() {
        try {
            Change[] changes = new Change[1];

            Change change = new Change();

            List<Field> fieldList = new ArrayList<Field>();
            fieldList.add(ieField(
                    WebServiceConstants.FIELD_CHANGE_INTEGRATIONID,
                    SystemConfigProperties.WSFM_INTEGRATIONID,
                    WebServiceConstants.FIELD_DATATYPE_INT));
            // fieldList.add(ieField(WebServiceConstants.FIELD_CHANGE_TYPE,
            // WebServiceConstants.FIELD_CHANGE_TYPE_INBOUNDADD,
            // WebServiceConstants.FIELD_DATATYPE_STRING));
            fieldList.add(ieField(WebServiceConstants.FIELD_CHANGE_TYPE,
                    WebServiceConstants.FIELD_CHANGE_TYPE_INBOUNDUPDATE,
                    WebServiceConstants.FIELD_DATATYPE_STRING));
            fieldList.add(ieField(WebServiceConstants.FIELD_CHANGE_PRACTICEID,
                    SystemConfigProperties.WSFM_PRACTICEID,
                    WebServiceConstants.FIELD_DATATYPE_INT));

            ChangeGroup[] changeGroups1 = new ChangeGroup[2];
            changeGroups1[0] = createChangeGroup(HEALTHHISTORYQUESTIONGROUPID,
                    healthHistoryQuestionFields);
            changeGroups1[1] = createChangeGroup(
                    MEDICATIONQUESTIONGROUPID,
                    getMedicationQuestionFields(
                            SystemConfigProperties.WSFM_MEMBERID1, null,
                            "Vicodin", "Active", "50", "mg", "After meals"));
            fieldList.add(ieField(
                    WebServiceConstants.FIELD_CHANGE_CHANGEQUESTIONGROUPS,
                    changeGroups1, WebServiceConstants.FIELD_DATATYPE_ANY));

            // Update to a Medication : insert medication ID to
            // getMedicationQuestionFields method
            // ChangeGroup[] changeGroups2 = new ChangeGroup[2];
            // changeGroups2[0] =
            // createChangeGroup(HEALTHHISTORYQUESTIONGROUPID,
            // healthHistoryQuestionFields);
            // changeGroups2[1] = createChangeGroup(MEDICATIONQUESTIONGROUPID,
            // getMedicationQuestionFields(WSFM_MEMBERID1,"",
            // "Zantac","Active","20","mg","Before bed with water"));
            // fieldList.add(ieField(WebServiceConstants.FIELD_CHANGE_CHANGEQUESTIONGROUPS,
            // changeGroups2, WebServiceConstants.FIELD_DATATYPE_ANY));
            //
            // ChangeGroup[] changeGroups3 = new ChangeGroup[2];
            // changeGroups3[0] =
            // createChangeGroup(HEALTHHISTORYQUESTIONGROUPID,
            // healthHistoryQuestionFields);
            // changeGroups3[1] = createChangeGroup(MEDICATIONQUESTIONGROUPID,
            // getMedicationQuestionFields(WSFM_MEMBERID1,"24",
            // "Vicodin-Update1","On Hold","50","mg","After meals"));
            //
            // fieldList.add(ieField(WebServiceConstants.FIELD_CHANGE_CHANGEQUESTIONGROUPS,
            // changeGroups3, WebServiceConstants.FIELD_DATATYPE_ANY));

            net.medfusion.integrations.webservices.intengine.objects.Fields theFields = new net.medfusion.integrations.webservices.intengine.objects.Fields();
            theFields
                    .setFields((net.medfusion.integrations.webservices.intengine.objects.Field[]) fieldList
                            .toArray(new net.medfusion.integrations.webservices.intengine.objects.Field[fieldList
                                    .size()]));
            change.setFields(theFields);

            changes[0] = change;

            System.out
                    .println("Let's Create some Inbound Medication Changes for Integration Id - "
                            + SystemConfigProperties.WSFM_INTEGRATIONID);
            WebServiceReturnComposite[] wsrc = ieService
                    .createOrUpdateInboundChanges(changes,
                            SystemConfigProperties.EXTERNALSYSTEM_TESTEMR);
            WebserviceUtils.dumpResults(wsrc);
        } catch (WebServiceExceptionInvalidParameter e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @AfterClass
    public void oneTimeTearDown() {
        // one-time cleanup code
        System.out.println("@AfterClass - oneTimeTearDown");
    }

}
