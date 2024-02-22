package com.hillspet.wearables.service.pet;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.PetDTO;
import com.hillspet.wearables.dto.PetDataStreamDTO;
import com.hillspet.wearables.dto.PetDuplicateConfigDTO;
import com.hillspet.wearables.dto.filter.DuplicatePetsFilter;
import com.hillspet.wearables.dto.filter.PetDataStreamFilter;
import com.hillspet.wearables.request.DataStreamGroupRequest;
import com.hillspet.wearables.response.DuplicatePetConfigResponse;
import com.hillspet.wearables.response.DuplicatePetResponse;

public interface DuplicatePetMgmtService {

	public DuplicatePetResponse getPrimaryPetList(DuplicatePetsFilter filter) throws ServiceExecutionException;

	public DuplicatePetResponse getPrimaryPets(DuplicatePetsFilter filter) throws ServiceExecutionException;

	public DuplicatePetResponse getDuplicatePets(DuplicatePetsFilter filter) throws ServiceExecutionException;

	public List<PetDataStreamDTO> getDataStreams(@Valid PetDataStreamFilter filter) throws ServiceExecutionException;

	public PetDTO getPrimaryPetById(int petId) throws ServiceExecutionException;

	public DataStreamGroupRequest savePetDataStream(DataStreamGroupRequest request) throws ServiceExecutionException;

	public DuplicatePetConfigResponse getDuplicatePetConfigList(DuplicatePetsFilter filter) throws ServiceExecutionException;

	public Map<Object, List<PetDataStreamDTO>> getDuplicatePetConfigDetailsById(int petId) throws ServiceExecutionException;

	public void deleteDuplicatePet(int primaryPetId, int modifiedBy)  throws ServiceExecutionException;
}
