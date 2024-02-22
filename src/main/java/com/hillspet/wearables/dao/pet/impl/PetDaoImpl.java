package com.hillspet.wearables.dao.pet.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.TableResult;
import com.hillspet.wearables.common.constants.Constants;
import com.hillspet.wearables.common.constants.SQLConstants;
import com.hillspet.wearables.common.constants.WearablesErrorCode;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.common.utils.BigQueryServiceUtil;
import com.hillspet.wearables.common.utils.GCPClientUtil;
import com.hillspet.wearables.common.utils.GCStorageUtil;
import com.hillspet.wearables.common.utils.WearablesUtils;
import com.hillspet.wearables.dao.BaseDaoImpl;
import com.hillspet.wearables.dao.pet.PetDao;
import com.hillspet.wearables.dto.Address;
import com.hillspet.wearables.dto.BehaviorHistory;
import com.hillspet.wearables.dto.BulkUploadExternalPetIdInfo;
import com.hillspet.wearables.dto.ExternalPetIdInfo;
import com.hillspet.wearables.dto.ExternalPetInfoListDTO;
import com.hillspet.wearables.dto.ObservationVideo;
import com.hillspet.wearables.dto.PetCampaignPointsDTO;
import com.hillspet.wearables.dto.PetCampaignPointsListDTO;
import com.hillspet.wearables.dto.PetDTO;
import com.hillspet.wearables.dto.PetDevice;
import com.hillspet.wearables.dto.PetExternalInfoListDTO;
import com.hillspet.wearables.dto.PetFeedingEnthusiasmScale;
import com.hillspet.wearables.dto.PetForwardMotionGoalSetting;
import com.hillspet.wearables.dto.PetForwardMotionInfo;
import com.hillspet.wearables.dto.PetIBWHistoryDTO;
import com.hillspet.wearables.dto.PetImageScale;
import com.hillspet.wearables.dto.PetLegLength;
import com.hillspet.wearables.dto.PetListDTO;
import com.hillspet.wearables.dto.PetName;
import com.hillspet.wearables.dto.PetNote;
import com.hillspet.wearables.dto.PetObservation;
import com.hillspet.wearables.dto.PetObservationMediaListDTO;
import com.hillspet.wearables.dto.PetParentDTO;
import com.hillspet.wearables.dto.PetParentListDTO;
import com.hillspet.wearables.dto.PetRedemptionHistoryDTO;
import com.hillspet.wearables.dto.PetSleepInfo;
import com.hillspet.wearables.dto.PetStudyDTO;
import com.hillspet.wearables.dto.PetStudyDevice;
import com.hillspet.wearables.dto.PetWeightChartDTO;
import com.hillspet.wearables.dto.PetWeightHistoryDTO;
import com.hillspet.wearables.dto.StreamDevice;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.dto.filter.ExternalPetInfoFilter;
import com.hillspet.wearables.dto.filter.PetActivityFactorFilter;
import com.hillspet.wearables.dto.filter.PetEnthusiasmFilter;
import com.hillspet.wearables.dto.filter.PetFilter;
import com.hillspet.wearables.dto.filter.PetImageScaleFilter;
import com.hillspet.wearables.dto.filter.PetObservationMediaFilter;
import com.hillspet.wearables.request.AddPetWeight;
import com.hillspet.wearables.request.AssociateNewStudyRequest;
import com.hillspet.wearables.request.BehaviorHistoryRequest;
import com.hillspet.wearables.request.BulkExtPetIdsUploadRequest;
import com.hillspet.wearables.request.ManualRecommendationRequest;
import com.hillspet.wearables.request.PetRequest;
import com.hillspet.wearables.request.UpdatePetIBWRequest;
import com.hillspet.wearables.request.ValidateDuplicatePetRequest;
import com.hillspet.wearables.response.ActivityFactorResultResponse;
import com.hillspet.wearables.response.BehaviorForwardMotionGoalSettingResponse;
import com.hillspet.wearables.response.BehaviorVisualizationResponse;
import com.hillspet.wearables.response.PetAddressResponse;
import com.hillspet.wearables.response.PetLegLengthResponse;
import com.hillspet.wearables.response.PetWeightHistoryResponse;
import com.hillspet.wearables.security.Authentication;

@Repository
public class PetDaoImpl extends BaseDaoImpl implements PetDao {

	@Value("${gcp.env}")
	private String environment;

	@Value("${gcp.storage.url}")
	private String storageUrl;

	@Autowired
	private GCStorageUtil gcStorageUtil;

	@Autowired
	private GCPClientUtil gcpClientUtil;

	@Autowired
	private Authentication authentication;

	@Autowired
	private ObjectMapper mapper;

	private static String BASE_AF_URL = System.getenv("BASE_AF_URL");

	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

	private static final Logger LOGGER = LogManager.getLogger(PetDaoImpl.class);

