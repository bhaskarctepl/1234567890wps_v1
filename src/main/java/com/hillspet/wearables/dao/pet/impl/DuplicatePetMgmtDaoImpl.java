package com.hillspet.wearables.dao.pet.impl;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillspet.wearables.common.constants.Constants;
import com.hillspet.wearables.common.constants.SQLConstants;
import com.hillspet.wearables.common.constants.WearablesErrorCode;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.common.utils.GCPClientUtil;
import com.hillspet.wearables.dao.BaseDaoImpl;
import com.hillspet.wearables.dao.pet.DuplicatePetMgmtDao;
import com.hillspet.wearables.dto.DuplicatePetConfigDTO;
import com.hillspet.wearables.dto.PetDataStreamDTO;
import com.hillspet.wearables.dto.PetDuplicateConfigDTO;
import com.hillspet.wearables.dto.PetListDTO;
import com.hillspet.wearables.dto.filter.DuplicatePetsFilter;
import com.hillspet.wearables.dto.filter.PetDataStreamFilter;
import com.hillspet.wearables.request.DataStreamGroupRequest;

@Repository
public class DuplicatePetMgmtDaoImpl extends BaseDaoImpl implements DuplicatePetMgmtDao {

	private static final Logger LOGGER = LogManager.getLogger(DuplicatePetMgmtDaoImpl.class);

	@Value("${gcp.env}")
	private String environment;

	@Autowired
	private GCPClientUtil gcpClientUtil;

