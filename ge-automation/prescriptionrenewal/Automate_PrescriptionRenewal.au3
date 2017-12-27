#include <IE.au3>
#include <MsgBoxConstants.au3>
#include <Array.au3>
#include <File.au3>
#include <Date.au3>
#include <AutoItConstants.au3>
#include <FileConstants.au3>
#include <WinAPIFiles.au3>
#include <String.au3>
#include "..\commonsteps\CommonSteps.au3"

$time = Call("getTimestamp")
$currentDir = @ScriptDir
$commonStepsDir = StringReplace($currentDir , "prescriptionrenewal", "commonsteps")
Call("deleteOldLogFiles",$commonStepsDir)
$logPath = $commonStepsDir & "\ProcessLog_PrescriptionRenewal_" & $time & ".txt"
Call("setLogPath",$logpath)
$hFileOpen = Call("openLogFile")

$arrConfig = Call("setConfig")
$arrQuery = Call("openQueryFile")
$arrEvent = Call("openEventFile")
$arrDoctype = Call("openDoctypeFile")

;Update servicesettings
	FileWriteLine($hFileOpen, @CRLF & " STEP 1 -- UPDATE SERVICESETTINGS FOR PRESCRIPTION RENEWAL FLOW" & @CRLF)
	Call("connectDatabase",$arrConfig[4],$arrConfig[6],$arrConfig[7],$arrConfig[8])
	Local $aData,$iRows,$iColumns
	ConsoleWrite("Executing Query: " & $arrQuery[50] & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[50] & @CRLF)
		$iRval = _SQL_GetTable2D(-1,$arrQuery[50] & ";",$aData,$iRows,$iColumns)
		If $iRval = $SQL_OK then
				If(($aData[1][0] = 1) And ($aData[2][0] = 1) And ($aData[3][0] = 1) And ($aData[4][0] = 1) And ($aData[5][0] = 1)) Then
					ConsoleWrite("Service settings already enabled" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Service Settings already enabled" & @CRLF)
					ConsoleWrite("Updating export frequencies for Pharmaciies and Medications")
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Updating export frequencies for Pharmaciies and Medications" & @CRLF)
					ConsoleWrite("Executing Query: " & $arrQuery[52] & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[52] & @CRLF )
					If (_SQL_Execute(-1,$arrQuery[51]) Or _SQL_Execute(-1,$arrQuery[52])) = $SQL_ERROR then
						ConsoleWrite("ERROR Updating frequencies for Pharmaciies and Medications...." & @CRLF)
						ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Updating frequencies for Pharmacies and Medications...." & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
						Exit

					Else
						ConsoleWrite("Frequencies for Pharmacies and Medications updated...." & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Frequencies for Pharmacies and Medications updated...." & @CRLF)
					EndIf

				Else
					ConsoleWrite("Executing Query: " & $arrQuery[51] & @CRLF & $arrQuery[52] & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[51] & @CRLF & $arrQuery[52] & @CRLF )
					If (_SQL_Execute(-1,$arrQuery[51]) Or _SQL_Execute(-1,$arrQuery[52])) = $SQL_ERROR then
						ConsoleWrite("ERROR Updating service settings...." & @CRLF)
						ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Updating service settings...." & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
						Exit

					Else
						ConsoleWrite("Service settings updated...." & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Service Settings updated...." & @CRLF)
					EndIf
				EndIf
			Else
				ConsoleWrite("ERROR Checking Service Settings...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Checking Service Settings...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

		_SQL_Close()

If($arrConfig[9] == "Preconditions Check") Then
	ConsoleWrite("Exiting after checking pre-conditions for Prescription Renewal Flow...." & @CRLF)
	FileWriteLine($hFileOpen, _NowCalc() & "  -- Exiting after checking pre-conditions for Prescription Renewal Flow...." & @CRLF)
	Exit

Else
			;Create RX REquest
	FileWriteLine($hFileOpen, @CRLF & " STEP 2 -- CREATE PRESCRIPTION RENEWAL REQUEST FROM PATIENT PORTAL" & @CRLF)
	ConsoleWrite("Create Prescription Renewal request from Patient Portal" &@CRLF )

 	$createRx = StringReplace($currentDir, "prescriptionrenewal", "jarfiles")  & "\prescriptionrenewal.jar"

	$counter = 0
	Do
		If($arrConfig[9]=="Data Generation") Then
			ConsoleWrite("Creating Prescription Renewal Request #" & $counter+1 & " of " & $arrConfig[49] & " requests"& @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Creating Prescription Renewal Request #" & $counter+1 & " of " & $arrConfig[49] & " requests" & @CRLF)
		Else
			ConsoleWrite("Creating Prescription Renewal Request #" &$counter+1 & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Creating Prescription Renewal Request #" &$counter+1 & @CRLF)
		EndIf

		$ProcessID = Run(@ComSpec & ' /c java -jar ' & $createRx &' ' & $time & $counter &'',"","",$STDOUT_CHILD)
		ConsoleWrite("$ProcessID :" & $ProcessID & @CRLF)
		ProcessWaitClose($ProcessID)
		$output =StdoutRead($ProcessID)

			If(StringInStr($output,"PASSED")) Then
				ConsoleWrite("Rx #" & $counter+1 & " created successfully" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Rx #" & $counter+1 & " created successfully" & @CRLF)
			Else
				ConsoleWrite("Error in Prescription Renewal Request creation from Patient Portal" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Error in Prescription Renewal Request creation from Patient Portal -- FAILED" & @CRLF)
				Exit
			EndIf

		If($arrConfig[9]=="Data Generation") Then
			$counter +=1
			If($counter = $arrConfig[49]) Then
				ConsoleWrite("Exiting after data generation for Prescription Renewal Flow...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Exiting after data generation for Prescription Renewal Flow...." & @CRLF)
				Exit
			EndIf
			ConsoleWrite("Wait for 1 min before next Rx request generation" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait for 1 min before next Rx request generation" & @CRLF)
			Sleep(60000)

		Else
			$counter = $arrConfig[49]
		EndIf
	Until $counter = $arrConfig[49]

	FileWriteLine($hFileOpen, @CRLF & " STEP 3 -- GET PATIENT DETAILS FROM DATABASE" & @CRLF)
		Call("connectDatabase",$arrConfig[4],$arrConfig[5],$arrConfig[7],$arrConfig[8])
		Local $aData,$iRows,$iColumns

		;Get PatientProfileId,PID
			$newQuery = StringReplace($arrQuery[6],"PatientProfileId =","First = '" & $arrConfig[15] & "' and Last = '" & $arrConfig[16] & "'")
			ConsoleWrite("Executing Query: " & $newQuery & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $newQuery & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$newQuery & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
				$PatientProfileId=$aData[1][6]
				$PID = Int($aData[1][1]) - 1

				ConsoleWrite("Patient Profile Id is " & $PatientProfileId & @CRLF	& "PID is " & $PID & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Patient Profile Id is " & $PatientProfileId & @CRLF & _NowCalc() &"  -- PID is " & $PID & @CRLF)

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

			;Get MedfusionMemberId
			$newQuery = StringReplace($arrQuery[5],"MemberId =","PatientProfileId = " & $PatientProfileId)
			ConsoleWrite("Executing Query: " & $newQuery & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $newQuery & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$newQuery & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK Then
				$MedfusionMemberId = $aData[1][2]

				ConsoleWrite("Medfusion Member Id is " & $MedfusionMemberId & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Medfusion Member Id is " & $MedfusionMemberId & @CRLF)

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

			;Get loginname, PVID of provider
			$newQuery = StringReplace($arrQuery[23],"LASTNAME = LNAME and FIRSTNAME = FNAME","LOGINNAME = '" & $arrConfig[2] & "'")
			ConsoleWrite("Executing Query: " & $newQuery & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $newQuery & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$newQuery & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
				$pvdrLogin = $aData[1][0]
				$pvdrPVID = Int($aData[1][1]) - 1

				ConsoleWrite("Provider PVID is " & $pvdrPVID & @CRLF	& "Provider login is " & $pvdrLogin & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Provider PVID is " & $pvdrPVID & @CRLF & _NowCalc() &"  -- Provider login is " & $pvdrLogin & @CRLF)

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

	FileWriteLine($hFileOpen, @CRLF & " STEP 4 -- LOGIN TO CPS" & @CRLF)
		;Open CPS -Patient Chart
		ConsoleWrite("Start the CPS client" &@CRLF )
		FileWriteLine($hFileOpen, _NowCalc()  &" -- Start the CPS client" &@CRLF)
		Call("startCPS",$arrConfig[1], $arrConfig[2], $arrConfig[3])

		WinActivate("Centricity Practice Solution")
		If(WinActive("Centricity Practice Solution")) Then
			FileWriteLine($hFileOpen, @CRLF & " STEP 5 -- OPEN PATIENT CHART" & @CRLF)
			ConsoleWrite("Open Patient Chart" &@CRLF )
			Call("openPatientChart",$arrConfig[15],$arrConfig[16])
			Sleep(8000)
			WinWaitActive("Chart - NOT FOR PATIENT USE")

;-------------------------Change this wait time
			ConsoleWrite("Wait of 8 mins for Rx to arrive from Medfusion" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait of 8 mins for Rx to arrive from Medfusion"& @CRLF)
			Sleep(480000)

		FileWriteLine($hFileOpen, @CRLF & " STEP 6 -- VERIFY PRESCRIPTION RENEWAL DETAILS IN INCOMING TABLE" & @CRLF)
			$newQuery = StringReplace($arrQuery[34]," from cusMedfusionCommIncoming",", MedfusionPrescriptionRenewalRequestId from cusMedfusionCommIncoming")
			ConsoleWrite("Executing Query: " & $newQuery & $PatientProfileId & $arrQuery[35] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $newQuery & $PatientProfileId & $arrQuery[35]& @CRLF)
			$iRval = _SQL_GetTable2D(-1,$newQuery & $PatientProfileId & $arrQuery[35] & ";",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
			;CommServiceType
				ConsoleWrite("Verifying CommServiceType in cusMedfusionCommIncoming table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying CommServiceType in cusMedfusionCommIncoming table" & @CRLF)
				Call("assertData", "Prescription Renewal", $aData[1][0])

			;EMRDocumentType
				ConsoleWrite("Verifying EMRDocumentType in cusMedfusionCommIncoming table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying EMRDocumentType in cusMedfusionCommIncoming table" & @CRLF)
				Call("assertData", "RX Refill", $aData[1][1])

			;Subject
				ConsoleWrite("Verifying Subject in cusMedfusionCommIncoming table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Subject in cusMedfusionCommIncoming table" & @CRLF)
				Call("assertData", "Prescription Renewal Request", $aData[1][3])

			;Received Date
			$receiveingDate = $aData[1][4]

			;CommIncomingID
			$commIncomingId  = $aData[1][5]

			;RxrequestId
			$rxRequestId = $aData[1][7]
			ConsoleWrite("MedfusionPrescriptionRenewalRequestId is " & $rxRequestId &  @CRLF)

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

		FileWriteLine($hFileOpen, @CRLF & " STEP 7 -- VERIFY PRESCRIPTION RENEWAL DETAILS IN CUSMEDFUSIONPRESCRIPTIONRENEWALREQUESTS TABLE" & @CRLF)
			ConsoleWrite("Executing Query: " & $arrQuery[48] & $rxRequestId & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[48] & $rxRequestId & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[48] & $rxRequestId & ";",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
			;MedfusionMemberId
				ConsoleWrite("Verifying MedfusionMemberId in cusMedfusionPrescriptionRenewalRequests table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying MedfusionMemberId in cusMedfusionPrescriptionRenewalRequests table" & @CRLF)
				Call("assertData", $MedfusionMemberId, $aData[1][0])

			;Status
				ConsoleWrite("Verifying PrescriptionRenewwalStatus in cusMedfusionPrescriptionRenewalRequests table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying PrescriptionRenewalStatus in cusMedfusionPrescriptionRenewalRequests table" & @CRLF)
				Call("assertData", "1", $aData[1][1])

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

		FileWriteLine($hFileOpen, @CRLF & " STEP 8 -- VERIFY PRESCRIPTION RENEWAL DETAILS IN CUSMEDFUSIONPRESCRIPTIONRENEWALREQUESTLINEITEMS TABLE" & @CRLF)
			ConsoleWrite("Executing Query: " & $arrQuery[49] & $rxRequestId & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[49] & $rxRequestId & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[49] & $rxRequestId & ";",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
			;MedicationName
				ConsoleWrite("Verifying Medication Name in cusMedfusionPrescriptionRenewalRequestLineItems table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Medication Name in cusMedfusionPrescriptionRenewalRequestLineItems table" & @CRLF)
;~ 				$medNameInConfig = StringReplace($arrConfig[33] & $time & "0"," ","")
;~ 				$medNameInDB = StringReplace($aData[1][0]," ","")
;~ 				Call("assertData", $medNameInConfig, $medNameInDB)
				Call("assertData", $arrConfig[33] & $time & "0", $aData[1][0])

			;Dosage
				ConsoleWrite("Verifying Medication Dosage in cusMedfusionPrescriptionRenewalRequestLineItems table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Medication Dosage in cusMedfusionPrescriptionRenewalRequestLineItems table" & @CRLF)
;~ 				$medDosageInConfig = StringReplace($arrConfig[34]," ","")
;~ 				$medDosageInDB = StringReplace($aData[1][1]," ","")
;~ 				Call("assertData", $medDosageInConfig, $medDosageInDB)
				Call("assertData", $arrConfig[34], $aData[1][1])

			;Prescription Number
				ConsoleWrite("Verifying Prescription Number in cusMedfusionPrescriptionRenewalRequestLineItems table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Prescriiption Number in cusMedfusionPrescriptionRenewalRequestLineItems table" & @CRLF)
;~ 				$medNoInConfig = StringReplace($arrConfig[37]," ","")
;~ 				$medNoInDB = StringReplace($aData[1][2]," ","")
;~ 				Call("assertData", $medNoInConfig, $medNoInDB)
				Call("assertData", $arrConfig[37], $aData[1][2])

			;REfills
				ConsoleWrite("Verifying Medication Refills in cusMedfusionPrescriptionRenewalRequestLineItems table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Medication Refills in cusMedfusionPrescriptionRenewalRequestLineItems table" & @CRLF)
				Call("assertData", $arrConfig[36], $aData[1][3])

			;Notes
				ConsoleWrite("Verifying Medication Notes in cusMedfusionPrescriptionRenewalRequestLineItems table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Medication Notes in cusMedfusionPrescriptionRenewalRequestLineItems table" & @CRLF)
;~ 				$medNotesInConfig = StringReplace($arrConfig[38]," ","")
;~ 				$medNotesInDB = StringReplace($aData[1][4]," ","")
;~ 				Call("assertData", $medNotesInConfig, $medNotesInDB)
				Call("assertData",$arrConfig[38],$aData[1][4])

			;Quantity
				ConsoleWrite("Verifying Medication Quantity in cusMedfusionPrescriptionRenewalRequestLineItems table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Medication Quantity in cusMedfusionPrescriptionRenewalRequestLineItems table" & @CRLF)
				Call("assertData", $arrConfig[35], $aData[1][5])

			;Pharmacy Name
				ConsoleWrite("Verifying Pharmacy Name in cusMedfusionPrescriptionRenewalRequestLineItems table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Pharmacy Name in cusMedfusionPrescriptionRenewalRequestLineItems table" & @CRLF)
;~ 				$pharmacyNameInConfig = StringReplace($arrConfig[39]," ","")
;~ 				$pharmacyNameInDB = StringReplace($aData[1][6]," ","")
;~ 				Call("assertData", $pharmacyNameInConfig, $pharmacyNameInDB)
				Call("assertData",  $arrConfig[39],$aData[1][6])

			;Pharmacy Phone
				ConsoleWrite("Verifying Pharmacy Phone in cusMedfusionPrescriptionRenewalRequestLineItems table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Pharmacy Phone in cusMedfusionPrescriptionRenewalRequestLineItems table" & @CRLF)
				$pharmacyPhoneInDB = StringReplace($aData[1][7]," ","")
				$pharmacyPhoneInDB = StringReplace($pharmacyPhoneInDB,"(","")
				$pharmacyPhoneInDB = StringReplace($pharmacyPhoneInDB,")","")
				$pharmacyPhoneInDB = StringReplace($pharmacyPhoneInDB,"-","")
				Call("assertData", $arrConfig[40], $pharmacyPhoneInDB)

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

		FileWriteLine($hFileOpen, @CRLF & " STEP 9 -- VERIFY EVENT FOR PRESCRIPTION RENEWAL REQUEST IN MUACTIVITYLOG TABLE" & @CRLF)
			ConsoleWrite("Executing Query: " & $arrQuery[11] & $PID &  $arrQuery[12] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[11] & $PID &  $arrQuery[12] & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[11] & $PID &  $arrQuery[12] & ";",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
			;MUActivityLogTypeId
				ConsoleWrite("Verifying MUActiviytLogTypeId in MUActivityLog table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying MUActivityLogTypeId in MUActivityLog table" & @CRLF)
				Call("assertData", $arrEvent[8], $aData[1][0])

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

		FileWriteLine($hFileOpen, @CRLF & " STEP 10 -- VERIFY PRESCRIPTION RENEWAL REQUEST IN PATIENT CHART" & @CRLF)
		$result = Call("verifyDocumentInPatientChart","Rx Refill")
			If($result == "PASSED") Then
				ConsoleWrite("Rx Request arrived in Patient Chart" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Rx Request arrived in Patient Chart -- PASSED" & @CRLF)

			Else
				ConsoleWrite("Rx Request did not arrive in Patient Chart" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Rx Request did not arrive in Patient Chart -- FAILED" & @CRLF)
				Exit
			EndIf

		FileWriteLine($hFileOpen, @CRLF & " STEP 11 -- REPLY PRESCRIPTION RENEWAL REQUEST FROM PATIENT CHART" & @CRLF)
			ConsoleWrite("Reply Prescription Renewal Request from Patient Chart" & @CRLF)
			Call("replyRxAppend",$arrConfig[15],$arrConfig[16],$arrConfig[33] & $time & "0",$arrConfig[34],$arrConfig[35],$arrConfig[36],$arrConfig[37],$arrConfig[38],$arrConfig[39])

		FileWriteLine($hFileOpen, @CRLF & " STEP 12 -- VERIFY REPLY DETAILS IN DOCUMENT TABLE" & @CRLF)
			ConsoleWrite("Executing Query: " & $arrQuery[28] & $PID & $arrQuery[29] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[28] & $PID & $arrQuery[29] & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[28] & $PID & $arrQuery[29] & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
				$msgDocumentId = Int($aData[1][0]) - 1

				ConsoleWrite("Document Id for Reply of Prescription Renewal Request is " & $msgDocumentId & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Document Id for Reply of Prescription Renewal Request is " & $msgDocumentId & @CRLF)

				;Summary
				ConsoleWrite("Verifying document summary in Document table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying document summary in Document table" & @CRLF)
				Call("assertData", "Medfusion Rx Refill Reply", $aData[1][1])

				$CreationDate = $aData[1][3]
				ConsoleWrite("Reply of Rx Request Creation Date is " & $CreationDate & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Reply of Rx Request Creation Date is " & $CreationDate & @CRLF)

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf


		ConsoleWrite("Wait for 4 mins for the reply to be sent to Medfusion" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait for 4 mins for the reply to  be sent to Medfusion" & @CRLF)
		Sleep(240000)

		FileWriteLine($hFileOpen, @CRLF & " STEP 13 -- VERIFY REPLY MESSAGE DETAILS IN CUSMEDFUSIONCOMMOUTGOING TABLE" & @CRLF)
			$newQuery = StringReplace($arrQuery[22],"where CommOutgoingId =","where PatientProfileId = " & $PatientProfileId & " order by CommOutgoingId desc")
			ConsoleWrite("Executing Query: " & $newQuery & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $newQuery & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$newQuery & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
			;PateintProfileId
				ConsoleWrite("Verifying PatientProfileId in cusMedfusionCommOutgoing table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying PatientProfileId in cusMedfusionCommOutgoing table" & @CRLF)
				Call("assertData", $PatientProfileId, $aData[1][0])

			;CommServiceType
				ConsoleWrite("Verifying CommServiceType in cusMedfusionCommOutgoing table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying CommServiceType in cusMedfusionCommOutgoing table" & @CRLF)
				Call("assertData", "Prescription Renewal", $aData[1][1])

			;Message Subject
				ConsoleWrite("Verifying Message Subject in cusMedfusionCommOutgoing table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Message Subject in cusMedfusionCommOutgoing table" & @CRLF)
				Call("assertData", "Your Rx Refill Request", $aData[1][2])

			;Created by User
				ConsoleWrite("Verifying user creating the communication in cusMedfusionCommOutgoing table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying user creating the communication in cusMedfusionCommOutgoing table" & @CRLF)
				Call("assertData", $arrConfig[2], $aData[1][3])

			;CommSentStatus
				ConsoleWrite("Verifying CommSentStatus in cusMedfusionCommOutgoing table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying CommSentStatus in cusMedfusionCommOutgoing table" & @CRLF)
				Call("assertData", "1", $aData[1][4])

			;MedfusionCommId
				ConsoleWrite("Verifying MedfusionCommId in cusMedfusionCommOutgoing table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying MedfusionCommId in cusMedfusionCommOutgoing table" & @CRLF)

				If ($aData[1][5] = Null) Then
					ConsoleWrite("MedfusionCommId returned from Medfusion is NULL" & $aData[1][5] & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- MedfusionCommId returned froom Medfusion is NULL" & $aData[1][5] & " -- FAILED" & @CRLF)
					Exit
				Else
					ConsoleWrite("Message reached Medfusion. MedfusionCommId returned is " & $aData[1][5] & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Message reached Medfusion. MedfusionCommId returned is " & $aData[1][5] & " -- PASSED" & @CRLF)
				EndIf

			;RespoonseToIncomingId
				ConsoleWrite("Verifying Message Thread (ResponseToIncomingId) in cusMedfusionCommOutgoing table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Message Thread (ResponseToIncomingId) in cusMedfusionCommOutgoing table" & @CRLF)
				Call("assertData", $commIncomingId, $aData[1][9])

			;CommSentDate
				$messageSentDate = $aData[1][7]

			;CommOutgoingId
				$commOutgoingId = $aData[1][8]

			;DocumentId
				$docId = $aData[1][6]

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

			FileWriteLine($hFileOpen, @CRLF & " STEP 14 -- VERIFY EVENT IN CUSMEDFUSIONMUEVENTS TABLE" & @CRLF)
			ConsoleWrite("Executing Query: " & $arrQuery[9] & $PatientProfileId & $arrQuery[10] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[9] & $PatientProfileId & $arrQuery[10] & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[9] & $PatientProfileId & $arrQuery[10] & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then

			;EventId
				ConsoleWrite("Verifying EventId in cusMedfusionMUEvents table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying EventId in cusMedfusionMUEvents table" & @CRLF)
				Call("assertData", "PI_" & $commOutgoingId & "_" & $msgDocumentId, $aData[1][0])

			;Evnet Type
				ConsoleWrite("Verifying Event Type in cusMedfusionMUEvents table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Event Type in cusMedfusionMUEvents table" & @CRLF)
				Call("assertData", "Provider Initiated", $aData[1][1])

			;Provider
				ConsoleWrite("Verifying Provider in cusMedfusionMUEvents table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Provider in cusMedfusionMUEvents table" & @CRLF)
				Call("assertData", $arrConfig[2], $aData[1][2])

			;SDID
				ConsoleWrite("Verifying SDID in cusMedfusionMUEvents table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying SDID in cusMedfusionMUEvents table" & @CRLF)
				$DocumentIdInEvents = Int($aData[1][3]) - 1
				Call("assertData", $msgDocumentId, $DocumentIdInEvents)

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

			ConsoleWrite("Wait of 4 min for event being processed" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait of 4 min for event being processed" & @CRLF)
			Sleep(240000)

			FileWriteLine($hFileOpen, @CRLF & " STEP 15 -- VERIFY EVENT 522 IN MUACTIVITYLOG TABLE" & @CRLF)
			ConsoleWrite("Executing Query: " & $arrQuery[11] & $PID & $arrQuery[12] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[11] & $PID & $arrQuery[12] & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[11] & $PID & $arrQuery[12] & ";",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
			;MUActivityLogId
				ConsoleWrite("Verifying MUActivityLogId in MUActivityLog table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying MUActivityLogId in MUActivityLog table" & @CRLF)
				Call("assertData", $arrEvent[9], $aData[1][0])

			;PVID
				ConsoleWrite("Verifying PVID in MUActivityLog table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying PVID in MUActivityLog table" & @CRLF)
				$temp = Int($aData[1][2]) - 1
				Call("assertData", $pvdrPVID, $temp)

			;SDID
				ConsoleWrite("Verifying SDID in MUActivityLog table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying SDID in MUActivityLog table" & @CRLF)
				$temp = Int($aData[1][1]) - 1
				Call("assertData", $msgDocumentId, $temp)

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf
;-------------------------------------------------------------------------------------
			FileWriteLine($hFileOpen, @CRLF & " STEP 16 -- VERIFY MESSAGE IN PATIENT PORTAL AND REPLY THE MESSAGE" & @CRLF)
			$medNameInConfig = StringReplace($arrConfig[33] & $time & "0"," ","")
			$pharmacyNameInConfig = StringReplace($arrConfig[39]," ","")
			$messageSubject = StringReplace("Your Rx Refill Request"," ","")

			$rxReplyJar = StringReplace($currentDir, "prescriptionrenewal", "jarfiles")  & "\verifyRxReply.jar"
			$PID = Run(@ComSpec & ' /c java -jar ' & $rxReplyJar & ' ' & $messageSubject & ' ' & $CreationDate & ' ' & $medNameInConfig & ' ' & $pharmacyNameInConfig &'' ,"","",$STDOUT_CHILD)
			ConsoleWrite("$PID :" & $PID & @CRLF)
			ProcessWaitClose($PID)
			$output =StdoutRead($PID)

				If(StringInStr($output,"PASSED")) Then
					ConsoleWrite("Rx Reply details verified in Patient Portal" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Rx Reply Details verified in Patient Portal -- PASSED" & @CRLF)
					ConsoleWrite("Prescription Renewal Flow -- SUCCESSFULLY COMPLETED...." & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- PRESCRIPTION RENEWAL FLOW -- SUCCESSFULLY COMPLETED...." & @CRLF)
				Else
					ConsoleWrite("Rx Reply details do not match in Patient Portal" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Rx Reply details do not match in Patient Portal -- FAILED" & @CRLF)
					Exit
				EndIf
;------------------------------------------------------------------------

		Else
			ConsoleWrite("ERROR Attaching GE CPS App...." & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Attaching GE CPS App...." & @CRLF)
		EndIf

EndIf