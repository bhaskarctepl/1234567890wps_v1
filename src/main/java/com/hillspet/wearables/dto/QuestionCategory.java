package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionCategory {

	private Integer questionCategoryId;
	private String questionCategory;
	private Boolean isQualifyingCategory;

	private String description;
	private Integer isActive;
	private Boolean isEditAllowed;
	private Boolean isDeleteAllowed;

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

	public Boolean getIsQualifyingCategory() {
		return isQualifyingCategory;
	}

	public void setIsQualifyingCategory(Boolean isQualifyingCategory) {
		this.isQualifyingCategory = isQualifyingCategory;
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

	public Boolean getIsEditAllowed() {
		return isEditAllowed;
	}

	public void setIsEditAllowed(Boolean isEditAllowed) {
		this.isEditAllowed = isEditAllowed;
	}

	public Boolean getIsDeleteAllowed() {
		return isDeleteAllowed;
	}

	public void setIsDeleteAllowed(Boolean isDeleteAllowed) {
		this.isDeleteAllowed = isDeleteAllowed;
	}

}
