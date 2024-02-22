package com.hillspet.wearables.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Shipment extends BaseDTO {

	private static final long serialVersionUID = 1L;

	private Integer deviceShipmentId;
	private Integer deviceId;
	private Integer petId;
	private Integer shipmentCompanyId;
	private LocalDate shipmentDate;
	private String trackingNumber;
	private String trackingUrl;
	private String status;
	private Integer userId;

	private String deviceNumber;
	private String petName;
	private String shipmentCompanyName;

	public Integer getDeviceShipmentId() {
		return deviceShipmentId;
	}

	public void setDeviceShipmentId(Integer deviceShipmentId) {
		this.deviceShipmentId = deviceShipmentId;
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getPetId() {
		return petId;
	}

	public void setPetId(Integer petId) {
		this.petId = petId;
	}

	public Integer getShipmentCompanyId() {
		return shipmentCompanyId;
	}

	public void setShipmentCompanyId(Integer shipmentCompanyId) {
		this.shipmentCompanyId = shipmentCompanyId;
	}

	public LocalDate getShipmentDate() {
		return shipmentDate;
	}

	public void setShipmentDate(LocalDate shipmentDate) {
		this.shipmentDate = shipmentDate;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public String getTrackingUrl() {
		return trackingUrl;
	}

	public void setTrackingUrl(String trackingUrl) {
		this.trackingUrl = trackingUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public String getShipmentCompanyName() {
		return shipmentCompanyName;
	}

	public void setShipmentCompanyName(String shipmentCompanyName) {
		this.shipmentCompanyName = shipmentCompanyName;
	}

}
