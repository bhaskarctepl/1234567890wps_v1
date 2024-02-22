package com.hillspet.wearables.dao.petparent.impl;

import java.math.BigDecimal;
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
import org.springframework.beans.BeanUtils;
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
import com.hillspet.wearables.common.utils.GCPClientUtil;
import com.hillspet.wearables.dao.BaseDaoImpl;
import com.hillspet.wearables.dao.petparent.PetParentDao;
import com.hillspet.wearables.dto.Address;
import com.hillspet.wearables.dto.PetParentDTO;
import com.hillspet.wearables.dto.PetParentListDTO;
import com.hillspet.wearables.dto.PetsAssociatedDTO;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.request.PetParentRequest;
import com.hillspet.wearables.request.PetParentValidateEmailRequest;
import com.hillspet.wearables.response.PetParentAddressResponse;
import com.hillspet.wearables.security.Authentication;

@Repository
public class PetParentDaoImpl extends BaseDaoImpl implements PetParentDao {

	private static final Logger LOGGER = LogManager.getLogger(PetParentDaoImpl.class);
	@Autowired
	GCPClientUtil gcpClientUtil;

	@Autowired
	private Authentication authentication;

	@Autowired
	private ObjectMapper mapper;

	@Override
	public PetParentDTO addPetParent(PetParentRequest petParentRequest) throws ServiceExecutionException {
		PetParentDTO petParentDTO = new PetParentDTO();
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_pet_parent_first_name", petParentRequest.getPetParentFirstName());
			inputParams.put("p_pet_parent_last_name", petParentRequest.getPetParentLastName());
			inputParams.put("p_email", petParentRequest.getEmail());
			inputParams.put("p_phone_number", petParentRequest.getPhoneNumber());
			inputParams.put("p_pets_associated",
					StringUtils.join(petParentRequest.getPetsAssociated(), StringLiterals.COMMA.getCode()));
			inputParams.put("p_created_by", petParentRequest.getUserId());
			inputParams.put("p_status", petParentRequest.getStatus());

			inputParams.put("p_secondary_email", petParentRequest.getSecondaryEmail());
			inputParams.put("p_notify_secondary_email", petParentRequest.getIsNotifySecondaryEmail());

			inputParams.put("p_residential_address_json",
					mapper.writeValueAsString(petParentRequest.getResidentialAddress()));
			inputParams.put("p_shipping_address_json",
					mapper.writeValueAsString(petParentRequest.getShippingAddress()));
			inputParams.put("p_is_ship_addr_same", petParentRequest.getIsShipAddrSameAsResdntlAddr());
			inputParams.put("p_preferred_food_unit_id", petParentRequest.getPreferredFoodUnitId());
			inputParams.put("p_preferred_weight_unit_id", petParentRequest.getPreferredWeightUnitId());

			LOGGER.info("addPetParent inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.PET_PARENT_INSERT, inputParams);
			LOGGER.info("addPetParent outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");

			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				// getting the inserted flag value
				Integer petParentId = (int) outParams.get("last_insert_id");
				LOGGER.info("Pet Parent has been created successfully, Pet Parent id is ", petParentId);
				petParentRequest.setPetParentId(petParentId);
				BeanUtils.copyProperties(petParentRequest, petParentDTO);
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException("AddPetParent service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.PET_PARENT_EMAIL_ALREADY_EXISTS,
									petParentRequest.getEmail())));
				} else if (statusFlag == -3) {
					throw new ServiceExecutionException("AddPetParent service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.PET_PARENT_EMAIL_ALREADY_EXISTS,
									petParentRequest.getSecondaryEmail())));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException | JsonProcessingException e) {
			LOGGER.error("error while executing AddPetParent ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petParentDTO;
	}

	@Override
	public PetParentDTO updatePetParent(PetParentRequest petParentRequest) throws ServiceExecutionException {
		PetParentDTO petParentDTO = new PetParentDTO();
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_pet_parent_id", petParentRequest.getPetParentId());
			inputParams.put("p_pet_parent_first_name", petParentRequest.getPetParentFirstName());
			inputParams.put("p_pet_parent_last_name", petParentRequest.getPetParentLastName());
			inputParams.put("p_email", petParentRequest.getEmail());
			inputParams.put("p_phone_number", petParentRequest.getPhoneNumber());
			inputParams.put("p_pets_associated",
					StringUtils.join(petParentRequest.getPetsAssociated(), StringLiterals.COMMA.getCode()));
			inputParams.put("p_status", petParentRequest.getStatus());
			inputParams.put("p_modified_by", petParentRequest.getUserId());

			inputParams.put("p_secondary_email", petParentRequest.getSecondaryEmail());
			inputParams.put("p_notify_secondary_email", petParentRequest.getIsNotifySecondaryEmail());

			inputParams.put("p_residential_address_json",
					mapper.writeValueAsString(petParentRequest.getResidentialAddress()));
			inputParams.put("p_shipping_address_json",
					mapper.writeValueAsString(petParentRequest.getShippingAddress()));
			inputParams.put("p_is_ship_addr_same", petParentRequest.getIsShipAddrSameAsResdntlAddr());
			inputParams.put("p_preferred_food_unit_id", petParentRequest.getPreferredFoodUnitId());
			inputParams.put("p_preferred_weight_unit_id", petParentRequest.getPreferredWeightUnitId());

			LOGGER.info("updatePetParent inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.PET_PARENT_UPDATE, inputParams);
			LOGGER.info("updatePetParent outParams are {}", outParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isNotEmpty(errorMsg) || statusFlag < NumberUtils.INTEGER_ONE) {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"updatePetParent service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.PET_PARENT_EMAIL_ALREADY_EXISTS,
									petParentRequest.getEmail())));
				} else if (statusFlag == -3) {
					throw new ServiceExecutionException(
							"updatePetParent service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.PET_PARENT_EMAIL_ALREADY_EXISTS,
									petParentRequest.getSecondaryEmail())));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
			BeanUtils.copyProperties(petParentRequest, petParentDTO);
		} catch (SQLException | JsonProcessingException e) {
			LOGGER.error("error while executing updatePetParent ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petParentDTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PetParentDTO getPetParentById(int petParentId) throws ServiceExecutionException {
		final PetParentDTO petParentDto = new PetParentDTO();
		List<PetsAssociatedDTO> petsAssociated = new ArrayList<>();
		Address residentialAddress = new Address();
		Address shippingAddress = new Address();
		LOGGER.debug("getPetParentById called");
		int userId = authentication.getAuthUserDetails().getUserId();

		try {

			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("p_pet_parent_id", petParentId);
			inputParams.put("p_user_id", userId);

			Map<String, Object> simpleJdbcCallResult = callStoredProcedure(SQLConstants.PET_PARENT_GET_BY_ID,
					inputParams);
			Iterator<Entry<String, Object>> itr = simpleJdbcCallResult.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();

				if (key.equals(SQLConstants.RESULT_SET_1)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(parent -> {
						petParentDto.setPetParentId((Integer) parent.get("PET_PARENT_ID"));
						petParentDto.setPetParentName((String) parent.get("FULL_NAME"));
						petParentDto.setFirstName((String) parent.get("FIRST_NAME"));
						petParentDto.setLastName((String) parent.get("LAST_NAME"));
						petParentDto.setEmail((String) parent.get("EMAIL"));
						petParentDto.setPhoneNumber((String) parent.get("PHONE_NUMBER"));
						petParentDto.setSecondaryEmail((String) parent.get("SECONDARY_EMAIL"));
						petParentDto.setIsShipAddrSameAsResdntlAddr((Integer) parent.get("IS_SHIPPING_ADDR_SAME"));
						petParentDto.setPreferredFoodUnitId((Integer) parent.get("PREF_FOOD_REC_UNIT_ID"));
						petParentDto.setPreferredWeightUnitId((Integer) parent.get("PREF_WEIGHT_UNIT_ID"));

						if (parent.get("SECONDARY_EMAIL_NOTIF_FLAG") != null) {
							int isNotifySecondaryEmail = (Integer) parent.get("SECONDARY_EMAIL_NOTIF_FLAG");
							petParentDto.setIsNotifySecondaryEmail(isNotifySecondaryEmail == 1 ? true : false);
						}

						Boolean status = (Boolean) parent.get("PET_PARENT_STATUS");
						petParentDto.setStatus(status ? 1 : 0);
					});
				}
				if (key.equals(SQLConstants.RESULT_SET_2)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(pets -> {
						PetsAssociatedDTO associatedPet = new PetsAssociatedDTO();
						associatedPet.setPetId((Integer) pets.get("PET_ID"));
						associatedPet.setPetName((String) pets.get("PET_NAME"));
						String fileName = (String) pets.get("PHOTO_NAME");
						if (fileName != null && !fileName.trim().equals("")) {
							associatedPet.setPetPhotoUrl(
									gcpClientUtil.getDownloaFiledUrl(fileName, Constants.GCP_PETPHOTO_PATH));
						}

						associatedPet.setPetPhoto((String) pets.get("PHOTO_NAME"));
						associatedPet.setBreedName((String) pets.get("BREED_NAME"));
						BigDecimal weight = (BigDecimal) pets.get("PET_WEIGHT");
						if (weight != null) {
							associatedPet.setWeight(weight.toString());
						}

						associatedPet.setGender((String) pets.get("GENDER"));
						LocalDateTime birthday = (LocalDateTime) pets.get("BIRTHDAY");
						associatedPet.setDob(birthday != null ? birthday.toLocalDate() : null);
						associatedPet.setIsDobUnknown((Boolean) pets.get("IS_UNKNOWN"));
						associatedPet.setPetStatusId((Integer) pets.get("PET_STATUS_ID"));
						associatedPet.setPetStatus((String) pets.get("PET_STAUTS"));

						associatedPet.setIsNeutered((Boolean) pets.get("IS_NEUTERED"));
						associatedPet.setIsDeceased((Boolean) pets.get("IS_DECEASED"));
						/*
						 * associatedPet.setDeviceNumber((String)pets.get("DEVICE_NUMBER"));
						 * associatedPet.setDeviceType((String)pets.get("DEVICE_TYPE"));
						 * associatedPet.setDeviceModel((String) pets.get("DEVICE_MODEL"));
						 */
						if (pets.get("PET_STUDY_ID") != null) {
							associatedPet.setPetStudyId((Integer) pets.get("PET_STUDY_ID"));
						}

						petsAssociated.add(associatedPet);
					});
				}

				if (key.equals(SQLConstants.RESULT_SET_3)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(address -> {
						residentialAddress.setAddressId((Integer) address.get("PET_PARENT_ADDRESS_ID"));
						residentialAddress.setAddress1((String) address.get("ADDRESS_1"));
						residentialAddress.setAddress2((String) address.get("ADDRESS_2"));
						residentialAddress.setCity((String) address.get("CITY"));
						residentialAddress.setState((String) address.get("STATE"));
						residentialAddress.setCountry((String) address.get("COUNTRY"));
						residentialAddress.setZipCode((String) address.get("ZIP_CODE"));
						residentialAddress.setTimeZone((String) address.get("TIME_ZONE"));
						residentialAddress.setTimeZoneId((Integer) address.get("TIMEZONE_ID"));
					});
				}

				if (key.equals(SQLConstants.RESULT_SET_4)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(address -> {
						shippingAddress.setAddressId((Integer) address.get("PET_PARENT_ADDRESS_ID"));
						shippingAddress.setAddress1((String) address.get("ADDRESS_1"));
						shippingAddress.setAddress2((String) address.get("ADDRESS_2"));
						shippingAddress.setCity((String) address.get("CITY"));
						shippingAddress.setState((String) address.get("STATE"));
						shippingAddress.setCountry((String) address.get("COUNTRY"));
						shippingAddress.setZipCode((String) address.get("ZIP_CODE"));
						shippingAddress.setTimeZone((String) address.get("TIME_ZONE"));
						shippingAddress.setTimeZoneId((Integer) address.get("TIMEZONE_ID"));
					});
				}
			}
			petParentDto.setPetsAssociated(petsAssociated);
			petParentDto.setResidentialAddress(residentialAddress);
			petParentDto.setShippingAddress(shippingAddress);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetParents", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petParentDto;
	}

	@Override
	public String getPetParentListCount(BaseFilter filter) throws ServiceExecutionException {
		String totalCount;
		LOGGER.debug("getPetParentListCount called");
		try {
			totalCount = selectForObject(SQLConstants.PET_PARENT_GET_LIST_COUNT, String.class, filter.getSearchText(),
					filter.getStatusId(), filter.getUserId());
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetParentListCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return totalCount;
	}

	@Override
	public List<PetParentListDTO> getPetParentList(BaseFilter filter) throws ServiceExecutionException {
		List<PetParentListDTO> petParentList = new ArrayList<>();
		LOGGER.debug("getPetParentList called");
		try {
			jdbcTemplate.query(SQLConstants.PET_PARENT_GET_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetParentListDTO petParentListDTO = new PetParentListDTO();
					petParentListDTO.setSlNumber(rs.getInt("slNo"));
					petParentListDTO.setPetParentId(rs.getInt("PET_PARENT_ID"));
					petParentListDTO.setPetParentName(rs.getString("FULL_NAME"));
					petParentListDTO.setPetParentFirstName(rs.getString("FIRST_NAME"));
					petParentListDTO
							.setPetParentLastName(rs.getString("LAST_NAME") != null ? rs.getString("LAST_NAME") : "");
					petParentListDTO.setEmail(rs.getString("EMAIL"));
					petParentListDTO.setStudyNames(rs.getString("STUDY_NAMES"));
					petParentListDTO.setPetNames(rs.getString("PET_NAMES"));
					petParentListDTO.setCreatedDate(rs.getTimestamp("CREATED_DATE").toLocalDateTime());
					petParentListDTO.setIsActive(rs.getBoolean("IS_ACTIVE"));
					petParentListDTO.setOnStudyPetExist(rs.getBoolean("PET_PARENT_ON_STUDY_PET_EXIST"));
					petParentList.add(petParentListDTO);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getStatusId(), filter.getUserId());

		} catch (Exception e) {
			LOGGER.error("error while fetching getPetParentList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petParentList;
	}

	@Override
	public List<PetParentListDTO> getPetParents() throws ServiceExecutionException {
		List<PetParentListDTO> petParentList = new ArrayList<>();
		LOGGER.debug("getPetParents called");
		int userId = authentication.getAuthUserDetails().getUserId();
		try {
			jdbcTemplate.query(SQLConstants.PET_PARENT_GET_ALL, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetParentListDTO petParentListDTO = new PetParentListDTO();
					petParentListDTO.setPetParentId(rs.getInt("PET_PARENT_ID"));
					petParentListDTO.setPetParentName(rs.getString("FULL_NAME"));
					petParentListDTO.setEmail(rs.getString("EMAIL"));
					petParentListDTO.setPhoneNumber(rs.getString("PHONE_NUMBER"));
					petParentListDTO.setPetParentFirstName(rs.getString("FIRST_NAME"));
					petParentListDTO.setPetParentLastName(rs.getString("LAST_NAME"));
					petParentListDTO.setSecondaryEmail(rs.getString("SECONDARY_EMAIL"));
					petParentListDTO.setPreferredFoodUnitId(rs.getInt("PREF_FOOD_REC_UNIT_ID"));
					petParentListDTO.setPreferredWeightUnitId(rs.getInt("PREF_WEIGHT_UNIT_ID"));

					petParentListDTO.setIsShipAddrSameAsResdntlAddr(rs.getInt("IS_SHIPPING_ADDR_SAME"));
					petParentListDTO.setResidentialAddressString(rs.getString("RESIDENTIAL_ADDRESS"));
					petParentListDTO.setShippingAddressString(rs.getString("SHIPPING_ADDRESS"));

					/*
					 * try { String restAddrStr = (rs.getString("RESIDENTIAL_ADDRESS") == null) ?
					 * "{}" : rs.getString("RESIDENTIAL_ADDRESS"); AddressRequest residentialAddress
					 * = objectMapper.readValue(restAddrStr, AddressRequest.class);
					 * petParentListDTO.setResidentialAddress(residentialAddress);
					 * 
					 * String shipAddrStr = (rs.getString("SHIPPING_ADDRESS") == null) ? "{}" :
					 * rs.getString("SHIPPING_ADDRESS");
					 * 
					 * AddressRequest shippingAddress = objectMapper.readValue(shipAddrStr,
					 * AddressRequest.class); petParentListDTO.setShippingAddress(shippingAddress);
					 * } catch (JsonProcessingException | SQLException e) { // TODO Auto-generated
					 * catch block e.printStackTrace(); }
					 */

					petParentList.add(petParentListDTO);
				}
			}, userId);

		} catch (Exception e) {
			LOGGER.error("error while fetching getPetParents", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petParentList;
	}

	@Override
	public void deletePetParent(int petParentId, int modifiedBy) throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_pet_parent_id", petParentId);
		inputParams.put("p_modified_by", modifiedBy);
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.PET_PARENT_DELETE, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			if (StringUtils.isNotEmpty(errorMsg) || (int) outParams.get("out_flag") < NumberUtils.INTEGER_ONE) {
				throw new ServiceExecutionException(errorMsg, Status.BAD_REQUEST.getStatusCode(),
						Arrays.asList(new WearablesError(WearablesErrorCode.ALREADY_BEING_REFERENCED, errorMsg)));
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing deletePetParent ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public Map<String, Object> validatePetParentEmail(PetParentValidateEmailRequest petParentRequest)
			throws ServiceExecutionException {
		Map<String, Object> response = new HashMap<>();
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_email", petParentRequest.getEmail());
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.PET_PARENT_VALIDATE_EMAIL, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (statusFlag == -2 || statusFlag == -3) {
				response.put("msg", errorMsg);
				response.put("isExist", true);
			} else {
				response.put("isExist", false);
			}
			return response;
		} catch (SQLException e) {
			LOGGER.error("error while executing validatePetParentEmail ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public PetParentAddressResponse getPetParentAddressHistoryById(int petParentId) throws ServiceExecutionException {
		PetParentAddressResponse response = new PetParentAddressResponse();
		List<Address> residentialAddressList = new ArrayList<Address>();
		List<Address> shippingAddressList = new ArrayList<Address>();
		LOGGER.debug("getPetParentAddressHistoryById called");
		int userId = authentication.getAuthUserDetails().getUserId();
		try {

			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("p_pet_parent_id", petParentId);

			Map<String, Object> simpleJdbcCallResult = callStoredProcedure(SQLConstants.PET_PARENT_GET_ADDRESSES_BY_ID,
					inputParams);
			Iterator<Entry<String, Object>> itr = simpleJdbcCallResult.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();

				if (key.equals(SQLConstants.RESULT_SET_1)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(address -> {
						Address residentialAddress = new Address();
						residentialAddress.setAddressId((Integer) address.get("PET_PARENT_ADDRESS_ID"));
						residentialAddress.setAddress1((String) address.get("ADDRESS_1"));
						residentialAddress.setAddress2((String) address.get("ADDRESS_2"));
						residentialAddress.setCity((String) address.get("CITY"));
						residentialAddress.setState((String) address.get("STATE"));
						residentialAddress.setCountry((String) address.get("COUNTRY"));
						residentialAddress.setZipCode((String) address.get("ZIP_CODE"));
						residentialAddress.setTimeZone((String) address.get("TIME_ZONE"));
						residentialAddress.setTimeZoneId((Integer) address.get("TIMEZONE_ID"));

						LocalDateTime fromDate = (LocalDateTime) address.get("FROM_DATE");
						residentialAddress.setDateFrom(fromDate != null ? fromDate : null);

						LocalDateTime toDate = (LocalDateTime) address.get("TO_DATE");
						residentialAddress.setDateTo(toDate != null ? toDate : null);

						residentialAddressList.add(residentialAddress);
					});
				}

				if (key.equals(SQLConstants.RESULT_SET_2)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(address -> {
						Address shippingAddress = new Address();
						shippingAddress.setAddressId((Integer) address.get("PET_PARENT_ADDRESS_ID"));
						shippingAddress.setAddress1((String) address.get("ADDRESS_1"));
						shippingAddress.setAddress2((String) address.get("ADDRESS_2"));
						shippingAddress.setCity((String) address.get("CITY"));
						shippingAddress.setState((String) address.get("STATE"));
						shippingAddress.setCountry((String) address.get("COUNTRY"));
						shippingAddress.setZipCode((String) address.get("ZIP_CODE"));
						shippingAddress.setTimeZone((String) address.get("TIME_ZONE"));
						shippingAddress.setTimeZoneId((Integer) address.get("TIMEZONE_ID"));

						LocalDateTime fromDate = (LocalDateTime) address.get("FROM_DATE");
						shippingAddress.setDateFrom(fromDate != null ? fromDate : null);

						LocalDateTime toDate = (LocalDateTime) address.get("TO_DATE");
						shippingAddress.setDateTo(toDate != null ? toDate : null);

						shippingAddressList.add(shippingAddress);
					});
				}
			}
			response.setResidentialAddressList(residentialAddressList);
			response.setShippingAddressList(shippingAddressList);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetParentAddressHistoryById", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return response;
	}

	@Override
	public Map<String, Object> validateUnAssignedPetAddress(int petId, int petParentId)
			throws ServiceExecutionException {
		Map<String, Object> response = new HashMap<>();
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_pet_id", petId);
			inputParams.put("p_pet_parent_id", petParentId);
			LOGGER.info("validateUnAssignedPetAddress inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.PET_PARENT_VALIDATE_ADDRESS_UNASSIGNED_PET,
					inputParams);
			LOGGER.info("validateUnAssignedPetAddress outParams are {}", outParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (statusFlag == -1) {
				response.put("msg", errorMsg);
				response.put("unAssignPet", Boolean.FALSE);
			} else {
				response.put("unAssignPet", Boolean.TRUE);
			}
			return response;
		} catch (SQLException e) {
			LOGGER.error("error while executing validateUnAssignedPetAddress ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

}
