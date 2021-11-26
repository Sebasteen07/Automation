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
