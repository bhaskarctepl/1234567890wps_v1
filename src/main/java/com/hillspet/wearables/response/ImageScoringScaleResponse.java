package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.ImageScoringResponse;
import com.hillspet.wearables.dto.ImageScoringScale;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageScoringScaleResponse extends BaseResultCollection {

	private ImageScoringScale imageScoringScale;
	private List<ImageScoringResponse> imageScoringList;

	@JsonProperty("rows")
	@ApiModelProperty(value = "Get the Study Phase Questionnaire Schedule based on search criteria")
	public List<ImageScoringResponse> getImageScoringList() {
		return imageScoringList;
	}

	public void setImageScoringList(List<ImageScoringResponse> imageScoringList) {
		this.imageScoringList = imageScoringList;
	}

	public void setImageScoringScale(ImageScoringScale imageScoringScale) {
		this.imageScoringScale = imageScoringScale;
	}

	@ApiModelProperty(value = "Get Image Scoring Scale by particular id")
	public ImageScoringScale getImageScoringScale() {
		return imageScoringScale;
	}
}
