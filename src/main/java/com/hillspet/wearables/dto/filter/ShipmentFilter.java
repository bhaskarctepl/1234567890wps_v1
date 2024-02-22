package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class ShipmentFilter extends BaseFilter {
	
	@ApiParam(name = "shipmentCompanyId", value = "Search based on Shipment Company Ids")
	@QueryParam("shipmentCompanyId")
	private String shipmentCompanyId;

	public String getShipmentCompanyId() {
		return shipmentCompanyId;
	}

	public void setShipmentCompanyId(String shipmentCompanyId) {
		this.shipmentCompanyId = shipmentCompanyId;
	}

	

	
}
