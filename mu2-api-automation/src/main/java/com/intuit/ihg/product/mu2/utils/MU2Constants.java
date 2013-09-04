package com.intuit.ihg.product.mu2.utils;

public interface MU2Constants {
	
		public static final String GET_REQUEST = "GET";
		public static final String PORT  = "8080";
		public static final String PROTOCOL = "http";
		public static final String DBPORT = "1521";
		
		//Pull API events details
		public static final String RESOURCE_TYPE = "CCD";
		public static final String VIEW_ACTION = "View";
		public static final String DOWNLOAD_ACTION = "Download";
		public static final String TRANSMIT_ACTION = "Transmit";
		public static final String TRANSMIT_RESOURCE_TYPE = "SecureMessage";
		public static final String EVENT = "Event";
		public static final String RESOURCE_TYPE_NODE = "ResourceType";
		public static final String ACTION_NODE = "Action";
		public static final String PULL_API_EXPECTED_RESPONSE = "HTTP/1.1 200 OK";
	
		
		// Push API
		public static final String PUSH_API_ACTIVITY_NODE = "ns4:UserActivities";
		public static final String PUSH_API_PATIENTID_NODE = "ns3:CodeValue";
		public static final String PUSH_API_PATIENT_FIRSTNAME_NODE = "ns3:First";
		public static final String PUSH_API_PATIENT_LASTNAME_NODE = "ns3:Last";
		public static final String PUSH_API_ACTION_NODE = "ns4:Action";
		public static final String PUSH_API_CONTENTTYPE_NODE = "ns4:ContentType";
		
		public static final String PUSH_API_EXPECTED_RESPONSE = "HTTP/1.1 202 Accepted";
		
		public static final String PUSH_API_MSG_TYPE = "internal/exporteventreport";
		public static final String PUSH_API_PROCESSING_STATUS_TYPE = "ERROR";
		
		//View Activity Log
		public static final String ACCOUNT_ACTIVITY_VIEWED = "Health information viewed.";
		public static final String ACCOUNT_ACTIVITY_DOWNLOADED = "Health information downloaded.";
		public static final String ACCOUNT_ACTIVITY_TRANSMITTED = "Health information transmitted.";
		
		
		
		
	}


