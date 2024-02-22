package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hillspet.wearables.dto.QuestionValidityPeriod;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionValidityPeriodResponse extends BaseResultCollection {
	private List<QuestionValidityPeriod> questionValidityPeriods;

	@ApiModelProperty(value = "List of Question Validity Periods")
	public List<QuestionValidityPeriod> getQuestionValidityPeriods() {
		return questionValidityPeriods;
	}

	public void setQuestionValidityPeriods(List<QuestionValidityPeriod> questionValidityPeriods) {
		this.questionValidityPeriods = questionValidityPeriods;
	}

}
