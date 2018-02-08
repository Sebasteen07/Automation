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

;$arrConfig = Call("setConfig")
$arrQuery = Call("openQueryFile")
$arrEvent = Call("openEventFile")
$arrDoctype = Call("openDoctypeFile")

$CPSURL = Call("readConfig","CPS URL")
$CPSLogin = Call("readConfig","CPS Login UserName")
$CPSPassword = Call("readConfig","CPS Login Password")
$SQLServer = Call("readConfig","SQL Server IP")
$CPSDb = Call("readConfig","Centricity DB")
$centralDb = Call("readConfig","Central DB")
$dbUser = Call("readConfig","SQL UserName")
$dbPassword = Call("readConfig","SQL Password")
$runFlag = Call("readConfig","Flag (Preconditions Check / Data Generation / Acceptance)")
$dataGenerationCounter =  Call("readConfig","Number of appointments to be scheduled from CPS")

$patientFirstName =  Call("readConfig","Patient First Name (For Patient Chart)")
$patientLastName =  Call("readConfig","Patient Last Name (For Patient Chart)")
$docTypeEmailMessage = Call("readConfig","New Document (Email Message)")

$askAStaffSubject =  Call("readConfig","AAS Subject")
$askAStaffBody =  Call("readConfig","AAS Body")
$askAStaffReply =  Call("readConfig","Reply to AAS")

;Update servicesettings
	FileWriteLine($hFileOpen, @CRLF & " STEP 1 -- UPDATE SERVICESETTINGS FOR ASK A STAFF FLOW" & @CRLF)
	Call("connectDatabase",$SQLServer,$centralDb,$dbUser,$dbPassword)

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

