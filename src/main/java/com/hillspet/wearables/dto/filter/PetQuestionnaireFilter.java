package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class PetQuestionnaireFilter extends BaseFilter {

	@ApiParam(name = "petQuestionnaireConfigId", value = "Filter based on pet Questionnaire Config Id drop down")
	@QueryParam("petQuestionnaireConfigId")
	private String petQuestionnaireConfigId;

	@ApiParam(name = "occurrenceId", value = "Filter based on occurrence Id drop down")
	@QueryParam("occurrenceId")
	private String occurrenceId;

	@ApiParam(name = "frequencyId", value = "filter based on frequency Id drop down")
	@QueryParam("frequencyId")
	private String frequencyId;

	@ApiParam(name = "startDate", value = "Start Date is the first date component value of date range")
	@QueryParam("startDate")
	private String startDate;

	@ApiParam(name = "endDate", value = "End Date is the second date component value of date range")
	@QueryParam("endDate")
	private String endDate;

	@ApiParam(name = "status", value = "Filter based on status drop down")
	@QueryParam("status")
	private String status;

	public String getPetQuestionnaireConfigId() {
		return petQuestionnaireConfigId;
	}

	public void setPetQuestionnaireConfigId(String petQuestionnaireConfigId) {
		this.petQuestionnaireConfigId = petQuestionnaireConfigId;
	}

	public String getOccurrenceId() {
		return occurrenceId;
	}

	public void setOccurrenceId(String occurrenceId) {
		this.occurrenceId = occurrenceId;
	}

	public String getFrequencyId() {
		return frequencyId;
	}

	public void setFrequencyId(String frequencyId) {
		this.frequencyId = frequencyId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
