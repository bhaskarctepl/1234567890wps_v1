package com.hillspet.wearables.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.hillspet.wearables.dto.StudyAsset;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Contains all the information that needed update device study mapping", value = "AssetStudyMappingRequest")
@JsonInclude(value = Include.NON_NULL)
public class AssetStudyMappingRequest {

	Integer studyId;
	String studyName;
	List<StudyAsset> assets;

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}

	public List<StudyAsset> getAssets() {
		return assets;
	}

	public void setAssets(List<StudyAsset> assets) {
		this.assets = assets;
	}

}
