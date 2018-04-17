#include <IE.au3>
#include <File.au3>
#include <FileConstants.au3>
#include <MsgBoxConstants.au3>
#include <_sql.au3>
#include "..\commonsteps\CommonSteps.au3"

$time = Call("getTimestamp")
$currentDir = @ScriptDir
$commonStepsDir = StringReplace($currentDir , "directmessaging", "commonsteps")
Call("deleteOldLogFiles",$commonStepsDir)
$logPath = $commonStepsDir & "\ProcessLog_DirectMessaging_" & $time & ".txt"
Call("setLogPath",$logpath)
$hFileOpen = Call("openLogFile")

;$arrConfig = Call("setConfig")
$arrQuery = Call("openQueryFile")
$arrEvent = Call("openEventFile")

$CPSURL = Call("readConfig","CPS URL")
$CPSLogin = Call("readConfig","CPS Login UserName")
$CPSPassword = Call("readConfig","CPS Login Password")
$SQLServer = Call("readConfig","SQL Server IP")
$CPSDb = Call("readConfig","Centricity DB")
$centralDb = Call("readConfig","Central DB")
$dbUser = Call("readConfig","SQL UserName")
$dbPassword = Call("readConfig","SQL Password")
$runFlag = Call("readConfig","Flag (Preconditions Check / Data Generation / Acceptance)")
$dataGenerationCounter =  Call("readConfig","Number of order creation")

$patientFirstName =  Call("readConfig","Patient First Name (For Patient Chart)")
$patientLastName =  Call("readConfig","Patient Last Name (For Patient Chart)")
$docTypeEmailMessage = Call("readConfig","New Document (Email Message)")

$orderCategory1 =  Call("readConfig","Order Category1")
$orderCategory2 =  Call("readConfig","Order Category2")
$orderAuthPvdr =  Call("readConfig","Authorizing Provider for TOC Flow (Lastname,Firstname)")
$orderRefPvdr =  Call("readConfig","Referring provider for TOC flow (Lastname,Firstname)")
$orderInstructions =  Call("readConfig","Instructions for ToC")
$orderReferalReason =  Call("readConfig","Reason for referal")
$orderPatientLetterSubject =  Call("readConfig","Letter to Patient Subject (TOC)")
$orderPatientLetter =  Call("readConfig","Letter to Patient(TOC)")
$orderToAddress =  Call("readConfig","Receiving ToC (to address)")
$orderFromAddress =  Call("readConfig","Sending/Receiving ToC (from address)")
$orderDownloadLocation =  Call("readConfig","ToC Download Location")

