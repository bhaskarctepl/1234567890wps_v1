package com.hillspet.wearables.service.mobileapp.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.mobileapp.MobileAppSelfOnboardingDao;
import com.hillspet.wearables.dto.MobileAppSelfOnboardingDTO;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.response.MobileAppSelfOnboardingResponse;
import com.hillspet.wearables.service.mobileapp.MobileAppSelfOnboardingService;

@Service
public class MobileAppSelfOnboardingServiceImpl implements MobileAppSelfOnboardingService {

	private static final Logger LOGGER = LogManager.getLogger(MobileAppSelfOnboardingServiceImpl.class);

	@Autowired
	private MobileAppSelfOnboardingDao mobileAppSelfOnboardingDao;

	@Override
	public MobileAppSelfOnboardingResponse getMobilAppSelfOnboarding(BaseFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getMobilAppSelfOnboarding called");
		Map<String, Integer> mapper = mobileAppSelfOnboardingDao.getMobileAppSelfOnboardingCount(filter);
		int total =	mapper.get("count");
		int totalCount = mapper.get("totalCount");
		List<MobileAppSelfOnboardingDTO> mobileAppSelfOnboardingDTOList = total > 0 ? mobileAppSelfOnboardingDao.getMobileAppSelfOnboarding(filter): new ArrayList<>();

		MobileAppSelfOnboardingResponse response = new MobileAppSelfOnboardingResponse();
		response.setMobileAppSelfOnboarding(mobileAppSelfOnboardingDTOList);
		response.setNoOfElements(mobileAppSelfOnboardingDTOList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);

		LOGGER.debug("getMobilAppSelfOnboarding list is {}", mobileAppSelfOnboardingDTOList);
		LOGGER.debug("getMobilAppSelfOnboarding completed successfully");
		return response;
	}
	
	
	
	@Override
	public void assignStudyToPet(int petId, int studyId)
			throws ServiceExecutionException {
		LOGGER.debug("assignStudyToPet called");	
		mobileAppSelfOnboardingDao.assignStudyToPet(petId, studyId);
		LOGGER.debug("assignStudyToPet completed successfully");
	}

}
