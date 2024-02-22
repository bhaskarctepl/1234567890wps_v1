package com.hillspet.wearables.dao.diet.impl;

import com.hillspet.wearables.common.constants.ActivityFactorConstants;
import com.hillspet.wearables.common.constants.SQLConstants;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.BaseDaoImpl;
import com.hillspet.wearables.dao.diet.DietDetailsDao;
import com.hillspet.wearables.dto.BulkUploadDietInfo;
import com.hillspet.wearables.dto.DietDTO;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.dto.filter.DietFilter;
import com.hillspet.wearables.request.BulkDietUploadRequest;
import com.hillspet.wearables.request.DietRequest;
import com.hillspet.wearables.response.DietLookUpResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class DietDetailsDaoImpl extends BaseDaoImpl implements DietDetailsDao {

    private static final Logger LOGGER = LogManager.getLogger(DietDetailsDaoImpl.class);
     /*
     * Insert the data into STG_DIET_UPLOAD_DETAILS & STG_UPLOAD_DIET_ERROR_DETAILS
     * */
    @Override
    public int[] saveBulkDietToStaging(List<BulkUploadDietInfo> dietInfo, String attachmentName, Integer userId, Integer auditId) throws ServiceExecutionException {
            LOGGER.debug("saveBulkDietToStaging called");
            LOGGER.debug("saveBulkDietToStaging batch size " + dietInfo.size());
            int[] result = null;
            try{
                LOGGER.debug("saveBulkDietToStaging before audit ");
                this.updateAuditRecord(auditId,userId,"InProgress");
                LOGGER.debug("saveBulkDietToStaging after audit ");
                LOGGER.debug("saveBulkDietToStaging staring staging loading ");
                result = jdbcTemplate.batchUpdate(SQLConstants.DIET_INFO_BULK_INSERT_STAGING,
                        new BatchPreparedStatementSetter() {
                            @Override
                            public void setValues(PreparedStatement ps, int i) throws SQLException {
                                BulkUploadDietInfo info = dietInfo.get(i);
                                ps.setInt(1, auditId);
                                ps.setInt(2, userId);
                                ps.setString(3, info.getDietNo());
                                ps.setString(4, info.getDietName());
                                ps.setString(5, info.getDietDescription());
                                ps.setString(6, info.getBrand());
                                ps.setString(7, info.getSubBrand());
                                ps.setString(8, info.getCompanyName());
                                if(info.getCalDensityKcalKg() == null) {
                                    ps.setNull(9, java.sql.Types.NULL);
                                } else {
                                    ps.setDouble(9, info.getCalDensityKcalKg());
                                }
                                ps.setString(10, info.getCategory());
                                ps.setString(11, info.getProductType());
                                ps.setString(12, info.getRecipeType());
                                //ps.setString(13, info.getMaterialNumber());
                                ps.setString(13, info.getPdmDensityUOM());
                                if(info.getPdmDensityValue() == null) {
                                    ps.setNull(14, java.sql.Types.NULL);
                                } else {
                                    ps.setDouble(14, info.getPdmDensityValue());
                                }
                                if(info.getLastModifiedDate() == null) {
                                    ps.setNull(15, java.sql.Types.NULL);
                                }else{
                                    ps.setString(15, info.getLastModifiedDate().toString());
                                }
                                ps.setInt(16,  ActivityFactorConstants.BULK_UPLOAD_DATA_SOURCE_ID);
                                ps.setInt(17, userId);
                                ps.setInt(18, userId);
                                ps.setString(19, info.getExceptionMsg());
                            }
                            @Override
                            public int getBatchSize() {
                                return dietInfo.size();
                            }
                        });
                LOGGER.debug("saveBulkDietToStaging staging loaded ");
                /*List<BulkUploadDietInfo> dietInfoError = dietInfo.stream().filter(x-> x.getExceptionMsg()!=null && !x.getExceptionMsg().isEmpty()).collect(Collectors.toList());
                LOGGER.debug("saveBulkDietToStaging batch size for error " + dietInfoError.size());
                if(!dietInfoError.isEmpty()) {
                    LOGGER.debug("saveBulkDietToStaging staring error loading ");
                    result = jdbcTemplate.batchUpdate(SQLConstants.DIET_INFO_BULK_INSERT_STAGING_ERROR, new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            BulkUploadDietInfo info = dietInfoError.get(i);
                            ps.setInt(1, auditId);
                            ps.setString(2, info.getDietNo());
                            ps.setString(3, info.getExceptionMsg());
                            ps.setInt(4, ActivityFactorConstants.BULK_UPLOAD_DATA_SOURCE_ID);
                            ps.setInt(5, userId);
                            ps.setInt(6, userId);
                        }
                        @Override
                        public int getBatchSize() {
                            return dietInfoError.size();
                        }
                    });
                    LOGGER.debug("saveBulkDietToStaging error loading ");
                }*/
            } catch (Exception e) {
                LOGGER.error("error while saveBulkDietToStaging", e);
            }
            LOGGER.debug("saveBulkDietToStaging Completed");
            LOGGER.debug("saveBulkDietToStaging - validateBulkDietList start.");
            validateBulkDietList(userId,auditId);
            LOGGER.debug("saveBulkDietToStaging - validateBulkDietList end.");
            return result;
    }
    /*
    * Validate diet details
    * */
    @Override
    public void validateBulkDietList(Integer userId, Integer auditId) throws ServiceExecutionException {
        Map<String, Object> inputParams = new HashMap<>();
        inputParams.put("p_user_id", userId);
        inputParams.put("p_audit_id", auditId);
        try {
            Map<String, Object> outParams = callStoredProcedure(SQLConstants.DIET_INFO_BULK_VALIDATION_STAGING,
                    inputParams);
            String errorMsg = (String) outParams.get("out_error_msg");
            if (StringUtils.isNotEmpty(errorMsg) || (int) outParams.get("out_flag") < NumberUtils.INTEGER_ONE) {
                throw new ServiceExecutionException(errorMsg);
            }
        } catch (SQLException e) {
            LOGGER.error("error while executing validateBulkDietList ", e);
            throw new ServiceExecutionException(e.getMessage());
        }
    }
    /*
    * Create audit record
    * */
    @Override
    public Integer createAuditRecord(String fileName, Integer userId, Integer dataSourceId) throws ServiceExecutionException {
        Map<String, Object> inputParams = new HashMap<>();
        Integer auditId=0;
        inputParams.put("P_FILE_NAME", fileName);
        inputParams.put("P_USER_ID", userId);
        inputParams.put("P_STATUS", "Pending");
        inputParams.put("P_DATASOURCE_ID", dataSourceId);
        try {
            Map<String, Object> outParams = callStoredProcedure(SQLConstants.CREATE_DIET_UPLOAD_AUDIT, inputParams);
            String errorMsg = (String) outParams.get("out_error_msg");
            int statusFlag = (int) outParams.get("out_flag");
            if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
                auditId = (Integer) outParams.get("auditId");
                LOGGER.info(" Diet audit record created successfully audit id :: "+ auditId);
            } else {
                throw new ServiceExecutionException(errorMsg);
            }
        } catch (SQLException e) {
            LOGGER.error("error while executing  "+SQLConstants.CREATE_DIET_UPLOAD_AUDIT, e);
            throw new ServiceExecutionException(e.getMessage());
        }
        return auditId;
    }
    /*
    * Update the audit records
    * */
    @Override
    public void updateAuditRecord(Integer auditId, Integer userId,String status) throws ServiceExecutionException {
        LOGGER.info("Diet audit record is updated for audit id "+ auditId+" for status "+status);
        Map<String, Object> inputParams = new HashMap<>();
        inputParams.put("P_AUDIT_ID", auditId);
        inputParams.put("P_USER_ID", userId);
        inputParams.put("P_STATUS", status);
        try {
            Map<String, Object> outParams = callStoredProcedure(SQLConstants.UPDATE_DIET_UPLOAD_AUDIT, inputParams);
            String errorMsg = (String) outParams.get("out_error_msg");
            int statusFlag = (int) outParams.get("out_flag");
            if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
                LOGGER.info("Diet audit record updated successfully for audit id "+ auditId);
            } else {
                throw new ServiceExecutionException(errorMsg);
            }
        } catch (SQLException e) {
            LOGGER.error("error while executing  "+SQLConstants.CREATE_DIET_UPLOAD_AUDIT, e);
            throw new ServiceExecutionException(e.getMessage());
        }
    }

    @Override
    public HashMap<String,Integer> getBulkUploadDietListCount(DietFilter filter) throws ServiceExecutionException {
        String countStr = "0-0-0";
        HashMap<String,Integer> resultMap = new HashMap<>(3);
        LOGGER.debug("getBulkUploadDietListCount called");
        try {
            countStr = selectForObject(
                           SQLConstants.DIET_INFO_BULK_STAGING_LIST_COUNT, String.class,
                           filter.getUserId(),
                           filter.getId()
                         );
        } catch (Exception e) {
            LOGGER.error("error while fetching getDietCount", e);
            throw new ServiceExecutionException(e.getMessage());
        }
        String[] countArr = countStr.split("-");
        resultMap.put("totalRecords",Integer.parseInt(countArr[0]));
        resultMap.put("successRecords",Integer.parseInt(countArr[1]));
        resultMap.put("failedRecords",Integer.parseInt(countArr[2]));
        return resultMap;
    }

    @Override
    public List<BulkUploadDietInfo> getBulkUploadDietList(DietFilter filter) throws ServiceExecutionException {
        List<BulkUploadDietInfo> dietList = new ArrayList<>();
        LOGGER.debug("getDietList called");
        try {
            jdbcTemplate.query(SQLConstants.DIET_INFO_BULK_STAGING_LIST, new RowCallbackHandler() {
                @Override
                public void processRow(ResultSet rs) throws SQLException {
                    BulkUploadDietInfo diet = new BulkUploadDietInfo();
                    diet.setUploadId(rs.getInt("UPLOAD_ID"));
                    diet.setDietNo(rs.getString("DIET_NUMBER"));
                    diet.setDietName(rs.getString("DIET_NAME"));
                    diet.setDietDescription(rs.getString("DIET_DESCRIPTION"));
                    diet.setBrand(rs.getString("BRAND"));
                    diet.setSubBrand(rs.getString("SUB_BRAND"));
                    diet.setCompanyName(rs.getString("COMPANY_NAME"));
                    diet.setCalDensityKcalKg(rs.getDouble("DIET_DENSITY_KCAL_KG"));
                    diet.setCalDensityKcalCup(rs.getDouble("DIET_DENSITY_KCAL_CUP"));
                    diet.setCategory(rs.getString("CATEGORY"));
                    diet.setProductType(rs.getString("PRODUCT_TYPE"));
                    diet.setRecipeType(rs.getString("RECIPE_TYPE"));
                    //diet.setMaterialNumber(rs.getString("MATERIAL_NUMBER"));
                    diet.setPdmDensityUOM(rs.getString("PDM_UOM"));
                    diet.setPdmDensityValue(rs.getDouble("PDM_DENSITY_VALUE"));
                    if (rs.getTimestamp("LATEST_MODIFIED_DATE") != null) {
                        diet.setLastModifiedDate(rs.getTimestamp("LATEST_MODIFIED_DATE").toLocalDateTime());
                    }
                    diet.setExceptionMsg(rs.getString("ERROR_DESCRIPTION"));
                    dietList.add(diet);
                }
            }, filter.getStartIndex(), filter.getLimit(), filter.getUserId(), filter.getId());

        } catch (Exception e) {
            LOGGER.error("error while fetching getDietList", e);
            throw new ServiceExecutionException(e.getMessage());
        }
        return dietList;
    }

    /*
    * Save the selected diet and logically deletes staging records
    * */
    @Override
    public Integer saveBulkUploadDietInfo(BulkDietUploadRequest request) throws ServiceExecutionException {
        LOGGER.debug("saveBulkUploadDietInfo started");
        LOGGER.debug("saveBulkUploadDietInfo userId = " + request.getUserId());
        LOGGER.debug("saveBulkUploadDietInfo auditId = " + request.getUploadId());
        LOGGER.debug("saveBulkUploadDietInfo selectedIds = " + request.getId());
        Map<String, Object> inputParams = new HashMap<>();
        inputParams.put("p_user_id", request.getUserId());
        inputParams.put("p_audit_id", request.getUploadId());
        inputParams.put("p_selected_staging_ids", request.getId());
        int dietID = 0;
        try {
            Map<String, Object> outParams = callStoredProcedure(SQLConstants.DIET_INFO_BULK_SAVE, inputParams);
            String errorMsg = (String) outParams.get("out_error_msg");
            int statusFlag = (int) outParams.get("out_flag");
            LOGGER.info("saveBulkUploadDietInfo :: "+errorMsg );
            LOGGER.info("statusFlag :: "+statusFlag );
            if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
                // getting the inserted flag value
                dietID = (int) outParams.get("last_insert_id");
                LOGGER.info("Diet created successfully, Diet ID is " + dietID);
            } else {
                    throw new ServiceExecutionException(errorMsg);
            }
            LOGGER.debug("saveBulkUploadDietInfo statusFlag " + statusFlag);
            LOGGER.debug("saveBulkUploadDietInfo errorMsg " + errorMsg);
        } catch (SQLException e) {
            LOGGER.error("error while executing addDietInfo ", e);
            throw new ServiceExecutionException(e.getMessage());
        }
        return dietID;
    }

    /*
    * To get the lookup data
    * */
    @Override
    public DietLookUpResponse getDietDetailsForLookUp() throws ServiceExecutionException {
        DietLookUpResponse dietLookUpResponse = new DietLookUpResponse();
        try {
            jdbcTemplate.query(SQLConstants.DIET_INFO_GET_LOOK_UP_DATA, new RowCallbackHandler() {
                @Override
                public void processRow(ResultSet rs) throws SQLException {
                   String companyName = rs.getString("companyName");
                   String brand = rs.getString("brand");
                   String subBrand = rs.getString("subBrand");
                   String category = rs.getString("category");
                   String productType = rs.getString("productType");
                   String recipeType = rs.getString("recipeType");
                   dietLookUpResponse.setCompanyName(StringUtils.isNotEmpty(companyName)?Arrays.stream(companyName.split(",")).sorted().collect(Collectors.toList()): new ArrayList<>());
                   dietLookUpResponse.setBrand(StringUtils.isNotEmpty(brand)?Arrays.stream(brand.split(",")).sorted().collect(Collectors.toList()): new ArrayList<>());
                   dietLookUpResponse.setSubBrand(StringUtils.isNotEmpty(subBrand)?Arrays.stream(subBrand.split(",")).sorted().collect(Collectors.toList()): new ArrayList<>());
                   dietLookUpResponse.setCategory(StringUtils.isNotEmpty(category)?Arrays.stream(category.split(",")).sorted().collect(Collectors.toList()): new ArrayList<>());
                   dietLookUpResponse.setProductType(StringUtils.isNotEmpty(productType)?Arrays.stream(productType.split(",")).sorted().collect(Collectors.toList()): new ArrayList<>());
                   dietLookUpResponse.setRecipeType(StringUtils.isNotEmpty(recipeType)?Arrays.stream(recipeType.split(",")).sorted().collect(Collectors.toList()): new ArrayList<>());
                }
            });
        } catch (Exception e) {
            LOGGER.error("error while fetching getDietDetailsForLookUp", e);
            throw new ServiceExecutionException(e.getMessage());
        }
        return dietLookUpResponse;
    }
    
    /**
     * @author akumarkhaspa
     * @param dietRequest
     * @return 
     */
    @Override
	public DietDTO addDiet(DietRequest dietRequest) throws ServiceExecutionException {
    	DietDTO dietDto = new DietDTO();
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_diet_name", dietRequest.getDietName());
			inputParams.put("p_diet_description", dietRequest.getDietDescription());
			inputParams.put("p_brand", dietRequest.getBrand());
			inputParams.put("p_sub_brand", dietRequest.getSubBrand());
			inputParams.put("p_company_name",dietRequest.getCompanyName());
			inputParams.put("p_diet_density_kcal_kg", dietRequest.getDietDensityKcalByKg());
			inputParams.put("p_diet_density_kcal_cup", dietRequest.getDietDensityKcalByCup());
			inputParams.put("p_category", dietRequest.getCategory());
			inputParams.put("p_product_type_id", dietRequest.getProductTypeId());
			inputParams.put("p_recipe_type", dietRequest.getRecipeType());
			//inputParams.put("p_material_number", dietRequest.getMaterialNumber());
			//inputParams.put("p_pdm_uom", dietRequest.getPdmUom());
			//inputParams.put("p_pdm_density_value", null);
			inputParams.put("p_user_id", dietRequest.getUserId());
			inputParams.put("p_datasource_id", 20);

			Map<String, Object> outParams = callStoredProcedure(SQLConstants.DIET_INFO_INSERT, inputParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");

			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				// getting the inserted flag value
				Integer dietId = (int) outParams.get("last_insert_id");
				LOGGER.info("Diet has been created successfully, Diet id is ", dietId);
				dietRequest.setDietId(dietId);
				BeanUtils.copyProperties(dietRequest, dietDto);
			} else {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (SQLException  e) {
			LOGGER.error("error while executing AddDiet ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return dietDto;
	}
    
    /**
     * @author akumarkhaspa
     * @param dietRequest
     * @return
     */
    @Override
	public DietDTO updateDiet(DietRequest dietRequest) throws ServiceExecutionException {
		DietDTO petParentDTO = new DietDTO();
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_diet_id", dietRequest.getDietId());
			inputParams.put("p_diet_name", dietRequest.getDietName());
			inputParams.put("p_diet_description", dietRequest.getDietDescription());
			inputParams.put("p_brand", dietRequest.getBrand());
			inputParams.put("p_sub_brand", dietRequest.getSubBrand());
			inputParams.put("p_company_name",dietRequest.getCompanyName());
			inputParams.put("p_diet_density_kcal_kg", dietRequest.getDietDensityKcalByKg());
			inputParams.put("p_diet_density_kcal_cup", dietRequest.getDietDensityKcalByCup());
			inputParams.put("p_category", dietRequest.getCategory());
			inputParams.put("p_product_type_id", dietRequest.getProductTypeId());
			inputParams.put("p_recipe_type", dietRequest.getRecipeType());
			//inputParams.put("p_material_number", dietRequest.getMaterialNumber());
			//inputParams.put("p_pdm_uom", dietRequest.getPdmUom());
			//inputParams.put("p_pdm_density_value", null);
			inputParams.put("p_user_id", dietRequest.getUserId());

			Map<String, Object> outParams = callStoredProcedure(SQLConstants.DIET_INFO_UPDATE, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isNotEmpty(errorMsg) || statusFlag < NumberUtils.INTEGER_ONE) {
				throw new ServiceExecutionException(errorMsg);
			}
			BeanUtils.copyProperties(dietRequest, petParentDTO);
		} catch (SQLException e) {
			LOGGER.error("error while executing updateDiet ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petParentDTO;
	}

    /**
     * @author akumarkhaspa
     * @param dietId
     * @return
     */
	@Override
	public DietDTO getDietById(int dietId) throws ServiceExecutionException {
		final DietDTO dietDto = new DietDTO();
		LOGGER.debug("getDietById called");

		try {
			jdbcTemplate.query(SQLConstants.DIET_INFO_BY_DIET_ID, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					dietDto.setDietId(rs.getInt("DIET_ID"));					
					dietDto.setDietNumber(rs.getString("DIET_NUMBER"));
					dietDto.setDietName(rs.getString("DIET_NAME"));
					dietDto.setCategory(rs.getString("CATEGORY"));
					dietDto.setBrand(rs.getString("BRAND"));
					dietDto.setSubBrand(rs.getString("SUB_BRAND"));
					dietDto.setCompanyName(rs.getString("COMPANY_NAME"));
					dietDto.setProductType(rs.getString("PRODUCT_TYPE"));
					dietDto.setProductTypeId(rs.getInt("PRODUCT_TYPE_ID"));
					dietDto.setDietDensityKcalByKg(rs.getDouble("DIET_DENSITY_KCAL_KG"));
					dietDto.setDietDensityKcalByCup(rs.getDouble("DIET_DENSITY_KCAL_CUP"));
					
					dietDto.setRecipeType(rs.getString("RECIPE_TYPE"));
					dietDto.setPdmUom(rs.getString("PDM_UOM"));
					dietDto.setPdmDensityValue(rs.getDouble("PDM_DENSITY_VALUE"));
					
					//dietDto.setMaterialNumber(rs.getString("MATERIAL_NUMBER"));
					dietDto.setDietDescription(rs.getString("DIET_DESCRIPTION"));
					
					//dietDto.setCreatedDate(rs.getString("CREATEDDATE"));
					//dietDto.setModifiedDate(rs.getString("LATEST_MODIFIEDDATE"));
				}
			}, dietId);
			
			
		} catch (Exception e) {
			LOGGER.error("error while fetching getDietById", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return dietDto;
	}

	/**
	 * @author akumarkhaspa
	 * @param filter
	 * @return
	 */
	@Override
	public String getDietListCount(BaseFilter filter) throws ServiceExecutionException {
		String totalCount;
		LOGGER.debug("getDietCount called");
		try {
			totalCount = selectForObject(SQLConstants.DIET_INFO_LIST_COUNT, String.class, 
				filter.getSearchText(), filter.getFilterType(), filter.getFilterValue());
		} catch (Exception e) {
			LOGGER.error("error while fetching getDietCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return totalCount;
	}

	/**
	 * @author akumarkhaspa
	 * @param filter
	 * @return
	 */
	@Override
	public ArrayList<DietDTO> getDietList(BaseFilter filter) throws ServiceExecutionException {
		ArrayList<DietDTO> dietList = new ArrayList<>();
		LOGGER.debug("getDiet called");
		try {
			jdbcTemplate.query(SQLConstants.DIET_INFO_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					DietDTO dietDto = new DietDTO();
					dietDto.setDietId(rs.getInt("DIET_ID"));					
					dietDto.setDietNumber(rs.getString("DIET_NUMBER"));
					dietDto.setDietName(rs.getString("DIET_NAME"));
					dietDto.setCategory(rs.getString("CATEGORY"));
					dietDto.setBrand(rs.getString("BRAND"));
					dietDto.setSubBrand(rs.getString("SUB_BRAND"));
					dietDto.setCompanyName(rs.getString("COMPANY_NAME"));
					dietDto.setProductType(rs.getString("PRODUCT_TYPE"));
					dietDto.setProductTypeId(rs.getInt("PRODUCT_TYPE_ID"));
					dietDto.setDietDensityKcalByKg(rs.getDouble("DIET_DENSITY_KCAL_KG"));
					dietDto.setDietDensityKcalByCup(rs.getDouble("DIET_DENSITY_KCAL_CUP"));					
					dietDto.setRecipeType(rs.getString("RECIPE_TYPE"));
					dietDto.setPdmUom(rs.getString("PDM_UOM"));
					dietDto.setPdmDensityValue(rs.getDouble("PDM_DENSITY_VALUE"));
					//dietDto.setMaterialNumber(rs.getString("MATERIAL_NUMBER"));
					dietDto.setDietDescription(rs.getString("DIET_DESCRIPTION"));					
					dietDto.setCreatedDate(rs.getString("CREATEDDATE"));
					dietDto.setModifiedDate(rs.getString("LATEST_MODIFIEDDATE"));
					dietDto.setIsEditable((StringUtils.isNotBlank(rs.getString("DIET_NUMBER")) 
							&& rs.getString("DIET_NUMBER").contains("X-")) ? 1 : 0);
					dietList.add(dietDto);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(),
					filter.getSearchText(), filter.getFilterType(), filter.getFilterValue());

		} catch (Exception e) {
			LOGGER.error("error while fetching getDiet", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return dietList;
	}
        
}
