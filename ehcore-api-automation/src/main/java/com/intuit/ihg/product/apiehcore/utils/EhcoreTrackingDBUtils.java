package com.intuit.ihg.product.apiehcore.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.testng.Assert;
import com.intuit.dc.framework.tracking.constants.TrackingEnumHolder;
import com.intuit.dc.framework.tracking.entity.DataJob;
import com.intuit.dc.framework.tracking.entity.Message;
import com.intuit.dc.framework.tracking.entity.ProcessingStatusType;


public class EhcoreTrackingDBUtils {

	private static final Logger logger = Logger.getLogger(EhcoreTrackingDBUtils.class);
	private static Connection dbConnPs = null;
	private static String objRefId = null;

	//List of DB Queries to check Data Channel details -Messages,Activities and object store details
	private static String GET_DATAJOB_DETAILS = "SELECT * FROM data_job WHERE data_job_guid = ? ";
	private static String GET_MESSAGE_DETAILS = "SELECT * FROM msg WHERE data_job_guid = ? ORDER BY msg_id ASC";
	private static String GET_AS_MESSAGE_DETAILS = "SELECT * FROM msg WHERE message_guid=?";
	private static String GET_ACTIVITY_STATUS = "SELECT * FROM activity WHERE msg_id = ?  AND activity_type = ? AND activity_name = ?";
	private static String GET_OBJ_REF_DETAILS = "SELECT * FROM object_ref WHERE msg_id = ? AND  node_type = ?";
	private static String GETSNAPSHOT_SUBJECTID = 
			"SELECT * FROM msg WHERE subject_id = (" +
					"SELECT subject_id FROM msg WHERE data_job_guid = ? and msg_id= ?)" +
					"and PROCESSING_STATUS_TYPE= 'COMPLETED' ORDER BY msg_id ASC";
	private static String GET_DATAJOBID = "SELECT * FROM msg WHERE msg_id = ?";
	//DB Queries to check AS adapter details - Messages,Activities and object store details 
	/*private static String GET_AS_MESSAGE_DETAILS = 
		"SELECT * FROM msg WHERE logsession_id = (" +
		"SELECT logsession_id FROM msg WHERE data_job_guid = ? )AND msg_type = ? ORDER BY msg_id ASC";*/
	//US3775 - Enhancement in datajob attributes 
	/*private static String GET_ATTRIBUTES_DETAILS = 
		"SELECT attributes FROM msg WHERE data_job_guid = ?";*/
	private static String GET_ATTRIBUTES_DETAILS = 
			"SELECT m.ATTRIBUTES.getStringVal() FROM msg m WHERE data_job_guid =?";
	private static String GET_AS_ATTRIBUTES_DETAILS = 
			"SELECT m.ATTRIBUTES.getStringVal() FROM msg m WHERE message_guid=?";
	private static String GETSNAPSHOT_DETAILS = 
			"SELECT * FROM(" +
					"SELECT * FROM msg WHERE subject_id = (" +
					"SELECT subject_id FROM msg WHERE data_job_guid = ? and msg_id= ?) AND processing_status_type = 'COMPLETED' ORDER BY msg_id ASC)" +
					"WHERE rownum < 3";



	/**
	 * This method is used to estalblish a connection with Oracle -Tracking DB
	 * @throws Exception 
	 */
	public static Connection getDBConnection() throws Exception {

		try {
			if (dbConnPs == null || dbConnPs.isClosed()) {
				// Load the JDBC driver
				String driverName = "oracle.jdbc.driver.OracleDriver";
				EhcoreAPI ehcoreApi = new EhcoreAPI();
				EhcoreAPITestData ehcoreTestData = new EhcoreAPITestData(ehcoreApi);
				String serverName = ehcoreTestData.getDBCCDHost(); //EhcoreTestConfigReader.getConfigItemValue(EhcoreTestConsts.UtilConsts.DB_CCD_HOST);
				String portNumber = EhcoreAPIConstants.DB_CCD_PORT; //EhcoreTestConfigReader.getConfigItemValue(EhcoreTestConsts.UtilConsts.DB_CCD_PORT);
				String sid = ehcoreTestData.getDBCCDSID(); //EhcoreTestConfigReader.getConfigItemValue(EhcoreTestConsts.UtilConsts.DB_CCD_SID);
				String username = EhcoreAPIConstants.DB_CCD_USER; //EhcoreTestConfigReader.getConfigItemValue(EhcoreTestConsts.UtilConsts.DB_CCD_USER);
				String password = EhcoreAPIConstants.DB_CCD_PASS; //EhcoreTestConfigReader.getConfigItemValue(EhcoreTestConsts.UtilConsts.DB_CCD_PASS);
				String connStr = "jdbc:oracle:thin:@"+serverName+":"+portNumber+":"+sid;
				Class.forName(driverName);
				dbConnPs = DriverManager.getConnection(connStr, username, password);
				logger.info("connectedto DB :"+dbConnPs.toString());
			}
		}catch (ClassNotFoundException cnfe) {
			logger.error("Failed to load the driver class", cnfe);
		} catch (SQLException se) {
			logger.error(se.getMessage(), se);
		}
		return dbConnPs;
	}

