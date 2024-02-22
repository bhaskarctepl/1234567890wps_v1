package com.hillspet.wearables.service.questionnaire;

import javax.validation.Valid;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.Question;
import com.hillspet.wearables.dto.QuestionnaireInstruction;
import com.hillspet.wearables.dto.filter.QuestionnaireFilter;
import com.hillspet.wearables.dto.filter.QuestionnaireResponseByStudyFilter;
import com.hillspet.wearables.dto.filter.QuestionnaireResponseFilter;
import com.hillspet.wearables.request.QuestionnaireRequest;
import com.hillspet.wearables.request.QuestionnaireSkipRequest;
import com.hillspet.wearables.response.PetQuestionnaireResponseList;
import com.hillspet.wearables.response.QuestionnaireListResponse;
import com.hillspet.wearables.response.QuestionnaireResponse;
import com.hillspet.wearables.response.QuestionnaireResponseByStudyListResponse;
import com.hillspet.wearables.response.QuestionnaireResponseListResponse;
import com.hillspet.wearables.response.QuestionnaireViewResponse;

public interface QuestionnaireService {

	Integer addQuestionnaire(QuestionnaireRequest questionnaireRequest, Integer userId)
			throws ServiceExecutionException;

	void updateQuestionnaire(QuestionnaireRequest questionnaireRequest, Integer userId)
			throws ServiceExecutionException;

	QuestionnaireResponse getQuestionnaireById(int questionnaireId) throws ServiceExecutionException;

	QuestionnaireListResponse getQuestionnaires(QuestionnaireFilter filter) throws ServiceExecutionException;

	void deleteQuestionnaire(int questionnaireId, int modifiedBy) throws ServiceExecutionException;

	void addNewInstruction(int questionnaireId, Integer userId, QuestionnaireInstruction instruction)
			throws ServiceExecutionException;

	void updateInstruction(int questionnaireId, Integer userId, QuestionnaireInstruction instruction)
			throws ServiceExecutionException;

	void deleteInstruction(int questionnaireId, Integer userId, int instructionId) throws ServiceExecutionException;

	void addNewQuestion(int questionnaireId, Integer userId, Question question) throws ServiceExecutionException;

	void updateQuestion(int questionnaireId, Integer userId, Question question) throws ServiceExecutionException;

	void deleteQuestion(int questionnaireId, Integer userId, int questionId) throws ServiceExecutionException;

	QuestionnaireListResponse getActiveQuestionnaires() throws ServiceExecutionException;

	QuestionnaireResponseListResponse getQuestionnaireResponseList(QuestionnaireResponseFilter filter)
			throws ServiceExecutionException;

	public QuestionnaireResponseByStudyListResponse getQuestionnaireResponseByStudyList(
			QuestionnaireResponseByStudyFilter filter) throws ServiceExecutionException;

	public QuestionnaireViewResponse getViewQuestionnaireResponse(int questionnaireResponseId, int studyId)
			throws ServiceExecutionException;

	public PetQuestionnaireResponseList getQuestionnaireResponseByPet(QuestionnaireResponseFilter filter)
			throws ServiceExecutionException;

	public void implementSkipOnQuestionnaire(QuestionnaireSkipRequest questionnaireSkipRequest, int modifiedBy)
			throws ServiceExecutionException;
}
