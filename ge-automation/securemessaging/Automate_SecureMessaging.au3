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
$logPath = StringReplace($currentDir, "securemessaging", "commonsteps") & "\ProcessLog_SecureMessaging_" & $time & ".txt"
Call("setLogPath",$logpath)
$hFileOpen = Call("openLogFile")

$arrConfig = Call("setConfig")
$arrQuery = Call("openQueryFile")
$arrEvent = Call("openEventFile")
$arrDoctype = Call("openDoctypeFile")

;Update servicesettings
	FileWriteLine($hFileOpen, @CRLF & " STEP 1 -- UPDATE SERVICESETTINGS FOR SECURE MESSAGING FLOW" & @CRLF)
	Call("connectDatabase",$arrConfig[4],$arrConfig[6],$arrConfig[7],$arrConfig[8])

			Local $aData,$iRows,$iColumns
			ConsoleWrite("Executing Query: " & $arrQuery[36] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[36] & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[36] & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
				If(($aData[1][0] = 0) And ($aData[2][0] = 1) And ($aData[3][0] = 1)) Then
					ConsoleWrite("Service settings already enabled" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Service Settings already enabled" & @CRLF)

				Else
					ConsoleWrite("Executing Query: " & $arrQuery[37] & @CRLF & $arrQuery[38] & @CRLF & $arrQuery[39] & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[37] & @CRLF & $arrQuery[38] & @CRLF & $arrQuery[39] & @CRLF)
					If (_SQL_Execute(-1,$arrQuery[37]) Or _SQL_Execute(-1,$arrQuery[38]) Or _SQL_Execute(-1,$arrQuery[39])) = $SQL_ERROR then
						;Msgbox(0 + 16 +262144,"Error",_SQL_GetErrMsg())
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
	ConsoleWrite("Exiting after checking pre-conditions for Secure Messaging Flow...." & @CRLF)
	FileWriteLine($hFileOpen, _NowCalc() & "  -- Exiting after checking pre-conditions for Secure Messaging Flow...." & @CRLF)
	Exit

Else
	;Open CPS -Patient Chart
	FileWriteLine($hFileOpen, @CRLF & " STEP 2 -- LOGIN TO CPS" & @CRLF)
	ConsoleWrite("Start the CPS client" &@CRLF )
	FileWriteLine($hFileOpen, _NowCalc()  &" -- Start the CPS client" &@CRLF)
	Call("startCPS",$arrConfig[1], $arrConfig[2], $arrConfig[3])

	WinActivate("Centricity Practice Solution")
	If(WinActive("Centricity Practice Solution")) Then
		FileWriteLine($hFileOpen, @CRLF & " STEP 3 -- OPEN PATIENT CHART" & @CRLF)
		ConsoleWrite("Open Patient Chart" &@CRLF )
		Call("openPatientChart",$arrConfig[15],$arrConfig[16])
		Sleep(8000)
		WinWaitActive("Chart - NOT FOR PATIENT USE")

		FileWriteLine($hFileOpen, @CRLF & " STEP 4 -- CREATE A NEW EMAIL MESSAGE FOR THE PATIENT" & @CRLF)
		If(WinActive("Chart - NOT FOR PATIENT USE")) Then
			$counter = 0
			Do
				ConsoleWrite("Creating Secure Message #" &$counter+1 & @CRLF)
				Call("createNewDocument",$arrConfig[17],$arrConfig[15],$arrConfig[16])
				Call("createEmailMessage",$arrConfig[15],$arrConfig[16],$arrConfig[27],$arrConfig[28])

				If($arrConfig[9]=="Data Generation") Then
					$counter +=1
					Sleep(60000)
					If($counter = $arrConfig[35]) Then
						ConsoleWrite("Exiting after data generation for Secure Messaging Flow...." & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Exiting after data generation for Secure Messaging Flow...." & @CRLF)
						Exit
					EndIf

				Else
					$counter = $arrConfig[35]
				EndIf
			Until $counter = $arrConfig[35]

			ConsoleWrite("Secure Message created successfully...." & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Secure Message created successfully...." & @CRLF)

			FileWriteLine($hFileOpen, @CRLF & " STEP 5 -- GET PATIENT DETAILS FROM DATABASE" & @CRLF)
			Call("connectDatabase",$arrConfig[4],$arrConfig[5],$arrConfig[7],$arrConfig[8])

			Local $aData,$iRows,$iColumns	;Variables to store the array data in to and the row count and the column count
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

				ConsoleWrite("Wait for message being sent to Medfusion" & @CRLF)
				FileWriteLine($hFileOpen, @CRLF & _NowCalc() & "  -- Wait for message being sent to Medfusion" & @CRLF)
				Sleep(240000)

			FileWriteLine($hFileOpen, @CRLF & " STEP 6 -- VERIFY MESSAGE DETAILS IN DOCUMENT TABLE" & @CRLF)
			ConsoleWrite("Executing Query: " & $arrQuery[28] & $PID & $arrQuery[29] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[28] & $PID & $arrQuery[29] & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[28] & $PID & $arrQuery[29] & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
				$msgDocumentId = Int($aData[1][0]) - 1

				ConsoleWrite("Document Id for Secure Message is " & $msgDocumentId & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Document Id for Secure Message is " & $msgDocumentId & @CRLF)

				;Summary
				ConsoleWrite("Verifying document summary in Document table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying document summary in Document table" & @CRLF)

				ConsoleWrite("Expected Result: " & "E-mail Message" &" and Actual Result: " & $aData[1][1] & " -- ")
				If (StringCompare("E-mail Message", $aData[1][1]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "E-mail Message" &" and Actual Result: " & $aData[1][1] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "E-mail Message" &" and Actual Result: " & $aData[1][1] & " -- FAILED" & @CRLF)
					Exit
				EndIf

				$CreationDate = $aData[1][3]
				ConsoleWrite("Secure Message Creation Date is " & $CreationDate & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Secure Message Creation Date is " & $CreationDate & @CRLF)

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

			FileWriteLine($hFileOpen, @CRLF & " STEP 7 -- VERIFY MESSAGE DETAILS IN CUSMEDFUSIONCOMMOUTGOING TABLE" & @CRLF)
			$newQuery = StringReplace($arrQuery[22],"where CommOutgoingId =","order by CommOutgoingId desc")
			ConsoleWrite("Executing Query: " & $newQuery & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $newQuery & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$newQuery & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
			;PateintProfileId
				ConsoleWrite("Verifying PatientProfileId in cusMedfusionCommOutgoing table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying PatientProfileId in cusMedfusionCommOutgoing table" & @CRLF)

				ConsoleWrite("Expected Result: " & $PatientProfileId &" and Actual Result: " & $aData[1][0] & " -- ")
				If (StringCompare($PatientProfileId, $aData[1][0]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $PatientProfileId &" and Actual Result: " & $aData[1][0] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $PatientProfileId &" and Actual Result: " & $aData[1][0] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;CommServiceType
				ConsoleWrite("Verifying CommServiceType in cusMedfusionCommOutgoing table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying CommServiceType in cusMedfusionCommOutgoing table" & @CRLF)

				ConsoleWrite("Expected Result: " & "Patient Messaging" &" and Actual Result: " & $aData[1][1] & " -- ")
				If (StringCompare("Patient Messaging", $aData[1][1]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Patient Messaging" &" and Actual Result: " & $aData[1][1] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Patient Messaging" &" and Actual Result: " & $aData[1][1] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;Message Subject
				ConsoleWrite("Verifying Message Subject in cusMedfusionCommOutgoing table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Message Subject in cusMedfusionCommOutgoing table" & @CRLF)

				ConsoleWrite("Expected Result: " & $arrConfig[27] &" and Actual Result: " & $aData[1][2] & " -- ")
				If (StringCompare($arrConfig[27], $aData[1][2]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrConfig[27] &" and Actual Result: " & $aData[1][2] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrConfig[27] &" and Actual Result: " & $aData[1][2] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;Created by User
				ConsoleWrite("Verifying user creating the communication in cusMedfusionCommOutgoing table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying user creating the communication in cusMedfusionCommOutgoing table" & @CRLF)

				ConsoleWrite("Expected Result: " & $arrConfig[2] &" and Actual Result: " & $aData[1][3] & " -- ")
				If (StringCompare($arrConfig[2], $aData[1][3]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrConfig[2] &" and Actual Result: " & $aData[1][3] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrConfig[2] &" and Actual Result: " & $aData[1][3] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;CommSentStatus
				ConsoleWrite("Verifying CommSentStatus in cusMedfusionCommOutgoing table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying CommSentStatus in cusMedfusionCommOutgoing table" & @CRLF)

				ConsoleWrite("Expected Result: " & "1" &" and Actual Result: " & $aData[1][4] & " -- ")
				If (StringCompare("1", $aData[1][4]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "1" &" and Actual Result: " & $aData[1][4] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "1" &" and Actual Result: " & $aData[1][4] & " -- FAILED" & @CRLF)
					Exit
				EndIf

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

			FileWriteLine($hFileOpen, @CRLF & " STEP 8 -- VERIFY EVENT IN CUSMEDFUSIONMUEVENTS TABLE" & @CRLF)
			ConsoleWrite("Executing Query: " & $arrQuery[9] & $PatientProfileId & $arrQuery[10] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[9] & $PatientProfileId & $arrQuery[10] & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[9] & $PatientProfileId & $arrQuery[10] & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then

			;EventId
				ConsoleWrite("Verifying EventId in cusMedfusionMUEvents table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying EventId in cusMedfusionMUEvents table" & @CRLF)
				ConsoleWrite("Expected Result: " & "PI_" & $commOutgoingId & "_" & $msgDocumentId &" and Actual Result: " & $aData[1][0] & " -- ")
				If (StringCompare("PI_" & $commOutgoingId & "_" & $msgDocumentId, $aData[1][0]) = 0)  Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "PI_" & $commOutgoingId & "_" & $msgDocumentId &" and Actual Result: " & $aData[1][0] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "PI_" & $commOutgoingId & "_" & $msgDocumentId &" and Actual Result: " & $aData[1][0] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;Evnet Type
				ConsoleWrite("Verifying Event Type in cusMedfusionMUEvents table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Event Type in cusMedfusionMUEvents table" & @CRLF)

				ConsoleWrite("Expected Result: " & "Provider Initiated" &" and Actual Result: " & $aData[1][1] & " -- ")
				If (StringCompare("Provider Initiated", $aData[1][1]) = 0)  Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Provider Initiated" &" and Actual Result: " & $aData[1][1] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Provider Initiated" &" and Actual Result: " & $aData[1][1] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;Provider
				ConsoleWrite("Verifying Provider in cusMedfusionMUEvents table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Provider in cusMedfusionMUEvents table" & @CRLF)

				ConsoleWrite("Expected Result: " & $arrConfig[2] &" and Actual Result: " & $aData[1][2] & " -- ")
				If (StringCompare($arrConfig[2], $aData[1][2]) = 0)  Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrConfig[2] &" and Actual Result: " & $aData[1][2] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrConfig[2] &" and Actual Result: " & $aData[1][2] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;SDID
				ConsoleWrite("Verifying SDID in cusMedfusionMUEvents table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying SDID in cusMedfusionMUEvents table" & @CRLF)
				$DocumentIdInEvents = Int($aData[1][3]) - 1

				ConsoleWrite("Expected Result: " & $msgDocumentId &" and Actual Result: " & $DocumentIdInEvents & " -- ")
				If (StringCompare($msgDocumentId, $DocumentIdInEvents) = 0)  Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $msgDocumentId &" and Actual Result: " & $DocumentIdInEvents & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $msgDocumentId &" and Actual Result: " & $DocumentIdInEvents & " -- FAILED" & @CRLF)
					Exit
				EndIf

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

			ConsoleWrite("Wait for event being processed" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait for event being processed" & @CRLF)
			Sleep(60000)

			FileWriteLine($hFileOpen, @CRLF & " STEP 9 -- VERIFY EVENT 522 IN MUACTIVITYLOG TABLE" & @CRLF)
			ConsoleWrite("Executing Query: " & $arrQuery[11] & $PID & $arrQuery[12] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[11] & $PID & $arrQuery[12] & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[11] & $PID & $arrQuery[12] & ";",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
			;MUActivityLogId
				ConsoleWrite("Verifying MUActivityLogId in MUActivityLog table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying MUActivityLogId in MUActivityLog table" & @CRLF)
				ConsoleWrite("Expected Result: " & $arrEvent[9] &" and Actual Result: " & $aData[1][0] & " -- ")
				If (StringCompare($arrEvent[9], $aData[1][0]) = 0)  Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrEvent[9] &" and Actual Result: " & $aData[1][0] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrEvent[9] &" and Actual Result: " & $aData[1][0] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;PVID
				ConsoleWrite("Verifying PVID in MUActivityLog table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying PVID in MUActivityLog table" & @CRLF)
				$temp = Int($aData[1][2]) - 1
				ConsoleWrite("Expected Result: " & $pvdrPVID &" and Actual Result: " & $temp & " -- ")
				If (StringCompare($pvdrPVID, $temp) = 0)  Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $pvdrPVID &" and Actual Result: " & $temp & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $pvdrPVID &" and Actual Result: " & $temp & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;SDID
				ConsoleWrite("Verifying SDID in MUActivityLog table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying SDID in MUActivityLog table" & @CRLF)
				$temp = Int($aData[1][1]) - 1
				ConsoleWrite("Expected Result: " & $msgDocumentId &" and Actual Result: " & $temp & " -- ")
				If (StringCompare($msgDocumentId, $temp) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $msgDocumentId &" and Actual Result: " & $temp & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $msgDocumentId &" and Actual Result: " & $temp & " -- FAILED" & @CRLF)
					Exit
				EndIf

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

			FileWriteLine($hFileOpen, @CRLF & " STEP 10 -- VERIFY MESSAGE IN PATIENT PORTAL AND REPLY THE MESSAGE" & @CRLF)
			ConsoleWrite("Message Subject " & $arrConfig[27] & @CRLF)
			$temp = StringSplit($arrConfig[27]," ")
			$messageSubject = Null
			For $i = 1 to $temp[0]
				$messageSubject = $messageSubject & $temp[$i]
			Next
			ConsoleWrite("New Message Subject " & $messageSubject & @CRLF)

			ConsoleWrite("Message Body " & $arrConfig[28] & @CRLF)
			$temp = StringSplit($arrConfig[28]," ")
			$messageBody = Null
			For $i = 1 to $temp[0]
				$messageBody = $messageBody & $temp[$i]
			Next
			ConsoleWrite("New Message Body " & $messageBody & @CRLF)
			$secureMessagejar = StringReplace($currentDir, "securemessaging", "jarfiles")  & "\secureMessage.jar"
			$PID = Run(@ComSpec & ' /c java -jar ' & $secureMessagejar & ' ' & $messageSubject & ' ' & $messageBody & ' ' & $CreationDate &'' ,"","",$STDOUT_CHILD)
			ConsoleWrite("$PID :" & $PID & @CRLF)
			ProcessWaitClose($PID)
			$output =StdoutRead($PID)

				If(StringInStr($output,"PASSED")) Then
					ConsoleWrite("Message details verified in Patient Portal" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Message Details verified in Patient Portal -- PASSED" & @CRLF)
				Else
					ConsoleWrite("Message details do not match in Patient Portal" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Message details do not match in Patient Portal -- FAILED" & @CRLF)
					Exit
				EndIf

			Sleep(240000)
			FileWriteLine($hFileOpen, @CRLF & " STEP 11 -- VERIFY REPLY IN CUSMEDFUSIONCOMMINCOMING TABLE" & @CRLF)
			ConsoleWrite("Executing Query: " & $arrQuery[34] & $PatientProfileId & $arrQuery[35] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[34] & $PatientProfileId & $arrQuery[35]& @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[34] & $PatientProfileId & $arrQuery[35] & ";",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
			;CommServiceType
				ConsoleWrite("Verifying CommServiceType in cusMedfusionCommIncoming table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying CommServiceType in cusMedfusionCommIncoming table" & @CRLF)
				ConsoleWrite("Expected Result: " & "Patient Messaging" &" and Actual Result: " & $aData[1][0] & " -- ")
				If (StringCompare("Patient Messaging", $aData[1][0]) = 0)  Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Patient Messaging" &" and Actual Result: " & $aData[1][0] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Patient Messaging" &" and Actual Result: " & $aData[1][0] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;EMRDocumentType
				ConsoleWrite("Verifying EMRDocumentType in cusMedfusionCommIncoming table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying EMRDocumentType in cusMedfusionCommIncoming table" & @CRLF)
				ConsoleWrite("Expected Result: " & "Pt Comm" &" and Actual Result: " & $aData[1][1] & " -- ")
				If (StringCompare("Pt Comm", $aData[1][1]) = 0)  Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Pt Comm" &" and Actual Result: " & $aData[1][1] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Pt Comm" &" and Actual Result: " & $aData[1][1] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;MessageThread
				ConsoleWrite("Verifying MessageThread in cusMedfusionCommIncoming table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying MessageThread in cusMedfusionCommIncoming table" & @CRLF)
				ConsoleWrite("Expected Result: " & $docId &" and Actual Result: " & $aData[1][2] & " -- ")
				If (StringCompare($docId, $aData[1][2]) = 0)  Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $docId &" and Actual Result: " & $aData[1][2] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $docId &" and Actual Result: " & $aData[1][2] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;Subject
				ConsoleWrite("Verifying Subject in cusMedfusionCommIncoming table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Subject in cusMedfusionCommIncoming table" & @CRLF)
				ConsoleWrite("Expected Result: " & "Re: " & $arrConfig[27] &" and Actual Result: " & $aData[1][3] & " -- ")
				If (StringCompare("Re: " & $arrConfig[27], $aData[1][3]) = 0)  Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Re: " & $arrConfig[27] &" and Actual Result: " & $aData[1][3] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Re: " & $arrConfig[27] &" and Actual Result: " & $aData[1][3] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf
			_SQL_Close()

			Sleep(240000)
			FileWriteLine($hFileOpen, @CRLF & " STEP 11 -- VERIFY READ RECEIPT IN PATIENT CHART" & @CRLF)
			$result = Call("verifyReadReceipt")
			If($result == "PASSED") Then
				ConsoleWrite("Read Receipt verified in Patient Chart" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Read Receipt verified in Patient Chart -- PASSED" & @CRLF)
				ConsoleWrite("Secure Messaging Flow -- SUCCESSFULLY COMPLETED...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- SECURE MESSAGING FLOW -- SUCCESSFULLY COMPLETED...." & @CRLF)
			Else
				ConsoleWrite("Read Receipt did not arrive in Patient Chart" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Read Receipt did not arrive in Patient Chart -- FAILED" & @CRLF)
				Exit
			EndIf

		Else
			ConsoleWrite("ERROR Attaching Patient Chart...." & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Attaching Patient Chart...." & @CRLF)
		EndIf
	Else
		ConsoleWrite("ERROR Attaching GE CPS App...." & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Attaching GE CPS App...." & @CRLF)
	EndIf
EndIf
