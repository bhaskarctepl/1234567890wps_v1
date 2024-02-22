package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.DeviceModel;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceModelResponse extends BaseResultCollection {

	private List<DeviceModel> DeviceModel;

	private List<DeviceModel> DeviceModelList;

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for all Device models based on given Asset Type")
	public List<DeviceModel> getDeviceModel() {
		return DeviceModel;
	}

	public void setDeviceModel(List<DeviceModel> deviceModel) {
		DeviceModel = deviceModel;
	}

	public List<DeviceModel> getDeviceModelList() {
		return DeviceModelList;
	}

	public void setDeviceModelList(List<DeviceModel> deviceModelList) {
		DeviceModelList = deviceModelList;
	}
}
