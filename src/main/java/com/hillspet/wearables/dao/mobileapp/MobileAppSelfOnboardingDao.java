

/**
 * Created Date: Jan 5, 2020
 * Class Name  : MobileAppSelfOnboardingDao.java
 * 
 * © Copyright 2020 Cambridge Technology Enterprises(India) Pvt. Ltd.,All rights reserved.
 *
 * * * * * * * * * * * * * * * Change History *  * * * * * * * * * * *
 * <Defect Tag>        <Author>        <Date>        <Comments on Change>
 * ID                rmaram          Jan 5, 2020        Mentions the comments on change, for the new file it's not required.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package com.hillspet.wearables.dao.mobileapp;

import java.util.List;
import java.util.Map;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.MobileAppSelfOnboardingDTO;
import com.hillspet.wearables.dto.filter.BaseFilter;


/**
 * Gets Mobile App Feedback Onboarding data from the db.
 * 
 * @author rmaram
 * @since - w1.0
 * @version - w1.0
 */
public interface MobileAppSelfOnboardingDao {

	public Map<String, Integer> getMobileAppSelfOnboardingCount(BaseFilter filter) throws ServiceExecutionException;

	public List<MobileAppSelfOnboardingDTO> getMobileAppSelfOnboarding(BaseFilter filter)
			throws ServiceExecutionException;

	public void assignStudyToPet(int petId, int studyId) throws ServiceExecutionException;

}

