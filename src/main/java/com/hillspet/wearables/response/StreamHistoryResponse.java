package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.StreamDevice;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StreamHistoryResponse extends BaseResultCollection {

	private List<StreamDevice> streamDevices;

	@JsonProperty("rows")
	@ApiModelProperty(value = "Get Stream Device History by stream id")
	public List<StreamDevice> getStreamDevices() {
		return streamDevices;
	}

	public void setStreamDevices(List<StreamDevice> streamDevices) {
		this.streamDevices = streamDevices;
	}

}
