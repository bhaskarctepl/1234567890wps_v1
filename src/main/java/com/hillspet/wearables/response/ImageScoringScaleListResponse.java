package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.ImageScoringScale;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageScoringScaleListResponse extends BaseResultCollection {

	private List<ImageScoringScale> imageScoringScales;
	private List<ImageScoringScale> imageScoringScaleList;

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for Image Scoring Scales based on search criteria")
	public List<ImageScoringScale> getImageScoringScales() {
		return imageScoringScales;
	}

	public void setImageScoringScales(List<ImageScoringScale> imageScoringScales) {
		this.imageScoringScales = imageScoringScales;
	}

	public List<ImageScoringScale> getImageScoringScaleList() {
		return imageScoringScaleList;
	}

	public void setImageScoringScaleList(List<ImageScoringScale> imageScoringScaleList) {
		this.imageScoringScaleList = imageScoringScaleList;
	}

}
