package com.hillspet.wearables.service.favourite.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.favourite.UserFavouriteDao;
import com.hillspet.wearables.dto.UserFavourite;
import com.hillspet.wearables.dto.UserFavouriteListDTO;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.response.IsUserFavouriteResponse;
import com.hillspet.wearables.response.UserFavouriteListResponse;
import com.hillspet.wearables.service.favourite.UserFavouriteService;

@Service
public class UserFavouriteServiceImpl implements UserFavouriteService {

	private static final Logger LOGGER = LogManager.getLogger(UserFavouriteServiceImpl.class);

	@Autowired
	private UserFavouriteDao userFavouriteDao;

	@Autowired
	private ObjectMapper mapper;

	@Override
	public void favourite(int menuId, int entityId, Integer userId, Integer roleId) throws ServiceExecutionException {
		LOGGER.debug("favourite called");
		userFavouriteDao.favourite(menuId, entityId, userId, roleId);
		LOGGER.debug("favourite completed successfully");
	}

	@Override
	public void unfavourite(int menuId, int entityId, Integer userId, Integer roleId) throws ServiceExecutionException {
		LOGGER.debug("unfavourite called");
		userFavouriteDao.unfavourite(menuId, entityId, userId, roleId);
		LOGGER.debug("unfavourite completed successfully");
	}

	@Override
	public UserFavouriteListResponse getFavouriteRecords(BaseFilter filter, Integer userId, Integer roleId)
			throws ServiceExecutionException {
		LOGGER.debug("getAllFavouriteRecords called");
		String totalCounts = userFavouriteDao.getFavouriteRecordsCount(filter, userId, roleId);
		HashMap<String, Integer> countMap = new HashMap<>();
		int searchCount, totalCount = 0;
		try {
			countMap = mapper.readValue(totalCounts, HashMap.class);
			searchCount = countMap.get("searchCount");
			totalCount = countMap.get("totalCount");
		} catch (Exception e) {
			LOGGER.error("error while fetching getFavouriteRecords", e);
			throw new ServiceExecutionException(e.getMessage());
		}

		List<UserFavouriteListDTO> favouriteList = searchCount > 0
				? userFavouriteDao.getFavouriteRecords(filter, userId, roleId)
				: new ArrayList<>();

		UserFavouriteListResponse response = new UserFavouriteListResponse();
		response.setFavouriteList(favouriteList);
		response.setNoOfElements(favouriteList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(searchCount);

		LOGGER.debug("getAllFavouriteRecords pet parent count is {}", favouriteList);
		LOGGER.debug("getAllFavouriteRecords completed successfully");

		return response;
	}

	@Override
	public IsUserFavouriteResponse isFavourite(int menuId, int entityId, Integer userId, Integer roleId)
			throws ServiceExecutionException {
		LOGGER.debug("isFavourite called");
		UserFavourite userFavourite = userFavouriteDao.isFavourite(menuId, entityId, userId, roleId);
		IsUserFavouriteResponse response = new IsUserFavouriteResponse();
		response.setFavourite(userFavourite);
		LOGGER.debug("isFavourite completed successfully");
		return response;
	}

	@Override
	public boolean checkUserAssociatedStudy(int menuId, int entityId, Integer userId) throws ServiceExecutionException {
		LOGGER.debug("checkUserAssociatedStudy called");
		boolean userFavourite = userFavouriteDao.checkUserAssociatedStudy(menuId, entityId, userId);
		LOGGER.debug("checkUserAssociatedStudy completed successfully");
		return userFavourite;
	}

}
