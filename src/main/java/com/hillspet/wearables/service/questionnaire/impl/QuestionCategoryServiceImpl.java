package com.hillspet.wearables.service.questionnaire.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.questionnaire.QuestionCategoryDao;
import com.hillspet.wearables.dto.QuestionCategory;
import com.hillspet.wearables.dto.filter.QuestionCategoryFilter;
import com.hillspet.wearables.request.QuestionCategoryRequest;
import com.hillspet.wearables.response.QuestionCategoryListResponse;
import com.hillspet.wearables.service.questionnaire.QuestionCategoryService;

@Service
public class QuestionCategoryServiceImpl implements QuestionCategoryService {

	private static final Logger LOGGER = LogManager.getLogger(QuestionCategoryServiceImpl.class);

	@Autowired
	private QuestionCategoryDao questionCategoryDao;

	@Override
	public void addQuestionCategory(QuestionCategoryRequest questionCategoryRequest, int createdBy) throws ServiceExecutionException {
		LOGGER.debug("addQuestionCategory called");
		questionCategoryDao.addQuestionCategory(questionCategoryRequest, createdBy);
		LOGGER.debug("addQuestionCategory called");
	}

	@Override
	public void updateQuestionCategory(QuestionCategoryRequest questionCategoryRequest, int modifiedBy)
			throws ServiceExecutionException {
		LOGGER.info("updateQuestionCategory called");
		questionCategoryDao.updateQuestionCategory(questionCategoryRequest, modifiedBy);
		LOGGER.info("updateQuestionCategory completed successfully");
	}

	@Override
	public void deleteQuestionCategory(int questionCategoryId, int modifiedBy) throws ServiceExecutionException {
		LOGGER.info("deleteQuestionCategory called");
		questionCategoryDao.deleteQuestionCategory(questionCategoryId, modifiedBy);
	}

	@Override
	public QuestionCategory getQuestionCategoryById(int questionCategoryId) throws ServiceExecutionException {
		LOGGER.info("getQuestionCategoryById called");
		return questionCategoryDao.getQuestionCategoryById(questionCategoryId);
	}

	@Override
	public List<QuestionCategory> getQuestionCategories() throws ServiceExecutionException {
		LOGGER.debug("getQuestionCategories called");
		List<QuestionCategory> questionCategories = questionCategoryDao.getQuestionCategories();
		LOGGER.debug("getQuestionCategories list", questionCategories);
		return questionCategories;
	}

	@Override
	public QuestionCategoryListResponse getQuestionCategoryList(QuestionCategoryFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getQuestionCategoryList called");
		Map<String, Integer> mapper = questionCategoryDao.getQuestionCategoryCount(filter);
		int total = mapper.get("count");
		int totalCount = mapper.get("totalCount");
		List<QuestionCategory> questionCategoryList = total > 0 ? questionCategoryDao.getQuestionCategoryList(filter)
				: new ArrayList<>();
		QuestionCategoryListResponse response = new QuestionCategoryListResponse();
		response.setQuestionCategoryList(questionCategoryList);
		response.setNoOfElements(questionCategoryList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);
		LOGGER.debug("getQuestionCategoryList Question Category count is {}", questionCategoryList);
		LOGGER.debug("getQuestionCategoryList completed successfully");
		return response;
	}

}
