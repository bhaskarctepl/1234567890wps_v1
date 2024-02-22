
/**
 * Created Date: Nov 9, 2020
 * Class Name  : MobileAppFeedbackDaoImpl.java
 * Description : Description of the package.
 *
 * © Copyright 2008 Cambridge Technology Enterprises(India) Pvt. Ltd.,All rights reserved.
 *
 * * * * * * * * * * * * * * * Change History *  * * * * * * * * * * *
 * <Defect Tag>        <Author>        <Date>        <Comments on Change>
 * ID                rmaram          Nov 9, 2020        Mentions the comments on change, for the new file it's not required.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package com.hillspet.wearables.dao.mobileapp.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillspet.wearables.common.constants.SQLConstants;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.BaseDaoImpl;
import com.hillspet.wearables.dao.mobileapp.MobileAppFeedbackDao;
import com.hillspet.wearables.dto.MobileAppFeedback;
import com.hillspet.wearables.dto.filter.MobileAppFeedbackFilter;

@Repository
public class MobileAppFeedbackDaoImpl extends BaseDaoImpl implements MobileAppFeedbackDao {

	private static final Logger LOGGER = LogManager.getLogger(MobileAppFeedbackDaoImpl.class);

	@Autowired
	private ObjectMapper mapper;

	@Override
	public Map<String, Integer> getMobileAppFeedbackCount(MobileAppFeedbackFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getMobileAppFeedbackCount called");
		HashMap<String, Integer> map = new HashMap<>();
		String countJson = "";
		try {
			countJson = selectForObject(SQLConstants.MOBILE_APP_FEEDBACK_COUNT, String.class, filter.getSearchText(),
					filter.getPhoneModel(), filter.getPageName(),
					// filter.getFilterType(),
					// filter.getFilterValue(),
					filter.getStartDate(), filter.getEndDate()

			);
			map = mapper.readValue(countJson, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getMobileAppFeedbackCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<MobileAppFeedback> getMobileAppFeedback(MobileAppFeedbackFilter filter)
			throws ServiceExecutionException {
		List<MobileAppFeedback> mobileAppFeedbackList = new ArrayList<>();
		LOGGER.debug("getMobileAppFeedback called");
		try {
			jdbcTemplate.query(SQLConstants.MOBILE_APP_FEEDBACK_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					MobileAppFeedback mobileAppFeedback = new MobileAppFeedback();

					// set the column values to fields like below

					mobileAppFeedback.setStudyName(rs.getString("study_name"));
					mobileAppFeedback.setPetOwnerName(rs.getString("pet_owner_name"));
					mobileAppFeedback.setPetName(rs.getString("pet_name"));
					mobileAppFeedback.setPageName(rs.getString("page_name"));
					mobileAppFeedback.setDeviceType(rs.getString("device_type"));
					mobileAppFeedback.setFeedback(rs.getString("feed_back_text"));
					// mobileAppFeedback.setPlan(rs.getString("plan_name"));

					mobileAppFeedback.setFeedbackDate(rs.getTimestamp("created_date").toLocalDateTime());
					mobileAppFeedback.setPetId(rs.getInt("PET_ID"));
					mobileAppFeedback.setPetParentId(rs.getInt("PET_PARENT_ID"));
					mobileAppFeedback.setPetStudyId(rs.getInt("PET_STUDY_ID"));

					mobileAppFeedbackList.add(mobileAppFeedback);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getSortBy(), filter.getOrder(), filter.getSearchText(),
					// filter.getFilterType(), filter.getFilterValue()
					filter.getStartDate(), filter.getEndDate(), filter.getPhoneModel(), filter.getPageName(),
					filter.getUserId());

		} catch (Exception e) {
			LOGGER.error("error while fetching getMobileAppFeedback", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return mobileAppFeedbackList;
	}

}
