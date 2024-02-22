package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

public class AddressFilter {

	@QueryParam("address1")
	private String address1;

	@QueryParam("address2")
	private String address2;

	@QueryParam("city")
	private String city;

	@QueryParam("state")
	private String state;

	@QueryParam("country")
	private String country;

	@QueryParam("zipCode")
	private String zipCode;
	
	

	public AddressFilter() {

	}

	public AddressFilter(String address1, String address2, String city, String state, String country, String zipCode) {
		super();
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zipCode = zipCode;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

}
