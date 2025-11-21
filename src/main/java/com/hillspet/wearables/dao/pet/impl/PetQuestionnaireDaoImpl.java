package com.hillspet.wearables.dao.pet.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillspet.wearables.common.constants.Constants;
import com.hillspet.wearables.common.constants.SQLConstants;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.common.utils.GCPClientUtil;
import com.hillspet.wearables.common.utils.GCStorageUtil;
import com.hillspet.wearables.dao.BaseDaoImpl;
import com.hillspet.wearables.dao.pet.PetQuestionnaireDao;
import com.hillspet.wearables.dto.PetListDTO;
import com.hillspet.wearables.dto.PetParent;
import com.hillspet.wearables.dto.PetQuestionnaireReccurrence;
import com.hillspet.wearables.dto.PetSchedQuestionnaire;
import com.hillspet.wearables.dto.QuestionAnswer;
import com.hillspet.wearables.dto.QuestionnaireDetailsDTO;
import com.hillspet.wearables.dto.QuestionnaireResponseDTO;
import com.hillspet.wearables.dto.QuestionnaireSection;
import com.hillspet.wearables.dto.filter.PetFilter;
import com.hillspet.wearables.dto.filter.PetQuestionnaireFilter;
import com.hillspet.wearables.request.PetQuestionnaireRequest;
import com.hillspet.wearables.response.QuestionnaireViewResponse;

@Repository
public class PetQuestionnaireDaoImpl extends BaseDaoImpl implements PetQuestionnaireDao {

	@Value("${gcp.env}")
	private String environment;

	@Value("${gcp.storage.url}")
	private String storageUrl;

	@Autowired
	private GCStorageUtil gcStorageUtil;

	@Autowired
	private GCPClientUtil gcpClientUtil;

	@Autowired
	private ObjectMapper mapper;

	private static final Logger LOGGER = LogManager.getLogger(PetQuestionnaireDaoImpl.class);

	public static final String PET_QUESTIONNAIRE_INSERT = "PET_QUESTIONNAIRE_INSERT";
	public static final String PET_QUESTIONNAIRE_UPDATE = "PET_QUESTIONNAIRE_UPDATE";
	public static final String PET_QUESTIONNAIRE_DELETE = "PET_QUESTIONNAIRE_DELETE";

	public static final String GET_PET_LIST_TO_SCHED_QUESTIONNAIRE_COUNT = "SELECT FN_GET_PET_LIST_TO_SCHED_QUESTIONNAIRE_COUNT(?,?,?,?,?,?,?,?,?,?,?)";
	public static final String PET_GET_LIST_TO_SCHED_QUESTIONNAIRE = "CALL PET_GET_LIST_TO_SCHED_QUESTIONNAIRE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String PET_QUESTIONNAIRE_SCHEDULED_COUNT = "SELECT FN_GET_PET_QUESTIONNAIRE_SCHEDULED_COUNT(?,?,?,?,?,?) as count";
	public static final String PET_QUESTIONNAIRE_GET_SCHEDULED_LIST = "CALL PET_QUESTIONNAIRE_GET_SCHEDULED_LIST(?,?,?,?,?,?,?,?,?,?)";

	public static final String PET_QUESTIONNAIRE_GET_BY_CONFIG_ID = "PET_QUESTIONNAIRE_GET_BY_CONFIG_ID";

	public static final String PET_QUESTIONNAIRE_LIST_BY_CONFIG_COUNT = "SELECT FN_GET_PET_QUESTIONNAIRE_LIST_BY_CONFIG_COUNT(?,?,?,?,?,?,?) as count";
	public static final String PET_QUESTIONNAIRE_GET_LIST_BY_CONFIG_ID = "CALL PET_QUESTIONNAIRE_GET_LIST_BY_CONFIG_ID(?,?,?,?,?,?,?,?,?,?,?)";

