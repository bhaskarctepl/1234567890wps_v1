package com.hillspet.wearables.service.plan.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.plan.PlanDao;
import com.hillspet.wearables.dto.PlanListDTO;
import com.hillspet.wearables.dto.filter.PlanFilter;
import com.hillspet.wearables.response.PlansResponse;
import com.hillspet.wearables.service.plan.PlanService;

@Service
public class PlanServiceImpl implements PlanService {

	private static final Logger LOGGER = LogManager.getLogger(PlanServiceImpl.class);

	@Autowired
	private PlanDao planDao;

	@Override
	public PlansResponse getPlanList(PlanFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getPlans called");
		Map<String, Integer> mapper = planDao.getPlanListCount(filter);
		int total =	mapper.get("count");
		int totalCount = mapper.get("totalCount");
		List<PlanListDTO> planList = total > 0 ? planDao.getPlanList(filter) : new ArrayList<>();
		PlansResponse response = new PlansResponse();
		response.setPlansList(planList);
		response.setNoOfElements(planList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);
		LOGGER.debug("getPlans plan count is {}", planList);
		LOGGER.debug("getPlans completed successfully");
		return response;
	}

	@Override
	public List<PlanListDTO> getPlans() throws ServiceExecutionException {
		LOGGER.debug("getPlans called");
		List<PlanListDTO> plans = planDao.getPlans();
		LOGGER.debug("getPlans list", plans);
		return plans;
	}

	@Override
	public PlanListDTO addPlan(PlanListDTO planListDTO) throws ServiceExecutionException {
		LOGGER.debug("addPointTracker called");
		planListDTO = planDao.addPlan(planListDTO);
		LOGGER.debug("addPointTracker called");
		return planListDTO;
	}

	@Override
	public PlanListDTO getPlanById(int planId) throws ServiceExecutionException {
		LOGGER.info("getPlanById called");
		return planDao.getPlanById(planId);
	}

	@Override
	public void updatePlan(PlanListDTO planListDTO) throws ServiceExecutionException {
		LOGGER.info("updatePlan called");
		planDao.updatePlan(planListDTO);
		LOGGER.info("updatePlan completed successfully");
	}
	
	@Override
	public void deletePlan(int planId, int modifiedBy) throws ServiceExecutionException {
		LOGGER.info("deletePlan called");
		planDao.deletePlan(planId, modifiedBy);
	}
}
