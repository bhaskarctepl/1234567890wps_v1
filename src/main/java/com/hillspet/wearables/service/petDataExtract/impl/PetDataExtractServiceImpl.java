package com.hillspet.wearables.service.petDataExtract.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.petDataExtract.PetDataExtractDao;
import com.hillspet.wearables.dto.PetDataExtractConfigListDTO;
import com.hillspet.wearables.dto.PetDataExtractStreamDTO;
import com.hillspet.wearables.dto.PetDataExtractStreamsDTO;
import com.hillspet.wearables.dto.PetListDTO;
import com.hillspet.wearables.dto.filter.DuplicatePetsFilter;
import com.hillspet.wearables.request.PetDataExtractStreamsRequest;
import com.hillspet.wearables.response.PetDataExtractConfigListResponse;
import com.hillspet.wearables.response.PetDataExtractListResponse;
import com.hillspet.wearables.service.petDataExtract.PetDataExtractService;

@Service
public class PetDataExtractServiceImpl implements PetDataExtractService {

	private static final Logger LOGGER = LogManager.getLogger(PetDataExtractServiceImpl.class);

	@Autowired
	private PetDataExtractDao petDataExtractDao;

	@Override
	public PetDataExtractStreamsDTO getPetDataExtractById(int petId) throws ServiceExecutionException {
		LOGGER.debug("getPetDataExtractById called");
		PetDataExtractStreamsDTO petDataExtractStreamsDTO = new PetDataExtractStreamsDTO();
		PetListDTO petListDTO = petDataExtractDao.getPetListById(petId);
		List<PetDataExtractStreamDTO> petDataExtractStreamList = petDataExtractDao.getPetDataExtractById(petId);
		petDataExtractStreamsDTO.setPetListDTO(petListDTO);
		petDataExtractStreamsDTO.setPetDataExtractStream(petDataExtractStreamList);
		LOGGER.debug("getPetDataExtractById completed successfully");
		return petDataExtractStreamsDTO;
	}

	@Override
	public PetDataExtractStreamDTO addPetExtractStreams(PetDataExtractStreamsRequest petDataExtractStreamsRequest)
			throws ServiceExecutionException {
		LOGGER.debug("updatePetExtractData called");
		PetDataExtractStreamDTO petDataExtractStreamDTO = petDataExtractDao
				.addPetExtractStreams(petDataExtractStreamsRequest);
		LOGGER.debug("updatePetExtractData completed successfully");
		return petDataExtractStreamDTO;
	}

	@Override
	public PetDataExtractListResponse getPets(DuplicatePetsFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getPetsExtractData called");
		int searchedElements = 0;
		String counts = petDataExtractDao.getPetsCount(filter);
		int total = 0;
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonCountObj = mapper.readTree(counts);
			total = jsonCountObj.get("totalCount").asInt();
			searchedElements = jsonCountObj.get("searchedElementsCount").asInt();

		} catch (Exception e) {
			LOGGER.error("error while fetching PetsExtractDataCount ", e);
			throw new ServiceExecutionException(e.getMessage());
		}

		List<PetListDTO> petsList = total > 0 ? petDataExtractDao.getPets(filter) : new ArrayList<>();
		PetDataExtractListResponse response = new PetDataExtractListResponse();
		response.setPetsList(petsList);
		response.setNoOfElements(petsList.size());
		response.setTotalRecords(total);
		response.setSearchElments(searchedElements);
		LOGGER.debug("getPetsExtractData count is {}", petsList);
		LOGGER.debug("getPetsExtractData completed successfully");
		return response;
	}

	@Override
	public PetDataExtractConfigListResponse getPetDataExtractConfigList(DuplicatePetsFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getPetDataExtractConfigList called");
		int searchedElements = 0;
		String counts = petDataExtractDao.getPetDataExtractConfigListCount(filter);
		int total = 0;
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonCountObj = mapper.readTree(counts);
			total = jsonCountObj.get("totalCount").asInt();
			searchedElements = jsonCountObj.get("searchedElementsCount").asInt();

		} catch (Exception e) {
			LOGGER.error("error while fetching getPrimaryPetsCount ", e);
			throw new ServiceExecutionException(e.getMessage());
		}

		List<PetDataExtractConfigListDTO> petDataExtractConfigList = total > 0
				? petDataExtractDao.getPetDataExtractConfigList(filter)
				: new ArrayList<>();
		PetDataExtractConfigListResponse response = new PetDataExtractConfigListResponse();
		response.setPetDataExtractConfigList(petDataExtractConfigList);
		response.setNoOfElements(petDataExtractConfigList.size());
		response.setTotalRecords(total);
		response.setSearchElments(searchedElements);
		LOGGER.debug("getPetsExtractData count is {}", petDataExtractConfigList);
		LOGGER.debug("getPetsExtractData completed successfully");
		return response;
	}

	@Override
	public List<PetDataExtractStreamDTO> getPetDataExtractConfigById(int petId) throws ServiceExecutionException {
		LOGGER.debug("getPetDataExtractConfigById called.");
		List<PetDataExtractStreamDTO> list = petDataExtractDao.getPetDataExtractConfigById(petId);
		LOGGER.debug("getPetDataExtractConfigById completed successfully " + list.size());
		return list;
	}

}