	/**
	 * This Method is used to get the Message Details in TrackingDB
	 * @param djId - DatajobId
	 * @param msg_type - msg type for import and export message details
	 * @return Message Details List<Message> 
	 * @throws Exception 
	 */

	public static List<Message> getMessageDetails(String Id,String msg_type) throws Exception {

		getDBConnection();

		List<Message>  details =  new ArrayList<Message>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String attributes = null;

		try {

			if((msg_type.equalsIgnoreCase(EhcoreAPIConstants.CCDImport))||(msg_type.equalsIgnoreCase(EhcoreAPIConstants.CCDExport))){
				stmt = dbConnPs.prepareStatement(GET_MESSAGE_DETAILS);
				stmt.setString(1, Id);
			}
			else if(msg_type.equalsIgnoreCase(EhcoreAPIConstants.AS_CCD_IMPORT)||(msg_type.equalsIgnoreCase(EhcoreAPIConstants.AS_CCD_EXPORT))){
				stmt = dbConnPs.prepareStatement(GET_AS_MESSAGE_DETAILS);
				stmt.setString(1, Id);
			}
			else {
				stmt = dbConnPs.prepareStatement(GETSNAPSHOT_DETAILS);
				stmt.setString(1, Id);
				stmt.setString(2, msg_type);
			}
			rs = stmt.executeQuery();
			while(rs.next()) {

				Message msg = new Message();

				msg.setQid(rs.getLong("MSG_ID"));
				if(msg_type.equalsIgnoreCase("importccd")){
					msg.setDataJob(new DataJob(null, rs.getString("DATA_JOB_GUID"), null, "", "", "", ""));
				}
				msg.setProcessingStatusType(new ProcessingStatusType(rs.getString("PROCESSING_STATUS_TYPE")));
				logger.debug("Msg_Type::"+rs.getString("MSG_TYPE")+",Processing_Status_Type::"+rs.getString("processing_status_type"));
				msg.setSubjectId(rs.getString("SUBJECT_ID"));
				msg.setMessageGuid(rs.getString("MESSAGE_GUID"));
				msg.setPrevMessageGuid(rs.getString("PREV_MESSAGE_GUID"));
				//To get AS activity status using log session id
				msg.setLogsessionId(rs.getString("LOGSESSION_ID"));
				Assert.assertNotNull(rs.getString("DATA_JOB_GUID"));
				Assert.assertNotNull(rs.getLong("MSG_ID"));
				Assert.assertNotNull(rs.getString("MESSAGE_GUID"));
				Assert.assertNotNull(rs.getString("MSG_TYPE"));

				//US3775 - Additional nodes in Attributes
				attributes = getAttributesDetails(Id,msg_type);
				Assert.assertNotNull(attributes);
				msg.setAttributes(attributes);
				logger.debug("ActualXML:"+attributes);
				if(attributes != null){
					if(attributes.contains("IntuitPracticeId")) {
						String intuitPracticeId =  attributes.substring(attributes.indexOf("<IntuitPracticeId>")+18,attributes.indexOf("</IntuitPracticeId>"));
						logger.debug("intuitPracticeId ::"+intuitPracticeId);
						Assert.assertNotNull(intuitPracticeId );																			
					}
				}
				/*//CCDImport : Check Additional nodes :CCDMessageLength  and IntuitPracticeId - not null
            	if(rs.getString("MSG_TYPE").equalsIgnoreCase(EhcoreTestConsts.EH_REST_API_Consts.CCDImport)||
            			rs.getString("MSG_TYPE").equalsIgnoreCase(EhcoreTestConsts.EH_REST_API_Consts.AS_CCDImport)){
            		String ccdMessageLength =  attributes.substring(attributes.indexOf("<CCDMessageLength>")+18,attributes.indexOf("</CCDMessageLength>"));
            		logger.debug("CCDMessageLength ::"+ccdMessageLength);
            		assertNotNull(ccdMessageLength);

            		if(attributes.contains("IntuitPracticeId")) {
                		String intuitPracticeId =  attributes.substring(attributes.indexOf("<IntuitPracticeId>")+18,attributes.indexOf("</IntuitPracticeId>"));
                		logger.debug("intuitPracticeId ::"+intuitPracticeId);
            			Assert.assertEquals(EhcoreTestConsts.intuitPracticeID,intuitPracticeId );																			
            		}
            	}
            	//CCDExport : Check Additional nodes :IntuitPracticeId not null
            	else if(rs.getString("MSG_TYPE").equalsIgnoreCase(EhcoreTestConsts.EH_REST_API_Consts.CCDExport)){
            		if(attributes.contains("IntuitPracticeId")) {
            			String intuitPracticeId =  attributes.substring(attributes.indexOf("<IntuitPracticeId>")+18,attributes.indexOf("</IntuitPracticeId>"));
                		logger.debug("intuitPracticeId ::"+intuitPracticeId);
            			Assert.assertNotNull(intuitPracticeId);																			
            		}
            	}*/
				details.add(msg);
			}
			stmt.close();
			dbConnPs.close();


		} catch (SQLException se) {
			logger.error(se.getMessage(), se);
			System.exit(-1);
		}

		return details;
	}
	/**
	 * This Method is used to get the Message Details in TrackingDB
	 * @param messageGuid - msg type for import and export message details
	 * @return Message Details List<Message> 
	 * @throws Exception 
	 */

