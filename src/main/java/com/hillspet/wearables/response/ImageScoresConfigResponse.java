package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hillspet.wearables.objects.common.response.CommonResponse;
import com.hillspet.wearables.request.ImageScoringAssociated;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageScoresConfigResponse extends CommonResponse {

	private List<ImageScoringAssociated> imageScoringConfigs;

	public List<ImageScoringAssociated> getImageScoringConfigs() {
		return imageScoringConfigs;
	}

	public void setImageScoringConfigs(List<ImageScoringAssociated> imageScoringConfigs) {
		this.imageScoringConfigs = imageScoringConfigs;
	}

}
