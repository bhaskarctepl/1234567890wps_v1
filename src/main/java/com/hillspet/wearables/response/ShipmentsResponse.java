package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.ShipmentListDTO;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShipmentsResponse extends BaseResultCollection {

	private List<ShipmentListDTO> shipmentList;

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for all shipments info that matches the search criteria")
	public List<ShipmentListDTO> getShipmentList() {
		return shipmentList;
	}

	public void setShipmentList(List<ShipmentListDTO> shipmentList) {
		this.shipmentList = shipmentList;
	}
}