	public static String getDataJobId(String messageGuid) throws Exception {
		getDBConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String djId = null;   
		try {
			stmt = dbConnPs.prepareStatement(GET_DATAJOBID);
			stmt.setString(1, messageGuid);
			rs = stmt.executeQuery();
			while(rs.next()) {
				logger.debug("DataJob_Guid::"+rs.getString("DATA_JOB_GUID")+",MessageGuid::"+rs.getString("MESSAGE_GUID"));
				//To get AS activity status using log session id
				djId = rs.getString("DATA_JOB_GUID");
				Assert.assertNotNull(rs.getString("DATA_JOB_GUID"));
				Assert.assertNotNull(rs.getString("MESSAGE_GUID"));
			}
			stmt.close();
			dbConnPs.close();
		} catch (SQLException se) {
			logger.error(se.getMessage(), se);
			System.exit(-1);
		}
		return djId;
	}


	/**
	 * This Method is used to get the Datajob Details in Tracking DB
	 * @param djId -Datajob ID
	 * @return
	 * @throws Exception 
	 */

	public static String getDatajobDetails(String djId ) throws Exception {

		getDBConnection();
		String djStatus = null;
		try {
			PreparedStatement stmt = dbConnPs.prepareStatement(GET_DATAJOB_DETAILS);
			stmt.setString(1, djId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {

				Assert.assertNotNull(rs.getString("DATA_CHANNEL"));
				Assert.assertNotNull(rs.getString("DATA_FEED"));
				Assert.assertNotNull(rs.getString("MSG_TYPE"));
				Assert.assertNotNull(rs.getString("JOB_STATUS_TYPE_CD"));
				Assert.assertNotNull(rs.getString("JOB_TYPE"));
				djStatus = rs.getString("JOB_STATUS_TYPE_CD");           	                           	
			} 
			stmt.close();
			dbConnPs.close();
		} catch (SQLException se) {
			logger.error(se.getMessage(), se);
			System.exit(-1);
		}
		return djStatus;
	}

	/**
	 * This method checks the ActivityStatus(Completed/Error) in TrackingDB 
	 * @throws Exception 
	 */
	public static boolean isActivityStatusCompleted(String qid,String activityType,String activityName ) throws Exception {

		getDBConnection();
		boolean activityStatus = false;
		try {
			PreparedStatement stmt = dbConnPs.prepareStatement(GET_ACTIVITY_STATUS);
			stmt.setString(1, qid);
			stmt.setString(2, activityType);
			stmt.setString(3, activityName);
			ResultSet rs = stmt.executeQuery();

			while(rs.next()) {

				if(rs.getString("PROCESSING_STATUS_TYPE_CD").equalsIgnoreCase(TrackingEnumHolder.ACTIVITY_STATUS.COMPLETED.toString()))
				{
					logger.debug("activity:: " + rs.getString("ACTIVITY_NAME") + " Status::"+ rs.getString("PROCESSING_STATUS_TYPE_CD"));
					activityStatus = true;
				}

				else if(rs.getString("PROCESSING_STATUS_TYPE_CD").equalsIgnoreCase(TrackingEnumHolder.ACTIVITY_STATUS.ERROR.toString()))
				{
					activityStatus = false;
				}
				Assert.assertNotNull(rs.getDate("PROCESSING_START_DT"));
				Assert.assertNotNull(rs.getDate("PROCESSING_END_DT"));
			}
			stmt.close();
			dbConnPs.close();

		} catch (SQLException se) {
			logger.error(se.getMessage(), se);
			System.exit(-1);
		}

		return activityStatus;
	}

	/**
	 * This method returns the objectRefDetails(ref_id) in TrackingDB 
	 * @throws Exception 
	 */

	public static String getObjRefDetails(String msgId,String nodeType) throws Exception {

		getDBConnection();
		try {

			PreparedStatement stmt = dbConnPs.prepareStatement(GET_OBJ_REF_DETAILS);
			stmt.setString(1, msgId);
			stmt.setString(2, nodeType);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				objRefId = rs.getString("OBJECT_REF");
				Assert.assertNotNull(rs.getString("MSG_GUID"));
			}
			stmt.close();
			dbConnPs.close();
		} catch (SQLException se) {
			logger.error(se.getMessage(), se);
			System.exit(-1);
		}
		return objRefId ;
	}

