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
$commonStepsDir = StringReplace($currentDir , "discreteforms", "commonsteps")
Call("deleteOldLogFiles",$commonStepsDir)
$logPath = $commonStepsDir & "\ProcessLog_DiscreteForms_" & $time & ".txt"
Call("setLogPath",$logpath)
$hFileOpen = Call("openLogFile")
$arrConfig = Call("setConfig")
$arrQuery = Call("openQueryFile")
$arrEvent = Call("openEventFile")
$arrDoctype = Call("openDoctypeFile")

;Update servicesettings
	FileWriteLine($hFileOpen, @CRLF & " STEP 1 -- UPDATE SOLUTIONS FOR DISCRETE FORMS FLOW" & @CRLF)
		Call("connectDatabase",$arrConfig[4],$arrConfig[6],$arrConfig[7],$arrConfig[8])
		Local $aData,$iRows,$iColumns
		$newQuery = StringReplace($arrQuery[25],"(2,3)","(1,3)")
		ConsoleWrite("Executing Query: " & $newQuery & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $newQuery & @CRLF)
		$iRval = _SQL_GetTable2D(-1,$newQuery & ";",$aData,$iRows,$iColumns)
		If $iRval = $SQL_OK then
			If(($aData[1][0] = 0) Or ($aData[2][0] = 0)) Then
				$newQuery = StringReplace($arrQuery[24],"(2,3)","(1,3)")
				ConsoleWrite("Executing Query: " & $newQuery & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $newQuery & @CRLF)
				If _SQL_Execute(-1,$newQuery) = $SQL_ERROR then
					ConsoleWrite("ERROR Updating solutions...." & @CRLF)
					ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Updating solutions...." & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
					Exit
				Else
					ConsoleWrite("Solutions updated" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Solutions updated" & @CRLF)
				EndIf
			Else
				ConsoleWrite("Solutions already enabled" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Solutions already enabled" & @CRLF)
			EndIf
		Else
			ConsoleWrite("ERROR Checking Solutions...." & @CRLF)
			ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Checking Solutions...." & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
			Exit
		EndIf
		FileWriteLine($hFileOpen, @CRLF & " STEP 2 -- UPDATE SERVICESETTINGS FOR DISCRETE FORMS FLOW" & @CRLF)
		ConsoleWrite("Executing Query: " & $arrQuery[53] & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[53] & @CRLF)
		$iRval = _SQL_GetTable2D(-1,$arrQuery[53] & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
				If($aData[1][0] = 1) Then
					ConsoleWrite("Service settings already enabled" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Service Settings already enabled" & @CRLF)
				Else
					ConsoleWrite("Executing Query: " & $arrQuery[54] & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[54] & @CRLF)
					If (_SQL_Execute(-1,$arrQuery[54])) = $SQL_ERROR then
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
		FileWriteLine($hFileOpen, @CRLF & " STEP 3 -- UPDATE FORM DESTINATION TO EMR (PATIENT CHART)" & @CRLF)
		Call("connectDatabase",$arrConfig[4],$arrConfig[5],$arrConfig[7],$arrConfig[8])
		Local $aData,$iRows,$iColumns
		ConsoleWrite("Executing Query: " & $arrQuery[55] & "'" & $arrConfig[41] & "';" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[55] & "'" & $arrConfig[41] & "';" & @CRLF)
		$iRval = _SQL_GetTable2D(-1,$arrQuery[55] & "'" & $arrConfig[41] & "';",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
				If(($aData[1][0] == "EMR") And ($aData[1][1] <> Null) And ($aData[1][2] <> Null)) Then
					ConsoleWrite("Form destination already set" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Form destination already set" & @CRLF)
				Else
					$newQuery = StringReplace($arrQuery[56],"{PVDR}", $arrConfig[2])
					ConsoleWrite("Executing Query: " & $newQuery & "'" & $arrConfig[41] & "';" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $newQuery & "'" & $arrConfig[41] & "';"  & @CRLF)
					If (_SQL_Execute(-1,$newQuery & "'" & $arrConfig[41] & "';" )) = $SQL_ERROR then
						ConsoleWrite("ERROR Updating form destination...." & @CRLF)
						ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Updating form destination...." & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
						Exit
					Else
						ConsoleWrite("Form destination updated...." & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Form destination updated...." & @CRLF)
					EndIf
				EndIf
			Else
				ConsoleWrite("ERROR Checking Form Destination...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Checking Form Destination...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf
If($arrConfig[9] == "Preconditions Check") Then
	ConsoleWrite("Exiting after checking pre-conditions for Discrete Forms Flow...." & @CRLF)
	FileWriteLine($hFileOpen, _NowCalc() & "  -- Exiting after checking pre-conditions for Discrete Forms Flow...." & @CRLF)
	Exit
Else
		;Fill Health Form with medications from patient portal
	FileWriteLine($hFileOpen, @CRLF & " STEP 4 -- FILL HEALTH FORM (DISCRETE FORM) FROM PATIENT PORTAL" & @CRLF)
	ConsoleWrite("Fill Health Form (Discrete Form) from Patient Portal" &@CRLF )
 	$discreteformjar = StringReplace($currentDir, "discreteforms", "jarfiles")  & "\discreteforms.jar"
	$counter = 0
	Do
		If($arrConfig[9]=="Data Generation") Then
			ConsoleWrite("Filling Discrete Form #" & $counter+1 & " of " & $arrConfig[56] & " requests"& @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Filling Discrete Form #" & $counter+1 & " of " & $arrConfig[56] & " requests" & @CRLF)
		Else
			ConsoleWrite("Filling Discrete Form #" &$counter+1 & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Filling Discrete Form #" &$counter+1 & @CRLF)
		EndIf
		$ProcessID = Run(@ComSpec & ' /c java -jar ' & $discreteformjar &'',"","",$STDOUT_CHILD)
		ConsoleWrite("$ProcessID :" & $ProcessID & @CRLF)
		ProcessWaitClose($ProcessID)
		$output =StdoutRead($ProcessID)
			If(StringInStr($output,"PASSED")) Then
				ConsoleWrite("Discrete Form #" & $counter+1 & " completed successfully" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Discrete Form #" & $counter+1 & " completed successfully" & @CRLF)
			Else
				ConsoleWrite("Error in filling discrete form from Patient Portal" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Error in filling discrete form from Patient Portal -- FAILED" & @CRLF)
				Exit
			EndIf
		If($arrConfig[9]=="Data Generation") Then
			$counter +=1
			If($counter = $arrConfig[56]) Then
				ConsoleWrite("Exiting after data generation for Discrete Forms Flow...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Exiting after data generation for Discrete Forms Flow...." & @CRLF)
				Exit
			EndIf
			ConsoleWrite("Wait for 1 min before next Discrete Form" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait for 1 min before next Discrete Form" & @CRLF)
			Sleep(60000)
		Else
			$counter = $arrConfig[56]
		EndIf
	Until $counter = $arrConfig[56]

	;Open CPS -Patient Chart
	FileWriteLine($hFileOpen, @CRLF & " STEP 5 -- LOGIN TO CPS" & @CRLF)
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
		FileWriteLine($hFileOpen, @CRLF & " STEP 7 -- GET PATIENT DETAILS FROM DATABASE" & @CRLF)
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
		ConsoleWrite("Wait for 1.5 min before for Discrete Form to arrive from Medfusion" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait for 1.5 min for Discrete Form to arrive from Medfusion" & @CRLF)
		Sleep(90000)
		FileWriteLine($hFileOpen, @CRLF & " STEP 8 -- VERIFY FORM ARRIVED IN CUSMEDFUSIONDISCRETEFORMFORMS TABLE" & @CRLF)
			$newQuery = StringReplace($arrQuery[57],"{MedfusionMemberId}",$MedfusionMemberId)
			ConsoleWrite("Executing Query: " & $newQuery & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $newQuery & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$newQuery & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
				;Status
				ConsoleWrite("Verifying form status in cusMedfusionDiscreteFormForms table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying form status in cusMedfusionDiscreteFormForms table" & @CRLF)
				Call("assertData", "CCD_Processed", $aData[1][0])

				;RouteToStaff
				ConsoleWrite("Verifying RouteToStaff in cusMedfusionDiscreteFormForms table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying RouteToStaff in cusMedfusionDiscreteFormForms table" & @CRLF)
				Call("assertData", $arrConfig[2], $aData[1][1])
			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf
		FileWriteLine($hFileOpen, @CRLF & " STEP 9 -- VERIFY FORM ARRIVED IN DOCUMENT TABLE" & @CRLF)
			$newQuery = StringReplace($arrQuery[28],"select","select top 3")
			$newQuery = StringReplace($newQuery,"from",", VISDOCID  from")
			ConsoleWrite("Executing Query: " & $newQuery & $PID & $arrQuery[29] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $newQuery & $PID & $arrQuery[29] & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$newQuery & $PID & $arrQuery[29] & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
				For $i = 1 to 3 Step +1

					;Doctype <-> Summary
					ConsoleWrite("Verifying doctype-summary in Document table" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying doctype-summary in Document table" & @CRLF)
					If(StringCompare($arrDoctype[4],$aData[$i][2])<0) Then
						;Doctype = 25
						ConsoleWrite("Verifying doctype in Document table" & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying doctype in Document table" & @CRLF)
						Call("assertData", $arrDoctype[5],$aData[$i][2])

						;Summary - Data Submitted / Form Name
						ConsoleWrite("Verifying document summary in Document table" & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying document summary in Document table" & @CRLF)
						If(StringInStr($aData[$i][1],"Data Submitted by Patient in Portal")) Then
							ConsoleWrite("Document 'Data Submitted by Patient in Portal' created in Document table -- PASSED" & @CRLF)
							FileWriteLine($hFileOpen, _NowCalc() & "  -- Document 'Data Submitted by Patient in Portal' created in Document table -- PASSED" & @CRLF)
							$visDocId_DataSubmitted = $aData[$i][5]
							ConsoleWrite(" VisDocId for Document 'Data Submitted by Patient in Portal' is " & $visDocId_DataSubmitted & @CRLF)
							FileWriteLine($hFileOpen, _NowCalc() & "  -- VisDocId for Document 'Data Submitted by Patient in Portal' is " & $visDocId_DataSubmitted & @CRLF)
							$DocId_DataSubmitted = $aData[$i][0]
							ConsoleWrite(" SDID for Document 'Data Submitted by Patient in Portal' is " & $DocId_DataSubmitted & @CRLF)
							FileWriteLine($hFileOpen, _NowCalc() & "  -- SDID for Document 'Data Submitted by Patient in Portal' is " & $DocId_DataSubmitted & @CRLF)
						ElseIf(StringInStr($aData[$i][1],$arrConfig[41])) Then
							ConsoleWrite("Document with Form Name '" & $arrConfig[41] & "' created in Document table -- PASSED" & @CRLF)
							FileWriteLine($hFileOpen, _NowCalc() & "  -- Document with Form Name '" & $arrConfig[41] & "' created in Document table -- PASSED" & @CRLF)
							$visDocId_FormName = $aData[$i][5]
							ConsoleWrite("VisDocId for '" & $arrConfig[41] & "' is " & $visDocId_FormName & @CRLF)
							FileWriteLine($hFileOpen, _NowCalc() & "  -- VisDocId for '" & $arrConfig[41] & "' is " & $visDocId_FormName & @CRLF)
						Else
							ConsoleWrite("Document for form submitted from Patient Portal not created in Document table -- FAILED" & @CRLF)
							FileWriteLine($hFileOpen, _NowCalc() & "  -- Document for form submitted from Patient Portal not created in Document table -- FAILED" & @CRLF)
							Exit
						EndIf
					ElseIf(StringCompare($arrDoctype[4],$aData[$i][2])=0) Then
						;Doctype = 20
						ConsoleWrite("Verifying doctype in Document table" & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying doctype in Document table" & @CRLF)
						Call("assertData", $arrDoctype[4],$aData[$i][2])

						;Summary
						ConsoleWrite("Verifying document summary in Document table" & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying document summary in Document table" & @CRLF)
						Call("assertData", "Imported CCDA", $aData[$i][1])
						$visDocId_ExtOth = $aData[$i][5]
						ConsoleWrite("VisDocId for 'Ext Oth' is " & $visDocId_ExtOth & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- VisDocId for 'Ext Oth' is " & $visDocId_ExtOth & @CRLF)
					Else
						ConsoleWrite("Doctype for form submitted did not match in Document table -- FAILED" & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Doctype for form submitted did  not match in Document table -- FAILED" & @CRLF)
						Exit
					EndIf
				Next
			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf
		FileWriteLine($hFileOpen, @CRLF & " STEP 10 -- VERIFY EVENT GENERATED FOR HEALTH FORM SUBMISSION FROM PATIENT PORTAL" & @CRLF)
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
		FileWriteLine($hFileOpen, @CRLF & " STEP 11 -- VERIFY FORM ARRIVED IN PATIENT CHART" & @CRLF)
			WinActivate("Chart - NOT FOR PATIENT USE")
			$index = 0
			$found = 0
			$counter = 0
			Local $aCoord = PixelSearch(15, 190, 94, 296, 3245766)
			Sleep(1000)
			MouseMove($aCoord[0]+10,$aCoord[1]+5)
			;Chart Summary in left menu
			MouseMove($aCoord[0]+10,$aCoord[1]+35)
			MouseClick("left",$aCoord[0]+10,$aCoord[1]+35,2)
			Sleep(1000)
			Local $bCoord = PixelSearch($aCoord[0]+45,$aCoord[1]+50,$aCoord[0]+80,$aCoord[1]+150,255)
			If Not @error Then
				ConsoleWrite("Clicking Charts Again"&@CRLF)
				MouseClick("left",$aCoord[0]+10,$aCoord[1]+35,2)
			Else
				Local $bCoord = PixelSearch($aCoord[0]+45,$aCoord[1]+50,$aCoord[0]+80,$aCoord[1]+150,12404009)
				If Not @error Then
					ConsoleWrite("Clicking Charts Again"&@CRLF)
					MouseClick("left",$aCoord[0]+10,$aCoord[1]+35,2)
				EndIf
			EndIf

			;Documents in left menu
			MouseClick("left",$aCoord[0]+10,$aCoord[1]+62)
			Sleep(1000)
			While $found <3
				;Click first document in list
				MouseClick("left",617,319)
				While ($counter > 0)
					Send("{DOWN}")
					$counter = $counter - 1
				WEnd
				$index = $index + 1
				$counter = $index
				$str = WinGetText("Chart - NOT FOR PATIENT USE")
				If(StringInStr($str,$visDocId_DataSubmitted)>0 Or StringInStr($str,$visDocId_ExtOth)>0 Or StringInStr($str,$visDocId_FormName)>0) Then
					If(StringInStr($str, $visDocId_DataSubmitted)>0) Then
						ConsoleWrite("Document 'Data submitted by Patient in Portal' arrived in Patient Chart" & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Document 'Data submitted by Patient in Portal' arrived in Patient Chart" & @CRLF)
					ElseIf(StringInStr($str,$visDocId_FormName)>0) Then
						ConsoleWrite("Document with Form Name arrived in Patient Chart" & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Document with Form Name arrived in Patient Chart" & @CRLF)
					Else
						ConsoleWrite("Document 'Ext Oth' arrived in Patient Chart" & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Document 'Ext Oth' arrived in Patient Chart" & @CRLF)
					EndIf
					$found = $found + 1
				Else
					ConsoleWrite("Document did not arrive..." & @CRLF)
				EndIf
				ConsoleWrite("$found is " & $found & @CRLF)
			WEnd
		ConsoleWrite("All 3 documents arrived in Patient Chart -- PASSED" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- All 3 douments arrived in Patient Chart -- PASSED" & @CRLF)
		Sleep(2000)
		FileWriteLine($hFileOpen, @CRLF & " STEP 12 -- VERIFY MAPPED MEDICATIONS AND ALLERGIES IN FLOWSHEET" & @CRLF)
			Local $aCoord = PixelSearch(15, 190, 94, 296, 3245766)
			Sleep(1000)
			MouseMove($aCoord[0]+10,$aCoord[1]+5)
			;Chart Summary in left menu
			MouseMove($aCoord[0]+10,$aCoord[1]+35)
			MouseClick("left",$aCoord[0]+10,$aCoord[1]+35,2)
			Sleep(1000)
			Local $bCoord = PixelSearch($aCoord[0]+45,$aCoord[1]+50,$aCoord[0]+80,$aCoord[1]+150,255)
			If Not @error Then
				ConsoleWrite("Clicking Charts Again"&@CRLF)
				MouseClick("left",$aCoord[0]+10,$aCoord[1]+35,2)
			Else
				Local $bCoord = PixelSearch($aCoord[0]+45,$aCoord[1]+50,$aCoord[0]+80,$aCoord[1]+150,12404009)
				If Not @error Then
					ConsoleWrite("Clicking Charts Again"&@CRLF)
					MouseClick("left",$aCoord[0]+10,$aCoord[1]+35,2)
				EndIf
			EndIf

			;Flowsheet in left menu
			MouseClick("left",$aCoord[0]+10,$aCoord[1]+90)
			Sleep(1000)
		FileWriteLine($hFileOpen, @CRLF & " STEP 13 -- VERIFY MAPPED MEDICATIONS IN DATABASE" & @CRLF)
			ConsoleWrite("Executing Query: " & $arrQuery[58] & " '" & $arrConfig[45] & "'" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[58] & " '" & $arrConfig[45] & "'" & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[58] & " '" & $arrConfig[45] & "'" & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
			;HDID for Medication Name
				$HDID_MedicationName = $aData[1][0]
				ConsoleWrite("HDID for OBSHEAD mapped to Medication Name is " & $HDID_MedicationName & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- HDID for OBSHEAD mapped to Medication Name is " & $HDID_MedicationName & @CRLF)
			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf
			$newQuery = StringReplace($arrQuery[59],"{HDID}",$HDID_MedicationName)
			$newQuery = StringReplace($newQuery,"{PID}",$PID)
			$newQuery = StringReplace($newQuery,"{SDID}",$DocId_DataSubmitted)
			ConsoleWrite("Executing Query: " & $newQuery & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $newQuery & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$newQuery & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
			;Medication NAme Mapping
				If(StringInStr($aData[1][0],$arrConfig[42])>0) Then
					ConsoleWrite("Medication Name correctly mapped to OBS term " & $arrConfig[45] & " -- PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Medication Name correctly mapped to OBS term " & $arrConfig[45] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("Medication Name not correctly mapped to OBS term " & $arrConfig[45] & " -- FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Medication Name not correctly mapped to OBS term " & $arrConfig[45] & " -- FAILED" & @CRLF)
					Exit
				EndIf
			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf
		FileWriteLine($hFileOpen, @CRLF & " STEP 14 -- VERIFY MAPPED ALLERGIES IN DATABASE" & @CRLF)
			ConsoleWrite("Executing Query: " & $arrQuery[58] & " '" & $arrConfig[46] & "'" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[58] & " '" & $arrConfig[46] & "'" & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[58] & " '" & $arrConfig[46] & "'" & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
			;HDID for Medication Name
				$HDID_Allergies = $aData[1][0]
				ConsoleWrite("HDID for OBSHEAD mapped to Allergies is " & $HDID_Allergies & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- HDID for OBSHEAD mapped to Allergies is " & $HDID_Allergies & @CRLF)
			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf
			$newQuery = StringReplace($arrQuery[59],"{HDID}",$HDID_Allergies)
			$newQuery = StringReplace($newQuery,"{PID}",$PID)
			$newQuery = StringReplace($newQuery,"{SDID}",$DocId_DataSubmitted)
			ConsoleWrite("Executing Query: " & $newQuery & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $newQuery & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$newQuery & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
			;Medication NAme Mapping
				If(StringInStr($aData[1][0],"Peanuts")>0) Then
					ConsoleWrite("Allergies correctly mapped to OBS term " & $arrConfig[46] & " -- PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Allergies correctly mapped to OBS term " & $arrConfig[46] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("Allergies not correctly mapped to OBS term " & $arrConfig[46] & " -- FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Allergies not correctly mapped to OBS term " & $arrConfig[46] & " -- FAILED" & @CRLF)
					Exit
				EndIf
			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf
		FileWriteLine($hFileOpen, @CRLF & " STEP 15 -- IMPORT THE MEDICATIONS SUBMITTED USING CCC BASIC FORM" & @CRLF)
			ConsoleWrite("Open 'CCC Basic' Form" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Open 'CCC Basic' Form" & @CRLF)
			Call("createNewDocument","CCC Basic",$arrConfig[15],$arrConfig[16])
			MouseClick("left",860,580)
			WinWaitActive("Reconciliation")

			Local $oIEEmbed =_IEAttach("Reconciliation","embedded",1)
			Sleep(10000)
			If ( IsObj($oIEEmbed) ) Then
				ConsoleWrite("Attached successfully to Reconciliation form" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Attached successfully to Reconciliation form" & @CRLF)

				ConsoleWrite('@@ Debug(' & @ScriptLineNumber & ') : $oIEEmbed = ' & $oIEEmbed & @CRLF & '>Error code: ' & @error & '    Extended code: 0x' & Hex(@extended) & @CRLF) ;### Debug Console
				ConsoleWrite("Embedded IE Url " &  _IEPropertyGet($oIEEmbed, "locationurl") &@CRLF )
				Sleep(1000)

				$mainTable = _IETableGetCollection($oIEEmbed,0)
				$Row1 = _IETagNameGetCollection($mainTable,"tr",0)

				;Get total  no. of forpending to review
				$oAnchors = _IETagNameGetCollection($Row1,"li")
					$a = 0
					For $oAnchor in $oAnchors
						$a = $a + 1
					Next
				ConsoleWrite("Number of forms pending to review-->" &$a & @CRLF)

				;Get hold of 'Mark Reiewed' button
				$Column2 = _IETagNameGetCollection($Row1,"td",1)
				$MarkReviewedBtn = _IEGetObjById($Column2,"btn_encounterform_mainform_markreviewed")

				;mark all forms as reviewed except the latest one
				For $i=1 to $a-1 Step +1
					_IEAction($MarkReviewedBtn,"click")
				Next
				Sleep(1000)

				;navigate to allergy tab
				$Row2 = _IETagNameGetCollection($mainTable,"tr",1)
				$allergyTabName = _IETagNameGetCollection($Row2,"a",1)
				_IEAction($allergyTabName,"click")

				$Row3 = _IETagNameGetCollection($mainTable,"tr",2)
				$allergyTabContent = _IEGetObjById($Row3,"tab_body_allergies")
				$allergyAddToListBtn = _IEGetObjById($allergyTabContent,"btn_encounterform_mainformallergies_addtolist")

				$allergyTR = _IETagNameGetCollection($allergyTabContent,"tr",2)
				$allergyTable = _IEGetObjById($allergyTR,"imported-allergy-grid")
				$allergyBody = _IETagNameGetCollection($allergyTable,"tr",1)
				$strAllergy = _IEPropertyGet($allergyBody,"innertext")

				If (StringInStr($strAllergy,"Peanuts")>0) Then
					ConsoleWrite("Peanuts found" &@CRLF)
					$allergyChkBox = _IETagNameGetCollection($allergyBody,"input",0)
					_IEAction($allergyChkBox,"click")
					Sleep(1000)
					_IEAction($allergyAddToListBtn,"click")

				Else
					ConsoleWrite("Allergy not imported...." & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Allergy not imported...." & @CRLF)
					Exit
				EndIf

				;navigate to Medications tab
				$medicationTabName = _IETagNameGetCollection($Row2,"a",2)
				_IEAction($medicationTabName,"click")

				$medicationTabContent = _IEGetObjById($Row3,"tab_body_medications")
				$medicationAddToListBtn = _IEGetObjById($medicationTabContent,"btn_encounterform_mainformmedications_addtolist")

				$medicationTR = _IETagNameGetCollection($medicationTabContent,"tr",2)
				$medicationTable =_IEGetObjById($medicationTR,"external-medications-grid")
				$medicationBody = _IETagNameGetCollection($medicationTable,"tr",1)
				$strMedication = _IEPropertyGet($medicationBody,"innertext")

				If (StringInStr($strMedication,"Crestor")>0) Then
					ConsoleWrite("Medication found" & @CRLF)
					$medicationChkbox = _IETagNameGetCollection($medicationBody,"input",0)
					_IEAction($medicationChkbox,"click")
					Sleep(1000)
					_IEAction($medicationAddToListBtn,"click")

				Else
					ConsoleWrite("Medication not imported...." & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Medication not imported...." & @CRLF)
					Exit
				EndIf

				WinClose("Reconciliation")
				ConsoleWrite("Discrete Forms Flow -- SUCCESSFULLY COMPLETED...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- DISCRETE FORMS FLOW -- SUCCESSFULLY COMPLETED...." & @CRLF)

			Else
				ConsoleWrite("ERROR Attaching Reconciliation form...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Attaching Reconciliation form...." & @CRLF)
				Exit
			EndIf




	Else
		ConsoleWrite("ERROR Attaching GE CPS App...." & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Attaching GE CPS App...." & @CRLF)
	EndIf
EndIf