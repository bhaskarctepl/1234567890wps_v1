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

	@QueryParam("petName")
	@ApiParam(name = "petName", value = "Search pet by pet name", required = false)
	private String petName;

	@QueryParam("petParentName")
	@ApiParam(name = "petParentName", value = "Search pet by Pet Parent Name", required = false)
	private String petParentName;

	@QueryParam("petStudyActionId")
	@ApiParam(name = "petStudyActionId", value = "Search pet by Pet Study Action", required = false)
	private String petStudyActionId;

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

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public String getPetParentName() {
		return petParentName;
	}

	public void setPetParentName(String petParentName) {
		this.petParentName = petParentName;
	}

	public String getPetStudyActionId() {
		return petStudyActionId;
	}

	public void setPetStudyActionId(String petStudyActionId) {
		this.petStudyActionId = petStudyActionId;
	}

}
