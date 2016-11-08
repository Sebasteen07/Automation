package com.medfusion.mdvip.objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration

public class MDVIPPojos {

	@Value("${url}")
	private String url;

	@Value("${vusername}")
	private String validUserName;

	@Value("${vpassword}")
	private String validPassword;

	@Value("${ivusername}")
	private String invalidUserName;

	@Value("${ivpassword}")
	private String invalidPassword;

	@Value("${email}")
	private String email;

	@Value("${name}")
	private String name;

	@Value("${zipcode}")
	private String zipCode;

	@Value("${salutation}")
	private String salutation;

	@Value("${street}")
	private String street;

	@Value("${city}")
	private String city;

	@Value("${state}")
	private String state;

	@Value("${zip}")
	private String zip;

	@Value("${updatedemail}")
	private String updatedEmail;

	@Value("${phone}")
	private String phone;

	@Value("${cardname}")
	private String cardName;

	@Value("${cardnumber}")
	private String cardNumber;

	@Value("${cardexpmon}")
	private String expMonth;

	@Value("${cardexpyr}")
	private String expYear;

	@Value("${cardcvv}")
	private String cvv;

	@Value("${cardaddress}")
	private String cardAddress;

	@Value("${cardcity}")
	private String cardCity;

	@Value("${cardstate}")
	private String cardState;

	@Value("${cardzip}")
	private String cardZip;

	@Value("${bankname}")
	private String bankName;

	@Value("${rNumber}")
	private String routingNum;

	@Value("${aNumber}")
	private String accountNum;

	@Value("${bankaddress}")
	private String bankAddress;

	@Value("${bankcity}")
	private String bankCity;

	@Value("${bankstate}")
	private String bankState;

	@Value("${bankzip}")
	private String bankZip;

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

	@Value("${password}")
	private String password;


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getValidUserName() {
		return validUserName;
	}

	public void setValidUserName(String validUserName) {
		this.validUserName = validUserName;
	}

	public String getValidPassword() {
		return validPassword;
	}

	public void setValidPassword(String validPassword) {
		this.validPassword = validPassword;
	}

	public String getInvalidUserName() {
		return invalidUserName;
	}

	public void setInvalidUserName(String invalidUserName) {
		this.invalidUserName = invalidUserName;
	}

	public String getInvalidPassword() {
		return invalidPassword;
	}

	public void setInvalidPassword(String invalidPassword) {
		this.invalidPassword = invalidPassword;
	}

	public String getName() {
		return name;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getSalutation() {
		return salutation;
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZip() {
		return zip;
	}

	public String getUpdatedEmail() {
		return updatedEmail;
	}

	public String getPhone() {
		return phone;
	}

	public String getCardName() {
		return cardName;
	}

	public String getCardNum() {
		return cardNumber;
	}

	public String getCardExpMonth() {
		return expMonth;
	}

	public String getCardExpYear() {
		return expYear;
	}

	public String getCardCvv() {
		return cvv;
	}

	public String getCardAddress() {
		return cardAddress;
	}

	public String getCardCity() {
		return cardCity;
	}

	public String getCardState() {
		return cardState;
	}

	public String getCardZip() {
		return cardZip;
	}

	public String getBankName() {
		return bankName;
	}

	public String getRoutingNumber() {
		return routingNum;
	}

	public String getAccountNumber() {
		return accountNum;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public String getBankCity() {
		return bankCity;
	}

	public String getBankState() {
		return bankState;
	}

	public String getBankZip() {
		return bankZip;
	}
}