	@Override
	public String getPrimaryPetsListCount(DuplicatePetsFilter filter) throws ServiceExecutionException {
		String totalCount = "";
		LOGGER.debug("getPrimaryPetsListCount called");
		try {
			totalCount = selectForObject(SQLConstants.PET_GET_PRIMARY_PETS_LIST_COUNT, String.class,
					filter.getSearchText(), filter.getPetName(), filter.getGender(), filter.getDateOfBirth(),
					filter.getBreedId(), filter.getUserId(), filter.getRoleTypeId());
		} catch (Exception e) {
			LOGGER.error("error while fetching getPrimaryPetsListCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return totalCount;

	}

	@Override
	public List<PetListDTO> getPrimaryPetsList(DuplicatePetsFilter filter) throws ServiceExecutionException {
		List<PetListDTO> petList = new ArrayList<>();
		LOGGER.debug("getPrimaryPetsList called");
		try {
			jdbcTemplate.query(SQLConstants.PET_GET_PRIMARY_PETS_LIST, new RowCallbackHandler() {
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
			LOGGER.error("error while fetching getPrimaryPetsList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petList;
	}

	@Override
	public String getPrimaryPetsCount(DuplicatePetsFilter filter) throws ServiceExecutionException {
		String totalCount = "";
		LOGGER.debug("getPrimaryPetsCount called");
		try {
			totalCount = selectForObject(SQLConstants.PET_GET_PRIMARY_PETS_COUNT, String.class, filter.getSearchText(),
					filter.getPetName(), filter.getGender(), filter.getDateOfBirth(), filter.getBreedId(),
					filter.getUserId(), filter.getRoleTypeId());
		} catch (Exception e) {
			LOGGER.error("error while fetching getPrimaryPetsCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return totalCount;

	}

	@Override
	public List<PetListDTO> getPrimaryPets(DuplicatePetsFilter filter) throws ServiceExecutionException {
		List<PetListDTO> petList = new ArrayList<>();
		LOGGER.debug("getPrimaryPets called");
		try {
			jdbcTemplate.query(SQLConstants.PET_GET_PRIMARY_PETS, new RowCallbackHandler() {
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
			LOGGER.error("error while fetching getPrimaryPets", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petList;
	}

	@Override
	public String getDuplicatePetsCount(DuplicatePetsFilter filter) throws ServiceExecutionException {
		String totalCount = "";
		LOGGER.debug("getDuplicatePetsCount called");
		try {
			totalCount = selectForObject(SQLConstants.PET_GET_DUPLICATE_PETS_COUNT, String.class,
					filter.getSearchText(), filter.getPetName(), filter.getGender(), filter.getDateOfBirth(),
					filter.getBreedId(), filter.getPrimaryPetId(),filter.getUserId(), filter.getRoleTypeId());
		} catch (Exception e) {
			LOGGER.error("error while fetching getDuplicatePetsCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return totalCount;

	}

	@Override
	public List<PetListDTO> getDuplicatePets(DuplicatePetsFilter filter) throws ServiceExecutionException {
		List<PetListDTO> petList = new ArrayList<>();
		LOGGER.debug("getDuplicatePets called");
		try {
			jdbcTemplate.query(SQLConstants.PET_GET_DUPLICATE_PETS, new RowCallbackHandler() {
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
					
					petListDTO.setStudyName(rs.getString("STUDY_NAME"));
					petListDTO.setPetParentEmail(rs.getString("EMAIL"));

					petList.add(petListDTO);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(),
					filter.getSearchText().trim(), filter.getPetName(), filter.getGender(), filter.getDateOfBirth(),
					filter.getBreedId(), filter.getPrimaryPetId(), filter.getUserId(), filter.getRoleTypeId());

		} catch (Exception e) {
			LOGGER.error("error while fetching getDuplicatePets", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petList;
	}

	@Override
	public List<PetDataStreamDTO> getDataStreams(PetDataStreamFilter filter) throws ServiceExecutionException {
		List<PetDataStreamDTO> petList = new ArrayList<>();
		LOGGER.debug("getDuplicatePets called");

		try {
			jdbcTemplate.query(SQLConstants.PET_GET_DATASTREAM_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetDataStreamDTO petListDTO = new PetDataStreamDTO();
					petListDTO.setPetId(rs.getInt("PET_ID"));
					petListDTO.setPetName(rs.getString("PET_NAME"));
					
					petListDTO.setStudyId(rs.getInt("STUDY_ID"));
					petListDTO.setStudyName(rs.getString("STUDY_NAME"));
					petListDTO.setAssetNumber(rs.getString("DEVICE_NUMBER")); 
					petListDTO.setStreamId(rs.getString("DATA_STREAM_ID"));
					
					petListDTO.setStartDate(rs.getDate("START_DATE") == null ? null : rs.getDate("START_DATE").toLocalDate());
					
					String allEndDates = rs.getString("ALL_END_DATES");
					if(allEndDates.contains("$$$")) {
						petListDTO.setEndDate(null);
					}else{
						petListDTO.setEndDate(rs.getDate("END_DATE") == null ? null : rs.getDate("END_DATE").toLocalDate());
					}
					
					petListDTO.setAssignDate(rs.getDate("ASSIGN_DATE") == null ? null : rs.getDate("ASSIGN_DATE").toLocalDate());
					
					petListDTO.setPetType(rs.getString("PET_TYPE"));
					//petListDTO.setPetStudyDeviceId(rs.getInt("PET_STUDY_DEVICE_ID"));
					
					petList.add(petListDTO);
				}
			}, 
			   filter.getPrimaryPetId()
			  ,filter.getDuplicatePetIds()
			  );
		} catch (Exception e) {
			LOGGER.error("error while fetching getDuplicatePets", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petList;
	}

	@Override
	public DataStreamGroupRequest saveDataStream(DataStreamGroupRequest request) throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			
			inputParams.put("p_duplicate_pet_list", new ObjectMapper().writeValueAsString(request.getDuplicatePetsList()));
			inputParams.put("p_created_by", request.getUserId());

			Map<String, Object> outParams = callStoredProcedure(SQLConstants.PET_DUPLICATE_STREAM_INSERT, inputParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");

			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				// getting the inserted flag value
				Integer dataStreamId = (int) outParams.get("last_insert_id");
				LOGGER.info("Data Stream added successfully", dataStreamId);
			}else {
				LOGGER.error("saveDataStream Data Stream errorMsg ", errorMsg);
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (Exception e) {
			LOGGER.error("error while executing saveDataStream ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return request;
	}

	@Override
	public String getDuplicatePetConfigListCount(DuplicatePetsFilter filter) throws ServiceExecutionException {
		String totalCount = "";
		LOGGER.debug("getDuplicatePetConfigListCount called");
		try {
			totalCount = selectForObject(SQLConstants.PET_GET_DUPLICATE_PETS_CONFIG_COUNT, String.class, filter.getSearchText(),
					filter.getPetName(), filter.getGender(), filter.getDateOfBirth(), filter.getBreedId(),
					filter.getUserId(), filter.getRoleTypeId());
		} catch (Exception e) {
			LOGGER.error("error while fetching getDuplicatePetListCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return totalCount;

	}

	@Override
	public List<DuplicatePetConfigDTO> getDuplicatePetConfigList(DuplicatePetsFilter filter) throws ServiceExecutionException {
		List<DuplicatePetConfigDTO> petList = new ArrayList<>();
		LOGGER.debug("getDuplicatePetConfigList called");

		try {
			jdbcTemplate.query(SQLConstants.PET_GET_DUPLICATE_PETS_CONFIG_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					DuplicatePetConfigDTO petListDTO = new DuplicatePetConfigDTO();
					petListDTO.setPrimaryPetId(rs.getInt("PRIMARY_PET_ID"));
					petListDTO.setPetName(rs.getString("PET_NAME"));
					petListDTO.setDataStreamId(rs.getString("PRIMARY_STREAM_ID"));
					petListDTO.setDuplicatePets(rs.getString("DUPLICATE_PETS"));
					
					
					petList.add(petListDTO);
				}
			}, 
					filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(),
					filter.getSearchText().trim(),
					filter.getPetName(), filter.getGender(), filter.getDateOfBirth(),
					filter.getBreedId(), filter.getUserId(), filter.getRoleTypeId()
			  );
		} catch (Exception e) {
			LOGGER.error("error while fetching getDuplicatePetConfigList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petList;
	}

	@Override
	public List<PetDuplicateConfigDTO> getDuplicatePetConfigDetailsById(int petId) throws ServiceExecutionException {
		List<PetDuplicateConfigDTO> petList = new ArrayList<>();
		LOGGER.debug("getDuplicatePet By Id called");

		try {
			jdbcTemplate.query(SQLConstants.PET_GET_DUPLICATE_PETS_CONFIG_DETAILS_BY_ID, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetDuplicateConfigDTO petListDTO = new PetDuplicateConfigDTO();
					
					petListDTO.setStudyName(rs.getString("STUDY_NAME"));
					petListDTO.setAssetNumber(rs.getString("ASSET_NUMBER"));
					petListDTO.setPetId(rs.getInt("PET_ID"));
					petListDTO.setPetName(rs.getString("PET_NAME"));
					petListDTO.setStartDate(rs.getDate("START_DATE") != null ? rs.getDate("START_DATE").toLocalDate() :  null);
					
					String allEndDates = rs.getString("ALL_END_DATES");
					if(allEndDates.contains("$$$")) {
						petListDTO.setEndDate(null);
					}else{
						petListDTO.setEndDate(rs.getDate("END_DATE") == null ? null : rs.getDate("END_DATE").toLocalDate());
					}
					
					
					petListDTO.setExtractStartDate(rs.getDate("EXTRACT_START_DATE") != null ? rs.getDate("EXTRACT_START_DATE").toLocalDate() :  null);
					petListDTO.setExtractEndDate(rs.getDate("EXTRACT_END_DATE") != null ? rs.getDate("EXTRACT_END_DATE").toLocalDate() :  null);
					
					
					petListDTO.setStitchGroupId(rs.getString("PET_STITCH_GROUP_ID"));
					petListDTO.setStreamId(rs.getString("STREAM_ID"));
					petListDTO.setPetType(rs.getString("PET_TYPE"));
					petListDTO.setConfigId(rs.getInt("DUP_PET_CONFIG_ID"));
					
					/*
					petListDTO.setPrimaryPetId(rs.getInt("PRIMARY_PET_ID"));
					petListDTO.setPrimaryPetName(rs.getString("PET_NAME"));
					petListDTO.setPrimaryStreamId(rs.getString("PRIMARY_STREAM_ID"));
					petListDTO.setPrimaryPetStartDate(rs.getDate("PR_PET_START_DATE") != null ? rs.getDate("PR_PET_START_DATE").toLocalDate() :  null);
					petListDTO.setPrimaryPetEndDate(rs.getDate("PR_PET_END_DATE") != null ? rs.getDate("PR_PET_END_DATE").toLocalDate() :  null);
					
					petListDTO.setDuplicatePetId(rs.getInt("DUPLICATE_PET_ID"));
					petListDTO.setDuplicatePetStreamId(rs.getString("DUPLICATE_STREAM_ID"));
					petListDTO.setIsContinuation(rs.getInt("IS_CONTINUATION"));
					
					
					petListDTO.setDuplicatePetName(rs.getString("DUPLICATE_PET_NAME"));
					petListDTO.setDuplicatePetStartDate(rs.getDate("DUP_PET_START_DATE") != null ? rs.getDate("DUP_PET_START_DATE").toLocalDate() :  null);
					petListDTO.setDuplicatePetEndDate(rs.getDate("DUP_PET_END_DATE") != null ? rs.getDate("DUP_PET_END_DATE").toLocalDate() :  null);
					*/
					
					petListDTO.setPetStudyId(rs.getInt("PET_STUDY_ID"));
					petListDTO.setExcludeFromDataExtract(rs.getInt("EXCLUDE_IN_DATA_EXTRACT"));
					
					petListDTO.setDupExcludeFromDataExtract(rs.getInt("DUP_EXCLUDE_IN_DATA_EXTRACT"));
					 
					petList.add(petListDTO);
				}
			}, 
				petId
			  );
		} catch (Exception e) {
			LOGGER.error("error while fetching getDuplicatePetConfigList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petList;
	}

	@Override
	public void deleteDuplicatePet(int primaryPetId, int modifiedBy) throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_pet_id", primaryPetId);
		inputParams.put("p_modified_by", modifiedBy);
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.PET_DELETE_DUPLICATE_PET, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			if (StringUtils.isNotEmpty(errorMsg) || (int) outParams.get("out_flag") < NumberUtils.INTEGER_ONE) {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing deletePet ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

}
