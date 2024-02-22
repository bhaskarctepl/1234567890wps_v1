
/**
 * Created Date: Nov 9, 2020
 * Class Name  : MobileAppFeedbackService.java
 * Description : Description of the package.
 *
 * © Copyright 2008 Cambridge Technology Enterprises(India) Pvt. Ltd.,All rights reserved.
 *
 * * * * * * * * * * * * * * * Change History *  * * * * * * * * * * *
 * <Defect Tag>        <Author>        <Date>        <Comments on Change>
 * ID                rmaram          Nov 9, 2020        Mentions the comments on change, for the new file it's not required.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package com.hillspet.wearables.service.mobileapp;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.filter.MobileAppFeedbackFilter;
import com.hillspet.wearables.response.MobileAppFeedbackResponse;

/**
 * This is the service class for implementing Mobile App Feedback list details.
 * 
 * @author rmaram
 * @since w1.0
 * @version w1.0
 * 
 */
public interface MobileAppFeedbackService {

	MobileAppFeedbackResponse getMobilAppFeedback(MobileAppFeedbackFilter filter) throws ServiceExecutionException;

}
