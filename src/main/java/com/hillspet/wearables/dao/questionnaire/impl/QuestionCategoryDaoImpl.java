package com.hillspet.wearables.dao.questionnaire.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillspet.wearables.common.constants.SQLConstants;
import com.hillspet.wearables.common.constants.WearablesErrorCode;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.BaseDaoImpl;
import com.hillspet.wearables.dao.questionnaire.QuestionCategoryDao;
import com.hillspet.wearables.dto.QuestionCategory;
import com.hillspet.wearables.dto.filter.QuestionCategoryFilter;
import com.hillspet.wearables.request.QuestionCategoryRequest;

@Repository
public class QuestionCategoryDaoImpl extends BaseDaoImpl implements QuestionCategoryDao {

	private static final Logger LOGGER = LogManager.getLogger(QuestionCategoryDaoImpl.class);

	@Autowired
	private ObjectMapper mapper;

	@Override
	public void addQuestionCategory(QuestionCategoryRequest questionCategoryRequest, int createdBy)
			throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_question_category", questionCategoryRequest.getQuestionCategory());
		inputParams.put("p_description", questionCategoryRequest.getDescription());
		inputParams.put("p_created_by", createdBy);
		inputParams.put("p_is_active", questionCategoryRequest.getIsActive());
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.QUESTION_CATEGORY_INSERT, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (!(StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO)) {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"addQuestionCategory service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.QUESTIONNAIRE_CATEGORY_ALREADY_EXISTS,
									questionCategoryRequest.getQuestionCategory())));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public void updateQuestionCategory(QuestionCategoryRequest questionCategoryRequest, int modifiedBy)
			throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_question_category_id", questionCategoryRequest.getQuestionCategoryId());
		inputParams.put("p_question_category", questionCategoryRequest.getQuestionCategory());
		inputParams.put("p_description", questionCategoryRequest.getDescription());
		inputParams.put("p_modified_by", modifiedBy);
		inputParams.put("p_is_active", questionCategoryRequest.getIsActive());
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.QUESTION_CATEGORY_UPDATE, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isNotEmpty(errorMsg) || statusFlag < NumberUtils.INTEGER_ONE) {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"addQuestionCategory service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.QUESTIONNAIRE_CATEGORY_ALREADY_EXISTS,
									questionCategoryRequest.getQuestionCategory())));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			throw new ServiceExecutionException(e.getMessage());
		}

	}

	@Override
	public void deleteQuestionCategory(int questionCategoryId, int modifiedBy) throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_question_category_id", questionCategoryId);
		inputParams.put("p_modified_by", modifiedBy);
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.QUESTION_CATEGORY_DELETE, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isNotEmpty(errorMsg) || statusFlag < NumberUtils.INTEGER_ONE) {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"deleteQuestionCategory service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.QUESTIONNAIRE_CATEGORY_ALREADY_MAPPED,
									errorMsg)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing deleteQuestionCategory ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public QuestionCategory getQuestionCategoryById(int questionCategoryId) throws ServiceExecutionException {
		QuestionCategory questionCategory = new QuestionCategory();
		LOGGER.debug("getQuestionCategoryById called");
		try {
			jdbcTemplate.query(SQLConstants.QUESTION_CATEGORY_GET_BY_ID, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					questionCategory.setQuestionCategoryId(rs.getInt("QUESTION_CATEGORY_ID"));
					questionCategory.setQuestionCategory(rs.getString("QUESTION_CATEGORY"));
					questionCategory.setDescription(rs.getString("DESCRIPTION"));
					questionCategory.setIsActive(rs.getInt("IS_ACTIVE"));
				}
			}, questionCategoryId);

		} catch (Exception e) {
			LOGGER.error("error while fetching getQuestionCategoryById", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return questionCategory;
	}

	@Override
	public List<QuestionCategory> getQuestionCategories() throws ServiceExecutionException {
		List<QuestionCategory> questionCategories = new ArrayList<>();
		LOGGER.debug("getQuestionCategories called");
		try {
			jdbcTemplate.query(SQLConstants.QUESTION_CATEGORY_GET_ALL_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					QuestionCategory questionCategory = new QuestionCategory();
					questionCategory.setQuestionCategoryId(rs.getInt("QUESTION_CATEGORY_ID"));
					questionCategory.setQuestionCategory(rs.getString("QUESTION_CATEGORY"));
					questionCategory.setDescription(rs.getString("DESCRIPTION"));
					questionCategory.setIsActive(rs.getInt("IS_ACTIVE"));
					questionCategories.add(questionCategory);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getQuestionCategories ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return questionCategories;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getQuestionCategoryCount(QuestionCategoryFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getQuestionCategoryCount called");
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		try {
			counts = selectForObject(SQLConstants.QUESTION_CATEGORY_GET_LIST_COUNT, String.class,
					filter.getSearchText(), filter.getStatusId());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getQuestionCategoryCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<QuestionCategory> getQuestionCategoryList(QuestionCategoryFilter filter)
			throws ServiceExecutionException {
		List<QuestionCategory> questionCategories = new ArrayList<>();
		LOGGER.debug("getQuestionCategoryList called");
		try {
			jdbcTemplate.query(SQLConstants.QUESTION_CATEGORY_GET_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					QuestionCategory questionCategory = new QuestionCategory();
					questionCategory.setQuestionCategoryId(rs.getInt("QUESTION_CATEGORY_ID"));
					questionCategory.setQuestionCategory(rs.getString("QUESTION_CATEGORY"));
					questionCategory.setDescription(rs.getString("DESCRIPTION"));
					questionCategory.setIsActive(rs.getInt("IS_ACTIVE"));
					questionCategory.setIsEditAllowed(
							rs.getInt("RESPONDED_COUNT") > NumberUtils.INTEGER_ZERO ? Boolean.FALSE : Boolean.TRUE);
					questionCategory.setIsDeleteAllowed(
							rs.getInt("MAPPED_COUNT") > NumberUtils.INTEGER_ZERO ? Boolean.FALSE : Boolean.TRUE);
					questionCategories.add(questionCategory);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getStatusId());

		} catch (Exception e) {
			LOGGER.error("error while fetching getQuestionCategoryList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return questionCategories;
	}

}
