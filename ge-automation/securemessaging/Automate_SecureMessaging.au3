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
$commonStepsDir = StringReplace($currentDir , "securemessaging", "commonsteps")
Call("deleteOldLogFiles",$commonStepsDir)
$logPath = $commonStepsDir & "\ProcessLog_SecureMessaging_" & $time & ".txt"
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
				If($arrConfig[9]=="Data Generation") Then
					ConsoleWrite("Creating Secure Message #" & $counter+1 & " of " & $arrConfig[58] & " secure messages"& @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Creating Secure message #" & $counter+1 & " of " & $arrConfig[58] & " secure messages" & @CRLF)
				Else
					ConsoleWrite("Creating Secure Message #" &$counter+1 & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Creating Secure Message #" &$counter+1 & @CRLF)
				EndIf

				Call("createNewDocument",$arrConfig[17],$arrConfig[15],$arrConfig[16])
				Call("createEmailMessage",$arrConfig[15],$arrConfig[16],$arrConfig[32],$arrConfig[33])

				If($arrConfig[9]=="Data Generation") Then
					$counter +=1
					If($counter = $arrConfig[58]) Then
						ConsoleWrite("Exiting after data generation for Secure Messaging Flow...." & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Exiting after data generation for Secure Messaging Flow...." & @CRLF)
						Exit
					EndIf
					ConsoleWrite("Wait for 1 min before next Secure Message generation" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait for 1 min before next Secure Message generation" & @CRLF)
					Sleep(60000)

				Else
					$counter = $arrConfig[58]
				EndIf
			Until $counter = $arrConfig[58]

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
				Call("assertData", "E-mail Message", $aData[1][1])

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
				Call("assertData", "Patient Messaging", $aData[1][1])

			;Message Subject
				ConsoleWrite("Verifying Message Subject in cusMedfusionCommOutgoing table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Message Subject in cusMedfusionCommOutgoing table" & @CRLF)
				Call("assertData", $arrConfig[32], $aData[1][2])

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

			ConsoleWrite("Wait of 1 min for event being processed" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait of 1 min for event being processed" & @CRLF)
			Sleep(60000)

			FileWriteLine($hFileOpen, @CRLF & " STEP 9 -- VERIFY EVENT 522 IN MUACTIVITYLOG TABLE" & @CRLF)
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

			FileWriteLine($hFileOpen, @CRLF & " STEP 10 -- VERIFY MESSAGE IN PATIENT PORTAL AND REPLY THE MESSAGE" & @CRLF)
			ConsoleWrite("Message Subject " & $arrConfig[32] & @CRLF)
			$messageSubject = StringReplace($arrConfig[32]," ","")
			ConsoleWrite("New Message Subject " & $messageSubject & @CRLF)

			ConsoleWrite("Message Body " & $arrConfig[33] & @CRLF)
			$messageBody = StringReplace($arrConfig[33]," ","")
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
				Call("assertData", "Patient Messaging", $aData[1][0])

			;EMRDocumentType
				ConsoleWrite("Verifying EMRDocumentType in cusMedfusionCommIncoming table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying EMRDocumentType in cusMedfusionCommIncoming table" & @CRLF)
				Call("assertData", "Pt Comm", $aData[1][1])

			;MessageThread - ResponseToDocumentId
				ConsoleWrite("Verifying MessageThread in cusMedfusionCommIncoming table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying MessageThread in cusMedfusionCommIncoming table" & @CRLF)
				Call("assertData", $docId, $aData[1][2])

			;Subject
				ConsoleWrite("Verifying Subject in cusMedfusionCommIncoming table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Subject in cusMedfusionCommIncoming table" & @CRLF)
				Call("assertData", "Re: " & $arrConfig[32], $aData[1][3])

			;REsponseToOutgoingId
				ConsoleWrite("Verifying ResponseToOutgoingId in cusMedfusionCommIncoming table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying ReponseToOutgoingId in cusMedfusionCommIncoming table" & @CRLF)
				Call("assertData", $commOutgoingId, $aData[1][6])

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf
			_SQL_Close()

			ConsoleWrite("Wait of 4 mins for read receipt to be generated" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait of 4 mins for read receipt to be generated" & @CRLF)
			Sleep(240000)
			FileWriteLine($hFileOpen, @CRLF & " STEP 12 -- VERIFY READ RECEIPT IN PATIENT CHART" & @CRLF)
			$result = Call("verifyDocumentInPatientChart","Read Receipt")
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
