package com.hillspet.wearables.dao.questionnaire;

import java.util.List;
import java.util.Map;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.CustomUserDetails;
import com.hillspet.wearables.dto.ExportQuestionnaireDTO;
import com.hillspet.wearables.dto.PetQuestionnaireResponse;
import com.hillspet.wearables.dto.Question;
import com.hillspet.wearables.dto.Questionnaire;
import com.hillspet.wearables.dto.QuestionnaireInstruction;
import com.hillspet.wearables.dto.QuestionnaireListDTO;
import com.hillspet.wearables.dto.QuestionnairePublishHistory;
import com.hillspet.wearables.dto.QuestionnaireResponseByStudyListDTO;
import com.hillspet.wearables.dto.QuestionnaireResponseListDTO;
import com.hillspet.wearables.dto.filter.QuestionnaireFilter;
import com.hillspet.wearables.dto.filter.QuestionnaireResponseByStudyFilter;
import com.hillspet.wearables.dto.filter.QuestionnaireResponseFilter;
import com.hillspet.wearables.request.QuestionnaireRequest;
import com.hillspet.wearables.request.QuestionnaireSkipRequest;
import com.hillspet.wearables.request.RepublishQuestionnaireRequest;
import com.hillspet.wearables.response.QuestionnaireExportDTO;
import com.hillspet.wearables.response.QuestionnaireViewResponse;

public interface QuestionnaireDao {

	Questionnaire addQuestionnaire(QuestionnaireRequest questionnaireRequest, Integer userId)
			throws ServiceExecutionException;

	Questionnaire updateQuestionnaire(QuestionnaireRequest questionnaireRequest, Integer userId)
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

	void republishQuestionnaire(RepublishQuestionnaireRequest republishQuestionnaireRequest, Integer userId)
			throws ServiceExecutionException;

	List<QuestionnairePublishHistory> getQuestionnairePublishHistory(int questionnaireId)
			throws ServiceExecutionException;

	String getCopyQuestionnaireName(String questionnaireName) throws ServiceExecutionException;

	Map<String, Integer> getExportsRequestedListCount(QuestionnaireFilter filter)  throws ServiceExecutionException;

	List<ExportQuestionnaireDTO> getExportsRequestedList(QuestionnaireFilter filter) throws ServiceExecutionException;

	ExportQuestionnaireDTO exportQuestionaire(String questionnaireIds, CustomUserDetails userDetails) throws ServiceExecutionException;

	List<QuestionnaireExportDTO> getQuestionnaireDetailsForExport(int questionnaireId) throws ServiceExecutionException;
	
	public boolean updateExportQuestionnaireFilePath(int exportId,String fileName, int userId) throws ServiceExecutionException;

	public String downloadQuestionnaire(int exportId) throws ServiceExecutionException;

	public Map<String, Integer> getQuestionnairesForExportCount(QuestionnaireFilter filter) throws ServiceExecutionException;
	
	List<QuestionnaireListDTO> getQuestionnairesForExport(QuestionnaireFilter filter) throws ServiceExecutionException;
}
