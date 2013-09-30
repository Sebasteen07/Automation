package com.intuit.ihg.product.apiehcore.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import com.intuit.dc.framework.objectstore.exception.ObjectStoreException;
import com.intuit.dc.framework.objectstore.mongo.config.MongoContext;
import com.intuit.dc.framework.objectstore.mongo.domain.RepositoryBuilder;
import com.intuit.dc.framework.objectstore.mongo.service.MongoObjectStoreService;
import com.intuit.dc.framework.objectstore.service.IMongoStoreService;
import com.intuit.dc.framework.objectstore.service.IObjectStoreService;
import com.intuit.dc.framework.objectstore.utils.DCPropertyManager;
import com.intuit.ihg.product.apiehcore.utils.EhcoreXmlUnitUtil;
import com.intuit.ihg.product.apiehcore.utils.EhcoreXpathGenerationUtil;
import com.intuit.ihg.product.apiehcore.utils.EhcoreMongoDBUtils;
import com.intuit.ihg.product.apiehcore.utils.constants.EhcoreAPIConstants;
import com.mongodb.DBObject;

public class EhcoreMongoDBUtils {

	private static final Logger logger = Logger.getLogger(EhcoreMongoDBUtils.class);

    public static void checkMsgInMongoDB(String obj_ref_id,String expectedCcdFileName) throws Exception {

		logger.debug("Entering checkMsgInMongoDB ... ");
		
		String actualCcd = null;
		DCPropertyManager.loadFromEnvVariable("EH_CORE_API_CONFIG");
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData ehcoreTestData = new EhcoreAPITestData(ehcoreApi);
		String serverAddress = ehcoreTestData.getMongoServerAddress(); //EhcoreTestConfigReader.getConfigItemValue(EhcoreTestConsts.UtilConsts.MONGO_SERVERADDRESS);
		String dbPattern = EhcoreAPIConstants.MONGO_DBPATTERN; //EhcoreTestConfigReader.getConfigItemValue(EhcoreTestConsts.UtilConsts.MONGO_DBPATTERN);
		String collPattern = EhcoreAPIConstants.MONGO_COLLECTIONPATTERN; //EhcoreTestConfigReader.getConfigItemValue(EhcoreTestConsts.UtilConsts.MONGO_COLLECTIONPATTERN);
		@SuppressWarnings("rawtypes")
		RepositoryBuilder builder = new RepositoryBuilder();
		MongoContext context = new MongoContext(serverAddress,dbPattern,collPattern,builder);
    	try {
    		IObjectStoreService mongoObjectStoreService = new MongoObjectStoreService(context);
			//Update the UUID below with the ObjectRefId from the Tracking DB
			actualCcd = mongoObjectStoreService.retrieveByUUID(obj_ref_id);

    	}catch (ObjectStoreException objStoreExp) {
			logger.error(objStoreExp.getMessage(), objStoreExp);
			Assert.fail("Problem in connecting to  mongod instance "
					+ objStoreExp.getMessage());
		}
		logger.debug(" *** actualCcd :" + obj_ref_id + " ***::"+actualCcd);
			
		// Save actual to a file.
		String actualCcdFileName = EhcoreAPIConstants.ACTUAL_CCD + "actualMessage.xml";
		try {
			FileUtils.writeStringToFile(new File(actualCcdFileName),
						actualCcd, false);
		}catch (IOException e) {
			logger.error(e.getMessage(), e);
			Assert.fail("Failed to write actualCcd to file. "
						+ e.getMessage());
		}
		//Compare Actual objStore Message with Expected CCD Message using XMLUnit .
		EhcoreXmlUnitUtil.assertEqualsXML(expectedCcdFileName,actualCcdFileName);
 
		logger.debug("Exiting checkMsgInMongoDB ... ");   
		 
    }
   
    /**
     * CDM Message Verification:Pass the valid MessageGUID to check the 
     * canonical message list in mongoDB
     */
    public static SortedMap<String,String> checkCDMRetrieve(String msg_guid,String nodePath,String nodeName){
    	
    	logger.debug("Entering checkCDMRetrieve ... "); 
        DCPropertyManager.loadFromEnvVariable("EH_CORE_API_CONFIG");
    	IMongoStoreService objectService;
        String dbName = EhcoreAPIConstants.MONGO_DBNAME; //EhcoreTestConfigReader.getConfigItemValue(EhcoreTestConsts.UtilConsts.MONGO_DBNAME);
        String collectionName = EhcoreAPIConstants.MONGO_COLLECTIONNAME; //EhcoreTestConfigReader.getConfigItemValue(EhcoreTestConsts.UtilConsts.MONGO_COLLECTIONNAME);
        String attrib = EhcoreAPIConstants.MONGO_ATTRIBUTE; //EhcoreTestConfigReader.getConfigItemValue(EhcoreTestConsts.UtilConsts.MONGO_ATTRIBUTE);
        String node_Name = EhcoreAPIConstants.MONGO_NODENAME; //EhcoreTestConfigReader.getConfigItemValue(EhcoreTestConsts.UtilConsts.MONGO_NODENAME);
        
		List<DBObject> cdmList;
		Map<String, String> unsortedMap = new HashMap<String, String>();
		SortedMap<String,String> sortedMap = new TreeMap<String,String>();
     

		try {
			objectService = new MongoObjectStoreService();
			cdmList = objectService.retrieveNodeByQuery(dbName, collectionName, attrib, msg_guid);
			Iterator<DBObject> walker = cdmList.iterator();
			logger.debug("cdmList Size ::"+cdmList.size());
			while(walker.hasNext()) {
	            DBObject node = walker.next();
	            String nodeValue = null;
	            String actualCDM =  (String)node.get(node_Name);
	            nodeValue = EhcoreXpathGenerationUtil.getXPathValue(actualCDM,nodePath,nodeName);
	            logger.debug("nodeValue:::"+nodeValue);
	            if(nodeValue.equalsIgnoreCase("CCDImport"))
	                unsortedMap.put(nodeValue, actualCDM);
			}    
			//Sorted Map based on the value of 'nodeName'
			sortedMap.putAll(unsortedMap);
            logger.debug("UnsortedMap Size ::"+unsortedMap.size());
            logger.debug("sortedMap Size ::"+sortedMap.size());
 
		} catch (ObjectStoreException objStoreExp) {
			logger.error(objStoreExp.getMessage(), objStoreExp);
			Assert.fail(objStoreExp.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Assert.fail(e.getMessage());
		}
		logger.debug("Exiting checkCDMRetrieve ... "); 
		return sortedMap;
		
    }
    
    
}
