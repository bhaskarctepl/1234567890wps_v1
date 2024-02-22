package com.hillspet.wearables.dao.imagescoring.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.Response.Status;

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
import com.hillspet.wearables.common.constants.WearablesErrorCode;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.common.utils.GCPClientUtil;
import com.hillspet.wearables.dao.BaseDaoImpl;
import com.hillspet.wearables.dao.imagescoring.ImageScoringScaleDao;
import com.hillspet.wearables.dto.ImageScoringScale;
import com.hillspet.wearables.dto.ImageScoringScaleDetails;
import com.hillspet.wearables.dto.ImageScoringScaleRequest;
import com.hillspet.wearables.dto.filter.ImageScaleFilter;

@Repository
public class ImageScoringScaleDaoImpl extends BaseDaoImpl implements ImageScoringScaleDao {

	private static final Logger LOGGER = LogManager.getLogger(ImageScoringScaleDaoImpl.class);

	@Autowired
	private GCPClientUtil gcpClientUtil;

	@Autowired
	private ObjectMapper mapper;

	@Override
	public void addImageScoringScale(ImageScoringScaleRequest imageScoringScaleRequest)
			throws ServiceExecutionException {
		LOGGER.debug("addImageScoringScale called");
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_image_scale_name", imageScoringScaleRequest.getImageScaleName());
		inputParams.put("p_classification_id", imageScoringScaleRequest.getClassificationId());
		inputParams.put("p_scoring_type_id", imageScoringScaleRequest.getScoringTypeId());
		inputParams.put("p_species_id", imageScoringScaleRequest.getSpeciesId());
		inputParams.put("p_status_id", imageScoringScaleRequest.getStatusId());
		inputParams.put("p_created_by", imageScoringScaleRequest.getCreatedBy());

		try {
			inputParams.put("p_image_scale_json",
					mapper.writeValueAsString(imageScoringScaleRequest.getImageScoringScaleDetails()));

			Map<String, Object> outParams = callStoredProcedure(SQLConstants.IMAGE_SCORING_SCALE_INSERT, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");

			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				// getting the inserted flag value
				Integer imageScoringScaleId = (int) outParams.get("last_insert_id");
				LOGGER.info("Image Scoring Scale has been created successfully, image Scoring ScaleId is {}",
						imageScoringScaleId);
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"addImageScoringScale service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.IMAGE_SCORING_SCALE_ALREADY_EXISTS,
									imageScoringScaleRequest.getImageScaleName())));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
			LOGGER.debug("addImageScoringScale successfully completed");
		} catch (SQLException | JsonProcessingException e) {
			LOGGER.error("error while executing addFirmwareVersion ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public void updateImageScoringScale(ImageScoringScaleRequest imageScoringScaleRequest)
			throws ServiceExecutionException {
		LOGGER.debug("updateImageScoringScale called");
		Map<String, Object> inputParams = new HashMap<>();

		inputParams.put("p_image_scale_id", imageScoringScaleRequest.getImageScoringScaleId());
		inputParams.put("p_image_scale_name", imageScoringScaleRequest.getImageScaleName());
		inputParams.put("p_classification_id", imageScoringScaleRequest.getClassificationId());
		inputParams.put("p_scoring_type_id", imageScoringScaleRequest.getScoringTypeId());
		inputParams.put("p_species_id", imageScoringScaleRequest.getSpeciesId());
		inputParams.put("p_status_id", imageScoringScaleRequest.getStatusId());
		inputParams.put("p_modified_by", imageScoringScaleRequest.getCreatedBy());
		try {
			inputParams.put("p_image_scale_json",
					mapper.writeValueAsString(imageScoringScaleRequest.getImageScoringScaleDetails()));
			LOGGER.info("inputParams::" + inputParams);

			Map<String, Object> outParams = callStoredProcedure(SQLConstants.IMAGE_SCORING_SCALE_UPDATE, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isNotEmpty(errorMsg) || statusFlag < NumberUtils.INTEGER_ONE) {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"updateImageScoringScale service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.IMAGE_SCORING_SCALE_ALREADY_EXISTS,
									imageScoringScaleRequest.getImageScaleName())));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
			LOGGER.debug("updateImageScoringScale successfully completed");
		} catch (SQLException | JsonProcessingException e) {
			LOGGER.error("error while executing updateImageScoringScale ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public Map<String, Integer> getImageScoringScaleCount(ImageScaleFilter filter) throws ServiceExecutionException {
		HashMap<String, Integer> map = new HashMap<>();
		LOGGER.debug("getImageScoringScaleCount called");
		try {
			String counts = selectForObject(SQLConstants.IMAGE_SCORING_SCALE_LIST_COUNT, String.class,
					filter.getSearchText(), filter.getClassificationId(), filter.getScoringTypeId(),
					filter.getStatusId());
			map = mapper.readValue(counts, HashMap.class);
			LOGGER.debug("getImageScoringScaleCount successfully completed");
		} catch (Exception e) {
			LOGGER.error("error while fetching getImageScoringScaleCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<ImageScoringScale> getImageScoringScales(ImageScaleFilter filter) throws ServiceExecutionException {
		List<ImageScoringScale> imageScoringScales = new ArrayList<>();
		LOGGER.debug("getImageScoringScales called");
		try {
			jdbcTemplate.query(SQLConstants.IMAGE_SCORING_SCALE_GET_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					ImageScoringScale imageScoringScale = new ImageScoringScale();
					imageScoringScale.setImageScoringScaleId(rs.getInt("IMAGE_SCORING_ID"));
					imageScoringScale.setImageScaleName(rs.getString("IMAGE_SCALE_NAME"));

					imageScoringScale.setClassificationId(rs.getInt("CLASSIFICATION_ID"));
					imageScoringScale.setClassification(rs.getString("CLASSIFICATION"));

					imageScoringScale.setScoringTypeId(rs.getInt("SCORING_TYPE_ID"));
					imageScoringScale.setScoringType(rs.getString("SCORING_TYPE"));

					imageScoringScale.setSpeciesId(rs.getInt("SPECIES_ID"));
					imageScoringScale.setSpeciesName(rs.getString("SPECIES_NAME"));

					imageScoringScale.setStatusId(rs.getInt("STATUS"));
					imageScoringScale.setIsPublished(rs.getBoolean("IS_PUBLISHED"));

					imageScoringScale.setNoOfScales(rs.getInt("NO_OF_SCALES"));

					imageScoringScale.setCreatedDate(rs.getTimestamp("CREATED_DATE").toLocalDateTime());
					imageScoringScale.setModifiedDate(rs.getTimestamp("MODIFIED_DATE").toLocalDateTime());
					imageScoringScales.add(imageScoringScale);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getClassificationId(), filter.getScoringTypeId(), filter.getStatusId());
			LOGGER.debug("getImageScoringScales successfully completed");
		} catch (Exception e) {
			LOGGER.error("error while fetching getImageScoringScales", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return imageScoringScales;
	}

	@Override
	public ImageScoringScale getImageScoringScaleById(int imageScoringScaleId) throws ServiceExecutionException {
		final ImageScoringScale imageScoringScale = new ImageScoringScale();
		List<ImageScoringScaleDetails> scoringScaleDetails = new ArrayList<>();
		Map<Integer, List<ImageScoringScaleDetails>> scaleDetailsMap = new HashMap<>();
		LOGGER.debug("getImageScoringScaleById called");
		try {
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("p_image_scoring_scale_id", imageScoringScaleId);

			Map<String, Object> simpleJdbcCallResult = callStoredProcedure(SQLConstants.IMAGE_SCORING_SCALE_GET_BY_ID,
					inputParams);
			Iterator<Entry<String, Object>> itr = simpleJdbcCallResult.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();

				if (key.equals(SQLConstants.RESULT_SET_1)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(imageScoreObj -> {
						imageScoringScale.setImageScoringScaleId((Integer) imageScoreObj.get("IMAGE_SCORING_ID"));
						imageScoringScale.setImageScaleName((String) imageScoreObj.get("IMAGE_SCALE_NAME"));

						imageScoringScale.setClassificationId((Integer) imageScoreObj.get("CLASSIFICATION_ID"));
						imageScoringScale.setClassification((String) imageScoreObj.get("CLASSIFICATION"));
						imageScoringScale.setScoringTypeId((Integer) imageScoreObj.get("SCORING_TYPE_ID"));
						imageScoringScale.setScoringType((String) imageScoreObj.get("SCORING_TYPE"));
						imageScoringScale.setSpeciesId((Integer) imageScoreObj.get("SPECIES_ID"));
						imageScoringScale.setSpeciesName((String) imageScoreObj.get("SPECIES_NAME"));

						imageScoringScale.setStatusId((Integer) imageScoreObj.get("STATUS"));
						imageScoringScale.setIsPublished(
								((Integer) imageScoreObj.get("IS_PUBLISHED") == NumberUtils.INTEGER_ONE) ? Boolean.TRUE
										: Boolean.FALSE);
						imageScoringScale.setCreatedDate((LocalDateTime) imageScoreObj.get("CREATED_DATE"));
						imageScoringScale.setModifiedDate((LocalDateTime) imageScoreObj.get("MODIFIED_DATE"));
					});
				}

				if (key.equals(SQLConstants.RESULT_SET_2)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();

					list.forEach(detailsObj -> {
						ImageScoringScaleDetails scaleDetails = new ImageScoringScaleDetails();

						scaleDetails.setImageScoringDetailsId((Integer) detailsObj.get("IMAGE_SCORING_DTLS_ID"));
						scaleDetails.setImageLabel((String) detailsObj.get("IMAGE_LABEL"));
						scaleDetails.setDescription((String) detailsObj.get("DESCRIPTION"));
						scaleDetails.setScore((Integer) detailsObj.get("SCORE"));
						scaleDetails.setUom((Integer) detailsObj.get("UOM"));
						scaleDetails.setUnitName((String) detailsObj.get("UNIT_NAME"));
						String imageName = (String) detailsObj.get("IMAGE_PATH");
						if (StringUtils.isNotEmpty(imageName)) {
							scaleDetails.setImageName(imageName);
							scaleDetails.setImagePath(
									gcpClientUtil.getDownloaFiledUrl(imageName, Constants.GCP_IMAGE_SCORING_PATH));
						}
						scoringScaleDetails.add(scaleDetails);
					});
					imageScoringScale.setNoOfScales(scoringScaleDetails.size());
					imageScoringScale.setImageScoringScaleDetails(scoringScaleDetails);
				}
			}

			LOGGER.debug("getImageScoringScaleById successfully completed");
		} catch (Exception e) {
			LOGGER.error("error while fetching getImageScoringScaleById", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return imageScoringScale;
	}

	@Override
	public void deleteImageScoringScale(int imageScoringScaleId, int modifiedBy) throws ServiceExecutionException {
		LOGGER.debug("deleteImageScoringScale called");
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_image_scale_id", imageScoringScaleId);
		inputParams.put("p_modified_by", modifiedBy);
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.IMAGE_SCORING_SCALE_DELETE, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isNotEmpty(errorMsg) || statusFlag < NumberUtils.INTEGER_ONE) {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"deleteImageScoringScale validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.ALREADY_BEING_REFERENCED, errorMsg)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
			LOGGER.debug("deleteImageScoringScale successfully completed");
		} catch (SQLException e) {
			LOGGER.error("error while executing deleteImageScoringScale ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public List<ImageScoringScale> getImageScoringScaleList() throws ServiceExecutionException {
		List<ImageScoringScale> imageScoringScales = new ArrayList<>();
		LOGGER.debug("getImageScoringScaleList called");
		try {
			jdbcTemplate.query(SQLConstants.IMAGE_SCORING_SCALE_GET_ACTIVE_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					ImageScoringScale imageScoringScale = new ImageScoringScale();
					imageScoringScale.setImageScoringScaleId(rs.getInt("IMAGE_SCORING_ID"));
					imageScoringScale.setImageScaleName(rs.getString("IMAGE_SCALE_NAME"));
					imageScoringScale.setScoringTypeId(rs.getInt("SCORING_TYPE_ID"));
					imageScoringScale.setScoringType(rs.getString("SCORING_TYPE"));

					imageScoringScales.add(imageScoringScale);
				}
			});
			LOGGER.debug("getImageScoringScales successfully completed");
		} catch (Exception e) {
			LOGGER.error("error while fetching getImageScoringScaleList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return imageScoringScales;
	}

}
