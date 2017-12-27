#include <IE.au3>
#include <File.au3>
#include <FileConstants.au3>
#include <MsgBoxConstants.au3>
#include <GuiComboBox.au3>
#include <_sql.au3>
#include "..\commonsteps\CommonSteps.au3"

$time = Call("getTimestamp")
$currentDir = @ScriptDir
$commonStepsDir = StringReplace($currentDir , "patientselfregistration", "commonsteps")
Call("deleteOldLogFiles",$commonStepsDir)
$logPath = $commonStepsDir & "\ProcessLog_SelfRegistration_" & $time & ".txt"
Call("setLogPath",$logpath)
$hFileOpen = Call("openLogFile")
$patientDetails = StringReplace($currentDir, "patientselfregistration", "commonsteps") & "\patientdetails.txt"

$arrConfig = Call("setConfig")
$arrQuery = Call("openQueryFile")
$arrEvent = Call("openEventFile")

;Pre-requisite: Verifying Database Flags. This is from GE Release - 17.3 onwards
	FileWriteLine($hFileOpen, @CRLF & " STEP 1 -- UPDATE SERVICESETTINGS FOR PATIENT SELF REGISTRATION FLOW" & @CRLF)
			Call("connectDatabase",$arrConfig[4],$arrConfig[6],$arrConfig[7],$arrConfig[8])
			Local $aData,$iRows,$iColumns
			ConsoleWrite("Executing Query: " & $arrQuery[27] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[27] & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[27] & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
				If(($aData[1][0] = 0) Or ($aData[2][0] = 0) Or ($aData[3][0] = 0)) Then
					ConsoleWrite("Executing Query: " & $arrQuery[1] & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[1] & @CRLF)
					If _SQL_Execute(-1,$arrQuery[1]) = $SQL_ERROR then
						ConsoleWrite("ERROR Updating service settings...." & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Updating service settings...." & @CRLF)
						ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
						Exit

					Else
						ConsoleWrite("Service settings updated...." & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Service Settings updated...." & @CRLF)
					EndIf

				Else
					ConsoleWrite("Service settings already enabled" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Service Settings already enabled" & @CRLF)
				EndIf

			Else
				ConsoleWrite("ERROR Checking Service Settings...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Checking Service Settings...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

			_SQL_Close()

If($arrConfig[9] == "Preconditions Check") Then
	ConsoleWrite("Exiting after checking pre-conditions for Direct Messaging Flow...." & @CRLF)
	FileWriteLine($hFileOpen, _NowCalc() & "  -- Exiting after checking pre-conditions for Direct Messaging Flow...." & @CRLF)
	Exit

Else
	;Create Self registered patient from patient portal
	FileWriteLine($hFileOpen, @CRLF  &" -- STEP 2 -- PATIENT SELF REGISTRATION" &@CRLF)
	ConsoleWrite("Create a self registered patient from Patient Portal" &@CRLF )

	FileWriteLine($hFileOpen, _NowCalc()  &" -- Create a self registered patient from Patient Portal" &@CRLF)

	$ptSelfRegjar = StringReplace($currentDir, "patientselfregistration", "jarfiles")  & "\selfRegistration.jar"
	ConsoleWrite($ptSelfRegjar & @CRLF)
	$counter = 0
	Do
		If($arrConfig[9]=="Data Generation") Then
			ConsoleWrite("Creating Patient #" & $counter+1 & " of " & $arrConfig[45] & " patients"& @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Creating Patient #" & $counter+1 & " of " & $arrConfig[45] & " patients" & @CRLF)
		Else
			ConsoleWrite("Creating Patient #" &$counter+1 & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Creating Patient #" &$counter+1 & @CRLF)
		EndIf

		$ProcessID = Run(@ComSpec & ' /c java -jar ' & $ptSelfRegjar &'',"","",$STDOUT_CHILD)
		ConsoleWrite("$ProcessID :" & $ProcessID & @CRLF)
		ProcessWaitClose($ProcessID)
		$output =StdoutRead($ProcessID)

			If(StringInStr($output,"PASSED")) Then
				ConsoleWrite("Patient #" & $counter+1 & " created successfully" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Patient #" & $counter+1 & " created successfully" & @CRLF)
			Else
				ConsoleWrite("Error in Patient creation from Patient Portal" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Error in Paitent creation from Patient Portal -- FAILED" & @CRLF)
				Exit
			EndIf

		If($arrConfig[9]=="Data Generation") Then
			$counter +=1
			If($counter = $arrConfig[45]) Then
				ConsoleWrite("Exiting after data generation for Patient Self Registration Flow...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Exiting after data generation for Patient Self Registration Flow...." & @CRLF)
				Exit
			EndIf
			ConsoleWrite("Wait for 1 min before next Patient creation" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait for 1 min before next Patient creation" & @CRLF)
			Sleep(60000)

		Else
			$counter = $arrConfig[45]
		EndIf
	Until $counter = $arrConfig[45]
	;Sleep(105000)
	FileWriteLine($hFileOpen, _NowCalc()  &" -- Patient successfully created. Patient data stored in patientdetails.txt" &@CRLF)
	ConsoleWrite("Patient successfully created. Patient data stored in patientdetails.txt" &@CRLF )

;Opening Patient Details File
	Local $arrPtDetails

	If Not _FileReadToArray($patientDetails, $arrPtDetails, 0) Then
		ConsoleWrite("Unable to read patient details file"&@CRLF)
		Exit

	Else
		_FileReadToArray($patientDetails,$arrPtDetails,Default)
		Local $iRows = UBound($arrPtDetails, $UBOUND_ROWS) ; Number of config rows.
		Local $iCols = UBound($arrPtDetails, $UBOUND_COLUMNS) ; Number of config cols.
		ConsoleWrite("Patient Details read"&@CRLF)
	EndIf

	;Check for patient arrived in GE
	FileWriteLine($hFileOpen, @CRLF & " STEP 3 -- FETCH PATIENT COUNT FROM MEDFUSION DASHBOARD" & @CRLF)

		Call("startCPS",$arrConfig[1], $arrConfig[2], $arrConfig[3])
		WinWaitActive("Centricity Practice Solution")

	If(WinActive("Centricity Practice Solution")) Then
		Call("openMFDashboard")

		If (WinActive("Medfusion Dashboard")) Then
			ConsoleWrite("Medfusion Dashboard opened" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Medfusion Dashboard opened" & @CRLF)
			ConsoleWrite("Reading Demographics Dashboard Count" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Reading Demographics Dashboard Count" & @CRLF)
			Local $Text = WinGetText("Medfusion Dashboard")
			Local $arrPtCount = _StringBetween($Text,"Demographics Dashboard", "")
			Local $intPtCount = Int($arrPtCount[0])
			ConsoleWrite("Demographics Dashboard Count is " & $intPtCount & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Demographics Dashboard Count is " & $intPtCount & @CRLF)

						;For testing set PTCount
						;$intPtCount=13
						;ConsoleWrite(@CRLF & $intPtCount & @CRLF)
			FileWriteLine($hFileOpen, @CRLF & " STEP 4 -- VERIFY PATIENT ARRIVED FROM MEDFUSION" & @CRLF)
			ConsoleWrite("Wait for self registered patient to come to Dashboard" & @CRLF)
			Sleep(180000)
			WinActivate("Medfusion Dashboard")

			While($intPtCount > 0)
				$intPtCount = Call("verifyPatientArrivalInDashboard",$intPtCount,$arrPtDetails[2])
				;ConsoleWrite("New Patient count = " & $intPtCount & @CRLF)
			WEnd

;Accepting Patient from Dashboard
			ConsoleWrite("Patient arrived in Medfusion Dashboard" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Patient arrived in Medfusion Dashboard" & @CRLF)

			FileWriteLine($hFileOpen, @CRLF & " STEP 5 -- VERIFY PATIENT DATA IN DATABASE" & @CRLF)

			ConsoleWrite("Verifying Patient Data in DB" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Patient Data in DB" & @CRLF)

;Verify Patient Data arrived from MF
			Call("connectDatabase",$arrConfig[4],$arrConfig[5],$arrConfig[7],$arrConfig[8])

		    Local $aData,$iRows,$iColumns	;Variables to store the array data in to and the row count and the column count
			ConsoleWrite("Executing Query: " & $arrQuery[4] & "'" & $arrPtDetails[1] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[4] & "'" & $arrPtDetails[1] & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[4] & "'" & $arrPtDetails[1] & "';",$aData,$iRows,$iColumns)
			$MedfusionMemberId=$aData[1][0]

			$newQuery = StringReplace($arrQuery[4], "MemberId", "ValueString, ValueDateTime")
			$newQuery = StringReplace($newQuery, "= '2' and ValueString =", "in (2,4,6,8) and MemberId = " &$MedfusionMemberId)
			;ConsoleWrite($newQuery & @CRLF)

			ConsoleWrite("Executing Query: " & $newQuery & $arrQuery[3] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $newQuery & $arrQuery[3] & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$newQuery & $arrQuery[3] &  ";",$aData,$iRows,$iColumns)
			;If $iRval = $SQL_OK then _arrayDisplay($aData,"2D  (" & $iRows & " Rows) (" & $iColumns & " Columns)" )

			If $iRval = $SQL_OK then
				;_arrayDisplay($aData,"1D (" & $iRows & " Rows) (" & $iColumns & " Columns)"  )
		;First Name
				ConsoleWrite("Verifying Patient First Name in Database" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Patient First Name in Database" & @CRLF)
				Call("assertData", $arrPtDetails[1], $aData[1][0])

		;Last Name
				ConsoleWrite("Verifying Patient Last Name in Database" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Patient Last Name in Database" & @CRLF)
				Call("assertData", $arrPtDetails[2], $aData[2][0])

		;Email
				ConsoleWrite("Verifying Patient Email in Database" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Patient Email in Database" & @CRLF)
				Call("assertData", $arrPtDetails[3], $aData[3][0])

		;Birth Date
				$BDateInDB = StringLeft($aData[4][1],8)
				ConsoleWrite("Verifying Patient Birth Date in Database" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Patient Birth Date in Database" & @CRLF)
				Call("assertData", $arrPtDetails[4], $BDateInDB)

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf
			_SQL_Close()

		FileWriteLine($hFileOpen, @CRLF & " STEP 6 -- VERIFY PATIENT DATA IN MEDFUSION DASHBOARD" & @CRLF)
		ConsoleWrite("Verifying Patient Data in  Medfusion Dashboard" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Patient Data in Medfusion Dashboard" & @CRLF)

		WinActivate("Medfusion Demographic Import Dashboard")

;~ 				;First Name

;~ 				MouseClick($MOUSE_CLICK_LEFT, 850,130)
;~ 				Sleep(8000)
;~ 				Send("^c")
;~ 				Sleep(8000)
;~ 				ControlSend("Medfusion Demographic Import Dashboard","","","^c")
;~ 				;ConsoleWrite(@CRLF & ClipGet() &@CRLF);
;~ 				$var = Stringsplit(ClipGet(), Chr (9), 1)
;~ 				;ConsoleWrite($var[2] & @CRLF)
;~ 				ConsoleWrite("Verifying Patient First Name in Medfusion Dashboard" & @CRLF)
;~ 				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Patient First Name in Medfusion Dashboard" & @CRLF)
;~ 				Call("assertData", $arrPtDetails[1], $var[2])

;~ 				;Last Name
;~ 				MouseClick($MOUSE_CLICK_LEFT, 850, 150)
;~ 				Sleep(8000)
;~ 				Send("^c")
;~ 				Sleep(8000)
;~ 				ControlSend("Medfusion Demographic Import Dashboard","","","^c")
;~ 				$var = Stringsplit (ClipGet(), Chr (9), 1)

;~ 				ConsoleWrite("Verifying Patient Last Name in Medfusion Dashboard" & @CRLF)
;~ 				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Patient Last Name in Medfusion Dashboard" & @CRLF)
;~ 				Call("assertData", $arrPtDetails[2], $var[2])
;~
;~ 				;Email
;~ 				MouseClick($MOUSE_CLICK_LEFT, 850, 195)
;~ 				Sleep(8000)
;~ 				Send("^c")
;~ 				Sleep(8000)
;~ 				ControlSend("Medfusion Demographic Import Dashboard","","","^c")
;~ 				$var = Stringsplit (ClipGet(), Chr (9), 1)

;~ 				ConsoleWrite("Verifying Patient Email in Medfusion Dashboard" & @CRLF)
;~ 				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Patient Email in Medfusion Dashboard" & @CRLF)
;~ 				Call("assertData", $arrPtDetails[3], $var[2])
;~
;~ 				;Birth Date
;~ 				MouseClick($MOUSE_CLICK_LEFT, 850, 175)
;~ 				;Send("^c")
;~ 				Sleep(8000)
;~ 				ControlSend("Medfusion Demographic Import Dashboard","","","^c")
;~ 				$var = Stringsplit (ClipGet(), Chr (9), 1)
;~ 				$temp = StringSplit($var[2],"/")

;~ 				If (StringLen($temp[2]) = 1) Then $temp[2] = "0" & $temp[2]
;~ 				If (StringLen($temp[1]) = 1) Then $temp[1] = "0" & $temp[1]

;~ 				$BDateInUI = $temp[3] & $temp[1] & $temp[2]

;~ 				ConsoleWrite("Verifying Patient Birth Date in Medfusion Dashboard" & @CRLF)
;~ 				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Patient Birth Date in Medfusion Dashboard" & @CRLF)
;~ 				Call("assertData", $arrPtDetails[4], $BDateInUI)
;~

		FileWriteLine($hFileOpen, @CRLF & " STEP 7 -- ACCEPT THE PATIENT FROM MEDFUSION DASHBOARD" & @CRLF)
		ConsoleWrite("Accepting patient from  Medfusion Dashboard" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Accepting patient from Medfusion Dashboard" & @CRLF)

				WinActivate("Medfusion Demographic Import Dashboard")
				ControlFocus("Medfusion Demographic Import Dashboard","","[NAME:ComboBoxMatchedPatient]")
				$hCombo = ControlGetHandle("Medfusion Demographic Import Dashboard","","[NAME:ComboBoxMatchedPatient]")
				$aList = _GUICtrlComboBox_GetListArray($hCombo)
				While($aList[0]>0)
					Sleep(1000)
					ControlSend("Medfusion Demographic Import Dashboard","","[NAME:ComboBoxMatchedPatient]","{DOWN}")
					$aList[0] = $aList[0] - 1
				WEnd

				;ControlSend("Medfusion Demographic Import Dashboard","","[NAME:ComboBoxMatchedPatient]","(create new account)")
				;Sleep(2000)
				ControlClick("Medfusion Demographic Import Dashboard","","[NAME:ButtonAccept]")
				ConsoleWrite("Patient accepted from Demographics Dashboard" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Patient accepted from Demographics Dashboard" & @CRLF)

				WinWaitActive("No Demographics to Display")
				ControlClick("No Demographics to Display","","[CLASS:Button; INSTANCE:1]")

				WinWaitActive("Medfusion Demographic Import Dashboard")
				WinClose("Medfusion Demographic Import Dashboard")
				ConsoleWrite("Medfusion Demographics Dashboard closed" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Medfusion Demographics Dashboard closed" & @CRLF)

				WinClose("Medfusion Dashboard")
				ConsoleWrite("Medfusion Dashboard closed" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Medfusion Dashboard closed" & @CRLF)

;---------------------------DB Checks - Additions in AC

		Call("connectDatabase",$arrConfig[4],$arrConfig[5],$arrConfig[7],$arrConfig[8])

		FileWriteLine($hFileOpen, @CRLF & " STEP 8 -- VERIFY MAPPING & STAFF IN CUSMEDFUSIONMEMBER TABLE" & @CRLF)
		ConsoleWrite("Verifying in cusMedfusionMember table" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying in cusMedfusionMember table" & @CRLF)

			Local $aData,$iRows,$iColumns	;Variables to store the array data in to and the row count and the column count
			ConsoleWrite("Executing Query: " & $arrQuery[5] & "'" & $MedfusionMemberId & "'"  & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[5] & "'" & $MedfusionMemberId & "'" & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[5] & "'" & $MedfusionMemberId & "';",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
				;_arrayDisplay($aData,"2D  (" & $iRows & " Rows) (" & $iColumns & " Columns)" )
		;Verify mapping of patient
				ConsoleWrite("Verifying Patient Profile not NULL i.e. mapping successfully established in database" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Patient Profile not NULL i.e. mapping successfully established in database" & @CRLF)

				If ($aData[1][0] = Null) Then
					ConsoleWrite("Mapping not established. Patient Profile Id is NULL" & $aData[1][0] & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Mapping not established. Patient Profile Id is NULL" & $aData[1][0] & " -- FAILED" & @CRLF)
					Exit
				Else
					$PatientProfileId = $aData[1][0]
					ConsoleWrite("Mapping successfully established. Patient Profile Id is " & $aData[1][0] & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Mapping successfully established. Patient Profile Id is " & $aData[1][0] & " -- PASSED" & @CRLF)
				EndIf

		;Verify staff
				ConsoleWrite("Verifying staff in cusMedfusionMemmber table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying staff in cusMedfusionMemmber table" & @CRLF)
				Call("assertData", $arrConfig[2], $aData[1][1])

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf
			Sleep(5000)

		FileWriteLine($hFileOpen, @CRLF & " STEP 9 -- VERIFY PATIENT DATA IN PATIENTPROFILE TABLE" & @CRLF)
		ConsoleWrite("Verifying in PatientProfile table" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying in PatientProfile table" & @CRLF)

			Local $aData,$iRows,$iColumns	;Variables to store the array data in to and the row count and the column count
			ConsoleWrite("Executing Query: " & $arrQuery[6] & "'" & $PatientProfileId & "'"  & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[6] & "'" & $PatientProfileId & "'" & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[6] & "'" & $PatientProfileId & "';",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
				;_arrayDisplay($aData,"2D  (" & $iRows & " Rows) (" & $iColumns & " Columns)" )
				$PatientId = $aData[1][0]
				$PID = Int($aData[1][1]-1)
				$PID = Number($PID)
				ConsoleWrite("PatientID is " & $PatientId & @CRLF)
				ConsoleWrite("PID is " & $PID & @CRLF)
			;Verify First Name
				ConsoleWrite("Verifying First Name in PatientProfile table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying First Name in PatientProfile table" & @CRLF)
				Call("assertData", $arrPtDetails[1], $aData[1][2])

			;Verify Last Name
				ConsoleWrite("Verifying Last Name in PatientProfile table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Last Name in PatientProfile table" & @CRLF)
				Call("assertData", $arrPtDetails[2], $aData[1][3])

			;Verify Email
				ConsoleWrite("Verifying Email in PatientProfile table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Email in PatientProfile table" & @CRLF)
				Call("assertData", $arrPtDetails[3], $aData[1][4])

			;Verify Birth Date
				$BDateInDB = StringLeft($aData[1][5],8)
				ConsoleWrite("Verifying Birth Date in PatientProfile table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying BirthDate in PatientProfile table" & @CRLF)
				Call("assertData", $arrPtDetails[4], $BDateInDB)

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf
			Sleep(5000)

		FileWriteLine($hFileOpen, @CRLF & " STEP 10 -- VERIFY PATIENT REGISTRATION TYPE IN PATIENTREGISTRATION TABLE" & @CRLF)
		ConsoleWrite("Verifying in cusMedfusionPatientRegistration table" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying in cusMedfusionPatientRegistration table" & @CRLF)

			ConsoleWrite("Executing Query: " & $arrQuery[7] & "'" & $PatientProfileId & "'"  & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[7] & "'" & $PatientProfileId & "'" & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[7] & "'" & $PatientProfileId & "';",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
				;_arrayDisplay($aData,"2D  (" & $iRows & " Rows) (" & $iColumns & " Columns)" )
				$PregID = $aData[1][2]
				ConsoleWrite("PregID is " &$PregID & @CRLF)
			;Verify Patient status
				ConsoleWrite("Verifying Patient Status in cusMedfusionPatientRegistration table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Patient Status in cusMedfusionPatientRegistration table" & @CRLF)
				Call("assertData", "Active", $aData[1][0])

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf
			Sleep(5000)

		ConsoleWrite("Wait of 4 mins for patient registration event generation" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait of 4 mins for patient registration event generation" & @CRLF)
		Sleep(240000)
		FileWriteLine($hFileOpen, @CRLF & " STEP 11 -- VERIFY EVENTID & EVENT TYPE IN CUSMEDFUSIONMUEVENTS TABLE" & @CRLF)
		ConsoleWrite("Verifying in cusMedfusionMUEvents table" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying in cusMedfusionMUEvents table" & @CRLF)

			ConsoleWrite("Executing Query: " & $arrQuery[9] & "'" & $PatientProfileId & "'"  & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[9] & "'" & $PatientProfileId & "'" & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[9] & "'" & $PatientProfileId & "';",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then

			;Verify EventId
				ConsoleWrite("Verifying EventId in cusMedfusionMUEvents table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying EventID in cusMedfusionMUEvents table" & @CRLF)
				$eventID = "PR_" & $PregID
				ConsoleWrite("Expected event Id is " & $eventID & @CRLF)
				Call("assertData", $eventID, $aData[1][0])

			;Verify Event Type
				ConsoleWrite("Verifying Event Type in cusMedfusionMUEvents table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Event Type in cusMedfusionMUEvents table" & @CRLF)
				Call("assertData", "Patient registration", $aData[1][1])

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf
			Sleep(5000)

		ConsoleWrite("Wait of 4 mins for event processing" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait of 4 mins for event processing" & @CRLF)
		Sleep(240000)
		FileWriteLine($hFileOpen, @CRLF & " STEP 12 -- VERIFY MUEVENTLOGTYPEID IN MUACTIVITYLOG TABLE" & @CRLF)
		ConsoleWrite("Verifying in MUActivityLog table" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying in MUActivityLog table" & @CRLF)

			ConsoleWrite("Executing Query: " & $arrQuery[11] & "'" & $PID & "'"  & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[11] & "'" & $PID & "'" & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[11] & "'" & $PID & "';",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then

			;Verify MUActivityLogTypeId
				ConsoleWrite("Verifying MUActivityLogTypeId in MUActivityLog table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying MUActivityLogTypeId in MUActivityLog table" & @CRLF)
				Call("assertData", $arrEvent[2], $aData[1][0])

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

		_SQL_Close()
		ConsoleWrite("Patient Self Registration Flow -- SUCCESSFULLY COMPLETED...." & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- PATIENT SELF REGISTRATION  FLOW -- SUCCESSFULLY COMPLETED...." & @CRLF)
;---------------------------DB Checks ended
		Else
			ConsoleWrite("ERROR Opening Medfusion Dashboard...." & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Opening Medfusion Dashboard...." & @CRLF)
			Exit
		EndIf

	Else
		ConsoleWrite("ERROR Attaching GE CPS App...." & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Attaching GE CPS App...." & @CRLF)
	EndIf
;-------------Code merging ended
EndIf

