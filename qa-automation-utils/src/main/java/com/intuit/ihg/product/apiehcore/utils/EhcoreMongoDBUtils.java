//Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.apiehcore.utils;

import static org.testng.Assert.fail;

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


import com.intuit.dc.framework.objectstore.exception.ObjectStoreException;
import com.intuit.dc.framework.objectstore.mongo.config.MongoContext;
import com.intuit.dc.framework.objectstore.mongo.domain.RepositoryBuilder;
import com.intuit.dc.framework.objectstore.mongo.service.MongoObjectStoreService;
import com.intuit.dc.framework.objectstore.service.IMongoStoreService;
import com.intuit.dc.framework.objectstore.service.IObjectStoreService;
import com.intuit.dc.framework.objectstore.utils.DCPropertyManager;
import com.intuit.ihg.product.apiehcore.utils.constants.EhcoreAPIConstants;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class EhcoreMongoDBUtils {

	private static final Logger logger = Logger.getLogger(EhcoreMongoDBUtils.class);

	public static void checkMsgInMongoDB(String obj_ref_id, String expectedCcdFileName) throws Exception {

		logger.debug("Entering checkMsgInMongoDB ... ");

		String actualCcd = null;
		DCPropertyManager.loadFromEnvVariable("EH_CORE_API_CONFIG");
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData ehcoreTestData = new EhcoreAPITestData(ehcoreApi);
		String serverAddress = ehcoreTestData.getMongoServerAddress();
		String dbPattern = EhcoreAPIConstants.MONGO_DBPATTERN;
		String collPattern = EhcoreAPIConstants.MONGO_COLLECTIONPATTERN;
		@SuppressWarnings("rawtypes")
		RepositoryBuilder builder = new RepositoryBuilder();
		MongoContext context = new MongoContext(serverAddress, dbPattern, collPattern, builder);
		try {
			IObjectStoreService mongoObjectStoreService = new MongoObjectStoreService(context);
			// Update the UUID below with the ObjectRefId from the Tracking DB
			actualCcd = mongoObjectStoreService.retrieveByUUID(obj_ref_id);

		} catch (ObjectStoreException objStoreExp) {
			logger.error(objStoreExp.getMessage(), objStoreExp);
			fail("Problem in connecting to  mongod instance " + objStoreExp.getMessage());
		}
		logger.debug(" *** actualCcd :" + obj_ref_id + " ***::" + actualCcd);

		// Save actual to a file.
		String actualCcdFileName = EhcoreAPIConstants.ACTUAL_CCD + "actualMessage.xml";
		try {
			FileUtils.writeStringToFile(new File(actualCcdFileName), actualCcd, false);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			fail("Failed to write actualCcd to file. " + e.getMessage());
		}
		// Compare Actual objStore Message with Expected CCD Message using XMLUnit .
		EhcoreXmlUnitUtil.assertEqualsXML(expectedCcdFileName, actualCcdFileName);

		logger.debug("Exiting checkMsgInMongoDB ... ");

	}

	/**
	 * CDM Message Verification:Pass the valid MessageGUID to check the canonical message list in mongoDB
	 */
	public static SortedMap<String, String> checkCDMRetrieve(String msg_guid, String nodePath, String nodeName) throws Exception {

		logger.debug("Entering checkCDMRetrieve ... ");
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
		System.out.println("mongo path from excel***************************************" + testData.getmongoproperty());
		DCPropertyManager.loadFromFilePath(testData.getmongoproperty());
		IMongoStoreService objectService;
		String dbName = EhcoreAPIConstants.MONGO_DBNAME;
		String collectionName = EhcoreAPIConstants.MONGO_COLLECTIONNAME;
		String attrib = EhcoreAPIConstants.MONGO_ATTRIBUTE;
		String node_Name = EhcoreAPIConstants.MONGO_NODENAME;

		List<DBObject> cdmList;
		Map<String, String> unsortedMap = new HashMap<String, String>();
		SortedMap<String, String> sortedMap = new TreeMap<String, String>();

		try {
			objectService = new MongoObjectStoreService();

			BasicDBObject query = new BasicDBObject();
			query.put(attrib, msg_guid);

			cdmList = objectService.retrieveNodeByQuery(dbName, collectionName, query, null);
			Iterator<DBObject> walker = cdmList.iterator();
			logger.debug("cdmList Size ::" + cdmList.size());
			while (walker.hasNext()) {
				DBObject node = walker.next();
				String nodeValue = null;
				String actualCDM = (String) node.get(node_Name);
				nodeValue = EhcoreXpathGenerationUtil.getXPathValue(actualCDM, nodePath, nodeName);
				logger.debug("nodeValue:::" + nodeValue);
				if (nodeValue.equalsIgnoreCase("CCDImport"))
					unsortedMap.put(nodeValue, actualCDM);
			}
			// Sorted Map based on the value of 'nodeName'
			sortedMap.putAll(unsortedMap);
			logger.debug("UnsortedMap Size ::" + unsortedMap.size());
			logger.debug("sortedMap Size ::" + sortedMap.size());

		} catch (ObjectStoreException objStoreExp) {
			logger.error(objStoreExp.getMessage(), objStoreExp);
			fail(objStoreExp.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			fail(e.getMessage());
		}
		logger.debug("Exiting checkCDMRetrieve ... ");
		return sortedMap;

	}


}
