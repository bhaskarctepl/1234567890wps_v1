package com.hillspet.wearables.service.pet.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.pet.DuplicatePetMgmtDao;
import com.hillspet.wearables.dto.DuplicatePetConfigDTO;
import com.hillspet.wearables.dto.PetDTO;
import com.hillspet.wearables.dto.PetDataStreamDTO;
import com.hillspet.wearables.dto.PetDuplicateConfigDTO;
import com.hillspet.wearables.dto.PetListDTO;
import com.hillspet.wearables.dto.filter.DuplicatePetsFilter;
import com.hillspet.wearables.dto.filter.PetDataStreamFilter;
import com.hillspet.wearables.request.DataStreamGroupRequest;
import com.hillspet.wearables.response.DuplicatePetConfigResponse;
import com.hillspet.wearables.response.DuplicatePetResponse;
import com.hillspet.wearables.service.pet.DuplicatePetMgmtService;



@Service
public class DuplicatePetMgmtServiceImpl implements DuplicatePetMgmtService {

	private static final Logger LOGGER = LogManager.getLogger(DuplicatePetMgmtServiceImpl.class);

	@Autowired
	private DuplicatePetMgmtDao duplicatePetMgmtDao;

	@Autowired
	private ObjectMapper mapper;

	@Override
	public DuplicatePetResponse getPrimaryPetList(DuplicatePetsFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getPrimaryPetList called");
		int searchedElements = 0;
		String counts = duplicatePetMgmtDao.getPrimaryPetsListCount(filter);
		int total = 0;
		try {
			JsonNode jsonCountObj = mapper.readTree(counts);
			total = jsonCountObj.get("totalCount").asInt();
			searchedElements = jsonCountObj.get("searchedElementsCount").asInt();

		} catch (Exception e) {
			LOGGER.error("error while fetching getPrimaryPetListCount ", e);
			throw new ServiceExecutionException(e.getMessage());
		}

		List<PetListDTO> primaryPetsList = total > 0 ? duplicatePetMgmtDao.getPrimaryPetsList(filter)
				: new ArrayList<>();

		DuplicatePetResponse response = new DuplicatePetResponse();
		response.setPrimaryPetsList(primaryPetsList);
		response.setNoOfElements(primaryPetsList.size());
		response.setTotalRecords(total);
		response.setSearchElments(searchedElements);

		LOGGER.debug("getPrimaryPetList count is {}", primaryPetsList);
		LOGGER.debug("getPrimaryPetList completed successfully");
		return response;
	}

	@Override
	public DuplicatePetResponse getPrimaryPets(DuplicatePetsFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getPrimaryPets called");
		int searchedElements = 0;
		String counts = duplicatePetMgmtDao.getPrimaryPetsCount(filter);
		int total = 0;
		try {
			JsonNode jsonCountObj = mapper.readTree(counts);
			total = jsonCountObj.get("totalCount").asInt();
			searchedElements = jsonCountObj.get("searchedElementsCount").asInt();

		} catch (Exception e) {
			LOGGER.error("error while fetching getPrimaryPetsCount ", e);
			throw new ServiceExecutionException(e.getMessage());
		}

		List<PetListDTO> primaryPetsList = total > 0 ? duplicatePetMgmtDao.getPrimaryPets(filter) : new ArrayList<>();

		DuplicatePetResponse response = new DuplicatePetResponse();
		response.setPrimaryPetsList(primaryPetsList);
		response.setNoOfElements(primaryPetsList.size());
		response.setTotalRecords(total);
		response.setSearchElments(searchedElements);

		LOGGER.debug("getPrimaryPets count is {}", primaryPetsList);
		LOGGER.debug("getPrimaryPets completed successfully");
		return response;
	}

	@Override
	public DuplicatePetResponse getDuplicatePets(DuplicatePetsFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getDuplicatePets called");
		int searchedElements = 0;
		String counts = duplicatePetMgmtDao.getDuplicatePetsCount(filter);
		int total = 0;
		try {
			JsonNode jsonCountObj = mapper.readTree(counts);
			total = jsonCountObj.get("totalCount").asInt();
			searchedElements = jsonCountObj.get("searchedElementsCount").asInt();

		} catch (Exception e) {
			LOGGER.error("error while fetching getDuplicatePetsCount ", e);
			throw new ServiceExecutionException(e.getMessage());
		}

		List<PetListDTO> primaryPetsList = total > 0 ? duplicatePetMgmtDao.getDuplicatePets(filter) : new ArrayList<>();

		DuplicatePetResponse response = new DuplicatePetResponse();
		response.setPrimaryPetsList(primaryPetsList);
		response.setNoOfElements(primaryPetsList.size());
		response.setTotalRecords(total);
		response.setSearchElments(searchedElements);

		LOGGER.debug("getDuplicatePets count is {}", primaryPetsList);
		LOGGER.debug("getDuplicatePets completed successfully");
		return response;
	}

	 
	@Override
	public PetDTO getPrimaryPetById(int petId) throws ServiceExecutionException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public DataStreamGroupRequest savePetDataStream(DataStreamGroupRequest request) throws ServiceExecutionException {
		LOGGER.debug("savePetDataStream called");
		return duplicatePetMgmtDao.saveDataStream(request);
	}

	@Override
	public DuplicatePetConfigResponse getDuplicatePetConfigList(DuplicatePetsFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getDuplicatePetConfigList called");
		int searchedElements = 0;
		String counts = duplicatePetMgmtDao.getDuplicatePetConfigListCount(filter);
		int total = 0;
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonCountObj = mapper.readTree(counts);
			total = jsonCountObj.get("totalCount").asInt();
			searchedElements = jsonCountObj.get("searchedElementsCount").asInt();

		} catch (Exception e) {
			LOGGER.error("error while fetching getDuplicatePetsCount ", e);
			throw new ServiceExecutionException(e.getMessage());
		}

