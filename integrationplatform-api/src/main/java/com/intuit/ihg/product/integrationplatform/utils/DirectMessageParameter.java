package com.intuit.ihg.product.integrationplatform.utils;

public class DirectMessageParameter {
	private String FirstName= "";
	private String LastName= "";
	private String OrganizationName= "";
	private String NationalProviderId= "";
	private String SpecialtyType= "";
	private String SpecialtyClassification= "";
	private String SpecialtySpecialization= "";
	private String Street= "";
	private String City= "";
	private String State= "";
	private String Zipcode= "";
	private String DirectAddress= "";
	private String Type= "";

	public DirectMessageParameter(String firstName,String lastName,String organizationName,String nationalProviderId,String specialtyType,String specialtyClassification,String specialtySpecialization,String street,String city,String state,String zipCode,String directAddress, String type) {
		this.FirstName = firstName;
		this.LastName = lastName;
		this.OrganizationName = organizationName;
		this.NationalProviderId = nationalProviderId;
		this.SpecialtyType = specialtyType;
		this.SpecialtyClassification = specialtyClassification;
		this.SpecialtySpecialization = specialtySpecialization;
		this.Street = street;
		this.City = city;
		this.State = state;
		this.Zipcode = zipCode;
		this.DirectAddress=directAddress;
		this.Type=type;
	}
	
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getOrganizationName() {
		return OrganizationName;
	}
	public void setOrganizationName(String organizationName) {
		OrganizationName = organizationName;
	}
	public String getNationalProviderId() {
		return NationalProviderId;
	}
	public void setNationalProviderId(String nationalProviderId) {
		NationalProviderId = nationalProviderId;
	}
	public String getSpecialtyType() {
		return SpecialtyType;
	}
	public void setSpecialtyType(String specialtyType) {
		SpecialtyType = specialtyType;
	}
	public String getSpecialtyClassification() {
		return SpecialtyClassification;
	}
	public void setSpecialtyClassification(String specialtyClassification) {
		SpecialtyClassification = specialtyClassification;
	}
	public String getSpecialtySpecialization() {
		return SpecialtySpecialization;
	}
	public void setSpecialtySpecialization(String specialtySpecialization) {
		SpecialtySpecialization = specialtySpecialization;
	}
	public String getStreet() {
		return Street;
	}
	public void setStreet(String street) {
		Street = street;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getZipcode() {
		return Zipcode;
	}
	public void setZipcode(String zipcode) {
		Zipcode = zipcode;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getDirectAddress() {
		return DirectAddress;
	}

	public void setDirectAddress(String directAddress) {
		DirectAddress = directAddress;
	}
	
}
