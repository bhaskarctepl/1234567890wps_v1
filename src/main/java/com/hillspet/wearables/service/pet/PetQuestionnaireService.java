package com.hillspet.wearables.service.pet;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.PetSchedQuestionnaire;
import com.hillspet.wearables.dto.filter.PetFilter;
import com.hillspet.wearables.dto.filter.PetQuestionnaireFilter;
import com.hillspet.wearables.request.NotificationRequest;
import com.hillspet.wearables.request.PetQuestionnaireRequest;
import com.hillspet.wearables.response.PetQuestionnaireReccurrenceResponse;
import com.hillspet.wearables.response.PetSchedQuestionnaireListResponse;
import com.hillspet.wearables.response.PetsResponse;
import com.hillspet.wearables.response.QuestionnaireViewResponse;

public interface PetQuestionnaireService {

	PetsResponse getPetsToSchedQuestionnaire(PetFilter filter) throws ServiceExecutionException;

	void addPetQuestionnaire(PetQuestionnaireRequest petQuestionnaireRequest) throws ServiceExecutionException;

	PetSchedQuestionnaireListResponse getPetSchedQuestionnaireList(PetQuestionnaireFilter filter)
			throws ServiceExecutionException;

	PetQuestionnaireReccurrenceResponse getPetQuestionnaireListByConfigId(PetQuestionnaireFilter filter)
			throws ServiceExecutionException;

	PetSchedQuestionnaire getPetQuestionnaireByConfigId(int petQuestionnaireConfigId) throws ServiceExecutionException;

	void deletePetQuestionnaire(int petQuestionnaireConfigId, int modifiedBy) throws ServiceExecutionException;

	QuestionnaireViewResponse getQuestionnaireResponse(int petQuestionnaireConfigId, int questionnaireResponseId);

	void sendNotification(NotificationRequest notificationRequest);

}
