package com.hillspet.wearables.service.timerLog;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.dto.filter.TimerLogFilter;
import com.hillspet.wearables.response.TimerLogResponse;

public interface TimerLogService {

	TimerLogResponse getTimerLogList(TimerLogFilter filter) throws ServiceExecutionException;
}
