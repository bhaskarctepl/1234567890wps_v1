/**
 * 
 */
package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.BulkAssetUploadDeviceInfo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author radepu
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BulkAssetUploadResponse extends BaseResultCollection {

	private List<BulkAssetUploadDeviceInfo> deviceInfoList;

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for all devices info that matches the search criteria")
	public List<BulkAssetUploadDeviceInfo> getDeviceInfoList() {
		return deviceInfoList;
	}

	public void setDeviceInfoList(List<BulkAssetUploadDeviceInfo> deviceInfoList) {
		this.deviceInfoList = deviceInfoList;
	}
}
