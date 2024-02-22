package com.hillspet.wearables.service.favourite;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.response.IsUserFavouriteResponse;
import com.hillspet.wearables.response.UserFavouriteListResponse;

public interface UserFavouriteService {

	void favourite(int menuId, int entityId, Integer userId, Integer roleId) throws ServiceExecutionException;

	void unfavourite(int menuId, int entityId, Integer userId, Integer roleId) throws ServiceExecutionException;

	UserFavouriteListResponse getFavouriteRecords(BaseFilter filter, Integer userId, Integer roleId)
			throws ServiceExecutionException;

	IsUserFavouriteResponse isFavourite(int menuId, int entityId, Integer userId, Integer roleId)
			throws ServiceExecutionException;

	public boolean checkUserAssociatedStudy(int menuId, int entityId, Integer userId) throws ServiceExecutionException;

}
