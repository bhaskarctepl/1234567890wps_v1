
/**
 * Created Date: Nov 9, 2020
 * Class Name  : MobileAppFeedbackDao.java
 * Description : Description of the package.
 *
 * © Copyright 2008 Cambridge Technology Enterprises(India) Pvt. Ltd.,All rights reserved.
 *
 * * * * * * * * * * * * * * * Change History *  * * * * * * * * * * *
 * <Defect Tag>        <Author>        <Date>        <Comments on Change>
 * ID                rmaram          Nov 9, 2020        Mentions the comments on change, for the new file it's not required.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package com.hillspet.wearables.dao.mobileapp;

import java.util.List;
import java.util.Map;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.MobileAppFeedback;
import com.hillspet.wearables.dto.filter.MobileAppFeedbackFilter;

/**
 * Gets Mobile App Feedback from the db.
 * 
 * @author rmaram
 * @since - w1.0
 * @version - w1.0
 */
public interface MobileAppFeedbackDao {

	public Map<String, Integer> getMobileAppFeedbackCount(MobileAppFeedbackFilter filter) throws ServiceExecutionException;

	public List<MobileAppFeedback> getMobileAppFeedback(MobileAppFeedbackFilter filter)
			throws ServiceExecutionException;

}
