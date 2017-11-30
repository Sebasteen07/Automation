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
$commonStepsDir = StringReplace($currentDir , "officevisit", "commonsteps")
Call("deleteOldLogFiles",$commonStepsDir)
$logPath = $commonStepsDir & "\ProcessLog_OfficeVisit_" & $time & ".txt"
Call("setLogPath",$logpath)
$hFileOpen = Call("openLogFile")

$arrConfig = Call("setConfig")
$arrQuery = Call("openQueryFile")
$arrEvent = Call("openEventFile")
$arrDoctype = Call("openDoctypeFile")

;Update servicesettings
	FileWriteLine($hFileOpen, @CRLF & " STEP 1 -- UPDATE SOLUTIONS FOR OFFICE VISIT FLOW" & @CRLF)
		Call("connectDatabase",$arrConfig[4],$arrConfig[6],$arrConfig[7],$arrConfig[8])
		Local $aData,$iRows,$iColumns
		ConsoleWrite("Executing Query: " & $arrQuery[25] & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[25] & @CRLF)
		$iRval = _SQL_GetTable2D(-1,$arrQuery[25] & ";",$aData,$iRows,$iColumns)
		If $iRval = $SQL_OK then
			If(($aData[1][0] = 0) Or ($aData[2][0] = 0)) Then
				ConsoleWrite("Executing Query: " & $arrQuery[24] & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[24] & @CRLF)
				If _SQL_Execute(-1,$arrQuery[24]) = $SQL_ERROR then
					ConsoleWrite("ERROR Updating solutions...." & @CRLF)
					ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Updating solutions...." & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
					Exit

				Else
					ConsoleWrite("Soutions updated" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Solutions updated" & @CRLF)
				EndIf

			Else
				ConsoleWrite("Soultions already enabled" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Solutions already enabled" & @CRLF)
			EndIf

		Else
			ConsoleWrite("ERROR Checking Solutions...." & @CRLF)
			ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Checking Solutions...." & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
			Exit
		EndIf

		FileWriteLine($hFileOpen, @CRLF & " STEP 2 -- UPDATE SERVICESETTINGS FOR OFFICE VISIT FLOW" & @CRLF)
		ConsoleWrite("Executing Query: " & $arrQuery[43] & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[43] & @CRLF)
		$iRval = _SQL_GetTable2D(-1,$arrQuery[43] & ";",$aData,$iRows,$iColumns)
		If $iRval = $SQL_OK then
				If(($aData[1][0] = 1) And ($aData[2][0] = 1)) Then
					ConsoleWrite("Service settings already enabled" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Service Settings already enabled" & @CRLF)

				Else
					ConsoleWrite("Executing Query: " & $arrQuery[44] & @CRLF & $arrQuery[45] & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[44] & @CRLF & $arrQuery[45] & @CRLF )
					If (_SQL_Execute(-1,$arrQuery[44]) Or _SQL_Execute(-1,$arrQuery[45])) = $SQL_ERROR then
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
	ConsoleWrite("Exiting after checking pre-conditions for Office Visit Flow...." & @CRLF)
	FileWriteLine($hFileOpen, _NowCalc() & "  -- Exiting after checking pre-conditions for Office Visit Flow...." & @CRLF)
	Exit

Else
	;Open CPS -Patient Chart
	FileWriteLine($hFileOpen, @CRLF & " STEP 3 -- LOGIN TO CPS" & @CRLF)
	ConsoleWrite("Start the CPS client" &@CRLF )
	FileWriteLine($hFileOpen, _NowCalc()  &" -- Start the CPS client" &@CRLF)
	Call("startCPS",$arrConfig[1], $arrConfig[2], $arrConfig[3])

	WinActivate("Centricity Practice Solution")
	If(WinActive("Centricity Practice Solution")) Then
		FileWriteLine($hFileOpen, @CRLF & " STEP 4 -- OPEN PATIENT CHART" & @CRLF)
		ConsoleWrite("Open Patient Chart" &@CRLF )
		Call("openPatientChart",$arrConfig[15],$arrConfig[16])
		Sleep(8000)
		WinWaitActive("Chart - NOT FOR PATIENT USE")

		FileWriteLine($hFileOpen, @CRLF & " STEP 5 -- CREATE A NEW OFFICE VISIT FOR THE PATIENT" & @CRLF)
		If(WinActive("Chart - NOT FOR PATIENT USE")) Then
			$counter = 0
			Do
				If($arrConfig[9]=="Data Generation") Then
					ConsoleWrite("Creating Office Visit #" & $counter+1 & " of " & $arrConfig[36] & " office visits"& @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Creating Office Visit #" & $counter+1 & " of " & $arrConfig[36] & " office visits" & @CRLF)
				Else
					ConsoleWrite("Creating Office Visit #" &$counter+1 & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Creating Office Visit #" &$counter+1 & @CRLF)
				EndIf

				Call("createNewDocument",$arrConfig[18],$arrConfig[15],$arrConfig[16])
				Call("createAsthmaVisit",$arrConfig[15],$arrConfig[16])

				If($arrConfig[9]=="Data Generation") Then
					$counter +=1
					If($counter = $arrConfig[36]) Then
						ConsoleWrite("Exiting after data generation for Office Visit Flow...." & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Exiting after data generation for Office Visit Flow...." & @CRLF)
						Exit
					EndIf
					ConsoleWrite("Wait for 1 min before next Office Visit generation" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait for 1 min before next Office Visit generation" & @CRLF)
					Sleep(60000)

				Else
					$counter = $arrConfig[36]
				EndIf
			Until $counter = $arrConfig[36]

			ConsoleWrite("Office Visit created successfully...." & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Office Visit created successfully...." & @CRLF)

		FileWriteLine($hFileOpen, @CRLF & " STEP 6 -- GET PATIENT DETAILS FROM DATABASE" & @CRLF)
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

		FileWriteLine($hFileOpen, @CRLF & " STEP 7 -- VERIFY OFFICE VISIT DETAILS IN DOCUMENT TABLE" & @CRLF)
			ConsoleWrite("Executing Query: " & $arrQuery[28] & $PID & $arrQuery[29] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[28] & $PID & $arrQuery[29] & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[28] & $PID & $arrQuery[29] & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
				$visitDocumentId = Int($aData[1][0]) - 1

				ConsoleWrite("Document Id for Office Visit is " & $visitDocumentId & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Document Id for Office Visit is " & $visitDocumentId & @CRLF)

				;Summary
				ConsoleWrite("Verifying document summary in Document table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying document summary in Document table" & @CRLF)
				Call("assertData", $arrConfig[18], $aData[1][1])

				;Doctype
				ConsoleWrite("Verifying doctype in Document table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying doctype in Document table" & @CRLF)
				Call("assertData", $arrDoctype[1], $aData[1][2])

				$CreationDate = $aData[1][3]
				ConsoleWrite("Office Visit Creation Date is " & $CreationDate & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Office Visit Creation Date is " & $CreationDate & @CRLF)

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

			ConsoleWrite("Wait of 3 mins for Office Visit to be processed" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait of 3 mins for Office Visit to be processed" & @CRLF)
			Sleep(180000)

			FileWriteLine($hFileOpen, @CRLF & " STEP 8 -- VERIFY OFFICE VISIT DETAILS IN CUSMEDFUSIONCVSINFO TABLE" & @CRLF)
			ConsoleWrite("Executing Query: " & $arrQuery[30] & $visitDocumentId & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[30] & $visitDocumentId & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[30] & $visitDocumentId & ";",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
				$CVSInfoId = $aData[1][0]

				ConsoleWrite("CVSInfoId for Office Visit is " & $CVSInfoId & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- CVSInfoId for Office Visit is " & $CVSInfoId & @CRLF)

				;MedfusionMemberId
				ConsoleWrite("Verifying MedfusionMemberId in cusMedfusionCVSInfo table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying MedfusionMemberId in cusMedfusionCVSInfo table" & @CRLF)
				Call("assertData", $MedfusionMemberId, $aData[1][1])

				;PatientProfileId
				ConsoleWrite("Verifying PatientProfileId in cusMedfusionCVSInfo table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying PatientProfileId in cusMedfusionCVSInfo table" & @CRLF)
				Call("assertData", $PatientProfileId, $aData[1][2])

				;Status
				ConsoleWrite("Verifying Status in cusMedfusionCVSInfo table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Status in cusMedfusionCVSInfo table" & @CRLF)
				Call("assertData", "Processed", $aData[1][3])

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

			ConsoleWrite("Wait of 3 mins" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait of 3 mins" & @CRLF)
			Sleep(180000)

			FileWriteLine($hFileOpen, @CRLF & " STEP 9 -- VERIFY OFFICE VISIT DETAILS IN CUSMEDFUSIONOUTBOUNDCCD TABLE" & @CRLF)
			ConsoleWrite("Executing Query: " & $arrQuery[31] & $CVSInfoId & $arrQuery[32] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[31] & $CVSInfoId & $arrQuery[32] & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[31] & $CVSInfoId & $arrQuery[32] &";",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
				$outboundCcdId = $aData[1][0]

				ConsoleWrite("OutboundCcdId for Office Visit is " & $outboundCcdId & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- OutboundCcdId for Office Visit is " & $outboundCcdId & @CRLF)

				;MedfusionMemberId
				ConsoleWrite("Verifying MedfusionMemberId in cusMedfusionOutboundCcd table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying MedfusionMemberId in cusMedfusionOutboundCcd table" & @CRLF)
				Call("assertData", $MedfusionMemberId, $aData[1][1])

				;PatientProfileId
				ConsoleWrite("Verifying PatientProfileId in cusMedfusionOutboundCcd table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying PatientProfileId in cusMedfusionOutboundCcd table" & @CRLF)
				Call("assertData", $PatientProfileId, $aData[1][2])

				;State
				ConsoleWrite("Verifying State in cusMedfusionOutboundCcd table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying State in cusMedfusionOutboundCcd table" & @CRLF)
				Call("assertData", "Processed", $aData[1][3])

				;Status
				ConsoleWrite("Verifying Status in cusMedfusionOutboundCcd table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Status in cusMedfusionOutboundCcd table" & @CRLF)
				Call("assertData", "Success", $aData[1][4])

				;CCDType
				ConsoleWrite("Verifying CCDType in cusMedfusionOutboundCcd table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying CCDType in cusMedfusionOutboundCcd table" & @CRLF)
				Call("assertData", "Office Visit", $aData[1][5])

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf
			;_SQL_Close()

			FileWriteLine($hFileOpen, @CRLF & " STEP 10 -- VERIFY OFFICE VISIT IN PATIENT PORTAL" & @CRLF)
			$officeVisitJar = StringReplace($currentDir, "officevisit", "jarfiles")  & "\chkOfficeVisit.jar"
			$ProcessID = Run(@ComSpec & ' /c java -jar ' & $officeVisitJar & ' OfficeVisit ' & $CreationDate &'',"","",$STDOUT_CHILD)
			ConsoleWrite("$ProcessID :" & $ProcessID & @CRLF)
			ProcessWaitClose($ProcessID)
			$output =StdoutRead($ProcessID)

				If(StringInStr($output,"PASSED")) Then
					ConsoleWrite("Office Visit verified in Patient Portal and VDT events generated" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Office Visit verified in Patient Portal and VDT events generated -- PASSED" & @CRLF)
				Else
					ConsoleWrite("Error in office visit verification or VDT events generation in Patient Portal" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Error in office visit verification or VDT events generation in Patient Portal -- FAILED" & @CRLF)
					Exit
				EndIf

			FileWriteLine($hFileOpen, @CRLF & " STEP 11 -- VERIFY VDT EVENTS FOR OFFICE VISIT" & @CRLF)
				ConsoleWrite("Wait of 10 mins for events to arrive from Medfusion" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait of 10 mins for events to arrive from Medfusion" & @CRLF)
				Sleep(600000)

				;Call("connectDatabase",$arrConfig[4],$arrConfig[5],$arrConfig[7],$arrConfig[8])

				;Local $aData,$iRows,$iColumns
				ConsoleWrite("Executing Query: " & $arrQuery[11] & $PID & $arrQuery[12] & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[11] & $PID & $arrQuery[12] & @CRLF)
				$iRval = _SQL_GetTable2D(-1,$arrQuery[11] & $PID & $arrQuery[12] & ";",$aData,$iRows,$iColumns)

				If $iRval = $SQL_OK then
 					;MUActivityLogId - 511
					ConsoleWrite("Verifying View event for Office Visit in MUActivityLog table" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying View event for Offcie Visit in MUActivityLog table" & @CRLF)
					Call("assertData", $arrEvent[3], $aData[3][0])

					;MUActivityLogId - 512
					ConsoleWrite("Verifying Download event for Office Visit in MUActivityLog table" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Download event for Offcie Visit in MUActivityLog table" & @CRLF)
					Call("assertData", $arrEvent[4], $aData[2][0])

					;MUActivityLogId - 513
					ConsoleWrite("Verifying Transmit event for Office Visit in MUActivityLog table" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Transmit event for Offcie Visit in MUActivityLog table" & @CRLF)
					Call("assertData", $arrEvent[5], $aData[1][0])

				Else
					ConsoleWrite("ERROR Querying Database...." & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
					ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
					Exit
				EndIf

			FileWriteLine($hFileOpen, @CRLF & " STEP 12 -- CREATE CLINICAL VISIT SUMMARY FOR OFFICE VISIT" & @CRLF)
				WinActivate("Chart - NOT FOR PATIENT USE")
				$result = Call("createCVS")
				If($result == "PASSED") Then
					ConsoleWrite("CVS successfully created for patient" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- CVS successfully created for patient -- PASSED" & @CRLF)
				Else
					ConsoleWrite("CVS creation failed" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- CVS creation failed -- FAILED" & @CRLF)
					Exit
				EndIf
;-----------------------------------------------------------------------------
			FileWriteLine($hFileOpen, @CRLF & " STEP 13 -- VERIFY CVS DETAILS IN DOCUMENT TABLE" & @CRLF)
			ConsoleWrite("Executing Query: " & $arrQuery[28] & $PID & $arrQuery[29] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[28] & $PID & $arrQuery[29] & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[28] & $PID & $arrQuery[29] & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
				$cvsDocumentId = Int($aData[1][0]) - 1

				ConsoleWrite("Document Id for CVS is " & $cvsDocumentId & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Document Id for CVS is " & $cvsDocumentId & @CRLF)

				;Summary
				ConsoleWrite("Verifying document summary in Document table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying document summary in Document table" & @CRLF)
				Call("assertData", "Clinical Visit Summary", $aData[1][1])

				;Doctype
				ConsoleWrite("Verifying doctype in Document table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying doctype in Document table" & @CRLF)
				Call("assertData", $arrDoctype[2], $aData[1][2])

				$cvsCreationDate = $aData[1][3]
				ConsoleWrite("CVS Creation Date is " & $cvsCreationDate & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- CVS Creation Date is " & $cvsCreationDate & @CRLF)

				;XID - reference of Office Visit
				ConsoleWrite("Verifying XID in Document table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying XID in Document table" & @CRLF)
				$xidInDocument = Int($aData[1][4])-1
				Call("assertData", $visitDocumentId, $xidInDocument)

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

			ConsoleWrite("Wait of 3 mins for CVS to be processed" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait of 3 mins for CVS to be processed" & @CRLF)
			Sleep(180000)

			FileWriteLine($hFileOpen, @CRLF & " STEP 14 -- VERIFY CVS DETAILS IN CUSMEDFUSIONCVSEXTINFO TABLE" & @CRLF)
			ConsoleWrite("Executing Query: " & $arrQuery[33] & $cvsDocumentId & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[33] & $cvsDocumentId & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[33] & $cvsDocumentId & ";",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
				$CVSExtInfoId = $aData[1][0]

				ConsoleWrite("CVSInfoId for CVS is " & $CVSExtInfoId & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- CVSInfoId for CVS is " & $CVSExtInfoId & @CRLF)

				;MedfusionMemberId
				ConsoleWrite("Verifying MedfusionMemberId in cusMedfusionCVSExtInfo table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying MedfusionMemberId in cusMedfusionCVSExtInfo table" & @CRLF)
				Call("assertData", $MedfusionMemberId, $aData[1][1])

				;PatientProfileId
				ConsoleWrite("Verifying PatientProfileId in cusMedfusionCVSExtInfo table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying PatientProfileId in cusMedfusionCVSExtInfo table" & @CRLF)
				Call("assertData", $PatientProfileId, $aData[1][2])

				;Status
				ConsoleWrite("Verifying Status in cusMedfusionCVSExtInfo table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Status in cusMedfusionCVSExtInfo table" & @CRLF)
				Call("assertData", "Processed", $aData[1][3])

				;XID - reference of Office Visit
				ConsoleWrite("Verifying XID in cusMedfusionCVSExtInfo table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying XID in cusMedfusionCVSExtInfo table" & @CRLF)
				$xidInCVSExtInfo = Int($aData[1][4]) -  1
				Call("assertData", $visitDocumentId, $xidInCVSExtInfo)

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

			ConsoleWrite("Wait of 3 mins" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait of 3 mins" & @CRLF)
			Sleep(180000)

			FileWriteLine($hFileOpen, @CRLF & " STEP 15 -- VERIFY CVS DETAILS IN CUSMEDFUSIONOUTBOUNDCCD TABLE" & @CRLF)
			ConsoleWrite("Executing Query: " & $arrQuery[31] & $CVSExtInfoId  & $arrQuery[32] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[31] & $CVSExtInfoId  & $arrQuery[32] & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[31] & $CVSExtInfoId  & $arrQuery[32] & ";",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
				$cvsOutboundCcdId = $aData[1][0]

				ConsoleWrite("OutboundCcdId for CVS is " & $cvsOutboundCcdId & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- OutboundCcdId for CVS is " & $cvsOutboundCcdId & @CRLF)

				;MedfusionMemberId
				ConsoleWrite("Verifying MedfusionMemberId in cusMedfusionOutboundCcd table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying MedfusionMemberId in cusMedfusionOutboundCcd table" & @CRLF)
				Call("assertData", $MedfusionMemberId, $aData[1][1])

				;PatientProfileId
				ConsoleWrite("Verifying PatientProfileId in cusMedfusionOutboundCcd table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying PatientProfileId in cusMedfusionOutboundCcd table" & @CRLF)
				Call("assertData", $PatientProfileId, $aData[1][2])

				;State
				ConsoleWrite("Verifying State in cusMedfusionOutboundCcd table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying State in cusMedfusionOutboundCcd table" & @CRLF)
				Call("assertData", "Processed", $aData[1][3])

				;Status
				ConsoleWrite("Verifying Status in cusMedfusionOutboundCcd table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Status in cusMedfusionOutboundCcd table" & @CRLF)
				Call("assertData", "Success", $aData[1][4])

				;CCDType
				ConsoleWrite("Verifying CCDType in cusMedfusionOutboundCcd table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying CCDType in cusMedfusionOutboundCcd table" & @CRLF)
				Call("assertData", "CVS", $aData[1][5])

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

			FileWriteLine($hFileOpen, @CRLF & " STEP 16 -- VERIFY EVENT FOR CVS CREATION IN MUACTIVITYLOG TABLE" & @CRLF)
			ConsoleWrite("Executing Query: " & $arrQuery[11] & $PID & $arrQuery[12] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[11] & $PID & $arrQuery[12] & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[11] & $PID & $arrQuery[12] & ";",$aData,$iRows,$iColumns)

				If $iRval = $SQL_OK then
 					;MUActivityLogId - 519
					ConsoleWrite("Verifying event for CVS in MUActivityLog table" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying event for CVS in MUActivityLog table" & @CRLF)
					Call("assertData", $arrEvent[7], $aData[1][0])

				Else
					ConsoleWrite("ERROR Querying Database...." & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
					ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
					Exit
				EndIf
			_SQL_Close()

			FileWriteLine($hFileOpen, @CRLF & " STEP 17 -- VERIFY CVS IN PATIENT PORTAL" & @CRLF)
			$officeVisitJar = StringReplace($currentDir, "officevisit", "jarfiles")  & "\chkOfficeVisit.jar"
			$ProcessID = Run(@ComSpec & ' /c java -jar ' & $officeVisitJar & ' CVS ' & $cvsCreationDate &'',"","",$STDOUT_CHILD)
			ConsoleWrite("$ProcessID :" & $ProcessID & @CRLF)
			ProcessWaitClose($ProcessID)
			$output =StdoutRead($ProcessID)

				If(StringInStr($output,"PASSED")) Then
					ConsoleWrite("CVS verified in Patient Portal" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- CVS verified in Patient Portal -- PASSED" & @CRLF)
				Else
					ConsoleWrite("Error in CVS in Patient Portal" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Error in CVS in Patient Portal -- FAILED" & @CRLF)
					Exit
				EndIf

			ConsoleWrite("Office Visit Flow -- SUCCESSFULLY COMPLETED...." & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- OFFCIE VISIT FLOW -- SUCCESSFULLY COMPLETED...." & @CRLF)

;-----------------------------------------------------------------------------
		Else
			ConsoleWrite("ERROR Attaching Patient Chart...." & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Attaching Patient Chart...." & @CRLF)
		EndIf

	Else
		ConsoleWrite("ERROR Attaching GE CPS App...." & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Attaching GE CPS App...." & @CRLF)
	EndIf
EndIf