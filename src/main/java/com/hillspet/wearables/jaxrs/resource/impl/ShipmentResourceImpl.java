package com.hillspet.wearables.jaxrs.resource.impl;

import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.builders.JaxrsJsonResponseBuilder;
import com.hillspet.wearables.common.response.SuccessResponse;
import com.hillspet.wearables.dto.CustomUserDetails;
import com.hillspet.wearables.dto.PetDTO;
import com.hillspet.wearables.dto.Shipment;
import com.hillspet.wearables.dto.filter.ShipmentFilter;
import com.hillspet.wearables.jaxrs.resource.ShipmentResource;
import com.hillspet.wearables.objects.common.response.CommonResponse;
import com.hillspet.wearables.response.PetResponse;
import com.hillspet.wearables.response.ShipmentResponse;
import com.hillspet.wearables.response.ShipmentsResponse;
import com.hillspet.wearables.security.Authentication;
import com.hillspet.wearables.service.shipment.ShipmentService;

/**
 * This class providing Shipment details.
 * 
 * @author sgorle
 * @since w2.0
 * @version w2.0
 * @version Mar 8, 2022
 */
@Service
public class ShipmentResourceImpl implements ShipmentResource {

	private static final Logger LOGGER = LogManager.getLogger(ShipmentResourceImpl.class);

	@Autowired
	private ShipmentService shipmentService;

	@Autowired
	private Authentication authentication;

	@Autowired
	private JaxrsJsonResponseBuilder responseBuilder;

	@Override
	public Response getShipmentList(ShipmentFilter filter) {
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setUserId(userDetails.getUserId());
		filter.setRoleTypeId(userDetails.getRoleTypeId());
		ShipmentsResponse response = shipmentService.getShipmentList(filter);
		SuccessResponse<ShipmentsResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response addShipment(Shipment shipment) {
		// Step 1: process
		shipment.setUserId(authentication.getAuthUserDetails().getUserId());
		shipment = shipmentService.addShipment(shipment);
		ShipmentResponse response = new ShipmentResponse();
		response.setShipment(shipment);

		// Step 2: build a successful response
		SuccessResponse<ShipmentResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response updateShipment(Shipment shipment) {
		// Step 1: process
		shipment.setUserId(authentication.getAuthUserDetails().getUserId());
		shipment = shipmentService.updateShipment(shipment);
		ShipmentResponse response = new ShipmentResponse();
		response.setShipment(shipment);

		// Step 2: build a successful response
		SuccessResponse<ShipmentResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getShipmentById(int shipmentId) {
		Shipment shipment = shipmentService.getShipmentById(shipmentId);
		ShipmentResponse response = new ShipmentResponse();
		response.setShipment(shipment);
		SuccessResponse<ShipmentResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response deleteShipment(int shipmentId) {
		int modifiedBy = authentication.getAuthUserDetails().getUserId();
		shipmentService.deleteShipment(shipmentId, modifiedBy);

		// Step 5: build a successful response
		CommonResponse response = new CommonResponse();
		response.setMessage("Shipment has been deleted successfully");
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}
}
