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

$currentDir = @ScriptDir
$logPath = StringReplace($currentDir, "officevisit", "commonsteps") & "\ProcessLog.txt"

;Opening Log file
		Local $hFileOpen = FileOpen($logPath, $FO_OVERWRITE)
		If $hFileOpen = -1 Then
			MsgBox($MB_SYSTEMMODAL, "", "An error occurred while writing process log file.")
			Exit
		EndIf

$arrConfig = Call("setConfig")
$arrQuery = Call("openQueryFile")
$arrEvent = Call("openEventFile")
$arrDoctype = Call("openDoctypeFile")

;Update servicesettings
	FileWriteLine($hFileOpen, @CRLF & " STEP 1 -- UPDATE SERVICESETTINGS FOR OFFICE VISIT FLOW" & @CRLF)
		Call("connectDatabase",$arrConfig[4],$arrConfig[6],$arrConfig[7],$arrConfig[8])
		Local $aData,$iRows,$iColumns
		$iRval = _SQL_GetTable2D(-1,$arrQuery[25] & ";",$aData,$iRows,$iColumns)
		If $iRval = $SQL_OK then
			If(($aData[1][0] = 0) Or ($aData[2][0] = 0)) Then
				If _SQL_Execute(-1,$arrQuery[24]) = $SQL_ERROR then
					;Msgbox(0 + 16 +262144,"Error",_SQL_GetErrMsg())
					ConsoleWrite("ERROR Updating service settings...." & @CRLF)
					ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Updating service settings...." & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
					Exit

				Else
					ConsoleWrite("Service settings updated" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Service Settings updated" & @CRLF)
				EndIf

			Else
				ConsoleWrite("Service settings already enabled" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Service Settings already enabled" & @CRLF)
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

		FileWriteLine($hFileOpen, @CRLF & " STEP 4 -- CREATE A NEW OFFICE VISIT FOR THE PATIENT" & @CRLF)
		If(WinActive("Chart - NOT FOR PATIENT USE")) Then
			$counter = 0
			Do
				Call("createNewDocument",$arrConfig[18],$arrConfig[15],$arrConfig[16])
				Call("createAsthmaVisit",$arrConfig[15],$arrConfig[16])

				If($arrConfig[9]=="Data Generation") Then
					$counter +=1
					If($counter = $arrConfig[30]) Then
						ConsoleWrite("Exiting after data generation for Office Visit Flow...." & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Exiting after data generation for Office Visit Flow...." & @CRLF)
						Exit
					EndIf

				Else
					$counter = $arrConfig[30]
				EndIf
			Sleep(1000)
			Until $counter = $arrConfig[30]

			ConsoleWrite("Office Visit created successfully...." & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Office Visit created successfully...." & @CRLF)

		FileWriteLine($hFileOpen, @CRLF & " STEP 5 -- GET PATIENT DETAILS FROM DATABASE" & @CRLF)
		Call("connectDatabase",$arrConfig[4],$arrConfig[5],$arrConfig[7],$arrConfig[8])

			Local $aData,$iRows,$iColumns	;Variables to store the array data in to and the row count and the column count
		;Get PatientProfileId,PID
			$newQuery = StringReplace($arrQuery[6],"PatientProfileId =","First = '" & $arrConfig[15] & "' and Last = '" & $arrConfig[16] & "'")
			ConsoleWrite($newQuery &  @CRLF)
			$iRval = _SQL_GetTable2D(-1,$newQuery & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
				$PatientProfileId=$aData[1][6]
				$PID = Int($aData[1][1]) - 1

				ConsoleWrite("Patient Profile Id is " & $PatientProfileId & @CRLF	& "PID is " & $PID & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Patient Profile Id is " & $PatientProfileId & @CRLF & _NowCalc() &"  -- PID is " & $PID & @CRLF)
			EndIf

		;Get MedfusionMemberId
			$newQuery = StringReplace($arrQuery[5],"MemberId =","PatientProfileId = " & $PatientProfileId)
			ConsoleWrite($newQuery & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$newQuery & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK Then
				$MedfusionMemberId = $aData[1][2]

				ConsoleWrite("Medfusion Member Id is " & $MedfusionMemberId & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Medfusion Member Id is " & $MedfusionMemberId & @CRLF)
			EndIf

		FileWriteLine($hFileOpen, @CRLF & " STEP 6 -- VERIFY OFFICE VISIT DETAILS IN DOCUMENT TABLE" & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[28] & $PID & $arrQuery[29] & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
				$visitDocumentId = Int($aData[1][0]) - 1

				ConsoleWrite("Document Id for Office Visit is " & $visitDocumentId & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Document Id for Office Visit is " & $visitDocumentId & @CRLF)

				;Summary
				ConsoleWrite("Verifying document summary in Document table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying document summary in Document table" & @CRLF)

				ConsoleWrite("Expected Result: " & $arrConfig[18] &" and Actual Result: " & $aData[1][1] & " -- ")
				If (StringCompare($arrConfig[18], $aData[1][1]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrConfig[18] &" and Actual Result: " & $aData[1][1] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrConfig[18] &" and Actual Result: " & $aData[1][1] & " -- FAILED" & @CRLF)
					Exit
				EndIf

				;Doctype
				ConsoleWrite("Verifying doctype in Document table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying doctype in Document table" & @CRLF)

				ConsoleWrite("Expected Result: " & $arrDoctype[1] &" and Actual Result: " & $aData[1][2] & " -- ")
				If (StringCompare($arrDoctype[1], $aData[1][2]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrDoctype[1] &" and Actual Result: " & $aData[1][2] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrDoctype[1] &" and Actual Result: " & $aData[1][2] & " -- FAILED" & @CRLF)
					Exit
				EndIf

				$CreationDate = $aData[1][3]

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				Exit
			EndIf

			ConsoleWrite("Wait of 3" & @CRLF)
			Sleep(180000)

			FileWriteLine($hFileOpen, @CRLF & " STEP 7 -- VERIFY OFFICE VISIT DETAILS IN CUSMEDFUSIONCVSINFO TABLE" & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[30] & $visitDocumentId & ";",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
				$CVSInfoId = $aData[1][0]

				ConsoleWrite("CVSInfoId for Office Visit is " & $CVSInfoId & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- CVSInfoId for Office Visit is " & $CVSInfoId & @CRLF)

				;MedfusionMemberId
				ConsoleWrite("Verifying MedfusionMemberId in cusMedfusionCVSInfo table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying MedfusionMemberId in cusMedfusionCVSInfo table" & @CRLF)

				ConsoleWrite("Expected Result: " & $MedfusionMemberId &" and Actual Result: " & $aData[1][1] & " -- ")
				If (StringCompare($MedfusionMemberId, $aData[1][1]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $MedfusionMemberId &" and Actual Result: " & $aData[1][1] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $MedfusionMemberId &" and Actual Result: " & $aData[1][1] & " -- FAILED" & @CRLF)
					Exit
				EndIf

				;PatientProfileId
				ConsoleWrite("Verifying PatientProfileId in cusMedfusionCVSInfo table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying PatientProfileId in cusMedfusionCVSInfo table" & @CRLF)

				ConsoleWrite("Expected Result: " & $PatientProfileId &" and Actual Result: " & $aData[1][2] & " -- ")
				If (StringCompare($PatientProfileId, $aData[1][2]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $PatientProfileId &" and Actual Result: " & $aData[1][2] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $PatientProfileId &" and Actual Result: " & $aData[1][2] & " -- FAILED" & @CRLF)
					Exit
				EndIf

				;Status
				ConsoleWrite("Verifying Status in cusMedfusionCVSInfo table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Status in cusMedfusionCVSInfo table" & @CRLF)

				ConsoleWrite("Expected Result: " & "Processed" &" and Actual Result: " & $aData[1][3] & " -- ")
				If (StringCompare("Processed", $aData[1][3]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Processed" &" and Actual Result: " & $aData[1][3] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Processed" &" and Actual Result: " & $aData[1][3] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				Exit
			EndIf

			ConsoleWrite("Wait of 3" & @CRLF)
			Sleep(180000)

			FileWriteLine($hFileOpen, @CRLF & " STEP 8 -- VERIFY OFFICE VISIT DETAILS IN CUSMEDFUSIONOUTBOUNDCCD TABLE" & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[31] & $CVSInfoId & $arrQuery[32] &";",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
				$outboundCcdId = $aData[1][0]

				ConsoleWrite("OutboundCcdId for Office Visit is " & $outboundCcdId & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- OutboundCcdId for Office Visit is " & $outboundCcdId & @CRLF)

				;MedfusionMemberId
				ConsoleWrite("Verifying MedfusionMemberId in cusMedfusionOutboundCcd table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying MedfusionMemberId in cusMedfusionOutboundCcd table" & @CRLF)

				ConsoleWrite("Expected Result: " & $MedfusionMemberId &" and Actual Result: " & $aData[1][1] & " -- ")
				If (StringCompare($MedfusionMemberId, $aData[1][1]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $MedfusionMemberId &" and Actual Result: " & $aData[1][1] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $MedfusionMemberId &" and Actual Result: " & $aData[1][1] & " -- FAILED" & @CRLF)
					Exit
				EndIf

				;PatientProfileId
				ConsoleWrite("Verifying PatientProfileId in cusMedfusionOutboundCcd table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying PatientProfileId in cusMedfusionOutboundCcd table" & @CRLF)

				ConsoleWrite("Expected Result: " & $PatientProfileId &" and Actual Result: " & $aData[1][2] & " -- ")
				If (StringCompare($PatientProfileId, $aData[1][2]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $PatientProfileId &" and Actual Result: " & $aData[1][2] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $PatientProfileId &" and Actual Result: " & $aData[1][2] & " -- FAILED" & @CRLF)
					Exit
				EndIf

				;State
				ConsoleWrite("Verifying State in cusMedfusionOutboundCcd table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying State in cusMedfusionOutboundCcd table" & @CRLF)

				ConsoleWrite("Expected Result: " & "Processed" &" and Actual Result: " & $aData[1][3] & " -- ")
				If (StringCompare("Processed", $aData[1][3]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Processed" &" and Actual Result: " & $aData[1][3] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Processed" &" and Actual Result: " & $aData[1][3] & " -- FAILED" & @CRLF)
					Exit
				EndIf

				;Status
				ConsoleWrite("Verifying Status in cusMedfusionOutboundCcd table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Status in cusMedfusionOutboundCcd table" & @CRLF)

				ConsoleWrite("Expected Result: " & "Success" &" and Actual Result: " & $aData[1][4] & " -- ")
				If (StringCompare("Success", $aData[1][4]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Success" &" and Actual Result: " & $aData[1][4] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Success" &" and Actual Result: " & $aData[1][4] & " -- FAILED" & @CRLF)
					Exit
				EndIf

				;CCDType
				ConsoleWrite("Verifying CCDType in cusMedfusionOutboundCcd table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying CCDType in cusMedfusionOutboundCcd table" & @CRLF)

				ConsoleWrite("Expected Result: " & "Office Visit" &" and Actual Result: " & $aData[1][5] & " -- ")
				If (StringCompare("Office Visit", $aData[1][5]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Office Visit" &" and Actual Result: " & $aData[1][5] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Office Visit" &" and Actual Result: " & $aData[1][5] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				Exit
			EndIf
			_SQL_Close()

			FileWriteLine($hFileOpen, @CRLF & " STEP 9 -- VERIFY OFFICE VISIT IN PATIENT PORTAL" & @CRLF)
			$officeVisitJar = StringReplace($currentDir, "officevisit", "jarfiles")  & "\chkOfficeVisit.jar"
			$PID = Run(@ComSpec & ' /c java -jar ' & $officeVisitJar & ' OfficeVisit ' & $CreationDate &'',"","",$STDOUT_CHILD)
			ConsoleWrite("$PID :" & $PID & @CRLF)
			ProcessWaitClose($PID)
			$output =StdoutRead($PID)

				If(StringInStr($output,"PASSED")) Then
					ConsoleWrite("Office Visit verified in Patient Portal and VDT events generated" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Office Visit verified in Patient Portal and VDT events generated -- PASSED" & @CRLF)
				Else
					ConsoleWrite("Error in office visit verification or VDT events generation in Patient Portal" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Error in office visit verification or VDT events generation in Patient Portal -- FAILED" & @CRLF)
					Exit
				EndIf

			FileWriteLine($hFileOpen, @CRLF & " STEP 10 -- VERIFY VDT EVENTS FOR OFFICE VISIT" & @CRLF)
				ConsoleWrite("Wait for events to arrive from Medfusion" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait for events to arrive from Medfusion -- FAILED" & @CRLF)
				Sleep(600000)
				ConsoleWrite("Wait for 10"  & @CRLF)

				Call("connectDatabase",$arrConfig[4],$arrConfig[5],$arrConfig[7],$arrConfig[8])

				Local $aData,$iRows,$iColumns
				$iRval = _SQL_GetTable2D(-1,$arrQuery[11] & $PID & $arrQuery[12] & ";",$aData,$iRows,$iColumns)

				If $iRval = $SQL_OK then
 					;MUActivityLogId - 511
					ConsoleWrite("Verifying View event for Office Visit in MUActivityLog table" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying View event for Offcie Visit in MUActivityLog table" & @CRLF)
					ConsoleWrite("Expected Result: " & $arrEvent[3] &" and Actual Result: " & $aData[3][0] & " -- ")
					If (StringCompare($arrEvent[3], $aData[3][0]) = 0) Then
						ConsoleWrite("PASSED" & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrEvent[3]  &" and Actual Result: " & $aData[3][0] & " -- PASSED" & @CRLF)
					Else
						ConsoleWrite("FAILED" & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrEvent[3] &" and Actual Result: " & $aData[3][0] & " -- FAILED" & @CRLF)
						Exit
					EndIf

					;MUActivityLogId - 512
					ConsoleWrite("Verifying Download event for Office Visit in MUActivityLog table" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Download event for Offcie Visit in MUActivityLog table" & @CRLF)
					ConsoleWrite("Expected Result: " & $arrEvent[4] &" and Actual Result: " & $aData[2][0] & " -- ")
					If (StringCompare($arrEvent[4], $aData[2][0]) = 0) Then
						ConsoleWrite("PASSED" & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrEvent[4]  &" and Actual Result: " & $aData[2][0] & " -- PASSED" & @CRLF)
					Else
						ConsoleWrite("FAILED" & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrEvent[4] &" and Actual Result: " & $aData[2][0] & " -- FAILED" & @CRLF)
						Exit
					EndIf

					;MUActivityLogId - 513
					ConsoleWrite("Verifying Transmit event for Office Visit in MUActivityLog table" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Transmit event for Offcie Visit in MUActivityLog table" & @CRLF)
					ConsoleWrite("Expected Result: " & $arrEvent[5] &" and Actual Result: " & $aData[1][0] & " -- ")
					If (StringCompare($arrEvent[5], $aData[1][0]) = 0) Then
						ConsoleWrite("PASSED" & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrEvent[5]  &" and Actual Result: " & $aData[1][0] & " -- PASSED" & @CRLF)
					Else
						ConsoleWrite("FAILED" & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrEvent[5] &" and Actual Result: " & $aData[1][0] & " -- FAILED" & @CRLF)
						Exit
					EndIf

				Else
					ConsoleWrite("ERROR Querying Database...." & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
					Exit
				EndIf

			FileWriteLine($hFileOpen, @CRLF & " STEP 11 -- CREATE CLINICAL VISIT SUMMARY FOR OFFICE VISIT" & @CRLF)
				WinActivate("Chart - NOT FOR PATIENT USE")
				Call("createCVS",$arrConfig[15],$arrConfig[16])
;-----------------------------------------------------------------------------
			FileWriteLine($hFileOpen, @CRLF & " STEP 12 -- VERIFY CVS DETAILS IN DOCUMENT TABLE" & @CRLF)

			$iRval = _SQL_GetTable2D(-1,$arrQuery[28] & $PID & $arrQuery[29] & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
				$cvsDocumentId = Int($aData[1][0]) - 1

				ConsoleWrite("Document Id for CVS is " & $cvsDocumentId & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Document Id for CVS is " & $cvsDocumentId & @CRLF)

				;Summary
				ConsoleWrite("Verifying document summary in Document table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying document summary in Document table" & @CRLF)

				ConsoleWrite("Expected Result: " & "Clinical Visit Summary" &" and Actual Result: " & $aData[1][1] & " -- ")
				If (StringCompare("Clinical Visit Summary", $aData[1][1]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Clinical Visit Summary" &" and Actual Result: " & $aData[1][1] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Clinical Visit Summary" &" and Actual Result: " & $aData[1][1] & " -- FAILED" & @CRLF)
					Exit
				EndIf

				;Doctype
				ConsoleWrite("Verifying doctype in Document table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying doctype in Document table" & @CRLF)

				ConsoleWrite("Expected Result: " & $arrDoctype[2] &" and Actual Result: " & $aData[1][2] & " -- ")
				If (StringCompare($arrDoctype[2], $aData[1][2]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrDoctype[2] &" and Actual Result: " & $aData[1][2] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrDoctype[2] &" and Actual Result: " & $aData[1][2] & " -- FAILED" & @CRLF)
					Exit
				EndIf

				$cvsCreationDate = $aData[1][3]

				;XID - reference of Office Visit
				ConsoleWrite("Verifying XID in Document table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying XID in Document table" & @CRLF)

				ConsoleWrite("Expected Result: " & $visitDocumentId &" and Actual Result: " & $aData[1][4] & " -- ")
				If (StringCompare($visitDocumentId, $aData[1][4]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $visitDocumentId &" and Actual Result: " & $aData[1][4] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $visitDocumentId &" and Actual Result: " & $aData[1][4] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				Exit
			EndIf

			ConsoleWrite("Wait of 3" & @CRLF)
			Sleep(180000)

			FileWriteLine($hFileOpen, @CRLF & " STEP 13 -- VERIFY CVS DETAILS IN CUSMEDFUSIONCVSEXTINFO TABLE" & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[30] & $cvsDocumentId & ";",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
				$CVSExtInfoId = $aData[1][0]

				ConsoleWrite("CVSInfoId for CVS is " & $CVSExtInfoId & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- CVSInfoId for CVS is " & $CVSExtInfoId & @CRLF)

				;MedfusionMemberId
				ConsoleWrite("Verifying MedfusionMemberId in cusMedfusionCVSExtInfo table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying MedfusionMemberId in cusMedfusionCVSExtInfo table" & @CRLF)

				ConsoleWrite("Expected Result: " & $MedfusionMemberId &" and Actual Result: " & $aData[1][1] & " -- ")
				If (StringCompare($MedfusionMemberId, $aData[1][1]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $MedfusionMemberId &" and Actual Result: " & $aData[1][1] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $MedfusionMemberId &" and Actual Result: " & $aData[1][1] & " -- FAILED" & @CRLF)
					Exit
				EndIf

				;PatientProfileId
				ConsoleWrite("Verifying PatientProfileId in cusMedfusionCVSExtInfo table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying PatientProfileId in cusMedfusionCVSExtInfo table" & @CRLF)

				ConsoleWrite("Expected Result: " & $PatientProfileId &" and Actual Result: " & $aData[1][2] & " -- ")
				If (StringCompare($PatientProfileId, $aData[1][2]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $PatientProfileId &" and Actual Result: " & $aData[1][2] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $PatientProfileId &" and Actual Result: " & $aData[1][2] & " -- FAILED" & @CRLF)
					Exit
				EndIf

				;Status
				ConsoleWrite("Verifying Status in cusMedfusionCVSExtInfo table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Status in cusMedfusionCVSExtInfo table" & @CRLF)

				ConsoleWrite("Expected Result: " & "Processed" &" and Actual Result: " & $aData[1][3] & " -- ")
				If (StringCompare("Processed", $aData[1][3]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Processed" &" and Actual Result: " & $aData[1][3] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Processed" &" and Actual Result: " & $aData[1][3] & " -- FAILED" & @CRLF)
					Exit
				EndIf

				;XID - reference of Office Visit
				ConsoleWrite("Verifying XID in cusMedfusionCVSExtInfo table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying XID in cusMedfusionCVSExtInfo table" & @CRLF)

				ConsoleWrite("Expected Result: " & $visitDocumentId &" and Actual Result: " & $aData[1][4] & " -- ")
				If (StringCompare($visitDocumentId, $aData[1][4]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $visitDocumentId &" and Actual Result: " & $aData[1][4] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $visitDocumentId &" and Actual Result: " & $aData[1][4] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				Exit
			EndIf

			ConsoleWrite("Wait of 3" & @CRLF)
			Sleep(180000)

			FileWriteLine($hFileOpen, @CRLF & " STEP 14 -- VERIFY CVS DETAILS IN CUSMEDFUSIONOUTBOUNDCCD TABLE" & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[31] & $CVSExtInfoId  & $arrQuery[32] & ";",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
				$cvsOutboundCcdId = $aData[1][0]

				ConsoleWrite("OutboundCcdId for CVS is " & $cvsOutboundCcdId & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- OutboundCcdId for CVS is " & $cvsOutboundCcdId & @CRLF)

				;MedfusionMemberId
				ConsoleWrite("Verifying MedfusionMemberId in cusMedfusionOutboundCcd table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying MedfusionMemberId in cusMedfusionOutboundCcd table" & @CRLF)

				ConsoleWrite("Expected Result: " & $MedfusionMemberId &" and Actual Result: " & $aData[1][1] & " -- ")
				If (StringCompare($MedfusionMemberId, $aData[1][1]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $MedfusionMemberId &" and Actual Result: " & $aData[1][1] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $MedfusionMemberId &" and Actual Result: " & $aData[1][1] & " -- FAILED" & @CRLF)
					Exit
				EndIf

				;PatientProfileId
				ConsoleWrite("Verifying PatientProfileId in cusMedfusionOutboundCcd table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying PatientProfileId in cusMedfusionOutboundCcd table" & @CRLF)

				ConsoleWrite("Expected Result: " & $PatientProfileId &" and Actual Result: " & $aData[1][2] & " -- ")
				If (StringCompare($PatientProfileId, $aData[1][2]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $PatientProfileId &" and Actual Result: " & $aData[1][2] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $PatientProfileId &" and Actual Result: " & $aData[1][2] & " -- FAILED" & @CRLF)
					Exit
				EndIf

				;State
				ConsoleWrite("Verifying State in cusMedfusionOutboundCcd table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying State in cusMedfusionOutboundCcd table" & @CRLF)

				ConsoleWrite("Expected Result: " & "Processed" &" and Actual Result: " & $aData[1][3] & " -- ")
				If (StringCompare("Processed", $aData[1][3]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Processed" &" and Actual Result: " & $aData[1][3] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Processed" &" and Actual Result: " & $aData[1][3] & " -- FAILED" & @CRLF)
					Exit
				EndIf

				;Status
				ConsoleWrite("Verifying Status in cusMedfusionOutboundCcd table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Status in cusMedfusionOutboundCcd table" & @CRLF)

				ConsoleWrite("Expected Result: " & "Success" &" and Actual Result: " & $aData[1][4] & " -- ")
				If (StringCompare("Success", $aData[1][4]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Success" &" and Actual Result: " & $aData[1][4] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Success" &" and Actual Result: " & $aData[1][4] & " -- FAILED" & @CRLF)
					Exit
				EndIf

				;CCDType
				ConsoleWrite("Verifying CCDType in cusMedfusionOutboundCcd table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying CCDType in cusMedfusionOutboundCcd table" & @CRLF)

				ConsoleWrite("Expected Result: " & "CVS" &" and Actual Result: " & $aData[1][5] & " -- ")
				If (StringCompare("CVS", $aData[1][5]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "CVS" &" and Actual Result: " & $aData[1][5] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "CVS" &" and Actual Result: " & $aData[1][5] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				Exit
			EndIf

			FileWriteLine($hFileOpen, @CRLF & " STEP 15 -- VERIFY EVENT FOR CVS CREATION IN MUACTIVITYLOG TABLE" & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[11] & $PID & $arrQuery[12] & ";",$aData,$iRows,$iColumns)

				If $iRval = $SQL_OK then
 					;MUActivityLogId - 519
					ConsoleWrite("Verifying event for CVS in MUActivityLog table" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying event for CVS in MUActivityLog table" & @CRLF)
					ConsoleWrite("Expected Result: " & $arrEvent[7] &" and Actual Result: " & $aData[1][0] & " -- ")
					If (StringCompare($arrEvent[7], $aData[1][0]) = 0) Then
						ConsoleWrite("PASSED" & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrEvent[7]  &" and Actual Result: " & $aData[1][0] & " -- PASSED" & @CRLF)
					Else
						ConsoleWrite("FAILED" & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrEvent[7] &" and Actual Result: " & $aData[1][0] & " -- FAILED" & @CRLF)
						Exit
					EndIf

				Else
					ConsoleWrite("ERROR Querying Database...." & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
					Exit
				EndIf
			_SQL_Close()

			FileWriteLine($hFileOpen, @CRLF & " STEP 16 -- VERIFY CVS IN PATIENT PORTAL" & @CRLF)
			$officeVisitJar = StringReplace($currentDir, "officevisit", "jarfiles")  & "\chkCVS.jar"
			$PID = Run(@ComSpec & ' /c java -jar ' & $officeVisitJar & ' CVS ' & $cvsCreationDate &'',"","",$STDOUT_CHILD)
			ConsoleWrite("$PID :" & $PID & @CRLF)
			ProcessWaitClose($PID)
			$output =StdoutRead($PID)

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