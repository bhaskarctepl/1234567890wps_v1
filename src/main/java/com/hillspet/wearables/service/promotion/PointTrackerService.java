package com.hillspet.wearables.service.promotion;

import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.PointTracker;
import com.hillspet.wearables.dto.TrackerActivityStatusNotes;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.dto.filter.PointTrackerFilter;
import com.hillspet.wearables.response.PointTrackerCampaignActivitiesResponse;
import com.hillspet.wearables.response.PointTrackerResponse;


@Service
public interface PointTrackerService {
	
	public PointTracker addPointTracker(PointTracker pointTracker) throws ServiceExecutionException;
	PointTrackerResponse  getPointTrackingList(PointTrackerFilter filter) throws ServiceExecutionException;
	void updatePointTracker(PointTracker pointTracker) throws ServiceExecutionException;
	 public PointTracker getPointTrackerById(int pointTrackerId) throws ServiceExecutionException;
	 //	List<PointTracker> getAllPointTracker() throws ServiceExecutionException;
	PointTrackerCampaignActivitiesResponse getPointTrackerActiviesList(PointTrackerFilter filter, int pointTrackerId) throws ServiceExecutionException;
	void updateTrackerActStatus(TrackerActivityStatusNotes trackerActivityStatusNotes) throws ServiceExecutionException;
	}
