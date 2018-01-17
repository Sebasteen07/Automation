package com.medfusion.product.practice.api.utils;

public class PracticeConstants {

	public final static String SubjectMessage = "RxRenewalSubject";
	public final static String MedicationName = "Albuterol Sulfate (5 MG/ML) 0.5% Inhalation Nebulization Solution";
	public final static String Drug = "21";
	public final static String Quantity = "10";
	public final static String Frequency = "DAILY";
	public final static String BodyMessage = "RxRenewalSubjectBody";

	public final static String ProcessingCompletedText = "You have completed processing this request.";
	public final static String BillPaymentSubject = "BillPaymentSubject";
	public final static String BillPaymentBody = "BillPaymentBody";

	// Document Upload via doc management
	public final static String patientNameForDocUpload = "Sample, tester1";
	public final static String filename = "fileUploadTest";
	public final static String fileDirectory = "documents";
	public final static String textFilePath = "documents/fileUploadTest.txt";

	// Add a new Patient
	public final static String firstName = "test";
	public final static String lastName = "auto";
	public final static String value = "1";
	public final static String email = "ihgqa@none.com";
	public final static String patientID = "ihgqa";
	public final static String zipCode = "560038";
	public final static String year = "1988";
	public final static String docUploadSuccessMsg = "New Document Uploaded";
	public final static String Email = "Test@gmail.com";
	public final static String Zipcode = "94043";
	public final static String DOB = "12/31/1980";
	public final static String Email_patient_aut_sub = "EMAIL_PATIENT_ACTIVATION_SUBJECT";
	public final static String Betaportaltitle = "BETA_PORTAL_TITLE";

	// Add treatment Plan
	public final static String treatmentPlanSuccessMsg = "You have successfully created a treatment plan";
	public final static String treatmentPlanTitle = "test";
	public final static String treatmentPlanSubject = "test";
	public final static String treatmentPlanBody = "test";

	// card payment
	public final static String paymentCompletedSuccessMsg = "Payment completed";
	public final static String ccName = "Test";
	public final static String ccNum = "4111111111111111";
	public final static String cardType = "Visa";
	public final static String expMonth = "12";
	public final static String expYear = "2022";
	public final static String cvv = "111";
	public final static String zip = "12345";
	public final static String patientAccount = "MF12345";
	public final static String patientName = "Test Patient";
	public final static String comment = "Testing card Payment.";

	// card PayPal MasterCard payment
	public final static String ccNumMasterCard = "5105105105105100";
	public final static String cardTypeMaster = "MasterCard";
	public final static String swipeStringMaster = ";5105105105105100=22121011000012345678?";


	// Patient Search
	public final static String PatientFirstName = "ihgqa";
	public final static String PatientLastName = "automation";
	public final static String PatientEmail = "ihgqa.dev3@gmail.com";

	// Quick Send
	public final static String pdfname = "QuickSend";
	public final static String RecipientType = "Individual Patient(s)";
	public final static String Subject = "Quick Send";
	public final static String Template1 = "AR";
	public final static String Template2 = "Appointment Reminder";
	public final static String MessageType = "Appointment Reminder";
	public final static String DeliveryMode = "E-mail";
	public final static String QuickSendPdfFilePath = "documents/QuickSend.pdf";


	// MakePayment
	public final static String frameName = "iframebody";
	public final static String Location = "Automation-Location-1";
	public final static String CardHolderName = "DevThree";
	public final static String CardNumber = "4111111111111111";
	public final static String CardType = "Visa";
	public final static String ExpirationMonth = "December";
	public final static String ExpirationYear = "2019";
	public final static String CCVCode = "111";
	public final static String ZipCode = "94043";
	public final static String Provider = "Geisel";
	public final static String PaymentComment = "Test Comment";
	public final static String PaymentSuccessfullText = "A payment was made for ihgqa automation";
	public final static String Location2 = "Location: Automation-Location-1";



	// change email for Patient
	public final static String chngMailFName = "Change";
	public final static String chngMailLName = "Email";

	// reminder user ID
	public final static String frgtFName = "Forgot";
	public final static String frgtLName = "UserID";


	// OnlineBillpay Process
	public final static String ProcessCardHolderName = "batchopen";
	public final static String processCardNum = "4111111111111111";
	public final static String processCardType = "Visa";
	public final static String voidComment = "Testing void";
	public final static String refundComment = "Testing Refund";
	public final static String errorForVoidPayment =
			"An error occurred while attempting to void this transaction. It is possible that the transaction can no longer be voided.Please try your payment again or contact support for more assistance";

}