	@Override
	public String getPetsCount(PetFilter filter) throws ServiceExecutionException {
		String totalCount = "";
		LOGGER.debug("getPetsCount called");
		try {
			totalCount = selectForObject(SQLConstants.PET_GET_LIST_COUNT, String.class, filter.getSearchText(),
					filter.getStatusId(), filter.getStudyId(), filter.getUserId(), filter.getRoleTypeId());
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetsCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return totalCount;
	}

	@Override
	public List<PetListDTO> getPetList(PetFilter filter) throws ServiceExecutionException {
		List<PetListDTO> petList = new ArrayList<>();
		LOGGER.debug("getPetList called");
		try {
			jdbcTemplate.query(SQLConstants.PET_GET_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetListDTO petListDTO = new PetListDTO();
					petListDTO.setSlNumber(rs.getInt("slNo"));
					petListDTO.setPetId(rs.getInt("PET_ID"));
					petListDTO.setPetName(rs.getString("PET_NAME"));

					petListDTO.setPetPhoto(rs.getString("PHOTO_NAME"));
					String fileName = rs.getString("PHOTO_NAME");

					if (fileName != null && !fileName.trim().equals("")) {
						petListDTO.setPetPhotoUrl(
								gcpClientUtil.getDownloaFiledUrl(fileName, Constants.GCP_PETPHOTO_PATH));
					}

					petListDTO.setBreedName(rs.getString("BREED_NAME"));
					petListDTO.setStudyId(rs.getInt("STUDY_ID"));
					petListDTO.setStudyName(rs.getString("STUDY_NAME"));
					petListDTO.setSensorDetails(rs.getString("sensorDetails"));
					petListDTO.setIsActive(rs.getBoolean("IS_ACTIVE"));
					petListDTO.setPetStatusId(rs.getInt("PET_STATUS_ID"));
					petListDTO.setPetStatus(rs.getString("STATUS_NAME"));
					petListDTO.setDeviceId(rs.getInt("DEVICE_ID"));
					petListDTO.setPetParentName(rs.getString("PET_PARENT_NAME"));
					petList.add(petListDTO);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(),
					filter.getSearchText().trim(), filter.getStatusId(), filter.getStudyId(), filter.getUserId(),
					filter.getRoleTypeId());

		} catch (Exception e) {
			LOGGER.error("error while fetching getPetList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petList;
	}

	@Override
	public List<PetListDTO> getPets() throws ServiceExecutionException {
		List<PetListDTO> petList = new ArrayList<>();
		int userId = authentication.getAuthUserDetails().getUserId();
		LOGGER.debug("getPets called " + userId);
		try {
			jdbcTemplate.query(SQLConstants.PET_GET_ALL_PETS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetListDTO petListDTO = new PetListDTO();
					petListDTO.setPetId(rs.getInt("PET_ID"));
					petListDTO.setPetName(rs.getString("PET_NAME"));
					petListDTO.setPetPhoto(rs.getString("PHOTO_NAME"));
					/*
					 * String fileName = rs.getString("PHOTO_NAME");
					 * 
					 * if (fileName != null && !fileName.trim().equals("")) {
					 * petListDTO.setPetPhotoUrl( gcpClientUtil.getDownloaFiledUrl(fileName,
					 * Constants.GCP_PETPHOTO_PATH)); }
					 */

					petListDTO.setBreedName(rs.getString("BREED_NAME"));
					petListDTO.setGender(rs.getString("GENDER"));
					petListDTO.setIsActive(rs.getBoolean("IS_ACTIVE"));
					petList.add(petListDTO);
				}
			}, userId);

		} catch (Exception e) {
			LOGGER.error("error while fetching getPets", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petList;
	}

	@Override
	public PetDTO addPet(PetRequest petRequest) throws ServiceExecutionException {
		PetDTO petDTO = new PetDTO();
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_pet_name", petRequest.getPetName());
			inputParams.put("p_breed_id", petRequest.getBreedId());
			inputParams.put("p_weight", petRequest.getWeight());
			inputParams.put("p_weight_unit", petRequest.getWeightUnit());
			inputParams.put("p_gender", petRequest.getGender());
			inputParams.put("p_dob", petRequest.getDateOfBirth());
			inputParams.put("p_dob_unknown", petRequest.getIsDobUnknown());
			inputParams.put("p_image", petRequest.getPetImage());
			inputParams.put("p_neutered", petRequest.getIsNeutered());
			inputParams.put("p_status_id", petRequest.getPetStatusId());
			inputParams.put("p_pet_devices", mapper.writeValueAsString(petRequest.getPetDevices()));
			inputParams.put("p_pet_study_devices", mapper.writeValueAsString(petRequest.getPetStudyDevices()));
			inputParams.put("p_pet_parents", mapper.writeValueAsString(petRequest.getPetParents()));

			inputParams.put("p_death_date", petRequest.getDateOfDeath());
			inputParams.put("p_is_approximate_date_of_death", petRequest.getIsApproximateDateOfDeath());

			inputParams.put("p_lost_to_followup_date", petRequest.getLostToFollowUpDate());
			inputParams.put("p_is_approx_lost_to_followup_date", petRequest.getIsApproxLostToFollowUpDate());

			inputParams.put("p_is_pet_with_pet_parent", petRequest.getIsPetWithPetParent());
			inputParams.put("p_pet_address_json", mapper.writeValueAsString(petRequest.getPetAddress()));

			inputParams.put("p_pet_leg_length", petRequest.getLegLength());
			inputParams.put("p_pet_leg_length_unit", petRequest.getLegLengthUnitId());

			inputParams.put("p_created_by", petRequest.getUserId());

			LOGGER.info("addPet inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.PET_INSERT, inputParams);
			LOGGER.info("addPet outParams are {}", outParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				// getting the inserted flag value
				Integer petId = (int) outParams.get("last_insert_id");
				petRequest.setPetId(petId);
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"addPet device assignment validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.DEVICE_ALREADY_MAPPED, errorMsg)));
				} else {
					if (statusFlag == -3) {
						throw new ServiceExecutionException(
								"AddPetParent service validation failed cannot proceed further",
								Status.BAD_REQUEST.getStatusCode(),
								Arrays.asList(new WearablesError(WearablesErrorCode.PET_PARENT_EMAIL_ALREADY_EXISTS,
										errorMsg)));
					} else if (statusFlag == -4) {
						throw new ServiceExecutionException(
								"AddPetParent service validation failed cannot proceed further",
								Status.BAD_REQUEST.getStatusCode(), Arrays.asList(new WearablesError(
										WearablesErrorCode.PET_PARENT_SECONDARY_EMAIL_ALREADY_EXISTS, errorMsg)));
					} else {
						throw new ServiceExecutionException(errorMsg);
					}
				}
			}
			BeanUtils.copyProperties(petRequest, petDTO);
		} catch (SQLException | JsonProcessingException e) {
			LOGGER.error("error while executing addPet ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petDTO;
	}

	@Override
	public PetDTO updatePet(PetRequest petRequest) throws ServiceExecutionException {
		PetDTO petDTO = new PetDTO();
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_pet_id", petRequest.getPetId());
			inputParams.put("p_pet_name", petRequest.getPetName());
			inputParams.put("p_breed_id", petRequest.getBreedId());
			/*
			 * inputParams.put("p_weight", petRequest.getWeight());
			 * inputParams.put("p_weight_unit", petRequest.getWeightUnit());
			 */
			inputParams.put("p_gender", petRequest.getGender());
			inputParams.put("p_dob", petRequest.getDateOfBirth());
			inputParams.put("p_dob_unknown", petRequest.getIsDobUnknown());
			inputParams.put("p_image", petRequest.getPetImage());
			inputParams.put("p_neutered", petRequest.getIsNeutered());
			inputParams.put("p_status_id", petRequest.getPetStatusId());
			inputParams.put("p_pet_devices", mapper.writeValueAsString(petRequest.getPetDevices()));
			inputParams.put("p_pet_study_devices", mapper.writeValueAsString(petRequest.getPetStudyDevices()));
			inputParams.put("p_pet_parents", mapper.writeValueAsString(petRequest.getPetParents()));

			inputParams.put("p_modified_by", petRequest.getUserId());
			inputParams.put("p_removed_pet_parent_ids", petRequest.getRemovedPetParents());
			inputParams.put("p_unassigned_assets", mapper.writeValueAsString(petRequest.getPetUnAssignAssets()));
			inputParams.put("p_confirm_off_study", petRequest.getConfirmOffStudy());

			inputParams.put("p_death_date", petRequest.getDateOfDeath());
			inputParams.put("p_is_approximate_date_of_death", petRequest.getIsApproximateDateOfDeath());

			inputParams.put("p_lost_to_followup_date", petRequest.getLostToFollowUpDate());
			inputParams.put("p_is_approx_lost_to_followup_date", petRequest.getIsApproxLostToFollowUpDate());

			inputParams.put("p_is_pet_with_pet_parent", petRequest.getIsPetWithPetParent());
			inputParams.put("p_pet_address_json", mapper.writeValueAsString(petRequest.getPetAddress()));

			inputParams.put("p_pet_leg_length", petRequest.getLegLength());
			inputParams.put("p_pet_leg_length_unit", petRequest.getLegLengthUnitId());

			inputParams.put("p_ext_pet_ids_json", mapper.writeValueAsString(petRequest.getExtPetIds()));

			LOGGER.info("updatePet inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.PET_UPDATE, inputParams);
			LOGGER.info("updatePet outParams are {}", outParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isNotEmpty(errorMsg) || statusFlag < NumberUtils.INTEGER_ONE) {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"updatePet device assignment validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.DEVICE_ALREADY_MAPPED, errorMsg)));
				} else if (statusFlag == -5) {
					throw new ServiceExecutionException("Error occurred.", Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.PET_PARENT_ALREADY_EXISTS, errorMsg)));
				} else {
					if (statusFlag == -3 || statusFlag == -4) {
						throw new ServiceExecutionException(
								"AddPetParent service validation failed cannot proceed further",
								Status.BAD_REQUEST.getStatusCode(),
								Arrays.asList(new WearablesError(WearablesErrorCode.PET_PARENT_EMAIL_ALREADY_EXISTS,
										errorMsg)));

					} else {
						throw new ServiceExecutionException(errorMsg);
					}
				}
			}
			BeanUtils.copyProperties(petRequest, petDTO);
		} catch (SQLException | JsonProcessingException e) {
			LOGGER.error("error while executing updatePet ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petDTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PetDTO getPetById(int petId) {
		final PetDTO petDTO = new PetDTO();
		List<PetStudyDevice> petStudyDevices = new ArrayList<>();
		List<PetDevice> petDevices = new ArrayList<>();
		List<PetParentDTO> petParents = new ArrayList<>();
		List<ExternalPetIdInfo> extPetIds = new ArrayList<>();
		int userId = authentication.getAuthUserDetails().getUserId();
		Address petAddress = new Address();

		LOGGER.debug("getPetById called " + userId);
		try {
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("p_pet_id", petId);
			inputParams.put("p_user_id", userId);

			Map<String, Object> simpleJdbcCallResult = callStoredProcedure(SQLConstants.PET_GET_BY_ID, inputParams);
			Iterator<Entry<String, Object>> itr = simpleJdbcCallResult.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();

				if (key.equals(SQLConstants.RESULT_SET_1)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();

					list.forEach(pet -> {
						petDTO.setPetId((Integer) pet.get("PET_ID"));
						petDTO.setPetName((String) pet.get("PET_NAME"));
						petDTO.setPhotoName((String) pet.get("PHOTO_NAME"));
						String fileName = (String) pet.get("PHOTO_NAME");
						if (fileName != null && !fileName.trim().equals("")) {
							petDTO.setPetPhotoUrl(
									gcpClientUtil.getDownloaFiledUrl(fileName, Constants.GCP_PETPHOTO_PATH));
						}
						if (pet.get("BREED_ID") != null) {
							petDTO.setBreedId((Integer) pet.get("BREED_ID"));
						}
						petDTO.setBreedName((String) pet.get("BREED_NAME"));
						petDTO.setSpeciesId((Integer) pet.get("SPECIES_ID"));
						petDTO.setSpeciesName((String) pet.get("SPECIES_NAME"));
						petDTO.setFeedingPreferences((String) pet.get("FEEDING_PREFERENCES"));
						petDTO.setGender((String) pet.get("GENDER"));
						if (pet.get("BIRTHDAY") != null) {
							// Timestamp birthday = (Timestamp) pet.get("BIRTHDAY");
							LocalDateTime birthday = (LocalDateTime) pet.get("BIRTHDAY");
							petDTO.setDateOfBirth(birthday != null ? birthday.toLocalDate() : null);
						}
						petDTO.setIsDobUnknown((Boolean) pet.get("IS_UNKNOWN"));

						if (pet.get("WEIGHT") != null) {
							BigDecimal weight = (BigDecimal) pet.get("WEIGHT");
							petDTO.setWeight(weight != null ? weight.doubleValue() : 0);
							BigDecimal weightInKgs = (BigDecimal) pet.get("WEIGHT_KGS");
							petDTO.setWeightString(weight.toString() + "LBS / " + weightInKgs.toString() + "KGS");
						}
						petDTO.setWeightUnit((String) pet.get("WEIGHT_UNIT"));
						petDTO.setIsNeutered((Boolean) pet.get("IS_NEUTERED"));
						petDTO.setIsDeceased((Boolean) pet.get("IS_DECEASED"));
						petDTO.setIsMixed((Boolean) pet.get("IS_MIXED"));
						petDTO.setPetStatusId((Integer) pet.get("PET_STATUS_ID"));
						petDTO.setIsActive((Boolean) pet.get("IS_ACTIVE"));

						// Timestamp timestamp = (Timestamp) pet.get("MODIFIED_DATE");
						petDTO.setModifiedDate((LocalDateTime) pet.get("MODIFIED_DATE"));

						if (pet.get("DATE_OF_DEATH") != null) {
							LocalDateTime deathDay = (LocalDateTime) pet.get("DATE_OF_DEATH");
							petDTO.setDateOfDeath(deathDay != null ? deathDay.toLocalDate() : null);
						}

						if (pet.get("IS_DOD_APPROX") != null) {
							int isApproxDateOfDeath = (int) pet.get("IS_DOD_APPROX");
							petDTO.setIsApproximateDateOfDeath(isApproxDateOfDeath == 1 ? true : false);
						}

						if (pet.get("LOST_TO_FOLLOW_UP_DATE") != null) {
							LocalDateTime lfuDate = (LocalDateTime) pet.get("LOST_TO_FOLLOW_UP_DATE");
							petDTO.setLostToFollowUpDate(lfuDate != null ? lfuDate.toLocalDate() : null);
						}

						if (pet.get("IS_LFD_APPROX") != null) {
							int isApproxDateOfDeath = (int) pet.get("IS_LFD_APPROX");
							petDTO.setIsApproxLostToFollowUpDate(isApproxDateOfDeath == 1 ? true : false);
						}

						petDTO.setIsPetWithPetParent((int) pet.get("IS_PET_WITH_PET_PARENT"));
						petDTO.setPetAddressString((String) pet.get("PET_ADDRESS"));

						String legLengthInfo = (String) pet.get("PET_LEG_LENGTH_INFO");
						if (StringUtils.isNotEmpty(legLengthInfo)) {
							String[] array = legLengthInfo.split(",");
							if (array.length == 3) {
								petDTO.setLegLength(Double.parseDouble(array[0]));
								petDTO.setLegLengthUnitId(Integer.parseInt(array[1]));
								petDTO.setLegLengthUnit(array[2]);
							}
						}
						petDTO.setLegLengthHistoryCount((Long) pet.get("PET_LEG_LENGTH_HISTORY_COUNT"));
						petDTO.setBreedSize((String) pet.get("BREED_SIZE"));

						if (pet.get("IBW_LBS") != null) {
							BigDecimal ibwInLbs = (BigDecimal) pet.get("IBW_LBS");
							BigDecimal ibwInKgs = (BigDecimal) pet.get("IBW_KGS");
							petDTO.setIbwString(ibwInLbs.toString() + "LBS / " + ibwInKgs.toString() + "KGS");
							if (pet.get("CORRECTED_IBW_LBS") != null) {
								BigDecimal correctedIBWInLbs = (BigDecimal) pet.get("CORRECTED_IBW_LBS");
								BigDecimal correctedIBWInKgs = (BigDecimal) pet.get("CORRECTED_IBW_KGS");
								petDTO.setIbwString(
										correctedIBWInLbs.toString() + "LBS / " + correctedIBWInKgs.toString() + "KGS");
							}
						}
					});
				}

				if (key.equals(SQLConstants.RESULT_SET_2)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(parent -> {
						PetParentDTO petParentDTO = new PetParentDTO();
						petParentDTO.setPetParentId((Integer) parent.get("PET_PARENT_ID"));
						petParentDTO.setPetParentName((String) parent.get("FULL_NAME"));
						petParentDTO.setFirstName((String) parent.get("FIRST_NAME"));
						petParentDTO.setLastName((String) parent.get("LAST_NAME"));
						petParentDTO.setEmail((String) parent.get("EMAIL"));
						petParentDTO.setPhoneNumber((String) parent.get("PHONE_NUMBER"));
						petParentDTO.setIsShipAddrSameAsResdntlAddr((Integer) parent.get("IS_SHIPPING_ADDR_SAME"));
						petParentDTO.setResidentialAddressString((String) parent.get("RESIDENTAIL_ADDRESS"));
						petParentDTO.setShippingAddressString((String) parent.get("SHIPPING_ADDRESS"));
						petParentDTO.setPreferredFoodUnitId((Integer) parent.get("PREF_FOOD_REC_UNIT_ID"));
						petParentDTO.setPreferredFoodUnit((String) parent.get("PREF_FOOD_REC_UNIT"));
						petParentDTO.setPreferredWeightUnitId((Integer) parent.get("PREF_WEIGHT_UNIT_ID"));
						petParentDTO.setPreferredWeightUnit((String) parent.get("PREF_WEIGHT_UNIT"));

						petParentDTO.setSecondaryEmail((String) parent.get("SECONDARY_EMAIL"));

						if (parent.get("SECONDARY_EMAIL_NOTIF_FLAG") != null) {
							int isNotifySecondaryEmail = (Integer) parent.get("SECONDARY_EMAIL_NOTIF_FLAG");
							petParentDTO.setIsNotifySecondaryEmail(isNotifySecondaryEmail == 1 ? true : false);
						}

						petParentDTO.setIsPetWithPetParent((Integer) parent.get("IS_PET_WITH_PET_PARENT"));
						petParents.add(petParentDTO);
					});
				}

				if (key.equals(SQLConstants.RESULT_SET_3)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(device -> {
						PetStudyDevice petStudyDevice = new PetStudyDevice();
						petStudyDevice.setStudyId((Integer) device.get("STUDY_ID"));
						petStudyDevice.setStudyName((String) device.get("STUDY_NAME"));
						Integer isExternal = (Integer) device.get("IS_EXTERNAL");
						petStudyDevice
								.setExternal((isExternal != null && isExternal == 1) ? Boolean.TRUE : Boolean.FALSE);
						petStudyDevice.setStudyDescription((String) device.get("STUDY_DESCRIPTION"));
						petStudyDevice.setDeviceId((Integer) device.get("DEVICE_ID"));
						petStudyDevice.setDeviceNumber((String) device.get("DEVICE_NUMBER"));
						petStudyDevice.setDeviceModel((String) device.get("DEVICE_MODEL"));

						if (device.get("STUDY_START_DATE") != null) {
							LocalDateTime studyStartDate = (LocalDateTime) device.get("STUDY_START_DATE");
							petStudyDevice
									.setStudyStartDate(studyStartDate != null ? studyStartDate.toLocalDate() : null);
						}

						if (device.get("MIN_STUDY_DEVICE_ASSOCIATION_DATE") != null) {
							LocalDateTime minStudyDeviceAssignedDate = (LocalDateTime) device
									.get("MIN_STUDY_DEVICE_ASSOCIATION_DATE");
							petStudyDevice.setMinStudyDeviceAssignedDate(
									minStudyDeviceAssignedDate != null ? minStudyDeviceAssignedDate.toLocalDate()
											: null);
						}

						if (device.get("ASSIGN_DATE") != null) {
							LocalDateTime assignedOn = (LocalDateTime) device.get("ASSIGN_DATE");
							petStudyDevice.setAssignedOn(assignedOn != null ? assignedOn.toLocalDate() : null);
						}

						if (device.get("START_DATE") != null) {
							LocalDateTime startDate = (LocalDateTime) device.get("START_DATE");
							petStudyDevice.setStudyAssignedOn(startDate != null ? startDate.toLocalDate() : null);
						}

						if (device.get("END_DATE") != null) {
							LocalDateTime endDate = (LocalDateTime) device.get("END_DATE");
							petStudyDevice.setStudyEndDate(endDate != null ? endDate.toLocalDate() : null);
						}

						Integer petStudyDeviceId = (Integer) device.get("PET_STUDY_DEVICE_ID");
						petStudyDevice.setPetStudyDeviceId(petStudyDeviceId != null ? petStudyDeviceId : 0);
						if (device.get("DEVICE_TYPE") != null) {
							petStudyDevice.setDeviceType((String) device.get("DEVICE_TYPE"));
						}
						if (device.get("PET_STUDY_ID") != null) {
							petStudyDevice.setPetStudyId((Integer) device.get("PET_STUDY_ID"));
						}
						if (device.get("UN_ASSIGN_DATE") != null) {
							LocalDateTime dateUnAssign = (LocalDateTime) device.get("UN_ASSIGN_DATE");
							petStudyDevice.setUnAssignedOn(dateUnAssign != null ? dateUnAssign.toLocalDate() : null);
							petStudyDevice.setUnAssignReasonId((Integer) device.get("REASON_ID"));
						}

						petStudyDevice.setDataStreamId((String) device.get("DATA_STREAM_ID"));
						petStudyDevice.setStreamDeviceSeqNum((Integer) device.get("STRM_DEVICE_SEQ_NUM"));

						Integer isStudyActive = (Integer) device.get("IS_ACTIVE");
						if (isStudyActive != null && isStudyActive != 0) {
							petStudyDevice.setStudyActive(true);
						} else {
							petStudyDevice.setStudyActive(false);
						}
						if (device.get("EXT_PET_ID") != null) {
							petStudyDevice.setExternalPetInfoId(device.get("EXT_PET_ID") + "");
							petStudyDevice.setExternalPetValue(device.get("EXT_PET_VALUE") + "");
							// petDevice.setExternal(true);
						} else {
							// petDevice.setExternal(false);
						}

						petStudyDevices.add(petStudyDevice);
					});
				}

				if (key.equals(SQLConstants.RESULT_SET_4)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(address -> {
						petAddress.setAddressId((Integer) address.get("PET_ADDRESS_ID"));
						petAddress.setAddress1((String) address.get("ADDRESS_1"));
						petAddress.setAddress2((String) address.get("ADDRESS_2"));
						petAddress.setCity((String) address.get("CITY"));
						petAddress.setState((String) address.get("STATE"));
						petAddress.setCountry((String) address.get("COUNTRY"));
						petAddress.setZipCode((String) address.get("ZIP_CODE"));
						petAddress.setTimeZone((String) address.get("TIME_ZONE"));
						petAddress.setTimeZoneId((Integer) address.get("TIMEZONE_ID"));
					});
				}

				if (key.equals(SQLConstants.RESULT_SET_5)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(device -> {
						PetDevice petDevice = new PetDevice();
						petDevice.setPetDeviceId((Integer) device.get("PET_DEVICE_ID"));
						petDevice.setPetId((Integer) device.get("PET_ID"));
						petDevice.setDeviceId((Integer) device.get("DEVICE_ID"));
						if (device.get("ALLOCATION_DATE") != null) {
							petDevice.setAllocatedOn((LocalDateTime) device.get("ALLOCATION_DATE"));
						}
						petDevice.setDeviceNumber((String) device.get("DEVICE_NUMBER"));
						petDevice.setDeviceType((String) device.get("DEVICE_TYPE"));
						petDevice.setDeviceModel((String) device.get("DEVICE_MODEL"));
						petDevices.add(petDevice);
					});
				}

				if (key.equals(SQLConstants.RESULT_SET_6)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(extPet -> {
						ExternalPetIdInfo extPetInfo = new ExternalPetIdInfo();
						extPetInfo.setPetId((Integer) extPet.get("PET_ID"));
						extPetInfo.setStudyId((String) extPet.get("STUDY_ID"));
						extPetInfo.setExtPetIdOrTattooNo((String) extPet.get("EXTERNAL_PET_ID"));
						extPetInfo.setStudyName((String) extPet.get("STUDY_NAME"));
						extPetInfo.setPreludeStudy(
								(Integer) extPet.get("IS_PRELUDE_STUDY") == NumberUtils.INTEGER_ONE ? true : false);
						extPetInfo.setExternalStudy(
								(Integer) extPet.get("IS_EXTERNAL") == NumberUtils.INTEGER_ONE ? true : false);
						extPetInfo.setStudyLocation((String) extPet.get("STUDY_LOCATION"));
						extPetIds.add(extPetInfo);
					});
				}
			}

			petDTO.setPetAddress(petAddress);
			petDTO.setPetParents(petParents);
			petDTO.setPetStudyDevices(petStudyDevices);
			petDTO.setPetDevices(petDevices);
			petDTO.setExtPetIds(extPetIds);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetById", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petDTO;
	}

	@Override
	public PetDTO getPetByIdAndStudyId(int petId, int studyId) {
		final PetDTO petDTO = new PetDTO();
		LOGGER.debug("getPetByIdAndStudyId called");
		try {
			jdbcTemplate.query(SQLConstants.PET_GET_BY_ID_AND_STUDY_ID, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					petDTO.setPetId(rs.getInt("PET_ID"));
					petDTO.setPetName(rs.getString("PET_NAME"));
					petDTO.setPhotoName(rs.getString("PHOTO_NAME"));
					String fileName = rs.getString("PHOTO_NAME");
					if (fileName != null && !fileName.trim().equals("")) {
						petDTO.setPetPhotoUrl(gcpClientUtil.getDownloaFiledUrl(fileName, Constants.GCP_PETPHOTO_PATH));
					}
					petDTO.setBreedId(rs.getInt("BREED_ID"));
					petDTO.setBreedName(rs.getString("BREED_NAME"));
					petDTO.setGender(rs.getString("GENDER"));
					petDTO.setDateOfBirth(rs.getDate("BIRTHDAY") == null ? null : rs.getDate("BIRTHDAY").toLocalDate());
					petDTO.setPetStatusId(rs.getInt("PET_STATUS_ID"));
					petDTO.setPetStatus(rs.getString("STATUS_NAME"));

					petDTO.setWeight(rs.getBigDecimal("WEIGHT").doubleValue());
					petDTO.setWeightUnit(rs.getString("WEIGHT_UNIT"));
					petDTO.setIsNeutered(rs.getBoolean("IS_NEUTERED"));
					petDTO.setIsDeceased(rs.getBoolean("IS_DECEASED"));
					petDTO.setIsMixed(rs.getBoolean("IS_MIXED"));
					petDTO.setIsActive(rs.getBoolean("IS_ACTIVE"));

					petDTO.setStudyId(rs.getInt("STUDY_ID"));
					petDTO.setStudyName(rs.getString("STUDY_NAME"));
					petDTO.setStartDate(
							rs.getDate("START_DATE") == null ? null : rs.getDate("START_DATE").toLocalDate());
					petDTO.setEndDate(rs.getDate("END_DATE") == null ? null : rs.getDate("END_DATE").toLocalDate());
					petDTO.setModifiedDate(rs.getTimestamp("MODIFIED_DATE").toLocalDateTime());
				}
			}, petId, studyId);

		} catch (Exception e) {
			LOGGER.error("error while fetching getPetByIdAndStudyId", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petDTO;
	}

	@Override
	public void deletePet(int petId, int modifiedBy) throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_pet_id", petId);
		inputParams.put("p_modified_by", modifiedBy);
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.PET_DELETE, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			if (StringUtils.isNotEmpty(errorMsg) || (int) outParams.get("out_flag") < NumberUtils.INTEGER_ONE) {
				throw new ServiceExecutionException(errorMsg, Status.BAD_REQUEST.getStatusCode(),
						Arrays.asList(new WearablesError(WearablesErrorCode.PET_ON_STUDY, errorMsg)));
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing deletePet ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public List<PetListDTO> getPetsByPetParent(int petParentId) throws ServiceExecutionException {
		List<PetListDTO> petList = new ArrayList<>();
		LOGGER.debug("getPetsByPetParent called");
		try {
			jdbcTemplate.query(SQLConstants.PET_GET_LIST_BY_PET_PARENT, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetListDTO petListDTO = new PetListDTO();
					petListDTO.setPetId(rs.getInt("PET_ID"));
					petListDTO.setPetName(rs.getString("PET_NAME"));
					petList.add(petListDTO);
				}
			}, petParentId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetsByPetParent", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getPetObservationsCount(PetObservationMediaFilter filter)
			throws ServiceExecutionException {
		String totalCount = "";
		LOGGER.debug("getPetObservationsCount called");
		HashMap<String, Integer> map = new HashMap<>();
		try {
			totalCount = selectForObject(SQLConstants.FN_GET_PET_OBSERVATIONS_COUNT, String.class, filter.getPetId(),
					filter.getStudyId(), filter.getPhaseId());
			map = mapper.readValue(totalCount, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetObservationsCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<PetObservation> getPetObservations(PetObservationMediaFilter filter) throws ServiceExecutionException {
		List<PetObservation> petObservations = new ArrayList<>();
		LOGGER.debug("getPetObservations called");
		try {
			jdbcTemplate.query(SQLConstants.PET_GET_OBSERVATIONS_BY_PET_AND_STUDY_ID, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetObservation petObservation = new PetObservation();
					petObservation.setPetId(rs.getInt("PET_ID"));
					petObservation.setActivityTypeId(rs.getInt("ACTIVITY_TYPE_ID"));
					petObservation.setActivityType(rs.getString("ACTIVITY_TYPE"));
					petObservation.setObservationText(rs.getString("OBS_TEXT"));
					petObservation.setObservationTime(rs.getTimestamp("OBSERVATION_DATE_TIME").toLocalDateTime());

					String imagePath = rs.getString("IMAGE_PATH");
					petObservation.setImageList(
							gcStorageUtil.getMediaSignedUrlList(imagePath, Constants.GCP_OBSERVATION_PHOTO_PATH));

					try {
						petObservation.setObservationVideos(new ObjectMapper()
								.readValue(rs.getString("VIDEO_DETAILS"), new TypeReference<List<ObservationVideo>>() {
								}).stream().filter(e -> e.getVideoUrl() != null).collect(Collectors.toList()));
						petObservation.getObservationVideos().stream().forEach(e -> {
							String videoUrl = gcStorageUtil.getSignedMediaUrl(e.getVideoUrl(),
									Constants.GCP_OBSERVATION_VIDEO_PATH);
							String videoThumbnailUrl = gcStorageUtil.getSignedMediaUrl(e.getVideoThumbnailUrl(),
									Constants.GCP_OBSERVATION_VIDEO_THUMBNAIL_PATH);
							e.setVideoUrl(videoUrl);
							e.setVideoThumbnailUrl(videoThumbnailUrl);
						});
					} catch (JsonProcessingException e) {
						LOGGER.error("error while converting ACTIVITY_DETAILS in getCampaignListByPet", e);
					}

					petObservation.setModifiedDate(rs.getTimestamp("MODIFIED_DATE").toLocalDateTime());

					petObservations.add(petObservation);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getSortBy(), filter.getOrder(), filter.getPetId(),
					filter.getStudyId(), filter.getPhaseId());
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetObservations", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petObservations;
	}

	@Override
	public List<PetParentListDTO> getPetParents(PetFilter filter) throws ServiceExecutionException {
		List<PetParentListDTO> parentDTOs = new ArrayList<>();
		LOGGER.debug("getPetParents called");
		try {
			jdbcTemplate.query(SQLConstants.PET_GET_PARENTS_BY_PET, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetParentListDTO parentDTO = new PetParentListDTO();
					parentDTO.setPetParentId(rs.getInt("PET_PARENT_ID"));
					parentDTO.setPetParentName(rs.getString("FULL_NAME"));
					parentDTO.setPetParentFirstName(rs.getString("FIRST_NAME"));
					parentDTO.setPetParentLastName(rs.getString("LAST_NAME"));
					parentDTO.setEmail(rs.getString("EMAIL"));
					parentDTO.setPhoneNumber(rs.getString("PHONE_NUMBER"));

					parentDTO.setIsShipAddrSameAsResdntlAddr(rs.getInt("IS_SHIPPING_ADDR_SAME"));
					parentDTO.setResidentialAddressString(rs.getString("RESIDENTIAL_ADDRESS"));
					parentDTO.setShippingAddressString(rs.getString("SHIPPING_ADDRESS"));

					parentDTO.setSecondaryEmail(rs.getString("SECONDARY_EMAIL"));
					parentDTO.setIsNotifySecondaryEmail(rs.getBoolean("SECONDARY_EMAIL_NOTIF_FLAG"));
					parentDTO.setPreferredFoodUnitId(rs.getInt("PREF_FOOD_REC_UNIT_ID"));
					parentDTO.setPreferredFoodUnit(rs.getString("PREF_FOOD_REC_UNIT"));
					parentDTO.setPreferredWeightUnitId(rs.getInt("PREF_WEIGHT_UNIT_ID"));
					parentDTO.setPreferredWeightUnit(rs.getString("PREF_WEIGHT_UNIT"));

					parentDTOs.add(parentDTO);
				}
			}, filter.getPetId(), filter.getStartIndex(), filter.getLimit(), filter.getSortBy(), filter.getOrder());
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetParents", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return parentDTOs;
	}

	@Override
	public List<StreamDevice> getDeviceHistoryByStreamId(int petStudyId, String streamId)
			throws ServiceExecutionException {
		List<StreamDevice> streamDevices = new ArrayList<>();
		LOGGER.debug("getDeviceHistoryByStreamId called");
		try {
			jdbcTemplate.query(SQLConstants.PET_GET_DEVICE_HISTORY_BY_STREAM_ID, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					StreamDevice streamDevice = new StreamDevice();
					streamDevice.setDataStreamId(rs.getString("DATA_STREAM_ID"));
					streamDevice.setDeviceId(rs.getInt("DEVICE_ID"));
					streamDevice.setDeviceNumber(rs.getString("DEVICE_NUMBER"));
					streamDevice.setDeviceType(rs.getString("DEVICE_TYPE"));
					streamDevice.setDeviceModel(rs.getString("DEVICE_MODEL"));
					streamDevice.setAssignedOn(rs.getTimestamp("ASSIGN_DATE").toLocalDateTime());
					if (rs.getTimestamp("UN_ASSIGN_DATE") != null) {
						streamDevice.setUnassignedOn(rs.getTimestamp("UN_ASSIGN_DATE").toLocalDateTime());
					}
					streamDevice.setPetStudyId(rs.getInt("PET_STUDY_ID"));
					streamDevice.setStudyId(rs.getInt("STUDY_ID"));
					streamDevice.setStudyName(rs.getString("STUDY_NAME"));
					streamDevices.add(streamDevice);
				}
			}, petStudyId, streamId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getDeviceHistoryByStreamId", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return streamDevices;
	}

	@Override
	public List<PetNote> getPetNotes(PetFilter filter) throws ServiceExecutionException {
		List<PetNote> petNotes = new ArrayList<>();
		LOGGER.debug("getPetNotes called");
		try {
			jdbcTemplate.query(SQLConstants.PET_GET_NOTES_BY_PET, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetNote petNote = new PetNote();
					petNote.setPetId(rs.getInt("PET_ID"));
					petNote.setNoteType(rs.getString("NOTE_TYPE"));
					petNote.setContent(rs.getString("CONTENT"));
					petNote.setModifiedDate(rs.getTimestamp("MODIFIED_DATE").toLocalDateTime());
					petNotes.add(petNote);
				}
			}, filter.getPetId(), filter.getStartIndex(), filter.getLimit(), filter.getSortBy(), filter.getOrder());
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetNotes", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petNotes;
	}

	@Override
	public List<PetStudyDevice> getPetDevices(int petId) throws ServiceExecutionException {
		List<PetStudyDevice> petStudyDevices = new ArrayList<>();
		LOGGER.debug("getPetDevices called");
		try {
			jdbcTemplate.query(SQLConstants.PET_GET_DEVICES_BY_PET, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetStudyDevice petDevice = new PetStudyDevice();
					petDevice.setPetId(rs.getInt("PET_ID"));
					petDevice.setStudyId(rs.getInt("STUDY_ID"));
					petDevice.setStudyName(rs.getString("STUDY_NAME"));
					petDevice.setStudyDescription(rs.getString("STUDY_DESCRIPTION"));
					petDevice.setDeviceId(rs.getInt("DEVICE_ID"));
					petDevice.setDeviceNumber(rs.getString("DEVICE_NUMBER"));
					petDevice.setFirmwareVersion(rs.getString("FIRMWARE_VERSION_NUMBER"));
					petDevice.setLastSync(rs.getString("lastSync"));
					petDevice.setAssignedOn(rs.getTimestamp("ASSIGN_DATE").toLocalDateTime().toLocalDate());
					petDevice.setPetStudyId(rs.getInt("PET_STUDY_ID"));
					petStudyDevices.add(petDevice);
				}
			}, petId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetDevices", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petStudyDevices;
	}

	@Override
	public void addPetNote(PetNote petNote) throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_pet_id", petNote.getPetId());
		inputParams.put("p_note_type", petNote.getNoteType());
		inputParams.put("p_notes", petNote.getContent());
		inputParams.put("p_created_by", petNote.getCreatedBy());
		try {
			LOGGER.info("addPetNote inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.PET_NOTES_INSERT, inputParams);
			LOGGER.info("addPetNote outParams are {}", outParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("Notes has been added successfully to Pet, Id is ", petNote.getPetId());
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException("addPetNote service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.PET_NOTE_ALREADY_EXISTS)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing addPetNote ", e);
			throw new ServiceExecutionException(e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getPetsObservatonMediaCount(PetObservationMediaFilter filter)
			throws ServiceExecutionException {
		String totalCount = "";
		LOGGER.debug("getPetsObservatonMediaCount called");
		HashMap<String, Integer> map = new HashMap<>();
		try {
			totalCount = selectForObject(SQLConstants.FN_GET_OBSERVATION_MEDIA_COUNT, String.class,
					filter.getSearchText(), filter.getStatus(), filter.getStudy(), filter.getStartDate(),
					filter.getEndDate());
			map = mapper.readValue(totalCount, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetsObservatonMediaCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<PetObservationMediaListDTO> getPetObservationMediaList(PetObservationMediaFilter filter)
			throws ServiceExecutionException {
		List<PetObservationMediaListDTO> petObservationMediaList = new ArrayList<>();
		LOGGER.debug("getPetObservationMediaList called");
		try {
			jdbcTemplate.query(SQLConstants.OBSERVATION_MEDIA_GET_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetObservationMediaListDTO petObservationMediaListDTO = new PetObservationMediaListDTO();
					petObservationMediaListDTO.setSlNumber(rs.getInt("slNo"));
					petObservationMediaListDTO.setPetId(rs.getInt("PET_ID"));
					petObservationMediaListDTO.setPetName(rs.getString("PET_NAME"));
					petObservationMediaListDTO.setPhotoName(rs.getString("PHOTO_NAME"));

					String fileName = rs.getString("PHOTO_NAME");

					if (fileName != null && !fileName.trim().equals("")) {
						petObservationMediaListDTO.setPetPhotoUrl(
								gcpClientUtil.getDownloaFiledUrl(fileName, Constants.GCP_PETPHOTO_PATH));
					}

					petObservationMediaListDTO.setBreedId(rs.getInt("BREED_ID"));
					petObservationMediaListDTO.setBreedName(rs.getString("BREED_NAME"));
					petObservationMediaListDTO.setPetStatusId(rs.getInt("PET_STATUS_ID"));
					petObservationMediaListDTO.setPetStatus(rs.getString("STATUS_NAME"));
					petObservationMediaListDTO.setStudyNames(rs.getString("STUDY_NAME"));
					petObservationMediaListDTO.setDuplicatePetNames(rs.getString("DUPLICATE_PET_NAMES"));

					setObservationMedia(rs.getString("IMAGE_PATH"), rs.getString("VIDEO_DETAILS"),
							petObservationMediaListDTO);
					if (rs.getInt("IS_PRIMARY") == NumberUtils.INTEGER_ONE
							&& StringUtils.isNotEmpty(rs.getString("DUPLICATE_PET_IDs"))) {
						List<PetObservationMediaListDTO> observations = getPetObservationsByPets(
								rs.getString("DUPLICATE_PET_IDs"));
						observations.stream().forEach(obj -> {
							petObservationMediaListDTO.getImageList().addAll(obj.getImageList());
							petObservationMediaListDTO.getObservationVideos().addAll(obj.getObservationVideos());
							if (StringUtils.isNotEmpty(obj.getStudyNames()))
								petObservationMediaListDTO.setStudyNames(
										petObservationMediaListDTO.getStudyNames() + "," + obj.getStudyNames());
						});
					}

					if (StringUtils.isNotEmpty(petObservationMediaListDTO.getDuplicatePetNames())) {
						petObservationMediaListDTO.setIsPrimaryPet(rs.getInt("IS_PRIMARY"));
					} else if (rs.getInt("IS_PRIMARY") == NumberUtils.INTEGER_ZERO) {
						petObservationMediaListDTO.setIsPrimaryPet(rs.getInt("IS_PRIMARY"));
					}
					petObservationMediaListDTO.setPetParentNames(rs.getString("PET_PARENT_NAMES"));

					petObservationMediaListDTO.setCreatedDate(rs.getTimestamp("CREATED_DATE") == null ? null
							: rs.getTimestamp("CREATED_DATE").toLocalDateTime());
					petObservationMediaListDTO.setModifiedDate(rs.getTimestamp("MODIFIED_DATE") == null ? null
							: rs.getTimestamp("MODIFIED_DATE").toLocalDateTime());
					petObservationMediaList.add(petObservationMediaListDTO);
				}

			}, filter.getStartIndex(), filter.getLimit(), filter.getSortBy(), filter.getOrder(), filter.getSearchText(),
					filter.getStatus(), filter.getStudy(), filter.getStartDate(), filter.getEndDate());

		} catch (Exception e) {
			LOGGER.error("error while fetching getPetObservationMediaList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petObservationMediaList;
	}

	private List<PetObservationMediaListDTO> getPetObservationsByPets(String petIds) throws ServiceExecutionException {
		List<PetObservationMediaListDTO> petObservations = new ArrayList<>();
		LOGGER.debug("getPetObservationsByPets called");
		try {
			jdbcTemplate.query(SQLConstants.OBSERVATION_MEDIA_FOR_DUPLICATE_PETS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetObservationMediaListDTO petObservation = new PetObservationMediaListDTO();
					petObservation.setPetId(rs.getInt("PET_ID"));

					setObservationMedia(rs.getString("IMAGE_PATH"), rs.getString("VIDEO_DETAILS"), petObservation);
					petObservations.add(petObservation);
				}
			}, petIds);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetObservationsByPets", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petObservations;
	}

	private void setObservationMedia(String imagePath, String videoDetails, PetObservationMediaListDTO petObservation) {
		petObservation
				.setImageList(gcStorageUtil.getMediaSignedUrlList(imagePath, Constants.GCP_OBSERVATION_PHOTO_PATH));
		try {
			petObservation.setObservationVideos(
					new ObjectMapper().readValue(videoDetails, new TypeReference<List<ObservationVideo>>() {
					}).stream().filter(e -> e.getVideoUrl() != null).collect(Collectors.toList()));
			petObservation.getObservationVideos().stream().forEach(e -> {
				String videoUrl = gcStorageUtil.getSignedMediaUrl(e.getVideoUrl(),
						Constants.GCP_OBSERVATION_VIDEO_PATH);
				String videoThumbnailUrl = gcStorageUtil.getSignedMediaUrl(e.getVideoThumbnailUrl(),
						Constants.GCP_OBSERVATION_VIDEO_THUMBNAIL_PATH);
				e.setVideoUrl(videoUrl);
				e.setVideoThumbnailUrl(videoThumbnailUrl);
			});
		} catch (JsonProcessingException e) {
			LOGGER.error("error while converting ACTIVITY_DETAILS in getCampaignListByPet", e);
		}
	}

	@Override
	public List<PetObservationMediaListDTO> getPetObservationMediaById(int petId) {
		List<PetObservationMediaListDTO> petObservationMediaList = new ArrayList<>();

		LOGGER.debug("getPetObservationMediaById called");
		try {
			jdbcTemplate.query(SQLConstants.OBSERVATION_MEDIA_GET_BY_ID, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetObservationMediaListDTO petObservationMediaListDTO = new PetObservationMediaListDTO();

					petObservationMediaListDTO.setPetName(rs.getString("PET_NAME"));
					petObservationMediaListDTO.setBreedId(rs.getInt("BREED_ID"));
					petObservationMediaListDTO.setBreedName(rs.getString("BREED_NAME"));
					petObservationMediaListDTO.setStudyNames(rs.getString("STUDY_NAME"));
					petObservationMediaListDTO.setPhotoName(rs.getString("PHOTO_NAME"));
					petObservationMediaListDTO.setPetStatusId(rs.getInt("PET_STATUS_ID"));
					petObservationMediaListDTO.setPetStatus(rs.getString("STATUS_NAME"));

					String fileName = rs.getString("PHOTO_NAME");
					if (fileName != null && !fileName.trim().equals("")) {
						petObservationMediaListDTO.setPetPhotoUrl(
								gcpClientUtil.getDownloaFiledUrl(fileName, Constants.GCP_PETPHOTO_PATH));
					}

					setObservationMedia(rs.getString("IMAGE_PATH"), rs.getString("VIDEO_DETAILS"),
							petObservationMediaListDTO);

					petObservationMediaListDTO.setCreatedDate(rs.getTimestamp("CREATED_DATE") == null ? null
							: rs.getTimestamp("CREATED_DATE").toLocalDateTime());
					petObservationMediaListDTO.setModifiedDate(rs.getTimestamp("MODIFIED_DATE") == null ? null
							: rs.getTimestamp("MODIFIED_DATE").toLocalDateTime());

					petObservationMediaList.add(petObservationMediaListDTO);
				}

			}, petId);

		} catch (Exception e) {
			LOGGER.error("error while fetching getPetObservationMediaById", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petObservationMediaList;
	}

	@Override
	public String getPetViewPaneCount(PetFilter filter) throws ServiceExecutionException {
		String totalCount = "";
		LOGGER.debug("getPetViewPaneCount called");
		try {
			totalCount = selectForObject(SQLConstants.PET_GET_VIEW_PANE_COUNT, String.class, filter.getSearchText(),
					filter.getUserId(), filter.getRoleTypeId());
		} catch (Exception e) {
			LOGGER.error("error while getPetViewPaneCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return totalCount;
	}

	@Override
	public List<PetListDTO> getPetViewPane(PetFilter filter) throws ServiceExecutionException {
		List<PetListDTO> petList = new ArrayList<>();
		LOGGER.debug("getPetViewPane called " + filter.getUserId());
		try {
			jdbcTemplate.query(SQLConstants.PET_GET_VIEW_PANE, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetListDTO petListDTO = new PetListDTO();
					petListDTO.setPetId(rs.getInt("PET_ID"));
					petListDTO.setPetName(rs.getString("PET_NAME"));
					petListDTO.setPetPhoto(rs.getString("PHOTO_NAME"));
					String fileName = rs.getString("PHOTO_NAME");

					if (fileName != null && !fileName.trim().equals("")) {
						petListDTO.setPetPhotoUrl(
								gcpClientUtil.getDownloaFiledUrl(fileName, Constants.GCP_PETPHOTO_PATH));
					}

					petListDTO.setBreedName(rs.getString("BREED_NAME"));
					/*
					 * petListDTO.setStudyId(rs.getInt("STUDY_ID"));
					 * petListDTO.setStudyName(rs.getString("STUDY_NAME"));
					 */
					petListDTO.setIsActive(rs.getBoolean("IS_ACTIVE"));
					petListDTO.setPetStatusId(rs.getInt("PET_STATUS_ID"));
					petListDTO.setPetStatus(rs.getString("STATUS_NAME"));
					petListDTO.setSelectStudyId(rs.getInt("SELECT_STUDY_ID"));
					petList.add(petListDTO);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getSearchText(), filter.getUserId(),
					filter.getRoleTypeId());

		} catch (Exception e) {
			LOGGER.error("error while fetching getPetViewPane", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petList;
	}

	@Override
	public void disassociateStudy(int petId, int studyId) {
		int userId = authentication.getAuthUserDetails().getUserId();
		LOGGER.debug("disassociateStudy called " + userId);
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_pet_id", petId);
		inputParams.put("p_study_id", studyId);
		inputParams.put("p_modified_by", userId);
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.PET_DISASSOCIATE_STUDY, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			if (StringUtils.isEmpty(errorMsg)) {
				LOGGER.info("Study has been disassociated with Pet successfully");
			} else {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (Exception e) {
			LOGGER.error("error while executing disassociateStudy ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		LOGGER.debug("disassociateStudy end " + userId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PetWeightHistoryResponse getPetWeightHistory(int petId, String fromDate, String toDate)
			throws ServiceExecutionException {
		PetWeightHistoryResponse response = new PetWeightHistoryResponse();
		List<PetWeightHistoryDTO> weightList = new ArrayList<>();
		List<PetIBWHistoryDTO> ibwList = new ArrayList<>();
		List<PetWeightChartDTO> weightChartList = new ArrayList<>();
		Map<LocalDate, PetWeightChartDTO> dateMap = new HashMap<>();
		LOGGER.debug("getPetWeightHistory called");
		Map<String, Object> inputParams = new HashMap<String, Object>();
		try {
			inputParams.put("pet_id", petId);
			inputParams.put("start_date", fromDate);
			inputParams.put("end_date", toDate);

			Map<String, Object> simpleJdbcCallResult = callStoredProcedure(SQLConstants.PET_GET_WEIGHT_HISTORY,
					inputParams);
			Iterator<Entry<String, Object>> itr = simpleJdbcCallResult.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();

				if (key.equals(SQLConstants.RESULT_SET_1)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(weight -> {
						PetWeightHistoryDTO dto = new PetWeightHistoryDTO();
						dto.setPetWeightId((Integer) weight.get("PET_WEIGHT_ID"));
						dto.setPetId((Integer) weight.get("PET_ID"));

						BigDecimal weightLbs = (BigDecimal) weight.get("WEIGHT_LBS");
						dto.setWeightLbs(weightLbs != null ? weightLbs.doubleValue() : 0);

						BigDecimal weightKgs = (BigDecimal) weight.get("WEIGHT_KGS");
						dto.setWeightKgs(weightKgs != null ? weightKgs.doubleValue() : 0);

						dto.setActive((boolean) weight.get("IS_ACTIVE"));
						dto.setAddDate((LocalDateTime) weight.get("ADD_DATE"));
						dto.setCreatedBy((Integer) weight.get("CREATED_BY"));
						dto.setModifiedBy((Integer) weight.get("MODIFIED_BY"));
						dto.setWeightUnit(
								(String) weight.get("WEIGHT_UNIT") == null ? null : (String) weight.get("WEIGHT_UNIT"));
						if (weight.get("CREATED_DATE") != null) {
							LocalDateTime createdDate = (LocalDateTime) weight.get("CREATED_DATE");
							dto.setCreatedDate(createdDate != null ? createdDate : null);
						}
						if (weight.get("MODIFIED_DATE") != null) {
							LocalDateTime modifiedDate = (LocalDateTime) weight.get("MODIFIED_DATE");
							dto.setModifiedDate(modifiedDate != null ? modifiedDate : null);
						}

						PetWeightChartDTO chartDto = new PetWeightChartDTO(dto.getAddDate().toLocalDate(),
								dto.getWeightKgs(), dto.getWeightLbs(), null, null);
						if (!dateMap.containsKey(dto.getAddDate().toLocalDate())) {
							dateMap.put(dto.getAddDate().toLocalDate(), chartDto);
							weightChartList.add(chartDto);
						}
						weightList.add(dto);
					});
				}

				if (key.equals(SQLConstants.RESULT_SET_2)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(ibw -> {
						PetIBWHistoryDTO dto = new PetIBWHistoryDTO();
						dto.setPetIBWId((Integer) ibw.get("PET_IBW_ID"));
						dto.setPetId((Integer) ibw.get("PET_ID"));

						BigDecimal ibwLbs = (BigDecimal) ibw.get("PET_IBW_LBS");
						dto.setIbwLbs(ibwLbs != null ? ibwLbs.doubleValue() : 0);

						BigDecimal ibwKgs = (BigDecimal) ibw.get("PET_IBW_KGS");
						dto.setIbwKgs(ibwKgs != null ? ibwKgs.doubleValue() : 0);

						dto.setIbwCalSource((String) ibw.get("IBW_CAL_SOURCE_NAME"));

						BigDecimal correctedIbwLbs = (BigDecimal) ibw.get("CORRECTED_IBW_LBS");
						dto.setCorrectedIBWLbs(correctedIbwLbs != null ? correctedIbwLbs.doubleValue() : 0);

						BigDecimal correctedIbwKgs = (BigDecimal) ibw.get("CORRECTED_IBW_KGS");
						dto.setCorrectedIBWKgs(correctedIbwKgs != null ? correctedIbwKgs.doubleValue() : 0);

						dto.setCorrectedIBWUnitId((Integer) ibw.get("CORRECTED_IBW_UNIT_ID"));
						dto.setCorrectedIBWUnit((String) ibw.get("UNIT"));
						dto.setComment((String) ibw.get("COMMENT"));

						dto.setRecordedDate((LocalDateTime) ibw.get("RECORDED_DATE"));

						dto.setCreatedBy((Integer) ibw.get("CREATED_BY"));
						dto.setModifiedBy((Integer) ibw.get("MODIFIED_BY"));

						if (ibw.get("CREATED_DATE") != null) {
							LocalDateTime createdDate = (LocalDateTime) ibw.get("CREATED_DATE");
							dto.setCreatedDate(createdDate != null ? createdDate : null);
						}
						if (ibw.get("MODIFIED_DATE") != null) {
							LocalDateTime modifiedDate = (LocalDateTime) ibw.get("MODIFIED_DATE");
							dto.setModifiedDate(modifiedDate != null ? modifiedDate : null);
						}

						dto.setGraphIBWLbs(dto.getCorrectedIBWLbs() != 0 ? dto.getCorrectedIBWLbs() : dto.getIbwLbs());
						dto.setGraphIBWKgs(dto.getCorrectedIBWKgs() != 0 ? dto.getCorrectedIBWKgs() : dto.getIbwKgs());

						PetWeightChartDTO chartDto = dateMap.get(dto.getRecordedDate().toLocalDate());

						if (chartDto != null) {
							if (chartDto.getIbwInKgs() == null) {
								chartDto.setIbwInKgs(dto.getGraphIBWKgs());
								chartDto.setIbwInLbs(dto.getGraphIBWLbs());
							}
						} else {
							chartDto = new PetWeightChartDTO(dto.getRecordedDate().toLocalDate(), null, null,
									dto.getGraphIBWKgs(), dto.getGraphIBWLbs());
							weightChartList.add(chartDto);
							dateMap.put(dto.getRecordedDate().toLocalDate(), chartDto);
						}
						ibwList.add(dto);
					});
				}
			}
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetWeightHistory", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		response.setWeightList(weightList);
		response.setIbwList(ibwList);
		response.setWeightChartList(weightChartList.stream().sorted(Comparator.comparing(PetWeightChartDTO::getDate))
				.collect(Collectors.toList()));
		return response;
	}

	@Override
	public String getPetWeightHistoryCount(BaseFilter filter, int petId, String fromDate, String toDate)
			throws ServiceExecutionException {
		String totalCount = "";
		LOGGER.debug("getPetWeightHistoryCount called");
		try {
			totalCount = selectForObject(SQLConstants.PET_GET_WEIGHT_HISTORY_COUNT, String.class, petId, fromDate,
					toDate);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetWeightHistoryCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return totalCount;
	}

	@Override
	public List<PetStudyDTO> getCurrentStudies(int petId) throws ServiceExecutionException {
		final List<PetStudyDTO> petList = new ArrayList<>();
		int userId = authentication.getAuthUserDetails().getUserId();
		LOGGER.debug("getCurrentStudies called " + userId);
		try {
			jdbcTemplate.query(SQLConstants.PET_GET_CURRENT_STUDY_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetStudyDTO petStudyDTO = new PetStudyDTO();
					petStudyDTO.setPetId(rs.getInt("PET_ID"));
					petStudyDTO.setStudyId(rs.getInt("STUDY_ID"));
					petStudyDTO.setStudyName(rs.getString("STUDY_NAME"));
					petStudyDTO.setStartDate(
							rs.getDate("START_DATE") != null ? rs.getDate("START_DATE").toLocalDate() : null);
					petStudyDTO
							.setEndDate(rs.getDate("END_DATE") != null ? rs.getDate("END_DATE").toLocalDate() : null);
					petStudyDTO.setStudyActive(rs.getBoolean("IS_ACTIVE"));
					petStudyDTO.setPricipleInvestigator(rs.getString("PRINCIPLE_INVESTIGATOR"));
					petStudyDTO.setAssigned(rs.getBoolean("IS_ASSIGN"));
					petStudyDTO.setPetStudyId(rs.getInt("PET_STUDY_ID"));
					Integer isExternalStudy = rs.getInt("IS_EXTERNAL");
					petStudyDTO.setExternalStudy(isExternalStudy == 1 ? true : false);

					// petStudyDTO.setis(rs.getBoolean("IS_ASSIGN"));
					/*
					 * petStudyDTO.setAssignedDate(rs.getDate("ASSIGN_DATE") != null ?
					 * rs.getDate("ASSIGN_DATE").toLocalDate() : null);
					 * petStudyDTO.setAssignedDate(rs.getDate("UN_ASSIGN_DATE") != null ?
					 * rs.getDate("UN_ASSIGN_DATE").toLocalDate() : null);
					 */

					petList.add(petStudyDTO);

				}
			}, petId, userId);

		} catch (Exception e) {
			LOGGER.error("error while fetching getCurrentStudies", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petList;
	}

	@Override
	public List<PetStudyDTO> getArchiveStudies(int petId) throws ServiceExecutionException {
		final List<PetStudyDTO> petList = new ArrayList<>();
		int userId = authentication.getAuthUserDetails().getUserId();
		LOGGER.debug("getArchiveStudies called " + userId);
		try {
			jdbcTemplate.query(SQLConstants.PET_GET_ARCHIVE_STUDY_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetStudyDTO petStudyDTO = new PetStudyDTO();
					petStudyDTO.setPetId(rs.getInt("PET_ID"));
					petStudyDTO.setStudyId(rs.getInt("STUDY_ID"));
					petStudyDTO.setStudyName(rs.getString("STUDY_NAME"));
					Date startDate = rs.getDate("START_DATE");
					if (startDate != null) {
						petStudyDTO.setStartDate(startDate != null ? rs.getDate("START_DATE").toLocalDate() : null);
					}
					Date endDate = rs.getDate("END_DATE");
					if (endDate != null) {
						petStudyDTO.setEndDate(endDate != null ? rs.getDate("END_DATE").toLocalDate() : null);
					}

					petStudyDTO.setStudyActive(rs.getBoolean("IS_ACTIVE"));
					petStudyDTO.setPricipleInvestigator(rs.getString("PRINCIPLE_INVESTIGATOR"));
					petStudyDTO.setAssigned(rs.getBoolean("IS_ASSIGN"));
					petStudyDTO.setPetStudyId(rs.getInt("PET_STUDY_ID"));

					// petStudyDTO.setAssetAssigned(rs.getBoolean("IS_ASSIGN"));

					/*
					 * petStudyDTO.setAssignedDate(rs.getDate("ASSIGN_DATE") != null ?
					 * rs.getDate("ASSIGN_DATE").toLocalDate() : null);
					 * petStudyDTO.setAssignedDate(rs.getDate("UN_ASSIGN_DATE") != null ?
					 * rs.getDate("UN_ASSIGN_DATE").toLocalDate() : null);
					 */

					petList.add(petStudyDTO);

				}
			}, petId, userId);

		} catch (Exception e) {
			LOGGER.error("error while fetching getArchiveStudies", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petList;
	}

	@Override
	public List<PetStudyDevice> getPetDevicesByStudy(PetFilter filter) throws ServiceExecutionException {
		List<PetStudyDevice> petStudyDevices = new ArrayList<>();
		LOGGER.debug("getPetDevicesByStudy called");
		try {
			jdbcTemplate.query(SQLConstants.PET_GET_DEVICES_BY_PET_ID_AND_STUDY_ID, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetStudyDevice petStudyDevice = new PetStudyDevice();
					petStudyDevice.setPetId(rs.getInt("PET_ID"));
					petStudyDevice.setStudyId(rs.getInt("STUDY_ID"));
					petStudyDevice.setStudyName(rs.getString("STUDY_NAME"));
					petStudyDevice.setStudyDescription(rs.getString("STUDY_DESCRIPTION"));
					petStudyDevice.setDeviceId(rs.getInt("DEVICE_ID"));
					petStudyDevice.setDeviceNumber(rs.getString("DEVICE_NUMBER"));
					petStudyDevice.setDeviceModel(rs.getString("DEVICE_MODEL"));
					petStudyDevice.setFirmwareVersion(rs.getString("FIRMWARE_VERSION_NUMBER"));
					petStudyDevice.setLastSync(rs.getString("LAST_SYNC")); // in days and hours.

					petStudyDevice.setAssignedOn(rs.getTimestamp("ASSIGN_DATE") != null
							? rs.getTimestamp("ASSIGN_DATE").toLocalDateTime().toLocalDate()
							: null);
					petStudyDevice.setBatteryPercentage(rs.getString("BATTERY_PERCENTAGE"));
					petStudyDevice.setUnassignedReason(rs.getString("UNASSIGN_REASON"));

					if (rs.getDate("UN_ASSIGN_DATE") != null) {
						petStudyDevice.setUnAssignedOn(rs.getDate("UN_ASSIGN_DATE").toLocalDate());
					}

					if (rs.getDate("DATE_OF_DEATH") != null) {
						petStudyDevice.setDateOfDeath(rs.getDate("DATE_OF_DEATH").toLocalDate());
					}
					petStudyDevice.setIsApproximateDateOfDeath(rs.getBoolean("IS_DOD_APPROX"));

					if (rs.getDate("LOST_TO_FOLLOW_UP_DATE") != null) {
						petStudyDevice.setLostToFollowUpDate(rs.getDate("LOST_TO_FOLLOW_UP_DATE").toLocalDate());
					}
					petStudyDevice.setIsApproxLostToFollowUpDate(rs.getBoolean("IS_LFD_APPROX"));

					petStudyDevices.add(petStudyDevice);
				}
			}, filter.getPetId(), filter.getStudyId(), filter.getStartIndex(), filter.getLimit(), filter.getSortBy(),
					filter.getOrder());
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetDevicesByStudy", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petStudyDevices;
	}

	/* This method using for View Pet */
	@SuppressWarnings("unchecked")
	@Override
	public PetDTO getPetDetailsById(int petId) throws ServiceExecutionException {
		final PetDTO petDTO = new PetDTO();
		LOGGER.debug("getPetDetailsById called");
		List<PetParentDTO> petParents = new ArrayList<>();
		List<PetNote> petNotes = new ArrayList<>();
		List<ExternalPetIdInfo> extPetIds = new ArrayList<>();
		try {

			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("p_pet_id", petId);

			Map<String, Object> simpleJdbcCallResult = callStoredProcedure(SQLConstants.PET_GET_DETAILS_BY_ID,
					inputParams);
			Iterator<Entry<String, Object>> itr = simpleJdbcCallResult.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();

				if (key.equals(SQLConstants.RESULT_SET_1)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();

					list.forEach(pet -> {
						petDTO.setPetId((Integer) pet.get("PET_ID"));
						petDTO.setPetName((String) pet.get("PET_NAME"));
						petDTO.setPhotoName((String) pet.get("PHOTO_NAME"));
						String fileName = (String) pet.get("PHOTO_NAME");
						if (fileName != null && !fileName.trim().equals("")) {
							petDTO.setPetPhotoUrl(
									gcpClientUtil.getDownloaFiledUrl(fileName, Constants.GCP_PETPHOTO_PATH));
						}
						if (pet.get("BREED_ID") != null) {
							petDTO.setBreedId((Integer) pet.get("BREED_ID"));
						}
						petDTO.setBreedName((String) pet.get("BREED_NAME"));
						petDTO.setSpeciesId((Integer) pet.get("SPECIES_ID"));
						petDTO.setSpeciesName((String) pet.get("SPECIES_NAME"));
						petDTO.setFeedingPreferences((String) pet.get("FEEDING_PREFERENCES"));
						petDTO.setGender((String) pet.get("GENDER"));
						LocalDateTime birthday = (LocalDateTime) pet.get("BIRTHDAY");
						petDTO.setDateOfBirth(birthday != null ? birthday.toLocalDate() : null);
						petDTO.setIsDobUnknown((Boolean) pet.get("IS_UNKNOWN"));

						if (pet.get("WEIGHT") != null) {
							BigDecimal weight = (BigDecimal) pet.get("WEIGHT");
							petDTO.setWeight(weight != null ? weight.doubleValue() : 0);
							BigDecimal weightInKgs = (BigDecimal) pet.get("WEIGHT_KGS");
							petDTO.setWeightString(weight.toString() + "LBS / " + weightInKgs.toString() + "KGS");
						}

						petDTO.setWeightUnit((String) pet.get("WEIGHT_UNIT"));
						petDTO.setIsNeutered((Boolean) pet.get("IS_NEUTERED"));
						petDTO.setIsDeceased((Boolean) pet.get("IS_DECEASED"));
						petDTO.setIsMixed((Boolean) pet.get("IS_MIXED"));
						petDTO.setPetStatusId((Integer) pet.get("PET_STATUS_ID"));
						petDTO.setPetStatus((String) pet.get("STATUS_NAME"));
						petDTO.setIsActive((Boolean) pet.get("IS_ACTIVE"));
						petDTO.setQuestionnaireAttempted((Integer) pet.get("QUESTIONNAIRE_COUNT"));

						Integer isExternalPet = (Integer) pet.get("IS_EXTERNAL_PET");
						petDTO.setIsExternalPet((isExternalPet != null && isExternalPet == 1) ? true : false);
						// Timestamp timestamp = (Timestamp) pet.get("MODIFIED_DATE");
						petDTO.setModifiedDate((LocalDateTime) pet.get("MODIFIED_DATE"));

						if (pet.get("SELECT_STUDY_ID") != null) {
							petDTO.setSelectStudyId(pet.get("SELECT_STUDY_ID") + "");
						}

						if (pet.get("DATE_OF_DEATH") != null) {
							LocalDateTime deathDay = (LocalDateTime) pet.get("DATE_OF_DEATH");
							petDTO.setDateOfDeath(deathDay != null ? deathDay.toLocalDate() : null);
						}

						if (pet.get("IS_DOD_APPROX") != null) {
							int isApproxDateOfDeath = (int) pet.get("IS_DOD_APPROX");
							petDTO.setIsApproximateDateOfDeath(isApproxDateOfDeath == 1 ? true : false);
						}

						if (pet.get("LOST_TO_FOLLOW_UP_DATE") != null) {
							LocalDateTime lfuDate = (LocalDateTime) pet.get("LOST_TO_FOLLOW_UP_DATE");
							petDTO.setLostToFollowUpDate(lfuDate != null ? lfuDate.toLocalDate() : null);
						}
						if (pet.get("IS_LFD_APPROX") != null) {
							int isApproxDateOfDeath = (int) pet.get("IS_LFD_APPROX");
							petDTO.setIsApproxLostToFollowUpDate(isApproxDateOfDeath == 1 ? true : false);
						}

						petDTO.setIsPetWithPetParent((int) pet.get("IS_PET_WITH_PET_PARENT"));
						petDTO.setPetAddressString((String) pet.get("PET_ADDRESS"));

						String legLengthInfo = (String) pet.get("PET_LEG_LENGTH_INFO");
						if (StringUtils.isNotEmpty(legLengthInfo)) {
							String[] array = legLengthInfo.split(",");
							if (array.length == 3) {
								petDTO.setLegLength(Double.parseDouble(array[0]));
								petDTO.setLegLengthUnitId(Integer.parseInt(array[1]));
								petDTO.setLegLengthUnit(array[2]);
							}
						}
						petDTO.setLegLengthHistoryCount((Long) pet.get("PET_LEG_LENGTH_HISTORY_COUNT"));
						petDTO.setBreedSize((String) pet.get("BREED_SIZE"));
						petDTO.setRecommendedFoodQuantity((String) pet.get("RECOMMENDATION"));

						if (pet.get("IBW_LBS") != null) {
							BigDecimal ibwInLbs = (BigDecimal) pet.get("IBW_LBS");
							BigDecimal ibwInKgs = (BigDecimal) pet.get("IBW_KGS");
							petDTO.setIbwString(ibwInLbs.toString() + "LBS / " + ibwInKgs.toString() + "KGS");
							if (pet.get("CORRECTED_IBW_LBS") != null) {
								BigDecimal correctedIBWInLbs = (BigDecimal) pet.get("CORRECTED_IBW_LBS");
								BigDecimal correctedIBWInKgs = (BigDecimal) pet.get("CORRECTED_IBW_KGS");
								petDTO.setIbwString(
										correctedIBWInLbs.toString() + "LBS / " + correctedIBWInKgs.toString() + "KGS");
							}
						}
					});
				}
				if (key.equals(SQLConstants.RESULT_SET_2)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(parent -> {
						PetParentDTO petParentDTO = new PetParentDTO();
						petParentDTO.setPetParentId((Integer) parent.get("PET_PARENT_ID"));
						petParentDTO.setPetParentName((String) parent.get("FULL_NAME"));
						petParentDTO.setFirstName((String) parent.get("FIRST_NAME"));
						petParentDTO.setLastName((String) parent.get("FIRST_NAME"));
						petParentDTO.setEmail((String) parent.get("EMAIL"));
						petParentDTO.setPhoneNumber((String) parent.get("PHONE_NUMBER"));

						petParentDTO.setIsShipAddrSameAsResdntlAddr((Integer) parent.get("IS_SHIPPING_ADDR_SAME"));
						petParentDTO.setResidentialAddressString((String) parent.get("RESIDENTAIL_ADDRESS"));
						petParentDTO.setShippingAddressString((String) parent.get("SHIPPING_ADDRESS"));

						petParentDTO.setSecondaryEmail((String) parent.get("SECONDARY_EMAIL"));
						if (parent.get("SECONDARY_EMAIL_NOTIF_FLAG") != null) {
							int isNotifySecondaryEmail = (Integer) parent.get("SECONDARY_EMAIL_NOTIF_FLAG");
							petParentDTO.setIsNotifySecondaryEmail(isNotifySecondaryEmail == 1 ? true : false);
						}
						petParentDTO.setPreferredFoodUnitId((Integer) parent.get("PREF_FOOD_REC_UNIT_ID"));
						petParentDTO.setPreferredFoodUnit((String) parent.get("PREF_FOOD_REC_UNIT"));
						petParentDTO.setPreferredWeightUnitId((Integer) parent.get("PREF_WEIGHT_UNIT_ID"));
						petParentDTO.setPreferredWeightUnit((String) parent.get("PREF_WEIGHT_UNIT"));

						petParents.add(petParentDTO);
					});
				}

				if (key.equals(SQLConstants.RESULT_SET_3)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(note -> {
						PetNote petNote = new PetNote();
						petNote.setPetId((Integer) note.get("PET_ID"));
						petNote.setNoteType((String) note.get("NOTE_TYPE"));
						petNote.setContent((String) note.get("CONTENT"));
						petNote.setModifiedDate((LocalDateTime) note.get("MODIFIED_DATE"));
						petNotes.add(petNote);
					});
				}

				if (key.equals(SQLConstants.RESULT_SET_4)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(extPet -> {
						ExternalPetIdInfo extPetInfo = new ExternalPetIdInfo();
						extPetInfo.setPetId((Integer) extPet.get("PET_ID"));
						extPetInfo.setStudyId((String) extPet.get("STUDY_ID"));
						extPetInfo.setExtPetIdOrTattooNo((String) extPet.get("EXTERNAL_PET_ID"));
						extPetInfo.setStudyName((String) extPet.get("STUDY_NAME"));
						extPetInfo.setPreludeStudy(
								(Integer) extPet.get("IS_PRELUDE_STUDY") == NumberUtils.INTEGER_ONE ? true : false);
						extPetInfo.setExternalStudy(
								(Integer) extPet.get("IS_EXTERNAL") == NumberUtils.INTEGER_ONE ? true : false);
						extPetInfo.setStudyLocation((String) extPet.get("STUDY_LOCATION"));
						extPetIds.add(extPetInfo);
					});
				}
			}
			petDTO.setPetParents(petParents);
			petDTO.setPetNotes(petNotes);
			petDTO.setExtPetIds(extPetIds);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetDetailsById", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petDTO;
	}

	@Override
	public PetCampaignPointsDTO getPetCampaignPoints(int petId) throws ServiceExecutionException {
		final PetCampaignPointsDTO petCampaignPointsDTO = new PetCampaignPointsDTO();
		LOGGER.debug("getPetCampaignPoints called");
		try {
			jdbcTemplate.query(SQLConstants.PET_GET_ALL_CAMPAIGN_POINTS_BY_PET, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {

					petCampaignPointsDTO.setTotalEarnedPoints(rs.getInt("TOTAL_EARNED_POINTS"));
					petCampaignPointsDTO.setImages(rs.getInt("TOTAL_IMAGES"));
					petCampaignPointsDTO.setObservations(rs.getInt("TOTAL_OBSERVATIONS"));
					petCampaignPointsDTO.setPointsUtilized(rs.getInt("POINTS_UTILIZED"));
					petCampaignPointsDTO.setQuestionnaire(rs.getInt("TOTAL_QUESIONARES"));
					petCampaignPointsDTO.setFeedback(rs.getInt("TOTAL_FEEDBACK"));
					petCampaignPointsDTO.setVideos(rs.getInt("TOTAL_VIDEOS"));
					petCampaignPointsDTO.setRedeemablePoints(rs.getInt("REMAINING_POINTS"));

				}
			}, petId);

		} catch (Exception e) {
			LOGGER.error("error while fetching getPetCampaignPoints", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petCampaignPointsDTO;
	}

	@Override
	public String getPetCampaignListCount(int petId, int activityId) throws ServiceExecutionException {
		String totalCount = "";
		LOGGER.debug("getPetCampaignListCount called");
		try {
			totalCount = selectForObject(SQLConstants.PET_GET_CAMPAIGN_POINTS_LIST_COUNT_BY_PET, String.class, petId,
					activityId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetCampaignListCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return totalCount;
	}

	@Override
	public List<PetCampaignPointsListDTO> getPetCampaignPointsList(int petId, int activityId, BaseFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getPetCampaignPointsList called");
		List<PetCampaignPointsListDTO> campaignList = new ArrayList<>();

		try {
			jdbcTemplate.query(SQLConstants.PET_GET_CAMPAIGN_POINTS_LIST_BY_PET, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetCampaignPointsListDTO campagin = new PetCampaignPointsListDTO();
					campagin.setPetCampaignPointsId(rs.getInt("TRACKER_PET_POINTS_ID"));
					campagin.setPetId(rs.getInt("PET_ID"));
					campagin.setPetName(rs.getString("PET_NAME"));
					campagin.setCampaignName(rs.getString("CAMPAIGN_NAME"));

					String fileName = rs.getString("FILE_NAME");
					String imageURL = rs.getString("IMAGE_PATH");
					if (StringUtils.isNotBlank(imageURL)) {
						if (!StringUtils.contains(imageURL, "firebasestorage")) {
							String imageSignedUrl = gcpClientUtil.getDownloaFiledUrl(
									imageURL.concat("/").concat(fileName), Constants.GCP_OBSERVATION_PHOTO_PATH);
							campagin.setImageUrl(imageSignedUrl);
						} else {
							campagin.setImageUrl(imageURL);
						}
					}

					String videoURL = rs.getString("VIDEO_URL");
					if (StringUtils.isNotBlank(videoURL)) {
						if (!StringUtils.contains(videoURL, "firebasestorage")) {
							String mediaSignedUrl = gcpClientUtil
									.getDownloaFiledUrl(
											videoURL.replaceAll(
													storageUrl + environment + "/GCloud/WPortal/ObservationVideo/", ""),
											Constants.GCP_OBSERVATION_VIDEO_PATH);
							campagin.setVideoUrl(mediaSignedUrl);
						} else {
							campagin.setVideoUrl(videoURL);
						}
					}

					String videoThumbnailURL = rs.getString("VIDEO_THUMBNAIL");
					if (StringUtils.isNotBlank(videoThumbnailURL)) {
						if (!StringUtils.contains(videoThumbnailURL, "firebasestorage")) {
							String mediaSignedThumbUrl = gcpClientUtil.getDownloaFiledUrl(videoThumbnailURL.replaceAll(
									storageUrl + environment + "/GCloud/WPortal/ObservationVideoThumbnail/", ""),
									Constants.GCP_OBSERVATION_VIDEO_THUMBNAIL_PATH);
							campagin.setVideoThumbnailUrl(mediaSignedThumbUrl);
						} else {
							campagin.setVideoThumbnailUrl(videoThumbnailURL);
						}
					}

					campagin.setQuestionnaireId(rs.getInt("QUESTIONNAIRE_ID"));
					campagin.setQuestionnaireName(rs.getString("QUESTIONNAIRE_NAME"));
					campagin.setFeedback(rs.getString("FEED_BACK_TEXT"));
					campagin.setStatusId(rs.getInt("STATUS_ID"));
					campagin.setStatus(rs.getString("STATUS"));

					Timestamp createdDate = (Timestamp) rs.getTimestamp("CREATED_DATE");
					campagin.setCreatedDate(createdDate != null ? createdDate.toLocalDateTime() : null);
					campagin.setObservation(rs.getString("OBSERVATION"));
					campagin.setActivityId(rs.getInt("ACTIVITY_ID"));
					campagin.setActivityName(rs.getString("ACTIVITY"));
					campagin.setBehaviourId(rs.getInt("BEHAVIOR_ID"));
					campagin.setBehaviourName(rs.getString("BEHAVIOR"));
					campagin.setPoints(rs.getInt("POINTS"));

					campaignList.add(campagin);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), petId, activityId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetCampaignPointsList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return campaignList;
	}

	@Override
	public String getPetRewardPointsHistoryCount(int petId) throws ServiceExecutionException {
		String totalCount = "";
		LOGGER.debug("getPetRewardPointsHistoryCount called");
		try {
			totalCount = selectForObject(SQLConstants.PET_GET_REWARD_POINTS_LIST_COUNT_BY_PET, String.class, petId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetRewardPointsHistoryCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return totalCount;
	}

	@Override
	public List<PetRedemptionHistoryDTO> getPetRedemptionHistory(int petId) throws ServiceExecutionException {
		LOGGER.debug("getPetRedemptionHistory called");
		List<PetRedemptionHistoryDTO> redemptionHistoryDTOs = new ArrayList<>();

		try {
			jdbcTemplate.query(SQLConstants.PET_GET_REDEEMTION_HISTORY_BY_PET, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetRedemptionHistoryDTO redemptionHistoryDTO = new PetRedemptionHistoryDTO();
					redemptionHistoryDTO.setPetId(rs.getInt("PET_ID"));
					redemptionHistoryDTO.setTotalPoints(rs.getInt("TOTAL_POINTS"));
					redemptionHistoryDTO.setPointsRedeemed(rs.getInt("POINTS_REDEMED"));
					redemptionHistoryDTO.setBalancePoints(rs.getInt("AVAILABLE_POINTS"));

					redemptionHistoryDTO.setRedeemedByUserId(rs.getInt("REDEEMED_BY"));
					redemptionHistoryDTO.setRedeemedByUser(rs.getString("FULL_NAME"));

					redemptionHistoryDTO.setCreatedBy(rs.getInt("CREATED_BY"));
					redemptionHistoryDTO.setModifiedBy(rs.getInt("MODIFIED_BY"));

					Timestamp modifiedDate = (Timestamp) rs.getTimestamp("MODIFIED_DATE");
					Timestamp createdDate = (Timestamp) rs.getTimestamp("CREATED_DATE");
					redemptionHistoryDTO.setCreatedDate(createdDate != null ? createdDate.toLocalDateTime() : null);
					redemptionHistoryDTO.setModifiedDate(modifiedDate != null ? modifiedDate.toLocalDateTime() : null);

					redemptionHistoryDTOs.add(redemptionHistoryDTO);
				}
			}, petId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetRedemptionHistory", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return redemptionHistoryDTOs;
	}

	@Override
	public void redeemRewardPoints(int petId, int redeemPoints, Integer userId) throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_pet_id", petId);
		inputParams.put("p_redeem_points", redeemPoints);
		inputParams.put("p_modified_by", userId);
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.PET_REDEEM_POINTS, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			if (StringUtils.isEmpty(errorMsg)) {
				LOGGER.info("Pet Redeem points for Pet successfully");
			} else {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (Exception e) {
			LOGGER.error("error while executing redeemRewardPoints ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public List<PetExternalInfoListDTO> getExternalPetInfoList(int studyId) throws ServiceExecutionException {
		LOGGER.debug("getExternalPetInfoList start");
		List<PetExternalInfoListDTO> externalPetList = new ArrayList<>();
		try {
			jdbcTemplate.query(SQLConstants.PET_GET_EXTERNAL_INFO_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetExternalInfoListDTO dto = new PetExternalInfoListDTO();
					dto.setExternalPetId(rs.getString("EXT_PET_ID"));
					dto.setExternalPetValue(rs.getString("EXT_PET_VALUE"));

					externalPetList.add(dto);
				}
			}, studyId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getExternalPetInfoList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		LOGGER.debug("getExternalPetInfo end ");
		return externalPetList;
	}

	@Override
	public List<ExternalPetInfoListDTO> getExternalPetInfo(ExternalPetInfoFilter filter)
			throws ServiceExecutionException {
		List<ExternalPetInfoListDTO> externalPetInfoListDTOList = new ArrayList<>();
		try {
			String sql = SQLConstants.PET_GET_EXTERNAL_PET_INFO;
			jdbcTemplate.query(sql, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					ExternalPetInfoListDTO externalPetInfoListDTO = new ExternalPetInfoListDTO();
					externalPetInfoListDTO.setPlainId(rs.getString("EXT_PLAN_ID"));
					externalPetInfoListDTO.setPetId(rs.getString("EXT_PET_ID"));
					externalPetInfoListDTO.setCategory(rs.getString("CATEGORY"));
					externalPetInfoListDTO.setExtractGroup(rs.getString("EXTRACT_GROUP"));
					externalPetInfoListDTO.setFieldName(rs.getString("FIELD_NAME"));
					externalPetInfoListDTO.setFieldValue(rs.getString("FIELD_VALUE"));
					externalPetInfoListDTOList.add(externalPetInfoListDTO);
				}
			}, filter.getPetId(), filter.getStudyId());
		} catch (Exception e) {
			LOGGER.error("error while executing getExternalPetInfo ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return externalPetInfoListDTOList;
	}

	@Override
	public PetDTO associateNewStudy(AssociateNewStudyRequest request) throws ServiceExecutionException {
		PetDTO petDTO = new PetDTO();
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_pet_id", request.getPetId());
			inputParams.put("p_study_id", request.getStudyId());
			inputParams.put("p_study_associated_on", request.getAssignedOnDate());
			inputParams.put("p_user_id", request.getUserId());
			inputParams.put("p_external_pet_id", request.getExternalPetInfoId());

			Map<String, Object> outParams = callStoredProcedure(SQLConstants.PET_ASSOCIATE_NEW_STUDY, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isNotEmpty(errorMsg) && statusFlag < NumberUtils.INTEGER_ZERO) {
				if (statusFlag == -2) {
					throw new ServiceExecutionException("Pet Associate New Study, Study already assigend to the pet.",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.STUDY_ALREADY_EXIST_TO_PET, errorMsg)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}

		} catch (SQLException e) {
			LOGGER.error("error while executing associateNewStudy ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petDTO;
	}

	@Override
	public PetDTO addPetWeight(AddPetWeight addPetWeight) throws ServiceExecutionException {
		PetDTO petDTO = new PetDTO();
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_pet_id", addPetWeight.getPetId());
			inputParams.put("p_weight", addPetWeight.getWeight());
			inputParams.put("p_weight_unit", addPetWeight.getWeightUnit());
			inputParams.put("p_user_id", addPetWeight.getUserId());
			inputParams.put("p_add_date", addPetWeight.getAddDate());

			LOGGER.info("addPetWeight inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.PET_ADD_WEIGHT, inputParams);
			LOGGER.info("addPetWeight outParams are {}", outParams);
			// Added by deepak for IBW trigger
			String errorMsg = null;// (String) outParams.get("out_error_msg");
			int statusFlag = 0;// (int) outParams.get("out_flag");
			for (Entry<String, Object> entry : outParams.entrySet()) {
				String key = entry.getKey();
				if (key.equals(SQLConstants.RESULT_SET_1)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(pet -> {
						LOGGER.info("IBW Updated from addPetWeight @out_flag :: ", pet.get("@out_flag"));
						LOGGER.info("IBW Updated from addPetWeight @out_error_msg :: ", pet.get("@out_error_msg"));
						if (StringUtils.isNotEmpty((String) pet.get("@out_error_msg"))
								&& (int) pet.get("@out_flag") < NumberUtils.INTEGER_ZERO) {
							throw new ServiceExecutionException((String) pet.get("@out_error_msg"));
						} else {
							this.invokeAFEngine(addPetWeight.getPetId());
							LOGGER.info("Pet IBW updated successfully");
						}
					});
				}
				if (key.equals("out_flag")) {
					LOGGER.info(outParams.get("out_flag"));
					statusFlag = (int) outParams.get("out_flag");
				}
				if (key.equals("out_error_msg")) {
					LOGGER.info(outParams.get("out_error_msg"));
					errorMsg = (String) outParams.get("out_error_msg");
				}
			}
			// END
			if (StringUtils.isNotEmpty(errorMsg) && statusFlag < NumberUtils.INTEGER_ZERO) {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (Exception e) {
			LOGGER.error("error while executing addPetWeight ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petDTO;
	}

	@Override
	public void updateWeight(int weightId, String weight, String unit, boolean latest) throws ServiceExecutionException {
		int userId = authentication.getAuthUserDetails().getUserId();
		LOGGER.debug("updateWeight called " + weightId);
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_weight_id", weightId);
		inputParams.put("p_weight", weight);
		inputParams.put("p_weight_unit", unit);
		inputParams.put("p_modified_by", authentication.getAuthUserDetails().getUserId());
		try {
			LOGGER.info("updateWeight inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.PET_UPDATE_WEIGHT, inputParams);
			LOGGER.info("updateWeight outParams are {}", outParams);
			// Added by deepak for IBW trigger
			String errorMsg = null;// (String) outParams.get("out_error_msg");
			int statusFlag = 0;// (int) outParams.get("out_flag");
			for (Entry<String, Object> entry : outParams.entrySet()) {
				String key = entry.getKey();
				if (key.equals(SQLConstants.RESULT_SET_2)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(pet -> {
						LOGGER.info("IBW Updated from updateWeight @out_flag :: " + pet.get("@out_flag"));
						LOGGER.info("IBW Updated from updateWeight @out_error_msg :: " + pet.get("@out_error_msg"));
						LOGGER.info("IBW Updated from updateWeight v_pet_id :: " + pet.get("v_pet_id"));
						if (StringUtils.isNotEmpty((String) pet.get("@out_error_msg"))
								&& (int) pet.get("@out_flag") < NumberUtils.INTEGER_ZERO) {
							throw new ServiceExecutionException((String) pet.get("@out_error_msg"));
						} else {
							if (pet.get("v_pet_id") != null && latest) {
								this.invokeAFEngine((int) pet.get("v_pet_id"));
								LOGGER.info("Pet IBW updated successfully");
							}
						}
					});
				}
				if (key.equals("out_flag")) {
					LOGGER.info(outParams.get("out_flag"));
					// statusFlag = (int) outParams.get("out_flag");
				}
				if (key.equals("out_error_msg")) {
					LOGGER.info(outParams.get("out_error_msg"));
					errorMsg = (String) outParams.get("out_error_msg");
				}
			}
			// END

			if (StringUtils.isEmpty(errorMsg)) {
				LOGGER.info("Pet weight updated successfully");
			} else {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (Exception e) {
			LOGGER.error("error while executing updateWeight ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		LOGGER.debug("updateWeight end " + userId);
	}

	@Override
	public String getPetFeedingEnthusiasmScaleDtlsCount(PetEnthusiasmFilter filter) throws ServiceExecutionException {
		String totalCount = "";
		LOGGER.debug("getPetFeedingEnthusiasmScaleDtlsCount called");
		try {
			totalCount = selectForObject(SQLConstants.PET_FEEDING_ENTHUSIASM_SCALE_GET_COUNT, String.class,
					filter.getSearchText(), filter.getPetId(), filter.getEnthusiasmScaleId(), filter.getFeedingTimeId(),
					filter.getStartDate(), filter.getEndDate());
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetFeedingEnthusiasmScaleDtlsCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return totalCount;
	}

	@Override
	public List<PetFeedingEnthusiasmScale> getPetFeedingEnthusiasmScaleDtls(PetEnthusiasmFilter filter)
			throws ServiceExecutionException {
		List<PetFeedingEnthusiasmScale> feedingEnthusiasmScaleDtls = new ArrayList<>();
		LOGGER.debug("getPetFeedingEnthusiasmScaleDtls called");
		try {
			jdbcTemplate.query(SQLConstants.PET_FEEDING_ENTHUSIASM_SCALE_GET_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetFeedingEnthusiasmScale feedingEnthusiasmScale = new PetFeedingEnthusiasmScale();
					feedingEnthusiasmScale.setSlNumber(rs.getInt("slNo"));
					feedingEnthusiasmScale.setFeedingEnthusiasmScaleId(rs.getInt("FEEDING_ENTSM_SCALE_ID"));
					feedingEnthusiasmScale.setPetId(rs.getInt("PET_ID"));
					feedingEnthusiasmScale.setEnthusiasmScaleId(rs.getInt("ENTHUSIASM_SCALE_ID"));
					feedingEnthusiasmScale.setFeedingTimeId(rs.getInt("PET_FEEDING_TIME_ID"));
					feedingEnthusiasmScale.setFeedingDate(rs.getTimestamp("FEEDING_DATE").toLocalDateTime());
					feedingEnthusiasmScale.setPetParentId(rs.getInt("MODIFIED_BY"));
					feedingEnthusiasmScale.setEnthusiasmScale(rs.getString("ENTHUSIASM_SCALE"));
					feedingEnthusiasmScale.setEnthusiasmScaleDesc(rs.getString("DESCRIPTION"));
					feedingEnthusiasmScale.setFeedingTime(rs.getString("FEEDING_VALUE"));
					feedingEnthusiasmScale.setPetParentName(rs.getString("FULL_NAME"));
					feedingEnthusiasmScaleDtls.add(feedingEnthusiasmScale);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getPetId(), filter.getEnthusiasmScaleId(), filter.getFeedingTimeId(), filter.getStartDate(),
					filter.getEndDate());

		} catch (Exception e) {
			LOGGER.error("error while fetching getPetFeedingEnthusiasmScaleDtls", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return feedingEnthusiasmScaleDtls;
	}

	@Override
	public String getPetImageScaleDtlsCount(PetImageScaleFilter filter) throws ServiceExecutionException {
		String totalCount = "";
		LOGGER.debug("getPetImageScaleDtlsCount called");
		try {
			totalCount = selectForObject(SQLConstants.PET_IMAGE_SCALE_GET_COUNT, String.class, filter.getSearchText(),
					filter.getPetId(), filter.getImageScaleId(), filter.getScoringTypeId(), filter.getStartDate(),
					filter.getEndDate());
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetImageScaleDtlsCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return totalCount;
	}

	@Override
	public List<PetImageScale> getPetImageScaleDtls(PetImageScaleFilter filter) throws ServiceExecutionException {
		List<PetImageScale> PetImageScaleList = new ArrayList<>();
		LOGGER.debug("getPetImageScaleDtls called");
		try {
			jdbcTemplate.query(SQLConstants.PET_IMAGE_SCALE_GET_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetImageScale petImageScale = new PetImageScale();
					petImageScale.setSlNumber(rs.getInt("slNo"));
					petImageScale.setPetId(rs.getInt("PET_ID"));
					petImageScale.setScoreType(rs.getString("SCORING_TYPE"));
					petImageScale.setScaleName(rs.getString("IMAGE_SCALE_NAME"));
					petImageScale.setImageLabel(rs.getString("IMAGE_LABEL"));
					petImageScale.setScore(rs.getString("SCORE"));
					petImageScale.setPetParentName(rs.getString("FULL_NAME"));
					petImageScale.setSubmittedOn(DateTimeFormatter.ofPattern("MM/dd/yyyy")
							.format(rs.getTimestamp("SUBMITTED_ON").toLocalDateTime()));
					petImageScale.setPetImagePath(rs.getString("PET_IMAGE"));
					petImageScale.setPetImgThumbnailPath(rs.getString("PET_IMAGE_THUMBNAIL"));
					String imageName = rs.getString("SCALE_IMAGE");
					if (StringUtils.isNotEmpty(imageName)) {
						petImageScale.setScaleImagePath(
								gcpClientUtil.getDownloaFiledUrl(imageName, Constants.GCP_IMAGE_SCORING_PATH));
					}

					PetImageScaleList.add(petImageScale);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getPetId(), filter.getImageScaleId(), filter.getScoringTypeId(), filter.getStartDate(),
					filter.getEndDate());

		} catch (Exception e) {
			LOGGER.error("error while fetching getPetImageScaleDtls", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return PetImageScaleList;
	}

	@Override
	public String getPetParentsCount(PetFilter filter) throws ServiceExecutionException {
		String totalCount = "";
		LOGGER.debug("getPetParentsCount called");
		try {
			totalCount = selectForObject(SQLConstants.PET_GET_PARENTS_LIST_COUNT, String.class, filter.getPetId());
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetParentsCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return totalCount;
	}

	@Override
	public String getPetNotesCount(PetFilter filter) throws ServiceExecutionException {
		String totalCount = "";
		LOGGER.debug("getPetNotesCount called");
		try {
			totalCount = selectForObject(SQLConstants.PET_GET_NOTES_LIST_COUNT, String.class, filter.getPetId());
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetNotesCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return totalCount;
	}

	@Override
	public String getPetDevicesByStudyCount(PetFilter filter) throws ServiceExecutionException {
		String totalCount = "";
		LOGGER.debug("getPetDevicesByStudyCount called");
		try {
			totalCount = selectForObject(SQLConstants.PET_GET_DEVICES_LIST_COUNT, String.class, filter.getPetId(),
					filter.getStudyId());
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetDevicesByStudyCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return totalCount;
	}

	@Override
	public String ActivityFactorResultResponseListCount(PetActivityFactorFilter filter)
			throws ServiceExecutionException {
		String totalCount = "";
		LOGGER.debug("ActivityFactorResultResponseListCount called");
		try {
			totalCount = selectForObject(SQLConstants.PET_GET_ACTIVITY_FACTOR_RESULTS_BY_PET_COUNT, String.class,
					filter.getStudyIds(), filter.getPetIds(), filter.getStartDate(), filter.getEndDate());
		} catch (Exception e) {
			LOGGER.error("error while ActivityFactorResultResponseListCount getPetDevicesByStudyCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return totalCount;
	}

	@Override
	public List<ActivityFactorResultResponse> getPetActivityFactorResult(PetActivityFactorFilter filter)
			throws ServiceExecutionException {
		List<ActivityFactorResultResponse> petAFResultList = new ArrayList<>();
		LOGGER.debug("getPetActivityFactorResult called");
		try {
			jdbcTemplate.query(SQLConstants.PET_GET_ACTIVITY_FACTOR_RESULTS_BY_PET, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					ActivityFactorResultResponse result = new ActivityFactorResultResponse();
					result.setStudyName(rs.getString("STUDY_NAME"));
					result.setStudyId(rs.getString("STUDY_ID"));
					result.setPetName(rs.getString("PET_NAME"));
					result.setPetId(rs.getInt("PET_ID"));
					result.setExtPetValue(rs.getString("EXT_PET_VALUE"));
					result.setAfCalculatedDate(rs.getString("ACTIVITY_FACTOR_CALCULATED_DATE"));
					result.setAlgorithmName(rs.getString("ALGORITHM_NAME"));
					result.setAlgorithmVerion(rs.getString("ALGORITHM_VERSION"));
					// result.setTraditionalEe(rs.getString("TRADITIONAL_EE"));
					result.setTotalEnergyExpenditure(rs.getFloat("TOTAL_ENERGY_EXPENDITURE"));
					result.setEstimatedEnergyExpenditure(rs.getString("ESTIMATED_ENERGY_EXPENDITURE"));
					result.setEstimatedStepCount(rs.getString("ESTIMATED_STEP_COUNT"));
					result.setEstimatedAf(rs.getString("ESTIMATED_ACTIVITY_FACTOR"));
					// result.setRecommendedFeedAmt(rs.getString("RECOMMENDED_FEED_AMOUNT"));
					result.setRecommendedFeedAmtGrams(rs.getFloat("RECOMMENDED_DIET_AMT_GMS"));
					result.setRecommendedFeedAmtCups(rs.getFloat("RECOMMENDED_DIET_AMT_CUPS"));
					result.setFeedUnits(rs.getString("FEED_UNITS"));
					result.setAssessmentStartDate(rs.getString("ASSESSMENT_START"));
					result.setAssessmentEndDate(rs.getString("ASSESSMENT_END"));
					result.setQualifyingDays(rs.getString("QUALIFYING_DAYS"));
					result.setRestingEnergyRequirement(rs.getFloat("RESTING_ENERGY_REQUIREMENT"));
					result.setAdjustmentFactor(rs.getString("ADJUSTMENT_FACTOR"));
					result.setAdjustedEnergyExpenditure(rs.getString("ADJUSTED_ENERGY_EXPENDITURE"));
					result.setFeedDensity(rs.getString("FEED_DENSITY"));
					result.setAfVersion(rs.getString("AF_VERSION"));
					result.setRoundedRecommendedFeedAmtGrams(rs.getString("ROUNDED_RECOMMENDED_FEED_AMOUNT_GRAMS"));
					result.setRoundedRecommendedFeedAmtCups(rs.getString("ROUNDED_RECOMMENDED_FEED_AMOUNT_CUPS"));
					result.setManualQuantityGrams(rs.getFloat("MANUAL_QTY_GRAMS"));
					result.setManualQuantityCups(rs.getFloat("MANUAL_QTY_CUPS"));
					result.setEnergyExpenditure(rs.getFloat("ENERGY_EXPENDITURE"));
					result.setFlagged(rs.getInt("IS_FLAGGED") > 0);
					result.setFlaggedMessage(rs.getString("FLAGGED_MESSAGE"));
					result.setAfId(rs.getInt("ACTIVITY_AF_ID"));
					result.setIsLatestRecord(rs.getShort("IS_EDIT"));
					result.setRecommendationStatusId(rs.getInt("RECOMMENDATION_STATUS_ID"));
					result.setLastModifiedDate(rs.getString("MODIFIED_DATE"));
					result.setAfRecommendationException(rs.getShort("AF_RECOMMENDATION_EXCEPTION"));
					result.setComments(rs.getString("ERROR_MESSAGE"));
					petAFResultList.add(result);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getStudyIds(), filter.getPetIds(), filter.getStartDate(), filter.getEndDate());

		} catch (Exception e) {
			LOGGER.error("error while fetching getPetActivityFactorResult", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petAFResultList;
	}

	@Override
	public PetAddressResponse getPetAddressHistoryById(int petId) throws ServiceExecutionException {
		PetAddressResponse response = new PetAddressResponse();
		List<Address> petAddressList = new ArrayList<Address>();
		LOGGER.debug("getPetParentAddressHistoryById called");
		// int userId = authentication.getAuthUserDetails().getUserId();
		try {
			String sql = SQLConstants.PET_GET_ADDRESSES_BY_ID;
			jdbcTemplate.query(sql, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					Address petAddress = new Address();
					petAddress.setAddressId(rs.getInt("PET_ADDRESS_ID"));
					petAddress.setAddress1(rs.getString("ADDRESS_1"));
					petAddress.setAddress2(rs.getString("ADDRESS_2"));
					petAddress.setCity(rs.getString("CITY"));
					petAddress.setState(rs.getString("STATE"));
					petAddress.setCountry(rs.getString("COUNTRY"));
					petAddress.setZipCode(rs.getString("ZIP_CODE"));
					petAddress.setTimeZone(rs.getString("TIME_ZONE"));
					petAddress.setTimeZoneId(rs.getInt("TIMEZONE_ID"));

					petAddress.setDateFrom(
							rs.getTimestamp("FROM_DATE") != null ? rs.getTimestamp("FROM_DATE").toLocalDateTime()
									: null);
					petAddress.setDateTo(
							rs.getTimestamp("TO_DATE") != null ? rs.getTimestamp("TO_DATE").toLocalDateTime() : null);

					petAddressList.add(petAddress);
				}
			}, petId);

			response.setPetAddressList(petAddressList);

			// List<Address> petParentAddressList = getParentAddressList(petId);
			// response.setPetParentAddressList(petParentAddressList);

		} catch (Exception e) {
			LOGGER.error("error while executing getExternalPetInfo ", e);
			throw new ServiceExecutionException(e.getMessage());
		}

		return response;
	}

	@Override
	public List<PetName> getPointsAccumulatedPets(int userId) throws ServiceExecutionException {
		List<PetName> petsList = new ArrayList<>();
		try {
			String sql = SQLConstants.GET_POINTS_ACCUMULATED_PETS;
			jdbcTemplate.query(sql, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetName dto = new PetName();
					dto.setPetName(rs.getString("PET_NAME"));
					dto.setPetId(rs.getInt("PET_ID"));

					petsList.add(dto);
				}
			}, userId);
		} catch (Exception e) {
			LOGGER.error("error while executing getPointsAccumulatedPets ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petsList;
	}

	@Override
	public String validateDuplicatePet(ValidateDuplicatePetRequest petRequest) throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_pet_name", petRequest.getPetName());
			inputParams.put("p_pet_id", petRequest.getPetId());
			inputParams.put("p_pet_parents", mapper.writeValueAsString(petRequest.getPetParents()));
			inputParams.put("p_species_id", petRequest.getSpeciesId());
			inputParams.put("p_breed_id", petRequest.getBreedId());
			inputParams.put("p_gender", petRequest.getGender());

			Map<String, Object> outParams = callStoredProcedure(SQLConstants.PET_VALIDATE_DUPLICATE_PET, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			return errorMsg;
		} catch (SQLException | JsonProcessingException e) {
			LOGGER.error("error while executing validateDuplicatePet ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public List<Address> getParentAddressList(int petId) {
		LOGGER.debug("getParentAddressList called");
		List<Address> petParentAddressList = new ArrayList<Address>();

		try {
			String sql = SQLConstants.PET_PARENT_ADDRESSES_BY_PET_ID;
			jdbcTemplate.query(sql, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					Address petAddress = new Address();
					petAddress.setAddressId(rs.getInt("PET_PARENT_ADDRESS_ID"));
					petAddress.setAddress1(rs.getString("ADDRESS_1"));
					petAddress.setAddress2(rs.getString("ADDRESS_2"));
					petAddress.setCity(rs.getString("CITY"));
					petAddress.setState(rs.getString("STATE"));
					petAddress.setCountry(rs.getString("COUNTRY"));
					petAddress.setZipCode(rs.getString("ZIP_CODE"));
					petAddress.setTimeZone(rs.getString("TIME_ZONE"));
					petAddress.setTimeZoneId(rs.getInt("TIMEZONE_ID"));

					petAddress.setDateFrom(
							rs.getTimestamp("FROM_DATE") != null ? rs.getTimestamp("FROM_DATE").toLocalDateTime()
									: null);
					petAddress.setDateTo(
							rs.getTimestamp("TO_DATE") != null ? rs.getTimestamp("TO_DATE").toLocalDateTime() : null);

					petParentAddressList.add(petAddress);
				}
			}, petId);

		} catch (Exception e) {
			LOGGER.error("error while executing getExternalPetInfo ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petParentAddressList;
	}

	@Override
	public BehaviorVisualizationResponse getBehaviorVisualization(int petId) throws ServiceExecutionException {
		String query = MessageFormat.format(SQLConstants.BIG_QEURY_GET_BEHAVIOR_VISULAZATION, Integer.toString(petId));
		LOGGER.info("===============getBehaviorVisualization================ " + query);
		TableResult tableResult = BigQueryServiceUtil.queryBigQueryTable(query);
		LOGGER.info("Total Rows == getBehaviorVisualization " + tableResult.getTotalRows());
		BehaviorVisualizationResponse response = new BehaviorVisualizationResponse();
		PetForwardMotionInfo forwardMotionInfo = new PetForwardMotionInfo();
		PetSleepInfo sleepInfo = new PetSleepInfo();
		PetForwardMotionGoalSetting fmGoalSetInfo = new PetForwardMotionGoalSetting();
		for (FieldValueList row : tableResult.iterateAll()) {
			response.setPetId(petId);
			int todayWalking = Integer.parseInt((String) row.get("TODAY_WALKING").getValue());
			int todayRunning = Integer.parseInt((String) row.get("TODAY_RUNNING").getValue());
			int todayForwardMotion = todayWalking + todayRunning;

			forwardMotionInfo.setRunning(todayRunning);
			forwardMotionInfo.setRunningText(WearablesUtils.covertToReadableTime(todayRunning));

			forwardMotionInfo.setWalking(todayWalking);
			forwardMotionInfo.setWalkingText(WearablesUtils.covertToReadableTime(todayWalking));

			forwardMotionInfo.setTodayForwardMotion(todayForwardMotion);
			forwardMotionInfo.setTodayForwardMotionText(WearablesUtils.covertToReadableTime(todayForwardMotion));

			forwardMotionInfo.setLastWeekForwardMotionAverage(
					(int) (Double.parseDouble((String) row.get("LAST_WEEK_FORWARD_MOTION_AVG").getValue())));
			forwardMotionInfo.setLastWeekFMAvgText(
					WearablesUtils.covertToReadableTime(forwardMotionInfo.getLastWeekForwardMotionAverage()));

			int todayVsLastWeekFMAvgPercentage = 0;
			if (forwardMotionInfo.getLastWeekForwardMotionAverage() > 0) {
				todayVsLastWeekFMAvgPercentage = ((todayForwardMotion * 100)
						/ forwardMotionInfo.getLastWeekForwardMotionAverage());
			} else {
				todayVsLastWeekFMAvgPercentage = todayForwardMotion > 0 ? 100 : 0;
			}
			forwardMotionInfo.setTodayVsLastWeekFMAvgPercentage(todayVsLastWeekFMAvgPercentage);

			forwardMotionInfo.setPreviousDayForwardMotion(
					Integer.parseInt((String) row.get("PREV_DAY_FORWARD_MOTION").getValue()));
			forwardMotionInfo.setPreviousDayFMText(
					WearablesUtils.covertToReadableTime(forwardMotionInfo.getPreviousDayForwardMotion()));

			int prevDayFMVsLastWeekFMAvgPercent = 0;
			if (forwardMotionInfo.getLastWeekForwardMotionAverage() > 0) {
				prevDayFMVsLastWeekFMAvgPercent = ((forwardMotionInfo.getPreviousDayForwardMotion() * 100)
						/ forwardMotionInfo.getLastWeekForwardMotionAverage());
			} else {
				prevDayFMVsLastWeekFMAvgPercent = forwardMotionInfo.getPreviousDayForwardMotion() > 0 ? 100 : 0;
			}
			forwardMotionInfo.setPrevDayFMVsLastWeekFMAvgPercentage(prevDayFMVsLastWeekFMAvgPercent);

			forwardMotionInfo.setTodayForwardMotionSofar(todayForwardMotion);
			forwardMotionInfo.setTodayForwardMotionSofarText(
					WearablesUtils.covertToReadableTime(forwardMotionInfo.getTodayForwardMotionSofar()));

			forwardMotionInfo.setTodayFMSofarVsLastWeekFMAvgPercentage(todayVsLastWeekFMAvgPercentage);
			forwardMotionInfo.setPrevDayForwardMotionAtThisTime(
					Integer.parseInt((String) row.get("PREV_DAY_TILL_NOW_FM").getValue()));
			forwardMotionInfo.setTodayFMSofarVsPrevDayFMAtThisTime(
					todayForwardMotion - forwardMotionInfo.getPrevDayForwardMotionAtThisTime());
			response.setForwardMotionInfo(forwardMotionInfo);

			// Sleep
			int todayNightSleep = Integer.parseInt((String) row.get("TODAY_NIGHT_SLEEP").getValue());
			int todayDaySleep = Integer.parseInt((String) row.get("TODAY_DAY_SLEEP").getValue());
			int todayTotalSleep = todayNightSleep + todayDaySleep;

			sleepInfo.setNightSleep(todayNightSleep);
			sleepInfo.setNightSleepText(WearablesUtils.covertToReadableTime(todayNightSleep));

			sleepInfo.setDaySleep(todayDaySleep);
			sleepInfo.setDaySleepText(WearablesUtils.covertToReadableTime(todayDaySleep));

			sleepInfo.setTodaySleep(todayTotalSleep);
			sleepInfo.setTodaySleepText(WearablesUtils.covertToReadableTime(todayTotalSleep));

			sleepInfo.setLastWeekTotalSleepAverage(
					(int) (Double.parseDouble((String) row.get("LAST_WEEK_SLEEP_AVG").getValue())));
			sleepInfo.setLastWeekTotalSleepAverageText(
					WearablesUtils.covertToReadableTime(sleepInfo.getLastWeekTotalSleepAverage()));

			int todayVsLastWeekSleepAvgPercenrage = 0;
			if (forwardMotionInfo.getLastWeekForwardMotionAverage() > 0) {
				todayVsLastWeekSleepAvgPercenrage = ((todayTotalSleep * 100)
						/ sleepInfo.getLastWeekTotalSleepAverage());
			} else {
				todayVsLastWeekSleepAvgPercenrage = todayTotalSleep > 0 ? 100 : 0;
			}
			sleepInfo.setTodayVsLastWeekSleepAvgPercentage(todayVsLastWeekSleepAvgPercenrage);

			sleepInfo.setPreviousDayTotalSleep(Integer.parseInt((String) row.get("PREV_DAY_SLEEP").getValue()));
			sleepInfo.setPreviousDayTotalSleepText(
					WearablesUtils.covertToReadableTime(sleepInfo.getPreviousDayTotalSleep()));

			int prevDayTSVsLastWeekTSAvgPercent = 0;
			if (forwardMotionInfo.getLastWeekForwardMotionAverage() > 0) {
				prevDayTSVsLastWeekTSAvgPercent = ((sleepInfo.getPreviousDayTotalSleep() * 100)
						/ sleepInfo.getLastWeekTotalSleepAverage());
			} else {
				prevDayTSVsLastWeekTSAvgPercent = sleepInfo.getPreviousDayTotalSleep() > 0 ? 100 : 0;
			}
			sleepInfo.setPrevDayTSVsLastWeekTSAvgPercentage(prevDayTSVsLastWeekTSAvgPercent);

			sleepInfo.setTodayTotalSleepSofar(todayTotalSleep);
			sleepInfo.setTodayTotalSleepSofarText(
					WearablesUtils.covertToReadableTime(sleepInfo.getTodayTotalSleepSofar()));

			sleepInfo.setTodayTSSofarVsLastWeekTSAvgPercentage(todayVsLastWeekSleepAvgPercenrage);
			sleepInfo.setPrevDayTotalSleepAtThisTime(
					Integer.parseInt((String) row.get("PREV_DAY_TILL_NOW_SLEEP").getValue()));
			sleepInfo
					.setTodayTSSofarVsPrevDayTSAtThisTime(todayTotalSleep - sleepInfo.getPrevDayTotalSleepAtThisTime());
			response.setSleepInfo(sleepInfo);
		}

		String sql = SQLConstants.PET_GET_FORWARD_MOTION_GOAL_SETTING;
		int forwardMotionGoalSettingValue = selectForObject(sql, Integer.class, petId);
		int todayForwardMotion = forwardMotionInfo.getTodayForwardMotionSofar();

		fmGoalSetInfo.setForwardMotionGoalSetting(forwardMotionGoalSettingValue);
		fmGoalSetInfo.setForwardMotionGoalSetText(WearablesUtils.covertToReadableTime(forwardMotionGoalSettingValue));

		fmGoalSetInfo.setTodayForwardMotionSofar(todayForwardMotion);
		fmGoalSetInfo.setTodayFMSofarText(WearablesUtils.covertToReadableTime(todayForwardMotion));

		fmGoalSetInfo.setTodayForwardMotionVsGoalSettingPercentage(
				(todayForwardMotion * 100) / forwardMotionGoalSettingValue);

		int goalToAchieve = forwardMotionGoalSettingValue - todayForwardMotion;
		int tobeAchieved = 0, overAchieved = 0;

		if (goalToAchieve > 0) {
			tobeAchieved = goalToAchieve;
		} else if (goalToAchieve < 0) {
			overAchieved = todayForwardMotion - forwardMotionGoalSettingValue;
		}

		fmGoalSetInfo.setTobeAchieved(tobeAchieved);
		fmGoalSetInfo.setTobeAchievedText(WearablesUtils.covertToReadableTime(tobeAchieved));

		fmGoalSetInfo.setOverAchieved(overAchieved);
		fmGoalSetInfo.setOverAchievedText(WearablesUtils.covertToReadableTime(overAchieved));

		response.setFmGoalSetting(fmGoalSetInfo);

		return response;
	}

	@Override
	public List<BehaviorHistory> getBehaviorHistoryByType(int petId, String behaviorType,
			BehaviorHistoryRequest behaviorHistoryRequest) throws ServiceExecutionException {
		String query = MessageFormat.format(SQLConstants.BIG_QEURY_GET_BEHAVIOR_HISTORY, Integer.toString(petId),
				behaviorHistoryRequest.getFromDate(), behaviorHistoryRequest.getToDate());
		LOGGER.info("===============getBehaviorHistoryByType================ " + query);
		TableResult tableResult = BigQueryServiceUtil.queryBigQueryTable(query);
		LOGGER.info("Total Rows == getBehaviorHistoryByType " + tableResult.getTotalRows());
		List<BehaviorHistory> behaviorHistory = new ArrayList<BehaviorHistory>();
		for (FieldValueList row : tableResult.iterateAll()) {
			BehaviorHistory history = new BehaviorHistory();
			history.setPetId(Integer.parseInt((String) row.get("PET_ID").getValue()));
			history.setActivityDay((String) row.get("ACTIVITY_DAY").getValue());
			if ("1".equalsIgnoreCase(behaviorType)) {
				history.setWalking(Integer.parseInt((String) row.get("WALKING").getValue()));
				history.setWalkingText(WearablesUtils.covertToReadableTime(history.getWalking()));
				history.setRunning(Integer.parseInt((String) row.get("RUNNING").getValue()));
				history.setRunningText(WearablesUtils.covertToReadableTime(history.getRunning()));
				history.setForwardMotion(Integer.parseInt((String) row.get("FORWARD_MOTION").getValue()));
				history.setForwardMotionText(WearablesUtils.covertToReadableTime(history.getForwardMotion()));
			}
			if ("2".equalsIgnoreCase(behaviorType)) {
				history.setNightSleep(Integer.parseInt((String) row.get("NIGHT_SLEEP").getValue()));
				history.setNightSleepText(WearablesUtils.covertToReadableTime(history.getNightSleep()));
				history.setDaySleep(Integer.parseInt((String) row.get("DAY_SLEEP").getValue()));
				history.setDaySleepText(WearablesUtils.covertToReadableTime(history.getDaySleep()));
				history.setTotalSleep(Integer.parseInt((String) row.get("TOTAL_SLEEP").getValue()));
				history.setTotalSleepText(WearablesUtils.covertToReadableTime(history.getTotalSleep()));
			}
			behaviorHistory.add(history);
		}
		return behaviorHistory;
	}

	@Override
	public BehaviorForwardMotionGoalSettingResponse getForwardMotionGoalSetting(int petId)
			throws ServiceExecutionException {
		BehaviorForwardMotionGoalSettingResponse response = new BehaviorForwardMotionGoalSettingResponse();
		try {
			String query = MessageFormat.format(SQLConstants.BIG_QEURY_GET_BEHAVIOR_VISULAZATION,
					Integer.toString(petId));
			LOGGER.info("===============getForwardMotionGoalSetting================ " + query);
			TableResult tableResult = BigQueryServiceUtil.queryBigQueryTable(query);
			LOGGER.info("Total Rows == getForwardMotionGoalSetting " + tableResult.getTotalRows());
			for (FieldValueList row : tableResult.iterateAll()) {
				Integer todayWalking = Integer.parseInt((String) row.get("TODAY_WALKING").getValue());
				Integer todayRunning = Integer.parseInt((String) row.get("TODAY_RUNNING").getValue());
				Integer todayForwardMotion = todayWalking + todayRunning;
				response.setTodayForwardMotion(todayForwardMotion);
			}

			String sql = SQLConstants.PET_GET_FORWARD_MOTION_GOAL_SETTING;
			int forwardMotionGoalSettingValue = selectForObject(sql, Integer.class, petId);
			response.setForwardMotionGoalSetting(forwardMotionGoalSettingValue);
			response.setTodayForwardMotionPercentage(
					(response.getTodayForwardMotion() * 100) / response.getForwardMotionGoalSetting());

		} catch (Exception e) {
			LOGGER.error("error while executing getExternalPetInfo ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return response;
	}

	public PetLegLengthResponse getPetLegLengthHistoryById(int petId, int unitId) throws ServiceExecutionException {
		PetLegLengthResponse response = new PetLegLengthResponse();
		List<PetLegLength> legLengthHistory = new ArrayList<PetLegLength>();
		LOGGER.debug("getPetLegLengthHistoryById called");
		try {
			String sql = SQLConstants.PET_GET_LEG_LENGTH_HISTORY_BY_ID;
			jdbcTemplate.query(sql, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetLegLength legLength = new PetLegLength();
					legLength.setLegLengthId(rs.getInt("PET_LEG_LENGTH_ID"));
					legLength.setLegLength(rs.getDouble("LEG_LENGTH"));
					legLength.setCapturedOn(rs.getTimestamp("RECORDED_DATE") != null
							? rs.getTimestamp("RECORDED_DATE").toLocalDateTime()
							: null);
					legLengthHistory.add(legLength);
				}
			}, petId, unitId);

			response.setLegLengthHistory(legLengthHistory);
		} catch (Exception e) {
			LOGGER.error("error while executing getPetLegLengthHistoryById ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return response;
	}

	private void invokeAFEngine(int petId) {
		LOGGER.info("Started executing invokeAFEngine ");
		try {
			if (petId == 0) {
				LOGGER.info("petId is not available in Env Variables");
			}
			WebClient webClient = WebClient.builder().baseUrl(BASE_AF_URL + "/afRunForOnePet?petId=" + petId).build();
			LOGGER.info(BASE_AF_URL + "/afRunForOnePet?petId=" + petId);
			webClient.method(HttpMethod.GET).contentType(MediaType.APPLICATION_JSON).retrieve()
					.onStatus(HttpStatus::is4xxClientError, response -> {
						LOGGER.error("AF Bad Request");
						throw new RuntimeException("AF Bad Request");
					}).onStatus(HttpStatus::is5xxServerError, response -> {
						LOGGER.error("AF Job Failed to process the data");
						throw new RuntimeException("AF Job Failed to process the data");
					}).bodyToMono(String.class).subscribe(response -> {
						LOGGER.error("onSuccess response is ::" + response);
					});
		} catch (Exception e) {
			LOGGER.error("Exception in invokeAFEngine method " + e.getMessage());
		}
		LOGGER.info("Completed executing invokeAFEngine ");
	}

	@Override
	public void updateIBW(int petId, int ibwId, UpdatePetIBWRequest updatePetIBWRequest)
			throws ServiceExecutionException {
		int userId = authentication.getAuthUserDetails().getUserId();
		LOGGER.debug("updateIBW called " + ibwId);
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_pet_id", petId);
		inputParams.put("p_ibw_id", ibwId);
		inputParams.put("p_ibw", updatePetIBWRequest.getCorrectedIBW());
		inputParams.put("p_ibw_unit", updatePetIBWRequest.getCorrectedIBWUnit());
		inputParams.put("p_comment", updatePetIBWRequest.getComment());
		inputParams.put("p_modified_by", authentication.getAuthUserDetails().getUserId());
		try {
			LOGGER.info("updateIBW inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.PET_UPDATE_IBW, inputParams);
			LOGGER.info("updateIBW outParams are {}", outParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			String currentDate = LocalDate.now().format(dateFormatter);
			if (StringUtils.isEmpty(errorMsg)) {
				// Added by deepak on 01-02-24 to Trigger AF engine on IBW Change.
				if (updatePetIBWRequest.getDate().equals(currentDate) && updatePetIBWRequest.isLatest()) {
					this.invokeAFEngine(petId);
				}
				LOGGER.info("Pet IBW updated successfully");
			} else {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (Exception e) {
			LOGGER.error("error while executing updateIBW ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		LOGGER.debug("updateIBW end " + userId);
	}

	@Override
	public void saveBulkExternalPetIdsToStaging(List<BulkUploadExternalPetIdInfo> list, String fileName, Integer userId)
			throws ServiceExecutionException {
		LOGGER.debug("saveBulkExternalPetIdsToStaging called");
		LOGGER.debug("saveBulkExternalPetIdsToStaging batch size " + list.size());

		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_user_id", userId);
			inputParams.put("p_ext_pet_ids_json", mapper.writeValueAsString(list));
			inputParams.put("p_file_name", fileName);

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.PET_SAVE_BULK_EXTPETS_STAGING,
					inputParams);
			LOGGER.info("outParams are {}", outParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			LOGGER.debug("saveBulkExternalPetIdsToStaging statusFlag " + statusFlag);
			LOGGER.debug("saveBulkExternalPetIdsToStaging errorMsg " + errorMsg);

			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.debug("saveBulkExternalPetIdsToStaging completed successfully. ");
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException | JsonProcessingException e) {
			LOGGER.error("error while executing saveBulkExternalPetIdsToStaging ", e);
		}
	}

	@Override
	public void saveBulkUploadExtPetIds(BulkExtPetIdsUploadRequest request) throws ServiceExecutionException {
		LOGGER.debug("saveBulkUploadExtPetIds called");
		LOGGER.debug("saveBulkUploadExtPetIds userId = " + request.getUserId());
		LOGGER.debug("saveBulkUploadExtPetIds selectedIds = " + request.getStagingId());
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_user_id", request.getUserId());
		inputParams.put("p_selected_staging_ids", request.getStagingId());
		try {

			Map<String, Object> outParams = callStoredProcedure(SQLConstants.PET_EXTERNAL_IDS_BULK_SAVE, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("External Pet Ids updated successfully, Staging Ids ", request.getStagingId());
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"saveBulkUploadExtPetIds service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList(
									new WearablesError(WearablesErrorCode.WEARABLES_INTERNAL_SRVR_ERROR, errorMsg)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
			LOGGER.debug("saveBulkUploadExtPetIds statusFlag " + statusFlag);
			LOGGER.debug("saveBulkUploadExtPetIds errorMsg " + errorMsg);
		} catch (SQLException e) {
			LOGGER.error("error while executing saveBulkUploadExtPetIds ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public int getBulkUploadExtPetIdsListCount(BaseFilter filter) {
		int totalCount = NumberUtils.INTEGER_ZERO;
		LOGGER.debug("getBulkUploadExtPetIdsListCount called");
		try {
			totalCount = selectForObject(SQLConstants.PET_EXT_IDS_BULK_STAGING_LIST_COUNT, Integer.class,
					filter.getUserId());
		} catch (Exception e) {
			LOGGER.error("error while fetching getBulkUploadExtPetIdsListCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return totalCount;
	}

	@Override
	public List<BulkUploadExternalPetIdInfo> getBulkUploadExtPetIdsList(BaseFilter filter) {
		List<BulkUploadExternalPetIdInfo> extPetIdInfoList = new ArrayList<>();
		LOGGER.debug("getBulkUploadExtPetIdsList called");
		try {
			jdbcTemplate.query(SQLConstants.PET_EXT_IDS_BULK_STAGING_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					BulkUploadExternalPetIdInfo extPetIdInfo = new BulkUploadExternalPetIdInfo();
					extPetIdInfo.setStagingId(rs.getInt("ID"));
					extPetIdInfo.setStudyId(rs.getString("STUDY_ID"));
					extPetIdInfo.setStudyName(rs.getString("STUDY_NAME"));
					extPetIdInfo.setPetId(rs.getInt("PET_ID"));
					extPetIdInfo.setPetName(rs.getString("PET_NAME"));
					extPetIdInfo.setExtPetId(rs.getString("EXT_PET_ID"));
					extPetIdInfo.setStudyLocation(rs.getString("STUDY_LOCATION"));
					extPetIdInfo.setExceptionMsg(rs.getString("EXCEPTION_TYPE"));

					extPetIdInfoList.add(extPetIdInfo);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getUserId());

		} catch (Exception e) {
			LOGGER.error("error while fetching getBulkUploadExtPetIdsList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return extPetIdInfoList;
	}

	@Override
	public void saveManualEnterForRecommendation(ManualRecommendationRequest manualRecommendationRequest) {
		LOGGER.debug("Started saveManualEnterForRecommendation");
		int userId = authentication.getAuthUserDetails().getUserId();
		LOGGER.debug(" manualRecommendationRequest " + manualRecommendationRequest);
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_pet_id", manualRecommendationRequest.getPetId());
		inputParams.put("p_study_id", manualRecommendationRequest.getStudyId());
		inputParams.put("p_user_id", userId);
		inputParams.put("p_activity_factor_id", manualRecommendationRequest.getActivityFactorId());
		inputParams.put("p_manual_value", manualRecommendationRequest.getValue());
		inputParams.put("p_unit", manualRecommendationRequest.getUnit());
		try {
			LOGGER.info("manualRecommendation  inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.PET_UPDATE_MANUAL_RECOMMENDATION,
					inputParams);
			LOGGER.info("manualRecommendation outParams are {}", outParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			if (StringUtils.isEmpty(errorMsg)) {
				LOGGER.info("Manual Recommendation updated successfully");
			} else {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (Exception e) {
			LOGGER.error("error while executing saveManualEnterForRecommendation ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		LOGGER.debug("Completed saveManualEnterForRecommendation");
	}

	@Override
	public HashMap<String, String> getPetThresholdDetails(int afId, int userId) {
		LOGGER.debug("getPetThresholdDetails called");
		HashMap<String, String> petMap = new HashMap<>(10);
		try {
			jdbcTemplate.query(SQLConstants.GET_PET_THRESHOLD_DETAILS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					petMap.put("afId", rs.getString("ACTIVITY_AF_ID"));
					petMap.put("studyId", rs.getString("STUDY_ID"));
					petMap.put("studyName", rs.getString("STUDY_NAME"));
					petMap.put("petId", rs.getString("PET_ID"));
					petMap.put("petName", rs.getString("PET_NAME"));
					petMap.put("afDate", rs.getString("ACTIVITY_FACTOR_CALCULATED_DATE"));
					petMap.put("previousAdjFactor", rs.getString("PREVIOUS_ADJ_FACTOR"));
					petMap.put("currentAdjFactor", rs.getString("CURRENT_ADJUSTMENT_FACTOR"));
					petMap.put("previousBodyWeight", rs.getString("PREVIOUS_BODY_WEIGHT"));
					petMap.put("currentBodyWeight", rs.getString("CURRENT_BODY_WEIGHT"));
					petMap.put("currentRecommendationGms", rs.getString("CURRENT_RECOMMENDATION_GMS"));
					petMap.put("prevRecommendationGms", rs.getString("PREV_RECOMMENDATION_GMS"));
					petMap.put("prevManualRecommendationGms", rs.getString("PREV_MANUAL_RECOMMENDATION_GMS"));
				}
			}, afId);

		} catch (Exception e) {
			LOGGER.error("error while fetching getPetThresholdDetails", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petMap;
	}
}
