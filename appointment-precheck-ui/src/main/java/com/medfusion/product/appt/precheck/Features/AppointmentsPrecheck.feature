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

  Scenario: verify notification count after multiple filter applied for location L1 and provider A1
    And I schedule an appointment for location L1 and provider A1
    When from setting in notifications curbside check-in reminder checkbox is check
    And I click on save button in notifications tab
    And in curbside check-in filtration is done for location L1 and provider A1
    And I schedule 2 appointments for location L1 and provider A2
    Then I verify notification count will get updated but entry will not come in arrival grid for location L1 and provider A2
    And logout from practice provisioning portal

  Scenario: verify notification count after filter applied for location L1 and provider A1
    And I schedule an appointment for location L1 and provider A1
    When from setting in notifications curbside check-in reminder checkbox is check
    And I click on save button in notifications tab
    And in curbside check-in filtration is done for location L1 and provider A1
    And I schedule 2 appointments for location L1 and provider A1
    Then I verify notification count will get updated and entry will be seen in arrival grid for location L1 and provider A1
    And logout from practice provisioning portal

  Scenario: verify notification count after multiple filter applied for location L1 and provider A1
    And I schedule an appointment for location L1 and provider A1
    When from setting in notifications curbside check-in reminder checkbox is check
    And I click on save button in notifications tab
    And from curbside check-in filtration is done for location L1 and provider A1
    And I schedule 2 appointments for location L2 and provider A1
    Then I verify notification count should not get updated and no entries will be seen in arrival grid for location L2 and provider A1
    And logout from practice provisioning portal

  Scenario: verify notification count after the filter is applied for location L1 and provider A1
    And I schedule an appointment for location L1 and provider A1
    When from setting in notifications curbside check-in reminder checkbox is check
    And I click on save button in notifications tab
    And from curbside check-in filtration is done for location L1 and provider A1
    And I schedule 2 appointments for location L2 and provider A2
    Then I verify notification count should not get updated and no entries will be seen in arrival grid for location L2 and provider A2
    And logout from practice provisioning portal

  Scenario: verify notification count after the filter is applied for location L1 and patient P1
    And I schedule an appointment for location L1 and patient P1
    When from setting in notifications curbside check-in reminder checkbox is check
    And I click on save button in notifications tab
    And from curbside check-in filtration is done for location L1 and patient P1
    And I schedule an appointment for location L1 and patient P1
    Then I verify notification count should get updated and entries will be seen in arrival grid for for location L1 and patient P1
    And logout from practice provisioning portal

  Scenario: verify notification count after filteration for location L1 if new arrival entry comes for Location L1 after user confirmation for arrival
    When from setting in notifications curbside check-in reminder checkbox is check
    And I click on save button in notifications tab
    And I schedule an appointment for location L1
    And in curbside check-in filtration is done for location L1
    Then I verify notification count get updated after arrival entry in curbside dashboard without refresh
    And logout from practice provisioning portal

  Scenario: verify selecting and deselecting the top checkbox in the arrival grid
    And I schedule 10 appointments who have confirmed their arrival
    And I go to curbside check-in tab select the top checkbox
    And I later deselect top checkbox in the curbside check-in tab
    Then I verify all the patients should be selected and deselected on the curbside tab
    And logout from practice provisioning portal

  Scenario: verify notification count after multiple filter applied with provider A1
    And I schedule an appointment for location L1 and provider A1
    When from setting in notifications curbside check-in reminder checkbox is check
    And I click on save button in notifications tab
    And in curbside check-in filtration is done for location L1 and provider A1
    And I schedule an appointment for location L1 and provider A2
    Then I verify notification count will get updated but entry will not come in arrival grid for provider A2
    And logout from practice provisioning portal

  Scenario: verify notification count after multiple filter is applied with provider A1
    And I schedule an appointment for location L1 and provider A1
    When from setting in notifications curbside check-in reminder checkbox is check
    And I click on save button in notifications tab
    And in curbside check-in filtration is done for location L1 and provider A1
    And I schedule an appointment for location L1 and provider A1
    Then I verify notification count will get updated and entry will also be seen in arrival grid for provider A1
    And logout from practice provisioning portal

  Scenario: verify on template editor page user is able to see proper template of email
    When from setting in notifications I click on email hamburgerbutton section of appointment reminder
    And I hit edit button
    Then I verify on template editor page all fields are displayed properly of appointment reminder for email
    And logout from practice provisioning portal

  Scenario: verify on template editor page user is able to see proper template of text
    When from setting in notifications I click on text hamburgerbutton section of appointment reminder
    And I hit edit button
    Then I verify on template editor page all fields are displayed properly of appointment reminder for text
    And logout from practice provisioning portal

  Scenario: verify if user is able to edit text or mail cadence template from cadence editor page
    When I click on setting tab
    And I click on notification tab
    And I click on edit of hamburger button for email in appointment reminders
    Then I verify that design tab is visible under setting tab
    And logout from practice provisioning portal

  Scenario: verify if user is able to see all default value on cadence page
    When I click on setting tab
    And I click on notification tab
    And I click on edit of hamburger button for email in appointment reminders
    Then I verify that system should show all default value on cadence page properly
    And logout from practice provisioning portal

  Scenario: verify if user is able to see default timing units
    When I click on setting tab
    And I click on notification tab
    And I click on edit of hamburger button for email in appointment reminders
    Then I verify that system should show  default three timing units
    And logout from practice provisioning portal

  Scenario: verify if user is able to add timing and timing units and automatically add button disappear
    When I click on setting tab
    And I click on notification tab
    And I click on edit of hamburger button for email in appointment reminders
    And I click on add button and add timing and timing unit
    Then I verify that system should not show add button after adding fourth timing and timing unit
    And logout from practice provisioning portal

  Scenario: verify if user is able to delete timing and timing units and automatically add button appears
    When I click on setting tab
    And I click on notification tab
    And I click on edit of hamburger button for email in appointment reminders
    And I click on delete button of timing and timing unit
    Then I verify that system should show add button after deleting fourth timing and timing unit
    And logout from practice provisioning portal

  Scenario: verify if user is able to add timing in timing and timing units fields
    When I click on setting tab
    And I click on notification tab
    And I click on edit of hamburger button for email in appointment reminders
    And I add timings in timing and timing unit fields and save changes
    Then I verify that system should redirect to the notification tab page after the changes are saved
    And logout from practice provisioning portal

  Scenario: Verify if Send notifiaction is off then broadcast messaging text/mail button will not show broadcast and send manual reminder button
    When I turn off send notification radio button from setting in notifications
    Then verify on appointment dashboard broadcast message and send reminder button not displayed
    And logout from practice provisioning portal

  Scenario: Verify if practice language is english then while sending broadcast only english language option text box should be seen in broadcast and Email should be recieved in english only
    When I select practice language as "English" from notification in setting
    And I schedule an appointment in "en"
    And I switch on appointment dashboard select patient and send broadcast message in english from action button
    Then I verify on while sending broadcast only english language option text box should be seen in broadcast and Email should be recieved in english only
    And logout from practice provisioning portal

  Scenario: Verify if practice language is english & spanish then while sending broadcast english & Spanish language option text box should be seen in broadcast and Email should be recieved in english
    When I select practice language as "English & Spanish" from notification in setting
    And I schedule an appointment in "en-es"
    And I switch on appointment dashboard select patient and send broadcast message in english and spanish from action button
    Then I verify on while sending broadcast in english and spanish language option text box should be seen in broadcast and Email should be recieved in english
    And logout from practice provisioning portal

  Scenario: Verify system should send reminder, curbside mail to patient when notification is on
    And I click on settings tab
    And I click on notifications tab
    And I select send notifications as On and Save notifications
    And I click on appointments tab
    And I schedule an appointment when notification is on
    And I send reminder to the patient
    Then I verify system should send reminder,curbside mail to patient
    And logout from practice provisioning portal

  Scenario: Verify system should not send reminder, curbside mail to patient when notification is off
    And I click on settings tab
    And I click on notifications tab
    And I select send notifications as Off and Save notifications
    And I click on appointments tab
    And I schedule an appointment when notification is off
    Then I verify system should not send reminder,curbside mail to patient
    And logout from practice provisioning portal

  Scenario: Verify in appt dashboard reminder and broadcast column will not be visible when notification is off
    And I click on settings tab
    And I click on notifications tab
    And I select send notifications as Off and Save notifications
    And I click on appointments tab
    And I schedule an appointment when notification is off
    Then I verify in appt dashboard reminder and broadcast column will not be visible
    And I select the patient and click on actions dropdown
    And there will be no options for send a broadcast and send a reminder in Actions dropdown
    And logout from practice provisioning portal

  Scenario: verify system should send reminder, curbside, broadcast on email when email checkbox is On
    And I go to settings tab
    And when from settings email checkbox is enable
    And I click on appointment tab
    And I schedule an appointment when email checkbox is on
    And I send broadcast message on email
    Then I verify system should send reminder,curbside,broadcast on email
    And logout from practice provisioning portal

  Scenario: verify system should not send reminder, curbside on email when email checkbox is Off
    And I go to settings tab
    And when from settings email checkbox is disable
    And I click on appointment tab
    And I schedule an appointment when email checkbox is off
    And I send broadcast message on email
    Then I verify system should not send reminder,curbside on email
    And logout from practice provisioning portal

  Scenario: verify if patient receives appointment scheduled email as per '[Practice name]' '<no-reply@medfusion.net>' and practice name contains special characters
    And I schedule an appointment using schedule Appointment API
    Then I verify appointment should be received from '[Practice name]<no-reply@medfusion.net>' in email
    And logout from practice provisioning portal

  Scenario: verify if patient receives appointment confirmation reminder in email as per '[Practice name]' '<no-reply@medfusion.net>' and practice name contains special characters
    And I schedule an appointment using schedule Appointment API
    Then I verify confirmation reminder for appointment should be received from '[Practice name]<no-reply@medfusion.net>' in email
    And logout from practice provisioning portal

  Scenario: verify if patient receives curbside reminder in email as per '[Practice name]' '<no-reply@medfusion.net>' and practice name contains special characters
    And I schedule an appointment using schedule Appointment API
    Then I verify appointment should be received from '[Practice name]<no-reply@medfusion.net>' in email for curbside reminder
    And logout from practice provisioning portal

  Scenario: verify if patient receives broadcast in email as per '[Practice name]' '<no-reply@medfusion.net>' and practice name contains special characters
    And I schedule an appointment using schedule Appointment API
    And I send broadcast message on email
    Then I verify appointment should be received from '[Practice name]<no-reply@medfusion.net>' in email for broadcast reminder
    And logout from practice provisioning portal

  Scenario: verify if patient receives manual reminder in email as per '[Practice name]' '<no-reply@medfusion.net>' and practice name contains special characters
    And I schedule an appointment using schedule Appointment API
    And I send reminder to the patient
    Then I verify appointment should be received from '[Practice name]<no-reply@medfusion.net>' in email for manual reminder

  Scenario: verify on deleting all 50 records from page 1 or page 2, appointment of next page or previous page are populated and page number gets reduced
    And I click on page 2 from appointment dashboard
    And I select all 50 records from page 2
    And I click on remove button from actions dropdown
    Then I verify all 50 records should get deleted after deleting the records on  clicking refresh button previous page 1 records should come on page 1 records
    And logout from practice provisioning portal

  Scenario: verify records are deleted as per the filter applied on location, provider
    And I apply filter for location and provider on appointment page
    And I select all records from page 1
    And I click on remove button from actions dropdown
    Then I verify all records should get deleted after deleting the records on  clicking refresh button previous page 1 records should come on page 1 records
    And logout from practice provisioning portal

  Scenario: verify on sending broadcast latest banner is updated for broadcast
    And I select appointment from appointment dashboard
    And I click on Actions button
    And I click on Send Reminder option and dont close the banner
    And I click on broadcast message options
    Then I verify now on sending broadcast broadcast banner should get updated at the top right corner
    And logout from practice provisioning portal

  Scenario: verify on removing appointment banner should get updated
    And I select appointment from appointment dashboard
    And I click on Actions button
    And I click on Send Reminder option and dont close the banner
    And I click on remove button options
    Then I verify now on removing appointment the appointment remove count should get updated at the top right corner
    And logout from practice provisioning portal

  Scenario: verify on sending broadcast and later removing the appointment banner gets updated
    And I select appointment from appointment dashboard
    And I click on Actions button
    And I click on broadcast message options
    And I click on remove button options
    Then I verify now on removing appointment the appointment remove count should get updated at the right corner
    And logout from practice provisioning portal

  Scenario: verify on doing check-in for current date -1 appointment and notification count are coming correctly after check in curbside arrival grid
    When I schedule two appointments
    And I apply filter for start date and end date in curbside arrival grid
    And I switch to appointments tab and again switch to curbside arrival grid and I checkin two patients
    Then I verify that after switching from appointments tab to curbside arrival grid filter resets to current date and time and check-in two patients then notifictaion count reduces
    And logout from practice provisioning portal

  Scenario: verify on doing check-in for current date -1 appointments and notification count are coming correctly after check in of patients in curbside arrival grid
    When I schedule an appointment
    And I apply filter for start date as current date -1 and end date as current date -1 in curbside arrival grid
    And I switch to appointments tab and again switch to curbside arrival grid and I checkin one patient
    Then I verify that after switching from appointments tab to curbside arrival grid filter resets to current date and time and check-in one patient then notifictaion count reduces
    And logout from practice provisioning portal

  Scenario: verify on doing check-in for current date -1 appointments and notification count are coming correctly after check in of all patients in curbside arrival grid
    When I schedule two appointments
    And I apply filter for start date as current date -1 and end date as current date in curbside arrival grid
    And I switch to appointments tab and again switch to curbside arrival grid and I checkin all patients
    Then I verify that after switching from appointments tab to curbside arrival grid filter resets to current date and time and check-in all patients then notifictaion count becomes zero
    And logout from practice provisioning portal

  Scenario: verify on doing check-in for current date appointment and notification count are coming correctly after check in of two patients in curbside arrival grid
    When I schedule two appointments
    And I apply filter for start date as current date and end date as current date in curbside arrival grid
    And I switch to appointments tab and again switch to curbside arrival grid and I checkin two patients
    Then I verify that after switching from appointments tab to curbside arrival grid filter resets to current date and time and check-in two patients then notifictaion count becomes reduces
    And logout from practice provisioning portal

  Scenario: verify all '50 records' from current page are deleted which contains valid data
    And I select all 50 records appointments from backdated 1 month
    And I delete all 50 records appointments
    Then I verify all appointment whose entry does not exist in arrival grid should not get deleted rest appointment entry should get deleted
    And logout from practice provisioning portal

  Scenario: verify all 50 records gets deleted and system shows page 1 of 2 with latest records on page 1 after deleting appointments
    And I select all 50 records appointments from page 1
    And I delete all 50 records appointments from page 1 and click on refresh button
    Then I verify all 50 records should get deleted and after deleting the records on clicking refresh button next page 2 records should come on page 1 records
    And logout from practice provisioning portal

  Scenario: Verify if broadcast is sent sucessfully and status in broadcast log is failed with message sent
    When I schedule an appointment where mobile number is valid ten digit number but not US number
    And I select patient from appointment dashboard and send broadcast message
    Then I verify if broadcast is sent sucessfully and status in broadcast log is failed with message sent
    And logout from practice provisioning portal

  Scenario: verify that user is able to see blank additional arrival instructions in english language
    When from setting in notifications I click on curbside checkin tab
    And I click on english button and additional arrival instructions text box keep blank
    And I click on save button
    And I schedule an appointment in "en"
    Then I verify user is not able to see additional arrival instruction in mail for english
    And logout from practice provisioning portal

  Scenario: verify that user is able to see blank additional arrival instructions in spanish language
    When from setting in notifications I click on curbside checkin tab
    And I click on spanish button and additional arrival instructions text box keep blank
    And I click on save button
    And I schedule an appointment in "es"
    Then I verify user is not able to see additional arrival instruction in mail for spanish
    And logout from practice provisioning portal

  Scenario: verify that Display oldest timer entry on the top
    When I enabled curbside remainder checkbox from notifications tab in Setting tab
    And I switch on curbside checkin tab
    And I schedule an appointment for patient "Kane" after 30 minutes
    And I received curbside message on email and confirm arrival
    And I schedule an appointment for patient "William" after 15 minutes
    And I received curbside message on email and confirm arrival
    Then I verify patient1 has oldest time enrty so patient1 should get displayed at the top of patient2
    And logout from practice provisioning portal

  Scenario: Verify that latest patient arrived goes to the end of the line that is row is added to the bottom
    When I enabled curbside remainder checkbox from notifications tab in Setting tab
    And I switch on curbside checkin tab
    And I schedule an appointment for patient "Kane" after 20 minutes
    And I received curbside message on email and confirm arrival
    And I schedule an appointment for patient "William" after 30 minutes
    And I received curbside message on email and confirm arrival
    Then I verify latest patient arrived goes to the end of the line that is row is added to the bottom
    And logout from practice provisioning portal

  Scenario: verify if user upload logo then on mail(Scheduled,Reminder,Curbside,Curbside checkin,Broadcast,Manual reminder)On UI should be reflected
    And I go to settings tab
    And I click on logo tab
    And I upload new logo image
    Then I verify on patient UI new logo image should be reflected after upload of new image
    And logout from practice provisioning portal

  Scenario: verify if user is able to add an image to a particular provider
    And I go to settings tab
    And I click on providers tab
    And I click on add a new provider button
    And I click on choose image link,add providerId,provider firstname,middlename,lastname,title
    And I click on save button for provider
    And I apply filter for provider
    And I delete the provider
    Then I verify user is able to add a provider and delete a provider
    And logout from practice provisioning portal

  Scenario: verify if system send email in English and Spanish language
    And I go to settings tab
    And I click on notifications tab
    And I click on practice preference language and select English and Spanish language from dropdown
    And I click on save button
    And I schedule an appointment for English and Spanish language
    Then I verify system should send emails and text in English and Spanish language
    And logout from practice provisioning portal

  Scenario: verify if system send email in English language
    And I go to settings tab
    And I click on notifications tab
    And I click on practice preference language and select English language from dropdown
    And I click on save button
    And I schedule an appointment for English language
    Then I verify system should send emails and text in English language
    And logout from practice provisioning portal

  Scenario: verify user is able to see appt manual reminder,scheduled reminder,confirmation reminder,curbside reminder in mail after display patient first name is enable
    And I go to settings tab
    And I click on notifications tab
    And I enable display patients first name checkbox
    And I switch on appointments tab
    And I schedule an appointment for English language
    And I send manual reminder for the scheduled appointment
    Then I verify user is able to see first name in manual reminder,scheduled reminder,confirmation reminder,curbside reminder in mail
    And logout from practice provisioning portal

  Scenario: verify user is able to see appt scheduled reminder,confirmation reminder,curbside reminder,broadcast reminder in mail after display patient first name is enable
    And I go to settings tab
    And I click on notifications tab
    And I enable display patients first name checkbox
    And I switch on appointments tab
    And I schedule an appointment for English language
    And I send broadcast reminder for the scheduled appointment
    Then I verify user is able to see first name in appointment scheduled reminder,appointment confirmation reminder , curbside reminder,broadcast in mail
    And logout from practice provisioning portal

  Scenario: verify user is not able to see manual reminder,scheduled reminder,confirmation reminder,curbside reminder in mail after display patient first name is disable
    And I go to settings tab
    And I click on notifications tab
    And I disable display patients first name checkbox
    And I click on save button
    And I switch on appointments tab
    And I schedule an appointment for English language
    And I send manual reminder for the scheduled appointment
    Then I verify user is not able to see first name in manual reminder,scheduled reminder,confirmation reminder,curbside reminderin mail
    And logout from practice provisioning portal

  Scenario: verify broadcast button is visible in appointment dashboard if user enable broadcast button
    And I go to settings tab
    And I click on notifications tab
    And I enable broadcast messaging checkbox
    And I click on appointment dashboard
    And I schedule an appointment
    And I select the patient checkbox
    And I click on Actions button
    Then I verify that broadcast button is visible in actions dropdown in appointment dashboard
    And logout from practice provisioning portal

  Scenario: verify broadcast button is not visible in appointment dashboard if user disable broadcast button
    And I go to settings tab
    And I click on notifications tab
    And I disable broadcast messaging checkbox
    And I click on appointment dashboard
    And I schedule an appointment
    And I select the patient checkbox
    And I click on Actions button
    Then I verify that broadcast button is not visible in actions dropdown in appointment dashboard
    And logout from practice provisioning portal

  Scenario: verify if user enter curbside arrival instruction msg for English and add up to 500 character & >500 character
    When I click on setting tab
    And I click on notification tab
    And I click on curbside checkin
    And I enter curbside arrival instruction msg for english and add 500 characters
    Then I verify custom arrival instruction msg should allow to add up to 500 characters and in the left side corner it should show filled character count in left for english language after 500 character is filled system should not allow to update arrival instruction message
    And logout from practice provisioning portal

  Scenario: verify if user enter curbside arrival instruction msg for Spanish and add up to 500 character & >500 character
    When I click on setting tab
    And I click on notification tab
    And I click on curbside checkin
    And I enter curbside arrival instruction msg for spanish and add 500 characters
    Then I verify custom arrival instruction msg should allow to add up to 500 characters and in the left side corner it should show filled character count in left for spanish language after 500 character is filled system should not allow to update arrival instruction message
    And logout from practice provisioning portal

  Scenario: verify if characters count are getting decremented when arrival instruction message is entered for English language
    When I click on setting tab
    And I click on notification tab
    And I click on curbside checkin
    And I enter curbside arrival instruction msg for english and add upto 100 characters
    Then I verify when 100 character are entered then total character count should should be 100/500
    And logout from practice provisioning portal

  Scenario: verify if characters count are getting decremented when arrival instruction message is entered for Spanish language
    When I click on setting tab
    And I click on notification tab
    And I click on curbside checkin
    And I enter curbside arrival instruction msg for spanish and add upto 100 characters
    Then I verify when 100 character are entered then total character count should should be 100/500
    And logout from practice provisioning portal

  Scenario: verify if user is on notification reminder template and user select first Hours,min and then Day in timing units and save changes
    When I click on setting tab
    And I click on notification tab
    And I click on edit of hamburger button for email in appointment reminders
    And I click on cadence editor template page and select hours,min,day
    Then I verify system should allow to edit template as per user point of view
    And logout from practice provisioning portal

  Scenario: verify user is on notification reminder template and user is able to see timing units in proper sequence
    When I click on setting tab
    And I click on notification tab
    Then I verify system should show timing units on template in proper format Days,Hours,Minutes
    And logout from practice provisioning portal

  Scenario: verify if user is on notification reminder template and user select first min,hour and then Day in timing units and save changes
    When I click on setting tab
    And I click on notification tab
    And I click on edit of hamburger button for email in appointment reminders
    And I click on cadence editor template page and select min,hours,day
    Then I verify system should show timing units on template properly
    And logout from practice provisioning portal

  Scenario: verify if user is on notification reminder template and user select first hour,min,hour and then Day in timing units and save changes
    When I click on setting tab
    And I click on notification tab
    And I click on edit of hamburger button for email in appointment reminders
    And I click on cadence editor template page and select first hour,min,hour and Day
    Then I verify system should allow to edit template in proper format as per user point of view
    And logout from practice provisioning portal

  Scenario: verify user is on notification reminder template and user is able to see timing units in proper format
    When I click on setting tab
    And I click on notification tab
    Then I verify system should show timing units on template in proper format hours,minutes,hours and day
    And logout from practice provisioning portal

  Scenario: verify after deleting all timing unit for mail in appointment reminder section timing and timing units fields system show null
    When from setting in notifications user click on email hamburgerButton section of appointment reminder
    And I hit edit button of "Email" for appointment reminder
    And I delete all timing and timing unit and save configuration
    Then I verify for mail on appointment reminder section timing and timing units fields system show blank
    And logout from practice provisioning portal

  Scenario: verify after deleting all timing unit for text in appointment reminder section timing and timing units fields system show null
    When from setting in notifications user click on text hamburgerButton section of appointment reminder
    And I hit edit button of "SMS" for appointment reminder
    And I delete all timing and timing unit and save configuration
    Then I verify for text on appointment reminder section timing and timing units fields system show blank
    And logout from practice provisioning portal

  Scenario: Verify broadcast is sent after unsubscribe and resubscribe
    When I enable Broadcast messaging checkbox from setting in notifications dashboard
    And I schedule an appointment with valid email and phone number
    And from mail I unsubscribe a patient
    And I switch on practice provisioning url
    And I select patient and send broadcast message from appointment dashboard
    And I get count from email broadcast logs
    And I resubscribe patient mail
    Then I verify after sending broadcast message count will be increases
    And logout from practice provisioning portal

  Scenario: verify paper plane icon and logs shows red color when schedule an apt with invalid email and text
    When from setting in notifications user turn on send notification radio button
    And I schedule an appointment with invalid email and phone number
    Then I verify paper plane icon and logs shows red colur and status is failed
    And logout from practice provisioning portal

  Scenario: verify paper plane icon and logs shows blank value when schedule an apt with valid email and text which are unsubscribed
    When from setting in notifications user turn on send notification radio button
    And I enable Broadcast messaging checkbox from setting in notifications dashboard
    And I schedule an appointment with valid email and phone number
    And from mail I unsubscribe a patient
    And I switch on practice provisioning url
    And again I schedule an appointment with same email and phone number
    Then I verify paper plane icon and logs shows blank value
    And logout from practice provisioning portal

  Scenario: verify system should send 1 hour prior curbside reminder if user enables Curbside checkin reminder checkbox
    And I go to settings tab
    And I click on notifications
    And I enable Curbside checkin reminder checkbox
    And I click on Appointment tab
    And I schedule an appointment for curbside checkin reminder
    Then I verify system should send curbside checkin reminder within next one hour
    And logout from practice provisioning portal

  Scenario: verify system should not send curbside reminder if user disables Curbside checkin reminder checkbox
    And I go to settings tab
    And I click on notifications
    And I disable Curbside checkin reminder checkbox
    And I click on Appointment tab
    And I schedule an appointment for curbside checkin reminder
    Then I verify system should not send curbside reminder
    And logout from practice provisioning portal

  Scenario: verify if user is able to add or edit arrival message for mail and it will reflect on mail
    And I go to settings tab
    And I go to notifications tab
    And I click on Curbside check-in of notification tab
    And I add additional arrival instructions in English
    And I go to Appointment tab
    And I schedule an appointment and confirm the arrival from mail
    Then I verify user is able to add or edit the arrival message after confirmation in mail
    And logout from practice provisioning portal

  Scenario: verify if user is able to keep blank arrival message for mail and it will reflect on mail
    And I go to settings tab
    And I go to notifications tab
    And I click on Curbside check-in of notification tab
    And I clear additional arrival instructions in English
    And I go to Appointment tab
    And I schedule an appointment and confirm the arrival from mail
    Then I verify user is able to keep blank arrival instruction message after confirmation in mail
    And logout from practice provisioning portal

  Scenario: verify user is able to add or edit patient mode completion message
    And I go to settings tab
    And I click on precheck tab
    And I add patient mode completion message
    Then I verify that user is able to add or edit patient mode completion message
    And logout from practice provisioning portal

  Scenario: verify changed practice display name in appointment dashboard
    And I go to settings tab
    And I click on general tab
    And I add new practice display name and update changes
    Then I verify that practice display name is changed in appointment dashboard
    And logout from practice provisioning portal

  Scenario: verify if user adds or edit phone number
    And I go to settings tab
    And I click on precheck tab
    And I add phone number
    And I edit phone number
    And I click on save changes button
    Then I verify user is able to add or edit phone number
    And logout from practice provisioning portal

  Scenario: verify user is able to add form in the forms grid
    And I go to settings tab
    And I click on forms tab
    And I click on add form button
    Then I verify user is able to add form in the forms grid
    And logout from practice provisioning portal

  Scenario: verify user is able to associate appointment type to form
    And I go to settings tab
    And I click on forms tab
    And I associate appointment type with form
    Then I verify user is able to associate appointment type to form
    And logout from practice provisioning portal

  Scenario: verify user is able to delete form
    And I go to settings tab
    And I click on forms tab
    And I delete the form
    Then I verify user is able to delete the form and form cannot be seen in form grid
    And logout from practice provisioning portal

  Scenario: verify user is able to add instructions for primary,secondary,tertiary insurance
    And I go to settings tab
    And I click on precheck tab
    And I add instructions for primary,secondary,tertiary
    Then I verify that user is able to see instructions for primary,secondary,tertiary on UI
    And logout from practice provisioning portal

  Scenario: verify while doing precheck change the first name and last name then verify updated first name should be reflected on appt dashboard, broadcast email notification logs and on email
    When I schedule an appointment with valid email and phone number
    And appointment should be scheduled and reminder & confirmation email should be received to patient
    And I do the precheck and update first name, middle name, last name
    Then I verify updated first name, middle name, last name should be reflect on appt dashboard, broadcast email notification logs and on email
    And logout from practice provisioning portal

  Scenario: verify while doing precheck change the first name, last name, email and phone number then verify updated first name, last name, email and phone number should be reflected on appt dashboard, broadcast email notification logs and on email
    When I schedule an appointment with valid email and phone number
    And appointment should be scheduled and reminder & confirmation email should be received to patient
    And I do the precheck and update first name, middle name, last name, email and phone number
    Then I verify updated first name, middle name, last name, email should be reflect on appt dashboard, broadcast email notification logs and on email
    And logout from practice provisioning portal

  Scenario: Verify if user is able to see paperplane icon on in reminders text and mails section
    When I schedule an appointment in "en"
    And I am able to click on > expand icon
    And on appointment dashboard default icon should be display
    Then I verify after getting cadance reminder default icon is replace with paper plane and on that paper plane icon count 1 will display
    And logout from practice provisioning portal

  Scenario: Verify if display first name is off then appointment scheduled confirmation mail does not show first name
    When I go to settings tab and click on notifications tab
    And I disable the display patient first name and save the notifications
    And I schedule an appointment and I receive the appointment scheduled confirmation mail
    Then I verify appointment scheduled confirmation mail recieved should not show first name
    And logout from practice provisioning portal

  Scenario: Verify if display first name is off then appointment reminder in mail does not show first name
    When I go to settings tab and click on notifications tab
    And I disable the display patient first name and save the notifications
    And I schedule an appointment and I receive the appointment reminder in mail
    Then I verify appointment reminder recieved in mail should not show first name
    And logout from practice provisioning portal

  Scenario: Verify if display first name is off then broadcast message in mail does not show first name
    When I go to settings tab and click on notifications tab
    And I disable the display patient first name and save the notifications
    And I schedule an appointment and I receive the broadcast message in mail
    And I send broadcast message to patient
    Then I verify broadcast message recieved in mail should not show first name
    And logout from practice provisioning portal

  Scenario: Verify if display first name is off then curbside reminder in mail does not show first name
    When I go to settings tab and click on notifications tab
    And I disable the display patient first name and save the notifications
    And I schedule an appointment and I receive the curbside reminder in mail
    Then I verify curbside reminder recieved in mail should not show first name
    And logout from practice provisioning portal

  Scenario: Verify if display first name is off then manual reminder in mail does not show first name
    When I go to settings tab and click on notifications tab
    And I disable the display patient first name and save the notifications
    And I schedule an appointment and I receive the manual reminder in mail
    And I send manual reminder for that appointment
    Then I verify manual reminder recieved in mail should not show first name
    And logout from practice provisioning portal

  Scenario: verify by default in timing textbox user is able to see days configured and timing units 1,3,5 days configured
    When I click on setting tab
    And I click on notification tab
    And I click on edit of hamburger button for email in appointment reminders
    Then I verify system should show by default days configured in days section and in timing units 1,3,5 configured
    And logout from practice provisioning portal

  Scenario: verify if user click on dropdown in timing section then there is 3 fields display in dropdown that is Day,hours,minutes
    When I click on setting tab
    And I click on notification tab
    And I click on edit of hamburger button for email in appointment reminders
    And I click on dropdown of timing
    Then I verify system should show only 3 fields in dropdown of timing that is Day,hours,minutes
    And logout from practice provisioning portal

  Scenario: verify if user select Days from timing section then in timing units user is able to enter 1 to unlimited numbers
    When I click on setting tab
    And I click on notification tab
    And I click on edit of hamburger button for email in appointment reminders
    And I enter 1 to unlimited numbers in timing units
    Then I verify system should allow user to enter values from 1 to no limit
    And logout from practice provisioning portal

  Scenario: verify if user select Hours from timing section then in timing units user is able to enter only 1 to 23 number.
    When I click on setting tab
    And I click on notification tab
    And I click on edit of hamburger button for email in appointment reminders
    And I select Hours and enter 1 to 23 number in timing units
    Then I verify system should allow only 1 to 23 number in the timing units section
    And logout from practice provisioning portal

  Scenario: verify if user select minutes from timing section then in timing units user is able to enter only 10 integer
    When I click on setting tab
    And I click on notification tab
    And I click on edit of hamburger button for email in appointment reminders
    And I select minutes and enter 10 number in timing units
    Then I verify system should allow only 10 integer in the timing units section
    And logout from practice provisioning portal

  Scenario: verify if user select minutes from timing section then in timing units user is able to enter only 59 number
    When I click on setting tab
    And I click on notification tab
    And I click on edit of hamburger button for email in appointment reminders
    And I select minutes and enter 59 number in timing unit
    Then I verify system should allow only 59 integer in the timing unit section
    And logout from practice provisioning portal

  Scenario: Verify for both manual and cadence reminder single history pop up will display
    When I schedule an appointment in "en"
    And I select patient and send manual reminder
    Then I verify for email and text reminder system should show all manual and cadence reminder log on mails history  pop up
    And logout from practice provisioning portal

  Scenario: verify if user is able to see latest reminder status in mail and text section either for cadence reminder.
    When I click on setting tab
    And I click on notification tab
    And I am on the reminder section of the appointment reminder
    And I click on appointments tabs
    And I schedule an appointment for English language
    And I go to appointment dashboard and select one user from appointment dashboard
    And I click on '>' icon of the selected patient from dashboard
    Then I verify system should show latest cadence reminder status on page
    And logout from practice provisioning portal

  Scenario: verify if user is able to see manual reminder status in mail and text section either for manual reminder.
    When I click on setting tab
    And I click on notification tab
    And I am on the reminder section of the appointment reminder
    And I click on appointments tab
    And I schedule an appointment for English language
    And I select one user from appointment dashboard and send manual reminder
    And I click on '>' icon of the selected patient from dashboard
    Then I verify system should show manual reminder status on page
    And logout from practice provisioning portal

  Scenario: Verify if display first name is on then appointment scheduled confirmation mail show first name
    When I go to settings tab and click on notifications tab
    And I enable the display patient first name and save the notifications
    And I schedule an appointment and I receive the appointment scheduled confirmation in mail
    Then I verify appointment scheduled confirmation mail recieved should show first name
    And logout from practice provisioning portal

  Scenario: Verify if display first name is on then appointment reminder in mail show first name
    When I go to settings tab and click on notifications tab
    And I enable the display patient first name and save the notifications
    And I schedule an appointment and I get the appointment reminder in mail
    Then I verify appointment reminder recieved in mail should show first name
    And logout from practice provisioning portal

  Scenario: Verify if display first name is on then broadcast message in mail show first name
    When I go to settings tab and click on notifications tab
    And I enable the display patient first name and save the notifications
    And I schedule an appointment and I receive the broadcast message reminder in mail
    And I send broadcast message to patient
    Then I verify broadcast message recieved in mail should show first name
    And logout from practice provisioning portal

  Scenario: Verify if display first name is on then curbside reminder in mail show first name
    When I go to settings tab and click on notifications tab
    And I enable the display patient first name and save the notifications
    And I schedule an appointment and I get the curbside reminder in mail
    Then I verify curbside reminder recieved in mail should show first name
    And logout from practice provisioning portal

  Scenario: Verify if display first name is on then manual reminder in mail show first name
    When I go to settings tab and click on notifications tab
    And I enable the display patient first name and save the notifications
    And I schedule an appointment and I get the manual reminder in mail
    And I send manual reminder for that appointment
    Then I verify manual reminder recieved in mail should show first name
    And logout from practice provisioning portal

  Scenario: verify if user delete 4th timing unit by click on (-) button from page
    When I click on setting tab
    And I click on notification tab
    And I click on edit of hamburger button for email in appointment reminders
    And I click on 4th timing unit
    And I click on delete button of 4th timing unit
    Then I verify that system should allow user to delete timing units from page
    And logout from practice provisioning portal

  Scenario: verify if user is able to see (+) add in enable format after deleting 4th timing unit
    When I click on setting tab
    And I click on notification tab
    And I click on edit of hamburger button for email in appointment reminders
    And I click on 4th timing unit
    And I click on delete button of 4th timing unit
    Then I verify add button should display in enable format
    And logout from practice provisioning portal

  Scenario: verify if user is able to see add button in enable format when I delete 2 more timing units
    When I click on setting tab
    And I click on notification tab
    And I click on edit of hamburger button for email in appointment reminders
    And I click on 4th timing unit
    And I click on delete button of 4th and 3rd timing unit
    Then I verify add button should display in enable format if I delete 2 timing units
    And logout from practice provisioning portal

  Scenario: verify if user is able to delete all timing units fields on cadence editor template page by clicking on (-) button
    When I click on setting tab
    And I click on notification tab
    And I click on edit of hamburger button for email in appointment reminders
    And I click on delete button of all timing unit fields
    Then I verify system should allow user to delete all timing units fields and in notification tab the timing and timing unit section blank space should display
    And logout from practice provisioning portal

  Scenario: verify if user is able to add again timing units field by clicking on (+) add button on cadence editor template page
    When I click on setting tab
    And I click on notification tab
    And I click on edit of hamburger button for email in appointment reminders
    And I click on add button on cadence editor template page
    Then I verify system should allow user to add timing fields on cadence editor template page
    And logout from practice provisioning portal

  Scenario: Verify add button functionality for email in appointment reminder template editor page
    When I click on email edit section of appointment reminders from setting in notifications tab
    Then I verify add button functionality for "Email"
    And I save cadence for email
    And logout from practice provisioning portal

  Scenario: Verify add button functionality for email in appointment reminder template editor page
    When I click on text edit section of appointment reminders from setting in notifications tab
    Then I verify add button functionality for "SMS"
    And I save cadence for text
    And logout from practice provisioning portal

  Scenario: verify If patient has Spanish as preferred language in appointment data then send message in Spanish
    When I enabled curbside remainder checkbox from notifications tab in Setting tab
    And I schedule an appointment in spanish "es" and have confirmed there arrival
    And click on Curbside check-in tab
    And I select patient and click on dropdown from curbside checkin page
    And I send "Come in the office now." message to selected patient
    And I received message "ven a la oficina ahora." in email in spanish language
    And I send "Wait in the parking lot until we send you a message to come in." message to selected patient
    And I received message "espere en el estacionamiento hasta que le enviemos un mensaje para que ingrese." in email in spanish language
    And I send "We will call you shortly to collect your insurance information." message to selected patient
    Then I verify message "lo llamaremos en breve para recopilar la información de su seguro." receive in spanish language
    And logout from practice provisioning portal

  Scenario: verify if the total count of number of patients waiting in the parking lot are displayed in the top left corner in Curbside check-in grid
    When I enabled curbside remainder checkbox from notifications tab in Setting tab
    And I clear all appointments from on curbside checkin tab
    And click on Curbside check-in tab
    And on nitification icon "0" count should be displayed on the top left corner
    And I schedule 3 appointments and finish curbside checkin process from mail by click on I have arrived
    And I logged into practice provisioning and view the notification icon count "3" on the top left corner
    And I schedule 2 appointments and finish curbside checkin process from mail by click on I have arrived
    And I logged into practice provisioning and view the notification icon count "5" on the top left corner
    And I switch on curbside checkin tab and select patient and click on check in button
    Then I verify on nitification icon "4" count should be displayed on the top left corner
    And logout from practice provisioning portal

  Scenario: verify notification setting for arrival instruction for spanish and english section and default msg is not editable and hardcoded
    When I click on setting tab
    And I click on notification tab
    And I click on curbside check-in of notifications tab
    Then I verify default arrival instruction message for spanish and english section is not editable
    And logout from practice provisioning portal

  Scenario: verify if arrival confirmation additional instruction text is displayed and textbox is displayed below for English and Spanish language
    When I click on setting tab
    And I click on notification tab
    And I click on curbside check-in of notifications tab
    Then I verify arrival confirmation additional instruction text is displayed and textbox is displayed below for English and spanish language
    And logout from practice provisioning portal

  Scenario: verify if arrival confirmation additional instruction text is displayed and blank textbox is displayed below for English language
    When I click on setting tab
    And I click on notification tab
    And I click on curbside check-in of notifications tab
    And I click on additional arrival instruction for English section
    Then I verify arrival confirmation additional instruction text is displayed and blank textbox is displayed below for English language
    And logout from practice provisioning portal

  Scenario: verify if arrival confirmation additional instruction text is displayed and blank textbox is displayed below for Spanish language
    When I click on setting tab
    And I click on notification tab
    And I click on curbside check-in of notifications tab
    And I click on additional arrival instruction for Spanish section
    Then I verify arrival confirmation additional instruction text is displayed and blank textbox is displayed below for Spanish language
    And logout from practice provisioning portal

  Scenario: verify if arrival confirmation additional instruction text msg length does not exceeds above 500 character for English language
    When I click on setting tab
    And I click on notification tab
    And I click on curbside check-in of notifications tab
    And I click on additional arrival instruction for English section and add arrival instruction
    Then I verify arrival confirmation additional instruction text msg length should not exceeds above 500 character

  Scenario: verify if arrival confirmation additional instruction text msg length does not exceeds above 500 character for Spanish language
    When I click on setting tab
    And I click on notification tab
    And I click on curbside check-in of notifications tab
    And I click on additional arrival instruction for Spanish section and add arrival instruction
    Then I verify arrival confirmation additional instruction text msg length should not exceeds above 500 character for Spanish language

  Scenario: verify arrival instruction msg received to patient is default msg when custom instruction is blank for English language
    When I click on setting tab
    And I click on notification tab
    And I select English as preferred language
    And I click on appointments tab
    And I schedule an appointment and confirm arrival message
    Then I verify arrival confirmation default msg should be received to patient in English language for email and text

  Scenario: verify if user is able to add up-to 500 characters in default additional arrival instruction text box for english language
    When from setting in notifications user click on curbside checkin tab and click on english button
    Then I verify after user able to add above five hundred character in default arrival instruction message for english and it should shows filled character count in left
    And I click on save for english and redirect to notification dashboard
    And logout from practice provisioning portal

  Scenario: verify if user enter 100 characters in the english additional arrival instructions text box then count should show correct
    When from setting in notifications user click on curbside checkin tab and click on english button
    Then I verify user able to enter hundred characters in arrival instructions text box for english then count should show correct
    And I click on save for english and redirect to notification dashboard
    And logout from practice provisioning portal

  Scenario: verify if user is able to add up-to 500 characters in default additional arrival instruction text box for spanish language
    When from setting in notifications user click on curbside checkin tab and click on spanish button
    Then I verify after user able to add above five hundred character in default arrival instruction message for spanish and it should shows filled character count in left
    And I click on save for spanish and redirect to notification dashboard
    And logout from practice provisioning portal

  Scenario: verify if user enter 100 characters in the english additional arrival instructions text box then count should show correct
    When from setting in notifications user click on curbside checkin tab and click on spanish button
    Then I verify user able to enter hundred characters in arrival instructions text box for spanish then count should show correct
    And I click on save for spanish and redirect to notification dashboard
    And logout from practice provisioning portal

  Scenario: verify arrival instruction msg received to patient is default msg + Customized msg when custom instruction is entered for English language
    When I click on setting tab
    And I click on notification tab
    And I select English as preferred language
    And I click on curbside checkin tab of notification tab and add additional arrival instruction for English section
    And I click on appointments tab
    And I schedule an appointment with English language and confirm arrival message
    Then I verify arrival confirmation default msg + custom msg should be received to patient in English language for email and text

  Scenario: verify if system shows arrival instruction on email and text as per default msg set and system shows phone number as appointment location phone number
    When I click on setting tab
    And I click on notification tab
    And I select English and Spanish as preferred language
    And I click on appointments tab
    And I schedule an appointment with English and Spanish language and confirm the arrival
    Then I verify arrival notification msg should show mobile number of appointment location in the arrival text msg and msg should come in English language

  Scenario: verify if system shows arrival instruction on email and text as per default msg set and system shows phone number as appointment location phone number in English
    When I click on setting tab
    And I click on notification tab
    And I select English as preferred language
    And I click on appointments tab
    And I schedule an appointment with English language and confirm the arrival
    Then I verify arrival notification message should show mobile number of appointment location in the arrival text msg and Msg should come in English language

  Scenario: verify if system shows arrival instruction on email and text as per default msg set and phone number as appointment location phone number is blank
    When I click on setting tab
    And I click on notification tab
    And I select English as preferred language
    And I click on appointments tab
    And I schedule an appointment with English language and set appointment location and phone number as blank
    Then I verify arrival notification msg should not show "mobile number" text  in the arrival text msg

  Scenario: Verify if user is able to see timing units days, hours, mins in proper sequence on notification tab for email
    When from setting in notifications user click on email hamburgerButton section of appointment reminder
    And I hit edit button of email for appointment reminder
    And I select first Hours, min and then Day in timing units and save changes for 'Email'
    Then I verify user is able to see timing units in proper sequence on notification tab for email
    And logout from practice provisioning portal

  Scenario: Verify if user is able to see timing units in proper sequence on notification tab for email
    When from setting in notifications user click on email hamburgerButton section of appointment reminder
    And I hit edit button of email for appointment reminder
    And I select first minutes, hours and then Day in timing units and save changes for 'Email'
    Then I verify user is able to see timing units in proper sequence on notification tab for email
    And logout from practice provisioning portal

  Scenario: Verify if user is able to see timing in proper sequence on notification tab for email
    When from setting in notifications user click on email hamburgerButton section of appointment reminder
    And I hit edit button of email for appointment reminder
    And I select first hour, min, hour and then Day in timing units and save changes for 'Email'
    Then I verify if user is able to see timing units in proper sequence on notification tab for email
    And logout from practice provisioning portal

  Scenario: Verify if user is able to see timing units days, hours, mins in proper sequence on notification tab for text
    When from setting in notifications user click on text hamburgerButton section of appointment reminder
    And I hit edit button of text for appointment reminder
    And I select first Hours, min and then Day in timing units in text and save changes for 'SMS'
    Then I verify user is able to see timing units in proper sequence on notification tab for text
    And logout from practice provisioning portal

  Scenario: Verify if user is able to see timing units in proper sequence on notification tab for text
    When from setting in notifications user click on text hamburgerButton section of appointment reminder
    And I hit edit button of text for appointment reminder
    And I select first minutes, hours and then Day in timing units in text and save changes for 'SMS'
    Then I verify user is able to see timing units in proper sequence on notification tab for text
    And logout from practice provisioning portal

  Scenario: Verify if user is able to see timing in proper sequence on notification tab for text
    When from setting in notifications user click on text hamburgerButton section of appointment reminder
    And I hit edit button of text for appointment reminder
    And I select first hour, min, hour and then Day in timing units in text and save changes for 'SMS'
    Then I verify if user is able to see timing units in proper sequence on notification tab for text
    And logout from practice provisioning portal

  Scenario: verify check-in change status decreases number of notification
    When I enabled curbside remainder checkbox from notifications tab in Setting tab
    And I clear all appointments from on curbside checkin tab
    And I schedule multiple appointments and perform arrival actions confirm
    And I schedule a new appointment and not having confirm
    And I click on the curbside link and finish curbside checkin process from mail
    And I logged into practice provisioning and view the notification icon count "6" on the top left corner
    And I switch on curbside checkin tab and select patient and click on check in button
    Then I verify notification icon count decreases and patient should be disappeared
    And logout from practice provisioning portal

  Scenario: verify broadcast count functionality is coming correct when select all patient on page one
    When I applied filter for start date
    And I select all record from page "1"
    And I deselect few records from page "1" and switch to page 2 and select all records
    And I again switch on page 1 click on banner message
    Then I verify broadcast message count from action button after clicking on banner message on page one
    And logout from practice provisioning portal

  Scenario: verify broadcast count functionality is coming correct when selects all patient on page two
    When I applied filter for start date
    And I select all record from page "1"
    And I switch on page "2" and select all records
    And I switch on page "1" and deselect few records
    And I switch on page "3" and select all records
    And I again switch on page 2 click on banner message
    Then I verify broadcast message count from action button after clicking on banner message on page two
    And logout from practice provisioning portal

  Scenario: verify broadcast count functionality is coming correct when selects all patient
    When I applied filter for start date, provider and location
    And I select all record from page "1"
    And I deselect few records from page "1" and switch to page 2 and select all records
    And I again switch on page 1 click on banner message
    Then I verify broadcast message count from action button after clicking on banner message on page one
    And logout from practice provisioning portal

  Scenario: verify patient personal information page
    When I schedule an appointment and update personal information
    And I click on patient name
    And I click on launch patient mode and change some of the fields
    Then I verify updated personal information should be reflected for the appointment in the appointment dashboard
    And logout from practice provisioning portal

  Scenario: verify patient contact info page
    When I schedule an appointment and update contact info
    And I click on patient name and do the precheck
    Then I verify updated contact info page should be reflected in appointment dashboard
    And logout from practice provisioning portal

  Scenario: verify by disabling the demographics on practice dashboard
    When I click on settings tab
    And I click on precheck tab and disable the demographics and save changes
    And I schedule an appointment
    And I click on patient name
    And I click on patient mode to do the precheck
    Then I verify precheck should not have personal info,contact info,pharmacy details
    And logout from practice provisioning portal

  Scenario: verify if user can add 3 tiers of insurances
    When I schedule an appointment and add insurances
    And I click on patient name and add 3 insurances during precheck
    Then I verify in appointment dashboard for that appointment 3 insurances are reflected
    And logout from practice provisioning portal

  Scenario: verify if user can remove existing insurances
    When I schedule an appointment and add insurances
    And I click on patient name and add 3 insurances during precheck and edit the insurance cards and remove primary,secondary,tertiary insurances
    Then I verify in appointment dashboard insurances should be removed for that appointment
    And logout from practice provisioning portal

  Scenario: verify if user disable insurance checkbox and while doing precheck insurance stepper should not ne seen
    When I disable insurance checkbox
    And I schedule an appointment and precheck
    And I click on patient name and do precheck and verify while doing precheck insurance stepper should not be seen
    And logout from practice provisioning portal

  Scenario: verify if user pays copay amount by pay with credit card
    When I schedule an appointment with copay
    And I click on patient name and pay the copay amount by credit card while doing precheck
    Then I verify in appointment dashboard for that appointment the copay amount is paid

  Scenario: verify after sending curbside arrival instruction message to curbside checkin patient in reminder column section system should not show day prior entry in reminder section
    When I click on setting tab and ON notification setting
    And I schedule an appointment and have confirmed there arrival
    And I click on Curbside check-in tab and select patient
    And I send "Come in the office now." message to selected patient
    And I switch on appointment dashboard
    Then I verify system should not show day prior entry in reminder section column
    And logout from practice provisioning portal

  Scenario: verify after sending curbside arrival instruction other message to curbside checkin patient in reminder column section system should not show day prior entry in reminder section
    When I click on setting tab and ON notification setting
    And I schedule an appointment and have confirmed there arrival
    And I click on Curbside check-in tab and select patient
    And I send "Other" message to curbside checkin patient
    And I switch on appointment dashboard
    Then I verify system should not show day prior entry in reminder section column
    And logout from practice provisioning portal

  Scenario: verify system is not allowing user to enter invalid integers in timing unit section for email in appointment reminders
    When from setting in notifications user click on email hamburgerButton section of appointment reminder
    And I hit edit button of email for appointment reminder
    Then I verify system is not allowing to enter invalid integers in timing unit section for email in appointment reminders for 'Email'
    And logout from practice provisioning portal

  Scenario: verify if the patient skips 'PayInOffice'
    When user schedule an appointment and skips
    And I do the precheck and click on skip 'PayInOffice'
    And I verify in appointment dashboard skips icon is seen for that appointment
    And logout from practice provisioning portal

  Scenario: verify if copay payments are already done
    When I schedule an appointment with copay
    And I do the precheck for copay
    Then I verify again after doing precheck for that appointment to check if copay and balance payments are already done
    And logout from practice provisioning portal

  Scenario: verify if user pays copay amount by pay in office button
    When I schedule an appointment with copay
    And I click on patient name and pay the copay amount by pay in office while doing precheck
    Then I verify in appointment dashboard for that appointment the copay should show pay in office
    And logout from practice provisioning portal

  Scenario: verify successful payment in copay
    When I schedule an appointment with copay
    And I pay copay with credit card and enter card details
    Then I verify confirm payment and processing payment message should be displayed
    And logout from practice provisioning portal

  Scenario: verify if disable copay from precheck settings
    When I disable copayment from precheck settings
    And I schedule an appointment with copay
    And I do the precheck after disabling copayment
    Then I verify copay page should not be seen in precheck
    And logout from practice provisioning portal

  Scenario: verify display of title and other headlines of Pay Balance
    When I schedule an appointment for paybalance
    And I do the precheck for pay balance
    Then I verify title and other details of pay balance should be seen proper
    And logout from practice provisioning portal

  Scenario: verify if user enable minimum balance amount in practice precheck settings
    When I enable minimum balance amount in precheck settings and enter minimum balance amount
    And I schedule an appointment for paybalance
    Then I verify precheck UI does not allow less than the amount payment
    And logout from practice provisioning portal

  Scenario: verify if user disables balance from practice precheck settings
    When I disable balance from practice precheck settings
    And I schedule an appointment for paybalance
    Then I verify balance page should not be seen during precheck
    And logout from practice provisioning portal

  Scenario: verify if patient has multiple appointments and does precheck for one of the appointments should update only that appointment
    When I schedule multiple appointments
    And I do the precheck for one appointment
    Then I verify system will show the appointment details of precheck completed not for other appointment whose precheck is not done
    And logout from practice provisioning portal

  Scenario: verify if user removes existing insurance
    When I schedule an appointment to remove the existing insurance
    And I do the precheck and remove existing insurances and
    Then I verify the message after removal of insurances
    And logout from practice provisioning portal

  Scenario: verify if user click On 'Im done button' after doing precheck
    When I schedule an appointment for doing precheck
    And I do the precheck for the appointment scheduled
    Then I verify the message after clicking on 'Im done button'
    And logout from practice provisioning portal

  Scenario: verify if user is able to see 'logout button' and 'cancel button' while doing precheck
    When I schedule an appointment for doing precheck
    And I do the precheck for the scheduled appointment
    Then I verify that user is able to see 'logout button' and 'cancel button'
    And logout from practice provisioning portal

  Scenario: verify if user after doing precheck clicks on cancel button it should bring back to Appointment details page
    When I schedule an appointment for doing precheck
    And I do the precheck and I click on cancel button
    Then I verify after clicking on cancel button it should bring back to Appointment details page
    And logout from practice provisioning portal

  Scenario: verify if user after doing precheck clicks on logout button it should logout from patient mode
    When I schedule an appointment for doing precheck
    And I click on logout button
    Then I verify it should logout from patient mode
    And logout from practice provisioning portal

  Scenario: verify appointment details page after precheck
    When I schedule an appointment for doing precheck
    And I do the precheck for the scheduled appointment
    Then I verify appointment details page is seen properly after doing precheck
    And logout from practice provisioning portal

  Scenario: verify check-in information in appointment details page for patient information
    When I schedule an appointment for doing precheck
    And I click on edit of patient information
    Then I verify it takes to precheck demographics, patient can be able to do precheck details
    And logout from practice provisioning portal

  Scenario: verify check-in information in appointment details page for insurance cards
    When I schedule an appointment for doing precheck
    And I click on edit of insurance cards
    Then I verify it takes to precheck insurance, patient can be able to do precheck for insurance details
    And logout from practice provisioning portal

  Scenario: verify menu options on all precheck pages
    When I schedule an appointment for doing precheck
    And I do the precheck for the scheduled appointment
    Then I verify all the menu options are seen properly on precheck page
    And logout from practice provisioning portal

  Scenario: verify signout options on precheck page
    When I schedule an appointment for doing precheck
    And I do the precheck for the scheduled appointment and click on signout link
    Then I verify signout link should take to the logout model for confirmation
    And logout from practice provisioning portal

  Scenario: verify disclaimer message for copay
    When I schedule an appointment with copay
    And I do the precheck for the appointment
    Then I verify that disclaimer message for copay is seen properly
    And logout from practice provisioning portal

  Scenario: verify appointment with dob before '1970'
    When I schedule an appointment with dob before 1970
    Then I verify that user complete precheck both from reminders as well as from patient mode
    And logout from practice provisioning portal

  Scenario: verify by adding insurance images to first/second/tertiary insurance from UI
    When I schedule an appointment for adding images to insurances
    And I do the precheck for adding images to insurances
    Then I verify that images for 'first,second,tertiary insurance ' should be added
    And logout from practice provisioning portal

  Scenario: verify that it is not possible to upload an insurance card that is not jpeg, png or gif
    When I schedule an appointment for adding images to insurances
    Then I verify that error message should be seen
    And logout from practice provisioning portal
