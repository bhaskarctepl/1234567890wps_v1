package com.hillspet.wearables.dao.questionnaire.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillspet.wearables.common.constants.Constants;
import com.hillspet.wearables.common.constants.SQLConstants;
import com.hillspet.wearables.common.constants.WearablesErrorCode;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.common.utils.GCPClientUtil;
import com.hillspet.wearables.common.utils.GCStorageUtil;
import com.hillspet.wearables.dao.BaseDaoImpl;
import com.hillspet.wearables.dao.questionnaire.QuestionnaireDao;
import com.hillspet.wearables.dto.CustomUserDetails;
import com.hillspet.wearables.dto.ExportQuestionnaireDTO;
import com.hillspet.wearables.dto.PetQuestionnaireResponse;
import com.hillspet.wearables.dto.Question;
import com.hillspet.wearables.dto.QuestionAnswer;
import com.hillspet.wearables.dto.QuestionAnswerOption;
import com.hillspet.wearables.dto.QuestionCategory;
import com.hillspet.wearables.dto.QuestionSliderType;
import com.hillspet.wearables.dto.Questionnaire;
import com.hillspet.wearables.dto.QuestionnaireDetailsDTO;
import com.hillspet.wearables.dto.QuestionnaireInstruction;
import com.hillspet.wearables.dto.QuestionnaireListDTO;
import com.hillspet.wearables.dto.QuestionnairePublishHistory;
import com.hillspet.wearables.dto.QuestionnaireResponseByStudyListDTO;
import com.hillspet.wearables.dto.QuestionnaireResponseDTO;
import com.hillspet.wearables.dto.QuestionnaireResponseListDTO;
import com.hillspet.wearables.dto.QuestionnaireSection;
import com.hillspet.wearables.dto.filter.QuestionnaireFilter;
import com.hillspet.wearables.dto.filter.QuestionnaireResponseByStudyFilter;
import com.hillspet.wearables.dto.filter.QuestionnaireResponseFilter;
import com.hillspet.wearables.request.QuestionnaireRequest;
import com.hillspet.wearables.request.QuestionnaireSkipRequest;
import com.hillspet.wearables.request.RepublishQuestionnaireRequest;
import com.hillspet.wearables.response.QuestionnaireExportDTO;
import com.hillspet.wearables.response.QuestionnaireViewResponse;

@Repository
public class QuestionnaireDaoImpl extends BaseDaoImpl implements QuestionnaireDao {

	private static final Logger LOGGER = LogManager.getLogger(QuestionnaireDaoImpl.class);

	@Autowired
	private GCPClientUtil gcpClientUtil;

	@Autowired
	private GCStorageUtil gcStorageUtil;

	@Autowired
	private ObjectMapper mapper;

