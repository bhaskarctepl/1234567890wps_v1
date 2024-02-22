package com.hillspet.wearables.dao.petparent;

import java.util.List;
import java.util.Map;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.PetParentDTO;
import com.hillspet.wearables.dto.PetParentListDTO;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.request.PetParentRequest;
import com.hillspet.wearables.request.PetParentValidateEmailRequest;
import com.hillspet.wearables.response.PetParentAddressResponse;

public interface PetParentDao {

	PetParentDTO addPetParent(PetParentRequest petParentRequest) throws ServiceExecutionException;

	PetParentDTO updatePetParent(PetParentRequest petParentRequest) throws ServiceExecutionException;

	PetParentDTO getPetParentById(int petParentId) throws ServiceExecutionException;

	String getPetParentListCount(BaseFilter filter) throws ServiceExecutionException;

	List<PetParentListDTO> getPetParents() throws ServiceExecutionException;

	List<PetParentListDTO> getPetParentList(BaseFilter filter) throws ServiceExecutionException;

	void deletePetParent(int petParentId, int modifiedBy) throws ServiceExecutionException;

	Map<String, Object> validatePetParentEmail(PetParentValidateEmailRequest petParentRequest)
			throws ServiceExecutionException;

	PetParentAddressResponse getPetParentAddressHistoryById(int petParentId) throws ServiceExecutionException;

	Map<String, Object>  validateUnAssignedPetAddress(int petId, int petParentId) throws ServiceExecutionException;

}
