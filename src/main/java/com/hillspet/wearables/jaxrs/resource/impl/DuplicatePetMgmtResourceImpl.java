package com.hillspet.wearables.jaxrs.resource.impl;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.builders.JaxrsJsonResponseBuilder;
import com.hillspet.wearables.common.response.SuccessResponse;
import com.hillspet.wearables.dto.CustomUserDetails;
import com.hillspet.wearables.dto.PetDTO;
import com.hillspet.wearables.dto.PetDataStreamDTO;
import com.hillspet.wearables.dto.PetDuplicateConfigDTO;
import com.hillspet.wearables.dto.filter.DuplicatePetsFilter;
import com.hillspet.wearables.dto.filter.PetDataStreamFilter;
import com.hillspet.wearables.jaxrs.resource.DuplicatePetMgmtResource;
import com.hillspet.wearables.objects.common.response.CommonResponse;
import com.hillspet.wearables.request.DataStreamGroupRequest;
import com.hillspet.wearables.request.PetRequest;
import com.hillspet.wearables.response.DuplicatePetConfigResponse;
import com.hillspet.wearables.response.DuplicatePetResponse;
import com.hillspet.wearables.response.PetResponse;
import com.hillspet.wearables.security.Authentication;
import com.hillspet.wearables.service.pet.DuplicatePetMgmtService;

@Service
public class DuplicatePetMgmtResourceImpl implements DuplicatePetMgmtResource {

	private static final Logger LOGGER = LogManager.getLogger(DuplicatePetMgmtResourceImpl.class);

	@Autowired
	private DuplicatePetMgmtService duplicatePetMgmtService;

	@Autowired
	private Authentication authentication;

	@Autowired
	private JaxrsJsonResponseBuilder responseBuilder;

	@Override
	public Response getPrimaryPetList(DuplicatePetsFilter filter) {
		LOGGER.info("getPrimaryPetList called");
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setUserId(userDetails.getUserId());
		filter.setRoleTypeId(userDetails.getRoleTypeId());
		DuplicatePetResponse response = duplicatePetMgmtService.getPrimaryPetList(filter);
		SuccessResponse<DuplicatePetResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPrimaryPets(DuplicatePetsFilter filter) {
		LOGGER.info("getPrimaryPets called");
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setUserId(userDetails.getUserId());
		filter.setRoleTypeId(userDetails.getRoleTypeId());
		DuplicatePetResponse response = duplicatePetMgmtService.getPrimaryPets(filter);
		SuccessResponse<DuplicatePetResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getDuplicatePets(DuplicatePetsFilter filter) {
		LOGGER.info("getDuplicatePets called");
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setUserId(userDetails.getUserId());
		filter.setRoleTypeId(userDetails.getRoleTypeId());
		DuplicatePetResponse response = duplicatePetMgmtService.getDuplicatePets(filter);
		SuccessResponse<DuplicatePetResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getDataStreams(PetDataStreamFilter filter) {
		LOGGER.info("getDataStreams called");
		List<PetDataStreamDTO> response = duplicatePetMgmtService.getDataStreams(filter);
		SuccessResponse<List<PetDataStreamDTO>> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response manageDuplicatePets(@Valid PetRequest petRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getPrimaryPetById(int petId) {
		LOGGER.info("getPrimaryPetById called");
		PetDTO petDTO = duplicatePetMgmtService.getPrimaryPetById(petId);
		PetResponse response = new PetResponse();
		response.setPetDTO(petDTO);
		SuccessResponse<PetResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response saveDataStream(DataStreamGroupRequest dataStreamRequest) {
		// Step 2: process
		dataStreamRequest.setUserId(authentication.getAuthUserDetails().getUserId());
		
		duplicatePetMgmtService.savePetDataStream(dataStreamRequest);
		
		// Step 5: build a successful response
		SuccessResponse<PetResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(null);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getDuplicatePetConfigList(DuplicatePetsFilter filter) {
		LOGGER.info("getDuplicatePetConfigList called");
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setUserId(userDetails.getUserId());
		filter.setRoleTypeId(userDetails.getRoleTypeId());
		
		DuplicatePetConfigResponse response = duplicatePetMgmtService.getDuplicatePetConfigList(filter);
		SuccessResponse<DuplicatePetConfigResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getDuplicatePet(int primaryPetId) {
		LOGGER.info("getDuplicatePet called");
		Map<Object, List<PetDataStreamDTO>> response = duplicatePetMgmtService.getDuplicatePetConfigDetailsById(primaryPetId);
		
		SuccessResponse<Map<Object, List<PetDataStreamDTO>>> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response deleteDuplicatePet(int primaryPetId) {
		int modifiedBy = authentication.getAuthUserDetails().getUserId();
		duplicatePetMgmtService.deleteDuplicatePet(primaryPetId, modifiedBy);

		// Step 5: build a successful response
		CommonResponse response = new CommonResponse();
		response.setMessage("Duplicate Pet has been deleted successfully");
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

}
