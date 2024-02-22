package com.hillspet.wearables.dao.timerLog;

import java.util.List;
import java.util.Map;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.TimerLog;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.dto.filter.TimerLogFilter;

public interface TimerLogDao {

	Map<String, Integer> getTimerLogListCount(TimerLogFilter filter) throws ServiceExecutionException;

	List<TimerLog> getTimerLogList(TimerLogFilter filter) throws ServiceExecutionException;
}
