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
$commonStepsDir = StringReplace($currentDir , "askastaff", "commonsteps")
Call("deleteOldLogFiles",$commonStepsDir)
$logPath = $commonStepsDir & "\ProcessLog_AskAStaff_" & $time & ".txt"
Call("setLogPath",$logpath)
$hFileOpen = Call("openLogFile")

$arrConfig = Call("setConfig")
$arrQuery = Call("openQueryFile")
$arrEvent = Call("openEventFile")
$arrDoctype = Call("openDoctypeFile")

;Update servicesettings
	FileWriteLine($hFileOpen, @CRLF & " STEP 1 -- UPDATE SERVICESETTINGS FOR ASK A STAFF FLOW" & @CRLF)
	Call("connectDatabase",$arrConfig[4],$arrConfig[6],$arrConfig[7],$arrConfig[8])

			Local $aData,$iRows,$iColumns
			ConsoleWrite("Executing Query: " & $arrQuery[46] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[46] & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[46] & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
				If($aData[1][0] = 1) Then
					ConsoleWrite("Service settings already enabled" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Service Settings already enabled" & @CRLF)

				Else
					ConsoleWrite("Executing Query: " & $arrQuery[47] & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[47] & @CRLF )
					If (_SQL_Execute(-1,$arrQuery[47])) = $SQL_ERROR then
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
		ConsoleWrite("Exiting after checking pre-conditions for Ask A Staff Flow...." & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Exiting after checking pre-conditions for Ask A Staff Flow...." & @CRLF)
		Exit

Else
		;Create AAS Question
	FileWriteLine($hFileOpen, @CRLF & " STEP 2 -- CREATE ASK A STAFF QUESTION FROM PATIENT PORTAL" & @CRLF)
	ConsoleWrite("Create ask a staff question from Patient Portal" &@CRLF )

 	$createAASJar = StringReplace($currentDir, "askastaff", "jarfiles")  & "\askastaff.jar"
		$temp = StringSplit($arrConfig[30]," ")
		$subjectAAS = Null
			For $i = 1 to $temp[0]
				$subjectAAS = $subjectAAS & $temp[$i]
			Next

		$temp = StringSplit($arrConfig[31]," ")
		$bodyAAS = Null
			For $i = 1 to $temp[0]
				$bodyAAS = $bodyAAS & $temp[$i]
			Next

	$counter = 0
	Do
		If($arrConfig[9]=="Data Generation") Then
			ConsoleWrite("Creating AAS #" & $counter+1 & " of " & $arrConfig[40] & " ask a staff questions"& @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Creating AAS #" & $counter+1 & " of " & $arrConfig[40] & " ask a staff questions" & @CRLF)
		Else
			ConsoleWrite("Creating AAS #" &$counter+1 & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Creating AAS #" &$counter+1 & @CRLF)
		EndIf

		$ProcessID = Run(@ComSpec & ' /c java -jar ' & $createAASJar &' ' & $subjectAAS & $time & $counter &' ' & $bodyAAS & $time & $counter &'',"","",$STDOUT_CHILD)
		ConsoleWrite("$ProcessID :" & $ProcessID & @CRLF)
		ProcessWaitClose($ProcessID)
		$output =StdoutRead($ProcessID)

			If(StringInStr($output,"PASSED")) Then
				ConsoleWrite("AAS #" & $counter+1 & " created successfully" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- AAS #" & $counter+1 & " created successfully" & @CRLF)
			Else
				ConsoleWrite("Error in AAS creation from Patient Portal" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Error in AAS creation from Patient Portal -- FAILED" & @CRLF)
				Exit
			EndIf

		If($arrConfig[9]=="Data Generation") Then
			$counter +=1
			Sleep(60000)
			If($counter = $arrConfig[40]) Then
				ConsoleWrite("Exiting after data generation for Ask A Staff Flow...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Exiting after data generation for Ask A Staff Flow...." & @CRLF)
				Exit
			EndIf

		Else
			$counter = $arrConfig[40]
		EndIf
	Until $counter = $arrConfig[40]

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

	ConsoleWrite("Wait for AAS to arrive from Medfusion" & @CRLF)
	FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait for AAS to arrive from Medfusion"& @CRLF)
	Sleep(240000)

		FileWriteLine($hFileOpen, @CRLF & " STEP 4 -- VERIFY AAS DETAILS IN DATABASE" & @CRLF)

			ConsoleWrite("Executing Query: " & $arrQuery[34] & $PatientProfileId & $arrQuery[35] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[34] & $PatientProfileId & $arrQuery[35]& @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[34] & $PatientProfileId & $arrQuery[35] & ";",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
			;CommServiceType
				ConsoleWrite("Verifying CommServiceType in cusMedfusionCommIncoming table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying CommServiceType in cusMedfusionCommIncoming table" & @CRLF)
				ConsoleWrite("Expected Result: " & "Ask A Doctor" &" and Actual Result: " & $aData[1][0] & " -- ")
				If (StringCompare("Ask A Doctor", $aData[1][0]) = 0)  Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Ask A Doctor" &" and Actual Result: " & $aData[1][0] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Ask A Doctor" &" and Actual Result: " & $aData[1][0] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;EMRDocumentType
				ConsoleWrite("Verifying EMRDocumentType in cusMedfusionCommIncoming table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying EMRDocumentType in cusMedfusionCommIncoming table" & @CRLF)
				ConsoleWrite("Expected Result: " & "AAS" &" and Actual Result: " & $aData[1][1] & " -- ")
				If (StringCompare("AAS", $aData[1][1]) = 0)  Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "AAS" &" and Actual Result: " & $aData[1][1] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "AAS" &" and Actual Result: " & $aData[1][1] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;Subject
				ConsoleWrite("Verifying Subject in cusMedfusionCommIncoming table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Subject in cusMedfusionCommIncoming table" & @CRLF)
				ConsoleWrite("Expected Result: " & $subjectAAS & $time & "0" &" and Actual Result: " & $aData[1][3] & " -- ")
				If (StringCompare($subjectAAS & $time & "0", $aData[1][3]) = 0)  Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $subjectAAS & $time & "0" &" and Actual Result: " & $aData[1][3] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $subjectAAS & $time & "0" &" and Actual Result: " & $aData[1][3] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;Received Date
			$receiveingDate = $aData[1][4]

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

	FileWriteLine($hFileOpen, @CRLF & " STEP 5 -- LOGIN TO CPS" & @CRLF)
	;Open CPS -Patient Chart
	ConsoleWrite("Start the CPS client" &@CRLF )
	FileWriteLine($hFileOpen, _NowCalc()  &" -- Start the CPS client" &@CRLF)
	Call("startCPS",$arrConfig[1], $arrConfig[2], $arrConfig[3])

	WinActivate("Centricity Practice Solution")
	If(WinActive("Centricity Practice Solution")) Then
		FileWriteLine($hFileOpen, @CRLF & " STEP 6 -- OPEN PATIENT CHART" & @CRLF)
		ConsoleWrite("Open Patient Chart" &@CRLF )
		Call("openPatientChart",$arrConfig[15],$arrConfig[16])
		Sleep(8000)
		WinWaitActive("Chart - NOT FOR PATIENT USE")

	FileWriteLine($hFileOpen, @CRLF & " STEP 7 -- VERIFY EVENT FOR AAS CREATED BY PATIENT" & @CRLF)
	ConsoleWrite("Executing Query: " & $arrQuery[11] & $PID & $arrQuery[12] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[11] & $PID & $arrQuery[12] & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[11] & $PID & $arrQuery[12] & ";",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
			;MUActivityLogId
				ConsoleWrite("Verifying MUActivityLogId in MUActivityLog table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying MUActivityLogId in MUActivityLog table" & @CRLF)
				ConsoleWrite("Expected Result: " & $arrEvent[8] &" and Actual Result: " & $aData[2][0] & " -- ")
				If (StringCompare($arrEvent[8], $aData[2][0]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrEvent[8]  &" and Actual Result: " & $aData[2][0] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrEvent[8] &" and Actual Result: " & $aData[2][0] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf


	ConsoleWrite("Wait for AAS to arrive in Patient Chart" & @CRLF)
	FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait for AAS to arrive in Patient Chart"& @CRLF)

	FileWriteLine($hFileOpen, @CRLF & " STEP 8 -- VERIFY AAS ARRIVED IN PATIENT CHART" & @CRLF)
		$result = Call("verifyDocumentInPatientChart","Ask a Staff")
			If($result == "PASSED") Then
				ConsoleWrite("AAS arrived in Patient Chart" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- AAS arrived in Patient Chart -- PASSED" & @CRLF)

			Else
				ConsoleWrite("AAS did not arrive in Patient Chart" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- AAS did not arrive in Patient Chart -- FAILED" & @CRLF)
				Exit
			EndIf

	FileWriteLine($hFileOpen, @CRLF & " STEP 9 -- REPLY AAS FROM PATIENT CHART" & @CRLF)
		Call("createNewDocument",$arrConfig[17],$arrConfig[15],$arrConfig[16])
		Call("replyAAS","SR_1507883561241","1507883561241",$subjectAAS & $time & "0",$bodyAAS & $time & "0",$arrConfig[32])

	FileWriteLine($hFileOpen, @CRLF & " STEP 10 -- VERIFY MESSAGE DETAILS IN DOCUMENT TABLE" & @CRLF)
			ConsoleWrite("Executing Query: " & $arrQuery[28] & $PID & $arrQuery[29] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[28] & $PID & $arrQuery[29] & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[28] & $PID & $arrQuery[29] & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
				$msgDocumentId = Int($aData[1][0]) - 1

				ConsoleWrite("Document Id for Reply of AAS is " & $msgDocumentId & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Document Id for Reply of AAS is " & $msgDocumentId & @CRLF)

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
				ConsoleWrite("Reply of AAS Creation Date is " & $CreationDate & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Reply of AAS Creation Date is " & $CreationDate & @CRLF)

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

			Sleep(240000)

			FileWriteLine($hFileOpen, @CRLF & " STEP 11 -- VERIFY MESSAGE DETAILS IN CUSMEDFUSIONCOMMOUTGOING TABLE" & @CRLF)
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

				ConsoleWrite("Expected Result: " & "Ask A Doctor" &" and Actual Result: " & $aData[1][1] & " -- ")
				If (StringCompare("Ask A Doctor", $aData[1][1]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Ask A Doctor" &" and Actual Result: " & $aData[1][1] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Ask A Doctor" &" and Actual Result: " & $aData[1][1] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;Message Subject
				ConsoleWrite("Verifying Message Subject in cusMedfusionCommOutgoing table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Message Subject in cusMedfusionCommOutgoing table" & @CRLF)

				ConsoleWrite("Expected Result: " & "RE: " & $subjectAAS & $time & "0" &" and Actual Result: " & $aData[1][2] & " -- ")
				If (StringCompare("RE: " & $subjectAAS & $time & "0", $aData[1][2]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "RE: " & $subjectAAS & $time & "0" &" and Actual Result: " & $aData[1][2] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "RE: " & $subjectAAS & $time & "0" &" and Actual Result: " & $aData[1][2] & " -- FAILED" & @CRLF)
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

			FileWriteLine($hFileOpen, @CRLF & " STEP 12 -- VERIFY EVENT IN CUSMEDFUSIONMUEVENTS TABLE" & @CRLF)
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

			FileWriteLine($hFileOpen, @CRLF & " STEP 13 -- VERIFY EVENT 522 IN MUACTIVITYLOG TABLE" & @CRLF)
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

			FileWriteLine($hFileOpen, @CRLF & " STEP 14 -- VERIFY MESSAGE IN PATIENT PORTAL AND REPLY THE MESSAGE" & @CRLF)

			$messageSubject = "RE:" & $subjectAAS & $time & "0"
			ConsoleWrite("New Message Subject " & $messageSubject & @CRLF)

			ConsoleWrite("Message Body " & $arrConfig[32] & @CRLF)
			$temp = StringSplit($arrConfig[32]," ")
			$messageBody = Null
			For $i = 1 to $temp[0]
				$messageBody = $messageBody & $temp[$i]
			Next
			ConsoleWrite("New Message Body " & $messageBody & @CRLF)
			$secureMessagejar = StringReplace($currentDir, "askastaff", "jarfiles")  & "\askastaffreply.jar"
			$PID = Run(@ComSpec & ' /c java -jar ' & $secureMessagejar & ' ' & $messageSubject & ' ' & $messageBody & ' ' & $CreationDate &'' ,"","",$STDOUT_CHILD)
			ConsoleWrite("$PID :" & $PID & @CRLF)
			ProcessWaitClose($PID)
			$output =StdoutRead($PID)

				If(StringInStr($output,"PASSED")) Then
					ConsoleWrite("Message details verified in Patient Portal" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Message Details verified in Patient Portal -- PASSED" & @CRLF)
					ConsoleWrite("ASK A STAFF FLOW -- SUCCESSFULLY COMPLETED...." & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- ASK A STAFF FLOW -- SUCCESSFULLY COMPLETED...." & @CRLF)
				Else
					ConsoleWrite("Message details do not match in Patient Portal" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Message details do not match in Patient Portal -- FAILED" & @CRLF)
					Exit
				EndIf

	Else
		ConsoleWrite("ERROR Attaching GE CPS App...." & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Attaching GE CPS App...." & @CRLF)
	EndIf
EndIf