@Regression
Feature: Test fuctionality of Appointment precheck

  Background: Login to practice provisioning portal
    Given user lauch practice provisioning url
    When user enter username and password

  Scenario: Verify Broadcast is sent successfully when filter is applied for within one month
    Then schedule an appointments within one month
    Then enter date and time within one month
    Then filter is applied for provider and Location
    Then select patients based on filters
    Then send broadcast message to all selected patient
    And verify count in banner message is coming correct
    Then logout from practice provisioning portal

  Scenario: Verify Broadcast is sent successfully when filter is applied for future month
    Then schedule an appointments for future
    Then enter future date and time
    Then filter is applied for provider and Location
    Then select patients based on filters
    Then send broadcast message to all selected patient
    And verify count in banner message is coming correct
    Then logout from practice provisioning portal

  Scenario: Verify Broadcast is sent successfully when filter is applied for backdated greater than one month
    Then enter date and time for backdated greater than one month
    Then filter is applied for provider and Location
    Then select patients based on filters
    Then send broadcast message to all selected patient
    And verify the count in banner message is coming correct for one month backdated
    Then logout from practice provisioning portal

  Scenario: Verify Broadcast is sent successfully when filter is applied only for provider for within one month
    Then enter date and time within one month
    Then filter is applied only for provider
    Then select patients based on filters
    Then send broadcast message to all selected patient
    And verify count in banner message is coming correct
    Then logout from practice provisioning portal

  Scenario: Verify Broadcast is sent successfully when filter is applied only for location for within one month
    Then enter date and time within one month
    Then filter is applied only for location
    Then select patients based on filters
    Then send broadcast message to all selected patient
    And verify count in banner message is coming correct
    Then logout from practice provisioning portal

  Scenario: Verify Broadcast is sent successfully when filter is applied only for provider for backdated greater than one month
    Then enter date and time for backdated greater than one month
    Then filter is applied only for provider
    Then select patients based on filters
    Then send broadcast message to all selected patient
    And verify the count in banner message is coming correct for one month backdated
    Then logout from practice provisioning portal

  Scenario: Verify Broadcast is sent successfully when filter is applied only for location for backdated greater than one month
    Then enter date and time for backdated greater than one month
    Then filter is applied only for location
    Then select patients based on filters
    Then send broadcast message to all selected patient
    And verify the count in banner message is coming correct for one month backdated
    Then logout from practice provisioning portal

  @Feature
  Scenario: Verify if notification checkbox turned off from setting then on UI broadcast message and reminder column should not be displayed.
    Then go to the setting dashboard and disable notifications checkbox from manage solution in general tab
    Then on appointments dashboard user is not able to see send reminder and broadcast message column
    And on appointments dashboard user is not able to see send reminder and broadcast message button in actions dropdown
    Then go to the setting dashboard and enable notifications checkbox from manage solution in general tab
    Then logout from practice provisioning portal

  Scenario: Verify if select all functionality is working when filter is applied on location and broadcast is sent from page 2 or page 3 and verify the banner count verify broadcast logs and verify on clicking refresh button
    Then schedule an appointments within one month
    Then enter date and time within one month
    Then filter is applied for provider and Location
    Then select patients based on filters and select banner then move to second page
    Then send broadcast message to all selected patient
    And verify banner message should show count for failure and sucess
    And verify after clicking on refresh button banner should not get close and current page should be displayed
    Then logout from practice provisioning portal

  Scenario: Verify if enable broadcast button in setting is off then in action button broadcast section is not seen. Verify if enable broadcast button in setting is on then in action button broadcast section is not seen
    When from setting dashboard in notifications disable Broadcast messaging checkbox
    Then verify on appointments dashboard user is not able to see Broadcast message button in actions dropdown
    When from setting dashboard in notifications Enable Broadcast messaging checkbox
    Then verify on appointments dashboard user is able to see Broadcast message button in actions dropdown
    Then logout from practice provisioning portal

  Scenario: Verify broadcast count for blank email and phone number in banner
    When schedule an appointment without email and phone number
    Then select patient which is having blank email and phone number and send broadcast message
    And verify failed count will consider for blank email and phone number
    Then logout from practice provisioning portal

  Scenario: Verify broadcast count for invalid email and blank phone number
    When schedule an appointment with invalid email and phone number
    Then select patient which is having invalid email and blank phone number and send broadcast message
    And verify broadcast message send successfully and success count will consider for invalid email and and blank phone number
    Then logout from practice provisioning portal

  Scenario: Verify broadcast count for invalid phone number and blank email
    When schedule an appointment with invalid phone number and blank email
    Then select patient which is having invalid phone number and blank email and send broadcast message
    And verify broadcast message send successfully and count will consider for invalid phone number and blank email
    And also verify banner will close after clicked on cross button
    Then logout from practice provisioning portal

  Scenario: Verify if after selecting patients,count will be reflected on send reminder and broadcast message button
    When select patients and click on actions dropdown
    And verify count will be reflected on send reminder and broadcast message button
    Then logout from practice provisioning portal

  Scenario: Verify if only email is enable from notification then in broadcast column only mail column should displayed on appointment dashboard
    When from setting dashboard in general enable email check box and disable text checkbox
    Then verify on appointment dashboard user is able to see only mail column under send reminder column and Text column is disappear
    And from setting dashboard in general enable email check box and enable text checkbox
    And logout from practice provisioning portal

  Scenario: Verify if only email is enable from notification then in reminder column only mail column should displayed on appointment dashboard
    When from setting dashboard in general enable email check box and disable text checkbox
    Then verify on appointment dashboard user is able to see only mail column under broadcast message column and Text column is disappear
    And from setting dashboard in general enable email check box and enable text checkbox
    And logout from practice provisioning portal

  Scenario: verify if text is enable from notification and email is disable then in broadcast column only text column should displayed in appointment dashboard
    When from setting dashboard in general text checkbox is enable and email checkbox is disable
    Then verify on appointment dashboard user is able to see only text column under broadcast message column and mail column is disappear
    And from setting dashboard in general enable email check box
    And logout from practice provisioning portal

  Scenario: verify if text is enable from notification and email is disable then in reminder column  only text column should displayed in appointment dashboard
    When from setting dashboard in general text checkbox is enable and email checkbox disable
    Then verify on appointment dashboard user is able to see only text column under send reminder column and mail column is disappear
    And from setting dashboard in general enable email check box
    And logout from practice provisioning portal

  Scenario: Verify if the Broadcast message is not sent to any patients when start date and end date is more than passed one month
    When enter date and time for backdated greater than one month
    And filter is applied only for provider
    And select patients based on filters
    And send broadcast message to all selected patient
    Then verify the count in banner message is coming correct for one month backdated
    And logout from practice provisioning portal

  Scenario: verify in appointment dashboard broadcast column for email and text should be visible by default count will be zero
    When in setting dashboard in notifications Enable Broadcast messaging checkbox
    And schedule an appointments
    Then verify in appointment dashboard broadcast column for email and text should be visible by default count will be zero
    And logout from practice provisioning portal

  Scenario: Verify on clicking icon for email and text pop up appears and patient name , time , status , message field is displayed
    When in setting dashboard in notifications Enable Broadcast messaging checkbox
    And schedule an appointments
    Then Verify on clicking icon for email and text pop up appears and patient name , time , status , message field is displayed
    And logout from practice provisioning portal

  Scenario: verify if text and email are enable from Notification setting then text and email should be display on appointment dashboard
    When schedule a new appointment
    And from setting dashboard in general enable email and text checkbox
    And from notifications in setting dashboard Enable Broadcast messaging checkbox
    Then verify in appointment dashboard in broadcast column  email and text should be visible and by default count will be zero
    And logout from practice provisioning portal

  Scenario: verify if text enable and email disable from notification setting then on text broadcast icon , broadcast logs should appear with patient name , time ,message and status field
    When schedule a new appointment
    And from setting in notifications dashboard Enable Broadcast messaging checkbox
    And from setting in general text checkbox is enable and email checkbox is disable
    Then verify on clicking on text broadcast icon , broadcast logs should appear with patient name , time ,message and status field
    And logout from practice provisioning portal

  Scenario: verify if text disable and email enable from notification setting then broadcast will be sent to patient on email only and broadcast text column entry will not be seen
    When schedule a new appointment
    And from setting in notifications dashboard Enable Broadcast messaging checkbox
    And from setting in general text checkbox is disable and email checkbox is enable
    And select patient and send broadcast
    Then verify on appointment dashboard in broadcast broadcast will be sent to patient on email only and broadcast text column entry will not be seen
    And logout from practice provisioning portal

  Scenario: verify if when notification setting are disable then appointment dashboard does not show broadcast column
    And from setting in notifications dashboard Enable Broadcast messaging checkbox
    When from setting in general disable notification setting
    Then verify in appointment dashboard in broadcast column text and email column should not be display
    And logout from practice provisioning portal

  Scenario: Verify if pop up message is coming as per requirement on clicking remove button
    When schedule a new appointment
    And select patient and from action dropdown click on remove
    Then Verify the pop up message is displayed as per requirement
    And logout from practice provisioning portal

  Scenario: verfiy after deleting single appointments message at the top indicated how many were deleted successfully without clicking on refresh
    When schedule a new appointment and confirm
    And select patient and from action dropdown click on remove
    Then verify after click on confirm banner message should be display and appointment should be deleted successfully
    And logout from practice provisioning portal

  Scenario: verfiy after deleting multiple appointments message at the top indicated how many were deleted successfully without clicking on refresh
    When logout from practice provisioning portal
    And user login to practice provisioning
    And schedule multiple new appointments and confirm
    And select multiple patients and from action dropdown click on remove
    Then verify after deleting multiple appointments banner message should be display and appointment should be deleted successfully
    And logout from practice provisioning portal

  Scenario: verfiy after deleting all appointments message at the top indicated how many were deleted successfully without clicking on refresh
    When logout from practice provisioning portal
    And user login to practice provisioning
    And schedule multiple appointments and confirm
    And select multiple patients and from action dropdown click on remove
    Then verify after deleting multiple appointments banner message should be display and appointment should be deleted successfully
    And logout from practice provisioning portal

  Scenario: verify on selecting all 50+ appointment records remove button should not be enabled
    When apply the filter such that count of appointment is greater than fifty count and more than one page is coming
    And select all appointments from appointment dashboard and select banner message
    Then verify remove button should be disabled and only broadcast button should be enabled
    And logout from practice provisioning portal

  Scenario: To verify after deleting appointment and clicking on refresh button page count should be same as it was after deleting record
    When logout from practice provisioning portal
    And user login to practice provisioning
    And schedule multiple appointments
    And from appointment dashboard select all appointments and remove from action dropdown
    Then verify after clicking on refresh button page count should be count should be same
    And logout from practice provisioning portal

  Scenario: verify if notification are off then only create and remove button are visible
    When from setting in general disable notification setting
    Then verify if notification are off then only create appointment and remove button are visible.
    And logout from practice provisioning portal

  Scenario: verify if create button functionality is working and appointment is getting created
    When from setting in general disable notification setting
    And from appointment dashboard click on action button and click on create option for schedule new appointment
    Then verify appointment get succesfully created and from remove button suceesfully deleted
    And logout from practice provisioning portal

  Scenario: verify refresh button functionality is working fine when confirmed appointment
    When schedule a new appointment
    And confirm appointment
    Then verify after refresh appointment should get confirmed and green tick should be appeared in apt dashbord
    And logout from practice provisioning portal

  Scenario: verify refresh button functionality is working fine when curbside checkin and curbside arrival done
    When schedule a new appointment
    And confirm appointment
    And curbside checkin and curbside arrival done
    Then verify after refresh appointment should get curbside checkin done and black check in symbol should appeared
    And logout from practice provisioning portal

  Scenario: verify refresh button functionality is working fine when check in done
    When schedule a new appointment
    And confirm appointment
    And curbside checkin and curbside arrival done
    And check in done
    Then verify after refresh appointment should get check in done and green check in symbol should appeared
    And logout from practice provisioning portal

  Scenario: verify refresh button functionality is working fine when manual reminder send
    When schedule a new appointment
    And confirm appointment
    And curbside checkin and curbside arrival done
    And check in done
    And select patient and send manual reminder to patient
    Then verify after refresh appointment manual reminder paper plane symbol should appear after sending reminder
    And logout from practice provisioning portal

  Scenario: verify refresh button functionality is working fine when broadcast send
    When schedule a new appointment
    And confirm appointment
    And curbside checkin and curbside arrival done
    And check in done
    And get broadcast count before broadcast send
    And select patient and send broadcast
    Then verify after refresh appointment broadcast count should get updated after sending broadcast
    And logout from practice provisioning portal

  Scenario: verify if on selecting few records from appointment dashboard and clicking on action and later on refresh button count should be same for broadcast and reminder
    When schedule multiple appointments and select patients
    And click on actions button and get broadcast message and send reminder count
    Then verify after click on refresh button count should be same for broadcast message and send reminder button
    And logout from practice provisioning portal

  Scenario: verify on selecting all records and later going to page 2 and redirecting to page 1 system does show the selected banner and appointment count should be same on brodcast button
    When enter date and time within one month
    And select all patients and click on banner
    And click on actions button and only broadcast count should be seen
    And going to second page and redirecting to page one
    Then verify system should show the selected banner and appointment count should be same on brodcast button by clicking action button
    And logout from practice provisioning portal

  Scenario: verify on selecting all records from page 1 and going to page 2 only create button should be enabled
    When enter date and time within one month
    And select all patients and going to second page
    And verify after clicking on action only create button should be enabled
    And logout from practice provisioning portal

  Scenario: verify if on selecting few records or selecting all records broadcast,send reminder,remove button count gets updated
    When schedule multiple appointments and select patients
    And click on action and get count for broadcast, send reminder, remove button
    Then verify when select all records count get updated for broadcast,send reminder,remove button in action button
    And logout from practice provisioning portal

  Scenario: verify on selecting all records from page 1 and later deselecting and going to next page 2 and coming back to page 1 system does not selected records and in action button only create button should be enabled
    When enter date and time within one month
    And select all patients and later deselect
    And going to second page and coming back to page one
    Then Verify system does not show selected records and in action button only create button should be enabled
    And logout from practice provisioning portal

  Scenario: verify if user is not able to see build and preview section for appointment reminders in edit for text
    When from setting in notifications user click on text edit section of appointment reminders
    Then verify user is not able to see preview and build section on template editor page
    And logout from practice provisioning portal

  Scenario: verify if user is not able to see build and preview appointment reminders in edit for email
    When from setting in notifications user click on email edit section of appointment reminders
    And verify user is not able to see preview and build section on template editor page
    And logout from practice provisioning portal

  Scenario: verify if system is not allowing user to enter 0 in timing unit section for sms in appointment reminders
    When from setting in notifications user click on text edit section of appointment reminders
    Then verify user not able to enter zero in timing unit section for sms in appointment reminders
    And logout from practice provisioning portal

  Scenario: verify if system is not allowing user to enter 0 in timing unit section for sms in appointment reminders
    When from setting in notifications user click on email edit section of appointment reminders
    Then verify user not able to enter zero in timing unit section for email in appointment reminders
    And logout from practice provisioning portal

  Scenario: verify on preview page from appointment confirmation all fields display properly for email
    When from setting in notifications user click on email hamburgerButton section
    And user click preview of appointment confirmation
    Then verify in settings section all fields are display related to email templates
    And logout from practice provisioning portal

  Scenario: verify desktop view in preview page from appointment confirmation all fields display properly for email
    When from setting in notifications user click on email hamburgerButton section
    And user click preview of appointment confirmation
    Then verify in desktop view all fields are display related to templates
    And logout from practice provisioning portal

  Scenario: verify mobile view in preview page from appointment confirmation all fields display properly for email
    When from setting in notifications user click on email hamburgerButton section
    And user click preview of appointment confirmation
    Then verify in mobile view all fields are display related to templates
    And logout from practice provisioning portal

  Scenario: verify on preview page from appointment confirmation all fields display properly for texts
    When from setting in notifications user click on text hamburgerButton section
    And user click preview of appointment confirmation
    Then verify in settings section all fields are display related to templates
    And logout from practice provisioning portal

  Scenario: verify desktop view in preview page from appointment confirmation all fields display properly for text
    When from setting in notifications user click on text hamburgerButton section
    And user click preview of appointment confirmation
    Then verify in setting section view all fields are display related to text templates
    And logout from practice provisioning portal

  Scenario: verify mobile view in preview page from appointment confirmation all fields display properly for email for existing practice
    When from setting in notifications user click on email hamburgerButton section
    And user click preview of appointment confirmation
    Then verify in mobile view all fields are display related to templates
    And logout from practice provisioning portal

  Scenario: verify mobile view in preview page from appointment confirmation all fields display properly for email for new practice
    When logout from practice provisioning portal
    And user login to new practice
    And from setting in notifications user click on email hamburgerButton section
    And user click preview of appointment confirmation
    Then verify in mobile view all fields are display related to templates for new practice
    And logout from practice provisioning portal

  Scenario: verify on preview page from appointment confirmation all fields display properly for texts
    When from setting in notifications user click on text hamburgerButton section
    And user click preview of appointment confirmation
    Then verify in settings section all fields are display related to templates
    And logout from practice provisioning portal

  Scenario: verify on preview page from appointment reminder mail template are properly display for desktop view
    When from setting in notifications user click on email hamburgerButton section of appointment reminder
    And user click preview of appointment reminder
    Then verify in desktop view all fields are display related to templates for appointment reminder
    And logout from practice provisioning portal

  Scenario: verify on preview page from appointment reminder mail template are properly display for desktop view for new practice
    When logout from practice provisioning portal
    And user login to new practice
    And from setting in notifications user click on email hamburgerButton section of appointment reminder
    And user click preview of appointment reminder
    Then verify in desktop view all fields are display related to templates for appointment reminder for new practice
    And logout from practice provisioning portal

  Scenario: verify on preview page from appointment reminder mail template are properly display for mobile view
    When from setting in notifications user click on email hamburgerButton section of appointment reminder
    And user click preview of appointment reminder
    Then verify in mobile view all fields are display related to templates for appointment reminder
    And logout from practice provisioning portal

  Scenario: verify on preview page from appointment reminder mail template are properly display for mobile view for new practice
    When logout from practice provisioning portal
    And user login to new practice
    And from setting in notifications user click on email hamburgerButton section of appointment reminder
    And user click preview of appointment reminder
    Then verify in mobile view all fields are display related to templates for appointment reminder for new practice
    And logout from practice provisioning portal

  Scenario: verify on preview page from appointment reminder all fields display properly for email
    When from setting in notifications user click on email hamburgerButton section of appointment reminder
    And user click preview of appointment reminder
    Then verify in settings section all fields are display related to email templates of appointment reminder
    And logout from practice provisioning portal

  Scenario: verify desktop view in preview page from appointment reminder all fields display properly for email
    When from setting in notifications user click on email hamburgerButton section of appointment reminder
    And user click preview of appointment reminder
    Then verify in desktop view all fields are display related to templates for appointment reminder
    And logout from practice provisioning portal

  Scenario: verify mobile view in preview page from appointment reminder all fields display properly for email
    When from setting in notifications user click on email hamburgerButton section of appointment reminder
    And user click preview of appointment reminder
    Then verify in mobile view all fields are display related to templates for appointment reminder
    And logout from practice provisioning portal

  Scenario: verify on preview page from appointment reminder after clickinh on edit button working properly for email
    When from setting in notifications user click on email hamburgerButton section of appointment reminder
    And user click preview of appointment reminder
    Then verify on preview page from appointment reminder edit button working properly
    And logout from practice provisioning portal

  Scenario: verify on preview page from appointment reminder all fields display properly for text
    When from setting in notifications user click on text hamburgerButton section of appointment reminder
    And user click preview of appointment reminder
    Then verify in settings section all fields are display related to text templates of appointment reminder
    And logout from practice provisioning portal

  Scenario: verify on preview page from appointment reminder after clicking on edit button working properly for text
    When from setting in notifications user click on text hamburgerButton section of appointment reminder
    And user click preview of appointment reminder
    Then verify on preview page from appointment reminder edit button working properly
    And logout from practice provisioning portal

  Scenario: verify on preview page from appointment reminder all fields display properly for text
    When from setting in notifications user click on text hamburgerButton section of appointment reminder
    And user click preview of appointment reminder
    Then verify in settings section all fields are display related to text templates of appointment reminder
    And logout from practice provisioning portal

  Scenario: verify if info tab (triangle ) near Send Notification text shows info Will not affect Appointment Confirmation sent via PSS when mouse hover on traingle
    When user mouse hover on info tab from setting in notifications
    Then verify 'Will not affect Appointment Confirmations sent via PSS' message will be display
    And logout from practice provisioning portal

  Scenario: verify if user is able to see broadcast column on appointment dashboard after disable broadcast messaging checkbox
    When from setting dashboard in notifications disable Broadcast messaging checkbox
    Then verify in appointment dashboard in broadcast column should not be display
    And from setting dashboard in notifications Enable Broadcast messaging checkbox
    And logout from practice provisioning portal

  Scenario: verify if user is able to see additional arrival instruction message in english language when check-in tab is visible
    When from setting in notifications user click on curbside checkin tab and click on english button
    Then verify if user is able see additional arrival instruction message in english
    And logout from practice provisioning portal

  Scenario: verify if user is able to see additional arrival instruction message in spanish language when check-in tab is visible
    When from setting in notifications user click on curbside checkin tab and click on spanish button
    Then verify if user is able see additional arrival instruction message in spanish
    And logout from practice provisioning portal

  Scenario: verify if user is able to see arrival confirmation message in english language in english tab when check-in tab is visible
    When from setting in notifications user click on curbside checkin tab and click on english button
    Then verify if user is able see arrival confirmation message in english
    And logout from practice provisioning portal

  Scenario: verify if user is able to see arrival confirmation message in spanish language in spanish tab when check-in tab is visible
    When from setting in notifications user click on curbside checkin tab and click on spanish button
    Then verify if user is able see arrival confirmation message in spanish
    And logout from practice provisioning portal

  Scenario: verify if user is able to see two section under notification type one for appointment confirmation and another for appointment reminder
    When from setting in notifications user click on notification tab
    Then verify if user is able to see appointment confirmation and appointment reminder section
    And logout from practice provisioning portal

  Scenario: verify if user is able to see two templates display under appointment confirmation section one for mail and another for text
    When from setting in notifications user click on notification tab
    Then verify if user is able to see two templates one for mail and another for text templates under appointment confirmation section
    And logout from practice provisioning portal

  Scenario: verify if user is able to see two templates display under appointment reminder one for mail and another for text
    When from setting in notifications user click on notification tab
    Then verify if user is able to see two templates one for mail and another for text templates under appointment reminder section
    And logout from practice provisioning portal

  Scenario: verify if user is able to see published status for all the templates under appointment confirmation and appointment reminder section.
    When from setting in notifications user click on notification tab
    Then verify if user is able to see published status under appointment reminder and appointment confirmation section
    And logout from practice provisioning portal

  Scenario: verify if user is able to see appointment confirmation text in bold fonts also user is able to see info icon along with this
    When from setting in notifications user click on notification tab
    Then verify if user is able to see appointment confirmation text in bold fonts along with info icon
    And logout from practice provisioning portal

  Scenario: verify if user is able to mouse hover over to info icon on appointment confirmation section then system shows one message on pop up.
    When from setting in notifications user click on notification tab
    Then verify mouse hover over to info icon and system shows 'Appointment confirmations are communications sent to notify a patient that an appointment has been scheduled.' on pop up
    And logout from practice provisioning portal

  Scenario: verify in delivery method section mail and text fields are display under appointment confirmation section
    When from setting in notifications user click on notification tab
    Then verify in delivery method section mail and text fields are display under appointment confirmation section
    And logout from practice provisioning portal

  Scenario: verify user is able to see default status under version section of appointment confirmation
    When from setting in notifications user click on notification tab
    Then verify user is able to see default status for mail and sms under version section of appointment confirmation
    And logout from practice provisioning portal

  Scenario: verify by default user is able to see in office status under appointment method section of appointment confirmation
    When from setting in notifications user click on notification tab
    Then verify by default user is able to see in office status for mail and text fields under appointment method section of appointment confirmation
    And logout from practice provisioning portal

  Scenario: verify by default user is able to see upon scheduling status under timing section of appointment confirmation
    When from setting in notifications user click on notification tab
    Then verify user is able to see upon scheduling status for mail and text fields under timing section of appointment confirmation
    And logout from practice provisioning portal

  Scenario: verify by default user is able to see published status under status section of appointment confirmation
    When from setting in notifications user click on notification tab
    Then verify by default user is able to see published status under status section for mail and text fields under timing section of appointment confirmation
    And logout from practice provisioning portal

  Scenario: verify if user is able to see appointment reminder text in bold fonts also user is able to see info icon along with this
    When from setting in notifications user click on notification tab
    Then verify if user is able to see appointment reminder text in bold fonts along with info icon
    And logout from practice provisioning portal

  Scenario: verify if user is able to mouse hover over to info icon on Appointment reminders section then system shows one message on pop up.
    When from setting in notifications user click on notification tab
    Then verify mouse hover over to info icon of appointment reminders and system shows 'Appointment reminders are communications sent to remind a patient they have an upcoming appointment.' on pop up
    And logout from practice provisioning portal

  Scenario: verify in delivery method section mail and text fields are display under appointment reminders
    When from setting in notifications user click on notification tab
    Then verify in delivery method section mail and text fields are display under appointment reminders section
    And logout from practice provisioning portal

  Scenario: verify user is able to see default status under version section of appointment reminders
    When from setting in notifications user click on notification tab
    Then verify user is able to see default status for mail and sms under version section of appointment reminders
    And logout from practice provisioning portal

  Scenario: verify by default user is able to see in office status under appointment method section of appointment reminders
    When from setting in notifications user click on notification tab
    Then verify by default user is able to see in office status for mail and text fields under appointment method section of appointment reminders
    And logout from practice provisioning portal

  Scenario: verify if day, hour and minutes are configured in email and sms template then it reflect in timing section appointment reminder
    When from setting in notifications user click on notification tab
    Then verify if day,hour and minutes are configured in email and sms template then it reflect in timing section of appointment reminder
    And logout from practice provisioning portal

  Scenario: verify is able to see day,hours and minutes in proper orders and proper sequence in timing section of appointment reminder
    When from setting in notifications user click on notification tab
    Then verify is able to see day,hours and minutes in proper orders and proper sequence in timing section of appointment reminder section
    And logout from practice provisioning portal

  Scenario: verify if user is able to see timing units under timing unit section of appointment reminder
    When from setting in notifications user click on notification tab
    Then verify if user is able to see timing units under timing unit section of appointment reminder
    And logout from practice provisioning portal

  Scenario: verify by default user is able to see published status under status section of appointment reminder
    When from setting in notifications user click on notification tab
    Then verify by default user is able to see published status under status section for mail and text fields under timing section of appointment reminder section
    And logout from practice provisioning portal

  Scenario: verify system should display preview button for appointment confirmation for email when user click on hamburger button
    When from setting in notifications user click on email hamburger button section
    Then verify if user is able to see preview button
    And logout from practice provisioning portal

  Scenario: verify system should display preview button for appointment reminder for text when user click on hamburger button
    When from setting in notifications user click on text hamburgerButton section
    Then verify if user is able to see preview button
    And logout from practice provisioning portal

  Scenario: verify if user hit hamburger button then edit and preview button display for appointment reminder
    When from setting in notifications user click on email hamburgerButton section of appointment reminder
    Then verify if user is able to see edit and preview button
    And logout from practice provisioning portal

  Scenario: verify if user hit hamburger button then edit and preview button display
    When from setting in notifications user click on text hamburgerButton section of appointment reminder
    Then verify if user is able to see edit and preview button
    And logout from practice provisioning portal

  Scenario: verify if user is able to hit back button from template design page for appointment reminder and system should redirect back user on notification tab
    When from setting in notifications user click on email hamburgerButton section of appointment reminder
    And user hit edit button of email for appointment reminder
    Then verify if user is able to hit back button and system should redirect back user on notification tab
    And logout from practice provisioning portal

  Scenario: verify if user is able to hit back button from template design page
    When from setting in notifications user click on text hamburgerButton section of appointment reminder
    And user hit edit button of email for appointment reminder
    Then verify if user is able to hit back button and system should redirect back user on notification tab
    And logout from practice provisioning portal

  Scenario: verify if user is able to hit preview button for appointment reminder for email
    When from setting in notifications user click on email hamburgerButton section of appointment reminder
    Then verify if user is able to hit preview button and system should redirect on preview template page
    And logout from practice provisioning portal

  Scenario: verify if user is able to hit preview button for appointment reminder for text
    When from setting in notifications user click on text hamburgerButton section of appointment reminder
    Then verify if user is able to hit preview button and system should redirect on preview template page
    And logout from practice provisioning portal

  Scenario: verify if user is able to hit close button from appointment reminder from email template design page and system should redirect back user on notification tab
    When from setting in notifications user click on email hamburgerButton section of appointment reminder
    And user hit preview button
    Then verify if user is able to hit close button and system should redirect back user on notification tab
    And logout from practice provisioning portal

  Scenario: verify if user is able to hit close button from text template design page and system should redirect back user on notification tab
    When from setting in notifications user click on text hamburgerButton section of appointment reminder
    And user hit preview button
    Then verify if user is able to hit close button and system should redirect back user on notification tab
    And logout from practice provisioning portal

  Scenario: verify if the user is able to see the practice display name remains as initial value after removing the name
    When from settings in general under manage solutions tab remove practice display name
    And click on update settings and it showing 'This is a required field. Please enter a valid display name' error
    And navigate to forms tab and again navigate to manage solutions tab
    Then verify if the user is able to see the practice display name remains as initial value
    And logout from practice provisioning portal

  Scenario: verify if the user is able to see the practice display name remains as initial value after removing the name
    When from settings in general under manage solutions tab remove practice display name
    And press space bar on textbox
    And click on update settings and it showing 'This is a required field. Please enter a valid display name' error
    And navigate to forms tab and again navigate to manage solutions tab
    Then verify if the user is able to see the practice display name remains as initial value
    And logout from practice provisioning portal

  Scenario: verify if user is able to see validation message when practice display name will be blank
    When from settings in general under manage solutions tab remove practice display name
    And click on update settings
    Then verify if user is able to see validation message 'This is a required field. Please enter a valid display name'
    And logout from practice provisioning portal

  Scenario: verify if user is able to see changes are reflected by clicking save button when send notification Radio is off
    When from setting in notifications user turn off send notification radio button
    And click on save button
    Then verify if user is able to see changes are reflected in notification tab as send notification is off
    And logout from practice provisioning portal

  Scenario: verify if user is able to see changes are reflected by clicking save button when send notification Radio is on
    When from setting in notifications user turn on send notification radio button
    And click on save button
    Then verify if user is able to see changes are reflected in notification tab as send notification is on
    And logout from practice provisioning portal

  Scenario: verify if user is able to see changes are reflected by clicking save button when user keep blank additional arrival instructions text box
    When from setting in notifications user click on curbside checkin tab and click on english button
    And user keep blank additional arrival instructions text box of english language
    And click on save button
    Then verify if user is able to see changes are reflected in curbside checkin tab of english language
    And user rewrite additional arrival instructions text box of english language
    And click on save button
    And logout from practice provisioning portal

  Scenario: verify if user is able to see changes are reflected by clicking save button when user keep blank additional arrival instructions text box
    When from setting in notifications user click on curbside checkin tab and click on spanish button
    And user keep blank additional arrival instructions text box of spanish language
    And click on save button
    Then verify if user is able to see changes are reflected in curbside checkin tab of spanish language
    And user rewrite additional arrival instructions text box of spanish language
    And click on save button
    And logout from practice provisioning portal

  Scenario: verify if user is able to see changes are reflected by clicking save button when user click on select  english and spanish language
    When from setting in notifications user click on practice language preference drop down and select english and spanish language
    And click on save button
    Then verify if user is able to see changes are reflected in notification tab as english and spanish language
    And logout from practice provisioning portal

  Scenario: verify if user is able to see changes are reflected by clicking save button when user click on select english language
    When from setting in notifications user click on practice language preference drop down and select english
    And click on save button
    Then verify if user is able to see changes are reflected in notification tab english language
    And logout from practice provisioning portal

  Scenario: verify Notification tab is showing all the feilds,button,textbox ,radio button , infobutton as per the requirement
    When user is on notification tab in setting
    Then verify that notification tab is showing all the fields,button,textbox,radio button,infobutton as per requirement
    And logout from practice provisioning portal

  Scenario: verify the grid section and column names in arrival dashboard screen
    When logged into precheck admin and user is able to view appointment dashboard screen
    And click on Curbside check-in tab
    Then verify the column header of grid section in arrival dashboard screen.
    And logout from practice provisioning portal

  Scenario: verify that in precheck admin settings  "Curbside check-in notifications will be sent 1 hour prior to the appointment." is displayed
    When logged into precheck admin and user is able to view appointment dashboard screen
    And click on Notifications tab in Setting tab
    Then verify the Curbside check-in reminder option
    And logout from practice provisioning portal

  Scenario: verify if the notification number is displayed near the account icon
    When schedule an appointment for four patient and have confirmed their arrival
    And click on the notification icon
    Then verify the notification icon count on the top
    And logout from practice provisioning portal

  Scenario: verify that for every patient details there are common fields & buttons are avialable
    When logged into precheck admin and user is able to view appointment dashboard screen
    And schedule an appointment and confirmed their arrival
    And click on Curbside check-in tab
    And select patient and click on dropdown
    Then verify common fields and buttons are available for every patient details
    And logout from practice provisioning portal

  Scenario: Verify once a message is sent to a patient it is added to the history link of messages with date and time stamp
    When schedule an appointment and confirmed their arrival
    And click on Curbside check-in tab
    And sent message from dropdown
    And click on the history link
    Then verify on curbside notification logs popup patient name,date,time,message and medium to be displayed
    And logout from practice provisioning portal

  Scenario: Verify that (current day -1) filtaration is not allowed in end time in curb side check-in tab if the current date is set as start time
    When logged into precheck admin and user is able to view appointment dashboard screen
    And click on Curbside check-in tab
    Then select end time of previous day in end time filter and verify previous day date is disable in curbside check in
    And logout from practice provisioning portal

  Scenario: verify that Pre-populated dropdown list of messages in Send message drop down field on curbside checkin page
    When schedule an appointment and confirmed their arrival
    And logged into precheck admin and user is able to view appointment dashboard screen
    And click on Curbside check-in tab
    And select patient and click on dropdown
    Then verify messages list should be displayed in send message dropdown
    And logout from practice provisioning portal

  Scenario: Verify multiple records are selected from different pages then it will not show the ribbon on top of the page
    When schedule multiple appointments and confirm
    And from setting dashboard in notifications disable Broadcast messaging checkbox
    And switch on appointment dashboard and Select all appointment
    Then verify after selecting all appointment ribbon message should be display as per expected
    And from setting dashboard in notifications Enable Broadcast messaging checkbox
    And logout from practice provisioning portal

  Scenario: Verify timing and timing units of email only for 'Days' of appointment reminder
    When from setting in notifications user click on email edit section of appointment reminders
    And enter timing and timing unit only for Days for 'Email' and click on save button
    Then verify system should allow only days
    And logout from practice provisioning portal

  Scenario: Verify timing and timing units of email only for 'Hours' of appointment reminder
    When from setting in notifications user click on email edit section of appointment reminders
    And enter timing and timing unit only for Hours for 'Email' and click on save button
    Then verify system should allow only hours
    And logout from practice provisioning portal

  Scenario: Verify timing and timing units of email only for 'Minutes' of appointment reminder
    When from setting in notifications user click on email edit section of appointment reminders
    And enter timing and timing unit only for Minutes for 'Email' and click on save button
    Then verify system should allow only Minutes
    And logout from practice provisioning portal

  Scenario: Verify combinations for timing and timing units of email for 'hours,minutes,day,day' of appointment reminder
    When from setting in notifications user click on email edit section of appointment reminders
    And enter timing and timing unit for hours,minutes,day,day for 'Email' and click on save button
    Then verify system should allow hours,minutes,day,day timing
    And click on edit for email and remove one cadence and save
    And logout from practice provisioning portal

  Scenario: Verify combinations for timing and timing units of email for 'day,hours,hours,minutes' of appointment reminder
    When from setting in notifications user click on email edit section of appointment reminders
    And enter timing and timing unit for day,hours,hours,minutes for 'Email' and click on save button
    Then verify system should allow day,hours,minutes,minutes timing
    And click on edit for email and remove one cadence and save
    And logout from practice provisioning portal

  Scenario: Verify combinations for timing and timing units of email for 'minutes,minutes,hours,day' of appointment reminder
    When from setting in notifications user click on email edit section of appointment reminders
    And enter timing and timing unit for minutes,minutes,hours,day for 'Email' and click on save button
    Then verify system should allow minutes,minutes,hours,day timing
    And click on edit for email and remove one cadence and save
    And logout from practice provisioning portal

  Scenario: Verify if user able to see validation message when user enter something wrong in fields
    When from setting in notifications user click on email edit section of appointment reminders
    Then verify user not able to enter zero in timing unit section for email in appointment reminders
    And logout from practice provisioning portal

  Scenario: Verify timing and timing units of text only for 'Days' of appointment reminder
    When from setting in notifications user click on text edit section of appointment reminders
    And enter timing and timing unit only for Days for 'SMS' and click on save button
    Then verify system should allow only days for SMS
    And logout from practice provisioning portal

  Scenario: Verify timing and timing units of text only for 'Hours' of appointment reminder
    When from setting in notifications user click on text edit section of appointment reminders
    And enter timing and timing unit only for Hours for 'SMS' and click on save button
    Then verify system should allow only hours SMS
    And logout from practice provisioning portal

  Scenario: Verify timing and timing units of text only for 'Minutes' of appointment reminder
    When from setting in notifications user click on text edit section of appointment reminders
    And enter timing and timing unit only for Minutes for 'SMS' and click on save button
    Then verify system should allow only Minutes SMS
    And logout from practice provisioning portal

  Scenario: Verify combinations for timing and timing units of text for 'hours,minutes,day,day' of appointment reminder
    When from setting in notifications user click on text edit section of appointment reminders
    And enter timing and timing unit for hours,minutes,day,day for 'SMS' and click on save button
    Then verify system should allow hours,minutes,day,day timing for SMS
    And click on edit for text and remove one cadence and save
    And logout from practice provisioning portal

  Scenario: Verify combinations for timing and timing units of text for 'day,hours,hours,minutes' of appointment reminder
    When from setting in notifications user click on text edit section of appointment reminders
    And enter timing and timing unit for day,hours,hours,minutes for 'SMS' and click on save button
    Then verify system should allow day,hours,minutes,minutes timing for SMS
    And click on edit for text and remove one cadence and save
    And logout from practice provisioning portal

  Scenario: Verify combinations for timing and timing units of text for 'minutes,minutes,hours,day' of appointment reminder
    When from setting in notifications user click on text edit section of appointment reminders
    And enter timing and timing unit for minutes,minutes,hours,day for 'SMS' and click on save button
    Then verify system should allow minutes,minutes,hours,day timing for SMS
    And click on edit for text and remove one cadence and save
    And logout from practice provisioning portal

  Scenario: Verify if user able to see validation message when user enter something wrong in fields
    When from setting in notifications user click on text edit section of appointment reminders
    Then verify user not able to enter zero in timing unit section for email in appointment reminders
    And logout from practice provisioning portal

  Scenario: verify the functionality of checkin for multiple patients at a time
    When user on curbside checkin tab and clear all appointments
    And schedule multiple appointments and confirm their appointment
    Then verify checkin button fuctionality after one patient gets checkin
    And logout from practice provisioning portal

  Scenario: verify the functionality of checkin for multiple patients at a time
    When user on curbside checkin tab and clear all appointments
    And schedule multiple appointments and confirm their appointment
    Then verify checkin button fuctionality after two patient gets checkin
    And logout from practice provisioning portal

  Scenario: verify the functionality of checkin for multiple patients at a time
    When user on curbside checkin tab and clear all appointments
    Then schedule multiple appointments and confirm their appointment
    Then verify checkin button fuctionality after all patient gets checkin
    And logout from practice provisioning portal

  Scenario: verify the functionality of all checkbox on curbside checkin page
    When user on curbside checkin tab and clear all appointments
    And schedule multiple appointments and confirm their appointment
    Then verify select and deselect functionality of all checkbox
    And logout from practice provisioning portal

  Scenario: verify the functionality of individual checkbox on curbside checkin page
    When user on curbside checkin tab and clear all appointments
    And schedule multiple appointments and confirm their appointment
    Then verify select functionality of individual checkbox
    And logout from practice provisioning portal

  Scenario: verify when appointment is schedule only with mail and broadcast is send then banner status should come as failure when email is unsubscribed
    When schedule a appointment without phone number
    And go to on yopmail and from mail unsubscribe a patient
    And I switch on practice provisioning url
    And I select patient and send broadcast message from appointment dashboard
    Then verify banner status should come as failure
    And logout from practice provisioning portal

  Scenario: Verify if patient confirmed appointment then message from curbside checkin send succesfully
    When schedule an appointment and confirmed their arrival
    And logged into precheck admin and user is able to view appointment dashboard screen
    And click on Curbside check-in tab
    And select patient and click on dropdown
    And I send message to selected patient
    Then verify last message send succesfully from curbside checkin
    And logout from practice provisioning portal

  Scenario: Verify if patient checkin his appointment then patient entry should be on appointment dashboard
    When schedule an appointment and confirmed their arrival
    And logged into precheck admin and user is able to view appointment dashboard screen
    And click on Curbside check-in tab
    And I select patient and click on check in
    And I switch to the appointment dashboard tab
    Then verify check in patient should be added in the appointments dashboard
    And logout from practice provisioning portal

  Scenario: verify broadcast message UI template should be visible
    When I schedule 5 appointments and select patients
    And I select broadcast message button from action dropdown
    Then verify broadcast message UI template visibility and when broadcast message entered in english and spanish footer note character count get decremented
    And logout from practice provisioning portal

  Scenario: verify after closing banner all selected appointment are deselected
    When I schedule 5 appointments
    And I select all patients
    And verify after closing banner message all selected appointments are deselected
    Then logout from practice provisioning portal

  Scenario: verify if appointment is rescheduled then in that case old broadcast message sent should not be shown
    When I schedule a new appointment
    And from setting dashboard in notifications Enable Broadcast messaging checkbox
    And I switch to the appointment dashboard tab
    And I select patient from appointment dashboard and send broadcast message
    And I click on selected patient broadcast message for email and get message
    And I reschedule an appointment
    Then verify old broadcast message sent should not be shown
    Then logout from practice provisioning portal

  Scenario: Verify if curbside check in reminder checkbox is turned off then curbside reminder is not recieved to patient
    When I click on Notifications tab from Setting tab and disable curbside remainder checkbox
    And I schedule a new appointment and confirm arrival
    Then verify curbside reminder is not receive to patient
    And from notifications tab in Setting tab and enable curbside remainder checkbox
    And logout from practice provisioning portal

  Scenario: Verify if curbside check in reminder checkbox is turned off then old curbside reminder mail recieved should be allowed to check in
    When I enabled curbside remainder checkbox from notifications tab in Setting tab
    Then I schedule a new appointment after one hour of current time
    Then I disable curbside remainder checkbox after five minutes of current time
    Then verify curbside reminder is receive to patient
    And from notifications tab in Setting tab and enable curbside remainder checkbox
    And logout from practice provisioning portal

  Scenario: verify practice staff is able to send message by selecting the other option from curnside checkin in drop down list
    When I schedule an appointment and have confirmed there arrival
    And I click on Curbside check-in tab and select patient
    And I click on dropdown and select "Other" option
    Then verify other message is able to send from curbside checkin in drop down list
    And logout from practice provisioning portal

  Scenario: verify practice staff is able to send message more than one message by selecting other option from curnside checkin in dropdown list
    When I schedule an appointment and have confirmed there arrival
    And I click on Curbside check-in tab and select patient
    And I send other message from curbside checkin in drop down list
    Then verify practice staff is able to send another message by using other option from the drop down list
    And logout from practice provisioning portal

  Scenario: verify staff is able to see default time and date on appointments when user first enter on dashboard
    When I switch on appointment dashboard
    Then verify System should show default date and time on appointment dashboard
    And logout from practice provisioning portal

  Scenario: verify system should not allowed user to select wrong start time and end time
    When I select end date as current date at two AM and select start date as current date after two AM
    Then verify system should not allow user to select start time after two AM for same day and after two AM slots should be disable
    And logout from practice provisioning portal

  Scenario: verify system should not allowed user to select wrong start time and end time
    When I select start date as current date at three AM and select end date as current date before three AM
    Then verify system should not allow user to select end time before three AM for same day and before three AM slots should be disable
    And logout from practice provisioning portal

  Scenario: verify if manual reminder is sent and later page is refresh user is able see navigation button properly
    When I select start date and time and navigate on fifth page
    And I select a appointment and send manual reminder
    And I click on refresh button from apt dashboard and lands on same page
    Then I verify that I am still on page five and arrows are working

  Scenario: verify notification count after filteration for location
    When from setting in notifications curbside check-in reminder checkbox is check
    And I click on save button in notifications tab
    And I schedule an appointment
    And from curbside check-in filtration is done for location
    Then I verify notification count get updated after arrival entry in appointment dashboard without refresh
    And logout from practice provisioning portal

  Scenario: verify notification count after filteration for location L1 and arrival entry for location L2
    And I schedule an appointment for location L2
    When from setting in notifications curbside check-in reminder checkbox is check
    And I click on save button in notifications tab
    And I schedule an appointment
    And from curbside check-in tab filtration is done for location L1 when there is already arrival entry for location L2
    Then I verify notification count should not get updated after arrival entry in curbside dashboard for location L2 without refresh
    And logout from practice provisioning portal

  Scenario: verify notification count after filteration for location L1 and arrival entry for location L2
    And I schedule an appointment for location L2
    When from setting in notifications curbside check-in reminder checkbox is check
    And I click on save button in notifications tab
    And I schedule an appointment
    And from curbside check-in tab filtration is done for location L1 when there is already arrival entry for location L2
    Then I verify notification count should not get updated after arrival entry in appointment dashboard for location L2 without refresh
    And logout from practice provisioning portal

  Scenario: Verify when select all records from first page then Ribbon message should be display
    When I enable Broadcast messaging checkbox from setting in notifications dashboard
    And I switch on appointment dashboard
    And I select past start date and select all appointment
    Then I verify ribbon message will be display as per expected
    And logout from practice provisioning portal

  Scenario: Verify when select multiple records from different pages then ribbon should not display
    When I enable Broadcast messaging checkbox from setting in notifications dashboard
    And I switch on appointment dashboard
    And I select past start date
    And I select 10 patients records from first page
    And I select 15 patient records from second page
    And I select 10 patient records from third page
    And I select 5 patient records from fourth page
    Then I verify on appointments dashboard multiple records are selected from different pages then it will not show the ribbon on top of the page
    And logout from practice provisioning portal

  Scenario: Verify when patient confirms his arrival then a new row is added in the grid
    When I switch on curbside checkin tab
    And I schedule 3 appointment and confirmed their arrival
    And 3 rows should be display on curbside checkin page and notification icon updated
    And I schedule 2 appointment and confirmed their arrival
    And 5 rows should be display on curbside checkin page and notification icon updated
    And I switches to Appointmant dashboard
    And I schedule 1 appointment and confirmed their arrival
    And one notification update should be displayed in the notification icon on the top
    Then I verify when switches to curbside checkin tab 6 row must be displayed without clicking on the notification icon on the top
    And logout from practice provisioning portal

  Scenario: verify that Pre-populated dropdown list of messages in Send message drop down field
    When I schedule an appointment and have confirmed there arrival
    And I click on Curbside check-in tab
    And I select patient and click on dropdown
    Then I verify messages list should be displayed in send message dropdown
    And logout from practice provisioning portal

  Scenario: Verify the notification count and arrival count on the grid when change the location filter from L1 to L2
    When user on curbside checkin tab and clear all appointments
    And I booked an appointment for patient "Micheal" and confirmed his arrival for Location "River Oaks Main"
    And I booked an appointment for patient "Ricky" and confirmed his arrival for Location "USA"
    And I booked an appointment for patient "Nicholus" and confirmed his arrival for Location "River Oaks Main"
    Then I verify the notification count and arrival count on the grid when change the location filter from L1 "River Oaks Main" to L2 "USA"
    And logout from practice provisioning portal

  Scenario: Verify the notification count and arrival count on the grid when change the provider filter from PR1 to PR2
    When user on curbside checkin tab and clear all appointments
    And I booked an appointment for patient "Micheal" and confirmed his arrival for provider "Brown, Jennifer"
    And I booked an appointment for patient "Ricky" and confirmed his arrival for provider "Brown, Jennifer"
    And I booked an appointment for patient "Nicholus" and confirmed his arrival for provider "Donald, Anderson"
    Then I verify the notification count and arrival count on the grid when change the provider filter from PR1 "Brown, Jennifer" to PR2 "Donald, Anderson"
    And logout from practice provisioning portal

  Scenario: verify if send notifiaction is off then no cadence reminder is sent
    When I turn off send notification radio button from setting in notifications
    And I schedule a new appointment
    Then verify on mail no cadence reminder is sent when send notifiaction is off

  Scenario: verify if user is able to see by default three timing and timing units on template editor page for email
    When from setting in notifications user click on email hamburgerButton section of appointment reminder
    And I hit edit button of email for appointment reminder
    Then I verify is able to see by default three timing with default days configured and timing units with configured one,three,five on template editor page
    And logout from practice provisioning portal

  Scenario: Verify user select Days from timing section then in timing units user is able to enter unlimited numbers so there is no limit for email
    When from setting in notifications user click on email hamburgerButton section of appointment reminder
    And I hit edit button of email for appointment reminder
    And I select timing days and enter timing unit for "Email"
    Then I verify user is able to enter unlimited numbers so there is no limit
    And logout from practice provisioning portal

  Scenario: verify if system is allowing user to enter integers from 1 to 23 in timing unit section for email in appointment reminders
    When from setting in notifications user click on email hamburgerButton section of appointment reminder
    And I hit edit button of email for appointment reminder
    Then I verify system is allowing to enter integers from one to twenty three in timing unit section for email in appointment reminders for 'Email'
    And logout from practice provisioning portal

  Scenario: verify if system is allowing user to enter integers from 10 to 59 in timing unit section for email in appointment reminders
    When from setting in notifications user click on email hamburgerButton section of appointment reminder
    And I hit edit button of email for appointment reminder
    Then I verify user able to enter integer from ten to fifty nine in timing unit section for minutes in appointment reminders for 'Email'
    And logout from practice provisioning portal

  Scenario: verify notification count  where filter is applied for location L2
    When I schedule an appointment for location L1
    When from setting in notifications curbside check-in reminder checkbox is check
    And I click on save button in notifications tab
    And I schedule an appointment for location L2
    And from curbside check-in filtration is done for location L2
    Then I verify notification count should not get updated after arrival entry in curbside dashboard for location L1
    And logout from practice provisioning portal

  Scenario: verify notification count where filter is removed for location L2
    When I schedule an appointment for location L1
    When from setting in notifications curbside check-in reminder checkbox is check
    And I click on save button in notifications tab
    And I schedule an appointment for location L2
    And from curbside check-in filtration is done for location L2
    And from curbside check-in remove filter for location L2
    Then I verify notification count should get updated for all the patients in curbside dashboard
    And logout from practice provisioning portal

  Scenario: verify notification count where filter is applied for location L1
    When I schedule an appointment for location L2
    When from setting in notifications curbside check-in reminder checkbox is check
    And I click on save button in notifications tab
    And I schedule an appointment for location L1
    And from curbside check-in filtration is done for location L1
    Then I verify notification count should not get updated after arrival entry in curbside dashboard for location L2
    And logout from practice provisioning portal

  Scenario: verify notification count where filter is removed for location L1
    When I schedule an appointment for location L2
    When from setting in notifications curbside check-in reminder checkbox is check
    And I click on save button in notifications tab
    And I schedule an appointment for location L1
    And from curbside check-in filtration is done for location L1
    And from curbside check-in remove filter for location L1
    Then I verify notification count should get updated for all the patients in arrival grid
    And logout from practice provisioning portal

  Scenario: verify notification count where filter is applied for location L2
    And I schedule an appointment for location L1
    When from setting in notifications curbside check-in reminder checkbox is check
    And I click on save button in notifications tab
    And I schedule an appointment for location L2
    And from curbside check-in filtration is done for location L2
    Then I verify notification count should not get updated after arrival entry in appointment dashboard for location L1
    And logout from practice provisioning portal

  Scenario: verify notification count and remove filter for location L2
    And I schedule an appointment for location L1
    When from setting in notifications curbside check-in reminder checkbox is check
    And I click on save button in notifications tab
    And I schedule an appointment for location L2
    And from curbside check-in filtration is done for location L2
    And from curbside check-in remove filter for location L2
    Then I verify notification count should get updated for all the patients after arrival entry in appointment dashboard for location L1
    And logout from practice provisioning portal

  Scenario: verify notification count where filter is applied for location L1
    When I schedule an appointment for location L2
    When from setting in notifications curbside check-in reminder checkbox is check
    And I click on save button in notifications tab
    And I schedule an appointment for location L1
    And from curbside check-in filtration is done for location L1
    Then I verify notification count should not get updated after arrival entry in appointment dashboard for location L2
    And logout from practice provisioning portal

  Scenario: verify notification count after filteration for location L1 and arrival entry for location L2 and after remove filter for location L1
    When I schedule an appointment for location L2
    When from setting in notifications curbside check-in reminder checkbox is check
    And I click on save button in notifications tab
    And I schedule an appointment for location L1
    And from curbside check-in filtration is done for location L1
    And from curbside check-in remove filter for location L1
    Then I verify notification count should get updated for all the patients after arrival entry in appointment dashboard for location L2
    And logout from practice provisioning portal

  Scenario: verify notification count in appointment dashboard
    And I schedule an appointment for three patients
    And I go to curbside check-in where three patients are already there in arrival grid
    And I go to appointments dashboard
    Then I verify notification count on top in appointment dashboard should be three
    And logout from practice provisioning portal

  Scenario: verify notification count after a new patient confirms the arrival in the parking lot
    And I schedule an appointment for three patients
    And I go to curbside check-in where three patients are already there in arrival tab
    And I go to appointments dashboard
    And I schedule an appointment
    And I go to curbside check-in tab after new patient arrival
    Then I verify notification count on top in appointment dashboard should get updated to four without refresh
    And logout from practice provisioning portal

  Scenario: verify that patient comes in the practice and staff marks single or multiple patients as checkedin in curbside checkin grid
    And I schedule an appointment
    And I go to curbside check-in where there are patients click one or multiple patients as checkedin
    Then I verify once the patient marked as checkedin that particular entries should not be there in curbside checkin grid
    And logout from practice provisioning portal

  Scenario: verify if the staff clicks on the check in when the timer is still on
    And I schedule an appointment
    And I go to curbside check-in where there are patients click one or multiple patients whose timer is still on and check-in
    Then I verify once the patient marked as checkedin that particular entries should get removed from curbside checkin grid
    And logout from practice provisioning portal

  Scenario: verify the patient status on appointments dashboard on clicking on check in button in curbside checkin
    And I schedule an appointment
    And I go to curbside check-in where there are patients click one or multiple patients as checkedin
    And I go to appointment dashboard
    Then I verify once the patient marked as checkedin and in appointment dashboard there should be a checkedin symbol for that patient
    And logout from practice provisioning portal

  Scenario: verify selecting individual or multiple patients checkbox in the arrival grid
    And I schedule an appointment
    And I go to curbside check-in tab select individual or multiple patients checkbox
    Then I verify all the patients should be selected on the curbside checkin tab
    And logout from practice provisioning portal

  Scenario: verify selecting three patients checkbox in the arrival grid
    And I schedule an appointment for three patients
    And I go to curbside check-in tab select three patients checkbox
    Then I verify three patients checkbox should be selected on the curbside checkin tab
    And logout from practice provisioning portal

  Scenario: verify in the appointment grid for patient P1
    And I schedule an appointment
    And I go to appointment tab and see the arrival displayed for the patient in the arrival grid
    Then I verify scheduling details are displayed for patient P1 but checked-in icon is not displayed until the staff checks him in
    And logout from practice provisioning portal

  Scenario: verify the curbside check-in grid staff member now checks the patient P1 by clicking on the check in button
    And I schedule an appointment
    And I go to curbside check-in tab and clicks on check-in button for the patient P1
    Then I verify patient P1 entry is removed from the curbside check-in grid
    And logout from practice provisioning portal

  Scenario: verify the patient P1 detail on the appointment dashboard
    And I schedule an appointment
    And I go to curbside check-in tab and clicks on check-in button for the patient P1
    And I go to appointment dashboard
    Then I verify checked in icon is added in a new column for patient P1 along with the other data in appointment dashboard
    And logout from practice provisioning portal

  Scenario: verify after sending broadcast message to curbside checkin patient then reminder column section system does not show any entry for broadcast
    When I schedule an appointment and perform checkin action
    And I click on setting tab and ON notification setting
    And I switch on appointment dashboard and send broadcast message for curbside check in patient
    Then I verify system should not show day prior entry in reminder section
    And logout from practice provisioning portal

  Scenario: Verify if user is able to edit mail cadence template from cadence editor page
    When from setting in notifications user click on email edit section of appointment reminders
    Then I verify user is able to edit "Email" cadence template from cadence editor page
    And logout from practice provisioning portal

  Scenario: Verify if user is able to edit text cadence template from cadence editor page
    When from setting in notifications user click on text edit section of appointment reminders
    Then I verify user is able to edit "SMS" cadence template from cadence editor page
    And logout from practice provisioning portal

  Scenario: Verify if additional arrival message text box is present and max size limit for additional arrival message for custom fields for English and Spanish Language
    When from setting in notifications user click on curbside checkin tab
    Then I verify if additional arrival message text box is present and max size limit for additional arrival message for custom fields for English and Spanish
    And logout from practice provisioning portal

  Scenario: Verify default arrival message for English and Spanish in text box
    When from setting in notifications user click on curbside checkin tab
    Then I verify user is able see default arrival confirmation message in english and Spanish in text box
    And logout from practice provisioning portal

  Scenario: Verify after clicking on refresh button user is able to redirect on same page and navigate to next page and previous page
    When I select start date and time
    And I select all patients
    And I am able to navigate from first page to second page and third page
    Then I verify after clicking on refresh button user on third page and able to navigate on first page
    And logout from practice provisioning portal