;Update servicesettings for TOC flow
		FileWriteLine($hFileOpen, @CRLF & " STEP 1 -- UPDATE SERVICESETTINGS FOR DIRECT MESSAGING FLOW" & @CRLF)
			Call("connectDatabase",$SQLServer,$centralDb,$dbUser,$dbPassword)

			Local $aData,$iRows,$iColumns
			ConsoleWrite("Executing Query: " & $arrQuery[26] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[26] & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[26] & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
				If(($aData[1][0] = 0) And ($aData[2][0] == "Both") And ($aData[3][0] = 1)) Then
					ConsoleWrite("Service settings already enabled" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Service Settings already enabled" & @CRLF)

				Else
					ConsoleWrite("Executing Query: " & $arrQuery[14] & @CRLF & $arrQuery[15] & @CRLF & $arrQuery[16] & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[14] & @CRLF & $arrQuery[15] & @CRLF & $arrQuery[16] & @CRLF)
					If (_SQL_Execute(-1,$arrQuery[14]) Or _SQL_Execute(-1,$arrQuery[15]) Or _SQL_Execute(-1,$arrQuery[16])) = $SQL_ERROR then
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

If($runFlag == "Preconditions Check") Then
	ConsoleWrite("Exiting after checking pre-conditions for Direct Messaging Flow...." & @CRLF)
	FileWriteLine($hFileOpen, _NowCalc() & "  -- Exiting after checking pre-conditions for Direct Messaging Flow...." & @CRLF)
	Exit

Else

;Open CPS -Patient Chart
	FileWriteLine($hFileOpen, @CRLF & " STEP 2 -- LOGIN TO CPS" & @CRLF)
	ConsoleWrite("Start the CPS client" &@CRLF )
	FileWriteLine($hFileOpen, _NowCalc()  &" -- Start the CPS client" &@CRLF)
	Call("startCPS",$CPSURL,$CPSLogin,$CPSPassword)

	WinActivate("Centricity Practice Solution")
	If(WinActive("Centricity Practice Solution")) Then
		FileWriteLine($hFileOpen, @CRLF & " STEP 3 -- OPEN PATIENT CHART" & @CRLF)
		ConsoleWrite("Open Patient Chart" &@CRLF )
		Call("openPatientChart",$patientFirstName,$patientLastName)
		Sleep(8000)
		WinWaitActive("Chart - NOT FOR PATIENT USE")

		FileWriteLine($hFileOpen, @CRLF & " STEP 4 -- CREATE A REFERRAL ORDER FOR THE PATIENT" & @CRLF)
		If(WinActive("Chart - NOT FOR PATIENT USE")) Then
			$counter = 0
			Do
				If($runFlag=="Data Generation") Then
					ConsoleWrite("Creating Order #" & $counter+1 & " of " & $dataGenerationCounter & " orders"& @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Creating Order #" & $counter+1 & " of " & $dataGenerationCounter & " orders" & @CRLF)
				Else
					ConsoleWrite("Creating Order #" &$counter+1 & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Creating Order #" &$counter+1 & @CRLF)
				EndIf

				Call("createNewDocument",$docTypeEmailMessage,$patientFirstName,$patientLastName)
				If (Mod($counter,2) = 0) Then
					Call("createOrder",$orderCategory2,$orderAuthPvdr,$orderRefPvdr,$patientFirstName,$patientLastName,$orderPatientLetter,$orderInstructions,$orderReferalReason)

				Else
					Call("createOrder",$orderCategory1,$orderAuthPvdr,$orderRefPvdr,$patientFirstName,$patientLastName,$orderPatientLetter,$orderInstructions,$orderReferalReason)
				EndIf

				If($runFlag=="Data Generation") Then
					$counter +=1
					If($counter = $dataGenerationCounter) Then
						ConsoleWrite("Exiting after data generation for Direct Messaging Flow...." & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Exiting after data generation for Direct Messaging Flow...." & @CRLF)
						Exit
					EndIf
					ConsoleWrite("Wait for 1 min before next order creation" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait for 1 min before next order creation" & @CRLF)
					Sleep(60000)

				Else
					$counter = $dataGenerationCounter
				EndIf
			Sleep(1000)
			Until $counter = $dataGenerationCounter

			Sleep(5000)

			FileWriteLine($hFileOpen, @CRLF & " STEP 5 -- VERIFY EVENT 4 FOR ORDER CREATION" & @CRLF)

			Call("connectDatabase",$SQLServer,$CPSDb,$dbUser,$dbPassword)

			Local $aData,$iRows,$iColumns	;Variables to store the array data in to and the row count and the column count
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

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

		;Get loginname, PVID of authorizing provider
			$temp = StringSplit($orderAuthPvdr, ', ', 1)
			$newQuery = StringReplace($arrQuery[23],"LNAME","'" & $temp[1] & "'")
			$newQuery = StringReplace($newQuery,"FNAME","'" & $temp[2] & "'")
			ConsoleWrite("Executing Query: " & $newQuery & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $newQuery & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$newQuery & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
				$authpvdrLogin = $aData[1][0]
				$authpvdrPVID = Int($aData[1][1]) - 1

				ConsoleWrite("Authorizing Provider PVID is " & $authpvdrPVID & @CRLF	& "Authorizing Provider login is " & $authpvdrLogin & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Authorizing Provider PVID is " & $authpvdrPVID & @CRLF & _NowCalc() &"  -- Authorizing Provider login is " & $authpvdrLogin & @CRLF)

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

		;Get pvid of order creating provider
			$newQuery = StringReplace($arrQuery[23],"LASTNAME = LNAME and FIRSTNAME = FNAME","LOGINNAME = '" & $CPSLogin & "'")
			ConsoleWrite("Executing Query: " & $newQuery & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $newQuery & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$newQuery & ";",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
				$signPvdrPVID = Int($aData[1][1]) - 1

				ConsoleWrite("Signing provider PVID is " & $signPvdrPVID & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Signing provider PVID is " & $signPvdrPVID & @CRLF)

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

		;MUActivityLog- Event 4
			ConsoleWrite("Executing Query: " & $arrQuery[11] & $PID & $arrQuery[12] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[11] & $PID & $arrQuery[12] & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[11] & $PID & $arrQuery[12] & ";",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
			;MUActivityLogTypeId
				ConsoleWrite("Verifying MUActivityLogTypeId in MUActivityLog table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying MUActivityLogTypeId in MUActivityLog table" & @CRLF)
				Call("assertData", $arrEvent[1], $aData[1][0])

			;PVID
				ConsoleWrite("Verifying PVID in MUActivityLog table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying PVID in MUActivityLog table" & @CRLF)
				$temp = Int($aData[1][2]) - 1
				Call("assertData", $signPvdrPVID, $temp)

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

			ConsoleWrite("Wait of 3.5 for order being sent to Medfusion" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait of 3.5 for order being sent to Medfusion" & @CRLF)
			Sleep(210000)

			FileWriteLine($hFileOpen, @CRLF & " STEP 6 -- VERIFY ORDER DETAILS IN DATABASE" & @CRLF)
		;cusMedfusionOutgoingToc
			ConsoleWrite("Executing Query: " & $arrQuery[17] & $PatientProfileId & $arrQuery[18] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[17] & $PatientProfileId & $arrQuery[18] & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[17] & $PatientProfileId & $arrQuery[18] & ";",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
				$outgoingTocId = $aData[1][0]
				$orderNum = $aData[1][1]
				$sdid = Int($aData[1][2]) - 1

				ConsoleWrite("Outgoing TOC Id is " & $outgoingTocId & @CRLF	& "Order number is " & $orderNum & @CRLF & "SDID is " & $sdid & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Outgoing TOC Id is " & $outgoingTocId & @CRLF & _NowCalc() &"  -- Order number is " & $orderNum & @CRLF & _NowCalc() & "  -- SDID is " & $sdid & @CRLF)

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

			ConsoleWrite("Wait of 3.5 for status of order to return from Medfusion" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait of 3.5 for status of order to return from Medfusion" & @CRLF)
			Sleep(210000)
		;cusMedfusionOutgoingTOCstatus
			FileWriteLine($hFileOpen, @CRLF & " STEP 7 -- VERIFY ORDER DETAILS IN CUSMEDFUSIONOUTGOINGTOCSTATUS TABLE" & @CRLF)
			ConsoleWrite("Executing Query: " & $arrQuery[19] & $outgoingTocId & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[19] & $outgoingTocId & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[19] & $outgoingTocId & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
			;TOC Status returned from SES
				ConsoleWrite("Verifying TOC Status (returned from SES) in cusMedfusionOutgoingTOCstatus table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying TOC Status (returned from SES) in cusMedfusionOutgoingTOCstatus table" & @CRLF)

				ConsoleWrite("Expected Result: " & "Confirmed / Accepted" &" and Actual Result: " & $aData[1][0] & " -- ")
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
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

		;cusMedfusionOutgoingTocProvider
			FileWriteLine($hFileOpen, @CRLF & " STEP 8 -- VERIFY ORDER DETAILS IN CUSMEDFUSIONOUTGOINGTOCPROVIDER TABLE" & @CRLF)
			ConsoleWrite("Executing Query: " & $arrQuery[20] & $outgoingTocId & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[20] & $outgoingTocId & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[20] & $outgoingTocId & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
			;Status in provider table
				ConsoleWrite("Verifying Status in cusMedfusionOutgoingTocProvider table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Status in cusMedfusionOutgoingTocProvider table" & @CRLF)
				Call("assertData", "SUCCESS", $aData[1][4])

			;Authorizing Provider (IntServProv)
				ConsoleWrite("Verifying Authorizing Provider (IntServProv) in cusMedfusionOutgoingTocProvider table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Authorizing Provider (IntServProv) in cusMedfusionOutgoingTocProvider table" & @CRLF)
				$temp = StringSplit($aData[1][0], ' ', 0)

				For $i = 4	 To $temp[0]
					$temp[3] = $temp[3] & " " &$temp[$i]
				Next

				Call("assertData", $orderAuthPvdr, $temp[3] &", " & $temp[1])

			;Authorizing Provider email
			$authPvdrEmail = $aData[1][1]

			;Referring Provider (ExtServProv)
				ConsoleWrite("Verifying Referring Provider (ExtServProv) in cusMedfusionOutgoingTocProvider table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Referring Provider (ExtServProv) in cusMedfusionOutgoingTocProvider table" & @CRLF)
				$temp = StringSplit($aData[1][2], ' ', 0)

				For $i = 3	 To $temp[0]
					$temp[2] = $temp[2] & " " &$temp[$i]
				Next

				Call("assertData", $orderRefPvdr, $temp[2] &", " & $temp[1])

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
			$orderBody = StringReplace($aData[1][6]," ","")

			;Order Attachment Name
			$orderAttachmentName = $aData[1][7]

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

		ConsoleWrite("Wait of 1 min for sending letter to patient" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait of 1 min for sending letter to patient" & @CRLF)
		Sleep(60000)
		;cusMedfusionOutgoingTOCPAtient
			FileWriteLine($hFileOpen, @CRLF & " STEP 9 -- VERIFY PATIENT LETTER DETAILS IN CUSMEDFUSIONOUTGOINGTOCPATIENT TABLE" & @CRLF)
			ConsoleWrite("Executing Query: " & $arrQuery[21] & $outgoingTocId & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[21] & $outgoingTocId & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[21] & $outgoingTocId & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then

			;Status in patient table
				ConsoleWrite("Verifying Status in cusMedfusionOutgoingTocPatient table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Status in cusMedfusionOutgoingTocPatient table" & @CRLF)
				Call("assertData", "SUCCESS", $aData[1][1])

			;PateintProfileId
				ConsoleWrite("Verifying PatientProfileId in cusMedfusionOutgoingTocPatient table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying PatientProfileId in cusMedfusionOutgoingTocPatient table" & @CRLF)
				Call("assertData", $PatientProfileId, $aData[1][0])

			;CommOutgoinngId
			$commOutgoingId = $aData[1][2]

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

		ConsoleWrite("Wait of 1 min for sending letter to patient" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait of 1 min for sending letter to patient" & @CRLF)
		Sleep(60000)
		;cusMedfusionCommOutgoing
			FileWriteLine($hFileOpen, @CRLF & " STEP 10 -- VERIFY PATIENT LETTER DETAILS IN CUSMEDFUSIONCOMMOUTGOING TABLE" & @CRLF)
			ConsoleWrite("Executing Query: " & $arrQuery[22] & $commOutgoingId & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[22] & $commOutgoingId & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[22] & $commOutgoingId & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
				;PateintProfileId
				ConsoleWrite("Verifying PatientProfileId in cusMedfusionCommOutgoing table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying PatientProfileId in cusMedfusionCommOutgoing table" & @CRLF)
				Call("assertData", $PatientProfileId, $aData[1][0])

			;CommServiceType
				ConsoleWrite("Verifying CommServiceType in cusMedfusionCommOutgoing table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying CommServiceType in cusMedfusionCommOutgoing table" & @CRLF)
				Call("assertData", "Transition of Care", $aData[1][1])

			;Message Subject in portal
				ConsoleWrite("Verifying Message Subject in cusMedfusionCommOutgoing table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Message Subject in cusMedfusionCommOutgoing table" & @CRLF)
				Call("assertData", $orderPatientLetterSubject, $aData[1][2])

			;Created by User
				ConsoleWrite("Verifying user creating the communication in cusMedfusionCommOutgoing table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying user creating the communication in cusMedfusionCommOutgoing table" & @CRLF)
				Call("assertData", $authpvdrLogin, $aData[1][3])

			;CommSentStatus
				ConsoleWrite("Verifying CommSentStatus in cusMedfusionCommOutgoing table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying CommSentStatus in cusMedfusionCommOutgoing table" & @CRLF)
				Call("assertData", "1", $aData[1][4])

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
				Call("assertData", $sdid, $DocumentId)

			;CommSentDate
				$messageSentDate = $aData[1][7]

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

		;cusMedfusionMUEvents - Event 514
			FileWriteLine($hFileOpen, @CRLF & " STEP 11 -- VERIFY EVENT 514 DETAILS IN CUSMEDFUSIONMUEVENTS TABLE" & @CRLF)
			ConsoleWrite("Executing Query: " &$arrQuery[9] & $PatientProfileId & " and sdid = '" & $sdid & "'" & $arrQuery[10] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[9] & $PatientProfileId & " and sdid = '" & $sdid & "'" & $arrQuery[10] & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[9] & $PatientProfileId & " and sdid = '" & $sdid & "'" & $arrQuery[10] & ";",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
			;EventId
				ConsoleWrite("Verifying EventId in cusMedfusionMUEvents table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying EventId in cusMedfusionMUEvents table" & @CRLF)
				Call("assertData", "TOC_" & $outgoingTocId & "_" & $sdid, $aData[2][0])

			;Evnet Type
				ConsoleWrite("Verifying Event Type in cusMedfusionMUEvents table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Event Type in cusMedfusionMUEvents table" & @CRLF)
				Call("assertData", "Transition of Care", $aData[2][1])

			;Provider
				ConsoleWrite("Verifying Provider in cusMedfusionMUEvents table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Provider in cusMedfusionMUEvents table" & @CRLF)
				Call("assertData", $authpvdrLogin, $aData[2][2])

		;cusMedfusionMUEvents - Event 522
				FileWriteLine($hFileOpen, @CRLF & " STEP 12 -- VERIFY EVENT 522 DETAILS IN CUSMEDFUSIONMUEVENTS TABLE" & @CRLF)
			;EventId
				ConsoleWrite("Verifying EventId in cusMedfusionMUEvents table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying EventId in cusMedfusionMUEvents table" & @CRLF)
				Call("assertData", "PI_" & $commOutgoingId & "_" & $sdid, $aData[1][0])

			;Evnet Type
				ConsoleWrite("Verifying Event Type in cusMedfusionMUEvents table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Event Type in cusMedfusionMUEvents table" & @CRLF)
				Call("assertData", "Provider Initiated", $aData[1][1])

			;Provider
				ConsoleWrite("Verifying Provider in cusMedfusionMUEvents table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Provider in cusMedfusionMUEvents table" & @CRLF)
				Call("assertData", $authpvdrLogin, $aData[1][2])

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

		;MUActivityLog - Event 514
			FileWriteLine($hFileOpen, @CRLF & " STEP 13-- VERIFY EVENT 514 DETAILS IN MUACTIVITYLOG TABLE" & @CRLF)
			ConsoleWrite("Executing Query: " & $arrQuery[11] & $PID & $arrQuery[12] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[11] & $PID & $arrQuery[12] & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[11] & $PID & $arrQuery[12] & ";",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
			;MUActivityLogId
				ConsoleWrite("Verifying MUActivityLogId in MUActivityLog table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying MUActivityLogId in MUActivityLog table" & @CRLF)
				Call("assertData", $arrEvent[6], $aData[2][0])

			;PVID
				ConsoleWrite("Verifying PVID in MUActivityLog table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying PVID in MUActivityLog table" & @CRLF)
				$temp = Int($aData[2][2]) - 1
				Call("assertData", $authpvdrPVID, $temp)

		;MUActivityLog - Event 522
			FileWriteLine($hFileOpen, @CRLF & " STEP 14-- VERIFY EVENT 522 DETAILS IN MUACTIVITYLOG TABLE" & @CRLF)

			;MUActivityLogId
				ConsoleWrite("Verifying MUActivityLogId in MUActivityLog table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying MUActivityLogId in MUActivityLog table" & @CRLF)
				Call("assertData", $arrEvent[9], $aData[1][0])

			;PVID
				ConsoleWrite("Verifying PVID in MUActivityLog table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying PVID in MUActivityLog table" & @CRLF)
				$temp = Int($aData[1][2]) - 1
				Call("assertData", $authpvdrPVID, $temp)

			;SDID
				ConsoleWrite("Verifying SDID in MUActivityLog table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying SDID in MUActivityLog table" & @CRLF)
				$temp = Int($aData[1][1]) - 1
				Call("assertData", $sdid, $temp)

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

		_SQL_Close()


		FileWriteLine($hFileOpen, @CRLF & " STEP 15-- VERIFY ORDER DETAILS IN DIRECT MESSAGING OUTBOX" & @CRLF)
			Call("openMFDashboard")
			Call("openMessageLink","Direct Messaging")
			Call("verifyOrderInUI",$orderNum,$orderAuthPvdr,$orderRefPvdr,$patientFirstName,$patientLastName,$orderFromAddress)

		FileWriteLine($hFileOpen, @CRLF & " STEP 16-- VERIFY ORDER DETAILS IN SES INBOX" & @CRLF)
			$SESjar = StringReplace($currentDir, "directmessaging", "jarfiles")  & "\sesInbox.jar"
			$PID = Run(@ComSpec & ' /c java -jar ' & $SESjar & ' ' & $orderSubject & ' ' & $fullSubject & ' ' & $orderAttachmentName & ' ' & $redPvdrEmail & ' ' & $authPvdrEmail & ' ' & $orderBody &'',"","",$STDOUT_CHILD)
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
			ConsoleWrite("Message Subject " & $orderPatientLetterSubject & @CRLF)
			$messageSubject = StringReplace($orderPatientLetterSubject," ","")
			ConsoleWrite("New Message Subject " & $messageSubject & @CRLF)

			ConsoleWrite("Message Body " & $orderPatientLetter & @CRLF)
			$messageBody = StringReplace($orderPatientLetter," ","")
			ConsoleWrite("New Message Body " & $messageBody & @CRLF)

			$TOCjar = StringReplace($currentDir, "directmessaging", "jarfiles")  & "\TOCLetterToPatient.jar"
			$PID = Run(@ComSpec & ' /c java -jar ' & $TOCjar & ' ' & $messageSubject & ' ' & $messageBody & ' ' & $messageSentDate &'' ,"","",$STDOUT_CHILD)
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
EndIf
