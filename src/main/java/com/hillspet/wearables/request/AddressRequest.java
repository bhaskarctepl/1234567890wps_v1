package com.hillspet.wearables.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author sgorle
 *
 */
@ApiModel(description = "All information that required to create a pet parent or pet address", value = "AddressRequest")
@JsonInclude(value = Include.NON_NULL)
public class AddressRequest {

	@ApiModelProperty(value = "addressId", required = false, example = "1")
	private Integer addressId;

	@ApiModelProperty(value = "address1", required = true, example = "132")
	private String address1;

	@ApiModelProperty(value = "address2", required = true, example = "My Street")
	private String address2;

	@ApiModelProperty(value = "city", required = true, example = "Kingston")
	private String city;

	@ApiModelProperty(value = "state", required = true, example = "New York")
	private String state;

	@ApiModelProperty(value = "country", required = true, example = "US")
	private String country;

	@ApiModelProperty(value = "zipCode", required = true, example = "12401")
	private String zipCode;

	@ApiModelProperty(value = "timeZoneId", required = true, example = "104")
	private Integer timeZoneId;

	@ApiModelProperty(value = "timeZone", required = false, example = "1")
	private String timeZone;

	@ApiModelProperty(value = "addressType", required = true, example = "1")
	private Integer addressType;

	@ApiModelProperty(value = "isPreludeAddress", required = true, example = "0")
	private Integer isPreludeAddress;

	@ApiModelProperty(value = "dateFrom", required = true, example = "2023-01-01")
	// @JsonDeserialize(using = LocalDateDeserializer.class)
	// @JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate dateFrom;

	@ApiModelProperty(value = "dateTo", required = false, example = "2023-01-01")
	// @JsonDeserialize(using = LocalDateDeserializer.class)
	// @JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate dateTo;
	
	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
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

	public Integer getTimeZoneId() {
		return timeZoneId;
	}

	public void setTimeZoneId(Integer timeZoneId) {
		this.timeZoneId = timeZoneId;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public Integer getAddressType() {
		return addressType;
	}

	public void setAddressType(Integer addressType) {
		this.addressType = addressType;
	}

	public Integer getIsPreludeAddress() {
		return isPreludeAddress;
	}

	public void setIsPreludeAddress(Integer isPreludeAddress) {
		this.isPreludeAddress = isPreludeAddress;
	}

	public LocalDate getDateFrom() {
		return dateFrom;
	}

	// @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public void setDateFrom(LocalDate dateFrom) {
		this.dateFrom = dateFrom;
	}

	public LocalDate getDateTo() {
		return dateTo;
	}

	// @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public void setDateTo(LocalDate dateTo) {
		this.dateTo = dateTo;
	}

}
