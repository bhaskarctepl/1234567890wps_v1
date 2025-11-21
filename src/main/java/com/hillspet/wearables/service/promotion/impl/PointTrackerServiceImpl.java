package com.hillspet.wearables.service.promotion.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.pointTracker.PointTrackerDao;
import com.hillspet.wearables.dto.PointTracker;
import com.hillspet.wearables.dto.PointTrackerCampaignActivities;
import com.hillspet.wearables.dto.TrackerActivityStatusNotes;
import com.hillspet.wearables.dto.filter.PointTrackerFilter;
import com.hillspet.wearables.response.PointTrackerCampaignActivitiesResponse;
import com.hillspet.wearables.response.PointTrackerResponse;
import com.hillspet.wearables.service.promotion.PointTrackerService;

@Service
public class PointTrackerServiceImpl implements PointTrackerService {

	private static final Logger LOGGER = LogManager.getLogger(PointTrackerServiceImpl.class);

	@Autowired
	private PointTrackerDao pointTrackerDao;

	@Autowired
	private ObjectMapper mapper;

	@Override
	public PointTracker addPointTracker(PointTracker pointTracker) throws ServiceExecutionException {

//		PointTracker pointTracker2 = cOptional.isPresent() ? cOptional.get() : new PointTracker();
		LOGGER.debug("addPointTracker called");
//		HashMap<Integer, Integer> permissionMap = pointTracker.getPermissionMap();

////		Boolean roleExist = false;
//		StringBuffer permissionsStringBuffer = new StringBuffer();
//		for (Map.Entry<Integer, Integer> entry : permissionMap.entrySet()) {
////			roleExist = true;
//			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//			permissionsStringBuffer.append(entry.getKey() + "#" + entry.getValue() + ",");
//		} // TODO: convert to Java 8
//
////		if (roleExist)
//			permissionsStringBuffer.deleteCharAt(permissionsStringBuffer.length() - 1);
//
//		String permissions = permissionsStringBuffer.toString();
//		pointTracker.setPermissions(permissions);

		pointTracker = pointTrackerDao.addPointTracker(pointTracker);
		LOGGER.debug("addPointTracker called");
		return pointTracker;
//	}

//	@Override
//	public List<PointTracker> getAllPointTracker() throws ServiceExecutionException {
//		// TODO Auto-generated method stub
//		
//		LOGGER.debug("getAllUsers called");
//		List<PointTracker> pointTrackerEntities = userDao.getAllPointTracker();
//		List<PointTracker> pointTracker = new ArrayList<>();
//		pointTrackerEntities.stream().forEach(entity -> {
//			pointTracker.add(entity);
//		});
//		LOGGER.debug("getAllUsers completed successfully");
//		return pointTracker;
//	}

	}

	@Override
	public PointTrackerResponse getPointTrackingList(PointTrackerFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getPointTrackingList called");

		String counts = pointTrackerDao.getPointTrackingListCount(filter);
		int searchCount, totalCount = 0;
		try {
			JsonNode jsonCountObj = mapper.readTree(counts);
			totalCount = jsonCountObj.get("totalCount").asInt();
			searchCount = jsonCountObj.get("searchCount").asInt();
		} catch (Exception e) {
			LOGGER.error("error while fetching getPointTrackingList", e);
			throw new ServiceExecutionException(e.getMessage());
		}

		List<PointTracker> pointTrackingList = searchCount > 0 ? pointTrackerDao.getPointTrackingList(filter)
				: new ArrayList<>();

		PointTrackerResponse response = new PointTrackerResponse();
		response.setPointTrackerList(pointTrackingList);
		response.setNoOfElements(pointTrackingList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(searchCount);

		LOGGER.debug("getPointTrackingList list is {}", pointTrackingList);
		LOGGER.debug("getPointTrackingList completed successfully");
		return response;
	}

	@Override
	public void updatePointTracker(PointTracker pointTracker) throws ServiceExecutionException {
		LOGGER.info("updatePointTracker called");
		pointTrackerDao.updatePointTracker(pointTracker);
		LOGGER.info("updatePointTracker completed successfully");

	}

	@Override
	public PointTracker getPointTrackerById(int pointTrackerId) throws ServiceExecutionException {
		LOGGER.info("getPointTrackerById called");
		return pointTrackerDao.getPointTrackerById(pointTrackerId);
	}

	@Override
	public PointTrackerCampaignActivitiesResponse getPointTrackerActiviesList(PointTrackerFilter filter,
			int pointTrackerId) throws ServiceExecutionException {
		LOGGER.debug("getPointTrackerActiviesList called");
		String counts = pointTrackerDao.getPointTrackerActiviesListCount(filter, pointTrackerId);
		int totalCount, searchCount = 0;
		try {
			JsonNode jsonCountObj = mapper.readTree(counts);
			totalCount = jsonCountObj.get("totalCount").asInt();
			searchCount = jsonCountObj.get("searchCount").asInt();
		} catch (Exception e) {
			LOGGER.error("error while fetching getPointTrackerActiviesList", e);
			throw new ServiceExecutionException(e.getMessage());
		}

		List<PointTrackerCampaignActivities> pointTrackerCampaignActivitiesList = totalCount > 0
				? pointTrackerDao.getPointTrackerActiviesList(filter, pointTrackerId)
				: new ArrayList<>();

		PointTrackerCampaignActivitiesResponse response = new PointTrackerCampaignActivitiesResponse();
		response.setPointTrackerCampaignActivitiesList(pointTrackerCampaignActivitiesList);
		response.setNoOfElements(pointTrackerCampaignActivitiesList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(searchCount);

		LOGGER.debug("getPointTrackerActiviesList activities count is {}", pointTrackerCampaignActivitiesList);
		LOGGER.debug("getPointTrackerActiviesList completed successfully");
		return response;
	}

	@Override
	public void updateTrackerActStatus(TrackerActivityStatusNotes trackerActivityStatusNotes)
			throws ServiceExecutionException {
		LOGGER.debug("updateTrackerActStatus called");
		pointTrackerDao.updateTrackerActStatus(trackerActivityStatusNotes);
		LOGGER.debug("updateTrackerActStatus completed successfully");
	}

}
