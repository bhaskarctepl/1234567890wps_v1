package com.hillspet.wearables.dao.pointTracker.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillspet.wearables.common.constants.Constants;
import com.hillspet.wearables.common.constants.SQLConstants;
import com.hillspet.wearables.common.constants.StringLiterals;
import com.hillspet.wearables.common.constants.WearablesErrorCode;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.common.utils.GCStorageUtil;
import com.hillspet.wearables.dao.BaseDaoImpl;
import com.hillspet.wearables.dao.pointTracker.PointTrackerDao;
import com.hillspet.wearables.dto.PointTracker;
import com.hillspet.wearables.dto.PointTrackerAssociatedDTO;
import com.hillspet.wearables.dto.PointTrackerCampaignActivities;
import com.hillspet.wearables.dto.PointTrackerMetricAssociatedDTO;
import com.hillspet.wearables.dto.TrackerActivityStatusNotes;
import com.hillspet.wearables.dto.filter.PointTrackerFilter;
import com.hillspet.wearables.request.PointTrackerActivityRequest;
import com.hillspet.wearables.request.PointTrackerMetricRequest;

@Repository
public class PointTrackerDaoImpl extends BaseDaoImpl implements PointTrackerDao {

	private static final Logger LOGGER = LogManager.getLogger(PointTrackerDaoImpl.class);

	@Autowired
	private GCStorageUtil gcStorageUtil;

	@Autowired
	private ObjectMapper mapper;

	@Override
	public PointTracker addPointTracker(PointTracker pointTracker) throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_tracker_name", pointTracker.getTrackerName());
			inputParams.put("p_study_id", pointTracker.getStudyId());
			inputParams.put("p_start_date", pointTracker.getStartDate());
			inputParams.put("p_end_date", pointTracker.getEndDate());
			inputParams.put("p_status", pointTracker.getStatus());

			inputParams.put("p_created_by", pointTracker.getUserId());
			inputParams.put("p_point_tracker_activity_json",
					mapper.writeValueAsString(pointTracker.getPointTrackerSubscribed()));
			inputParams.put("p_point_tracker_metric_json", null);

			for (int i = 0; i <= pointTracker.getPointTrackerSubscribed().size() - 1; i++) {
				if (pointTracker.getPointTrackerSubscribed().get(i).getActivitiesId() == 4) {
					List<PointTrackerMetricRequest> metricRequests = pointTracker.getPointTrackerSubscribed().get(i)
							.getPointTrackerMetricRequest();

					if (metricRequests != null && metricRequests.size() > 0) {
						inputParams.put("p_point_tracker_metric_json",
								metricRequests != null ? mapper.writeValueAsString(metricRequests) : "");
					} else {
						inputParams.put("p_point_tracker_metric_json", null);
					}
				}
			}

