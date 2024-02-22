package com.hillspet.wearables.dto;

import io.swagger.annotations.ApiModelProperty;

public class StudyTreatmentGroup {

	@ApiModelProperty(value = "treamentGroupId", required = false)
	private Integer treamentGroupId;

	@ApiModelProperty(value = "groupName", required = true)
	private String groupName;

	@ApiModelProperty(value = "description", required = false)
	private String description;

	@ApiModelProperty(value = "minPets", required = false)
	private Integer minPets;

	@ApiModelProperty(value = "maxPets", required = true)
	private Integer maxPets;

	public Integer getTreamentGroupId() {
		return treamentGroupId;
	}

	public void setTreamentGroupId(Integer treamentGroupId) {
		this.treamentGroupId = treamentGroupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getMinPets() {
		return minPets;
	}

	public void setMinPets(Integer minPets) {
		this.minPets = minPets;
	}

	public Integer getMaxPets() {
		return maxPets;
	}

	public void setMaxPets(Integer maxPets) {
		this.maxPets = maxPets;
	}

}
