package com.hillspet.wearables.service.petDataExtract;

import java.util.List;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.PetDataExtractStreamDTO;
import com.hillspet.wearables.dto.PetDataExtractStreamsDTO;
import com.hillspet.wearables.dto.filter.DuplicatePetsFilter;
import com.hillspet.wearables.request.PetDataExtractStreamsRequest;
import com.hillspet.wearables.response.PetDataExtractConfigListResponse;
import com.hillspet.wearables.response.PetDataExtractListResponse;

public interface PetDataExtractService {

	PetDataExtractStreamsDTO getPetDataExtractById(int petId) throws ServiceExecutionException;
	
	PetDataExtractListResponse getPets(DuplicatePetsFilter filter) throws ServiceExecutionException;

	PetDataExtractStreamDTO addPetExtractStreams(PetDataExtractStreamsRequest petDataExtractStreamsRequest) throws ServiceExecutionException;
	
	PetDataExtractConfigListResponse getPetDataExtractConfigList(DuplicatePetsFilter filter) throws ServiceExecutionException;

	List<PetDataExtractStreamDTO> getPetDataExtractConfigById(int petId)  throws ServiceExecutionException;
}
