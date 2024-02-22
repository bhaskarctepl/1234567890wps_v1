package com.hillspet.wearables.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contains all the information that needed to create Question Category", value = "QuestionCategoryRequest")
@JsonInclude(value = Include.NON_NULL)
public class QuestionCategoryRequest {

	@ApiModelProperty(value = "petName", required = true, example = "Beaumont")
	private Integer questionCategoryId;

	@ApiModelProperty(value = "questionCategory", required = true, example = "Health")
	private String questionCategory;

	@ApiModelProperty(value = "description", required = true, example = "Health")
	private String description;

	@ApiModelProperty(value = "isActive", required = true, example = "TRUE")
	private Integer isActive;

	public Integer getQuestionCategoryId() {
		return questionCategoryId;
	}

	public void setQuestionCategoryId(Integer questionCategoryId) {
		this.questionCategoryId = questionCategoryId;
	}

	public String getQuestionCategory() {
		return questionCategory;
	}

	public void setQuestionCategory(String questionCategory) {
		this.questionCategory = questionCategory;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

}
