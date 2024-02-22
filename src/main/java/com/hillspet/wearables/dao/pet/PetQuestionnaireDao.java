package com.hillspet.wearables.dao.pet;

import java.util.List;
import java.util.Map;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.PetListDTO;
import com.hillspet.wearables.dto.PetParent;
import com.hillspet.wearables.dto.PetQuestionnaireReccurrence;
import com.hillspet.wearables.dto.PetSchedQuestionnaire;
import com.hillspet.wearables.dto.filter.PetFilter;
import com.hillspet.wearables.dto.filter.PetQuestionnaireFilter;
import com.hillspet.wearables.request.PetQuestionnaireRequest;
import com.hillspet.wearables.response.QuestionnaireViewResponse;

public interface PetQuestionnaireDao {

	Map<String, Integer> getPetsToSchedQuestionnaireCount(PetFilter filter) throws ServiceExecutionException;

	List<PetListDTO> getPetsToSchedQuestionnaire(PetFilter filter) throws ServiceExecutionException;

	void addPetQuestionnaire(PetQuestionnaireRequest petQuestionnaireRequest) throws ServiceExecutionException;

	Map<String, Integer> getPetSchedQuestionnaireCount(PetQuestionnaireFilter filter) throws ServiceExecutionException;

	List<PetSchedQuestionnaire> getPetSchedQuestionnaireList(PetQuestionnaireFilter filter)
			throws ServiceExecutionException;

	Map<String, Integer> getPetQuestionnaireListByConfigCount(PetQuestionnaireFilter filter)
			throws ServiceExecutionException;

	List<PetQuestionnaireReccurrence> getPetQuestionnaireListByConfigId(PetQuestionnaireFilter filter)
			throws ServiceExecutionException;

	PetSchedQuestionnaire getPetQuestionnaireByConfigId(int petQuestionnaireConfigId) throws ServiceExecutionException;

	void deletePetQuestionnaire(int petQuestionnaireConfigId, int modifiedBy) throws ServiceExecutionException;

	QuestionnaireViewResponse getQuestionnaireResponse(int petQuestionnaireConfigId, int questionnaireResponseId);

	List<PetParent> getPetParentsByPetId(int petId);

}
