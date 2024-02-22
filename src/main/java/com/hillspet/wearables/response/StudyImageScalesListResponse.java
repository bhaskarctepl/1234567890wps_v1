package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.StudyImageScale;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudyImageScalesListResponse extends BaseResultCollection {

	private List<StudyImageScale> studyImageScaleList;

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for image scale mapped to a study based on search criteria")
	public List<StudyImageScale> getStudyImageScaleList() {
		return studyImageScaleList;
	}

	public void setStudyImageScaleList(List<StudyImageScale> studyImageScaleList) {
		this.studyImageScaleList = studyImageScaleList;
	}

}
