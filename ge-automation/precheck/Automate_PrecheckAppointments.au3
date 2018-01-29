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
$arrConfig = Call("setConfig")
$arrQuery = Call("openQueryFile")
$arrEvent = Call("openEventFile")
$arrDoctype = Call("openDoctypeFile")

;TODO -- Update Servicesettings - after implementation
	FileWriteLine($hFileOpen, @CRLF & " STEP 1 -- UPDATE SERVICESETTINGS FOR PRECHECK APPOINTMENTS FLOW" & @CRLF)
		Call("connectDatabase",$arrConfig[4],$arrConfig[6],$arrConfig[7],$arrConfig[8])
		Local $aData,$iRows,$iColumns

		_SQL_Close()


If($arrConfig[9] == "Preconditions Check") Then
	ConsoleWrite("Exiting after checking pre-conditions for Precheck Appointments Flow...." & @CRLF)
	FileWriteLine($hFileOpen, _NowCalc() & "  -- Exiting after checking pre-conditions for Precheck Appointments Flow...." & @CRLF)
	Exit
Else
	FileWriteLine($hFileOpen, @CRLF & " STEP 2 -- START CPS AND OPEN SCHEDULING LINK" & @CRLF)
	ConsoleWrite("Start CPS and open Scheduling Link" &@CRLF )
		Call("startCPS",$arrConfig[1], $arrConfig[2], $arrConfig[3])
		WinActivate("Centricity Practice Solution")

		If(WinActive("Centricity Practice Solution")) Then

			FileWriteLine($hFileOpen, @CRLF & " STEP 2 -- SCHEDULE A FUTURE DATE APPOINTMENT FROM CPS" & @CRLF)
			ConsoleWrite("Schedule a future date appointment from CPS" &@CRLF )
			Call("scheduleAppointment",$arrConfig[15],$arrConfig[16],$arrConfig[53],$arrConfig[54],$arrConfig[55],$arrConfig[56],$arrConfig[57],$arrConfig[58])


		Else
			ConsoleWrite("ERROR Attaching GE CPS App...." & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Attaching GE CPS App...." & @CRLF)
		EndIf
EndIf