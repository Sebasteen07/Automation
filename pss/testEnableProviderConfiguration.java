@Test(enabled = true, dataProvider = "staffPractice", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testEnableProviderConfiguration(String staffPracitceName) throws Exception {
		Log4jUtil.log("Step 2: Login to Admin portal.");
		PSS2PracticeConfiguration pss2practiceconfig = loginToAdminPortal(driver, adminuser);

		Log4jUtil.log("Step 3: Clicking to Appointment tab.");
		AdminAppointment adminappointment = pss2practiceconfig.gotoAdminAppointmentTab();

		Log4jUtil.log("Step 4: Checking the Enable Provider Configuration is ON/OFF and set configuration accordingly.");
		adminappointment.toggleAllowPCPONOF();
		appointment.setPcptoggleState(adminappointment.toggleAllowPCPONOF());
		Log4jUtil.log("Status of PCP is " + appointment.isPcptoggleState());
		if (appointment.isPcptoggleState() == false) {
			Log4jUtil.log("Status of PCP  OFF");
			adminappointment.pcptoggleclick();
			Log4jUtil.log("Status of PCP  OFF and Clicked on ON");
		} else {
			Log4jUtil.log("Status of PCP is Already ON");
		}

		PatientFlow patientflow = pss2practiceconfig.gotoPatientFlowTab();
		adminuser.setRule(patientflow.getRule());
		Log4jUtil.log("rule= " + patientflow.getRule());
		setRulesNoSpecialitySet1(patientflow);
		


	}
