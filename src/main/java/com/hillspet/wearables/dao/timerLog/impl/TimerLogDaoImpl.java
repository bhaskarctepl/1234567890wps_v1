package com.hillspet.wearables.dao.timerLog.impl;

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
import com.hillspet.wearables.dao.timerLog.TimerLogDao;
import com.hillspet.wearables.dto.TimerLog;
import com.hillspet.wearables.dto.filter.TimerLogFilter;

@Repository
public class TimerLogDaoImpl extends BaseDaoImpl implements TimerLogDao {

	private static final Logger LOGGER = LogManager.getLogger(TimerLogDaoImpl.class);

	@Autowired
	private ObjectMapper mapper;

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getTimerLogListCount(TimerLogFilter filter) throws ServiceExecutionException {
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		LOGGER.debug("getTimerLogListCount called");
		try {

			counts = selectForObject(SQLConstants.TIMER_LOG_GET_LIST_COUNT, String.class,
					// filter.getFilterType(), filter.getFilterValue(),
					filter.getSearchText(), filter.getStartDate(), filter.getEndDate(), filter.getCategory(), filter.getPetName(), filter.getPetParentName());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getTimerLogListCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<TimerLog> getTimerLogList(TimerLogFilter filter) throws ServiceExecutionException {
		List<TimerLog> timerLogList = new ArrayList<>();
		LOGGER.debug("getTimerLogList called");
		try {
			jdbcTemplate.query(SQLConstants.TIMER_LOG_GET_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {

					TimerLog timerLog = new TimerLog();
//					timerLog.setSlNumber(rs.getInt("slNo"));
					timerLog.setPetTimerLogId(rs.getInt("PET_TIMER_LOG_ID"));
					timerLog.setAssetNumber(rs.getString("DEVICE_NUMBER"));
					timerLog.setCategory(rs.getString("CATEGORY"));
					timerLog.setDuration(rs.getString("DURATION"));
					timerLog.setPetId(rs.getInt("PET_ID"));
					timerLog.setPetName(rs.getString("PET_NAME"));
					timerLog.setPetParentId(rs.getInt("PET_PARENT_ID"));
					timerLog.setPetParentName(rs.getString("PET_PARENT_NAME"));
					timerLog.setRecordName(rs.getString("REC_NAME"));
					timerLog.setTimerDate(rs.getString("TIMER_DATE"));
					timerLog.setIsActive(rs.getBoolean("IS_ACTIVE"));
					timerLog.setIsPetVip(rs.getBoolean("IS_VIP_PET"));

					timerLogList.add(timerLog);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getSortBy(), filter.getOrder(), filter.getSearchText(),
					// filter.getFilterType(), filter.getFilterValue()
					filter.getStartDate(), filter.getEndDate(), filter.getCategory(), filter.getPetName(), filter.getPetParentName());

		} catch (Exception e) {
			LOGGER.error("error while fetching getTimerLogList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return timerLogList;
	}

}
