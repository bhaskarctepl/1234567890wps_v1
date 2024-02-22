package com.hillspet.wearables.dao.study.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.IdTokenCredentials;
import com.google.auth.oauth2.IdTokenProvider;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.hillspet.wearables.common.constants.Constants;
import com.hillspet.wearables.common.constants.SQLConstants;
import com.hillspet.wearables.common.constants.StringLiterals;
import com.hillspet.wearables.common.constants.WearablesErrorCode;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.common.exceptions.ServiceValidationException;
import com.hillspet.wearables.common.utils.GCPClientUtil;
import com.hillspet.wearables.dao.BaseDaoImpl;
import com.hillspet.wearables.dao.study.StudyDao;
import com.hillspet.wearables.dto.Algorithm;
import com.hillspet.wearables.dto.FeedingSchedule;
import com.hillspet.wearables.dto.FeedingScheduleConfig;
import com.hillspet.wearables.dto.FoodIntakeDetails;
import com.hillspet.wearables.dto.ImageScoringResponse;
import com.hillspet.wearables.dto.PetBreed;
import com.hillspet.wearables.dto.PetListDTO;
import com.hillspet.wearables.dto.PhaseWisePetListDTO;
import com.hillspet.wearables.dto.PreludeDataByStudyDTO;
import com.hillspet.wearables.dto.PreludeListDTO;
import com.hillspet.wearables.dto.PreludeMandatory;
import com.hillspet.wearables.dto.PushNotification;
import com.hillspet.wearables.dto.Question;
import com.hillspet.wearables.dto.QuestionAnswerOption;
import com.hillspet.wearables.dto.QuestionSliderType;
import com.hillspet.wearables.dto.Questionnaire;
import com.hillspet.wearables.dto.QuestionnaireInstruction;
import com.hillspet.wearables.dto.QuestionnaireListDTO;
import com.hillspet.wearables.dto.QuestionnaireSection;
import com.hillspet.wearables.dto.RecommendationDetails;
import com.hillspet.wearables.dto.StudyBasicDetails;
import com.hillspet.wearables.dto.StudyDTO;
import com.hillspet.wearables.dto.StudyDiary;
import com.hillspet.wearables.dto.StudyDietListDTO;
import com.hillspet.wearables.dto.StudyImageScale;
import com.hillspet.wearables.dto.StudyListDTO;
import com.hillspet.wearables.dto.StudyNotes;
import com.hillspet.wearables.dto.StudyNotification;
import com.hillspet.wearables.dto.StudyPhase;
import com.hillspet.wearables.dto.StudyPhaseQuestionnaireScheduleDTO;
import com.hillspet.wearables.dto.StudyTreatmentGroup;
import com.hillspet.wearables.dto.ThresholdByStudyReport;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.dto.filter.FeedingScheduleFilter;
import com.hillspet.wearables.dto.filter.FeedingScheduleResponseFilter;
import com.hillspet.wearables.dto.filter.ImageScaleFilter;
import com.hillspet.wearables.dto.filter.IntakeFilter;
import com.hillspet.wearables.dto.filter.PhaseWisePetListFilter;
import com.hillspet.wearables.dto.filter.QuestionnaireResponseFilter;
import com.hillspet.wearables.dto.filter.StudyDiaryFilter;
import com.hillspet.wearables.dto.filter.StudyDietFilter;
import com.hillspet.wearables.dto.filter.StudyFilter;
import com.hillspet.wearables.request.ActivityFactorConfig;
import com.hillspet.wearables.request.AddCrossOverStudyRequest;
import com.hillspet.wearables.request.AddPhaseWisePetRequest;
import com.hillspet.wearables.request.AddStudyRequest;
import com.hillspet.wearables.request.FlaggedRecommendationRequest;
import com.hillspet.wearables.request.FlaggedRecommendationStatusRequest;
import com.hillspet.wearables.request.ImageScoringAssociated;
import com.hillspet.wearables.request.ImageScoringConfigRequest;
import com.hillspet.wearables.request.PlanSubscribed;
import com.hillspet.wearables.request.PreludeAssociated;
import com.hillspet.wearables.request.PushNotificationAssociated;
import com.hillspet.wearables.request.PushNotificationConfigRequest;
import com.hillspet.wearables.request.PushNotificationsAssociated;
import com.hillspet.wearables.request.QuestionnaireAssociated;
import com.hillspet.wearables.request.QuestionnaireConfigRequest;
import com.hillspet.wearables.request.StudyActivityFactorRequest;
import com.hillspet.wearables.request.StudyDietRequest;
import com.hillspet.wearables.request.StudyMobileAppConfigRequest;
import com.hillspet.wearables.request.StudyNotesRequest;
import com.hillspet.wearables.request.StudyNotificationRequest;
import com.hillspet.wearables.request.StudyPlanRequest;
import com.hillspet.wearables.request.StudyPreludeConfigRequest;
import com.hillspet.wearables.request.StudyQuestionnaireRequest;
import com.hillspet.wearables.response.FRLookUpResponse;
import com.hillspet.wearables.response.FeedingSchedulesResponse;
import com.hillspet.wearables.response.StudyActivityFactorResponse;
import com.hillspet.wearables.response.StudyMobileAppConfigResponse;
import com.hillspet.wearables.response.StudyNotesListResponse;
import com.hillspet.wearables.response.StudyNotesResponse;
import com.hillspet.wearables.response.StudyPreludeConfigResponse;

import reactor.core.publisher.Mono;

@Repository
public class StudyDaoImpl extends BaseDaoImpl implements StudyDao {

	private static final Logger LOGGER = LogManager.getLogger(StudyDaoImpl.class);

	@Autowired
	private GCPClientUtil gcpClientUtil;

	@Autowired
	private ObjectMapper mapper;

	@Value("${gcp.env}")
	private String environment;

	private String loadPreludeCFUrl = System.getenv("LOAD_PRELUDE_CR_URL");

	private static String BASE_PRELUDE_URL = System.getenv("BASE_PRELUDE_URL");

	private static IdTokenCredentials tokenCredential;

