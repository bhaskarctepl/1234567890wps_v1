package com.hillspet.wearables.service.plan;

import java.util.List;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.PlanListDTO;
import com.hillspet.wearables.dto.filter.PlanFilter;
import com.hillspet.wearables.response.PlansResponse;

public interface PlanService {

	PlansResponse getPlanList(PlanFilter filter) throws ServiceExecutionException;

	PlanListDTO addPlan(PlanListDTO planListDTO) throws ServiceExecutionException;

	public PlanListDTO getPlanById(int planId) throws ServiceExecutionException;

	void updatePlan(PlanListDTO planListDTO) throws ServiceExecutionException;

	List<PlanListDTO> getPlans() throws ServiceExecutionException;

	void deletePlan(int planId, int modifiedBy) throws ServiceExecutionException;

}
