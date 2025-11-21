package com.hillspet.wearables.response;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.QuestionnariesByStudy;


@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionnariesByStudyResponse {
	
	private List<QuestionnariesByStudy> QuestionnariesByStudy;

	public List<QuestionnariesByStudy> getQuestionnariesByStudy() {
		return QuestionnariesByStudy;
	}

	public void setQuestionnariesByStudy(List<QuestionnariesByStudy> questionnariesByStudy) {
		QuestionnariesByStudy = questionnariesByStudy;
	}

}
