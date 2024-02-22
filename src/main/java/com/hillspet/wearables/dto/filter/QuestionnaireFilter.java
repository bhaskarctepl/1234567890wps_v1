package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class QuestionnaireFilter extends BaseFilter {

	@ApiParam(name = "startDate", value = "Start Date is the first date component value of date range")
	@QueryParam("startDate")
	private String startDate;

	@ApiParam(name = "endDate", value = "End Date is the second date component value of date range")
	@QueryParam("endDate")
	private String endDate;
	
	@ApiParam(name = "questionnaireTypeId", value = "Search Questionnaire Type Id")
	@QueryParam("questionnaireTypeId")
	private String questionnaireTypeId;
	
	@ApiParam(name = "questionnaireLevelId", value = "Search Questionnaire Level Id")
	@QueryParam("questionnaireLevelId")
	private String questionnaireLevelId;
	
	

	public String getQuestionnaireLevelId() {
		return questionnaireLevelId;
	}

	public void setQuestionnaireLevelId(String questionnaireLevelId) {
		this.questionnaireLevelId = questionnaireLevelId;
	}

	public QuestionnaireFilter() {

	}

	public QuestionnaireFilter(String startDate, String endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
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

	public String getQuestionnaireTypeId() {
		return questionnaireTypeId;
	}

	public void setQuestionnaireTypeId(String questionnaireTypeId) {
		this.questionnaireTypeId = questionnaireTypeId;
	}

 
	
}
