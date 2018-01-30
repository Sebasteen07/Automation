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
$commonStepsDir = StringReplace($currentDir , "directmessaging_receiving", "commonsteps")
Call("deleteOldLogFiles",$commonStepsDir)
$logPath = $commonStepsDir & "\ProcessLog_DirectMessaging_Receiving_" & $time & ".txt"
Call("setLogPath",$logpath)
$hFileOpen = Call("openLogFile")
$arrConfig = Call("setConfig")
$arrQuery = Call("openQueryFile")
$arrEvent = Call("openEventFile")
$arrDoctype = Call("openDoctypeFile")

;Update servicesettings for TOC flow
		FileWriteLine($hFileOpen, @CRLF & " STEP 1 -- UPDATE SERVICESETTINGS FOR DIRECT MESSAGING - RECEIVING TOC FLOW" & @CRLF)
			Call("connectDatabase",$arrConfig[4],$arrConfig[6],$arrConfig[7],$arrConfig[8])

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

If($arrConfig[9] == "Preconditions Check") Then
	ConsoleWrite("Exiting after checking pre-conditions for Direct Messaging - Receiving Flow...." & @CRLF)
	FileWriteLine($hFileOpen, _NowCalc() & "  -- Exiting after checking pre-conditions for Direct Messaging - Receiving Flow...." & @CRLF)
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

		FileWriteLine($hFileOpen, @CRLF & " STEP 4 -- CREATE A REFERRAL ORDER FOR THE PATIENT" & @CRLF)
		If(WinActive("Chart - NOT FOR PATIENT USE")) Then
			$counter = 0
			Do
				If($arrConfig[9]=="Data Generation") Then
					ConsoleWrite("Creating Order #" & $counter+1 & " of " & $arrConfig[53] & " orders"& @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Creating Order #" & $counter+1 & " of " & $arrConfig[53] & " orders" & @CRLF)
				Else
					ConsoleWrite("Creating Order #" &$counter+1 & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Creating Order #" &$counter+1 & @CRLF)
				EndIf

				Call("createNewDocument",$arrConfig[17],$arrConfig[15],$arrConfig[16])
				If (Mod($counter,2) = 0) Then
					Call("createOrder",$arrConfig[55],$arrConfig[20],$arrConfig[21],$arrConfig[15],$arrConfig[16],$arrConfig[25],$arrConfig[27],$arrConfig[28])

				Else
					Call("createOrder",$arrConfig[19],$arrConfig[20],$arrConfig[21],$arrConfig[15],$arrConfig[16],$arrConfig[25],$arrConfig[27],$arrConfig[28])
				EndIf

				If($arrConfig[9]=="Data Generation") Then
					$counter +=1
					If($counter = $arrConfig[53]) Then
						ConsoleWrite("Exiting after data generation for Direct Messaging Flow...." & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Exiting after data generation for Direct Messaging Flow...." & @CRLF)
						Exit
					EndIf
					ConsoleWrite("Wait for 1 min before next order creation" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait for 1 min before next order creation" & @CRLF)
					Sleep(60000)

				Else
					$counter = $arrConfig[53]
				EndIf
			Sleep(1000)
			Until $counter = $arrConfig[53]

			Sleep(5000)

			FileWriteLine($hFileOpen, @CRLF & " STEP 5 -- GET PATIENT DETAILS" & @CRLF)

			Call("connectDatabase",$arrConfig[4],$arrConfig[5],$arrConfig[7],$arrConfig[8])
			Local $aData,$iRows,$iColumns	;Variables to store the array data in to and the row count and the column count

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

			FileWriteLine($hFileOpen, @CRLF & " STEP 6 -- GET LAST INCOMINGTOCID FROM CUSMEDFUSIONINCOMINGTOCHEADER TABLE" & @CRLF)
			$newQuery = StringReplace($arrQuery[60],"{TOADDRESS}",$arrConfig[29])
			$newQuery = StringReplace($newQuery,"{FROMADDRESS}",$arrConfig[30])
			ConsoleWrite("Executing Query: " & $newQuery & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $newQuery & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$newQuery & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
				$lastIncomingToCId = $aData[1][0]

				ConsoleWrite("Last Incoming ToC Id is " & $lastIncomingToCId & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Last Incoming ToC Id is " & $lastIncomingToCId & @CRLF)

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

			ConsoleWrite("Wait of 6 minutes for ToC to be posted successfully to " & $arrConfig[29] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait of 6 minutes for ToC to be posted successfully to " & $arrConfig[29] & @CRLF)
			Sleep(360000)

			ConsoleWrite("Verify Incoming ToC arrived from Medfusion" & @CRLF)
			FileWriteLine($hFileOpen, @CRLF & " STEP 7 -- VERIFY INCOMING TOC ARRIVED FROM MEDFUSION" & @CRLF)

			For $i = 1 To 4 Step +1
				FileWriteLine($hFileOpen, @CRLF & " STEP 7." & $i & " -- VERIFY INCOMING TOC DETAILS FROM CUSMEDFUSIONINCOMINGTOCHEADER TABLE" & @CRLF)
				ConsoleWrite("Executing Query: " & $newQuery & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $newQuery & @CRLF)
				$iRval = _SQL_GetTable2D(-1,$newQuery & ";",$aData,$iRows,$iColumns)
				If $iRval = $SQL_OK then
					$IncomingToCId = $aData[1][0]

					If($IncomingToCId > $lastIncomingToCId) Then
						ConsoleWrite("ToC arrived from Medfusion" & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- ToC arrived from Medfusion" & @CRLF)

						;From SES Address
						ConsoleWrite("Verifying From SES Address in cusMedfusionIncomingTOCHeader table" & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying From SES Address in cusMedfusionIncomingTOCHeader table" & @CRLF)
						Call("assertData",$arrConfig[30], $aData[1][1])

						;To SES Address
						ConsoleWrite("Verifying To SES Address in cusMedfusionIncomingTOCHeader table" & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying To SES Address in cusMedfusionIncomingTOCHeader table" & @CRLF)
						Call("assertData",$arrConfig[29], $aData[1][2])

						;Date received
						$dateReceived = $aData[1][3]
						$tempDate = StringMid($dateReceived,1,4) & "/" & StringMid($dateReceived,5,2) & "/" & StringMid($dateReceived,7,2) &" " & StringMid($dateReceived,9,2) &":" & StringMid($dateReceived,11,2) &":" & StringMid($dateReceived,13,2)
						$dateInUI =  _DateFormat($tempDate, "MMM/dd/yy HH:mm:ss tt")
						ConsoleWrite("ToC Date received is " & $dateInUI & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- ToC Date received is " & $dateInUI & @CRLF)

						;Read Status
						ConsoleWrite("Verifying Read Status in cusMedfusionIncomingTOCHeader table" & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Read Status in cusMedfusionIncomingTOCHeader table" & @CRLF)
						Call("assertData",False, $aData[1][5])

						ExitLoop

					Else
						If ($i = 4) Then
							ConsoleWrite("3 attempts to retrieve ToC failed -- FAILED" & @CRLF)
							FileWriteLine($hFileOpen, _NowCalc() & "  -- 3 attemptsto retrieve ToC failed -- FAILED" & @CRLF)
							Exit
						EndIf
						ConsoleWrite("Wait of 2 minutes - next WebService Poll for successful ToC retrieval for " & $arrConfig[29] & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait of 2 minutes - next WebService Poll for successful ToC retrieval for " & $arrConfig[29] & @CRLF)
						Sleep(120000)
					EndIf

				Else
					ConsoleWrite("ERROR Querying Database...." & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
					ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
					Exit
				EndIf
			Next

			FileWriteLine($hFileOpen, @CRLF & " STEP 8 -- VERIFY INCOMING TOC DETAILS IN CUSMEDFUSIONINCOMINGTOCMESSAGE TABLE" & @CRLF)
			ConsoleWrite("Executing Query: " & $arrQuery[61] & $IncomingToCId & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[61] & $IncomingToCId & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[61] & $IncomingToCId & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
				$IncomingToCMessageId = $aData[1][0]

				ConsoleWrite("Incoming ToC Message Id is " & $IncomingToCMessageId & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Incoming ToC Message Id is " & $IncomingToCMessageId & @CRLF)

				;Attachment Status
;~ 				ConsoleWrite("Verifying Attachment Status in cusMedfusionIncomingTOCMessage table" & @CRLF)
;~ 				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Attachment Status in cusMedfusionIncomingTOCMessage table" & @CRLF)
;~ 				Call("assertData","1", $aData[1][1])

				;Subject
				$subject = "Transition of Care"
				ConsoleWrite("Verifying Subject in cusMedfusionIncomingTOCMessage table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Subject in cusMedfusionIncomingTOCMessage table" & @CRLF)
				Call("assertData",$subject, $aData[1][2])

				;Body
				$body = $aData[1][3]
				ConsoleWrite("Incoming Message body is " & $body & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Incoming Message body is " & $body & @CRLF)

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

			FileWriteLine($hFileOpen, @CRLF & " STEP 9 -- VERIFY INCOMING TOC ATTACHMENT DETAILS IN CUSMEDFUSIONINCOMINGTOCMESSAGEATTACHMENT TABLE" & @CRLF)
			ConsoleWrite("Executing Query: " & $arrQuery[62] & $IncomingToCMessageId & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[62] & $IncomingToCMessageId & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[62] & $IncomingToCMessageId & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
				;IncomingToCMessageAttachmentId
				$IncomingToCMessageAttachmentId = $aData[1][0]

				ConsoleWrite("Incoming ToC Message Attachment Id is " & $IncomingToCMessageAttachmentId & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Incoming ToC Message Attachment Id is " & $IncomingToCMessageAttachmentId & @CRLF)

				;Attachment Name
				$attachmentName = $aData[1][1]
				ConsoleWrite("Incoming ToC Message Attachment Name is " & $attachmentName & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Incoming ToC Message Attachment Name is " & $attachmentName & @CRLF)

				;Attachment Status
				$attachmentStatus = $aData[1][2]
				ConsoleWrite("Incoming ToC Message Attachment Status is " & $attachmentStatus & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Incoming ToC Message Attachment Status is " & $attachmentStatus & @CRLF)

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

			FileWriteLine($hFileOpen, @CRLF & " STEP 10 -- VERIFY INCOMING TOC ATTACHMENT CONTENT DETAILS IN CUSMEDFUSIONINCOMINGTOCATTACHMENTCONTENT TABLE" & @CRLF)
			ConsoleWrite("Executing Query: " & $arrQuery[63] & $IncomingToCMessageAttachmentId & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[63] & $IncomingToCMessageAttachmentId & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[63] & $IncomingToCMessageAttachmentId & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
				;IncomingToCAttachmentContentId
				$IncomingToCAttachmentContentId = $aData[1][0]

				ConsoleWrite("Incoming ToC Attachment Content Id is " & $IncomingToCAttachmentContentId & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Incoming ToC Attachment Content Id is " & $IncomingToCAttachmentContentId & @CRLF)

				;Attachment Content Status
				ConsoleWrite("Verifying Attachment Content Status in cusMedfusionIncomingTOCAttachmentContent table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Attachment Content Status in cusMedfusionIncomingTOCAttachmentContent table" & @CRLF)
				Call("assertData","Pending", $aData[1][1])

				;Patient Profile Id
				ConsoleWrite("Verifying PatientProfileId in cusMedfusionIncomingTOCAttachmentContent table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying PatientProfileId in cusMedfusionIncomingTOCAttachmentContent table" & @CRLF)
				Call("assertData",NULL, $aData[1][2])

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

			FileWriteLine($hFileOpen, @CRLF & " STEP 11 -- VERIFY INCOMING TOC IN DIRECT MESSAGING INBOX" & @CRLF)
				Call("openMFDashboard")
				Call("openMessageLink","Direct Messaging")
				Call("verifyIncomingToCInInbox", $dateInUI, $arrConfig[30], $arrConfig[29], $subject, $body, $attachmentName)

			FileWriteLine($hFileOpen, @CRLF & " STEP 12 -- IMPORT ToC TO PATIENT CHART" & @CRLF)
				Call("importToCToChart",$attachmentName,$arrConfig[15])

			FileWriteLine($hFileOpen, @CRLF & " STEP 13 -- DOWNLOAD ToC" & @CRLF)
				Call("downloadToC",$attachmentName,$arrConfig[31],$arrConfig[15],$arrConfig[16],$arrConfig[28])

			ConsoleWrite("Wait of 2.5 minutes for imported ToC to appear in Chart" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait of 2.5 minutes for imported ToC to appear in Chart" & @CRLF)
			Sleep(150000)

			FileWriteLine($hFileOpen, @CRLF & " STEP 14 -- VERIFY IMPORTED DOCUMENT DETAILS IN DOCUMENT TABLE" & @CRLF)
			$newQuery = StringReplace($arrQuery[28],"from",", VISDOCID  from")
			ConsoleWrite("Executing Query: " & $newQuery & $PID & $arrQuery[29] & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $newQuery & $PID & $arrQuery[29] & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$newQuery & $PID & $arrQuery[29] & ";",$aData,$iRows,$iColumns)

			If $iRval = $SQL_OK then
				;$visDocId_ImportDocument
				$visDocId_ImportDocument = $aData[1][5]
				ConsoleWrite("VisDocId for imported document is " & $visDocId_ImportDocument & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- VisDocId for imported document is " & $visDocId_ImportDocument & @CRLF)

				;Imported Document Summary
				ConsoleWrite("Verifying Summary of imported document in Document table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Summary of imported document in Document table" & @CRLF)
				Call("assertData",$arrConfig[28], $aData[1][1])

				;Imported Document Doctype
				ConsoleWrite("Verifying Doctype of imported document in Document table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Doctype of imported document in Document table" & @CRLF)
				Call("assertData",$arrDoctype[4], $aData[1][2])

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf
;---------------------------------------------------------------------------------------
			FileWriteLine($hFileOpen, @CRLF & " STEP 15 -- VERIFY IMPORTED DOCUMENT IN PATIENT CHART" & @CRLF)
			WinActivate("Chart - NOT FOR PATIENT USE")
			$index = 0
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
			While 1
				;Click first document in list
				MouseClick("left",617,319)
				While ($counter > 0)
					Send("{DOWN}")
					$counter = $counter - 1
				WEnd
				$index = $index + 1
				$counter = $index
				$str = WinGetText("Chart - NOT FOR PATIENT USE")
				If(StringInStr($str,$visDocId_ImportDocument)>0) Then
					ConsoleWrite("Imported Document arrived in Patient Chart -- PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Imported Document arrived in Patient Chart -- PASSED" & @CRLF)
					ExitLoop
				Else
					ConsoleWrite("Document did not arrive..." & @CRLF)
				EndIf
			WEnd

			FileWriteLine($hFileOpen, @CRLF & " STEP 16 -- VERIFY READ STATUS OF INCOMING TOC IN DATABASE" & @CRLF)
			$newQuery = StringReplace($arrQuery[60],"{TOADDRESS}",$arrConfig[29])
			$newQuery = StringReplace($newQuery,"{FROMADDRESS}",$arrConfig[30])
			ConsoleWrite("Executing Query: " & $newQuery & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $newQuery & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$newQuery & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
				;Read Status
				ConsoleWrite("Verifying Read Status in cusMedfusionIncomingTOCHeader table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Read Status in cusMedfusionIncomingTOCHeader table" & @CRLF)
				Call("assertData",True, $aData[1][5])

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf

			FileWriteLine($hFileOpen, @CRLF & " STEP 17 -- VERIFY ATTACHMENT STATUS OF INCOMING TOC ATTACHMENT IN DATABASE" & @CRLF)
			ConsoleWrite("Executing Query: " & $arrQuery[63] & $IncomingToCMessageAttachmentId & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Executing Query: " & $arrQuery[63] & $IncomingToCMessageAttachmentId & @CRLF)
			$iRval = _SQL_GetTable2D(-1,$arrQuery[63] & $IncomingToCMessageAttachmentId & ";",$aData,$iRows,$iColumns)
			If $iRval = $SQL_OK then
				;Attachment Content Status
				ConsoleWrite("Verifying Attachment Content Status in cusMedfusionIncomingTOCAttachmentContent table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying Attachment Content Status in cusMedfusionIncomingTOCAttachmentContent table" & @CRLF)
				Call("assertData","IMPORTED", $aData[1][1])

				;Patient Profile Id
				ConsoleWrite("Verifying PatientProfileId in cusMedfusionIncomingTOCAttachmentContent table" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verifying PatientProfileId in cusMedfusionIncomingTOCAttachmentContent table" & @CRLF)
				Call("assertData",$PatientProfileId, $aData[1][2])

				ConsoleWrite("Direct Messaging - Receiving ToC Forms Flow -- SUCCESSFULLY COMPLETED...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- DIRECT MESSAGING - RECEIVING TOC FLOW -- SUCCESSFULLY COMPLETED...." & @CRLF)

			Else
				ConsoleWrite("ERROR Querying Database...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Querying Database...." & @CRLF)
				ConsoleWrite("ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR MESSAGE: " & _SQL_GetErrMsg() & @CRLF)
				Exit
			EndIf
;-----------------------------------------------------------------------------------------------------------------
;after UI check - check DB - csuMedfusionIncomingTOc - ReadStatus, cusMedfusionIncomingTOCAttachmentContent -> statuus (IMPORTED), PAtientprofileId


		Else
			ConsoleWrite("ERROR Attaching Patient Chart...." & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Chart...." & @CRLF)
		EndIf
	Else
		ConsoleWrite("ERROR Attaching GE CPS App...." & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Attaching GE CPS App...." & @CRLF)
	EndIf
EndIf
