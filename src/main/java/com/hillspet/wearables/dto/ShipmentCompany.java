package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShipmentCompany {

	private Integer shipmentCompanyId;
	private String shipmentCompanyName;
	private String trackingUrl;

	public Integer getShipmentCompanyId() {
		return shipmentCompanyId;
	}

	public void setShipmentCompanyId(Integer shipmentCompanyId) {
		this.shipmentCompanyId = shipmentCompanyId;
	}

	public String getShipmentCompanyName() {
		return shipmentCompanyName;
	}

	public void setShipmentCompanyName(String shipmentCompanyName) {
		this.shipmentCompanyName = shipmentCompanyName;
	}

	public String getTrackingUrl() {
		return trackingUrl;
	}

	public void setTrackingUrl(String trackingUrl) {
		this.trackingUrl = trackingUrl;
	}

}
