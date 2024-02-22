package com.hillspet.wearables.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contains all information that needed to create support materials", value = "FAQRequest")
@JsonInclude(value = Include.NON_NULL)
public class FAQRequest {
	
	@ApiModelProperty(value = "faqId", required = false)
	private Integer faqId;
	
	@ApiModelProperty(value = "faqQuestion", required = false)
	private String faqQuestion;
	
	@ApiModelProperty(value = "faqAnswer", required = false)
	private String faqAnswer;
	
	@ApiModelProperty(value = "isUpdated", required = false)
	private Integer isUpdated;
	
	
	
	
	public Integer getIsUpdated() {
		return isUpdated;
	}
	public void setIsUpdated(Integer isUpdated) {
		this.isUpdated = isUpdated;
	}
	public Integer getFaqId() {
		return faqId;
	}
	public void setFaqId(Integer faqId) {
		this.faqId = faqId;
	}
	public String getFaqQuestion() {
		return faqQuestion;
	}
	public void setFaqQuestion(String faqQuestion) {
		this.faqQuestion = faqQuestion;
	}
	public String getFaqAnswer() {
		return faqAnswer;
	}
	public void setFaqAnswer(String faqAnswer) {
		this.faqAnswer = faqAnswer;
	}

	

}
