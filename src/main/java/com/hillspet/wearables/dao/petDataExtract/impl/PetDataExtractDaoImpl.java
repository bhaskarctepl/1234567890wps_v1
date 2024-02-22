package com.hillspet.wearables.dao.petDataExtract.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillspet.wearables.common.constants.Constants;
import com.hillspet.wearables.common.constants.SQLConstants;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.common.utils.GCPClientUtil;
import com.hillspet.wearables.dao.BaseDaoImpl;
import com.hillspet.wearables.dao.petDataExtract.PetDataExtractDao;
import com.hillspet.wearables.dto.PetDataExtractConfigListDTO;
import com.hillspet.wearables.dto.PetDataExtractStreamDTO;
import com.hillspet.wearables.dto.PetListDTO;
import com.hillspet.wearables.dto.filter.DuplicatePetsFilter;
import com.hillspet.wearables.request.PetDataExtractStreamsRequest;

@Repository
public class PetDataExtractDaoImpl extends BaseDaoImpl implements PetDataExtractDao {

	private static final Logger LOGGER = LogManager.getLogger(PetDataExtractDaoImpl.class);

	@Value("${gcp.env}")
	private String environment;

	@Autowired
	private GCPClientUtil gcpClientUtil;

	@Override
	public List<PetDataExtractStreamDTO> getPetDataExtractById(int petId) throws ServiceExecutionException {
		List<PetDataExtractStreamDTO> petDataExtractStreamDTOList = new ArrayList<>();
		// List<PetDataExtractStreamDTO> petDevices = new ArrayList<>();
		LOGGER.debug("getPetDataExtractById called");
		try {
			jdbcTemplate.query(SQLConstants.PET_GET_DATA_EXTRACT_LIST_BY_PET_ID, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetDataExtractStreamDTO petDataExtractStreamDTO = new PetDataExtractStreamDTO();
					petDataExtractStreamDTO.setStreamId(rs.getString("DATA_STREAM_ID"));
					petDataExtractStreamDTO.setStreamDeviceSeqNum(rs.getInt("STRM_DEVICE_SEQ_NUM"));
					petDataExtractStreamDTO.setPetId(rs.getInt("PET_ID"));
					petDataExtractStreamDTO.setPetName(rs.getString("PET_NAME"));
					petDataExtractStreamDTO.setStudyId(rs.getInt("STUDY_ID"));
					petDataExtractStreamDTO.setStudyName(rs.getString("STUDY_NAME"));
					petDataExtractStreamDTO.setAssetNumber(rs.getString("DEVICE_NUMBER"));
					petDataExtractStreamDTO.setStartDate(
							rs.getDate("START_DATE") == null ? null : rs.getDate("START_DATE").toLocalDate());
					
					String allEndDates = rs.getString("ALL_END_DATES");
					if(allEndDates.contains("$$$")) {
						petDataExtractStreamDTO.setEndDate(null);
					}else{
						petDataExtractStreamDTO
						.setEndDate(rs.getDate("END_DATE") == null ? null : rs.getDate("END_DATE").toLocalDate());
					}
					
					petDataExtractStreamDTO.setPetType(rs.getString("PET_TYPE"));
					
					petDataExtractStreamDTOList.add(petDataExtractStreamDTO);
				}
			}, petId);

		} catch (Exception e) {
			LOGGER.error("error while fetching getPetById", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petDataExtractStreamDTOList;
	}

	@Override
	public PetDataExtractStreamDTO addPetExtractStreams(PetDataExtractStreamsRequest petDataExtractStreamsRequest)
			throws ServiceExecutionException {
		PetDataExtractStreamDTO petDataExtractStreamDTO = new PetDataExtractStreamDTO();
		try {
			Map<String, Object> inputParams = new HashMap<>();

			inputParams.put("p_data_extract_list",
					new ObjectMapper().writeValueAsString(petDataExtractStreamsRequest.getExtractStreamList()));
			inputParams.put("p_pet_id", petDataExtractStreamsRequest.getPetId());
			inputParams.put("p_created_by", petDataExtractStreamsRequest.getUserId());

			Map<String, Object> outParams = callStoredProcedure(SQLConstants.PET_DATA_EXTRACT_INSERT, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			BeanUtils.copyProperties(petDataExtractStreamsRequest, petDataExtractStreamDTO);
		} catch (SQLException | JsonProcessingException e) {
			LOGGER.error("error while executing addPetExtractStreams ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petDataExtractStreamDTO;
	}

	@Override
	public String getPetsCount(DuplicatePetsFilter filter) throws ServiceExecutionException {
		String totalCount = "";
		LOGGER.debug("getPetsCount called");
		try {
			totalCount = selectForObject(SQLConstants.FN_PET_GET_DETAILS_FOR_DATA_EXTRACT, String.class,
					filter.getSearchText(), filter.getPetName(), filter.getGender(), filter.getDateOfBirth(),
					filter.getBreedId(), filter.getUserId(), filter.getRoleTypeId());
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetsCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return totalCount;
	}

	@Override
	public List<PetListDTO> getPets(DuplicatePetsFilter filter) throws ServiceExecutionException {
		List<PetListDTO> petList = new ArrayList<>();
		LOGGER.debug("getPets called");
		try {
			jdbcTemplate.query(SQLConstants.PET_GET_DETAILS_FOR_DATA_EXTRACT, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetListDTO petListDTO = new PetListDTO();
					petListDTO.setSlNumber(rs.getInt("slNo"));
					petListDTO.setPetId(rs.getInt("PET_ID"));
					petListDTO.setPetName(rs.getString("PET_NAME"));
					petListDTO.setPetParentName(rs.getString("PET_PARENT_NAME"));
					petListDTO.setPetPhoto(rs.getString("PHOTO_NAME"));
					String fileName = rs.getString("PHOTO_NAME");
					if (fileName != null && !fileName.trim().equals("")) {
						petListDTO.setPetPhotoUrl(
								gcpClientUtil.getDownloaFiledUrl(fileName, Constants.GCP_PETPHOTO_PATH));
					}

					petListDTO.setGender(rs.getString("GENDER"));
					petListDTO.setDateOfBirth(
							rs.getDate("BIRTHDAY") == null ? null : rs.getDate("BIRTHDAY").toLocalDate());
					petListDTO.setBreedName(rs.getString("BREED_NAME"));

					petList.add(petListDTO);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(),
					filter.getSearchText().trim(), filter.getPetName(), filter.getGender(), filter.getDateOfBirth(),
					filter.getBreedId(), filter.getUserId(), filter.getRoleTypeId());

		} catch (Exception e) {
			LOGGER.error("error while fetching getPets", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petList;
	}

	@Override
	public List<PetDataExtractConfigListDTO> getPetDataExtractConfigList(DuplicatePetsFilter filter)
			throws ServiceExecutionException {
		List<PetDataExtractConfigListDTO> petDataExtractConfigList = new ArrayList<>();
		LOGGER.debug("getPetDataExtractConfigList called");
		try {
			jdbcTemplate.query(SQLConstants.PET_GET_DATA_EXTRACT_CONFIG_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetDataExtractConfigListDTO petDataExtractConfigListDTO = new PetDataExtractConfigListDTO();
					petDataExtractConfigListDTO.setPetId(rs.getInt("PET_ID"));
					petDataExtractConfigListDTO.setPetName(rs.getString("PET_NAME"));
					petDataExtractConfigListDTO.setCreatedBy(rs.getString("REQUESTED_BY"));
					petDataExtractConfigListDTO.setCreatedDate(rs.getDate("CREATED_DATE").toLocalDate());
					petDataExtractConfigList.add(petDataExtractConfigListDTO);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(),
					filter.getSearchText().trim(), filter.getPetName(), filter.getGender(), filter.getDateOfBirth(),
					filter.getBreedId(), filter.getUserId(), filter.getRoleTypeId());

		} catch (Exception e) {
			LOGGER.error("error while fetching getPetDataExtractConfigList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petDataExtractConfigList;
	}

	@Override
	public String getPetDataExtractConfigListCount(DuplicatePetsFilter filter) throws ServiceExecutionException {
		String totalCount = "";
		LOGGER.debug("getPetsCount called");
		try {
			totalCount = selectForObject(SQLConstants.FN_PET_GET_DATA_EXTRACT_CONFIG_LIST, String.class,
					filter.getSearchText(), filter.getPetName(), filter.getGender(), filter.getDateOfBirth(),
					filter.getBreedId(), filter.getUserId(), filter.getRoleTypeId());
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetsCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return totalCount;
	}

	@Override
	public List<PetDataExtractStreamDTO> getPetDataExtractConfigById(int petId) throws ServiceExecutionException {
		List<PetDataExtractStreamDTO> petDataExtractConfigList = new ArrayList<>();
		LOGGER.debug("getPetDataExtractConfigList called");
		try {
			jdbcTemplate.query(SQLConstants.PET_GET_DATA_EXTRACT_CONFIG_DETAILS_BY_ID, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetDataExtractStreamDTO dto = new PetDataExtractStreamDTO();
					dto.setStreamId(rs.getString("DATA_STREAM_ID"));
					dto.setAssetNumber(rs.getString("DEVICE_NUMBER"));
					dto.setStudyName(rs.getString("STUDY_NAME"));
					dto.setStudyId(rs.getInt("STUDY_ID"));
					dto.setDeviceSeqNum(rs.getInt("STRM_DEVICE_SEQ_NUM"));
					
					dto.setStartDate(rs.getDate("START_DATE") == null ? null : rs.getDate("START_DATE").toLocalDate());
					String allEndDates = rs.getString("ALL_END_DATES");
					if(allEndDates.contains("$$$")) {
						dto.setEndDate(null);
					}else{
						dto.setEndDate(rs.getDate("END_DATE") == null ? null : rs.getDate("END_DATE").toLocalDate());
					}
					
					
					dto.setExtractStartDate(rs.getDate("EXTRACT_START_DATE") == null ? null : rs.getDate("EXTRACT_START_DATE").toLocalDate());
					dto.setExtractEndDate(rs.getDate("EXTRACT_END_DATE") == null ? null : rs.getDate("EXTRACT_END_DATE").toLocalDate());
					
					dto.setPetName(rs.getString("PET_NAME"));
					dto.setRequestedBy(rs.getString("REQUESTED_BY"));
					dto.setRequestId(rs.getInt("REQUEST_ID"));
					dto.setPetStudyId(rs.getInt("PET_STUDY_ID")); 
					
					dto.setPetType(rs.getString("PET_TYPE"));
					dto.setExcludeFromDataExtract(rs.getInt("EXCLUDE_IN_DATA_EXTRACT"));

					petDataExtractConfigList.add(dto);
				}
			}, petId);

		} catch (Exception e) {
			LOGGER.error("error while fetching getPetDataExtractConfigList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petDataExtractConfigList;
	}

	@Override
	public PetListDTO getPetListById(int petId) throws ServiceExecutionException {
		final PetListDTO petListDTO = new PetListDTO();
		try {

			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("p_pet_id", petId);

			Map<String, Object> simpleJdbcCallResult = callStoredProcedure(
					SQLConstants.PET_GET_DETAILS_FOR_DATA_EXTRACT_BY_ID, inputParams);
			Iterator<Entry<String, Object>> itr = simpleJdbcCallResult.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();
				if (key.equals(SQLConstants.RESULT_SET_1)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(pet -> {
						petListDTO.setPetId((Integer) pet.get("PET_ID"));
						petListDTO.setPetName((String) pet.get("PET_NAME"));
						petListDTO.setBreedName((String) pet.get("BREED_NAME"));
						petListDTO.setGender((String) pet.get("GENDER"));
						LocalDateTime birthday = (LocalDateTime) pet.get("BIRTHDAY");
						petListDTO.setDateOfBirth(birthday != null ? birthday.toLocalDate() : null);
						petListDTO.setPetParentName((String) pet.get("PET_PARENT_NAME"));
					});
				}
			}
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetDetailsById", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petListDTO;
	}
}
