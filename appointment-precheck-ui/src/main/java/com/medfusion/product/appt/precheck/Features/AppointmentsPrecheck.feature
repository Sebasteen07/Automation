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
    And logout from practice provisioning portal
    
    Scenario: Verify if only email is enable from notification then in reminder column only mail column should displayed on appointment dashboard
    When from setting dashboard in general enable email check box and disable text checkbox
    Then verify on appointment dashboard user is able to see only mail column under broadcast message column and Text column is disappear
    And logout from practice provisioning portal