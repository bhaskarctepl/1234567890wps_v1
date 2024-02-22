package com.hillspet.wearables.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Contains all the information that needed to download Media", value = "Download Media Request")
@JsonInclude(value = Include.NON_NULL)
public class DownloadMediaRequest {

	public DownloadMediaRequest() {
		// TODO Auto-generated constructor stub
	}

	private String mediaUrl;
	private String extension;

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

}
