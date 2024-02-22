package com.hillspet.wearables.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contains all information that needed to create support materials", value = "FileUploadRequest")
@JsonInclude(value = Include.NON_NULL)
public class FileUploadRequest {

	@ApiModelProperty(value = "fileName", required = false)
	private String fileName;
	@ApiModelProperty(value = "gcpFileName", required = false)
	private String gcpFileName;
	@ApiModelProperty(value = "thumnailUrl", required = false)
	private String thumnailUrl;
	@ApiModelProperty(value = "thumnailUrl", required = false)
	private Integer isUpdated;
	
	

	public Integer getIsUpdated() {
		return isUpdated;
	}

	public void setIsUpdated(Integer isUpdated) {
		this.isUpdated = isUpdated;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getGcpFileName() {
		return gcpFileName;
	}

	public void setGcpFileName(String gcpFileName) {
		this.gcpFileName = gcpFileName;
	}

	public String getThumnailUrl() {
		return thumnailUrl;
	}

	public void setThumnailUrl(String thumnailUrl) {
		this.thumnailUrl = thumnailUrl;
	}

}
