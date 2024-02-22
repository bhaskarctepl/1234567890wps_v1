package com.hillspet.wearables.dao.favourite.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.hillspet.wearables.common.constants.SQLConstants;
import com.hillspet.wearables.common.constants.WearablesErrorCode;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.BaseDaoImpl;
import com.hillspet.wearables.dao.favourite.UserFavouriteDao;
import com.hillspet.wearables.dto.UserFavourite;
import com.hillspet.wearables.dto.UserFavouriteListDTO;
import com.hillspet.wearables.dto.filter.BaseFilter;

@Repository
public class UserFavouriteDaoImpl extends BaseDaoImpl implements UserFavouriteDao {

	private static final Logger LOGGER = LogManager.getLogger(UserFavouriteDaoImpl.class);

	@Override
	public void favourite(int menuId, int entityId, Integer userId, Integer roleId) throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_menu_id", menuId);
		inputParams.put("p_entity_id", entityId);
		inputParams.put("p_user_id", userId);
		inputParams.put("p_role_id", roleId);
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.USER_FAVOURITE_INSERT, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				// getting the inserted flag value
				Integer favouriteId = (int) outParams.get("last_insert_id");
				LOGGER.info("favourite has been created successfully, favourite id is ", favouriteId);
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException("favourite service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.FAVOURITE_ALREADY_EXISTS)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing favourite ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public void unfavourite(int menuId, int entityId, Integer userId, Integer roleId) throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_menu_id", menuId);
		inputParams.put("p_entity_id", entityId);
		inputParams.put("p_user_id", userId);
		inputParams.put("p_role_id", roleId);
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.USER_FAVOURITE_UPDATE, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("Successfully unfavourited the reacord, entitiy id is ", entityId);
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException("unfavourite service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.FAVOURITE_DOES_NOT_EXISTS, entityId,
									menuId)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing favourite ", e);
			throw new ServiceExecutionException(e.getMessage());
		}

	}

	@Override
	public String getFavouriteRecordsCount(BaseFilter filter, Integer userId, Integer roleId)
			throws ServiceExecutionException {
		String totalCount = "";
		LOGGER.debug("getFavouriteRecordsCount called");
		try {
			totalCount = selectForObject(SQLConstants.USER_FAVOURITES_LIST_COUNT, String.class, userId, roleId,
					filter.getSearchText(), filter.getFilterType(), filter.getFilterValue());
		} catch (Exception e) {
			LOGGER.error("error while fetching getFavouriteRecordsCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return totalCount;
	}

	@Override
	public List<UserFavouriteListDTO> getFavouriteRecords(BaseFilter filter, Integer userId, Integer roleId)
			throws ServiceExecutionException {
		List<UserFavouriteListDTO> favouriteList = new ArrayList<>();
		LOGGER.debug("getPetParentList called");
		try {
			jdbcTemplate.query(SQLConstants.USER_FAVOURITE_GET_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					UserFavouriteListDTO favouriteListDTO = new UserFavouriteListDTO();
					favouriteListDTO.setSlNumber(rs.getInt("slNo"));
					favouriteListDTO.setFavouriteId(rs.getInt("FAVOURITE_ID"));
					favouriteListDTO.setMenuId(rs.getInt("MENU_ID"));
					favouriteListDTO.setMenuName(rs.getString("MENU_NAME"));
					if(rs.getInt("MENU_ID") == 14) {
						favouriteListDTO.setPetStudyId(rs.getInt("PET_STUDY_ID"));
					}
					favouriteListDTO.setEntityId(rs.getInt("ENTITY_ID"));
					favouriteListDTO.setUserId(rs.getInt("USER_ID"));
					favouriteListDTO.setRoleId(rs.getInt("ROLE_ID"));
					favouriteListDTO.setCreatedDate(rs.getTimestamp("CREATED_DATE").toLocalDateTime());
					favouriteListDTO.setModifiedDate(rs.getTimestamp("MODIFIED_DATE").toLocalDateTime());
					favouriteListDTO.setRecordName(rs.getString("RECORD_NAME"));
					favouriteList.add(favouriteListDTO);
					
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), userId, roleId,
					filter.getSearchText(), filter.getFilterType(), filter.getFilterValue());

		} catch (Exception e) {
			LOGGER.error("error while fetching getFavouriteRecords", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return favouriteList;
	}

	@Override
	public UserFavourite isFavourite(int menuId, int entityId, Integer userId, Integer roleId)
			throws ServiceExecutionException {
		LOGGER.debug("isFavourite called");
		UserFavourite favourite = new UserFavourite();
		try {
			int isFavourite = selectForObject(SQLConstants.IS_USER_FAVOURITES, Integer.class, menuId, entityId, userId,
					roleId);
			favourite.setEntityId(entityId);
			favourite.setMenuId(menuId);
			favourite.setIsFavourite(isFavourite);
		} catch (Exception e) {
			LOGGER.error("error while fetching isFavourite", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return favourite;
	}

	@Override
	public boolean checkUserAssociatedStudy(int menuId, int entityId, Integer userId) throws ServiceExecutionException {
		LOGGER.debug("checkUserAssociatedStudy called");
		int isStudyExist = 0;
		try {
			isStudyExist = selectForObject(SQLConstants.FN_GET_USER_VALIDATE_ASSOCIATED_STUDY, Integer.class, menuId, entityId, userId);
			LOGGER.debug("checkUserAssociatedStudy isStudyExist " + isStudyExist);
		} catch (Exception e) {
			LOGGER.error("error while fetching checkUserAssociatedStudy", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return (isStudyExist > 0 ? true : false);
	}

}
