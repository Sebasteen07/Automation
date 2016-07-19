package com.medfusion.mdvip.objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

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

}
