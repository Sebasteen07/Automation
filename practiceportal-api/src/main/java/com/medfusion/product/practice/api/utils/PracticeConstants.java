package com.medfusion.product.practice.api.utils;

public class PracticeConstants {
		public final static String MESSAGE_SUBJECT = "RxRenewalSubject";
		public final static String MEDICATION_NAME = "Albuterol Sulfate (5 MG/ML) 0.5% Inhalation Nebulization Solution";
		public final static String DRUG = "21";
		public final static String QUANTITY = "10";
		public final static String FREQUENCY = "DAILY";
		public final static String MESSAGE_BODY = "RxRenewalSubjectBody";

		public final static String PROCESSING_COMPLETED_TEXT = "You have completed processing this request.";
		public final static String BILL_PAYMENT_SUBJECT = "BillPaymentSubject";
		public final static String BILL_PAYMENT_BODY = "BillPaymentBody";

		// Document Upload via doc management
		public final static String PATIENT_NAME_FOR_DOC_UPLOAD = "Sample, tester1"; //never used
		public final static String FILENAME = "fileUploadTest"; //never used
		public final static String FILE_DIRECTORY = "documents";
		public final static String TEXT_FILE_PATH = "documents/fileUploadTest.txt"; //never used

		// Add treatment Plan
		public final static String TREATMENT_PLAN_SUCCESS_MSG = "You have successfully created a treatment plan";
		public final static String TREATMENT_PLAN_TITLE = "test";
		public final static String TREATMENT_PLAN_SUBJECT = "test";
		public final static String TREATMENT_PLAN_BODY = "test";

		public final static String CARD_TYPE_MASTERCARD = "MasterCard";
		public final static String CARD_TYPE_VISA = "Visa";

		public final static String CARD_NUMBER = "4111111111111111";
		public final static String CVV = "111";

		// card payment
		public final static String PAYMENT_COMPLETED_SUCCESS_MSG = "Payment completed";
		public final static String CARD_NAME = "Test";
		public final static String EXP_MONTH = "12";
		public final static String EXP_YEAR = "2022";
		public final static String ZIP = "12345";
		public final static String PATIENT_ACCOUNT = "MF12345";
		public final static String PATIENT_NAME = "Test Patient";
		public final static String COMMENT = "Testing card Payment.";

		// card PayPal MasterCard payment
		public final static String CARD_NUM_MASTERCARD = "5105105105105100";
		public final static String SWIPE_STRING_MASTERCARD = ";5105105105105100=22121011000012345678?";

		// Patient Search //TODO remove, the usage is really strange
		public final static String PATIENT_FIRST_NAME = "ihgqa";
		public final static String PATIENT_LAST_NAME = "automation";
		public final static String PATIENT_EMAIL = "ihgqa.dev3@gmail.com";

		// Quick Send //TODO check usage of this strings
		public final static String PDF_NAME = "QuickSend";
		public final static String RECIPIENT_TYPE = "Individual Patient(s)";
		public final static String SUBJECT = "Quick Send";
		public final static String TEMPLATE1 = "AR";
		public final static String TEMPLATE2 = "Appointment Reminder";
		public final static String MESSAGE_TYPE = "Appointment Reminder";
		public final static String DELIVERY_MODE = "E-mail";
		public final static String QUICK_SEND_PDF_FILE_PATH = "documents/QuickSend.pdf";


		// MakePayment
		public final static String FRAME_NAME = "iframebody";
		public final static String LOCATION = "Automation-Location-1";
		public final static String LOCATION2 = "Location: Automation-Location-1";

		public final static String CARD_HOLDER_NAME = "DevThree";
		public final static String EXPIRATION_MONTH = "December";
		//public final static String EXPIRATION_YEAR = String.valueOf(2020+3);
		public final static String ZIP_CODE = "94043";
		public final static String PROVIDER = "Geisel";
		public final static String PAYMENT_COMMENT = "Test Comment";
		public final static String PAYMENT_SUCCESSFULL_TEXT = "A payment was made for ihgqa automation";


		// OnlineBillpay Process
		public final static String PROCESS_CARD_HOLDER_NAME = "batchopen";
		public final static String VOID_COMMENT = "Testing void";
		public final static String REFUND_COMMENT = "Testing Refund";
		public final static String ERROR_FOR_VOID_PAYMENT =
				"An error occurred while attempting to void this transaction. It is possible that the transaction can no longer be voided.Please try your payment again or contact support for more assistance";
}