		List<DuplicatePetConfigDTO> petList = total > 0 ? duplicatePetMgmtDao.getDuplicatePetConfigList(filter) : new ArrayList<>();

		DuplicatePetConfigResponse response = new DuplicatePetConfigResponse();
		response.setPetList(petList);
		response.setNoOfElements(petList.size());
		response.setTotalRecords(total);
		response.setSearchElments(searchedElements);
		
		LOGGER.debug("getDuplicatePetList count is {}", petList.size());
		LOGGER.debug("getDuplicatePetList completed successfully");
		return response;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<Object, List<PetDataStreamDTO>> getDuplicatePetConfigDetailsById(int petId) throws ServiceExecutionException {
		LOGGER.debug("getDuplicatePet called.");
		List<PetDuplicateConfigDTO> result = duplicatePetMgmtDao.getDuplicatePetConfigDetailsById(petId);
		
		Map<Object, List<PetDuplicateConfigDTO>> grouped = new HashMap<>();
		Map<Object, List<PetDataStreamDTO>> resultMap = new HashMap<>();
		
		if(result.size() > 0) {
			grouped = result.stream().collect(Collectors.groupingBy(w -> w.getStitchGroupId()));
			
			Map<Object, List<PetDuplicateConfigDTO>> sortedMap = new TreeMap<>(grouped);
			
			List<PetDuplicateConfigDTO> resultNew = new ArrayList(sortedMap.values());

			
			for (int i = 0; i < resultNew.size(); i++) {
				
				List<PetDataStreamDTO> totalList = new ArrayList<>();
				
				List<PetDuplicateConfigDTO> list = (List<PetDuplicateConfigDTO>) resultNew.get(i);
				
				for (PetDuplicateConfigDTO configDTO : list) {
					
					if(configDTO.getPetType().equals("P")) {
						PetDataStreamDTO dto = new PetDataStreamDTO();
						dto.setPetId(configDTO.getPetId());
						dto.setPetName(configDTO.getPetName());
						dto.setStartDate(configDTO.getStartDate());
						dto.setEndDate(configDTO.getEndDate());
						dto.setStudyName(configDTO.getStudyName());
						
						dto.setStreamId(configDTO.getStreamId());
						
						dto.setPetType(configDTO.getPetType());
						
						dto.setPetDuplicateConfigID(configDTO.getConfigId());
						
						dto.setExtractStartDate(configDTO.getExtractStartDate());
						dto.setExtractEndDate(configDTO.getExtractEndDate());
						dto.setAssetNumber(configDTO.getAssetNumber());
						
						dto.setPetStudyId(configDTO.getPetStudyId());
						dto.setExcludeFromDataExtract(configDTO.getExcludeFromDataExtract());
						dto.setDupExcludeFromDataExtract(configDTO.getDupExcludeFromDataExtract());
						
						if(!totalList.contains(dto))
						totalList.add(dto);
					}
					
					if(configDTO.getPetType().equals("D")) {
						PetDataStreamDTO dto = new PetDataStreamDTO();
						dto.setPetId(configDTO.getPetId());
						dto.setPetName(configDTO.getPetName());
						dto.setStartDate(configDTO.getStartDate());
						dto.setEndDate(configDTO.getEndDate());
						dto.setStudyName(configDTO.getStudyName());
						
											
						dto.setStreamId(configDTO.getStreamId());
						dto.setPetType(configDTO.getPetType());
						
						dto.setPetDuplicateConfigID(configDTO.getConfigId());
						
						dto.setExtractStartDate(configDTO.getExtractStartDate());
						dto.setExtractEndDate(configDTO.getExtractEndDate());
						dto.setAssetNumber(configDTO.getAssetNumber());
						
						dto.setPetStudyId(configDTO.getPetStudyId());
						dto.setExcludeFromDataExtract(configDTO.getExcludeFromDataExtract());
						dto.setDupExcludeFromDataExtract(configDTO.getDupExcludeFromDataExtract());
						
						totalList.add(dto);
					}
					
					/*if(primaryList.size() > 0) {
						totalList.addAll(primaryList);
					}
					if(duplicateList.size() > 0) {
						totalList.addAll(duplicateList);
					}*/
				}
				
				//Map<Integer, PetDataStreamDTO> map =  totalList.stream().collect(Collectors.toMap(PetDataStreamDTO::getPetId, d -> d, (a, b) -> a));
				
				List<PetDataStreamDTO> uniqueList = new ArrayList<>(totalList);
				uniqueList.sort(Comparator.comparing(PetDataStreamDTO::getPetDuplicateConfigID, 
						Comparator.nullsLast(Comparator.naturalOrder())));
				
				resultMap.put(i, uniqueList);
			}
		}
		
		
		
		
		return resultMap;
	}

	@Override
	public List<PetDataStreamDTO> getDataStreams(PetDataStreamFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getDataStreams called");

		List<PetDataStreamDTO> dataStreamList = duplicatePetMgmtDao.getDataStreams(filter);

		LOGGER.debug("getDataStreams completed successfully");
		return dataStreamList;
	}

	@Override
	public void deleteDuplicatePet(int primaryPetId, int modifiedBy) throws ServiceExecutionException {
		LOGGER.debug("deleteDuplicatePet called");
		duplicatePetMgmtDao.deleteDuplicatePet(primaryPetId, modifiedBy);
		LOGGER.debug("deleteDuplicatePet completed successfully");
	}

}