	@Override
	public Questionnaire addQuestionnaire(QuestionnaireRequest questionnaireRequest, Integer userId)
			throws ServiceExecutionException {
		Integer questionnaireId = 0;
		Questionnaire questionnaire = questionnaireRequest.getQuestionnaire();
		try {
			Map<String, Object> inputParams = new HashMap<>();

			inputParams.put("p_questionnaire_name", questionnaire.getQuestionnaireName());
			inputParams.put("p_start_date", questionnaire.getStartDate());
			inputParams.put("p_end_date", questionnaire.getEndDate());
			inputParams.put("p_is_published",
					questionnaire.getIsPublished() != null ? questionnaire.getIsPublished() : 0);
			inputParams.put("p_created_by", userId);

			inputParams.put("p_question_json", mapper.writeValueAsString(questionnaire.getQuestions()));

			if (questionnaire.getInstructions() != null && questionnaire.getInstructions().size() > 0) {
				inputParams.put("p_instruction_json", mapper.writeValueAsString(questionnaire.getInstructions()));
			} else {
				inputParams.put("p_instruction_json", null);
			}

			inputParams.put("p_questionnaire_type_id", questionnaire.getQuestionnaireTypeId());
			inputParams.put("p_questionnaire_category_id", questionnaire.getQuestionnaireCategoryId());
			inputParams.put("p_questionnaire_level_id", questionnaire.getQuestionnaireLevelId());

			inputParams.put("p_questionnaire_banner_image", questionnaire.getQuestionnaireImageName());

			if (questionnaire.getSections() != null && questionnaire.getSections().size() > 0) {
				inputParams.put("p_section_json", mapper.writeValueAsString(questionnaire.getSections()));
			} else {
				inputParams.put("p_section_json", null);
			}

			inputParams.put("p_parent_questionnaire_id", questionnaire.getParentQuestionnaireId());

			LOGGER.info("addQuestionnaire inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.QUESTIONNAIRE_INSERT, inputParams);
			LOGGER.info("addQuestionnaire outParams are {}", outParams);
			// System.out.println(outParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				// getting the inserted flag value
				questionnaireId = (int) outParams.get("last_insert_id");
				LOGGER.info("Questionnaire has been created successfully, Questionnaire id is ", questionnaireId);
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"addQuestionnaire service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.QUESTIONNAIRE_NAME_ALREADY_EXISTS,
									questionnaireRequest.getQuestionnaire().getQuestionnaireName())));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException | JsonProcessingException e) {
			LOGGER.error("error while executing addQuestionnaire ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return getQuestionnaireById(questionnaireId);
	}

	@Override
	public Questionnaire updateQuestionnaire(QuestionnaireRequest questionnaireRequest, Integer userId)
			throws ServiceExecutionException {
		Questionnaire questionnaire = questionnaireRequest.getQuestionnaire();
		try {
			Map<String, Object> inputParams = new HashMap<>();

			inputParams.put("p_questionnaire_id", questionnaire.getQuestionnaireId());
			inputParams.put("p_questionnaire_name", questionnaire.getQuestionnaireName());
			inputParams.put("p_start_date", questionnaire.getStartDate());
			inputParams.put("p_end_date", questionnaire.getEndDate());
			inputParams.put("p_is_published", questionnaire.getIsPublished());
			inputParams.put("p_modified_by", userId);

			inputParams.put("p_question_json", mapper.writeValueAsString(questionnaire.getQuestions()));
			inputParams.put("p_instruction_json", mapper.writeValueAsString(questionnaire.getInstructions()));

			inputParams.put("p_questionnaire_type_id", questionnaire.getQuestionnaireTypeId());
			inputParams.put("p_questionnaire_category_id", questionnaire.getQuestionnaireCategoryId());
			inputParams.put("p_questionnaire_level_id", questionnaire.getQuestionnaireLevelId());

			inputParams.put("p_questionnaire_banner_image", questionnaire.getQuestionnaireImageName());

			if (questionnaire.getSections() != null && questionnaire.getSections().size() > 0) {
				inputParams.put("p_section_json", mapper.writeValueAsString(questionnaire.getSections()));
			} else {
				inputParams.put("p_section_json", null);
			}

			LOGGER.info("updateQuestionnaire inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.QUESTIONNAIRE_UPDATE, inputParams);
			LOGGER.info("updateQuestionnaire outParams are {}", outParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("Questionnaire has been updated successfully, Questionnaire id is ",
						questionnaireRequest.getQuestionnaire().getQuestionnaireId());
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"updateQuestionnaire service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.QUESTIONNAIRE_NAME_ALREADY_EXISTS,
									questionnaireRequest.getQuestionnaire().getQuestionnaireName())));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException | JsonProcessingException e) {
			LOGGER.error("error while executing updateQuestionnaire ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return getQuestionnaireById(questionnaire.getQuestionnaireId());
	}

	@Override
	public void addNewInstruction(int questionnaireId, Integer userId, QuestionnaireInstruction instruction)
			throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_questionnaire_id", questionnaireId);
			inputParams.put("p_created_by", userId);
			inputParams.put("p_instruction", instruction.getInstruction());
			inputParams.put("p_order", instruction.getInstructionOrder());

			Map<String, Object> outParams = callStoredProcedure(SQLConstants.QUESTIONNAIRE_INSERT_INSTRUCTION,
					inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("Instruction has been added successfully to Questionnaire id ", questionnaireId);
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"addNewInstruction service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList(new WearablesError(
									WearablesErrorCode.QUESTIONNAIRE_INSTRUCTION_ALREADY_EXISTS, questionnaireId)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing addNewInstruction ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public void deleteInstruction(int questionnaireId, Integer userId, int instructionId)
			throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_questionnaire_id", questionnaireId);
			inputParams.put("p_instruction_id", instructionId);
			inputParams.put("p_modified_by", userId);

			Map<String, Object> outParams = callStoredProcedure(SQLConstants.QUESTIONNAIRE_DELETE_INSTRUCTION,
					inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("Instruction has been deleted successfully from the Questionnaire, id is", questionnaireId);
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"deleteInstruction service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.QUESTIONNAIRE_INSTRUCTION_ID_INVALID,
									questionnaireId)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing deleteInstruction ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public void updateInstruction(int questionnaireId, Integer userId, QuestionnaireInstruction instruction)
			throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_questionnaire_id", questionnaireId);
			inputParams.put("p_instruction_id", instruction.getInstructionId());
			inputParams.put("p_instruction", instruction.getInstruction());
			inputParams.put("p_instruction_order", instruction.getInstructionOrder());
			inputParams.put("p_modified_by", userId);

			Map<String, Object> outParams = callStoredProcedure(SQLConstants.QUESTIONNAIRE_UPDATE_INSTRUCTION,
					inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("Instruction has been updated successfully for Questionnaire id ", questionnaireId);
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"updateInstruction service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.QUESTIONNAIRE_INSTRUCTION_ID_INVALID,
									questionnaireId)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing updateInstruction ", e);
			throw new ServiceExecutionException(e.getMessage());
		}

	}

	@Override
	public void addNewQuestion(int questionnaireId, Integer userId, Question question)
			throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_questionnaire_id", questionnaireId);
			inputParams.put("p_question_json", mapper.writeValueAsString(question));
			inputParams.put("p_created_by", userId);

			Map<String, Object> outParams = callStoredProcedure(SQLConstants.QUESTIONNAIRE_INSERT_QUESTION,
					inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("Question has been added successfully to Questionnaire, id is ", questionnaireId);
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"updateQuestionnaire service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.QUESTIONNAIRE_QUESTION_ALREADY_EXISTS,
									questionnaireId)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException | JsonProcessingException e) {
			LOGGER.error("error while executing updateQuestionnaire ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public void deleteQuestion(int questionnaireId, Integer userId, int questionId) throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_questionnaire_id", questionnaireId);
			inputParams.put("p_question_id", questionId);
			inputParams.put("p_modified_by", userId);

			Map<String, Object> outParams = callStoredProcedure(SQLConstants.QUESTIONNAIRE_DELETE_QUESTION,
					inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("Question has been deleted successfully from the Questionnaire, id is", questionnaireId);
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"deleteQuestion service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.QUESTIONNAIRE_QUESTION_ID_INVALID,
									questionnaireId)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing deleteQuestion ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public void updateQuestion(int questionnaireId, Integer userId, Question question)
			throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_questionnaire_id", questionnaireId);
			inputParams.put("p_question_json", mapper.writeValueAsString(question));
			inputParams.put("p_created_by", userId);

			Map<String, Object> outParams = callStoredProcedure(SQLConstants.QUESTIONNAIRE_UPDATE_QUESTION,
					inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("Question has been added successfully to Questionnaire, id is ", questionnaireId);
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"updateQuestionnaire service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.QUESTIONNAIRE_QUESTION_ID_INVALID,
									questionnaireId)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException | JsonProcessingException e) {
			LOGGER.error("error while executing updateQuestionnaire ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Questionnaire getQuestionnaireById(int questionnaireId) throws ServiceExecutionException {
		LOGGER.debug("getQuestionnaireById called");
		Questionnaire questionnaire = new Questionnaire();
		List<QuestionnaireInstruction> instructions = new ArrayList<>();
		List<QuestionnaireSection> sections = new ArrayList<>();
		List<Question> questions = new ArrayList<>();
		Map<Integer, List<QuestionAnswerOption>> questionAnsOptsMap = new HashMap<>();
		Map<Integer, QuestionnaireSection> questionSectionMap = new HashMap<>();
		Map<Integer, List<QuestionCategory>> answerOptCategoryMap = new HashMap<>();
		Map<Integer, List<QuestionCategory>> questionCategoryMap = new HashMap<>();
		try {
			// in params
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("p_questionnaire_id", questionnaireId);

			Map<String, Object> simpleJdbcCallResult = callStoredProcedure(SQLConstants.QUESTIONNAIRE_GET_BY_ID,
					inputParams);
			Iterator<Entry<String, Object>> itr = simpleJdbcCallResult.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();

				if (key.equals(SQLConstants.RESULT_SET_1)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(quesObj -> {
						questionnaire.setQuestionnaireId((Integer) quesObj.get("QUESTIONNAIRE_ID"));
						questionnaire.setQuestionnaireName((String) quesObj.get("QUESTIONNAIRE_NAME"));

						questionnaire.setQuestionnaireTypeId(quesObj.get("QUESTIONNAIRE_TYPE_ID") != null
								? (Integer) quesObj.get("QUESTIONNAIRE_TYPE_ID")
								: null);
						questionnaire.setQuestionnaireCategoryId(quesObj.get("QUESTIONNAIRE_CATEGORY_ID") != null
								? (Integer) quesObj.get("QUESTIONNAIRE_CATEGORY_ID")
								: null);
						questionnaire.setQuestionnaireLevelId(quesObj.get("QUESTIONNAIRE_LEVEL_ID") != null
								? (Integer) quesObj.get("QUESTIONNAIRE_LEVEL_ID")
								: null);

						questionnaire.setQuestionnaireType((String) quesObj.get("QUESTIONNAIRE_TYPE"));
						questionnaire.setQuestionnaireCategory((String) quesObj.get("QUESTIONNAIRE_CATEGORY"));
						questionnaire.setQuestionnaireLevel((String) quesObj.get("QUESTIONNAIRE_LEVEL"));

						Date startDate = (Date) quesObj.get("START_DATE");
						questionnaire.setStartDate((startDate.toLocalDate()));

						Date endDate = (Date) quesObj.get("END_DATE");
						questionnaire.setEndDate((endDate.toLocalDate()));

						questionnaire.setIsPublished(quesObj.get("IS_PUBLISHED") != null
								&& (Integer) quesObj.get("IS_PUBLISHED") > NumberUtils.INTEGER_ZERO ? Boolean.TRUE
										: Boolean.FALSE);
						questionnaire.setIsActive(
								(Integer) quesObj.get("IS_ACTIVE") > NumberUtils.INTEGER_ZERO ? Boolean.TRUE
										: Boolean.FALSE);

						String fileName = (String) quesObj.get("QUESTIONNAIRE_IMAGE");

						questionnaire.setQuestionnaireImageName(fileName);
						if (fileName != null && fileName != "null" && !fileName.trim().equals("")) {
							questionnaire.setQuestionnaireImageUrl(gcpClientUtil.getDownloaFiledUrl(fileName,
									Constants.GCP_QUESTIONNAIRE_QUESTION_IMAGE_PATH));
						}

						questionnaire.setParentQuestionnaireId(quesObj.get("PARENT_QUESTIONNAIRE_ID") != null
								? (Integer) quesObj.get("PARENT_QUESTIONNAIRE_ID")
								: null);
					});
				}

				if (key.equals(SQLConstants.RESULT_SET_2)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();

					list.forEach(instruction -> {
						QuestionnaireSection section = new QuestionnaireSection();
						section.setSectionId((Integer) instruction.get("QUESTIONNAIRE_SECTION_ID"));
						section.setSectionName((String) instruction.get("SECTION_NAME"));
						section.setSectionDescription((String) instruction.get("SECTION_DESCRIPTION"));
						section.setSectionOrder((Integer) instruction.get("SECTION_ORDER"));

						questionSectionMap.put(section.getSectionId(), section);

						sections.add(section);
					});
					questionnaire.setSections(sections);
				}

				if (key.equals(SQLConstants.RESULT_SET_3)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();

					list.forEach(instruction -> {
						QuestionnaireInstruction questionnaireInstruction = new QuestionnaireInstruction();
						questionnaireInstruction
								.setInstructionId((Integer) instruction.get("QUESTIONNAIRE_INSTRUCTION_ID"));
						questionnaireInstruction.setInstruction((String) instruction.get("INSTRUCTION"));
						questionnaireInstruction.setInstructionOrder((Integer) instruction.get("INSTRUCTION_ORDER"));
						instructions.add(questionnaireInstruction);
					});
					questionnaire.setInstructions(instructions);
				}

				if (key.equals(SQLConstants.RESULT_SET_4)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(answerOptCategories -> {
						QuestionCategory questionCategory = new QuestionCategory();

						questionCategory
								.setQuestionCategoryId((Integer) answerOptCategories.get("QUESTION_CATEGORY_ID"));
						questionCategory.setQuestionCategory((String) answerOptCategories.get("QUESTION_CATEGORY"));
						questionCategory.setIsQualifyingCategory((Integer) answerOptCategories
								.get("IS_QUALIFYING_CATEGORY") != NumberUtils.INTEGER_ZERO);

						Integer questionAnswerOptId = (Integer) answerOptCategories.get("QUESTION_ANSWER_OPTION_ID");

						answerOptCategoryMap
								.computeIfAbsent(questionAnswerOptId, k -> new ArrayList<QuestionCategory>())
								.add(questionCategory);
					});
				}

				if (key.equals(SQLConstants.RESULT_SET_5)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(answerOpts -> {
						QuestionAnswerOption ansOptions = new QuestionAnswerOption();
						ansOptions.setQuestionAnswerId((Integer) answerOpts.get("QUESTION_ANSWER_OPTION_ID"));
						ansOptions.setQuestionAnswer((String) answerOpts.get("ANSWER"));
						
						String isAutoSubmit = String.valueOf(answerOpts.get("AUTO_SUBMIT_QUESTIONNAIRE"));
						
						if(StringUtils.isNotBlank(isAutoSubmit)) {
							ansOptions.setSubmitQuestionnaire((Integer) answerOpts.get("AUTO_SUBMIT_QUESTIONNAIRE") > NumberUtils.INTEGER_ZERO
											? Boolean.TRUE
											: Boolean.FALSE);
						}else {
							ansOptions.setSubmitQuestionnaire(Boolean.FALSE);
						}
						
						
						ansOptions.setSkipTo((Integer) answerOpts.get("SKIP_TO"));

						ansOptions.setAnsOptionMediaType((Integer) answerOpts.get("MEDIA_TYPE"));

						String fileName = (String) answerOpts.get("MEDIA_FILE_NAME");

						ansOptions.setAnsOptionMediaName(fileName);
						if (fileName != null && fileName != "null" && !fileName.trim().equals("")) {
							ansOptions.setAnsOptionMediaUrl(gcpClientUtil.getDownloaFiledUrl(fileName,
									Constants.GCP_QUESTIONNAIRE_QUESTION_IMAGE_PATH));
						}

						Integer questionId = (Integer) answerOpts.get("QUESTION_ID");
						Integer questionAnswerOptId = (Integer) answerOpts.get("QUESTION_ANSWER_OPTION_ID");

						questionAnsOptsMap.computeIfAbsent(questionId, k -> new ArrayList<QuestionAnswerOption>())
								.add(ansOptions);
						

						ansOptions.setAnswerCategories(answerOptCategoryMap.get(questionAnswerOptId) != null
								? answerOptCategoryMap.get(questionAnswerOptId)
								: new ArrayList<>());
					});
				}

				if (key.equals(SQLConstants.RESULT_SET_6)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(questionCategories -> {
						QuestionCategory questionCategory = new QuestionCategory();

						questionCategory
								.setQuestionCategoryId((Integer) questionCategories.get("QUESTION_CATEGORY_ID"));
						questionCategory.setQuestionCategory((String) questionCategories.get("QUESTION_CATEGORY"));

						Integer questionId = (Integer) questionCategories.get("QUESTION_ID");

						questionCategoryMap.computeIfAbsent(questionId, k -> new ArrayList<QuestionCategory>())
								.add(questionCategory);
					});
				}

				if (key.equals(SQLConstants.RESULT_SET_7)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();

					list.forEach(quest -> {
						Question question = new Question();
						QuestionSliderType other = new QuestionSliderType();
						Integer questionId = (Integer) quest.get("QUESTION_ID");
						question.setQuestionId(questionId);
						question.setQuestion((String) quest.get("QUESTION"));
						question.setQuestionCode((String) quest.get("QUESTION_CODE"));

						String fileName = (String) quest.get("QUESTION_IMAGE");

						question.setQuestionImageName(fileName);
						if (fileName != null && fileName != "null" && !fileName.trim().equals("")) {
							question.setQuestionImageUrl(gcpClientUtil.getDownloaFiledUrl(fileName,
									Constants.GCP_QUESTIONNAIRE_QUESTION_IMAGE_PATH));
						}

						question.setQuestionTypeId((Integer) quest.get("QUESTION_TYPE_ID"));
						question.setQuestionType((String) quest.get("QUESTION_TYPE"));
						question.setQuestionOrder((Integer) quest.get("QUESTION_ORDER"));
						question.setIsMandatory(quest.get("IS_MANDATORY") != null
								&& (Integer) quest.get("IS_MANDATORY") > NumberUtils.INTEGER_ZERO ? Boolean.TRUE
										: Boolean.FALSE);
						other.setCeil((Integer) quest.get("SCALE_MAX"));
						other.setFloor((Integer) quest.get("SCALE_MIN"));
						other.setCeilDescription((String) quest.get("SCALE_MAX_DESC"));
						other.setFloorDescription((String) quest.get("SCALE_MIN_DESC"));
						other.setTickStep((Integer) quest.get("STEP_VALUE"));
						other.setIsVerticalScale(quest.get("IS_VERTICAL_SCALE") != null
								&& (Integer) quest.get("IS_VERTICAL_SCALE") > NumberUtils.INTEGER_ZERO ? Boolean.TRUE
										: Boolean.FALSE);
						other.setIsContinuousScale(quest.get("IS_CONTINUOUS_SCALE") != null
								&& (Integer) quest.get("IS_CONTINUOUS_SCALE") > NumberUtils.INTEGER_ZERO ? Boolean.TRUE
										: Boolean.FALSE);

						question.setOther(other);

						question.setQuestionAnswerOptions(
								questionAnsOptsMap.get(questionId) != null ? questionAnsOptsMap.get(questionId)
										: new ArrayList<>());

						Integer sectionId = (Integer) quest.get("QUESTIONNAIRE_SECTION_ID");

						question.setSection(
								questionSectionMap.get(sectionId) != null ? questionSectionMap.get(sectionId) : null);

						question.setShuffleOptionOrder(quest.get("SHUFFLE_OPTION_ORDER") != null
								&& (Integer) quest.get("SHUFFLE_OPTION_ORDER") > NumberUtils.INTEGER_ZERO ? Boolean.TRUE
										: Boolean.FALSE);
						question.setIsIncludeInDataExtract(quest.get("INCLUDE_IN_EXTRACT") != null
								&& (Integer) quest.get("INCLUDE_IN_EXTRACT") > NumberUtils.INTEGER_ZERO ? Boolean.TRUE
										: Boolean.FALSE);
						question.setValidityPeriodId((Integer) quest.get("VALIDITY_PERIOD_ID"));
						question.setValidityPeriod((String) quest.get("VALIDITY_DESCRIPTION"));
						question.setNoOfDays(quest.get("NO_OF_DAYS") != null ? (Integer) quest.get("NO_OF_DAYS") :  null);

						question.setQuestionCategories(
								questionCategoryMap.get(questionId) != null ? questionCategoryMap.get(questionId)
										: new ArrayList<>());

						questions.add(question);
					});
					questionnaire.setQuestions(questions);
				}
			}
		} catch (Exception e) {
			LOGGER.error("error while fetching getQuestionnaireById", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return questionnaire;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getQuestionnairesCount(QuestionnaireFilter filter) throws ServiceExecutionException {
		HashMap<String, Integer> map = new HashMap<>();
		try {
			String counts = selectForObject(SQLConstants.QUESTIONNAIRE_GET_LIST_COUNT, String.class,
					filter.getSearchText(), filter.getQuestionnaireTypeId(), filter.getStatusId(),
					filter.getStartDate(), filter.getEndDate(), filter.getQuestionnaireLevelId());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while executing getQuestionnairesCount ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<QuestionnaireListDTO> getQuestionnaires(QuestionnaireFilter filter) throws ServiceExecutionException {
		List<QuestionnaireListDTO> questionnaireList = new ArrayList<>();
		try {
			String sql = SQLConstants.QUESTIONNAIRE_GET_LIST;
			jdbcTemplate.query(sql, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					QuestionnaireListDTO questionnaire = new QuestionnaireListDTO();
					questionnaire.setSlNumber(rs.getInt("slNo"));
					questionnaire.setQuestionnaireId(rs.getInt("QUESTIONNAIRE_ID"));
					questionnaire.setQuestionnaireName(rs.getString("QUESTIONNAIRE_NAME"));
					questionnaire.setQuestionnaireTypeId(rs.getInt("QUESTIONNAIRE_TYPE_ID"));
					questionnaire.setQuestionnaireCategoryId(rs.getInt("QUESTIONNAIRE_CATEGORY_ID"));
					questionnaire.setQuestionnaireType(rs.getString("QUESTIONNAIRE_TYPE"));
					questionnaire.setQuestionnaireCategory(rs.getString("QUESTIONNAIRE_CATEGORY"));
					questionnaire.setStartDate(rs.getDate("START_DATE").toLocalDate());
					questionnaire.setEndDate(rs.getDate("END_DATE").toLocalDate());
					questionnaire.setIsActive(rs.getBoolean("IS_ACTIVE"));
					questionnaire.setIsPublished(rs.getBoolean("IS_PUBLISHED"));
					questionnaire.setQuestionnaireLevel(rs.getString("QUESTIONNAIRE_LEVEL"));
					questionnaire.setIsRepublished(rs.getBoolean("IS_REPUBLISHED"));
					questionnaireList.add(questionnaire);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getQuestionnaireTypeId(), filter.getStatusId(), filter.getStartDate(), filter.getEndDate(),
					filter.getQuestionnaireLevelId());
		} catch (Exception e) {
			LOGGER.error("error while executing getQuestionnaires ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return questionnaireList;
	}

	@Override
	public void deleteQuestionnaire(int questionnaireId, int modifiedBy) throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_questionnaire_id", questionnaireId);
		inputParams.put("p_modified_by", modifiedBy);
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.QUESTIONNAIRE_DELETE, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			if (StringUtils.isNotEmpty(errorMsg) || (int) outParams.get("out_flag") < NumberUtils.INTEGER_ONE) {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing deleteQuestionnaire ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public List<QuestionnaireListDTO> getActiveQuestionnaires() throws ServiceExecutionException {
		List<QuestionnaireListDTO> questionnaireList = new ArrayList<>();
		LOGGER.debug("getActiveQuestionnaires called");
		try {
			jdbcTemplate.query(SQLConstants.QUESTIONNAIRE_GET_ACTIVE_QUESTIONNAIRES, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					QuestionnaireListDTO questionnaireListDTO = new QuestionnaireListDTO();
					questionnaireListDTO.setQuestionnaireId(rs.getInt("QUESTIONNAIRE_ID"));
					questionnaireListDTO.setQuestionnaireName(rs.getString("QUESTIONNAIRE_NAME"));
					questionnaireListDTO.setStartDate(rs.getDate("START_DATE").toLocalDate());
					questionnaireListDTO.setEndDate(rs.getDate("END_DATE").toLocalDate());
					questionnaireListDTO.setIsActive(rs.getBoolean("IS_ACTIVE"));
					questionnaireList.add(questionnaireListDTO);
				}
			});

		} catch (Exception e) {
			LOGGER.error("error while fetching getActiveQuestionnaires", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return questionnaireList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getQuestionnaireResponseCount(QuestionnaireResponseFilter filter)
			throws ServiceExecutionException {
		HashMap<String, Integer> map = new HashMap<>();
		try {
			String counts = selectForObject(SQLConstants.QUESTIONNAIRE_RESPONSE_GET_LIST_COUNT, String.class,
					filter.getSearchText(), filter.getQuestionnaireTypeId(), filter.getStudyId(), filter.getStartDate(),
					filter.getEndDate(), filter.getUserId(), filter.getQuestionnaireLevelId());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while executing getQuestionnairesCount ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getQuestionnaireResponseByStudyCount(QuestionnaireResponseByStudyFilter filter)
			throws ServiceExecutionException {
		HashMap<String, Integer> map = new HashMap<>();
		try {
			String counts = selectForObject(SQLConstants.QUESTIONNAIRE_RESPONSE_LIST_BY_STUDY_COUNT, String.class,
					filter.getSearchText(), filter.getFilterType(), filter.getFilterValue(), filter.getStartDate(),
					filter.getEndDate(), filter.getQuestionnaireId(), filter.getStudyId(), filter.getPetName(),
					filter.getPetParentName());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while executing getQuestionnaireResponseByStudyCount ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<QuestionnaireResponseListDTO> getQuestionnaireResponseList(QuestionnaireResponseFilter filter)
			throws ServiceExecutionException {
		List<QuestionnaireResponseListDTO> questionnaireResponseList = new ArrayList<>();
		try {
			String sql = SQLConstants.QUESTIONNAIRE_RESPONSE_GET_LIST;
			jdbcTemplate.query(sql, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					QuestionnaireResponseListDTO questionnaire = new QuestionnaireResponseListDTO();
					questionnaire.setSlNumber(rs.getInt("slNo"));
					questionnaire.setQuestionnaireId(rs.getInt("QUESTIONNAIRE_ID"));
					questionnaire.setQuestionnaireName(rs.getString("QUESTIONNAIRE_NAME"));
					questionnaire.setStudy(rs.getString("STUDY_NAME"));
					questionnaire.setStudyId(rs.getInt("STUDY_ID"));
					questionnaire.setStartDate(rs.getDate("START_DATE").toLocalDate());
					questionnaire
							.setEndDate(rs.getDate("END_DATE") != null ? rs.getDate("END_DATE").toLocalDate() : null);

					questionnaire.setSubmitedON(rs.getDate("SUBMITED_ON").toLocalDate());
					questionnaire.setPetName(rs.getString("PET_NAME"));
					questionnaire.setRespondentPetParentName(rs.getString("PET_PARENT_NAME"));
					questionnaire.setCompletionPercentage(rs.getDouble("COMPLETION_PERCENTAGE"));
					questionnaire.setPetParentId(rs.getInt("STUDY_ID"));
					questionnaire.setIsVipPetParent(rs.getBoolean("IS_VIP"));

					questionnaireResponseList.add(questionnaire);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getQuestionnaireTypeId(), filter.getStudyId(), filter.getStartDate(), filter.getEndDate(),
					filter.getUserId(), filter.getQuestionnaireLevelId());
		} catch (Exception e) {
			LOGGER.error("error while executing getQuestionnaires ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return questionnaireResponseList;
	}

	@Override
	public List<QuestionnaireResponseByStudyListDTO> getQuestionnaireResponseByStudyList(
			QuestionnaireResponseByStudyFilter filter) throws ServiceExecutionException {
		List<QuestionnaireResponseByStudyListDTO> questionnaireResponseList = new ArrayList<>();
		try {
			String sql = SQLConstants.QUESTIONNAIRE_RESPONSE_LIST_BY_STUDY;
			jdbcTemplate.query(sql, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					QuestionnaireResponseByStudyListDTO questionnaire = new QuestionnaireResponseByStudyListDTO();
					questionnaire.setPetParentName(rs.getString("FULL_NAME"));
					questionnaire.setIsVipPetParent(rs.getBoolean("IS_VIP"));
					questionnaire.setPetId(rs.getInt("PET_ID"));
					questionnaire.setQuestionnaireResponseId(rs.getInt("QUESTIONNAIRE_RESPONSE_ID"));
					questionnaire.setPetName(rs.getString("PET_NAME"));
					questionnaire.setSubmittedDate(rs.getTimestamp("CREATED_DATE"));
					questionnaire.setSharedDate(
							rs.getDate("START_DATE") == null ? null : rs.getDate("START_DATE").toLocalDate());
					questionnaire.setQuestionnaireId(Integer.valueOf(filter.getQuestionnaireId()));
					questionnaire.setStudyId(Integer.valueOf(filter.getStudyId()));
					questionnaireResponseList.add(questionnaire);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getFilterType(), filter.getFilterValue(), filter.getStartDate(), filter.getEndDate(),
					filter.getQuestionnaireId(), filter.getStudyId(), filter.getPetName(), filter.getPetParentName());
		} catch (Exception e) {
			LOGGER.error("error while executing getQuestionnaireResponseByStudyList ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return questionnaireResponseList;
	}

	@Override
	public QuestionnaireViewResponse getViewQuestionnaireResponse(int questionnaireResponseId, int studyId)
			throws ServiceExecutionException {
		QuestionnaireViewResponse response = new QuestionnaireViewResponse();
		List<QuestionnaireResponseDTO> questionnaireResponseDTOList = new ArrayList<>();
		List<QuestionnaireSection> sectionList = new ArrayList<>();
		Map<Integer, QuestionnaireSection> questionSectionMap = new HashMap<>();

		response.setQuestionnaireResponseList(questionnaireResponseDTOList);
		try {
			String sql = SQLConstants.QUESTIONNAIRE_VIEW_RESPONSE_DETAILS;
			jdbcTemplate.query(sql, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					QuestionnaireDetailsDTO detailsDTO = new QuestionnaireDetailsDTO();
					detailsDTO.setPetName(rs.getString("PET_NAME"));
					detailsDTO.setPetParentName(rs.getString("FULL_NAME"));
					detailsDTO.setQuestionnaireId(rs.getInt("QUESTIONNAIRE_ID"));
					detailsDTO.setQuestionnaireName(rs.getString("QUESTIONNAIRE_NAME"));
					detailsDTO.setSubmittedDate(rs.getTimestamp("CREATED_DATE"));
					detailsDTO.setStudyId(rs.getInt("STUDY_ID"));
					detailsDTO.setStudyName(rs.getString("STUDY_NAME"));
					response.setQuestionnaireDetails(detailsDTO);
				}
			}, questionnaireResponseId, studyId);

			sql = SQLConstants.QUESTIONNAIRE_SECTION_LIST;
			jdbcTemplate.query(sql, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					QuestionnaireSection section = new QuestionnaireSection();
					section.setSectionId(rs.getInt("QUESTIONNAIRE_SECTION_ID"));
					section.setSectionName(rs.getString("SECTION_NAME"));
					section.setSectionDescription(rs.getString("SECTION_DESCRIPTION"));
					section.setSectionOrder(rs.getInt("SECTION_ORDER"));

					questionSectionMap.put(section.getSectionId(), section);

					sectionList.add(section);
				}
			}, questionnaireResponseId);

			response.setSectionList(sectionList);

			sql = SQLConstants.QUESTIONNAIRE_VIEW_RESPONSE;
			jdbcTemplate.query(sql, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					QuestionnaireResponseDTO questionnaire = new QuestionnaireResponseDTO();
					questionnaire.setQuestion(rs.getString("QUESTION"));
					questionnaire.setQuestionId(rs.getInt("QUESTION_ID"));

					String fileName = rs.getString("QUESTION_IMAGE");

					if (fileName != null && fileName != "null" && !fileName.trim().equals("")) {
						questionnaire.setQuestionImageUrl(gcpClientUtil.getDownloaFiledUrl(fileName,
								Constants.GCP_QUESTIONNAIRE_QUESTION_IMAGE_PATH));
					}

					questionnaire.setQuestionTypeId(rs.getInt("QUESTION_TYPE_ID"));

					List<QuestionAnswer> answers = null;

					try {
						answers = new ObjectMapper().readValue(rs.getString("ANSWER"),
								new TypeReference<List<QuestionAnswer>>() {
								});
					} catch (JsonProcessingException e) {
						LOGGER.error("error while converting ANSWER in getViewQuestionnaireResponse", e);
					}

					generateGoogleFileUrl(answers);
					questionnaire.setAnswers(answers);
					// questionnaire.setAnswer(rs.getString("ANSWER"));

					Integer sectionId = rs.getInt("QUESTIONNAIRE_SECTION_ID");

					questionnaire.setSection(
							questionSectionMap.get(sectionId) != null ? questionSectionMap.get(sectionId) : null);

					questionnaireResponseDTOList.add(questionnaire);
				}
			}, questionnaireResponseId);

		} catch (Exception e) {
			LOGGER.error("error while executing getViewQuestionnaireResponse ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getQuestionnaireResponseByPetCount(QuestionnaireResponseFilter filter)
			throws ServiceExecutionException {
		HashMap<String, Integer> map = new HashMap<>();
		try {
			String counts = selectForObject(SQLConstants.QUESTIONNAIRE_RESPONSE_BY_PET_COUNT, String.class,
					filter.getSearchText(), filter.getPetId(), filter.getFilterType(), filter.getFilterValue(),
					filter.getStartDate(), filter.getEndDate());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while executing getQuestionnaireResponseByPetCount ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<PetQuestionnaireResponse> getQuestionnaireResponseByPet(QuestionnaireResponseFilter filter)
			throws ServiceExecutionException {
		List<PetQuestionnaireResponse> petQuestionnaireResponses = new ArrayList<>();
		try {
			String sql = SQLConstants.QUESTIONNAIRE_RESPONSE_LIST_BY_PET;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

			jdbcTemplate.query(sql, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetQuestionnaireResponse petQuestionnaireResponse = new PetQuestionnaireResponse();
					petQuestionnaireResponse.setPetId(rs.getInt("PET_ID"));
					petQuestionnaireResponse.setPetName(rs.getString("PET_NAME"));
					petQuestionnaireResponse.setQuestionnaireResponseId(rs.getInt("QUESTIONNAIRE_RESPONSE_ID"));
					petQuestionnaireResponse.setQuestionnaireId(rs.getInt("QUESTIONNAIRE_ID"));
					petQuestionnaireResponse.setQuestionnaireName(rs.getString("QUESTIONNAIRE_NAME"));
					petQuestionnaireResponse.setQuestionId(rs.getInt("QUESTION_ID"));
					petQuestionnaireResponse.setQuestionName(rs.getString("QUESTION"));

					String imageName = rs.getString("QUESTION_IMAGE");
					petQuestionnaireResponse.setQuestionImageName(imageName);
					if (StringUtils.isNotEmpty(imageName) && imageName != "null") {
						petQuestionnaireResponse.setQuestionImageUrl(gcpClientUtil.getDownloaFiledUrl(imageName,
								Constants.GCP_QUESTIONNAIRE_QUESTION_IMAGE_PATH));
					}

					petQuestionnaireResponse.setQuestionTypeId(rs.getInt("QUESTION_TYPE_ID"));
					petQuestionnaireResponse.setQuestionType(rs.getString("QUESTION_TYPE"));
					petQuestionnaireResponse.setAnswerOpts(rs.getString("ANSWER_OPTS"));

					List<QuestionAnswer> answers = null;
					try {
						answers = new ObjectMapper().readValue(rs.getString("ANSWER"),
								new TypeReference<List<QuestionAnswer>>() {
								});
					} catch (JsonProcessingException e) {
						LOGGER.error("error while converting ANSWER in getQuestionnaireResponseByPet", e);
					}
					generateGoogleFileUrl(answers);
					petQuestionnaireResponse.setAnswers(answers);

					if (!CollectionUtils.isEmpty(answers)) {
						String answer = answers.stream().map(e -> String.valueOf(e.getAnswer()))
								.collect(Collectors.joining(", "));
						petQuestionnaireResponse.setAnswer(answer);
						if (StringUtils.isNotBlank(answer) && answer.contains("firebasestorage")) {
							petQuestionnaireResponse.setAnswerImage("Image");
						} else {
							petQuestionnaireResponse.setAnswerImage(answer);
						}
					}

					petQuestionnaireResponse.setStudyNames(rs.getString("STUDY_NAMES"));
					petQuestionnaireResponse.setSubmittedDate(rs.getTimestamp("CREATED_DATE"));
					petQuestionnaireResponse.setSubmittedOn(rs.getTimestamp("CREATED_DATE") != null
							? rs.getTimestamp("CREATED_DATE").toLocalDateTime().format(formatter)
							: null);
					petQuestionnaireResponses.add(petQuestionnaireResponse);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getPetId(), filter.getFilterType(), filter.getFilterValue(), filter.getStartDate(),
					filter.getEndDate());
		} catch (Exception e) {
			LOGGER.error("error while executing getQuestionnaireResponseByPet ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petQuestionnaireResponses;
	}

	@Override
	public void implementSkipOnQuestionnaire(QuestionnaireSkipRequest questionnaireSkipRequest, int modifiedBy)
			throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_questionnaire_id", questionnaireSkipRequest.getQuestionnaireId());

			if (questionnaireSkipRequest.getQuestions() != null && questionnaireSkipRequest.getQuestions().size() > 0) {
				inputParams.put("p_questions_json", mapper.writeValueAsString(questionnaireSkipRequest.getQuestions()));
			} else {
				inputParams.put("p_questions_json", null);
			}

			inputParams.put("p_questionnaire_type_id", questionnaireSkipRequest.getQuestionnaireTypeId());
			inputParams.put("p_questionnaire_category_id", questionnaireSkipRequest.getQuestionnaireCategoryId());
			inputParams.put("p_questionnaire_level_id", questionnaireSkipRequest.getQuestionnaireLevelId());

			// inputParams.put("p_start_date", questionnaireSkipRequest.getStartDate());
			// inputParams.put("p_end_date", questionnaireSkipRequest.getEndDate());

			inputParams.put("p_modified_by", modifiedBy);

			Map<String, Object> outParams = callStoredProcedure(SQLConstants.QUESTIONNAIRE_SKIP_UPDATE, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("Skip Questionnaire has been updated successfully, Questionnaire id is ",
						questionnaireSkipRequest.getQuestionnaireId());
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"implementSkipOnQuestionnaire service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.QUESTIONNAIRE_NAME_ALREADY_EXISTS,
									questionnaireSkipRequest.getQuestionnaireId())));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException | JsonProcessingException e) {
			LOGGER.error("error while executing implementSkipOnQuestionnaire ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	private void generateGoogleFileUrl(List<QuestionAnswer> answers) {
		if (!answers.isEmpty()) {
			answers.stream().forEach(answer -> {
				if (StringUtils.isNotEmpty(answer.getMediaFileName()) && answer.getMediaFileName() != "null") {
					answer.setMediaUrl(gcpClientUtil.getDownloaFiledUrl(answer.getMediaFileName(),
							Constants.GCP_QUESTIONNAIRE_QUESTION_IMAGE_PATH));
				}
				if (StringUtils.isNotEmpty(answer.getAnswer()) && StringUtils.isNotEmpty(answer.getMediaType())
						&& Integer.valueOf(answer.getMediaType()) == NumberUtils.INTEGER_ONE) {
					answer.setAnswer(gcStorageUtil.getSignedMediaUrl(answer.getAnswer(),
							Constants.GCP_QUESTIONNAIRE_ANSWER_IMAGE_PATH));
				}
				if (StringUtils.isNotEmpty(answer.getAnswer()) && StringUtils.isNotEmpty(answer.getMediaType())
						&& Integer.valueOf(answer.getMediaType()) == NumberUtils.INTEGER_TWO) {
					answer.setAnswer(gcStorageUtil.getSignedMediaUrl(answer.getAnswer(),
							Constants.GCP_QUESTIONNAIRE_ANSWER_VIDEO_PATH));
				}
			});
		}

	}

	@Override
	public void republishQuestionnaire(RepublishQuestionnaireRequest republishQuestionnaireRequest, Integer userId)
			throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_questionnaire_id", republishQuestionnaireRequest.getQuestionnaireId());
			inputParams.put("p_end_date", republishQuestionnaireRequest.getEndDate());
			inputParams.put("p_comments", republishQuestionnaireRequest.getComments());
			inputParams.put("p_modified_by", userId);

			LOGGER.info("republishQuestionnaire inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.QUESTIONNAIRE_UPDATE_REPUBLISH,
					inputParams);
			LOGGER.info("republishQuestionnaire outParams are {}", outParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");

			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("Questionnaire has been republished successfully, Questionnaire id is ",
						republishQuestionnaireRequest.getQuestionnaireId());
			} else {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing republishQuestionnaire ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public List<QuestionnairePublishHistory> getQuestionnairePublishHistory(int questionnaireId)
			throws ServiceExecutionException {
		List<QuestionnairePublishHistory> publishHistory = new ArrayList<>();
		try {
			String sql = SQLConstants.QUESTIONNAIRE_GET_PUBLISH_HISTORY;
			jdbcTemplate.query(sql, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					QuestionnairePublishHistory questionnairePublishHistory = new QuestionnairePublishHistory();
					questionnairePublishHistory.setQuestionnaireId(rs.getInt("QUESTIONNAIRE_ID"));
					questionnairePublishHistory.setPublishDate(
							rs.getDate("PUBLISHED_DATE") != null ? rs.getDate("PUBLISHED_DATE").toLocalDate() : null);
					questionnairePublishHistory
							.setEndDate(rs.getDate("END_DATE") != null ? rs.getDate("END_DATE").toLocalDate() : null);
					questionnairePublishHistory.setComments(rs.getString("COMMENTS"));
					publishHistory.add(questionnairePublishHistory);
				}
			}, questionnaireId);
		} catch (Exception e) {
			LOGGER.error("error while executing getQuestionnairePublishHistory ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return publishHistory;
	}

	@Override
	public String getCopyQuestionnaireName(String questionnaireName) throws ServiceExecutionException {
		String proposedQuestionnaireName = questionnaireName;
		proposedQuestionnaireName = selectForObject(SQLConstants.FN_GET_PROPOSED_QUESTIONNAIRE_NAME, String.class,
				questionnaireName);
		if (proposedQuestionnaireName.length() > 100) {
			throw new ServiceExecutionException(
					"getCopyQuestionnaireName service validation failed cannot proceed further",
					Status.BAD_REQUEST.getStatusCode(),
					Arrays.asList(new WearablesError(WearablesErrorCode.QUESTIONNAIRE_NAME_INVALID_LENGTH)));
		}
		return proposedQuestionnaireName;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getExportsRequestedListCount(QuestionnaireFilter filter)
			throws ServiceExecutionException {
		HashMap<String, Integer> map = new HashMap<>();
		try {
			String counts = selectForObject(SQLConstants.QUESTIONNAIRE_EXPORT_REQUEST_LIST_COUNT, String.class,
					filter.getUserId());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while executing getExportsRequestedListCount ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<ExportQuestionnaireDTO> getExportsRequestedList(QuestionnaireFilter filter)
			throws ServiceExecutionException {
		List<ExportQuestionnaireDTO> exportQuestionnaire = new ArrayList<>();
		try {
			String sql = SQLConstants.QUESTIONNAIRE_EXPORT_REQUEST_LIST;
			jdbcTemplate.query(sql, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {

					ExportQuestionnaireDTO exportQuestionnaireDTO = new ExportQuestionnaireDTO();
					exportQuestionnaireDTO.setRequestedDate(rs.getDate("EXPORT_DATE").toLocalDate());
					exportQuestionnaireDTO.setQuestionnaireName(rs.getString("QUESTIONNAIRE_NAME"));
					exportQuestionnaireDTO.setStatus(rs.getString("STATUS"));
					exportQuestionnaireDTO.setQuestionnaireExportId(rs.getInt("QUESTIONNAIRE_EXPORT_ID"));
					
					exportQuestionnaire.add(exportQuestionnaireDTO);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getUserId(),
					filter.getRoleTypeId());
		} catch (Exception e) {
			LOGGER.error("error while executing getQuestionnairePublishHistory ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return exportQuestionnaire;
	}

	@Override
	public ExportQuestionnaireDTO exportQuestionaire(String questionnaireIds, CustomUserDetails userDetails)
			throws ServiceExecutionException {
		ExportQuestionnaireDTO exportQuestionnaireDTO = new ExportQuestionnaireDTO();
		try {
			Map<String, Object> inputParams = new HashMap<>();

			inputParams.put("p_questionnaire_ids", questionnaireIds);
			inputParams.put("p_user_id", userDetails.getUserId());

			LOGGER.info("exportQuestionaire inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.QUESTIONNAIRE_EXPORT_INSERT, inputParams);
			LOGGER.info("exportQuestionaire outParams are {}", outParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				int exportRequestId = (int) outParams.get("questionnaire_export_id");
				LOGGER.info("Questionnaire has been exported succcessfully exportRequestId is ", exportRequestId);

				exportQuestionnaireDTO.setQuestionnaireExportId(exportRequestId);

			} else {
				throw new ServiceExecutionException(errorMsg);
			}

		} catch (SQLException e) {
			LOGGER.error("error while executing addQuestionnaire ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return exportQuestionnaireDTO;
	}

	@Override
	public List<QuestionnaireExportDTO> getQuestionnaireDetailsForExport(int questionnaireId)
			throws ServiceExecutionException {

		List<QuestionnaireExportDTO> questionnaires = new ArrayList<QuestionnaireExportDTO>();

		try {

			jdbcTemplate.query(SQLConstants.QUESTIONNAIRE_EXPORT_GET_QUESTIONNAIRES, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {

					QuestionnaireExportDTO exportDTO = new QuestionnaireExportDTO();
					exportDTO.setQuestionnaireId(rs.getInt("QUESTIONNAIRE_ID"));
					exportDTO.setQuestionnaireName(rs.getString("QUESTIONNAIRE_NAME"));
					exportDTO.setStudyName(rs.getString("STUDY_NAME"));
					exportDTO.setPetId(rs.getInt("PET_ID"));
					exportDTO.setPetName(rs.getString("PET_NAME"));
					exportDTO.setFullName(rs.getString("FULL_NAME"));
					exportDTO.setEmail(rs.getString("EMAIL"));
					exportDTO.setSubmittedDate(rs.getDate("SUBMITTED_DATE") != null ? rs.getDate("SUBMITTED_DATE").toLocalDate() : null);
					exportDTO.setSharedDate(rs.getDate("SHARED_DATE") != null ? rs.getDate("SHARED_DATE").toLocalDate() : null);
					exportDTO.setQuestionCode(rs.getString("QUESTION_CODE"));
					exportDTO.setQuestionnaireId(rs.getInt("QUESTIONNAIRE_ID"));

					String fileName = rs.getString("QUESTION_IMAGE");

					if (fileName != null && fileName != "null" && !fileName.trim().equals("")) {
						exportDTO.setQuestionImageUrl(gcpClientUtil.getDownloaFiledUrl(fileName,
								Constants.GCP_QUESTIONNAIRE_QUESTION_IMAGE_PATH));
					}

					List<QuestionAnswer> answers = null;

					try {
						answers = new ObjectMapper().readValue(rs.getString("ANSWER"),
								new TypeReference<List<QuestionAnswer>>() {
								});

						generateGoogleFileUrl(answers);
						exportDTO.setAnswers(answers);

					} catch (JsonProcessingException e) {
						LOGGER.error("error while converting ANSWER in getViewQuestionnaireResponse "
								+ rs.getString("ANSWER"), e);
					}

					questionnaires.add(exportDTO);

				}
			}, questionnaireId);
			
			return questionnaires;

		} catch (Exception e) {
			LOGGER.error("error while executing getViewQuestionnaireResponse ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public boolean updateExportQuestionnaireFilePath(int exportId, String fileName, int userId)
			throws ServiceExecutionException {
		LOGGER.info("updateExportQuestionnaireFilePath called");
		try {
			Map<String, Object> inputParams = new HashMap<>();

			inputParams.put("p_export_id", exportId);
			inputParams.put("p_file_name", fileName);
			inputParams.put("p_user_id", userId);

			LOGGER.info("updateQuestionnaire inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.QUESTIONNAIRE_EXPORT_UPDATE, inputParams);
			LOGGER.info("updateQuestionnaire outParams are {}", outParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("updateExportQuestionnaireFilePath updated successfully ", exportId);
				return true;
			} else {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing updateQuestionnaire ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	 
	@Override
	public String downloadQuestionnaire(int exportId) throws ServiceExecutionException {
		final StringBuilder file = new StringBuilder("");
		try {

			jdbcTemplate.query(SQLConstants.QUESTIONNAIRE_EXPORT_DOWNLOAD, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {

					String fileName = rs.getString("EXPORT_URL");

					if (StringUtils.isNotBlank(fileName)) {
						file.append(gcpClientUtil.getDownloaFiledUrl(fileName,
								Constants.GCP_QUESTIONNAIRE_EXPORT_PATH));
					}
				}
			}, exportId);

		} catch (Exception e) {
			LOGGER.error("error while executing getViewQuestionnaireResponse ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return file.toString();
	}

	 
	@Override
	public Map<String, Integer> getQuestionnairesForExportCount(QuestionnaireFilter filter)
			throws ServiceExecutionException {
		HashMap<String, Integer> map = new HashMap<>();
		try {
			String counts = selectForObject(SQLConstants.QUESTIONNAIRE_GET_LIST_FOR_EXPORT_COUNT, String.class,
					filter.getSearchText(), filter.getQuestionnaireTypeId(), filter.getStatusId(),
					filter.getStartDate(), filter.getEndDate(), filter.getQuestionnaireLevelId());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while executing getQuestionnairesCount ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}
	
	@Override
	public List<QuestionnaireListDTO> getQuestionnairesForExport(QuestionnaireFilter filter) throws ServiceExecutionException {
		List<QuestionnaireListDTO> questionnaireList = new ArrayList<>();
		try {
			String sql = SQLConstants.QUESTIONNAIRE_GET_LIST_FOR_EXPORT;
			jdbcTemplate.query(sql, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					QuestionnaireListDTO questionnaire = new QuestionnaireListDTO();
					questionnaire.setSlNumber(rs.getInt("slNo"));
					questionnaire.setQuestionnaireId(rs.getInt("QUESTIONNAIRE_ID"));
					questionnaire.setQuestionnaireName(rs.getString("QUESTIONNAIRE_NAME"));
					questionnaire.setQuestionnaireTypeId(rs.getInt("QUESTIONNAIRE_TYPE_ID"));
					questionnaire.setQuestionnaireCategoryId(rs.getInt("QUESTIONNAIRE_CATEGORY_ID"));
					questionnaire.setQuestionnaireType(rs.getString("QUESTIONNAIRE_TYPE"));
					questionnaire.setQuestionnaireCategory(rs.getString("QUESTIONNAIRE_CATEGORY"));
					questionnaire.setStartDate(rs.getDate("START_DATE").toLocalDate());
					questionnaire.setEndDate(rs.getDate("END_DATE").toLocalDate());
					questionnaire.setIsActive(rs.getBoolean("IS_ACTIVE"));
					questionnaire.setIsPublished(rs.getBoolean("IS_PUBLISHED"));
					questionnaire.setQuestionnaireLevel(rs.getString("QUESTIONNAIRE_LEVEL"));
					questionnaire.setIsRepublished(rs.getBoolean("IS_REPUBLISHED"));
					questionnaireList.add(questionnaire);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getQuestionnaireTypeId(), filter.getStatusId(), filter.getStartDate(), filter.getEndDate(),
					filter.getQuestionnaireLevelId());
		} catch (Exception e) {
			LOGGER.error("error while executing getQuestionnaires ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return questionnaireList;
	}
	 
}
