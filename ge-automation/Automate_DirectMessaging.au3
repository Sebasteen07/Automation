#include <IE.au3>
#include <File.au3>
#include <FileConstants.au3>
#include <MsgBoxConstants.au3>
#include <_sql.au3>
#include <C:\XASYST\AUTOIT\AutoIT\CommonSteps.au3>

$logPath = @ScriptDir & "\ProcessLog.txt"

;Opening Log file
		Local $hFileOpen = FileOpen($logPath, $FO_OVERWRITE)
		If $hFileOpen = -1 Then
			MsgBox($MB_SYSTEMMODAL, "", "An error occurred while writing process log file.")
			Exit
		EndIf

$arrConfig = Call("setConfig")
$arrQuery = Call("openQueryFile")
$arrEvent = Call("openEventFile")

;Update servicesettings for TOC flow
		FileWriteLine($hFileOpen, @CRLF & " STEP 1 -- UPDATE SERVICESETTINGS FOR DIRECT MESSAGING FLOW" & @CRLF)
			Call("connectDatabase",$arrConfig[4],$arrConfig[6],$arrConfig[7],$arrConfig[8])

			If (_SQL_Execute(-1,$arrQuery[14]) Or _SQL_Execute(-1,$arrQuery[15]) Or _SQL_Execute(-1,$arrQuery[16])) = $SQL_ERROR then
				;Msgbox(0 + 16 +262144,"Error",_SQL_GetErrMsg())
				ConsoleWrite("ERROR Executing Query...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Executing Query...." & @CRLF)
			Exit

			Else
				ConsoleWrite("Service settings updated...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Service Settings updated...." & @CRLF)
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

		FileWriteLine($hFileOpen, @CRLF & " STEP 4 -- CREATE A REFERRAL ORDER FOR THE PATIENT" & @CRLF)
		If(WinActive("Chart - NOT FOR PATIENT USE")) Then
			Call("createNewDocument",$arrConfig[16],$arrConfig[14],$arrConfig[15])
			Call("createOrder",$arrConfig[18],$arrConfig[19],$arrConfig[20],$arrConfig[14],$arrConfig[15],$arrConfig[24])
			Sleep(5000)

			FileWriteLine($hFileOpen, @CRLF & " STEP 5 -- VERIFY EVENT 4 FOR ORDER CREATION" & @CRLF)

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

		;Get loginname, PVID of authorizing provider
			$temp = StringSplit($arrConfig[19], ', ', 1)
			$newQuery = StringReplace($arrQuery[23],"LNAME","'" & $temp[1] & "'")
			$newQuery = StringReplace($newQuery,"FNAME","'" & $temp[2] & "'")
		ConsoleWrite($newQuery & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$newQuery & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
				$authpvdrLogin = $aData[1][0]
				$authpvdrPVID = Int($aData[1][1]) - 1

				ConsoleWrite("Authorizing Provider PVID is " & $authpvdrPVID & @CRLF	& "Authorizing Provider login is " & $authpvdrLogin & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Authorizing Provider PVID is " & $authpvdrPVID & @CRLF & _NowCalc() &"  -- Authorizing Provider login is " & $authpvdrLogin & @CRLF)
			EndIf

		;Get pvid of order creating provider
			$newQuery = StringReplace($arrQuery[23],"LASTNAME = LNAME and FIRSTNAME = FNAME","LOGINNAME = '" & $arrConfig[2] & "'")
			$iRval = _SQL_GetTable2D(-1,$newQuery & ";",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
				$signPvdrPVID = Int($aData[1][1]) - 1

				ConsoleWrite("Signing provider PVID is " & $signPvdrPVID & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Signing provider PVID is " & $signPvdrPVID & @CRLF)
			EndIf

		;MUActivityLog- Event 4
			$iRval = _SQL_GetTable2D(-1,$arrQuery[11] & $PID & $arrQuery[12] & ";",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
			;MUActivityLogTypeId
				ConsoleWrite("Verifying MUActivityLogTypeId in MUActivityLog table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying MUActivityLogTypeId in MUActivityLog table" & @CRLF)
				ConsoleWrite("Expected Result: " & $arrEvent[1] &" and Actual Result: " & $aData[1][0] & " -- ")
				If (StringCompare($arrEvent[1], $aData[1][0]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrEvent[1] &" and Actual Result: " & $aData[1][0] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrEvent[1] &" and Actual Result: " & $aData[1][0] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;PVID
				ConsoleWrite("Verifying PVID in MUActivityLog table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying PVID in MUActivityLog table" & @CRLF)
				$temp = Int($aData[1][2]) - 1
				ConsoleWrite("Expected Result: " & $signPvdrPVID &" and Actual Result: " & $temp & " -- ")
				If (StringCompare($signPvdrPVID, $temp) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $signPvdrPVID &" and Actual Result: " & $temp & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $signPvdrPVID &" and Actual Result: " & $temp & " -- FAILED" & @CRLF)
					Exit
				EndIf

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				Exit
			EndIf

			ConsoleWrite("Wait of 3.5" & @CRLF)
			Sleep(210000)
			FileWriteLine($hFileOpen, @CRLF & " STEP 6 -- VERIFY ORDER DETAILS IN DATABASE" & @CRLF)

		;cusMedfusionOutgoingToc
			$iRval = _SQL_GetTable2D(-1,$arrQuery[17] & $PatientProfileId & $arrQuery[18] & ";",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
				$outgoingTocId = $aData[1][0]
				$orderNum = $aData[1][1]
				$sdid = Int($aData[1][2]) - 1

				ConsoleWrite("Outgoing TOC Id is " & $outgoingTocId & @CRLF	& "Order number is " & $orderNum & @CRLF & "SDID is " & $sdid & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Outgoing TOC Id is " & $outgoingTocId & @CRLF & _NowCalc() &"  -- Order number is " & $orderNum & @CRLF & _NowCalc() & "  -- SDID is " & $sdid & @CRLF)
			EndIf

			ConsoleWrite("Wait of 3.5" & @CRLF)
			Sleep(210000)
		;cusMedfusionOutgoingTOCstatus
			FileWriteLine($hFileOpen, @CRLF & " STEP 7 -- VERIFY ORDER DETAILS IN CUSMEDFUSIONOUTGOINGTOCSTATUS TABLE" & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[19] & $outgoingTocId & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
			;TOC Status returned from SES
				ConsoleWrite("Verifying TOC Status (returned from SES) in cusMedfusionOutgoingTOCstatus table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying TOC Status (returned from SES) in cusMedfusionOutgoingTOCstatus table" & @CRLF)
				ConsoleWrite("Expected Result: " & "Confiremd / Accepted" &" and Actual Result: " & $aData[1][0] & " -- ")
				If (StringCompare("Confirmed", $aData[1][0]) = 0) Or (StringCompare("Accepted", $aData[1][0]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Confirmed / Accepted" &" and Actual Result: " & $aData[1][0] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Confirmed / Accepted" &" and Actual Result: " & $aData[1][0] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;mdnConfirmationDate
				ConsoleWrite("Verifying mdnConfirmationDate in cusMedfusionOutgoingTOCstatus table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying mdnConfirmationDate in cusMedfusionOutgoingTOCstatus table" & @CRLF)
				If ($aData[1][1] = Null) Then
					ConsoleWrite("MDNConfirmationDate not returned from SES " & $aData[1][1] & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- MDNConfirmationDate not returned from SES " & $aData[1][1] & " -- FAILED" & @CRLF)
					Exit
				Else
					ConsoleWrite("MDNConfirmationDate returned from SES " & $aData[1][1] & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- MDNConfirmationDate returned from SES " & $aData[1][1] & " -- PASSED" & @CRLF)
				EndIf

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				Exit
			EndIf

		;cusMedfusionOutgoingTocProvider
			FileWriteLine($hFileOpen, @CRLF & " STEP 8 -- VERIFY ORDER DETAILS IN CUSMEDFUSIONOUTGOINGTOCPROVIDER TABLE" & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[20] & $outgoingTocId & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
			;Status in provider table
				ConsoleWrite("Verifying Status in cusMedfusionOutgoingTocProvider table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Status in cusMedfusionOutgoingTocProvider table" & @CRLF)
				ConsoleWrite("Expected Result: " & "SUCCESS" &" and Actual Result: " & $aData[1][4] & " -- ")
				If (StringCompare("SUCCESS", $aData[1][4]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "SUCCESS" &" and Actual Result: " & $aData[1][4] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "SUCCESS" &" and Actual Result: " & $aData[1][4] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;Authorizing Provider (IntServProv)
				ConsoleWrite("Verifying Authorizing Provider (IntServProv) in cusMedfusionOutgoingTocProvider table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Authorizing Provider (IntServProv) in cusMedfusionOutgoingTocProvider table" & @CRLF)
				$temp = StringSplit($aData[1][0], ' ', 0)

				For $i = 4	 To $temp[0]
					$temp[3] = $temp[3] & " " &$temp[$i]
				Next

				ConsoleWrite("Expected Result: " & $arrConfig[19] &" and Actual Result: " & $temp[3] &", " & $temp[1] & " -- ")
				If (StringCompare($arrConfig[19], $temp[3] &", " & $temp[1]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrConfig[19] &" and Actual Result: " & $temp[3] &", " & $temp[1] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrConfig[19] &" and Actual Result: " & $temp[3] &", " & $temp[1] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;Authorizing Provider email
			$authPvdrEmail = $aData[1][1]

			;Referring Provider (ExtServProv)
				ConsoleWrite("Verifying Referring Provider (ExtServProv) in cusMedfusionOutgoingTocProvider table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Referring Provider (ExtServProv) in cusMedfusionOutgoingTocProvider table" & @CRLF)
				$temp = StringSplit($aData[1][2], ' ', 0)

				For $i = 3	 To $temp[0]
					$temp[2] = $temp[2] & " " &$temp[$i]
				Next

				ConsoleWrite("Expected Result: " & $arrConfig[20] &" and Actual Result: " & $temp[2] &", " & $temp[1] & " -- ")
				If (StringCompare($arrConfig[20], $temp[2] &", " & $temp[1]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrConfig[20] &" and Actual Result: " & $temp[2] &", " & $temp[1] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrConfig[20] &" and Actual Result: " & $temp[2] &", " & $temp[1] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;Referring Provider email
			$redPvdrEmail = $aData[1][3]

			;Order Subject
			$temp = StringSplit($aData[1][5]," ")
			$orderSubject = $temp[1]
			$fullSubject = Null
			For $i = 1 to $temp[0]
				$fullSubject = $fullSubject & $temp[$i]
			Next

			;Order Body
			$temp = StringSplit($aData[1][6]," ")
			$orderBody = Null
			For $i = 1 to $temp[0]
				$orderBody = $orderBody & $temp[$i]
			Next


			;Order Attachment Name
			$orderAttachmentName = $aData[1][7]

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				Exit
			EndIf

		ConsoleWrite("Wait of 1" & @CRLF)
		Sleep(60000)
		;cusMedfusionOutgoingTOCPAtient
			FileWriteLine($hFileOpen, @CRLF & " STEP 9 -- VERIFY PATIENT LETTER DETAILS IN CUSMEDFUSIONOUTGOINGTOCPATIENT TABLE" & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[21] & $outgoingTocId & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then

			;Status in patient table
				ConsoleWrite("Verifying Status in cusMedfusionOutgoingTocPatient table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Status in cusMedfusionOutgoingTocPatient table" & @CRLF)
				ConsoleWrite("Expected Result: " & "SUCCESS" &" and Actual Result: " & $aData[1][1] & " -- ")
				If (StringCompare("SUCCESS", $aData[1][1]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "SUCCESS" &" and Actual Result: " & $aData[1][1] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "SUCCESS" &" and Actual Result: " & $aData[1][1] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;PateintProfileId
				ConsoleWrite("Verifying PatientProfileId in cusMedfusionOutgoingTocPatient table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying PatientProfileId in cusMedfusionOutgoingTocPatient table" & @CRLF)

				ConsoleWrite("Expected Result: " & $PatientProfileId &" and Actual Result: " & $aData[1][0] & " -- ")
				If (StringCompare($PatientProfileId, $aData[1][0]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $PatientProfileId &" and Actual Result: " & $aData[1][0] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $PatientProfileId &" and Actual Result: " & $aData[1][0] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;CommOutgoinngId
			$commOutgoingId = $aData[1][2]

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				Exit
			EndIf

		ConsoleWrite("Wait of 1" & @CRLF)
		Sleep(60000)
		;cusMedfusionCommOutgoing
			FileWriteLine($hFileOpen, @CRLF & " STEP 10 -- VERIFY PATIENT LETTER DETAILS IN CUSMEDFUSIONCOMMOUTGOING TABLE" & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[22] & $commOutgoingId & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
				;PateintProfileId
				ConsoleWrite("Verifying PatientProfileId in cusMedfusionCommOutgoing table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying PatientProfileId in cusMedfusionCommOutgoing table" & @CRLF)

				ConsoleWrite("Expected Result: " & $PatientProfileId &" and Actual Result: " & $aData[1][0] & " -- ")
				If (StringCompare($PatientProfileId, $aData[1][0]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $PatientProfileId &" and Actual Result: " & $aData[1][0] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $PatientProfileId &" and Actual Result: " & $aData[1][0] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;CommServiceType
				ConsoleWrite("Verifying CommServiceType in cusMedfusionCommOutgoing table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying CommServiceType in cusMedfusionCommOutgoing table" & @CRLF)

				ConsoleWrite("Expected Result: " & "Transition of Care" &" and Actual Result: " & $aData[1][1] & " -- ")
				If (StringCompare("Transition of Care", $aData[1][1]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Transition of Care" &" and Actual Result: " & $aData[1][1] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Transition of Care" &" and Actual Result: " & $aData[1][1] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;Message Subject in portal
				ConsoleWrite("Verifying Message Subject in cusMedfusionCommOutgoing table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Message Subject in cusMedfusionCommOutgoing table" & @CRLF)

				ConsoleWrite("Expected Result: " & $arrConfig[25] &" and Actual Result: " & $aData[1][2] & " -- ")
				If (StringCompare($arrConfig[25], $aData[1][2]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrConfig[25] &" and Actual Result: " & $aData[1][2] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrConfig[25] &" and Actual Result: " & $aData[1][2] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;Created by User
				ConsoleWrite("Verifying user creating the communication in cusMedfusionCommOutgoing table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying user creating the communication in cusMedfusionCommOutgoing table" & @CRLF)

				ConsoleWrite("Expected Result: " & $authpvdrLogin &" and Actual Result: " & $aData[1][3] & " -- ")
				If (StringCompare($authpvdrLogin, $aData[1][3]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $authpvdrLogin &" and Actual Result: " & $aData[1][3] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $authpvdrLogin &" and Actual Result: " & $aData[1][3] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;CommSentStatus
				ConsoleWrite("Verifying CommSentStatus in cusMedfusionCommOutgoing table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying CommSentStatus in cusMedfusionCommOutgoing table" & @CRLF)

				ConsoleWrite("Expected Result: " & "1" &" and Actual Result: " & $aData[1][4] & " -- ")
				If (StringCompare("1", $aData[1][4]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "1" &" and Actual Result: " & $aData[1][4] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "1" &" and Actual Result: " & $aData[1][4] & " -- FAILED" & @CRLF)
					Exit
				EndIf

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

			;DocumentId
				ConsoleWrite("Verifying DocumentId in cusMedfusionCommOutgoing table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying DocumentId in cusMedfusionCommOutgoing table" & @CRLF)
				$DocumentId = Int($aData[1][6]) - 1
				ConsoleWrite("Expected Result: " & $sdid &" and Actual Result: " & $DocumentId & " -- ")
				If (StringCompare($sdid, $DocumentId) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $sdid &" and Actual Result: " & $DocumentId & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $sdid &" and Actual Result: " & $DocumentId & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;CommSentDate
				$messageSentDate = $aData[1][7]

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				Exit
			EndIf

		;cusMedfusionMUEvents - Event 514
			FileWriteLine($hFileOpen, @CRLF & " STEP 11 -- VERIFY EVENT 514 DETAILS IN CUSMEDFUSIONMUEVENTS TABLE" & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[9] & $PatientProfileId & " and sdid = '" & $sdid & "'" & $arrQuery[10] & ";",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
			;EventId
				ConsoleWrite("Verifying EventId in cusMedfusionMUEvents table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying EventId in cusMedfusionMUEvents table" & @CRLF)
				ConsoleWrite("Expected Result: " & "TOC_" & $outgoingTocId &" and Actual Result: " & $aData[2][0] & " -- ")
				If (StringCompare("TOC_" & $outgoingTocId, $aData[2][0]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "TOC_" & $outgoingTocId &" and Actual Result: " & $aData[2][0] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "TOC_" & $outgoingTocId &" and Actual Result: " & $aData[2][0] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;Evnet Type
				ConsoleWrite("Verifying Event Type in cusMedfusionMUEvents table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Event Type in cusMedfusionMUEvents table" & @CRLF)

				ConsoleWrite("Expected Result: " & "Transition of Care" &" and Actual Result: " & $aData[2][1] & " -- ")
				If (StringCompare("Transition of Care", $aData[2][1]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Transition of Care" &" and Actual Result: " & $aData[2][1] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Transition of Care" &" and Actual Result: " & $aData[2][1] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;Provider
				ConsoleWrite("Verifying Provider in cusMedfusionMUEvents table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Provider in cusMedfusionMUEvents table" & @CRLF)

				ConsoleWrite("Expected Result: " & $authpvdrLogin &" and Actual Result: " & $aData[2][2] & " -- ")
				If (StringCompare($authpvdrLogin, $aData[2][2]) = 0)  Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $authpvdrLogin &" and Actual Result: " & $aData[2][2] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $authpvdrLogin &" and Actual Result: " & $aData[2][2] & " -- FAILED" & @CRLF)
					Exit
				EndIf

		;cusMedfusionMUEvents - Event 522
				FileWriteLine($hFileOpen, @CRLF & " STEP 12 -- VERIFY EVENT 522 DETAILS IN CUSMEDFUSIONMUEVENTS TABLE" & @CRLF)
			;EventId
				ConsoleWrite("Verifying EventId in cusMedfusionMUEvents table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying EventId in cusMedfusionMUEvents table" & @CRLF)
				ConsoleWrite("Expected Result: " & "PI_" & $commOutgoingId &" and Actual Result: " & $aData[1][0] & " -- ")
				If (StringCompare("PI_" & $commOutgoingId, $aData[1][0]) = 0)  Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "PI_" & $commOutgoingId &" and Actual Result: " & $aData[1][0] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "PI_" & $commOutgoingId &" and Actual Result: " & $aData[1][0] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;Evnet Type
				ConsoleWrite("Verifying Event Type in cusMedfusionMUEvents table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Event Type in cusMedfusionMUEvents table" & @CRLF)

				ConsoleWrite("Expected Result: " & "Provider Initiated" &" and Actual Result: " & $aData[1][1] & " -- ")
				If (StringCompare("Provider Initiated", $aData[1][1]) = 0)  Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Provider Initiated" &" and Actual Result: " & $aData[1][1] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & "Provider Initiated" &" and Actual Result: " & $aData[1][1] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;Provider
				ConsoleWrite("Verifying Provider in cusMedfusionMUEvents table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Provider in cusMedfusionMUEvents table" & @CRLF)

				ConsoleWrite("Expected Result: " & $authpvdrLogin &" and Actual Result: " & $aData[1][2] & " -- ")
				If (StringCompare($authpvdrLogin, $aData[1][2]) = 0)  Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $authpvdrLogin &" and Actual Result: " & $aData[1][2] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $authpvdrLogin &" and Actual Result: " & $aData[1][2] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				Exit
			EndIf

		;MUActivityLog - Event 514
			FileWriteLine($hFileOpen, @CRLF & " STEP 13-- VERIFY EVENT 514 DETAILS IN MUACTIVITYLOG TABLE" & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[11] & $PID & $arrQuery[12] & ";",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
			;MUActivityLogId
				ConsoleWrite("Verifying MUActivityLogId in MUActivityLog table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying MUActivityLogId in MUActivityLog table" & @CRLF)
				ConsoleWrite("Expected Result: " & $arrEvent[6] &" and Actual Result: " & $aData[2][0] & " -- ")
				If (StringCompare($arrEvent[6], $aData[2][0]) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrEvent[6]  &" and Actual Result: " & $aData[2][0] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrEvent[6] &" and Actual Result: " & $aData[2][0] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;PVID
				ConsoleWrite("Verifying PVID in MUActivityLog table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying PVID in MUActivityLog table" & @CRLF)
				$temp = Int($aData[2][2]) - 1
				ConsoleWrite("Expected Result: " & $authpvdrPVID &" and Actual Result: " & $temp & " -- ")
				If (StringCompare($authpvdrPVID, $temp) = 0)  Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $authpvdrPVID  &" and Actual Result: " & $temp & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $authpvdrPVID &" and Actual Result: " & $temp & " -- FAILED" & @CRLF)
					Exit
				EndIf

		;MUActivityLog - Event 522
			FileWriteLine($hFileOpen, @CRLF & " STEP 14-- VERIFY EVENT 522 DETAILS IN MUACTIVITYLOG TABLE" & @CRLF)

			;MUActivityLogId
				ConsoleWrite("Verifying MUActivityLogId in MUActivityLog table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying MUActivityLogId in MUActivityLog table" & @CRLF)
				ConsoleWrite("Expected Result: " & $arrEvent[9] &" and Actual Result: " & $aData[1][0] & " -- ")
				If (StringCompare($arrEvent[9], $aData[1][0]) = 0)  Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrEvent[9] &" and Actual Result: " & $aData[1][0] & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $arrEvent[9] &" and Actual Result: " & $aData[1][0] & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;PVID
				ConsoleWrite("Verifying PVID in MUActivityLog table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying PVID in MUActivityLog table" & @CRLF)
				$temp = Int($aData[1][2]) - 1
				ConsoleWrite("Expected Result: " & $authpvdrPVID &" and Actual Result: " & $temp & " -- ")
				If (StringCompare($authpvdrPVID, $temp) = 0)  Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $authpvdrPVID &" and Actual Result: " & $temp & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $authpvdrPVID &" and Actual Result: " & $temp & " -- FAILED" & @CRLF)
					Exit
				EndIf

			;SDID
				ConsoleWrite("Verifying SDID in MUActivityLog table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying SDID in MUActivityLog table" & @CRLF)
				$temp = Int($aData[1][1]) - 1
				ConsoleWrite("Expected Result: " & $sdid &" and Actual Result: " & $temp & " -- ")
				If (StringCompare($sdid, $temp) = 0) Then
					ConsoleWrite("PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $sdid &" and Actual Result: " & $temp & " -- PASSED" & @CRLF)
				Else
					ConsoleWrite("FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $sdid &" and Actual Result: " & $temp & " -- FAILED" & @CRLF)
					Exit
				EndIf

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				Exit
			EndIf

		_SQL_Close()


		FileWriteLine($hFileOpen, @CRLF & " STEP 15-- VERIFY ORDER DETAILS IN DIRECT MESSAGING OUTBOX" & @CRLF)
			Call("openMFDashboard")
			Call("openMessageLink","Direct Messaging")
			Call("verifyOrderInUI",$orderNum,$arrConfig[19],$arrConfig[20],$arrConfig[14],$arrConfig[15])

		FileWriteLine($hFileOpen, @CRLF & " STEP 16-- VERIFY ORDER DETAILS IN SES INBOX" & @CRLF)
			$PID = Run(@ComSpec & ' /c java -jar "C:/XASYST/AUTOIT/AutoIT/sesInbox.jar" ' & $orderSubject & ' ' & $fullSubject & ' ' & $orderAttachmentName & ' ' & $redPvdrEmail & ' ' & $authPvdrEmail & ' ' & $orderBody &'',"","",$STDOUT_CHILD)
			ConsoleWrite("$PID :" & $PID & @CRLF)
			ProcessWaitClose($PID)
			$output =StdoutRead($PID)

				If(StringInStr($output,"PASSED")) Then
					ConsoleWrite("Order details verified in SES Inbox" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Order Details verified in SES Inbox -- PASSED" & @CRLF)
				Else
					ConsoleWrite("Order details do not match in SES Inbox" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Order details do not match in SES Inbox -- FAILED" & @CRLF)
					Exit
				EndIf

		FileWriteLine($hFileOpen, @CRLF & " STEP 17-- VERIFY PATIENT LETTER IN PATIENT PORTAL" & @CRLF)
			ConsoleWrite("Message Subject " & $arrConfig[25] & @CRLF)
			$temp = StringSplit($arrConfig[25]," ")
			$messageSubject = Null
			For $i = 1 to $temp[0]
				$messageSubject = $messageSubject & $temp[$i]
			Next
			ConsoleWrite("New Message Subject " & $messageSubject & @CRLF)

			ConsoleWrite("Message Body " & $arrConfig[24] & @CRLF)
			$temp = StringSplit($arrConfig[24]," ")
			$messageBody = Null
			For $i = 1 to $temp[0]
				$messageBody = $messageBody & $temp[$i]
			Next
			ConsoleWrite("New Message Body " & $messageBody & @CRLF)

			$PID = Run(@ComSpec & ' /c java -jar "C:/XASYST/AUTOIT/AutoIT/TOCLetterToPatient.jar" ' & $messageSubject & ' ' & $messageBody & ' ' & $messageSentDate &'' ,"","",$STDOUT_CHILD)
			ConsoleWrite("$PID :" & $PID & @CRLF)
			ProcessWaitClose($PID)
			$output =StdoutRead($PID)

				If(StringInStr($output,"PASSED")) Then
					ConsoleWrite("Letter to patient details verified in Patient Portal" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Letter to Patient Details verified in Patient Portal -- PASSED" & @CRLF)
				Else
					ConsoleWrite("Letter to patient details do not match in Patient Portal" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Letter to patient details do not match in Patient Portal -- FAILED" & @CRLF)
					Exit
				EndIf

		ConsoleWrite("Direct Messaging Flow -- SUCCESSFULLY COMPLETED...." & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- DIRECT MESSAGING FLOW -- SUCCESSFULLY COMPLETED...." & @CRLF)

		Else
			ConsoleWrite("ERROR Attaching Patient Chart...." & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Chart...." & @CRLF)
		EndIf

	Else
		ConsoleWrite("ERROR Attaching GE CPS App...." & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Attaching GE CPS App...." & @CRLF)
	EndIf

