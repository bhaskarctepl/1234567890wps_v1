package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageScoringType {
	private Integer imageScoringTypeId;
	private String imageScoringType;

	public Integer getImageScoringTypeId() {
		return imageScoringTypeId;
	}

	public void setImageScoringTypeId(Integer imageScoringTypeId) {
		this.imageScoringTypeId = imageScoringTypeId;
	}

	public String getImageScoringType() {
		return imageScoringType;
	}

	public void setImageScoringType(String imageScoringType) {
		this.imageScoringType = imageScoringType;
	}

}
