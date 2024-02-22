package com.hillspet.wearables.dao.plan.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillspet.wearables.common.constants.SQLConstants;
import com.hillspet.wearables.common.constants.WearablesErrorCode;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.BaseDaoImpl;
import com.hillspet.wearables.dao.plan.PlanDao;
import com.hillspet.wearables.dto.CustomUserDetails;
import com.hillspet.wearables.dto.PlanAssociatedDTO;
import com.hillspet.wearables.dto.PlanListDTO;
import com.hillspet.wearables.dto.filter.PlanFilter;
import com.hillspet.wearables.security.Authentication;

@Repository
public class PlanDaoImpl extends BaseDaoImpl implements PlanDao {

	private static final Logger LOGGER = LogManager.getLogger(PlanDaoImpl.class);

	@Autowired
	private Authentication authentication;

	@Autowired
	private ObjectMapper mapper;

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getPlanListCount(PlanFilter filter) throws ServiceExecutionException {
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		LOGGER.debug("getPlanListCount called");
		try {
			counts = selectForObject(SQLConstants.PLAN_GET_LIST_COUNT, String.class, filter.getSearchText(),
					filter.getStudyId(), filter.getStatusId(), filter.getUserId(), filter.getRoleTypeId());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPlanListCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<PlanListDTO> getPlanList(PlanFilter filter) throws ServiceExecutionException {
		List<PlanListDTO> planList = new ArrayList<>();
		LOGGER.debug("getPlanList called");
		try {
			jdbcTemplate.query(SQLConstants.PLAN_GET_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PlanListDTO planListDTO = new PlanListDTO();
					planListDTO.setSlNumber(rs.getInt("slNo"));
					planListDTO.setPlanId(rs.getInt("PLAN_ID"));
					planListDTO.setPlanName(rs.getString("PLAN_NAME"));
					// planListDTO.setStudyId(rs.getString("STUDY_ID"));
					planListDTO.setStudyName(rs.getString("STUDY_NAME"));
					planListDTO.setCreatedDate(rs.getTimestamp("CREATED_DATE").toLocalDateTime());
					planListDTO.setIsActive(rs.getBoolean("IS_ACTIVE"));

					planList.add(planListDTO);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getStudyId(), filter.getStatusId(), filter.getUserId(), filter.getRoleTypeId());

		} catch (Exception e) {
			LOGGER.error("error while fetching getPlanList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return planList;
	}

	@Override
	public PlanListDTO addPlan(PlanListDTO planListDTO) throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_plan_name", planListDTO.getPlanName());
		inputParams.put("p_plan_description", planListDTO.getPlanDescription());
		inputParams.put("p_created_by", planListDTO.getCreatedBy());
		inputParams.put("p_is_active", planListDTO.getIsActiv());
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.PLANS_INSERT, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				// getting the inserted flag value
				Integer planId = (int) outParams.get("last_insert_id");
				planListDTO.setPlanId(planId);
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException("addPlan service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.PLAN_NAME_ALREADY_EXISTS,
									planListDTO.getPlanName())));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			throw new ServiceExecutionException(e.getMessage());
		}
		return planListDTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlanListDTO getPlanById(int planId) throws ServiceExecutionException {
		PlanListDTO plan = new PlanListDTO();
		try {
			LOGGER.debug("getPlanById called");
			CustomUserDetails userDetails = authentication.getAuthUserDetails();
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("p_plan_id", planId);
			inputParams.put("p_user_id", userDetails.getUserId());
			inputParams.put("p_role_type_id", userDetails.getRoleTypeId());
			Map<String, Object> simpleJdbcCallResult = callStoredProcedure(SQLConstants.PLAN_GET_BY_ID, inputParams);
			Iterator<Map.Entry<String, Object>> itr = simpleJdbcCallResult.entrySet().iterator();
			List<PlanAssociatedDTO> StudyAssociatedList = new LinkedList<>();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();

				if (key.equals(SQLConstants.RESULT_SET_1)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(rs -> {
						plan.setPlanName((String) rs.get("PLAN_NAME"));
						plan.setPlanDescription((String) rs.get("PLAN_DESCRIPTION"));
						plan.setCreateDate(rs.get("CREATED_DATE") != null
								? ((LocalDateTime) rs.get("CREATED_DATE")).format(formatter)
								: null);
						plan.setIsActiv((String) rs.get("IS_ACTIVE"));
						plan.setTotalCount((Integer) rs.get("TotalCount"));
					});
				}
				if (key.equals(SQLConstants.RESULT_SET_2)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(rs -> {
						PlanAssociatedDTO planAssociated = new PlanAssociatedDTO();
						planAssociated.setAssociatedDate(
								rs.get("START_DATE") != null ? ((LocalDateTime) rs.get("START_DATE")).format(formatter)
										: null);
						planAssociated.setId((Integer) rs.get("StudyIds"));
						planAssociated.setStudyName((String) rs.get("StudyNames"));
						if (rs.get("StudyIds") != null && rs.get("StudyNames") != null) {
							StudyAssociatedList.add(planAssociated);
						}
						plan.setStudy_Dates(StudyAssociatedList);
					});
				}
			}

		} catch (Exception e) {
			LOGGER.error("error while fetching getPlanById", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return plan;
	}

	@Override
	public PlanListDTO updatePlan(PlanListDTO planListDTO) {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_plan_name", planListDTO.getPlanName());
		inputParams.put("p_plan_description", planListDTO.getPlanDescription());
		inputParams.put("p_is_active", planListDTO.getIsActiv());
		inputParams.put("p_plan_id", planListDTO.getPlanId());
		inputParams.put("p_modified_by", planListDTO.getModifiedBy());
		try {
			LOGGER.info("inputParams::" + inputParams);
			LOGGER.debug("updatePlan called");
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.PLAN_UPDATE, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isNotEmpty(errorMsg) || statusFlag < NumberUtils.INTEGER_ONE) {
				if (statusFlag == -2) {
					throw new ServiceExecutionException("updatePlan service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.PLAN_NAME_ALREADY_EXISTS,
									planListDTO.getPlanName())));
				} else if (statusFlag == -3) {
					throw new ServiceExecutionException("updatePlan service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.PLAN_MAPPED_TO_STUDY_ACTIVE,
									planListDTO.getPlanName())));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			throw new ServiceExecutionException(e.getMessage());
		}
		return planListDTO;
	}

	@Override
	public List<PlanListDTO> getPlans() throws ServiceExecutionException {
		List<PlanListDTO> plans = new ArrayList<>();
		LOGGER.debug("getplans called");
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		try {
			jdbcTemplate.query(SQLConstants.PLAN_GET_PLANS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					PlanListDTO planDto = new PlanListDTO();
					// set the column values to fields like below
					planDto.setPlanId(resultSet.getInt("plan_id"));
					planDto.setPlanName(resultSet.getString("plan_name"));
					plans.add(planDto);
				}
			}, userDetails.getUserId(), userDetails.getRoleTypeId());
		} catch (Exception e) {
			LOGGER.error("error while fetching getplans ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return plans;
	}

	@Override
	public void deletePlan(int planId, int modifiedBy) throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_plan_id", planId);
		inputParams.put("p_modified_by", modifiedBy);
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.PLAN_DELETE, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isNotEmpty(errorMsg) || statusFlag < NumberUtils.INTEGER_ONE) {
				if (statusFlag == -2) {
					throw new ServiceExecutionException("deletePlan service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList(
									new WearablesError(WearablesErrorCode.PLAN_ALREADY_MAPPED_TO_STUDY, errorMsg)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing deletePlan ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

}
