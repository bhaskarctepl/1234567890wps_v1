package com.hillspet.wearables.dao.petDataExtract;

import java.util.List;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.PetDataExtractConfigListDTO;
import com.hillspet.wearables.dto.PetDataExtractStreamDTO;
import com.hillspet.wearables.dto.PetListDTO;
import com.hillspet.wearables.dto.filter.DuplicatePetsFilter;
import com.hillspet.wearables.request.PetDataExtractStreamsRequest;

public interface PetDataExtractDao {

	List<PetDataExtractStreamDTO> getPetDataExtractById(int petId) throws ServiceExecutionException;

	PetDataExtractStreamDTO addPetExtractStreams(PetDataExtractStreamsRequest petDataExtractStreamsRequest) throws ServiceExecutionException;
	
    String getPetsCount(DuplicatePetsFilter filter) throws ServiceExecutionException;
    
    PetListDTO getPetListById(int petId) throws ServiceExecutionException;

    List<PetListDTO> getPets(DuplicatePetsFilter filter) throws ServiceExecutionException;
    
    List<PetDataExtractConfigListDTO> getPetDataExtractConfigList(DuplicatePetsFilter filter)  throws ServiceExecutionException;

    String getPetDataExtractConfigListCount(DuplicatePetsFilter filter) throws ServiceExecutionException;

	List<PetDataExtractStreamDTO> getPetDataExtractConfigById(int petId) throws ServiceExecutionException;
}
