package com.hillspet.wearables.service.questionnaire;

import java.util.List;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.QuestionCategory;
import com.hillspet.wearables.dto.filter.QuestionCategoryFilter;
import com.hillspet.wearables.request.QuestionCategoryRequest;
import com.hillspet.wearables.response.QuestionCategoryListResponse;

public interface QuestionCategoryService {

	void addQuestionCategory(QuestionCategoryRequest questionCategoryRequest, int createdBy)
			throws ServiceExecutionException;

	void updateQuestionCategory(QuestionCategoryRequest questionCategoryRequest, int modifiedBy) throws ServiceExecutionException;

	void deleteQuestionCategory(int questionCategoryId, int modifiedBy) throws ServiceExecutionException;

	QuestionCategory getQuestionCategoryById(int questionCategoryId) throws ServiceExecutionException;

	List<QuestionCategory> getQuestionCategories() throws ServiceExecutionException;

	QuestionCategoryListResponse getQuestionCategoryList(QuestionCategoryFilter filter)
			throws ServiceExecutionException;

}
