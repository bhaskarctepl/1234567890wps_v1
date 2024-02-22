package com.hillspet.wearables.service.petparent.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.petparent.PetParentDao;
import com.hillspet.wearables.dto.PetParentDTO;
import com.hillspet.wearables.dto.PetParentListDTO;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.request.PetParentRequest;
import com.hillspet.wearables.request.PetParentValidateEmailRequest;
import com.hillspet.wearables.response.PetParentAddressResponse;
import com.hillspet.wearables.response.PetParentListResponse;
import com.hillspet.wearables.service.petparent.PetParentService;

@Service
public class PetParentServiceImpl implements PetParentService {

	private static final Logger LOGGER = LogManager.getLogger(PetParentServiceImpl.class);

	@Autowired
	private PetParentDao petParentDao;

	@Autowired
	private ObjectMapper mapper;

	@Override
	public PetParentDTO addPetParent(PetParentRequest petParentRequest) throws ServiceExecutionException {
		LOGGER.debug("addPetParent called");
		PetParentDTO petParentDto = petParentDao.addPetParent(petParentRequest);
		LOGGER.debug("addPetParent completed successfully");
		return petParentDto;
	}

	@Override
	public PetParentDTO updatePetParent(PetParentRequest petParentRequest) throws ServiceExecutionException {
		LOGGER.debug("updatePetParent called");
		PetParentDTO petParentDto = petParentDao.updatePetParent(petParentRequest);
		LOGGER.debug("updatePetParent completed successfully");
		return petParentDto;

	}

	@Override
	public PetParentDTO getPetParentById(int petParentId) throws ServiceExecutionException {
		LOGGER.debug("getPetParentById called");
		PetParentDTO petParentDto = petParentDao.getPetParentById(petParentId);
		LOGGER.debug("getPetParentById completed successfully");
		return petParentDto;
	}

	@Override
	public PetParentListResponse getPetParents() throws ServiceExecutionException {
		LOGGER.debug("getPetParents called");

		List<PetParentListDTO> petParentList = petParentDao.getPetParents();

		PetParentListResponse response = new PetParentListResponse();
		response.setPetParentList(petParentList.size() > 0 ? petParentList : new ArrayList<>());

		LOGGER.debug("getPetParentList pet parent count is {}", petParentList);
		LOGGER.debug("getPetParentList completed successfully");
		return response;
	}

	@Override
	public PetParentListResponse getPetParentList(BaseFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getPetParentList called");

		BaseFilter baseFilter = new BaseFilter();
		baseFilter.setUserId(filter.getUserId());

		String totalCounts = petParentDao.getPetParentListCount(filter);
		HashMap<String, Integer> countMap = new HashMap<>();
		int searchCount, totalCount = 0;
		try {
			countMap = mapper.readValue(totalCounts, HashMap.class);
			searchCount = countMap.get("searchCount");
			totalCount = countMap.get("totalCount");
		} catch (Exception e) {
			LOGGER.error("error while fetching getFavouriteRecords", e);
			throw new ServiceExecutionException(e.getMessage());
		}

		List<PetParentListDTO> petParents = searchCount > 0 ? petParentDao.getPetParentList(filter) : new ArrayList<>();

		PetParentListResponse response = new PetParentListResponse();
		response.setPetParents(petParents);
		response.setNoOfElements(petParents.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(searchCount);

		LOGGER.debug("getPetParentList pet parent count is {}", petParents);
		LOGGER.debug("getPetParentList completed successfully");
		return response;
	}

	@Override
	public void deletePetParent(int petParentId, int modifiedBy) throws ServiceExecutionException {
		LOGGER.debug("deletePetParent called");
		petParentDao.deletePetParent(petParentId, modifiedBy);
		LOGGER.debug("deletePetParent completed successfully");
	}

	@Override
	public Map<String, Object> validatePetParentEmail(PetParentValidateEmailRequest petParentRequest)
			throws ServiceExecutionException {
		LOGGER.debug("validatePetParentEmail called");
		return petParentDao.validatePetParentEmail(petParentRequest);
	}

	@Override
	public PetParentAddressResponse getPetParentAddressHistoryById(int petParentId) throws ServiceExecutionException {
		LOGGER.debug("getPetParentAddressHistoryById called");
		PetParentAddressResponse petParentAddresses = petParentDao.getPetParentAddressHistoryById(petParentId);
		LOGGER.debug("getPetParentAddressHistoryById completed successfully");
		return petParentAddresses;
	}
	
	@Override
	public Map<String, Object>  validateUnAssignedPetAddress(int petId, int petParentId) throws ServiceExecutionException {
		LOGGER.debug("validateUnAssignedPetAddress called");
		return petParentDao.validateUnAssignedPetAddress(petId, petParentId);
	}
}
