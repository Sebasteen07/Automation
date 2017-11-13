#include <IE.au3>
#include <File.au3>
#include <FileConstants.au3>
#include <MsgBoxConstants.au3>
#include <_sql.au3>
#include "..\commonsteps\CommonSteps.au3"

$currentDir = @ScriptDir
$logPath = StringReplace($currentDir, "patientselfregistration", "commonsteps") & "\ProcessLog.txt"
$patientDetails = StringReplace($currentDir, "patientselfregistration", "commonsteps") & "\patientdetails.txt"

;Opening Log file
		Local $hFileOpen = FileOpen($logPath, $FO_OVERWRITE)
		If $hFileOpen = -1 Then
			MsgBox($MB_SYSTEMMODAL, "", "An error occurred while writing process log file.")
			Exit
		EndIf

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
						;Msgbox(0 + 16 +262144,"Error",_SQL_GetErrMsg())
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
		ConsoleWrite("Creating Patient #" &$counter+1 & @CRLF)
		ShellExecuteWait($ptSelfRegjar)
		If($arrConfig[9]=="Data Generation") Then
			$counter +=1
			Sleep(60000)
			If($counter = $arrConfig[34]) Then
				ConsoleWrite("Exiting after data generation for Patient Self Registration Flow...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Exiting after data generation for Patient Self Registration Flow...." & @CRLF)
				Exit
			EndIf

		Else
			$counter = $arrConfig[34]
		EndIf
	Until $counter = $arrConfig[34]
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
;-----------------------FROM HERE
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
			ConsoleWrite("Wait for self registered patient to come to Dashboard")
			Sleep(180000)
			WinActivate("Medfusion Dashboard")

			While($intPtCount > 0)
				$intPtCount = Call("verifyPatientArrivalInDashboard",$intPtCount,$arrPtDetails[2])
				;ConsoleWrite("New Patient count = " & $intPtCount & @CRLF)
			WEnd
