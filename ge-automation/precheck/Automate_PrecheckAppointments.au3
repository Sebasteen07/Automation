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
$commonStepsDir = StringReplace($currentDir , "precheck", "commonsteps")
Call("deleteOldLogFiles",$commonStepsDir)
$logPath = $commonStepsDir & "\ProcessLog_PrecheckAppointments_" & $time & ".txt"
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

$precheckFacilityName = Call("readConfig","Precheck Facility Name")
$precheckResource = Call("readConfig","Precheck Resource Name")
$precheckApptDt = Call("readConfig","Precheck Appointment Date(mm/dd/yyyy)")
$precheckApptStartTime = Call("readConfig","Precheck Appointment Start Time")
$precheckApptStopTime = Call("readConfig","Precheck Appointment Stop Time(As per slot)")
$precheckResponsibleProvider = Call("readConfig","Precheck Responsible provider")

;TODO -- Update Servicesettings - after implementation
	FileWriteLine($hFileOpen, @CRLF & " STEP 1 -- UPDATE SERVICESETTINGS FOR PRECHECK APPOINTMENTS FLOW" & @CRLF)
		Call("connectDatabase",$SQLServer,$centralDb,$dbUser,$dbPassword)
		Local $aData,$iRows,$iColumns

		_SQL_Close()


If($runFlag == "Preconditions Check") Then
	ConsoleWrite("Exiting after checking pre-conditions for Precheck Appointments Flow...." & @CRLF)
	FileWriteLine($hFileOpen, _NowCalc() & "  -- Exiting after checking pre-conditions for Precheck Appointments Flow...." & @CRLF)
	Exit
