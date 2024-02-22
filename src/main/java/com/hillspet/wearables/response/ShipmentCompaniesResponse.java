package com.hillspet.wearables.response;

import java.util.List;

import com.hillspet.wearables.dto.ShipmentCompany;

public class ShipmentCompaniesResponse {

	private List<ShipmentCompany> shipmentCompanies;

	public List<ShipmentCompany> getShipmentCompanies() {
		return shipmentCompanies;
	}

	public void setShipmentCompanies(List<ShipmentCompany> shipmentCompanies) {
		this.shipmentCompanies = shipmentCompanies;
	}

}
