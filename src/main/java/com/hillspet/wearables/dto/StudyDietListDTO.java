package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudyDietListDTO extends BaseDTO {
	private Integer studyDietId;
	private String dietName;
	private String dietNumber;
	private Integer dietId;
	
	

	public Integer getDietId() {
		return dietId;
	}

	public void setDietId(Integer dietId) {
		this.dietId = dietId;
	}

	public Integer getStudyDietId() {
		return studyDietId;
	}

	public void setStudyDietId(Integer studyDietId) {
		this.studyDietId = studyDietId;
	}

	public String getDietName() {
		return dietName;
	}

	public void setDietName(String dietName) {
		this.dietName = dietName;
	}

	public String getDietNumber() {
		return dietNumber;
	}

	public void setDietNumber(String dietNumber) {
		this.dietNumber = dietNumber;
	}

}
