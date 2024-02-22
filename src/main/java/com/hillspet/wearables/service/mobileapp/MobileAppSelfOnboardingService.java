
/**
 * Created Date: Jan 5, 2021
 * Class Name  : MobileAppSelfOnboardingService.java
 * Description : Description of the package.
 *
 * © Copyright 2020 Cambridge Technology Enterprises(India) Pvt. Ltd.,All rights reserved.
 *
 * * * * * * * * * * * * * * * Change History *  * * * * * * * * * * *
 * <Defect Tag>        <Author>        <Date>        <Comments on Change>
 * ID                rmaram          Jan 5, 2021        Mentions the comments on change, for the new file it's not required.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package com.hillspet.wearables.service.mobileapp;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.response.MobileAppSelfOnboardingResponse;

/**
 *  This is the service class for implementing Mobile App Self Onboarding list details.
 * @author  rmaram 
 */
public interface MobileAppSelfOnboardingService {

	MobileAppSelfOnboardingResponse getMobilAppSelfOnboarding(BaseFilter filter) throws ServiceExecutionException;
	
	void assignStudyToPet(int petId, int studyId) throws ServiceExecutionException;
	
	

}
