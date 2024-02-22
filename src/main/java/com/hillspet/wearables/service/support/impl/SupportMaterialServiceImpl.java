
/**
 * Created Date: 08-Dec-2020
 * Class Name  : SupportMaterialServiceImpl.java
 * Description : Description of the package.
 *
 * © Copyright 2020 Cambridge Technology Enterprises(India) Pvt. Ltd.,All rights reserved.
 *
 * * * * * * * * * * * * * * * Change History *  * * * * * * * * * * *
 * <Defect Tag>        <Author>        <Date>        <Comments on Change>
 * ID                rajesh          08-Dec-2020        Mentions the comments on change, for the new file it's not required.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package com.hillspet.wearables.service.support.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.support.SupportDao;
import com.hillspet.wearables.dao.support.SupportMaterialDao;
import com.hillspet.wearables.dto.SupportMaterialDetails;
import com.hillspet.wearables.dto.SupportMaterialDetailsDTO;
import com.hillspet.wearables.dto.SupportMaterialTypeCountDTO;
import com.hillspet.wearables.dto.filter.SupportMaterialFilter;
import com.hillspet.wearables.request.AddSupportMaterialRequest;
import com.hillspet.wearables.request.UpdateSupportMaterialRequest;
import com.hillspet.wearables.response.SupportMaterialTypeCountResponse;
import com.hillspet.wearables.response.SupportMaterialsDetailsListResponse;
import com.hillspet.wearables.service.support.SupportMaterialService;

/**
 * Enter detailed explanation of the class here..
 * <p>
 * This class implementation of the <tt>Interface or class Name</tt> interface
 * or class. In addition to implementing the <tt>Interface Name</tt> interface,
 * this class provides methods to do other operations. (Mention other methods
 * purpose)
 *
 * <p>
 * More Description about the class need to be entered here.
 *
 * @author rajesh
 * @version Wearables Portal Relase Version..
 * @since Available Since Wearables Portal Version.
 * @see New line seperated Classes or Interfaces related to this class.
 */
@Service
public class SupportMaterialServiceImpl implements SupportMaterialService {

	private static final Logger LOGGER = LogManager.getLogger(SupportServiceImpl.class);

	@Autowired
	private SupportMaterialDao supportDao;

	@Override
	public void addSuportMaterial(List<AddSupportMaterialRequest>  addSupportMaterialRequest)
			throws ServiceExecutionException {
		LOGGER.debug("addSuportMaterial called");
		supportDao.addSuportMaterial(addSupportMaterialRequest);
		LOGGER.debug("addSuportMaterial completed successfully");
	}

	@Override
	public SupportMaterialsDetailsListResponse getMaterialDetailsListById(SupportMaterialFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getMaterialDetailsListById called");
		Map<String, Integer> mapper = supportDao.getMaterialDetailsListByIdCount(filter);
		int total =	mapper.get("count");
		int totalCount = mapper.get("totalCount");
		LOGGER.debug("total " + total);
		LOGGER.debug("totalCount " + totalCount);
		List<SupportMaterialDetailsDTO> supportMateriaDetailList = total > 0 ? supportDao.getMaterialDetailsListById(filter) : new ArrayList<>();
		SupportMaterialsDetailsListResponse response = new SupportMaterialsDetailsListResponse();
		response.setSupportMateriaDetailList(supportMateriaDetailList);
		response.setNoOfElements(supportMateriaDetailList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);
		LOGGER.debug("getMaterialDetailsListById completed successfully");
		return response;
	}

	@Override
	public SupportMaterialTypeCountResponse getSupportMaterialTypeCountList(SupportMaterialFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getSupportMaterialTypeCountList called");
		SupportMaterialTypeCountResponse response = new SupportMaterialTypeCountResponse();
		Map<String, Integer> mapper = supportDao.getSupportMaterialCounts(filter);
		int total =	mapper.get("count");
		int totalCount = mapper.get("totalCount");
		
		LOGGER.debug("getSupportMaterialTypeCountList map" + mapper);
		
		List<SupportMaterialTypeCountDTO> supportList = total > 0 ? supportDao.getSupportMaterialTypeCountList(filter) : new ArrayList<>();
		
		response.setCountInfo(supportList);
		response.setNoOfElements(supportList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);
		LOGGER.debug("getMaterialDetailsListById completed successfully");
		return response; 
	}

	@Override
	public SupportMaterialDetails getSupportMaterialDetails(int materialId) throws ServiceExecutionException {
		LOGGER.debug("getSupportMaterialDetails called");
		SupportMaterialDetails details = supportDao.getMaterialDetailsById(materialId);
		LOGGER.debug("getSupportMaterialDetails completed successfully");
		return details;
	}

	@Override
	public void updateSupportMaterial(UpdateSupportMaterialRequest updateSupportMaterialRequest)
			throws ServiceExecutionException {
		LOGGER.debug("updateSupportMaterial called");
		supportDao.updateSupportMaterial(updateSupportMaterialRequest);
		LOGGER.debug("updateSupportMaterial completed successfully");
	}

	@Override
	public Map<String, String> validateTitle(List<AddSupportMaterialRequest> addSupportMaterialRequest)
			throws ServiceExecutionException {
		LOGGER.debug("addSuportMaterial called");
		return supportDao.validateTitle(addSupportMaterialRequest);
	}

	@Override
	public void deleteSupportMaterial(int supportMaterialId, int modifiedBy) throws ServiceExecutionException {
		LOGGER.debug("deleteSupportMaterial called");
		supportDao.deleteSupportMaterial(supportMaterialId, modifiedBy);
		LOGGER.debug("deleteSupportMaterial completed successfully");
	}

	 
}
