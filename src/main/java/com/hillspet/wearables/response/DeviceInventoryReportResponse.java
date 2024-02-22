
	package com.hillspet.wearables.response;

	import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.DeviceInventoryReport;

import io.swagger.annotations.ApiModelProperty;

	@JsonIgnoreProperties(ignoreUnknown = true)
	public class DeviceInventoryReportResponse extends BaseResultCollection {

		private List<DeviceInventoryReport> deviceInventoryReportList;

		@JsonProperty("rows")
		@ApiModelProperty(value = "List of details for device details list search criteria")
		public List<DeviceInventoryReport> getDeviceInventoryReport() {
			return deviceInventoryReportList;
		}

		public void setDeviceInventoryReport(List<DeviceInventoryReport> deviceInventoryReportList) {
			this.deviceInventoryReportList = deviceInventoryReportList;
		}

	}