If($runFlag == "Preconditions Check") Then
		ConsoleWrite("Exiting after checking pre-conditions for Ask A Staff Flow...." & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Exiting after checking pre-conditions for Ask A Staff Flow...." & @CRLF)
		Exit

Else
		;Create AAS Question
	FileWriteLine($hFileOpen, @CRLF & " STEP 2 -- CREATE ASK A STAFF QUESTION FROM PATIENT PORTAL" & @CRLF)
	ConsoleWrite("Create ask a staff question from Patient Portal" &@CRLF )

 	$createAASJar = StringReplace($currentDir, "askastaff", "jarfiles")  & "\askastaff.jar"
		$subjectAAS = StringReplace($askAStaffSubject," ","")
		$bodyAAS = StringReplace($askAStaffBody," ","")

	$counter = 0
	Do
		If($runFlag=="Data Generation") Then
			ConsoleWrite("Creating AAS #" & $counter+1 & " of " & $dataGenerationCounter & " ask a staff questions"& @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Creating AAS #" & $counter+1 & " of " & $dataGenerationCounter & " ask a staff questions" & @CRLF)
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

		If($runFlag=="Data Generation") Then
			$counter +=1
			If($counter = $dataGenerationCounter) Then
				ConsoleWrite("Exiting after data generation for Ask A Staff Flow...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Exiting after data generation for Ask A Staff Flow...." & @CRLF)
				Exit
			EndIf
			ConsoleWrite("Wait for 1 min before next AAS generation" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait for 1 min before next AAS generation" & @CRLF)
			Sleep(60000)

		Else
			$counter = $dataGenerationCounter
		EndIf
	Until $counter = $dataGenerationCounter

	FileWriteLine($hFileOpen, @CRLF & " STEP 3 -- GET PATIENT DETAILS FROM DATABASE" & @CRLF)
		Call("connectDatabase",$SQLServer,$CPSDb,$dbUser,$dbPassword)
		Local $aData,$iRows,$iColumns

		;Get PatientProfileId,PID
			$newQuery = StringReplace($arrQuery[6],"PatientProfileId =","First = '" & $patientFirstName & "' and Last = '" & $patientLastName & "'")
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
			$newQuery = StringReplace($arrQuery[23],"LASTNAME = LNAME and FIRSTNAME = FNAME","LOGINNAME = '" & $CPSLogin & "'")
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

	ConsoleWrite("Wait of 4 mins for AAS to arrive from Medfusion" & @CRLF)
	FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait of 4 mins for AAS to arrive from Medfusion"& @CRLF)
	Sleep(240000)

		FileWriteLine($hFileOpen, @CRLF & " STEP 4 -- VERIFY AAS DETAILS IN DATABASE" & @CRLF)

			ConsoleWrite("Executing Query: " & $arrQuery[34] & $PatientProfileId & $arrQuery[35] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[34] & $PatientProfileId & $arrQuery[35]& @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[34] & $PatientProfileId & $arrQuery[35] & ";",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
			;CommServiceType
				ConsoleWrite("Verifying CommServiceType in cusMedfusionCommIncoming table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying CommServiceType in cusMedfusionCommIncoming table" & @CRLF)
				Call("assertData", "Ask A Doctor", $aData[1][0])

			;EMRDocumentType
				ConsoleWrite("Verifying EMRDocumentType in cusMedfusionCommIncoming table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying EMRDocumentType in cusMedfusionCommIncoming table" & @CRLF)
				Call("assertData", "AAS", $aData[1][1])

			;Subject
				ConsoleWrite("Verifying Subject in cusMedfusionCommIncoming table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Subject in cusMedfusionCommIncoming table" & @CRLF)
				Call("assertData", $subjectAAS & $time & "0", $aData[1][3])

			;Received Date
			$receiveingDate = $aData[1][4]

			;CommIncomingID
			$commIncomingId  = $aData[1][5]

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
	Call("startCPS",$CPSURL,$CPSLogin,$CPSPassword)

	WinActivate("Centricity Practice Solution")
	If(WinActive("Centricity Practice Solution")) Then
		FileWriteLine($hFileOpen, @CRLF & " STEP 6 -- OPEN PATIENT CHART" & @CRLF)
		ConsoleWrite("Open Patient Chart" &@CRLF )
		Call("openPatientChart",$patientFirstName,$patientLastName)
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
				Call("assertData", $arrEvent[8], $aData[2][0])

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
		ConsoleWrite("Reply the AAS from Patient Chart" & @CRLF)
		Call("createNewDocument",$docTypeEmailMessage,$patientFirstName,$patientLastName)
		Call("replyAAS",$patientFirstName,$patientLastName,$subjectAAS & $time & "0",$bodyAAS & $time & "0",$askAStaffReply)

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
				Call("assertData", "E-mail Message", $aData[1][1])

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

			ConsoleWrite("Wait of 4 mins for reply to reach Medfusion" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait of 4 mins for reply to reach Medfusion" & @CRLF)
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
				Call("assertData", $PatientProfileId, $aData[1][0])

			;CommServiceType
				ConsoleWrite("Verifying CommServiceType in cusMedfusionCommOutgoing table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying CommServiceType in cusMedfusionCommOutgoing table" & @CRLF)
				Call("assertData", "Ask A Doctor", $aData[1][1])

			;Message Subject
				ConsoleWrite("Verifying Message Subject in cusMedfusionCommOutgoing table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Message Subject in cusMedfusionCommOutgoing table" & @CRLF)
				Call("assertData", "RE: " & $subjectAAS & $time & "0", $aData[1][2])

			;Created by User
				ConsoleWrite("Verifying user creating the communication in cusMedfusionCommOutgoing table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying user creating the communication in cusMedfusionCommOutgoing table" & @CRLF)
				Call("assertData",$CPSLogin, $aData[1][3])

			;CommSentStatus
				ConsoleWrite("Verifying CommSentStatus in cusMedfusionCommOutgoing table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying CommSentStatus in cusMedfusionCommOutgoing table" & @CRLF)
				Call("assertData","1", $aData[1][4])

			;ResponseToIncomingId
				ConsoleWrite("Verifying ResponseToIncomingId in cusMedfusionCommOutgoing table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying ResponseToIncomingId in cusMedfusionCommOutgoing table" & @CRLF)
				Call("assertData",$commIncomingId, $aData[1][9])

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
				Call("assertData","PI_" & $commOutgoingId & "_" & $msgDocumentId, $aData[1][0])

			;Evnet Type
				ConsoleWrite("Verifying Event Type in cusMedfusionMUEvents table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Event Type in cusMedfusionMUEvents table" & @CRLF)
				Call("assertData","Provider Initiated", $aData[1][1])

			;Provider
				ConsoleWrite("Verifying Provider in cusMedfusionMUEvents table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Provider in cusMedfusionMUEvents table" & @CRLF)
				Call("assertData",$CPSLogin, $aData[1][2])

			;SDID
				ConsoleWrite("Verifying SDID in cusMedfusionMUEvents table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying SDID in cusMedfusionMUEvents table" & @CRLF)
				$DocumentIdInEvents = Int($aData[1][3]) - 1
				Call("assertData",$msgDocumentId, $DocumentIdInEvents)

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

			ConsoleWrite("Wait of 3 mins for event being processed" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait of 3 mins for event being processed" & @CRLF)
			Sleep(180000)

			FileWriteLine($hFileOpen, @CRLF & " STEP 13 -- VERIFY EVENT 522 IN MUACTIVITYLOG TABLE" & @CRLF)
			ConsoleWrite("Executing Query: " & $arrQuery[11] & $PID & $arrQuery[12] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[11] & $PID & $arrQuery[12] & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[11] & $PID & $arrQuery[12] & ";",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
			;MUActivityLogId
				ConsoleWrite("Verifying MUActivityLogId in MUActivityLog table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying MUActivityLogId in MUActivityLog table" & @CRLF)
				Call("assertData",$arrEvent[9], $aData[1][0])

			;PVID
				ConsoleWrite("Verifying PVID in MUActivityLog table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying PVID in MUActivityLog table" & @CRLF)
				$temp = Int($aData[1][2]) - 1
				Call("assertData",$pvdrPVID, $temp)

			;SDID
				ConsoleWrite("Verifying SDID in MUActivityLog table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying SDID in MUActivityLog table" & @CRLF)
				$temp = Int($aData[1][1]) - 1
				Call("assertData",$msgDocumentId, $temp)

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf
			_SQL_Close()

			FileWriteLine($hFileOpen, @CRLF & " STEP 14 -- VERIFY MESSAGE IN PATIENT PORTAL AND REPLY THE MESSAGE" & @CRLF)

			$messageSubject = "RE:" & $subjectAAS & $time & "0"
			ConsoleWrite("New Message Subject " & $messageSubject & @CRLF)

			ConsoleWrite("Message Body " & $askAStaffReply & @CRLF)
			$messageBody = StringReplace($askAStaffReply," ","")
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