			Map<String, Object> outParams = callStoredProcedure(SQLConstants.POINT_TRACKER_INSERT, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				// getting the inserted flag value
				Integer pointTrackerId = (int) outParams.get("last_insert_id");
				pointTracker.setPointTrackerId(pointTrackerId);
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"addPointTracker service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.TRACKER_NAME_ALREADY_EXISTS,
									pointTracker.getTrackerName())));
				} else if (statusFlag == -3) {
					throw new ServiceExecutionException(
							"addPointTracker service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.STUDY_CAMPAIGN_EXISTS,
									pointTracker.getTrackerName())));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}

		} catch (SQLException | JsonProcessingException e) {
			throw new ServiceExecutionException(e.getMessage());
		}
		return pointTracker;
	}

	@Override
	public String getPointTrackingListCount(PointTrackerFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getPointTrackingListCount called");
		String totalCount;
		try {
			totalCount = selectForObject(SQLConstants.POINT_TRACKER_GET_LIST_COUNT, String.class,
					filter.getSearchText(), filter.getStatusId(), filter.getStudyId(), filter.getStartDate(),
					filter.getEndDate(), filter.getUserId(), filter.getRoleTypeId());
		} catch (Exception e) {
			LOGGER.error("error while fetching getPointTrackingListCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return totalCount;
	}

	@Override
	public List<PointTracker> getPointTrackingList(PointTrackerFilter filter) throws ServiceExecutionException {
		List<PointTracker> pointTrackerList = new ArrayList<>();
		LOGGER.debug("getPointTrackingList called");
		try {
			jdbcTemplate.query(SQLConstants.POINT_TRACKER_GET_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PointTracker pointTracker = new PointTracker();
					pointTracker.setSlNumber(rs.getInt("slNo"));
					pointTracker.setPointTrackerId(rs.getInt("POINT_TRACKER_ID"));
					pointTracker.setTrackerName(rs.getString("TRACKER_NAME"));

					Date startDate = (Date) rs.getDate("START_DATE");
					pointTracker.setStartDate((startDate.toLocalDate()));

					Date endDate = (Date) rs.getDate("END_DATE");
					pointTracker.setEndDate((endDate.toLocalDate()));
					pointTracker.setStudyName(rs.getString("STUDY_NAME"));
					pointTracker.setStudyId(rs.getInt("STUDY_ID"));
					pointTracker.setCreatedDate(rs.getTimestamp("CREATED_DATE").toLocalDateTime());
					pointTracker.setStatus((rs.getInt("STATUS")));

					pointTracker.setIsPublished(rs.getBoolean("IS_PUBLISHED"));
					/* pointTracker.setIsActive(rs.getBoolean("STATUS")); */

					pointTrackerList.add(pointTracker);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getSortBy(), filter.getOrder(), filter.getSearchText(),
					filter.getStatusId(), filter.getStudyId(), filter.getStartDate(), filter.getEndDate(),
					filter.getUserId(), filter.getRoleTypeId());

		} catch (Exception e) {
			LOGGER.error("error while fetching getPointTrackingList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return pointTrackerList;
	}

	@Override
	public PointTracker updatePointTracker(PointTracker pointTracker) {
		String activityIds = StringUtils.EMPTY;
		if (CollectionUtils.isNotEmpty(pointTracker.getPointTrackerSubscribed())) {
			List<Integer> activities = pointTracker.getPointTrackerSubscribed().stream()
					.map(PointTrackerActivityRequest::getActivitiesId).collect(Collectors.toList());
			activityIds = StringUtils.join(activities, StringLiterals.COMMA.getCode());
		}

		String metricIds = StringUtils.EMPTY;
		if (CollectionUtils.isNotEmpty(pointTracker.getPointTrackerSubscribed())) {
			Optional<PointTrackerActivityRequest> optional = pointTracker.getPointTrackerSubscribed().stream()
					.filter(obj -> obj.getActivitiesId() == 4).findFirst();

			if (optional.isPresent() && CollectionUtils.isNotEmpty(optional.get().getPointTrackerMetricRequest())) {
				List<Integer> metrics = optional.get().getPointTrackerMetricRequest().stream()
						.map(PointTrackerMetricRequest::getMetricId).collect(Collectors.toList());
				metricIds = StringUtils.join(metrics, StringLiterals.COMMA.getCode());
			}
		}

		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_point_tracker_id", pointTracker.getPointTrackerId());
			inputParams.put("p_tracker_name", pointTracker.getTrackerName());
			inputParams.put("p_study_id", pointTracker.getStudyId());
			inputParams.put("p_start_date", pointTracker.getStartDate());
			inputParams.put("p_end_date", pointTracker.getEndDate());
			inputParams.put("p_status", pointTracker.getStatus());

			inputParams.put("p_point_tracker_activity_json",
					mapper.writeValueAsString(pointTracker.getPointTrackerSubscribed()));
			inputParams.put("p_point_tracker_metric_json", null);
			for (int i = 0; i <= pointTracker.getPointTrackerSubscribed().size() - 1; i++) {
				if (pointTracker.getPointTrackerSubscribed().get(i).getActivitiesId() == 4) {
					List<PointTrackerMetricRequest> metricRequests = pointTracker.getPointTrackerSubscribed().get(i)
							.getPointTrackerMetricRequest();

					if (metricRequests != null && metricRequests.size() > 0) {
						inputParams.put("p_point_tracker_metric_json",
								metricRequests != null ? mapper.writeValueAsString(metricRequests) : "");
					} else {
						inputParams.put("p_point_tracker_metric_json", null);
					}
				}
			}

			inputParams.put("activityIds", activityIds);
			inputParams.put("metricIds", metricIds);
			inputParams.put("p_modified_by", pointTracker.getUserId());

			Map<String, Object> outParams = callStoredProcedure(SQLConstants.POINT_TRACKER_UPDATE, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"updatePointTracker service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.TRACKER_NAME_ALREADY_EXISTS,
									pointTracker.getTrackerName())));
				} else if (statusFlag == -3) {
					throw new ServiceExecutionException(
							"addPointTracker service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.STUDY_CAMPAIGN_EXISTS,
									pointTracker.getTrackerName())));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}

		} catch (SQLException | JsonProcessingException e) {
			throw new ServiceExecutionException(e.getMessage());
		}
		return pointTracker;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PointTracker getPointTrackerById(int pointTrackerId) throws ServiceExecutionException {
		PointTracker pointTracker = new PointTracker();
		try {
			LOGGER.debug("getPointTrackerById called");
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("p_point_tracker_id", pointTrackerId);

			Map<String, Object> simpleJdbcCallResult = callStoredProcedure(SQLConstants.POINT_TRACKER_GET_BY_ID,
					inputParams);
			Iterator<Entry<String, Object>> itr = simpleJdbcCallResult.entrySet().iterator();

			List<PointTrackerAssociatedDTO> PointTrackerAssociatedList = new LinkedList<>();
			List<PointTrackerMetricAssociatedDTO> PointTrackerMetricAssociatedList = new LinkedList<>();

			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();

				if (key.equals(SQLConstants.RESULT_SET_1)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(pointTrackerObj -> {

						pointTracker.setTrackerName((String) pointTrackerObj.get("TRACKER_NAME"));
						pointTracker.setTotalPoints(String.valueOf(pointTrackerObj.get("TOTAL_POINTS")));

						pointTracker.setStudyName((String) pointTrackerObj.get("STUDY_NAME"));
						pointTracker.setStudyId((Integer) pointTrackerObj.get("STUDY_ID"));

						LocalDateTime startDate = (LocalDateTime) pointTrackerObj.get("START_DATE");
						pointTracker.setStartDate(startDate != null ? (startDate.toLocalDate()) : null);

						LocalDateTime endDate = (LocalDateTime) pointTrackerObj.get("END_DATE");
						pointTracker.setEndDate(endDate != null ? (endDate.toLocalDate()) : null);

						pointTracker.setStatus((Integer) pointTrackerObj.get("STATUS"));
						pointTracker
								.setIsPublished(((Integer) pointTrackerObj.get("IS_PUBLISHED") == 1) ? true : false);
					});
				}

				if (key.equals(SQLConstants.RESULT_SET_2)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();

					list.forEach(activityObj -> {
						Date startDate = (Date) activityObj.get("START_DATE");
						Date endDate = (Date) activityObj.get("END_DATE");
						if (activityObj.get("METRIC_ID") != null) {
							PointTrackerMetricAssociatedDTO pointMetricTrackerAssociated = new PointTrackerMetricAssociatedDTO();
							pointMetricTrackerAssociated.setSpeciesId((Integer) activityObj.get("SPECIES_ID"));
							pointMetricTrackerAssociated.setMetricId((Integer) activityObj.get("METRIC_ID"));
							pointMetricTrackerAssociated.setMetricName((String) activityObj.get("METRIC_NAME"));
							pointMetricTrackerAssociated.setMetricPoints(String.valueOf(activityObj.get("POINTS")));
							pointMetricTrackerAssociated.setStartDate((startDate.toLocalDate()));
							pointMetricTrackerAssociated.setEndDate((endDate.toLocalDate()));
							PointTrackerMetricAssociatedList.add(pointMetricTrackerAssociated);
						} else {
							PointTrackerAssociatedDTO pointTrackerAssociated = new PointTrackerAssociatedDTO();
							pointTrackerAssociated.setId((Integer) activityObj.get("ACTIVITY_ID"));
							pointTrackerAssociated.setActivityName((String) activityObj.get("ACTIVITY_NAME"));
							pointTrackerAssociated.setPoints(String.valueOf(activityObj.get("POINTS")));
							pointTrackerAssociated.setEligibleForPointsAccumulation(
									activityObj.get("ELIGIBLE_FOR_POINT_ACCUMULATION") == null ? null
											: String.valueOf(activityObj.get("ELIGIBLE_FOR_POINT_ACCUMULATION")));
							pointTrackerAssociated.setStartDate((startDate.toLocalDate()));
							pointTrackerAssociated.setEndDate((endDate.toLocalDate()));
							PointTrackerAssociatedList.add(pointTrackerAssociated);
						}
						pointTracker.setPointTrackerAssociatedObject(PointTrackerAssociatedList);
					});
					pointTracker.setPointTrackerMetricAssociatedObject(PointTrackerMetricAssociatedList);
					pointTracker.setPointTrackerAssociatedObject(PointTrackerAssociatedList);
				}
			}

		} catch (Exception e) {
			LOGGER.error("error while fetching getPointTrackerById", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return pointTracker;
	}

	@Override
	public String getPointTrackerActiviesListCount(PointTrackerFilter filter, int pointTrackerId)
			throws ServiceExecutionException {
		String counts;
		LOGGER.debug("getPointTrackerActiviesListCount called");
		try {
			counts = selectForObject(SQLConstants.POINT_TRACKER_ACTIVITIES_GET_LIST_COUNT, String.class, pointTrackerId,
					filter.getSearchText(), filter.getFilterType(), filter.getFilterValue(), filter.getStartDate(),
					filter.getEndDate());
		} catch (Exception e) {
			LOGGER.error("error while fetching getPointTrackerActiviesListCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return counts;
	}

	@Override
	public List<PointTrackerCampaignActivities> getPointTrackerActiviesList(PointTrackerFilter filter,
			int pointTrackerId) throws ServiceExecutionException {
//		List<PlanListDTO> planList = new ArrayList<>();
		List<PointTrackerCampaignActivities> PointTrackerCampaignActivitiesList = new ArrayList<>();
		LOGGER.debug("getPointTrackerActiviesList called");
		try {
			jdbcTemplate.query(SQLConstants.POINT_TRACKER_ACTIVITIES_GET_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PointTrackerCampaignActivities pointTrackerCampaignActivities = new PointTrackerCampaignActivities();
//					PlanListDTO planListDTO = new PlanListDTO();
					pointTrackerCampaignActivities.setTrackerPetPointsId(rs.getInt("TRACKER_PET_POINTS_ID"));
					pointTrackerCampaignActivities.setPetId(rs.getInt("PET_ID"));
					pointTrackerCampaignActivities.setPetName(rs.getString("PET_NAME"));
					pointTrackerCampaignActivities.setStudyName(rs.getString("STUDY_NAME"));
//					PET_ID, PET_NAME, STUDY_ID, STUDY_NAME, CREATED_DATE, OBSERVATION, ACTIVITY_ID, ACTIVITY, METRIC_ID,
//					BEHAVIOR, IMAGE, VIDEO_THUMBNAIL, VIDEO, POINTS, STATUS_ID, STATUS
					pointTrackerCampaignActivities.setCreatedDate(rs.getDate("CREATED_DATE"));
					pointTrackerCampaignActivities.setObseravattion(rs.getString("OBSERVATION"));
					pointTrackerCampaignActivities.setStudyId(rs.getInt("STUDY_ID"));
//					pointTrackerCampaignActivities.setCreatedDate(rs.getTimestamp("CREATED_DATE").toLocalDateTime());
					pointTrackerCampaignActivities.setStatusId((rs.getInt("STATUS_ID")));
					pointTrackerCampaignActivities.setStatus((rs.getString("STATUS")));
					pointTrackerCampaignActivities.setActivity(rs.getString("ACTIVITY"));
					pointTrackerCampaignActivities.setSpeciesId(rs.getInt("SPECIES_ID"));
					pointTrackerCampaignActivities.setBehavior(rs.getString("BEHAVIOR"));
					pointTrackerCampaignActivities.setBehaviorId(rs.getInt("BEHAVIOR_ID"));
					pointTrackerCampaignActivities.setPoints(rs.getInt("POINTS"));
					// pointTrackerCampaignActivities.setMedia(rs.getString("VIDEO_THUMBNAIL"));
					String orginalPhotoPath = "";

					if (rs.getString("IMAGE") != null && !rs.getString("IMAGE").isEmpty()) {
						/*
						 * String imageName = rs.getString("IMAGE"); String photoPath =
						 * MessageFormat.format(Constants.GCLOUD_PHOTO_PATH, environment);
						 * orginalPhotoPath = photoPath + imageName;
						 */
						orginalPhotoPath = gcStorageUtil
								.getMediaSignedUrlList(rs.getString("IMAGE"), Constants.GCP_OBSERVATION_PHOTO_PATH)
								.get(0);
					}

					pointTrackerCampaignActivities.setImageURL(orginalPhotoPath);
					String orginalVideoPath = "";
					if (rs.getString("VIDEO") != null && !rs.getString("VIDEO").isEmpty()) {
						orginalVideoPath = rs.getString("VIDEO");
						pointTrackerCampaignActivities.setVideoURL(gcStorageUtil
								.getMediaSignedUrlList(orginalVideoPath, Constants.GCP_OBSERVATION_VIDEO_PATH).get(0));
					} else {
						pointTrackerCampaignActivities.setVideoURL(orginalVideoPath);
					}

					String orginalVideoThumbnailPath = "";
					if (rs.getString("VIDEO_THUMBNAIL") != null && !rs.getString("VIDEO_THUMBNAIL").isEmpty()) {
						orginalVideoThumbnailPath = rs.getString("VIDEO_THUMBNAIL");
						pointTrackerCampaignActivities
								.setMedia(gcStorageUtil.getMediaSignedUrlList(orginalVideoThumbnailPath,
										Constants.GCP_OBSERVATION_VIDEO_THUMBNAIL_PATH).get(0));
					} else {
						pointTrackerCampaignActivities.setMedia(orginalVideoThumbnailPath);
					}

					pointTrackerCampaignActivities.setQuestionnaireRespId(rs.getInt("QUESTIONNARE_RESPONSE_ID"));
					PointTrackerCampaignActivitiesList.add(pointTrackerCampaignActivities);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), pointTrackerId,
					filter.getSearchText(), filter.getFilterType(), filter.getFilterValue(), filter.getStartDate(),
					filter.getEndDate());

		} catch (Exception e) {
			LOGGER.error("error while fetching getPointTrackerActiviesList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return PointTrackerCampaignActivitiesList;
	}

	@Override
	public void updateTrackerActStatus(TrackerActivityStatusNotes trackerActivityStatusNotes)
			throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_tracker_pet_points_id", trackerActivityStatusNotes.getTrackerPetPointsId());
		inputParams.put("p_behavior_id", trackerActivityStatusNotes.getBehaviorId());
		inputParams.put("p_status_id", trackerActivityStatusNotes.getStatusId());
		inputParams.put("p_notes", trackerActivityStatusNotes.getRejectNotes());
		inputParams.put("p_created_by", trackerActivityStatusNotes.getCreatedBy());
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.TRACKER_STATUS_UPDATE, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("Tarcker Status has been updated successfully to Tracker Activity, Id is ",
						trackerActivityStatusNotes.getTrackerPetPointsId());
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"updateTrackerActStatus service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.PET_NOTE_ALREADY_EXISTS)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing updateTrackerActStatus ", e);
			throw new ServiceExecutionException(e.getMessage());
		}

	}
}
