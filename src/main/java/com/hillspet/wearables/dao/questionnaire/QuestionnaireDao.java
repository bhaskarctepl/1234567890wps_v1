package com.hillspet.wearables.dao.questionnaire;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.PetQuestionnaireResponse;
import com.hillspet.wearables.dto.Question;
import com.hillspet.wearables.dto.Questionnaire;
import com.hillspet.wearables.dto.QuestionnaireInstruction;
import com.hillspet.wearables.dto.QuestionnaireListDTO;
import com.hillspet.wearables.dto.QuestionnaireResponseByStudyListDTO;
import com.hillspet.wearables.dto.QuestionnaireResponseListDTO;
import com.hillspet.wearables.dto.filter.QuestionnaireFilter;
import com.hillspet.wearables.dto.filter.QuestionnaireResponseByStudyFilter;
import com.hillspet.wearables.dto.filter.QuestionnaireResponseFilter;
import com.hillspet.wearables.request.QuestionnaireRequest;
import com.hillspet.wearables.request.QuestionnaireSkipRequest;
import com.hillspet.wearables.response.QuestionnaireViewResponse;

public interface QuestionnaireDao {

	Integer addQuestionnaire(QuestionnaireRequest questionnaireRequest, Integer userId)
			throws ServiceExecutionException;

	void updateQuestionnaire(QuestionnaireRequest questionnaireRequest, Integer userId)
			throws ServiceExecutionException;

	void addNewInstruction(int questionnaireId, Integer userId, QuestionnaireInstruction instruction)
			throws ServiceExecutionException;

	void deleteInstruction(int questionnaireId, Integer userId, int instructionId) throws ServiceExecutionException;

	void updateInstruction(int questionnaireId, Integer userId, QuestionnaireInstruction instruction)
			throws ServiceExecutionException;

	void addNewQuestion(int questionnaireId, Integer userId, Question question) throws ServiceExecutionException;

	void deleteQuestion(int questionnaireId, Integer userId, int questionId) throws ServiceExecutionException;

	void updateQuestion(int questionnaireId, Integer userId, Question question) throws ServiceExecutionException;

	Questionnaire getQuestionnaireById(int questionnaireId) throws ServiceExecutionException;

	Map<String, Integer> getQuestionnairesCount(QuestionnaireFilter filter) throws ServiceExecutionException;

	List<QuestionnaireListDTO> getQuestionnaires(QuestionnaireFilter filter) throws ServiceExecutionException;

	void deleteQuestionnaire(int questionnaireId, int modifiedBy) throws ServiceExecutionException;

	List<QuestionnaireListDTO> getActiveQuestionnaires() throws ServiceExecutionException;

	List<QuestionnaireResponseListDTO> getQuestionnaireResponseList(QuestionnaireResponseFilter filter)
			throws ServiceExecutionException;

	List<QuestionnaireResponseByStudyListDTO> getQuestionnaireResponseByStudyList(
			QuestionnaireResponseByStudyFilter filter) throws ServiceExecutionException;

	Map<String, Integer> getQuestionnaireResponseCount(QuestionnaireResponseFilter filter)
			throws ServiceExecutionException;

	Map<String, Integer> getQuestionnaireResponseByStudyCount(QuestionnaireResponseByStudyFilter filter)
			throws ServiceExecutionException;

	QuestionnaireViewResponse getViewQuestionnaireResponse(int questionnaireResponseId, int studyId)
			throws ServiceExecutionException;

	Map<String, Integer> getQuestionnaireResponseByPetCount(QuestionnaireResponseFilter filter)
			throws ServiceExecutionException;

	List<PetQuestionnaireResponse> getQuestionnaireResponseByPet(QuestionnaireResponseFilter filter)
			throws ServiceExecutionException;

	void implementSkipOnQuestionnaire(QuestionnaireSkipRequest questionnaireSkipRequest, int modifiedBy)
			throws ServiceExecutionException;

}
