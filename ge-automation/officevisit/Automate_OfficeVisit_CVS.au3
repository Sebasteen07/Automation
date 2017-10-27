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
					FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Updating service settings...." & @CRLF)
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
			FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Checking Service Settings...." & @CRLF)

			Exit
		EndIf

		_SQL_Close()

	;Open CPS -Patient Chart
	FileWriteLine($hFileOpen, @CRLF & " STEP 2 -- LOGIN TO CPS" & @CRLF)
	ConsoleWrite("Start the CPS client" &@CRLF )
	FileWriteLine($hFileOpen, _NowCalc()  &" -- Start the CPS client" &@CRLF)
	Call("startCPS",$arrConfig[1], $arrConfig[2], $arrConfig[3])

	WinActivate("Centricity Practice Solution")
	If(WinActive("Centricity Practice Solution")) Then
		FileWriteLine($hFileOpen, @CRLF & " STEP 3 -- OPEN PATIENT CHART" & @CRLF)
		ConsoleWrite("Open Patient Chart" &@CRLF )
		Call("openPatientChart",$arrConfig[14],$arrConfig[15])
		Sleep(8000)
		WinWaitActive("Chart - NOT FOR PATIENT USE")

		FileWriteLine($hFileOpen, @CRLF & " STEP 4 -- CREATE A NEW OFFICE VISIT FOR THE PATIENT" & @CRLF)
		If(WinActive("Chart - NOT FOR PATIENT USE")) Then
			$counter = 0
			Do
				Call("createNewDocument",$arrConfig[17],$arrConfig[14],$arrConfig[15])
				Call("createAsthmaVisit",$arrConfig[14],$arrConfig[15])

				If($arrConfig[31]=="Yes") Then
					$counter +=1
					If($counter = $arrConfig[32]) Then
						Exit
					EndIf

				Else
					$counter = $arrConfig[32]
				EndIf
			Sleep(1000)
			Until $counter = $arrConfig[32]

			ConsoleWrite("Office Visit created successfully...." & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Office Visit created successfully...." & @CRLF)

		FileWriteLine($hFileOpen, @CRLF & " STEP 5 -- GET PATIENT DETAILS FROM DATABASE" & @CRLF)
		Call("connectDatabase",$arrConfig[4],$arrConfig[5],$arrConfig[7],$arrConfig[8])

			Local $aData,$iRows,$iColumns	;Variables to store the array data in to and the row count and the column count
		;Get PatientProfileId,PID
			$newQuery = StringReplace($arrQuery[6],"PatientProfileId =","First = '" & $arrConfig[14] & "' and Last = '" & $arrConfig[15] & "'")
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

				ConsoleWrite("Expected Result: " & $arrConfig[17] &" and Actual Result: " & $aData[1][1] & " -- ")
				If (StringCompare($arrConfig[17], $aData[1][1]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrConfig[17] &" and Actual Result: " & $aData[1][1] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrConfig[17] &" and Actual Result: " & $aData[1][1] & " -- FAILED" & @CRLF)
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
				Call("createCVS",$arrConfig[14],$arrConfig[15])
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

;~ 	ConsoleWrite(@CRLF & "Reading configuration varaibles"&@CRLF)
;~ 	Local $arrConfig

;~ 	If Not _FileReadToArray($Path, $arrConfig, 0) Then
;~ 		ConsoleWrite("Exiting CCD-CVS Flow"&@CRLF)
;~ 		Exit

;~ 	Else
;~ 		_FileReadToArray($path,$arrConfig,Default)

;~ 		Local $iRows = UBound($arrConfig, $UBOUND_ROWS) ; Number of config rows.
;~ 		Local $iCols = UBound($arrConfig, $UBOUND_COLUMNS) ; Number of config cols.

;~ 		$Cpsurl = $arrConfig[1]
;~ 		$Username = $arrConfig[2]
;~ 		$Password = $arrConfig[3]
;~ 		$PatientName = $arrConfig[4]
;~ 		$PatientEmail = $arrConfig[5]
;~ 		$PatientCount = $arrConfig[6]
;~ 		$PatientPortal = $arrConfig[7]
;~ 		$PatientFirstName =$arrConfig[8]
;~ 		$PatientLastName= $arrConfig[9]

;~ 		ConsoleWrite("CPS URL: " & $Cpsurl & @CRLF)
;~ 		ConsoleWrite("CPS UserName: " & $Username & @CRLF)
;~ 		ConsoleWrite("CPS Password: " & $Password & @CRLF)
;~ 		ConsoleWrite("Patient First Name append to: " & $PatientName & @CRLF)
;~ 		ConsoleWrite("Patient Email: " & $PatientEmail & @CRLF)
;~ 		ConsoleWrite("Patient Count: " & $PatientCount & @CRLF)
;~ 		ConsoleWrite("Patient Portal: " & $PatientPortal & @CRLF)
;~ 		ConsoleWrite("Patient First name: " & $PatientFirstName & @CRLF)
;~ 		ConsoleWrite("Patient Last Name: " & $PatientLastName & @CRLF)

;~ 		ConsoleWrite("Configuration variables set"&@CRLF)
;~ 	EndIf
;~ ;Opening Log file
;~ 	Local $hFileOpen = FileOpen($logPath, $FO_OVERWRITE)
;~     If $hFileOpen = -1 Then
;~         MsgBox($MB_SYSTEMMODAL, "", "An error occurred while writing process log file.")
;~         Exit
;~     EndIf

;~ ; Killing all earlier IE sessions
;~ 	RunWait('taskkill /F /IM "iexplore.exe"')
;~ 	RunWait('taskkill /F /IM "AutoIt32.exe"')

;~ ;Initiate GE CPS Application
;~ 	_IECreate ($Cpsurl)
;~ 	WinWait ($title)
;~ 	WinActivate($title)
;~ 	WinWaitActive ($title)

;~ 	Sleep (3000)

;~ 	FileWriteLine($hFileOpen, @CRLF&@CRLF&@CRLF  &"STEP 1 -- LOGIN PROCESS" & @CRLF)
;~ 	; Sending user name and password
;~ 	ControlSend($title, "", $edtId, $Username) ; UserName read from config file
;~ 	ControlSend($title, "", $edtPw, $Password) ; Password read from config file
;~ 	ConsoleWrite("Sent Login credentials to GE CPS" &@CRLF )
;~ 	FileWriteLine($hFileOpen, _NowCalc()  &" *** Sent Login credentials to GE CPS ***" &@CRLF)
;~ 	Sleep (5000)

;~ 	; Clicking on Login button
;~ 	ControlClick($title, "", $btnLogin)
;~ 	ConsoleWrite("Clicked on Login button" &@CRLF )
;~ 	FileWriteLine($hFileOpen, _NowCalc() & "  -- Clicked on Login button" &@CRLF )
;~ 	Sleep (5000)

;~ 	ControlClick($title, "", "[CLASS:Button; INSTANCE:3]")
;~ 	ConsoleWrite("Clicked on Loggedin warning message Yes" & @CRLF)
;~ 	FileWriteLine($hFileOpen, _NowCalc() & "  -- Clicked on Loggedin warning message Yes" & @CRLF)
;~ 	Sleep (5000)

;~ 	;ConsoleWrite("Embedded IE Url " &  _IEPropertyGet($oIEEmbed, "locationurl") &@CRLF )
;~ 	ConsoleWrite("GE CPS is Running" &@CRLF )
;~ 	FileWriteLine($hFileOpen, _NowCalc() & "  -- GE CPS is Running" &@CRLF )
;~ 	Sleep(8000)

;~ 	Local $aList = WinList($title)
;~ 	;_ArrayDisplay($aList)
;~ 	; Loop through the array displaying only visable windows with a title.

;~ 	   ; Loop through the array displaying only visable windows with a title.

;~    Local $iRows = UBound($aList, $UBOUND_ROWS)
;~    ConsoleWrite("Active window(s) " & $iRows & @CRLF)
;~    FileWriteLine($hFileOpen, _NowCalc() & "  -- Active window(s) " & $iRows & @CRLF)

;~    For $i = 1 To $aList[0][0]
;~         If $aList[$i][0] <> "" And BitAND(WinGetState($aList[$i][1]), 2) Then
;~             ;MsgBox($MB_SYSTEMMODAL, "", "Title: " & $aList[$i][0] & @CRLF & "Handle: " & $aList[$i][1])
;~ 			ConsoleWrite("GE CPS active window title - "& $i & " - " & $aList[$i][0] &  " Handle: " & $aList[$i][1]&@CRLF )
;~         EndIf
;~    Next

;~    $iRows = $aList[0][0]
;~    ConsoleWrite("Active windows - " & $iRows & @CRLF)

;~    Sleep(5000)
;~    ;_ArrayDisplay($aList)
;~    sleep(5000)

;~    ;GE CPS loggedin user warning
;~    ControlClick($title, "", $btnMsgYes)
;~    ConsoleWrite("YES click sent to Message Box" & @CRLF)
;~    FileWriteLine($hFileOpen, _NowCalc() & "  -- YES click sent to Message Box" & @CRLF)
;~    Sleep(5000)

;~    ControlClick($title, "", $btnButton1)
;~    ConsoleWrite("First OK click sent to Message Box" & @CRLF)
;~    FileWriteLine($hFileOpen, _NowCalc() & "  -- First OK click sent to Message Box" & @CRLF)
;~    Sleep(5000)


;~    $iRows = $aList[0][0]
;~    While $iRows > 1
;~ 	  ;ControlClick($title, "", $btnMsgOk)
;~ 	  ;ControlSend($title,"",$btnMsgOk,"{Enter}")
;~ 	  ConsoleWrite("WHILE START Active windows - " & $iRows & @CRLF)

;~ 	  Send("{Enter}")
;~ 	  $aList = WinList($title)
;~ 	  ;$iRows = UBound($aList, $UBOUND_ROWS)
;~ 	  ConsoleWrite("OK click sent to Message Box - Active window " & $iRows & @CRLF)
;~ 	  FileWriteLine($hFileOpen, _NowCalc() & "  --  OK click sent to Message Box - Active window " & $iRows & @CRLF)
;~ 	  Sleep(10000)

;~ 	  ;Check wheather message boxes are coming from GE CPS
;~ 	  $aList = WinList($title)
;~ 	  $iRows = $aList[0][0]

;~ 	  ;Sleep(5000)

;~ 	  If ( $iRows = 0 ) Then
;~ 		 ExitLoop
;~ 	  EndIf

;~ 	  ConsoleWrite("WHILE END Active windows - " & $iRows & @CRLF)
;~    WEnd


;~    $oWndCPS = WinActive($title)
;~    ConsoleWrite("GE CPS active window title - " & $oWndCPS &@CRLF )
;~    ;_ArrayDisplay($aList)

;~    RunWait('taskkill /F /IM "iexplore.exe"')
;~    ConsoleWrite("IE Process Kill which initiated GE CPS" &@CRLF )
;~    FileWriteLine($hFileOpen, _NowCalc() & "  -- IE Process Kill which initiated GE CPS" &@CRLF )

;~    Sleep(10000)

;~ 	;;;Start : Attaching to IE instance when it runs as a embedded control in Windows Forms Application ;;;
;~ 	If Not(ProcessExists("iexplorer.exe")) Then
;~ 		FileWriteLine($hFileOpen, @CRLF & "STEP 2 -- REGISTRATION PROCESS" & @CRLF)
;~ 		Local $oIEEmbed =_IEAttach("Centricity Practice Solution","embedded",1)

;~ 		sleep(1000)
;~ 		ConsoleWrite("Activating GE CPS App" &@CRLF)
;~ 		FileWriteLine($hFileOpen, _NowCalc() & "  -- Activating GE CPS App" &@CRLF)
;~ 		WinActivate("Centricity Practice Solution")
;~ 	    sleep(10000)

;~ 		if ( IsObj($oIEEmbed) ) Then
;~ 			ConsoleWrite("Attached successfully to IE instance running under GE app" & @CRLF)
;~ 			FileWriteLine($hFileOpen, _NowCalc() & "  -- Attached successfully to IE instance running under GE app" & @CRLF)

;~ 			ConsoleWrite('@@ Debug(' & @ScriptLineNumber & ') : $oIEEmbed = ' & $oIEEmbed & @CRLF & '>Error code: ' & @error & '    Extended code: 0x' & Hex(@extended) & @CRLF) ;### Debug Console
;~ 			ConsoleWrite("Embedded IE Url " &  _IEPropertyGet($oIEEmbed, "locationurl") &@CRLF )
;~ 			sleep(1000)

;~ 			Local $oDoc = _IEBodyReadHTML($oIEEmbed)
;~ 			Local $oLinkCollection = _IELinkGetCollection($oIEEmbed)

;~ 			ConsoleWrite("Finding links collection" & @CRLF)
;~ 			If ( IsObj($oLinkCollection) ) Then
;~ 				;ConsoleWrite( @CRLF & "Links Collection --> "  &  IsObj($oLinkCollection) & " -------- " & @CRLF )
;~ 				;ConsoleWrite( @CRLF & "Button Count --> "  &  $oBtnCollection.count() & " -------- " & @CRLF )
;~ 				$lCount = 0
;~ 					For $oLink in $oLinkCollection
;~ 					;ConsoleWrite(  "Link " & $lCount & " --> "  & $oLink & " -------- " & @CRLF )
;~ 					$lCount = $lCount + 1
;~ 					Next
;~ 				;_ArrayDisplay($oLinkCollection)
;~ 				ConsoleWrite("For loop finished for Link Collection" & @CRLF )
;~ 				ConsoleWrite("Links found - " & $lCount & @CRLF)
;~ 			EndIf

;~ 			ConsoleWrite("Clicking CHART link of GE CPS App...." & @CRLF)
;~ 			FileWriteLine($hFileOpen, _NowCalc() & "  -- Clicking CHART link of GE CPS App" & @CRLF)
;~ 			Sleep(5000)
;~ 			_IELinkClickByIndex($oIEEmbed,0)

;~ 			Local $chartWnd = WinWaitActive("Chart Desktop")
;~ 			ConsoleWrite("Active Window is " & WinGetTitle("[ACTIVE]") & @CRLF)
;~ 			WinSetState($chartWnd,"",@SW_MAXIMIZE)

;~ 			If($chartWnd) Then
;~ 				ConsoleWrite("Click on FIND PATIENT"& @CRLF)
;~ 				FileWriteLine($hFileOpen, _NowCalc() & "  -- Click on FIND PATIENT"& @CRLF)
;~ 				MouseClick("left",33,68)
;~ 				$findPatientWnd = WinWaitActive("Find Patient")

;~ 				If ($findPatientWnd) Then
;~ 					ControlFocus($findPatientWnd,"","[CLASS:Edit; INSTANCE:6]")
;~ 					ControlSetText($findPatientWnd,"","[CLASS:Edit; INSTANCE:6]",$PatientLastName &", " & $PatientFirstName)
;~ 					ConsoleWrite("PatientName" & @CRLF)
;~ 					Sleep(15000)
;~ 					ControlFocus($findPatientWnd,"","[CLASS:Button; INSTANCE:6]")
;~ 					ControlClick($findPatientWnd,"","[CLASS:Button; INSTANCE:6]")
;~ 					ConsoleWrite("Search Clicked" &@CRLF)
;~ 					Sleep(10000)
;~ 					ControlFocus($findPatientWnd,"","[CLASS:Button; INSTANCE:1]")
;~ 					ControlClick($findPatientWnd,"","[CLASS:Button; INSTANCE:1]")
;~ 					ConsoleWrite("OK LCicked" & @CRLF)
;~ 					Sleep(8000)

;~ 						$patientChartWnd = WinWaitActive("Chart - NOT FOR PATIENT USE")
;~ 						If ($patientChartWnd) Then
;~ 							;ControlClick($patientChartWnd,"","!D")
;~ 							;Send("!D")
;~ 							ConsoleWrite("Check new document" & @CRLF)









;~ 						Else
;~ 							ConsoleWrite("ERROR Opening Patient Chart...." & @CRLF)
;~ 							FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Opening Patient Chart...." & @CRLF)
;~ 						EndIf

;~ 				Else
;~ 					ConsoleWrite("ERROR Inactive Find Patient Window...." & @CRLF)
;~ 					FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Inactive Find Patient Window...." & @CRLF)
;~ 				EndIf

;~ 			Else
;~ 				ConsoleWrite("ERROR Opening Chart Link...." & @CRLF)
;~ 				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Opening Chart Link...." & @CRLF)
;~ 			EndIf

;~ 		Else
;~ 			ConsoleWrite("ERROR Attaching IE Process Within GE CPS App...." & @CRLF)
;~ 			FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Attaching IE Process Within GE CPS App...." & @CRLF)
;~ 		EndIf
;~ 	EndIf
;~    ;;;End : Attaching to IE instance when it runs as a embedded control in Windows Forms Application ;;;

