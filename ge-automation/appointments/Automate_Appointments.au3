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
$commonStepsDir = StringReplace($currentDir , "appointments", "commonsteps")
Call("deleteOldLogFiles",$commonStepsDir)
$logPath = $commonStepsDir & "\ProcessLog_Appointments_" & $time & ".txt"
Call("setLogPath",$logpath)
$hFileOpen = Call("openLogFile")

$arrConfig = Call("setConfig")
$arrQuery = Call("openQueryFile")
$arrEvent = Call("openEventFile")
$arrDoctype = Call("openDoctypeFile")

;Update servicesettings
	FileWriteLine($hFileOpen, @CRLF & " STEP 1 -- UPDATE SERVICESETTINGS FOR APPOINTMENTS FLOW" & @CRLF)
	Call("connectDatabase",$arrConfig[4],$arrConfig[6],$arrConfig[7],$arrConfig[8])

			Local $aData,$iRows,$iColumns
			ConsoleWrite("Executing Query: " & $arrQuery[40] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[40] & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[40] & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
				If(($aData[1][0] = 1) And ($aData[2][0] = 1)) Then
					ConsoleWrite("Service settings already enabled" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Service Settings already enabled" & @CRLF)

				Else
					ConsoleWrite("Executing Query: " & $arrQuery[41] & @CRLF & $arrQuery[42] & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[41] & @CRLF & $arrQuery[42] & @CRLF )
					If (_SQL_Execute(-1,$arrQuery[41]) Or _SQL_Execute(-1,$arrQuery[42])) = $SQL_ERROR then
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
	ConsoleWrite("Exiting after checking pre-conditions for Appointments Flow...." & @CRLF)
	FileWriteLine($hFileOpen, _NowCalc() & "  -- Exiting after checking pre-conditions for Appointments Flow...." & @CRLF)
	Exit

Else
	;---------------------------------------------------------------
	;Create Appointment
	FileWriteLine($hFileOpen, @CRLF & " STEP 2 -- CREATE APPOINTMENT FROM PATIENT PORTAL" & @CRLF)
	ConsoleWrite("Create appointments from Patient Portal" &@CRLF )

 	$createAppointmentJar = StringReplace($currentDir, "appointments", "jarfiles")  & "\createAppointments.jar"
	$counter = 0
	Do
		If($arrConfig[9]=="Data Generation") Then
			ConsoleWrite("Creating Appointment #" & $counter+1 & " of " & $arrConfig[39] & " appointments"& @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Creating Appointment #" & $counter+1 & " of " & $arrConfig[39] & " appointments" & @CRLF)
		Else
			ConsoleWrite("Creating Appointment #" &$counter+1 & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Creating Appointment #" &$counter+1 & @CRLF)
		EndIf

		$ProcessID = Run(@ComSpec & ' /c java -jar ' & $createAppointmentJar &'',"","",$STDOUT_CHILD)
		ConsoleWrite("$ProcessID :" & $ProcessID & @CRLF)
		ProcessWaitClose($ProcessID)
		$output =StdoutRead($ProcessID)

			If(StringInStr($output,"PASSED")) Then
				ConsoleWrite("Appointment #" & $counter+1 & " created successfully" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Appointment #" & $counter+1 & " created successfully" & @CRLF)
			Else
				ConsoleWrite("Error in Appointment creation from Patient Portal" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Error in Appointment creation from Patient Portal -- FAILED" & @CRLF)
				Exit
			EndIf

		If($arrConfig[9]=="Data Generation") Then
			$counter +=1
			Sleep(60000)
			If($counter = $arrConfig[39]) Then
				ConsoleWrite("Exiting after data generation for Appointments Flow...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Exiting after data generation for Appointments Flow...." & @CRLF)
				Exit
			EndIf

		Else
			$counter = $arrConfig[39]
		EndIf
	Until $counter = $arrConfig[39]
;----------------------------------------------------
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

	ConsoleWrite("Wait for appointment to arrive from Medfusion" & @CRLF)
	FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait for appointment to arrive from Medfusion"& @CRLF)
	Sleep(240000)
	FileWriteLine($hFileOpen, @CRLF & " STEP 4 -- VERIFY APPOINTMENT DETAILS IN DATABASE" & @CRLF)

			ConsoleWrite("Executing Query: " & $arrQuery[34] & $PatientProfileId & $arrQuery[35] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[34] & $PatientProfileId & $arrQuery[35]& @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[34] & $PatientProfileId & $arrQuery[35] & ";",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
			;CommServiceType
				ConsoleWrite("Verifying CommServiceType in cusMedfusionCommIncoming table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying CommServiceType in cusMedfusionCommIncoming table" & @CRLF)
				ConsoleWrite("Expected Result: " & "Appointment Request" &" and Actual Result: " & $aData[1][0] & " -- ")
				If (StringCompare("Appointment Request", $aData[1][0]) = 0)  Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Appointment Request" &" and Actual Result: " & $aData[1][0] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Appointment Request" &" and Actual Result: " & $aData[1][0] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;EMRDocumentType
				ConsoleWrite("Verifying EMRDocumentType in cusMedfusionCommIncoming table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying EMRDocumentType in cusMedfusionCommIncoming table" & @CRLF)
				ConsoleWrite("Expected Result: " & "ApptReq" &" and Actual Result: " & $aData[1][1] & " -- ")
				If (StringCompare("ApptReq", $aData[1][1]) = 0)  Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "ApptReq" &" and Actual Result: " & $aData[1][1] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "ApptReq" &" and Actual Result: " & $aData[1][1] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;Subject
				ConsoleWrite("Verifying Subject in cusMedfusionCommIncoming table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Subject in cusMedfusionCommIncoming table" & @CRLF)
				ConsoleWrite("Expected Result: " & "Appointment Request" &" and Actual Result: " & $aData[1][3] & " -- ")
				If (StringCompare("Appointment Request", $aData[1][3]) = 0)  Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Appointment Request" &" and Actual Result: " & $aData[1][3] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Appointment Request" &" and Actual Result: " & $aData[1][3] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

		Sleep(120000)

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

	FileWriteLine($hFileOpen, @CRLF & " STEP 7 -- VERIFY EVENT FOR APPOINTMENT CREATED BY PATIENT" & @CRLF)
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
		_SQL_Close()

	ConsoleWrite("Wait for appointment to arrive in Patient Chart" & @CRLF)
	FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait for appointment to arrive in Patient Chart"& @CRLF)

	FileWriteLine($hFileOpen, @CRLF & " STEP 8 -- VERIFY APPOINTMENT ARRIVED IN PATIENT CHART" & @CRLF)
		$result = Call("verifyDocumentInPatientChart","ApptReq")
			If($result == "PASSED") Then
				ConsoleWrite("Appointment arrived in Patient Chart" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Appointment arrived in Patient Chart -- PASSED" & @CRLF)

			Else
				ConsoleWrite("Appointment did not arrive in Patient Chart" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Appointment did not arrive in Patient Chart -- FAILED" & @CRLF)
				Exit
			EndIf

			WinActivate("Chart - NOT FOR PATIENT USE")
			MouseClick("left",617,319)
			$str = WinGetText("Chart - NOT FOR PATIENT USE")
			;ConsoleWrite($str & @CRLF)
			$arrAlert = _StringBetween($str,"Flags(",")")
			;ConsoleWrite("Chk Here "&$arrAlert[0]& @CRLF)
			$intAlert = Int($arrAlert[0])
			ConsoleWrite("Initial Alert count: " & $intAlert & @CRLF)

	FileWriteLine($hFileOpen, @CRLF & " STEP 9 -- UPDATE SERVICE SETTINGS TO VIEW APPOINTMENT AS ALERT" & @CRLF)
		ConsoleWrite("Update servicesettings to view appointment as Alert" & @CRLF)
		Call("connectDatabase",$arrConfig[4],$arrConfig[6],$arrConfig[7],$arrConfig[8])
		Local $aData,$iRows,$iColumns

			$newQuery = StringReplace($arrQuery[42],"'1'","'0'")
			ConsoleWrite("Executing Query: " & $newQuery & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $newQuery & @CRLF )
					If (_SQL_Execute(-1,$newQuery)) = $SQL_ERROR then
						ConsoleWrite("ERROR Updating service settings...." & @CRLF)
						ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Updating service settings...." & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
						Exit

					Else
						ConsoleWrite("Service settings updated...." & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Service Settings updated...." & @CRLF)
					EndIf
		_SQL_Close()

	FileWriteLine($hFileOpen, @CRLF & " STEP 10 -- CREATE APPOINTMENT FROM PATIENT PORTAL" & @CRLF)
		ConsoleWrite("Creating New Appointment to view as Alert" & @CRLF)
		$ProcessID = Run(@ComSpec & ' /c java -jar ' & $createAppointmentJar &'',"","",$STDOUT_CHILD)
		ConsoleWrite("$ProcessID :" & $ProcessID & @CRLF)
		ProcessWaitClose($ProcessID)
		$output =StdoutRead($ProcessID)

			If(StringInStr($output,"PASSED")) Then
				ConsoleWrite("Appointment created successfully" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Appointment created successfully" & @CRLF)
			Else
				ConsoleWrite("Error in Appointment creation from Patient Portal" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Error in Appointment creation from Patient Portal -- FAILED" & @CRLF)
				Exit
			EndIf

		ConsoleWrite("Wait for appointment to arrive" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait for appointment to arrive"& @CRLF)
		Sleep(240000)

	FileWriteLine($hFileOpen, @CRLF & " STEP 11 -- VERIFY APPOINTMENT ARRIVED AS ALERT" & @CRLF)
			Call("openPatientChart","","")
			Call("openPatientChart",$arrConfig[15],$arrConfig[16])

			$result = Call("verifyDocumentInPatientChart","ApptReq")
			If($result == "PASSED") Then
				ConsoleWrite("Appointment arrived in Patient Chart" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Appointment arrived in Patient Chart -- PASSED" & @CRLF)

				;Alert Count
				WinActivate("Chart - NOT FOR PATIENT USE")
				MouseClick("left",617,319)
				$str = WinGetText("Chart - NOT FOR PATIENT USE")
				;ConsoleWrite($str & @CRLF)
				$arrNewAlert = _StringBetween($str,"Flags(",")")
				;ConsoleWrite("Chk Here "&$arrNewAlert[0]& @CRLF)
				$intNewAlert = Int($arrNewAlert[0])
				ConsoleWrite("New Alert count: " & $intNewAlert & @CRLF)

				Local $aCoord = PixelSearch(15, 190, 94, 296, 3245766)
				Sleep(1000)
				MouseMove($aCoord[0]+10,$aCoord[1]+5)

				;Chart Summary in left menu
				MouseMove($aCoord[0]+10,$aCoord[1]+35)
				MouseClick("left",$aCoord[0]+10,$aCoord[1]+35,2)
				Sleep(1000)

				Local $bCoord = PixelSearch($aCoord[0]+45,$aCoord[1]+50,$aCoord[0]+80,$aCoord[1]+150,255)
				If @error Then
					ConsoleWrite("Clicking Again"&@CRLF)
					MouseClick("left",$aCoord[0]+10,$aCoord[1]+35,2)
				EndIf
				;open Alerts
				MouseMove($aCoord[0]+10,$aCoord[1]+140)
				MouseClick("left",$aCoord[0]+10,$aCoord[1]+140)

					If($intNewAlert > $intAlert)  Then
						ConsoleWrite("Appointment arrived as alert" & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Appointment arrived as alert" & @CRLF)

					Else
						ConsoleWrite("Appointment did not arrive" & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Appointment did not arrive" & @CRLF)
						Exit
					EndIf

					ConsoleWrite("Appointments Flow -- SUCCESSFULLY COMPLETED...." & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- APPOINTMENTS FLOW -- SUCCESSFULLY COMPLETED...." & @CRLF)

			Else
				ConsoleWrite("Appointment did not arrive" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Appointment did not arrive -- FAILED" & @CRLF)
				Exit
			EndIf

	Else
		ConsoleWrite("ERROR Attaching GE CPS App...." & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Attaching GE CPS App...." & @CRLF)
	EndIf
EndIf