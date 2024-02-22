package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.QuestionCategory;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionCategoryListResponse extends BaseResultCollection {
	private List<QuestionCategory> questionCategoryList;
	private List<QuestionCategory> questionCategories;

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of Question Category")
	public List<QuestionCategory> getQuestionCategoryList() {
		return questionCategoryList;
	}

	public void setQuestionCategoryList(List<QuestionCategory> questionCategoryList) {
		this.questionCategoryList = questionCategoryList;
	}

	public List<QuestionCategory> getQuestionCategories() {
		return questionCategories;
	}

	public void setQuestionCategories(List<QuestionCategory> questionCategories) {
		this.questionCategories = questionCategories;
	}

}
