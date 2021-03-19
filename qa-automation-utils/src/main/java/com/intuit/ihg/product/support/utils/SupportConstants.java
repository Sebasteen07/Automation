// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.support.utils;

import com.medfusion.common.utils.IHGUtil;

public class SupportConstants {
	public static final String TEST_FILE = "c:\temp\testfile.pdf";
	public static final String TEST_DATA = "src/test/resources/testfiles/";
	public static final String XML_REQUEST_PATH = TEST_DATA + IHGUtil.getEnvironmentType() + "/ccd/";
	public static final String MESSAGE_PATH = TEST_DATA + IHGUtil.getEnvironmentType() + "/Messages/";
	public static final String TAG_INTUITPATIENT_ID = "CodeValue";
	public static final String REQUESTOR_INTUITPATIENTID_OLDVALUE = "??49316??";
	public static final String ATTACHMENT_FILE1 = TEST_DATA + IHGUtil.getEnvironmentType().toString() + "/Attachments/MRI Attachment.pdf";
	public static final String ATTACHMENT_FILE2 = TEST_DATA + IHGUtil.getEnvironmentType().toString() + "/Attachments/ImmunizationRecord.pdf";
	public static final String ATTACHMENT_FILE3 = TEST_DATA + IHGUtil.getEnvironmentType().toString() + "/Attachments/Clinical Summary.pdf";

	// LabResult Subjects for patients
	public static final String SUBJECTFORPATIENT1 = "[LABS] Results for LIPID PANEL (80061)";
	public static final String SUBJECTFORPATIENT2 = "[LABS] Results for Basic Metabolic Panel";
	public static final String SUBJECTFORPATIENT3 = "[LABS] Results for TSH";

	// LabResult Messages for patients
	public static final String MESSAGEFORPATIENT1 = "LabResultForPatient1.xml";
	public static final String MESSAGEFORPATIENT2 = "LabResultForPatient2.xml";
	public static final String MESSAGEFORPATIENT3 = "LabResultForPatient3.xml";

	// Attachment mail subjects to patients
	public static final String MESSAGESUBJECT1 = "MRI knee";
	public static final String MESSAGESUBJECT2 = "Immunzation Record*";
	public static final String MESSAGESUBJECT3 = "*Clinical Summary";

	// Attachments to send to patients
	public static final String MESSAGE1 = "Message1.xml";
	public static final String MESSAGE2 = "Message2.xml";
	public static final String MESSAGE3 = "Message3.xml";

}
