package com.hillspet.wearables.service.timerLog.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.timerLog.TimerLogDao;
import com.hillspet.wearables.dto.TimerLog;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.dto.filter.TimerLogFilter;
import com.hillspet.wearables.response.TimerLogResponse;
import com.hillspet.wearables.service.timerLog.TimerLogService;

@Service
public class TimerLogServiceImpl implements TimerLogService{

	private static final Logger LOGGER = LogManager.getLogger(TimerLogServiceImpl.class);

	@Autowired
	private TimerLogDao timerLogDao;

	@Override
	public TimerLogResponse getTimerLogList(TimerLogFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getTimerLogList called");
		Map<String, Integer> mapper = timerLogDao.getTimerLogListCount(filter);
		int total =	mapper.get("count");
		int totalCount = mapper.get("totalCount");
		List<TimerLog> TimerLogList = total > 0 ? timerLogDao.getTimerLogList(filter) : new ArrayList<>();
		TimerLogResponse response = new TimerLogResponse();
		response.setTimerLogList(TimerLogList);
		response.setNoOfElements(TimerLogList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);
		LOGGER.debug("getTimerLogList timer log count is {}", TimerLogList);
		LOGGER.debug("getPlans completed successfully");
		return response;
	}
}
