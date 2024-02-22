package com.hillspet.wearables.dao.pet;

import java.util.List;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.DuplicatePetConfigDTO;
import com.hillspet.wearables.dto.PetDataStreamDTO;
import com.hillspet.wearables.dto.PetDuplicateConfigDTO;
import com.hillspet.wearables.dto.PetListDTO;
import com.hillspet.wearables.dto.filter.DuplicatePetsFilter;
import com.hillspet.wearables.dto.filter.PetDataStreamFilter;
import com.hillspet.wearables.request.DataStreamGroupRequest;

public interface DuplicatePetMgmtDao {

	public String getPrimaryPetsListCount(DuplicatePetsFilter filter) throws ServiceExecutionException;

	public List<PetListDTO> getPrimaryPetsList(DuplicatePetsFilter filter) throws ServiceExecutionException;

	public String getPrimaryPetsCount(DuplicatePetsFilter filter) throws ServiceExecutionException;

	public List<PetListDTO> getPrimaryPets(DuplicatePetsFilter filter) throws ServiceExecutionException;

	public String getDuplicatePetsCount(DuplicatePetsFilter filter) throws ServiceExecutionException;

	public List<PetListDTO> getDuplicatePets(DuplicatePetsFilter filter) throws ServiceExecutionException;

	public List<PetDataStreamDTO> getDataStreams(PetDataStreamFilter filter)throws ServiceExecutionException;
	
	public DataStreamGroupRequest saveDataStream(DataStreamGroupRequest request) throws ServiceExecutionException;

	public String getDuplicatePetConfigListCount(DuplicatePetsFilter filter) throws ServiceExecutionException;

	public List<DuplicatePetConfigDTO> getDuplicatePetConfigList(DuplicatePetsFilter filter)  throws ServiceExecutionException;

	public List<PetDuplicateConfigDTO> getDuplicatePetConfigDetailsById(int petId) throws ServiceExecutionException;

	public void deleteDuplicatePet(int primaryPetId, int modifiedBy)  throws ServiceExecutionException;

}
