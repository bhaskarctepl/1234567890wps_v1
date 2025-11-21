
/**
 * Created Date: Jan 5, 2021
 * Class Name  : MobileAppSelfOnboardingDaoImpl.java
 * Description : Description of the package.
 *
 * © Copyright 2020 Cambridge Technology Enterprises(India) Pvt. Ltd.,All rights reserved.
 *
 * * * * * * * * * * * * * * * Change History *  * * * * * * * * * * *
 * <Defect Tag>        <Author>        <Date>        <Comments on Change>
 * ID                rmaram          Jan 5, 2021        Mentions the comments on change, for the new file it's not required.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package com.hillspet.wearables.dao.mobileapp.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
import com.hillspet.wearables.dao.mobileapp.MobileAppSelfOnboardingDao;
import com.hillspet.wearables.dto.MobileAppSelfOnboardingDTO;
import com.hillspet.wearables.dto.filter.MobileAppSelfOnboardFilter;
import com.hillspet.wearables.security.Authentication;

@Repository
public class MobileAppSelfOnboardingDaoImpl extends BaseDaoImpl implements MobileAppSelfOnboardingDao {

	private static final Logger LOGGER = LogManager.getLogger(MobileAppSelfOnboardingDaoImpl.class);

	@Autowired
	private Authentication authentication;

	@Autowired
	private ObjectMapper mapper;

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getMobileAppSelfOnboardingCount(MobileAppSelfOnboardFilter filter)
			throws ServiceExecutionException {
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		LOGGER.debug("getMobileAppFeedbackCount called");
		try {
			counts = selectForObject(SQLConstants.MOBILE_APP_SELF_ONBOARD_COUNT, String.class, filter.getSearchText(),
					filter.getFilterType(), filter.getFilterValue(), filter.getPetName(), filter.getPetParentName());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getMobileAppSelfOnboardingCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<MobileAppSelfOnboardingDTO> getMobileAppSelfOnboarding(MobileAppSelfOnboardFilter filter)
			throws ServiceExecutionException {
		List<MobileAppSelfOnboardingDTO> mobileAppSelfOnboardingDTOList = new ArrayList<MobileAppSelfOnboardingDTO>();
		LOGGER.debug("getMobileAppSelfOnboarding called");
		try {
			jdbcTemplate.query(SQLConstants.MOBILE_APP_SELF_ONBOARDING_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					MobileAppSelfOnboardingDTO mobileAppSelfOnboardingDTO = new MobileAppSelfOnboardingDTO();

					// set the column values to fields like below
					mobileAppSelfOnboardingDTO.setSlNumber(rs.getInt("slNo"));
					mobileAppSelfOnboardingDTO.setPetId(rs.getInt("pet_id"));
					mobileAppSelfOnboardingDTO.setPetName(rs.getString("pet_name"));
					mobileAppSelfOnboardingDTO.setPetParentName(rs.getString("pet_parent_name"));
					mobileAppSelfOnboardingDTO.setEmail(rs.getString("email"));
					mobileAppSelfOnboardingDTO.setDeviceId(rs.getString("device_id"));
					mobileAppSelfOnboardingDTO.setIsVipPet(rs.getBoolean("IS_VIP_PET"));

					mobileAppSelfOnboardingDTOList.add(mobileAppSelfOnboardingDTO);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getFilterType(), filter.getFilterValue(), filter.getPetName(), filter.getPetParentName());

		} catch (Exception e) {
			LOGGER.error("error while fetching getMobileAppSelfOnboarding", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return mobileAppSelfOnboardingDTOList;
	}

	@Override
	public void assignStudyToPet(int petId, int studyId) throws ServiceExecutionException {
		LOGGER.info("assignStudyToPet started");
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_pet_id", petId);
		inputParams.put("p_study_id", studyId);
		inputParams.put("p_created_by", authentication.getAuthUserDetails().getUserId());
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.PET_ASSIGN_STUDY, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info(String.format("Study (%s) to Pet (%s) successfully", studyId, petId));
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"assignStudyToPet service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.STUDY_ALREADY_ASSIGNED_TO_PET)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing assignStudyToPet ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

}