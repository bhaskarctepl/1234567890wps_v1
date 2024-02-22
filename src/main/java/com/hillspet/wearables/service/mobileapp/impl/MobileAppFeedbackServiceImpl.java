package com.hillspet.wearables.service.mobileapp.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.mobileapp.MobileAppFeedbackDao;
import com.hillspet.wearables.dto.MobileAppFeedback;
import com.hillspet.wearables.dto.filter.MobileAppFeedbackFilter;
import com.hillspet.wearables.response.MobileAppFeedbackResponse;
import com.hillspet.wearables.service.mobileapp.MobileAppFeedbackService;

@Service
public class MobileAppFeedbackServiceImpl implements MobileAppFeedbackService {

	private static final Logger LOGGER = LogManager.getLogger(MobileAppFeedbackServiceImpl.class);

	@Autowired
	private MobileAppFeedbackDao mobileAppFeedbackDao;
	
	
	@Override
	public MobileAppFeedbackResponse getMobilAppFeedback(MobileAppFeedbackFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getMobilAppFeedback called");		
		
		//int total			= mobileAppFeedbackDao.getMobileAppFeedbackCount(new MobileAppFeedbackFilter());
		//int searchElements  = mobileAppFeedbackDao.getMobileAppFeedbackCount(filter);

		Map<String, Integer> mapper = mobileAppFeedbackDao.getMobileAppFeedbackCount(filter);

		int searchCount =	mapper.get("searchedElementsCount");
		int totalCount = mapper.get("totalCount");
		HashMap<String, Integer> map = new HashMap<>();

		List<MobileAppFeedback> mobileAppFeedbackList = totalCount > 0 ? mobileAppFeedbackDao.getMobileAppFeedback(filter)
				: new ArrayList<>();

		MobileAppFeedbackResponse response = new MobileAppFeedbackResponse();
		response.setMobileAppFeeback(mobileAppFeedbackList);
		response.setNoOfElements(mobileAppFeedbackList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(searchCount);

		LOGGER.debug("getMobilAppFeedback mobileAppFeedbackList is {}", mobileAppFeedbackList);
		LOGGER.debug("getMobilAppFeedback completed successfully");
		return response;
	}

}
