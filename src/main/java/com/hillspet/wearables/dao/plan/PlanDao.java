package com.hillspet.wearables.dao.plan;

import java.util.List;
import java.util.Map;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.PlanListDTO;
import com.hillspet.wearables.dto.filter.PlanFilter;

public interface PlanDao {

	Map<String, Integer> getPlanListCount(PlanFilter filter) throws ServiceExecutionException;

	List<PlanListDTO> getPlanList(PlanFilter filter) throws ServiceExecutionException;

	PlanListDTO addPlan(PlanListDTO planListDTO) throws ServiceExecutionException;

	PlanListDTO getPlanById(int planId) throws ServiceExecutionException;

	PlanListDTO updatePlan(PlanListDTO planListDTO) throws ServiceExecutionException;

	List<PlanListDTO> getPlans() throws ServiceExecutionException;

	void deletePlan(int planId, int modifiedBy) throws ServiceExecutionException;

}