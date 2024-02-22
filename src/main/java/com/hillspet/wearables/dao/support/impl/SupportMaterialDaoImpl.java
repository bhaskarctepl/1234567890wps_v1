
/**
 * Created Date: 14-02-2022
 * Class Name  : SupportMaterialDaoImpl.java
 * Description : Description of the package.
 *
 * © Copyright 2020 Cambridge Technology Enterprises(India) Pvt. Ltd.,All rights reserved.
 *
 * * * * * * * * * * * * * * * Change History *  * * * * * * * * * * *
 * <Defect Tag>      <Author>        <Date>        <Comments on Change>
 * ID                Rajesh          14-02-2022   Mentions the comments on change, for the new file it's not required.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package com.hillspet.wearables.dao.support.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillspet.wearables.common.constants.Constants;
import com.hillspet.wearables.common.constants.SQLConstants;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.common.utils.GCPClientUtil;
import com.hillspet.wearables.dao.BaseDaoImpl;
import com.hillspet.wearables.dao.support.SupportMaterialDao;
import com.hillspet.wearables.dto.SupportMaterialDetails;
import com.hillspet.wearables.dto.SupportMaterialDetailsDTO;
import com.hillspet.wearables.dto.SupportMaterialTypeCountDTO;
import com.hillspet.wearables.dto.filter.SupportMaterialFilter;
import com.hillspet.wearables.request.AddSupportMaterialRequest;
import com.hillspet.wearables.request.UpdateSupportMaterialRequest;

/**
 * Enter detailed explanation of the class here..
 * <p>
 * This class implementation of the <tt>Interface or class Name</tt> interface
 * or class. In addition to implementing the <tt>Interface Name</tt> interface,
 * this class provides methods to do other operations. (Mention other methods
 * purpose)
 *
 * <p>
 * More Description about the class need to be entered here.
 *
 * @author rajesh
 * @version Wearables Portal Relase Version..
 * @since Available Since Wearables Portal Version.
 * @see New line seperated Classes or Interfaces related to this class.
 */
@Repository
public class SupportMaterialDaoImpl extends BaseDaoImpl implements SupportMaterialDao {

	private static final Logger LOGGER = LogManager.getLogger(SupportDaoImpl.class);

	@Autowired
	private GCPClientUtil gcpClientUtil;

	@Autowired
	private ObjectMapper mapper;

