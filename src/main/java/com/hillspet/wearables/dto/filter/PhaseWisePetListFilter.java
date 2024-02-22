package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

/**
 * @author yreddy
 *
 */
public class PhaseWisePetListFilter extends BaseFilter {

	
	@ApiParam(name = "studyId", value = "Pet Id for the study")
	@QueryParam("studyId")
	private int studyId;
	
	@ApiParam(name = "phaseId", value = "Pet Id for the study")
	@QueryParam("phaseId")
	private int phaseId;
	
	@ApiParam(name = "isImgNotRequired", value = "Is image is not required field.")
	@QueryParam("isImgNotRequired")
	private boolean isImgNotRequired;


	public boolean isImgNotRequired() {
		return isImgNotRequired;
	}

	public void setImgNotRequired(boolean isImgNotRequired) {
		this.isImgNotRequired = isImgNotRequired;
	}

	public int getStudyId() {
		return studyId;
	}

	public void setStudyId(int studyId) {
		this.studyId = studyId;
	}

	public int getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(int phaseId) {
		this.phaseId = phaseId;
	}

	


}
