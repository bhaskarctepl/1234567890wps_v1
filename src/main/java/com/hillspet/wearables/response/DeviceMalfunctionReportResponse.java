package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.DeviceMalfunctionReport;

import io.swagger.annotations.ApiModelProperty;

	@JsonIgnoreProperties(ignoreUnknown = true)
	public class DeviceMalfunctionReportResponse extends BaseResultCollection {

		private List<DeviceMalfunctionReport> deviceMalfunctionReportList;

		@JsonProperty("rows")
		@ApiModelProperty(value = "List of details for device details list search criteria")
		public List<DeviceMalfunctionReport> getDeviceMalfunctionReport() {
			return deviceMalfunctionReportList;
		}

		public void setDeviceMalfunctionReport(List<DeviceMalfunctionReport> deviceMalfunctionReportList) {
			this.deviceMalfunctionReportList = deviceMalfunctionReportList;
		}

	}


