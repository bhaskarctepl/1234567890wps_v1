package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hillspet.wearables.dto.PreludeDataByStudyDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PreludeDataByStudyResponse {

	private List<PreludeDataByStudyDTO> preludeDataByStudy;
	private List<PreludeDataByStudyDTO> preludeDataByStudyList;
	public List<PreludeDataByStudyDTO> getPreludeDataByStudy() {
		return preludeDataByStudy;
	}
	public void setPreludeDataByStudy(List<PreludeDataByStudyDTO> preludeDataByStudy) {
		this.preludeDataByStudy = preludeDataByStudy;
	}
	public List<PreludeDataByStudyDTO> getPreludeDataByStudyList() {
		return preludeDataByStudyList;
	}
	public void setPreludeDataByStudyList(List<PreludeDataByStudyDTO> preludeDataByStudyList) {
		this.preludeDataByStudyList = preludeDataByStudyList;
	}
	
	
}
