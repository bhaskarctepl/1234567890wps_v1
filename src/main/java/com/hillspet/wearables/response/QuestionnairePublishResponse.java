package com.hillspet.wearables.response;

import java.util.List;

import com.hillspet.wearables.dto.QuestionnairePublishHistory;

public class QuestionnairePublishResponse {
	
	List<QuestionnairePublishHistory> publishHistory;

	public List<QuestionnairePublishHistory> getPublishHistory() {
		return publishHistory;
	}

	public void setPublishHistory(List<QuestionnairePublishHistory> publishHistory) {
		this.publishHistory = publishHistory;
	}

}
