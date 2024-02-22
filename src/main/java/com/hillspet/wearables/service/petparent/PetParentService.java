package com.hillspet.wearables.service.petparent;

import java.util.Map;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.PetParentDTO;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.request.PetParentRequest;
import com.hillspet.wearables.request.PetParentValidateEmailRequest;
import com.hillspet.wearables.response.PetParentAddressResponse;
import com.hillspet.wearables.response.PetParentListResponse;

public interface PetParentService {

	PetParentDTO addPetParent(PetParentRequest petParentRequest) throws ServiceExecutionException;

	PetParentDTO updatePetParent(PetParentRequest petParentRequest) throws ServiceExecutionException;

	PetParentDTO getPetParentById(int petParentId) throws ServiceExecutionException;

	PetParentListResponse getPetParents() throws ServiceExecutionException;

	PetParentListResponse getPetParentList(BaseFilter filter) throws ServiceExecutionException;

	void deletePetParent(int petParentId, int modifiedBy) throws ServiceExecutionException;

	Map<String, Object> validatePetParentEmail(PetParentValidateEmailRequest petParentRequest)
			throws ServiceExecutionException;

	PetParentAddressResponse getPetParentAddressHistoryById(int petParentId) throws ServiceExecutionException;

	Map<String, Object>  validateUnAssignedPetAddress(int petId, int petParentId) throws ServiceExecutionException;

}
