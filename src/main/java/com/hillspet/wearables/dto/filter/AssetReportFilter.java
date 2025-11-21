package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class AssetReportFilter extends BaseFilter {

	@ApiParam(name = "isWhiteListed", value = "Search based on isWhiteListed 1 or 0 ")
	@QueryParam("isWhiteListed")
	private String isWhiteListed;

	@ApiParam(name = "boxNumber", value = "Search based on Box Number ")
	@QueryParam("boxNumber")
	private String boxNumber;

	@ApiParam(name = "wifiSSId", value = "Search based on wifiSSId")
	@QueryParam("wifiSSId")
	private String wifiSSId;

	@ApiParam(name = "macAddress", value = "Search based on MAC Address ")
	@QueryParam("macAddress")
	private String macAddress;

	public String getIsWhiteListed() {
		return isWhiteListed;
	}

	public void setIsWhiteListed(String isWhiteListed) {
		this.isWhiteListed = isWhiteListed;
	}

	public String getBoxNumber() {
		return boxNumber;
	}

	public void setBoxNumber(String boxNumber) {
		this.boxNumber = boxNumber;
	}

	public String getWifiSSId() {
		return wifiSSId;
	}

	public void setWifiSSId(String wifiSSId) {
		this.wifiSSId = wifiSSId;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

}
