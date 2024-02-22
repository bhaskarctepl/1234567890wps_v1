package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class PreludeReportFilter extends BaseFilter  {

	@ApiParam(name = "preludeStudyId", value = "Study id")
	@QueryParam("preludeStudyId")
	private Integer preludeStudyId;

	@ApiParam(name = "externalFileCategory", value = "File Category")
	@QueryParam("externalFileCategory")
	private String externalFileCategory;

	public Integer getPreludeStudyId() {
		return preludeStudyId;
	}

	public void setPreludeStudyId(Integer preludeStudyId) {
		this.preludeStudyId = preludeStudyId;
	}

	public String getExternalFileCategory() {
		return externalFileCategory;
	}

	public void setExternalFileCategory(String externalFileCategory) {
		this.externalFileCategory = externalFileCategory;
	}
}
