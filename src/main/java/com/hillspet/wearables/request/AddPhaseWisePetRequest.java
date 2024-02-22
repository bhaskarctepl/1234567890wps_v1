package com.hillspet.wearables.request;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.hillspet.wearables.dto.PetName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author yreddy
 *
 *
 */
@ApiModel(description = "Contains all information that needed to create Phase wise Pet", value = "AddPhaseWisePetRequest")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
public class AddPhaseWisePetRequest {

	@ApiModelProperty(value = "studyId", required = true, example = "12")
	private Integer studyId;

 
	@ApiModelProperty(value = "fromDate", required = true, example = "2022-01-10")
	private LocalDate fromDate;

	@ApiModelProperty(value = "PetList", required = true)
	private List<PetName> petList;
	

	@ApiModelProperty(value = "treatmentGroupId", required = false, example = "1")
	private Integer treatmentGroupId;
	
	@ApiModelProperty(value = "phaseId", required = true, example = "1")
	private Integer phaseId;
	
	@ApiModelProperty(value = "phaseDay", required = true, example = "1")
	private Integer phaseDay;
	
	@ApiModelProperty(value = "reason", required = true, example = "Reason")
	private String reason;
	
	@ApiModelProperty(value = "days", required = true, example = "Days")
	private Integer days;
	

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	} 

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	

	 

	public Integer getTreatmentGroupId() {
		return treatmentGroupId;
	}

	public void setTreatmentGroupId(Integer treatmentGroupId) {
		this.treatmentGroupId = treatmentGroupId;
	}

	public Integer getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(Integer phaseId) {
		this.phaseId = phaseId;
	}

	public Integer getPhaseDay() {
		return phaseDay;
	}

	public void setPhaseDay(Integer phaseDay) {
		this.phaseDay = phaseDay;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public List<PetName> getPetList() {
		return petList;
	}

	public void setPetList(List<PetName> petList) {
		this.petList = petList;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}
	

}
