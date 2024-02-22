package com.hillspet.wearables.dao.favourite;

import java.util.List;
import java.util.Map;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.UserFavourite;
import com.hillspet.wearables.dto.UserFavouriteListDTO;
import com.hillspet.wearables.dto.filter.BaseFilter;

public interface UserFavouriteDao {

	void favourite(int menuId, int entityId, Integer userId, Integer roleId) throws ServiceExecutionException;

	void unfavourite(int menuId, int entityId, Integer userId, Integer roleId) throws ServiceExecutionException;

	String getFavouriteRecordsCount(BaseFilter filter, Integer userId, Integer roleId) throws ServiceExecutionException;

	List<UserFavouriteListDTO> getFavouriteRecords(BaseFilter filter, Integer userId, Integer roleId)
			throws ServiceExecutionException;

	UserFavourite isFavourite(int menuId, int entityId, Integer userId, Integer roleId)
			throws ServiceExecutionException;

	boolean checkUserAssociatedStudy(int menuId, int entityId, Integer userId) throws ServiceExecutionException;

}
