package com.hillspet.wearables.dao.questionnaire;

import java.util.List;
import java.util.Map;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.QuestionCategory;
import com.hillspet.wearables.dto.filter.QuestionCategoryFilter;
import com.hillspet.wearables.request.QuestionCategoryRequest;

public interface QuestionCategoryDao {

	public void addQuestionCategory(QuestionCategoryRequest questionCategoryRequest, int createdBy) throws ServiceExecutionException;

	public void updateQuestionCategory(QuestionCategoryRequest questionCategoryRequest, int modifiedBy)
			throws ServiceExecutionException;

	public void deleteQuestionCategory(int questionCategoryId, int modifiedBy) throws ServiceExecutionException;

	public QuestionCategory getQuestionCategoryById(int questionCategoryId) throws ServiceExecutionException;

	public List<QuestionCategory> getQuestionCategories() throws ServiceExecutionException;

	public Map<String, Integer> getQuestionCategoryCount(QuestionCategoryFilter filter)
			throws ServiceExecutionException;

	public List<QuestionCategory> getQuestionCategoryList(QuestionCategoryFilter filter)
			throws ServiceExecutionException;

}
