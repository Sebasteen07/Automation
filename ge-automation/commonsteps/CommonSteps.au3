#include <IE.au3>
#include <MsgBoxConstants.au3>
#include <Array.au3>
#include <File.au3>
#include <Date.au3>
#include <DateTimeConstants.au3>
#include <AutoItConstants.au3>
#include <FileConstants.au3>
#include <WinAPIFiles.au3>
#include <String.au3>
#include <GuiComboBox.au3>
#include <GUIConstantsEx.au3>
#include <TreeViewConstants.au3>
#include <WindowsConstants.au3>
#include <_sql.au3>

$title = "Centricity Practice Solution"
$edtId = "Edit1"
$edtPw = "Edit2"
$btnButton1 = "Button1"
$btnLogin = "Button2"
$btnMsgYes = "Button3"
$btnMsgOk = "Button4"

$path="..\commonsteps\config.csv"
$logPath = ""
$queryPath = "..\commonsteps\queries.csv"
$eventsPath = "..\commonsteps\events.csv"
$doctypePath = "..\commonsteps\doctype.csv"

;-----Function to read from config file & set config variables
;-----Returns array of config variables
Func setConfig()
	;ConsoleWrite(@CRLF & "Reading configuration varaibles"&@CRLF)
	Local $arrConfigRead
	Local $arrConfig[1] = ["Config Values Array"]

	If Not _FileReadToArray($path,$arrConfigRead,4,":-") Then
		ConsoleWrite("Unable to read config file"&@CRLF)
		Exit

	Else
		_FileReadToArray($path,$arrConfigRead,4,":-")

		Local $iRows = UBound($arrConfigRead, $UBOUND_ROWS) ; Number of config rows.
		Local $iCols = UBound($arrConfigRead, $UBOUND_COLUMNS) ; Number of config cols.
		;ConsoleWrite("Configuration variables set"&@CRLF)

			For $row = 0 To $iRows - 1

				Switch StringLower($arrConfigRead[$row][0])
					Case "cps url"
					    _ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "cps login username"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "cps login password"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "sql server ip"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "centricity db"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "central db"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "sql username"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "sql password"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "patient first name to be appended with timestamp (for patient creation in cps)"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "patient's email id to be appended with timestamp (for patient creation in cps)"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "patient count"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "patient portal url"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "patient portal login"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "patient first name (for patient chart)"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "patient last name (for patient chart)"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "new document (email message)"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "new document (asthma visit)"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "order category1"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "authorizing provider for toc flow (lastname,firstname)"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "referring provider for toc flow (lastname,firstname)"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "ses inbox url"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "ses username"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "ses password"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "letter to patient(toc)"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "letter to patient subject (toc)"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "number of order creation"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "number of toc creation"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "order category2"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "number of visits to be created"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "flag (preconditions check / data generation / acceptance)"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "number of selfregistered patients to be created"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "number of messages to be created"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "email message subject"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "email message body"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "reply to email message"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "number of appointments to be created"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "aas subject"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "aas body"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "reply to aas"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "number of aas to be created"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "rx request medication name"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "number of rx requests to be generated"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "rx request dosage"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "rx request quantity"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "rx request refills"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "rx request prescription number"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "rx request notes"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "rx request pharmacy"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "rx request pharmacy phone"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "discrete form name"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "discrete form medication name"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "discrete form medication frequency"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "discrete form medication frequency type(day/week/month/year)"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "discrete form allergy(peanuts / eggs / seafood)"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "discrete form medication name obs term"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "discrete form allergies obs term"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "number of health forms to be filled"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "receiving toc (to address)"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "sending/receiving toc (from address)"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "toc download location"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "instructions for toc"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "reason for referal"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "precheck facility name"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "precheck resource name"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "precheck appointment date(mm/dd/yyyy)"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "precheck responsible provider"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "precheck appointment start time"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])

					Case "precheck appointment stop time(as per slot)"
						_ArrayAdd($arrConfig,$arrConfigRead[$row][1])
				EndSwitch
			Next

		Return($arrConfig)
	EndIf
EndFunc



;-----Function to open the log file in append mode
;-----Returns handle of log file
Func openLogFile()
		Local $hFileOpen = FileOpen($logPath, $FO_APPEND)
		If $hFileOpen = -1 Then
			MsgBox($MB_SYSTEMMODAL, "", "An error occurred while writing process log file.")
			Exit
		EndIf
		Return($hFileOpen)
EndFunc



;-----Sets path for log file
;-----Input Parameter -
Func setLogPath($mypath)
	Assign($logPath,$mypath,$ASSIGN_FORCEGLOBAL)
EndFunc



;-----Get current timestamp
Func getTimestamp()
	$EPOCH = "1970/01/01 00:00:00"
	$NOW = _NowCalc()
	$timestamp = _DateDiff("s", $EPOCH, $NOW)
	Return($timestamp)
EndFunc