	/**
	 * This method returns true if previous snapshot is present for the same patient.
	 * To check the previous snapshot, 'suject_id' of the current snapshot is being searched in message table. 
	 * @throws Exception 
	 */

	public static boolean isSnapshotPreviousVersionPresent(String dataJobId, String qid) throws Exception {
		getDBConnection();
		boolean status = false;
		String subjectId = null;
		int rowCount = 0;

		try {
			PreparedStatement stmt = dbConnPs.prepareStatement(GETSNAPSHOT_SUBJECTID);
			stmt.setString(1, dataJobId);
			stmt.setString(2, qid);

			ResultSet rs = stmt.executeQuery();
			while (rs.next())
			{	
				if(rs.getString("PROCESSING_STATUS_TYPE").equalsIgnoreCase("completed"))
				{
					subjectId = rs.getString("subject_id");
					rowCount+=1;
				}
			}
			logger.debug("Number of Rows::" + rowCount);
			logger.debug("SubjectID::" + subjectId);
			if(rowCount > 1)
				status = true;
			else
				status = false;
			stmt.close();
		} catch (SQLException se) {
			logger.error(se.getMessage(), se);
			Assert.fail(se.getMessage());
		}
		return status ;
	}


	//US3775 - Enhancement in datajob attributes 
	public static String getAttributesDetails(String djId,String msg_type) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String str = null;
		try {
			if(msg_type.equalsIgnoreCase(EhcoreAPIConstants.AS_CCD_IMPORT)||(msg_type.equalsIgnoreCase(EhcoreAPIConstants.AS_CCD_EXPORT))){
				stmt = dbConnPs.prepareStatement(GET_AS_ATTRIBUTES_DETAILS);
			}
			else{
				stmt = dbConnPs.prepareStatement(GET_ATTRIBUTES_DETAILS);
			}

			stmt.setString(1, djId);
			rs =  stmt.executeQuery();
			while(rs.next()) {
				str = rs.getString(1);
			}
			stmt.close();
		} catch (SQLException se) {
			logger.error(se.getMessage(), se);
			System.exit(-1);
		}
		return str;
	}



}