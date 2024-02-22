package com.hillspet.wearables.jaxrs.resource.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.builders.JaxrsJsonResponseBuilder;
import com.hillspet.wearables.common.response.SuccessResponse;
import com.hillspet.wearables.dto.CustomUserDetails;
import com.hillspet.wearables.dto.PetDataExtractStreamDTO;
import com.hillspet.wearables.dto.PetDataExtractStreamsDTO;
import com.hillspet.wearables.dto.filter.DuplicatePetsFilter;
import com.hillspet.wearables.jaxrs.resource.PetDataExtractMgmtResource;
import com.hillspet.wearables.request.PetDataExtractStreamsRequest;
import com.hillspet.wearables.response.PetDataExtractConfigListResponse;
import com.hillspet.wearables.response.PetDataExtractListResponse;
import com.hillspet.wearables.response.PetDataExtractResponse;
import com.hillspet.wearables.response.PetDataExtractStreamResponse;
import com.hillspet.wearables.security.Authentication;
import com.hillspet.wearables.service.petDataExtract.PetDataExtractService;

@Service
public class PetDataExtractMgmtResourceImpl implements PetDataExtractMgmtResource {

	private static final Logger LOGGER = LogManager.getLogger(PetDataExtractMgmtResourceImpl.class);

	@Autowired
	private PetDataExtractService petDataExtractService;

	@Autowired
	private Authentication authentication;

	@Autowired
	private JaxrsJsonResponseBuilder responseBuilder;

	@Override
	public Response addPetDataExtractStreams(PetDataExtractStreamsRequest petDataExtractStreamsRequest) {
		petDataExtractStreamsRequest.setUserId(authentication.getAuthUserDetails().getUserId());
		PetDataExtractStreamDTO petDataExtractStreamDTO = petDataExtractService
				.addPetExtractStreams(petDataExtractStreamsRequest);
		PetDataExtractStreamResponse response = new PetDataExtractStreamResponse();
		response.setPetDataExtractStreamDTO(petDataExtractStreamDTO);
		SuccessResponse<PetDataExtractStreamResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPetDataExtractById(int petId) {
		PetDataExtractStreamsDTO petDataExtractStreamsDTO = petDataExtractService.getPetDataExtractById(petId);
		PetDataExtractResponse response = new PetDataExtractResponse();
		response.setPetDataExtractStreamsDTO(petDataExtractStreamsDTO);
		SuccessResponse<PetDataExtractResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPets(DuplicatePetsFilter filter) {
		LOGGER.info("getPets called");
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setUserId(userDetails.getUserId());
		filter.setRoleTypeId(userDetails.getRoleTypeId());
		PetDataExtractListResponse response = petDataExtractService.getPets(filter);
		SuccessResponse<PetDataExtractListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPetDataExtractConfigList(DuplicatePetsFilter filter) {
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setUserId(userDetails.getUserId());
		filter.setRoleTypeId(userDetails.getRoleTypeId());
		PetDataExtractConfigListResponse response = petDataExtractService.getPetDataExtractConfigList(filter);
		SuccessResponse<PetDataExtractConfigListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPetDataExtractConfigById(int petId) {
		List<PetDataExtractStreamDTO> list = petDataExtractService.getPetDataExtractConfigById(petId);
		SuccessResponse<List<PetDataExtractStreamDTO>> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(list);
		return responseBuilder.buildResponse(successResponse);
	}

}
