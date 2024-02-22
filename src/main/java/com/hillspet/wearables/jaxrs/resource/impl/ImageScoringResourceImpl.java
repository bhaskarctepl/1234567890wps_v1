package com.hillspet.wearables.jaxrs.resource.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.builders.JaxrsJsonResponseBuilder;
import com.hillspet.wearables.common.response.SuccessResponse;
import com.hillspet.wearables.dto.ImageScoringScale;
import com.hillspet.wearables.dto.ImageScoringScaleRequest;
import com.hillspet.wearables.dto.filter.ImageScaleFilter;
import com.hillspet.wearables.jaxrs.resource.ImageScoringResource;
import com.hillspet.wearables.objects.common.response.CommonResponse;
import com.hillspet.wearables.response.ImageScoringScaleListResponse;
import com.hillspet.wearables.response.ImageScoringScaleResponse;
import com.hillspet.wearables.security.Authentication;
import com.hillspet.wearables.service.imagescoring.ImageScoringScaleService;

@Service
public class ImageScoringResourceImpl implements ImageScoringResource {

	private static final Logger LOGGER = LogManager.getLogger(ImageScoringResourceImpl.class);

	@Autowired
	private ImageScoringScaleService imageScoringScaleService;

	@Autowired
	private Authentication authentication;

	@Autowired
	private JaxrsJsonResponseBuilder responseBuilder;

	@Override
	public Response addImageScoringScale(ImageScoringScaleRequest imageScoringScaleRequest) {
		imageScoringScaleRequest.setCreatedBy(authentication.getAuthUserDetails().getUserId());
		imageScoringScaleService.addImageScoringScale(imageScoringScaleRequest);
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response updateImageScoringScale(ImageScoringScaleRequest imageScoringScaleRequest) {
		imageScoringScaleRequest.setCreatedBy(authentication.getAuthUserDetails().getUserId());
		imageScoringScaleService.updateImageScoringScale(imageScoringScaleRequest);
		// build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getImageScoringScales(ImageScaleFilter filter) {
		ImageScoringScaleListResponse response = imageScoringScaleService.getImageScoringScales(filter);
		SuccessResponse<ImageScoringScaleListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getImageScoringScaleById(int imageScoringScaleId) {
		ImageScoringScale imageScoringScale = imageScoringScaleService.getImageScoringScaleById(imageScoringScaleId);
		ImageScoringScaleResponse response = new ImageScoringScaleResponse();
		response.setImageScoringScale(imageScoringScale);
		SuccessResponse<ImageScoringScaleResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response deleteImageScoringScale(int imageScoringScaleId) {
		imageScoringScaleService.deleteImageScoringScale(imageScoringScaleId,
				authentication.getAuthUserDetails().getUserId());

		// Step 5: build a successful response
		CommonResponse response = new CommonResponse();
		response.setMessage("Image Scoring Scale has been deleted successfully");
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getImageScoringScaleList() {
		List<ImageScoringScale> imageScoringScaleList = imageScoringScaleService.getImageScoringScaleList();
		ImageScoringScaleListResponse response = new ImageScoringScaleListResponse();
		response.setImageScoringScaleList(imageScoringScaleList);
		// Step 5: build a successful response
		SuccessResponse<ImageScoringScaleListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

}
