package com.hillspet.wearables.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeoCodeAddress {

	private String address1;

	private String city;
	private String cityShortCode;
	private List<String> cities;

	private String state;
	private String stateShortCode;
	private List<String> states;

	private String country;
	private String countryShortCode;

	private String zipCode;
	private String zipCodeId;

	private String lat;
	private String lng;

	private TimeZone timeZone;

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityShortCode() {
		return cityShortCode;
	}

	public void setCityShortCode(String cityShortCode) {
		this.cityShortCode = cityShortCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStateShortCode() {
		return stateShortCode;
	}

	public void setStateShortCode(String stateShortCode) {
		this.stateShortCode = stateShortCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryShortCode() {
		return countryShortCode;
	}

	public void setCountryShortCode(String countryShortCode) {
		this.countryShortCode = countryShortCode;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getZipCodeId() {
		return zipCodeId;
	}

	public void setZipCodeId(String zipCodeId) {
		this.zipCodeId = zipCodeId;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}

	public List<String> getStates() {
		return states;
	}

	public void setStates(List<String> states) {
		this.states = states;
	}

	public List<String> getCities() {
		return cities;
	}

	public void setCities(List<String> cities) {
		this.cities = cities;
	}
}