	static {
		try {
			GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
			if (!(credentials instanceof IdTokenProvider)) {
				throw new IllegalArgumentException("Credentials are not an instance of IdTokenProvider.");
			}
			tokenCredential = IdTokenCredentials.newBuilder().setIdTokenProvider((IdTokenProvider) credentials)
					.setTargetAudience(BASE_PRELUDE_URL).build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* ---------------- STUDY SERVICES ------------------------- */
	@Override
	public int addStudy(AddStudyRequest studyRequest, Integer userId) throws ServiceExecutionException {
		Integer studyId = 0;
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_study_name", studyRequest.getStudyName());
			inputParams.put("p_principle_investigator", studyRequest.getPrincipalInvestigator());
			inputParams.put("p_description", studyRequest.getDescription());
			inputParams.put("p_start_date", studyRequest.getStartDate());
			inputParams.put("p_study_location", studyRequest.getStudyLocationId());

			if (studyRequest.getStudyLocationId() == 1) {
				inputParams.put("p_is_external", 0);
			} else {
				inputParams.put("p_is_external", 1);
			}

			if (!StringUtils.isBlank(studyRequest.getExternalLink())) {
				inputParams.put("p_url", studyRequest.getExternalLink());
			} else {
				inputParams.put("p_url", "");
			}

			inputParams.put("p_notification_enabled",
					(studyRequest.getIsNotificationEnable() ? NumberUtils.INTEGER_ONE : NumberUtils.INTEGER_ZERO));

			inputParams.put("p_treatment_groups_json", mapper.writeValueAsString(studyRequest.getTreatmentGroups()));
			inputParams.put("p_study_phases_json", mapper.writeValueAsString(studyRequest.getStudyPhases()));
			inputParams.put("p_created_by", userId);

			LOGGER.info("addStudy inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_INSERT_NEW, inputParams);
			LOGGER.info("addStudy outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				studyId = (int) outParams.get("last_insert_id");
				if (studyRequest.getStudyLocationId() == 2 && !studyRequest.getExternalLink().isEmpty()) {
					LOGGER.info("Study has been created successfully, Study id is {}", studyId);
					invokePreludeJob(loadPreludeCFUrl, studyId.toString(), environment);
				}
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException("addStudy service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.STUDY_NAME_ALREADY_EXISTS,
									studyRequest.getStudyName())));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException | JsonProcessingException e) {
			LOGGER.error("error while executing addStudy ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return studyId;
	}

	@Override
	public void updateStudy(AddStudyRequest studyRequest, Integer userId) throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_study_id", studyRequest.getStudyId());
			inputParams.put("p_study_name", studyRequest.getStudyName());
			inputParams.put("p_principle_investigator", studyRequest.getPrincipalInvestigator());
			inputParams.put("p_description", studyRequest.getDescription());
			inputParams.put("p_start_date", studyRequest.getStartDate());
			inputParams.put("p_study_location", studyRequest.getStudyLocationId());

			if (studyRequest.getStudyLocationId() == 1) {
				inputParams.put("p_is_external", 0);
			} else {
				inputParams.put("p_is_external", 1);
			}

			if (!StringUtils.isBlank(studyRequest.getExternalLink())) {
				inputParams.put("p_url", studyRequest.getExternalLink());
			} else {
				inputParams.put("p_url", "");
			}

			inputParams.put("p_notification_enabled",
					(studyRequest.getIsNotificationEnable() ? NumberUtils.INTEGER_ONE : NumberUtils.INTEGER_ZERO));

			List<Integer> groupIds = studyRequest.getTreatmentGroups().stream()
					.filter(e -> (e.getTreamentGroupId() != null && e.getTreamentGroupId() > 0))
					.map(e -> e.getTreamentGroupId()).collect(Collectors.toList());

			inputParams.put("p_active_group_ids", StringUtils.join(groupIds, ','));

			inputParams.put("p_treatment_groups_json", mapper.writeValueAsString(studyRequest.getTreatmentGroups()));
			inputParams.put("p_study_phases_json", mapper.writeValueAsString(studyRequest.getStudyPhases()));
			inputParams.put("p_status_id", studyRequest.getStatus());
			inputParams.put("p_modified_by", userId);

			LOGGER.info("updateStudy inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_UPDATE_NEW, inputParams);
			LOGGER.info("updateStudy outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			String message = (String) outParams.get("out_msg");

			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("Study has been updated successfully, Study id is ", studyRequest.getStudyId());

				int updateScheduler = (int) outParams.get("out_update_scheduler");
				// invoke scheduler procedure
				if (updateScheduler == 1) {
					invokeStudyScheduler(studyRequest.getStudyId(), userId).subscribe(response -> {
						// Handle the asynchronous response
						System.out.println("Asynchronous response: " + response);
					});
				}

				// If Study Status is completed
				if (studyRequest.getStatus() == 4) {
					invokeCompleteStudyScheduler(studyRequest.getStudyId(), userId).subscribe(response -> {
						// Handle the asynchronous response
						System.out.println("Asynchronous response: " + response);
					});
				}
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException("updateStudy service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.STUDY_NAME_ALREADY_EXISTS,
									studyRequest.getStudyName())));
				} else if (statusFlag == -3) {
					throw new ServiceExecutionException("updateStudy service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.STUDY_UPDATE_STATUS_DRAFT)));
				} else if (statusFlag == -4) {
					throw new ServiceExecutionException("updateStudy service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.STUDY_UPDATE_STATUS_FINALIZE_GROUPS)));
				} else if (statusFlag == -5) {
					throw new ServiceExecutionException("updateStudy service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.STUDY_UPDATE_STATUS_SCHEDULED)));
				} else if (statusFlag == -6) {
					throw new ServiceExecutionException("updateStudy service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.STUDY_UPDATE_STATUS_INPROGRESS)));
				} else if (statusFlag == -7) {
					throw new ServiceExecutionException("updateStudy service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList(
									new WearablesError(WearablesErrorCode.STUDY_UPDATE_MANDATORY_CHECK_FAIL, message)));
				} else if (statusFlag == -8) {
					throw new ServiceExecutionException("updateStudy service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList(new WearablesError(
									WearablesErrorCode.STUDY_FEEDING_SCHEDULE_VALIDATION_FAILED, message)));
				} else {
					LOGGER.error("error while executing updateStudy ============= {}", errorMsg);
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException | JsonProcessingException e) {
			LOGGER.error("error while executing updateStudy ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	public HttpURLConnection postDataToEndpointSync(String endpoint) {
		HttpURLConnection httpClient = null;
		try {
			if (endpoint != null && !endpoint.equals("")) {
				httpClient = (HttpURLConnection) new URL(endpoint).openConnection();
				httpClient.setRequestMethod("GET");
				// httpClient.setRequestProperty("StudyURL", "500");
				httpClient.setDoOutput(true);
				LOGGER.error("\n Sent 'POST' request to URL : " + endpoint + " \n Response Code: "
						+ httpClient.getResponseCode());
			}
		} catch (MalformedURLException e) {
			LOGGER.error("MalformedURLException in postDataToEndpoint method:" + e.getMessage());
		} catch (ProtocolException e) {
			LOGGER.error("ProtocolException in postDataToEndpoint method:" + e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Exception in postDataToEndpoint method:" + e.getMessage());
		}
		return httpClient;
	}

	/**
	 * Calls Cloud Function Asynchronously
	 * 
	 * @param endpoint
	 * @param output
	 * @return
	 * @throws ExecutionException
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	@SuppressWarnings("deprecation")
	public DeferredResult<String> postDataToEndpointAysnc(String endpoint, String studyId, String env)
			throws ExecutionException, InterruptedException, TimeoutException {
		LOGGER.info("STARTED postDataToEndpointAysnc");
		String bearerToken = "";
		HttpHeaders headers = new HttpHeaders();
		AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();
		headers.setContentType(MediaType.APPLICATION_JSON);
		// headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccessControlRequestMethod(HttpMethod.POST);
		LOGGER.info("BEFORE TRY");

		try {
			// GoogleCredentials googleCredentials =
			// GoogleCredentials.getApplicationDefault();

			// googleCredentials.refreshAccessToken().getTokenValue();

			// LOGGER.info("googleCredentials ::" + googleCredentials);

			/****************************** NEW MOVE ***************************/

			ServiceAccountCredentials saCreds = ServiceAccountCredentials
					.fromStream(new ClassPathResource("ct-wearables-portal-pf-dba36e55aed3.json").getInputStream());

			/*
			 * ServiceAccountCredentials saCreds = ServiceAccountCredentials .fromStream(
			 * new ByteArrayInputStream(cfAuthJsonText.getBytes()) );
			 */

			saCreds = (ServiceAccountCredentials) saCreds
					.createScoped(Arrays.asList("https://www.googleapis.com/auth/iam"));
			IdTokenCredentials tokenCredential = IdTokenCredentials.newBuilder().setIdTokenProvider(saCreds)
					.setTargetAudience(
							"https://us-central1-ct-wearables-portal-pf.cloudfunctions.net/WP-CF-GetPreludeFields")
					.build();
			LOGGER.info("tokenCredential " + tokenCredential);
			LOGGER.info("tokenCredential " + tokenCredential.refreshAccessToken());
			bearerToken = tokenCredential.refreshAccessToken().getTokenValue();
			/****************************** NEW MOVE end ***************************/

			LOGGER.info("BEARER Token NWE ::" + bearerToken);
		} catch (IOException e) {
			LOGGER.info("<<<< EXCEPTION OCCURED >>>>>");
			e.printStackTrace();
		}
		headers.set("Authorization", "Bearer " + bearerToken);

		LOGGER.info("postDataToEndpoint::StudyId, ENV" + studyId + ":" + env);
		HttpEntity<String> requestEntity = new HttpEntity<String>(
				"{\"StudyId\":\"" + studyId + "\", \"Env\":\"" + env + "\"}", headers);

		// final DeferredResult<String> result = new DeferredResult<>();
		ListenableFuture<ResponseEntity<String>> future = asyncRestTemplate.postForEntity(endpoint, requestEntity,
				String.class);

		future.addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {

			@Override
			public void onFailure(Throwable ex) {
				LOGGER.error("onFaileusre::" + ex.getMessage());
			}

			@Override
			public void onSuccess(ResponseEntity<String> value) {
				LOGGER.error("onSuccess::" + value.getBody());
				LOGGER.error("status code::" + value.getStatusCodeValue());
			}
		});

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public StudyBasicDetails getStudyBasicDetails(int studyId) throws ServiceExecutionException {
		StudyBasicDetails study = new StudyBasicDetails();
		List<StudyTreatmentGroup> treatmentGroups = new ArrayList<>();
		List<StudyPhase> studyPhases = new ArrayList<>();
		LOGGER.debug("getStudyBasicDetails called");
		try {
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("p_study_id", studyId);

			LOGGER.info("getStudyBasicDetails inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_GET_BASIC_DETAILS_BY_ID,
					inputParams);
			LOGGER.info("getStudyBasicDetails outParams are {}", outParams);

			Iterator<Entry<String, Object>> itr = outParams.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();

				if (key.equals(SQLConstants.RESULT_SET_1)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(studyData -> {
						study.setStudyId((Integer) studyData.get("STUDY_ID"));
						study.setStudyName((String) studyData.get("STUDY_NAME"));
						study.setDescription((String) studyData.get("DESCRIPTION") == null ? ""
								: (String) studyData.get("DESCRIPTION"));
						study.setPrincipalInvestigator((String) studyData.get("PRINCIPLE_INVESTIGATOR") == null ? ""
								: (String) studyData.get("PRINCIPLE_INVESTIGATOR"));
						Date startDate = (Date) studyData.get("START_DATE");
						study.setStartDate(startDate != null ? startDate.toLocalDate() : null);

						study.setStatus((Integer) studyData.get("STUDY_STATUS_ID"));
						study.setStudyStatus((String) studyData.get("STUDY_STATUS"));
						Long totalActivePets = (Long) studyData.get("TOTAL_ACTIVE_PETS");
						study.setTotalActivePets(Integer.parseInt(String.valueOf(totalActivePets)));

						Boolean notificationEnabled = (Boolean) studyData.get("NOTIFICATIONS_ENABLED");
						if (notificationEnabled != null) {
							study.setIsNotificationEnable(notificationEnabled);
						} else {
							study.setIsNotificationEnable(notificationEnabled);
						}

						study.setIsExtetnal((Integer) studyData.get("IS_EXTERNAL"));
						study.setStudyLocationId((Integer) studyData.get("STUDY_LOCATION_ID"));
						study.setStudyLocationName((String) studyData.get("STUDY_LOCATION"));
						study.setExternalLink((String) studyData.get("URL"));
					});
				}
				if (key.equals(SQLConstants.RESULT_SET_2)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(groupData -> {
						StudyTreatmentGroup studyGroup = new StudyTreatmentGroup();

						studyGroup.setTreamentGroupId((Integer) groupData.get("TREATMENT_GROUP_ID"));
						studyGroup.setGroupName((String) groupData.get("GROUP_NAME"));
						studyGroup.setDescription((String) groupData.get("DESCRIPTION"));
						studyGroup.setMinPets((Integer) groupData.get("MIN_PETS"));
						studyGroup.setMaxPets((Integer) groupData.get("MAX_PETS"));
						treatmentGroups.add(studyGroup);
					});
				}

				if (key.equals(SQLConstants.RESULT_SET_3)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(phaseData -> {
						StudyPhase studyPhase = new StudyPhase();
						studyPhase.setStudyPhaseConfigId((Integer) phaseData.get("STUDY_PHASE_CONFIG_ID"));
						studyPhase.setStudyPhaseId((Integer) phaseData.get("STUDY_PHASE_ID"));
						studyPhase.setStudyPhase((String) phaseData.get("STUDY_PHASE"));
						studyPhase.setDuration((Integer) phaseData.get("DURATION"));
						studyPhase.setDurationUnitId((Integer) phaseData.get("DURATION_UNIT_ID"));
						studyPhase.setDurationIndays((Integer) phaseData.get("DURATION_IN_DAYS"));
						studyPhase.setIsChecked(Boolean.TRUE);
						studyPhases.add(studyPhase);
					});
				}
			}
			study.setTreatmentGroups(treatmentGroups);
			study.setStudyPhases(studyPhases);
		} catch (Exception e) {
			LOGGER.error("error while fetching getStudyBasicDetails", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return study;
	}

	@Override
	public void addStudyDiet(StudyDietRequest studyDietRequest, int studyId, Integer userId)
			throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_study_id", studyId);
			inputParams.put("p_study_diet_json", mapper.writeValueAsString(studyDietRequest.getStudyDietList()));

			List<Integer> studyDietIds = studyDietRequest.getStudyDietList().stream()
					.filter(e -> (e.getDietId() != null && e.getDietId() > 0)).map(e -> e.getDietId())
					.collect(Collectors.toList());
			inputParams.put("p_active_diet_ids", StringUtils.join(studyDietIds, ','));
			inputParams.put("p_created_by", userId);

			LOGGER.info("addStudyDiet inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_DIET_INSERT, inputParams);
			LOGGER.info("addStudyDiet outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("Diets successfully mapped with study {}", studyId);
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"addStudyDiet service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList(
									new WearablesError(WearablesErrorCode.STUDY_DIET_ALREADY_MAPPED_CANNOT_DELETE,errorMsg)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException | JsonProcessingException e) {
			LOGGER.error("error while executing addStudyDiet ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getStudyDietListCount(StudyDietFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getStudyDietListCount called");
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		try {
			counts = selectForObject(SQLConstants.STUDY_DIET_GET_LIST_COUNT, String.class, filter.getSearchText(),
					filter.getStudyId());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getStudyDietListCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<StudyDietListDTO> getStudyDiets(StudyDietFilter filter) throws ServiceExecutionException {
		List<StudyDietListDTO> studyDietList = new ArrayList<>();
		LOGGER.debug("getStudyDiets called");
		try {
			jdbcTemplate.query(SQLConstants.STUDY_DIET_GET_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					StudyDietListDTO studyDietListDTO = new StudyDietListDTO();
					studyDietListDTO.setStudyDietId(rs.getInt("STUDY_DIET_ID"));
					studyDietListDTO.setDietName(rs.getString("DIET_NAME"));
					studyDietListDTO.setDietNumber(rs.getString("DIET_NUMBER"));
					studyDietListDTO.setDietId(rs.getInt("DIET_ID"));
					studyDietListDTO.setCreatedDate(rs.getTimestamp("CREATED_DATE").toLocalDateTime());
					studyDietList.add(studyDietListDTO);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getStudyId());

		} catch (Exception e) {
			LOGGER.error("error while fetching getStudyDiets", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return studyDietList;
	}

	@Override
	public void validateStudyDiet(int studyId, int studyDietId, int modifiedBy) throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_study_id", studyId);
		inputParams.put("p_study_diet_id", studyDietId);
		inputParams.put("p_modified_by", modifiedBy);
		try {
			LOGGER.info("deleteStudyDiet inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_DIET_VALIDATE_FOR_DELETE, inputParams);
			LOGGER.info("deleteStudyDiet outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isNotEmpty(errorMsg) || (int) outParams.get("out_flag") < NumberUtils.INTEGER_ONE) {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"deleteStudyDiet service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList(
									new WearablesError(WearablesErrorCode.STUDY_DIET_ALREADY_MAPPED_CANNOT_DELETE, errorMsg)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing deleteStudyDiet ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public void addStudyPlan(StudyPlanRequest studyPlanRequest, int studyId, Integer userId)
			throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_study_id", studyId);
			inputParams.put("p_plans_subscribed",
					StringUtils.join(studyPlanRequest.getPlansSubscribed(), StringLiterals.COMMA.getCode()));
			inputParams.put("p_created_by", userId);

			LOGGER.info("addStudyPlan inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_PLAN_INSERT, inputParams);
			LOGGER.info("addStudyPlan outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("Plan successfully mapped with study {}", studyId);
			} else {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing addStudyPlan ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public List<PlanSubscribed> getStudyPlans(int studyId) throws ServiceExecutionException {
		List<PlanSubscribed> studyPlanList = new ArrayList<>();
		LOGGER.debug("getStudyPlans called");
		try {
			jdbcTemplate.query(SQLConstants.STUDY_PLAN_GET_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PlanSubscribed plan = new PlanSubscribed();
					plan.setPlanId(rs.getInt("PLAN_ID"));
					plan.setPlanName(rs.getString("PLAN_NAME"));
					plan.setSubscribedDate(rs.getDate("START_DATE").toLocalDate());
					studyPlanList.add(plan);
				}
			}, studyId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getStudyPlans", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return studyPlanList;
	}

	@Override
	public void addStudyMobileAppConfig(StudyMobileAppConfigRequest studyMobileAppConfigRequest, int studyId,
			Integer userId) throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_study_id", studyId);
			inputParams.put("p_mobile_app_configs", StringUtils.join(studyMobileAppConfigRequest.getMobileAppConfigs(),
					StringLiterals.COMMA.getCode()));
			inputParams.put("p_weight_unit", studyMobileAppConfigRequest.getWeightUnit());
			inputParams.put("p_entsm_scale_start_date", studyMobileAppConfigRequest.getEntsmScaleStartDate());
			inputParams.put("p_entsm_scale_end_date", studyMobileAppConfigRequest.getEntsmScaleEndDate());
			inputParams.put("p_forward_motion_goal_setting", studyMobileAppConfigRequest.getForwardMotionGoalSetting());
			inputParams.put("p_created_by", userId);

			LOGGER.info("addStudyMobileAppConfig inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_MOBILE_APP_CONFIG_INSERT,
					inputParams);
			LOGGER.info("addStudyMobileAppConfig outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("StudyMobileAppConfig successfully mapped with study {}", studyId);
			} else {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing addStudyMobileAppConfig ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public StudyMobileAppConfigResponse getStudyMobielAppConfigs(int studyId) throws ServiceExecutionException {
		StudyMobileAppConfigResponse response = new StudyMobileAppConfigResponse();
		LOGGER.debug("getStudyMobielAppConfigs called");
		try {
			jdbcTemplate.query(SQLConstants.STUDY_MOBILE_APP_CONFIG_GET_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {

					String weightUnit = rs.getString("WEIGHT_UNIT") != null ? rs.getString("WEIGHT_UNIT") : "";

					String appConfigs = rs.getString("MOBILE_APP_CONFIGS");
					response.setMobileAppConfigs(appConfigs == null ? new ArrayList<Integer>()
							: Arrays.asList(appConfigs.split(",", -1)).stream().map(Integer::parseInt)
									.collect(Collectors.toList()));
					response.setWeightUnit(weightUnit);

					String entsmScleStartDate = rs.getString("ENTSM_SCALE_START_DATE");
					String entsmScleEndDate = rs.getString("ENTSM_SCALE_END_DATE");
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					response.setEntsmScaleStartDate(
							entsmScleStartDate != null ? LocalDate.parse(entsmScleStartDate, formatter) : null);
					response.setEntsmScaleEndDate(
							entsmScleEndDate != null ? LocalDate.parse(entsmScleEndDate, formatter) : null);

					response.setForwardMotionGoalSetting(rs.getInt("FM_GOAL_DUR_MINS"));
				}
			}, studyId);

		} catch (Exception e) {
			LOGGER.error("error while fetching getStudyMobielAppConfigs", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return response;
	}

	@Override
	public void addNotes(StudyNotesRequest studyNotesRequest, int studyId, int phaseId, int userId)
			throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_study_id", studyId);
			inputParams.put("p_phase_id", phaseId);
			inputParams.put("p_notes", studyNotesRequest.getNotes());
			inputParams.put("p_created_by", userId);

			LOGGER.info("addNotes inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_NOTES_INSERT, inputParams);
			LOGGER.info("addNotes outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("Study Notes successfully mapped with study {}", studyId);
			} else {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing addNotes ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public StudyNotesListResponse getStudyNotes(int studyId, int phaseId) throws ServiceExecutionException {
		StudyNotesListResponse response = new StudyNotesListResponse();
		List<StudyNotes> studyNotesList = new ArrayList<>();
		LOGGER.debug("getStudyNotes called");
		try {
			jdbcTemplate.query(SQLConstants.STUDY_NOTES_GET_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					StudyNotes notes = new StudyNotes();
					notes.setStudyNoteId(rs.getInt("STUDY_NOTE_ID"));
					notes.setNotes(rs.getString("CONTENT"));
					notes.setCreatedDate(rs.getTimestamp("CREATED_DATE").toLocalDateTime());
					notes.setUserName(rs.getString("USER_NAME"));
					studyNotesList.add(notes);
				}
			}, studyId, phaseId);
			response.setNotes(studyNotesList);
		} catch (Exception e) {
			LOGGER.error("error while fetching getStudyNotes", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return response;
	}

	@Override
	public void addStudyPreludeConfig(StudyPreludeConfigRequest studyPreludeConfigRequest, int studyId, Integer userId)
			throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_study_id", studyId);
			inputParams.put("p_prelude_mandatory_associated",
					StringUtils.join(studyPreludeConfigRequest.getPreludeMandatory(), StringLiterals.COMMA.getCode()));
			inputParams.put("p_prelude_associated",
					StringUtils.join(studyPreludeConfigRequest.getPreludeAssociated(), StringLiterals.COMMA.getCode()));
			inputParams.put("p_created_by", userId);

			LOGGER.info("addStudyPreludeConfig inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_PRELUDE_CONFIG_INSERT, inputParams);
			LOGGER.info("addStudyPreludeConfig outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("Prelude Configuration successfully mapped with study {}", studyId);
			} else {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing addStudyPreludeConfig ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public StudyPreludeConfigResponse getStudyPreludeConfig(int studyId) throws ServiceExecutionException {
		StudyPreludeConfigResponse resoponse = new StudyPreludeConfigResponse();
		List<PreludeMandatory> preludeMandatoryList = new ArrayList<>();
		LOGGER.debug("getStudyPreludeConfig called");
		try {
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("p_study_id", studyId);

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_PRELUDE_CONFIG_GET_LIST,
					inputParams);
			LOGGER.info("outParams are {}", outParams);

			Iterator<Entry<String, Object>> itr = outParams.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();

				if (key.equals(SQLConstants.RESULT_SET_1)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(studyData -> {
						String studyPrelude = (String) studyData.get("STUDY_PRELUDE");
						resoponse.setPreludeAssociated(getAssociatedPreludeList(studyPrelude));
					});
				}
				if (key.equals(SQLConstants.RESULT_SET_2)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(mandatoryData -> {
						PreludeMandatory preludeMandatory = new PreludeMandatory();
						preludeMandatory.setCategory((String) mandatoryData.get("CATEGORY"));
						preludeMandatory.setExternalStudyId((String) mandatoryData.get("EXT_STUDY_ID"));
						preludeMandatory.setExtractDefId((Integer) mandatoryData.get("EXTRACT_DEF_ID"));
						preludeMandatory.setFieldName((String) mandatoryData.get("FIELD"));
						preludeMandatory.setFormName((String) mandatoryData.get("FORM"));
						preludeMandatory.setLabel((String) mandatoryData.get("WEARABLE_FIELD_NAME"));
						preludeMandatory.setPreludeGroup((String) mandatoryData.get("EXTRACT_GROUP"));
						preludeMandatoryList.add(preludeMandatory);
					});
				}
			}
			resoponse.setPreludeMandatory(preludeMandatoryList);
		} catch (Exception e) {
			LOGGER.error("error while fetching getStudyPreludeConfig", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return resoponse;
	}

	@Override
	public void addStudyActivityFactorConfig(StudyActivityFactorRequest studyActivityFactorRequest, int studyId,
			Integer userId) throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_study_id", studyId);
			inputParams.put("p_activity_factor_config_json", studyActivityFactorRequest.getActivityFactorConfig());
			inputParams.put("p_created_by", userId);

			LOGGER.info("addStudyActivityFactorConfig inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_ACTIVITY_FACTOR_CONFIG_INSERT,
					inputParams);
			LOGGER.info("addStudyActivityFactorConfig outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("Actvity Factor Configuration successfully mapped with study {}", studyId);
			} else {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing addStudyActivityFactorConfig ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public StudyActivityFactorResponse getStudyActivityFactorConfig(int studyId) throws ServiceExecutionException {
		StudyActivityFactorResponse response = new StudyActivityFactorResponse();
		LOGGER.debug("getStudyActivityFactorConfig called");
		try {
			jdbcTemplate.query(SQLConstants.STUDY_ACTIVITY_FACTOR_CONFIG_GET_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					ActivityFactorConfig activityFactorConfig = new ActivityFactorConfig();
					activityFactorConfig.setGoogleSheetUrl(rs.getString("URL"));
					activityFactorConfig.setHasPrelude(rs.getInt("HAS_PRELUDE"));
					activityFactorConfig.setStartDate(rs.getString("START_DATE"));
					activityFactorConfig.setEndDate(rs.getString("END_DATE"));
					response.setActivityFactorConfig(activityFactorConfig);
				}
			}, studyId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getStudyActivityFactorConfig", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return response;
	}

	// ============================================================================================================================

	@Override
	public void associatePlan(int studyId, int planId, LocalDate subscriptionDate, Integer userId) {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_study_id", studyId);
		inputParams.put("p_plan_id", planId);
		inputParams.put("p_subscription_date", subscriptionDate);
		inputParams.put("p_created_by", userId);
		try {
			LOGGER.info("associatePlan inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_ASSOCIATE_PLAN, inputParams);
			LOGGER.info("associatePlan outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("Plan has been associated to Study successfully");
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"associatePlan service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.PLAN_ALREADY_MAPPED_TO_STUDY)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing addStudy ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public void disassociatePlan(int studyId, int planId, Integer userId) {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_study_id", studyId);
		inputParams.put("p_plan_id", planId);
		inputParams.put("p_modified_by", userId);
		try {
			LOGGER.info("disassociatePlan inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_DISASSOCIATE_PLAN, inputParams);
			LOGGER.info("disassociatePlan outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("Plan has been disassociated with Study successfully");
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException("updateStudy service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList(
									new WearablesError(WearablesErrorCode.PLAN_ALREADY_MAPPED_TO_STUDY, errorMsg)));

				} else if (statusFlag == -3) {

					throw new ServiceExecutionException("updateStudy service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.STUDY_NAME_ALREADY_EXISTS, studyId)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing addStudy ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public List<PetListDTO> getAssociatedPets(int studyId) {
		List<PetListDTO> petList = new ArrayList<>();
		LOGGER.debug("getAssociatedPets called");
		try {
			jdbcTemplate.query(SQLConstants.STUDY_GET_ASSOCIATED_PETS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetListDTO petListDTO = new PetListDTO();
					petListDTO.setPetId(rs.getInt("PET_ID"));
					petListDTO.setPetName(rs.getString("PET_NAME"));
					petListDTO.setPetPhoto(rs.getString("PHOTO_NAME"));
					petListDTO.setIsActive(rs.getBoolean("IS_ACTIVE"));
					petListDTO.setPetStatus(rs.getString("STATUS_NAME"));
					petListDTO.setPetStudyId(rs.getInt("PET_STUDY_ID"));
					petList.add(petListDTO);
				}
			}, studyId);

		} catch (Exception e) {
			LOGGER.error("error while fetching getAssociatedPets", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getStudyListCount(StudyFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getStudyListCount called");
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		try {
			counts = selectForObject(SQLConstants.STUDY_GET_LIST_COUNT, String.class, filter.getSearchText(),
					filter.getPlanId(), filter.getStatusId(), filter.getUserId(), filter.getRoleTypeId());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getStudyListCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<StudyListDTO> getStudies(StudyFilter filter) throws ServiceExecutionException {
		List<StudyListDTO> studyList = new ArrayList<>();
		LOGGER.debug("getStudies called");
		try {
			jdbcTemplate.query(SQLConstants.STUDY_GET_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					StudyListDTO studyListDTO = new StudyListDTO();
					// studyListDTO.setSlNumber(rs.getInt("slNo"));
					studyListDTO.setStudyId(rs.getInt("STUDY_ID"));
					studyListDTO.setActionId(rs.getInt("ACTION_ID"));
					studyListDTO.setStudyName(rs.getString("STUDY_NAME"));
					studyListDTO.setPlanName(rs.getString("PLAN_NAME"));
					studyListDTO.setStartDate(rs.getString("START_DATE") == null ? "" : rs.getString("START_DATE"));
					studyListDTO.setEndDate(rs.getString("END_DATE") == null ? "" : rs.getString("END_DATE"));
					studyListDTO.setCreatedDate(rs.getTimestamp("CREATED_DATE").toLocalDateTime());
					studyListDTO.setStudyStatusId(rs.getInt("STUDY_STATUS_ID"));
					studyListDTO.setStudyStatus(rs.getString("STUDY_STATUS"));
					studyList.add(studyListDTO);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getPlanId(), filter.getStatusId(), filter.getUserId(), filter.getRoleTypeId());

		} catch (Exception e) {
			LOGGER.error("error while fetching getStudies", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return studyList;
	}

	@Override
	public List<StudyListDTO> getStudyList(int userId, String includeFuture, String includeVirtual)
			throws ServiceExecutionException {
		List<StudyListDTO> studyList = new ArrayList<>();
		LOGGER.debug("getStudyList called");
		try {
			jdbcTemplate.query(SQLConstants.STUDY_GET_ALL_STUDY_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					StudyListDTO studyListDTO = new StudyListDTO();
					studyListDTO.setStudyId(rs.getInt("STUDY_ID"));
					studyListDTO.setStudyName(rs.getString("STUDY_NAME"));
					studyListDTO.setPrincipalInvestigator(rs.getString("PRINCIPLE_INVESTIGATOR"));
					studyListDTO.setIsActive(rs.getBoolean("IS_ACTIVE"));
					studyListDTO.setStartDate(rs.getString("START_DATE") == null ? "" : rs.getString("START_DATE"));
					studyListDTO.setEndDate(rs.getString("END_DATE") == null ? "" : rs.getString("END_DATE"));
					studyListDTO.setCreatedDate(rs.getTimestamp("CREATED_DATE").toLocalDateTime());
					studyListDTO.setIsExternal(rs.getInt("IS_EXTERNAL"));
					studyListDTO.setStudyDescription(rs.getString("DESCRIPTION"));
					studyList.add(studyListDTO);
				}
			}, userId, includeFuture, includeVirtual);
		} catch (Exception e) {
			LOGGER.error("error while fetching getStudyList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return studyList;
	}

	@Override
	public List<StudyListDTO> getStudiesByPetParentAndPet(int petParentId, int petId) throws ServiceExecutionException {
		List<StudyListDTO> studyList = new ArrayList<>();
		LOGGER.debug("getStudyList called");
		try {
			jdbcTemplate.query(SQLConstants.STUDY_GET_STUDY_LIST_BY_PET_PARENT_AND_PET, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					StudyListDTO studyListDTO = new StudyListDTO();
					studyListDTO.setStudyId(rs.getInt("STUDY_ID"));
					studyListDTO.setStudyName(rs.getString("STUDY_NAME"));
					studyList.add(studyListDTO);
				}
			}, petParentId, petId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getStudyList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return studyList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public StudyDTO getStudyById(int studyId) throws ServiceExecutionException {
		final StudyDTO study = new StudyDTO();
		List<StudyNotesResponse> notes = new ArrayList<>();
		List<PreludeMandatory> preludeMandatoryList = new ArrayList<>();
		LOGGER.debug("getStudyById called");
		try {
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("p_study_id", studyId);

			LOGGER.info("getStudyById inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_GET_BY_ID, inputParams);
			LOGGER.info("getStudyById outParams are {}", outParams);

			Iterator<Entry<String, Object>> itr = outParams.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();

				if (key.equals(SQLConstants.RESULT_SET_1)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(studyData -> {
						study.setStudyId((Integer) studyData.get("STUDY_ID"));
						study.setStudyName((String) studyData.get("STUDY_NAME"));
						study.setDescription((String) studyData.get("DESCRIPTION") == null ? ""
								: (String) studyData.get("DESCRIPTION"));
						study.setPrincipalInvestigator((String) studyData.get("PRINCIPLE_INVESTIGATOR") == null ? ""
								: (String) studyData.get("PRINCIPLE_INVESTIGATOR"));
						Date startDate = (Date) studyData.get("START_DATE");
						Date endDate = (Date) studyData.get("END_DATE");
						study.setStartDate(startDate != null ? startDate : null);
						study.setEndDate(endDate != null ? endDate : null);
						Boolean isActive = (Boolean) studyData.get("IS_ACTIVE");
						if (isActive != null) {
							study.setStatus(isActive ? 1 : 0);
						} else {
							study.setStatus(0);
						}

						study.setAlgorithmId((Integer) studyData.get("ALGORITHM_MASTER_ID"));
						study.setAlgorithmName((String) studyData.get("ALGORITHM_NAME"));

						String appConfigs = (String) studyData.get("MOBILE_APP_CONFIGS");
						String studyPlans = (String) studyData.get("STUDY_PLANS");
						String studyQuestionnaires = (String) studyData.get("STUDY_QUESTIONNAIRES");
						String studyPushNotifications = (String) studyData.get("STUDY_PUSH_NOTIFICATIONS");
						String studyImageSclaes = (String) studyData.get("STUDY_IMAGE_SCALES");
						String studyPrelude = (String) studyData.get("STUDY_PRELUDE");
//						String studyPreludeMandatoru = (String) studyData.get("STUDY_PRELUDE_MANDATORY");
						String preludeUrl = (String) studyData.get("URL") != null ? (String) studyData.get("URL") : "";
						String weightUnit = (String) studyData.get("WEIGHT_UNIT") != null
								? (String) studyData.get("WEIGHT_UNIT")
								: "";

						study.setMobileAppConfigs(appConfigs == null ? new ArrayList<Integer>()
								: Arrays.asList(appConfigs.split(",", -1)).stream().map(Integer::parseInt)
										.collect(Collectors.toList()));
						study.setWeightUnit(weightUnit);

						String entsmScleStartDate = (String) studyData.get("ENTSM_SCALE_START_DATE");
						String entsmScleEndDate = (String) studyData.get("ENTSM_SCALE_END_DATE");
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						study.setEntsmScaleStartDate(
								entsmScleStartDate != null ? LocalDate.parse(entsmScleStartDate, formatter) : null);
						study.setEntsmScaleEndDate(
								entsmScleEndDate != null ? LocalDate.parse(entsmScleEndDate, formatter) : null);

						study.setPlansSubscribed(getPlanSubscribedList(studyPlans));
						study.setQuestionnairesAssociated(getAssociatedQuestionnaireList(studyQuestionnaires));
						study.setPushNotificationsAssociated(getAssociatedPushNotificationList(studyPushNotifications));
						study.setImageScoringSaclesAssociated(getAssociatedImageScaleList(studyImageSclaes));
						study.setPreludeAssociated(getAssociatedPreludeList(studyPrelude));
						study.setPreludeUrl(preludeUrl);
						study.setIsDataLoadSuccess((Integer) studyData.get("IS_DATA_LOAD_SUCCESS"));
						Long totalPets = (Long) studyData.get("TOTAL_PETS");
						Long totalActivePets = (Long) studyData.get("TOTAL_ACTIVE_PETS");
						Long totalInactivePets = (Long) studyData.get("TOTAL_INACTIVE_PETS");
						Boolean notificationEnabled = (Boolean) studyData.get("NOTIFICATIONS_ENABLED");
						if (notificationEnabled != null) {
							study.setIsNotificationEnable(notificationEnabled ? 1 : 0);
						} else {
							study.setIsNotificationEnable(0);
						}
						study.setIsExternal((Integer) studyData.get("IS_EXTERNAL"));
						study.setTotalPets(Integer.parseInt(String.valueOf(totalPets)));
						study.setTotalActivePets(Integer.parseInt(String.valueOf(totalActivePets)));
						study.setTotalInactivePets(Integer.parseInt(String.valueOf(totalInactivePets)));
					});
				}
				if (key.equals(SQLConstants.RESULT_SET_2)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(noteData -> {
						StudyNotesResponse note = new StudyNotesResponse();
						note.setStudyNoteId((Integer) noteData.get("STUDY_NOTE_ID"));
						note.setContent((String) noteData.get("CONTENT"));
						// Timestamp createdDate = (Timestamp) noteData.get("CREATED_DATE");
						// note.setCreatedDate(createdDate != null ?
						// createdDate.toLocalDateTime().toLocalDate() : null);
						note.setCreatedDate((LocalDateTime) noteData.get("CREATED_DATE"));
						note.setUserName((String) noteData.get("USER_NAME"));
						notes.add(note);
					});
				}

				if (key.equals(SQLConstants.RESULT_SET_3)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(mandatoryData -> {
						PreludeMandatory preludeMandatory = new PreludeMandatory();

						preludeMandatory.setCategory((String) mandatoryData.get("CATEGORY"));
						preludeMandatory.setExternalStudyId((String) mandatoryData.get("EXT_STUDY_ID"));
						preludeMandatory.setExtractDefId((Integer) mandatoryData.get("EXTRACT_DEF_ID"));
						preludeMandatory.setFieldName((String) mandatoryData.get("FIELD"));
						preludeMandatory.setFormName((String) mandatoryData.get("FORM"));
						preludeMandatory.setLabel((String) mandatoryData.get("WEARABLE_FIELD_NAME"));
						preludeMandatory.setPreludeGroup((String) mandatoryData.get("EXTRACT_GROUP"));
						preludeMandatoryList.add(preludeMandatory);
					});
				}

				if (key.equals(SQLConstants.RESULT_SET_4)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					ActivityFactorConfig activityFactorConfig = new ActivityFactorConfig();
					list.forEach(activityFactorData -> {
						activityFactorConfig.setGoogleSheetUrl((String) activityFactorData.get("URL"));
						activityFactorConfig.setStartDate(activityFactorData.get("START_DATE") == null ? null
								: activityFactorData.get("START_DATE").toString());
						activityFactorConfig.setEndDate(activityFactorData.get("END_DATE") == null ? null
								: activityFactorData.get("END_DATE").toString());
						activityFactorConfig.setHasPrelude((Integer) activityFactorData.get("HAS_PRELUDE"));
					});
					study.setActivityFactorConfig(activityFactorConfig);
				}

				if (key.equals(SQLConstants.RESULT_SET_5)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					List<Algorithm> algorithmHistory = list.stream().map(algorithmData -> {
						Algorithm algorithm = new Algorithm();
						algorithm.setAlgorithmId((Integer) algorithmData.get("ALGORITHM_MASTER_ID"));
						algorithm.setAlgorithmName((String) algorithmData.get("ALGORITHM_NAME"));
						algorithm.setStartDate(algorithmData.get("START_DATE") == null ? null
								: (LocalDateTime) algorithmData.get("START_DATE"));
						algorithm.setEndDate(algorithmData.get("END_DATE") == null ? null
								: (LocalDateTime) algorithmData.get("END_DATE"));
						return algorithm;
					}).collect(Collectors.toList());
					study.setAlgorithmHistory(algorithmHistory);
				}
			}
			study.setPreludeMandatory(preludeMandatoryList);
			study.setNotes(notes);
		} catch (Exception e) {
			// e.printStackTrace();
			LOGGER.error("error while fetching getStudyById", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return study;
	}

	@Override
	public void deleteStudy(int studyId, int modifiedBy) throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_study_id", studyId);
		inputParams.put("p_modified_by", modifiedBy);
		try {
			LOGGER.info("deleteStudy inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_DELETE, inputParams);
			LOGGER.info("deleteStudy outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isNotEmpty(errorMsg) || (int) outParams.get("out_flag") < NumberUtils.INTEGER_ONE) {
				if (statusFlag == -2) {
					throw new ServiceExecutionException("deleteStudy service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.STUDY_CANNOT_DELETE_ACTIVE)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing deletePlan ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public void associateQuestionnaire(int studyId, QuestionnaireAssociated questionnaireAssociated, Integer userId)
			throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_study_id", studyId);
		inputParams.put("p_questionnaire_id", questionnaireAssociated.getQuestionnaireId());
		inputParams.put("p_start_date", questionnaireAssociated.getStartDate());
		inputParams.put("p_end_date", questionnaireAssociated.getEndDate());
		inputParams.put("p_created_by", userId);
		try {
			LOGGER.info("associateQuestionnaire inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_ASSOCIATE_QUESTIONNAIRE,
					inputParams);
			LOGGER.info("associateQuestionnaire outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("Questionnaire has been associated successfully, Study id is ", studyId);
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"associateQuestionnaire service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.QUESTIONNAIRE_ALREADY_MAPPED_TO_STUDY,
									questionnaireAssociated.getQuestionnaireName())));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing associateQuestionnaire ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public void updateStudyQuestionnaire(int studyId, QuestionnaireAssociated questionnaireAssociated, Integer userId)
			throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_study_id", studyId);
		inputParams.put("p_questionnaire_id", questionnaireAssociated.getQuestionnaireId());
		inputParams.put("p_start_date", questionnaireAssociated.getStartDate());
		inputParams.put("p_end_date", questionnaireAssociated.getEndDate());
		inputParams.put("p_modofied_by", userId);
		try {
			LOGGER.info("updateStudyQuestionnaire inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_UPDATE_QUESTIONNAIRE, inputParams);
			LOGGER.info("updateStudyQuestionnaire outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("Questinnaire mapping details has been updated successfully, Study id is ", studyId);
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"updateSudyQuestionnaire service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList(
									new WearablesError(WearablesErrorCode.INVALID_STUDY_QUESTIONNAIRE_ASSOCAITION)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing updateSudyQuestionnaire ", e);
			throw new ServiceExecutionException(e.getMessage());
		}

	}

	@Override
	public void disassociateQuestionnaire(int studyId, int questionnaireId, Integer userId)
			throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_study_id", studyId);
		inputParams.put("p_questionnaire_id", questionnaireId);
		inputParams.put("p_modified_by", userId);
		try {
			LOGGER.info("disassociateQuestionnaire inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_DISASSOCIATE_QUESTIONNAIRE,
					inputParams);
			LOGGER.info("disassociateQuestionnaire outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("Questionnaire has been disassociated with Study successfully");
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"disassociateQuestionnaire service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList(
									new WearablesError(WearablesErrorCode.INVALID_STUDY_QUESTIONNAIRE_ASSOCAITION)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing disassociateQuestionnaire ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public List<QuestionnaireListDTO> getAssociatedQuestionnaires(int studyId) throws ServiceExecutionException {
		List<QuestionnaireListDTO> questionnaireList = new ArrayList<>();
		LOGGER.debug("getAssociatedQuestionnaires called");
		try {
			jdbcTemplate.query(SQLConstants.STUDY_GET_ASSOCIATED_QUESTIONNAIRES, new RowCallbackHandler() {
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
			}, studyId);

		} catch (Exception e) {
			LOGGER.error("error while fetching getAssociatedQuestionnaires", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return questionnaireList;
	}

	/*---------------------- STUDY NOTIFICATIONS SERVICES -----------------------------------*/

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getStudyNotificationCount(BaseFilter filter) throws ServiceExecutionException {
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		LOGGER.debug("getgetStudyNotificationCount called");
		try {
			counts = selectForObject(SQLConstants.STUDY_NOTIFICATION_GET_LIST_COUNT, String.class,
					filter.getSearchText(), filter.getStatusId(), filter.getUserId(), filter.getRoleTypeId());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getgetStudyNotificationCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<StudyNotification> getStudyNotifications(BaseFilter filter) {
		List<StudyNotification> studyNotifications = new ArrayList<>();
		LOGGER.debug("getStudyNotifications called");
		try {
			jdbcTemplate.query(SQLConstants.STUDY_NOTIFICATION_GET_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					StudyNotification studyNotification = new StudyNotification();
					studyNotification.setSlNumber(rs.getInt("slNo"));
					studyNotification.setStudyId(rs.getInt("STUDY_ID"));
					studyNotification.setStudyName(rs.getString("STUDY_NAME"));
					studyNotification.setIsNotificationEnable(rs.getBoolean("NOTIFICATIONS_ENABLED"));
					studyNotification.setIsActive(rs.getBoolean("IS_ACTIVE"));
					studyNotifications.add(studyNotification);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getStatusId(), filter.getUserId(), filter.getRoleTypeId());

		} catch (Exception e) {
			LOGGER.error("error while fetching getStudyNotifications", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return studyNotifications;
	}

	@Override
	public void updateStudyNotificationStatus(StudyNotificationRequest studyNotificationRequest, int modifiedBy)
			throws ServiceExecutionException {

		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_study_ids",
					mapper.writeValueAsString(studyNotificationRequest.getStudyNotificationStatusList()));
			inputParams.put("p_modified_by", modifiedBy);

			LOGGER.info("updateStudyNotificationStatus inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_NOTIFICATION_UPDATE_STATUS,
					inputParams);
			LOGGER.info("updateStudyNotificationStatus outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			if (StringUtils.isNotEmpty(errorMsg) || (int) outParams.get("out_flag") < NumberUtils.INTEGER_ONE) {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (SQLException | JsonProcessingException e) {
			LOGGER.error("error while executing updateStudyNotificationStatus ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	/* ----------- PRIVATE METHODS --------------- */
	private List<PlanSubscribed> getPlanSubscribedList(String plansSubscribed) {
		List<String> plansSubscribedStr = plansSubscribed == null ? new ArrayList<String>()
				: Arrays.asList(plansSubscribed.split(StringLiterals.SEPERATOR.getCode(), -1));
		List<PlanSubscribed> plansSubscribedList = new ArrayList<>();

		for (String planSubsStr : plansSubscribedStr) {

			List<String> plansSubsList = Arrays
					.asList(planSubsStr.toString().split(StringLiterals.SEPERATOR_KEYS.getCode(), -1));

			PlanSubscribed planSubscribed = new PlanSubscribed();
			planSubscribed.setPlanId(Integer.valueOf(plansSubsList.get(NumberUtils.INTEGER_ZERO)));
			planSubscribed.setSubscribedDate(LocalDate.parse(plansSubsList.get(NumberUtils.INTEGER_ONE),
					DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			planSubscribed.setPlanName(plansSubsList.get(NumberUtils.INTEGER_TWO));
			plansSubscribedList.add(planSubscribed);

		}
		return plansSubscribedList;
	}

	private List<QuestionnaireAssociated> getAssociatedQuestionnaireList(String studyQuestionnaires) {
		List<String> associatedQuestionnaireStr = studyQuestionnaires == null ? new ArrayList<String>()
				: Arrays.asList(studyQuestionnaires.split(StringLiterals.SEPERATOR.getCode(), -1));

		List<QuestionnaireAssociated> associatedQuestionnaireList = new ArrayList<>();

		for (String questionnaireDtlsStr : associatedQuestionnaireStr) {

			List<String> questionnaireDtlsList = Arrays
					.asList(questionnaireDtlsStr.split(StringLiterals.SEPERATOR_KEYS.getCode(), -1));

			QuestionnaireAssociated questionnaireAssociated = new QuestionnaireAssociated();

			questionnaireAssociated
					.setQuestionnaireId(Integer.valueOf(questionnaireDtlsList.get(NumberUtils.INTEGER_ZERO)));
			questionnaireAssociated.setQuestionnaireName(questionnaireDtlsList.get(NumberUtils.INTEGER_ONE));
			questionnaireAssociated.setStartDate(questionnaireDtlsList.get(NumberUtils.INTEGER_TWO));
			questionnaireAssociated.setEndDate(questionnaireDtlsList.get(Constants.APP_INDEX_THREE));
			questionnaireAssociated
					.setOccuranceId(Integer.parseInt(questionnaireDtlsList.get(Constants.APP_INDEX_FOUR)));
			questionnaireAssociated
					.setFrequencyId(Integer.parseInt(questionnaireDtlsList.get(Constants.APP_INDEX_FIVE)));

			questionnaireAssociated.setOccurance(questionnaireDtlsList.get(Constants.APP_INDEX_SIX));
			questionnaireAssociated.setFrequency(questionnaireDtlsList.get(Constants.APP_INDEX_SEVEN));
			questionnaireAssociated
					.setStudyQuestionnaireConfigId(questionnaireDtlsList.get(Constants.APP_INDEX_EIGHT) != null
							? Integer.parseInt(questionnaireDtlsList.get(Constants.APP_INDEX_EIGHT))
							: null);

			if (questionnaireDtlsList.get(Constants.APP_INDEX_NINE) != null) {
				questionnaireAssociated
						.setIsActive(Integer.parseInt(questionnaireDtlsList.get(Constants.APP_INDEX_NINE)));
			}

			associatedQuestionnaireList.add(questionnaireAssociated);

		}
		return associatedQuestionnaireList;
	}

	@Override
	public List<StudyListDTO> getStudyListByUser(int userId) throws ServiceExecutionException {
		List<StudyListDTO> studyList = new ArrayList<>();
		LOGGER.debug("getStudyList called");
		try {
			jdbcTemplate.query(SQLConstants.STUDY_GET_ALL_STUDY_LIST_BY_USER, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					StudyListDTO studyListDTO = new StudyListDTO();
					studyListDTO.setStudyId(rs.getInt("STUDY_ID"));
					studyListDTO.setStudyName(rs.getString("STUDY_NAME"));
					studyListDTO.setPrincipalInvestigator(rs.getString("PRINCIPLE_INVESTIGATOR"));
					studyListDTO.setIsActive(rs.getBoolean("IS_ACTIVE"));
					studyListDTO.setStartDate(rs.getString("START_DATE") == null ? "" : rs.getString("START_DATE"));
					studyListDTO.setEndDate(rs.getString("END_DATE") == null ? "" : rs.getString("END_DATE"));
					studyListDTO.setCreatedDate(rs.getTimestamp("CREATED_DATE").toLocalDateTime());
					studyListDTO.setIsExternal(rs.getInt("IS_EXTERNAL"));
					studyList.add(studyListDTO);
				}
			}, userId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getStudyList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return studyList;
	}

	@Override
	public List<StudyListDTO> getAllStudyList() throws ServiceExecutionException {
		List<StudyListDTO> studyList = new ArrayList<>();
		LOGGER.debug("getAllStudyList called");
		try {
			jdbcTemplate.query(SQLConstants.STUDY_GET_ALL_STUDY_LIST_ACTIVE_INACTIVE, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					StudyListDTO studyListDTO = new StudyListDTO();
					studyListDTO.setStudyId(rs.getInt("STUDY_ID"));
					studyListDTO.setStudyName(rs.getString("STUDY_NAME"));
					studyListDTO.setIsActive(rs.getBoolean("IS_ACTIVE"));

					studyList.add(studyListDTO);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getAllStudyList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return studyList;
	}

	@Override
	public List<PreludeListDTO> getPreludeDataList(int studyId) throws ServiceExecutionException {
		List<PreludeListDTO> preludeList = new ArrayList<>();
		LOGGER.debug("getPreludeDataList called");
		try {
			jdbcTemplate.query(SQLConstants.GET_ALL_PRELUDE_DATA, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PreludeListDTO preludeListDTO = new PreludeListDTO();
					preludeListDTO.setCategory(rs.getString("CATEGORY"));
					preludeListDTO.setExternalStudyId(rs.getString("EXT_STUDY_ID"));
					preludeListDTO.setExtractDefId(rs.getInt("EXTRACT_DEF_ID"));
					preludeListDTO.setField(rs.getString("FIELD"));
					preludeListDTO.setForm(rs.getString("FORM"));
					preludeListDTO.setExtractGroup(rs.getString("EXTRACT_GROUP"));
					preludeListDTO.setWearablesFieldName(
							rs.getString("WEARABLE_FIELD_NAME") != null ? rs.getString("WEARABLE_FIELD_NAME") : "");
					preludeList.add(preludeListDTO);
				}
			}, studyId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPreludeDataList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return preludeList;
	}

	private List<PreludeAssociated> getAssociatedPreludeList(String studyPrelude) {
		List<String> associatedPreluideStr = studyPrelude == null ? new ArrayList<String>()
				: Arrays.asList(studyPrelude.split(StringLiterals.SEPERATOR.getCode(), -1));

		List<PreludeAssociated> associatedPreluideList = new ArrayList<>();

		for (String associatedPreluideDtlsStr : associatedPreluideStr) {

			List<String> associatedPreluideDtlsList = Arrays
					.asList(associatedPreluideDtlsStr.split(StringLiterals.SEPERATOR_KEYS.getCode(), -1));

			PreludeAssociated preludeAssociated = new PreludeAssociated();
			preludeAssociated.setFormName(associatedPreluideDtlsList.get(NumberUtils.INTEGER_ZERO));
			preludeAssociated.setCategory(associatedPreluideDtlsList.get(NumberUtils.INTEGER_ONE));
			preludeAssociated.setPreludeGroup(associatedPreluideDtlsList.get(NumberUtils.INTEGER_TWO));
			preludeAssociated.setFieldName(associatedPreluideDtlsList.get((Integer.valueOf(3))));
			preludeAssociated.setExternalStudyId(associatedPreluideDtlsList.get((Integer.valueOf(4))));
			preludeAssociated.setExtractDefId(Integer.valueOf(associatedPreluideDtlsList.get((Integer.valueOf(5)))));
			associatedPreluideList.add(preludeAssociated);

		}
		return associatedPreluideList;
	}

	@Override
	public List<PreludeDataByStudyDTO> getPreludeDataByStudy(String studyName) throws ServiceExecutionException {
		List<PreludeDataByStudyDTO> preludeList = new ArrayList<>();
		LOGGER.debug("getPreludeDataList called");
		try {
			jdbcTemplate.query(SQLConstants.GET_PRELUDE_DATA_BY_STUDY, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PreludeDataByStudyDTO preludeDataByStudyDTO = new PreludeDataByStudyDTO();
					String fields = rs.getString("FIELDS");
					if (!fields.isEmpty() && fields != null) {
						String[] fieldList = fields.split(",");
						if (fieldList.length > 0 && fieldList[0] != null) {
							preludeDataByStudyDTO.setPatientId(fieldList[0]);
						}
						if (fieldList.length > 1 && fieldList[1] != null) {
							preludeDataByStudyDTO.setOwnerLastName(fieldList[1]);
						}
						if (fieldList.length > 2 && fieldList[2] != null) {
							preludeDataByStudyDTO.setOwnerEmail(fieldList[2]);
						}
						if (fieldList.length > 3 && fieldList[3] != null) {
							preludeDataByStudyDTO.setPetName(fieldList[3]);
						}
					}
					preludeList.add(preludeDataByStudyDTO);
				}
			}, studyName);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPreludeDataList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return preludeList;
	}

	/*********** STUDY PUSH NOTIFICATIONS ******************/

	@Override
	public void associatePushNotifications(int studyId, PushNotificationsAssociated pushNotificationsAssociated,
			Integer userId) throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_study_id", studyId);
		inputParams.put("p_push_notification_id", pushNotificationsAssociated.getNotificationId());
		inputParams.put("p_start_date", pushNotificationsAssociated.getStartDate());
		inputParams.put("p_end_date", pushNotificationsAssociated.getEndDate());
		inputParams.put("p_display_time", pushNotificationsAssociated.getDisplayTime()); // TIME CHECK
		inputParams.put("p_frequency", pushNotificationsAssociated.getFrequency());
		inputParams.put("p_created_by", userId);
		try {
			LOGGER.info("associatePushNotifications inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_ASSOCIATE_PUSH_NOTIFICATION,
					inputParams);
			LOGGER.info("associatePushNotifications outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("Study Push Notification has been associated successfully, Study id is ", studyId);
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"associatePushNotifications service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(
									new WearablesError(WearablesErrorCode.PUSH_NOTIFICATION_ALREADY_MAPPED_TO_STUDY,
											pushNotificationsAssociated.getNotificationName())));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing associatePushNotifications ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public void updateStudyPushNotifications(int studyId, PushNotificationsAssociated pushNotificationsAssociated,
			Integer userId) throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_study_id", studyId);
		inputParams.put("p_push_notification_id", pushNotificationsAssociated.getNotificationId());
		inputParams.put("p_start_date", pushNotificationsAssociated.getStartDate());
		inputParams.put("p_end_date", pushNotificationsAssociated.getEndDate());
		inputParams.put("p_display_time", pushNotificationsAssociated.getDisplayTime()); // TIME CHECK
		inputParams.put("p_frequency", pushNotificationsAssociated.getFrequency());
		inputParams.put("p_modofied_by", userId);
		try {
			LOGGER.info("updateStudyPushNotifications inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_UPDATE_PUSH_NOTIFICATION,
					inputParams);
			LOGGER.info("updateStudyPushNotifications outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("Questinnaire mapping details has been updated successfully, Study id is ", studyId);
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"updateStudyPushNotifications service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList(new WearablesError(
									WearablesErrorCode.INVALID_STUDY_PUSH_NOTIFICATION_ASSOCAITION)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing updateStudyPushNotifications ", e);
			throw new ServiceExecutionException(e.getMessage());
		}

	}

	@Override
	public List<PushNotification> getAssociatedPushNotifications(int studyId) throws ServiceExecutionException {
		List<PushNotification> pushNotificationList = new ArrayList<>();
		LOGGER.debug("getAssociatedPushNotifications called");
		try {
			jdbcTemplate.query(SQLConstants.STUDY_GET_ASSOCIATED_PUSH_NOTIFICATIONS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PushNotification studyPushNotification = new PushNotification();
					studyPushNotification.setNotificationId(rs.getInt("PUSH_NOTIFICATION_ID"));
					studyPushNotification.setNotificationName(rs.getString("NOTIFICATION_NAME"));
					/*
					 * studyPushNotification.setStartDate(rs.getDate("START_DATE").toLocalDate());
					 * studyPushNotification.setEndDate(rs.getDate("END_DATE").toLocalDate()); TODO
					 * CHECK
					 */
					studyPushNotification
							.setStartDate(rs.getString("START_DATE") == null ? "" : rs.getString("START_DATE"));
					studyPushNotification.setEndDate(rs.getString("END_DATE") == null ? "" : rs.getString("END_DATE"));
					studyPushNotification.setDisplayTime(rs.getString("DISPLAY_TIME"));
					studyPushNotification.setFrequency(rs.getString("FREQUENCY"));
					studyPushNotification.setIsActive(rs.getBoolean("IS_ACTIVE"));
					pushNotificationList.add(studyPushNotification);
				}
			}, studyId);

		} catch (Exception e) {
			LOGGER.error("error while fetching getAssociatedPushNotifications", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return pushNotificationList;
	}

	@Override
	public void disassociateStudyPushNotifications(int studyId, int notificationId, Integer userId)
			throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_study_id", studyId);
		inputParams.put("p_push_notification_id", notificationId);
		inputParams.put("p_modified_by", userId);
		try {
			LOGGER.info("disassociateStudyPushNotifications inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_DISASSOCIATE_PUSH_NOTIFICATION,
					inputParams);
			LOGGER.info("disassociateStudyPushNotifications outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("Questionnaire has been disassociated with Study successfully");
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"disassociateStudyPushNotifications service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList(new WearablesError(
									WearablesErrorCode.INVALID_STUDY_PUSH_NOTIFICATION_ASSOCAITION)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing disassociateStudyPushNotifications ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public List<StudyImageScale> associatedImageScales(int studyId) throws ServiceExecutionException {
		List<StudyImageScale> studyImageScaleList = new ArrayList<>();
		LOGGER.debug("associatedImageScales called");
		try {
			jdbcTemplate.query(SQLConstants.STUDY_GET_ASSOCIATED_PUSH_NOTIFICATIONS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
//					StudyImageScale studyImageScale = new StudyImageScale();
//					studyImageScale.setNotificationId(rs.getInt("PUSH_NOTIFICATION_ID"));
//					studyImageScale.setNotificationName(rs.getString("NOTIFICATION_NAME"));
//					studyImageScale
//							.setStartDate(rs.getString("START_DATE") == null ? "" : rs.getString("START_DATE"));
//					studyImageScale.setEndDate(rs.getString("END_DATE") == null ? "" : rs.getString("END_DATE"));
//					studyImageScale.setFrequency(rs.getString("FREQUENCY_NAME"));
//					studyImageScale.setFrequencyId(Integer.praseInt(rs.getString("FREQUENCY")));
//					studyImageScale.setIsActive(rs.getBoolean("IS_ACTIVE"));
//					pushNotificationList.add(studyPushNotification);
				}
			}, studyId);

		} catch (Exception e) {
			LOGGER.error("error while fetching associatedImageScales", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return studyImageScaleList;
	}

	private List<PushNotificationsAssociated> getAssociatedPushNotificationList(String studyPushNotifications) {
		List<String> associatedPushNotificationsStr = studyPushNotifications == null ? new ArrayList<String>()
				: Arrays.asList(studyPushNotifications.split(StringLiterals.SEPERATOR.getCode(), -1));

		List<PushNotificationsAssociated> pushNotificationsAssociatedList = new ArrayList<>();

		for (String pushNotificationsDtlsStr : associatedPushNotificationsStr) {

			List<String> pushNotificationDtlsList = Arrays
					.asList(pushNotificationsDtlsStr.split(StringLiterals.SEPERATOR_KEYS.getCode(), -1));

			PushNotificationsAssociated pushNotificationsAssociated = new PushNotificationsAssociated();
			pushNotificationsAssociated
					.setNotificationId(Integer.valueOf(pushNotificationDtlsList.get(Constants.APP_INDEX_ZERO)));
			pushNotificationsAssociated.setNotificationName(pushNotificationDtlsList.get(Constants.APP_INDEX_ONE));
			pushNotificationsAssociated
					.setStartDate(LocalDate.parse(pushNotificationDtlsList.get(Constants.APP_INDEX_TWO)));
			pushNotificationsAssociated
					.setEndDate(LocalDate.parse(pushNotificationDtlsList.get((Constants.APP_INDEX_THREE))));

			pushNotificationsAssociated.setDisplayTime(pushNotificationDtlsList.get(Constants.APP_INDEX_FOUR));
			pushNotificationsAssociated.setFrequency(pushNotificationDtlsList.get(Constants.APP_INDEX_FIVE));

			pushNotificationsAssociatedList.add(pushNotificationsAssociated);
		}
		return pushNotificationsAssociatedList;
	}

	private List<ImageScoringAssociated> getAssociatedImageScaleList(String studyImageScales) {
		List<String> associatedImageScalesStr = studyImageScales == null ? new ArrayList<String>()
				: Arrays.asList(studyImageScales.split(StringLiterals.SEPERATOR.getCode(), -1));

		List<ImageScoringAssociated> imageScalesAssociatedList = new ArrayList<>();

		for (String imageSclesDtlsStr : associatedImageScalesStr) {

			List<String> ImageScleDtlsList = Arrays
					.asList(imageSclesDtlsStr.split(StringLiterals.SEPERATOR_KEYS.getCode(), -1));

			ImageScoringAssociated imageSclesAssociated = new ImageScoringAssociated();
			imageSclesAssociated.setImageScoringId(Integer.valueOf(ImageScleDtlsList.get(Constants.APP_INDEX_ZERO)));
			imageSclesAssociated.setImageScaleName(ImageScleDtlsList.get(Constants.APP_INDEX_ONE));
			imageSclesAssociated.setStartDate(ImageScleDtlsList.get(Constants.APP_INDEX_TWO));
			imageSclesAssociated.setEndDate(ImageScleDtlsList.get(Constants.APP_INDEX_THREE));
			imageSclesAssociated.setFrequencyId(Integer.parseInt(ImageScleDtlsList.get(Constants.APP_INDEX_FOUR)));
			imageSclesAssociated.setFrequency(ImageScleDtlsList.get(Constants.APP_INDEX_FIVE));
			imageScalesAssociatedList.add(imageSclesAssociated);
		}
		return imageScalesAssociatedList;
	}

	@Override
	public List<PreludeListDTO> getAFPreludeDataList(int studyId) throws ServiceExecutionException {
		List<PreludeListDTO> preludeList = new ArrayList<>();
		LOGGER.debug("getAFPreludeDataList called");
		try {
			jdbcTemplate.query(SQLConstants.GET_ALL_PRELUDE_DATA, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PreludeListDTO preludeListDTO = new PreludeListDTO();
					preludeListDTO.setCategory(rs.getString("CATEGORY"));
					preludeListDTO.setExternalStudyId(rs.getString("EXT_STUDY_ID"));
					preludeListDTO.setExtractDefId(rs.getInt("EXTRACT_DEF_ID"));
					preludeListDTO.setField(rs.getString("FIELD"));
					preludeListDTO.setForm(rs.getString("FORM"));
					preludeListDTO.setExtractGroup(rs.getString("EXTRACT_GROUP"));
					preludeListDTO.setWearablesFieldName(
							rs.getString("WEARABLE_FIELD_NAME") != null ? rs.getString("WEARABLE_FIELD_NAME") : "");
					preludeList.add(preludeListDTO);
				}
			}, studyId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getAFPreludeDataList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return preludeList;
	}

	@Override
	public List<PreludeDataByStudyDTO> getAFPreludeDataByStudy(String studyName) throws ServiceExecutionException {
		List<PreludeDataByStudyDTO> preludeList = new ArrayList<>();
		LOGGER.debug("getAFPreludeDataByStudy called");
		try {
			jdbcTemplate.query(SQLConstants.GET_AF_PRELUDE_DATA_BY_STUDY, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PreludeDataByStudyDTO preludeDataByStudyDTO = new PreludeDataByStudyDTO();
					String fields = rs.getString("FIELDS");
					if (!fields.isEmpty() && fields != null) {
						String[] fieldList = fields.split(",");
						if (fieldList.length > 0 && fieldList[0] != null) {
							preludeDataByStudyDTO.setPatientId(fieldList[0]);
						}
						if (fieldList.length > 1 && fieldList[1] != null) {
							preludeDataByStudyDTO.setOwnerLastName(fieldList[1]);
						}
						if (fieldList.length > 2 && fieldList[2] != null) {
							preludeDataByStudyDTO.setOwnerEmail(fieldList[2]);
						}
						if (fieldList.length > 3 && fieldList[3] != null) {
							preludeDataByStudyDTO.setPetName(fieldList[3]);
						}
					}
					preludeList.add(preludeDataByStudyDTO);
				}
			}, studyName);
		} catch (Exception e) {
			LOGGER.error("error while fetching getAFPreludeDataByStudy", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return preludeList;
	}

	private String getAccessToken(String endpoint) {
		String bearerToken = "";
		try {
			tokenCredential.refreshIfExpired();
			bearerToken = tokenCredential.refreshAccessToken().getTokenValue();
		} catch (IOException e) {
			System.out.println("Exception in getAccessToken method " + e.getMessage());
			e.printStackTrace();
		}
		return bearerToken;
	}

	private void invokePreludeJob(String endpoint, String studyId, String env) {
		try {
			if (StringUtils.isEmpty(endpoint)) {
				System.out.println("endpoint is not available in Env Variables");
			}

			String accessToken = getAccessToken(endpoint);
			// System.out.println("accessToken is " + accessToken);
			// Create a WebClient instance with the authorization header
			WebClient webClient = WebClient.builder().baseUrl(BASE_PRELUDE_URL)
					.clientConnector(new ReactorClientHttpConnector())
					.defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken).build();

			LOGGER.info("postDataToEndpoint::StudyId, ENV" + studyId + ":" + env);
			String json = "{\"StudyId\":\"" + studyId + "\", \"Env\":\"" + env + "\"}";

			webClient.method(HttpMethod.POST).uri(endpoint).contentType(MediaType.APPLICATION_JSON)
					.body(BodyInserters.fromValue(json)).retrieve().onStatus(HttpStatus::is4xxClientError, response -> {
						// Handle Bad Request
						throw new RuntimeException("Prelude Bad Request");
					}).onStatus(HttpStatus::is5xxServerError, response -> {
						// Handle Server Error
						throw new RuntimeException("Prelude Job Failed to process the data");
					}).bodyToMono(String.class).subscribe(response -> {
						// System.out.println("Request is successfully processed");
						LOGGER.error("onSuccess response is ::" + response.toString());
					});

		} catch (Exception e) {
			System.out.println("Exception in invokePreludeJob method " + e.getMessage());
			e.printStackTrace();
		}
	}

	// ------- Services for study questionnaire data extract configuration --------
	@SuppressWarnings("unchecked")
	@Override
	public Questionnaire getQuestionnaireDtlsForDataExtractConfig(Integer studyQuestionnireId) {
		LOGGER.debug("getQuestionnaireDtlsForDataExtractConfig called");
		Questionnaire questionnaire = new Questionnaire();
		List<QuestionnaireInstruction> instructions = new ArrayList<>();
		List<QuestionnaireSection> sections = new ArrayList<>();
		List<Question> questions = new ArrayList<>();
		Map<Integer, List<QuestionAnswerOption>> questionAnsOptsMap = new HashMap<>();
		Map<Integer, QuestionnaireSection> questionSectionMap = new HashMap<>();
		try {
			// in params
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("p_study_questionnaire_id", studyQuestionnireId);

			Map<String, Object> simpleJdbcCallResult = callStoredProcedure(
					SQLConstants.STUDY_QUESTIONNAIRE_DATA_EXTRACT_CONFIG_GET_BY_ID, inputParams);
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
					list.forEach(answerOpts -> {
						QuestionAnswerOption ansOptions = new QuestionAnswerOption();
						ansOptions.setQuestionAnswerId((Integer) answerOpts.get("QUESTION_ANSWER_OPTION_ID"));
						ansOptions.setQuestionAnswer((String) answerOpts.get("ANSWER"));

						ansOptions.setSubmitQuestionnaire(answerOpts.get("AUTO_SUBMIT_QUESTIONNAIRE") != null
								&& (Integer) answerOpts.get("AUTO_SUBMIT_QUESTIONNAIRE") > NumberUtils.INTEGER_ZERO
										? Boolean.TRUE
										: Boolean.FALSE);
						ansOptions.setSkipTo((Integer) answerOpts.get("SKIP_TO"));

						ansOptions.setAnsOptionMediaType((Integer) answerOpts.get("MEDIA_TYPE"));

						String fileName = (String) answerOpts.get("MEDIA_FILE_NAME");

						ansOptions.setAnsOptionMediaName(fileName);
						if (fileName != null && fileName != "null" && !fileName.trim().equals("")) {
							ansOptions.setAnsOptionMediaUrl(gcpClientUtil.getDownloaFiledUrl(fileName,
									Constants.GCP_QUESTIONNAIRE_QUESTION_IMAGE_PATH));
						}

						Integer questionId = (Integer) answerOpts.get("QUESTION_ID");

						questionAnsOptsMap.computeIfAbsent(questionId, k -> new ArrayList<QuestionAnswerOption>())
								.add(ansOptions);

					});
				}

				if (key.equals(SQLConstants.RESULT_SET_5)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();

					list.forEach(quest -> {
						Question question = new Question();
						QuestionSliderType other = new QuestionSliderType();
						Integer questionId = (Integer) quest.get("QUESTION_ID");
						question.setQuestionId(questionId);
						question.setQuestion((String) quest.get("QUESTION"));

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

	@Override
	public void updateDataExtractConfigStudyQuestionnaire(StudyQuestionnaireRequest studyQuestionnaireRequest,
			Integer userId) {

		Integer studyQuestionnaireId = studyQuestionnaireRequest.getStudyQuestionnaireId();
		LOGGER.info("updateDataExtractConfigStudyQuestionnaire start, studyQuestionnaireId {} ", studyQuestionnaireId);
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_study_questionnaire_id", studyQuestionnaireId);
			inputParams.put("p_question_json", mapper.writeValueAsString(studyQuestionnaireRequest.getQuestions()));
			inputParams.put("p_created_by", userId);

			LOGGER.info("updateDataExtractConfigStudyQuestionnaire inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(
					SQLConstants.STUDY_QUESTIONNAIRE_DATA_EXTRACT_CONFIG_INSERT, inputParams);
			LOGGER.info("updateDataExtractConfigStudyQuestionnaire outParams are {}", outParams);

			// System.out.println(outParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("Study Questionnaire has been created successfully, Study Questionnaire id is ",
						studyQuestionnaireId);
			} else {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (SQLException | JsonProcessingException e) {
			LOGGER.error("error while executing addQuestionnaire ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public Integer getStudyIdByName(String studyName) throws ServiceExecutionException {
		LOGGER.debug("getStudyIdByName called");
		Integer studyId = null;
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_study_name", studyName);

			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_ID_GET_BY_NAME, inputParams);
			studyId = (Integer) outParams.get("p_study_id");

		} catch (SQLException e) {
			LOGGER.error("error while executing addQuestionnaire ", e);
		}
		return studyId;
	}

	@Override
	public FeedingScheduleConfig getStudyPhaseDuration(Integer studyId, Integer phaseId)
			throws ServiceExecutionException {
		FeedingScheduleConfig feedingScheduleConfig = new FeedingScheduleConfig();
		LOGGER.debug("getStudyPhaseDuration called");
		try {
			int duration = selectForObject(SQLConstants.FN_GET_STUDY_PHASE_DURATION_BY_STUDY_AND_PHASE, Integer.class,
					studyId, phaseId);
			feedingScheduleConfig.setStudyId(studyId);
			feedingScheduleConfig.setPhaseId(phaseId);
			feedingScheduleConfig.setPhaseDuration(duration);
		} catch (Exception e) {
			LOGGER.error("error while fetching getStudyPhaseDuration", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return feedingScheduleConfig;
	}

	@Override
	public FeedingScheduleConfig getStudyPhaseDiets(Integer studyId, Integer phaseId) throws ServiceExecutionException {

		FeedingScheduleConfig feedingScheduleConfig = new FeedingScheduleConfig();
		List<FeedingSchedule> feedingScheduleList = new ArrayList<>();

		LOGGER.debug("getStudyPhaseDiets called");
		try {
			jdbcTemplate.query(SQLConstants.STUDY_GET_PHASE_DIETS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					FeedingSchedule feedingSchedule = new FeedingSchedule();
					feedingSchedule.setStudyDietId(rs.getInt("STUDY_DIET_ID"));
					feedingSchedule.setDietId(rs.getInt("DIET_ID"));
					feedingSchedule.setDietNumber(rs.getString("DIET_NUMBER"));
					feedingSchedule.setDietName(rs.getString("DIET_NAME"));
					feedingScheduleList.add(feedingSchedule);
				}
			}, studyId, phaseId);

			feedingScheduleConfig.setFeedingScheduleList(feedingScheduleList);

		} catch (Exception e) {
			LOGGER.error("error while fetching getStudyPhaseDiets", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return feedingScheduleConfig;
	}

	@Override
	public Map<String, Integer> getFeedingScheduleListCount(Integer studyId, Integer phaseId,
			FeedingScheduleFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getFeedingScheduleListCount called");
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		try {
			counts = selectForObject(SQLConstants.STUDY_FEEDING_SCHEDULE_GET_LIST_COUNT, String.class, studyId, phaseId,
					filter.getFromPhaseDay(), filter.getToPhaseDay(), filter.getSearchText());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getFeedingScheduleListCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<FeedingSchedule> getFeedingScheduleList(Integer studyId, Integer phaseId, FeedingScheduleFilter filter)
			throws ServiceExecutionException {
		List<FeedingSchedule> feedingScheduleList = new ArrayList<>();
		LOGGER.debug("getFeedingScheduleList called");
		try {
			jdbcTemplate.query(SQLConstants.STUDY_FEEDING_SCHEDULE_GET_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					FeedingSchedule feedingDTO = new FeedingSchedule();
					feedingDTO.setStudyId(rs.getInt("STUDY_ID"));
					feedingDTO.setPhaseId(rs.getInt("STUDY_PHASE_ID"));
					feedingDTO.setPetId(rs.getInt("PET_ID"));
					feedingDTO.setPetName(rs.getString("PET_NAME"));
					feedingDTO.setDietId(rs.getInt("DIET_ID"));
					feedingDTO.setDietNumber(rs.getString("DIET_NUMBER"));
					feedingDTO.setDietName(rs.getString("DIET_NAME"));
					feedingDTO.setFromPhaseDay(rs.getInt("FROM_PHASE_DAY"));
					feedingDTO.setToPhaseDay(rs.getInt("TO_PHASE_DAY"));
					feedingDTO.setByTreatmentGroup(rs.getInt("BY_TREATMENT_GROUP"));
					feedingDTO.setUserId(rs.getInt("CREATED_BY"));
					feedingDTO.setFeedingScheduleConfigId(rs.getInt("FEEDING_SCHEDULE_CONFIG_ID"));
					feedingDTO.setStudyDietId(rs.getInt("STUDY_DIET_ID"));
					feedingDTO.setDays(rs.getString("DAYS"));
					feedingDTO.setIsDeleted(rs.getInt("IS_DELETED"));

					feedingScheduleList.add(feedingDTO);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), studyId, phaseId,
					filter.getFromPhaseDay(), filter.getToPhaseDay(), filter.getSearchText());
		} catch (Exception e) {
			LOGGER.error("error while fetching getFeedingScheduleList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return feedingScheduleList;
	}

	@Override
	public void deleteFeedingSchedule(int feedingScheduleConfigId, int modifiedBy) throws ServiceExecutionException {
		LOGGER.debug("deleteFeedingSchedule called");
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_feeding_schedule_id", feedingScheduleConfigId);
		inputParams.put("p_modified_by", modifiedBy);
		try {
			LOGGER.info("deleteFeedingSchedule inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_FEEDING_SCHEDULE_DELETE,
					inputParams);
			LOGGER.info("deleteFeedingSchedule outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			/* int statusFlag = (int) outParams.get("out_flag"); */
			if (StringUtils.isNotEmpty(errorMsg)) {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing deletePlan ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		LOGGER.debug("deleteFeedingSchedule completed.");
	}

	@Override
	public void saveStudyPhaseFeedingSchedule(FeedingScheduleConfig feedingSchedule) throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_study_id", feedingSchedule.getStudyId());
			inputParams.put("p_phase_id", feedingSchedule.getPhaseId());
			inputParams.put("p_from_phase_day", feedingSchedule.getFromPhaseDay());
			inputParams.put("p_to_phase_day", feedingSchedule.getToPhaseDay());
			// inputParams.put("p_feeding_schedule", new
			// ObjectMapper().writeValueAsString(feedingSchedule.getFeedingScheduleList()));

			List<FeedingSchedule> feedingScheduleList = feedingSchedule.getFeedingScheduleList().stream()
					.filter(e -> (e.getFeedingScheduleConfigId() == null || e.getFeedingScheduleConfigId() == 0))
					.map(e -> e).collect(Collectors.toList());
			inputParams.put("p_feeding_schedule", new ObjectMapper().writeValueAsString(feedingScheduleList));

			String deletedIds = feedingSchedule.getFeedingScheduleList().stream()
					.filter(e -> (e.getIsDeleted() == 1 && e.getFeedingScheduleConfigId() > 0))
					.map(e -> e.getFeedingScheduleConfigId().toString()).collect(Collectors.joining(","));
			inputParams.put("p_deleted_config_ids", StringUtils.isNotBlank(deletedIds) ? deletedIds : "");

			inputParams.put("p_login_user_id", feedingSchedule.getUserId());

			LOGGER.info("saveStudyPhaseFeedingSchedule inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_FEEDING_SCHEDULE_SAVE, inputParams);
			LOGGER.info("saveStudyPhaseFeedingSchedule outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");

			if (!(StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO)) {

				if (StringUtils.isNotEmpty(errorMsg) && statusFlag == -2) {
					String[] msgArray = errorMsg.split("#@#");
					List<WearablesError> errMsgList = new ArrayList<WearablesError>();
					for (String msg : msgArray) {
						errMsgList
								.add(new WearablesError(WearablesErrorCode.STUDY_FEEDING_SCHEDULE_ALREADY_EXISTS, msg));
					}
					if (CollectionUtils.isNotEmpty(errMsgList)) {
						throw new ServiceValidationException(
								"saveStudyPhaseFeedingSchedule service validation failed cannot proceed further",
								HttpStatus.BAD_REQUEST.value(), errMsgList);
					}
				} else {
					throw new ServiceExecutionException(errorMsg);
				}

			}

		} catch (SQLException | JsonProcessingException e) {
			LOGGER.error("error while executing saveStudyPhaseFeedingSchedule ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	/*
	 * public static void main(String[] args) throws ExecutionException,
	 * InterruptedException, TimeoutException { StudyDaoImpl dao = new
	 * StudyDaoImpl(); dao.postDataToEndpointAysnc(
	 * "https://us-central1-ct-wearables-portal-pf.cloudfunctions.net/WP-CF-GetPreludeFields",
	 * "6072", "UAT"); }
	 */

	/*
	 * private List<PreludeMandatory> getMandatoryPreludeList(String studyPrelude) {
	 * List<String> associatedPreluideStr = studyPrelude == null ? new
	 * ArrayList<String>() : Arrays.asList(studyPrelude.split(",", -1));
	 * 
	 * List<PreludeMandatory> associatedPreluideList = new ArrayList<>();
	 * 
	 * for (String associatedPreluideDtlsStr : associatedPreluideStr) {
	 * 
	 * List<String> associatedPreluideDtlsList =
	 * Arrays.asList(associatedPreluideDtlsStr.split("#", -1));
	 * 
	 * PreludeMandatory preludeAssociated = new PreludeMandatory();
	 * preludeAssociated
	 * .setFormName(associatedPreluideDtlsList.get(NumberUtils.INTEGER_ZERO));
	 * preludeAssociated.setCategory(associatedPreluideDtlsList.get(NumberUtils.
	 * INTEGER_ONE));
	 * preludeAssociated.setPreludeGroup(associatedPreluideDtlsList.get(NumberUtils.
	 * INTEGER_TWO));
	 * preludeAssociated.setFieldName(associatedPreluideDtlsList.get((Integer.
	 * valueOf(3))));
	 * preludeAssociated.setExternalStudyId(associatedPreluideDtlsList.get((Integer.
	 * valueOf(4))));
	 * preludeAssociated.setExtractDefId(Integer.valueOf(associatedPreluideDtlsList.
	 * get((Integer.valueOf(5))))); associatedPreluideList.add(preludeAssociated);
	 * 
	 * } return associatedPreluideList; }
	 */

	// ----------------- Services for Adding pet,Search page and Delete Pet to Study

	@Override
	public void addPhaseWisePet(AddPhaseWisePetRequest addPhaseWisePetRequest, Integer userId)
			throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();

			inputParams.put("p_study_id", addPhaseWisePetRequest.getStudyId());
			inputParams.put("p_phase_id", addPhaseWisePetRequest.getPhaseId());
			inputParams.put("p_treatment_group_id", addPhaseWisePetRequest.getTreatmentGroupId());
			inputParams.put("p_phase_day", addPhaseWisePetRequest.getPhaseDay());
			inputParams.put("p_from_date", addPhaseWisePetRequest.getFromDate());

			inputParams.put("p_reason", addPhaseWisePetRequest.getReason());

			List<Integer> petIds = addPhaseWisePetRequest.getPetList().stream().filter(e -> (e.getPetId() > 0))
					.map(e -> e.getPetId()).collect(Collectors.toList());
			inputParams.put("p_pet_ids", StringUtils.join(petIds, ','));
			inputParams.put("p_user_id", userId);

			LOGGER.info(" addPhaseWisePet inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_PHASE_PET_INSERT, inputParams);
			LOGGER.info("addPhaseWisePet outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				// getting the inserted flag value
				LOGGER.info("addPhaseWisePet statusFlag is {}", statusFlag);
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"addPhaseWisePet service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList(new WearablesError(
									WearablesErrorCode.STUDY_MAX_PETS_IN_TREATMENT_GROUP_VALIDATION_FAILED)));
				} else if(statusFlag == -3){
					String[] error = errorMsg.split("##");
					throw new ServiceExecutionException("addPhaseWisePet service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.STUDY_EXTEND_PHASE_SCHEDULE_ALREADY_EXISTS,
									error[0], error[1], error[2])));
				}else if(statusFlag == -4){ //If pet is removed from study and trying to add it 
					throw new ServiceExecutionException("addPhaseWisePet service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.STUDY_PET_CANNOT_ADD_IN_CURRENT_OR_PREVIOUS_PHASES,
									errorMsg)));
				}else {
					throw new ServiceExecutionException(errorMsg);
				}
			}

		} catch (SQLException e) {
			LOGGER.error("error while executing addPhaseWisePet ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public void removePetsFromPhase(AddPhaseWisePetRequest addPhaseWisePetRequest, Integer userId)
			throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();

			inputParams.put("p_study_id", addPhaseWisePetRequest.getStudyId());
			inputParams.put("p_phase_id", addPhaseWisePetRequest.getPhaseId());
			inputParams.put("p_phase_day", addPhaseWisePetRequest.getPhaseDay());
			inputParams.put("p_from_date", addPhaseWisePetRequest.getFromDate());
			inputParams.put("p_reason", addPhaseWisePetRequest.getReason());

			List<Integer> petIds = addPhaseWisePetRequest.getPetList().stream().filter(e -> (e.getPetId() > 0))
					.map(e -> e.getPetId()).collect(Collectors.toList());
			inputParams.put("p_pet_ids", StringUtils.join(petIds, ','));

			inputParams.put("p_modified_by", userId);

			LOGGER.info("removePetsFromPhase inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_PHASE_PET_REMOVE, inputParams);
			LOGGER.info("removePetsFromPhase outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				// getting the inserted flag value
				LOGGER.info("removePetsFromPhase statusFlag is {}", statusFlag);
			} else {
				if (statusFlag == -1) {
					throw new ServiceExecutionException(
							"study removePetsFromPhase service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList(
									new WearablesError(WearablesErrorCode.STUDY_REMOVE_PET_INVALID_DATE, errorMsg)));
				} else if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"study removePetsFromPhase service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.STUDY_REMOVE_PET_INVALID_PHASE_DAY,
									errorMsg)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}

		} catch (SQLException e) {
			LOGGER.error("error while executing removePetsFromPhase ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getPhaseWisePetListCount(PhaseWisePetListFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getPhaseWisePetListCount called");
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		try {
			counts = selectForObject(SQLConstants.GET_STUDY_PHASE_PET_LIST_COUNT, String.class, filter.getStudyId(),
					filter.getPhaseId(), filter.getSearchText(), filter.getFilterType(), filter.getFilterValue(),
					filter.getUserId(), filter.getRoleTypeId());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPhaseWisePetListCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<PhaseWisePetListDTO> getPhaseWisePetList(PhaseWisePetListFilter filter)
			throws ServiceExecutionException {
		List<PhaseWisePetListDTO> petList = new ArrayList<>();
		LOGGER.debug("getPhaseWisePetList called");
		try {
			jdbcTemplate.query(SQLConstants.STUDY_PHASE_GET_PET_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PhaseWisePetListDTO phaseWisePetListDTO = new PhaseWisePetListDTO();

					phaseWisePetListDTO.setPetId(rs.getInt("PET_ID"));
					phaseWisePetListDTO.setPetName(rs.getString("PET_NAME"));
					String fileName = rs.getString("PHOTO_NAME");
					phaseWisePetListDTO.setPetPhoto(fileName);

					if (!filter.isImgNotRequired()) {
						if (fileName != null && !fileName.trim().equals("")) {
							phaseWisePetListDTO.setPetPhotoUrl(
									gcpClientUtil.getDownloaFiledUrl(fileName, Constants.GCP_PETPHOTO_PATH));
						}
					}

					phaseWisePetListDTO.setSpeciesName(rs.getString("SPECIES_NAME"));
					phaseWisePetListDTO.setBreedName(rs.getString("BREED_NAME"));
					phaseWisePetListDTO.setGender(rs.getString("GENDER"));
					phaseWisePetListDTO.setPetParentName(rs.getString("PET_PARENT_NAME"));
					phaseWisePetListDTO.setTreatmentGroupName(rs.getString("GROUP_NAME"));

					// below column added for extend phase.
					phaseWisePetListDTO.setPetStudyId(rs.getInt("PET_STUDY_ID"));
					phaseWisePetListDTO.setFromPhaseDay(rs.getInt("FROM_PHASE_DAY"));

					phaseWisePetListDTO
							.setStartDate(rs.getString("START_DATE") == null ? "" : rs.getString("START_DATE"));
					phaseWisePetListDTO.setEndDate(rs.getString("END_DATE") == null ? "" : rs.getString("END_DATE"));
					phaseWisePetListDTO
							.setRemovedDate(rs.getString("REMOVED_DATE") == null ? "" : rs.getString("REMOVED_DATE"));
					phaseWisePetListDTO.setWeight(rs.getString("WEIGHT"));

					phaseWisePetListDTO.setAfEligibilityDate(
							rs.getDate("AF_ELIGIBILITY_DATE") != null ? rs.getDate("AF_ELIGIBILITY_DATE").toLocalDate()
									: null);
					petList.add(phaseWisePetListDTO);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getStudyId(),
					filter.getPhaseId(), filter.getSearchText(), filter.getFilterType(), filter.getFilterValue(),
					filter.getUserId(), filter.getRoleTypeId());

		} catch (Exception e) {
			LOGGER.error("error while fetching getPhaseWisePetList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petList;
	}

	@Override
	public void changePetsTreatmentGroup(AddPhaseWisePetRequest addPhaseWisePetRequest, Integer userId)
			throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();

			inputParams.put("p_study_id", addPhaseWisePetRequest.getStudyId());
			inputParams.put("p_phase_id", addPhaseWisePetRequest.getPhaseId());
			inputParams.put("p_phase_day", addPhaseWisePetRequest.getPhaseDay());
			inputParams.put("p_treatment_group_id", addPhaseWisePetRequest.getTreatmentGroupId());
			inputParams.put("p_reason", addPhaseWisePetRequest.getReason());

			List<Integer> petIds = addPhaseWisePetRequest.getPetList().stream().filter(e -> (e.getPetId() > 0))
					.map(e -> e.getPetId()).collect(Collectors.toList());
			inputParams.put("p_pet_ids", StringUtils.join(petIds, ','));

			inputParams.put("p_login_user_id", userId);

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_PET_CHANGE_TREATMENT_GROUP,
					inputParams);
			LOGGER.info("outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");

			if (!(StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO)) {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"changePetsTreatmentGroup service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList(new WearablesError(
									WearablesErrorCode.STUDY_MAX_PETS_IN_TREATMENT_GROUP_VALIDATION_FAILED)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}

		} catch (SQLException e) {
			LOGGER.error("error while executing changePetsTreatmentGroup ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public void movePetsToNextPhase(AddPhaseWisePetRequest addPhaseWisePetRequest, Integer userId)
			throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();

			inputParams.put("p_study_id", addPhaseWisePetRequest.getStudyId());
			inputParams.put("p_phase_id", addPhaseWisePetRequest.getPhaseId());
			inputParams.put("p_from_date", addPhaseWisePetRequest.getFromDate());
			inputParams.put("p_reason", addPhaseWisePetRequest.getReason());

			List<Integer> petIds = addPhaseWisePetRequest.getPetList().stream().filter(e -> (e.getPetId() > 0))
					.map(e -> e.getPetId()).collect(Collectors.toList());
			inputParams.put("p_pet_ids", StringUtils.join(petIds, ','));

			inputParams.put("p_login_user_id", userId);

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_PET_MOVE_TO_NEXT_PHASE, inputParams);
			LOGGER.info("outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");

			LOGGER.info("statusFlag : " + statusFlag + " errorMsg : " + errorMsg);
			if (!(StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO)) {

				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"study movetonextphase service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList(new WearablesError(
									WearablesErrorCode.STUDY_MOVE_TO_NEXT_PHASE_NO_STUDY_NEXT_PHASE)));
				} else if (statusFlag == -3) {
					throw new ServiceExecutionException(
							"study movetonextphase service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList(new WearablesError(
									WearablesErrorCode.STUDY_MOVE_TO_NEXT_PHASE_NO_PET_NEXT_PHASE, errorMsg)));
				} else if (statusFlag == -4) {
					throw new ServiceExecutionException(
							"study movetonextphase service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList(new WearablesError(
									WearablesErrorCode.STUDY_MOVE_TO_NEXT_PHASE_PET_INVALID_MOVE_DATE, errorMsg)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing movePetsToNextPhase ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	public Map<String, Integer> getStudyDiaryListCount(int studyId, StudyDiaryFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getStudyDiaryListCount called");
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		try {
			counts = selectForObject(SQLConstants.STUDY_DIARY_GET_LIST_COUNT, String.class, filter.getSearchText(),
					studyId, filter.getFilterType(), filter.getFilterValue());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getStudyDiaryListCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<StudyDiary> getStudyDiaryList(int studyId, StudyDiaryFilter filter) throws ServiceExecutionException {
		List<StudyDiary> studyDairies = new ArrayList<>();
		LOGGER.debug("getStudyDiaryList called");
		try {
			jdbcTemplate.query(SQLConstants.STUDY_DIARY_GET_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					StudyDiary studyDiaryDTO = new StudyDiary();

					studyDiaryDTO.setPetId(rs.getInt("PET_ID"));
					studyDiaryDTO.setPetName(rs.getString("PET_NAME"));
					studyDiaryDTO.setStudyId(rs.getInt("STUDY_ID"));
					studyDiaryDTO.setStudyName(rs.getString("STUDY_NAME"));
					studyDiaryDTO.setStudyPhaseDayId(rs.getInt("STUDY_PHASE_ID"));
					studyDiaryDTO.setStudyPhaseName(rs.getString("STUDY_PHASE_NAME"));
					studyDiaryDTO.setStudyPhaseDayId(rs.getInt("STUDY_PHASE_DAY"));
					studyDiaryDTO.setStudyEventId(rs.getInt("STUDY_EVENT_ID"));
					studyDiaryDTO.setStudyEventName(rs.getString("STUDY_EVENT_NAME"));
					studyDiaryDTO.setStudyTaskId(rs.getInt("STUDY_TASK_ID"));
					studyDiaryDTO.setStudyTaskName(rs.getString("STUDY_TASK_NAME"));
					studyDiaryDTO.setComments(rs.getString("COMMENTS"));

					studyDiaryDTO.setCreatedBy(rs.getInt("CREATED_BY"));
					studyDiaryDTO.setCreatedByUser(rs.getString("FULL_NAME"));
					studyDiaryDTO.setCreatedDate(rs.getDate("CREATED_DATE").toLocalDate());

					studyDairies.add(studyDiaryDTO);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), studyId,
					filter.getSearchText(), filter.getFilterType(), filter.getFilterValue());

		} catch (Exception e) {
			LOGGER.error("error while fetching getStudyDiaryList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return studyDairies;
	}

	@Override
	public void extendPhase(AddPhaseWisePetRequest extendPhaseRequest, Integer userId)
			throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_study_id", extendPhaseRequest.getStudyId());
			inputParams.put("p_phase_id", extendPhaseRequest.getPhaseId());
			inputParams.put("p_user_id", userId);
			inputParams.put("p_extend_reason", extendPhaseRequest.getReason());
			inputParams.put("p_extend_days", extendPhaseRequest.getDays());
			inputParams.put("p_extend_pets", mapper.writeValueAsString(extendPhaseRequest.getPetList()));

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_EXTEND_PHASE, inputParams);
			LOGGER.info("outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("Study has been updated successfully, Study id is ", extendPhaseRequest.getStudyId());
			} else {
				String[] error = errorMsg.split("##");
				throw new ServiceExecutionException("Extend phase validation failed",
						Status.BAD_REQUEST.getStatusCode(),
						Arrays.asList(new WearablesError(WearablesErrorCode.STUDY_EXTEND_PHASE_SCHEDULE_ALREADY_EXISTS,
								error[0], error[1], error[2])));
			}
		} catch (SQLException | JsonProcessingException e) {
			LOGGER.error("error while executing updateStudy ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getFeedingScheduleResponseCount(FeedingScheduleResponseFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getFeedingScheduleResponseCount called");
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		try {
			counts = selectForObject(SQLConstants.STUDY_PHASE_GET_FEEDING_RESPONSE_COUNT, String.class,
					filter.getSearchText(), filter.getStudyId(), filter.getPhaseId(), filter.getStartDate(),
					filter.getEndDate());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getFeedingScheduleResponseCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<FeedingSchedule> getFeedingScheduleResponse(FeedingScheduleResponseFilter filter)
			throws ServiceExecutionException {
		List<FeedingSchedule> feedingScheduleList = new ArrayList<>();
		LOGGER.debug("getFeedingScheduleResponse called");
		try {
			jdbcTemplate.query(SQLConstants.STUDY_PHASE_GET_FEEDING_RESPONSE, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					FeedingSchedule feedingSchedule = new FeedingSchedule();
					feedingSchedule.setFeedingScheduleId(rs.getInt("FEEDING_SCHEDULE_ID"));
					feedingSchedule.setPetId(rs.getInt("PET_ID"));
					feedingSchedule.setPetName(rs.getString("PET_NAME"));
					feedingSchedule.setTreatmentGroupName(rs.getString("GROUP_NAME"));
					feedingSchedule.setScheduleDate(
							rs.getString("SCHEDULED_DATE") == null ? "" : rs.getString("SCHEDULED_DATE"));
					feedingSchedule.setDietName(rs.getString("DIET_NAME"));
					double recommendedQty = rs.getDouble("RECOMMENDED_FOOD_AMOUNT");

					if (rs.wasNull()) {
						feedingSchedule.setRecommendFoodAmount(0.00);
					} else {
						feedingSchedule.setRecommendFoodAmount(recommendedQty);
					}
					feedingSchedule.setRecommendationDietAmtCups(rs.getFloat("FINAL_ROUNDED_REC_AMT_CUPS"));
					feedingSchedule.setRecommendationDietAmtGrams(rs.getFloat("FINAL_ROUNDED_REC_AMT_GRAMS"));
					double qtyConsumed = rs.getDouble("QUANTITY_CONSUMED");

					if (rs.wasNull()) {
						feedingSchedule.setQuantityConsumed(0.00);
					} else {
						feedingSchedule.setQuantityConsumed(qtyConsumed);
					}
					feedingSchedule.setUnitName(rs.getString("UNIT_ABBREVATION"));
					feedingSchedule.setStatus(rs.getString("status"));
					feedingScheduleList.add(feedingSchedule);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getStudyId(), filter.getPhaseId(), filter.getStartDate(), filter.getEndDate());

		} catch (Exception e) {
			LOGGER.error("error while fetching getFeedingScheduleResponse", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return feedingScheduleList;
	}

	@Override
	public FeedingSchedulesResponse getFeedingScheduleByPet(int feedingScheduleId) throws ServiceExecutionException {

		FeedingSchedulesResponse feedingSchedulesResponse = new FeedingSchedulesResponse();
		List<FeedingSchedule> feedingScheduleList = new ArrayList<>();

		LOGGER.debug("getFeedingScheduleByPet called");
		try {
			jdbcTemplate.query(SQLConstants.STUDY_GET_FEEDING_SCHEDULE_BY_PET, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					FeedingSchedule feedingSchedule = new FeedingSchedule();
					feedingSchedule.setFeedingScheduleId(rs.getInt("FEEDING_SCHEDULE_ID"));
					feedingSchedule.setPetId(rs.getInt("PET_ID"));
					feedingSchedule.setPetName(rs.getString("PET_NAME"));
					feedingSchedule.setTreatmentGroupName(rs.getString("GROUP_NAME"));
					feedingSchedule.setScheduleDate(
							rs.getString("SCHEDULED_DATE") == null ? "" : rs.getString("SCHEDULED_DATE"));
					feedingSchedule.setDietName(rs.getString("DIET_NAME"));
					double recommendedQty = rs.getDouble("RECOMMENDED_FOOD_AMOUNT");

					if (rs.wasNull()) {
						feedingSchedule.setRecommendFoodAmount(0.00);
					} else {
						feedingSchedule.setRecommendFoodAmount(recommendedQty);
					}
					feedingSchedule.setRecommendationDietAmtCups(rs.getFloat("FINAL_ROUNDED_REC_AMT_CUPS"));
					feedingSchedule.setRecommendationDietAmtGrams(rs.getFloat("FINAL_ROUNDED_REC_AMT_GRAMS"));

					double qtyConsumed = rs.getDouble("QUANTITY_CONSUMED");

					if (rs.wasNull()) {
						feedingSchedule.setQuantityConsumed(0.00);
					} else {
						feedingSchedule.setQuantityConsumed(qtyConsumed);
					}
					feedingSchedule.setUnitName(rs.getString("UNIT_ABBREVATION"));
					feedingScheduleList.add(feedingSchedule);
				}
			}, feedingScheduleId);

			feedingSchedulesResponse.setFeedingScheduleList(feedingScheduleList);

		} catch (Exception e) {
			LOGGER.error("error while fetching getFeedingScheduleByPet", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return feedingSchedulesResponse;
	}

	@Override
	public void pushNotificationConfig(PushNotificationConfigRequest notificationConfigRequest)
			throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_study_id", notificationConfigRequest.getStudyId());
			inputParams.put("p_phase_id", notificationConfigRequest.getPhaseId());
			inputParams.put("p_push_notification_json",
					mapper.writeValueAsString(notificationConfigRequest.getPushNotificationsAssociated()));

			List<Integer> studyNotificationIds = notificationConfigRequest.getPushNotificationsAssociated().stream()
					.filter(e -> (e.getStudyPushNotificationConfigId() != null
							&& e.getStudyPushNotificationConfigId() > 0))
					.map(e -> e.getStudyPushNotificationConfigId()).collect(Collectors.toList());
			inputParams.put("p_active_study_push_noti_config_ids", StringUtils.join(studyNotificationIds, ','));
			inputParams.put("p_modified_by", notificationConfigRequest.getUserId());

			LOGGER.info("pushNotificationConfig inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_PHASE_PUSH_NOTIFICATION_CONFIG,
					inputParams);
			LOGGER.info("pushNotificationConfig outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				// getting the inserted flag value
				int studyStatusId = (int) outParams.get("out_study_status_id");
				String newStudyConfigIds = (String) outParams.get("out_new_study_img_config_ids");
				String deleteStudyConfigIds = (String) outParams.get("out_delete_study_img_config_ids");
				LOGGER.info("pushNotificationConfig statusFlag is {}", statusFlag);
				LOGGER.info("pushNotificationConfig newStudyConfigIds are {}", newStudyConfigIds);
				LOGGER.info("pushNotificationConfig deleteStudyConfigIds is {}", deleteStudyConfigIds);

				// invoke scheduler procedure
				if (studyStatusId == 2 || studyStatusId == 3) {
					invokePushNotificationScheduler(notificationConfigRequest.getStudyId(), newStudyConfigIds,
							deleteStudyConfigIds, notificationConfigRequest.getUserId()).subscribe(response -> {
								// Handle the asynchronous response
								System.out.println("Asynchronous response: " + response);
							});
				}
			} else {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (SQLException | JsonProcessingException e) {
			LOGGER.error("error while executing pushNotificationConfig ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public List<PushNotificationAssociated> getPushNotificationConfig(int studyId, int phaseId)
			throws ServiceExecutionException {
		List<PushNotificationAssociated> pushNotificationsAssociated = new ArrayList<>();
		try {
			jdbcTemplate.query(SQLConstants.STUDY_PHASE_GET_PUSH_NOTIFICATION_CONFIG, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PushNotificationAssociated pushNotificationAssociated = new PushNotificationAssociated();
					pushNotificationAssociated
							.setStudyPushNotificationConfigId(rs.getInt("STUDY_PUSH_NOTIFICATION_CONFIG_ID"));
					pushNotificationAssociated.setStudyId(rs.getInt("STUDY_ID"));
					pushNotificationAssociated.setPhaseId(rs.getInt("STUDY_PHASE_ID"));
					pushNotificationAssociated.setNotificationId(rs.getInt("PUSH_NOTIFICATION_ID"));
					pushNotificationAssociated.setNotificationName(rs.getString("NOTIFICATION_NAME"));

					pushNotificationAssociated.setFrequencyId(rs.getInt("FREQUENCY_ID"));
					pushNotificationAssociated.setFrequency(rs.getString("FREQUENCY"));
					pushNotificationAssociated.setDisplayTime(rs.getString("DISPLAY_TIME"));

					pushNotificationAssociated.setStartDate(rs.getString("FROM_DATE"));
					pushNotificationAssociated.setEndDate(rs.getString("TO_DATE"));
					pushNotificationsAssociated.add(pushNotificationAssociated);
				}
			}, studyId, phaseId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPushNotificationConfig", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return pushNotificationsAssociated;
	}

	@Override
	public void imageScoringConfig(ImageScoringConfigRequest imageScoringConfigRequest)
			throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_study_id", imageScoringConfigRequest.getStudyId());
			inputParams.put("p_phase_id", imageScoringConfigRequest.getPhaseId());
			inputParams.put("p_image_scoring_json",
					mapper.writeValueAsString(imageScoringConfigRequest.getImageScoringSaclesAssociated()));

			List<Integer> studyImgIds = imageScoringConfigRequest.getImageScoringSaclesAssociated().stream()
					.filter(e -> (e.getStudyImageScoringConfigId() != null && e.getStudyImageScoringConfigId() > 0))
					.map(e -> e.getStudyImageScoringConfigId()).collect(Collectors.toList());
			inputParams.put("p_active_study_img_config_ids", StringUtils.join(studyImgIds, ','));
			inputParams.put("p_modified_by", imageScoringConfigRequest.getUserId());

			LOGGER.info("imageScoringConfig inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_PHASE_IMAGE_SCORING_CONFIG,
					inputParams);
			LOGGER.info("imageScoringConfig outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				// getting the inserted flag value
				int studyStatusId = (int) outParams.get("out_study_status_id");
				String newStudyConfigIds = (String) outParams.get("out_new_study_img_config_ids");
				String deleteStudyConfigIds = (String) outParams.get("out_delete_study_img_config_ids");
				LOGGER.info("imageScoringConfig statusFlag is {}", statusFlag);
				LOGGER.info("imageScoringConfig newStudyConfigIds are {}", newStudyConfigIds);
				LOGGER.info("imageScoringConfig deleteStudyConfigIds is {}", deleteStudyConfigIds);

				// invoke scheduler procedure
				if (studyStatusId == 2 || studyStatusId == 3) {
					invokeImageScoringScheduler(imageScoringConfigRequest.getStudyId(), newStudyConfigIds,
							deleteStudyConfigIds, imageScoringConfigRequest.getUserId()).subscribe(response -> {
								// Handle the asynchronous response
								System.out.println("Asynchronous response: " + response);
							});
				}
			} else {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (SQLException | JsonProcessingException e) {
			LOGGER.error("error while executing imageScoringConfig ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public List<ImageScoringAssociated> getImageScoringConfig(int studyId, int phaseId)
			throws ServiceExecutionException {
		List<ImageScoringAssociated> imageScoringAssociatedList = new ArrayList<>();
		try {
			jdbcTemplate.query(SQLConstants.STUDY_PHASE_GET_IMAGE_SCORING_CONFIG, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					ImageScoringAssociated imageScoringScalesAssociated = new ImageScoringAssociated();
					imageScoringScalesAssociated.setStudyImageScoringConfigId(rs.getInt("STUDY_IMG_SCORING_CONFIG_ID"));
					imageScoringScalesAssociated.setStudyId(rs.getInt("STUDY_ID"));
					imageScoringScalesAssociated.setPhaseId(rs.getInt("STUDY_PHASE_ID"));
					imageScoringScalesAssociated.setImageScoringId(rs.getInt("IMAGE_SCORING_ID"));
					imageScoringScalesAssociated.setImageScaleName(rs.getString("IMAGE_SCALE_NAME"));

					imageScoringScalesAssociated.setFrequencyId(rs.getInt("FREQUENCY_ID"));
					imageScoringScalesAssociated.setFrequency(rs.getString("FREQUENCY"));

					imageScoringScalesAssociated.setStartDate(rs.getString("FROM_DATE"));
					imageScoringScalesAssociated.setEndDate(rs.getString("TO_DATE"));
					imageScoringAssociatedList.add(imageScoringScalesAssociated);
				}
			}, studyId, phaseId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getImageScoringConfig", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return imageScoringAssociatedList;
	}

	private Mono<String> invokeImageScoringScheduler(Integer studyId, String newStudyConfigIds,
			String deleteStudyConfigIds, Integer userId) {
		try {
			return Mono.fromCallable(() -> {
				if (StringUtils.isNotEmpty(newStudyConfigIds)) {
					Map<String, Object> inputParams = new HashMap<>();
					inputParams.put("p_study_id", studyId);
					inputParams.put("p_pet_id", null);
					inputParams.put("p_study_phase_id", null);
					inputParams.put("p_from_date", null);
					inputParams.put("p_user_id", userId);

					LOGGER.info("invokeImageScoringScheduler create inputParams are {}", inputParams);
					Map<String, Object> outParams = callStoredProcedure(
							SQLConstants.STUDY_CREATE_IMAGE_SCORING_SCHEDULE, inputParams);
					LOGGER.info("invokeImageScoringScheduler create outParams are {}", outParams);
				}

				if (StringUtils.isNotEmpty(deleteStudyConfigIds)) {
					Map<String, Object> inputParams = new HashMap<>();
					inputParams.put("p_study_img_sco_conf_id", deleteStudyConfigIds);
					inputParams.put("p_remove_date", null);
					inputParams.put("p_user_id", userId);

					LOGGER.info("invokeImageScoringScheduler delete inputParams are {}", inputParams);
					try {
						Map<String, Object> outParams = callStoredProcedure(
								SQLConstants.STUDY_REMOVE_IMAGE_SCORING_SCHEDULE, inputParams);
						LOGGER.info("invokeImageScoringScheduler delete outParams are {}", outParams);
					} catch (SQLException e) {
						LOGGER.error("error while deleting schedule", e);
						throw new RuntimeException("Error while deleting schedule", e);
					}
				}
				return "invokeImageScoringScheduler completed for " + studyId;
			});
		} catch (Exception e) {
			LOGGER.error("error while executing invokeImageScoringScheduler", e);
			return Mono.error(e); // Convert the exception to a Mono error
		}
	}

	@Override
	public void questionnaireConfig(QuestionnaireConfigRequest questionnaireConfigRequest)
			throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_study_id", questionnaireConfigRequest.getStudyId());
			inputParams.put("p_phase_id", questionnaireConfigRequest.getPhaseId());
			inputParams.put("p_questionnaire_json",
					mapper.writeValueAsString(questionnaireConfigRequest.getQuestionnairesAssociated()));

			List<Integer> studyQuesIds = questionnaireConfigRequest.getQuestionnairesAssociated().stream()
					.filter(e -> (e.getStudyQuestionnaireConfigId() != null && e.getStudyQuestionnaireConfigId() > 0))
					.map(e -> e.getStudyQuestionnaireConfigId()).collect(Collectors.toList());
			inputParams.put("p_active_study_ques_config_ids", StringUtils.join(studyQuesIds, ','));
			inputParams.put("p_modified_by", questionnaireConfigRequest.getUserId());

			LOGGER.info("questionnaireConfig inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_PHASE_QUESTIONNAIRE_CONFIG,
					inputParams);
			LOGGER.info("questionnaireConfig outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				// getting the inserted flag value
				int studyStatusId = (int) outParams.get("out_study_status_id");
				String newStudyConfigIds = (String) outParams.get("out_new_study_ques_config_ids");
				String deleteStudyConfigIds = (String) outParams.get("out_delete_study_ques_config_ids");
				LOGGER.info("questionnaireConfig statusFlag is {}", statusFlag);
				LOGGER.info("questionnaireConfig newStudyConfigIds are {}", newStudyConfigIds);
				LOGGER.info("questionnaireConfig deleteStudyConfigIds is {}", deleteStudyConfigIds);

				// invoke scheduler procedure
				if (studyStatusId == 2 || studyStatusId == 3) {
					invokeQuestionnaireScheduler(questionnaireConfigRequest.getStudyId(), newStudyConfigIds,
							deleteStudyConfigIds, questionnaireConfigRequest.getUserId()).subscribe(response -> {
								// Handle the asynchronous response
								System.out.println("Asynchronous response: " + response);
							});
				}
			} else {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (SQLException | JsonProcessingException e) {
			LOGGER.error("error while executing questionnaireConfig ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	private Mono<String> invokeQuestionnaireScheduler(Integer studyId, String newStudyConfigIds,
			String deleteStudyConfigIds, Integer userId) {
		try {
			return Mono.fromCallable(() -> {
				if (StringUtils.isNotEmpty(newStudyConfigIds)) {
					Map<String, Object> inputParams = new HashMap<>();
					inputParams.put("p_study_id", studyId);
					inputParams.put("p_pet_id", null);
					inputParams.put("p_study_phase_id", null);
					inputParams.put("p_from_date", null);
					inputParams.put("p_user_id", userId);

					LOGGER.info("invokeQuestionnaireScheduler create inputParams are {}", inputParams);
					Map<String, Object> outParams = callStoredProcedure(
							SQLConstants.STUDY_CREATE_QUESTIONNAIRE_SCHEDULE, inputParams);
					LOGGER.info("invokeQuestionnaireScheduler create outParams are {}", outParams);
				}

				if (StringUtils.isNotEmpty(deleteStudyConfigIds)) {
					Map<String, Object> inputParams = new HashMap<>();
					inputParams.put("p_study_questionnaire_config_id", deleteStudyConfigIds);
					inputParams.put("p_remove_date", null);
					inputParams.put("p_user_id", userId);

					LOGGER.info("invokeQuestionnaireScheduler delete inputParams are {}", inputParams);
					try {
						Map<String, Object> outParams = callStoredProcedure(
								SQLConstants.STUDY_REMOVE_QUESTIONNAIRE_SCHEDULE, inputParams);
						LOGGER.info("invokeQuestionnaireScheduler delete outParams are {}", outParams);
					} catch (SQLException e) {
						LOGGER.error("error while deleting schedule", e);
						throw new RuntimeException("Error while deleting schedule", e);
					}
				}
				return "invokeQuestionnaireScheduler completed for " + studyId;
			});
		} catch (Exception e) {
			LOGGER.error("error while executing invokeQuestionnaireScheduler", e);
			return Mono.error(e); // Convert the exception to a Mono error
		}
	}

	@Override
	public List<QuestionnaireAssociated> getQuestionnaireConfig(int studyId, int phaseId)
			throws ServiceExecutionException {
		List<QuestionnaireAssociated> questionnaireAssociateds = new ArrayList<>();
		try {
			jdbcTemplate.query(SQLConstants.STUDY_PHASE_GET_QUESTIONNAIRE_CONFIG, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					QuestionnaireAssociated questionnaireAssociated = new QuestionnaireAssociated();
					questionnaireAssociated.setStudyQuestionnaireConfigId(rs.getInt("STUDY_QUESTIONNAIRE_CONFIG_ID"));
					questionnaireAssociated.setStudyId(rs.getInt("STUDY_ID"));
					questionnaireAssociated.setPhaseId(rs.getInt("STUDY_PHASE_ID"));
					questionnaireAssociated.setQuestionnaireId(rs.getInt("QUESTIONNAIRE_ID"));
					questionnaireAssociated.setQuestionnaireName(rs.getString("QUESTIONNAIRE_NAME"));

					questionnaireAssociated.setOccuranceId(rs.getInt("OCCURANCE_ID"));
					questionnaireAssociated.setOccurance(rs.getString("OCCURANCE_NAME"));
					questionnaireAssociated.setFrequencyId(rs.getInt("FREQUENCY_ID"));
					questionnaireAssociated.setFrequency(rs.getString("FREQUENCY_NAME"));

					questionnaireAssociated.setStartDate(rs.getString("FROM_DATE"));
					questionnaireAssociated.setEndDate(rs.getString("TO_DATE"));
					if (StringUtils.isNotEmpty(rs.getString("PHASE_DAY"))) {
						questionnaireAssociated.setPhaseDays(Arrays.asList(rs.getString("PHASE_DAY").split(","))
								.stream().map(e -> Integer.parseInt(e)).collect(Collectors.toList()));
					}
					questionnaireAssociateds.add(questionnaireAssociated);
				}
			}, studyId, phaseId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getQuestionnaireConfig", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return questionnaireAssociateds;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getImageScoringResponseCount(ImageScaleFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getImageScoringResponseCount called");
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		try {
			counts = selectForObject(SQLConstants.STUDY_PHASE_GET_IMAGE_SCORE_RESPONSE_COUNT, String.class,
					filter.getSearchText(), filter.getStudyId(), filter.getPhaseId(), filter.getScoringTypeId(),
					filter.getStartDate(), filter.getEndDate());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getImageScoringResponseCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<ImageScoringResponse> getImageScoringResponse(ImageScaleFilter filter)
			throws ServiceExecutionException {
		List<ImageScoringResponse> imageScoringResponseList = new ArrayList<>();
		LOGGER.debug("getFeedingScheduleResponse called");
		try {
			jdbcTemplate.query(SQLConstants.STUDY_PHASE_GET_IMAGE_SCORE_RESPONSE, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					ImageScoringResponse imageScoringResponse = new ImageScoringResponse();
					imageScoringResponse.setPetId(rs.getInt("PET_ID"));
					imageScoringResponse.setPetName(rs.getString("PET_NAME"));
					imageScoringResponse.setScoreType(rs.getString("SCORING_TYPE"));
					imageScoringResponse.setScaleName(rs.getString("IMAGE_SCALE_NAME"));
					imageScoringResponse.setImageLabel(rs.getString("IMAGE_LABEL"));
					imageScoringResponse.setScore(rs.getString("SCORE"));
					imageScoringResponse.setPetImagePath(rs.getString("PET_IMAGE"));
					// imageScoringResponse.setPetImgThumbnailPath(rs.getString("PET_IMAGE_THUMBNAIL"));
					String imageName = rs.getString("SCALE_IMAGE");
					if (StringUtils.isNotEmpty(imageName)) {
						imageScoringResponse.setScaleImagePath(
								gcpClientUtil.getDownloaFiledUrl(imageName, Constants.GCP_IMAGE_SCORING_PATH));
					}

					LocalDateTime startDate = rs.getTimestamp("FROM_DATE").toLocalDateTime();
					imageScoringResponse.setFromDate(startDate.toLocalDate());
					LocalDateTime endDate = rs.getTimestamp("TO_DATE").toLocalDateTime();
					imageScoringResponse.setToDate(endDate.toLocalDate());

					LocalDateTime scheduleDate = rs.getTimestamp("SCHEDULED_DATE").toLocalDateTime();
					imageScoringResponse.setScheduleDate(scheduleDate.toLocalDate());
					LocalDateTime submittedDate = rs.getTimestamp("ANSWERED_DATE") != null
							? rs.getTimestamp("ANSWERED_DATE").toLocalDateTime()
							: null;
					imageScoringResponse.setSubmittedDate(submittedDate != null ? submittedDate.toLocalDate() : null);

					LocalDateTime nextScheduleDate = rs.getTimestamp("NEXT_SCHEDULED_DATE") != null
							? rs.getTimestamp("NEXT_SCHEDULED_DATE").toLocalDateTime()
							: null;
					if (submittedDate != null) {
						imageScoringResponse.setStatus("Completed");
						if (nextScheduleDate == null) {
							imageScoringResponse.setDueDate(imageScoringResponse.getToDate());
						} else {
							imageScoringResponse
									.setDueDate(nextScheduleDate.toLocalDate().minusDays(NumberUtils.INTEGER_ONE));
						}
					} else {
						if (nextScheduleDate == null) {
							if (imageScoringResponse.getToDate().isBefore(LocalDate.now())) {
								imageScoringResponse.setStatus("Missed");
							} else {
								if (scheduleDate.toLocalDate().isEqual(LocalDate.now())
										|| scheduleDate.toLocalDate().isAfter(LocalDate.now())) {
									imageScoringResponse.setStatus("Scheduled");
								} else {
									imageScoringResponse.setStatus("Pending");
								}
							}
							imageScoringResponse.setDueDate(imageScoringResponse.getToDate());
						} else {
							imageScoringResponse
									.setDueDate(nextScheduleDate.toLocalDate().minusDays(NumberUtils.INTEGER_ONE));
							if (nextScheduleDate.toLocalDate().isEqual(LocalDate.now())
									|| nextScheduleDate.toLocalDate().isBefore(LocalDate.now())) {
								imageScoringResponse.setStatus("Missed");
							} else {
								if (scheduleDate.toLocalDate().isEqual(LocalDate.now())
										|| scheduleDate.toLocalDate().isAfter(LocalDate.now())) {
									imageScoringResponse.setStatus("Scheduled");
								} else {
									imageScoringResponse.setStatus("Pending");
								}
							}
						}
					}

					imageScoringResponseList.add(imageScoringResponse);

				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getStudyId(), filter.getPhaseId(), filter.getScoringTypeId(), filter.getStartDate(),
					filter.getEndDate());

		} catch (Exception e) {
			LOGGER.error("error while fetching getImageScoringResponse", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return imageScoringResponseList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getQuestionnaireResponseCount(QuestionnaireResponseFilter filter)
			throws ServiceExecutionException {
		HashMap<String, Integer> map = new HashMap<>();
		try {
			String counts = selectForObject(SQLConstants.STUDY_PHASE_QUESTIONNAIRE_RESPONSE_LIST_COUNT, String.class,
					filter.getStudyId(), filter.getPhaseId(), filter.getQuestionnaireId(), filter.getStartDate(),
					filter.getEndDate(), filter.getSearchText());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while executing getQuestionnaireResponseByPetCount ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<StudyPhaseQuestionnaireScheduleDTO> getQuestionnaireResponse(QuestionnaireResponseFilter filter)
			throws ServiceExecutionException {
		List<StudyPhaseQuestionnaireScheduleDTO> studyPhaseQuestionnaireScheduleDTOs = new ArrayList<>();
		try {
			String sql = SQLConstants.STUDY_PHASE_QUESTIONNAIRE_RESPONSE_LIST;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

			jdbcTemplate.query(sql, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					StudyPhaseQuestionnaireScheduleDTO studyPhaseQuestionnaireScheduleDTO = new StudyPhaseQuestionnaireScheduleDTO();
					studyPhaseQuestionnaireScheduleDTO
							.setStudyQuestionnaireScheduleId(rs.getInt("STUDY_QUESTIONNAIRE_SCHEDULE_ID"));
					studyPhaseQuestionnaireScheduleDTO
							.setQuestionnaireResponseId(rs.getInt("QUESTIONNAIRE_RESPONSE_ID"));
					studyPhaseQuestionnaireScheduleDTO.setQuestionnaireId(rs.getInt("QUESTIONNAIRE_ID"));
					studyPhaseQuestionnaireScheduleDTO.setQuestionnaireName(rs.getString("QUESTIONNAIRE_NAME"));
					studyPhaseQuestionnaireScheduleDTO.setOccurrenceId(rs.getInt("OCCURANCE_ID"));
					studyPhaseQuestionnaireScheduleDTO.setOccurrence(rs.getString("OCCURANCE_NAME"));
					studyPhaseQuestionnaireScheduleDTO.setFrequencyId(rs.getInt("FREQUENCY_ID"));
					studyPhaseQuestionnaireScheduleDTO.setFrequency(rs.getString("FREQUENCY_NAME"));
					studyPhaseQuestionnaireScheduleDTO.setPetId(rs.getInt("PET_ID"));
					studyPhaseQuestionnaireScheduleDTO.setPetName(rs.getString("PET_NAME"));
					studyPhaseQuestionnaireScheduleDTO.setPetParentId(rs.getInt("PET_PARENT_ID"));
					studyPhaseQuestionnaireScheduleDTO.setPetParentName(rs.getString("PET_PARENT_NAME"));

					LocalDateTime startDate = rs.getTimestamp("FROM_DATE") != null
							? rs.getTimestamp("FROM_DATE").toLocalDateTime()
							: null;
					studyPhaseQuestionnaireScheduleDTO.setFromDate(startDate != null ? startDate.toLocalDate() : null);
					LocalDateTime endDate = rs.getTimestamp("TO_DATE") != null
							? rs.getTimestamp("TO_DATE").toLocalDateTime()
							: null;
					studyPhaseQuestionnaireScheduleDTO.setToDate(endDate != null ? endDate.toLocalDate() : null);

					LocalDateTime scheduleDate = rs.getTimestamp("SCHEDULED_DATE").toLocalDateTime();
					studyPhaseQuestionnaireScheduleDTO.setScheduledDate(scheduleDate.toLocalDate());
					LocalDateTime submittedDate = rs.getTimestamp("SUBMITTED_DATE") != null
							? rs.getTimestamp("SUBMITTED_DATE").toLocalDateTime()
							: null;
					studyPhaseQuestionnaireScheduleDTO
							.setSubmittedDate(submittedDate != null ? submittedDate.toLocalDate() : null);

					LocalDateTime nextScheduleDate = rs.getTimestamp("NEXT_SCHEDULED_DATE") != null
							? rs.getTimestamp("NEXT_SCHEDULED_DATE").toLocalDateTime()
							: null;
					if (submittedDate != null) {
						studyPhaseQuestionnaireScheduleDTO.setStatus("Completed");
						if (nextScheduleDate == null) {
							studyPhaseQuestionnaireScheduleDTO
									.setDueDate(studyPhaseQuestionnaireScheduleDTO.getToDate());
						} else {
							studyPhaseQuestionnaireScheduleDTO
									.setDueDate(nextScheduleDate.toLocalDate().minusDays(NumberUtils.INTEGER_ONE));
						}
					} else {
						if (nextScheduleDate == null) {
							if (studyPhaseQuestionnaireScheduleDTO.getFrequencyId() != 5
									&& studyPhaseQuestionnaireScheduleDTO.getToDate().isBefore(LocalDate.now())) {
								studyPhaseQuestionnaireScheduleDTO.setStatus("Missed");
							} else {
								if (scheduleDate.toLocalDate().isEqual(LocalDate.now())
										|| scheduleDate.toLocalDate().isAfter(LocalDate.now())) {
									studyPhaseQuestionnaireScheduleDTO.setStatus("Scheduled");
								} else {
									studyPhaseQuestionnaireScheduleDTO.setStatus("Pending");
								}
							}
							studyPhaseQuestionnaireScheduleDTO
									.setDueDate(studyPhaseQuestionnaireScheduleDTO.getToDate());
						} else {
							studyPhaseQuestionnaireScheduleDTO
									.setDueDate(nextScheduleDate.toLocalDate().minusDays(NumberUtils.INTEGER_ONE));
							if (nextScheduleDate.toLocalDate().isEqual(LocalDate.now())
									|| nextScheduleDate.toLocalDate().isBefore(LocalDate.now())) {
								studyPhaseQuestionnaireScheduleDTO.setStatus("Missed");
							} else {
								if (scheduleDate.toLocalDate().isEqual(LocalDate.now())
										|| scheduleDate.toLocalDate().isAfter(LocalDate.now())) {
									studyPhaseQuestionnaireScheduleDTO.setStatus("Scheduled");
								} else {
									studyPhaseQuestionnaireScheduleDTO.setStatus("Pending");
								}
							}
						}
					}
					studyPhaseQuestionnaireScheduleDTOs.add(studyPhaseQuestionnaireScheduleDTO);
				}
			}, filter.getStudyId(), filter.getPhaseId(), filter.getQuestionnaireId(), filter.getStartDate(),
					filter.getEndDate(), filter.getSearchText(), filter.getStartIndex(), filter.getLimit(),
					filter.getOrder(), filter.getSortBy());
		} catch (Exception e) {
			LOGGER.error("error while executing getQuestionnaireResponseByPet ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return studyPhaseQuestionnaireScheduleDTOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getPetFoodIntakeHistoryCount(FeedingScheduleResponseFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getPetFoodIntakeHistoryCount called");
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		try {
			counts = selectForObject(SQLConstants.PET_GET_FEEDING_RESPONSE_COUNT, String.class, filter.getSearchText(),
					filter.getStudyId(), filter.getPhaseId(), filter.getPetId(), filter.getStartDate(),
					filter.getEndDate());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetFoodIntakeHistoryCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<FeedingSchedule> getPetFoodIntakeHistory(FeedingScheduleResponseFilter filter)
			throws ServiceExecutionException {
		List<FeedingSchedule> feedingScheduleList = new ArrayList<>();
		LOGGER.debug("getPetFoodIntakeHistory called");
		try {
			jdbcTemplate.query(SQLConstants.PET_GET_FOOD_INTAKE_RESPONSE, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					FeedingSchedule feedingSchedule = new FeedingSchedule();
					feedingSchedule.setFeedingScheduleId(rs.getInt("FEEDING_SCHEDULE_ID"));
					feedingSchedule.setPetId(rs.getInt("PET_ID"));
					feedingSchedule.setPetName(rs.getString("PET_NAME"));
					feedingSchedule.setTreatmentGroupName(rs.getString("GROUP_NAME"));
					feedingSchedule.setScheduleDate(
							rs.getString("SCHEDULED_DATE") == null ? "" : rs.getString("SCHEDULED_DATE"));
					feedingSchedule.setDietName(rs.getString("DIET_NAME"));
					double recommendedQty = rs.getDouble("RECOMMENDED_FOOD_AMOUNT");
					feedingSchedule.setRecommendationDietAmtCups(rs.getFloat("FINAL_ROUNDED_REC_AMT_CUPS"));
					feedingSchedule.setRecommendationDietAmtGrams(rs.getFloat("FINAL_ROUNDED_REC_AMT_GRAMS"));

					if (rs.wasNull()) {
						feedingSchedule.setRecommendFoodAmount(0.00);
					} else {
						feedingSchedule.setRecommendFoodAmount(recommendedQty);
					}

					double qtyConsumed = rs.getDouble("QUANTITY_CONSUMED");

					if (rs.wasNull()) {
						feedingSchedule.setQuantityConsumed(0.00);
					} else {
						feedingSchedule.setQuantityConsumed(qtyConsumed);
					}
					feedingSchedule.setUnitName(rs.getString("UNIT_ABBREVATION"));
					feedingSchedule.setStatus(rs.getString("status"));
					feedingScheduleList.add(feedingSchedule);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getStudyId(), filter.getPhaseId(), filter.getPetId(), filter.getStartDate(),
					filter.getEndDate());

		} catch (Exception e) {
			LOGGER.error("error while fetching getPetFoodIntakeHistory", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return feedingScheduleList;
	}

	@Override
	public List<PetListDTO> getPets(int study_id) throws ServiceExecutionException {
		List<PetListDTO> petList = new ArrayList<>();
		try {
			jdbcTemplate.query(SQLConstants.STUDY_GET_ALL_PETS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetListDTO petListDTO = new PetListDTO();
					petListDTO.setPetId(rs.getInt("PET_ID"));
					petListDTO.setPetName(rs.getString("PET_NAME"));
					petListDTO.setPetPhoto(rs.getString("PHOTO_NAME"));
					petListDTO.setBreedName(rs.getString("BREED_NAME"));
					petListDTO.setGender(rs.getString("GENDER"));
					petListDTO.setIsActive(rs.getBoolean("IS_ACTIVE"));
					petList.add(petListDTO);
				}
			}, study_id);

		} catch (Exception e) {
			LOGGER.error("error while fetching getPets", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petList;
	}

	@Override
	public List<ThresholdByStudyReport> getThresholdByCountReport(
			FlaggedRecommendationRequest flaggedRecommendationRequest) throws ServiceExecutionException {
		LOGGER.debug("getThresholdByCountReport called");
		List<ThresholdByStudyReport> thresholdByStudyReportList = new ArrayList<>();
		Map<String, ThresholdByStudyReport> thresholdMap = new LinkedHashMap<>();
		try {
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("p_start_date", flaggedRecommendationRequest.getStartDate());
			inputParams.put("p_end_date", flaggedRecommendationRequest.getEndDate());
			inputParams.put("p_study_id", flaggedRecommendationRequest.getStudyId());
			Map<String, Object> simpleJdbcCallResult = callStoredProcedure(SQLConstants.AF_GET_THRESHOLD_BY_ACTION,
					inputParams);
			for (Entry<String, Object> entry : simpleJdbcCallResult.entrySet()) {
				String key = entry.getKey();
				if (key.equals(SQLConstants.RESULT_SET_1)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(rs -> {
						if (thresholdMap.containsKey(rs.get("threshold").toString())) {
							thresholdMap.get(rs.get("threshold").toString()).getThresholdActionByCount()
									.put(rs.get("STATUS").toString(), (long) rs.get("count"));
						} else {
							HashMap<String, Long> thresholdActionByCount = new HashMap<>(5);
							ThresholdByStudyReport tr = new ThresholdByStudyReport();
							tr.setMessage(rs.get("message").toString());
							thresholdActionByCount.put(rs.get("STATUS").toString(), (long) rs.get("count"));
							tr.setThreshold(rs.get("threshold").toString());
							tr.setThresholdActionByCount(thresholdActionByCount);
							thresholdMap.put(rs.get("threshold").toString(), tr);
						}
					});
				}
			}
		} catch (Exception e) {
			LOGGER.error("error while fetching getAssetsDevicesByStudyReport", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return new ArrayList<ThresholdByStudyReport>(thresholdMap.values());
	}

	@Override
	public FRLookUpResponse getFRFilterData(FlaggedRecommendationRequest flaggedRecommendationRequest)
			throws ServiceExecutionException {
		LOGGER.info("Started getFRFilterData");
		FRLookUpResponse response = new FRLookUpResponse();
		jdbcTemplate.query(SQLConstants.GET_FLAGGED_RECOMMENDATION_FILTER, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				HashMap<String, String> map = new HashMap<>(2);
				if (rs.getString("type").equals("ACTION")) {
					map.put("id", rs.getString("id"));
					map.put("name", rs.getString("name"));
					response.getActions().add(map);
				}
				if (rs.getString("type").equals("ACTION_ALL")) {
					map.put("id", rs.getString("id"));
					map.put("name", rs.getString("name"));
					response.getActionsAll().add(map);
				}
				if (rs.getString("type").equals("PET")) {
					map.put("petId", rs.getString("id"));
					map.put("petName", rs.getString("name"));
					response.getPets().add(map);
				}
				if (rs.getString("type").equals("THRESHOLD")) {
					map.put("id", rs.getString("id"));
					map.put("name", rs.getString("name"));
					response.getThreshold().add(map);
				}
			}
		}, flaggedRecommendationRequest.getStartDate(), flaggedRecommendationRequest.getEndDate(),
				flaggedRecommendationRequest.getStudyId());
		LOGGER.info("Completed getFRFilterData");
		return response;
	}

	@Override
	public HashMap<String, Integer> getRecommendationListForStudyCount(FlaggedRecommendationRequest request)
			throws ServiceExecutionException {
		LOGGER.debug("getRecommendationListForStudyCount called");
		String counts;
		HashMap<String, Integer> thresholdMap = getThreshold(request);
		HashMap<String, Integer> map = new HashMap<>(2);
		try {
			counts = selectForObject(SQLConstants.FN_GET_AF_FLAGGED_RECOMMENDATIONS_COUNT, String.class,
					request.getStudyId(), request.getPetIds().equals("null") ? null : request.getPetIds(),
					thresholdMap.get("thresholdId1"), thresholdMap.get("thresholdId2"),
					thresholdMap.get("thresholdId3"), thresholdMap.get("thresholdId4"),
					request.getActionIds().equals("null") ? null : request.getActionIds(), request.getStartDate(),
					request.getEndDate());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getRecommendationListForStudyCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<RecommendationDetails> getRecommendationListForStudy(FlaggedRecommendationRequest request)
			throws ServiceExecutionException {
		List<RecommendationDetails> recommendationList = new ArrayList<>();
		HashMap<String, Integer> thresholdMap = getThreshold(request);
		LOGGER.debug("getRecommendationListForStudy called");
		try {
			jdbcTemplate.query(SQLConstants.AF_FLAGGED_RECOMMENDATIONS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					RecommendationDetails recommendationDetails = new RecommendationDetails();
					recommendationDetails.setPetId(rs.getInt("PET_ID"));
					recommendationDetails.setPetName(rs.getString("PET_NAME"));
					recommendationDetails.setDietId(rs.getInt("DIET_ID"));
					recommendationDetails.setDietNumber(rs.getString("DIET_NUMBER"));
					recommendationDetails.setDietName(rs.getString("DIET_NAME"));
					recommendationDetails.setThreshold(rs.getString("THRESHOLDS"));
					recommendationDetails.setDeviations(rs.getString("DEVIATIONS"));
					recommendationDetails.setScheduleDate(rs.getString("SCHEDULED_DATE"));
					recommendationDetails.setRecommendationDietAmtCups(rs.getFloat("RECOMMENDED_DIET_AMT_CUPS"));
					recommendationDetails.setRecommendationDietAmtGrams(rs.getFloat("RECOMMENDED_DIET_AMT_GMS"));
					recommendationDetails.setManualQualityCups(rs.getFloat("MANUAL_QTY_CUPS"));
					recommendationDetails.setManualQualityGrams(rs.getFloat("MANUAL_QTY_GRAMS"));
					recommendationDetails.setIsFlagged(rs.getShort("IS_FLAGGED"));
					recommendationDetails.setRecommendationStatusId(
							rs.getShort("RECOMMENDATION_STATUS_ID") > 0 ? rs.getShort("RECOMMENDATION_STATUS_ID") : 1);
					recommendationDetails.setComments(
							rs.getString("COMMENTS")!=null ?
									rs.getString("COMMENTS")+(rs.getString("ERROR_MESSAGE")!=null?","+rs.getString("ERROR_MESSAGE"):"") :
									rs.getString("ERROR_MESSAGE")
					);
					recommendationDetails.setFeedUnits(rs.getString("FEED_UNITS"));
					recommendationDetails.setAfId(rs.getInt("ACTIVITY_AF_ID"));
					recommendationDetails.setStudyId(rs.getInt("STUDY_ID"));
					recommendationDetails.setIsLatestRecord(rs.getShort("IS_EDIT"));
					recommendationDetails.setAfRecommendationException(rs.getShort("AF_RECOMMENDATION_EXCEPTION"));
					//recommendationDetails.setPreviousValuesInCups(rs.getFloat("PREV_VALUES_CUPS"));
					//recommendationDetails.setPreviousValuesInGrams(rs.getFloat("PREV_VALUES_GRAMS"));
					recommendationList.add(recommendationDetails);
				}
			}, request.getStartIndex(), request.getLimit(), request.getStudyId(),
					request.getPetIds().equals("null") ? null : request.getPetIds(), thresholdMap.get("thresholdId1"),
					thresholdMap.get("thresholdId2"), thresholdMap.get("thresholdId3"),
					thresholdMap.get("thresholdId4"),
					request.getActionIds().equals("null") ? null : request.getActionIds(), request.getStartDate(),
					request.getEndDate());
		} catch (Exception e) {
			LOGGER.error("error while fetching getRecommendationListForStudy", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return recommendationList;
	}

	public HashMap<String, Integer> getThreshold(FlaggedRecommendationRequest request) {
		HashMap<String, Integer> map = new HashMap<>(4);
		if (request.getThresholdIds().contains("1")) {
			map.put("thresholdId1", 1);
		} else {
			map.put("thresholdId1", null);
		}
		if (request.getThresholdIds().contains("2")) {
			map.put("thresholdId2", 1);
		} else {
			map.put("thresholdId2", null);
		}
		if (request.getThresholdIds().contains("3")) {
			map.put("thresholdId3", 1);
		} else {
			map.put("thresholdId3", null);
		}
		if (request.getThresholdIds().contains("4")) {
			map.put("thresholdId4", 1);
		} else {
			map.put("thresholdId4", null);
		}
		return map;
	}

	@Override
	public HashMap<String, Integer> getFoodIntakeListCount(IntakeFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getFoodIntakeListCount called");
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		try {
			counts = selectForObject(SQLConstants.GET_FOOD_INTAKE_LIST_COUNT, String.class, filter.getStudyId(),
					filter.getPetId(), filter.getPhaseId(), filter.getStartDate(), filter.getEndDate());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getFoodIntakeListCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	/*
	 * To get food intake for give filter
	 */
	@Override
	public List<FoodIntakeDetails> getFoodIntake(IntakeFilter filter) throws ServiceExecutionException {
		List<FoodIntakeDetails> foodIntakeDetailsList = new ArrayList<>();
		LOGGER.debug("getFoodIntake called");
		try {
			jdbcTemplate.query(SQLConstants.GET_FOOD_INTAKE_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					FoodIntakeDetails foodIntakeDetails = new FoodIntakeDetails();
					// INTAKE_DATE, FOOD_INTAKE_ID, DIET_NAME, DIET_NUMBER, OFFERED_AMOUNT,
					// CONSUMED_AMOUNT,
					// RECOMMENDED_AMOUNT, OTHER_FOOD_CONSUMED_PERCENT, UNIT, OTHER_FOOD,
					// FEEDBACK_TEXT
					foodIntakeDetails.setIntakeDate(resultSet.getString("INTAKE_DATE")); //
					foodIntakeDetails.setIntakeId(resultSet.getString("FOOD_INTAKE_ID")); //
					foodIntakeDetails.setDietNumber(resultSet.getString("DIET_NAME")); //
					foodIntakeDetails.setDietName(resultSet.getString("DIET_NUMBER")); //
					foodIntakeDetails.setStudyRecommendedAmount(resultSet.getString("RECOMMENDED_AMOUNT")); //
					foodIntakeDetails.setStudyOfferedAmount(resultSet.getString("OFFERED_AMOUNT")); //
					foodIntakeDetails.setStudyConsumedAmount(resultSet.getString("CONSUMED_AMOUNT")); //
					foodIntakeDetails.setStudyUnit(resultSet.getString("UNIT")); //
					foodIntakeDetails.setOtherFood(resultSet.getString("OTHER_FOOD"));
					// foodIntakeDetails.setOtherFoodCategory(resultSet.getString("OTHER_FOOD_CATEGORY"));
					// foodIntakeDetails.setOtherPleaseSpecify(resultSet.getString("OTHER_PLEASE_SPECIFY"));
					foodIntakeDetails.setOtherConsumedAmount(resultSet.getString("OTHER_FOOD_CONSUMED_PERCENT")); //
					// foodIntakeDetails.setOtherCaloricDensity(resultSet.getString("OTHER_CAL_DENSITY"));
					foodIntakeDetails.setPetName(resultSet.getString("PET_NAME")); //
					foodIntakeDetails.setFeedback(resultSet.getString("FEEDBACK_TEXT")); //
					// foodIntakeDetails.setDescription(resultSet.getString("DESCRIPTION"));
					foodIntakeDetailsList.add(foodIntakeDetails);
				}
			}, filter.getStudyId(), filter.getPetId(), filter.getPhaseId(), filter.getStartDate(), filter.getEndDate(),
					filter.getStartIndex(), filter.getLimit());

		} catch (Exception e) {
			LOGGER.error("error while fetching getFoodIntake", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return foodIntakeDetailsList;
	}

	@Override
	public void updateStatus(FlaggedRecommendationStatusRequest request) throws ServiceExecutionException {
		LOGGER.info("updateStatus start, request {} ", request.getAfId());
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_pet_id", request.getPetId());
			inputParams.put("p_study_id", request.getStudyId());
			inputParams.put("p_user_id", request.getUserId());
			inputParams.put("p_activity_factor_id", request.getAfId());
			inputParams.put("p_manual_value", request.getManualRecommendation());
			inputParams.put("p_set_manual_recommendation", request.getSetRecommendQuantity());
			inputParams.put("p_unit", request.getFeedUnits());
			inputParams.put("p_status_id", request.getStatus());
			inputParams.put("p_comments", request.getComments());
			LOGGER.info("updateStatus inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.AF_FLAGGED_RECOMMENDATIONS_UPDATE_STATUS, inputParams);
			// System.out.println(outParams);
			LOGGER.info("updateStatus outParams are {}", outParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("Recommendation status has been updated successfully, AF id is ", request.getAfId());
			} else {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing updateStatus ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public int createCrossOverStudy(AddCrossOverStudyRequest addCrossOverStudyRequest)
			throws ServiceExecutionException {
		Integer studyId = 0;
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_study_id", addCrossOverStudyRequest.getStudyId());
			inputParams.put("p_study_name", addCrossOverStudyRequest.getCrossOverStudyName());
			inputParams.put("p_days", addCrossOverStudyRequest.getDays());
			inputParams.put("p_phase_from", addCrossOverStudyRequest.getCrossOverStudyPhaseFrom());
			inputParams.put("p_created_by", addCrossOverStudyRequest.getUserId());

			LOGGER.info("createCrossOverStudy inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_INSERT_CROSS_OVER, inputParams);
			LOGGER.info("createCrossOverStudy outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				studyId = (int) outParams.get("last_insert_id");
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"createCrossOverStudy service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.STUDY_NAME_ALREADY_EXISTS,
									addCrossOverStudyRequest.getCrossOverStudyName())));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing v ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return studyId;
	}

	private Mono<String> invokeStudyScheduler(Integer studyId, Integer userId) {
		try {
			return Mono.fromCallable(() -> {
				Map<String, Object> inputParams = new HashMap<>();
				inputParams.put("p_study_id", studyId);
				inputParams.put("p_user_id", userId);

				LOGGER.info("invokeStudyScheduler create inputParams are {}", inputParams);
				Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_CREATE_SCHEDULE_PUBLISH_STUDY,
						inputParams);
				LOGGER.info("invokeStudyScheduler create outParams are {}", outParams);

				return "invokeStudyScheduler completed for " + studyId;
			});
		} catch (Exception e) {
			LOGGER.error("error while executing invokeStudyScheduler", e);
			return Mono.error(e); // Convert the exception to a Mono error
		}
	}

	private Mono<String> invokePushNotificationScheduler(Integer studyId, String newStudyConfigIds,
			String deleteStudyConfigIds, Integer userId) {
		try {
			return Mono.fromCallable(() -> {
				if (StringUtils.isNotEmpty(newStudyConfigIds)) {
					Map<String, Object> inputParams = new HashMap<>();
					inputParams.put("p_study_id", studyId);
					inputParams.put("p_pet_id", null);
					inputParams.put("p_study_phase_id", null);
					inputParams.put("p_from_date", null);
					inputParams.put("p_user_id", userId);

					LOGGER.info("invokePushNotificationScheduler create inputParams are {}", inputParams);
					Map<String, Object> outParams = callStoredProcedure(
							SQLConstants.STUDY_CREATE_PUSH_NOTIFICATION_SCHEDULE, inputParams);
					LOGGER.info("invokePushNotificationScheduler create outParams are {}", outParams);
				}

				if (StringUtils.isNotEmpty(deleteStudyConfigIds)) {
					Map<String, Object> inputParams = new HashMap<>();
					inputParams.put("p_study_pn_config_id", deleteStudyConfigIds);
					inputParams.put("p_remove_date", null);
					inputParams.put("p_user_id", userId);

					LOGGER.info("invokePushNotificationScheduler delete inputParams are {}", inputParams);
					try {
						Map<String, Object> outParams = callStoredProcedure(
								SQLConstants.STUDY_REMOVE_PUSH_NOTIFICATION_SCHEDULE, inputParams);
						LOGGER.info("invokePushNotificationScheduler delete outParams are {}", outParams);
					} catch (SQLException e) {
						LOGGER.error("error while deleting schedule", e);
						throw new RuntimeException("Error while deleting schedule", e);
					}
				}
				return "invokePushNotificationScheduler completed for " + studyId;
			});
		} catch (Exception e) {
			LOGGER.error("error while executing invokePushNotificationScheduler", e);
			return Mono.error(e); // Convert the exception to a Mono error
		}
	}

	private Mono<String> invokeCompleteStudyScheduler(Integer studyId, Integer userId) {
		try {
			return Mono.fromCallable(() -> {
				Map<String, Object> inputParams = new HashMap<>();
				inputParams.put("p_study_id", studyId);
				inputParams.put("p_removed_date", LocalDate.now());
				inputParams.put("p_user_id", userId);

				LOGGER.info("invokeCompleteStudyScheduler create inputParams are {}", inputParams);
				Map<String, Object> outParams = callStoredProcedure(SQLConstants.STUDY_COMPLETE_REMOVE_SCHEDULE,
						inputParams);
				LOGGER.info("invokeCompleteStudyScheduler create outParams are {}", outParams);

				return "invokeCompleteStudyScheduler completed for " + studyId;
			});
		} catch (Exception e) {
			LOGGER.error("error while executing invokeCompleteStudyScheduler", e);
			return Mono.error(e); // Convert the exception to a Mono error
		}
	}

	@Override
	public List<PetBreed> getPetBreeds(int studyId, int phaseId) throws ServiceExecutionException {
		List<PetBreed> breeds = new ArrayList<>();
		LOGGER.debug("getPetBreeds called");
		try {
			jdbcTemplate.query(SQLConstants.STUDY_PHASE_GET_PET_BREEDS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					PetBreed breed = new PetBreed();
					// set the column values to fields like below
					breed.setBreedId(resultSet.getInt("BREED_ID"));
					breed.setBreedName(resultSet.getString("BREED_NAME"));
					breed.setSpeciesId(resultSet.getInt("SPECIES_ID"));
					breeds.add(breed);
				}
			}, studyId, phaseId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetBreeds ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return breeds;
	}
}