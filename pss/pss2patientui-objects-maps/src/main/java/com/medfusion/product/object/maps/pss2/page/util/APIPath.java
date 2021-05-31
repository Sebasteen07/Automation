// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.util;

public class APIPath {

	public static final class apiPath {

		public static final String Get_List_Location = "/location/rule/26308";
		public static final String Get_List_Book = "/book/rule/26308";
		public static final String Get_List_Appointment = "/appointmenttypes/rule/50256";
		public static final String Get_List_Slots = "/availableslots/26308";
		public static final String ScheduleAPPT = "/scheduleappointment/26308";
		public static final String Appointment_Status = "/24249/appointmentstatus";
		public static final String Past_APPT = "/24249/pastappointments";
		public static final String next_Available = "/24293/nextavailableslots";
		public static final String Apt_Type = "/24249/appointmenttypes";
		public static final String Cancel_Reason = "/24249/cancellationreason";
		public static final String cancelAppointment = "/24249/cancelappointment/50151";
		public static final String cancellationReason = "/24249/cancellationreason";
		public static final String rescheduleAppt = "/24249/rescheduleappointment";
		public static final String scheduleApptNG = "/24249/scheduleappointment";
		public static final String upcommingApptNG = "/24293/upcomingappointments";
		public static final String careprovideravailabilityNG = "/24293/careprovideravailability";
		public static final String insurancecarrierNG = "/24293/insurancecarrier";
		public static final String locationsNG = "/24293/locations";
		public static final String demographicNG = "/24293/demographics";
		public static final String lockoutNG = "/24293/lockout";
		public static final String patientMatchNG = "/24293/matchpatient";
		public static final String patientLastVisistNG = "/24293/patientlastvisit/50056";
		public static final String patientStatusNG = "/24293/patientstatus";
		public static final String prerequisteappointmenttypesNG = "/24293/prerequisteappointmenttypes";
		public static final String booklistNG = "/24293/books";
		public static final String specialtyNG = "/24293/specialty";
		public static final String healthCheckNG = "/24293/healthcheck";
		public static final String pingNG = "/24293/ping";
		public static final String versionNG = "/24293/version";
		public static final String searchpatientNG = "/24293/searchpatient";
		public static final String patientrecordbybooksNG = "/24293/patientrecordbybooks/50056";
		public static final String patientrecordbyapptypesNG = "/24293/patientrecordbyapptypes/50056";
		public static final String lastseenproviderNG = "/24249/getlastseenprovider";

	}

	public static final class apiPathGE {
		public static final String HEALTH_CHECK = "/24248/healthcheck";
		public static final String PING = "/24248/ping";
		public static final String VERSION = "/24248/version";
		public static final String LOCKOUT = "/24248/lockout";
		public static final String APPOINTMENT_TYPE = "/24248/appointmenttypes";
		public static final String BOOKS = "/24248/books";
		public static final String INSURANCE_CARRIER = "/24248/insurancecarrier";
		public static final String ACTUATOR = "/actuator";
		public static final String LAST_SEEN_PROVIDER = "/24333/getlastseenprovider";
		public static final String LOCATIONS = "/24248/locations";
		public static final String APPOINTMENT_STATUS = "/24248/appointmentstatus";
		public static final String SCHEDUL_APPT = "24248/scheduleappointment";
		public static final String CANCEL_STATUS = "/24248/cancelstatus";
		public static final String CANCEL_APPOINTMENT = "/24248/cancelappointment/27565";
		public static final String CANCEL_APPT_WITH_CANCEL_REASON = "/24248/cancelappointment/27554";
		public static final String PAST_APPOINTMENTS = "/24248/pastappointments";
		public static final String UPCOMING_APPT = "/24248/upcomingappointments";
		public static final String PREVENT_SCHEDULING_DATE = "/24333/preventschedulingdate/27552/158";
		public static final String RESCHEDUL_APPT = "/24248/rescheduleappointment";
		public static final String AVAILABLE_SLOTS = "/24248/availableslots";
		public static final String ADD_PATIENT="/24333/addpatient";
		public static final String CAREPROVIDER="/24333/careprovideravailability";
		public static final String DEMOGRAPHICS="/24248/demographics";
		public static final String HEALTH_OPERATION="/actuator/health";
		public static final String MATCH_PATIENT="/24333/matchpatient";
		public static final String PATIENT_LASTVISIT="/24333/patientlastvisit/26854";
		public static final String PREREQUISTE_APPOINTMENTTYPESGET="/24333/prerequisteappointmenttypes";
		public static final String SEARCH_PATIENT="/24333/searchpatient";

	}

