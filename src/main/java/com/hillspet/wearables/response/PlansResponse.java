package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.PlanListDTO;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlansResponse extends BaseResultCollection {
	private List<PlanListDTO> plansList;
	private PlanListDTO planListDTO;
	public PlanListDTO getPlanListDTO() {
		return planListDTO;
	}

	public void setPlanListDTO(PlanListDTO planListDTO) {
		this.planListDTO = planListDTO;
	}

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for Plans based on search criteria")
	public List<PlanListDTO> getPlansList() {
		return plansList;
	}

	public void setPlansList(List<PlanListDTO> plansList) {
		this.plansList = plansList;
	}

}