	public static final String PET_QUESTIONNAIRE_GET_RESPONSE_DETAILS = "PET_QUESTIONNAIRE_GET_RESPONSE_DETAILS";
	public static final String PET_QUESTIONNAIRE_GET_PET_PARENTS_BY_PET_ID = "CALL PET_QUESTIONNAIRE_GET_PET_PARENTS_BY_PET_ID(?)";

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getPetsToSchedQuestionnaireCount(PetFilter filter) throws ServiceExecutionException {
		HashMap<String, Integer> map = new HashMap<>();
		LOGGER.debug("getPetsToSchedQuestionnaireCount called");
		try {
			String counts = selectForObject(GET_PET_LIST_TO_SCHED_QUESTIONNAIRE_COUNT, String.class,
					filter.getSearchText(), filter.getRoleTypeId(), filter.getGender(), filter.getSpeciesId(),
					filter.getBreedId(), filter.getStatusId(), filter.getUserId(), filter.getStudyId(),
					filter.getPetName(), filter.getPetParentName(), filter.getStudyName());
			map = mapper.readValue(counts, HashMap.class);
			LOGGER.debug("getPetsToSchedQuestionnaireCount successfully completed");
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetsToSchedQuestionnaireCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<PetListDTO> getPetsToSchedQuestionnaire(PetFilter filter) throws ServiceExecutionException {
		List<PetListDTO> petList = new ArrayList<>();
		LOGGER.debug("getPetsToSchedQuestionnaire called");
		try {
			jdbcTemplate.query(PET_GET_LIST_TO_SCHED_QUESTIONNAIRE, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetListDTO petListDTO = new PetListDTO();
					petListDTO.setPetId(rs.getInt("PET_ID"));
					petListDTO.setPetName(rs.getString("PET_NAME"));
					petListDTO.setGender(rs.getString("GENDER"));
					petListDTO.setWeight(rs.getString("WEIGHT"));

					String fileName = rs.getString("PHOTO_NAME");
					petListDTO.setPetPhoto(fileName);
					if (fileName != null && !fileName.trim().equals("")) {
						petListDTO.setPetPhotoUrl(
								gcpClientUtil.getDownloaFiledUrl(fileName, Constants.GCP_PETPHOTO_PATH));
					}

					petListDTO.setSpeciesName(rs.getString("SPECIES_NAME"));
					petListDTO.setBreedName(rs.getString("BREED_NAME"));
					petListDTO.setIsActive(rs.getBoolean("IS_ACTIVE"));
					petListDTO.setPetStatusId(rs.getInt("PET_STATUS_ID"));
					petListDTO.setPetStatus(rs.getString("STATUS_NAME"));
					petListDTO.setPetParentName(rs.getString("PET_PARENT_NAME"));
					petListDTO.setStudyName(rs.getString("STUDY_NAMES"));
					petList.add(petListDTO);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getStatusId(), filter.getGender(), filter.getSpeciesId(), filter.getBreedId(),
					filter.getUserId(), filter.getRoleTypeId(), filter.getStudyId(), filter.getPetName(),
					filter.getPetParentName(), filter.getStudyName());

		} catch (Exception e) {
			LOGGER.error("error while fetching getPetsToSchedQuestionnaire", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petList;
	}

	@Override
	public void addPetQuestionnaire(PetQuestionnaireRequest petQuestionnaireRequest) throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_pet_ids", petQuestionnaireRequest.getPetIds());
			inputParams.put("p_questionnaire_json",
					mapper.writeValueAsString(petQuestionnaireRequest.getQuestionnairesAssociated()));
			inputParams.put("p_created_by", petQuestionnaireRequest.getUserId());
			LOGGER.info("addPetQuestionnaire inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(PET_QUESTIONNAIRE_INSERT, inputParams);
			LOGGER.info("addPetQuestionnaire outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				// getting the inserted flag value
				LOGGER.info("addPetQuestionnaire statusFlag is {}", statusFlag);
			} else {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (SQLException | JsonProcessingException e) {
			LOGGER.error("error while executing addPetQuestionnaire ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getPetSchedQuestionnaireCount(PetQuestionnaireFilter filter)
			throws ServiceExecutionException {
		HashMap<String, Integer> map = new HashMap<>();
		LOGGER.debug("getPetSchedQuestionnaireCount called");
		try {
			String counts = selectForObject(PET_QUESTIONNAIRE_SCHEDULED_COUNT, String.class, filter.getSearchText(),
					filter.getOccurrenceId(), filter.getFrequencyId(), filter.getStartDate(), filter.getEndDate(),
					filter.getPetName());
			map = mapper.readValue(counts, HashMap.class);
			LOGGER.debug("getPetSchedQuestionnaireCount successfully completed");
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetSchedQuestionnaireCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<PetSchedQuestionnaire> getPetSchedQuestionnaireList(PetQuestionnaireFilter filter)
			throws ServiceExecutionException {
		List<PetSchedQuestionnaire> petSchedQuestionnaires = new ArrayList<>();
		LOGGER.debug("getPetSchedQuestionnaireList called");
		try {
			jdbcTemplate.query(PET_QUESTIONNAIRE_GET_SCHEDULED_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetSchedQuestionnaire petSchedQuestionnaire = new PetSchedQuestionnaire();
					petSchedQuestionnaire.setPetQuestionnaireConfigId(rs.getInt("PET_QUESTIONNAIRE_CONFIG_ID"));
					petSchedQuestionnaire.setPets(rs.getString("PET_NAMES"));

					petSchedQuestionnaire.setQuestionnaireId(rs.getInt("QUESTIONNAIRE_ID"));
					petSchedQuestionnaire.setQuestionnaireName(rs.getString("QUESTIONNAIRE_NAME"));

					petSchedQuestionnaire.setOccurrenceId(rs.getInt("OCCURANCE_ID"));
					petSchedQuestionnaire.setOccurrence(rs.getString("OCCURANCE_NAME"));

					petSchedQuestionnaire.setFrequencyId(rs.getInt("FREQUENCY_ID"));
					petSchedQuestionnaire.setFrequency(rs.getString("FREQUENCY_NAME"));

					petSchedQuestionnaire.setStartDate(rs.getDate("START_DATE").toLocalDate());
					petSchedQuestionnaire.setEndDate(rs.getDate("END_DATE").toLocalDate());
					petSchedQuestionnaire.setIsDeleted(rs.getBoolean("IS_DELETED"));
					petSchedQuestionnaire.setResponseCount(rs.getInt("RESPONSE_COUNT"));

					petSchedQuestionnaires.add(petSchedQuestionnaire);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getOccurrenceId(), filter.getFrequencyId(), filter.getStartDate(), filter.getEndDate(),
					filter.getPetName());

		} catch (Exception e) {
			LOGGER.error("error while fetching getPetSchedQuestionnaireList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petSchedQuestionnaires;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getPetQuestionnaireListByConfigCount(PetQuestionnaireFilter filter)
			throws ServiceExecutionException {
		HashMap<String, Integer> map = new HashMap<>();
		LOGGER.debug("getPetQuestionnaireListByConfigCount called");
		try {
			String counts = selectForObject(PET_QUESTIONNAIRE_LIST_BY_CONFIG_COUNT, String.class,
					filter.getSearchText(), filter.getPetQuestionnaireConfigId(), filter.getStatus(),
					filter.getStartDate(), filter.getEndDate(), filter.getPetName(), filter.getPetParentName());
			map = mapper.readValue(counts, HashMap.class);
			LOGGER.debug("getPetQuestionnaireListByConfigCount successfully completed");
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetQuestionnaireListByConfigCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<PetQuestionnaireReccurrence> getPetQuestionnaireListByConfigId(PetQuestionnaireFilter filter)
			throws ServiceExecutionException {
		List<PetQuestionnaireReccurrence> petQuestionnaireReccurrences = new ArrayList<>();
		LOGGER.debug("getPetQuestionnaireListByConfigId called");
		try {
			jdbcTemplate.query(PET_QUESTIONNAIRE_GET_LIST_BY_CONFIG_ID, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetQuestionnaireReccurrence petReccurrence = new PetQuestionnaireReccurrence();
					petReccurrence.setPetQuestionnaireScheduleId(rs.getInt("PET_QUESTIONNAIRE_SCHEDULE_ID"));

					petReccurrence.setQuestionnaireResponseId(rs.getInt("QUESTIONNAIRE_RESPONSE_ID"));

					petReccurrence.setPetId(rs.getInt("PET_ID"));
					petReccurrence.setPetName(rs.getString("PET_NAME"));

					LocalDateTime scheduleDate = rs.getTimestamp("SCHEDULE_DATE").toLocalDateTime();
					petReccurrence.setScheduledDate(scheduleDate.toLocalDate());
					LocalDateTime submittedDate = rs.getTimestamp("SUBMITTED_DATE") != null
							? rs.getTimestamp("SUBMITTED_DATE").toLocalDateTime()
							: null;
					petReccurrence.setSubmittedDate(submittedDate != null ? submittedDate.toLocalDate() : null);

					LocalDateTime dueDate = rs.getTimestamp("DUE_DATE") != null
							? rs.getTimestamp("DUE_DATE").toLocalDateTime()
							: null;
					petReccurrence.setDueDate(dueDate.toLocalDate());
					petReccurrence.setStatus(rs.getString("STATUS"));
					petReccurrence.setPetParentName(rs.getString("PET_PARENT_NAME"));

					petQuestionnaireReccurrences.add(petReccurrence);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getPetQuestionnaireConfigId(), filter.getStatus(), filter.getStartDate(),
					filter.getEndDate(), filter.getPetName(), filter.getPetParentName());

		} catch (Exception e) {
			LOGGER.error("error while fetching getPetQuestionnaireListByConfigId", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petQuestionnaireReccurrences;
	}

	/*
	 * @Override public PetSchedQuestionnaire getPetQuestionnaireByConfigId(int
	 * petQuestionnaireConfigId) throws ServiceExecutionException {
	 * PetSchedQuestionnaire petSchedQuestionnaire = new PetSchedQuestionnaire();
	 * LOGGER.debug("getPetQuestionnaireByConfigId called"); try {
	 * jdbcTemplate.query(PET_QUESTIONNAIRE_GET_LIST_BY_CONFIG_ID, new
	 * RowCallbackHandler() {
	 * 
	 * @Override public void processRow(ResultSet rs) throws SQLException {
	 * 
	 * petSchedQuestionnaire.setPetQuestionnaireConfigId(rs.getInt(
	 * "PET_QUESTIONNAIRE_CONFIG_ID"));
	 * 
	 * petSchedQuestionnaire.setQuestionnaireId(rs.getInt("QUESTIONNAIRE_ID"));
	 * petSchedQuestionnaire.setQuestionnaireName(rs.getString("QUESTIONNAIRE_NAME")
	 * );
	 * 
	 * petSchedQuestionnaire.setOccurrenceId((Integer) rs.getInt("OCCURANCE_ID"));
	 * petSchedQuestionnaire.setOccurrence(rs.getString("OCCURANCE_NAME"));
	 * 
	 * petSchedQuestionnaire.setFrequencyId((Integer) rs.getInt("FREQUENCY_ID"));
	 * petSchedQuestionnaire.setFrequency(rs.getString("FREQUENCY_NAME"));
	 * 
	 * Date startDate = (Date) rs.getDate("START_DATE");
	 * petSchedQuestionnaire.setStartDate(startDate.toLocalDate()); Date endDate =
	 * (Date) rs.getDate("END_DATE");
	 * petSchedQuestionnaire.setEndDate(endDate.toLocalDate()); } });
	 * 
	 * } catch (Exception e) {
	 * LOGGER.error("error while fetching getPetQuestionnaireByConfigId", e); throw
	 * new ServiceExecutionException(e.getMessage()); } return
	 * petSchedQuestionnaire; }
	 */

	@SuppressWarnings("unchecked")
	@Override
	public PetSchedQuestionnaire getPetQuestionnaireByConfigId(int petQuestionnaireConfigId)
			throws ServiceExecutionException {
		PetSchedQuestionnaire petSchedQuestionnaire = new PetSchedQuestionnaire();
		List<PetQuestionnaireReccurrence> petQuestionnaireReccurrences = new ArrayList<>();
		LOGGER.debug("getPetQuestionnaireByConfigId called");
		try {
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("pet_questionnaire_config_id", petQuestionnaireConfigId);

			Map<String, Object> simpleJdbcCallResult = callStoredProcedure(PET_QUESTIONNAIRE_GET_BY_CONFIG_ID,
					inputParams);
			Iterator<Entry<String, Object>> itr = simpleJdbcCallResult.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();

				if (key.equals(SQLConstants.RESULT_SET_1)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(questionnaire -> {
						petSchedQuestionnaire.setPetQuestionnaireConfigId(
								(Integer) questionnaire.get("PET_QUESTIONNAIRE_CONFIG_ID"));

						petSchedQuestionnaire.setQuestionnaireId((Integer) questionnaire.get("QUESTIONNAIRE_ID"));
						petSchedQuestionnaire.setQuestionnaireName((String) questionnaire.get("QUESTIONNAIRE_NAME"));

						petSchedQuestionnaire.setOccurrenceId((Integer) questionnaire.get("OCCURANCE_ID"));
						petSchedQuestionnaire.setOccurrence((String) questionnaire.get("OCCURANCE_NAME"));

						petSchedQuestionnaire.setFrequencyId((Integer) questionnaire.get("FREQUENCY_ID"));
						petSchedQuestionnaire.setFrequency((String) questionnaire.get("FREQUENCY_NAME"));

						Date startDate = (Date) questionnaire.get("START_DATE");
						petSchedQuestionnaire.setStartDate(startDate.toLocalDate());
						Date endDate = (Date) questionnaire.get("END_DATE");
						petSchedQuestionnaire.setEndDate(endDate.toLocalDate());
					});
				}

				if (key.equals(SQLConstants.RESULT_SET_2)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(reccurrence -> {
						PetQuestionnaireReccurrence petReccurrence = new PetQuestionnaireReccurrence();
						petReccurrence.setPetQuestionnaireScheduleId(
								(Integer) reccurrence.get("PET_QUESTIONNAIRE_SCHEDULE_ID"));

						petReccurrence
								.setQuestionnaireResponseId((Integer) reccurrence.get("QUESTIONNAIRE_RESPONSE_ID"));

						petReccurrence.setPetId((Integer) reccurrence.get("PET_ID"));
						petReccurrence.setPetName((String) reccurrence.get("PET_NAME"));

						Date scheduleDate = (Date) reccurrence.get("SCHEDULE_DATE");
						petReccurrence.setScheduledDate(scheduleDate.toLocalDate());
						LocalDateTime submittedDate = (LocalDateTime) reccurrence.get("SUBMITTED_DATE");
						petReccurrence.setSubmittedDate(submittedDate != null ? submittedDate.toLocalDate() : null);

						Date nextScheduleDate = (Date) reccurrence.get("NEXT_SCHEDULE_DATE");
						if (submittedDate != null) {
							petReccurrence.setStatus("Completed");
							if (nextScheduleDate == null) {
								petReccurrence.setDueDate(petSchedQuestionnaire.getEndDate());
							} else {
								petReccurrence
										.setDueDate(nextScheduleDate.toLocalDate().minusDays(NumberUtils.INTEGER_ONE));
							}
						} else {
							if (nextScheduleDate == null) {
								if (petSchedQuestionnaire.getEndDate().isBefore(LocalDate.now())) {
									petReccurrence.setStatus("Missed");
								} else {
									if (scheduleDate.toLocalDate().isEqual(LocalDate.now())
											|| scheduleDate.toLocalDate().isAfter(LocalDate.now())) {
										petReccurrence.setStatus("Scheduled");
									} else {
										petReccurrence.setStatus("Pending");
									}
								}
								petReccurrence.setDueDate(petSchedQuestionnaire.getEndDate());
							} else {
								petReccurrence
										.setDueDate(nextScheduleDate.toLocalDate().minusDays(NumberUtils.INTEGER_ONE));
								if (nextScheduleDate.toLocalDate().isEqual(LocalDate.now())
										|| nextScheduleDate.toLocalDate().isBefore(LocalDate.now())) {
									petReccurrence.setStatus("Missed");
								} else {
									if (scheduleDate.toLocalDate().isEqual(LocalDate.now())
											|| scheduleDate.toLocalDate().isAfter(LocalDate.now())) {
										petReccurrence.setStatus("Scheduled");
									} else {
										petReccurrence.setStatus("Pending");
									}
								}
							}
						}

						petQuestionnaireReccurrences.add(petReccurrence);
					});
				}
				petSchedQuestionnaire.setPetQuestionnaireReccurrences(petQuestionnaireReccurrences);
			}

		} catch (Exception e) {
			LOGGER.error("error while fetching getPetQuestionnaireByConfigId", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petSchedQuestionnaire;
	}

	@Override
	public void deletePetQuestionnaire(int petQuestionnaireConfigId, int modifiedBy) throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_pet_questionnaire_config_id", petQuestionnaireConfigId);
		inputParams.put("p_modified_by", modifiedBy);
		try {
			LOGGER.info("deletePetQuestionnaire inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(PET_QUESTIONNAIRE_DELETE, inputParams);
			LOGGER.info("deletePetQuestionnaire outParams are {}", outParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			if (StringUtils.isNotEmpty(errorMsg) || (int) outParams.get("out_flag") < NumberUtils.INTEGER_ONE) {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing deletePetQuestionnaire ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public QuestionnaireViewResponse getQuestionnaireResponse(int petQuestionnaireConfigId,
			int questionnaireResponseId) {
		QuestionnaireViewResponse response = new QuestionnaireViewResponse();
		List<QuestionnaireResponseDTO> questionnaireResponseDTOList = new ArrayList<>();
		List<QuestionnaireSection> sectionList = new ArrayList<>();
		Map<Integer, QuestionnaireSection> questionSectionMap = new HashMap<>();
		response.setQuestionnaireResponseList(questionnaireResponseDTOList);

		try {
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("pet_questionnaire_config_id", petQuestionnaireConfigId);
			inputParams.put("p_questionnaire_response_id", questionnaireResponseId);

			Map<String, Object> simpleJdbcCallResult = callStoredProcedure(PET_QUESTIONNAIRE_GET_RESPONSE_DETAILS,
					inputParams);
			Iterator<Entry<String, Object>> itr = simpleJdbcCallResult.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();

				if (key.equals(SQLConstants.RESULT_SET_1)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(questionnaire -> {
						QuestionnaireDetailsDTO detailsDTO = new QuestionnaireDetailsDTO();
						detailsDTO.setPetId((Integer) questionnaire.get("PET_ID"));
						detailsDTO.setPetName((String) questionnaire.get("PET_NAME"));
						detailsDTO.setPetParentId((Integer) questionnaire.get("PET_PARENT_ID"));
						detailsDTO.setPetParentName((String) questionnaire.get("FULL_NAME"));
						detailsDTO.setQuestionnaireId((Integer) questionnaire.get("QUESTIONNAIRE_ID"));
						detailsDTO.setQuestionnaireName((String) questionnaire.get("QUESTIONNAIRE_NAME"));

						detailsDTO.setOccurrenceId((Integer) questionnaire.get("OCCURANCE_ID"));
						detailsDTO.setOccurrence((String) questionnaire.get("OCCURANCE_NAME"));

						detailsDTO.setFrequencyId((Integer) questionnaire.get("FREQUENCY_ID"));
						detailsDTO.setFrequency((String) questionnaire.get("FREQUENCY_NAME"));

						Date startDate = (Date) questionnaire.get("START_DATE");
						detailsDTO.setStartDate(startDate.toLocalDate());
						Date endDate = (Date) questionnaire.get("END_DATE");
						detailsDTO.setEndDate(endDate.toLocalDate());

						Date scheduleDate = (Date) questionnaire.get("SCHEDULE_DATE");
						detailsDTO.setScheduledDate(scheduleDate.toLocalDate());

						detailsDTO.setSubmittedDate(
								(Timestamp.valueOf((LocalDateTime) questionnaire.get("CREATED_DATE"))));
						response.setQuestionnaireDetails(detailsDTO);
					});
				}

				if (key.equals(SQLConstants.RESULT_SET_2)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(sec -> {
						QuestionnaireSection section = new QuestionnaireSection();
						section.setSectionId((Integer) sec.get("QUESTIONNAIRE_SECTION_ID"));
						section.setSectionName((String) sec.get("SECTION_NAME"));
						section.setSectionDescription((String) sec.get("SECTION_DESCRIPTION"));
						section.setSectionOrder((Integer) sec.get("SECTION_ORDER"));

						questionSectionMap.put(section.getSectionId(), section);
						sectionList.add(section);
					});
					response.setSectionList(sectionList);
				}

				if (key.equals(SQLConstants.RESULT_SET_3)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(resp -> {
						QuestionnaireResponseDTO questionnaire = new QuestionnaireResponseDTO();
						questionnaire.setQuestionId((Integer) resp.get("QUESTION_ID"));
						questionnaire.setQuestion((String) resp.get("QUESTION"));
						String fileName = (String) resp.get("QUESTION_IMAGE");

						if (StringUtils.isNotEmpty(fileName) && fileName != "null") {
							questionnaire.setQuestionImageUrl(gcpClientUtil.getDownloaFiledUrl(fileName,
									Constants.GCP_QUESTIONNAIRE_QUESTION_IMAGE_PATH));
						}
						questionnaire.setQuestionTypeId((Integer) resp.get("QUESTION_TYPE_ID"));

						List<QuestionAnswer> answers = null;
						try {
							answers = new ObjectMapper().readValue((String) resp.get("ANSWER"),
									new TypeReference<List<QuestionAnswer>>() {
									});
						} catch (JsonProcessingException e) {
							LOGGER.error("error while converting ANSWER in getQuestionnaireResponse", e);
						}
						generateGoogleFileUrl(answers);
						questionnaire.setAnswers(answers);

						Integer sectionId = (Integer) resp.get("QUESTIONNAIRE_SECTION_ID");
						questionnaire.setSection(
								questionSectionMap.get(sectionId) != null ? questionSectionMap.get(sectionId) : null);

						questionnaireResponseDTOList.add(questionnaire);
					});
				}
			}
		} catch (Exception e) {
			LOGGER.error("error while executing getQuestionnaireResponse ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return response;
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
	public List<PetParent> getPetParentsByPetId(int petId) {
		List<PetParent> petParents = new ArrayList<>();
		LOGGER.debug("getPetParentsByPetId called");
		try {
			jdbcTemplate.query(PET_QUESTIONNAIRE_GET_PET_PARENTS_BY_PET_ID, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetParent petParent = new PetParent();
					petParent.setPetParentId(rs.getInt("PET_PARENT_ID"));
					petParent.setPetParentName(rs.getString("FULL_NAME"));
					petParent.setEmail(rs.getString("EMAIL"));
					petParent.setFcmToken(rs.getString("FCM_TOKEN"));
					petParents.add(petParent);
				}
			}, petId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetParentsByPetId ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petParents;
	}

}