	public static final class apiPathPatientMod {
		public static final String APPT_DETAIL_FROM_GUID = "/24333/cf61827d-cfaa-4024-94e4-03d305f2357f/getapptdetails";
		public static final String PRACTICE_FROM_GUID = "/anonymous/092e74fd-1dc4-447e-b97a-4c9ac39e379c";
		public static final String GUID_FOR_LOGOUT_PATIENT = "/24333/patientlogout";
		public static final String HEALTH = "/actuator/health";
		public static final String LINKS_DETAIL_GUID_AND_PRACTICE = "/linkdetail/cf61827d-cfaa-4024-94e4-03d305f2357f/24333";
		public static final String LINKS_DETAIL_GUID = "/linkdetail/cf61827d-cfaa-4024-94e4-03d305f2357f";
		public static final String LINKLS_VALUE_GUID_AND_PRACTICE = "/link/2fd6eff0-2443-43d2-b741-b2ce6817443a/24333";
		public static final String LINKLS_VALUE_GUID = "/link/2fd6eff0-2443-43d2-b741-b2ce6817443a";
		public static final String LOGO = "/24248/logo";
		public static final String PRACTICE_DETAIL = "/24333/sso";
		public static final String PRACTICE_FROM_GUID_LOGINLESS = "/loginless/bc89e551-ac6d-4d69-b0b2-fb63c0a4951b";
		public static final String PRACTICE_FROM_GUID_SSO = "/sso/092e74fd-1dc4-447e-b97a-4c9ac39e379c";
		public static final String PRACTICE_INFO = "/24333/practice";
		public static final String RESELLER_LOGO = "/24333/reseller/logo";
		public static final String SESSION_CONFIGURATION = "/24248/getsessionconfiguration";
		public static final String TIME_ZONE_RESOURCES = "/timezone/26854";
		public static final String TOKEN_FOR_LOGINLESS = "/view-appointment/2fd6eff0-2443-43d2-b741-b2ce6817443a";
		public static final String UPCOMING_CONFIGURATION = "/24248/upcomingconfiguration";
		public static final String TIMEZONE_PRACTICE_RESOURCES = "/24248/medfusionpractice";
		public static final String CREATE_TOKEN_GUID = "/24248/accesstoken";
		public static final String GET_APPT_DETAIL = "/24333/getdetails";
		public static final String ANNOUNCEMENT_BY_NAME = "/24333/announcement/AG";
		public static final String ANNOUNCEMENT_BY_LANGUAGE = "/24333/announcementbylanguage";
		public static final String ANNOUNCEMENT_TYPE = "/announcementtype";
		public static final String GET_IMAGE = "/24333/book/551/image";
		public static final String GET_LANGUAGE = "/language/26854";
		public static final String DEMOGRAPHICS_PROFILES = "/24333/createtoken/26854";
		public static final String MATCH_PATIENT = "/24333/patientmatch/27643/LOGINLESS";
		public static final String FLOW_IDENTITY = "/24333/flowidentity/27658/LOGINLESS";
		public static final String GENDER_MAPPING = "/24333/gendermapping/27639";
		public static final String GET_STATUS = "/24333/states/27649";
		public static final String PATIENT_DEMOGRAPHICS = "/24333/demographics/26854";
		public static final String VALIDATE_PROVIDER_LINK = "/24333/validateproviderlink/27651";
		public static final String LOCATION_BY_NEXT_AVAILABLE = "/24333/location/nextavailable/26854";
		public static final String LOCATION_BY_RULE = "/24333/location/rule/26854";
		public static final String ANONYMOUS_MATCH_AND_CREATE_PATIENT = "/24333/anonymouspatient/27698";
		public static final String IDENTIFY_PATIENT_FOR_RESCHEDULE = "/24333/identifypatient/27699";
		public static final String SPECIALITY_BY_RULE = "/24333/specialty/rule/27638";
		public static final String CREATE_TOKEN = "/createtoken/24333/tokens";
		public static final String LOCATION_BASED_ON_ZIPCODE_AND_RADIUS = "/24333/zipcode/27566";
		public static final String APPOINTMENT = "/24333/appointment/9529/book/test";
		public static final String APPOINTMENT_FOR_ICS = "/24333/appointment/9608";
		public static final String UPCOMING_APPOINTMENTS_BY_PAGE = "/24333/appointment/9608";
		public static final String INSURANCE_CARRIER = "/24333/appointment/9608";
		public static final String CANCELLATION_REASON = "/24333/cancellationreason/27646";
		public static final String RESCHEDULE_REASON = "/24333/reschedulereason/27661";
		public static final String APPT_TYPE_NEXT_AVAIABLE = "24269/apptype/nextavailable/10314";
		public static final String BOOK_BY_NEXT_AVAILABLE = "24704/book/nextavailable/51957";
	}
}