	@Override
	@Transactional
	public void addSuportMaterial(List<AddSupportMaterialRequest> addSupportMaterialRequest)
			throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		try {
			inputParams.put("p_add_list", mapper.writeValueAsString(addSupportMaterialRequest));
			inputParams.put("p_created_by", addSupportMaterialRequest.get(0).getUserId());
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.SUPPORT_MATERIAL_INSERT, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isNotEmpty(errorMsg) && statusFlag < NumberUtils.INTEGER_ZERO) {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing addSuportMaterial ", e);
			throw new ServiceExecutionException(e.getMessage());
		} catch (JsonProcessingException e) {
			LOGGER.error("error while executing addSuportMaterial ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public void updateSupportMaterial(UpdateSupportMaterialRequest updateSupportMaterialRequest)
			throws ServiceExecutionException {
		LOGGER.debug("updateSupportMaterial called");
		Map<String, Object> inputParams = new HashMap<>();
		try {
			inputParams.put("p_support_material_details_id",
					updateSupportMaterialRequest.getSupportMaterialDetailsId());
			inputParams.put("p_support_material_id", updateSupportMaterialRequest.getSupportMaterialId());
			inputParams.put("p_device_type", updateSupportMaterialRequest.getAssetType());
			inputParams.put("p_device_model", updateSupportMaterialRequest.getAssetModel());
			inputParams.put("p_material_type_id", updateSupportMaterialRequest.getMaterialTypeId());
			inputParams.put("p_category_id", updateSupportMaterialRequest.getCategoryId());
			if (StringUtils.isNotBlank(updateSupportMaterialRequest.getSubCategoryId())) {
				inputParams.put("p_sub_category_id", updateSupportMaterialRequest.getSubCategoryId());
			} else {
				inputParams.put("p_sub_category_id", Types.NULL);
			}

			inputParams.put("p_title", updateSupportMaterialRequest.getTitle());
			inputParams.put("p_faq_list", mapper.writeValueAsString(updateSupportMaterialRequest.getFaqList()));
			inputParams.put("p_video_or_guide_details",
					mapper.writeValueAsString(updateSupportMaterialRequest.getUploadedFileNames()));
			inputParams.put("p_created_by", updateSupportMaterialRequest.getUserId());

			Map<String, Object> outParams = callStoredProcedure(SQLConstants.SUPPORT_MATERIAL_UPDATE, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");

			LOGGER.debug("updateSupportMaterial errorMsg " + errorMsg);
			LOGGER.debug("updateSupportMaterial statusFlag " + statusFlag);
		} catch (SQLException e) {
			LOGGER.error("error while executing updateSupportMaterial", e);
			throw new ServiceExecutionException(e.getMessage());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOGGER.debug("updateSupportMaterial end");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getSupportMaterialListCount(SupportMaterialFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getSupportMaterialListCount starts");
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		try {
			counts = selectForObject(SQLConstants.SUPPORT_MATERIAL_GET_LIST_COUNT, String.class,
					filter.getSearchText());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while executing getCustomerSupportTicketsCount ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		LOGGER.debug("getSupportMaterialListCount ends");
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getSupportMaterialCounts(SupportMaterialFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getSupportMaterialCounts starts");
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		try {
			counts = selectForObject(SQLConstants.SUPPORT_MATERIAL_GET_LIST_COUNT, String.class,
					filter.getMaterialType());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while executing getSupportMaterialCounts ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		LOGGER.debug("getSupportMaterialCounts ends");
		return map;
	}

	@Override
	public List<SupportMaterialTypeCountDTO> getSupportMaterialTypeCountList(SupportMaterialFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getSupportMaterialTypeCountList starts");
		List<SupportMaterialTypeCountDTO> supportList = new ArrayList<SupportMaterialTypeCountDTO>();
		try {
			String sql = SQLConstants.SUPPORT_MATERIAL_GET_LIST;
			jdbcTemplate.query(sql, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					SupportMaterialTypeCountDTO dto = new SupportMaterialTypeCountDTO();
					dto.setMaterialType(rs.getString("MATERIAL_TYPE_NAME"));
					dto.setMaterialCount(rs.getInt("CNT"));
					/*dto.setSupportMaterialId(rs.getInt("SUPPORT_MATERIAL_ID"));*/
					dto.setMaterialTypeId(rs.getInt("MATERIAL_TYPE_ID"));
					supportList.add(dto);

				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getSortBy(), filter.getOrder(),
					filter.getMaterialType());
		} catch (Exception e) {
			LOGGER.error("error while executing getSupportMaterialTypeCountList ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		LOGGER.debug("getSupportMaterialTypeCountList end");
		return supportList;
	}

	@Override
	public List<SupportMaterialDetailsDTO> getMaterialDetailsListById(SupportMaterialFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getSupportMaterialTypeCountList starts");
		List<SupportMaterialDetailsDTO> detailsList = new ArrayList<SupportMaterialDetailsDTO>();
		try {
			String sql = SQLConstants.SUPPORT_MATERIAL_DETAILS_GET_LIST;
			jdbcTemplate.query(sql, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					SupportMaterialDetailsDTO supportMaterialDetailsDTO = new SupportMaterialDetailsDTO();
					supportMaterialDetailsDTO.setSupportMaterialId(rs.getInt("SUPPORT_MATERIAL_ID"));
					supportMaterialDetailsDTO.setSupportDetailsId(rs.getInt("SUPPORT_MATERIAL_DTLS_ID"));
					supportMaterialDetailsDTO.setTitleOrQuestion(rs.getString("TITLE_OR_QUESTION"));
					supportMaterialDetailsDTO.setUrlOrAnswer(rs.getString("URL_OR_ANSWER"));
					supportMaterialDetailsDTO.setMaterialTypeName(rs.getString("MATERIAL_TYPE_NAME"));
					supportMaterialDetailsDTO.setMaterialTypeId(rs.getInt("MATERIAL_TYPE_ID"));
					supportMaterialDetailsDTO.setModifiedDate(
							rs.getDate("MODIFIED_DATE") != null ? rs.getDate("MODIFIED_DATE").toLocalDate() : null);
					String filePath = rs.getString("URL_OR_ANSWER");
					if (StringUtils.isNotBlank(filePath) && supportMaterialDetailsDTO.getMaterialTypeId() != 2) {
						Map<String, String> uploadedFileInfo = null;
						Map<String, String> thumbnailFileInfo = null;
						if (!filePath.contains("SupportMaterial/")) {
							uploadedFileInfo = gcpClientUtil.getDownloadGcpFileUrlWithSize(filePath,
									Constants.GCP_SUPPORT_MATERIAL_FILE_PATH);
						} else {
							uploadedFileInfo = gcpClientUtil.getDownloadFirebaseFileUrlWithSize(filePath);

							thumbnailFileInfo = gcpClientUtil
									.getDownloadFirebaseFileUrlWithSize(rs.getString("THUMBNAIL_URL"));
						}
						if (uploadedFileInfo != null) {
							supportMaterialDetailsDTO.setUrlOrAnswer(uploadedFileInfo.get("URL"));
							supportMaterialDetailsDTO.setSize(uploadedFileInfo.get("SIZE")); // SIZE
							supportMaterialDetailsDTO.setUploadedFileName(rs.getString("UPLOADED_FILE_NAME"));
						}
						if (thumbnailFileInfo != null) {
							supportMaterialDetailsDTO.setThumbnailUrl(thumbnailFileInfo.get("URL"));
						}
					}

					supportMaterialDetailsDTO.setCategoryId(rs.getInt("CATEGORY_ID"));
					supportMaterialDetailsDTO.setCategoryName(rs.getString("MATERIAL_CATEGORY_NAME"));
					supportMaterialDetailsDTO.setSubCategoryId(rs.getInt("SUB_CATEGORY_ID"));
					supportMaterialDetailsDTO.setSubCategoryName(rs.getString("SUB_CATEGORY_NAME"));

					supportMaterialDetailsDTO.setDuration(""); // DURATION
					supportMaterialDetailsDTO.setCreatedDate(
							rs.getDate("CREATED_DATE") != null ? rs.getDate("CREATED_DATE").toLocalDate() : null);

					supportMaterialDetailsDTO.setAssetModel(rs.getString("ASSET_MODEL"));
					supportMaterialDetailsDTO.setAssetType(rs.getString("ASSET_TYPE"));

					detailsList.add(supportMaterialDetailsDTO);

				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getSortBy(), filter.getOrder(),
					filter.getSupportMaterialId(), filter.getCategoryId(), filter.getSubCategoryId(),
					filter.getAssetType(), filter.getAssetModel());
		} catch (Exception e) {
			LOGGER.error("error while executing getSupportMaterialTypeCountList ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		LOGGER.debug("getSupportMaterialTypeCountList end");
		return detailsList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getMaterialDetailsListByIdCount(SupportMaterialFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getSupportMaterialCounts starts");
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		try {
			counts = selectForObject(SQLConstants.SUPPORT_MATERIAL_DETAILS_LIST_COUNT, String.class,
					filter.getSupportMaterialId(), filter.getCategoryId(), filter.getSubCategoryId(),
					filter.getAssetType(), filter.getAssetModel());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while executing getSupportMaterialCounts ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		LOGGER.debug("getSupportMaterialCounts ends");
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SupportMaterialDetails getMaterialDetailsById(int id) throws ServiceExecutionException {
		final SupportMaterialDetails supportMaterialDetailsDTO = new SupportMaterialDetails();
		LOGGER.debug("getMaterialDetailsById called");
		List<SupportMaterialDetailsDTO> detailsList = new ArrayList<SupportMaterialDetailsDTO>();
		try {
			// in params
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("p_support_material_id", id);
			Map<String, Object> simpleJdbcCallResult = callStoredProcedure(SQLConstants.SUPPORT_MATERIAL_DETAILS_BY_ID,
					inputParams);
			Iterator<Entry<String, Object>> itr = simpleJdbcCallResult.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();

				if (key.equals(SQLConstants.RESULT_SET_1)) { // Details
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(u -> {
						SupportMaterialDetailsDTO dto = new SupportMaterialDetailsDTO();

						dto.setSupportMaterialId((Integer) u.get("SUPPORT_MATERIAL_ID"));
						dto.setSupportDetailsId((Integer) u.get("SUPPORT_MATERIAL_DTLS_ID"));

						dto.setMaterialTypeName((String) u.get("MATERIAL_TYPE_NAME"));
						dto.setMaterialTypeId((Integer) u.get("MATERIAL_TYPE_ID"));

						dto.setCategoryId((Integer) u.get("CATEGORY_ID"));
						dto.setCategoryName((String) u.get("MATERIAL_CATEGORY_NAME"));

						dto.setSubCategoryId((Integer) u.get("SUB_CATEGORY_ID"));
						dto.setSubCategoryName((String) u.get("SUB_CATEGORY_NAME"));

						dto.setAssetModel((String) u.get("ASSET_MODEL"));
						dto.setAssetType((String) u.get("ASSET_TYPE"));

						dto.setTitleOrQuestion((String) u.get("TITLE_OR_QUESTION"));
						dto.setUrlOrAnswer((String) u.get("URL_OR_ANSWER"));

						dto.setUploadedFileName((String) u.get("UPLOADED_FILE_NAME"));
						dto.setThumbnailUrl((String) u.get("THUMBNAIL_URL"));

						detailsList.add(dto);

					});
				}
			}
		} catch (Exception e) {
			LOGGER.error("error while fetching getCustomerSupportTicketById", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		supportMaterialDetailsDTO.setDetailsList(detailsList);
		return supportMaterialDetailsDTO;
	}

	@Override
	public Map<String, String> validateTitle(List<AddSupportMaterialRequest> addSupportMaterialRequest)
			throws ServiceExecutionException {
		LOGGER.debug("validateTitle called");
		Map<String, Object> inputParams = new HashMap<>();
		Map<String, String> errorList = new HashMap<>();
		try {
			inputParams.put("p_add_list", mapper.writeValueAsString(addSupportMaterialRequest));
			inputParams.put("p_created_by", addSupportMaterialRequest.get(0).getUserId());
			LOGGER.debug("inputParams =" + inputParams);
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.SUPPORT_MATERIAL_VALIDATE_TITLE,
					inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			LOGGER.debug("errorMsg =" + errorMsg);
			LOGGER.debug("statusFlag =" + statusFlag);
			if (StringUtils.isNotEmpty(errorMsg) && statusFlag < NumberUtils.INTEGER_ZERO) {
				errorList.put("error", errorMsg);
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing validateTitle ", e);
			throw new ServiceExecutionException(e.getMessage());
		} catch (JsonProcessingException e) {
			LOGGER.error("error while executing validateTitle ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		LOGGER.debug("validateTitle completed successfully");
		return errorList;
	}

	@Override
	public void deleteSupportMaterial(int supportMaterialId, int modifiedBy) throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_support_material_dtls_id", supportMaterialId);
		inputParams.put("p_modified_by", modifiedBy);
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.SUPPORT_MATERIAL_DELETE, inputParams);
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