Else
	FileWriteLine($hFileOpen, @CRLF & " STEP 2 -- START CPS AND OPEN SCHEDULING LINK" & @CRLF)
	ConsoleWrite("Start CPS and open Scheduling Link" &@CRLF )
		;Call("startCPS",$CPSURL, $CPSLogin, $CPSPassword)
		WinActivate("Centricity Practice Solution")

		If(WinActive("Centricity Practice Solution")) Then

			FileWriteLine($hFileOpen, @CRLF & " STEP 3 -- SCHEDULE A FUTURE DATE APPOINTMENT FROM CPS" & @CRLF)
			ConsoleWrite("Schedule a future date appointment from CPS" &@CRLF )

			$counter = 0
			Do
				If($runFlag=="Data Generation") Then
					ConsoleWrite("Schedule apointment #" & $counter+1 & " of " & $dataGenerationCounter & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Schedule appointment #" & $counter+1 & " of " & $dataGenerationCounter & @CRLF)
				Else
					ConsoleWrite("Schedule appointment #" &$counter+1 & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Schedule appointment #" &$counter+1 & @CRLF)
				EndIf

				Call("scheduleAppointment",$patientFirstName,$patientLastName,$precheckFacilityName,$precheckResource,$precheckApptDt,$precheckResponsibleProvider,$precheckApptStartTime,$precheckApptStopTime)

				If($runFlag=="Data Generation") Then
					$counter +=1
					If($counter = $dataGenerationCounter) Then
						ConsoleWrite("Exiting after data generation for Precheck Appointments Flow...." & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Exiting after data generation for Precheck Appointments Flow...." & @CRLF)
						Exit
					EndIf
					ConsoleWrite("Wait for 1 min before next appointment to be scheduled" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait for 1 min before next appointment to be scheduled" & @CRLF)
					Sleep(60000)
				Else
					$counter = $dataGenerationCounter
				EndIf
			Until $counter = $dataGenerationCounter

			FileWriteLine($hFileOpen, @CRLF & " STEP 4 -- GET PATIENT DETAILS FROM DATABASE" & @CRLF)
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

				$patientZipCode = $aData[1][7]
				$patientDOB = $aData[1][5]
				ConsoleWrite("Patient Zip Code is " & $patientZipCode & @CRLF	& "Patient DOB is " & $patientDOB & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Patient Zipcode is " & $patientZipCode & @CRLF & _NowCalc() &"  -- Patient DOB is " & $patientDOB & @CRLF)

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

			FileWriteLine($hFileOpen, @CRLF & " STEP 5 -- GET RESOURCE IDS FROM DATABASE" & @CRLF)
			ConsoleWrite("Get FacilityId for Facility " & $precheckFacilityName & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Get FacilityId for Facility " & $precheckFacilityName & @CRLF)
			ConsoleWrite("Executing Query: " & $arrQuery[65] & "'" & $precheckFacilityName & "'" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[65] & "'" & $precheckFacilityName & "'" & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[65] & "'" & $precheckFacilityName & "'" & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
				$FacilityId=$aData[1][0]
				ConsoleWrite("Facility Id for " & $precheckFacilityName & " is " & $FacilityId & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Facility Id for " & $precheckFacilityName & " is " & $FacilityId & @CRLF)
			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

			ConsoleWrite("Get ResourceId for Resource " & $precheckResource & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Get ResourceId for Resource " & $precheckResource & @CRLF)
			ConsoleWrite("Executing Query: " & $arrQuery[65] & "'" & $precheckResource & "'" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[65] & "'" & $precheckResource & "'" & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[65] & "'" & $precheckResource & "'" & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
				$resourceId=$aData[1][0]
				ConsoleWrite("Resource Id for " & $precheckResource & " is " & $resourceId & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Resource Id for " & $precheckResource & " is " & $resourceId & @CRLF)
			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

			ConsoleWrite("Get DoctorId for Responsible Provider " & $precheckResponsibleProvider & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Get DoctorId for REsponsible Provider " & $precheckResponsibleProvider & @CRLF)
			ConsoleWrite("Executing Query: " & $arrQuery[65] & "'" & $precheckResponsibleProvider & "'" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[65] & "'" & $precheckResponsibleProvider & "'" & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[65] & "'" & $precheckResponsibleProvider & "'" & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
				$DoctorId=$aData[1][0]
				ConsoleWrite("Doctor Id for " & $precheckResponsibleProvider & " is " & $DoctorId & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Doctor Id for " & $precheckResponsibleProvider & " is " & $DoctorId & @CRLF)
			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf
;AppointmentsId|FacilityId|ApptStart|ApptStop|Status|DoctorId|ResourceId|CreatedBy
;385734|20|20180130110000|20180130111500|Scheduled|453|509|mmanohar
;385733|20|20180130110000|20180130111500|Scheduled|453|509|mmanohar

			FileWriteLine($hFileOpen, @CRLF & " STEP 6 -- VERIFY SCHEDULE APPOINTMENT DETAILS IN APPOINTMENTS TABLE" & @CRLF)
			$newQuery = StringReplace($arrQuery[64],"{PatientProfileId}",$PatientProfileId)
			ConsoleWrite("Executing Query: " & $newQuery & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $newQuery & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$newQuery & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
				;AppointmentId
				$AppointmentId=$aData[1][0]
				ConsoleWrite("Appointment Id for above scheduled appointment is " & $AppointmentId & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Appointment Id for above scheduled appointment is " & $AppointmentId & @CRLF)

				;FacilityId
				ConsoleWrite("Verifying FacilityId in Appointments table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying FacilityId in Appointments table" & @CRLF)
				Call("assertData", $FacilityId, $aData[1][1])

				;Appointment Start Time
				ConsoleWrite("Verifying Appointment Start Time in Appointments table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Appointment Start Time in Appointments table" & @CRLF)
				$temp = StringSplit($precheckApptDt,"/")
				$apptStartDt = $temp[3] & $temp[1] & $temp[2] & StringMid($precheckApptStartTime,1,2) & StringMid($precheckApptStartTime,4,2) & "00"
				Call("assertData",$apptStartDt,$aData[1][2])

				;Appointment End Time
				ConsoleWrite("Verifying Appointment Stop Time in Appointments table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Appointment Stop Time in Appointments table" & @CRLF)
				$apptEndDt = $temp[3] & $temp[1] & $temp[2] & StringMid($precheckApptStopTime,1,2) & StringMid($precheckApptStopTime,4,2) & "00"
				Call("assertData",$apptEndDt,$aData[1][3])

				;Status
				ConsoleWrite("Verifying appointemnt status in Appointments table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying appointment status in Appointments table" & @CRLF)
				Call("assertData", "Scheduled", $aData[1][4])

				;DoctorId
				ConsoleWrite("Verifying DoctorId in Appointments table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying DoctorId in Appointments table" & @CRLF)
				Call("assertData", $DoctorId, $aData[1][5])

				;ResourceId
				ConsoleWrite("Verifying ResourceId in Appointments table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying ResourceId in Appointments table" & @CRLF)
				Call("assertData", $resourceId, $aData[1][6])

				;CreatedBy
				ConsoleWrite("Verifying CreatedBy staff in Appointments table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying CreatedBy staff in Appointments table" & @CRLF)
				Call("assertData", $CPSLogin, $aData[1][7])

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf





		Else
			ConsoleWrite("ERROR Attaching GE CPS App...." & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Attaching GE CPS App...." & @CRLF)
		EndIf
EndIf