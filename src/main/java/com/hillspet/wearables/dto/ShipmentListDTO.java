package com.hillspet.wearables.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShipmentListDTO extends BaseDTO {

	private Integer deviceShipmentId;
	private String deviceNumber;
	private String petName;
	private String shipmentCompanyName;
	private LocalDate shipmentDate;
	private String trackingNumber;
	private String trackingUrl;
	private String status;

	public Integer getDeviceShipmentId() {
		return deviceShipmentId;
	}

	public void setDeviceShipmentId(Integer deviceShipmentId) {
		this.deviceShipmentId = deviceShipmentId;
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

}
