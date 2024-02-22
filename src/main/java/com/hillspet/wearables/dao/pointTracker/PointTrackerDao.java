package com.hillspet.wearables.dao.pointTracker;

import java.util.List;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.PointTracker;
import com.hillspet.wearables.dto.PointTrackerCampaignActivities;
import com.hillspet.wearables.dto.TrackerActivityStatusNotes;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.dto.filter.PointTrackerFilter;

public interface PointTrackerDao {

	PointTracker addPointTracker(PointTracker pointTracker);

	String getPointTrackingListCount(PointTrackerFilter filter) throws ServiceExecutionException;

	List<PointTracker> getPointTrackingList(PointTrackerFilter filter) throws ServiceExecutionException;

	PointTracker updatePointTracker(PointTracker pointTracker) throws ServiceExecutionException;

	PointTracker getPointTrackerById(int pointTrackerId) throws ServiceExecutionException;

	String getPointTrackerActiviesListCount(PointTrackerFilter filter, int pointTrackerId)
			throws ServiceExecutionException;

	List<PointTrackerCampaignActivities> getPointTrackerActiviesList(PointTrackerFilter filter, int pointTrackerId)
			throws ServiceExecutionException;

	void updateTrackerActStatus(TrackerActivityStatusNotes trackerActivityStatusNotes) throws ServiceExecutionException;
}