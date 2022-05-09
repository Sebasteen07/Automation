package com.medfusion.product.precheck;


import com.medfusion.common.utils.IHGUtil;

public class PrecheckPatient {

	private String email = "";
	private String firstName = "";
	private String lastName = "";
	private String patientId = "";
	private String middleName = "";

	// Getters for getting the email and password value and reusing in other
	// tests
	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getPatientId() {
		return patientId;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void initPatientData() {
		IHGUtil.PrintMethodName();

		int randomize = IHGUtil.createRandomNumber();

		// Setting data according to test purpose

		email = "pr" + randomize + "@mailinator.com";
		firstName = "pr" + randomize + "First";
		lastName = "pr" + randomize + "Last";
		middleName = "pr" + randomize + "Middle";
		patientId = "pr" + randomize;


	}

}
