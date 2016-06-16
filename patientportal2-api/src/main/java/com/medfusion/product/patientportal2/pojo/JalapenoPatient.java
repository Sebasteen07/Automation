package com.medfusion.product.patientportal2.pojo;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.IHGUtil.Gender;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.patientportal2.utils.PortalUtil;

public class JalapenoPatient {

    private String email = "";
    private String password = "";
    private String url = "";
    private String firstName = "";
    private String lastName = "";

    private Gender gender;

    private String DOBDay;
    private String DOBMonth;
    private String DOBMonthText;
    private String DOBYear;

    private String ZipCode;
    private String address1;
    private String address2;
    private String city;
    private String state;

    public JalapenoPatient(PropertyFileLoader testData) {
        IHGUtil.PrintMethodName();

        email = IHGUtil.createRandomEmailAddress(testData.getEmail(), '.');
        firstName = testData.getFirstName() + PortalUtil.createRandomNumber();
        lastName = "TestPatient1";
        password = testData.getPassword();
        gender = Gender.MALE;
        url = testData.getUrl();
        DOBDay = testData.getDOBDay();
        DOBMonth = testData.getDOBMonth();
        DOBMonthText = testData.getDOBMonthText();
        DOBYear = testData.getDOBYear();
        ZipCode = testData.getZipCode();
        // TODO fix this hack - sak nemame pristup do com.medfusion.common.utils, tak co? :)
        address1 = "U rychtare gejoty 8";
        address2 = "U bobra 12";
        city = "Mamlasov";
        state = "Alabama";
    }

    public JalapenoPatient(String email, String password, String url, String firstName, String lastName, Gender gender,
            String dOBDay, String dOBMonth, String dOBMonthText, String dOBYear, String zipCode, String address1,
            String address2, String city, String state) {
        super();
        this.email = email;
        this.password = password;
        this.url = url;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        DOBDay = dOBDay;
        DOBMonth = dOBMonth;
        DOBMonthText = dOBMonthText;
        DOBYear = dOBYear;
        ZipCode = zipCode;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getDOBDay() {
        return DOBDay;
    }

    public void setDOBDay(String dOBDay) {
        DOBDay = dOBDay;
    }

    public String getDOBMonth() {
        return DOBMonth;
    }

    public void setDOBMonth(String dOBMonth) {
        DOBMonth = dOBMonth;
    }

    public String getDOBMonthText() {
        return DOBMonthText;
    }

    public void setDOBMonthText(String dOBMonthText) {
        DOBMonthText = dOBMonthText;
    }

    public String getDOBYear() {
        return DOBYear;
    }

    public void setDOBYear(String dOBYear) {
        DOBYear = dOBYear;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