;-----Function to delete old log files
;-----Input PArameter - path to commonsteps directory
Func deleteOldLogFiles($commonStepsDir)
	ConsoleWrite("METHOD: deleteOldLogFiles() started" & @CRLF)
	;get date for deletion
	$deleteDate = _DateAdd('d', -7, _NowCalcDate())
	ConsoleWrite("Date before which log files to be deleted is " & $deleteDate & @CRLF)

	;split deletion date into dd,mm,yyyy
	Local $aMyDate, $aMyTime
	_DateTimeSplit($deleteDate, $aMyDate, $aMyTime)

	;get list of all log files
	$arrFile = _FileListToArray($commonStepsDir,"ProcessLog_*.txt",$FLTA_FILES)

	For $i = 1 To $arrFile[0]
		$arrTime = FileGetTime($commonStepsDir &"\" & $arrFile[$i],$FT_CREATED,$FT_ARRAY)
		If(($arrTime[2]<$aMyDate[3]) Or ($arrTime[1]<$aMyDate[2]) Or ($arrTime[0]<$aMyDate[1])) Then
			FileDelete ( $commonStepsDir & "\" & $arrFile[$i] )
		EndIf
	Next
	ConsoleWrite("METHOD: deleteOldLogFiles() ended" & @CRLF)
EndFunc



;-----Function to read from query file
;-----Returns array of queries in query file
Func openQueryFile()
	Local $arrQuery

	If Not _FileReadToArray($queryPath, $arrQuery, 0) Then
		ConsoleWrite("Unable to read query file"&@CRLF)
		Exit

	Else
		_FileReadToArray($queryPath,$arrQuery,Default)
		Local $iRows = UBound($arrQuery, $UBOUND_ROWS) ; Number of config rows.
		Local $iCols = UBound($arrQuery, $UBOUND_COLUMNS) ; Number of config cols.
		ConsoleWrite("Queries set"&@CRLF)
		Return($arrQuery)
	EndIf
EndFunc



;-----Function to read from events file
;-----Returns array of eventids
Func openEventFile()
	Local $arrEventRead
	Local $arrEvent[1] = ["Event Ids Array"]

	If Not _FileReadToArray($eventsPath,$arrEventRead,4,":-") Then
		ConsoleWrite("Unable to read events file"&@CRLF)
		Exit

	Else
		_FileReadToArray($eventsPath,$arrEventRead,4,":-")

		Local $iRows = UBound($arrEventRead, $UBOUND_ROWS) ; Number of config rows.
		Local $iCols = UBound($arrEventRead, $UBOUND_COLUMNS) ; Number of config cols.
		;ConsoleWrite("Configuration variables set"&@CRLF)

			For $row = 0 To $iRows - 1

				Switch StringLower($arrEventRead[$row][0])
					Case "patient registration"
					    _ArrayAdd($arrEvent,$arrEventRead[$row][1])

					Case "toc creation"
					    _ArrayAdd($arrEvent,$arrEventRead[$row][1])

					Case "toc successfully received at ses"
					    _ArrayAdd($arrEvent,$arrEventRead[$row][1])

					Case "provider initiated message sent"
					    _ArrayAdd($arrEvent,$arrEventRead[$row][1])

					Case "clinical visit summary sent"
					    _ArrayAdd($arrEvent,$arrEventRead[$row][1])

					Case "ccd view"
					    _ArrayAdd($arrEvent,$arrEventRead[$row][1])

					Case "ccd download"
					    _ArrayAdd($arrEvent,$arrEventRead[$row][1])

					Case "ccd transmit"
					    _ArrayAdd($arrEvent,$arrEventRead[$row][1])

					Case "provider reply"
					    _ArrayAdd($arrEvent,$arrEventRead[$row][1])

					Case "secure message sent by patient"
					    _ArrayAdd($arrEvent,$arrEventRead[$row][1])

				EndSwitch
			Next
		;_ArrayDisplay($arrEvent, "1D - Single")
		Return($arrEvent)
	EndIf
EndFunc



;-----Function to read from doctype file
;-----Returns array of doctypeids
Func openDoctypeFile()
	Local $arrDoctypeRead
	Local $arrDoctype[1] = ["Doctype Ids Array"]

	If Not _FileReadToArray($doctypePath,$arrDoctypeRead,4,":-") Then
		ConsoleWrite("Unable to read doctype file"&@CRLF)
		Exit

	Else
		_FileReadToArray($doctypePath,$arrDoctypeRead,4,":-")

		Local $iRows = UBound($arrDoctypeRead, $UBOUND_ROWS) ; Number of config rows.
		Local $iCols = UBound($arrDoctypeRead, $UBOUND_COLUMNS) ; Number of config cols.
		;ConsoleWrite("Configuration variables set"&@CRLF)

			For $row = 0 To $iRows - 1

				Switch StringLower($arrDoctypeRead[$row][0])
					Case "office visit"
					    _ArrayAdd($arrDoctype,$arrDoctypeRead[$row][1])

					Case "clinical visit summary"
					    _ArrayAdd($arrDoctype,$arrDoctypeRead[$row][1])

					Case "append"
					    _ArrayAdd($arrDoctype,$arrDoctypeRead[$row][1])

					Case "external other"
					    _ArrayAdd($arrDoctype,$arrDoctypeRead[$row][1])

					Case "clinical summary"
					    _ArrayAdd($arrDoctype,$arrDoctypeRead[$row][1])

					Case "lab report"
					    _ArrayAdd($arrDoctype,$arrDoctypeRead[$row][1])

					Case "imaging report"
					    _ArrayAdd($arrDoctype,$arrDoctypeRead[$row][1])

				EndSwitch
			Next
		;_ArrayDisplay($arrEvent, "1D - Single")
		Return($arrDoctype)
	EndIf
EndFunc



;-----Function to start the CPS Client
;-----Input parameters -> CPS details from config file
Func startCPS($CPSURL, $CPSLogin, $CPSPassword)
	ConsoleWrite("METHOD: startCPS() started" & @CRLF)
	;$arrConfig = Call("setConfig")
	$hFileOpen = Call("openLogFile")

	; Killing all earlier IE sessions
	RunWait('taskkill /F /IM "iexplore.exe"')
	RunWait('taskkill /F /IM "AutoIt32.exe"')

	;Initiate GE CPS Application
	_IECreate ($CPSURL)
	WinWaitActive ($title)
	sleep(3000)

	;FileWriteLine($hFileOpen, @CRLF&@CRLF&@CRLF  &"STEP 1 -- LOGIN PROCESS" & @CRLF)------------This to be written in calling function
	; Sending user name and password
	ControlSend($title, "", $edtId, $CPSLogin) ; UserName read from config file
	ControlSend($title, "", $edtPw, $CPSPassword) ; Password read from config file
	ConsoleWrite("Sent Login credentials to GE CPS" &@CRLF )
	FileWriteLine($hFileOpen, _NowCalc()  &" -- Sent Login credentials to GE CPS" &@CRLF)
	Sleep (5000)

	; Clicking on Login button
	ControlClick($title, "", $btnLogin)
	ConsoleWrite("Clicked on Login button" &@CRLF )
	FileWriteLine($hFileOpen, _NowCalc() & "  -- Clicked on Login button" &@CRLF )
	Sleep (10000)

	ControlClick($title,"", "[CLASS:Button; INSTANCE:1]")
	Sleep(3000)
	ControlClick($title, "", "[CLASS:Button; INSTANCE:3]")
	ConsoleWrite("Clicked on Loggedin warning message Yes" & @CRLF)
	FileWriteLine($hFileOpen, _NowCalc() & "  -- Clicked on Loggedin warning message Yes" & @CRLF)
	Sleep (5000)

	ConsoleWrite("GE CPS is Running" &@CRLF )
	FileWriteLine($hFileOpen, _NowCalc() & "  -- GE CPS is Running" &@CRLF )
	Sleep(15000)

	Local $aList = WinList($title)
	; Loop through the array displaying only visable windows with a title.
	Local $iRows = UBound($aList, $UBOUND_ROWS)
	ConsoleWrite("Active window(s) " & $iRows & @CRLF)
	FileWriteLine($hFileOpen, _NowCalc() & "  -- Active window(s) " & $iRows & @CRLF)

	For $i = 1 To $aList[0][0]
        If $aList[$i][0] <> "" And BitAND(WinGetState($aList[$i][1]), 2) Then
            ;MsgBox($MB_SYSTEMMODAL, "", "Title: " & $aList[$i][0] & @CRLF & "Handle: " & $aList[$i][1])
			ConsoleWrite("GE CPS active window title - "& $i & " - " & $aList[$i][0] &  " Handle: " & $aList[$i][1]&@CRLF )
        EndIf
	Next

	$iRows = $aList[0][0]
	ConsoleWrite("Active windows - " & $iRows & @CRLF)

	Sleep(15000)


	;GE CPS loggedin user warning
	ControlClick($title, "", $btnMsgYes)
	ConsoleWrite("YES click sent to Message Box" & @CRLF)
	FileWriteLine($hFileOpen, _NowCalc() & "  -- YES click sent to Message Box" & @CRLF)
	Sleep(15000)

	ControlClick($title, "", $btnButton1)
	ConsoleWrite("First OK click sent to Message Box" & @CRLF)
	FileWriteLine($hFileOpen, _NowCalc() & "  -- First OK click sent to Message Box" & @CRLF)
	Sleep(15000)


	$iRows = $aList[0][0]
	While $iRows > 1
		;ControlClick($title, "", $btnMsgOk)
		;ControlSend($title,"",$btnMsgOk,"{Enter}")
		ConsoleWrite("WHILE START Active windows - " & $iRows & @CRLF)

		Send("{Enter}")
		$aList = WinList($title)
		;$iRows = UBound($aList, $UBOUND_ROWS)
		ConsoleWrite("OK click sent to Message Box - Active window " & $iRows & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- OK click sent to Message Box - Active window " & $iRows & @CRLF)
		Sleep(20000)

		;Check wheather message boxes are coming from GE CPS
		$aList = WinList($title)
		$iRows = $aList[0][0]

		;Sleep(5000)

		If ( $iRows = 0 ) Then
			ExitLoop
		EndIf

		ConsoleWrite("WHILE END Active windows - " & $iRows & @CRLF)
	WEnd

    RunWait('taskkill /F /IM "iexplore.exe"')
	ConsoleWrite("IE Process Kill which initiated GE CPS" &@CRLF )
	FileWriteLine($hFileOpen, _NowCalc() & "  -- IE Process Kill which initiated GE CPS" &@CRLF )
	Sleep(10000)

	ConsoleWrite("METHOD: startCPS() ended" & @CRLF)
EndFunc



;-----Funcion to open Patient Chart
;-----Input parameters -> Patient Details from config file
;-----Input parameter - $Fname = Patient's First Name
;-----Input parameter - $Lname = Patient's Last Name
Func openPatientChart($ptFName,$ptLName)
	ConsoleWrite("METHOD: openPatientChart() started" & @CRLF)
	;$arrConfig = Call("setConfig")
	$hFileOpen = Call("openLogFile")

	WinActivate($title)
	ConsoleWrite("Activate GE CPS window" &@CRLF )

	Local $oIEEmbed =_IEAttach("Centricity Practice Solution","embedded",1)
	Sleep(1000)
	ConsoleWrite("Activating GE CPS App" &@CRLF)
	FileWriteLine($hFileOpen, _NowCalc() & "  -- Activating GE CPS App" &@CRLF)
	WinWaitActive($title)
	ConsoleWrite("Active Window is " & WinGetTitle("[ACTIVE]") & @CRLF)

	If ( IsObj($oIEEmbed) ) Then
			ConsoleWrite("Attached successfully to IE instance running under GE app" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Attached successfully to IE instance running under GE app" & @CRLF)

			ConsoleWrite('@@ Debug(' & @ScriptLineNumber & ') : $oIEEmbed = ' & $oIEEmbed & @CRLF & '>Error code: ' & @error & '    Extended code: 0x' & Hex(@extended) & @CRLF) ;### Debug Console
			ConsoleWrite("Embedded IE Url " &  _IEPropertyGet($oIEEmbed, "locationurl") &@CRLF )
			sleep(1000)

			Local $oDoc = _IEBodyReadHTML($oIEEmbed)
			Local $oLinkCollection = _IELinkGetCollection($oIEEmbed)

			ConsoleWrite("Finding links collection" & @CRLF)
			If ( IsObj($oLinkCollection) ) Then
				;ConsoleWrite( @CRLF & "Links Collection --> "  &  IsObj($oLinkCollection) & " -------- " & @CRLF )
				;ConsoleWrite( @CRLF & "Button Count --> "  &  $oBtnCollection.count() & " -------- " & @CRLF )
				$lCount = 0
					For $oLink in $oLinkCollection
					;ConsoleWrite(  "Link " & $lCount & " --> "  & $oLink & " -------- " & @CRLF )
					$lCount = $lCount + 1
					Next
				;_ArrayDisplay($oLinkCollection)
				ConsoleWrite("For loop finished for Link Collection" & @CRLF )
				ConsoleWrite("Links found - " & $lCount & @CRLF)
			EndIf

			ConsoleWrite("Clicking CHART link of GE CPS App...." & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Clicking CHART link of GE CPS App" & @CRLF)
			Sleep(5000)
			_IELinkClickByIndex($oIEEmbed,0)

			Local $chartWnd = WinWaitActive("Chart Desktop")
			ConsoleWrite("Active Window is " & WinGetTitle("[ACTIVE]") & @CRLF)
			WinSetState($chartWnd,"",@SW_MAXIMIZE)

			If($chartWnd) Then
				ConsoleWrite("Click on FIND PATIENT"& @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Click on FIND PATIENT"& @CRLF)
				MouseClick("left",33,68)
				$findPatientWnd = WinWaitActive("Find Patient")

				If ($findPatientWnd) Then
					ControlFocus($findPatientWnd,"","[CLASS:Edit; INSTANCE:6]")
					ControlSetText($findPatientWnd,"","[CLASS:Edit; INSTANCE:6]",$ptLName &", " & $ptFName)
					ConsoleWrite("PatientName" & @CRLF)
					Sleep(15000)
;~ 					Send("{TAB}")
;~ 					Sleep(10000)
;~ 					Send("{ENTER}")
					ControlFocus($findPatientWnd,"","[CLASS:Button; INSTANCE:6]")
					Sleep(10000)
					ControlClick($findPatientWnd,"","[CLASS:Button; INSTANCE:6]")
					ConsoleWrite("Search Clicked" &@CRLF)
					Sleep(15000)
					ControlFocus($findPatientWnd,"","[CLASS:Button; INSTANCE:1]")
					Sleep(10000)
					ControlClick($findPatientWnd,"","[CLASS:Button; INSTANCE:1]")
;~ 					Send("{TAB 4}")
;~ 					Sleep(10000)
;~ 					Send("{ENTER}")
					ConsoleWrite("OK LCicked" & @CRLF)
					Sleep(8000)

						$patientChartWnd = WinWaitActive("Chart - NOT FOR PATIENT USE")
						If ($patientChartWnd) Then
							ConsoleWrite("Patient Chart successfully opened" & @CRLF)
							FileWriteLine($hFileOpen, _NowCalc() & "  -- Patient Chart successfully opened" & @CRLF)

						Else
							ConsoleWrite("ERROR Opening Patient Chart...." & @CRLF)
							FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Opening Patient Chart...." & @CRLF)
						EndIf

				Else
					ConsoleWrite("ERROR Inactive Find Patient Window...." & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Inactive Find Patient Window...." & @CRLF)
				EndIf
			Else
				ConsoleWrite("ERROR Opening Chart Desktop...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Opening Chart Desktop...." & @CRLF)
			EndIf
	Else
		ConsoleWrite("ERROR Attaching IE Process Within GE CPS App...." & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Attaching IE Process Within GE CPS App...." & @CRLF)
		Exit
	EndIf
	ConsoleWrite("METHOD: openPatientChart() ended" & @CRLF)
EndFunc



;-----Function to open Medfusion Dashboard
Func openMFDashboard()
	ConsoleWrite("METHOD: openMFDashboard() started" & @CRLF)
	;$arrConfig = Call("setConfig")
	$hFileOpen = Call("openLogFile")

	WinActivate($title)
	ConsoleWrite("Activating GE CPS App" &@CRLF)
	FileWriteLine($hFileOpen, _NowCalc() & "  -- Activating GE CPS App" &@CRLF)
	WinWaitActive($title)
	ConsoleWrite("Active Window is " & WinGetTitle("[ACTIVE]") & @CRLF)

		MouseClick("left",689,85)
		MouseWheel($MOUSE_WHEEL_DOWN,10)
		Sleep(1000)
		MouseClick("left",345,545,2)
		WinWaitActive("Medfusion Dashboard")
		Local $sText1 = WinGetTitle("[ACTIVE]")
		ConsoleWrite("Active Window is " & $sText1 & @CRLF)

	ConsoleWrite("METHOD: openMFDashboard() ended" & @CRLF)
EndFunc



;-----Funtion to verify that patient arrived in Medfusion Dashboard
;-----Input parameter - $intPtCount = Initial Patient count in MEdfusion Dashboard
;-----Input parameter - $lastName = Patient's Last Name to search in Medfusion Dashboard
;-----Returns new patient count if creaated patient didnot arrive in Dashboard
;-----Returns 0 if created patient arrived in Dashboard
Func verifyPatientArrivalInDashboard($intPtCount,$lastName)
	ConsoleWrite("METHOD: verifyPatientArrivalInDashboard() started" & @CRLF)
	$hFileOpen = Call("openLogFile")

	Local $arrNewPtCount
	Local $intNewPtCount=0

	While ($intNewPtCount <= $intPtCount)
		WinClose("Medfusion Dashboard")
		Sleep(5000)
		Call("openMFDashboard")

		If (WinActive("Medfusion Dashboard")) Then
			ConsoleWrite("Again Reading Demographics Dashboard Count" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Again Reading Demographics Dashboard Count" & @CRLF)
			Local $Text2 = WinGetText("Medfusion Dashboard")
			$arrNewPtCount = _StringBetween($Text2,"Demographics Dashboard", "")
			$intNewPtCount = Int($arrNewPtCount[0])
			ConsoleWrite("New Demographics Dashboard Count is " & $intNewPtCount & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- New Demographics Dashboard Count is " & $intNewPtCount & @CRLF)

		Else
			ConsoleWrite("ERROR Opening Medfusion Dashboard for re-reading patient count...." & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Opening Medfusion Dashboard for re-reading patient count...." & @CRLF)
		EndIf
	WEnd

	WinActivate("Medfusion Dashboard")
			;Open DEmograhics Dashboard
			ControlClick("Medfusion Dashboard","","[NAME:DemographicsLabel]")
			Sleep(5000)
			WinWaitActive("Medfusion Demographic Import Dashboard")

			If Not (WinActive("Medfusion Demographic Import Dashboard")) Then
				ConsoleWrite("ERROR Opening Medfusion Demographic Import Dashboard...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Opening Medfusion Demographic Import Dashboard...." & @CRLF)
				Exit

			Else
				ConsoleWrite("Demographics Dashboard opened" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Demographics Dashboard opened" & @CRLF)
				ConsoleWrite("Find Patient by Last Name" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Find Patient by Last Name" & @CRLF)
				ControlFocus("Medfusion Demographic Import Dashboard","","[NAME:TextSearchLastName]")
				ControlSend("Medfusion Demographic Import Dashboard","","[NAME:TextSearchLastName]",$lastName)

				ControlClick("Medfusion Demographic Import Dashboard","","[NAME:ButtonSearch]")
				Sleep(8000)

				If(WinActive("No Demographics to Display")) Then
					ConsoleWrite("Created Patient did not arrive in Dashboard" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Created Patient did not arrive in Dashboard" & @CRLF)
					ControlClick("No Demographics to Display","","[CLASS:Button; INSTANCE:1]")
					SLeep(10000)
					WinClose("Medfusion Demographic Import Dashboard")
					Sleep(10000)
					ConsoleWrite("Wait for next patient to arrive in Dashboard" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Wait for next patient to arrive in Dashboard" & @CRLF)
					Sleep(180000)
					Return($intNewPtCount)

				Else
					ConsoleWrite("Created Patient arrived in Dashboard" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Created Patient arrived in Dashboard" & @CRLF)
					Return(0)
				EndIf

			EndIf
	ConsoleWrite("METHOD: verifyPatientArrivalInDashboard() ended" & @CRLF)
EndFunc



;-----Function to create a new document in patient chart
;-----Input parameter - Type of New Document to be created (Email / Office Visit etc.)
;-----Input parameter - $Fname = Patient's First Name
;-----Input parameter - $Lname = Patient's Last Name
Func createNewDocument($DocType,$Fname,$Lname)
	ConsoleWrite("METHOD: createNewDocument() started" & @CRLF)
	;$arrConfig = Call("setConfig")
	$hFileOpen = Call("openLogFile")

	WinActivate("Chart - NOT FOR PATIENT USE")
	;Code to click New Document
		Local $aCoord = PixelSearch(15, 190, 94, 296, 3245766)
		Sleep(1000)
		MouseMove($aCoord[0]+10,$aCoord[1]+5)
		MouseClick("left",$aCoord[0]+10,$aCoord[1]+5)

;After New Document Click
		$selectDoc = WinWaitActive("Update Chart")
		ConsoleWrite("Activating New document Selection Window" &@CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Activating New Document Selection Window" &@CRLF)
		ConsoleWrite("Active Window is " & WinGetTitle("[ACTIVE]") & @CRLF)

		If( WinActive($selectDoc) ) Then
			ConsoleWrite("Enter the type of document to be created" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Enter the type of document to be created" & @CRLF)

			ControlFocus("Update Chart","","[CLASS:ComboLBox; INSTANCE:1]")
			Send("{DOWN}")

			For $i = 0 To 40
				$text_v_okne = ControlGetText("Update Chart","","[CLASS:Edit; INSTANCE:1]")
				;MsgBox(0,"Check","Loop: "&$i&" "&$text_v_okne)
				;$text_v_okne = WinGetText("Update Chart")
				If StringInStr($text_v_okne, $DocType) > 0 Then
					ControlFocus("Update Chart","","[CLASS:Button; INSTANCE:5]")
					Sleep(3000)
					ControlClick("Update Chart","","[CLASS:Button; INSTANCE:5]")
					$i = 40
				EndIf
				ControlSend("Update Chart","","[CLASS:ComboLBox; INSTANCE:1]",'{Down}')
			Next
			$newDocument = "Update - " & $Fname & " " & $Lname
			;ConsoleWrite($newDocument & @CRLF)
			WinWaitActive($newDocument)
			WinSetState($newDocument,"",@SW_MAXIMIZE)
		Else
			ConsoleWrite("ERROR Opening New Document Selection Window...." & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Opening New Document Selection Window...." & @CRLF)
		EndIf

	ConsoleWrite("METHOD: createNewDocument() ended" & @CRLF)
EndFunc



;-----Function to create a new order
;-----Input parameters - $orderCat = Category of order to be created
;-----Input parameters - $authpvdr = Authorizing Provider for order (From)
;-----Input parameters - $refpvdr = Referring Provider for order (To)
;-----Input parameters - $Fname = Patient's First Name
;-----Input parameters - $Lname = Patient's Last Name
;-----Input parameters - $letterToPatient - Text for patient letter
;-----Input parameters - $instructions - Instructions to be added for order creation
;-----Input parameters - $referalReason - Reason of referal for order
Func createOrder($orderCat,$authpvdr,$refpvdr,$Fname,$Lname,$letterToPatient,$instructions,$referalReason)
	ConsoleWrite("METHOD: createOrder() started" & @CRLF)
	;$arrConfig = Call("setConfig")
	$hFileOpen = Call("openLogFile")

	$newDocument = "Update - " & $Fname & " " & $Lname
	WinActivate($newDocument)
	ConsoleWrite("Activating Update Chart Window" &@CRLF)
	FileWriteLine($hFileOpen, _NowCalc() & "  -- Activating Update Chart Window" &@CRLF)
	ConsoleWrite("Active Window is " & WinGetTitle("[ACTIVE]") & @CRLF)

	If(WinActive($newDocument)) Then
		MouseClick("left",260,36)
		$orderWnd = "Update Orders - " & $Fname & " " & $Lname
		WinWaitActive($orderWnd)
		ConsoleWrite("Create New Order of Category " & $orderCat &@CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Create New Order of Category " & $orderCat &@CRLF)
		If(WinActive($orderWnd)) Then
			ConsoleWrite("Active Window is " & WinGetTitle("[ACTIVE]") & @CRLF)
			ControlFocus($orderWnd,"","[CLASS:SysTabControl32; INSTANCE:1]")
			Sleep(2000)
			;Select order -> Categories tab
			ControlClick($orderWnd,"","[CLASS:SysTabControl32; INSTANCE:1]","left",1,145,10)
			;Select Referral orders radio button
;~ 			Sleep(5000)
;~ 			ControlClick($orderWnd,"","[CLASS:Button; INSTANCE:16]")
;~ 			Sleep(5000)
			ControlClick($orderWnd,"","[CLASS:Button; INSTANCE:17]")
			Sleep(2000)

			;Select order type
			;ControlClick($orderWnd,"","[CLASS:ListBox; INSTANCE:1]")
			$myitem = "NULL"
			$i=0
			While(StringCompare($myitem,$orderCat)<> 0)
				ControlCommand($orderWnd, "", "[CLASS:ListBox; INSTANCE:1]", "SetCurrentSelection", $i)
				$myitem = ControlCommand($orderWnd, "", "[CLASS:ListBox; INSTANCE:1]", "GetCurrentSelection")
				;MsgBox(0,"Test", $myitem)
				;Send("{DOWN}")
				$i = $i +1
			WEnd

			Sleep(2000)
;~ 			ControlFocus($orderWnd,"","[CLASS:SftTreeControl70; INSTANCE:2]")
;~ 			$test = ControlTreeView($orderWnd, "", "[CLASS:SftTreeControl70; INSTANCE:2]", "GetText", "#0")
;~ 			ConsoleWrite($test & @CRLF)
;~ 			Sleep(3000)
;~ 			$myitem = "NULL"
;~ 			$i = 0
;~ 			While(StringCompare($myitem,$orderCatType)<> 0)
;~ 				ControlCommand($orderWnd, "", "[CLASS:SftTreeControl70; INSTANCE:2]", "SetCurrentSelection", $i)
;~ 				$myitem = ControlCommand($orderWnd, "", "[CLASS:SftTreeControl70; INSTANCE:2]", "GetCurrentSelection")
;~ 				MsgBox(0,"Test", $myitem)
;~ 				Send("{DOWN}")
;~ 				$i = $i +1
;~ 			WEnd
			ControlClick($orderWnd,"","[CLASS:Button; INSTANCE:21]")
			Sleep(2000)

			ControlFocus($orderWnd,"","[CLASS:SysTabControl32; INSTANCE:1]")
			;Sleep(2000)
			;Select order -> Order Details tab
			ControlClick($orderWnd,"","[CLASS:SysTabControl32; INSTANCE:1]","left",1,325,10)
			Sleep(1000)

			ConsoleWrite("Set Authorizing Provider as " &  $authpvdr &@CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Set Authorizing Provider as " & $authpvdr &@CRLF)
			ControlFocus($orderWnd, "","[CLASS:ComboBox; INSTANCE:1]")
			$myitem = "NULL"
			$i=0
			While(StringCompare($myitem,$authpvdr)<> 0)
				ControlCommand($orderWnd, "", "[CLASS:ComboBox; INSTANCE:1]", "SetCurrentSelection", $i)
				$myitem = ControlCommand($orderWnd, "", "[CLASS:ComboBox; INSTANCE:1]", "GetCurrentSelection")
				;MsgBox(0,"Test", $myitem)
				;Send("{DOWN}")

				$i = $i +1
			WEnd

			Sleep(2000)

			ConsoleWrite("Enter instructions for referral order" &@CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Enter instructions for referral order" &@CRLF)
			ControlFocus($orderWnd,"","[CLASS:Edit; INSTANCE:4]")
			ControlSetText($orderWnd,"","[CLASS:Edit; INSTANCE:4]",$instructions)
			Sleep(1000)

			ConsoleWrite("Enter reason for referral order" &@CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Enter reason for referral order" &@CRLF)
			ControlFocus($orderWnd,"","[CLASS:Edit; INSTANCE:12]")
			ControlSetText($orderWnd,"","[CLASS:Edit; INSTANCE:12]",$referalReason)
			Sleep(1000)

			ConsoleWrite("Set referring provider (external) as " & $refpvdr &@CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Set referring provider (external) as " & $refpvdr &@CRLF)
			ControlFocus($orderWnd,"","[CLASS:Button; INSTANCE:25]")
			ControlClick($orderWnd,"","[CLASS:Button; INSTANCE:25]")
			Sleep(1000)
			ControlFocus($orderWnd,"","[CLASS:Edit; INSTANCE:14]")
			ControlClick($orderWnd,"","[CLASS:Edit; INSTANCE:14]","left",1,221,10)
			$LnameRefPvdr = StringSplit($refpvdr,",")
			;ConsoleWrite("Referring pvdr last name is " & $LnameRefPvdr[1])
			WinWaitActive("Find Service Provider")
			If(WinActive("Find Service Provider")) Then
				ControlFocus("Find Service Provider","","[CLASS:Edit; INSTANCE:1]")
				ControlSetText("Find Service Provider","","[CLASS:Edit; INSTANCE:1]",$LnameRefPvdr[1])
				Sleep(2000)
				ControlFocus("Find Service Provider","","[CLASS:Button; INSTANCE:1]")
				ControlClick("Find Service Provider","","[CLASS:Button; INSTANCE:1]")
				Sleep(3000)
				ControlFocus("Find Service Provider","","[CLASS:Button; INSTANCE:7]")
				ControlClick("Find Service Provider","","[CLASS:Button; INSTANCE:7]")
				Sleep(2000)

			Else
				ConsoleWrite("ERROR Opening Find Service Provider Window...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Opening Find Service Provider Window...." & @CRLF)
				Exit
			EndIf

			;Set Letter to patient
			WinActivate($orderWnd)
			ControlFocus($orderWnd,"","[CLASS:Button; INSTANCE:29]")
			ControlClick($orderWnd,"","[CLASS:Button; INSTANCE:29]")
			WinWaitActive("Referral Coordination")
			If(WinActive("Referral Coordination")) Then
				ControlFocus("Referral Coordination","","[CLASS:MLogicFormWindow; INSTANCE:1]")
				ControlClick("Referral Coordination","","[CLASS:MLogicFormWindow; INSTANCE:1]","left",1,25,195)

				WinWaitActive("Letter to Patient")
				If(WinActive("Letter to Patient")) Then
					ConsoleWrite("Inserting Letter to Patient" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Inserting Letter to Patient" & @CRLF)
					Send($letterToPatient)
					Sleep(2000)
;~ 					ControlFocus("Letter to Patient","","[CLASS:EmrTerClass; INSTANCE:1]")
;~ 					ControlSetText("Letter to Patient","","[CLASS:EmrTerClass; INSTANCE:1]","This it auto generated letter to patient for TOC flow")

					ControlFocus("Letter to Patient","","[CLASS:Button; INSTANCE:15]")
					ControlClick("Letter to Patient","","[CLASS:Button; INSTANCE:15]")
					Sleep(3000)
					ConsoleWrite("OK-Letter to patient" & @CRLF)
				Else
					ConsoleWrite("ERROR Opening Letter to Patient Window...." & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Opening Letter to Patient Window...." & @CRLF)
					Exit
				EndIf
				WinActivate("Referral Coordination")
				ControlFocus("Referral Coordination","","[CLASS:MLogicFormWindow; INSTANCE:1]")
				ControlClick("Referral Coordination","","[CLASS:MLogicFormWindow; INSTANCE:1]","left",1,442,402)

			Else
				ConsoleWrite("ERROR Opening Referral Coordination Window...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Opening Referral Coordination Window...." & @CRLF)
				Exit
			EndIf

			WinActivate($orderWnd)
			ConsoleWrite("Sign the Order" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Sign the Order" & @CRLF)
			ControlFocus($orderWnd,"","[CLASS:Button; INSTANCE:10]")
			ControlClick($orderWnd,"","[CLASS:Button; INSTANCE:10]")

		Else
			ConsoleWrite("ERROR Opening Update Orders Window...." & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Opening Update Orders Window...." & @CRLF)
		EndIf

		;for event 4
		WinActivate($newDocument)
		ConsoleWrite("Complete the document" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Complete the document" & @CRLF)
		MouseClick("left",1290,35)
		WinWaitActive("End Update")
		ControlFocus("End Update","","[CLASS:Button; INSTANCE:10]")
		ControlClick("End Update","","[CLASS:Button; INSTANCE:10]")
		Sleep(1000)
		ControlFocus("End Update","","[CLASS:Button; INSTANCE:15]")
		ControlClick("End Update","","[CLASS:Button; INSTANCE:15]")

		ConsoleWrite("Order creation completed" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Order creation completed" & @CRLF)

	Else
		ConsoleWrite("ERROR Opening Update Chart Window...." & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Opening Update Chart Window...." & @CRLF)
	EndIf
	ConsoleWrite("METHOD: createOrder() ended" & @CRLF)
EndFunc



;-----Function to open Messaging Link in MF Dashboard
;-----Input parameter - Type of Messages  UI to be opened (Mass Messages / Automated Messages / Direct Messages / Template Editor)
Func openMessageLink($MsgType)
	ConsoleWrite("METHOD: openMessageLink() started" & @CRLF)
	;$arrConfig = Call("setConfig")
	$hFileOpen = Call("openLogFile")

	WinActivate("Medfusion Dashboard")
	ConsoleWrite("Opening Medfusion -> Messaging Tab" & @CRLF)
	FileWriteLine($hFileOpen, _NowCalc() & "  -- Opening Medfusion -> Messaging Tab" & @CRLF)
	ControlFocus("Medfusion Dashboard","","[NAME:lblBMM]")
	ControlClick("Medfusion Dashboard","","[NAME:lblBMM]")

	WinWaitActive("Messaging")
	If(WinActive("Messaging")) Then
;~ 		ConsoleWrite("Opening Direct Messaging UI...." & @CRLF)
;~ 		FileWriteLine($hFileOpen, _NowCalc() & "  -- Opening Direct Messaging UI...." & @CRLF)

		Switch $MsgType
			Case "Mass Messaging"
				ConsoleWrite("Opening Mass Messaging UI" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Opening Mass Messaging UI" & @CRLF)
				ControlClick("Messaging","","[NAME:pbBMM]")
				WinWaitActive("Mass Messaging")

			Case "Automated Messaging"
				ConsoleWrite("Opening Automated Messaging UI" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Opening Automated Messaging UI" & @CRLF)
				ControlClick("Messaging","","[NAME:pbAM]")
				WinWaitActive("Automated Messaging")

			Case "Direct Messaging"
				ConsoleWrite("Opening Direct Messaging UI" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Opening Direct Messaging UI" & @CRLF)
				ControlClick("Messaging","","[NAME:PBPP]")
				WinWaitActive("Direct Messaging")

			Case "Template Editor"
				ConsoleWrite("Opening Template Editor UI" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Opening Template Editor UI" & @CRLF)
				ControlClick("Messaging","","[NAME:pbTempEdit]")
				WinWaitActive("Templates")

			Case Else
				ConsoleWrite("Option not available on Messaging Screen...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Option not available on Messaging Screen...." & @CRLF)

		EndSwitch

	Else
		ConsoleWrite("ERROR Opening Medfusion -> Messaging Tab...." & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Opening Medfusion -> Messaging Tab...." & @CRLF)
	EndIf

	ConsoleWrite("METHOD: openMessageLink() ended" & @CRLF)
EndFunc



;-----Function to verify order in Direct Messages Outbox
;-----Input parameter - $orderid = Ordernum for the generated ToC
;-----Input parameter - $authpvdr = Authorizing provider for generated ToC
;-----Input parameter - $refpvdr = Referring  provider for generated ToC
;-----Input parameter - $ptFName = Patient's first Name for whom ToC was generated
;-----Input parameter - $ptLName = Patient's last Name for whom ToC was generated
;-----Input parameter - $fromAddress = SES address of authorizing provider
Func verifyOrderInUI($orderid, $authpvdr, $refpvdr,$ptFName, $ptLName, $fromAddress)
	ConsoleWrite("METHOD: verifyOrderInUI() started" & @CRLF)
	;$arrConfig = Call("setConfig")
	$hFileOpen = Call("openLogFile")

	WinActivate("Direct Messaging")
	If(WinActive("Direct Messaging")) Then
		ConsoleWrite("Opening Direct Messaging Sent box" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Opening Direct Messaging Sent box" & @CRLF)
		;Navigate to Direct Messaging -> Messages
		ControlFocus("Direct Messaging","","[NAME:pbTOC]")
		ControlClick("Direct Messaging","","[NAME:pbTOC]")
		Sleep(1000)

		;Select SES address from drpdown
		ConsoleWrite("Select SES address " & $fromAddress & " from dropdown" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Select SES address " & $fromAddress & " from dropdown" & @CRLF)
		ControlFocus("Direct Messaging","","[NAME:cbTOCEmailIds]")
		While 1
			$sentBoxAddress = ControlGetText("Direct Messaging","","[NAME:cbTOCEmailIds]")
			If(StringCompare($sentBoxAddress,$fromAddress) = 0) Then
				ExitLoop
			Else
				ControlSend("Direct Messaging","","[NAME:cbTOCEmailIds]",'{DOWN}')
			EndIf
		WEnd

		;click Inbox
		ControlClick("Direct Messaging","","[NAME:lblRTocSent]")
		Sleep(1000)
		ControlClick("Direct Messaging","","[NAME:pbTOC]")

		ConsoleWrite("Select the message from LHS list" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Select the message from LHS list" & @CRLF)
		Sleep(2000)
		;ConsoleWrite("Oredr Id is " & ControlGetText("Direct Messaging","","[NAME:lblDorderid]") & @CRLF)
		While(StringCompare($orderid,ControlGetText("Direct Messaging","","[NAME:lblDorderid]"))<> 0)
			ControlFocus("Direct Messaging", "", "[NAME:pbDMNext]")
			ControlClick("Direct Messaging", "", "[NAME:pbDMNext]")
			Sleep(500)
		WEnd

		ConsoleWrite("Order with order id " & $orderid & " found" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Order with order id " & $orderid & " found" & @CRLF)

	;Authorizing Provider
		ConsoleWrite("Verify Authorizing Provider for the order with order id " & $orderid & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Verify Authorizing Provider for the order with order id " & $orderid & @CRLF)
		$temp = StringSplit(ControlGetText("Direct Messaging","","[NAME:lblDIntServProv]")," ")

		For $i = 4	 To $temp[0]-1
			$temp[3] = $temp[3] & " " &$temp[$i]
		Next

		ConsoleWrite("Expected Result: " & $authpvdr &" and Actual Result: " & $temp[3] &", "&$temp[1] & " -- ")
		If(StringCompare($authpvdr,$temp[3] &", "&$temp[1]) = 0) Then
			ConsoleWrite("PASSED" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $authpvdr &" and Actual Result: " & $temp[3] &", "&$temp[1] & " -- PASSED" & @CRLF)
		Else
			ConsoleWrite("FAILED" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $authpvdr &" and Actual Result: " & $temp[3] &", "&$temp[1] & " -- FAILED" & @CRLF)
			Exit
		EndIf

	;Referring Provider
		ConsoleWrite("Verify Referring Provider for the order with order id " & $orderid & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Verify Referring Provider for the order with order id " & $orderid & @CRLF)
		$temp = StringSplit(ControlGetText("Direct Messaging","","[NAME:lblDExtServProv]")," ")

		For $i = 3	 To $temp[0]-1
			$temp[2] = $temp[2] & " " &$temp[$i]
		Next

		ConsoleWrite("Expected Result: " & $refpvdr &" and Actual Result: " & $temp[2] &", "&$temp[1] & " -- ")
		If(StringCompare($refpvdr,$temp[2] &", "&$temp[1]) = 0) Then
			ConsoleWrite("PASSED" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $refpvdr &" and Actual Result: " & $temp[2] &", "&$temp[1] & " -- PASSED" & @CRLF)
		Else
			ConsoleWrite("FAILED" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $refpvdr &" and Actual Result: " & $temp[2] &", "&$temp[1] & " -- FAILED" & @CRLF)
			Exit
		EndIf

	;Patient Name
		ConsoleWrite("Verify Patient Name for the order with order id " & $orderid & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Verify Patient Name for the order with order id " & $orderid & @CRLF)
		$temp = ControlGetText("Direct Messaging","","[NAME:lblDPatient]")
		ConsoleWrite("Expected Result: " & $ptFName & " " & $ptLName &" and Actual Result: " & $temp & " -- ")
		If(StringCompare($ptFName & " " & $ptLName,$temp) = 0) Then
			ConsoleWrite("PASSED" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $ptFName & " " & $ptLName &" and Actual Result: " & $temp & " -- PASSED" & @CRLF)
		Else
			ConsoleWrite("FAILED" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $ptFName & " " & $ptLName &" and Actual Result: " & $temp & " -- FAILED" & @CRLF)
			Exit
		EndIf

	Else
		ConsoleWrite("ERROR Opening Direct Messaging UI...." & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Opening Direct Messaging UI...." & @CRLF)
	EndIf

	ConsoleWrite("METHOD: verifyOrderInUI() ended" & @CRLF)
EndFunc



;-----Function to connect database
;-----Input parameter - SQL server IP, database name, SQL UserName, SQL, PAssword
Func connectDatabase($SQLServerIP, $DBName, $user, $password)
	$hFileOpen = Call("openLogFile")

	_SQL_RegisterErrorHandler()

	$oADODB = _SQL_Startup()
	If $oADODB = $SQL_ERROR then Msgbox(0 + 16 +262144,"Error",_SQL_GetErrMsg())

	If _sql_Connect(-1,$SQLServerIP, $DBName, $user, $password) = $SQL_ERROR then
		;Msgbox(0 + 16 +262144,"Error",_SQL_GetErrMsg())
		ConsoleWrite("ERROR Connecting Database " & $DBName & "...." & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Connecting Database " & $DBName & "...." & @CRLF)
		_SQL_Close()
		Exit

	Else
		ConsoleWrite($DBName & " Database connected" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- " & $DBName &" Database Connected" & @CRLF)
	EndIf
EndFunc



;-----Function to open Patient Registration
Func openPatientRegistration()
	ConsoleWrite("METHOD: openPatientRegistration() started" & @CRLF)
	;$arrConfig = Call("setConfig")
	$hFileOpen = Call("openLogFile")

	WinActivate($title)
	ConsoleWrite("Activate GE CPS window" &@CRLF )

	Local $oIEEmbed =_IEAttach("Centricity Practice Solution","embedded",1)
	Sleep(1000)
	ConsoleWrite("Activating GE CPS App" &@CRLF)
	FileWriteLine($hFileOpen, _NowCalc() & "  -- Activating GE CPS App" &@CRLF)
	WinWaitActive($title)
	ConsoleWrite("Active Window is " & WinGetTitle("[ACTIVE]") & @CRLF)

	If ( IsObj($oIEEmbed) ) Then
			ConsoleWrite("Attached successfully to IE instance running under GE app" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Attached successfully to IE instance running under GE app" & @CRLF)

			;ConsoleWrite('@@ Debug(' & @ScriptLineNumber & ') : $oIEEmbed = ' & $oIEEmbed & @CRLF & '>Error code: ' & @error & '    Extended code: 0x' & Hex(@extended) & @CRLF) ;### Debug Console
			ConsoleWrite("Embedded IE Url " &  _IEPropertyGet($oIEEmbed, "locationurl") &@CRLF )
			sleep(1000)

			Local $oDoc = _IEBodyReadHTML($oIEEmbed)
			Local $oLinkCollection = _IELinkGetCollection($oIEEmbed)

			ConsoleWrite("Finding links collection" & @CRLF)
			If ( IsObj($oLinkCollection) ) Then
				;ConsoleWrite( @CRLF & "Links Collection --> "  &  IsObj($oLinkCollection) & " -------- " & @CRLF )
				;ConsoleWrite( @CRLF & "Button Count --> "  &  $oBtnCollection.count() & " -------- " & @CRLF )
				$lCount = 0
					For $oLink in $oLinkCollection
					;ConsoleWrite(  "Link " & $lCount & " --> "  & $oLink & " -------- " & @CRLF )
					$lCount = $lCount + 1
					Next
				;_ArrayDisplay($oLinkCollection)
				ConsoleWrite("For loop finished for Link Collection" & @CRLF )
				ConsoleWrite("Links found - " & $lCount & @CRLF)
			EndIf

			ConsoleWrite("Clicking REGISTRATION link of GE CPS App...." & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Clicking REGISTRATION link of GE CPS App" & @CRLF)
			Sleep(5000)
			_IELinkClickByIndex($oIEEmbed,2)

			WinWaitActive("Find Patient")

			ConsoleWrite("Click on NEW button of FIND PATIENT screen"& @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Click on NEW button of FIND PATIENT screen"& @CRLF)
			   If WinActive ("Find Patient") Then
				  Sleep (5000)
				  ControlClick("Find Patient", "", "Button4")
				  Sleep (3000)
				  Send ("{Enter}")
				  Sleep (20000)

				  Local $aListReg = WinList()
				  ;_ArrayDisplay($aListReg)
				  ; Loop through the array displaying only visable windows with a title.

				  Local $iRows = UBound($aListReg, $UBOUND_ROWS)
				  ConsoleWrite("Active window(s) " & $iRows & @CRLF)
				  ConsoleWrite("Active Window is " & WinGetTitle("[ACTIVE]") & @CRLF)


				Else
				  ConsoleWrite("No active Find Patient screen found"&@CRLF)
				  FileWriteLine($hFileOpen, _NowCalc() & "  -- No active Find Patient screen found" & @CRLF)
			   EndIf
	Else
		ConsoleWrite("ERROR Attaching IE Process Within GE CPS App...." & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Attaching IE Process Within GE CPS App...." & @CRLF)
		Exit
	EndIf
	ConsoleWrite("METHOD: openPatientRegistration() ended" & @CRLF)
EndFunc



;-----Function to open Patient Registration
;-----Input parameter - $PtCount = Number of patients to be created
;-----Input parameter - $iDateCalc = Current Timestamp
;-----Input parameter - $pFirstName = Patient's First Name
;-----Input parameter - $pEmailId = Patient's Email Id
Func registerPatient($PtCount,$iDateCalc,$pFirstName,$pEmailId)
	ConsoleWrite("METHOD: registerPatient() started" & @CRLF)
	$hFileOpen = Call("openLogFile")

	WinActivate("Patient Registration")
	Local $oIEEmbedReg = _IEAttach("[REGEXPTITLE:Patient; INSTANCE:1]","embedded",1)
	ConsoleWrite("Registration window IE Instance - " &$oIEEmbedReg &@CRLF )

		If ( IsObj($oIEEmbedReg) ) Then
			ConsoleWrite("IE instance of Registration Screen connected"&@CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- IE instance of Registration Screen connected"&@CRLF)
			ConsoleWrite("Embedded Registration Screen IE Url " &  _IEPropertyGet($oIEEmbedReg, "locationurl") &@CRLF )

			Local $oDocReg = _IEBodyReadHTML($oIEEmbedReg)
			;MsgBox($MB_SYSTEMMODAL, "Document HTML", $oDocReg)
			;ConsoleWrite( @CRLF & "Registration Form HTML --> "  & $oDocReg & " -------- " & @CRLF )

			Local $btnRegWithMF = _IEGetObjById($oIEEmbedReg,"cmdMedFusion");

			If ( IsObj($btnRegWithMF) ) Then
				ConsoleWrite("Register With Medfusion button found" & @CRLF)

				$regWindow = "[CLASS:AfxFrameOrView110]"




						   Local $regWinHandle = WinActivate ( $regWindow )

						   If WinActivate ( $regWindow ) Then

							  ConsoleWrite("Active registration window - " & $regWinHandle & @CRLF)
							  FileWriteLine($hFileOpen, @CRLF & " STEP 3 -- ENTER PATIENT DETAILS" & @CRLF)
							  FileWriteLine($hFileOpen, _NowCalc() & "  -- Active registration window - " & $regWinHandle & @CRLF)

							  ;Local $pCount = $j & $j & $j
							  ;ConsoleWrite("Patient - " & $pCount & @CRLF)
							  ;Local $hControl = ControlGetHandle($regWindow, "", "m_First")
							  ;ConsoleWrite("First Name Found " & IsObj($hControl) &@CRLF )
							  Local $Fname = $pFirstName &"_"&$iDateCalc
							  ControlFocus($regWinHandle,"","[Name:m_First]")
							  ControlSetText($regWinHandle, "", "[NAME:m_First]", $Fname)
							  sleep(1000)
							  ;ControlSend($regWinHandle, "", "[Name:m_Last]", $lastName & $pCount )
							  Local $Lname = $iDateCalc
							  ControlSetText($regWinHandle, "", "[Name:m_Last]", $Lname)
							  sleep(1000)
							  Local $BDate = "01/01/1987"
							  ControlSetText($regWinHandle, "", "[Name:m_DateBox]", $BDate )
							  Sleep(1000)
							  ControlFocus($regWinHandle,"","[Name:m_PatientSameAsGuarantor]")
							  Send("{Space}")
							  Sleep(1000)
							  ControlFocus($regWinHandle,"","[Name:m_Sex]")
							  Send("{Down}")
							  sleep(1000)
							  ControlSetText($regWindow, "", "[Name:m_txtAddress1]", "Address_" &$iDateCalc )
							  sleep(1000)

							  ;Start Find ZIP CODE

								 ControlFocus($regWinHandle,"","[Name:m_btnLookup]")
								 ControlClick($regWinHandle,"","[Name:m_btnLookup]")
								 sleep(3000)
								 ;Local $activeWindows = WinList("Find Zip Code")
								 ;_ArrayDisplay($activeWindows)

								 ConsoleWrite("Clicked on Find Zip Code Button& wait till 20 sec"&@CRLF)
								 FileWriteLine($hFileOpen, _NowCalc() & "  -- Clicked on Find Zip Code Button& wait till 20 sec"&@CRLF)
								 $findzipWinHandle = WinWaitActive("Find Zip Code","",20)

								 ConsoleWrite("Active window Find Zip Code - " & $findzipWinHandle &@CRLF )
								Local $zipCode = "00220"
								 If ( $findzipWinHandle ) Then
									;Local $findzipWinHandle = WinActivate ( "Find Zip Code" )

									;ConsoleWrite("Active window Find Zip Code - " & $findzipWinHandle &@CRLF )
									ControlFocus($findzipWinHandle,"","[CLASS:Edit;INSTANCE:1]")
									ControlSetText($findzipWinHandle,"","[CLASS:Edit;INSTANCE:1]",$zipCode)
									sleep(5000)
									ControlFocus($findzipWinHandle,"","[CLASS:Edit;INSTANCE:2]")
									ControlSetText($findzipWinHandle,"","[CLASS:Edit;INSTANCE:2]","New York")
									sleep(5000)
									ControlFocus($findzipWinHandle,"","[CLASS:Button;INSTANCE:1]")
									ControlClick($findzipWinHandle,"","[CLASS:Button;INSTANCE:1]")
									sleep(10000)
									ControlFocus($findzipWinHandle,"","[CLASS:Button;INSTANCE:32]")
									ControlClick($findzipWinHandle,"","[CLASS:Button;INSTANCE:32 ]")
									sleep(5000)
								 Else
									ConsoleWrite("Inactive window Find Zip Code"&@CRLF)
									FileWriteLine($hFileOpen, _NowCalc() & "  -- Inactive window Find Zip Code"&@CRLF)
								 EndIf
							  ;End Find ZIP CODE


							  ControlFocus($regWinHandle, "", "[Name:m_PatientId]")

							  sleep(2000)
							  $PatientID = $iDateCalc
							  ControlClick($regWinHandle, "", "[Name:m_PatientId]")
							  ControlSetText($regWinHandle, "", "[Name:m_PatientId]", $iDateCalc )
							  ;Exit
							  sleep(1000)
							  ControlSetText($regWinHandle, "", "[Name:m_Email]", $pEmailId)
							  sleep(1000)
							 ; ControlSetText($regWinHandle, "", "[Name:m_Preferred]", "" )
							 ; sleep(1000)

							  ControlFocus($regWinHandle,"","[Name:m_BtnApply]")
							  sleep(3000)
							  ControlClick($regWinHandle,"","[Name:m_BtnApply]")
							  Send("{Enter}")
							  ConsoleWrite("Clicked on SAVE button on Registration screen" & @CRLF )
							  FileWriteLine($hFileOpen, @CRLF & " STEP 4 -- SAVE PATIENT DETAILS" & @CRLF)
							  FileWriteLine($hFileOpen, _NowCalc() & "  -- Clicked on SAVE button on Registration screen" & @CRLF )
							  sleep(10000)
						   Else
							  ConsoleWrite("Inactive registration windows" & @CRLF)
							  FileWriteLine($hFileOpen, _NowCalc() & "  -- Clicked on SAVE button on Registration screen" & @CRLF )
						   EndIf
						   ; Send a string of text to the edit control of Notepad. The handle returned by WinWait is used for the "title" parameter of ControlSend.

						   _IEAction($btnRegWithMF,"click")
						   ConsoleWrite("Clicked on Register with MF" &@CRLF)
						   FileWriteLine($hFileOpen, @CRLF & " STEP 5 -- REGISTER WITH MEDFUSION" & @CRLF)
						   FileWriteLine($hFileOpen, _NowCalc() & "  -- Clicked on Register with MF" &@CRLF)
						   Sleep(10000)
						   WinActivate ( $regWindow )
						   ConsoleWrite("Activating Registration Window again "&@CRLF)
						   FileWriteLine($hFileOpen, _NowCalc() & "  -- Activating Registration Window again "&@CRLF)
						   If ( WinActivate($regWindow) ) Then
							  $hndSaveExit = ControlGetHandle($regWinHandle,"","[Name:m_OK]")
							  ConsoleWrite("Save & Exit handle " & $hndSaveExit &@CRLF )
							  ControlFocus($regWinHandle,"","[Name:m_OK]")
							  ControlClick($regWinHandle,"","[Name:m_OK]")
							  Send("{Enter}")
							  ConsoleWrite("Clicked on Save & Exit button" & @CRLF)
							  FileWriteLine($hFileOpen, @CRLF & " STEP 6 -- EXIT TO CPS HOME SCREEN" & @CRLF)
							  FileWriteLine($hFileOpen, _NowCalc() & "  -- Clicked on Save & Exit button" & @CRLF)
							  ConsoleWrite("Patient registration part completed ... waiting for email now " & $pEmailId & "  iDate  " & $iDateCalc & " " & @CRLF)
							  ConsoleWrite("Patient firstname" & $pFirstName &"_"&$iDateCalc & @CRLF)
							EndIf
							  Sleep(10000)
							   Local Const $sFilePath =@WorkingDir & "\Email.txt"
							  ConsoleWrite("Working directory path " & $sFilePath & @CRLF)
							  Local $hEmailFileOpen = FileOpen($sFilePath, $FO_OVERWRITE)
								If $hEmailFileOpen = -1 Then
									MsgBox($MB_SYSTEMMODAL, "", "An error occurred whilst writing the temporary file.")
								Return False
								EndIf

								FileWrite($hEmailFileOpen, $pEmailId & @CRLF)
								FileClose($hEmailFileOpen)
								;MsgBox($MB_SYSTEMMODAL, "", "Contents of the file:" & @CRLF & FileRead($sFilePath))
							 Else
						ConsoleWrite("Register With Medfusion button not found" & @CRLF)
						FileWriteLine($hFileOpen, _NowCalc() & "  -- Register With Medfusion button not found" & @CRLF)
					 EndIf

 Else
					 ConsoleWrite("IE instance of Registration Screen not connected"&@CRLF)
					 FileWriteLine($hFileOpen, _NowCalc() & "  -- IE instance of Registration Screen not connected"&@CRLF)
				  EndIf
;~ 	If(WinActive("Patient Registration")) Then
;~ 		ConsoleWrite("Active registration window - " & @CRLF)
;~ 		FileWriteLine($hFileOpen, @CRLF & " STEP 3." &$PtCount &" -- ENTER PATIENT DETAILS FOR PATIENT " & $PtCount & @CRLF)
;~ 		FileWriteLine($hFileOpen, _NowCalc() & "  -- Active registration window - " & @CRLF)

;~ 		$Fname = $pFirstName &"_"&$iDateCalc
;~ 		ControlFocus("Patient Registration","","[Name:m_First]")
;~ 		ControlSetText("Patient Registration", "", "[NAME:m_First]", $Fname)
;~ 		Sleep(1000)

;~ 		$Lname = $iDateCalc
;~ 		ControlSetText("Patient Registration", "", "[Name:m_Last]", $Lname)
;~ 		Sleep(1000)

;~ 		$BDate = "01/01/1987"
;~ 		ControlSetText("Patient Registration", "", "[Name:m_DateBox]", $BDate )
;~ 		Sleep(1000)

;~ 		ControlFocus("Patient Registration","","[Name:m_PatientSameAsGuarantor]")
;~ 		Send("{Space}")
;~ 		Sleep(1000)

;~ 		ControlFocus("Patient Registration","","[Name:m_Sex]")
;~ 		Send("{Down}")
;~ 		Sleep(1000)

;~ 		ControlSetText("Patient Registration", "", "[Name:m_txtAddress1]", "Address_" &$iDateCalc )
;~ 		Sleep(1000)

;~ 		;Start Find ZIP CODE
;~ 		ControlFocus("Patient Registration","","[Name:m_btnLookup]")
;~ 		ControlClick("Patient Registration","","[Name:m_btnLookup]")
;~ 		Sleep(3000)

;~ 		ConsoleWrite("Clicked on Find Zip Code Button& wait till 20 sec"&@CRLF)
;~ 		FileWriteLine($hFileOpen, _NowCalc() & "  -- Clicked on Find Zip Code Button& wait till 20 sec"&@CRLF)
;~ 			$findzipWinHandle = WinWaitActive("Find Zip Code","",20)
;~ 			ConsoleWrite("Active window is - " & WinGetTitle("[ACTIVE]") &@CRLF )

;~ 			$zipCode = "00220"
;~ 			If($findzipWinHandle) Then
;~ 				;Local $findzipWinHandle = WinActivate ( "Find Zip Code" )
;~ 				;ConsoleWrite("Active window Find Zip Code - " & $findzipWinHandle &@CRLF )
;~ 				ControlFocus($findzipWinHandle,"","[CLASS:Edit;INSTANCE:1]")
;~ 				ControlSetText($findzipWinHandle,"","[CLASS:Edit;INSTANCE:1]",$zipCode)
;~ 				Sleep(5000)

;~ 				ControlFocus($findzipWinHandle,"","[CLASS:Edit;INSTANCE:2]")
;~ 				ControlSetText($findzipWinHandle,"","[CLASS:Edit;INSTANCE:2]","New York")
;~ 				Sleep(5000)

;~ 				ControlFocus($findzipWinHandle,"","[CLASS:Button;INSTANCE:1]")
;~ 				ControlClick($findzipWinHandle,"","[CLASS:Button;INSTANCE:1]")
;~ 				Sleep(10000)

;~ 				ControlFocus($findzipWinHandle,"","[CLASS:Button;INSTANCE:32]")
;~ 				ControlClick($findzipWinHandle,"","[CLASS:Button;INSTANCE:32 ]")
;~ 				Sleep(5000)

;~ 			Else
;~ 				ConsoleWrite("Inactive window Find Zip Code"&@CRLF)
;~ 				FileWriteLine($hFileOpen, _NowCalc() & "  -- Inactive window Find Zip Code"&@CRLF)
;~ 			EndIf
;~ 		;End Find ZIP CODE

;~ 		ControlFocus("Patient Registration", "", "[Name:m_PatientId]")
;~ 		Sleep(2000)
;~ 		$PatientID = $iDateCalc
;~ 		ControlClick("Patient Registration", "", "[Name:m_PatientId]")
;~ 		ControlSetText("Patient Registration", "", "[Name:m_PatientId]", $iDateCalc )
;~ 		;Exit
;~ 		Sleep(1000)

;~ 		ControlSetText("Patient Registration", "", "[Name:m_Email]", $pEmailId)
;~ 		Sleep(1000)

;~ 		ControlFocus("Patient Registration","","[Name:m_BtnApply]")
;~ 		Sleep(3000)
;~ 		ControlClick("Patient Registration","","[Name:m_BtnApply]")
;~ 		Send("{Enter}")


;~ 		FileWriteLine($hFileOpen, @CRLF & " STEP 4." &$PtCount &" -- SAVE PATIENT DETAILS FOR PATIENT " & $PtCount & @CRLF)
;~ 		ConsoleWrite("Clicked on SAVE button on Registration screen" & @CRLF )
;~ 		FileWriteLine($hFileOpen, _NowCalc() & "  -- Clicked on SAVE button on Registration screen" & @CRLF )
;~ 		Sleep(10000)





;~ 		Else
;~ 			ConsoleWrite("Inactive registration windows" & @CRLF)
;~ 			FileWriteLine($hFileOpen, _NowCalc() & "  -- Clicked on SAVE button on Registration screen" & @CRLF )
;~ 		EndIf

;~ 		_IEAction($btnRegWithMF,"click")
;~ 						   ConsoleWrite("Clicked on Register with MF" &@CRLF)
;~ 						   FileWriteLine($hFileOpen, @CRLF & " STEP 5 -- REGISTER WITH MEDFUSION" & @CRLF)
;~ 						   FileWriteLine($hFileOpen, _NowCalc() & "  -- Clicked on Register with MF" &@CRLF)
;~ 						   Sleep(10000)
;~ 						   WinActivate ( $regWindow )
;~ 						   ConsoleWrite("Activating Registration Window again "&@CRLF)
;~ 						   FileWriteLine($hFileOpen, _NowCalc() & "  -- Activating Registration Window again "&@CRLF)
;~ 						   If ( WinActivate($regWindow) ) Then
;~ 							  $hndSaveExit = ControlGetHandle("Patient Registration","","[Name:m_OK]")
;~ 							  ConsoleWrite("Save & Exit handle " & $hndSaveExit &@CRLF )
;~ 							  ControlFocus("Patient Registration","","[Name:m_OK]")
;~ 							  ControlClick("Patient Registration","","[Name:m_OK]")
;~ 							  Send("{Enter}")
;~ 							  ConsoleWrite("Clicked on Save & Exit button" & @CRLF)
;~ 							  FileWriteLine($hFileOpen, @CRLF & " STEP 6 -- EXIT TO CPS HOME SCREEN" & @CRLF)
;~ 							  FileWriteLine($hFileOpen, _NowCalc() & "  -- Clicked on Save & Exit button" & @CRLF)
;~ 							  ConsoleWrite("Patient registration part completed ... waiting for email now " & $pEmailId & "  iDate  " & $iDateCalc & " " & @CRLF)
;~ 							  ConsoleWrite("Patient firstname" & $pFirstName &"_"&$iDateCalc & @CRLF)
;~ 							EndIf
;~ 							  Sleep(10000);
;~ 							   Local Const $sFilePath =@WorkingDir & "\Email.txt"
;~ 							  ConsoleWrite("Working directory path " & $sFilePath & @CRLF)
;~ 							  Local $hEmailFileOpen = FileOpen($sFilePath, $FO_OVERWRITE)
;~ 								If $hEmailFileOpen = -1 Then
;~ 									MsgBox($MB_SYSTEMMODAL, "", "An error occurred whilst writing the temporary file.")
;~ 								Return False
;~ 								EndIf

;~ 								FileWrite($hEmailFileOpen, $pEmailId & @CRLF)
;~ 								FileClose($hEmailFileOpen)

;~ 	Else
;~ 		ConsoleWrite("ERROR Attaching Patient Registration Screen...." & @CRLF)
;~ 		FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Attaching Patient Registration Screen...." & @CRLF)
;~ 		Exit
;~ 	EndIf
	ConsoleWrite("METHOD: registerPatient() ended" & @CRLF)
EndFunc



;-----Function to create Office Visist (Asthma Visit)
;-----Input parameter - $Fname = Patient's First Name
;-----Input parameter - $Lname = Patient's Last Name
Func createAsthmaVisit($Fname,$Lname)
	ConsoleWrite("METHOD: createAsthmaVisit() started" & @CRLF)
	;$arrConfig = Call("setConfig")
	$hFileOpen = Call("openLogFile")

	$newDocument = "Update - " & $Fname & " " & $Lname
	WinActivate($newDocument)
	ConsoleWrite("Activating Update Chart Window" &@CRLF)
	FileWriteLine($hFileOpen, _NowCalc() & "  -- Activating Update Chart Window" &@CRLF)
	ConsoleWrite("Active Window is " & WinGetTitle("[ACTIVE]") & @CRLF)

	If(WinActive($newDocument)) Then
		ConsoleWrite("Filling up Asthma Visit details" &@CRLF)
		WinSetState($newDocument,"",@SW_MAXIMIZE)
		MouseClick("left",569,128)
		Send("{TAB}")
		Send("{DOWN}")
		Send("{TAB}")
		Send("{DOWN}")
		Send("{TAB}")
		Send("{DOWN}")
		Send("{TAB}")
		Send("{DOWN}")
		Send("{TAB 3}")
		Send("This is automated message")

		ConsoleWrite("Complete the Office Visit" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Complete the Office Visit" & @CRLF)
		MouseClick("left",1290,35)
		WinWaitActive("End Update")
		ControlFocus("End Update","","[CLASS:Button; INSTANCE:15]")
		ControlClick("End Update","","[CLASS:Button; INSTANCE:15]")

		ConsoleWrite("Office Visit creation completed" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Office Visit creation completed" & @CRLF)

	Else
		ConsoleWrite("ERROR Opening Update Chart Window...." & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Opening Update Chart Window...." & @CRLF)
	EndIf
	ConsoleWrite("METHOD: createAsthmaVisit() ended" & @CRLF)
EndFunc



;-----Function to create Clinical Visit Summary
Func createCVS()
	ConsoleWrite("METHOD: createCVS() started" & @CRLF)
	;WinActivate("Chart - NOT FOR PATIENT USE")
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

	;Click first document in list
	MouseClick("left",617,319)
	$str = WinGetText("Chart - NOT FOR PATIENT USE")
	;$str = ControlGetText("Chart - NOT FOR PATIENT USE","","[CLASS:Static; INSTANCE:4]")
	;ConsoleWrite($str & @CRLF)
	If(StringInStr($str,"Office Visit at")>0) Then
		;right click office visit in documnet list to create CVS
		;ControlClick("Chart - NOT FOR PATIENT USE","","[CLASS:SftTreeControl70; INSTANCE:1]","right",1,240,28)
		MouseClick("right",617,319)
		Sleep(1000)
		Send("{V}")

		WinWaitActive("Clinical Visit Summary")
		WinSetState("Clinical Visit Summary","",@SW_MAXIMIZE)
		MouseClick("left",880,720)
		Sleep(1000)
		; double click office visit in documnet list
		MouseClick("left",617,319,2)
		Sleep(1000)
		;ControlClick("Chart - NOT FOR PATIENT USE","","[CLASS:SftTreeControl70; INSTANCE:1]","left",2,240,28)
		;click CVS in documnet list
		MouseClick("left",617,338)
		;ControlClick("Chart - NOT FOR PATIENT USE","","[CLASS:SftTreeControl70; INSTANCE:1","left",1,240,48)
		Sleep(1000)
		$str = WinGetText("Chart - NOT FOR PATIENT USE")
		;$str = ControlGetText("Chart - NOT FOR PATIENT USE","","[CLASS:Static; INSTANCE:4]")
		If(StringInStr($str,"Clinical Visit Summary at")>0) Then
			ConsoleWrite("CVS Created" & @CRLF)
			Return("PASSED")

		Else
			ConsoleWrite("CVS Creation failed...." & @CRLF)
			Return("FAILED")
		EndIf
	Else
		ConsoleWrite("Unable to fetch Office Visit...." & @CRLF)
		Exit
	EndIf
	ConsoleWrite("METHOD: createCVS() ended" & @CRLF)
EndFunc



;-----Function to create a new email message
;-----Input parameter - $Fname = Patient's First Name
;-----Input parameter - $Lname = Patient's Last Name
;-----Input parameter - $subject = Email Message Subject
;-----Input parameter - $body = Email MEssage Body
Func createEmailMessage($Fname,$Lname,$subject,$body)
	ConsoleWrite("METHOD: createEmailMessage() started" & @CRLF)
	;$arrConfig = Call("setConfig")
	$hFileOpen = Call("openLogFile")

	$newDocument = "Update - " & $Fname & " " & $Lname
	WinActivate($newDocument)
	ConsoleWrite("Activating Update Chart Window" &@CRLF)
	FileWriteLine($hFileOpen, _NowCalc() & "  -- Activating Update Chart Window" &@CRLF)
	ConsoleWrite("Active Window is " & WinGetTitle("[ACTIVE]") & @CRLF)

	If(WinActive($newDocument)) Then
		Send("{TAB 2}")
		Send($subject)
		;ControlSetText($newDocument,"","[CLASSNN:Edit1]","Body of test email.")
		Send("{TAB 4}")
		Send($body)
		Sleep(1000)
		Send("+{TAB 2}")
		Sleep(2000)
		Send("{SPACE}")
		WinWaitActive("Centricity Practice Solution")
		ControlClick("Centricity Practice Solution","","[CLASS:Button; INSTANCE:1]")

		WinWaitActive($newDocument)
		ConsoleWrite("Complete the document" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Complete the document" & @CRLF)
		MouseClick("left",1290,35)
		WinWaitActive("End Update")
		ControlFocus("End Update","","[CLASS:Button; INSTANCE:15]")
		ControlClick("End Update","","[CLASS:Button; INSTANCE:15]")

		ConsoleWrite("Email message creation completed" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Email message creation completed" & @CRLF)

	Else
		ConsoleWrite("ERROR Opening Update Chart Window...." & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Opening Update Chart Window...." & @CRLF)
	EndIf

	ConsoleWrite("METHOD: createEmailMessage() ended" & @CRLF)
EndFunc



;-----Function to verify document in PAtient Chart
;-----Input Parmeter - Title of document  to be verified
Func verifyDocumentInPatientChart($docTitle)
	ConsoleWrite("METHOD: verifyDocumentInPatientChart() started" & @CRLF)
	WinActivate("Chart - NOT FOR PATIENT USE")
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

	;Click first document in list
	MouseClick("left",617,319)
	$str = WinGetText("Chart - NOT FOR PATIENT USE")
	;$str = ControlGetText("Chart - NOT FOR PATIENT USE","","[CLASS:Static; INSTANCE:4]")
	;ConsoleWrite($str & @CRLF)
	If(StringInStr($str,$docTitle)>0) Then
		ConsoleWrite("Document " & $docTitle & " arrived" & @CRLF)
		Return("PASSED")
	Else
		ConsoleWrite("Document " & $docTitle & " did not arrive..." & @CRLF)
		Return("FAILED")
	EndIf
	ConsoleWrite("METHOD: verifyDocumentInPatientChart() ended" & @CRLF)
EndFunc



;-----Function to reply 'Ask A Staff'
;-----Input parameter - $Fname = Patient's First Name
;-----Input parameter - $Lname = Patient's Last Name
;-----Input parameter - $subject = AAS Message Subject
;-----Input parameter - $body = AAS MEssage Body
;-----Input Parameter - $replyMsg = Text for replying AAS
Func replyAAS($Fname,$Lname,$subject,$body,$replyMsg)
	ConsoleWrite("METHOD: replyAAS() started" & @CRLF)
	;$arrConfig = Call("setConfig")
	$hFileOpen = Call("openLogFile")

	$newDocument = "Update - " & $Fname & " " & $Lname
	WinActivate($newDocument)
	ConsoleWrite("Activating Update Chart Window" &@CRLF)
	FileWriteLine($hFileOpen, _NowCalc() & "  -- Activating Update Chart Window" &@CRLF)
	ConsoleWrite("Active Window is " & WinGetTitle("[ACTIVE]") & @CRLF)

	If(WinActive($newDocument)) Then
		Send("{TAB 1}")
		Send("{SPACE}")
		WinWaitActive("Secure Message: Reply-To")

		$counter = 1
		While $counter <=5
			$str = WinGetText("Secure Message: Reply-To")

			If((StringInStr($str, $subject)>0) And (StringInStr($str,$body)>0)) Then
				ConsoleWrite("Found the message to reply" & @CRLF)
				Sleep(1000)
				ControlClick("Secure Message: Reply-To","","[NAME:ButtonSelect]")
				Sleep(1000)

				WinWaitActive($newDocument)
				Send("{TAB 7}")
				Send($replyMsg)
				Sleep(1000)
				Send("+{TAB 2}")
				Sleep(2000)
				Send("{SPACE}")
				WinWaitActive("Centricity Practice Solution")
				ControlClick("Centricity Practice Solution","","[CLASS:Button; INSTANCE:1]")

				WinWaitActive($newDocument)
				ConsoleWrite("Complete the document" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Complete the document" & @CRLF)
				MouseClick("left",1290,35)
				WinWaitActive("End Update")
				ControlFocus("End Update","","[CLASS:Button; INSTANCE:15]")
				ControlClick("End Update","","[CLASS:Button; INSTANCE:15]")
				ExitLoop

			Else
				ConsoleWrite("Message not found" & @CRLF)
				Send("{DOWN}")
			EndIf
			$counter += 1
		WEnd

	Else
		ConsoleWrite("ERROR Opening Update Chart Window...." & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Opening Update Chart Window...." & @CRLF)
	EndIf

	ConsoleWrite("METHOD: replyAAS() ended" & @CRLF)
EndFunc



;-----Function to assert values
;-----Input parameter - $Expected = expected value
;-----Input parameter - $Actual = actual value
Func assertData($Expected, $Actual)
	ConsoleWrite("METHOD: assertData() started" & @CRLF)
	$hFileOpen = Call("openLogFile")

	If(StringCompare($Expected, $Actual) = 0) Then
		ConsoleWrite("Expected Result: " & $Expected &" and Actual Result: " & $Actual & " -- PASSED" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $Expected &" and Actual Result: " & $Actual & " -- PASSED" & @CRLF)

	Else
		ConsoleWrite("Expected Result: " & $Expected &" and Actual Result: " & $Actual & " -- FAILED" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Expected Result: " & $Expected &" and Actual Result: " & $Actual & " -- FAILED" & @CRLF)
		Exit
	EndIf
	ConsoleWrite("METHOD: assertData() ended" & @CRLF)
EndFunc



;-----Function to reply Prescription Renewal Request vi Append Flow
;-----Input Parameter - $Fname = Patient's First Name
;-----Input Parameter - $Lname = Patient's Last Name
;-----Input Parameter - $medName = Medication Name as per Patient's Prescription Renewal Request
;-----Input Parameter - $medDosage = Medication Dose as per Patient's Prescription Renewal Request
;-----Input Parameter - $medQuantity = Medication Quantity as per Patient's Prescription Renewal Request
;-----Input Parameter - $medRefills = Medication Refills as per Patient's Prescription Renewal Request
;-----Input Parameter - $prescriptionNo = Medication Prescription Number as per Patient's Prescription Renewal Request
;-----Input Parameter - $medNotes = Additional Notes along with Medication Details as per Patient's Prescription Renewal Request
;-----Input Parameter - $pharmacyName = Pharmacy Name as per Patient's Prescription Renewal Request
Func replyRxAppend($Fname, $Lname, $medName, $medDosage, $medQuantity, $medRefills, $prescriptionNo, $medNotes, $pharmacyName)
	ConsoleWrite("METHOD: replyRxAppend() started" & @CRLF)
	$hFileOpen = Call("openLogFile")
	WinActivate("Chart - NOT FOR PATIENT USE")
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

	;Click first document in list
	MouseClick("left",617,319)
	$str = WinGetText("Chart - NOT FOR PATIENT USE")

	If(StringInStr($str,"Rx Refill")>0) Then
 		;right click Rx Request in documnet list to append reply
		;ControlClick("Chart - NOT FOR PATIENT USE","","[CLASS:SftTreeControl70; INSTANCE:1]","right",1,240,28)
		ConsoleWrite("Reply Rx Request via Append flow" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Reply Rx Request via Append flow" & @CRLF)
		MouseClick("right",617,319)
		Sleep(1000)
		Send("{DOWN 3}")
		Send("{ENTER}")

		ConsoleWrite("Verify Prescription Renewal request details sent by patient in Append Form (top panel)" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Verify Prescription Renewal request details sent by patient in Append Form (top panel)" & @CRLF)
		WinWaitActive("Append to Document")
		$str = WinGetText("Append to Document")

		If((StringInStr($str,$pharmacyName)>0) And (StringInStr($str,$medName)>0) And (StringInStr($str,$medDosage)>0) And (StringInStr($str,"Refills: " & $medRefills)>0) And (StringInStr($str,"Rx #: " & $prescriptionNo)>0) And (StringInStr($str,"Quantity: " & $medQuantity)>0) And (StringInStr($str,"NOTES: " & $medNotes)>0)) Then
			ConsoleWrite("Prescription Renewal Request details verified -- PASSED" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Prescription Renewal Request details verified -- PASSED" & @CRLF)

			ControlClick("Append to Document","","[CLASS:Button; INSTANCE:1]")
			ConsoleWrite("Click on full update" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Click on full update" & @CRLF)

			WinWaitActive("Append Document")
			If( WinActive("Append Document") ) Then
				ControlFocus("Append Document","","[CLASS:Edit; INSTANCE:1]")
;~ 				ControlSetText("Append Document","","[CLASS:Edit; INSTANCE:1]","** Medfusion Rx Refill (Append)")
;~ 				ControlClick("Append Document","","[CLASS:Button; INSTANCE:5]")
				Send("{DOWN}")

				ConsoleWrite("Select the Medfusion Rx Refill (Append) form" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Select the Medfusion Rx Refill (Append) form" & @CRLF)

				For $i = 0 To 40
					$text_v_okne = ControlGetText("Append Document","","[CLASS:Edit; INSTANCE:1]")

					If StringInStr($text_v_okne, "** Medfusion Rx Refill (Append)") > 0 Then
						ControlFocus("Append Document","","[CLASS:Button; INSTANCE:5]")
						Sleep(3000)
						ControlClick("Append Document","","[CLASS:Button; INSTANCE:5]")
						$i = 40
					EndIf
					ControlSend("Append Document","","[CLASS:ComboLBox; INSTANCE:1]",'{Down}')
				Next

				Sleep(5000)
				ConsoleWrite("Active Window is " & WinGetTitle("[ACTIVE]") & @CRLF)
				;Signing permission popoup if appears
				If(WinGetTitle("[ACTIVE]") == "Sign Document") Then
					ControlClick("Sign Document","","[CLASS:Button; INSTANCE:1]")
				EndIf

				;wait till Append form opens
				WinWaitActive("Update - " & $Fname & " " & $Lname & " -- Append")
				ConsoleWrite("Verify Prescription Renewal request details sent by patient in Rx Refill (right panel)" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Verify Prescription Renewal request details sent by patient in Rx Refill (right panel)" & @CRLF)
				WinWaitActive("Rx Refill")
				Sleep(20000)

				If((StringInStr($str,$pharmacyName)>0) And (StringInStr($str,$medName)>0) And (StringInStr($str,$medDosage)>0) And (StringInStr($str,"Refills: " & $medRefills)>0) And (StringInStr($str,"Rx #:" & $prescriptionNo)>0) And (StringInStr($str,"Quantity: " & $medQuantity)>0) And (StringInStr($str,"Note: " & $medNotes)>0)) Then
					ConsoleWrite("Prescription Renewal Request details verified -- PASSED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Prescription Renewal Request details verified -- PASSED" & @CRLF)
					WinClose("Rx Refill")

					WinActivate("Update - " & $Fname & " " & $Lname & " -- Append")
					WinSetState("Update - " & $Fname & " " & $Lname & " -- Append","",@SW_MAXIMIZE)
					MouseClick("left",410,220)
					ConsoleWrite("Set the Pharmacy" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Set the Pharmacy" & @CRLF)
					;Set Pharmacy
					WinWaitActive("Find Pharmacy")
					ControlFocus("Find Pharmacy","","[NAME:m_btnSearch]")
					ControlClick("Find Pharmacy","","[NAME:m_btnSearch]")
					Sleep(1000)
					ControlClick("Find Pharmacy","","[NAME:m_btnOk]")

					ConsoleWrite("Set the Medication" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Set the Medication" & @CRLF)
					;SEt Medication
					MouseClick("left",625,175)
					WinWaitActive("Update Medications")
					ControlFocus("Update Medications","","[CLASS:MLogicFormWindow; INSTANCE:1]")
					ControlClick("Update Medications","","[CLASS:MLogicFormWindow; INSTANCE:1]","left",1,30,480)
					WinWaitActive("New Medication")
					ControlFocus("New Medication","","[CLASS:Edit; INSTANCE:6]")
					Send("{DOWN 2}")
					Send("{ENTER}")
					WinWaitActive("Update Medications")
					ControlFocus("Update Medications","","[CLASS:MLogicFormWindow; INSTANCE:1]")
					ControlClick("Update Medications","","[CLASS:MLogicFormWindow; INSTANCE:1]","left",1,545,510)

					ConsoleWrite("Send reply message for Rx Request" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Send reply message for Rx Request" & @CRLF)
					WinActivate("Update - " & $Fname & " " & $Lname & " -- Append")
					ControlClick("Update - " & $Fname & " " & $Lname & " -- Append","","[CLASS:Button; INSTANCE:1]")
					Send("{TAB 3}")
					Send("{SPACE}")
					WinWaitActive("Centricity Practice Solution")
					ControlClick("Centricity Practice Solution","","[CLASS:Button; INSTANCE:1]")

					WinWaitActive("Update - " & $Fname & " " & $Lname & " -- Append")
					ConsoleWrite("Complete the document" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Complete the document" & @CRLF)
					MouseClick("left",1290,35)
					WinWaitActive("End Update")
					ControlFocus("End Update","","[CLASS:Button; INSTANCE:15]")
					ControlClick("End Update","","[CLASS:Button; INSTANCE:15]")

				Else
					ConsoleWrite("Prescription Renewal Request details did not match -- FAILED" & @CRLF)
					FileWriteLine($hFileOpen, _NowCalc() & "  -- Prescription Renewal Request details did not match -- FAILED" & @CRLF)
					Exit
				EndIf

			Else
				ConsoleWrite("ERROR Opening Append Document Window...." & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Opening Append Document Window...." & @CRLF)
			EndIf

		Else
			ConsoleWrite("Prescription Renewal Request details did not match -- FAILED" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Prescription Renewal Request details did not match -- FAILED" & @CRLF)
			Exit
		EndIf

	Else
		ConsoleWrite("Unable to fetch Rx Request...." & @CRLF)
		Exit
	EndIf
	ConsoleWrite("METHOD: replyRxAppend() ended" & @CRLF)
EndFunc



;-----Function to verify Incoming ToC in Direct Messaging Inbox
;-----Input parameter - $dateReceived = Date on which direct message was received
;-----Input parameter - $fromAddress = SES address from whom direct message was sent
;-----Input parameter - $toAddress = SES address to whom direct message was sent
;-----Input parameter - $msgSubject = Subject of direct message
;-----Input parameter - $msgBody = Body of direct message
;-----Input parameter - $attachmentName = Name of attachment sent along with direct message
Func verifyIncomingToCInInbox($dateReceived, $fromAddress, $toAddress, $msgSubject, $msgBody, $attachmentName)
	ConsoleWrite("METHOD: verifyIncomingToCInInbox() started" & @CRLF)
	;$arrConfig = Call("setConfig")
	$hFileOpen = Call("openLogFile")

	WinActivate("Direct Messaging")
	If(WinActive("Direct Messaging")) Then
		ConsoleWrite("Opening Direct Messaging Inbox" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Opening Direct Messaging Inbox" & @CRLF)
		;Navigate to Direct Messaging -> Messages
		ControlFocus("Direct Messaging","","[NAME:pbTOC]")
		ControlClick("Direct Messaging","","[NAME:pbTOC]")
		Sleep(1000)

		;Select SES address from drpdown
		ConsoleWrite("Select SES address " & $toAddress & " from dropdown" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Select SES address " & $toAddress & " from dropdown" & @CRLF)
		ControlFocus("Direct Messaging","","[NAME:cbTOCEmailIds]")
		While 1
			;ConsoleWrite("I am in While Loop")
			$inboxAddress = ControlGetText("Direct Messaging","","[NAME:cbTOCEmailIds]")
			;ConsoleWrite("Address is -->" & $inboxAddress & @CRLF)
			If(StringCompare($inboxAddress,$toAddress) = 0) Then
				ExitLoop
			Else
				ControlSend("Direct Messaging","","[NAME:cbTOCEmailIds]",'{DOWN}')
			EndIf
		WEnd
		Sleep(1000)
		;click Inbox
		ControlClick("Direct Messaging","","[NAME:lblRTOCInbox]")
		Sleep(1000)
		ControlClick("Direct Messaging","","[NAME:pbTOC]")

		ConsoleWrite("Select the message from LHS list" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Select the message from LHS list" & @CRLF)
		While(StringCompare($dateReceived,ControlGetText("Direct Messaging","","[NAME:lblDInTOCSentOn]"))<> 0)
			ControlFocus("Direct Messaging", "", "[NAME:pbTocInboxNext]")
			ControlClick("Direct Messaging", "", "[NAME:pbTocInboxNext]")
			Sleep(500)
		WEnd

		ConsoleWrite("Verify message details on RHS" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Verify message details on RHS" & @CRLF)

		ConsoleWrite("Verify sending provider address" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Verify sending provider address" & @CRLF)
		Call("assertData",$fromAddress, ControlGetText("Direct Messaging","","[NAME:lblDInTOCFromAddress]"))

		ConsoleWrite("Verify receiving provider address" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Verify receiving provider address" & @CRLF)
		Call("assertData",$toAddress, ControlGetText("Direct Messaging","","[NAME:lblDInTOCToAddress]"))

		ConsoleWrite("Verify sent date" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Verify sent date" & @CRLF)
		Call("assertData",$dateReceived, ControlGetText("Direct Messaging","","[NAME:lblDInTOCSentOn]"))

		ConsoleWrite("Verify message subject" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Verify message subject" & @CRLF)
		Call("assertData",$msgSubject, ControlGetText("Direct Messaging","","[NAME:lblDInTOCSubject]"))

		ConsoleWrite("Verify message body" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Verify message body" & @CRLF)
		$str = WinGetText("Direct Messaging")
		If(StringInStr($str,$msgBody)  > 0 ) Then
			ConsoleWrite("Message Body verified -- PASSED" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Message Body verified -- PASSED" & @CRLF)

		Else
			ConsoleWrite("Message Body did not match -- FAILED" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Message Body did not match -- FAILED" & @CRLF)
			Exit
		EndIf

		ConsoleWrite("Verify message attachment" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Verify message attachment" & @CRLF)
		If(StringInStr($str,$attachmentName)  > 0 ) Then
			ConsoleWrite("Attachment Name verified -- PASSED" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Attachment Name verified -- PASSED" & @CRLF)

		Else
			ConsoleWrite("Attachment Name did not match -- FAILED" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Attachment Name did not match -- FAILED" & @CRLF)
			Exit
		EndIf

	Else
		ConsoleWrite("ERROR Opening Direct Messaging UI...." & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Opening Direct Messaging UI...." & @CRLF)
	EndIf

	ConsoleWrite("METHOD: verifyIncomingToCInInbox() ended" & @CRLF)
EndFunc



;-----Function to format date
;-----Input parameter - $Date = Input date to be formatted
;-----Input parameter - $style = format to which date to be converted
Func _DateFormat($Date, $style)
    Local $hGui = GUICreate("My GUI get date", 200, 200, 800, 200)
    Local $idDate = GUICtrlCreateDate($Date, 10, 10, 185, 20)
    GUICtrlSendMsg($idDate, 0x1032, 0, $style)
    Local $sReturn = GUICtrlRead($idDate)
    GUIDelete($hGui)
    Return $sReturn
EndFunc



;-----Function to import ToC in Patient Chart
;-----Input parameter - $attachmentName = Name of attachment to be imported
;-----Input parameter - $firstName = First name of patient to whose chart ToC is to be imported (preferred unique First Name)
Func importToCToChart($attachmentName,$firstName)
	ConsoleWrite("METHOD: importToCToChart() started" & @CRLF)
	$hFileOpen = Call("openLogFile")

	WinActivate("Direct Messaging")
	ControlFocus("Direct Messaging","","[NAME:lblMsgAttachment2]")
	ControlClick("Direct Messaging","","[NAME:lblMsgAttachment2]")
	Sleep(2000)

	ConsoleWrite("Verify attachment name" & @CRLF)
	FileWriteLine($hFileOpen, _NowCalc() & "  -- Verify attachment name" & @CRLF)
	Call("assertData",$attachmentName, ControlGetText("Direct Messaging","","[NAME:lblMsgAttachment2]"))

	ControlFocus("Direct Messaging","","[NAME:panAddtoChart]")
	Sleep(2000)
	ControlClick("Direct Messaging","","[NAME:panAddtoChart]")

	ControlFocus("Direct Messaging","","[NAME:txtIncomingTOCMatPat]")
	ConsoleWrite("First Name is -->" & $firstName & @CRLF)
	ControlSend("Direct Messaging","","[NAME:txtIncomingTOCMatPat]",$firstName)
	Sleep(3000)
	ControlClick("Direct Messaging","","[NAME:dgvIncomingTOCMatPat]","left",1,83,30)
	Sleep(2000)
	WinWaitActive("Add to Chart")
	ControlClick("Add to Chart","","[CLASS:Button; INSTANCE:1]")
	Sleep(2000)
	If(StringInStr(WinGetText("[ACTIVE]"),"Attachment imported for patient :"&$firstName) >0) Then
		ConsoleWrite("Attachment imported successfully -- PASSED" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Attachment imported successfully -- PASSED" & @CRLF)
		WinKill("[ACTIVE]")

	Else
		ConsoleWrite("Attachment not imported -- FAILED" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Attachment not imported -- FAILED" & @CRLF)
		Exit
	EndIf

	ConsoleWrite("METHOD: importToCToChart() ended" & @CRLF)
EndFunc



;-----Function to download ToC
;-----Input parameter - $attachmentName = Name of attachment to be downloaded
;-----Input parameter - $location = path where attachment to be downloaded
;-----Input parameter - $firstName = First Name of patient for whom ToC was generated
;-----Input parameter - $lastName = Last Name of patient for whom ToC was generated
;-----Input parameter - $referalReason = Reason for referral mentioned in ToC
Func downloadToC($attachmentName,$location,$firstName,$lastName,$referalReason)
	ConsoleWrite("METHOD: downloadToC() started" & @CRLF)
	$hFileOpen = Call("openLogFile")

	WinActivate("Direct Messaging")
	ControlFocus("Direct Messaging","","[NAME:lblMsgAttachment2]")
	ControlClick("Direct Messaging","","[NAME:lblMsgAttachment2]")
	Sleep(2000)

	ConsoleWrite("Verify attachment name" & @CRLF)
	FileWriteLine($hFileOpen, _NowCalc() & "  -- Verify attachment name" & @CRLF)
	Call("assertData",$attachmentName, ControlGetText("Direct Messaging","","[NAME:lblMsgAttachment2]"))

	ControlFocus("Direct Messaging","","[NAME:panDownload]")
	Sleep(2000)
	ControlClick("Direct Messaging","","[NAME:panDownload]")

	WinWaitActive("Save As")

	ControlSend("Save As","", 1001,$location & "\" & $attachmentName,0)
	ControlClick("Save As", "", "[CLASS:Button; INSTANCE:1]")
	Sleep(2000)
	If(StringInStr(WinGetText("[ACTIVE]"),"File Downloaded Successfully") >0) Then
		ConsoleWrite("File downloaded successfully -- PASSED" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- File downloaded successfully -- PASSED" & @CRLF)
		WinKill("[ACTIVE]")

	Else
		ConsoleWrite("File not downloaded -- FAILED" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- File not downloaded -- FAILED" & @CRLF)
		Exit
	EndIf

	ConsoleWrite("Verify the contents of downloaded file" & @CRLF)
	FileWriteLine($hFileOpen, _NowCalc() & "  -- Verify the contents of downloaded file" & @CRLF)

	$fileHnd = FileOpen($location & "\" & $attachmentName, $FO_READ)
	Local $sFileRead = FileRead($fileHnd)
	FileClose($fileHnd)

		If((StringInStr($sFileRead, $firstName)>0) And (StringInStr($sFileRead, $lastName)>0) And (StringInStr($sFileRead, $referalReason)>0))  Then
			ConsoleWrite("Contents of ToC match -- PASSED" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Contents of ToC match -- PASSED" & @CRLF)

		Else
			ConsoleWrite("Contents of ToC did not match -- FAILED" & @CRLF)
			FileWriteLine($hFileOpen, _NowCalc() & "  -- Contents of ToC did not match -- FAILED" & @CRLF)
			Exit
		EndIf
	ConsoleWrite("METHOD: downloadToC() ended" & @CRLF)
EndFunc



;-----Function to schedule appointment
;-----Input parameter - $Fname = Patient's First Name
;-----Input parameter - $Lname = Patient's Last Name
;-----Input parameter - $facility = Facility to schedule appointment
;-----Input parameter - $resource = Resource t oschedule appointment
;-----Input parameter - $apptDt = Date for which appointment to be scheduled
;-----Input parameter - $respPvdr = Patient's responsible provider
;-----Input parameter - $apptStartTime = Start time for appointment to be scheduled
;-----Input parameter - $apptStopTime = Stop (End) time for appointment to be scheduled
Func scheduleAppointment($Fname,$Lname,$facility,$resource,$apptDt,$respPvdr,$apptStartTime,$apptStopTime)
	ConsoleWrite("METHOD: scheduleAppointment() started" & @CRLF)
	;$arrConfig = Call("setConfig")
	$hFileOpen = Call("openLogFile")

	WinActivate($title)
	ConsoleWrite("Activate GE CPS window" &@CRLF )

	Local $oIEEmbed =_IEAttach("Centricity Practice Solution","embedded",1)
	Sleep(1000)
	ConsoleWrite("Activating GE CPS App" &@CRLF)
	FileWriteLine($hFileOpen, _NowCalc() & "  -- Activating GE CPS App" &@CRLF)
	WinWaitActive("Centricity Practice Solution")

	ConsoleWrite("Active Window is " & WinGetTitle("[ACTIVE]") & @CRLF)

	If ( IsObj($oIEEmbed) ) Then
		ConsoleWrite("Attached successfully to IE instance running under GE app" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Attached successfully to IE instance running under GE app" & @CRLF)

		ConsoleWrite('@@ Debug(' & @ScriptLineNumber & ') : $oIEEmbed = ' & $oIEEmbed & @CRLF & '>Error code: ' & @error & '    Extended code: 0x' & Hex(@extended) & @CRLF) ;### Debug Console
		ConsoleWrite("Embedded IE Url " &  _IEPropertyGet($oIEEmbed, "locationurl") &@CRLF )
		Sleep(1000)

		Local $oDoc = _IEBodyReadHTML($oIEEmbed)
		Local $oLinkCollection = _IELinkGetCollection($oIEEmbed)

		ConsoleWrite("Finding links collection" & @CRLF)
		If ( IsObj($oLinkCollection) ) Then
			$lCount = 0
			For $oLink in $oLinkCollection
				$lCount = $lCount + 1
			Next
		;_ArrayDisplay($oLinkCollection)
		ConsoleWrite("Links found - " & $lCount & @CRLF)
		EndIf

		ConsoleWrite("Clicking SCHEDULING link of GE CPS App...." & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Clicking SCHEDULING link of GE CPS App" & @CRLF)
		Sleep(5000)
		_IELinkClickByIndex($oIEEmbed,1)

		WinWaitActive("Open Schedule")
		;Facility
		ConsoleWrite("Enter Facility for scheduling appointment" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Enter Facility for scheduling appointment" & @CRLF)
		ControlFocus("Open Schedule","","[CLASS:AfxOleControl110; INSTANCE:1]")
		ControlClick("Open Schedule","","[CLASS:AfxOleControl110; INSTANCE:1]")
		WinWaitActive("Find Facility")
		ConsoleWrite("Search and Select Facility for scheduling appointment" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Search and Select Facility for scheduling appointment" & @CRLF)
		ControlSend("Find Facility","","[CLASS:Edit; INSTANCE:1]",$facility)
		ControlClick("Find Facility","","[CLASS:Button; INSTANCE:3]")
		Sleep(1000)
		ControlClick("Find Facility","","[CLASS:ListBox; INSTANCE:2]","left",2)

		WinWaitActive("Open Schedule")
		;Resource
		ConsoleWrite("Enter Resource for scheduling appointment" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Enter Resource for scheduling appointment" & @CRLF)
		ControlFocus("Open Schedule","","[CLASS:AfxOleControl110; INSTANCE:2]")
		ControlClick("Open Schedule","","[CLASS:AfxOleControl110; INSTANCE:2]")
		WinWaitActive("Find Resource")
		ConsoleWrite("Search and Select Resource for scheduling appointment" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Search and Select Resource for scheduling appointment" & @CRLF)
		ControlSend("Find Resource","","[CLASS:Edit; INSTANCE:1]",$resource)
		ControlClick("Find Resource","","[CLASS:Button; INSTANCE:3]")
		Sleep(1000)
		ControlClick("Find Resource","","[CLASS:ListBox; INSTANCE:2]","left",2)

		WinWaitActive("Open Schedule")
		;Appointment Date
		ConsoleWrite("Enter Date for scheduling appointment" & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- Enter Date for scheduling appointment" & @CRLF)
		ControlFocus("Open Schedule","","[CLASS:Edit; INSTANCE:3]")
		Send("{DEL 8}")
		ControlSend("Open Schedule","","[CLASS:Edit; INSTANCE:3]",$apptDt)
		ControlClick("Open Schedule","","[CLASS:Button; INSTANCE:5]")

		$scheduleApptWnd = "Schedule " & $facility & " - " & $apptDt & " (" & $resource & ")"
		WinWaitActive($scheduleApptWnd)
			;click new appointment
			ControlClick($scheduleApptWnd,"","[CLASS:ToolbarWindow32; INSTANCE:3]","left",1,46,12)
			Sleep(2000)

			;handling popup
			If((WinGetTitle("[ACTIVE]") = "Centricity Practice Solution") And (StringInStr(WinGetText("[ACTIVE]"),"Some portion of the time you are attempting to book is not available at this facility."))) Then
				ControlClick("Centricity Practice Solution","Some portion of the time you are attempting to book is not available at this facility.","[CLASS:Button; INSTANCE:4]")
			EndIf

			WinWaitActive("Find Patient")

			If(WinActive("Find Patient")) Then
				ConsoleWrite("Find the patient for whom appointment to be scheduled" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Find the patient for whom appointment to be scheduled" & @CRLF)
				ControlFocus("Find Patient","","[CLASS:Edit; INSTANCE:1]")
				ControlSetText("Find Patient","","[CLASS:Edit; INSTANCE:1]",$Lname)
				Sleep(1000)
				ControlClick("Find Patient","","[CLASS:Button; INSTANCE:2]")
				Sleep(2000)
				ControlClick("Find Patient","","[CLASS:Button; INSTANCE:8]")

				$newApptWnd = "New Appointment - " & $Lname & ", " & $Fname
				WinWaitActive($newApptWnd)
				;Responsible Provider
				ConsoleWrite("Enter responsible provider for patient" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Enter responsible provider for patient" & @CRLF)
				ControlFocus($newApptWnd,"","[CLASS:AfxOleControl110; INSTANCE:3]")
				ControlClick($newApptWnd,"","[CLASS:AfxOleControl110; INSTANCE:3]")
				WinWaitActive("Find Provider")
				ConsoleWrite("Search and Select Responsible provider for patient" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Search and Select Responsible provider for patient" & @CRLF)
				ControlSend("Find Provider","","[CLASS:Edit; INSTANCE:1]",$respPvdr)
				ControlClick("Find Provider","","[CLASS:Button; INSTANCE:3]")
				Sleep(1000)
				ControlClick("Find Provider","","[CLASS:Button; INSTANCE:9]")

				WinWaitActive($newApptWnd)
				;Appointment Type
				ConsoleWrite("Enter appointment type" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Enter appointment type" & @CRLF)
				ControlFocus($newApptWnd,"","[CLASS:AfxOleControl110; INSTANCE:5]")
				ControlClick($newApptWnd,"","[CLASS:AfxOleControl110; INSTANCE:5]")
				WinWaitActive("Find Appointment Type")
				ConsoleWrite("Search and Select appointment type" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Search and Select appointment type" & @CRLF)
				ControlClick("Find Appointment Type","","[CLASS:Button; INSTANCE:3]")
				Sleep(1000)
				ControlClick("Find Appointment Type","","[CLASS:ListBox; INSTANCE:1]","left",2)

				WinWaitActive($newApptWnd)
				;Appt Start Time
				ConsoleWrite("Enter appointment start time" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Enter appointment start time" & @CRLF)
				ControlFocus($newApptWnd,"","[CLASS:Edit; INSTANCE:14]")
				Send("{DEL 6}")
				ControlSend($newApptWnd,"","[CLASS:Edit; INSTANCE:14]",$apptStartTime)

				;Appt Stop Time
				ConsoleWrite("Enter appointment end time" & @CRLF)
				FileWriteLine($hFileOpen, _NowCalc() & "  -- Enter appointment end time" & @CRLF)
				ControlFocus($newApptWnd,"","[CLASS:Edit; INSTANCE:15]")
				Send("{DEL 6}")
				ControlSend($newApptWnd,"","[CLASS:Edit; INSTANCE:15]",$apptStopTime)
				Sleep(2000)

				ControlClick($newApptWnd,"","[CLASS:Button; INSTANCE:5]")
				Sleep(2000)

				;handling popup
				If((WinGetTitle("[ACTIVE]") = "Centricity Practice Solution") And (StringInStr(WinGetText("[ACTIVE]"),"Some portion of the time you are attempting to book is not available at this facility. Would you like to create an overbooked appointment?"))) Then
					ControlClick("Centricity Practice Solution","Some portion of the time you are attempting to book is not available at this facility. Would you like to create an overbooked appointment?","[CLASS:Button; INSTANCE:3]")
				EndIf

			Else
				ConsoleWrite("ERROR Inactive Find Patient Window...." & @CRLF)
 				FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Inactive Find Patient Window...." & @CRLF)
				Exit
			EndIf

	Else
		ConsoleWrite("ERROR Attaching IE Process Within GE CPS App...." & @CRLF)
		FileWriteLine($hFileOpen, _NowCalc() & "  -- ERROR Attaching IE Process Within GE CPS App...." & @CRLF)
		Exit
	EndIf
	ConsoleWrite("METHOD: scheduleAppointment() ended" & @CRLF)
EndFunc