;------------------TO HERE

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
				ConsoleWrite("Expected Result: " & $arrPtDetails[1] &" and Actual Result: " & $aData[1][0] & " -- ")
				If (StringCompare($arrPtDetails[1], $aData[1][0]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrPtDetails[1] &" and Actual Result: " & $aData[1][0] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrPtDetails[1] &" and Actual Result: " & $aData[1][0] & " -- FAILED" & @CRLF)
					Exit
				EndIf

		;Last Name
				ConsoleWrite("Verifying Patient Last Name in Database" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Patient Last Name in Database" & @CRLF)
				ConsoleWrite("Expected Result: " & $arrPtDetails[2] &" and Actual Result: " & $aData[2][0] & " -- ")
				If (StringCompare($arrPtDetails[2], $aData[2][0]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrPtDetails[2] &" and Actual Result: " & $aData[2][0] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrPtDetails[2] &" and Actual Result: " & $aData[2][0] & " -- FAILED" & @CRLF)
					Exit
				EndIf

		;Email
				ConsoleWrite("Verifying Patient Email in Database" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Patient Email in Database" & @CRLF)
				ConsoleWrite("Expected Result: " & $arrPtDetails[3] &" and Actual Result: " & $aData[3][0] & " -- ")
				If (StringCompare($arrPtDetails[3], $aData[3][0]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrPtDetails[3] &" and Actual Result: " & $aData[3][0] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrPtDetails[3] &" and Actual Result: " & $aData[3][0] & " -- FAILED" & @CRLF)
					Exit
				EndIf

		;Birth Date
				$BDateInDB = StringLeft($aData[4][1],8)
				ConsoleWrite("Verifying Patient Birth Date in Database" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Patient Birth Date in Database" & @CRLF)
				ConsoleWrite("Expected Result: " & $arrPtDetails[4] &" and Actual Result: " & $BDateInDB & " -- ")
				If (StringCompare($arrPtDetails[4], $BDateInDB) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrPtDetails[4] &" and Actual Result: " & $BDateInDB & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrPtDetails[4] &" and Actual Result: " & $BDateInDB & " -- FAILED" & @CRLF)
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
;---------------------

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
;~ 				ConsoleWrite("Expected Result: " & $arrPtDetails[1] &" and Actual Result: " & $var[2] & " -- ")
;~ 				If (StringCompare($arrPtDetails[1], $var[2]) = 0) Then
;~ 					ConsoleWrite("PASSED" & @CRLF)
;~ 					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrPtDetails[1] &" and Actual Result: " & $var[2] & " -- PASSED" & @CRLF)
;~ 				Else
;~ 					ConsoleWrite("FAILED" & @CRLF)
;~ 					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrPtDetails[1] &" and Actual Result: " & $var[2] & " -- FAILED" & @CRLF)
;~ 					Exit
;~ 				EndIf

;~ 				;Last Name
;~ 				MouseClick($MOUSE_CLICK_LEFT, 850, 150)
;~ 				Sleep(8000)
;~ 				Send("^c")
;~ 				Sleep(8000)
;~ 				ControlSend("Medfusion Demographic Import Dashboard","","","^c")
;~ 				$var = Stringsplit (ClipGet(), Chr (9), 1)

;~ 				ConsoleWrite("Verifying Patient Last Name in Medfusion Dashboard" & @CRLF)
;~ 				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Patient Last Name in Medfusion Dashboard" & @CRLF)
;~ 				ConsoleWrite("Expected Result: " & $arrPtDetails[2] &" and Actual Result: " & $var[2] & " -- ")
;~ 				If (StringCompare($arrPtDetails[2], $var[2]) = 0) Then
;~ 					ConsoleWrite("PASSED" & @CRLF)
;~ 					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrPtDetails[2] &" and Actual Result: " & $var[2] & " -- PASSED" & @CRLF)
;~ 				Else
;~ 					ConsoleWrite("FAILED" & @CRLF)
;~ 					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrPtDetails[2] &" and Actual Result: " & $var[2] & " -- FAILED" & @CRLF)
;~ 					Exit
;~ 				EndIf

;~ 				;Email
;~ 				MouseClick($MOUSE_CLICK_LEFT, 850, 195)
;~ 				Sleep(8000)
;~ 				Send("^c")
;~ 				Sleep(8000)
;~ 				ControlSend("Medfusion Demographic Import Dashboard","","","^c")
;~ 				$var = Stringsplit (ClipGet(), Chr (9), 1)

;~ 				ConsoleWrite("Verifying Patient Email in Medfusion Dashboard" & @CRLF)
;~ 				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Patient Email in Medfusion Dashboard" & @CRLF)
;~ 				ConsoleWrite("Expected Result: " & $arrPtDetails[3] &" and Actual Result: " & $var[2] & " -- ")
;~ 				If (StringCompare($arrPtDetails[3], $var[2]) = 0) Then
;~ 					ConsoleWrite("PASSED" & @CRLF)
;~ 					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrPtDetails[3] &" and Actual Result: " & $var[2] & " -- PASSED" & @CRLF)
;~ 				Else
;~ 					ConsoleWrite("FAILED" & @CRLF)
;~ 					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrPtDetails[3] &" and Actual Result: " & $var[2] & " -- FAILED" & @CRLF)
;~ 					Exit
;~ 				EndIf

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
;~ 				ConsoleWrite("Expected Result: " & $arrPtDetails[4] &" and Actual Result: " & $BDateInUI & " -- ")
;~ 				If (StringCompare($arrPtDetails[4], $BDateInUI) = 0) Then
;~ 					ConsoleWrite("PASSED" & @CRLF)
;~ 					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrPtDetails[4] &" and Actual Result: " & $BDateInUI & " -- PASSED" & @CRLF)
;~ 				Else
;~ 					ConsoleWrite("FAILED" & @CRLF)
;~ 					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrPtDetails[4] &" and Actual Result: " & $BDateInUI & " -- FAILED" & @CRLF)
;~ 					Exit
;~ 				EndIf

		FileWriteLine($hFileOpen, @CRLF & " STEP 7 -- ACCEPT THE PATIENT FROM MEDFUSION DASHBOARD" & @CRLF)
		ConsoleWrite("Accepting patient from  Medfusion Dashboard" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Accepting patient from Medfusion Dashboard" & @CRLF)
;----------------FROM  HERE
				WinActivate("Medfusion Demographic Import Dashboard")

				ControlSend("Medfusion Demographic Import Dashboard","","[NAME:ComboBoxMatchedPatient]","(create new account)")
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
;-------------TO HERE
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
				ConsoleWrite("Expected Result: " & $arrConfig[2] &" and Actual Result: " & $aData[1][1] & " -- ")
				If (StringCompare($arrConfig[2], $aData[1][1]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrConfig[2] &" and Actual Result: " & $aData[1][1] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrConfig[2] &" and Actual Result: " & $aData[1][1] & " -- FAILED" & @CRLF)
					Exit
				EndIf

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
				ConsoleWrite("Expected Result: " & $arrPtDetails[1] &" and Actual Result: " & $aData[1][2] & " -- ")
				If (StringCompare($arrPtDetails[1], $aData[1][2]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrPtDetails[1] &" and Actual Result: " & $aData[1][2] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrPtDetails[1] &" and Actual Result: " & $aData[1][2] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;Verify Last Name
				ConsoleWrite("Verifying Last Name in PatientProfile table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Last Name in PatientProfile table" & @CRLF)
				ConsoleWrite("Expected Result: " & $arrPtDetails[2] &" and Actual Result: " & $aData[1][3] & " -- ")
				If (StringCompare($arrPtDetails[2], $aData[1][3]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrPtDetails[2] &" and Actual Result: " & $aData[1][3] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrPtDetails[2] &" and Actual Result: " & $aData[1][3] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;Verify Email
				ConsoleWrite("Verifying Email in PatientProfile table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Email in PatientProfile table" & @CRLF)
				ConsoleWrite("Expected Result: " & $arrPtDetails[3] &" and Actual Result: " & $aData[1][4] & " -- ")
				If (StringCompare($arrPtDetails[3], $aData[1][4]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrPtDetails[3] &" and Actual Result: " & $aData[1][4] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrPtDetails[3] &" and Actual Result: " & $aData[1][4] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;Verify Birth Date
				$BDateInDB = StringLeft($aData[1][5],8)
				ConsoleWrite("Verifying Birth Date in PatientProfile table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying BirthDate in PatientProfile table" & @CRLF)
				ConsoleWrite("Expected Result: " & $arrPtDetails[4] &" and Actual Result: " & $BDateInDB & " -- ")
				If (StringCompare($arrPtDetails[4], $BDateInDB) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrPtDetails[4] &" and Actual Result: " & $BDateInDB & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrPtDetails[4] &" and Actual Result: " & $BDateInDB & " -- FAILED" & @CRLF)
					Exit
				EndIf
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
				ConsoleWrite("Expected Result: " & "Active" &" and Actual Result: " & $aData[1][0] & " -- ")
				If (StringCompare("Active", $aData[1][0]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Active" &" and Actual Result: " & $aData[1][0] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Active" &" and Actual Result: " & $aData[1][0] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf
			Sleep(5000)

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
				ConsoleWrite("Expected Result: " & $eventID &" and Actual Result: " & $aData[1][0] & " -- ")
				If (StringCompare($eventID, $aData[1][0]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $eventID &" and Actual Result: " & $aData[1][0] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $eventID &" and Actual Result: " & $aData[1][0] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;Verify Event Type
				ConsoleWrite("Verifying Event Type in cusMedfusionMUEvents table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Event Type in cusMedfusionMUEvents table" & @CRLF)
				ConsoleWrite("Expected Result: " & "Patient registration" &" and Actual Result: " & $aData[1][1] & " -- ")
				If (StringCompare("Patient registration", $aData[1][1]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Patient registration" &" and Actual Result: " & $aData[1][1] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Patient registration" &" and Actual Result: " & $aData[1][1] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf
			Sleep(5000)

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

				ConsoleWrite("Expected Result: " & $arrEvent[2] &" and Actual Result: " & $aData[1][0] & " -- ")
				If (StringCompare($arrEvent[2], $aData[1][0]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrEvent[2] &" and Actual Result: " & $aData[1][0] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrEvent[2] &" and Actual Result: " & $aData[1][0] & " -- FAILED" & @CRLF)
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
		ConsoleWrite("Patient Self Registration Flow -- SUCCESSFULLY COMPLETED...." & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- PATIENT SELF REGISTRATION  FLOW -- SUCCESSFULLY COMPLETED...." & @CRLF)
;---------------------------DB Checks ended
;-------------------FROM HERE
		Else
			ConsoleWrite("ERROR Opening Medfusion Dashboard...." & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Opening Medfusion Dashboard...." & @CRLF)
			Exit
		EndIf

	Else
		ConsoleWrite("ERROR Attaching GE CPS App...." & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Attaching GE CPS App...." & @CRLF)
	EndIf
;----------------TO HERE
;-------------Code merging ended
EndIf

